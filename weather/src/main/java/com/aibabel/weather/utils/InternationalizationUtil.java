package com.aibabel.weather.utils;


import com.aibabel.weather.bean.CurrencyBean;
import com.aibabel.weather.app.Constant;

import java.util.Locale;

/**
 * 作者：SunSH on 2018/5/21 19:45
 * 功能：国际化赋值
 * 版本：1.0
 */
public class InternationalizationUtil {

    public static String getNativeLanguage() {
        return Locale.getDefault().getCountry();
    }

    public static String getCurrentText(CurrencyBean.ChildBean childBean) {
        String string = "";
        switch (Constant.NATIVE_LANGUAGE) {
            case "CN":
                string = childBean.getZh_ch();
                break;
            case "TW":
                string = childBean.getZh_tw();
                break;
            case "US":
                string = childBean.getEn();
                break;
            case "IP":
                string = childBean.getJp();
                break;
            case "KR":
                string = childBean.getKo();
                break;
        }
        return string;
    }
}
