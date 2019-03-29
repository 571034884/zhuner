package com.aibabel.locationservice.utils;

import com.aibabel.locationservice.bean.PushBean;

import java.util.ArrayList;
import java.util.List;

public class Constants {

    public static final long DELAY_MILLIS = 60 * 1000 * 5;//延时请求推送数据，单位毫秒
    public static final long FIVE_MILLIS = 60 * 1000 * 5;//延时请求推送数据，单位毫秒
    public static final long SERVER_MILLIS = 60 * 1000 * 60 * 2;//延时请求服务器列表数据，单位毫秒
    public static final long POI_MILLIS = 60 * 1000 * 3;//延时请求服务器列表数据，单位毫秒
    public static final long POI_MILLIS_S = 60 * 1000 * 60 * 2;//延时请求服务器列表数据，单位毫秒
    public static final int LOCATION_MILLIS = 60 * 1000 * 3;//默认请求间隔，单位毫秒
    public static final int LOCATION_MILLIS_S = 60 * 1000 * 60 * 2;//默认请求间隔，单位毫秒
//    public static final int LOCATION_MILLIS = 4 * 1000 ;//默认请求间隔，单位毫秒
    public static final int PERIOD = 30 * 1000 * 60;//给语音翻译发送广播时间间隔
    public static final String ACTION_ALARM = "action_alarm";//闹钟广播action
    public static final String ACTION_TRANSLATE = "com.aibabel.translate.RELEASE_MODEL";//语音翻译广播action

    public static final String ACTION_WEATHER = "com.aibabel.broadcast.weather";
    public static final String ACTION_NOTICE = "com.aibabel.broadcast.notice";
    public static final String ACTION_TRAVEL = "com.aibabel.broadcast.travel";
    public static final String ACTION_TRAVEL_ADVISORY = "com.aibabel.broadcast.advisory";
    public static final String ACTION_LOCATION = "com.aibabel.broadcast.location";
    public static final String RADIUS = "1.5";//默认半径
    public static final String TRAVEL = "tourguide";
    public static final String ADVISORY = "advisory";
    public static final String WEATHER = "weather";
    public static final String NOTICE = "notice";
    public static  String CARD_1 = "";
    public static  String CARD_0 = "";

    public static long LAST_TIME = 0;//上次定位时间
    public static boolean LAST_NET_CONNECT = false;

    public static final CharSequence PH = "PH";//fly
    public static final CharSequence PM = "PM";//go
    public static final CharSequence PL = "PL";//pro

    public static  String CONTEXT_JG = "";
    public static String TITLE_JG  = "";
    public static String MESSAGE_JG  = "";
    public static String CONTEXTS_JG  = "";
    public static String TYPE_JG  = "";
    public static List<PushBean> pushBeanList = new ArrayList<>();
}
