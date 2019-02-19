package com.aibabel.locationservice.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.aibabel.locationservice.alarm.ScreenListener;

public class ScreenBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = ScreenBroadcastReceiver.class.getSimpleName();
    private boolean isRegisterReceiver = false;
    private ScreenListener listener;

    @Override
    public void onReceive(Context context, Intent intent) {

            if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {
                listener.wake();
            }
            if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())){
                listener.sleep();
            }
        }

    /**
     * 设置监听
     * @param listener
     */
    public void setAlarmListener(ScreenListener listener){
        this.listener = listener;
     }

    /**
     * 注册广播
     * @param mContext
     */
    public void registerScreenBroadcastReceiver(Context mContext) {
        if (!isRegisterReceiver) {
            isRegisterReceiver = true;
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            filter.addAction(Intent.ACTION_SCREEN_ON);
            filter.addAction(Intent.ACTION_USER_PRESENT);
            mContext.registerReceiver(ScreenBroadcastReceiver.this, filter);
        }
    }

    /**
     * 注销广播
     * @param mContext
     */
    public void unRegisterScreenBroadcastReceiver(Context mContext) {
        if (isRegisterReceiver) {
            isRegisterReceiver = false;
            mContext.unregisterReceiver(ScreenBroadcastReceiver.this);
        }
    }


}
