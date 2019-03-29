package com.aibabel.scenic.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.aibabel.scenic.utils.Constants;

/**
 * 到期广播监听
 */
public class ExpireBroadcast extends BroadcastReceiver {

    private static StopMp3 stopMp3_spots;
    private static StopMp3 stopMp3_history;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (TextUtils.equals("com.android.zhuner", action) ||TextUtils.equals("com.aibabel.scenic.stop", action)) {
            if (null != stopMp3_spots)
                stopMp3_spots.stopMp3();
            if (null != stopMp3_history) {
                stopMp3_history.stopMp3();
            }
        }
    }

    public static void setStopMp3(ExpireBroadcast.StopMp3 listener, String key) {
        if (TextUtils.equals(key, Constants.key_spot)) {
            stopMp3_spots = listener;
        } else {
            stopMp3_history = listener;
        }

    }

    public interface StopMp3 {
        void stopMp3();
    }

}
