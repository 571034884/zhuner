package com.aibabel.translate.offline;

import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import com.aibabel.translate.R;
import com.aibabel.translate.app.BaseApplication;
import com.aibabel.translate.socket.TTSUtil;
import com.aibabel.translate.utils.Constant;
import com.aibabel.translate.utils.L;
import com.aibabel.translate.utils.LanguageUtils;
import com.aibabel.translate.utils.ToastUtil;
import com.qinghuaofflineasr.api.SpeechBase;
import com.qinghuaofflineasr.api.SpeechJP;
import com.qinghuaofflineasr.api.SpeechType;
import com.qinghuaofflineasr.api.TranBase;

import java.util.HashMap;
import java.util.Map;

import static com.aibabel.translate.app.BaseApplication.getContext;

public class ChangeOffline {
    //识别对象
    public SpeechBase speechZh;
    public SpeechBase speechJp;
    public SpeechBase speechType;
    //翻译对象
    public TranBase tranUp;
    public TranBase tranDown;

    //支持的离线{"ch_ch", "en", "jpa"}C
    public Map<String, String> offlineListMap = new HashMap() {{
        put("ch_ch", "");
        put("en", "");
        put("jpa", "");
//        put("kor", "");
//        put("rus", "");
//        put("ita", "");
//        put("fr", "");
//        put("ger", "");
    }};


    //正在执行模型启动或切换等任务
    public Map<String, String> stackMap = new HashMap<>();
    //释放的任务栈
    public Map<String, String> releaseStackMap = new HashMap<>();
    //TTS任务栈
    public Map<String, String> ttsStackMap = new HashMap<>();

    //识别映射Map
    public Map<String, String> speechMap = new HashMap() {{
        put("ch_ch", "ZH");

        put("en", "en-US");
        put("en_en", "en-US");
        put("en_ch", "en-US");
        put("en_usa", "en-US");
        put("en_asu", "en-US");
        put("en_new", "en-US");
        put("en_can", "en-US");
        put("en_ind", "en-US");
        put("en_ire", "en-US");
        put("en_sa", "en-US");


        put("jpa", "JP");
        put("kor", "ko-KR");
        put("rus", "ru-RU");
        put("ita", "it-IT");
        put("fr", "fr-FR");
        put("fra_fra", "fr-FR");
        put("fra_can", "fr-FR");


        put("ger", "de-DE");


    }};
    //语言映射Map
    public Map<String, String> lanMap = new HashMap() {{
        put("ch_ch>en", "Zh2en");
        put("ch_ch>en_en", "Zh2en");
        put("ch_ch>en_ch", "Zh2en");
        put("ch_ch>en_usa", "Zh2en");
        put("ch_ch>en_asu", "Zh2en");
        put("ch_ch>en_new", "Zh2en");
        put("ch_ch>en_can", "Zh2en");
        put("ch_ch>en_ind", "Zh2en");
        put("ch_ch>en_ire", "Zh2en");
        put("ch_ch>en_sa", "Zh2en");

        put("en>ch_ch", "En2zh");
        put("en_en>ch_ch", "En2zh");
        put("en_ch>ch_ch", "En2zh");
        put("en_usa>ch_ch", "En2zh");
        put("en_asu>ch_ch", "En2zh");
        put("en_new>ch_ch", "En2zh");
        put("en_can>ch_ch", "En2zh");
        put("en_ind>ch_ch", "En2zh");
        put("en_ire>ch_ch", "En2zh");
        put("en_sa>ch_ch", "En2zh");


        put("ch_ch>jpa", "Zh2jp");
        put("jpa>ch_ch", "Jp2zh");
        put("ch_ch>kor", "Zh2ko");
        put("kor>ch_ch", "Ko2zh");
        put("ch_ch>rus", "Zh2ru");
        put("rus>ch_ch", "Ru2zh");

        put("ch_ch>ita", "Zh2it");
        put("ita>ch_ch", "It2zh");

        put("ch_ch>fr", "Zh2fr");
        put("fr>ch_ch", "Fr2zh");
        put("ch_ch>fra_fra", "Zh2fr");
        put("fra_fra>ch_ch", "Fr2zh");
        put("ch_ch>fra_can", "Zh2fr");
        put("fra_can>ch_ch", "Fr2zh");

        put("ch_ch>ger", "Zh2de");
        put("ger>ch_ch", "De2zh");

    }};

    //移除任务map和离线回调str值映射
    Map<String, String> offlineKeyMap = new HashMap() {{
        put("zhSmall", "ZH");
        put("zhBig", "ZH");
        put("jpSmall", "JP");
        put("jpBig", "JP");
        put("enBig", "EN");
        put("zh2en", "Zh2en");
        put("en2zh", "En2zh");
        put("zh2jp", "Zh2jp");
        put("jp2zh", "Jp2zh");
        put("zh2ko", "Zh2ko");
        put("ko2zh", "Ko2zh");
        put("zh2ru", "Zh2ru");
        put("ru2zh", "Ru2zh");

        put("zh2it", "Zh2it");
        put("it2zh", "It2zh");

        put("zh2fr", "Zh2fr");
        put("fr2zh", "Fr2zh");

        put("zh2de", "Zh2de");
        put("de2zh", "De2zh");

    }};
    private Map<String, String> allNameMap = new HashMap() {{
        put("com.qinghuaofflineasr.api.SpeechEN", "EN");
        put("com.qinghuaofflineasr.api.SpeechZH", "ZH");
        put("com.qinghuaofflineasr.api.SpeechJP", "JP");

        put("com.qinghuaofflineasr.api.TranEn2zh", "En2zh");
        put("com.qinghuaofflineasr.api.TranZh2en", "Zh2en");
        put("com.qinghuaofflineasr.api.TranJp2zh", "Jp2zh");
        put("com.qinghuaofflineasr.api.TranZh2jp", "Zh2jp");

        put("com.qinghuaofflineasr.api.TranKo2zh", "Ko2zh");
        put("com.qinghuaofflineasr.api.TranZh2ko", "Zh2ko");
        put("com.qinghuaofflineasr.api.TranRu2zh", "Ru2zh");
        put("com.qinghuaofflineasr.api.TranZh2ru", "Zh2ru");

        put("com.qinghuaofflineasr.api.TranIt2zh", "It2zh");
        put("com.qinghuaofflineasr.api.TranZh2it", "Zh2it");
        put("com.qinghuaofflineasr.api.TranFr2zh", "Fr2zh");
        put("com.qinghuaofflineasr.api.TranZh2fr", "Zh2fr");

        put("com.qinghuaofflineasr.api.TranDe2zh", "De2zh");
        put("com.qinghuaofflineasr.api.TranZh2de", "Zh2de");
    }};


    //单例
    private static ChangeOffline instance;

    public static ChangeOffline getInstance() {
        if (instance == null) {
            instance = new ChangeOffline();
            return instance;
        }
        return instance;
    }

    /**
     * 根据语言返回识别对象
     *
     * @param from
     * @param to
     * @return
     */
    public SpeechBase getSpeech(String from, String to) {
        if (speechMap.containsKey(from)) {
            if (from.equals("ch_ch") && speechZh != null) {
                return speechZh;
            }

            if (from.equals("jpa") && speechJp != null) {
                return speechJp;
            }

            if (speechType != null && speechType.getClass().getName().equals("com.qinghuaofflineasr.api.SpeechType")) {
                if (speechMap.get(from).equals(((SpeechType) speechType).getLan())) {
                    return speechType;
                }
            }
        }

        return null;
    }

    /**
     * @param from
     * @param to
     * @return
     */
    public TranBase getTran(String from, String to) {
        if (tranUp != null) {
            String res = getSubStr(tranUp.getClass().getName(), 5);
            if (res.equals(lanMap.get(from + ">" + to)))
                return tranUp;
        }
        if (tranDown != null) {
            String res = getSubStr(tranDown.getClass().getName(), 5);
            if (res.equals(lanMap.get(from + ">" + to)))
                return tranDown;
        }

        return null;
    }

    /**
     * 初始或切换识别语言  同时添加任务标识 加载100 释放200
     *
     * @param lan     语言
     * @param keyCode 上按键 下按键
     */
    public void initSpeechModel(String lan, int keyCode) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (speechMap.containsKey(lan)) {
//            if (isHaveSpeech(speechMap.get(lan))) {
//                //已加载 返回
//                return;
//            }

            //中文模型 不在逻辑之中
            if (speechZh != null && lan.equals("ch_ch")) {
                return;
            } else if (speechZh == null && lan.equals("ch_ch")) {
                //添加加载任务标识
                stackMap.put(speechMap.get(lan), "");
                Class clz = Class.forName("com.qinghuaofflineasr.api.SpeechZH");
                speechZh = (SpeechBase) clz.newInstance();
                speechZh.init(mHandler);
                return;
            }

            if (speechJp != null && lan.equals("jpa")) {
//                ((SpeechJP) speechJp).loadBigModle(mHandler);
                return;
            } else if (speechJp == null && lan.equals("jpa")) {
                //添加加载任务标识
                stackMap.put(speechMap.get(lan), "");
                Class clz = Class.forName("com.qinghuaofflineasr.api.SpeechJP");
                speechJp = (SpeechBase) clz.newInstance();
                speechJp.init(mHandler);
                return;
            }

            if (!lan.equals("jpa") && !lan.equals("ch_ch") && speechType != null) {

                if (!speechMap.get(lan).equals(((SpeechType) speechType).getLan())) {
                    ((SpeechType) speechType).change(speechMap.get(lan));
                    L.e("切换语言====================="+speechMap.get(lan));
                }
                return;
            } else if (!lan.equals("jpa") && !lan.equals("ch_ch") && speechType == null) {
                //添加加载任务标识
                L.e("创建语言====================="+speechMap.get(lan));
                stackMap.put(speechMap.get(lan), "");
                speechType = (SpeechBase) Class.forName("com.qinghuaofflineasr.api.SpeechType").newInstance();
                ((SpeechType) speechType).init(getContext(), mHandler, speechMap.get(lan));
            }


        }

    }

    public void createAgain(String lan) {
        L.e("创建语言====================="+speechMap.get(lan));

        if (offlineListMap.containsKey(lan)) {
            if (speechType!=null) {
                releaseStackMap.put(((SpeechType) speechType).getLan(), "");
                speechType.close(mHandler);
                speechType=null;
            }
            try {
                stackMap.put(speechMap.get(lan), "");
                speechType = (SpeechBase) Class.forName("com.qinghuaofflineasr.api.SpeechType").newInstance();
                ((SpeechType) speechType).init(getContext(), mHandler, speechMap.get(lan));
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * 截取想要的后几位字符串
     *
     * @param str
     * @param lastIndex
     * @return
     */
    public String getSubStr(String str, int lastIndex) {
        return str.substring(str.length() - lastIndex);

    }

    /**
     * 判断是否已经这个模型了 避免重复创建
     *
     * @param lan 根据语言判断
     * @return
     */
   /* public boolean isHaveSpeech(String lan) {
        if (speechZh != null) {
            String res = getSubStr(speechZh.getClass().getName(), 2);
            if (res.equals(lan))
                return true;
        }
        if (speechJp != null) {
            String res = getSubStr(speechJp.getClass().getName(), 2);
            //排除 gs 的asr 单做判断
            if (speechJp.getClass().getName().equals("com.qinghuaofflineasr.api.SpeechType")) {
                if (((SpeechType) speechJp).getLan().equals(lan)) {
                    return true;
                }
            } else if (res.equals(lan)){
                return true;
            }

        }
        if (speechType != null) {
            String res = getSubStr(speechType.getClass().getName(), 2);
            //排除 gs 的asr 单做判断
            if (speechType.getClass().getName().equals("com.qinghuaofflineasr.api.SpeechType")) {
                if (((SpeechType) speechType).getLan().equals(lan)) {
                    return true;
                }
            } else if (res.equals(lan)){
                return true;
            }
        }

        return false;
    }*/


    /**
     * 初始或切换翻译语言  同时添加任务标识 加载300 释放400
     *
     * @param lan     语言
     * @param keyCode 上按键 下按键
     */
    public void initLanModel(String lan, int keyCode) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (lanMap.containsKey(lan)) {
            if (isHaveTran(lanMap.get(lan))) {
                return;
            }
            //添加加载任务标识
            stackMap.put(lanMap.get(lan), "");
            if (keyCode == 132) {
                if (tranUp != null) {
                    //添加释放任务标识
                    releaseStackMap.put(allNameMap.get(tranUp.getClass().getName()), "");
                    tranUp.close(mHandler);
                    tranUp = null;

                }
                tranUp = (TranBase) Class.forName("com.qinghuaofflineasr.api.Tran" + lanMap.get(lan)).newInstance();
                tranUp.init(mHandler);
            }
            if (keyCode == 131) {
                if (tranDown != null) {
                    //添加释放任务标识
                    releaseStackMap.put(allNameMap.get(tranDown.getClass().getName()), "");
                    tranDown.close(mHandler);
                    tranDown = null;

                }
                tranDown = (TranBase) Class.forName("com.qinghuaofflineasr.api.Tran" + lanMap.get(lan)).newInstance();
                tranDown.init(mHandler);
            }

        }
    }

    /**
     * 判断是否已经这个翻译模型了 避免重复创建
     *
     * @param lan 根据语言判断
     * @return
     */
    public boolean isHaveTran(String lan) {
        if (tranUp != null) {
            String res = getSubStr(tranUp.getClass().getName(), 5);
            if (res.equals(lan))
                return true;
        }
        if (tranDown != null) {
            String res = getSubStr(tranDown.getClass().getName(), 5);
            if (res.equals(lan))
                return true;
        }
        return false;
    }

    public int getMapNum() {
        int jiazai = stackMap.size();
        int shifang = releaseStackMap.size();
        int ttsqiehuan=ttsStackMap.size();
        L.e("还有在执行的任务数量：" + (jiazai + shifang));
        return jiazai + shifang+ttsqiehuan;
    }


    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    try {
                        L.e((String) msg.obj + "=================加载成功!");
                        switch ((String) msg.obj) {
                            case "zhSmall":
//                                ToastUtil.showLong("zhSmall加载成功");
                                //移除已完成的任务
                                L.e((String) msg.obj + "======================加载成功" + "====" + (String) msg.obj);
                                if (stackMap.containsKey(offlineKeyMap.get((String) msg.obj))) {
                                    stackMap.remove(offlineKeyMap.get((String) msg.obj));
                                    L.e((String) msg.obj + "8888888888888888888888");
                                }

                                break;
                            case "zhBig":
                                stackMap.remove(offlineKeyMap.get((String) msg.obj));
                                break;
                            case "en-US":
                                stackMap.remove((String) msg.obj);
                                break;
                            case "ko-KR":
                                stackMap.remove((String) msg.obj);
                                break;
                            case "ru-RU":
                                stackMap.remove((String) msg.obj);
                                break;
                            case "it-IT":
                                stackMap.remove((String) msg.obj);
                                break;
                            case "fr-FR":
                                stackMap.remove((String) msg.obj);
                                break;
                            case "de-DE":
                                stackMap.remove((String) msg.obj);
                                break;
                            case "jpSmall":
                                stackMap.remove(offlineKeyMap.get((String) msg.obj));

                                break;
                            case "jpBig":
                                stackMap.remove(offlineKeyMap.get((String) msg.obj));
                                break;

                        }
                    } catch (Exception e) {
                        L.e(">>>>>>>>>>>>>>>>》" + e.getMessage());
                    }
                    break;

                case 200:
                    try {
                        L.e((String) msg.obj + "======================释放成功");
                        switch ((String) msg.obj) {
                            case "zhSmall":
                                break;
                            case "zhBig":
                                L.e("释放---------------------" + (String) msg.obj);
                                releaseStackMap.remove(offlineKeyMap.get((String) msg.obj));
                                break;
                            case "en-US":
                                L.e("释放---------------------" + (String) msg.obj);
                                releaseStackMap.remove((String) msg.obj);
                                break;
                            case "jpSmall":

                                break;
                            case "jpBig":
                                L.e("释放---------------------" + (String) msg.obj);
                                releaseStackMap.remove(offlineKeyMap.get((String) msg.obj));
                                break;
                            case "ko-KR":
                                L.e("释放---------------------" + (String) msg.obj);
                                releaseStackMap.remove((String) msg.obj);
                                break;
                            case "ru-RU":
                                L.e("释放---------------------" + (String) msg.obj);
                                releaseStackMap.remove((String) msg.obj);
                                break;
                            case "it-IT":
                                releaseStackMap.remove((String) msg.obj);
                                break;
                            case "fr-FR":
                                releaseStackMap.remove((String) msg.obj);
                                break;
                            case "de-DE":
                                releaseStackMap.remove((String) msg.obj);
                                break;

                        }
                    } catch (Exception e) {
                        L.e(">>>>>>>>>>>>>>>>" + e.getMessage());
                    }
                    break;
                case 300:
                    if (msg.obj != null) {
                        L.e((String) msg.obj + "======================加载成功");
                        switch (msg.obj.toString()) {
                            case "en2zh":
                                stackMap.remove(offlineKeyMap.get((String) msg.obj));

                                break;
                            case "jp2zh":
                                stackMap.remove(offlineKeyMap.get((String) msg.obj));
                                break;
                            case "ko2zh":

                                stackMap.remove(offlineKeyMap.get((String) msg.obj));
                                break;
                            case "zh2en":
                                stackMap.remove(offlineKeyMap.get((String) msg.obj));
                                break;
                            case "zh2jp":
                                stackMap.remove(offlineKeyMap.get((String) msg.obj));
                                break;
                            case "zh2ko":
                                stackMap.remove(offlineKeyMap.get((String) msg.obj));
                                break;
                            case "ru2zh":
                                stackMap.remove(offlineKeyMap.get((String) msg.obj));
                                break;
                            case "zh2ru":
                                stackMap.remove(offlineKeyMap.get((String) msg.obj));
                                break;
                            case "it2zh":
                                stackMap.remove(offlineKeyMap.get((String) msg.obj));
                                break;
                            case "zh2it":
                                stackMap.remove(offlineKeyMap.get((String) msg.obj));
                                break;
                            case "fr2zh":
                                stackMap.remove(offlineKeyMap.get((String) msg.obj));
                                break;
                            case "zh2fr":
                                stackMap.remove(offlineKeyMap.get((String) msg.obj));
                                break;
                            case "de2zh":
                                stackMap.remove(offlineKeyMap.get((String) msg.obj));
                                break;
                            case "zh2de":
                                stackMap.remove(offlineKeyMap.get((String) msg.obj));
                                break;
                        }
                    }

                    break;
                case 400:
                    if (msg.obj != null) {
                        L.e((String) msg.obj + "======================释放成功");
                        switch (msg.obj.toString()) {
                            case "en2zh":
                                L.e("释放---------------------" + (String) msg.obj);
                                releaseStackMap.remove(offlineKeyMap.get((String) msg.obj));

                                break;
                            case "jp2zh":
                                L.e("释放---------------------" + (String) msg.obj);
                                releaseStackMap.remove(offlineKeyMap.get((String) msg.obj));
                                break;
                            case "ko2zh":
                                L.e("释放---------------------" + (String) msg.obj);
                                releaseStackMap.remove(offlineKeyMap.get((String) msg.obj));
                                break;
                            case "zh2en":
                                L.e("释放---------------------" + (String) msg.obj);
                                releaseStackMap.remove(offlineKeyMap.get((String) msg.obj));
                                break;
                            case "zh2jp":
                                L.e("释放---------------------" + (String) msg.obj);
                                releaseStackMap.remove(offlineKeyMap.get((String) msg.obj));
                                break;
                            case "zh2ko":
                                L.e("释放---------------------" + (String) msg.obj);
                                releaseStackMap.remove(offlineKeyMap.get((String) msg.obj));
                                break;
                            case "ru2zh":
                                L.e("释放---------------------" + (String) msg.obj);
                                releaseStackMap.remove(offlineKeyMap.get((String) msg.obj));
                                break;
                            case "zh2ru":
                                L.e("释放---------------------" + (String) msg.obj);
                                releaseStackMap.remove(offlineKeyMap.get((String) msg.obj));
                                break;
                            case "it2zh":
                                releaseStackMap.remove(offlineKeyMap.get((String) msg.obj));
                                break;
                            case "zh2it":
                                releaseStackMap.remove(offlineKeyMap.get((String) msg.obj));
                                break;
                            case "fr2zh":
                                releaseStackMap.remove(offlineKeyMap.get((String) msg.obj));
                                break;
                            case "zh2fr":
                                releaseStackMap.remove(offlineKeyMap.get((String) msg.obj));
                                break;
                            case "de2zh":
                                releaseStackMap.remove(offlineKeyMap.get((String) msg.obj));
                                break;
                            case "zh2de":
                                releaseStackMap.remove(offlineKeyMap.get((String) msg.obj));
                                break;
                        }
                    }
                    break;
                case -100:
                    try {
                        switch ((String) msg.obj) {
                            case "zhSmall":
                                ToastUtil.showLong("中文离线模型加载失败!");
                                break;
                            case "zhBig":

                                break;
                            case "enBig":

                                break;
                            case "jpSmall":


                                break;
                            case "jpBig":


                                break;

                        }
                    } catch (Exception e) {
                        L.e(">>>>>>>>>>>>>>>>" + e.getMessage());
                    }
                    break;
                case -300:
                    if (msg.obj != null) {
                        switch (msg.obj.toString()) {
                            case "en2zh":


                                break;
                            case "jp2zh":


                                break;
                            case "ko2zh":


                                break;
                            case "zh2en":


                                break;
                            case "zh2jp":


                                break;
                            case "zh2ko":


                                break;
                        }
                    }
                    break;
            }
        }
    };


    /**
     * 回到在线模式  并对资源进行释放
     */
    public void changeOnLine() {

    }

    /**
     * 释放所有资源
     */
    public void releaseAll() {
        if (speechZh!=null) {
            releaseStackMap.put(allNameMap.get(speechZh.getClass().getName()), "");
            speechZh.close(mHandler);
            speechZh=null;
        }
        if (speechJp!=null) {
            releaseStackMap.put(allNameMap.get(speechJp.getClass().getName()), "");
            speechJp.close(mHandler);
            speechJp=null;

        }
        if (speechType!=null) {
            releaseStackMap.put(((SpeechType) speechType).getLan(), "");
            speechType.close(mHandler);
            speechType=null;

        }
        if (tranUp!=null) {
            releaseStackMap.put(allNameMap.get(tranUp.getClass().getName()), "");
            tranUp.close(mHandler);
            tranUp=null;
        }
        if (tranDown!=null) {
            releaseStackMap.put(allNameMap.get(tranDown.getClass().getName()), "");
            tranDown.close(mHandler);
            tranDown=null;
        }
    }

    public void releaseOnline() {
        if (speechJp!=null) {
            releaseStackMap.put(allNameMap.get(speechJp.getClass().getName()), "");
            speechJp.close(mHandler);
            speechJp=null;

        }
        if (speechType!=null) {
            releaseStackMap.put(((SpeechType) speechType).getLan(), "");
            speechType.close(mHandler);
            speechType=null;

        }
        if (tranUp!=null) {
            releaseStackMap.put(allNameMap.get(tranUp.getClass().getName()), "");
            tranUp.close(mHandler);
            tranUp=null;
        }
        if (tranDown!=null) {
            releaseStackMap.put(allNameMap.get(tranDown.getClass().getName()), "");
            tranDown.close(mHandler);
            tranDown=null;
        }
    }


    public void test() {
//        String res = "com.qinghuaofflineasr.api.SpeechZH";
//        res = res.substring(res.length() - 2);
//        L.e("ceshi-----------" + res);
//
//        String res1 = "com.qinghuaofflineasr.api.TranEn2zh";
//        res1 = res1.substring(res1.length() - 5);
//        L.e("ceshi-----------" + res1);
        try {

            L.e("************************speechZh:" + speechZh.getClass());
            L.e("************************speechJp:" + speechJp.getClass());

            L.e("************************TranUp:" + tranUp.getClass());
            L.e("************************TranDown:" + tranDown.getClass());
            L.e("************************speechType:" + speechType.getClass());


        } catch (Exception e) {

        }

        for (String key : ChangeOffline.getInstance().stackMap.keySet()) {
            L.e("加载中的" + key + "=============" + ChangeOffline.getInstance().stackMap.get(key));
        }
        for (String key : ChangeOffline.getInstance().releaseStackMap.keySet()) {
            L.e("释放中的" + key + "=============" + ChangeOffline.getInstance().releaseStackMap.get(key));
        }

    }


    public void createOrChange() {

        if (getMapNum()<=1) {

        String down_code = LanguageUtils.getCurrentDown(getContext()).get(Constant.CODE_DOWN);
        String up_code = LanguageUtils.getCurrentUp(getContext()).get(Constant.CODE_UP);
        L.e("createOrChange qiehuan====================down_code:" + down_code + "==============up_code:" + up_code);

        try {

            if (down_code.contains("en")) {
                down_code="en";
            } else if (down_code.contains("fr")) {
                down_code="fr";
            }  else if (down_code.contains("ch")) {
                down_code="ch_ch";
            }

            if (up_code.contains("en")) {
                up_code="en";
            } else if (up_code.contains("fr")) {
                up_code="fr";
            }else if (up_code.contains("ch")) {
                up_code="ch_ch";
            }
            if (!offlineListMap.containsKey(down_code)||!offlineListMap.containsKey(up_code)) {
//                String up_name = LanguageUtils.getCurrentUp(getContext()).get(Constant.LAN_UP);
//                String down_name = LanguageUtils.getCurrentDown(getContext()).get(Constant.LAN_DOWN);
                ToastUtil.showShort(BaseApplication.getContext(). getString(R.string.buzhichi));
                return;
            }

            if (ChangeOffline.getInstance().lanMap.containsKey(up_code + ">" + down_code)) {
                ChangeOffline.getInstance().initSpeechModel(down_code, 131);
                ChangeOffline.getInstance().initLanModel(down_code + ">" + up_code, 131);
                TTSUtil.getInstance().setLangage(down_code,131);

                ChangeOffline.getInstance().initSpeechModel(up_code, 132);
                ChangeOffline.getInstance().initLanModel(up_code + ">" + down_code, 132);
                TTSUtil.getInstance().setLangage(up_code,132);
            } else {
//                String up_name = LanguageUtils.getCurrentUp(getContext()).get(Constant.LAN_UP);
//                String down_name = LanguageUtils.getCurrentDown(getContext()).get(Constant.LAN_DOWN);
                ToastUtil.showShort(BaseApplication.getContext(). getString(R.string.buzhichi));

            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }

    }
    }


    public void getOfflineList() {
        try {
            //获取支持的离线语言
            ChangeOffline.getInstance().offlineListMap=new HashMap(){{
                put("ch_ch", "");
                put("en", "");
                put("jpa", "");
            }};
            Cursor cursor = BaseApplication.getContext().getContentResolver().query(Constant.CONTENT_URI, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String status=cursor.getString( cursor.getColumnIndex("status"));
                    if (status.equals("1")) {
                        String  code=cursor.getString( cursor.getColumnIndex("lan_code"));
                        L.e("lan_code-----------"+code);
                        ChangeOffline.getInstance().offlineListMap.put(code,"");
                    }

                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            L.e("lan_code--------"+e.getMessage());

        }
    }


    public boolean isContansAsr() {
        String down_code = LanguageUtils.getCurrentDown(getContext()).get(Constant.CODE_DOWN);
        String up_code = LanguageUtils.getCurrentUp(getContext()).get(Constant.CODE_UP);
        L.e("isContansAsr down_code:"+down_code+"==================up_code:"+up_code);
        if (down_code.contains("en")) {
            down_code="en";
        } else if (down_code.contains("fr")) {
            down_code="fr";
        }  else if (down_code.contains("ch")) {
            down_code="ch_ch";
        }if (up_code.contains("en")) {
            up_code="en";
        } else if (up_code.contains("fr")) {
            up_code="fr";
        }else if (up_code.contains("ch")) {
            up_code="ch_ch";
        }
        if (!offlineListMap.containsKey(down_code)||!offlineListMap.containsKey(up_code)) {
//            String up_name = LanguageUtils.getCurrentUp(getContext()).get(Constant.LAN_UP);
//            String down_name = LanguageUtils.getCurrentDown(getContext()).get(Constant.LAN_DOWN);
            ToastUtil.showShort(BaseApplication.getContext(). getString(R.string.buzhichi));
            return false;
        }
        return true;
    }
}


