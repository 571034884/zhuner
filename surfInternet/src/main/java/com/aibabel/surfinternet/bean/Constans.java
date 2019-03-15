package com.aibabel.surfinternet.bean;

/**
 * Created by Wuqinghua on 2018/6/16 0016.
 */
public class Constans {
    //国家列表
    public static final String METHOD_GUOJIALIEBIAO = "getFirstPage";

    //详细的订单详情
    public static final String METHOD_KADINGDANXIANGQING = "checkIccid";
    //卡列表套餐详情
    public static final String METHOD_GUOJIALIEBIAOXIANQINGYE = "flowPackageCommodity";
    //购物价钱
    public static final String METHOD_GUOJIALIEBIAOXIANQINGYEPIRCE = "flowPackagePrice";
    //创建订单
    public static final String METHOD_CHUANGJIANDINGDAN = "createOrder";

    public static String SETCOUNTRYlANGUAGE="Chj";

    /**
     * 线上环境
     */
    public final static int ONLINE = 2;
    public static String HOST_XW = "http://abroad.api.joner.aibabel.cn:7001";
//    public static String HOST_XW = "http://39.107.238.111:7001";
    public static String HOST_XS = "http://abroad.api.web.aibabel.cn:7002";
//    public static String HOST_XS = " https://wx.aibabel.com:3002";
    public static String PHONE_ICCID = "";
//    public static String PHONE_ICCID = "860729040093513";
    public static String PHONE_IMEI = "";
    public static String PHONE_COUNTRY = "";
    public static String PHONE_LANGUAGE= "";



    public static String METHOD_GROUP_XW = "/v1/netflow/";
    public static String METHOD_GROUP_XS = "/common/api/flow/";

    // 手机版本号 ：国际版 5 /普通版
    public static  String COUNTRY_VERSION_NUMBER = "";
    // 手机机型号  PL : pro   PH :  fly    PM : go
    public static  String PHONE_MOBILE_NUMBER = "";
    // pro 的类型  L ：租赁  S： 销售
    public static  String PRO_VERSION_NUMBER = "";
    // 领科卡类型
    public static  boolean Lk_CARDTYPE = false;
//    public static  boolean Lk_CARDTYPE = true;

}
