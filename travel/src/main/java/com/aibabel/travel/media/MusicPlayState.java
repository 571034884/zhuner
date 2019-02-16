package com.aibabel.travel.media;

public class MusicPlayState {

        // 播放状态
        public static final int S_NOFILE = -1; // 无音乐文件
        public static final int S_INVALID = 0; // 当前音乐文件无效
        public static final int S_PREPARE = 1; // 准备
        public static final int S_PLAYING = 2; // 播放中
        public static final int S_PAUSE = 3; // 暂停

        // 播放模式
        public static final int M_SINGLE_LOOP = 0;// 单曲循环
        public static final int M_LIST_PLAY = 1;// 顺序播放
        public static final int M_LIST_LOOP = 2;// 循环播放
        public static final int M_RANDOM = 3;// 随机播放

        public static final String STATE_NAME = "STATE_NAME";
        public static final String MUSIC_INDEX = "MUSIC_INDEX";
        public static final String MUSIC_INVALID = "MUSIC_INVALID";
        public static final String MUSIC_PREPARE = "MUSIC_PREPARE";
        public static final String MUSIC_PLAY = "MUSIC_PLAY";
        public static final String MUSIC_PAUSE = "MUSIC_PAUSE";
        public static final String MUSIC_STOP = "MUSIC_STOP";

        // 广播
        public static final String MUSIC_BROCAST = "com.trvale.brocast";

}
