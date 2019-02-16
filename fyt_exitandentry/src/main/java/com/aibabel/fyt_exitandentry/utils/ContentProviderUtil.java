package com.aibabel.fyt_exitandentry.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;


import com.aibabel.fyt_exitandentry.app.BaseApplication;
import com.aibabel.fyt_exitandentry.bean.Constans;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ContentProviderUtil {
    /*获取当前定位城市和该地区的host地址*/
    private static final Uri CITY_URI = Uri.parse("content://com.aibabel.locationservice.provider.AibabelProvider/aibabel_location");
    /*获取这台机器对应的国家*/
    private static final Uri COUNTRY_URI = Uri.parse("content://com.dommy.qrcode/aibabel_information");
    /*获取离线下载状态*/
    private static final Uri CONTENT_URI = Uri.parse("content://com.aibabel.download.offline.provider.OfflineProvider");
    /**
     * 获取当前地区的host地址
     *
     * @param context
     * @return
     */
    public static String getHost(Context context) {
        Cursor cursor = context.getContentResolver().query(CITY_URI, null, null, null, null);
        String ip_host = Constans.HOST_XW;
        String key = "";
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                String ips = cursor.getString(cursor.getColumnIndex("ips"));
                String countryNameCN = cursor.getString(cursor.getColumnIndex("country"));
//                countryNameCN.equals("中国")
                if (TextUtils.equals(countryNameCN, "中国")) {
                    key = "中国_" + context.getPackageName() + "_joner";
                } else {
                    key = "default_" + context.getPackageName() + "_joner";
                }
                JSONObject jsonObject = new JSONObject(ips);
                JSONArray jsonArray = new JSONArray(jsonObject.getString(key));
                ip_host = jsonArray.getJSONObject(0).get("domain").toString() + "/v1/entryAndExit/";
            } else {
                Log.d("TAG", "：没有获取到服务器信息");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != cursor)
                cursor.close();
        }
        Log.d("ContentProviderUtil", ip_host);
        return ip_host;

    }

    /**
     * 获取当前定位城市（省）
     *
     * @param context
     * @return
     */
    public static String getCity(Context context) {
        Cursor cursor = context.getContentResolver().query(CITY_URI, null, null, null, null);
        String cityName = "";
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                int provienceId = cursor.getColumnIndex("city");
                cityName = cursor.getString(provienceId);
            } else {
                Log.d("TAG", "：没有获取到定位信息");
            }
        } finally {
            if (null != cursor)
                cursor.close();
        }
        return cityName;
    }


    /**
     * 获取该机器对应的国家
     *
     * @param context
     * @return
     */
    public static String getZeFengCountry(Context context) {
        Cursor cursor = context.getContentResolver().query(COUNTRY_URI, null, null, null, null);
        String countryName = "";
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                int countryIndex = cursor.getColumnIndex("Country");
                countryName = cursor.getString(countryIndex);
            } else {
                Log.d("TAG", "：没有获取到定位信息");
            }
        } finally {
            if (null != cursor)
                cursor.close();
        }
        return countryName;

    }


    /**
     * 获取定位国家
     *
     * @param context
     * @return
     */
    public static String getCountry(Context context) {
        Cursor cursor = context.getContentResolver().query(CITY_URI, null, null, null, null);
        String countryName = "";
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                int countryIndex = cursor.getColumnIndex("country");
                countryName = cursor.getString(countryIndex);
            } else {
                Log.d("TAG", "：没有获取到定位信息");
            }
        } finally {
            if (null != cursor)
                cursor.close();
        }
        return countryName;

    }


}
