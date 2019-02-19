package com.aibabel.translate.audio;

import android.content.Context;
import android.util.Log;

import com.aibabel.micarray.api.MICArray;


import com.aibabel.translate.bean.JsonDataBean;
import com.aibabel.translate.socket.SocketManger;
import com.aibabel.translate.utils.CommonUtils;
import com.aibabel.translate.utils.L;
import com.aibabel.translate.utils.SDCardUtils;
import com.aibabel.translate.utils.ThreadPoolManager;
import com.aibabel.translate.utils.ZipFileUtil;
import com.speex.util.SpeexUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 音频录制工具类
 */
public class MicArrayUtil {

    private MICArray micArray;

    private static MicArrayUtil mInstance;

    //露营数据组
    private static List<byte[]> audioDatasList = new LinkedList<>();//待发送包列表
    private static List<byte[]> speexDataList = new LinkedList<>();//待发送包列表
    /**
     * 数据回调
     */


    private OnDealwithListener listener;


    private MicArrayUtil() {

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
    public synchronized static MicArrayUtil getInstance() {
        if (mInstance == null)
            mInstance = new MicArrayUtil();
        return mInstance;
    }

    public void setOnDealwithListener(OnDealwithListener listener) {
        this.listener = listener;
    }

    public int startRecord(final boolean needSpeex, Context context) {
        // 判断是否有外部存储设备sdcard
        if (micArray == null)
            try {

                switch (CommonUtils.getDeviceInfo()) {

                    case "PL":
                        L.e("PL Mic====================arrayL3225_9_0818.txt");
                        micArray = new MICArray(context,"arrayL3225_9_0818.txt");
                        break;
                    case "PH":
                        L.e("PH Mic====================arrayL3185.txt");
                        micArray = new MICArray(context,"arrayL3185.txt");
                        break;
                    case "PM":
                        L.e("PM Mic====================arrayL3155_22.txt");
                        micArray = new MICArray(context,"arrayL3155_22.txt");
                        break;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
           micArray.start();
        // 开启音频文件写入线程
        ThreadPoolManager.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                saveVideoData(needSpeex);
            }
        });

        return 0;
    }


    public void stopRecord() {
        try {
                if (micArray!=null) {
                    micArray.stop();
                }
        } catch (Exception e) {

        }

    }

    public void relaseRecord() {
        micArray.realse();
    }

    /**
     * 这里将数据写入文件，但是并不能播放，因为AudioRecord获得的音频是原始的裸音频，
     * 如果需要播放就必须加入一些格式或者编码的头信息。但是这样的好处就是你可以对音频的 裸数据进行处理，比如你要做一个爱说话的TOM
     * 猫在这里就进行音频的处理，然后重新封装 所以说这样得到的音频比较容易做一些音频的处理。
     */
    int num=0;
    private void saveVideoData(boolean needSpeex) {
        audioDatasList.clear();
        speexDataList.clear();
        num=0;

        // new一个byte数组用来存一些字节数据，大小为缓冲区大小
        byte[] buffer = new byte[1];
        Log.e("saveVideoData: ", "是保存了");
        while (buffer != null) {
            buffer = micArray.read();

            if (buffer != null && buffer.length > 1) {
                num++;
                Log.e("saveVideoData  num", num+"===buffer.length ："+buffer.length );
                if (needSpeex) {
                    audioSpeexEncode(buffer);
                }
                listener.dealwithEachOne(buffer);
                audioDatasList.add(buffer);
            }
        }
         //结束命令  或者暴露出去
        audioSpeexEncode(null);

        listener.dealwithComplete();
//        SocketManger.getInstance().sendMessage(new JsonDataBean(53, ""));
//        ZipFileUtil.createFileWithByte(audioDatasList, SDCardUtils.getSDCardPath()+"pcm/"+"1219_00"+System.currentTimeMillis()+".pcm");

    /*    byte[] sumB=new byte[0];
        for (int i = 0; i < speexDataList.size(); i++) {
            sumB = concat(sumB, speexDataList.get(i));

        }
        byte[]  newB=new byte[ShortByteUtil.byteArray2ShortArray(sumB).length*20];
        SpeexUtil.getInstance().decode(newB,ShortByteUtil.byteArray2ShortArray(sumB),newB.length);
      LinkedList list1=  new LinkedList();
      list1.add(newB);
        ZipFileUtil.createFileWithByte(list1, SDCardUtils.getSDCardPath()+"pcm/"+"1218_00"+System.currentTimeMillis()+".pcm");*/

        Log.e("识别结束","识别结束》》》》》》》》》》》》》》》》》》》》"+audioDatasList.size());
    }

    //压缩到1024前的大小
    final int sendL=1024*20;
    //需要压缩的数组
    volatile byte[] speexByte=new byte[0];
    //压缩完需要发送的数组
    volatile byte[] sendByte=new byte[0];

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
//                    speexDataList.add(sendByte);
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
//                speexDataList.add(sendByte);
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
//        List<byte[]> list = breakUpByteArray(from, 640);
//        for (int i = 0; i < list.size(); i++) {
//            byte[] out = new byte[32];
//            short[] formShort = ShortByteUtil.byteArray2ShortArray(list.get(i));
//            SpeexUtil.getInstance().encode(formShort, 0, out, formShort.length);
////            spx2Wav(out, fileOutputStream);
//            listener.dealwithSpxInEachOne(out);
//        }

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


    /**
     * 有小数点向上取整
     * @param divisor
     * @param dividend
     * @return
     */
    public static int getUp(int  divisor,int dividend ) {
        if (divisor % dividend != 0) {
            return divisor / dividend + 1;
        } else {
            return divisor / dividend;
        }

    }
}
