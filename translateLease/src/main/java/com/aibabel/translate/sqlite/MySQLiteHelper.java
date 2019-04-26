package com.aibabel.translate.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 作者：wuqinghua_fyt on 2018/10/15 17:33
 * 功能：
 * 版本：1.0
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    /**
     * id 自增长
     * from 识别语种
     * mt 翻译语种
     * asr 识别文本 string
     * mt 翻译文本 string
     * tts 语音录音
     * eng 英文助译
     * time 时间  int
     */
    public static final String CREATE_TABLE= "create table t_record("
            +"id integer primary key autoincrement,"
            +"lan_from text,"
            +"lan_to text,"
            +"lan_from_code text,"
            +"lan_to_code text,"
            +"asr text,"
            +"mt text,"
            +"tts text,"
            +"eng text,"
            +"time integer)";

    /**
     *
     * @param context
     * @param name 数据库名
     * @param factory
     * @param version  版本号 每次更改 需+1
     */
    public MySQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
