package com.aibabel.coupon.app;

import android.content.pm.PackageManager;

import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.baselibrary.utils.DeviceUtils;

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
        OkGoUtil.setDefualtServerUrl( "http://abroad.api.joner.aibabel.cn:7001");
        OkGoUtil.setDefaultInterfaceGroup("/v1/coupon/");
    }

    @Override
    public String setUmengKey() {
        if(lease_Debug_v&& DeviceUtils.getSystem()==DeviceUtils.System.PRO_LEASE){
            return "5c9ac8ab61f564a1ff000856";
        }

        return "5c331054f1f556aa320007af";
    }


}
