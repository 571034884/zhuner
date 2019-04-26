package com.aibabel.menu.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Message;

import com.aibabel.menu.activity.MainActivity;
import com.aibabel.menu.utils.LogUtil;


public class MesManageReceiver extends BroadcastReceiver {

    private final static String ACTION_unlock_ok = "com.android.ok";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        final String action = intent.getAction();
        LogUtil.e("hjs", "Action:" + action);
        try {
            if (intent.getAction().equals(ACTION_unlock_ok)) {
                Message mes= new Message();
                mes.what = 400;
                mes.setData(intent.getExtras());

                if (MainActivity.loopHandler != null) {
                    MainActivity.loopHandler.sendMessage(mes);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
