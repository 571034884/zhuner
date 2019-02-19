package com.aibabel.download.offline.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.aibabel.download.offline.util.L;


public class NoticeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        L.e("------------------------NoticeReceiver onReceive");

    }
}
