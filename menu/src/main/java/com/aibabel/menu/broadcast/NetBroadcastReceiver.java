package com.aibabel.menu.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.aibabel.menu.MainActivity;
import com.aibabel.menu.app.MyApplication;
import com.aibabel.menu.util.CommonUtils;
import com.aibabel.menu.util.L;

public class NetBroadcastReceiver extends BroadcastReceiver {
    int num=0;

    @Override
    public void onReceive(Context context, Intent intent) {
        L.e("NetBroadcastReceiver onReceive=====================");


    }


}
