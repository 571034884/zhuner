package com.aibabel.ocr.utils;


import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FastJsonUtil {


    /**
     * 将JSONArray 转成实体List<calss>
     *
     * @param jsonArray
     * @param descClass
     * @param <T>
     * @return
     */
    public static <T> List<T> getListFromArray(JSONArray jsonArray, Class<T> descClass) {
        List<T> descList = new ArrayList<T>();
        if (jsonArray == null) {
            return null;
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            T desc = null;
            try {
                desc = new Gson().fromJson(jsonArray.getJSONObject(i).toString(), descClass);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            descList.add(desc);
        }
        return descList;
    }

    /**
     * 从JSONObject中通过指定key获取到JSONArray
     *
     * @param job
     * @param key
     * @return
     */
    public static JSONArray getArrayFromObject(JSONObject job, String key) {
        JSONArray gArray = null;
        try {
            gArray = job.getJSONArray(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return gArray;
    }

    /**
     * 将JSONObject转换成实体业务bean
     *
     * @param obj
     * @param descClass
     * @param <T>
     * @return
     */
    public static <T> T getModeFromJson(Object obj, Class<T> descClass) {
        JSONObject job = null;
        try {
            job = (JSONObject) obj;
        } catch (ClassCastException ce) {
            ce.printStackTrace();
        }
        return new Gson().fromJson(job.toString(), descClass);
    }

    /*===================================以下是fastjson的实现方式===================================*/


    public static <T> T changeJsonToBean(String jsonString, Class<T> cls) {
        T t = null;
        try {
            t = JSON.parseObject(jsonString, cls);
        } catch (Exception e) {
            // TODO: handle exception
        }

        return t;
    }

    public static <T> List<T> changeJsonToList(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            list = JSON.parseArray(jsonString, cls);
        } catch (Exception e) {
        }
        return list;
    }

    public static <T> String changListToString(List<T> list) {

        return JSON.toJSONString(list, true);
    }

    public static String changObjectToString(Object object) {

        return JSON.toJSONString(object, true);
    }

}
