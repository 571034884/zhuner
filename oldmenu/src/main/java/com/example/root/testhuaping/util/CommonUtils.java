package com.example.root.testhuaping.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.List;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by fytworks on 2019/3/12.
 */

public class CommonUtils {

    /**
     *获取IMEI
     */
    public static  String getIMEI(Context mContext) {


        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(TELEPHONY_SERVICE);

        TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "";
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return telephonyManager.getDeviceId(0)+"";
        }
        return "";
    }


    /**
     * 获取本机SN 设备识别码
     *
     * @return
     */
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

    /**
     * 获取ICCID
     * @return
     */
    public static String getICCID(Context mContext) {
        try {
            List<SubscriptionInfo> list = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                list = SubscriptionManager.from(mContext).getActiveSubscriptionInfoList();
            }
            if (null == list || list.size() == 0) {

            }


            for (int i = 0; i < list.size(); i++) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    if (list.get(i).getSimSlotIndex() == 1) {
                        String iccid = list.get(i).getIccId();
                        return iccid;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }


}
