package com.aibabel.translate.socket;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.speech.tts.Voice;
import android.text.TextUtils;
import android.util.Log;

import com.aibabel.translate.R;
import com.aibabel.translate.app.BaseApplication;
import com.aibabel.translate.offline.ChangeOffline;
import com.aibabel.translate.utils.AudioUtil;
import com.aibabel.translate.utils.CommonUtils;
import com.aibabel.translate.utils.L;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TTSUtil {
    private TextToSpeech tts;//播放准儿没有听懂
    private boolean isNoSpeak = true;
    private static String oldCode;
    private volatile static TTSUtil ttsUtil;
    static TextToSpeech tts1;//播放离线音频文件
    static TextToSpeech upTTS;//播放离线音频文件
    static TextToSpeech downTTS;//播放离线音频文件
    static TextToSpeech offlineTTS;//
    static String tempCode;
    static List<Map<String,TextToSpeech>> list  = new ArrayList<>();
    private static List<Map<String, String>> langs = new ArrayList<>();

    private TTSUtil() {
    }

    public static TTSUtil getInstance() {
        if (ttsUtil == null) {
            synchronized (TTSUtil.class) {
                if (ttsUtil == null) {
                    ttsUtil = new TTSUtil();
                }
            }
        }
        return ttsUtil;
    }


    /**
     * 播放文本
     *
     * @param res
     */
    private void speakText(String res) {
        if (isNoSpeak && null != tts) {
            tts.speak(res, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    

    
    /**
     * 播放离线语音
     * @param key
     * @param res
     */

    public static void speekOffline(int key,String res,String lan_code){
        //key  取反调用
        switch (key){
            case 131:
                if (upTTS!=null&&!upTTS.isSpeaking()) {
                    upTTS.setLanguage(getLangageByCode(lan_code));
                    upTTS.speak(res, TextToSpeech.QUEUE_FLUSH, null);

                }

                break;
            case 132:
                if(downTTS!=null&&!downTTS.isSpeaking()) {
                    downTTS.setLanguage(getLangageByCode(lan_code));
                    downTTS.speak(res, TextToSpeech.QUEUE_FLUSH, null);
                }
                break;
            default:
                break;
        }


    }




    /**
     * 设置离线TTS
     * @param code
     */
    public static void setTTS(final String code){
        if(list.size()==2){
            if(!list.get(0).containsKey(code)&&!list.get(1).containsKey(code)){
                upTTS.setLanguage(getLangageByCode(code));
                final Map<String,TextToSpeech> map = new HashMap<>();
                map.put(code,upTTS);
                list.set(0,map);
            }
        }

        if(list.size()==0){
            final Map<String,TextToSpeech> map = new HashMap<>();
            upTTS = new TextToSpeech(BaseApplication.getContext(),
                    new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            if(status==TextToSpeech.SUCCESS){
                                upTTS.setLanguage(getLangageByCode(code));
                                map.put(code,upTTS);
                                list.add(map);
                            }
                        }
                    });
        }
        if(list.size()==1){
            if(!list.get(0).containsKey(code)){
                final Map<String,TextToSpeech> map = new HashMap<>();
                downTTS = new TextToSpeech(BaseApplication.getContext(),
                        new TextToSpeech.OnInitListener() {
                            @Override
                            public void onInit(int status) {
                                if(status==TextToSpeech.SUCCESS){
                                    downTTS.setLanguage(getLangageByCode(code));
                                    map.put(code,downTTS);
                                    list.add(map);
                                }

                            }
                        });
            }
        }


    }


    /**
     * 播放文本
     *
     * @param code
     * @param res
     */
    public static void playText(final String code, final String res) {
        if (null != tts1 && TextUtils.equals(oldCode, code)) {
            if (!TextUtils.isEmpty(res)&&!tts1.isSpeaking())
                tts1.speak(res, TextToSpeech.QUEUE_FLUSH, null);
            return;
        }
        oldCode = code;
        try {
            tts1 = new TextToSpeech(BaseApplication.getContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {

                    if (TextUtils.isEmpty(code)) {
                        return;
                    }
                    if (TextUtils.equals(code, "ch_ch")) {
                        tts1.setLanguage(Locale.CHINA);
                    } else if (code.contains("en")) {
                        tts1.setLanguage(Locale.ENGLISH);
                    } else if (TextUtils.equals(code, "jpa")) {
                        tts1.setLanguage(Locale.JAPAN);
                    } else if (TextUtils.equals(code, "kor")) {
                        tts1.setLanguage(Locale.KOREA);
                    } else if (TextUtils.equals(code, "ger")) {
                        tts1.setLanguage(Locale.GERMAN);
                    } else if (TextUtils.equals(code, "ita")) {
                        tts1.setLanguage(new Locale("it","IT"));
                    } else if (TextUtils.equals(code, "rus")) {
                        tts1.setLanguage(new Locale("ru", "RU"));
                    } else if (code.contains("fr")) {
                        tts1.setLanguage(new Locale("fr","FR"));
                    }
                    if (!TextUtils.isEmpty(res))
                        tts1.speak(res, TextToSpeech.QUEUE_FLUSH, null);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static Locale getLangageByCode(String code) {
        if (TextUtils.equals(code, "ch_ch")) {
            return Locale.CHINESE;
        } else if (code.contains("en")) {
            return Locale.ENGLISH;
        } else if (TextUtils.equals(code, "jpa")) {
            return Locale.JAPANESE;
        } else if (TextUtils.equals(code, "kor")) {
            return Locale.KOREAN;
        } else if (TextUtils.equals(code, "ger")) {
            return Locale.GERMANY;
        } else if (TextUtils.equals(code, "ita")) {
            return new Locale("it", "IT");
        } else if (TextUtils.equals(code, "rus")) {
            return new Locale("ru", "RU");
        } else if (code.contains("fr")) {
            return new Locale("fr", "FR");
        }

        return Locale.CHINESE;
    }





    public  void setLangage(final String code, final int key) {
        switch (key) {
            case 132:
                if (null == upTTS) {
                upTTS = new TextToSpeech(BaseApplication.getContext(),
                        new TextToSpeech.OnInitListener() {
                            @Override
                            public void onInit(int status) {
                                if(status==TextToSpeech.SUCCESS){
                                    upTTS.setLanguage(getLangageByCode(code));

                                }
                            }
                        });

            } else {

                upTTS.setLanguage(getLangageByCode(code));


            }
            break;
            case 131:
                if (null == downTTS) {
                    downTTS = new TextToSpeech(BaseApplication.getContext(),
                            new TextToSpeech.OnInitListener() {
                                @Override
                                public void onInit(int status) {
                                    if(status==TextToSpeech.SUCCESS){
                                        downTTS.setLanguage(getLangageByCode(code));

                                    }
                                }
                            });

                } else {

                    downTTS.setLanguage(getLangageByCode(code));
                }
                break;

        }



    }




    public void initTTs(final Context context) {

        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                Log.e("TextToSpeech",CommonUtils.getLocal());
                switch (CommonUtils.getLocal()) {
                    case "zh_CN":
                    case "zh_TW":
                        tts.setLanguage(Locale.CHINA);
                        break;
                    case "en":
                        tts.setLanguage(Locale.ENGLISH);
                        break;
                    case "ja":
                        tts.setLanguage(Locale.JAPAN);
                        break;
                    case "ko":
                        tts.setLanguage(Locale.KOREA);
                        break;
                    default:
                        tts.setLanguage(Locale.CHINA);
                        break;

                }


            }
        });


    }

    /**
     * 准儿没有听懂
     */
    public void notUnderstand(Context context, int type, boolean isSound) {
        switch (CommonUtils.getLocal()) {
            case "zh_CN":
            case "zh_TW":
                tts.setLanguage(Locale.CHINA);
                break;
            case "en":
                tts.setLanguage(Locale.ENGLISH);
                break;
            case "ja":
                tts.setLanguage(Locale.JAPAN);
                break;
            case "ko":
                tts.setLanguage(Locale.KOREA);
                break;
            default:
                tts.setLanguage(Locale.CHINA);
                break;

        }
        if (CommonUtils.isWake(BaseApplication.getContext())) {//如果亮屏
            if (type == 1 && isSound) {
                String res = context.getString(R.string.notUnderstand);
                speakText(res);
            }

            if (type == 4 && isSound) {
                String res = context.getString(R.string.tran_failed);
                speakText(res);
            }
        }
    }

    /**
     * tts 释放
     */
    public void tts_Stop() {

        if (null != tts) {
            tts.stop();
        }
        if (null != tts1) {
            tts1.stop();
        }

        if (upTTS!=null) {
            upTTS.stop();
        }

        if (downTTS!=null) {
            downTTS.stop();
        }
        if (offlineTTS!=null) {
            offlineTTS.stop();
        }

    }

    private  AudioUtil audioUtil=new AudioUtil(BaseApplication.getContext());
    private  UtteranceProgressListener ttsListener=new UtteranceProgressListener() {
        @Override
        public void onStart(String s) {
            L.e(s+":onStart=================");
        }

        @Override
        public void onDone(String s) {
            L.e(s+":切换完成=======");
            ChangeOffline.getInstance().ttsStackMap.remove(s);
            if (ChangeOffline.getInstance().ttsStackMap.size()==0) {
                audioUtil.setSpeakerStatus(true);
            }

        }

        @Override
        public void onError(String s) {
            L.e(s+":onError=================");
//            audioUtil.setSpeakerStatus(true);
        }
    };


}
