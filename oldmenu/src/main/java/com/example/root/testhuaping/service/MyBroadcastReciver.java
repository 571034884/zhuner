package com.example.root.testhuaping.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by fytworks on 2019/3/8.
 */

public class MyBroadcastReciver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getExtras().getString("type");
        if (type.equals("lingke")){
            Intent ins = new Intent();
            ins.setAction("com.oldmenu");
            ins.putExtra("type", "refresh");
            context.sendBroadcast(ins);
        }
    }
}
