package com.aibabel.ocr.utils;

import android.app.job.JobParameters;
import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import com.aibabel.ocr.bean.LanBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

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

    public static List<LanBean> getLanList(Context context){
        String country = Locale.getDefault().getCountry();
        String language = Locale.getDefault().getLanguage();
        List<LanBean> list =null;
        switch (language){
            case "zh":
                if(TextUtils.equals("CN",country)){
                list = FastJsonUtil.changeJsonToList(getJson("lang_zh.json",context),LanBean.class);
                }else {
                list = FastJsonUtil.changeJsonToList(getJson("lang_zh_tw.json",context),LanBean.class);
                }
                break;
            case "en":
                list = FastJsonUtil.changeJsonToList(getJson("lang_en.json",context),LanBean.class);
                break;
//            case "TW":
//                list = FastJsonUtil.changeJsonToList(getJson("lang_zh_tw.json",context),LanBean.class);
//                break;
            case "ja":
                list = FastJsonUtil.changeJsonToList(getJson("lang_jp.json",context),LanBean.class);
                break;
            case "ko":
                list = FastJsonUtil.changeJsonToList(getJson("lang_kr.json",context),LanBean.class);
                break;
            default:
                list = FastJsonUtil.changeJsonToList(getJson("lang_en.json",context),LanBean.class);
                break;
        }

         return list;
    }


    public static String getNameByCode(String code,Context context){
        List<LanBean> list = getLanList(context);
        String name = "";
        for (LanBean bean : list){
            if(TextUtils.equals(bean.getLang_code(),code)){
                name = bean.getName();
            }
        }

        return name;
    }


    public static String getRightNameByCode(String code,Context context){
        List<LanBean> list = getLanRightList(context);
//        if(TextUtils.equals(code,"jpa")||TextUtils.equals(code,"jpa_v")){
//            code = "jpa";
//        }
        String name = "";
        for (LanBean bean : list){
            if(TextUtils.equals(bean.getLang_code(),code)){
                name = bean.getName();
            }
        }

        return name;
    }

    public static List<LanBean> getLanRightList(Context context){
        String country = Locale.getDefault().getCountry();
        String language = Locale.getDefault().getLanguage();
        List<LanBean> list =null;
        switch (language){
            case "zh":
                if(TextUtils.equals("CN",country)) {
                    list = FastJsonUtil.changeJsonToList(getJson("lang_zh_right.json", context), LanBean.class);
                }else{
                    list = FastJsonUtil.changeJsonToList(getJson("lang_zh_tw_right.json",context),LanBean.class);
                }
                break;
            case "en":
                list = FastJsonUtil.changeJsonToList(getJson("lang_en_right.json",context),LanBean.class);
                break;
//            case "TW":
//                list = FastJsonUtil.changeJsonToList(getJson("lang_zh_tw_right.json",context),LanBean.class);
//                break;
            case "ja":
                list = FastJsonUtil.changeJsonToList(getJson("lang_jp_right.json",context),LanBean.class);
                break;
            case "ko":
                list = FastJsonUtil.changeJsonToList(getJson("lang_kr_right.json",context),LanBean.class);
                break;
            default:
                list = FastJsonUtil.changeJsonToList(getJson("lang_en_right.json",context),LanBean.class);
                break;
        }

        return list;
    }

    /**
     * 获取assets文件夹中json文件
     * @param fileName
     * @param context
     * @return
     */
    private static String getJson(String fileName,Context context) {

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

    public static String getCurrentLan(){
        String country = Locale.getDefault().getCountry();
        String name ="ch_ch";
        switch (country){
            case "CN":
                name="中文";
                break;
            case "US":
                name="English";
                break;
            case "TW":
                name="中文";
                break;
            case "JP":
                name="日本語";
                break;
            case "KR":
                name="한국어";
                break;
            default:
                break;
        }

        return name;
    }

    public static String getCurrentCode(){
        String country = Locale.getDefault().getCountry();
        String code ="en";
        switch (country){
            case "CN":
                code ="ch_ch";
                break;
            case "US":
                code ="en";
                break;
            case "TW":
                code ="ch_ch";
                break;
            case "JP":
                code ="jpa";
                break;
            case "KR":
                code ="kor";
                break;
            default:
                break;
        }

        return code;
    }


    /**
     * 通过code获取语言名称
     * @param context
     * @param code
     * @return
     */
    public static String getLanByCode(Context context,String code){
        String lan = "";
        List<LanBean> list= getLanList(context);

        for(LanBean bean :list){
            if(TextUtils.equals(code,bean.getLang_code())){
                lan = bean.getName();
            }
        }

        return lan;


    }

    /**
     * 通过code获取语言名称
     * @param context
     * @param code
     * @return
     */
    public static String getRightLanByCode(Context context,String code){
        String lan = "";

        if(TextUtils.equals("jpa",code)||TextUtils.equals("jpa_v",code)){
            code = "jpa";
        }
        List<LanBean> list= getLanRightList(context);

        for(LanBean bean :list){
            if(TextUtils.equals(code,bean.getLang_code())){
                lan = bean.getName();
            }
        }

        return lan;


    }

    public static String getLang(){
        String country = Locale.getDefault().getCountry();
        String language = Locale.getDefault().getLanguage();

        if(TextUtils.equals("TW",country)){
            language = "zh_TW";
        }

        if(TextUtils.isEmpty(language)){
            language = "zh";
        }

        return language;
    }

}
