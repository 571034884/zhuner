package com.aibabel.locationservice.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.aibabel.baselibrary.impl.IDataManager;
import com.aibabel.baselibrary.utils.XIPCUtils;
import com.aibabel.locationservice.R;
import com.aibabel.locationservice.service.LocationService;
import com.xuexiang.xipc.XIPC;
import com.xuexiang.xipc.core.channel.IPCListener;
import com.xuexiang.xipc.core.channel.IPCService;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;

import static com.xuexiang.xipc.XIPC.getContext;

public class CommonUtils {
    //    获取订单信息
    private static String orderNo = "";


    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param mContext
     * @param serviceName 是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public static boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }


    /**
     * 获取版本号名称
     *
     * @param context 上下文
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }


    /**
     * 判断网络是否可用
     *
     * @return true: 可用 false: 不可用
     */
    public static boolean isAvailable(Context context) {
        NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return info != null && info.isAvailable();
    }


//    /**
//     * 获取本机SN 设备识别码
//     *
//     * @return
//     */
//    public static String getSN() {
//        String serialNum = android.os.Build.SERIAL;
//        if (TextUtils.isEmpty(serialNum)) {
//            return "0000000000000000";
//        }
//        return serialNum;
//    }



    public static String getSN() {
        String sn="0000000000000000";
        try {
            Class clz = Class.forName("android.os.SystemProperties");
            Method method = clz.getMethod("get", String.class,String.class);
            sn = (String) method.invoke(clz,"gsm.serial", "0000000000000000");
            sn.trim();
            if (sn.indexOf(" ") != -1) {
                sn = sn.substring(0, sn.indexOf(" "));
            }
            Log.e("CommonUtils","sn="+sn);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("CommonUtils",e.getMessage());
        }

        return sn;
    }



    /*
     * 获取系统版本号 去除 “-” */

    public static String getDevice() {
        String result = "";
        String display = Build.DISPLAY;
        result = display.replace("-", "");
        Log.e("result", result);
        return result;
    }


    /**
     * 获取系统版本号 机器类型
     *
     * @return
     */
    public static String getDeviceFlag() {
        String result = "PH";
        String version = Build.DISPLAY;
        result = version.substring(0, 2);
        return result;
    }


    /**
     * 获取系统版本号
     *
     * @return
     */
    public static String getSystemFlag() {
        String result = "PL";
        result = Build.DISPLAY;
        return result;
    }


    /**
     * 获取租赁和销售版本，s 销售 、 l 租赁
     *
     * @return
     */
    public static String getChildFlag() {
        String version = "M";
        try {
            String display = Build.DISPLAY;
            version = display.substring(9, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;
    }


    public static int getRandom() {
        int random = (int) ((Math.random() * 9 + 1) * 1000);
        return random;
    }

    /**
     * 获取系统语言
     *
     * @return
     */
    public static String getLocal() {

        String country = Locale.getDefault().getCountry();
        String language = Locale.getDefault().getLanguage();
        String sl = "";

        switch (language) {
            case "zh":
                sl = language + "_" + country;
                break;
            case "en":
            case "ja":
            case "ko":
                sl = language;
                break;
            default:
                sl = "en";
                break;
        }
        return sl;
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

    /**
     * 获取声音Uri
     *
     * @param context
     * @return
     */
    public static Uri getSound(Context context) {
        return Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.sound);

    }

    /**
     * @方法说明:判断是否是移动网络
     * @方法名称:is4GNet
     * @param context
     * @return
     * @返回值:boolean
     */
    public static boolean is4GNet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }


   public static String getOrderNo(){
        try{
            XIPC.connectApp(getContext(), XIPCUtils.XIPC_MENU_NEW);
            XIPC.setIPCListener(new IPCListener() {
                @Override
                public void onIPCConnected(Class<? extends IPCService> service) {
                    IDataManager dm = XIPC.getInstance(IDataManager.class);
                    orderNo = dm.getString("order_oid");
                    Log.e("orderNo", orderNo);
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }


       return orderNo;
   }


}
