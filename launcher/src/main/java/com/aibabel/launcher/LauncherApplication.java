package com.aibabel.launcher;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import com.aibabel.baselibrary.base.BaseApplication;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.baselibrary.utils.CommonUtils;
import com.aibabel.launcher.service.MyService;
import com.aibabel.launcher.utils.Logs;
import com.aibabel.message.helper.DemoHelper;

import java.util.List;

/**
 * Created by fytworks on 2019/4/16.
 */

public class LauncherApplication extends BaseApplication{

    private static LauncherApplication instance;
    public static Context applicationContext;



    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        applicationContext = this;
        //init helper
        DemoHelper.getInstance().init(applicationContext);

        startService(new Intent(this, MyService.class));

    }

    @Override
    public String getAppVersionName() {
        return null;
    }

    @Override
    public String getAppPackageName() {
        return null;
    }

    @Override
    public void setServerUrlAndInterfaceGroup() {
        String HOST = "http://abroad.api.function.aibabel.cn:7001";
        switch (CommonUtils.getTimerType()){
            case 0://国外
                HOST = "http://abroad.api.function.aibabel.cn:7001";
                break;
            case 1://国内
                HOST = "http://api.function.aibabel.cn:7001";
                break;
        }
        Logs.e(HOST);
        //设置服务器地址
        OkGoUtil.setDefualtServerUrl(HOST);
//        //如果需要其他接口组
        OkGoUtil.setDefaultInterfaceGroup("/v1/deviceMenu/");

    }

    @Override
    public String setUmengKey() {
        return null;
    }

    public static LauncherApplication getInstance() {
        return instance;
    }
}
