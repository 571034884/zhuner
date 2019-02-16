package com.aibabel.map.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.aibabel.baselibrary.utils.CommonUtils;


/**
 *==========================================================================================
 * @Author： 张文颖
 *
 * @Date：2018/11/9
 *
 * @Desc：网络变化监听广播
 *==========================================================================================
 */
public class NetBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "NetBroadcastReceiver";


    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            boolean this_networkAvailable = CommonUtils.isNetworkAvailable(context);
            Log.e("this_networkAvailable",this_networkAvailable+"---");
            long this_time = System.currentTimeMillis();

            }else {

            }

    }

}
