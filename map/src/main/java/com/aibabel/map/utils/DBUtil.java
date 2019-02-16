package com.aibabel.map.utils;

import com.aibabel.map.bean.CollectStarBean;
import com.aibabel.map.bean.search.AddressResult;

import org.litepal.LitePal;

import java.util.List;

/**
 * Created by fytworks on 2018/12/7.
 */

public class DBUtil {


    /**
     * 判断数据是否被存储    进行双重判断
     * @param uid   百度唯一标识
     * @param name  POI信息名称
     * @return
     */
    public static boolean isSaveALL(String uid, String name){
        List<AddressResult> all = LitePal.findAll(AddressResult.class, true);
        for (AddressResult result : all){
            String name1 = result.getName();
            String uid1 = result.getUid();
            if (uid.equals(uid1) && name.equals(name1)){
                return false;
            }
        }
        return true;
    }

    /**
     * 判断数据是否被存储    进行双重判断
     * @param uid   百度唯一标识
     * @param name  POI信息名称
     * @return
     */
    public static boolean isSaveCollect(String uid, String name){
        List<CollectStarBean> all = LitePal.findAll(CollectStarBean.class);
        for (CollectStarBean result : all){
            String name1 = result.getName();
            String uid1 = result.getUid();
            if (uid.equals(uid1) && name.equals(name1)){
                return false;
            }
        }
        return true;
    }

}
