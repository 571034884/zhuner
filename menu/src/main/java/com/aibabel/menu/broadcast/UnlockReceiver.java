package com.aibabel.menu.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.aibabel.menu.MainActivity;
import com.aibabel.menu.util.LogUtil;

public class UnlockReceiver extends BroadcastReceiver {

    private final static String ACTION_unlock_ok = "com.android.qrcode.unlock.ok";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        final String action = intent.getAction();
        LogUtil.e("hjs","UnlockReceiver Action:"+action);
        if (intent.getAction().equals(ACTION_unlock_ok)) {
            if(MainActivity.loopHandler!=null){
                MainActivity.loopHandler.sendEmptyMessage(200);
            }
        }
    }
}
