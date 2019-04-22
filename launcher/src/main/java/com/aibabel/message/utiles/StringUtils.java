package com.aibabel.message.utiles;

import android.text.TextUtils;

import com.aibabel.baselibrary.sphelper.SPHelper;
import com.tencent.mmkv.MMKV;

public class StringUtils {
    /**
     * 是否支持微领队
     *
     * @return
     */

    public static boolean isSupported() {
        boolean isSupported = false;
        MMKV mmkv = MMKV.defaultMMKV();
        if (mmkv.decodeBool("isSupported", false)) {
            isSupported = true;
        }
        return isSupported;
    }
}
