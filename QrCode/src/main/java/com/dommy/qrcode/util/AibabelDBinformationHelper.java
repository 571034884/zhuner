package com.dommy.qrcode.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class AibabelDBinformationHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "aibabelinformation.db";
    private static  AibabelDBinformationHelper instance;

    public AibabelDBinformationHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_CONTACT_TABLE = "CREATE TABLE " + LocationinformationModel.LocationEntry.TABLE_NAME + "( "
                + LocationinformationModel.LocationEntry._ID + " TEXT PRIMARY KEY,"
                + LocationinformationModel.LocationEntry.DEST_COUNTRY + " TEXT ,"
                + LocationinformationModel.LocationEntry.DEST_CITY + " TEXT ,"
                + LocationinformationModel.LocationEntry.DEST_PROVINCE + " TEXT ,"
                + LocationinformationModel.LocationEntry.START_TIME + " TEXT ,"
                + LocationinformationModel.LocationEntry.END_TIME + " TEXT ,"
                + LocationinformationModel.LocationEntry.DD_TIME + " TEXT ,"
                + LocationinformationModel.LocationEntry.JH_TIME + " TEXT)";

        db.execSQL(SQL_CREATE_CONTACT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LocationinformationModel.LocationEntry.TABLE_NAME);
        onCreate(db);
        Log.d("wzf", "db="+db );
    }

    public static AibabelDBinformationHelper getInstance(Context context) {
        if (instance == null) {
            instance = new AibabelDBinformationHelper(context.getApplicationContext());
        }
        return instance;
    }
}
