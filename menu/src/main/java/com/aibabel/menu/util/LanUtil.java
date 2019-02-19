package com.aibabel.menu.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import java.util.Locale;

public class LanUtil {


    //国际化
    private  static String[] lanArr = {"zh-CN", "zh-TW", "ja-JP", "ko-KR", "th-TH", "en-US"};

    /**
     * 支持的国际化的语言
     *
     * @param lan
     * @return
     */
    public static String[] cantansLan(String lan) {
        for (int i = 0; i < lanArr.length; i++) {
            if (lanArr[i].indexOf(lan) != -1) {
                return lanArr[i].split("-");
            }
        }
        return "en-US".split("-");
    }


    /**
     * 根据系统改变语言
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void setLan(Context mContext) {
        try {
            String lan = Locale.getDefault().getLanguage();
            if (lan.indexOf("zh") != -1) {
                lan = "zh-" + Locale.getDefault().getCountry();
            }
            L.e("获取的系统语言》》》》》》" + lan);
            String[] newLan = cantansLan(lan);
            Resources resources = mContext.getResources();// 获得res资源对象
            Configuration config = resources.getConfiguration();// 获得设置对象
            DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像
            Locale locale = new Locale(newLan[0], newLan[1]);
            config.setLocale(locale);
            resources.updateConfiguration(config, dm);

        } catch (Exception e) {
            Resources resources = mContext.getResources();// 获得res资源对象
            Configuration config = resources.getConfiguration();// 获得设置对象
            DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像
            Locale locale = new Locale("en", "US");
            config.setLocale(locale);
            resources.updateConfiguration(config, dm);
        }

    }

}
