package com.aibabel.baselibrary.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.orhanobut.logger.Logger;

import java.security.PublicKey;

/**
 * 作者：SunSH on 2018/11/14 15:29
 * 功能：获取定位信息以及服务器地址
 * 版本：1.0
 */
public class ProviderUtils {

    public static final Uri CONTENT_URI_LOCATION = Uri.parse("content://com.aibabel.locationservice.provider.AibabelProvider/aibabel_location");
    public static final Uri CONTENT_URI_INFO = Uri.parse("content://com.dommy.qrcode/aibabel_information");
    public static final Uri CONTENT_URI_DEFAULT = CONTENT_URI_LOCATION;

    /**
     * 使用默认uri
     *
     * @param context    上下文
     * @param columnName 列名
     * @return 列名对应的值
     */
    public static String getInfo(Context context, String columnName) {
        return getInfo(context, CONTENT_URI_DEFAULT, columnName);
    }

    /**
     * @param context     上下文
     * @param providerUri uri地址
     * @param columnName  列名
     * @return 列名对应的值
     */
    public static String getInfo(Context context, Uri providerUri, String columnName) {
        String area = "";
        Cursor cursor = context.getContentResolver().query(providerUri, null, null, null, null);
        try {
            if (cursor != null) {
                cursor.moveToFirst();
                area = cursor.getString(cursor.getColumnIndex(columnName));
            }
        } catch (Exception e) {
            Logger.e("没有获取到定位信息");
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
        }
        return area == null ? "" : area;
    }

}
