package com.aibabel.weather.app;

/**
 * 作者：SunSH on 2018/5/17 21:41
 * 功能：
 * 版本：1.0
 */
public class Constant {

    //    public static String IP_PORT = "http://api.joner.aibabel.cn:7001";
    public static String IP_PORT = "http://abroad.api.joner.aibabel.cn:7001";

    //天气网址
    public static String URL = "/v1/weather/Checkweather?";
    //    public static String URL = "/weather?";
    //城市列表网址
    public static String URL_CITYLIST = "/v1/weather/getCityList";
    public static final String URL_CITYLIST_NEW = "getCityNoGroup";
    //保存到sharedpreference中的3中定位key，value
    public final static String CURRENT_LOCATION_KEY = "location";
    public static String CURRENT_LOCATION_VALUE = "";
    //国际化的本地语言
    public static String NATIVE_LANGUAGE = "";

    public static String LISHI_TIANQI_KEY = "LishiTiqian";
    public static String LISHI_CHENGSHI_KEY = "LishiChengshi";

    public static String SYSTEM_LANGUAGE = "Chj";

    public static final String DEFAULT_METHOD_GROUP = "/v1/weather/";
    public static final String METHOD_GROUP_MDD = "/v1/destination/";

    public static final String METHOD_GET_LOCATION_INTERNATIONAL = "GetCnName?";

}
