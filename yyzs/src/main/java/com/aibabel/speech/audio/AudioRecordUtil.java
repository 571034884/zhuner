package com.aibabel.speech.audio;

import android.media.AudioRecord;
import android.util.Log;


import com.aibabel.speech.util.ErrorCode;
import com.aibabel.speech.util.ThreadPoolManager;
import com.speex.util.SpeexUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.aibabel.speech.audio.AudioFileUtils.getWavFilePath;

/**
 * 音频录制工具类
 */
public class AudioRecordUtil {

    // 缓冲区字节大小
    private int bufferSizeInBytes = 0;

    // mAudioRaw ，麦克风
    private String mAudioRaw = "";

    // mAudioWav可播放的wav音频文件
    private String mAudioWav = "";

    //压缩后的spx文件
    private String mAudioSpx = "";

    private AudioRecord audioRecord;
    private boolean isRecord = false;// 设置正在录制的状态

    private static AudioRecordUtil mInstance;

    //未压缩的字节缓冲
    private byte[] oldByteToSpeex;
    //未发送字节长度
    private int noSendLength = 0;
    //是否要压缩
//    private boolean needSpeex = false;

    //露营数据组
    private static List<byte[]> audioDatasList = new ArrayList<byte[]>();//待发送包列表
    /**
     * 数据回调
     */

    private OnDealwithListener listener;


    private AudioRecordUtil() {
        // 获取音频文件路径
        mAudioRaw = AudioFileUtils.getRawFilePath();
        mAudioWav = getWavFilePath();      // 1
        mAudioSpx = AudioFileUtils.getSpxFilePath();

        // 获得缓冲区字节大小

        bufferSizeInBytes = AudioRecord.getMinBufferSize(AudioFileUtils.AUDIO_SAMPLE_RATE,
                AudioFileUtils.CHANNEL_CONFIG, AudioFileUtils.AUDIO_FORMAT);
    }

    /**
     * 接收到数据的回调
     */
    public interface OnDealwithListener {
        /**
         * 回调录音处理，实时返回原始byte[]数组
         *
         * @param msgBytes
         */
        void dealwithEachOne(byte[] msgBytes);

        /**
         * 回调录音处理，实时返回压缩后byte[]数组，采用speekx压缩
         *
         * @param speexBytes
         */
        void dealwithSpxInEachOne(byte[] speexBytes);

        /**
         * 处理完成
         */
        void dealwithComplete();

    }


    //单一实例
    public synchronized static AudioRecordUtil getInstance() {
        if (mInstance == null)
            mInstance = new AudioRecordUtil();
        return mInstance;
    }

    public void setOnDealwithListener(OnDealwithListener listener) {
        this.listener = listener;
    }

    public int startRecord(final boolean needSpeex) {
        // 判断是否有外部存储设备sdcard
        if (AudioFileUtils.isSdcardExit()) {
            if (isRecord) {
                return ErrorCode.E_STATE_RECODING;
            } else {
                synchronized (this) {
                    isRecord = true;
                    if (audioRecord == null)
                        creatAudioRecord();
                    // 让录制状态为true
                }
                System.out.println("online startRecord:按下");
                try {
                    audioRecord.startRecording();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // 开启音频文件写入线程
                ThreadPoolManager.getInstance().addTask(new Runnable() {
                    @Override
                    public void run() {
                        saveVideoData(needSpeex);
                    }
                });

                return ErrorCode.SUCCESS;
            }

        } else {
            return ErrorCode.E_NOSDCARD;
        }

    }

    public void stopRecord() {
        close();
    }


    private void close() {
        try {
            if (audioRecord != null) {
                isRecord = false;// 停止文件写入
                audioRecord.stop();
                audioRecord.release();// 释放资源
                audioRecord = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void creatAudioRecord() {
        // 创建AudioRecord对象
        // MONO单声道，STEREO为双声道立体声
        audioRecord = new AudioRecord(AudioFileUtils.AUDIO_INPUT, AudioFileUtils.AUDIO_SAMPLE_RATE,
                AudioFileUtils.CHANNEL_CONFIG, AudioFileUtils.AUDIO_FORMAT, bufferSizeInBytes);
    }


    /**
     * 这里将数据写入文件，但是并不能播放，因为AudioRecord获得的音频是原始的裸音频，
     * 如果需要播放就必须加入一些格式或者编码的头信息。但是这样的好处就是你可以对音频的 裸数据进行处理，比如你要做一个爱说话的TOM
     * 猫在这里就进行音频的处理，然后重新封装 所以说这样得到的音频比较容易做一些音频的处理。
     */
    private void saveVideoData(boolean needSpeex) {

        // new一个byte数组用来存一些字节数据，大小为缓冲区大小

        int readsize = 0;
        while (isRecord) {

            try {
                byte[] audiodata = new byte[bufferSizeInBytes];
//            L.e("audiodata  length:"+audiodata.length);


                readsize = audioRecord.read(audiodata, 0, bufferSizeInBytes);
                if (AudioRecord.ERROR_INVALID_OPERATION != readsize) {
                    //将音频保存到数据组中
                    if (needSpeex) {
                        audioSpeexEncode(audiodata);
                    }
                    listener.dealwithEachOne(audiodata);
                }
            } catch (Exception e) {

            }

        }

        //结束命令  或者暴露出去
        audioSpeexEncode(null);
        listener.dealwithComplete();
    }


    //压缩到1024前的大小
    final int sendL=1024*20;
    //需要压缩的数组
    byte[] speexByte=new byte[0];
    //压缩完需要发送的数组
    byte[] sendByte=new byte[0];

    public void audioSpeexEncode(byte[] from) {
        try {
            if (from != null) {
                if (speexByte.length == sendL) {

                    List<byte[]> list = breakUpByteArray(speexByte, 640);
                    for (int i = 0; i < list.size(); i++) {
                        byte[] out = new byte[32];
                        short[] formShort = ShortByteUtil.byteArray2ShortArray(list.get(i));
                        SpeexUtil.getInstance().encode(formShort, 0, out, formShort.length);
                        sendByte = concat(sendByte, out);
//                        listener.dealwithSpxInEachOne(out);
                    }

                    listener.dealwithSpxInEachOne(sendByte);
                    Log.e("speexByte LEGTH===",speexByte.length+"==========sendByte"+sendByte.length);
                    //发送完清零
                    speexByte = new byte[0];
                    sendByte=new byte[0];
                    speexByte = concat(speexByte, from);

                } else {
                    speexByte = concat(speexByte, from);
                }

            } else {

                List<byte[]> list = breakUpByteArray(speexByte, 640);
                for (int i = 0; i < list.size(); i++) {
                    byte[] out = new byte[32];
                    short[] formShort = ShortByteUtil.byteArray2ShortArray(list.get(i));
                    SpeexUtil.getInstance().encode(formShort, 0, out, formShort.length);
                    sendByte = concat(sendByte, out);
//                        listener.dealwithSpxInEachOne(out);
                }
                Log.e("END>>speexByte LEGTH===",speexByte.length+"==========sendByte"+sendByte.length);
                listener.dealwithSpxInEachOne(sendByte);
                //发送完清零
                speexByte = new byte[0];
                sendByte=new byte[0];
            }


        } catch (Exception e) {

        }

    }


    public  byte[] concat(byte[] first, byte[] second) {
        byte[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }





    public void audioSpeexEncode(byte[] from, FileOutputStream fileOutputStream) {
        List<byte[]> list = breakUpByteArray(from, 640);
        for (int i = 0; i < list.size(); i++) {
            byte[] out = new byte[32];
            short[] formShort = ShortByteUtil.byteArray2ShortArray(list.get(i));
            SpeexUtil.getInstance().encode(formShort, 0, out, formShort.length);
            spx2Wav(out, fileOutputStream);
            listener.dealwithSpxInEachOne(out);
        }

    }

    public void spx2Wav(byte[] bytes, FileOutputStream fileOutputStream) {

        short[] decoded = new short[320];
        SpeexUtil.getInstance().decode(bytes, decoded, 32);
        byte[] out = ShortByteUtil.shortArray2ByteArray(decoded);
        try {
            fileOutputStream.write(out, 0, 640);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 数组差分
     *
     * @param from 原数组
     * @param size 每组长度
     * @return
     */
    private static List<byte[]> breakUpByteArray(byte[] from, int size) {
        List<byte[]> results = new ArrayList<>();
//        int sum= getUp();

        for (int i = 0; i < from.length / size; i++) {
            byte[] temp = new byte[size];
            System.arraycopy(from, 0 + i * size, temp, 0, size);
            results.add(temp);
        }
        try {
            //有余数不丢包
            if (from.length % size!=0) {
                byte[] temp = new byte[size];
                System.arraycopy(from, 0 +  (from.length / size)* size, temp, 0, from.length-((from.length / size)* size));
                results.add(temp);
            }
        } catch (Exception e) {
            Log.e("=============",">>>>>>>>"+e.getMessage());
        }

        Log.e("数组差分====results.size():"+results.size(),"=====byte[] from:"+from.length);
//        Log.e("***from.length / size="+ from.length / size,"=======sum="+getUp(from.length,size));

        return results;
    }

}
