package com.aibabel.map.utils;

/**
 * Created by fytworks on 2018/11/29.
 */

public class ColorTextUtil {

    public static String changeTextColor(String str, String change) {
        String s = str.replaceAll(change, "<font color = 'red'>" + change + "</font>");
        return s;
    }
}
