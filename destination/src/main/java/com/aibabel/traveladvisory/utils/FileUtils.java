package com.aibabel.traveladvisory.utils;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：SunSH on 2018/9/10 11:32
 * 功能：
 * 版本：1.0
 */
public class FileUtils {
    /**
     * 根据文件路径获取文件
     * Return the file by path.
     *
     * @param filePath The path of file.
     * @return the file
     */
    public static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    /**
     * 判断文件是否存在
     * Return whether the file exists.
     *
     * @param filePath The path of file.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isFileExists(final String filePath) {
        return isFileExists(getFileByPath(filePath));
    }

    /**
     * 判断文件是否存在
     * Return whether the file exists.
     *
     * @param file The file.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isFileExists(final File file) {
        return file != null && file.exists();
    }

    /**
     * isWhitespace() 方法用于判断指定字符是否为空白字符，空白符包含：空格、tab键、换行符。
     * @param s
     * @return
     */
    public static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static Map<String, String> getFilesAllName(String path) {
        long time = System.currentTimeMillis();
        File file = new File(OffLineUtil.offlinePath + path);
        File[] files = file.listFiles();
        if (files == null) {
            Log.e("error", "空目录");
            return null;
        }
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < files.length; i++) {
            map.put(files[i].getName(), path);
        }
        Log.e("aaaaarun: ", OffLineUtil.getTimeFromLong(System.currentTimeMillis() - time));
        return map;
    }

    public static Map<String, Integer> getFilesAllName(String path, int state) {
        File file = new File(OffLineUtil.offlinePath + path);
        File[] files = file.listFiles();
        if (files == null) {
            Log.e("error", "空目录");
            return null;
        }
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < files.length; i++) {
            map.put(files[i].getName(), state);
        }
        return map;
    }

}
