package com.aibabel.menu.util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import android.util.Log;

import com.aibabel.menu.R;

import java.util.ArrayList;
import java.util.List;

public class AppStatusUtils {


    //当前应用是否处于前台
    public static boolean isForeground(Context context) {
        if (context != null) {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
            String currentPackageName = cn.getPackageName();
            if (!TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(context.getPackageName())) {
                return true;
            }
            return false;
        }
        return false;
    }

    public static boolean isAppOnForeground(Context context) {
        ActivityManager  mActivityManager = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));

       String mPackageName =context. getPackageName();
        List<ActivityManager.RunningTaskInfo> tasksInfo = mActivityManager.getRunningTasks(1);
        if (tasksInfo.size() > 0) {
            L.e("top Activity = "
                    + tasksInfo.get(0).topActivity.getPackageName());
            // 应用程序位于堆栈的顶层
            if (mPackageName.equals(tasksInfo.get(0).topActivity
                    .getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public  static boolean isAppForeground(Context context) {
        ActivityManager activityManager =
                (ActivityManager)context.getSystemService(Service.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfoList =
                activityManager.getRunningAppProcesses();
        if (runningAppProcessInfoList == null) {
            Log.d("isAppForeground","runningAppProcessInfoList is null!");
            return false;
        }

        for(ActivityManager.RunningAppProcessInfo processInfo : runningAppProcessInfoList) {
            if (processInfo.processName.equals(context.getPackageName())
                    &&(processInfo.importance ==
                    ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND)) {
                return true;
            }
        }
        return false;
    }


    public static boolean isBackground(Context context) {    ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    Log.i("后台", appProcess.processName);
                    return true;
                }else{
                    Log.i("前台", appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 调起应用  没有启动直接启动   启动过直接调起
     * @param context
     * @param packageName
     * @return
     */
    public static Intent getAppOpenIntentByPackageName(Context context, String packageName) {
        String mainAct = null;
        PackageManager pkgMag = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_NEW_TASK);

        @SuppressLint("WrongConstant") List<ResolveInfo> list = pkgMag.queryIntentActivities(intent,
                PackageManager.GET_ACTIVITIES);
        for (int i = 0; i < list.size(); i++) {
            ResolveInfo info = list.get(i);
            if (info.activityInfo.packageName.equals(packageName)) {
                mainAct = info.activityInfo.name;
                break;
            }
        }
        if (TextUtils.isEmpty(mainAct)) {
            return null;
        }
        intent.setComponent(new ComponentName(packageName, mainAct));
        return intent;
    }


    //判断当前应用是否是debug状态
    public static boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }



    public static List getLauncherPackageName(Context context) {
        List packageNames = new ArrayList();
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
//        final ResolveInfo res = context.getPackageManager().resolveActivity(intent, 0);
        List<ResolveInfo> resolveInfo = context.getPackageManager().queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        for(ResolveInfo ri : resolveInfo){
            packageNames.add(ri.activityInfo.packageName);
            Log.i("================", "packageName =" + ri.activityInfo.packageName);
        }
        if(packageNames == null || packageNames.size() == 0){
            return null;
        }else{
            return packageNames;
        }
    }


    public static boolean isLauncher(Context context) {
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> rti = mActivityManager.getRunningTasks(1);
        String topPackageName = rti.get(0).topActivity.getPackageName();
        List launcherName = getLauncherPackageName(context);
        Log.e("=========", "topPackageName =" + topPackageName);
        Log.e("==========", "launcherName =" + launcherName);
        if (launcherName != null && launcherName.size() != 0) {
            for (int i = 0; i < launcherName.size(); i ++) {
                if (launcherName.get(i) != null && launcherName.get(i).equals(topPackageName)) {
                    return true;
                }
            }
        }
        return false;
    }


    public static boolean getTopApp(Context mContext) {
//        String[] arr={"com.aibabel.menu.MainActivity","com.aibabel.menu.MenuActivity","com.aibabel.menu.SearchActivity"};
        String lockAppName = "com.aibabel.menu.MainActivity";
        String topActivityName = "";
        ActivityManager am = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = am
                .getRunningTasks(1);
        if (runningTasks != null && !runningTasks.isEmpty()) {
            ActivityManager.RunningTaskInfo taskInfo = runningTasks.get(0);
            topActivityName = taskInfo.topActivity.getClassName();
        }

         /*   if (arr[0].equals(topActivityName)||arr[1].equals(topActivityName)||arr[2].equals(topActivityName)) {
                return true;
            }*/

        if (lockAppName.equals(topActivityName)) {
            return true;
        }

        return false;
    }


}
