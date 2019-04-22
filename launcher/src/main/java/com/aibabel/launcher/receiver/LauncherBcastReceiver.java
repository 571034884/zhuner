package com.aibabel.launcher.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.aibabel.launcher.utils.Logs;

/**
 * Created by fytworks on 2019/4/20.
 */

public class LauncherBcastReceiver extends BroadcastReceiver {

    private LauncherListener listener;

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()){
            case "com.aibabel.menu.MENULOCATION"://后台定位通知
                String city = intent.getExtras().getString("city");
                Logs.e("接收到后台定位通知");
                if (!TextUtils.isEmpty(city)){
                    Logs.e("接收到后台定位通知："+city);
                    listener.launcherReceiver(city);
                }
                break;
            case "com.aibabel.launcher.MUSIC":
                listener.launcherMusic("0");
                break;
        }
    }


    public void setListener(LauncherBcastReceiver.LauncherListener listener) {
        this.listener = listener;
    }

    public interface LauncherListener {
        void launcherReceiver(String city);
        void launcherMusic(String type);
    }

}
