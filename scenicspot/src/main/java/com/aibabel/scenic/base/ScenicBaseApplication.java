package com.aibabel.scenic.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.aibabel.baselibrary.base.BaseApplication;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.baselibrary.sphelper.SPHelper;
import com.aibabel.baselibrary.utils.CommonUtils;
import com.aibabel.baselibrary.utils.DeviceUtils;
import com.aibabel.baselibrary.utils.FastJsonUtil;
import com.aibabel.baselibrary.utils.ServerKeyUtils;
import com.aibabel.scenic.bean.AddressBean;
import com.aibabel.scenic.okgo.ApiConstant;
import com.aibabel.scenic.utils.CityConfig;
import com.aibabel.scenic.utils.Logs;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Interceptor;

import static android.content.ContentValues.TAG;


public class ScenicBaseApplication extends BaseApplication {

    public static int stateCount = 0;

    private static Context CONTEXT;

    private static BackGroundListener listener;

    public static AddressBean addressBeanAsia = null;//亚洲
    public static AddressBean addressBeanEuiope = null;//欧洲
    public static AddressBean addressBeanAfrica = null;//非洲
    public static AddressBean addressBeanSouth = null;//南美洲
    public static AddressBean addressBeanNorth = null;//北美洲
    public static AddressBean addressBeanOceanica = null;//大洋洲
    public static String LEASEID = "";//订单id

    /**
     * 存储程序中所创建的activity
     */
    private static LinkedList<Activity> activityLinkedList;

    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT = this.getApplicationContext();
        initAppExitConfig();
        configUmeng();
        initCountry();
    }

    //初始化城市数据
    private void initCountry() {
        addressBeanAsia = FastJsonUtil.changeJsonToBean(CityConfig.asiaFragment,AddressBean.class);
        addressBeanEuiope = FastJsonUtil.changeJsonToBean(CityConfig.euiopeFragment,AddressBean.class);
        addressBeanAfrica = FastJsonUtil.changeJsonToBean(CityConfig.africaFragment,AddressBean.class);
        addressBeanSouth = FastJsonUtil.changeJsonToBean(CityConfig.southFragment,AddressBean.class);
        addressBeanNorth = FastJsonUtil.changeJsonToBean(CityConfig.northFragment,AddressBean.class);
        addressBeanOceanica = FastJsonUtil.changeJsonToBean(CityConfig.oceaniaFragment,AddressBean.class);
        LEASEID = SPHelper.getString("order_oid","");

        Logs.e(ApiConstant.HOST);
        ApiConstant.HOST = SPHelper.getString(ServerKeyUtils.serverKeyScenic,"http://abroad.api.joner.aibabel.cn:7001");
        Logs.e(ApiConstant.HOST);
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
        OkGoUtil.setDefualtServerUrl(ApiConstant.HOST);
        OkGoUtil.setDefaultInterfaceGroup(ApiConstant.HOST_GROUP);
    }

    @Override
    public String setUmengKey() {
        if(lease_Debug_v&&DeviceUtils.getSystem()== DeviceUtils.System.PRO_LEASE) {
            return "5c9ac851203657850a0004fd";
        }else {
            return "5c9ac851203657850a0004fd";
//            return "5b519f22a40fa35134000042";

        }
    }


    private void configUmeng() {

        UMConfigure.init(this, "5b519f22a40fa35134000042", CommonUtils.getSN(), UMConfigure.DEVICE_TYPE_PHONE,
                null);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }

    /**
     * 获取全局context
     * @return
     */
    public static Context getContext() {

        return CONTEXT;
    }

    /**
     * 每当创建新的activity的时候，添加Activity到list中，方便统一退出
     */
    public void initAppExitConfig() {

        activityLinkedList = new LinkedList<>();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                activityLinkedList.add(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Log.d(TAG, "onActivityStarted: " + activity.getLocalClassName());

            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
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
                activityLinkedList.remove(activity);
            }
        });
    }


    /**
     * 判断程序是否在后台运行
     *
     * @return
     */
    public static boolean isBackground() {
        return ScenicBaseApplication.stateCount == 0;
    }


    /**
     * 退出所有app
     */
    public static void exit() {
        if(activityLinkedList!=null&& activityLinkedList.size()>0){
            for (Activity activity : activityLinkedList) {
                activity.finish();
            }
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        },3000);    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (null != listener)
            listener.isBackGround(true);
    }


    public void setListener(BackGroundListener listener) {
        this.listener = listener;
    }

    public interface BackGroundListener {

        void isBackGround(boolean isBack);

    }

}
