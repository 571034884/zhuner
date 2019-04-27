package com.aibabel.menu.net;

/**
 * Created by fytworks on 2019/4/19.
 */

public class Api {

    public static final String GET_MENU = "ServerGetMenuV3";//菜单数据
    public static final String GET_CITYLIST = "GetCityList";//目的地城市列表

    //------------------------环信相关的api-----------------------------
    /**
     * 正式服务器
     */
    public static String HOST_WEB = "https://api.web.aibabel.cn:7001";
    //测试服务器
//    public static String HOST_WEB = "https://wx.aibabel.com:3002";

    public static final String METHOD_GROUP_WEB = "/common/api/im/";

    //获取环信用户信息
    public static final String METHOD_IM = "user/getImInfoBySn";
    public static final String METHOD_IM_EDIT = "user/editNickname";

}
