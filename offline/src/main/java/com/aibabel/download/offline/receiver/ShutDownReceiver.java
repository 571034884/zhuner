package com.aibabel.download.offline.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import com.aibabel.download.offline.app.MyApplication;
import com.aibabel.download.offline.util.L;
import com.liulishuo.filedownloader.FileDownloader;

public class ShutDownReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving

        L.e("离线下载关机广播  处理文件状态=========================");
        FileDownloader.getImpl().pauseAll();
         MyApplication.dbHelper.shutdownUPdateDB();
    }
}
