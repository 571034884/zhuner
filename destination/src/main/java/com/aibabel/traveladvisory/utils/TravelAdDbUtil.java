package com.aibabel.traveladvisory.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.aibabel.traveladvisory.bean.CountryCitysBean;
import com.aibabel.traveladvisory.bean.HotCityBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：SunSH on 2018/9/20 12:01
 * 功能：
 * 版本：1.0
 */
public class TravelAdDbUtil {

    public static final String DATABASE_PATH = "/data/data/com.aibabel.traveladvisory/databases/mudidi.db";

    public static final String TABEL_REMEN = "remenchengshi";
    public static final String COLUMN_R_TYPE = "addrType";
    public static final String COLUMN_R_ID = "Id";
    public static final String COLUMN_R_URL = "imageUrl";
    public static final String COLUMN_R_NAME_CITY = "cityName";
    public static final String COLUMN_R_NAME_COUNTRY = "countryName";
    public static final String COLUMN_R_NAME_CITY_EN = "cityEnName";

    public static final String TABEL_QUANBU = "quanbuchengshi";
    public static final String COLUMN_Q_ID = "id";
    public static final String COLUMN_Q_URL = "imageUrl";
    public static final String COLUMN_Q_NAME_CITY = "cnName";
    public static final String COLUMN_Q_NAME_COUNTRY = "country_name";
    public static final String COLUMN_Q_NAME_CITY_EN = "enName";
    public static final String COLUMN_Q_NAME_TRAVEL_TIME = "travelTime";

    public static List<HotCityBean.DataBean> getReMen(String countryName) {
        List<HotCityBean.DataBean> list = new ArrayList<>();
        try {
            SQLiteDatabase db = SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READWRITE);
            if (db.isOpen()) {
                Cursor cursor = db.rawQuery("select distinct " + COLUMN_R_TYPE + "," + COLUMN_R_URL + "," + COLUMN_R_ID + "," + COLUMN_R_NAME_CITY + "," + COLUMN_R_NAME_CITY_EN + " from " + TABEL_REMEN + " where " + COLUMN_R_NAME_COUNTRY + " = '" + countryName + "'" + " and " + COLUMN_R_TYPE + " != 'country'", null);
//                select distinct addrType,id,cityName,cityEnName from remenchengshi where countryName = '日本'
                while (cursor.moveToNext()) {
                    HotCityBean.DataBean bean = new HotCityBean.DataBean();
                    bean.setType(cursor.getString(cursor.getColumnIndex(COLUMN_R_TYPE)));
                    bean.setImageCityUrl(cursor.getString(cursor.getColumnIndex(COLUMN_R_URL)));
                    bean.setId(cursor.getString(cursor.getColumnIndex(COLUMN_R_ID)));
                    bean.setName(cursor.getString(cursor.getColumnIndex(COLUMN_R_NAME_CITY)));
                    bean.setEnName(cursor.getString(cursor.getColumnIndex(COLUMN_R_NAME_CITY_EN)));
                    list.add(bean);
                }
                cursor.close();
            }
        } catch (Exception e) {

        }
        return list;
    }

    public static List<CountryCitysBean.DataBean> getAllChengshi(String countryName) {
        List<CountryCitysBean.DataBean> list = new ArrayList<>();
        try {
            SQLiteDatabase db = SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READWRITE);
            if (db.isOpen()) {
                Cursor cursor = db.rawQuery("select distinct " + COLUMN_Q_ID + ","+ COLUMN_Q_NAME_TRAVEL_TIME + "," + COLUMN_Q_URL + "," + COLUMN_Q_NAME_CITY + "," + COLUMN_Q_NAME_CITY_EN + " from " + TABEL_QUANBU + " where " + COLUMN_Q_NAME_COUNTRY + " = '" + countryName + "'", null);
//                select distinct addrType,id,cityName,cityEnName from remenchengshi where countryName = '日本'
                while (cursor.moveToNext()) {
                    CountryCitysBean.DataBean bean = new CountryCitysBean.DataBean();
                    bean.setId(cursor.getString(cursor.getColumnIndex(COLUMN_Q_ID)));
                    bean.setImageUrl(cursor.getString(cursor.getColumnIndex(COLUMN_Q_URL)));
                    bean.setTravelTime(FastJsonUtil.changeJsonToBean(cursor.getString(cursor.getColumnIndex(COLUMN_Q_NAME_TRAVEL_TIME)), CountryCitysBean.DataBean.TravelTimeBean.class));
                    bean.setCnName(cursor.getString(cursor.getColumnIndex(COLUMN_Q_NAME_CITY)));
                    bean.setEnName(cursor.getString(cursor.getColumnIndex(COLUMN_Q_NAME_CITY_EN)));
                    list.add(bean);
                }
                cursor.close();
            }
        } catch (Exception e) {

        }
        return list;
    }

}
