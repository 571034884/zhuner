package com.aibabel.alliedclock.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

/**
 * 作者：SunSH on 2018/8/16 16:51
 * 功能：
 * 版本：1.0
 */
public class WeizhiUtil {

    public static String TAG = "WeizhiUtil";

    public static final Uri CONTENT_URI_WY = Uri.parse("content://com.aibabel.locationservice.provider.AibabelProvider/aibabel_location");
    public static final Uri CONTENT_URI_ZF = Uri.parse("content://com.dommy.qrcode/aibabel_information");

    public static String getInfo(Context context, Uri providerUri, String columnName) {
        String area = "";
        Cursor cursor = context.getContentResolver().query(providerUri, null, null, null, null);
        try {
            if (cursor != null) {
                cursor.moveToFirst();
                area = cursor.getString(cursor.getColumnIndex(columnName));
            }
        } catch (Exception e) {
            Log.e(TAG, "没有获取到定位信息");
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
        }
        return area == null ? "" : area;
    }
}
