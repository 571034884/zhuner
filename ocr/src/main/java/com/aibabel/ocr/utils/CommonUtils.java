package com.aibabel.ocr.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.aibabel.ocr.R;
import com.aibabel.ocr.app.BaseApplication;

import java.util.Locale;

public class CommonUtils {


    /**
     * 获取系统语言
     * @param context
     * @return
     */
    public static String getLocal(Context context){

        String country = Locale.getDefault().getCountry();
        String language = Locale.getDefault().getLanguage();
        String sl = "";

        switch (language){
            case "zh":
                sl = language+"_"+country;
                break;
            case "en":
            case "ja":
            case "ko":
                sl = language;
                break;
            default:
                sl="en";
                break;
        }
        return sl;
    }

    /**
     * 屏幕是否亮着
      * @param context
     * @return
     */
    public static boolean isWake(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        boolean isWake = pm.isScreenOn();
        return isWake;
    }


    public static View getVersion (Context context){
        View view;
        String display = Build.DISPLAY;
        String version = display.substring(0, 2);
        Log.e("version","==="+version);
        switch (version){
            case "PM":
                view = View.inflate(context, R.layout.popupwindow_go, null);
                break;
            case "PL":
                view = View.inflate(context, R.layout.popupwindow, null);
                break;
            case "PH":
                view = View.inflate(context, R.layout.popupwindow_go, null);
                break;
            default:
                view = View.inflate(context, R.layout.popupwindow_go, null);
                break;
        }

        return view;
        
    }


}
