package com.aibabel.launcher;

import com.aibabel.baselibrary.base.BaseApplication;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.baselibrary.utils.CommonUtils;
import com.aibabel.launcher.utils.Logs;

/**
 * Created by fytworks on 2019/4/16.
 */

public class LauncherApplication extends BaseApplication{

    @Override
    public void onCreate() {
        super.onCreate();

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
}
