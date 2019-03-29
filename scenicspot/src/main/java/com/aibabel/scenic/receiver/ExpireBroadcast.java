package com.aibabel.scenic.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/**
 * 到期广播监听
 */
public class ExpireBroadcast extends BroadcastReceiver {

    private stopMp3 stopMp3 ;
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (TextUtils.equals("com.android.zhuner",action)){
            if(null!= stopMp3)
            stopMp3.stopMp3();
        }
    }
    public void setStopMp3(ExpireBroadcast.stopMp3 stopMp3) {
        this.stopMp3 = stopMp3;
    }

    public interface stopMp3{
        void stopMp3();
    }

}
