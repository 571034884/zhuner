package com.aibabel.fyt_exitandentry.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;


import com.aibabel.baselibrary.BuildConfig;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.baselibrary.utils.DeviceUtils;
import com.aibabel.fyt_exitandentry.utils.DensityHelper;
import com.lzy.okgo.OkGo;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Wuqinghua on 2018/6/28 0028.
 */
public class BaseApplication extends com.aibabel.baselibrary.base.BaseApplication {
    /**
     * 存储程序中所创建的activity
     */
    private static LinkedList<Activity> activityLinkedList;
    public static int stateCount = 0;
    private static final int DESIGN_WIDTH = 540;
    @Override
    public void onCreate() {
        super.onCreate();
        com.aibabel.baselibrary.base.BaseApplication.setAllPhysicalButtonsExitEnable(true);




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
        OkGoUtil.setDefualtServerUrl( "http://abroad.api.joner.aibabel.cn:7001");
        OkGoUtil.setDefaultInterfaceGroup("/v1/entryAndExit/");
    }

    @Override
    public String setUmengKey() {
        if(lease_Debug_v&& DeviceUtils.getSystem()==DeviceUtils.System.PRO_LEASE){
            return "5c9ac8ab61f564a1ff000856";
        }

        return "5c3310b7f1f556e6ab001396";
    }

}
