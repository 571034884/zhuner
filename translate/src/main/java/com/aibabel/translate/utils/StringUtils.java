package com.aibabel.translate.utils;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import proto.Dls;

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

    /**
     * 识别请求必要参数
     * @param id
     * @param from
     * @param to
     * @param context
     * @return
     */
    public static Dls.SpeechInfo getSpeechInfo(int id, String from, String to, Context context) {

        String uuid = UUID.randomUUID().toString();
        String md5 = "id=" + id + "&context=" + uuid + "&secret=aibabel";
        Dls.SpeechInfo speechInfo = Dls.SpeechInfo.newBuilder()
                .setId(String.valueOf(id))
                .setF("ch_ch")
                .setT("en_ch")
                .setSpeed("normal")
                .setDev(CommonUtils.getSN())
                .setAudio("mp3")
                .setFlag("0")
                .setFunc("3")
                .setGps(CommonUtils.getGps(context))
                .setLocation(CommonUtils.getCountry(context))
                .setIssell(CommonUtils.getChildFlag())
                .setSv(CommonUtils.getSysVersion(context))
                .setAv(CommonUtils.getVersionName(context))
                .setIntelinfo(CommonUtils.getNetworkType(context))
                .setContext(uuid)
                .setSign(md5(md5))
                .build();
        return speechInfo;

    }


    /**
     * 格式化日期
     * @param time
     * @return
     */
    public static String formatDate(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(time);
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }


    /**
     * md5对字符串进行加密
     *
     * @param string
     * @return
     */
    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
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


    // 判断一个字符是否是中文
    public static boolean isChinese(char c) {
        return c >= 0x4E00 && c <= 0x9FA5;// 根据字节码判断
    }

    // 判断一个字符串是否含有中文
    public static boolean isChinese(String str) {
        if (str == null)
            return false;
        for (char c : str.toCharArray()) {
            if (isChinese(c))
                return true;// 有一个中文字符就返回
        }
        return false;
    }

}
