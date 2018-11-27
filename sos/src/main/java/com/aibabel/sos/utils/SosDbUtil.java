package com.aibabel.sos.utils;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.aibabel.sos.bean.SosBean;
import com.aibabel.sos.bean.SousuoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：SunSH on 2018/7/2 17:25
 * 功能：
 * 版本：1.0
 */
public class SosDbUtil {

    public static final String DATABASE_PATH = "/data/data/com.aibabel.sos/databases/sos.db";

    public static final String TABLE_NAME = "sos";
    public static String COLUMN_DZ = "dz";
    public static String COLUMN_GJ = "gj";
    public static String COLUMN_CS = "cs";
    public static final String COLUMN_GJQH = "gjqh";
    public static final String COLUMN_LSGDH = "lsgdh";
    public static String COLUMN_LSGDZ = "lsgdz";
    public static final String COLUMN_BJDH = "bjdh";
    public static final String COLUMN_JJDH = "jjdh";
    public static final String COLUMN_GQ = "gq";
    public static final String COLUMN_ZB = "zb";
    public static  String COLUMN_L = "cs";

    public static List<SosBean> getDazhou(String dazhou) {
        List<SosBean> list = new ArrayList<SosBean>();
        try {
            SQLiteDatabase db = SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READWRITE);
            if (db.isOpen()) {
                Cursor cursor = db.rawQuery("select distinct " + COLUMN_GJ + "," + COLUMN_GQ + " from " + TABLE_NAME + " where " + COLUMN_DZ + " = '" + dazhou + "'", null);
//            Cursor cursor = db.rawQuery("select distinct gj from sos1 where dz = '亚洲'",null);
                while (cursor.moveToNext()) {
                    SosBean bean = new SosBean();
                    bean.setDz(dazhou);
                    bean.setGj(cursor.getString(cursor.getColumnIndex(COLUMN_GJ)));
                    bean.setGq(cursor.getString(cursor.getColumnIndex(COLUMN_GQ)));
                    list.add(bean);
                }
                cursor.close();
            }
        } catch (Exception e) {

        }

        return list;
    }

    public static List<SosBean> getChengshi(boolean paixu) {
        List<SosBean> list = new ArrayList<SosBean>();
        try {
            SQLiteDatabase db = SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READWRITE);
            if (db.isOpen()) {
                Cursor cursor;
                if (paixu)
                    cursor = db.rawQuery("select " + COLUMN_CS + "," + COLUMN_GJ + "," + COLUMN_L + " from " + TABLE_NAME + " order by " + COLUMN_L, null);
                else
                    cursor = db.rawQuery("select " + COLUMN_CS + "," + COLUMN_GJ + "," + COLUMN_L + " from " + TABLE_NAME, null);
//            Cursor cursor = db.rawQuery("select distinct gj from sos1 where dz = '亚洲'",null);
                while (cursor.moveToNext()) {
                    SosBean bean = new SosBean();
                    bean.setCs(cursor.getString(cursor.getColumnIndex(COLUMN_CS)));
                    bean.setGj(cursor.getString(cursor.getColumnIndex(COLUMN_GJ)));
                    bean.setL(cursor.getString(cursor.getColumnIndex(COLUMN_L)));
                    list.add(bean);
                }
                cursor.close();
            }
        } catch (Exception e) {

        }

        return list;
    }

    public static List<SosBean> getChengshi(String guojia) {
        List<SosBean> list = new ArrayList<SosBean>();
        try {
            SQLiteDatabase db = SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READWRITE);
            if (db.isOpen()) {
                Cursor cursor = db.rawQuery("select " + COLUMN_CS + " from " + TABLE_NAME + " where " + COLUMN_GJ + " = '" + guojia + "'", null);
//            Cursor cursor = db.rawQuery("select distinct gj from sos1 where dz = '亚洲'",null);
                while (cursor.moveToNext()) {
                    SosBean bean = new SosBean();
                    bean.setCs(cursor.getString(cursor.getColumnIndex(COLUMN_CS)));
                    list.add(bean);
                }
                cursor.close();
            }
        } catch (Exception e) {

        }

        return list;
    }

    public static List<SosBean> getLingshiguan(String chengshi) {
        List<SosBean> list = new ArrayList<SosBean>();
        try {
            SQLiteDatabase db = SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READWRITE);
            if (db.isOpen()) {
                Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_CS + " = '" + chengshi + "'", null);
//            Cursor cursor = db.rawQuery("select distinct gj from sos1 where dz = '亚洲'",null);
                while (cursor.moveToNext()) {
                    SosBean bean = new SosBean();
                    bean.setDz(cursor.getString(cursor.getColumnIndex(COLUMN_DZ)));
                    bean.setGj(cursor.getString(cursor.getColumnIndex(COLUMN_GJ)));
                    bean.setCs(cursor.getString(cursor.getColumnIndex(COLUMN_CS)));
                    bean.setGjqh(cursor.getInt(cursor.getColumnIndex(COLUMN_GJQH)));
                    bean.setLsgdh(cursor.getString(cursor.getColumnIndex(COLUMN_LSGDH)));
                    bean.setLsgdz(cursor.getString(cursor.getColumnIndex(COLUMN_LSGDZ)));
                    bean.setBjdh(cursor.getString(cursor.getColumnIndex(COLUMN_BJDH)));
                    bean.setJjdh(cursor.getString(cursor.getColumnIndex(COLUMN_JJDH)));
                    bean.setZb(cursor.getString(cursor.getColumnIndex(COLUMN_ZB)));
                    list.add(bean);
                }
                cursor.close();
            }
        } catch (Exception e) {

        }

        return list;
    }

    public static List<SosBean> getLingshiguanInGuojia(String guojia) {
        List<SosBean> list = new ArrayList<SosBean>();
        try {
            SQLiteDatabase db = SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READWRITE);
            if (db.isOpen()) {
                Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_GJ + " = '" + guojia + "'", null);
//            Cursor cursor = db.rawQuery("select distinct gj from sos1 where dz = '亚洲'",null);
                while (cursor.moveToNext()) {
                    SosBean bean = new SosBean();
                    bean.setDz(cursor.getString(cursor.getColumnIndex(COLUMN_DZ)));
                    bean.setGj(cursor.getString(cursor.getColumnIndex(COLUMN_GJ)));
                    bean.setCs(cursor.getString(cursor.getColumnIndex(COLUMN_CS)));
                    bean.setGjqh(cursor.getInt(cursor.getColumnIndex(COLUMN_GJQH)));
                    bean.setLsgdh(cursor.getString(cursor.getColumnIndex(COLUMN_LSGDH)));
                    bean.setLsgdz(cursor.getString(cursor.getColumnIndex(COLUMN_LSGDZ)));
                    bean.setBjdh(cursor.getString(cursor.getColumnIndex(COLUMN_BJDH)));
                    bean.setJjdh(cursor.getString(cursor.getColumnIndex(COLUMN_JJDH)));
                    bean.setZb(cursor.getString(cursor.getColumnIndex(COLUMN_ZB)));
                    list.add(bean);
                }
                cursor.close();
            }
        } catch (Exception e) {

        }

        return list;
    }

    public static List<SousuoBean> mohuSousuo(String key) {
        List<SousuoBean> list = new ArrayList<SousuoBean>();
        try {
            SQLiteDatabase db = SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READWRITE);
            if (db.isOpen()) {
                Cursor cursor = db.rawQuery("select " + COLUMN_CS + "," + COLUMN_GJ + " from " + TABLE_NAME + " where " + COLUMN_GJ + " like '%" + key + "%'", null);
//            Cursor cursor = db.rawQuery("select distinct gj from sos1 where dz = '亚洲'",null);
                while (cursor.moveToNext()) {
                    SousuoBean bean = new SousuoBean();
                    bean.setGj(cursor.getString(cursor.getColumnIndex(COLUMN_GJ)));
                    bean.setCs(cursor.getString(cursor.getColumnIndex(COLUMN_CS)));
                    bean.setType(1);
                    list.add(bean);
                }
                cursor = db.rawQuery("select " + COLUMN_CS + "," + COLUMN_GJ + " from " + TABLE_NAME + " where " + COLUMN_CS + " like '%" + key + "%'", null);
//            Cursor cursor = db.rawQuery("select distinct gj from sos1 where dz = '亚洲'",null);
                while (cursor.moveToNext()) {
                    SousuoBean bean = new SousuoBean();
                    bean.setGj(cursor.getString(cursor.getColumnIndex(COLUMN_GJ)));
                    bean.setCs(cursor.getString(cursor.getColumnIndex(COLUMN_CS)));
                    bean.setType(2);
                    list.add(bean);
                }
                cursor.close();
            }
        } catch (Exception e) {

        }

        return list;
    }
}
