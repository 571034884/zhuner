package com.aibabel.traveladvisory.app;

import android.os.Environment;

/**
 * 作者：SunSH on 2018/6/14 20:43
 * 功能：
 * 版本：1.0
 */
public class Constans {

    public static String DB_NAME = "mudidi.db";
    public static String PACKAGE_NAME = "com.aibabel.traveladvisory";
    public static String DB_PATH = "/data" + Environment.getDataDirectory().getAbsolutePath() + "/" + PACKAGE_NAME + "/databases/";
    public static String DB_FILE = DB_PATH + DB_NAME;
    public static String DB_VERSION_CODE_KEY = "db_version_code";

    //    public static String IP_PORT = "http://api.joner.aibabel.cn:7001";
    public static String IP_PORT = "http://abroad.api.joner.aibabel.cn:7001";
//    public static String IP_PORT = "http://192.168.50.13:7001";

    public static final String PIC_HOST = "http://destination.cdn.aibabel.com/";

    public static final String METHOD_GET_SHOUYE = "GetAddr";//用户启动展示页面
    public static final String METHOD_GUOJIAGAILAN = "getCountrySummeryByName";//国家概览
    public static final String METHOD_CHEGNSHIGAILAN_NAME = "getCitySummeryByName";//城市概览
    public static final String METHOD_CHEGNSHIGAILAN_ID = "getCitySummeryById";//城市概览
    public static final String METHOD_ALLCITY_IN_COUNTRY = "GetCountryCity";//当前国家的全部城市
    public static final String METHOD_GET_MSG = "getmsg";//模糊搜索
    public static final String METHOD_GET_INTEREST_POINT_MSG = "GetTagMsg";//兴趣点
    public static final String METHOD_GET_WORLD_COUNTRY = "GetCountryState";//大洲
    public static final String METHOD_GET_HOT_COUNTRY = "GetHotCountry";//热门国家
    public static final String METHOD_GET_HOT_CITY = "GetHotCity";//热门城市
    public static final String METHOD_GET_POI_MSG = "GetPoiMsg";//兴趣点
    public static final String METHOD_GET_GJGL = "getSummeryPage";//兴趣点

    public static final String OFFLINE_TIP = "OffLineTip";//离线提示对话框显示否 键

    /**
     * 线上环境
     */
    public final static int ONLINE = 2;
    /**
     * 测试环境
     */
    public final static int TEST = 1;
    public final static int ON_SERVICE = ONLINE;
    public final static String HOST_URL = getHost();

    /**
     * 获取主域
     *
     * @return
     */
    private static String getHost() {
        switch (ON_SERVICE) {
            case TEST:
                return "http://192.168.50.7:700/v1/destination/";
            case ONLINE:
//                http://192.168.50.7:7001/v1/destination/GetHotCity?countryName=日本
                return "/v1/destination/";
//                return "/tourism?status=";
//                return "http://39.107.152.248:8002/tourism?status=";
            default:
                return "";
        }
    }
}
