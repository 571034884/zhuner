package com.aibabel.travel.app;

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
     * 测试环境
     */
    public final static int TEST = 1;
    public final static String  DEFAULT_HOST = "http://abroad.api.joner.aibabel.cn:7001/v1/tourguide/";
//    public final static String  DEFAULT_HOST = "http://api.joner.aibabel.cn:7001/gowithtommy?cmd=";
    /**
     * 线上环境
     */
    public final static int ONLINE = 2;




    public final static  int ON_SERVICE = ONLINE;
//    public static String HOST_URL = getHost();
    private static String host;

    public static String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    //    /**
//     * 获取主域
//     *
//     * @return
//     */
//    private static String getHost() {
//            switch (ON_SERVICE) {
//                case TEST:
//                    return "http://192.168.5.9:8002/gowithtommy?cmd=";
////                case ONLINE:
////                    return "http://39.107.152.248:8002/gowithtommy?cmd=";
//                    case ONLINE:
//                    return "http://api.joner.aibabel.cn:7001/gowithtommy?cmd=";
//                default:
//                    return "";
//            }
//
//    }
//    public static String URL_HOST = HOST_URL + "";//
    public static String CMD_COUNTRY_CITY = "getcityByNameAndCountry";// launcher获取国家和省
    public static String CMD_GETCOUNTRY = "getcountry";// launcher获取国家和省
    public static String CMD_SCENIC = "getscenic";// launcher获取国家和省
    public static String CMD_CITY = "getcity";// launcher获取国家和省
    public static String CMD_SAERCH = "saerch";// launcher获取国家和省
    public static String CMD_SUBSCENIC = "getsubscenic";// launcher获取国家和省





}
