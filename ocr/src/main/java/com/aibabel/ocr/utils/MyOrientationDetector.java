package com.aibabel.ocr.utils;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.OrientationEventListener;

public class MyOrientationDetector extends OrientationEventListener {

    private int orientation = 0;

    public MyOrientationDetector(Context context, int sensorDelayNormal) {
        super(context);
    }

    public MyOrientationDetector(Context context, int rate, int orientation) {
        super(context, rate);
        this.orientation = orientation;
    }

    @Override
    public void onOrientationChanged(int orientation) {


        if (orientation == OrientationEventListener.ORIENTATION_UNKNOWN) {
            orientation = 0;
            return;  //手机平放时，检测不到有效的角度
        }

        //只检测是否有四个角度的改变
        if (orientation > 350 || orientation < 10) { //0度
            orientation = 0;
        } else if (orientation > 80 && orientation < 100) { //90度
            orientation = 90;
        } else if (orientation > 170 && orientation < 190) { //180度
            orientation = 180;
        } else if (orientation > 260 && orientation < 280) { //270度
            orientation = 270;
        } else {
            orientation = 0;
            return;
        }

    }

    /**
     * 获取手机角度角度
     * @return
     */
    public int getOrientation() {
        return orientation;
    }
}
