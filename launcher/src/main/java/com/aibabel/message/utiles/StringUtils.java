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
        if (mmkv.decodeBool(Constant.EM_SUPPORT, false)) {
            isSupported = true;
        }
        return isSupported;
    }


    /**
     * 是否支持微领队
     *
     * @return
     */

    public static void setSupport(int isNeed) {

        MMKV mmkv = MMKV.defaultMMKV();
        if (isNeed == 1) {
            mmkv.encode(Constant.EM_SUPPORT, false);
        } else {
            mmkv.encode(Constant.EM_SUPPORT, true);
        }
    }

}
