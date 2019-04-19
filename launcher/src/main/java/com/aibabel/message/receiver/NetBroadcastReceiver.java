package com.aibabel.message.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.aibabel.baselibrary.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

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


}
