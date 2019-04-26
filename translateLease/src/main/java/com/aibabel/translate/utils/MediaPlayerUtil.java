package com.aibabel.translate.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.text.TextUtils;

import java.io.IOException;

/**
 * Created by Wuqinghua on 2018/5/29 0029.
 */
public class MediaPlayerUtil implements MediaPlayer.OnCompletionListener {

    public static MediaPlayer mediaPlayer;


    public static MediaPlayer getMediaPlayer() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        return mediaPlayer;

    }

    public static boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public static void stop() {
        if (null != mediaPlayer && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    public static void playMp3(String path, Context context) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        if (null != mediaPlayer) {
            release();
        }
        mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();

        }


    }


    public static void playMp3(Context context, int res) {

        if (null == context) {
            return;
        }
        MediaPlayer mPlayer = null;
        try {
            mPlayer= MediaPlayer.create(context, res);
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
            mPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /****
     * 释放资源
     * @param
     */
    public static void release() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        release();
    }
}
