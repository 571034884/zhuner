package com.aibabel.statisticalserver;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * ===================================================================================
 *
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
                //todo aidl传
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
        } else {
            sp.edit().putInt(key, (Integer) value);
        }
    }

    /**
     * 取值
     *
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public static Object get(String key, Object defaultValue) {
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

}
