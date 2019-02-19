package com.aibabel.locationservice.utils;

import android.content.Context;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.service.JPushMessageReceiver;

/**
 * 作者：wuqinghua_fyt on 2018/12/25 10:00
 * 功能：
 * 版本：1.0
 */
public class JPushutils  extends JPushMessageReceiver {
    public static final String PROJECTNAME = "PB";//极光推送别名

    /**
     * 设置极光推送app别名
     * 用于给某特定用户推送消息。别名,可以近似地被认为,是用户帐号里的昵称 使用标签
     * 覆盖逻辑,而不是增量逻辑。即新的调用会覆盖之前的设置。
     *
     * @param alias
     */


    /**
     * 删除极光推送app别名
     */
    public static void deleteAlias(Context conn) {
        deleteJpushAlias(conn, 0x2);
    }

    /**
     * 获取极光推送app别名
     */
    public static void getAlias(Context conn) {
        getJpushAlias(conn, 0x3);
    }

    /**
     * 设置标签
     * 用于给某一群人推送消息。标签类似于博客里为文章打上 tag ,即为某资源分类。
     */
    public static void setTags(Context conn, final String[] Tags) {
        setJpushTags(conn, 0x4, Tags);
    }

    /**
     * 添加标签
     */
    public static void addTags(Context conn, final String[] Tags) {
        addJpushTags(conn, 0x5, Tags);
    }

    /**
     * 删除标签
     */
    public static void deleteTags(Context conn, final String[] Tags) {
        deleteJpushTags(conn, 0x6, Tags);
    }

    /**
     * 删除所有标签
     */
    public static void cleanTags(Context conn) {
        cleanJpushTags(conn, 0x7);
    }

    /**
     * 获取所有标签
     */
    public static void getAllTags(Context conn) {
        getAllJpushTags(conn, 0x8);
    }

    private static void setJpushAlias(Context conn, int sequence, final String alias) {
        JPushInterface.setAlias(conn, sequence, alias);
    }

    private static void deleteJpushAlias(Context conn, int sequence) {
        JPushInterface.deleteAlias(conn, sequence);
    }

    private static void getJpushAlias(Context conn, int sequence) {
        JPushInterface.getAlias(conn, sequence);
    }

    //设置标签
    private static void setJpushTags(Context conn, int sequence, final String[] Tags) {
        Set set = new HashSet<>();
        for (int i = 0; i < Tags.length; i++) {
            set.add(Tags[i]);
        }
        JPushInterface.setTags(conn, sequence, set);
    }

    private static void addJpushTags(Context conn, int sequence, final String[] Tags) {
        Set set = new HashSet<>();
        for (int i = 0; i < Tags.length; i++) {
            set.add(Tags[i]);
        }
        JPushInterface.addTags(conn, sequence, set);
    }

    private static void deleteJpushTags(Context conn, int sequence, final String[] Tags) {
        Set set = new HashSet<>();
        for (int i = 0; i < Tags.length; i++) {
            set.add(Tags[i]);
        }
        JPushInterface.deleteTags(conn, sequence, set);
    }

    private static void cleanJpushTags(Context conn, int sequence) {
        JPushInterface.cleanTags(conn, sequence);
    }

    private static void getAllJpushTags(Context conn, int sequence) {
        JPushInterface.getAllTags(conn, sequence);
    }

    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        switch (jPushMessage.getSequence()) {

        }
        super.onAliasOperatorResult(context, jPushMessage);
    }

    @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {

        super.onTagOperatorResult(context, jPushMessage);
    }
}
