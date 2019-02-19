package com.aibabel.menu.app;

import android.os.Build;
import android.text.TextUtils;

import com.aibabel.baselibrary.base.BaseApplication;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.menu.BuildConfig;
import com.aibabel.menu.MainActivity;
import com.aibabel.menu.util.AppStatusUtils;
import com.aibabel.menu.util.FileUtil;
import com.aibabel.menu.util.L;
import com.aibabel.menu.util.SPUtils;
import com.aibabel.menu.util.UrlConstants;
import com.liulishuo.filedownloader.FileDownloader;

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


//        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler(this.getApplicationContext()));

        if (FileUtil.isFolderExists("/sdcard/download_offline/")) {

        }

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
        //如果需要其他接口组
        OkGoUtil.setDefaultInterfaceGroup("/v1/deviceMenu/");
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
        L.e("setUmengKey debug=============="+AppStatusUtils.isApkInDebug(getApplicationContext()));
        if (!AppStatusUtils.isApkInDebug(getApplicationContext())) {
           //debug
//            L.e("setUmengKey debug==============");
            return "5bfe33c8b465f55342000344";
        }
            //release
            return "5bfe125db465f545ae0000eb";


    }


    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (MainActivity.upListener!=null) {
            MainActivity.upListener.closePlayer();

        }

    }
}
