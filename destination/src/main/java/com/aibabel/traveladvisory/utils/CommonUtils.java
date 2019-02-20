package com.aibabel.traveladvisory.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

public class CommonUtils {


    /**
     * 判断网络是否可用
     *
     * @return true: 可用 false: 不可用
     */
    public static boolean isNetAvailable(Context context) {
        NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return info != null && info.isAvailable();
    }



    /**
     * 获取本机SN 设备识别码
     * @return
     */
    public static String getSN() {
        String serialNum = android.os.Build.SERIAL;
        if(TextUtils.isEmpty(serialNum)){
            return "0000000000000000";
        }
        return serialNum;
    }


    public static int getRandom(){
        int random =  (int)((Math.random()*9+1)*1000);
        return random;
    }

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

    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

}
