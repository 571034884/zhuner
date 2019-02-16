package com.aibabel.map.utils;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringUtils {


    /**
     * 分割字符串
     * @param value
     * @param regex
     * @return
     */
    public static List<String> spliteByRegex(String value, String regex) {
        List<String> list = new ArrayList<>();
        if (TextUtils.isEmpty(value) || TextUtils.isEmpty(regex)) {
            return list;
        }

        String[] result = value.split(regex);
        list = Arrays.asList(result);

        return list;


    }


    /**
     * m转化成km
     * @param distance
     * @return
     */
    public static String getKm(int distance) {

        if (distance < 1000) {
            return distance + "m";
        }
        float result = Math.round((distance / 100d) / 10); //输出0.8

        return result + "km";

    }


    /**
     * 向textview上赋值，值为空时隐藏textview
     * @param view
     * @param text
     */
    public static void setText(TextView view , String text){
        if(!TextUtils.isEmpty(text)){
            view.setText(text);
        }else{
            view.setVisibility(View.GONE);
        }

    }


//    public static String getLocationWhere(int where){
//        switch (where){
//            case BDLocation.LOCATION_WHERE_IN_CN:
//
//                break;
//            case BDLocation.LOCATION_WHERE_OUT_CN:
//
//                break;
//            case BDLocation.LOCATION_WHERE_UNKNOW:
//
//                break;
//
//        }
//    }



}
