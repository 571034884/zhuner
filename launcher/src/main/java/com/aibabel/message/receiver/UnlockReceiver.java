package com.aibabel.message.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.aibabel.menu.activity.MainActivity;


public class UnlockReceiver extends BroadcastReceiver {

    private final static String ACTION_unlock_ok = "com.android.qrcode.unlock.ok";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        final String action = intent.getAction();
        try {
            if (intent.getAction().equals(ACTION_unlock_ok)) {
                if (MainActivity.loopHandler != null) {
                    MainActivity.loopHandler.sendEmptyMessage(200);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
