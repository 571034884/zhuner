package com.aibabel.message.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.aibabel.baselibrary.utils.CommonUtils;


/**
 * ==========================================================================================
 *
 * @Author： 张文颖
 * @Date：2018/11/9
 * @Desc：网络变化监听广播
 * @==========================================================================================
 */
public class HXNetBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "HXNetBroadcastReceiver";

    private NetListener listener;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            boolean this_networkAvailable = CommonUtils.isNetworkAvailable(context);
            if (this_networkAvailable) {
                Log.e(TAG, this_networkAvailable + "---");
                listener.netState(true);
            } else {
                listener.netState(false);
            }
        }

    }


    public void setListener(NetListener listener) {
        this.listener = listener;
    }

    public interface NetListener {
        void netState(boolean isAvailable);
    }


}
