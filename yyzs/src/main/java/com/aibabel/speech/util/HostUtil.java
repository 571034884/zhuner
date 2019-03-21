package com.aibabel.speech.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.aibabel.baselibrary.impl.IServerManager;
import com.aibabel.baselibrary.sphelper.SPHelper;
import com.aibabel.baselibrary.utils.ServerKeyUtils;
import com.aibabel.baselibrary.utils.XIPCUtils;
import com.aibabel.speech.app.BaseApplication;
import com.xuexiang.xipc.XIPC;
import com.xuexiang.xipc.core.channel.IPCListener;
import com.xuexiang.xipc.core.channel.IPCService;

import org.json.JSONArray;
import org.json.JSONObject;

public class HostUtil {
    private static final Uri CITY_URI = Uri.parse("content://com.aibabel.locationservice.provider.AibabelProvider/aibabel_location");


//    public static String getHost(Context context, String typeName) {
//        Cursor cursor = context.getContentResolver().query(CITY_URI, null, null, null, null);
//        String ip_host = "";
//        String key = "";
//        try {
//            if (cursor != null && cursor.getCount() > 0) {
//                cursor.moveToFirst();
//                String ips = cursor.getString(cursor.getColumnIndex("ips"));
//                String countryNameCN = cursor.getString(cursor.getColumnIndex("country"));
//
//                if (countryNameCN.equals("中国")) {
//
//                    key = "中国_" + context.getPackageName() + "_" + typeName;
//                } else {
//                    key = "default_" + context.getPackageName() + "_" + typeName;
//                }
//                JSONObject jsonObject = new JSONObject(ips);
//                JSONArray jsonArray = new JSONArray(jsonObject.getString(key));
//                ip_host = jsonArray.getJSONObject(0).get("domain").toString();
//            } else {
//                Log.d("TAG", "：没有获取到定位信息");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (null != cursor)
//                cursor.close();
//        }
//        L.e("url>>>>>>>>>>>>>>>>>>>>>" + ip_host);
//        return ip_host;
//    }

    /**
     * 获取当前地区的host地址
     *
     * @return
     */
    public static String getServerHost(String typeName) {
        String ip_host = "";
        switch (typeName) {
            case "function"://识别服务器，默认是国外地址
                ip_host =SPHelper.getString(ServerKeyUtils.serverKeySpeechFunction, "abroad.api.function.aibabel.cn:5005");
                break;
            case "pa"://意图识别服务器，默认是国外地址
                ip_host = SPHelper.getString(ServerKeyUtils.serverKeySpeechPa, "http://abroad.api.pa.aibabel.cn:6021");
                break;
        }
        Log.e("http", ip_host);
        return ip_host;

    }


    /**
     * 请求切换服务器
     */
    public static void sendErrorServer(final String errorHost) {
        XIPC.connectApp(XIPC.getContext(), XIPCUtils.XIPC_MENU_NEW);
        XIPC.setIPCListener(new IPCListener() {
            @Override
            public void onIPCConnected(Class<? extends IPCService> service) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        IServerManager dsm = XIPC.getInstance(IServerManager.class);
                        dsm.setPingServerError(errorHost);
                        Log.e("http", "请求换服务器！");
                        XIPC.disconnect(XIPC.getContext());
                    }
                }).start();

            }
        });
    }

    /**
     * 获取坐标
     *
     * @param context
     * @return
     */
    public static String getLocation(Context context) {
        String mLocation = "0,0";
        try {
            Cursor cursor = context.getContentResolver().query(CITY_URI, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Double lat = cursor.getDouble(cursor.getColumnIndex("latitude"));
                    Double lon = cursor.getDouble(cursor.getColumnIndex("longitude"));
                    L.e(lat + "-----------");
                    L.e(lon + "----------");
                    if (lat == 4.9E-324) {
                        mLocation = "0,0";
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


    /**
     * 获取热修复地址
     *
     * @return
     */
    public static String getHotFixHost(Context mContext) {
        Cursor cursor = mContext.getContentResolver().query(CITY_URI, null, null, null, null);
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


}
