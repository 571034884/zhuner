package com.aibabel.download.offline.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.aibabel.download.offline.app.MyApplication;
import com.aibabel.download.offline.util.FileUtil;
import com.aibabel.download.offline.util.L;
import com.aibabel.download.offline.util.T;



public class DownloadService extends Service  {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


}
