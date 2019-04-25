package com.aibabel.menu.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.aibabel.baselibrary.mode.StatisticsManager;

public class StatisticsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.aibabel.statistics")){
            StatisticsManager statisticsManager=StatisticsManager.getInstance();
            String appName=intent.getStringExtra("an");
            String appVersion=intent.getStringExtra("av");
            String appPageInfo=intent.getStringExtra("pageInfo");


            statisticsManager.addPath(appName,appVersion,appPageInfo);

        }

    }
}
