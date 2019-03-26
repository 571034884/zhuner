package com.aibabel.ocr.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.aibabel.baselibrary.impl.IServerManager;
import com.aibabel.baselibrary.sphelper.SPHelper;
import com.aibabel.baselibrary.utils.ServerKeyUtils;
import com.aibabel.baselibrary.utils.XIPCUtils;
import com.aibabel.ocr.app.BaseApplication;
import com.aibabel.ocr.app.UrlConfig;
import com.xuexiang.xipc.XIPC;
import com.xuexiang.xipc.core.channel.IPCListener;
import com.xuexiang.xipc.core.channel.IPCService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

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
                if(getTimerType()==1){
                    key = "中国_" + context.getPackageName() + "_camera";
                }else{
                    key = "default_" + context.getPackageName() + "_camera";
                }
//                if (TextUtils.equals(countryNameCN,"中国")) {
//                    key = "中国_" + context.getPackageName() + "_camera";
//                } else {
//                    key = "default_" + context.getPackageName() + "_camera";
//                }
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
     * 获取当前地区的host地址
     *
     * @return
     */
    public static String getServerHost() {
        String ip_host = SPHelper.getString(ServerKeyUtils.serverKeyOcrCamera, UrlConfig.DEFAULT_HOST);
        Log.e("http：",ip_host);
        return ip_host;

    }


    /**
     * 请求切换服务器
     */
    public static void sendErrorServer() {
        XIPC.connectApp(XIPC.getContext(), XIPCUtils.XIPC_MENU_NEW);
        XIPC.setIPCListener(new IPCListener() {
            @Override
            public void onIPCConnected(Class<? extends IPCService> service) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        IServerManager dsm = XIPC.getInstance(IServerManager.class);
                        dsm.setPingServerError(ServerKeyUtils.serverKeyOcrCameraError);
                        Log.e("http", "请求换服务器！");
                        XIPC.disconnect(XIPC.getContext());
                    }
                }).start();

            }
        });
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
