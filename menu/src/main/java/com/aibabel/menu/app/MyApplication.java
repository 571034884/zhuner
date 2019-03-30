package com.aibabel.menu.app;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.aibabel.baselibrary.base.BaseApplication;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.baselibrary.utils.DeviceUtils;
import com.aibabel.menu.BuildConfig;
import com.aibabel.menu.MainActivity;
import com.aibabel.menu.util.AppStatusUtils;
import com.aibabel.menu.util.CommonUtils;
import com.aibabel.menu.util.FileUtil;
import com.aibabel.menu.util.L;
import com.aibabel.menu.util.LogUtil;
import com.aibabel.menu.util.SPUtils;
import com.liulishuo.filedownloader.FileDownloader;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;
import org.litepal.tablemanager.Connector;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

public class MyApplication extends BaseApplication {
    //一个小时更新一下桌面天气
    public static int num=1;
    public static int frist=0;

    public static String  city_id="";
    public static String  country_id="";

    @Override
    public void onCreate() {
        super.onCreate();
        //开机清除   城市切换标识
        SPUtils.put(getApplicationContext(),"isChange","");
//        SPUtils.put(getApplicationContext(),"cityName","");
//        SPUtils.put(getApplicationContext(),"isChange","");
        FileDownloader.setup(this);
        //适配类初始化
        LitePal.initialize(this);
        initSQLite();
        configJPush();
//        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler(this.getApplicationContext()));

        LogUtil.e("hjs MyApplication ==");

        if (FileUtil.isFolderExists("/sdcard/download_offline/")) {

        }

    }

    private void initSQLite() {
        //创建表,如果存在就不会在创建了
        SQLiteDatabase db = Connector.getDatabase();
    }

    @Override
    public String getAppVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    @Override
    public String getAppPackageName() {
        return getPackageName();
    }

    @Override
    public void setServerUrlAndInterfaceGroup() {

        //设置服务器地址
        OkGoUtil.setDefualtServerUrl("http://abroad.api.function.aibabel.cn:7001");
//        //如果需要其他接口组
        OkGoUtil.setDefaultInterfaceGroup("/v1/deviceMenu/");

//        OkGoUtil.setDefualtServerUrl("http://39.107.238.111:7001");
//        //如果需要其他接口组
//        OkGoUtil.setDefaultInterfaceGroup("/v1/deviceMenu/");

    }

//    @Override
//    public void setServerUrlAndInterfaceGroup(String s) {
//
//        //设置服务器地址
//        OkGoUtil.setServerUrl(TextUtils.equals(s, "") ? "http://39.107.238.111:7001" : s);
//        //如果需要其他接口组
//        OkGoUtil.setDefaultInterfaceGroup("/v1/deviceMenu/");
//
//
//
//    }

    @Override
    public String setUmengKey() {
        if(lease_Debug_v&& DeviceUtils.getSystem()==DeviceUtils.System.PRO_LEASE){
            return "5c9ac8ab61f564a1ff000856";
        }

        L.e("setUmengKey debug=============="+AppStatusUtils.isApkInDebug(getApplicationContext()));
        if (!AppStatusUtils.isApkInDebug(getApplicationContext())) {
           //debug
//            L.e("setUmengKey debug==============");
            return "5bfe33c8b465f55342000344";
        }
            //release
            return "5bfe125db465f545ae0000eb";


    }

    /**
     * 配置极光推送
     */
    public void configJPush() {
        Log.e("BaseApplication","配置极光推送");
        JPushInterface.setDebugMode (true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init (this);
        L.e("SN:"+CommonUtils.getSN());
        JPushInterface.setAlias(this, 1, CommonUtils.getSN());
        JPushInterface.getAlias(this,1);
        Set<String> hashSet = new HashSet<>();
        hashSet.add(CommonUtils.getDevice());
        try {
            hashSet.add(CommonUtils.getVerName(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        JPushInterface.setTags(this, 2, hashSet);
        //设置通知只显示20条
        JPushInterface.setLatestNotificationNumber(this, 20);

    }
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (MainActivity.upListener!=null) {
            MainActivity.upListener.closePlayer();
        }
    }
}
