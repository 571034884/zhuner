package com.example.root.testhuaping.service;

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by root on 18-8-1.
 */

public class Getsystem_info {
    public static String getSN_SN() {
        String sn="0000000000000000";
        try {
            Class clz = Class.forName("android.os.SystemProperties");
            Method method = clz.getMethod("get", String.class,String.class);
            sn = (String) method.invoke(clz,"gsm.serial", "0000000000000000");
            sn.trim();
            if (sn.indexOf(" ") != -1) {
                sn = sn.substring(0, sn.indexOf(" "));
            }

            Log.e("==============","sn="+sn);

        } catch (ClassNotFoundException e) {
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            Log.e("IllegalArgum",e.getMessage());
        } catch (IndexOutOfBoundsException e){
            Log.e("IndexO",e.getMessage());
        }

        return sn;
    }
}
