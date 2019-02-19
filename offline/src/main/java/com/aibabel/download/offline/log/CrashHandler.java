package com.aibabel.download.offline.log;

import android.content.Context;

import com.aibabel.download.offline.app.MyApplication;
import com.aibabel.download.offline.util.L;

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private Context mContext;
    public CrashHandler(Context context) {
        mContext=context;

    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {

        L.e("崩溃处理========================"+throwable);
        MyApplication.dbHelper.shutdownUPdateDB();
        android.os.Process.killProcess(android.os.Process.myPid());

    }
}
