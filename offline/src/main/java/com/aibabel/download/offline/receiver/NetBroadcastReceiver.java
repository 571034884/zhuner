package com.aibabel.download.offline.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Message;

import com.aibabel.download.offline.app.MyApplication;
import com.aibabel.download.offline.util.CommonUtils;

public class NetBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        try {
            if (CommonUtils.isAvailable(context)) {

                if (MyApplication.uiHandler!=null) {
                    Message message=MyApplication.uiHandler.obtainMessage();
                    message.what=300;

                    MyApplication.uiHandler.sendMessage(message);
                }

            }
        } catch (Exception e) {

        }
    }
}
