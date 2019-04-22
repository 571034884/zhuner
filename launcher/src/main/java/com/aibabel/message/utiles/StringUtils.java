package com.aibabel.message.utiles;

import android.text.TextUtils;

import com.aibabel.baselibrary.sphelper.SPHelper;

public class StringUtils {
    /**
     * 是否支持微领队
     *
     * @return
     */

    public static boolean isSupported() {
        boolean isSupported = false;
        String country = SPHelper.getString("country", "cn");
        if (TextUtils.equals(country, "jpa") || TextUtils.equals(country, "th")) {
            isSupported = true;
        }
        return isSupported;
    }
}
