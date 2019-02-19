package com.aibabel.menu.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.aibabel.menu.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DBUtils {

    /**
     *du读取本地的地区数据库
             * @param dbfile
     * @return
             */
    public static SQLiteDatabase openDatabase(String dbfile) {
        try {
           File file = new File(dbfile);
            if (!file.exists()) {
//                InputStream is = context.getResources().openRawResource(R.raw.pca2);
//                FileOutputStream fos = new FileOutputStream(dbfile);
//                byte[] buffer = new byte[1024];
//                int count = 0;
//                while ((count = is.read(buffer)) > 0) {
//                    fos.write(buffer, 0, count);
//                    fos.flush();
//                }
//                fos.close();
//                is.close();
            }
            SQLiteDatabase  database = SQLiteDatabase.openOrCreateDatabase(dbfile, null);
            return database;
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 读取assets   db
     * @param mContext
     * @param dbName
     * @return
     */
    public static SQLiteDatabase DBManager(Context mContext,String dbName) {
        String dbPath = "/data/data/" + mContext.getPackageName() + "/databases/" + dbName;
        if (!new File(dbPath).exists()) {
            try {
                FileOutputStream out = new FileOutputStream(dbPath);
                InputStream in = mContext.getAssets().open(dbName);
                byte[] buffer = new byte[1024];
                int readBytes = 0;
                while ((readBytes = in.read(buffer)) != -1) out.write(buffer, 0, readBytes);
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return SQLiteDatabase.openOrCreateDatabase(dbPath, null);
    }



    public static  void  copyAssetsToSd(Context mContext,String dbName) {
        String dbPath = "/sdcard/offline/menu/" + dbName;
        if (!new File(dbPath).exists()) {
            try {
                FileOutputStream out = new FileOutputStream(dbPath);
                InputStream in = mContext.getAssets().open(dbName);
                byte[] buffer = new byte[1024];
                int readBytes = 0;
                while ((readBytes = in.read(buffer)) != -1) out.write(buffer, 0, readBytes);
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
