package com.aibabel.download.offline.app;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.os.Handler;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.download.offline.BuildConfig;
import com.aibabel.download.offline.db.OfflineDBHelper;
import com.aibabel.download.offline.log.CrashHandler;
import com.aibabel.download.offline.service.DownloadService;
import com.aibabel.download.offline.ui.DensityHelper;
import com.aibabel.download.offline.util.CommonUtils;
import com.aibabel.download.offline.util.FileUtil;
import com.aibabel.download.offline.util.L;
import com.aibabel.download.offline.util.URL_API;
import com.liulishuo.filedownloader.FileDownloader;


import org.xutils.x;

import java.io.File;
import java.util.List;

public class MyApplication extends Application {

    public static Handler FileHandler;
    public static Handler uiHandler;
    public static Handler offlineNoticeUIHandler;
    private final int DESIGN_WIDTH=540;
    public static int isDialog_install=0;


    public static OfflineDBHelper dbHelper;

    public static Context mContext;


    //文件执行 是否继续
    public static boolean isFile =true;




    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;
        StatisticsManager.getInstance(this).setConfig(getPackageName(), BuildConfig.VERSION_NAME);

        x.Ext.init(this);
        x.Ext.setDebug(true);
        dbHelper=new OfflineDBHelper(this);
        FileDownloader.setup(this);


        //适配类初始化
        initLayoutConfig();

//        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler(this.getApplicationContext()));

        if (FileUtil.isFolderExists("/sdcard/download_offline/")) {

        }


    }


    @Override
    public void onTerminate() {
        //注销下载服务
        L.e("LocalResourceActivity   onDestroy=================================");


        super.onTerminate();
    }


    /**
     * 初始化布局适配  布局中使用pt做位单位
     */
    public void initLayoutConfig() {
        new DensityHelper(this, DESIGN_WIDTH).activate();
    }


    @Override
    public void onTrimMemory(int level) {
        URL_API.getHost();
        CommonUtils.rexiufu(mContext);

        super.onTrimMemory(level);
    }
}
