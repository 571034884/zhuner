package com.aibabel.menu.util;

import android.app.ActivityManager;
import android.content.Context;

import java.lang.reflect.Method;
import java.util.List;

public class KillAppUtil {

    /**
     *  判断进程是否存活
     */
    public static boolean isProcessExist(Context context, int pid) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> lists ;
        if (am != null) {
            lists = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo appProcess : lists) {
                if (appProcess.pid == pid) {
                    LogUtil.e("exist pid");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * android.permission.FORCE_STOP_PACKAGES
     * @param context
     * @param packageName
     */
    public static void closeWPS(Context context,String packageName) {
        try {
            ActivityManager m = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
            Method method = m.getClass().getMethod("forceStopPackage", String.class);
            method.setAccessible(true);
            method.invoke(m, packageName);
            //finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closePackage(Context context, String packageName) {
        try {
            ActivityManager mAm = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            mAm.killBackgroundProcesses(packageName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
