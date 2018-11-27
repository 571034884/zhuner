package com.aibabel.currencyconversion.utils;

import android.content.Context;

import com.aibabel.currencyconversion.app.Constant;
import com.aibabel.currencyconversion.bean.CurrencyBean;
import com.aibabel.currencyconversion.bean.NewCurrencyBean;

import java.util.Locale;

/**
 * 作者：SunSH on 2018/5/21 19:45
 * 功能：国际化赋值
 * 版本：1.0
 */
public class InternationalizationUtil {

    public static String getNativeLanguage() {
        return Locale.getDefault().getLanguage() + "-" + Locale.getDefault().getCountry();
    }

    public static String getCurrentText(NewCurrencyBean childBean) {
        String string = "";
        switch (Constant.NATIVE_LANGUAGE) {
            case "CN":
            case "zh-CN":
                string = childBean.getZh_ch();
                break;
            case "TW":
            case "zh-TW":
                string = childBean.getZh_tw();
                break;
            case "US":
            case "en-US":
            case "en-":
                string = childBean.getEn();
                break;
            case "JP":
            case "ja-":
            case "ja-JP":
                string = childBean.getJp();
                break;
            case "KR":
            case "ko-":
            case "ko-KR":
                string = childBean.getKo();
                break;
        }
        return string;
    }
}
