package com.aibabel.locationservice.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


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

////        if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
//            boolean this_networkAvailable = CommonUtils.isAvailable(context);
//            Log.e("this_networkAvailable",this_networkAvailable+"---");
//            long this_time = System.currentTimeMillis();
//            if (Constants.LAST_NET_CONNECT!=this_networkAvailable&&this_time-Constants.LAST_TIME>=5000){
//                if (this_networkAvailable){
//
//                }else {
//
//                }
//
//                Constants.LAST_NET_CONNECT=this_networkAvailable;
//                Constants.LAST_TIME=this_time;
//            }else {
//
//            }

    }

}
