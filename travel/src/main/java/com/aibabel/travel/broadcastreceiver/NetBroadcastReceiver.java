package com.aibabel.travel.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.aibabel.travel.utils.CommonUtils;

/**
 * Created by Wuqinghua on 2018/6/23 0023.
 */
public class NetBroadcastReceiver extends BroadcastReceiver {

    private NetListener listener;


    @Override
    public void onReceive(final Context context, Intent intent) {
        if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {

            listener.netChanged();
//            try {
//                if (CommonUtils.isAvailable()) {
//                    listener.connect();
//                } else {
//                    listener.disConnect();
//                }
//            } catch (Exception e) {
//
//            }

        }

    }


    public void setListener(NetListener listener) {
        this.listener = listener;
    }

    public interface NetListener {
//       void connect();
//       void disConnect();
       void netChanged();
    }


}
