package com.aibabel.fyt_play.bean;

/**
 * Created by Wuqinghua on 2018/6/16 0016.
 */
public class Constans {
    //国家列表
    public static final String METHOD_GETPLAYHOMEPAGE= "GetPlayHomePage";
    public static final String METHOD_GETPLAYADDRLIST= "GetPlayAddrList";
    public static final String METHOD_GETPLAYORDERMSG= "GetPlayOrderMsg";
    public static final String METHOD_GETPLAYH5FORMAT= "GetPlayH5Format";

//    public static String HOST_XW  ="";
    public static String CITY  ="";
    public static String COUNTRY  ="";
    public static boolean ISCOUNTRY  =false;
    //安卓6.0以上手机权限处理
    public static final int PERMISSIONS_REQUEST_FOR_AUDIO = 1;
    public static String HOST_XW = "http://abroad.api.joner.aibabel.cn:7001";
//    public static String HOST_XW = "http://39.107.238.111:7001";


    public static String METHOD_GROUP_XW = "/v1/play/";
//    http://abroad.api.joner.aibabel.cn:7001/v1/weather/getCityNoGroup


}
