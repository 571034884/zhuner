package com.aibabel.translate.utils;

import android.os.Handler;

import java.util.TimerTask;


public class CheckTimerTask extends TimerTask {
    private Handler handler;


    public CheckTimerTask(Handler handler) {
        this.handler = handler;

    }

    @Override
    public void run() {
        if (null != handler)
            handler.sendEmptyMessage(Constant.TIMEOUT_READ);
    }


}
