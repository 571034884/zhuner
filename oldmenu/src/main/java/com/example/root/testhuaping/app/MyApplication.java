package com.example.root.testhuaping.app;

import android.app.Application;
import android.util.Log;

import com.example.root.testhuaping.service.Getsystem_info;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;


/**
 * Created by root on 18-8-8.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        UMConfigure.init(this, "5b6a5215f29d98775a000176",Getsystem_info.getSN_SN(), UMConfigure.DEVICE_TYPE_PHONE,
                null);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        Log.e("*********","start Application");
        super.onCreate();
    }
}
