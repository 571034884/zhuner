package com.aibabel.menu.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


import com.aibabel.menu.util.FileUtil;
import com.aibabel.menu.util.L;
import com.liulishuo.filedownloader.FileDownloader;

public class ShutDownReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving

        L.e("menu关机 处理sp状态=========================");

        FileUtil.deleteFile(context.getFilesDir().getAbsolutePath() + "/mohuImg.jpg");

    }
}
