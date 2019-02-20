package com.aibabel.traveladvisory.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.WindowManager;

import com.aibabel.traveladvisory.R;
import com.aibabel.traveladvisory.utils.CommonUtils;
import com.aibabel.traveladvisory.utils.DensityHelper;
import com.aibabel.traveladvisory.utils.FileUtils;
import com.aibabel.traveladvisory.utils.OffLineUtil;
import com.aibabel.traveladvisory.utils.SharePrefUtil;
import com.aibabel.traveladvisory.utils.WeizhiUtil;
import com.lzy.okgo.OkGo;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

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
//    private static final int DESIGN_WIDTH = 480;
    private static final int DESIGN_WIDTH = 540;
    /**
     * 存储程序中所创建的activity
     */
    public static int stateCount = 0;
    private static LinkedList<Activity> activityLinkedList;

    /**
     * 监控其他类的内存泄漏
     */
    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();

        initOKgoConfig();
//        initLeakCanaryConfig();
        initAppExitConfig();
        initUmengConfig();
        initLayoutConfig();
        initOfflineConfig();
        createDatabase();
    }

    public void initOfflineConfig(){
        OffLineUtil.init(getApplicationContext());
    }

    /**
     * 初始化布局适配  布局中使用pt做位单位
     */
    public void initLayoutConfig() {
        new DensityHelper(this, DESIGN_WIDTH).activate();
    }

    /**
     * 初始化okgo
     */
    public void initOKgoConfig() {

//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        //全局的读取超时时间
//        builder.readTimeout(5000, TimeUnit.MILLISECONDS);
//        //全局的写入超时时间
//        builder.writeTimeout(5000, TimeUnit.MILLISECONDS);
//        //全局的连接超时时间
//        builder.connectTimeout(5000, TimeUnit.MILLISECONDS);
//        OkGo.getInstance().init(this).setOkHttpClient(builder.build());
        OkGo.getInstance().init(this).setRetryCount(0);

    }

    /**
     * 初始化LeakCanary
     */
//    public void initLeakCanaryConfig() {
//        refWatcher = setupLeakCanary();
//    }

//    private RefWatcher setupLeakCanary() {
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return RefWatcher.DISABLED;
//        }
//        return LeakCanary.install(this);
//    }

    /**
     * 在需要的位置调用rewatvher.watch方法
     *
     * @param context
     * @return
     */
    public RefWatcher getRefWatcher(Context context) {
        MyApplication leakApplication = (MyApplication) context.getApplicationContext();
        return leakApplication.refWatcher;
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
     * 退出所有app
     */
    public static void exit() {
        for (Activity activity : activityLinkedList) {
            activity.finish();
        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public void initUmengConfig() {
        //初始化组件化基础库, 统计SDK/推送SDK/分享SDK都必须调用此初始化接口
        UMConfigure.init(this, "5b519f8cf29d981e1400006b", CommonUtils.getSN(), UMConfigure.DEVICE_TYPE_PHONE,
                null);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }

    private void createDatabase() {
        final int BUFFER_SIZE = 200000;
        try {
            int code = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
            //获取当前系统中数据库的版本号是否和应用版本号码一致
            if (code != SharePrefUtil.getInt(getApplicationContext(), Constans.DB_VERSION_CODE_KEY, 0)) {
                File filepath = new File(Constans.DB_PATH);
                if (!filepath.exists()) {
                    filepath.mkdirs();
                }
                InputStream is = getApplicationContext().getResources().openRawResource(R.raw.mudidi); //欲导入的数据库
                FileOutputStream fos = new FileOutputStream(Constans.DB_FILE);
                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
                SharePrefUtil.saveInt(getApplicationContext(), Constans.DB_VERSION_CODE_KEY, code);
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } catch (Exception e) {
        }
    }

}
