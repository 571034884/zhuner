package com.aibabel.launcher.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.aibabel.launcher.utils.Logs;
import com.tencent.mmkv.MMKV;

import static android.content.Intent.ACTION_SHUTDOWN;

public class ShutdownBcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_SHUTDOWN)) {
            Logs.e("关机监听");
            MMKV.defaultMMKV().encode("isDialogShow",false);
        }
    }
}
