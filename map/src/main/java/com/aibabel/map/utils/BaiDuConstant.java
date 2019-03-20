package com.aibabel.map.utils;

public class BaiDuConstant {
    /**
     * 出行方式
     *  1   驾车
     *  2   公交
     *  3   步行
     */
    public static final int DRIVING_MODE = 0;
    public static final int TRANSIT_MODE = 1;
    public static final int WALKING_MODE = 2;


    /**
     * 境内外
     *  0   境内
     *  1   境外
     */
    public static final int IN_MODE = 0;
    public static final int OUT_MODE = 1;

    //精度圈颜色
    public static final int accuracyCircleFillColor = 0x4D87CEEB;
    //精度圈边框颜色
    public static final int accuracyCircleStrokeColor = 0x4D87CEEB;
    //精度圈大小
    public static final int accuracyCircleSize = 2000;

    //base类 循环时间
    public static final long baseMapMinMuch = 1000;
    //base类 循环时间
    public static final long baseMapMinLess = 500;

    //后台定位时间间隔
    public static final int locationService = 1000;

    //用于设置个性化地图的样式文件
    public static final String CUSTOM_FILE_NAME = "custom_map_config.json";

}
