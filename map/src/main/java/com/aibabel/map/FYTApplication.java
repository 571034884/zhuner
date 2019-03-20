package com.aibabel.map;

import android.app.Activity;
import android.app.Service;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;

import com.aibabel.baselibrary.base.BaseApplication;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.map.service.LocationService;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;

import static android.content.ContentValues.TAG;

/**
 * Created by fytworks on 2018/9/11.
 */

public class FYTApplication extends BaseApplication {
    private boolean mMainOnPaused = false;
    private boolean mMainOnResumed = false;
    public Vibrator mVibrator;


    public LocationService locationService;
    //定位信息
    private static BDLocation location;

    public static BDLocation getLocation() {
        return location;
    }

    public static void setLocation(BDLocation location) {
        FYTApplication.location = location;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        initBaiDu();
        BaseApplication.setAllPhysicalButtonsExitEnable(true);
//        registerActivityLifecycle();
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
//        OkGoUtil.setDefualtServerUrl("http://abroad.api.joner.aibabel.cn:7001");
        OkGoUtil.setDefualtServerUrl("http://39.107.238.111:7001");
        OkGoUtil.setDefaultInterfaceGroup("/v1/baiduMap/");
        //如果需要其他接口组
//        OkGoUtil.addOtherInterfaceGroup("/v1/destination/");
//        OkGoUtil.otherInterfaceGroup.get();
    }

    @Override
    public String setUmengKey() {
        return null;
    }

    private void initBaiDu() {
        locationService = new LocationService(getApplicationContext());

        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(getApplicationContext());
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);

        //启动服务
        locationService.start();
    }

    private void registerActivityLifecycle() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                Log.d(TAG, "onActivityStarted: " + activity.getLocalClassName());
                stateCount++;
            }

            @Override
            public void onActivityResumed(Activity activity) {
                // TODO: 2018/12/17 执行了
                mMainOnResumed = (activity instanceof MainActivity);
                if (mMainOnPaused && mMainOnResumed) {
                    // 应用从桌面或者其他地方回来
                    // 可以做一些回调
                    MainActivity mainActivity = (MainActivity)activity;
                    mainActivity.initLocationOption();

                }

            }

            @Override
            public void onActivityPaused(Activity activity) {
                mMainOnPaused = (activity instanceof MainActivity);
            }

            @Override
            public void onActivityStopped(Activity activity) {
                stateCount--;
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

}
