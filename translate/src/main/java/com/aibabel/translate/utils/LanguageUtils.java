package com.aibabel.translate.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import com.aibabel.translate.R;
import com.aibabel.translate.bean.LanguageBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * =====================================================================
 * <p>
 * 作者 : 张文颖
 * <p>
 * 时间: 2018/4/2
 * <p>
 * 描述: 语言工具类
 * <p>
 * =====================================================================
 */

public class LanguageUtils {


    /**
     * 获取语言列表
     *
     * @param context
     * @return
     */
    public static List<LanguageBean> getLanList(Context context) {
        String country = Locale.getDefault().getCountry();
        String language = Locale.getDefault().getLanguage();
        List<LanguageBean> list = null;
        String code = "";
        switch (language) {
            case "zh":
                if (TextUtils.equals("CN", country)) {
                    list = FastJsonUtil.changeJsonToList(getJson("lang_data_zh.json", context), LanguageBean.class);
                } else {
                    list = FastJsonUtil.changeJsonToList(getJson("lang_data_zh_tw.json", context), LanguageBean.class);
                }
                break;
            case "en":
                list = FastJsonUtil.changeJsonToList(getJson("lang_data_en.json", context), LanguageBean.class);
                break;
//            case "TW":
//                list = FastJsonUtil.changeJsonToList(getJson("lang_zh_tw.json",context),LanBean.class);
//                break;
            case "ja":
                list = FastJsonUtil.changeJsonToList(getJson("lang_data_ja.json", context), LanguageBean.class);
                break;
            case "ko":
                list = FastJsonUtil.changeJsonToList(getJson("lang_data_ko.json", context), LanguageBean.class);
                break;
            default:
                list = FastJsonUtil.changeJsonToList(getJson("lang_data_en.json", context), LanguageBean.class);
                break;
        }

        return list;
    }


    /**
     * 获取assets文件夹中json文件
     *
     * @param fileName
     * @param context
     * @return
     */
    private static String getJson(String fileName, Context context) {

        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static Map<String, String> getCurrentUp(Context context) {
        String name = SharePrefUtil.getString(context, Constant.LAN_UP, "英语(美国)");
        String code = SharePrefUtil.getString(context, Constant.CODE_UP, "en_usa");
        if (!CommonUtils.isAvailable()) {
            if (code.contains("ch_")) {
                code = "ch_ch";
            } else if (code.contains("en_")) {
                code = "en";
            } else if (code.contains("fr")) {
                code = "fr";
            }
        }
        String alert = SharePrefUtil.getString(context, Constant.ALERT_UP, context.getResources().getString(R.string.hint_up));
        String offline = SharePrefUtil.getString(context, Constant.OFFLINE_UP, "offline");
        Map<String, String> map = new HashMap<>();
        map.put(Constant.LAN_UP, name);
        map.put(Constant.CODE_UP, code);
        map.put(Constant.ALERT_UP, alert);
        map.put(Constant.OFFLINE_UP, offline);
        return map;
    }

    public static Map<String, String> getCurrentDown(Context context) {
        String name = SharePrefUtil.getString(context, Constant.LAN_DOWN, "汉语");
        String code = SharePrefUtil.getString(context, Constant.CODE_DOWN, "ch_ch");
        if (!CommonUtils.isAvailable()) {
            if (code.contains("ch_")) {
                code = "ch_ch";
            } else if (code.contains("en_")) {
                code = "en";
            } else if (code.contains("fr")) {
                code = "fr";
            }
        }
        String alert = SharePrefUtil.getString(context, Constant.ALERT_DOWN, context.getResources().getString(R.string.hint_down));
        String offline = SharePrefUtil.getString(context, Constant.OFFLINE_DOWN, "离线");
        Map<String, String> map = new HashMap<>();
        map.put(Constant.LAN_DOWN, name);
        map.put(Constant.CODE_DOWN, code);
        map.put(Constant.ALERT_DOWN, alert);
        map.put(Constant.OFFLINE_DOWN, offline);
        return map;
    }


//    public static boolean isSupportOffline(String code1, String code2) {
//        if (CommonUtils.isAvailable()) {
//            return false;
//        }
//        if (TextUtils.isEmpty(code1) || TextUtils.isEmpty(code2)) {
//            return false;
//        }
//        if (TextUtils.equals(code1, code2)) {
//            return false;
//        }
//
//        if (code1.contains("en") && code2.contains("en")) {
//            return false;
//        }
//
//        if (code1.contains("fr") && code2.contains("fr")) {
//            return false;
//        }
//
//
//        if (BaseApplication.offline_lan.contains(code1)) {
//            return true;
//        } else {
//            if (code1.contains("en") || code1.contains("fr")) {
//                return true;
//            }
//        }
//        if (BaseApplication.offline_lan.contains(code2)) {
//            return true;
//        } else {
//            if (code2.contains("en") || code2.contains("fr")) {
//                return true;
//            }
//        }
//        return true;
//    }

    public static String getOffline(String code) {

        String name = "";

        if (TextUtils.equals(code, "ch_ch")) {
            name = "离线";
        }
        if (TextUtils.equals(code, "jpa")) {
            name = "オフライン";
        }

        if (TextUtils.equals(code, "kor")) {
            name = "오프라인";
        }
        if (TextUtils.equals(code, "rus")) {
            name = "Офлайновый";
        }
        if (code.contains("en")) {
            name = "offline";
        }
        if (code.contains("fr")) {
            name = "";
        }
        return name;
    }


    public static String getNameByCode(String code, Context context) {
        List<LanguageBean> list = getLanList(context);
        String name = "";
        for (LanguageBean bean : list) {
            if (TextUtils.equals(bean.getLang_code(), code)) {
                name = bean.getName();
            }

            if (null != bean.getChild() && bean.getChild().size() > 0) {
                for (int i = 0; i < bean.getChild().size(); i++) {
                    if (TextUtils.equals(bean.getChild().get(i).getVar_code(), code)) {
                        name = bean.getChild().get(i).getVar();
                    }
                }

            }
        }

        return name;
    }


    /**
     * 获取粤语不支持的语言列表
     *
     * @return
     */
    public static List<String> getNotSupport() {
        List<String> list = new ArrayList<>();
        list.add("kk-KZ");
        list.add("ka-GE");
        list.add("am-ET");
        list.add("az-AZ");
        list.add("ne-NP");
        list.add("lo-LA");
        list.add("km-KH");
        list.add("gl");
        list.add("ja");
        list.add("zu");
        return list;
    }


    /**
     * 获取粤语不支持语言列表(不支持的,显示不同的状态)
     *
     * @return
     */
    public static List<LanguageBean> getSpecailList(List<LanguageBean> lans) {
        for(LanguageBean bean:lans){
                if(getNotSupport().contains(bean.getLang_code())){
                    bean.setNotSupport(true);
                }
            }
        return lans;
    }

    /**
     * 获取10种不支持粤语语言列表(不支持的,显示不同的状态)
     *
     * @return
     */
    public static List<LanguageBean> getList(List<LanguageBean> lans) {
        for(LanguageBean bean:lans){
            if (TextUtils.equals(bean.getLang_code(), "ch_yy") || TextUtils.equals(bean.getLang_code(), "ch_hk_j")) {
                bean.setNotSupport(true);
                for(LanguageBean.ChildBean childBean : bean.getChild()){
                    childBean.setNotSupport(true);
                }
            }

        }
        return lans;
    }

}
