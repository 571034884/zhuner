package com.aibabel.download.offline.util;

import android.text.TextUtils;

import com.aibabel.baselibrary.utils.DeviceUtils;
import com.aibabel.download.offline.app.MyApplication;
import com.spreada.utils.chinese.ZHConverter;

public class StringUtils {
    /**
     * 判定为台湾版本，并且系统为繁体
     *
     * @param text
     * @return
     */
    public static String setCH2TW(String text) {
        String result = "";
        try {
//            if (TextUtils.equals(BaseApplication.sysLanguage, "zh_TW")) {
            if (TextUtils.equals(CommonUtils.getLocal(null), "zh_TW") && DeviceUtils.getSystem() == DeviceUtils.System.FLY_TAIWAN) {
                ZHConverter converter2 = ZHConverter.getInstance(ZHConverter.TRADITIONAL);
                String traditionalStr = converter2.convert(text);
                result = traditionalStr;
            } else {
                result = text;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
