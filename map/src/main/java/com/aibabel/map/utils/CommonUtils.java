package com.aibabel.map.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import com.aibabel.baselibrary.utils.ToastUtil;

import java.lang.reflect.Method;

public class CommonUtils {

    private static final int MIN_CLICK_DELAY_TIME = 2000;
    private static long lastClickTime;
    /*获取当前定位城市和该地区的host地址*/
    private static final Uri CITY_URI = Uri.parse("content://com.aibabel.locationservice.provider.AibabelProvider/aibabel_location");
    private static final Uri CONTENT_URI = Uri.parse("content://com.dommy.qrcode/aibabel_information");

    /**
     * 获取当前城市
     *
     * @param context
     * @return
     */
    public static String getCity(Context context) {
        Cursor cursor = context.getContentResolver().query(CITY_URI, null, null, null, null);
        String city = "";
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                int cityIndex = cursor.getColumnIndex("city");
                city = cursor.getString(cityIndex);
            } else {
                Log.d("TAG", "：没有获取到当前城市");
            }
        } catch (Exception e){
            ToastUtil.showShort(context,"没有获取到当前城市");
        }finally {
            if (null != cursor)
                cursor.close();
        }
        return city;
    }

    /**
     * 获取当前国内外
     *
     * @param context
     * @return
     */
    public static String getLocationWhere(Context context) {
        Cursor cursor = context.getContentResolver().query(CITY_URI, null, null, null, null);
        String where = "";
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                int whereIndex = cursor.getColumnIndex("locationWhere");
                where = cursor.getString(whereIndex);
            } else {
                Log.d("TAG", "：没有获取到国内外数据");
            }
        }catch (Exception e){
            ToastUtil.showShort(context,"没有获取到国内外数据");
        } finally {
            if (null != cursor)
                cursor.close();
        }
        return where;
    }

    /**
     * 获取当前坐标系
     *
     * @param context
     * @return
     */
    public static String getCoorType(Context context) {
        Cursor cursor = context.getContentResolver().query(CITY_URI, null, null, null, null);
        String coor = "";
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                int coorIndex = cursor.getColumnIndex("coor");
                coor = cursor.getString(coorIndex);
            } else {
                Log.d("TAG", "：没有获取到坐标系");
            }
        }catch (Exception e){
            ToastUtil.showShort(context,"没有获取到坐标系");
        } finally {
            if (null != cursor)
                cursor.close();
        }
        return coor;
    }

    /**
     * 获取当前经纬度
     *
     * @param context
     * @return
     */
    public static String getLatLng(Context context) {
        Cursor cursor = context.getContentResolver().query(CITY_URI, null, null, null, null);
        String latlng = "";
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                int latId = cursor.getColumnIndex("latitude");
                int lonId = cursor.getColumnIndex("longitude");
                double latitude = cursor.getDouble(latId);
                double longitude = cursor.getDouble(lonId);
                latlng = latitude+","+longitude;
            } else {
                Log.d("TAG", "：没有获取到定位信息");
            }
        } catch (Exception e){
            ToastUtil.showShort(context,"没有获取到定位信息");
        }finally {
            if (null != cursor)
                cursor.close();
        }
        return latlng;
    }

    /**
     * 获取订单ID
     * @param context
     * @return  返回orderid
     */
    public static String getOrderID(Context context) {

        Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, null, null, null);
        String leaseId = "";
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                leaseId = cursor.getString(cursor.getColumnIndex("oid"));
            } else {
                Log.e("Orderid:","空");
            }
        }catch (Exception e){
            Log.e("Orderid:","空");
        }finally {
            if (null != cursor)
                cursor.close();
        }
        return leaseId;
    }

    /**
     * 获取SN码
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

    public static boolean isNetworkAvailable(Context context) {
        @SuppressLint("WrongConstant") NetworkInfo info = ((ConnectivityManager)context.getSystemService("connectivity")).getActiveNetworkInfo();
        return info != null && info.isAvailable();
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

}