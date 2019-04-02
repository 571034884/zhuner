package com.aibabel.sos.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.aibabel.sos.R;
import com.aibabel.sos.utils.CommonUtils;
import com.aibabel.sos.utils.DensityHelper;
import com.aibabel.sos.utils.SharePrefUtil;
import com.aibabel.sos.utils.SosDbUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

    /**
     * 监控其他类的内存泄漏
     */
//    private RefWatcher refWatcher;
    @Override
    public void onCreate() {
        super.onCreate();

        initLayoutConfig();
        createDatabase();
//        initOKgoConfig();
//        new DBHelper(getApplicationContext());
//        initLeakCanaryConfig();
        initUmengConfig();
        initAppExitConfig();
    }

    /**
     * 初始化布局适配  布局中使用pt做位单位
     */
    public void initLayoutConfig() {
        new DensityHelper(this, DESIGN_WIDTH).activate();
    }

//    /**
//     * 初始化okgo
//     */
//    public void initOKgoConfig() {
//
////        OkHttpClient.Builder builder = new OkHttpClient.Builder();
////        //全局的读取超时时间
////        builder.readTimeout(5000, TimeUnit.MILLISECONDS);
////        //全局的写入超时时间
////        builder.writeTimeout(5000, TimeUnit.MILLISECONDS);
////        //全局的连接超时时间
////        builder.connectTimeout(5000, TimeUnit.MILLISECONDS);
////        OkGo.getInstance().init(this).setOkHttpClient(builder.build());
//        OkGo.getInstance().init(this);
//
//    }
//
//    /**
//     * 初始化LeakCanary
//     */
//    public void initLeakCanaryConfig() {
//        refWatcher = setupLeakCanary();
//    }
//
//    private RefWatcher setupLeakCanary() {
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return RefWatcher.DISABLED;
//        }
//        return LeakCanary.install(this);
//    }
//
//    /**
//     * 在需要的位置调用rewatvher.watch方法
//     *
//     * @param context
//     * @return
//     */
//    public RefWatcher getRefWatcher(Context context) {
//        MyApplication leakApplication = (MyApplication) context.getApplicationContext();
//        return leakApplication.refWatcher;
//    }

    private void createDatabase() {

        final int BUFFER_SIZE = 200000;
        final String DB_NAME = "sos.db"; //保存的数据库文件名
        final String PACKAGE_NAME = "com.aibabel.sos";
        final String DB_PATH = "/data"
                + Environment.getDataDirectory().getAbsolutePath() + "/"
                + PACKAGE_NAME;  //在手机里存放数据库的位置
        final String dbPath = DB_PATH + "/databases/";
        final String dbfile = dbPath + DB_NAME;
        try {
            int code = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
            //获取当前系统中数据库的版本号是否和应用版本号码一致
            if (code != SharePrefUtil.getInt(getApplicationContext(), Constans.DB_VERSION_CODE_KEY, 0)) {
                File filepath = new File(dbPath);
                if (!filepath.exists()) {
                    filepath.mkdirs();
                }
                InputStream is = getApplicationContext().getResources().openRawResource(R.raw.sos); //欲导入的数据库
                FileOutputStream fos = new FileOutputStream(dbfile);
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

    public void initUmengConfig() {
        //初始化组件化基础库, 统计SDK/推送SDK/分享SDK都必须调用此初始化接口
        UMConfigure.init(this, "5b51a11c8f4a9d489d000071", CommonUtils.getSN(), UMConfigure.DEVICE_TYPE_PHONE,
                null);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }

    /**
     * 每当创建新的activity的时候，添加Activity到list中，方便统一退出
     */
    public void initAppExitConfig() {

        switch (CommonUtils.getLocalLanguage()) {
            case "zh_CN":
                SosDbUtil.COLUMN_DZ = "dz";
                SosDbUtil.COLUMN_GJ = "gj";
                SosDbUtil.COLUMN_CS = "cs";
                SosDbUtil.COLUMN_LSGDZ = "lsgdz";
                break;
            case "zh_TW":
                SosDbUtil.COLUMN_DZ = "dz_fan";
                SosDbUtil.COLUMN_GJ = "gj_fan";
                SosDbUtil.COLUMN_CS = "cs_fan";
                SosDbUtil.COLUMN_LSGDZ = "lsgdz_fan";
                break;
            case "en":
                SosDbUtil.COLUMN_DZ = "dz_ying";
                SosDbUtil.COLUMN_GJ = "gj_ying";
                SosDbUtil.COLUMN_CS = "cs_ying";
                SosDbUtil.COLUMN_LSGDZ = "lsgdz_ying";
                break;
            case "ja":
                SosDbUtil.COLUMN_DZ = "dz_ri";
                SosDbUtil.COLUMN_GJ = "gj_ri";
                SosDbUtil.COLUMN_CS = "cs_ri";
                SosDbUtil.COLUMN_LSGDZ = "lsgdz_ri";
                SosDbUtil.COLUMN_L = "l_ri";
                break;
            case "ko":
                SosDbUtil.COLUMN_DZ = "dz_han";
                SosDbUtil.COLUMN_GJ = "gj_han";
                SosDbUtil.COLUMN_CS = "cs_han";
                SosDbUtil.COLUMN_LSGDZ = "lsgdz_han";
                SosDbUtil.COLUMN_L = "l_han";
                break;
            default:
                SosDbUtil.COLUMN_DZ = "dz";
                SosDbUtil.COLUMN_GJ = "gj";
                SosDbUtil.COLUMN_CS = "cs";
                SosDbUtil.COLUMN_LSGDZ = "lsgdz";
                break;
        }

        Log.e(TAG, "initAppExitConfig: "+CommonUtils.getLocalLanguage()+"   "+ SosDbUtil.COLUMN_CS +"  "+SosDbUtil.COLUMN_L);
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
        },3000);
    }


}
