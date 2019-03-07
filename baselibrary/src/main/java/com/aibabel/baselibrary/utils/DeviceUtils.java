package com.aibabel.baselibrary.utils;

import android.os.Build;

/**
 *  author :liuwei
 *  des:  判断系统版本工具类
 */
public class DeviceUtils {
    public enum System {
        PRO_LEASE,  // pro租赁版
        PRO_SELL,// pro销售版
        FLY_CHINA,// fly中国大陆版
        FLY_Mobile,// fly中国移动版
        FLY_INTERNATIONAL,// fly国际版
        GO// GO版


    }
    private static System system;

    /**
     * 获取系统版本
     * @return  System枚举类型
     */
    public static System getSystem() {
        if (system == null) {
            String[] nodes = getSystemVersionName().split("-");

            String systemSeries = nodes[0];
            if (systemSeries.startsWith("PM")) {
                system = System.GO;
            } else if (systemSeries.startsWith("PH")) {

                switch (nodes[1]) {
                    case "L01"://  l01 国内版  05国际版  0607订制
                        system = System.FLY_CHINA;
                        break;
                    case "L05"://  l01 国内版  05国际版  0607订制
                        system = System.FLY_INTERNATIONAL;
                        break;
                    case "L08"://  l01 国内版  05国际版  0607订制
                        system = System.FLY_Mobile;
                        break;
                    default:
                        system = System.FLY_CHINA;
                        break;
                }
            } else if (systemSeries.startsWith("PL")) {
                if (nodes[2].startsWith("L")) {
                    system = System.PRO_LEASE;
                } else {
                    system = System.PRO_SELL;
                }
            }


        }
        return system;

    }

    public static String getSystemVersionName() {

        return Build.DISPLAY;
    }



}
