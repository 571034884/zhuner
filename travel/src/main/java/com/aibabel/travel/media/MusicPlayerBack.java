package com.aibabel.travel.media;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.aibabel.travel.utils.Constant;
import com.aibabel.travel.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.media.MediaPlayer.*;

/**
 * 原始文件
 */

public class MusicPlayerBack implements OnCompletionListener, OnErrorListener {


    private MediaPlayer mMediaPlayer; // 播放器对象
    private List<MusicData> mMusicFileList; // 音乐文件列表
    private int mCurMusicIndex; // 当前播放索引
    private int mPlayState; // 播放器状态
    private int mPlayMode; // 歌曲播放模式
    private Random mRandom;// 产生随机数
    private Context mContext;//
    private boolean isPrepared =false;

    private int wrongMusics;// 循环不超过总数的两倍，预防next播放引起的死循环

    public MusicPlayerBack(Context context) {
        mContext = context;
        mMediaPlayer = new MediaPlayer();
        mRandom = new Random();
        mMusicFileList = new ArrayList<MusicData>();
        mRandom.setSeed(System.currentTimeMillis());
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnErrorListener(this);
        DefaultInitial();
    }

    /**
     * 默认配置
     */
    private void DefaultInitial() {
        mMediaPlayer.reset();
        mMusicFileList.clear();
        mCurMusicIndex = -1;
        mPlayState = MusicPlayState.S_NOFILE;
        mPlayMode = MusicPlayState.M_LIST_LOOP;// 列表循环
        wrongMusics = -1;
    }

    /**
     * 退出时重新初始化
     */
    public void exit() {
        DefaultInitial();
        // 释放播放器
        mMediaPlayer.stop();
    }

    /**
     * 更新播放列表
     */
    public void refreshMusicList(List<MusicData> FileList) {
        if (FileList == null) {
            mMusicFileList.clear();
            mPlayState = MusicPlayState.S_NOFILE;
            mCurMusicIndex = -1;
            return;
        } else {
            mMusicFileList = FileList;

            if (mMusicFileList.size() == 0) {
                mPlayState = MusicPlayState.S_NOFILE;
                mCurMusicIndex = -1;
                return;
            }
            mCurMusicIndex = 0;
            switch (mPlayState) {
                case MusicPlayState.S_INVALID:
                case MusicPlayState.S_NOFILE:
                case MusicPlayState.S_PREPARE:
                    prepare(mCurMusicIndex);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 获取播放列表
     */
    public List<MusicData> getMusicFileList() {
        return mMusicFileList;
    }

    /**
     * 获取当前播放曲目的序号
     */
    public int getCurIndex() {
        return mCurMusicIndex;
    }

    /**
     * 获取播放状态
     */
    public int getPlayState() {
        return mPlayState;
    }

    /**
     * 获取播放模式
     */
    public int getPlayMode() {
        return mPlayMode;
    }

    /**
     * 设置播放模式
     */
    public void setPlayMode(int mode) {
        switch (mode) {
            case MusicPlayState.M_SINGLE_LOOP:
            case MusicPlayState.M_LIST_PLAY:
            case MusicPlayState.M_LIST_LOOP:
            case MusicPlayState.M_RANDOM:
                mPlayMode = mode;
                break;
        }
    }

    /**
     * 播放索引合法化
     */
    private int reviceIndex(int index) {
        int length = mMusicFileList.size();// 循环播放
        if (index < 0)
            index = length - 1;
        else if (index >= length)
            index = 0;
        return index;
    }

    /**
     * 播放进度合法化
     */
    private int reviceSeekValue(int value) {
        if (value < 0)
            value = 0;
        else if (value > 100)
            value = 100;
        return value;
    }

    /**
     * 获取随机索引，随机播放使用
     */
    private int getRandomIndex() {
        int length = mMusicFileList.size();
        if (length == 0)
            return -1;
        else
            return Math.abs(mRandom.nextInt(length - 1));
    }

    /**
     * 准备资源播放
     */
    private boolean prepare(int index) {
        mCurMusicIndex = index;
        if (mMediaPlayer == null)
            mMediaPlayer = new MediaPlayer();
        else
            mMediaPlayer.reset();

        String path = mMusicFileList.get(index).mMusicPath;

        try {
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.prepare();
            mPlayState = MusicPlayState.S_PREPARE;
            wrongMusics = -1;
            isPrepared = true;
//            mMediaPlayer.prepareAsync();// Async();// 异步的，不会阻塞当前UI线程
//            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//                    mPlayState = MusicPlayState.S_PREPARE;
//                    wrongMusics = -1;
//                    isPrepared = true;
//                }
//            });

        } catch (Exception e) {
            mPlayState = MusicPlayState.S_INVALID;// 非法
            sendPlayStateBrocast();
            wrongMusics += 1;
            if (wrongMusics < 2 * mMusicFileList.size()) {// 统计错误次数
                playNext();
            }
            return false;
        }
        sendPlayStateBrocast();
        return true;
    }



    /**
     * 获取播放器Duration(音乐持续时间)
     */
    public int getDuration() {
        if (mPlayState == MusicPlayState.S_INVALID
                || mPlayState == MusicPlayState.S_NOFILE)
            return 0;
        else
            return mMediaPlayer.getDuration();
    }

    /**
     * 获取播放进度
     */
    public int getCurPosition() {
        if (mPlayState == MusicPlayState.S_PLAYING
                || mPlayState == MusicPlayState.S_PAUSE)
            return mMediaPlayer.getCurrentPosition();
        else
            return 0;
    }

    /**
     * 播放第index首
     */
    public boolean playIndex(int index) {
        if (mPlayState == MusicPlayState.S_NOFILE)
            return false;
        if (!NetworkUtils.isAvailable(mContext)){// 无网络
            mPlayState = MusicPlayState.S_PLAYING;
            pause();
            return false;
        }
        mCurMusicIndex = index;
        if (prepare(mCurMusicIndex))
            return play();
        else
            return false;
    }

    /**
     * 播放
     */
    public boolean play() {

        if (mPlayState == MusicPlayState.S_NOFILE
                || mPlayState == MusicPlayState.S_INVALID)
            return false;
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            prepare(mCurMusicIndex);
        }
        mMediaPlayer.start();
        mPlayState = MusicPlayState.S_PLAYING;
        sendPlayStateBrocast();
        return true;
    }

    /**
     * 暂停
     */
    public boolean pause() {
        if (mPlayState != MusicPlayState.S_PLAYING)
            return false;
        mMediaPlayer.pause();
        mPlayState = MusicPlayState.S_PAUSE;
        sendPlayStateBrocast();
        return true;
    }

    /**
     * 停止
     */
    public boolean stop() {
        if (mPlayState != MusicPlayState.S_PAUSE && mPlayState == MusicPlayState.S_PLAYING)
            return false;
        else
            return prepare(mCurMusicIndex);
    }

    /**
     * 上一首
     */
    public boolean playPre() {
        if (mPlayState == MusicPlayState.S_NOFILE)
            return false;
//        if (!NetworkUtils.isAvailable(mContext)){// 无网络
//            mPlayState = MusicPlayState.S_PLAYING;
//            pause();
//            return false;
//        }
        mCurMusicIndex--;
        mCurMusicIndex = reviceIndex(mCurMusicIndex);// 合法化
        if (prepare(mCurMusicIndex))
            return play();
        else
            return false;
    }

    /**
     * 下一首
     */
    public boolean playNext() {
        if (mPlayState == MusicPlayState.S_NOFILE)
            return false;
//        if (!NetworkUtils.isAvailable(mContext)){// 无网络
//            mPlayState = MusicPlayState.S_PLAYING;
//            pause();
//            return false;
//        }

        if (mPlayMode == MusicPlayState.M_RANDOM) {
            int index = getRandomIndex();
            if (index == -1) {
                mCurMusicIndex++;
            } else
                mCurMusicIndex = index;
            mCurMusicIndex = reviceIndex(mCurMusicIndex);
        } else {
            mCurMusicIndex++;
            mCurMusicIndex = reviceIndex(mCurMusicIndex);// 合法化
        }
        if (prepare(mCurMusicIndex))
            return play();
        else
            return false;
    }

    /**
     * 进度跳转
     */
    public boolean seekTo(int rate) {
        if (mPlayState == MusicPlayState.S_INVALID
                || mPlayState == MusicPlayState.S_NOFILE)
            return false;
        rate = reviceSeekValue(rate);
        int time = mMediaPlayer.getDuration();
        int curTime = (int) ((float) rate / 100 * time);

        mMediaPlayer.seekTo(curTime);
        return true;
    }

    /**
     * 广播播放状态，
     */
    public void sendPlayStateBrocast() {
        if (mContext != null) {
            Intent intent = new Intent(MusicPlayState.MUSIC_BROCAST);
            intent.putExtra(MusicPlayState.STATE_NAME, mPlayState);
            intent.putExtra(MusicPlayState.MUSIC_INDEX, mCurMusicIndex);
            intent.putExtra(Constant.CURRENT_NET, NetworkUtils.isAvailable(mContext));

            if (mPlayState != MusicPlayState.S_NOFILE) {// 将当前音乐的信息广播回去
                Bundle bundle = new Bundle();
                MusicData data = mMusicFileList.get(mCurMusicIndex);

                bundle.putParcelable(MusicData.KEY_MUSIC_DATA, data);
                intent.putExtra(MusicData.KEY_MUSIC_DATA, bundle);
            }
            mContext.sendBroadcast(intent);
        }
    }

    /**
     * 播放完成时的动作
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        switch (mPlayMode) {
            case MusicPlayState.M_SINGLE_LOOP:
                playIndex(mCurMusicIndex);
                break;
            case MusicPlayState.M_LIST_PLAY: {
                if (mCurMusicIndex != mMusicFileList.size() - 1)
                    playNext();
                else
                    prepare(mCurMusicIndex);
            }
            break;
            case MusicPlayState.M_LIST_LOOP:
                playNext();
                break;
            case MusicPlayState.M_RANDOM: {
                int index = getRandomIndex();
                if (index == -1) {
                    mCurMusicIndex++;
                } else
                    mCurMusicIndex = index;
                mCurMusicIndex = reviceIndex(mCurMusicIndex);

                if (prepare(mCurMusicIndex))
                    play();
            }
            break;
            default:
                prepare(mCurMusicIndex);
                break;
        }
    }

    /**
     * 播放的时候发生错误
     */
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        // System.out.println("矮油，播放出错了哟");
        pause();
        sendPlayStateBrocast();

        return true;
    }


}

