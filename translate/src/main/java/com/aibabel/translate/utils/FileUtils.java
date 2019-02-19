package com.aibabel.translate.utils;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件工具类
 */
public class FileUtils {

    private static File tempMp3;


    public static String saveFile(byte[] bytes, Context context) {
        if(null==bytes){
            SharePrefUtil.saveString(context, "mp3_1", "");
            return "";
        }
        String path = "";
        try {
            String name = "zhuner.mp3";
            tempMp3 = File.createTempFile(name, "mp3", context.getCacheDir());
            tempMp3.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tempMp3);
            fos.write(bytes);
            fos.close();
            path = tempMp3.getPath();
//            playMp3_1(tempMp3.getPath(),context);
            SharePrefUtil.saveString(context, "mp3_1", tempMp3.getPath());
        } catch (IOException ex) {
            Log.e("File文件报错", "");
            String s = ex.toString();
            ex.printStackTrace();
        }
        return path;
    }

    /**
     * 删除文件
     */
    public static void deleteCacheFile() {
        try {
            if (null != tempMp3 && tempMp3.isFile()) {
                tempMp3.delete();
            }
        } catch (Exception e) {

        }

    }


}
