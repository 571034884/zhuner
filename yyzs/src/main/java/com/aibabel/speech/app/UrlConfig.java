package com.aibabel.speech.app;

/**
 * =====================================================================
 * <p>
 * 作者 : 张文颖
 * <p>
 * 时间: 2018/3/28
 * <p>
 * 描述: 后台地址以及端口号
 * <p>
 * =====================================================================
 */

public class UrlConfig {

    /**
     * 线上环境
     */
    public final static int ONLINE = 1;
    /**
     * 测试环境
     */
    public final static  int TEST = 2;
    public final static  int ON_SERVICE = ONLINE;
    public final static String HOST_URL = getHost();
    /**
     * 获取主域
     *
     * @return
     */
    private static String getHost() {
            switch (ON_SERVICE) {
                case TEST:
                    return "";
                case ONLINE:
                    return "";
                default:
                    return "";
            }

    }
    public static String URL_LEAST_FRIENDS = HOST_URL + "";// 最近聊天好友

}
