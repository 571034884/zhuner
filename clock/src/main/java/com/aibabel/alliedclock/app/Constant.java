package com.aibabel.alliedclock.app;

/**
 * 作者：SunSH on 2018/5/17 21:41
 * 功能：
 * 版本：1.0
 */
public class Constant {

    public static String IP_PORT = "http://abroad.api.joner.aibabel.cn:7001";
    //    public static String IP_PORT = "http://192.168.50.7:7001";
    //城市列表网址
    public final static String URL_CITYLIST = "/v1/weather/getCityList";
    public static final String URL_CITYLIST_NEW = "getCityNoGroup";
    //单一城市网址
    public final static String URL_CITY = "/v1/weather/getZone?cityEnName=";
    //    public final static String URL_CITY = "http://192.168.5.7:8002/weather?GetCityList=getZone&cityEnName=";
    //保存到sharedpreference中的定位key，value
    public final static String CURRENT_LOCATION_KEY = "location";
    public static String CURRENT_LOCATION_VALUE = "";
    public final static String CURRENT_TIMEZONE_KEY = "timezone";
    public static String CURRENT_TIMEZONE_VALUE = "";
    //国际化的本地语言
    public static String NATIVE_LANGUAGE = "";

    public static String HOST_URL = "/v1/weather/";

    public static String SYSTEM_LANGUAGE = "Chj";
}
