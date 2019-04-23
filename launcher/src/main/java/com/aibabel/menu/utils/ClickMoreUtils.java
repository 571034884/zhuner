package com.aibabel.menu.utils;

/**
 * Created by fytworks on 2019/4/22.
 */

public class ClickMoreUtils {
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;


    /**
     * 两次点击按钮之间的点击间隔不能少于1000毫秒
     */
    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            lastClickTime = curClickTime;
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }
}
