package com.aibabel.readme.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.baselibrary.impl.IDataManager;
import com.aibabel.baselibrary.impl.IServerManager;
import com.aibabel.baselibrary.impl.IStatistics;
import com.aibabel.baselibrary.mode.DataManager;
import com.aibabel.baselibrary.mode.ServerManager;
import com.aibabel.readme.BuildConfig;
import com.aibabel.readme.utils.CommonUtils;
import com.aibabel.readme.utils.DensityHelper;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.xuexiang.xipc.XIPC;

import java.util.LinkedList;
/**
 * Created by Wuqinghua on 2018/6/28 0028.
 */
public class BaseApplication extends Application {
    /**
     * 存储程序中所创建的activity
     */
    private static LinkedList<Activity> activityLinkedList;
    public static int stateCount = 0;
    private static final int DESIGN_WIDTH = 540;
    @Override
    public void onCreate() {
        super.onCreate();

        initAppExitConfig();
        initLayoutConfig();

//        if (!CommonUtils.isApkInDebug(getApplicationContext())){
//
//        }else {
//
//        }
        //初始化组件化基础库, 统计SDK/推送SDK/分享SDK都必须调用此初始化接口
        UMConfigure.init(this, "5bc6a287b465f5e42a00036e", CommonUtils.getSN(), UMConfigure.DEVICE_TYPE_PHONE,
                null);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        StatisticsManager.getInstance(this).setConfig(getPackageName(), BuildConfig.VERSION_NAME);
        initXipc();

    }


    /**
     * 跨进程初始化
     */
    private void initXipc() {
        XIPC.init(this);
        XIPC.debug(BuildConfig.DEBUG);
//        XIPC.register(DataManager.class);
//        XIPC.register(ServerManager.class);
        XIPC.register(IDataManager.class);
        XIPC.register(IServerManager.class);
//        XIPC.register(com.aibabel.baselibrary.mode.StatisticsManager.class);
        XIPC.register(IStatistics.class);

    }
    /**
     * 初始化布局适配  布局中使用pt做位单位
     */
    public void initLayoutConfig() {
        new DensityHelper(this, DESIGN_WIDTH).activate();
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
//                Log.d(TAG, "onActivityStarted: " + activity.getLocalClassName());
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
     * 退出所有app
     */
    public static void exit() {
      /*  for (Activity activity : activityLinkedList) {
            activity.finish();
        }*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        },3000);
    }
}
