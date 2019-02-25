package com.example.root.testhuaping;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class ContentProviderUtil {
    /*获取当前定位城市和该地区的host地址*/
    private static final Uri CITY_URI = Uri.parse("content://com.aibabel.locationservice.provider.AibabelProvider/aibabel_location");
    /*获取这台机器对应的国家*/
    private static final Uri COUNTRY_URI = Uri.parse("content://com.dommy.qrcode/aibabel_information");


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
        Cursor cursor = context.getContentResolver().query(CITY_URI, null, null, null, null);
        String cityName = "";
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                int provienceId = cursor.getColumnIndex("country");
                cityName = cursor.getString(provienceId);
            } else {
                Log.d("TAG", "：没有获取到定位信息");
            }
        }finally {
            if(null!=cursor)
                cursor.close();
        }
        Log.d("wzf", "cityName="+cityName);
        return cityName;
    }


}
