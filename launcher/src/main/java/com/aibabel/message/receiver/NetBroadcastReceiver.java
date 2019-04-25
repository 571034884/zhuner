package com.aibabel.message.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;

import com.aibabel.baselibrary.utils.CommonUtils;
import com.aibabel.baselibrary.utils.FastJsonUtil;
import com.aibabel.menu.bean.PublicBean;
import com.aibabel.menu.utils.Logs;
import com.aibabel.menu.utils.ServerUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tencent.mmkv.MMKV;

import static android.content.Context.WIFI_SERVICE;


/**
 * ==========================================================================================
 *
 * @Author： 张文颖
 * @Date：2018/11/9
 * @Desc：网络变化监听广播
 * @==========================================================================================
 */
public class NetBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "NetBroadcastReceiver";

    private NetListener listener;

    @Override
    public void onReceive(Context context, Intent intent) {
        MMKV mmkv = MMKV.defaultMMKV();
        if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            boolean this_networkAvailable = CommonUtils.isNetworkAvailable(context);
            if (this_networkAvailable) {
                Log.e(TAG, this_networkAvailable + "---");
                listener.netState(true);
                //判断是否是wifi
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetInfo != null) {
                    // 判断是wifi连接
                    if (activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                        WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
                        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                        listener.netState(wifiInfo.getSSID().replaceAll("\"",""));
                    }
                }

                //预判服务器域名
                String serIP = mmkv.decodeString("serIP","");
                if (TextUtils.isEmpty(serIP)){
                    Logs.e("当前 serIP 空");
                    getInternetService(context);
                }else{
                    long intIP = Long.parseLong(serIP);
                    long outIP = System.currentTimeMillis();
                    long results = outIP - intIP;

                    Logs.e("服务器域名计算:"+outIP+"-"+intIP+"="+results);
                    //超过5小时 请求一次 1800000
                    if (results > 1800000){
                        mmkv.encode("serIP",outIP+"");
                        getInternetService(context);
                    }
                }
            } else {
                listener.netState(false);
                listener.netState("WIFI");
            }
        }

    }


    public void setListener(NetListener listener) {
        this.listener = listener;
    }

    public interface NetListener {
        void netState(boolean isAvailable);
        void netState(String nameWifi);
    }

    private int servers = 0;

    /**
     * 1.获取服务器域名
     * 2.存储
     * 3.开启定时轮询
     * 1.判断容错是否达到3次
     * 1.达到三次
     * 判断时区，切换域名   容错清零
     * 2.未达到三次
     * 判断时区，域名不做变动     超时容错清零
     */
    private void getInternetService(final Context context) {
        Log.e("SERVICE_FUWU", "开始请求域名接口");
        String HOST_SERVER = "http://abroad.api.joner.aibabel.cn:7001";
        switch (CommonUtils.getTimerType()){
            case 1:
                HOST_SERVER = "http://api.joner.aibabel.cn:7001";
                break;
            case 0:
                HOST_SERVER = "http://abroad.api.joner.aibabel.cn:7001";
                break;
        }
        OkGo.<String>get(HOST_SERVER+"/v2/jonerconfig/getConfig")
                .tag(this)
                .params("sn", CommonUtils.getSN())
                .params("no", CommonUtils.getRandom())
                .params("sl", CommonUtils.getLocalLanguage())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body().toString())) {
                            Log.e("SERVICE_FUWU", "onSuccess:" + response.body().toString());
                            MMKV.defaultMMKV().encode("serIP",System.currentTimeMillis()+"");
                            saveService(response.body().toString());
                        } else {
                            //TODO 获取服务列表空
                            Log.e("SERVICE_FUWU", "onSuccess:数据空");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        switch (servers) {
                            case 0:
                                servers = 1;
                                getInternetService(context);
                                Log.e("SERVICE_FUWU", "获取服务器列表 第一次 出错");
                                break;
                            case 1:
                                servers = 2;
                                getInternetService(context);
                                Log.e("SERVICE_FUWU", "获取服务器列表 第二次 出错");
                                break;
                            case 2:
                                servers = 0;
                                Log.e("SERVICE_FUWU", "获取服务器列表 第三次 出错");
                                break;
                        }

                        Log.e("SERVICE_FUWU", "onError:" + response.getException().toString());
                    }
                });
    }
    private void saveService(String response) {
        try {
            PublicBean bean = FastJsonUtil.changeJsonToBean(response, PublicBean.class);
            //备份所有服务器数据，使用,分割
            ServerUtils.saveAllServer(bean.data.server);
//            ServerManager.getInstance().setPingServerError(ServerKeyUtils.serverKeyChatJoner);
            //TODO 任务
        } catch (Exception e) {
            Log.e("SERVICE_FUWU", "onError:" + e.toString());
        }
    }
}
