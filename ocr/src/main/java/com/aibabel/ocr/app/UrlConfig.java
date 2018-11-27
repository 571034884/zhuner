package com.aibabel.ocr.app;

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
//    public  final static String DEFAULT_HOST = "http://abroad.api.object.aibabel.cn:6011";
    public final static  String DEFAULT_HOST = "http://abroad.api.ocr.aibabel.cn:6001";
//    public final static  String DEFAULT_HOST = "http://api.ocr.aibabel.cn:6003";
    /**
     * 测试环境
     */

    public final static  int TEST = 1;
    /**
     * 线上环境
     */
    public final static int ONLINE = 2;

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
                    return "http://babel_us_test.aibabel.cn:6003";
//                case ONLINE:
//                    return "http://babel_us_test.aibabel.cn:6003";
                case ONLINE:
                    return "http://api.ocr.aibabel.cn:6001";
                default:
                    return "";
            }

    }
    public static String URL_OCR = HOST_URL + "";// 图像识别

}
