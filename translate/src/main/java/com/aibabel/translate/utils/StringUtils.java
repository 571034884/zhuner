package com.aibabel.translate.utils;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONObject;

public class StringUtils {

    public static String getAsrJson(String from, String to, String asr, Context context) {


        String result = "";
        try {
            JSONObject jsonObject = new JSONObject();
//            id暂时没有意义，只是为了保证后台接口统一性
            jsonObject.put("id", CommonUtils.getSN());
            jsonObject.put("text", asr);
//            jsonObject.put("func", "1");
            jsonObject.put("flag", "2");
            jsonObject.put("dev", CommonUtils.getSN());
            jsonObject.put("f", from);
            jsonObject.put("t", to);
            jsonObject.put("av", CommonUtils.getVersionName(context));
            jsonObject.put("sv", CommonUtils.getSysVersion(context));
            jsonObject.put("intelinfo", CommonUtils.getNetworkType(context));
            jsonObject.put("issell", CommonUtils.getChildFlag());
            jsonObject.put("gps", CommonUtils.getGps(context));
            jsonObject.put("location", CommonUtils.getCountry(context));
            result = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;


    }


    public static String split(String text, String regex) {
        String result = "";
        if (TextUtils.isEmpty(text)) {
            return result;
        }
        if (TextUtils.isEmpty(regex)) {
            return text;
        }
        try {
            String[] results = text.split(regex);
            result = results[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * @param text
     * @return
     */
    public static String deleteNbsp(String text) {
        String result = "";
        if (TextUtils.isEmpty(text)) {
            return result;
        }
        try {
            result = text.replaceAll("\\s*", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * long去掉4位转int
     * @return
     */
    public static int long2Int() {

        String str = String.valueOf(System.currentTimeMillis());
        String r_str = str.substring(4);

        int res = Integer.parseInt(r_str);

        return res;


    }


}
