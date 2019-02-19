package com.aibabel.translate.utils;


import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

public class FastJsonUtil {

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
