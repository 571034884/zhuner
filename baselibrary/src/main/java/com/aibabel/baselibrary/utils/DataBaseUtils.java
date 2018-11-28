package com.aibabel.baselibrary.utils;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

/**
 * 作者：SunSH on 2018/11/13 16:47
 * 功能：数据库增删查改
 * 版本：1.0
 */
public class DataBaseUtils {

    /**
     * 增
     *
     * @param bean
     */
    public static void save(LitePalSupport bean) {
        bean.save();
    }

    /**
     * 通过id删
     *
     * @param clazz
     * @param id
     */
    public static void deleteBy(Class<LitePalSupport> clazz, long id) {
        LitePal.delete(clazz, id);
    }

    /**
     * 通过条件删
     *
     * @param clazz
     * @param conditions 可变长度，从第三个开始依次替换 第二个参数中的“？”
     */
    public static void deleteIf(Class<LitePalSupport> clazz, String... conditions) {
        LitePal.deleteAll(clazz, conditions);
    }

    /**
     * 通过条件删
     *
     * @param clazz
     * @param id
     */
    public static void queryBy(Class<LitePalSupport> clazz, long id) {
        LitePal.find(clazz, id);
    }

    public static void queryAll(Class<LitePalSupport> clazz) {
        LitePal.findAll(clazz);
    }

    /**
     * 通过条件查
     *
     * @param clazz
     * @param conditions 可变长度，从第三个开始依次替换 第二个参数中的“？”
     */
    public static void queryIf(Class<LitePalSupport> clazz, String... conditions) {
        LitePal.where(conditions).find(clazz);
    }

//    public static void updata(LitePalSupport bean) {
//
//        Album albumToUpdate = LitePal.find(Album.class, 1);
//        albumToUpdate.setPrice(20.99f); // raise the price
//        albumToUpdate.save();
//
//
//        Album albumToUpdate = new Album();
//        albumToUpdate.setPrice(20.99f); // raise the price
//        albumToUpdate.updateAll("name = ?", "album");
//    }

}
