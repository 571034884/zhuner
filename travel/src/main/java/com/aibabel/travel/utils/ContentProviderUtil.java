package com.aibabel.travel.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.aibabel.travel.app.BaseApplication;
import com.aibabel.travel.app.UrlConfig;
import com.aibabel.travel.bean.OfflineBean;

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
        String ip_host = UrlConfig.DEFAULT_HOST;
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
                ip_host = jsonArray.getJSONObject(0).get("domain").toString() + "/v1/tourguide/";
                Constant.RXF_URL = jsonArray.getJSONObject(0).get("domain").toString();

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
                int provienceId = cursor.getColumnIndex("province");
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
    public static String getCountry(Context context) {
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
     * 获取离线下载
     *
     * @param
     * @return
     */
    public static List<OfflineBean> getOffline() {
//        Cursor cursor = BaseApplication.CONTEXT.getContentResolver().query(COUNTRY_URI, null, null, null, null);
        Cursor cursor = BaseApplication.CONTEXT.getContentResolver().query(CONTENT_URI, null, null, new String[]{"jqdl"}, null);

        List<OfflineBean> list = new ArrayList<>();

//        OfflineBean bean1 = new OfflineBean();
//        OfflineBean bean2 = new OfflineBean();
//        OfflineBean bean3 = new OfflineBean();
//        OfflineBean bean4 = new OfflineBean();
//        OfflineBean bean5 = new OfflineBean();
//        OfflineBean bean6 = new OfflineBean();
//        OfflineBean bean7 = new OfflineBean();
//        bean1.setId("jqdl_jp");
//        bean1.setStatus("10");//1为安装成功 10为已下载请去安装
//        bean2.setId("jqdl_ch");
//        bean2.setStatus("1");//1为安装成功 10为已下载请去安装
//        bean3.setId("jqdl_it");
//        bean3.setStatus("10");//1为安装成功 10为已下载请去安装
//        bean4.setId("jqdl_ru");
//        bean4.setStatus("1");//1为安装成功 10为已下载请去安装
//        bean5.setId("jqdl_fr");
//        bean5.setStatus("1");//1为安装成功 10为已下载请去安装
//        bean6.setId("jqdl_th");
//        bean6.setStatus("1");//1为安装成功 10为已下载请去安装
//        bean7.setId("jqdl_ko");
//        bean7.setStatus("99");//1为安装成功 10为已下载请去安装
//
//        list.add(bean1);
//        list.add(bean2);
//        list.add(bean3);
//        list.add(bean4);
//        list.add(bean5);
//        list.add(bean6);
//        list.add(bean7);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    OfflineBean bean = new OfflineBean();
                    String id = cursor.getString(cursor.getColumnIndex("Id"));
                    String status = cursor.getString(cursor.getColumnIndex("status"));
                    bean.setId(id);
                    bean.setStatus(status);
                    list.add(bean);
                }
            } else {
                Log.d("TAG", "：can not get offline data！");
            }

        } finally {
            if (null != cursor)
                cursor.close();
        }
        return list;

    }

}
