package com.aibabel.translate.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.aibabel.translate.utils.L;
import com.aibabel.translate.utils.SharePrefUtil;

public class ShutDownReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        L.e("translate App ShutDownReceiver ===============");
        SharePrefUtil.saveString(context, "isAgree", "false");
    }
}
