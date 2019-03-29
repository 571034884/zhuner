package com.aibabel.scenic.utils;

import android.util.Log;

/**
 * Created by fytworks on 2019/3/22.
 */

public class Logs {

    public static final boolean DEBUG = true;//开关控制
    public static final String TAG = "LOGS_DEBUG";
    public static void i(String msg){
        if (DEBUG){
            Log.i(TAG,msg);
        }
    }
    public static void e(String msg){
        if (DEBUG){
            Log.e(TAG,msg);
        }
    }
}
