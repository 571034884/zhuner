package com.aibabel.travel.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.travel.BuildConfig;
import com.aibabel.travel.db.AssetsDatabaseManager;
import com.aibabel.travel.utils.CommonUtils;
import com.aibabel.travel.utils.Constant;
import com.aibabel.travel.utils.ContentProviderUtil;
import com.aibabel.travel.utils.SharePrefUtil;
import com.aibabel.travel.widgets.DensityHelper;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.common.BaiduMapSDKException;
import com.lzy.okgo.OkGo;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

import static android.content.ContentValues.TAG;


public class BaseApplication extends Application {

    public static int stateCount = 0;
    /**
     * DESIGN_WIDTH为设计图宽度
     */
    private static final int DESIGN_WIDTH = 540;
    public static Context CONTEXT;

    private static BackGroundListener listener;
    /**
     * 存储程序中所创建的activity
     */
    private static LinkedList<Activity> activityLinkedList;
    public static SQLiteDatabase sqLiteDatabase;

    private static Activity sActivity;
    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT = this.getApplicationContext();
        initLayoutConfig();
        configOKGo();
        initAppExitConfig();
        ContentProviderUtil.getHost(this);
        configUmeng();
        initDataBase();
        SDKInitializer.initialize(this);
//        registerReceiver(new NotiftLocation(),new IntentFilter("com.aibabel.broadcast.noticelocation")); // 注册广播接受者

        StatisticsManager.getInstance(this).setConfig(getPackageName(), BuildConfig.VERSION_NAME);
    }



    public void initDataBase() {
        int code = 0;
        try {
            code = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        //获取当前系统中数据库的版本号是否和应用版本号码一致
        if (code != SharePrefUtil.getInt(getApplicationContext(), Constant.DB_VERSION_CODE_KEY, 0)) {
            SharePrefUtil.saveInt(getApplicationContext(), Constant.DB_VERSION_CODE_KEY,0);
            // 初始化，只需要调用一次
            AssetsDatabaseManager.initManager(getApplicationContext());
            // 获取管理对象，因为数据库需要通过管理对象才能够获取
            AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();
            // 通过管理对象获取数据库
            sqLiteDatabase = mg.getDatabase("travel.db");
        }
    }


    /**
     * 初始化布局适配  布局中使用pt做位单位
     */
    public void initLayoutConfig() {
        new DensityHelper(this, DESIGN_WIDTH).activate();
    }

    private void configUmeng() {

        UMConfigure.init(this, "5b519f22a40fa35134000042", CommonUtils.getSN(), UMConfigure.DEVICE_TYPE_PHONE,
                null);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }


    private void configOKGo() {
//       HttpHeaders headers = new HttpHeaders();
//       headers.put("Accept-Encoding", "identity");    //所有的 header 都 不支持 中文
//       headers.put("Connection","close");
//       headers.put("charset","utf-8");
//       headers.put("Content-Type","text/plain");

//       headers.put("commonHeaderKey2", "commonHeaderValue2");
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //全局的读取超时时间
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);


//       builder.retryOnConnectionFailure(false);
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
                sActivity=activity;

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
        return BaseApplication.stateCount == 0;
    }


    /**
     * 退出所有app
     */
    public static void exit() {
        for (Activity activity : activityLinkedList) {
            activity.finish();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        },3000);
    }

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
    public static Activity getActivity(){
        return sActivity;
    }

}
