package com.aibabel.translate.socket;

import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.translate.R;
import com.aibabel.translate.activity.BaseActivity;
import com.aibabel.translate.app.BaseApplication;
import com.aibabel.translate.audio.MicArrayUtil;
import com.aibabel.translate.bean.AsrAndTranResultBean;
import com.aibabel.translate.bean.ByteDataBean;
import com.aibabel.translate.bean.ErrorResultBean;
import com.aibabel.translate.bean.JsonDataBean;
import com.aibabel.translate.offline.ChangeOffline;
import com.aibabel.translate.sqlite.SqlUtils;
import com.aibabel.translate.utils.CommonUtils;
import com.aibabel.translate.utils.Constant;
import com.aibabel.translate.utils.FastJsonUtil;
import com.aibabel.translate.utils.FileUtils;
import com.aibabel.translate.utils.L;
import com.aibabel.translate.utils.LanguageUtils;
import com.aibabel.translate.utils.MediaPlayerUtil;
import com.aibabel.translate.utils.StringUtils;
import com.aibabel.translate.utils.ThreadPoolManager;
import com.aibabel.translate.utils.ToastUtil;
import com.qinghuaofflineasr.api.SpeechBase;
import com.qinghuaofflineasr.api.SpeechType;
import com.qinghuaofflineasr.api.TranBase;
import com.qinghuaofflineasr.inf.ResultListener;

import org.json.JSONObject;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class TranslateUtil implements MicArrayUtil.OnDealwithListener, SocketManger.OnReceiveListener {

    public int readnum = (int)System.currentTimeMillis();
    public int oldReadnum = (int)System.currentTimeMillis();
    private Context context;
    private MicArrayUtil util;
    private OnResponseListener listener;
    private String id = "0";
    private long clickTime = 0;
    private final long DEFAULT = 5000;
    private String asrText;
    private String mttext;
    private String entext;
    private String asrTextOffline;
    private long newTime;
    private int currentStatue = 0;
    private long startTimer;
    private long stopTimer;

    //离线缓冲
    private List<byte[]> offlineList = new LinkedList<>();
    //    private List<byte[]> copyofflineList=new LinkedList<>();
    private UP_listener up_listener;
    private DOWN_listener down_listener;
    private boolean isStart = false;
    private boolean isRead = false;
    private String to_lan_code;
    private String from_lan_code;
    private int isResultFirst = 0;

    private Activity mActivity;
    //当前录音开始  是同侧还是异侧
    private String currFrag = "Ipsil";

    private long startTime;
    private long endTime;
    private String mode;
    private String isSuccess;
    private long mtTime;
    private long soundTime;
    private int key_press;

    /**
     * 构造函数
     *
     * @param context
     * @param activity
     */
    public TranslateUtil(Context context, Activity activity) {
        this.context = context;
        mActivity = activity;
        util = MicArrayUtil.getInstance();
        util.setOnDealwithListener(this);
//        SocketManger.getInstance().setOnReceiveListener(this);
    }

    /**
     * 设置在线监听
     *
     * @param listener
     */
    public void setResponseListener(OnResponseListener listener) {
        this.listener = listener;
    }

    /**
     * 发送数据开始
     *
     * @param from
     * @param to
     * @param key_
     */
    public void sendAudio(String from, String to, int key_, String _currFrag) {
        startTime = 0;
        endTime = 0;
        mtTime = 0;
        soundTime = 0;

        //开始 en
        SocketManger.getInstance().setOnReceiveListener(this);
//        L.e("hhh",(SystemClock.uptimeMillis()-newTime)+"");
        if (SystemClock.uptimeMillis() - newTime <= 300) {
            //如果两次的时间差＜500ms，就不执行操作
            newTime = SystemClock.uptimeMillis();
            listener.reset();
            L.e("reset", "sendAudio");
            return;
        }
        //当前系统时间的毫秒值
        newTime = SystemClock.uptimeMillis();
        mttext = "";
        asrText = "";

        if (CommonUtils.isAvailable()) {
            SocketManger.getInstance().connect();
        }
        MediaPlayerUtil.stop();
        TTSUtil.getInstance().tts_Stop();
        FileUtils.deleteCacheFile();
        //可以读取标识录音标识
        if (ThreadPoolManager.getInstance().getCarryNum() == 0) {
            isRead = false;
            getOfflineList().clear();

        }

        isStart = true;
        isRead = true;
        isResultFirst = 0;
        key_press = key_;

        this.to_lan_code = to;
        from_lan_code = from;
//        copyofflineList.clear();
        currFrag = _currFrag;
        otherAsrResult = "";
        if (offlineTimer != null)
            offlineTimer.cancel();
        sendStartFlag(from, to, readnum, key_);


        L.e(readnum + "");

    }

    /**
     * 发送开始标记
     *
     * @param from
     * @param to
     * @param index
     * @param key
     */
    private void sendStartFlag(String from, String to, int index, int key) {
        startTime = System.currentTimeMillis();
        //发送在线开始标识
        if (CommonUtils.isAvailable()) {
            mode = "online";
            // TODO: 2019/1/9
            MediaPlayerUtil.playMp3(context, R.raw.start);
            MicArrayUtil.getInstance().startRecord(true, context);
            if (TextUtils.equals("fr", from)) {
                from = "fra_fra";
            }
            if (TextUtils.equals("fr", to)) {
                to = "fra_fra";
            }
            startTimer = System.currentTimeMillis();
            SocketManger.getInstance().sendMessage(new JsonDataBean(Constant.RECOGNIZE_BEGIN, getOnlineRecordTag(index, from, to)));

        } else {
            mode = "offline";
            //离线
            if (from.equals("ch_ch") || from.equals("jpa")) {
                // TODO: 2019/1/9
                MediaPlayerUtil.playMp3(context, R.raw.start);
                //中文离线永不释放  独立
                MicArrayUtil.getInstance().startRecord(false, context);
                oldReadnum = readnum;
                offlineASR(ChangeOffline.getInstance().getSpeech(from, to), ChangeOffline.getInstance().getTran(from, to), index, key);
            } else {
//                if (from.equals("jpa")) {
//                    MicArrayUtil.getInstance().startRecord(false);
//                    offlineASR(ChangeOffline.getInstance().speechUp, ChangeOffline.getInstance().tranUp, index);
//                } else {
                //其他asr不同的调法
//                L.e("======================================"+BaseApplication.isTran);
                if ((ChangeOffline.getInstance().getSpeech(from, to)) != null) {
                    BaseApplication.isTran = false;
                    ((SpeechType) ChangeOffline.getInstance().getSpeech(from, to)).down();
                    oldReadnum = readnum;
//                    L.e("oldReadnum=============="+oldReadnum+"===========readnum:"+readnum);
                    if (key == 132) {
                        if (up_listener == null) {
                            up_listener = new UP_listener();
                            ((SpeechType) ChangeOffline.getInstance().getSpeech(from, to)).setListener(up_listener);
                        } else {
                            ((SpeechType) ChangeOffline.getInstance().getSpeech(from, to)).setListener(up_listener);
                        }
                    } else {
                        if (down_listener == null) {
                            down_listener = new DOWN_listener();
                            ((SpeechType) ChangeOffline.getInstance().getSpeech(from, to)).setListener(down_listener);
                        } else {
                            ((SpeechType) ChangeOffline.getInstance().getSpeech(from, to)).setListener(down_listener);
                        }
                    }


                }
//                }
            }


        }
    }

    private synchronized List<byte[]> getOfflineList() {
        return offlineList;
    }


    public synchronized void offlineASR(final SpeechBase speechBase, final TranBase tranBase, final int ui_index, final int key) {
//        L.e("kaishioffline==================");
        BaseApplication.isTran = false;
        ThreadPoolManager.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                int num = 0;

                while (isRead) {
                    if (getOfflineList().size() > 0) {
                        //L.e("------------", "------------------------------------");

                        byte[] readdata = null;

                        try {
                            readdata = getOfflineList().remove(0);
                        } catch (Exception e) {
                            isRead = false;
                            return;
                        }
                        if (readdata != null) {
                            num++;
                            speechBase.recognition(readdata, readdata.length, num);
                        }

//                        L.e("offline======================" + Arrays.toString(readdata));

                    } else if (getOfflineList().size() == 0 && isStart == false) {
                        isRead = false;

                    }

                }


                //识别结束
                num++;
                speechBase.recognition(new byte[1], 1, -num);
                final String asr = speechBase.getResult().replace(" ", "").replace("<eps>", ",");
//                L.e("asr===========================" + asr+"======readnum:"+readnum);

                if (!TextUtils.isEmpty(asr)) {
                    //设置识别结果
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (oldReadnum != readnum) {
                                BaseApplication.isTran = true;
                                if (BaseApplication.isIpsil.equals(currFrag)) {
                                    TTSUtil.getInstance().notUnderstand(context, 1, Constant.isSound);
                                }
                                listener.reset();
                                return;
                            }
                            if (BaseApplication.isIpsil.equals(currFrag)) {
                                listener.setAsr(asr, Constant.FLAG_OFFLINE);
                            }

                        }
                    });
                    asrTextOffline = asr;
                    //翻译
                    String mt = tranBase.tran(asr, ui_index);
                    final String res[] = mt.split("_");
                    L.e("curr readnum:" + readnum + "============res[1]:" + res[1] + "==========ui_index:" + ui_index);
                    if (!TextUtils.isEmpty(mt) && res.length == 2) {
                        //设置翻译结果
                        if (res[1].equals("" + readnum)) {

                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (oldReadnum == readnum) {
//                                        L.e("readnum:"+readnum,"===========oldnum:"+oldReadnum+"============tran:"+res[0]);
                                        listener.setMt(res[0], Constant.FLAG_OFFLINE);
                                        //存入数据库
                                        insert(from_lan_code, to_lan_code, asrTextOffline, res[0], "");
                                        if (BaseApplication.isIpsil.equals(currFrag)) {
                                            TTSUtil.speekOffline(key, res[0], to_lan_code);
                                        }
//                                        L.e("当前tts===============" + to_lan_code);
                                        BaseApplication.isTran = true;
                                    } else {
//                                        L.e("NO **readnum:"+readnum,"===========oldnum:"+oldReadnum+"============tran:"+res[0]);
                                        if (BaseApplication.isIpsil.equals(currFrag)) {
                                            TTSUtil.getInstance().notUnderstand(context, 4, Constant.isSound);
                                        }
                                        FileUtils.deleteCacheFile();
                                        listener.reset();
                                        BaseApplication.isTran = true;

                                    }

                                }
                            });
                        } else {
                            //已不是上一次翻译了  做出界面反馈
                            if (TextUtils.isEmpty(asr)) {
                                if (BaseApplication.isIpsil.equals(currFrag)) {
                                    TTSUtil.getInstance().notUnderstand(context, 1, Constant.isSound);
                                }
                                listener.reset();
                            } else {
                                if (BaseApplication.isIpsil.equals(currFrag)) {
                                    TTSUtil.getInstance().notUnderstand(context, 4, Constant.isSound);
                                }
                                FileUtils.deleteCacheFile();
                                listener.reset();
                            }
                            BaseApplication.isTran = true;
                        }

                    } else {
                        if (BaseApplication.isIpsil.equals(currFrag)) {
                            TTSUtil.getInstance().notUnderstand(context, 4, Constant.isSound);
                        }
                        FileUtils.deleteCacheFile();
                        listener.reset();
                        BaseApplication.isTran = true;
                    }

                } else {
                    //准儿没有听懂
                    if (BaseApplication.isIpsil.equals(currFrag)) {
                        TTSUtil.getInstance().notUnderstand(context, 1, Constant.isSound);
                    }
                    listener.reset();
                    BaseApplication.isTran = true;
                }
            }
        });


    }

    /**
     * 发送结束标志
     *
     * @param key
     */
    public void stop(String from, String to, int key) {
        endTime = System.currentTimeMillis();
        //停止录音
//        ZipFileUtil.createFileWithByte(copyofflineList, SDCardUtils.getSDCardPath() + "pcm/20180829_00_" + System.currentTimeMillis() + ".pcm");
        isStart = false;
        MicArrayUtil.getInstance().stopRecord();
        if (SystemClock.uptimeMillis() - clickTime <= 300) {
            //如果两次的时间差＜500ms，就不执行操作
            clickTime = SystemClock.uptimeMillis();
            listener.reset();
//            L.e("发送结束标志函数");
            return;
        }
        clickTime = SystemClock.uptimeMillis();

        if (CommonUtils.isAvailable()) {
            //有网络，在线识别结束


        } else {
            //无网络，离线识别结束
            if (key == 132) {
                String lan = LanguageUtils.getCurrentUp(mActivity).get(Constant.CODE_UP);
                if (!lan.equals("ch_ch") && !lan.equals("jpa") && (ChangeOffline.getInstance().getSpeech(from, to)) != null) {
                    ((SpeechType) ChangeOffline.getInstance().getSpeech(from, to)).up();
                }


            } else if (key == 131) {
                String lan = LanguageUtils.getCurrentDown(mActivity).get(Constant.CODE_DOWN);
                if (!lan.equals("ch_ch") && !lan.equals("jpa") && (ChangeOffline.getInstance().getSpeech(from, to)) != null) {
                    ((SpeechType) ChangeOffline.getInstance().getSpeech(from, to)).up();
                }
            }
        }
    }


    @Override
    public void dealwithEachOne(byte[] msgBytes) {
        //离线发送数据
        getOfflineList().add(msgBytes);
//        copyofflineList.add(msgBytes);


    }


    /**
     * 在线发送录音数据
     *
     * @param speexBytes
     */
    @Override
    public void dealwithSpxInEachOne(byte[] speexBytes) {
        //在线发送数据
        if (CommonUtils.isAvailable()) {
            SocketManger.getInstance().sendMessage(new ByteDataBean(Constant.UPLOAD_AUDIO_DATA, speexBytes));
        }

    }


    /**
     * 录音结束发送在线结束标志
     */
    @Override
    public void dealwithComplete() {
        //发送结束标志
        if (CommonUtils.isAvailable()) {
            SocketManger.getInstance().sendMessage(new JsonDataBean(Constant.RECOGNIZE_END, ""));
        }
        // TODO: 2019/1/9
        MediaPlayerUtil.playMp3(context, R.raw.finish);
    }


    /**
     * 在线语音包开始、结束标注json
     *
     * @param fromLan 识别语言
     * @param toLan   目标语言
     * @return json字符串
     */
    private String getOnlineRecordTag(int translate_count, String fromLan, String toLan) {
        String result = "";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("flag", "1");
            jsonObject.put("id", translate_count + "");
            jsonObject.put("audio", Constant.AUDIO);
            jsonObject.put("speed", Constant.SPEED);
            jsonObject.put("gender", Constant.GENDER);
            jsonObject.put("dev", CommonUtils.getSN());
            jsonObject.put("f", fromLan);
            jsonObject.put("t", toLan);
            jsonObject.put("av", CommonUtils.getVersionName(context));
            jsonObject.put("sv", CommonUtils.getSysVersion(context));
            jsonObject.put("intelinfo", CommonUtils.getNetworkType(context));
            jsonObject.put("issell", CommonUtils.getChildFlag());
            jsonObject.put("gps", CommonUtils.getGps(context));
            jsonObject.put("location", CommonUtils.getCountry(context));
            result = jsonObject.toString();
            Log.e("TranslateUtil", result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 在线语音包开始、结束标注json
     *
     * @param fromLan 识别语言
     * @param toLan   目标语言
     * @return json字符串
     */
//    private String getOnlineRecordTag(int translate_count, String fromLan, String toLan) {
//        String result = "";
//        try {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("flag", "1");
//            jsonObject.put("id", translate_count + "");
//            jsonObject.put("audio", Constant.AUDIO);
//            jsonObject.put("speed", Constant.SPEED);
//            jsonObject.put("gender", Constant.GENDER);
//            jsonObject.put("func", "3");
//            jsonObject.put("dev", CommonUtils.getSN());
//            jsonObject.put("f", fromLan);
//            jsonObject.put("t", toLan);
//            result = jsonObject.toString();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }


    /**
     * 在线成功回调
     *
     * @param flag
     * @param result
     */
    @Override
    public void onSuccess(int flag, byte[] result) {
        String json = new String(result, Charset.forName("utf-8"));
        if (flag != Constant.RESPONSE_TTS) {
            setText(flag, json);
        } else {//TTS
            byte[] data = new byte[result.length - 1];
            System.arraycopy(result, 4, data, 0, result.length - 4);
            if (TextUtils.equals(id, String.valueOf(readnum)) && CommonUtils.isWake(context)) {//播放合成语音
                MediaPlayerUtil.playMp3(FileUtils.saveFile(data, context), context);
                soundTime = System.currentTimeMillis();
            }
        }


    }

    /**
     * 在线结果赋值到界面上
     *
     * @param flag
     * @param json
     */
    private void setText(int flag, String json) {
        AsrAndTranResultBean mtBean = FastJsonUtil.changeJsonToBean(json, AsrAndTranResultBean.class);
        String text = mtBean.getInfo();
        id = mtBean.getId();
        switch (flag) {
            case Constant.RESPONSE_ASR:
                asrText = text;
                if (!TextUtils.isEmpty(text)) {
                    if (TextUtils.equals(id, String.valueOf(readnum))) {
                        //设置识别结果
                        listener.setAsr(text, Constant.FLAG_ONLINE);
                    }

                } else {
                    if (TextUtils.equals(id, String.valueOf(readnum))) {
                        //准儿没有听懂
                        TTSUtil.getInstance().notUnderstand(context, 1, Constant.isSound);
                        listener.reset();
//                        L.e("reset","setAsr");
                        isSuccess = "failed";
                        statistics();
                    }

                }
                break;
            case Constant.RESPONSE_MT:
                isSuccess = "success";
                mtTime = System.currentTimeMillis();
                mttext = text;
                if (!TextUtils.isEmpty(text)) {
                    //设置翻译结果
                    if (TextUtils.equals(id, String.valueOf(readnum))) {
                        listener.setMt(text, Constant.FLAG_ONLINE);
//                        Log.e("mttext",mttext);
                        //存入数据库
                        insert(from_lan_code, to_lan_code, asrText, mttext, entext);
                    }
                } else {
                    if (!TextUtils.isEmpty(asrText) && TextUtils.equals(id, String.valueOf(readnum))) {
                        TTSUtil.getInstance().notUnderstand(context, 4, Constant.isSound);
                    }
                    FileUtils.deleteCacheFile();
                    listener.reset();
//                    L.e("reset","setMt");
                }
                statistics();
                break;
            case Constant.RESPONSE_EN:
                if (!TextUtils.isEmpty(text) && TextUtils.equals(id, String.valueOf(readnum))) {
                    listener.setEnglish(text);
                    entext = text;
                } else {
                    listener.setEnglish("");
                }
                break;
            default:
                break;
        }

    }

    /**
     * 在线请求错误处理
     *
     * @param flag
     * @param json
     */

    @Override
    public void onError(int flag, String json) {
        isSuccess = "failed";
        switch (flag) {
            case Constant.CONNECTION_FAILED://
                ToastUtil.showShort(context.getString(R.string.error_connect));
                break;
            case Constant.TIMEOUT_CONNECTION:
                ToastUtil.showShort(context.getString(R.string.timeout_connect));
                break;
            case Constant.TIMEOUT_READ:
                listener.reset();
                TTSUtil.getInstance().notUnderstand(context, 1, Constant.isSound);
                break;
            case Constant.RESPONSE_ERROR:
                ServerError(json);
                break;

            default:
                break;
        }
        statistics();
    }

    @Override
    public void onFinish() {
    }


    /**
     * 服务器返回错误54中 包含的101/102/103/104
     *
     * @param json
     */
    private void ServerError(String json) {
        ErrorResultBean bean = FastJsonUtil.changeJsonToBean(json, ErrorResultBean.class);
        int cmd = Integer.parseInt(bean.getEcode());
        L.e("cmd:", bean.getEcode());
        switch (cmd) {
            case 101://识别失败
                TTSUtil.getInstance().notUnderstand(context, 1, Constant.isSound);
                listener.reset();
                break;
            case 102://翻译失败
                ToastUtil.showShort(context.getResources().getString(R.string.error_response));
                break;
            case 103://合成失败
            case 104://参数不对
                MediaPlayerUtil.playMp3(null, context);
                FileUtils.deleteCacheFile();
                break;
            default:
                break;
        }
    }

    /**
     * 统计（在线为主）
     */
    private void statistics() {
        stopTimer = System.currentTimeMillis();
        long timer = stopTimer - startTime;
        int key = 1303;
        String nkey = "translation_main9";

        try {


            if (key_press == 132) {
                key = 1302;
                nkey = "translation_main9";
                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    HashMap<String, Serializable> add_hp = new HashMap<>();
                    add_hp.put("original_language_up", "" + from_lan_code);
                    add_hp.put("translation_language_up", "" + to_lan_code);
                    add_hp.put("translation_status_up", "" + isSuccess);
                    add_hp.put("translation_status_over", "" + timer);
                    ((BaseActivity) context).addStatisticsEvent(nkey, add_hp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/
            } else {
                key = 1303;
                nkey = "translation_main10";
                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    HashMap<String, Serializable> add_hp = new HashMap<>();
                    add_hp.put("original_language_down", "" + from_lan_code);
                    add_hp.put("translation_language_down", "" + to_lan_code);
                    add_hp.put("translation_status_down", "" + isSuccess);
                    add_hp.put("translation_status_over", "" + timer);
                    ((BaseActivity) context).addStatisticsEvent(nkey, add_hp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //asr结果值
    private String otherAsrResult = "";
    Timer offlineTimer;

    private void setOfflineTimeout(long sj) {
        offlineTimer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
//                L.e("UP_listener   end===================="+otherAsrResult);
                BaseApplication.isTran = true;

            }
        };
        offlineTimer.schedule(timerTask, sj);
    }


    //gs  asr 识别结果监听
    //上键监听结果  asr结果
    class UP_listener implements ResultListener {
        @Override
        public void onEnd() {

//
//            if (otherAsrResult.equals("")) {
//
////                BaseApplication.isTran = true;
//                offlineTimer.schedule(timerTask,7000);
//
//
//            }

            setOfflineTimeout(7000);
        }

        @Override
        public void onError(int i) {
//            L.e("UP_listener onError:"+i);
            //准儿没有听懂
            if (i == 8 || i == 4) {
                ChangeOffline.getInstance().createAgain(LanguageUtils.getCurrentUp(BaseApplication.getContext()).get(Constant.CODE_UP));
            }
            isResultFirst++;
            if (otherAsrResult.equals("")) {
                if (isResultFirst == 1) {
                    if (BaseApplication.isIpsil.equals(currFrag)) {
                        TTSUtil.getInstance().notUnderstand(context, 1, Constant.isSound);

                    }
                }
                listener.reset();

            }
            BaseApplication.isTran = true;
        }

        @Override
        public void onResult(final String s) {
//            L.e("result===============");
            otherAsrResult = s;
            if (!TextUtils.isEmpty(s)) {
                //设置识别结果

                if (oldReadnum != readnum) {
                    BaseApplication.isTran = true;
                    return;
                }
                listener.setAsr(s, Constant.FLAG_OFFLINE);
                asrTextOffline = s;
//                 L.e("BaseApplication.isIpsil========="+BaseApplication.isIpsil+"==========currFrag===="+currFrag);
                //翻译
                ThreadPoolManager.getInstance().addTask(new Runnable() {
                    @Override
                    public void run() {
                        String mt = ChangeOffline.getInstance().getTran(from_lan_code, to_lan_code).tran(s, readnum);
                        final String res[] = mt.split("_");

                        if (!TextUtils.isEmpty(mt) && res.length == 2) {
                            //设置翻译结果
                            if (res[1].equals("" + readnum)) {
                                mActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        listener.setMt(res[0], Constant.FLAG_OFFLINE);
                                        //存入数据库
                                        insert(from_lan_code, to_lan_code, asrTextOffline, res[0], "");
//                                        L.e("to_lan_code");
//                                        TTSUtil.playText(to_lan_code, res[0]);
                                        BaseApplication.isTran = true;
                                        if (BaseApplication.isIpsil.equals(currFrag)) {
                                            TTSUtil.speekOffline(132, res[0], to_lan_code);
                                        }


                                    }
                                });
                            }

                        } else {
                            if (BaseApplication.isIpsil.equals(currFrag)) {
                                TTSUtil.getInstance().notUnderstand(context, 4, Constant.isSound);
                            }
                            FileUtils.deleteCacheFile();
                            listener.reset();
                            BaseApplication.isTran = true;
                        }


                    }
                });


            } else {
                //准儿没有听懂
                if (BaseApplication.isIpsil.equals(currFrag)) {
                    TTSUtil.getInstance().notUnderstand(context, 1, Constant.isSound);
                }
                listener.reset();
                BaseApplication.isTran = true;
            }

        }

        @Override
        public void onPartialResults(String s) {

        }

    }


    //下键监听结果  asr结果
    class DOWN_listener implements ResultListener {
        @Override
        public void onEnd() {


//            if (otherAsrResult.equals("")) {
//                L.e("DOWN_listener   end===================="+otherAsrResult);
////                BaseApplication.isTran = true;
//                offlineTimer.schedule(timerTask,7000);
//
//            }

            setOfflineTimeout(7000);
        }

        @Override
        public void onError(int i) {
            //准儿没有听懂
//            L.e("DOWN_listener onError:"+i);
            if (i == 8 || i == 4) {
                ChangeOffline.getInstance().createAgain(LanguageUtils.getCurrentDown(BaseApplication.getContext()).get(Constant.CODE_DOWN));
            }
            isResultFirst++;
            if (otherAsrResult.equals("")) {
                if (isResultFirst == 1) {
                    if (BaseApplication.isIpsil.equals(currFrag)) {
                        TTSUtil.getInstance().notUnderstand(context, 1, Constant.isSound);
                    }
                }
                listener.reset();

            }
            BaseApplication.isTran = true;
        }

        @Override
        public void onResult(final String s) {
//            L.e("result===============");
            otherAsrResult = s;
            if (!TextUtils.isEmpty(s)) {
                //设置识别结果
                if (oldReadnum != readnum) {
                    BaseApplication.isTran = true;
                    return;
                }

                listener.setAsr(s, Constant.FLAG_OFFLINE);
                asrTextOffline = s;
                //翻译
                ThreadPoolManager.getInstance().addTask(new Runnable() {
                    @Override
                    public void run() {
                        String mt = ChangeOffline.getInstance().getTran(from_lan_code, to_lan_code).tran(s, readnum);
                        final String res[] = mt.split("_");

                        if (!TextUtils.isEmpty(mt) && res.length == 2) {
                            //设置翻译结果
                            if (res[1].equals("" + readnum)) {
                                mActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (oldReadnum == readnum) {
                                            listener.setMt(res[0], Constant.FLAG_OFFLINE);
                                            //存入数据库
                                            insert(from_lan_code, to_lan_code, asrTextOffline, res[0], "");
                                            L.e("to_lan_code");
//                                        TTSUtil.playText(to_lan_code, res[0]);
                                            if (BaseApplication.isIpsil.equals(currFrag)) {
                                                TTSUtil.speekOffline(131, res[0], to_lan_code);
                                            }

                                        }

                                        BaseApplication.isTran = true;


                                    }
                                });
                            }


                        } else {

                            TTSUtil.getInstance().notUnderstand(context, 4, Constant.isSound);
                            FileUtils.deleteCacheFile();
                            listener.reset();
                            BaseApplication.isTran = true;

                        }


                    }
                });


            } else {
                //准儿没有听懂
                TTSUtil.getInstance().notUnderstand(context, 1, Constant.isSound);
                listener.reset();
                BaseApplication.isTran = true;
            }
        }

        @Override
        public void onPartialResults(String s) {

        }

    }


    /**
     * 停止播放录音、准儿没有听懂、翻译失败等
     */

    public void stopPlay() {
        MediaPlayerUtil.stop();
        TTSUtil.getInstance().tts_Stop();
    }


    /**
     * 插入记录到数据库
     */
    private void insert(String from, String to, String asr, String mt, String en) {

        boolean IsSave = SqlUtils.insertData("", "", from, to, asr, mt, en, "", System.currentTimeMillis());

        if (IsSave) {
            Log.e("News_db", "存储成功");
        } else {
            Log.e("News_db", "存储成功");
        }
    }

    public void resetListener() {
        SocketManger.getInstance().setOnReceiveListener(this);
    }

}
