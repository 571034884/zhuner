package com.aibabel.translate.utils;

import android.net.Uri;
import android.text.TextUtils;

import com.qinghuaofflineasr.api.SpeechBase;
import com.qinghuaofflineasr.api.TranBase;

/**
 * @作者： SunSH
 * @日期： 2018/3/28 9:36.
 * @描述： 常量工具类
 * @增改：
 */

public class Constant {
    public static final Uri CONTENT_URI = Uri.parse("content://com.aibabel.download.offline.provider.OfflineProvider");


    public static String CURRENT_MY_ENGINE = "curr_my_engine";//当前下面的引擎code，en：英文，ch_ch:中文，jpa：日本
    public static String CURRENT_OP_ENGINE = "curr_op_engine";//当前上面的引擎code，en：英文，ch_ch:中文，jpa：日本
    public static String CURRENT_TRAN_ENGINE1 = "curr_tran_engine1";//当前的引擎1code，en2ch_ch：
    public static String CURRENT_TRAN_ENGINE2 = "curr_tran_engine2";//当前的引擎2code，en2ch_ch：和引擎1是相反的翻译

    public static String DEFAULT_HOST = "api.function.aibabel.cn";
//    public static String HOST = "39.107.238.111";
//    public static String HOST = CommonUtils.getHost();
    public static String HOST = CommonUtils.getTranslateHost();
    public static String HOST_HOTFIX = CommonUtils.getHotFixHost();
    public static int PORT = 5005;
    public static final int RECOGNIZER_INIT = 60;//识别器初始化命令（标志）
    public static final int RECOGNIZE_BEGIN = 51;//识别开始命令
    public static final int UPLOAD_AUDIO_DATA = 52;//上传音频数据命令
    public static final int RECOGNIZE_END = 53;//识别结束命令
    public static final int TRANSLATE = 54;//单独发送翻译
    public static final int RESPONSE_ERROR = 54;//返回错误flag
    public static final int RESPONSE_ASR = 55;//识别结果flag
    public static final int RESPONSE_MT = 56;//翻译结果flag
    public static final int RESPONSE_TTS = 57;//tts合成结果flag
    public static final int RESPONSE_EN = 65;//英语结果flag
    public static final int TIMEOUT_READ = 11;//读取结果超时flag
    public static final int TIMEOUT_CONNECTION = 12;//连接超时flag
    public static final int CONNECTION_FAILED = 13;//连接失败flag
    public static final int FLAG_ONLINE = 0;//在线
    public static final int FLAG_OFFLINE = 1;//离线

    public static final String AUDIO = "spx16";
    public static final String SPEED = "normal";
    public static final String GENDER = "m";
    public static final String DEV = "AAAAAAAAAAAA";

    //离线模型是否创建完成
    public static  boolean IS_OFFLINE_CREATE_COMPLETE = false ;

    //离线模型是否创建完成
    public static  boolean IS_OFFLINE_RELEASE_COMPLETE = false ;

    //离线识别模型个数
    public static  int OFFLINE_SPEECH_CREATE_NUMBER = 0;
    public static  int OFFLINE_SPEECH_RELEASE_NUMBER = 0;

    //离线翻译模型个数
    public static  int OFFLINE_TRAN_CREATE_NUMBER = 0;
    public static  int OFFLINE_TRAN_RELEASE_NUMBER = 0;

    //当前
    public static String LAN_UP = "lan_up";
    public static String LAN_DOWN = "lan_down";
    public static String CODE_UP = "code_up";
    public static String CODE_DOWN = "code_down";
    public static String ALERT_DOWN = "alert_down";
    public static String ALERT_UP = "alert_up";
    public static String SOUND_DOWN = "sound_down";
    public static String SOUND_UP = "sound_up";
    public static String OFFLINE_DOWN = "offline_down";
    public static String OFFLINE_UP = "offline_up";

    public static boolean isSound = true;

    //录音状态
    public static final int STOP = 0;
    public static final int IS_RECORDING = 1;
    public static final int START = 2;
    public static final int TIME_OUT = 3;

    public static boolean IS_NEED_SHOW = true;

    //在线和离线倒计时时间，在线15秒，离线7秒
    public static final int DEFAULT = 15 * 1000 + 10;
    public static final int OFFLINE_DEFAULT = 7 * 1000 + 10;
    //当前机器类型0：pro ，1：go ，2：fly
    public static int DEVICE_CATEGORY = 0;



}
