package com.aibabel.menu.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.aibabel.menu.util.L;
import com.aibabel.menu.util.LogUtil;

public class NetBroadcastReceiver extends BroadcastReceiver {
    int num=0;

    @Override
    public void onReceive(Context context, Intent intent) {
        L.e("NetBroadcastReceiver onReceive=====================");
        LogUtil.e("NetBroadcastReceiver onReceive");
        boolean success = false;
        //获得网络连接服务
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        //获取wifi连接状态
        NetworkInfo.State state = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        //判断是否正在使用wifi网络
        if (state == NetworkInfo.State.CONNECTED) {
            success = true;
        }
        //获取GPRS状态
        state = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        //判断是否在使用GPRS网络
        if (state == NetworkInfo.State.CONNECTED) {
            success = true;
        }
        //如果没有连接成功
        if(!success){
            LogUtil.e("当前网络无连接");
        }

    }


}
