package com.aibabel.download.offline.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.aibabel.download.offline.app.MyApplication;
import com.aibabel.download.offline.util.L;

public class OfflineDBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 6;
    private static final String DB_NAME = "offline.db";
    public static final String TABLE_NAME_OFFLINE_LAN = "offline_lan_list";

    private OfflineDBHelper offlineDBHelper;
    SQLiteDatabase db = getWritableDatabase();
    public OfflineDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        L.e("================sqLiteDatabase onCreate");
        // create table
        // 1.成功 2.正在下载 3.正在安装 4.暂停 5.失败  -1已卸载        10预装 11正在解压  12解压失败 13正在卸载 14，卸载失败  15正在删除  16删除失败   99服务器有
        String sql = "create table if not exists " + TABLE_NAME_OFFLINE_LAN
                + " (" +
                "Id text primary key, " +
                "name text," +
                "size text, " +
                "progress text," +
                "status text," +
                "down_url text," +
                "from_path text ," +
                "to_path text," +
                "down_start_time integer," +
                "copy_start_time integer," +
                "uninstall_start_time integer,"+
                "lan_name text, " +
                "lan_code text," +
                "need_again_unzip text," +
                "down_zip_filename text," +
                "version_code text)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME_OFFLINE_LAN;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }


    public void insert(ContentValues contentValues) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.insert(TABLE_NAME_OFFLINE_LAN, null, contentValues);
        } catch (Exception e) {

        }

    }

    public void delete(String down_url) {
        SQLiteDatabase db = getWritableDatabase();
//        Cursor cursor= db.query(TABLE_NAME_OFFLINE_LAN,null,null,null,null,null,null);
//        if (cursor.moveToFirst()) {
//            String url = cursor.getString(cursor.getColumnIndex("down_url"));
//            if (url.equals(down_url)) {
//                String url = cursor.getString(cursor.getColumnIndex("down_url"));
//            }
//        }
        db.delete(TABLE_NAME_OFFLINE_LAN, "down_url=?", new String[]{down_url});

    }

    public void deleteId(String id) {
        SQLiteDatabase db = getWritableDatabase();
//        Cursor cursor= db.query(TABLE_NAME_OFFLINE_LAN,null,null,null,null,null,null);
//        if (cursor.moveToFirst()) {
//            String url = cursor.getString(cursor.getColumnIndex("down_url"));
//            if (url.equals(down_url)) {
//                String url = cursor.getString(cursor.getColumnIndex("down_url"));
//            }
//        }
        db.delete(TABLE_NAME_OFFLINE_LAN, "Id=?", new String[]{id});

    }




    public Cursor queryCursor() {
        return getWritableDatabase().query(TABLE_NAME_OFFLINE_LAN, null, null, null, null, null, null);
    }

    public boolean queryID(String id) {

        Cursor cursor = getWritableDatabase().query(TABLE_NAME_OFFLINE_LAN, null, "Id=?", new String[]{id}, null, null, null);
        try{
            if (cursor!=null&&cursor.moveToFirst()) {

                return true;
            } else {
                return false;
            }
        }finally {
            if (cursor!=null) {
                cursor.close();
            }
        }

    }

    public String queryStatus(String id) {
        Cursor cursor = getWritableDatabase().query(TABLE_NAME_OFFLINE_LAN, null, "Id=?", new String[]{id}, null, null, null);
        try{

            if (cursor!=null&&cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndex("status"));

            } else {
                return "";
            }
        }finally {
            if (cursor!=null) {
                cursor.close();
            }

        }


    }

    public String queryProgess(String id) {
        Cursor cursor = getWritableDatabase().query(TABLE_NAME_OFFLINE_LAN, null, "Id=?", new String[]{id}, null, null, null);
        try{
            if (cursor!=null&&cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndex("progress"));

            } else {
                return "";
            }
        }finally {
            if (cursor!=null) {
                cursor.close();
            }
        }


    }

    /**
     * 查询
     * @param selId 条件 id
     * @param filed 字段名
     * @return
     */
    public String queryFiled(String selId,String filed ) {
        Cursor cursor = getWritableDatabase().query(TABLE_NAME_OFFLINE_LAN, null, "Id=?", new String[]{selId}, null, null, null);
        try{
            if (cursor!=null&&cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndex(filed));

            } else {
                return "";
            }

        }finally {
            if (cursor!=null) {
                cursor.close();
            }
        }

    }

    /**
     * 查询
     * @param selId 条件 id
     * @param filed 字段名
     * @return
     */
    public long queryFiledLong(String selId,String filed ) {
        Cursor cursor = getWritableDatabase().query(TABLE_NAME_OFFLINE_LAN, null, "Id=?", new String[]{selId}, null, null, null);

        try {
            if (cursor!=null&&cursor.moveToFirst()) {
                return cursor.getLong(cursor.getColumnIndex(filed));

            } else {
                return 2000000000000l;
            }
        }finally {
            if (cursor!=null) {
                cursor.close();
            }
        }

    }

    /**
     * 查询
     * @param selId 条件 id
     * @param filed 字段名
     * @param val 更新实际值
     * @return
     */
    public void updateFiled(String selId,String filed ,String val) {

        try {
            ContentValues contentValues1 = new ContentValues();
            if (filed.equals("copy_start_time")) {
                contentValues1.put(filed, Long.valueOf(val));
            } else {
                contentValues1.put(filed, val);
            }

            db.update(TABLE_NAME_OFFLINE_LAN,contentValues1,"Id=?",new String[]{selId});
        } catch (Exception e) {

        }

    }
    public void updateAll(String id,ContentValues contentValues1) {
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_NAME_OFFLINE_LAN,contentValues1,"Id=?",new String[]{id});

    }

    public void updateStatus(String down_url,String status) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put("status", status);
        db.update(TABLE_NAME_OFFLINE_LAN,contentValues1,"down_url=?",new String[]{down_url});

    }
    public void updateStatusId(String id,String status) {
        try {
            String _status=queryStatus(id);
            if (_status.equals("1")) {
                return;
            }
            if (status.equals("3")) {
                String prog= queryFiled(id,"progress");
                String res=prog.replace("%","");
                if (!TextUtils.isEmpty(res)) {
                    if (Integer.valueOf(res) < 90) {
                        return;

                    }
                } else {
                    return;
                }
            }
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues1 = new ContentValues();
            contentValues1.put("status", status);
            db.update(TABLE_NAME_OFFLINE_LAN,contentValues1,"Id=?",new String[]{id});
        } catch (Exception e) {

        }



    }


    public void updateUninstallStatusId(String id,String status) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put("status", status);
        db.update(TABLE_NAME_OFFLINE_LAN,contentValues1,"Id=?",new String[]{id});


    }

    public void updateProgress(String down_url,String progress) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues1 = new ContentValues();
        if (progress!=null&&!progress.equals("")) {
            String prog=progress.replace("%","");
            try {
                if (Integer.valueOf(prog) >= 100) {

                    return;
                } else {
                    contentValues1.put("progress", progress);
                    db.update(TABLE_NAME_OFFLINE_LAN,contentValues1,"down_url=?",new String[]{down_url});
                }

            } catch (Exception e) {

            }

        }

    }
    public void updateProgressId(String id,String progress) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues1 = new ContentValues();

        if (progress!=null&&!progress.equals("")) {
            String prog=progress.replace("%","");
            try {
                if (Integer.valueOf(prog) >100) {

                    updateStatusId(id,"5");
                    return;
                } else {
                    contentValues1.put("progress", progress);
                    db.update(TABLE_NAME_OFFLINE_LAN,contentValues1,"Id=?",new String[]{id});
                }

            } catch (Exception e) {

            }

        }
    }


    /**
     * 关机  处理数据状态
     *
     */
    public  void shutdownUPdateDB() {
        L.e("离线下载关机广播  处理文件状态=========================");
        Cursor cursor= queryCursor();
        try{

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String status=cursor.getString( cursor.getColumnIndex("status"));
                    if(status.equals("2")){
                        updateStatusId(cursor.getString(cursor.getColumnIndex("Id")), "5");

                    } else  if (status.equals("3")) {
                        updateStatusId(cursor.getString(cursor.getColumnIndex("Id")), "5");
                        L.e("关机二次解压："+cursor.getString(cursor.getColumnIndex("Id")));
                        MyApplication.dbHelper.updateFiled(cursor.getString(cursor.getColumnIndex("Id")), "need_again_unzip", "true");

                    } else if (status.equals("11")) {
                        updateStatusId(cursor.getString(cursor.getColumnIndex("Id")), "12");
                        L.e("关机正在安装变为失败："+cursor.getString(cursor.getColumnIndex("Id")));
                    } else if (status.equals("13")) {

                        updateStatusId(cursor.getString(cursor.getColumnIndex("Id")), "14");
                        L.e("关机正在卸载变为失败：" + cursor.getString(cursor.getColumnIndex("Id")));

                    } else if (status.equals("1")) {
                        MyApplication.dbHelper.updateFiled(cursor.getString(cursor.getColumnIndex("Id")), "need_again_unzip", "flase");
                    }

                } while (cursor.moveToNext());
            }

        }finally {
            cursor.close();
        }


    }


    /**
     * 预装json  韩国名字错误问题  解决
     */
    public void replaceKO() {

        if (queryID("ko_KR")) {
            updateFiled("ko_KR","lan_name","韩语（Korean）");
        }
    }

}
