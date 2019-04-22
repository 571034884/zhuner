package com.aibabel.baselibrary.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.PowerManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.aibabel.baselibrary.base.BaseApplication;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * cbb testbase--》master
 */
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
        String sn="0000000000000000";
        try {
            Class clz = Class.forName("android.os.SystemProperties");
            Method method = clz.getMethod("get", String.class,String.class);
            sn = (String) method.invoke(clz,"gsm.serial", "0000000000000000");
            sn.trim();
            if (sn.indexOf(" ") != -1) {
                sn = sn.substring(0, sn.indexOf(" "));
            }
            Log.e("CommonUtils","sn="+sn);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("CommonUtils",e.getMessage());
        }

        return sn;
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
    @SuppressLint("MissingPermission")
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

    public static boolean getRequestHotRepaireEnable(int repairCount) {
        if (CommonUtils.isSameDate(new Date(), new Date(SharePrefUtil.getLong(BaseApplication.mApplication, "requestDay", 0)))) {
            return SharePrefUtil.getInt(BaseApplication.mApplication, "requestCount", 10000) > repairCount - 1 ? false : true;
        } else {
            SharePrefUtil.saveInt(BaseApplication.mApplication, "requestCount", 0);
            return true;
        }
    }

    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
        boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }


    /**
     * 获取当前时区
     *
     * @return 1国内服务器，0国外服务器
     */
    public static int getTimerType() {
        try {
            String timerID = TimeZone.getDefault().getID();
            if (timerID.equals("Asia/Shanghai")) {
                Log.e("SERVICE_FUWU", "时区:" + 1);
                return 1;
            } else {
                Log.e("SERVICE_FUWU", "时区:" + 0);
                return 0;
            }
        } catch (Exception e) {
            Log.e("SERVICE_FUWU", "获取时区报错");
        }
        return 0;
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
}
