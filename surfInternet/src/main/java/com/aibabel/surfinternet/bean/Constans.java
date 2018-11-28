package com.aibabel.surfinternet.bean;

/**
 * Created by Wuqinghua on 2018/6/16 0016.
 */
public class Constans {
    //国家列表
    public static final String METHOD_GUOJIALIEBIAO = "getFirstPage";
    //卡订单列表
    public static final String METHOD_KADINGDAN = "checkCard";
    //详细的订单详情
//    public static final String METHOD_KADINGDANXIANGQING= "checkOrder";
    public static final String METHOD_KADINGDANXIANGQING = "checkIccid";
    //卡列表套餐详情
    public static final String METHOD_GUOJIALIEBIAOXIANQINGYE = "flowPackageCommodity";
    //购物价钱
    public static final String METHOD_GUOJIALIEBIAOXIANQINGYEPIRCE = "flowPackagePrice";
    //创建订单
    public static final String METHOD_CHUANGJIANDINGDAN = "createOrder";
//    public static final String METHOD_CHUANGJIANDINGDAN = "createPayment";
//    http://192.168.50.7:7001/flow?cmd=flowPackageCommodity

    public static String SETCOUNTRYlANGUAGE="Chj";

    /**
     * 线上环境
     */
    public final static int ONLINE = 2;
    /**
     * 测试环境
     */
    public final static int TEST = 1;
    public final static int ON_SERVICE = ONLINE;
    //    public static String HOST_URL = getHost();
//    public final static String HOST_URL_XS = "http://192.168.5.199:3001/common/api/flow/";
//    public final static String HOST_URL_XS = "https://wx.aibabel.com:3002/common/api/flow/";
//    public final static String HOST_URL_XS = "http://api.web.aibabel.cn:7002/common/api/flow/";

//    public static String HOST_XW = "http://api.joner.aibabel.cn";
//    public static String HOST_XW = "http://192.168.50.7";
//    public static String HOST_XW = "http://abroad.api.joner.aibabel.cn";
//    public static String HOST_XW = "http://api.joner.aibabel.cn:7001";
    public static String HOST_XW = "http://abroad.api.joner.aibabel.cn:7001";
//    public static String HOST_XW = "http://39.107.238.111:7001";
//    public static String HOST_XW = "http://39.107.238.111:7001";
//    public static String HOST_XW = "http://192.168.50.8:7001";
    public static String HOST_XS = "http://abroad.api.web.aibabel.cn:7002";
//    public static String HOST_XS = "http://api.web.aibabel.cn:7000";
//    public static String HOST_XS = "https://wx.aibabel.com:3002";
//    public static String HOST_XS = "http://192.168.1.107:3001";



    public static String METHOD_GROUP_XW = "/v1/netflow/";
    public static String METHOD_GROUP_XS = "/common/api/flow/";
//    public static String METHOD_GROUP_XS = "/common/api/paypal/";

//    https://wx.aibabel.com:3002/common/api/paypal/createPayment
    /**
     * 获取主域
     *
     * @return
     */
//    private static String getHost() {
//        switch (ON_SERVICE) {
//            case TEST:
////                return "http://39.107.152.248:8002/flow?cmd=";
//                return "/flow?cmd=";
////            return "http://192.168.50.7:8002/flow?cmd=";
//            case ONLINE:
////                return "http://192.168.5.7:8002/flow?cmd=";
////                return "http://39.107.152.248:8002/flow?cmd=";
////                return "http://192.168.50.7:8002/flow?cmd=";
//                return "/flow?cmd=";
//            default:
//                return "";
//        }
//    }

    // 手机版本号 ：国际版 5 /普通版
    public static  String COUNTRY_VERSION_NUMBER = "";
    // 手机机型号  PL : pro   PH :  fly    PM : go
    public static  String PHONE_MOBILE_NUMBER = "";
    // pro 的类型  L ：租赁  S： 销售
    public static  String PRO_VERSION_NUMBER = "";

}
