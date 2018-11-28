package com.aibabel.baselibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.util.Locale;

public class CommonUtils {

    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;


    /**
     * 两次点击按钮之间的点击间隔不能少于1000毫秒
     */
    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

    /**
     * 判断网络是否可用
     *
     * @return true: 可用 false: 不可用
     */
    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return info != null && info.isAvailable();
    }

    /**
     * 获取系统版本号
     *
     * @return
     */
    public static String getDeviceInfo() {
        String version = Build.DISPLAY;
        String result = version.substring(0, 2);
        return result;
    }


    /**
     * 获取本机SN 设备识别码
     *
     * @return
     */
    public static String getSN() {
        String serialNum = Build.SERIAL;

        if (TextUtils.isEmpty(serialNum)) {
            return "0000000000000000";
        }
        return serialNum;
    }

    public static int getRandom() {
        int random = (int) ((Math.random() * 9 + 1) * 1000);
        return random;
    }

    /**
     * 获取系统语言
     *
     * @return
     */
    public static String getLocalLanguage() {
        String country = Locale.getDefault().getCountry();
        String language = Locale.getDefault().getLanguage();
        String lan = "";

        switch (language) {
            case "zh":
                lan = language + "_" + country;
                break;
            case "en":
            case "ja":
            case "ko":
                lan = language;
                break;
            default:
                lan = "en";
                break;
        }
        return lan;
    }

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return 手机IMEI
     */
    public static String getIMEI(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
        if (tm != null) {
            return tm.getDeviceId();
        }
        return null;
    }


    /**
     * 判断是否黑屏
     *
     * @param c
     * @return
     */
    public final static boolean isScreenLocked(Context c) {

        PowerManager powerManager = (PowerManager) c.getSystemService(Context.POWER_SERVICE);
        //true为打开，false为关闭
        boolean ifOpen = powerManager.isScreenOn();
        return ifOpen;

    }


}
