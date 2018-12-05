package com.dommy.qrcode.util;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

public class DevUtils {


    public static String getSN(Context context) {
        String serialNum = android.os.Build.SERIAL;

        if(TextUtils.isEmpty(serialNum)){
            return "0000000000";
        }


        return serialNum;
    }

   /* public static Map<String,String> getImeiAndSn(Context context) {
android.os.Build.SERIAL
        Map<String,String> map = new HashMap<>();


        String sn = android.os.Build.SERIAL;
        String imei_1 = "";
        String imei_2 = "";

        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            imei_1 = tm.getImei(0);
            imei_2 = tm.getImei(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(TextUtils.isEmpty(sn)){
            sn = "0000000000";
        }

        if(TextUtils.isEmpty(imei_1)){
          imei_1= "imei_1";
        }
        if(TextUtils.isEmpty(imei_2)){
            imei_2= "imei_2";
        }
        map.put("sn",sn);
        map.put("imei_1",imei_1);
        map.put("imei_2",imei_2);

        return map;
    }*/

}
