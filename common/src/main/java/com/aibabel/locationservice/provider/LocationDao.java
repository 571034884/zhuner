package com.aibabel.locationservice.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.aibabel.locationservice.bean.LocationBean;

public class LocationDao {


    protected static final String TABLE_NAME = "_location";

    public static final String COLUMN_ID = "id";//详细地址信息
    public static final String COLUMN_ADDR = "addr";//详细地址信息
    public static final String COLUMN_COUNTRY = "country";//国家
    public static final String COLUMN_PROVINCE = "province";//省份
    public static final String COLUMN_CITY = "city";//城市
    public static final String COLUMN_DISTRICT = "district"; //区县
    public static final String COLUMN_STREET = "street"; //街道信息
    public static final String COLUMN_DESCRIBE = "describe";//位置描述信息
    public static final String COLUMN_LATITUDE = "latitude";//纬度
    public static final String COLUMN_LONGITUDE = "longitude"; //经度
    public static final String COLUMN_WHERE = "where"; //国内外
    public static final String COLUMN_COOR = "coor"; //定位类型



    private AibabelDBHelper dbHelper;

    public LocationDao(Context context) {
        dbHelper = AibabelDBHelper.getInstance(context);
    }

    /**
     * 保存定位
     * @param locationBean
     */
    public void saveLocation(LocationBean locationBean){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, locationBean.getId());
        values.put(COLUMN_COUNTRY, locationBean.getCountry());
        values.put(COLUMN_PROVINCE,locationBean.getProvince());
        values.put(COLUMN_LATITUDE,locationBean.getLatitude());
        values.put(COLUMN_LONGITUDE,locationBean.getLongitude());
        values.put(COLUMN_CITY,locationBean.getCity());
        values.put(COLUMN_DISTRICT,locationBean.getDistrict());
        values.put(COLUMN_STREET,locationBean.getStreet());
        values.put(COLUMN_ADDR,locationBean.getAddr());
        values.put(COLUMN_DESCRIBE,locationBean.getLocationDescribe());
        values.put(COLUMN_WHERE,locationBean.getWhere());
        values.put(COLUMN_COOR,locationBean.getCoor());

        if(db.isOpen()){
            db.replace(TABLE_NAME, null, values);
        }
    }


    private void get(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME,null);
        if (!cursor.moveToFirst()) {
            cursor.close();

        }

        String strVal = cursor.getString(0);
        if (strVal == null || strVal.equals("")) {
            Log.d("4444444",strVal);
        }

        cursor.close();
    }


}
