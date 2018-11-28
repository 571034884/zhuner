package com.aibabel.baselibrary.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.aibabel.baselibrary.BuildConfig;
import com.aibabel.baselibrary.imageloader.ImageLoader;
import com.aibabel.baselibrary.imageloader.object.GlideLoader;
import com.aibabel.baselibrary.utils.CommonUtils;
import com.aibabel.baselibrary.utils.ProviderUtils;
import com.lzy.okgo.OkGo;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import org.json.JSONArray;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.util.LinkedList;


/**
 * 作者：SunSH on 2018/11/9 10:43
 * 功能：通用配置
 * 版本：1.0
 */
public abstract class BaseApplication extends Application {

    public static BaseApplication mApplication;

    /**
     * 存储程序中所创建的activity
     */
    public static int stateCount = 0;
    private static LinkedList<Activity> activityLinkedList;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        initHotfixConfig();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mApplication = this;

        initAppExitConfig();
        initLogConfig();
//        initLayoutConfig();
        initUmengConfig(setUmengKey());
        initLitPalConfig();
        initOkGoConfig();
        initImageLoaderConfig();
    }

    /**
     * 初始化热修复配置
     */
    private void initHotfixConfig() {
        String appVersion;
        try {
            appVersion = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (Exception e) {
            appVersion = "1.0.0";
        }

        SophixManager.getInstance().setContext(this)
                .setAppVersion(appVersion)
//                .setAesKey("")
                .setAesKey("0123456789123456")//与编译差异文件使用的key保持一致
                .setEnableDebug(true)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        String msg = new StringBuilder("").append("Mode:").append(mode)
                                .append(" Code:").append(code)
                                .append(" Info:").append(info)
                                .append(" HandlePatchVersion:").append(handlePatchVersion).toString();
                        Logger.e(msg);
                    }
                }).initialize();
        //调取后台接口判断是否真的有版本修复

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
     * 通过当前程序包名 获取服务器地址
     *
     * @param packageName 包名
     * @return
     */
    public String checkIps(String packageName) {
        String countryName = ProviderUtils.getInfo(mApplication, ProviderUtils.CONTENT_URI_LOCATION, "country");
        String ips = ProviderUtils.getInfo(mApplication, ProviderUtils.CONTENT_URI_LOCATION, "ips");
        String key;
        try {
            if (countryName.equals("中国")) {
                key = "中国_" + packageName + "_joner";
            } else {
                key = "default_" + packageName + "_joner";
            }
            JSONObject jsonObject = new JSONObject(ips);
            JSONArray jsonArray = new JSONArray(jsonObject.getString(key));
            return jsonArray.getJSONObject(0).get("domain").toString();
        } catch (Exception e) {
            Log.e("init: ", "ip信息错误");
            return "";
        }
    }

    /**
     * 初始化服务器地址和接口组
     */
    public abstract void setServerUrlAndInterfaceGroup(String ips);

    /**
     * 初始化okgo
     */
    public void initOkGoConfig() {
        OkGo.getInstance().init(this);
        setServerUrlAndInterfaceGroup(checkIps(getAppPackageName()));
        getAppVersionName();
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
     * 初始化布局适配  布局中使用pt做位单位
     */
    public void initLayoutConfig() {
//        AutoSizeConfig.getInstance().getUnitsManager().setSupportDP(true).setSupportSubunits(Subunits.PT);
    }

    /**
     * 初始化日志框架
     */
    public void initLogConfig() {
        FormatStrategy strategy = PrettyFormatStrategy.newBuilder().tag("AibabelLog").build();
        Logger.addLogAdapter(new AndroidLogAdapter(strategy) {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return BuildConfig.DEBUG;
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
