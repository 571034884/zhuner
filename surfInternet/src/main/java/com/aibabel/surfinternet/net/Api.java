package com.aibabel.surfinternet.net;

/**
 * Created by Wuqinghua on 2018/6/16 0016.
 */
public class Api {

//    public static String HOST = "http://abroad.api.joner.aibabel.cn:7001";
    public static String HOST = "http://test39.aibabel.cn:7001";
//    public static String HOST = "http://39.107.238.111:7001";



//    public static String HOST_WEB = "http://abroad.api.web.aibabel.cn:7002";
    public static String HOST_WEB = " https://wx.aibabel.com:3002";

//    public static String HOST_GROUP = "/v1/netflow/";
    public static String HOST_GROUP = "/v2/netflow/";



    public static String METHOD_GROUP_WEB = "/common/api/flow/";

    public static String GET_COUNTRY_DETAIL = "GetCountryPackage";//获取国家对应套餐
    public static String GET_COUNTRY_LIST = "GetCountryList";//获取国家列表
    public static String GET_COUNTRY_ORDER = "GetIccidPackage";//获取已购买套餐
    public static final String METHOD_CHUANGJIANDINGDAN = "createOrder";//创建订单




    //国家列表
    public static final String METHOD_GUOJIALIEBIAO = "getFirstPage";
    //详细的订单详情
    public static final String METHOD_KADINGDANXIANGQING = "checkIccid";
    //卡列表套餐详情
    public static final String METHOD_GUOJIALIEBIAOXIANQINGYE = "flowPackageCommodity";
    //购物价钱
    public static final String METHOD_GUOJIALIEBIAOXIANQINGYEPIRCE = "flowPackagePrice";

    public static String SETCOUNTRYlANGUAGE="zh_CN";

    public static String PHONE_ICCID = "";
    public static String PHONE_COUNTRY = "";
    public static String PHONE_LANGUAGE= "";

    // 手机版本号 ：国际版 5 /普通版
    public static  String COUNTRY_VERSION_NUMBER = "";
    // 手机机型号  PL : pro   PH :  fly    PM : go
    public static  String PHONE_MOBILE_NUMBER = "";
    // pro 的类型  L ：租赁  S： 销售
    public static  String PRO_VERSION_NUMBER = "";
    // 领科卡类型
    public static  boolean Lk_CARDTYPE = false;

}
