package com.aibabel.scenic.utils;

import java.util.HashMap;
import java.util.Map;

public class Constants {


    //musicservice的name
    public static final String MUSIC_SERVICE = "com.aibabel.travel.service.MusicService";
    //本地歌曲listview点击
    public static final String ACTION_LIST_ITEM = "com.aibabel.travel.listitem";
    //暂停音乐
    public static final String ACTION_PAUSE = "com.aibabel.travel.pause";
    //播放音乐
    public static final String ACTION_PLAY = "com.aibabel.travel.play";
    //下一曲
    public static final String ACTION_NEXT = "com.aibabel.travel.next";
    //上一曲
    public static final String ACTION_PRV = "com.aibabel.travel.prv";
    //关闭播放器
    public static final String ACTION_CLOSE = "com.aibabel.travel.close";
    //更新一个新的list
    public static final String ACTION_NEW = "com.aibabel.travel.new";
    //追加list
    public static final String ACTION_ADD = "com.aibabel.travel.add";
    //seekbar手动控制
    public static final String ACTION_SEEK ="com.aibabel.travel.seek";
    //以上操作结束的时候
    public static final String ACTION_COMPLETION = "com.aibabel.travel.completion";

    public static final int MSG_PROGRESS = 001;
    public static final int MSG_PREPARED = 002;
    public static final int MSG_PLAY_STATE = 003;
    // 取消
    public static final int MSG_CANCEL = 004;
    public static final int NOTIFICATION_CEDE = 100;

//    ===================================以上为音乐处理==========================================

}
