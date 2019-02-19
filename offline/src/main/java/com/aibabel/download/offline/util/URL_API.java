package com.aibabel.download.offline.util;

import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.aibabel.download.offline.app.MyApplication;

import org.json.JSONArray;
import org.json.JSONObject;

public class URL_API {
    // 1.PL  pro   2.PH   fly    3.PM   go
    private static final String SYSTEM_TYPE="1";
    public static String baseUrl="http://abroad.api.joner.aibabel.cn:7001";

    //pro   fly
    public static String list_file_pro=baseUrl+"/v2/devicefiledownload/getFileList";

    //go
    public static String list_file_go=baseUrl+"/v1/devicefiledownload/getFileList";


    public static String get_download_url=baseUrl+"/v1/devicefiledownload/getFileUrl";


    public static String getList_file() {
        StringBuffer sb = null;
        if (TextUtils.equals(CommonUtils.getDeviceInfo(),"PL")||TextUtils.equals(CommonUtils.getDeviceInfo(),"PH")) {
            sb = new StringBuffer(list_file_pro);
            sb.append("?sn=" + CommonUtils.getSN());
            sb.append("&sv=" + Build.DISPLAY);
        } else if (TextUtils.equals(CommonUtils.getDeviceInfo(),"PM")) {
             sb=new StringBuffer(list_file_go);
            sb.append("?sn="+CommonUtils.getSN());
        }

        L.e("getList_file====================================="+sb.toString());
        return sb.toString();
    }



    /*获取当前定位城市和该地区的host地址*/
    private static final Uri CITY_URI = Uri.parse("content://com.aibabel.locationservice.provider.AibabelProvider/aibabel_location");

    /**
     * 获取当前地区的host地址
     *
     * @return
     */
    public static String getHost() {
        Cursor cursor = MyApplication.mContext.getContentResolver().query(CITY_URI, null, null, null, null);
        String ip_host = "http://abroad.api.joner.aibabel.cn:7001";
        String key = "";
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                String ips = cursor.getString(cursor.getColumnIndex("ips"));
                String countryNameCN = cursor.getString(cursor.getColumnIndex("country"));

                if (TextUtils.equals("中国", countryNameCN)) {
                    key = "中国_" + MyApplication.mContext.getPackageName() + "_function";
                } else {
                    key = "default_" + MyApplication.mContext.getPackageName() + "_function";
                }
                JSONObject jsonObject = new JSONObject(ips);
                JSONArray jsonArray = new JSONArray(jsonObject.getString(key));
                ip_host = jsonArray.getJSONObject(0).get("domain").toString();

            } else {
                Log.d("ContentProvider", "没有获取到定位信息");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != cursor)
                cursor.close();
        }
        baseUrl=ip_host;
        L.e("baseUrl======================="+baseUrl);
        return ip_host;

    }



}
