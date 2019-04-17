package com.aibabel.baselibrary.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SIMUtils {
    private static final String SIM_STATE = "getSimState";

    /**
     * 获取相应卡的状态
     *
     * @param slotIdx:0(sim1),1(sim2)
     * @return true:使用中；false:未使用中
     */
    public static boolean getSimStateBySlotIdx(Context context, int slotIdx) {
        boolean isReady = false;
        Object getSimState = getSimByMethod(context, SIM_STATE, slotIdx);
        if (getSimState != null) {
            int simState = Integer.parseInt(getSimState.toString());
            if ((simState != TelephonyManager.SIM_STATE_ABSENT) && (simState != TelephonyManager.SIM_STATE_UNKNOWN)) {
                isReady = true;
            }
        }
        return isReady;
    }

    /**
     * 通过slotid获取相应卡的subid
     *
     * @param context
     * @param slotId
     * @return
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    public static int getSubidBySlotId(Context context, int slotId) {
        SubscriptionManager subscriptionManager = (SubscriptionManager) context.getSystemService(
                Context.TELEPHONY_SUBSCRIPTION_SERVICE);

        try {
            Class<?> telephonyClass = Class.forName(subscriptionManager.getClass().getName());
            Class<?>[] parameter = new Class[1];
            parameter[0] = int.class;
            Method getSimState = telephonyClass.getMethod("getSubId", parameter);
            Object[] obParameter = new Object[1];
            obParameter[0] = slotId;
            Object ob_phone = getSimState.invoke(subscriptionManager, obParameter);
            if (ob_phone != null) {
//                Log.d(LOG_TAG, "slotId:" + slotId + " subid: " + ((int[]) ob_phone)[0]);
                return ((int[]) ob_phone)[0];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    /**
     * 通过slotid获取相应卡的subid
     *
     * @param context
     * @param
     * @return
     */
    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static int getSubidBySlotId(Context context) {
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
      return   telephony.getDataNetworkType();

    }

    /**
     * 通过反射调用相应的方法
     */
    public static Object getSimByMethod(Context context, String method, int param) {
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            Class<?> telephonyClass = Class.forName(telephony.getClass().getName());
            Class<?>[] parameter = new Class[1];
            parameter[0] = int.class;
            Method getSimState = telephonyClass.getMethod(method, parameter);
            Object[] obParameter = new Object[1];
            obParameter[0] = param;
            Object ob_phone = getSimState.invoke(telephony, obParameter);
            if (ob_phone != null) {
                return ob_phone;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean isInUse(Context context, int slotId) {
        int subId = getSubidBySlotId(context, slotId);
        if (subId == -1) return false;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);


        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            try {
                Method getDataNetworkType;
                getDataNetworkType = TelephonyManager.class.getMethod("getDataNetworkType", int.class);


                return 0 != (int) getDataNetworkType.invoke(telephonyManager, subId);
            } catch (Exception e) {
                e.printStackTrace();

            }

        }
        return false;
//        int type= (int) RefInvoker.invokeMethod(telephonyManager,TelephonyManager.class.getName(), "getDataNetworkType",new Class[]{int.class}, new Object[]{subId});

    }
    /*
     * 判断是哪张卡 在消耗流量
     * 0 卡槽1 = 外置卡
     * 1 卡槽2 = 内置卡
     * */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("MissingPermission")
    public static Integer getDefaultDataSubId(Context context) {
        int slotIndex=0;
        try {
        SubscriptionManager subscriptionManager = (SubscriptionManager) context.getSystemService(
                Context.TELEPHONY_SUBSCRIPTION_SERVICE);
        SubscriptionInfo mSubscriptionInfo= (SubscriptionInfo) SubscriptionManager.class.getMethod("getDefaultDataSubscriptionInfo",null).invoke(subscriptionManager,null);
        if (mSubscriptionInfo!=null){
            slotIndex=mSubscriptionInfo.getSimSlotIndex();
        }


//            Method method=SubscriptionManager.class.getMethod("getSlotIndex",int.class);
//            slotIndex= (int) method.invoke(subscriptionManager,subId);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return  slotIndex;
    }

}
