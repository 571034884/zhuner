package com.aibabel.tucao.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;


import com.aibabel.tucao.utils.CommonUtils;
import com.aibabel.tucao.utils.DensityHelper;
import com.lzy.okgo.OkGo;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

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

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //全局的读取超时时间
        builder.readTimeout(15000, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(15000, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(15000, TimeUnit.MILLISECONDS);

        OkGo.getInstance().init(this).setOkHttpClient(builder.build()); //必须调用初始化


        initAppExitConfig();
        initUmengConfig();
        initLayoutConfig();


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
//        for (Activity activity : activityLinkedList) {
//            activity.finish();
//        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        },3000);

    }


    public void initUmengConfig() {
        //初始化组件化基础库, 统计SDK/推送SDK/分享SDK都必须调用此初始化接口
        UMConfigure.init(this, "5be3ae8af1f556d869000135", CommonUtils.getSN(), UMConfigure.DEVICE_TYPE_PHONE,
                null);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }
}
