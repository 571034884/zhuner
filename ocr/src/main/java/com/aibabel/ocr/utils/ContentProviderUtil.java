package com.aibabel.ocr.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.aibabel.ocr.app.BaseApplication;
import com.aibabel.ocr.app.UrlConfig;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ContentProviderUtil {
    /*获取当前定位城市和该地区的host地址*/
    private static final Uri CITY_URI = Uri.parse("content://com.aibabel.locationservice.provider.AibabelProvider/aibabel_location");
    /*获取这台机器对应的国家*/
    private static final Uri COUNTRY_URI = Uri.parse("content://com.dommy.qrcode/aibabel_information");

    /**
     * 获取当前地区的host地址
     * @param context
     * @return
     */
    public static String getHost(Context context) {
        Cursor cursor = context.getContentResolver().query(CITY_URI, null, null, null, null);
        String ip_host = UrlConfig.DEFAULT_HOST;
        String key = "";
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                String ips = cursor.getString(cursor.getColumnIndex("ips"));
                String countryNameCN = cursor.getString(cursor.getColumnIndex("country"));
                if (TextUtils.equals(countryNameCN,"中国")) {
                    key = "中国_" + context.getPackageName() + "_camera";
                } else {
                    key = "default_" + context.getPackageName() + "_camera";
                }
                JSONObject jsonObject = new JSONObject(ips);
                JSONArray jsonArray = new JSONArray(jsonObject.getString(key));
                ip_host = jsonArray.getJSONObject(0).get("domain").toString();
                Log.d("ContentProviderUtil", ip_host);
            } else {
                Log.d("ContentProviderUtil", "：没有获取到定位信息");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(null!=cursor)
                cursor.close();
        }

        return ip_host;

    }

    /**
     * 获取当前定位城市（省）
     * @param context
     * @return
     */
    public static String getCity(Context context){
        Cursor cursor = context.getContentResolver().query(CITY_URI, null, null, null, null);
        String cityName = "";
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                int provienceId = cursor.getColumnIndex("province");
                cityName = cursor.getString(provienceId);
            } else {
                Log.d("TAG", "：没有获取到定位信息");
            }
        }finally {
            if(null!=cursor)
                cursor.close();
        }
       return cityName;
    }


    /**
     * 获取该机器对应的国家
     * @param context
     * @return
     */
    public static String getCountry(Context context){
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
        }finally {
            if(null!=cursor)
                cursor.close();
        }
        return countryName;

    }


    /**
     * 获取经纬度
     * @return
     */
    public static Map<String,Double> getLocation() {
        Cursor cursor = BaseApplication.CONTEXT.getContentResolver().query(Constant.CONTENT_URI, null, null, null, null);
        Map<String,Double> map = new HashMap<>();
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                cursor.getString(cursor.getColumnIndex("city"));
                int latId = cursor.getColumnIndex("latitude");
                int lonId = cursor.getColumnIndex("longitude");
                double latitude = cursor.getDouble(latId);
                double longitude = cursor.getDouble(lonId);
                map.put("latitude",latitude);
                map.put("longitude",longitude);

            } else {

            }
        } finally {
            if (null != cursor)
                cursor.close();
        }

        return map;

    }


}
