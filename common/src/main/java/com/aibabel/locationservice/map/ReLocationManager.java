package com.aibabel.locationservice.map;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.aibabel.locationservice.utils.CommonUtils;
import com.baidu.location.LocationClient;

public class ReLocationManager {

    /**
     * 默认重新定位时间(后面会以指数次增加)
     */
    private static final long DEFAULT = 15 * 1000;
    /**
     * 最大定位次数,不包括断开异常
     */
    private static final int MAX_CONNECTION_FAILED_TIMES = 12;
    /**
     * 延时定位时间
     */
    private long mLocationTimeDelay = DEFAULT;
    /**
     * 定位失败次数,不包括断开异常
     */
    private int mLocationFailedTimes = 0;


    private LocationClient mLocationClient;


    public ReLocationManager(LocationClient mLocationClient) {
        this.mLocationClient = mLocationClient;
    }

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (null != mLocationClient) {
                mLocationClient.restart();
            }
        }
    };


    public void onLocationSuccess() {
        reset();
    }


    public void onLocationFailed() {

        mLocationFailedTimes++;
        if (mLocationFailedTimes > MAX_CONNECTION_FAILED_TIMES) {
            reset();
            //超过最大次数后
            mLocationClient.stop();
        } else {
            Log.e("relocation", "it is " + mLocationFailedTimes + " times relocation failed！");
            reLocationDelay();
        }

    }

    /**
     * 停止重连，将默认时间重置
     */
    private void reset() {
        mHandler.removeCallbacksAndMessages(null);
        mLocationTimeDelay = DEFAULT;
        mLocationFailedTimes = 0;
    }

    private void reLocationDelay() {
        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendEmptyMessageDelayed(0, mLocationTimeDelay);

        mLocationTimeDelay = mLocationTimeDelay * 2;//15+30+60+120 = 234 4次
        if (mLocationTimeDelay >= DEFAULT * 10) {//DEFAULT * 10 = 150
            mLocationTimeDelay = DEFAULT;
        }
    }


}
