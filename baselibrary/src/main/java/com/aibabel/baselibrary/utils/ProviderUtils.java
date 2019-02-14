package com.aibabel.baselibrary.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.aibabel.baselibrary.base.BaseApplication;
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

    public static final String COLUMN_ADDR = "addr";//详细地址信息
    public static final String COLUMN_COUNTRY = "country";//国家
    public static final String COLUMN_PROVINCE = "province";//省份
    public static final String COLUMN_CITY = "city";//城市
    public static final String COLUMN_DISTRICT = "district"; //区县
    public static final String COLUMN_STREET = "street"; //街道信息
    public static final String COLUMN_DESCRIBE = "describe";//位置描述信息
    public static final String COLUMN_LATITUDE = "latitude";//纬度
    public static final String COLUMN_LONGITUDE = "longitude"; //经度
    public static final String COLUMN_IP = "ips"; //服务器地址列表

    /**
     * 使用默认uri
     *
     * @param columnName 列名
     * @return 列名对应的值
     */
    public static String getInfo( String columnName) {
        return getInfo( CONTENT_URI_DEFAULT, columnName);
    }

    /**
     * @param providerUri uri地址
     * @param columnName  列名
     * @return 列名对应的值
     */
    public static String getInfo (Uri providerUri, String columnName) {
        String info = "";
        Cursor cursor = BaseApplication.mApplication.getContentResolver().query(providerUri, null, null, null, null);
        try {
            if (cursor != null) {
                cursor.moveToFirst();
                info = cursor.getString(cursor.getColumnIndex(columnName));
            }
        } catch (Exception e) {
            Logger.e("没有获取到定位信息");
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
        }
        return info == null ? "" : info;
    }

}
