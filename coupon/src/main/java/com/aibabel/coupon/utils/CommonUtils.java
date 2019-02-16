package com.aibabel.coupon.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.aibabel.baselibrary.imageloader.ImageLoader;
import com.aibabel.coupon.bean.Constans;

import java.util.Locale;

public class CommonUtils {


    /**
     * 判断网络是否可用
     *
     * @return true: 可用 false: 不可用
     */
    public static boolean isAvailable(Context context) {
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
        Log.e("sn",serialNum);
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
    public static void setPicture1x1(String url, ImageView view) {
        ImageLoader.getInstance().load(url).placeholder(Constans.LOADING_1X1).error(Constans.LOAD_FAIL_1X1).into(view);
    }

    public static void setPicture540x280(String url, ImageView view) {
        ImageLoader.getInstance().load(url).placeholder(Constans.LOADING_540X280).error(Constans.LOAD_FAIL_540X280).into(view);
    }

    public static void setPicture(String url, int placeholder, int error, ImageView view) {
        ImageLoader.getInstance().load(url).placeholder(placeholder).error(error).into(view);
    }

}
