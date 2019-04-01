package com.aibabel.fyt_play.app;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;

import com.aibabel.baselibrary.BuildConfig;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.baselibrary.utils.DeviceUtils;
import com.aibabel.fyt_play.utils.DensityHelper;
import com.lzy.okgo.OkGo;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Wuqinghua on 2018/6/28 0028.
 */
public class BaseApplication extends com.aibabel.baselibrary.base.BaseApplication {

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

//        OkGoUtil.setDefualtServerUrl("http://abroad.api.joner.aibabel.cn:7001" );
        OkGoUtil.setDefualtServerUrl("http://39.107.238.111:7001" );
        OkGoUtil.setDefaultInterfaceGroup("/v1/play/");
    }

    @Override
    public String setUmengKey() {
        if(lease_Debug_v&&DeviceUtils.getSystem()==DeviceUtils.System.PRO_LEASE){
            return "5c9a245661f5641241000e8f";
        }
        return "5c33109af1f5566770000134";
    }
}
