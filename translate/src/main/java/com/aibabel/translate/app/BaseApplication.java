package com.aibabel.translate.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.translate.BuildConfig;
import com.aibabel.translate.MainActivity;
import com.aibabel.translate.offline.ChangeOffline;
import com.aibabel.translate.socket.TTSUtil;
import com.aibabel.translate.utils.CommonUtils;
import com.aibabel.translate.utils.Constant;
import com.aibabel.translate.utils.DensityHelper;

import com.aibabel.translate.utils.L;
import com.aibabel.translate.utils.SharePrefUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.taobao.sophix.SophixManager;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.xuhao.android.libsocket.sdk.OkSocket;

import org.json.JSONObject;
import org.litepal.LitePalApplication;
import org.litepal.tablemanager.Connector;

import java.util.LinkedList;
import java.util.Locale;


import static android.content.ContentValues.TAG;

/**
 * Created by SunSH on 2018/3/14.
 */

public class BaseApplication extends LitePalApplication {



    public static  boolean isTran=true;
    public static String isIpsil="Ipsil";

    /**
     * DESIGN_WIDTH为设计图宽度
     */
    private static final int DESIGN_WIDTH = 540;

    /**
     * 存储程序中所创建的activity
     */
    private static LinkedList<Activity> activityLinkedList;
    public static Context context;

    public static Context getContext() {

        return context;
    }

    /**
     * 创建全局变量
     * 注意在AndroidManifest.xml中的Application节点添加android:name=".MyApplication"属性
     */
    private WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();

    public WindowManager.LayoutParams getMywmParams() {
        return wmParams;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        //初始化组件化基础库, 统计SDK/推送SDK/分享SDK都必须调用此初始化接口
        if (CommonUtils.isApkInDebug(this)) {
            //debug  版本
            UMConfigure.init(this, "5bcec692f1f5566bec0000c5", CommonUtils.getSN(), UMConfigure.DEVICE_TYPE_PHONE,
                    null);
            MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        } else {
            //relase 版本
            UMConfigure.init(this, "5b446a5ca40fa31c6300003e", CommonUtils.getSN(), UMConfigure.DEVICE_TYPE_PHONE,
                    null);
            MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        }

        initLayoutConfig();
        initLogConfig();
        initOkSocketConfig();
        initAppExitConfig();
//        initLanguageModle();
        initCountryLanguage();
        try {
            ChangeOffline.getInstance().test();
            ChangeOffline.getInstance().initSpeechModel("ch_ch",0);

        } catch (ClassNotFoundException e) {
            L.e("ClassNotFoundException>>>>>>>>>>"+e.getMessage());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            L.e("IllegalAccessException>>>>>>>>>>"+e.getMessage());
            e.printStackTrace();
        } catch (InstantiationException e) {
            L.e("InstantiationException>>>>>>>>>>"+e.getMessage());
            e.printStackTrace();
        }

        TTSUtil.getInstance().initTTs(BaseApplication.getContext());

        initSQLite();
        //热修复
        rexiufu();
        //
        StatisticsManager.getInstance(this).setConfig(getPackageName(),BuildConfig.VERSION_NAME);

    }

    private void initCountryLanguage() {
        String country = Locale.getDefault().getCountry();
        String language = getResources().getConfiguration().locale.getLanguage();
        SharePrefUtil.saveString(context,"language",language);
        if (language.equals("zh")){
            SharePrefUtil.saveString(context,"language",country);
        }
    }

    private void initSQLite() {
        //创建表,如果存在就不会在创建了
        SQLiteDatabase db = Connector.getDatabase();
    }

    /**
     * 初始化布局适配  布局中使用pt做位单位
     */
    public void initLayoutConfig() {
        new DensityHelper(this, DESIGN_WIDTH).activate();
    }

    /**
     * Log框架初始化
     */
    public void initLogConfig() {
//        Logger.addLogAdapter(new AndroidLogAdapter(){
//            @Override
//            public boolean isLoggable(int priority, String tag) {
//                return BuildConfig.LOGGER_ABLE;
//            }
//        });
    }


    /**
     * Socket初始化
     */
    public void initOkSocketConfig() {
        OkSocket.initialize(this, true);
    }

    /**
     * 退出APP配置
     */
    public void initAppExitConfig() {

        activityLinkedList = new LinkedList<>();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Log.d(TAG, "onActivityCreated: " + activity.getLocalClassName());
                Log.d(TAG, "Pid: " + android.os.Process.myPid());
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
                Log.d(TAG, "onActivityStopped: " + activity.getLocalClassName());
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.d(TAG, "onActivityDestroyed: " + activity.getLocalClassName());

                activityLinkedList.remove(activity);
                if (activityLinkedList.size() == 0) {
                    // release application's RAM

                    Intent intent = new Intent(Intent.ACTION_MAIN);

                    intent.addCategory(Intent.CATEGORY_HOME);

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(intent);

                    System.exit(0);
                }
            }
        });
    }

    /**
     * 退出所有app
     */
    public static void exitAppList() {
        for (Activity activity : activityLinkedList) {
            activity.finish();
        }
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onLowMemory() {
        // 低内存的时候执行
        Log.d(TAG, "onLowMemory");
        super.onLowMemory();
    }

    //程序真正终止的时候
    @Override
    public void onTerminate() {

      //释放所有的模型



        super.onTerminate();
    }


    public void rexiufu() {
        String latitude = CommonUtils.getLatAndLng(this).get("lat");
        String longitude = CommonUtils.getLatAndLng(this).get("lng");
        String url = Constant.HOST_HOTFIX + "/v1/jonersystem/GetAppNew?sn=" + CommonUtils.getSN() + "&no=" + CommonUtils.getRandom() + "&sl=" + CommonUtils.getLocal()+ "&av=" + BuildConfig.VERSION_NAME + "&app=" + getPackageName() + "&sv=" + Build.DISPLAY + "&lat=" + latitude + "&lng=" + longitude;

        OkGo.<String>get(url)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(TAG, response.body().toString());
                        if (!TextUtils.isEmpty(response.body().toString())) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().toString());
                                boolean isNew = (Boolean) ((JSONObject) jsonObject.get("data")).get("isNew");
                                if (isNew) {
                                    SophixManager.getInstance().queryAndLoadNewPatch();
                                    Log.e("success:", "=================" + isNew + "=================");
                                } else {
                                    Log.e("failed:", "=================" + isNew + "=================");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("Exception:", "==========" + e.getMessage() + "===========");
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                    }
                });
    }


}
