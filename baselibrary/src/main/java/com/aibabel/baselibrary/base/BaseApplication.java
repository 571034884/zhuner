package com.aibabel.baselibrary.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.baselibrary.BuildConfig;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.baselibrary.imageloader.ImageLoader;
import com.aibabel.baselibrary.imageloader.object.GlideLoader;
import com.aibabel.baselibrary.impl.IDataManager;
import com.aibabel.baselibrary.impl.IServerManager;
import com.aibabel.baselibrary.mode.DataManager;
import com.aibabel.baselibrary.mode.ServerManager;
import com.aibabel.baselibrary.sphelper.SPHelper;
import com.aibabel.baselibrary.utils.CommonUtils;
import com.aibabel.baselibrary.utils.ProviderUtils;
import com.lzy.okgo.OkGo;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.xuexiang.xipc.XIPC;

import org.json.JSONArray;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 作者：SunSH on 2018/11/9 10:43
 * 功能：通用配置
 * 版本：1.0
 */
public abstract class BaseApplication extends Application {

    public static BaseApplication mApplication;
    public static int iReadTime = 30000;
    public static int iWriteTime = 30000;
    private static int iConnectTime = 10000;

    public static boolean allPhysicalButtonsExitEnable = false;
    public static boolean statisticsEnable = false;

    /**
     * 设置物理按键杀死程序，回到菜单
     *
     * @return
     */
    public static boolean isAllPhysicalButtonsExitEnable() {
        return allPhysicalButtonsExitEnable;
    }

    public static void setAllPhysicalButtonsExitEnable(boolean allPhysicalButtonsExitEnable) {
        BaseApplication.allPhysicalButtonsExitEnable = allPhysicalButtonsExitEnable;
    }

    /**
     * 全局设置是否开启统计功能
     *
     * @return
     */
    public static boolean isStatisticsEnable() {
        return statisticsEnable;
    }

    public static void setStatisticsEnable(boolean statisticsEnable) {
        BaseApplication.statisticsEnable = statisticsEnable;
    }

    /**
     * 存储程序中所创建的activity
     */
    public static int stateCount = 0;
    private static LinkedList<Activity> activityLinkedList;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mApplication = this;

        initAppExitConfig();
        initLogConfig();
        initUmengConfig(setUmengKey());
        initLitPalConfig();
        initOkGoConfig();
        initImageLoaderConfig();
        initStatisticsConfig();
        initXipc();
        SPHelper.init(this);
    }

    /**
     * 跨进程初始化
     */
    private void initXipc() {
        XIPC.init(this);
//        XIPC.debug(BuildConfig.DEBUG);
        XIPC.register(DataManager.class);
        XIPC.register(ServerManager.class);
        XIPC.register(IDataManager.class);
        XIPC.register(IServerManager.class);
    }

    public void initStatisticsConfig() {
        StatisticsManager.getInstance(this).setConfig(getAppPackageName(),getAppVersionName());
    }

    /**
     * 初始化图片加载框架
     */
    public void initImageLoaderConfig() {
        ImageLoader.getInstance().setGlobalImageLoader(new GlideLoader());
    }

    /**
     * 获取当前程序版本名
     *
     * @return
     */
    public abstract String getAppVersionName();

    /**
     * 获取当前程序包名
     *
     * @return
     */
    public abstract String getAppPackageName();


    /**
     * 初始化服务器地址和接口组
     */
    public abstract void setServerUrlAndInterfaceGroup();

    public static void setOkGoTimeConfig(int readTime, int writeTime, int connectTime) {
        iReadTime = readTime;
        iWriteTime = writeTime;
        iConnectTime = connectTime;
    }

    /**
     * 初始化okgo
     */
    public void initOkGoConfig() {
        setServerUrlAndInterfaceGroup();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //全局的读取超时时间
        builder.readTimeout(iReadTime, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(iWriteTime, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(iConnectTime, TimeUnit.MILLISECONDS);
        OkGo.getInstance().init(this).setOkHttpClient(builder.build()).setRetryCount(0);
        OkGoUtil.appVersionName = getAppVersionName();
    }

    /**
     * 初始化数据库
     */
    public void initLitPalConfig() {
        LitePal.initialize(this);
    }

    /**
     * 设置友盟统计的key
     *
     * @return
     */
    public abstract String setUmengKey();

    /**
     * 友盟统计  初始化组件化基础库, 统计SDK/推送SDK/分享SDK都必须调用此初始化接口
     */
    public void initUmengConfig(String key) {
        UMConfigure.init(this, key, CommonUtils.getSN(), UMConfigure.DEVICE_TYPE_PHONE, null);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }

    /**
     * 初始化日志框架
     */
    public void initLogConfig() {
        FormatStrategy strategy = PrettyFormatStrategy.newBuilder().tag("AibabelLog").build();
        Logger.addLogAdapter(new AndroidLogAdapter(strategy) {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return true;
            }
        });
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
                Logger.d(activity.getLocalClassName());
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
