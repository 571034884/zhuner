package com.aibabel.statisticalserver;

import android.content.Context;
import android.content.SharedPreferences;

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

    /**
     * 判断存储方式
     *
     * @param context
     * @param key
     * @param value
     */
    public static void put(Context context, String key, Object value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        if (value instanceof Boolean) {
            sp.edit().putBoolean(key, (Boolean) value).commit();
        } else if (value instanceof Integer) {
            sp.edit().putInt(key, (Integer) value).commit();
        } else if (value instanceof Float) {
            sp.edit().putFloat(key, (Float) value).commit();
        } else if (value instanceof Long) {
            sp.edit().putLong(key, (Long) value).commit();
        } else if (value instanceof String) {
            sp.edit().putString(key, (String) value).commit();
        }
    }


    /**
     * 判断获取方式
     *
     * @param context
     * @param key
     * @param defaultValue
     */
    public static Object get(Context context, String key, Object defaultValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
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
