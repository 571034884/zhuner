package com.aibabel.menu.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.text.TextUtils;
import android.util.Log;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DetectUtil {

    /**
     * 判断某个activity是否在前台显示
     */
    public static boolean isForeground(Activity activity) {
        return isForeground(activity, activity.getClass().getName());
    }
    /**
     * 判断某个activity是否在前台显示
     */
    public static boolean isForeground(Context con,Class activity) {
        return isForeground(con, activity.getName());
    }
    /**
     * 判断某个界面是否在前台,返回true，为显示,否则不是
     */
    public static boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className))
            return false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName()))
                return true;
        }
        return false;
    }

    /**
     * 判断某个界面是否在前台,返回true，为显示,否则不是
     */
    public static boolean isForeground(Activity context, String className) {
        if (context == null || TextUtils.isEmpty(className))
            return false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName()))
                return true;
        }
        return false;
    }

    /**
     * 判断服务是否在运行中
     * @param context 即为Context对象
     * @param serviceName 即为Service的全名
     * @return 是否在运行中
     */
    public static boolean isServiceRunning(Context context, String serviceName) {
        if (!TextUtils.isEmpty(serviceName) && context != null) {
            ActivityManager activityManager
                    = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ArrayList<ActivityManager.RunningServiceInfo> runningServiceInfoList
                    = (ArrayList<ActivityManager.RunningServiceInfo>) activityManager.getRunningServices(100);
            for (Iterator<ActivityManager.RunningServiceInfo> iterator = runningServiceInfoList.iterator(); iterator.hasNext();) {
                ActivityManager.RunningServiceInfo runningServiceInfo = (ActivityManager.RunningServiceInfo) iterator.next();
                if (serviceName.equals(runningServiceInfo.service.getClassName().toString())) {
                    return true;
                }
            }
        } else {
            return false;
        }
        return false;
    }

    ////获取已安装应用的 uid，-1 表示未安装此应用或程序异常
    public static int getPackageUid(Context context, String packageName) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(packageName, 0);
            if (applicationInfo != null) {
                Logger.d(applicationInfo.uid);
                return applicationInfo.uid;
            }
        } catch (Exception e) {
            return -1;
        }
        return -1;
    }
    public static boolean isProcessRunning(Context context, int uid) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServiceInfos = am.getRunningServices(200);
        if (runningServiceInfos.size() > 0) {
            for (ActivityManager.RunningServiceInfo appProcess : runningServiceInfos){
                if (uid == appProcess.uid) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 判断APP是否在前台
     * @param context
     * @param packname
     * @return
     */
    public static boolean isAppInForeground(Context context,String packname) {
        try{

        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            Log.e("hjs","topActivity="+topActivity.toString());
            if (topActivity.getPackageName().equals(packname)) {
                return true;
            }
        }}catch (Exception e){

        }
        return false;
    }


//    /**
//     * 返回app运行状态
//     *
//     * @param context
//     *            一个context
//     * @param packageName
//     *            要判断应用的包名
//     * @return int 1:前台 2:后台 0:不存在
//     */
//    public static int isAppAlive(Context context, String packageName) {
//        ActivityManager activityManager = (ActivityManager) context
//                .getSystemService(Context.ACTIVITY_SERVICE);
//        List<RunningTaskInfo> listInfos = activityManager.getRunningAppProcesses();
//        // 判断程序是否在栈顶
//        if (listInfos.get(0)..topActivity.getPackageName().equals(packageName)) {
//            return 1;
//        } else {
//            // 判断程序是否在栈里
//            for (ActivityManager.RunningTaskInfo info : listInfos) {
//                if (info.topActivity.getPackageName().equals(packageName)) {
//                    return 2;
//                }
//            }
//            return 0;// 栈里找不到，返回3
//        }
//    }

    /**
     * 判断进程是否运行
     *
     * @param context
     * @param proessName 应用程序的主进程名一般为包名
     * @return
     */
    public static boolean isProessRunning(Context context, String proessName) {
        boolean isRunning = false;
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> lists = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : lists) {
            if (info.processName.equals(proessName)) {
                isRunning = true;
            }
        }
        return isRunning;
    }
}
