package com.dommy.qrcode.util;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexFile;

/**
 * Created by root on 18-7-31.
 */

public class SystemPropertiesProxy {
    private static final String TAG = "SystemPropertiesInvoke";
    private static Method getLongMethod = null;
    private static Method getBooleanMethod = null;

    public static long getLong(final String key, final long def) {
        try {
            if (getLongMethod == null) {
                getLongMethod = Class.forName("android.os.SystemProperties")
                        .getMethod("getLong", String.class, long.class);
            }

            return ((Long) getLongMethod.invoke(null, key, def)).longValue();
        } catch (Exception e) {
            Log.e(TAG, "Platform error: " + e.toString());
            return def;
        }
    }

    public static boolean getBoolean(final String key, final boolean def) {
        try {
            if (getBooleanMethod == null) {
                getBooleanMethod = Class.forName("android.os.SystemProperties")
                        .getMethod("getBoolean", String.class,boolean.class);
            }

            //Log.i(TAG,"getBoolean:"+"key:"+key+" def:"+def);
            //Log.i(TAG,"getBoolean:"+getBooleanMethod.invoke(null, key, def));

            return (Boolean)getBooleanMethod.invoke(null, key, def);
        } catch (Exception e) {
            Log.e(TAG, "Platform error: " + e.toString());
            return def;
        }
    }
    
}
