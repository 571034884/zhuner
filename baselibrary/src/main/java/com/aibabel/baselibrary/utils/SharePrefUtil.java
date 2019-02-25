package com.aibabel.baselibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.RemoteException;

import com.aibabel.aidlaar.StatisticsManager;

import java.util.Map;

/**
 * ===================================================================================
 *
 * @作者: 张文颖
 * @创建时间: 2018-6-5 上午11:36:53
 * @描述: SharePreferences操作工具类
 * @修改时间: ====================================================================================
 * <p>
 * 2019年2月22日09:48:01 修改
 * @孙舒恒 改写成 instanceof 写法
 */

public class SharePrefUtil {

    private static String TAG = SharePrefUtil.class.getSimpleName();
    private final static String SP_NAME = "config";
    private static SharedPreferences sp;
    //存储方式
    public static final String MODE_SELF = "self";
    public static final String MODE_OTHER = "other";
    public static final String MODE_DEFAULT = MODE_OTHER;


    /**
     * 使用默认方式存储
     *
     * @param context
     * @param key
     * @param value
     */
    public static void put(Context context, String key, Object value) {
        put(context, key, value, MODE_DEFAULT);
    }

    /**
     * 使用默认方式获取
     *
     * @param context
     * @param key
     * @param defaultValue
     */
    public static void get(Context context, String key, Object defaultValue) {
        get(context, key, defaultValue, MODE_DEFAULT);
    }

    /**
     * 判断存储方式
     *
     * @param context
     * @param key
     * @param value
     * @param mode
     */
    public static void put(Context context, String key, Object value, String mode) {
        switch (mode) {
            case MODE_SELF:
                putBySelf(context, key, value);
                break;
            case MODE_OTHER:
                StatisticsManager.getInstance(context).saveSharePreference(key, value.toString());
                break;
        }
    }

    /**
     * 存值
     *
     * @param key
     * @param value
     */
    public static void putBySelf(Context context, String key, Object value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        if (value instanceof Boolean) {
            sp.edit().putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            sp.edit().putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            sp.edit().putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            sp.edit().putLong(key, (Long) value);
        } else if (value instanceof String) {
            sp.edit().putString(key, (String) value);
        }
    }

    /**
     * 判断获取方式
     *
     * @param context
     * @param key
     * @param defaultValue
     * @param mode
     */
    public static void get(Context context, String key, Object defaultValue, String mode) {
        switch (mode) {
            case MODE_SELF:
                getBySelf(key, defaultValue);
                break;
            case MODE_OTHER:
                if (defaultValue instanceof Boolean) {
                    StatisticsManager.getInstance(context).getBooleanSP(key, Boolean.valueOf(defaultValue.toString()));
                } else if (defaultValue instanceof Integer) {
                    StatisticsManager.getInstance(context).getIntSP(key, Integer.valueOf(defaultValue.toString()));
                } else if (defaultValue instanceof Float) {
                    StatisticsManager.getInstance(context).getFloatSP(key, Float.valueOf(defaultValue.toString()));
                } else if (defaultValue instanceof Long) {
                    StatisticsManager.getInstance(context).getLongSP(key, Long.valueOf(defaultValue.toString()));
                } else if (defaultValue instanceof String) {
                    StatisticsManager.getInstance(context).getStringSP(key, defaultValue.toString());
                }
                break;
        }
    }

    /**
     * 取值
     *
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public static Object getBySelf(String key, Object defaultValue) {
        if (defaultValue instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return sp.getInt(key, (Integer) defaultValue);
        } else if (defaultValue instanceof Float) {
            return sp.getFloat(key, (Float) defaultValue);
        } else if (defaultValue instanceof Long) {
            return sp.getLong(key, (Long) defaultValue);
        } else {
            return sp.getString(key, "");
        }
    }

    /**
     * 移除某个key值已经对应的值
     */
    public void remove(Context context, String key) {
        //todo 需要验证是否正确
        sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().remove(key);
        sp.edit().commit();
    }

    /**
     * 清除所有数据
     */
    public void clear(Context context) {
        //todo 需要验证是否正确
        sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().clear();
        sp.edit().commit();
    }

    /**
     * 查询某个key是否存在
     */
    public Boolean contain(String key) {
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     */
    public Map<String, ?> getAll() {
        return sp.getAll();
    }


    /**
     * 保存布尔值
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveBoolean(Context context, String key, boolean value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * 保存字符串
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveString(Context context, String key, String value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().putString(key, value).commit();

    }

//    /**
//     * 清除所有的数据
//     *
//     * @param context
//     */
//    public static void clear(Context context) {
//        if (sp == null)
//            sp = context.getSharedPreferences(SP_NAME, 0);
//        sp.edit().clear().commit();
//    }

    /**
     * 保存long型
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveLong(Context context, String key, long value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().putLong(key, value).commit();
    }

    /**
     * 保存int型
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveInt(Context context, String key, int value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().putInt(key, value).commit();
    }

    /**
     * 保存float型
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveFloat(Context context, String key, float value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().putFloat(key, value).commit();
    }

    /**
     * 获取字符值
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static String getString(Context context, String key, String defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        return sp.getString(key, defValue);
    }

    /**
     * 获取int值
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static int getInt(Context context, String key, int defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        return sp.getInt(key, defValue);
    }

    /**
     * 获取long值
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static long getLong(Context context, String key, long defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        return sp.getLong(key, defValue);
    }

    /**
     * 获取float值
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static float getFloat(Context context, String key, float defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        return sp.getFloat(key, defValue);
    }

    /**
     * 获取布尔值
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        return sp.getBoolean(key, defValue);
    }

    /**
     * 清除指定数据
     *
     * @param context
     * @param key
     */

    public static void removeByKey(Context context, String key) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().remove(key);
        sp.edit().commit();
    }

}
