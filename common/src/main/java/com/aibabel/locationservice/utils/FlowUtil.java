package com.aibabel.locationservice.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.text.format.Formatter;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class FlowUtil {


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public static void initMtkDoubleSim(Context context) {
        try {

            List<SubscriptionInfo> list = SubscriptionManager.from(context).getActiveSubscriptionInfoList();
            //获取内置卡的Iccid
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getSimSlotIndex() == 1) {
                    //内置卡
                    Constants.CARD_1 = list.get(i).getIccId();
                    Log.e("iccid_1", Constants.CARD_1);
                } else if (list.get(i).getSimSlotIndex() == 0) {
                    //外置卡
                    Constants.CARD_0 = list.get(i).getIccId();
                    Log.e("iccid_0", Constants.CARD_0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("eeee", e.toString());
        }


    }


    /**
     * 判断数据流量开关是否打开
     *
     * @param context
     * @return
     */
    public static boolean isMobileEnabled(Context context) {
        try {
            Method getMobileDataEnabledMethod = ConnectivityManager.class.getDeclaredMethod("getMobileDataEnabled");

            getMobileDataEnabledMethod.setAccessible(true);

            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            return (Boolean) getMobileDataEnabledMethod.invoke(connectivityManager);

        } catch (Exception e) {

            e.printStackTrace();

        }
        return true;
    }


    /*
     * 判断是哪张卡 在消耗流量
     * 0 卡槽1 = 外置卡
     * 1 卡槽2 = 内置卡
     * */
    @SuppressLint("MissingPermission")
    public static Integer getDefaultDataSubId(Context context) {
        Integer id = -1;
        try {
            SubscriptionManager sm = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                sm = SubscriptionManager.from(context.getApplicationContext());
                Method getSubId = sm.getClass().getMethod("getDefaultDataSubId");
                if (getSubId != null) {
                    id = (int) getSubId.invoke(sm);
                }
            }
        } catch (NoSuchMethodException e) {
            try {
                SubscriptionManager sm = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    sm = SubscriptionManager.from(context.getApplicationContext());
                    Method getSubId = sm.getClass().getMethod("getDefaultDataSubscrptionId");
                    if (getSubId != null) {
                        id = (int) getSubId.invoke(sm);
                    }
                }
            } catch (NoSuchMethodException e1) {
//
                try {
                    SubscriptionManager sm = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                        sm = SubscriptionManager.from(context.getApplicationContext());
                        Method getSubId = sm.getClass().getMethod("getDefaultDataPhoneId");
//            Method getSubId = Class.forName("android.telephony.SubscriptionManager").getDeclaredMethod("getSubId", new Class[]{Integer.TYPE});
                        if (getSubId != null) {
                            id = (int) getSubId.invoke(sm);
                            Log.v("", (int) getSubId.invoke(sm) + "");
                        }
                    }
                } catch (NoSuchMethodException e2) {
                    e.printStackTrace();
                } catch (IllegalAccessException e2) {
                    e.printStackTrace();
                } catch (InvocationTargetException e2) {
                    e.printStackTrace();
                }
            } catch (IllegalAccessException e1) {
                e.printStackTrace();
            } catch (InvocationTargetException e1) {
                e.printStackTrace();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return id;

    }


    /**
     * 获取手机内部可用存储空间
     *
     * @param context
     * @return 以M, G为单位的容量
     */
    public static String getAvailableInternalMemorySize(Context context, long flow) {

        return Formatter.formatFileSize(context, flow);
    }


}
