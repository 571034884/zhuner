package com.aibabel.menu.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by hjs
 * 工具包
 */
public class Tools {

    public static final int MSG_OK = 1;

    /**
     * 反序列化json字符串
     *
     * @param jsonResult json
     * @param clz        clz
     * @return bean
     */
    public static <T> T json2Bean(String jsonResult, Class<T> clz) {
        Gson gson = new Gson();
        return gson.fromJson(jsonResult, clz);
    }

    /**
     * 序列化json对象
     *
     * @param obj 对象
     * @return json
     */
    public static String bean2Json(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    public static <T> String list2Json(ArrayList<T> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<T>>() {
        }.getType();
        return gson.toJson(list, type);
    }


    /**
     * json字符串 转换为 ArrayList类型的数组
     *
     * @param json json 字符串
     * @return list集合
     */
    public static <T> ArrayList<T> json2ListData(String json, Class<T> clz) {
        ArrayList<T> list = new ArrayList<T>();
        Gson gson = new Gson();
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String str = obj.toString();
                T t = gson.fromJson(str, clz);
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 合并列表
     *
     * @param originList 原始列表
     * @param addList    新增列表
     * @param <T>        泛型类型
     * @return 合并之后的列表
     */
    public static <T> ArrayList<T> mergeList(ArrayList<T> originList, ArrayList<T> addList) {
        if (isListEmpty(originList)) {
            return addList;
        }
        if (!isListEmpty(addList)) {
            originList.addAll(addList);
        }
        return originList;
    }

    public static boolean isListEmpty(ArrayList list) {
        return list == null || list.size() <= 0;
    }

    /**
     * 封装获取drawable的方法
     *
     * @param context 上下文
     * @param id      资源id
     * @return drawable 对象
     */
    public static Drawable getDrawable(Context context, int id) {
        return ContextCompat.getDrawable(context, id);
    }

    public static int getColor(Context context, int id) {
        return ContextCompat.getColor(context, id);
    }

    /**
     * 获取校准过的时间
     * @return 校准过的时间戳
     */
    /**
     * 获取校准过的时间
     *
     * @return 校准过的时间戳
     */
    public static long getCorrectTimeStamp() {
        Calendar calender = Calendar.getInstance();
        calender.setTime(new Date());
        int year = calender.get(Calendar.YEAR);
        int month = calender.get(Calendar.MONTH) + 1;
        int day = calender.get(Calendar.DATE);
        int hour = calender.get(Calendar.HOUR_OF_DAY);
        int minute = calender.get(Calendar.MINUTE);
        String time = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":00";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        try {
            Date date = simpleDateFormat.parse(time);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 获取真实时间
     *
     * @param time 时间戳
     * @return 时间  yyyy-MM-dd HH:mm:ss 格式的
     */
    public static String getRealTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return format.format(time);
    }

    public static String getRealtime() {
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            String format = formatter.format(calendar.getTime());
            return format;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }


    /**
     * 判断当前网络是否可用
     *
     * @param context 上下文环境
     * @return true 可用 false 不可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        boolean isOk = false;
        if (info != null) {
            isOk = info.isAvailable();
        }
        return isOk;
    }
//    public static void main(String args[]){
//        String res  =  "{\"code\":1,\"msg\":\"\",\"body\":{\"oid\":\"123\",\"uid\":\"345\",\"channelName\":\"aaa\",\"uname\":\"\",\"sn\":\"\",\"f\":\"\",\"t\":\"\",\"d\":\"\",\"isLock\":1,\"at\":24}}";
//
//
//        SyncOrder so =  json2Bean(res,SyncOrder.class);
//        System.out.println(so.getBody().getOid());
//
//        SyncOrder synorder = JSON.parseObject(res,SyncOrder.class);
//        System.out.println(synorder.getBody().getOid());
//
//    }
}