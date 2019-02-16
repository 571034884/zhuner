package com.aibabel.travel.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.aibabel.travel.activity.CountryActivity;
import com.aibabel.travel.bean.CountryData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.AccessMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * =====================================================================
 * <p>
 * 作者 : 张文颖
 * <p>
 * 时间: 2018/4/2
 * <p>
 * 描述:
 * <p>
 * =====================================================================
 */

public class JsonUtils {

    public static List<CountryData> getCountryList(Context context) {
        List<CountryData> list = new ArrayList<>();
        list = FastJsonUtil.changeJsonToList(getJson("world.json", context), CountryData.class);
        return list;
    }


    public static CountryData getCountry(Context context) {
        CountryData bean = new CountryData();
        bean = FastJsonUtil.changeJsonToBean(getJson("world.json", context), CountryData.class);
        return bean;
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


}
