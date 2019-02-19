package com.aibabel.translate.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.aibabel.translate.R;
import com.aibabel.translate.app.BaseApplication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CommonUtils {

    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;

    /*获取当前定位城市和该地区的host地址*/
    private static final Uri CITY_URI = Uri.parse("content://com.aibabel.locationservice.provider.AibabelProvider/aibabel_location");

    /**
     * 获取当前地区的host地址
     *
     * @return
     */
    public static String getHost() {
        Cursor cursor = BaseApplication.getContext().getContentResolver().query(CITY_URI, null, null, null, null);
        String ip_host = "abroad.api.function.aibabel.cn";
        String key = "";
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                String ips = cursor.getString(cursor.getColumnIndex("ips"));
                String countryNameCN = cursor.getString(cursor.getColumnIndex("country"));

                if (TextUtils.equals("中国", countryNameCN)) {
                    key = "中国_" + BaseApplication.getContext().getPackageName() + "_function";
                } else {
                    key = "default_" + BaseApplication.getContext().getPackageName() + "_function";
                }
                JSONObject jsonObject = new JSONObject(ips);
                JSONArray jsonArray = new JSONArray(jsonObject.getString(key));
                ip_host = jsonArray.getJSONObject(0).get("domain").toString();
                ip_host = StringUtils.split(ip_host, ":");
            } else {
                Log.d("ContentProvider", "没有获取到定位信息");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != cursor)
                cursor.close();
        }
        return ip_host;

    }


    /**
     * 获取热修复地址
     * @return
     */
    public static String getHotFixHost() {
        Cursor cursor = BaseApplication.getContext().getContentResolver().query(CITY_URI, null, null, null, null);
        String ip = "http://api.joner.aibabel.cn:7001";
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                String locationWhere = cursor.getString(cursor.getColumnIndex("locationWhere"));
                if (null == locationWhere || TextUtils.equals(locationWhere, "0")) {
                    ip = "http://abroad.api.joner.aibabel.cn:7001";
                } else {
                    ip = "http://api.joner.aibabel.cn:7001";
                }

            } else {
                Log.d("ContentProvider", "没有获取到定位信息");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != cursor)
                cursor.close();
        }
        return ip;

    }


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
     * 获取当前定位城市（省）
     *
     * @param context
     * @return
     */
    public static String getCountry(Context context) {
        Cursor cursor = context.getContentResolver().query(CITY_URI, null, null, null, null);
        String city = "";
        String province = "";
        String country = "";
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                int countryIndex = cursor.getColumnIndex("country");
                int provinceIndex = cursor.getColumnIndex("province");
                int cityIndex = cursor.getColumnIndex("city");
                city = cursor.getString(cityIndex);
                province = cursor.getString(provinceIndex);
                country = cursor.getString(countryIndex);
            } else {
                Log.d("TAG", "：没有获取到定位信息");
            }
        } finally {
            if (null != cursor)
                cursor.close();
        }
        return country + " " + province + " " + city;
    }


    /**
     * 获取定位信息
     *
     * @param context
     * @return
     */
    public static String getGps(Context context) {
        Cursor cursor = context.getContentResolver().query(CITY_URI, null, null, null, null);
        double lat = 0d;
        double lon = 0d;
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                int latitudeIndex = cursor.getColumnIndex("latitude");
                int longitudeIndex = cursor.getColumnIndex("longitude");
                lat = cursor.getDouble(latitudeIndex);
                lon = cursor.getDouble(longitudeIndex);
            } else {
                Log.d("TAG", "：没有获取到定位信息");
            }
        } finally {
            if (null != cursor)
                cursor.close();
        }
        return lat + "," + lon;
    }


    /**
     * 获取经纬度
     *
     * @param context
     * @return
     */
    public static  Map<String,String> getLatAndLng(Context context) {
        Cursor cursor = context.getContentResolver().query(CITY_URI, null, null, null, null);

        Map<String,String> map = new HashMap<>();
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                int latitudeIndex = cursor.getColumnIndex("latitude");
                int longitudeIndex = cursor.getColumnIndex("longitude");
                double lat = cursor.getDouble(latitudeIndex);
                double lng = cursor.getDouble(longitudeIndex);
                map.put("lat",lat+"");
                map.put("lng",lng+"");
            } else {
                Log.d("TAG", "：没有获取到定位信息");
            }
        } finally {
            if (null != cursor)
                cursor.close();
        }
        return map;
    }

    /**
     * 判断网络是否可用
     *
     * @return true: 可用 false: 不可用
     */
    public static boolean isAvailable() {
        NetworkInfo info = ((ConnectivityManager) BaseApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
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
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static synchronized String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取本机SN 设备识别码
     *
     * @return
     */
    public static String getSN() {
        String serialNum = android.os.Build.SERIAL;

        if (TextUtils.isEmpty(serialNum)) {
            return "0000000000000000";
        }
        return serialNum;
    }

    /**
     * 获取一个随机数
     *
     * @return
     */
    public static int getRandom() {
        int random = (int) ((Math.random() * 9 + 1) * 1000);
        return random;
    }


    /**
     * 获取系统语言
     *
     * @return
     */
    public static String getLocal() {

        String country = Locale.getDefault().getCountry();
        String language = Locale.getDefault().getLanguage();
        String sl = "";

        switch (language) {
            case "zh":
                sl = language + "_" + country;
                break;
            case "en":
            case "ja":
            case "ko":
                sl = language;
                break;
            default:
                sl = "en";
                break;
        }
        return sl;
    }

    /**
     * 屏幕是否亮着
     *
     * @param context
     * @return
     */
    public static boolean isWake(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        boolean isWake = pm.isScreenOn();
        return isWake;
    }


    public static View getVersion(Context context) {
        View view;
        String version = "";

        try {
            String display = Build.DISPLAY;
            version = display.substring(0, 2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.e("CommonUtils_version", "===" + version);
        switch (version) {
            case "PM":
                view = View.inflate(context, R.layout.popupwindow_go, null);
                break;
            case "PL":
                view = View.inflate(context, R.layout.popupwindow_pro, null);
                break;
            case "PH":
                view = View.inflate(context, R.layout.popupwindow_fly, null);
                break;
            default:
                view = View.inflate(context, R.layout.popupwindow_go, null);
                break;
        }

        return view;

    }


    /**
     * 获取租赁和销售版本，s 销售 、 l 租赁
     *
     * @return
     */
    public static String getChildFlag() {
        String version = "M";
        try {
            String display = Build.DISPLAY;
            version = display.substring(9, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;
    }


    /**
     * 获取网络类型
     *
     * @return
     */
    public static String getNetworkType(Context context) {
        String netType = "NETWORK_NO";
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            if (info.getType() == ConnectivityManager.TYPE_ETHERNET) {
                netType = "NETWORK_ETHERNET";
            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                netType = "NETWORK_WIFI";
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                switch (info.getSubtype()) {

                    case TelephonyManager.NETWORK_TYPE_GSM:
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        netType = "NETWORK_2G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_TD_SCDMA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        netType = "NETWORK_3G";
                        break;

                    case TelephonyManager.NETWORK_TYPE_IWLAN:
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        netType = "NETWORK_4G";
                        break;
                    default:

                        String subtypeName = info.getSubtypeName();
                        if (subtypeName.equalsIgnoreCase("TD-SCDMA")
                                || subtypeName.equalsIgnoreCase("WCDMA")
                                || subtypeName.equalsIgnoreCase("CDMA2000")) {
                            netType = "NETWORK_3G";
                        } else {
                            netType = "NETWORK_UNKNOWN";
                        }
                        break;
                }
            } else {
                netType = "NETWORK_UNKNOWN";
            }
        }
        return netType;
    }


    /**
     * 获取系统版本号
     *
     * @param context
     * @return
     */
    public static String getSysVersion(Context context) {
        String version = "";

        try {
            version = Build.DISPLAY;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;

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


    /* 获取手机IMEI
     *
     * @param context
     * @return
     */
    public static String getIMEI(Context context, int index) {
        try {
            //实例化TelephonyManager对象
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //获取IMEI号
            @SuppressLint("MissingPermission") String imei = telephonyManager.getDeviceId(index);
            //在次做个验证，也不是什么时候都能获取到的啊
            if (imei == null) {
                imei = "";
            }
            return imei;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取手机IMSI
     */
    public static String getIMSI(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //获取IMSI号
            @SuppressLint("MissingPermission")
            String imsi = telephonyManager.getSubscriberId();
            if (null == imsi) {
                imsi = "";
            }
            return imsi;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private void getWhere() {
        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
    }


}
