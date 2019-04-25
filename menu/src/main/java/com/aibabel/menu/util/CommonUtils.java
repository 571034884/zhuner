package com.aibabel.menu.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import java.lang.reflect.Method;
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



//    /**
//     * 获取本机SN 设备识别码
//     * @return
//     */
//    public static String getSN() {
//        String serialNum = android.os.Build.SERIAL;
//        if(TextUtils.isEmpty(serialNum)){
//            return "0000000000000000";
//        }
//        return serialNum;
//    }
    /**
     * 获取本机SN 设备识别码
     *
     * @return
     */

    public static String getSN() {
        String sn="0000000000000000";
        try {
            Class clz = Class.forName("android.os.SystemProperties");
            Method method = clz.getMethod("get", String.class,String.class);
            sn = (String) method.invoke(clz,"gsm.serial", "0000000000000000");
            sn.trim();
            if (sn.indexOf(" ") != -1) {
                sn = sn.substring(0, sn.indexOf(" "));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("CommonUtils",e.getMessage());
        }

        return sn;
    }


    /*
     * 获取系统版本号 去除 “-” */

    public static String getDevice() {
        String result = "";
        String display = Build.DISPLAY;
        result = display.replace("-", "");
        Log.e("result", result);
        return result;
    }


    /**
     * 获取系统版本号
     *
     * @return
     */
    public static String getDeviceInfo() {
        String result="PL";
        String version = Build.DISPLAY;
        result = version.substring(0, 2);

        return result;
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
    /**
     * 获取版本号名称
     *
     * @param context 上下文
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

    //判断当前应用是否是debug状态
    public static boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }


}
