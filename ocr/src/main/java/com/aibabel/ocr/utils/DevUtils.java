package com.aibabel.ocr.utils;

import android.content.Context;
import android.text.TextUtils;

public class DevUtils {


    public static String getSN() {
        String serialNum = android.os.Build.SERIAL;
        if(TextUtils.isEmpty(serialNum)){
            return "0000_ocr";
        }

        return serialNum;
    }

}
