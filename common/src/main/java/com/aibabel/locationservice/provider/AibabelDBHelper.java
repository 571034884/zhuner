package com.aibabel.locationservice.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class AibabelDBHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "aibabel.db";
    private static  AibabelDBHelper instance;

    public AibabelDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_CONTACT_TABLE = "CREATE TABLE " + LocationModel.LocationEntry.TABLE_NAME + "( "
                + LocationModel.LocationEntry._ID + " TEXT PRIMARY KEY, "
                + LocationModel.LocationEntry.COLUMN_ADDR + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_COUNTRY + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_PROVINCE + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_CITY + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_COOR + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_WHERE + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_DISTRICT + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_STREET + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_DESCRIBE + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_IP + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_TRAVEL + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_WEATHER + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_CLOCK + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_CURRENCY + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_ADVISORY + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_INTERNET + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_SPEECH + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_DICTIONARY + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_TRANSLATE + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_LOCATION + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_OCR + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_OBJECT + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_CHAT + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_SOS + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_CONVERT + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_TRANSLATE_2 + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_RENT + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_LOCAL + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_FOOD + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_RESERVE_1 + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_RESERVE_2 + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_RESERVE_3 + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_RESERVE_4 + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_RESERVE_5 + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_RESERVE_6 + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_RESERVE_7 + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_RESERVE_8 + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_RESERVE_9 + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_RESERVE_10 + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_RESERVE_11 + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_RESERVE_12 + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_RESERVE_13 + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_RESERVE_14 + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_RESERVE_15 + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_RESERVE_16 + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_RESERVE_17 + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_RESERVE_18 + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_RESERVE_19 + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_RESERVE_20 + " TEXT , "
                + LocationModel.LocationEntry.COLUMN_LATITUDE + " DOUBLE , "
                + LocationModel.LocationEntry.COLUMN_LONGITUDE + " DOUBLE );";

        db.execSQL(SQL_CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LocationModel.LocationEntry.TABLE_NAME);
        onCreate(db);
    }

    public static AibabelDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new AibabelDBHelper(context.getApplicationContext());
        }
        return instance;
    }
}
