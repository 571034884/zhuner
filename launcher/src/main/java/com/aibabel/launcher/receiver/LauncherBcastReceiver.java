package com.aibabel.launcher.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.aibabel.baselibrary.utils.FastJsonUtil;
import com.aibabel.launcher.bean.MusicBean;
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
                int type = intent.getExtras().getInt("type");
                Logs.e("景区导览：type:"+type);
                String urlPic = intent.getExtras().getString("urlPic");
                String poiName = intent.getExtras().getString("poiName");
                String name = intent.getExtras().getString("name");
                listener.launcherMusic(poiName,urlPic,name,type);
                break;
        }
    }


    public void setListener(LauncherBcastReceiver.LauncherListener listener) {
        this.listener = listener;
    }

    public interface LauncherListener {
        void launcherReceiver(String city);
        void launcherMusic(String poiName,String urlPic,String name,int type);
    }

}
