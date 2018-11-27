package com.aibabel.weather.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.aibabel.weather.utils.CommonUtils;
import com.aibabel.weather.utils.DensityHelper;
import com.lzy.okgo.OkGo;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import java.io.File;
import java.util.LinkedList;



/**
 * 作者：SunSH on 2018/5/12 18:28
 * 功能：
 * 版本：1.0
 */
public class MyApplication extends Application {
    public static String TAG = "MyApplication";

    /**
     * DESIGN_WIDTH为设计图宽度
     */
    private static final int DESIGN_WIDTH = 540;
    /**
     * 存储程序中所创建的activity
     */
    public static int stateCount = 0;
    private static LinkedList<Activity> activityLinkedList;

    @Override
    public void onCreate() {
        super.onCreate();

        initLayoutConfig();
        initAppExitConfig();
        initOKgoConfig();
        initUmengConfig();
    }

    /**
     * 初始化布局适配  布局中使用pt做位单位
     */
    public void initLayoutConfig() {
        new DensityHelper(this, DESIGN_WIDTH).activate();
//        AutoSizeConfig.getInstance().getUnitsManager().setSupportSubunits(Subunits.PT);
    }

    /**
     * 初始化okgo
     */
    public void initOKgoConfig() {
        OkGo.getInstance().init(this);
    }

    public void initUmengConfig() {
        //初始化组件化基础库, 统计SDK/推送SDK/分享SDK都必须调用此初始化接口
        UMConfigure.init(this, "5b51a0a5a40fa3514c000078", CommonUtils.getSN(), UMConfigure.DEVICE_TYPE_PHONE,
                null);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }

    public void initSharedpreferenceConfig(boolean delete) {
        if (delete) {
            //清除缓存的方法
            deleteFilesByDirectory(new File("/data/data/" + getApplicationContext().getPackageName() + "/shared_prefs"));
        }
    }

    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * * @param directory
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }

    /**
     * 每当创建新的activity的时候，添加Activity到list中，方便统一退出
     */
    public void initAppExitConfig() {

        switch (CommonUtils.getLocalLanguage()) {
            case "zh_CN":
            case "zh_TW":
                Constant.SYSTEM_LANGUAGE = "Chj";
                break;
            case "en":
                Constant.SYSTEM_LANGUAGE = "En";
                break;
            case "ja":
                Constant.SYSTEM_LANGUAGE = "Jpa";
                break;
            case "ko":
                Constant.SYSTEM_LANGUAGE = "Kor";
                break;
            default:
                Constant.SYSTEM_LANGUAGE = "Chj";
                break;
        }

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
     * 退出所有app
     */
    public static void exit() {
        for (Activity activity : activityLinkedList) {
            activity.finish();
        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
