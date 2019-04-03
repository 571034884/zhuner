package com.aibabel.food.base;

import android.content.pm.PackageManager;

import com.aibabel.baselibrary.base.BaseApplication;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.baselibrary.utils.DeviceUtils;


/**
 * 作者：SunSH on 2018/12/3 19:01
 * 功能：
 * 版本：1.0
 */
public class MyApp extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        BaseApplication.setAllPhysicalButtonsExitEnable(true);
    }

    @Override
    public String getAppVersionName() {
        try {
            return getPackageManager().getPackageInfo(getPackageName(),0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return  null;

    }

    @Override
    public String getAppPackageName() {
        return getPackageName();
    }

    @Override
    public void setServerUrlAndInterfaceGroup() {
        //设置服务器地址
//        OkGoUtil.setDefualtServerUrl("http://39.107.238.111:7001");
        OkGoUtil.setDefualtServerUrl("http://abroad.api.joner.aibabel.cn:7001");
        OkGoUtil.setDefaultInterfaceGroup("/v1/food/");
    }

    @Override
    public String setUmengKey() {
        if(lease_Debug_v&& DeviceUtils.getSystem()==DeviceUtils.System.PRO_LEASE){
            return "5c9ac8ab61f564a1ff000856";
        }
        return "5c1c518cf1f556458d000a08";
    }
}
