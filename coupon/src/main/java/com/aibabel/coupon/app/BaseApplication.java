package com.aibabel.coupon.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;

import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.coupon.BuildConfig;
import com.aibabel.coupon.utils.DensityHelper;
import com.lzy.okgo.OkGo;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Wuqinghua on 2018/6/28 0028.
 */
public class BaseApplication extends com.aibabel.baselibrary.base.BaseApplication{

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
        OkGoUtil.setDefaultInterfaceGroup("/v1/coupon/");
    }

    @Override
    public String setUmengKey() {
        return "5c331054f1f556aa320007af";
    }


}
