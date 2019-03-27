package com.aibabel.ocr.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.baselibrary.utils.CommonUtils;
import com.aibabel.baselibrary.utils.DeviceUtils;
import com.aibabel.ocr.BuildConfig;
import com.aibabel.ocr.utils.DensityHelper;
import com.lzy.okgo.OkGo;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

import static android.content.ContentValues.TAG;


public class BaseApplication extends com.aibabel.baselibrary.base.BaseApplication {

    public static Application CONTEXT;
    public static int stateCount = 0;
    /**
     * 存储程序中所创建的activity
     */
    private static LinkedList<Activity> activityLinkedList;
    public static DisplayMetrics displayMetrics;
    public static int screenW = 540;
    public static int screenH = 960;
    private static final int DESIGN_WIDTH = 540;
    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT = this;
        configOKGo();
        displayMetrics = getResources().getDisplayMetrics();
        screenH = displayMetrics.heightPixels;
        screenW = displayMetrics.widthPixels;
        initAppExitConfig();
        configUmeng();
        initLayoutConfig();
        StatisticsManager.getInstance(this).setConfig(getPackageName(), BuildConfig.VERSION_NAME);
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

    }

    @Override
    public String setUmengKey() {
        return null;
    }

    /**
     * 初始化布局适配  布局中使用pt做位单位
     */
    public void initLayoutConfig() {
        new DensityHelper(this, DESIGN_WIDTH).activate();
    }

    /**
     * 初始化友盟统计
     */
    private void  configUmeng(){
        if(lease_Debug_v&& DeviceUtils.getSystem()==DeviceUtils.System.PRO_LEASE){
            UMConfigure.init(this, "5c9ac86861f564b955000550", CommonUtils.getSN(), UMConfigure.DEVICE_TYPE_PHONE,
                    null);
            MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        }else {
            UMConfigure.init(this, "5b519cdff29d981e1400002b", CommonUtils.getSN(), UMConfigure.DEVICE_TYPE_PHONE,
                    null);
            MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        }
    }

    /**
     * 初始化OKGO
     */
   private void configOKGo(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //全局的读取超时时间
        builder.readTimeout(30*1000, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(30*1000, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(10*1000, TimeUnit.MILLISECONDS);
        OkGo.getInstance().init(this).setRetryCount(0).setOkHttpClient(builder.build()); //必须调用初始化
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
                stateCount++;
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
     * 判断程序是否启动
     *
     * @param context
     * @return
     */
    public static boolean isStart(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        boolean isAppRunning = false;
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(context.getPackageName()) || info.baseActivity
                    .getPackageName().equals(context.getPackageName())) {
                isAppRunning = true;
                Log.i("isStart", info.topActivity.getPackageName() + " info.baseActivity.getPackageName()=" + info
                        .baseActivity.getPackageName());
                break;
            }
        }
        return isAppRunning;
    }

    /**
     * 判断程序是否在后台运行
     *
     * @return
     */
    public static boolean isBackground() {
        return BaseApplication.stateCount == 0;
    }


    /**
     * 退出所有app
     */
    public static void exit() {
        for (Activity activity : activityLinkedList) {
            activity.finish();
        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
