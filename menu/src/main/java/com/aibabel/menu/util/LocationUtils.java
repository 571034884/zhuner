package com.aibabel.menu.util;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;

import com.aibabel.baselibrary.utils.ProviderUtils;

import java.util.HashMap;
import java.util.Map;

import static com.aibabel.baselibrary.http.OkGoUtil.appVersionName;

public class LocationUtils {

    /**
     * 获取坐标
     *
     * @param context
     * @return
     */
    public static String getLocation(Context context) {
        String mLocation = "";
        try {
            Cursor cursor = context.getContentResolver().query(ProviderUtils.CONTENT_URI_LOCATION, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Double lat = cursor.getDouble(cursor.getColumnIndex("latitude"));
                    Double lon = cursor.getDouble(cursor.getColumnIndex("longitude"));
                    L.e(lat + "-----------");
                    L.e(lon + "----------");
                    if (lat == 4.9E-324||lat==0.0) {
                        mLocation = "";
                    } else {
                        mLocation = lat + "," + lon;
                    }

                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            L.e("getLocation--------" + e.getMessage());

        }
        return mLocation;
    }


    static String[] provinceArr = {"首尔特别市", "东京都", "曼谷直辖市", "河内市", "万象市"};
    static String[] countryArr = {"新加坡"};
    public static boolean isTest = false;

    /**
     * 获取坐标
     *
     * @param context
     * @return
     */
    public static Map getLocMap(Context context) {
        Map<String, String> map = new HashMap<>();
        try {
            Cursor cursor = context.getContentResolver().query(ProviderUtils.CONTENT_URI_LOCATION, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String city = cursor.getString(cursor.getColumnIndex("city"));
                    String country = cursor.getString(cursor.getColumnIndex("country"));
                    String province = cursor.getString(cursor.getColumnIndex("province"));
                    String street = cursor.getString(cursor.getColumnIndex("street"));


                    if (street != null) {
                        map.put("street", street);
                    } else {
                        map.put("street", "");
                    }

                    if (city != null) {
                        map.put("city", city);
                    } else {
                        map.put("city", "");
                    }

                    if (province != null) {
                        map.put("province", province);
                    } else {
                        map.put("province", "");
                    }

                    if (country != null) {
                        map.put("country", country);
                    } else {
                        map.put("country", "");
                    }


                    L.e("getLocMap===================" + isTest);

                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            L.e("getLocation--------" + e.getMessage());

        }
        return map;
    }


    public static void printUrl(Context mContext,String urlName,Map<String,String>  prams){
        String baseUrl="http://abroad.api.function.aibabel.cn:7001"+"/v1/deviceMenu/"+urlName;
        Map<String, String> map = new HashMap<>();
        map.put("sv", Build.DISPLAY);
        map.put("sn", CommonUtils.getSN());
        map.put("sl", com.aibabel.baselibrary.utils.CommonUtils.getLocalLanguage());
        map.put("av", appVersionName);
        map.put("no", CommonUtils.getRandom()+"");
        String[] arr=getLocation(mContext).split(",");
        try {
            map.put("lat", arr[0]);
            map.put("lng", arr[1]);
        } catch (Exception e) {

        }
        for (String key :prams.keySet()) {
            map.put(key,prams.get(key));
        }
        StringBuilder sb=new StringBuilder(baseUrl);
        int i=0;
        for (String key :map.keySet()) {
            if (i == 0) {
                sb.append("?");
                sb.append(key + "=");
                sb.append(map.get(key));
            } else {
                sb.append("&");
                sb.append(key+"=");
                sb.append(map.get(key));
            }
            i++;

        }

        L.e("getUrl>>>>>>>>>>>>>>>>>>>>>>>>>>"+sb.toString());


        }
}
