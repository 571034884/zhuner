package com.aibabel.translate.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.aibabel.translate.R;
import com.qinghuaofflineasr.api.SpeechBase;
import com.qinghuaofflineasr.api.SpeechEN;
import com.qinghuaofflineasr.api.SpeechJP;
import com.qinghuaofflineasr.api.SpeechZH;
import com.qinghuaofflineasr.api.TranBase;
import com.qinghuaofflineasr.api.TranEn2zh;
import com.qinghuaofflineasr.api.TranJp2zh;
import com.qinghuaofflineasr.api.TranZh2en;
import com.qinghuaofflineasr.api.TranZh2jp;

/**
 * Created by Wuqinghua on 2018/5/4 0004.
 */

public class OffLineUtils {

    public static String TAG = OffLineUtils.class.getSimpleName();

    public TranBase tranZh2en;
    public TranBase tranZh2jp;
    public TranBase tranEn2zh;
    public TranBase tranJp2zh;
    public TranBase tranBase;

    public SpeechBase speechZh;
    public SpeechBase speechJp;
    public SpeechBase speechBase;
    public SpeechEN speechEn;
    private Context context;
    private ProgressDialog progressDialog_same;


    public OffLineUtils(Context context) {
        this.context = context;
    }

    private volatile static OffLineUtils offLineUtils;

    public static OffLineUtils getInstance(Context context) {
        if (offLineUtils == null) {
            synchronized (OffLineUtils.class) {
                if (offLineUtils == null) {
                    offLineUtils = new OffLineUtils(context);
                }
            }
        }
        return offLineUtils;
    }

    public void showDialog_same(Context context) {
        //1.创建一个ProgressDialog的实例
        progressDialog_same = new ProgressDialog(context);
        progressDialog_same.setTitle("正在创建离线模型");//2.设置标题
        progressDialog_same.setMessage("正在加载中，请稍等......");//3.设置显示内容
        progressDialog_same.setCancelable(true);//4.设置可否用back键关闭对话框
        progressDialog_same.show();//5.将ProgessDialog显示出来
    }

    public void showDialog_op(Context context) {
        //1.创建一个ProgressDialog的实例
        progressDialog_same = new ProgressDialog(context);
        progressDialog_same.setTitle("正在创建离线模型");//2.设置标题
        progressDialog_same.setMessage("正在加载中，请稍等......");//3.设置显示内容
        progressDialog_same.setCancelable(true);//4.设置可否用back键关闭对话框
        progressDialog_same.show();//5.将ProgessDialog显示出来
    }

    public void initAll(Context context) {
        try {
            tranZh2en = new TranZh2en();
            tranZh2jp = new TranZh2jp();
            tranEn2zh = new TranEn2zh();
            tranJp2zh = new TranJp2zh();

            speechEn = new SpeechEN();
            speechZh = new SpeechZH();
            speechJp = new SpeechJP();

            tranZh2en.init(mHandler);
            tranZh2jp.init(mHandler);
            tranEn2zh.init(mHandler);
            tranJp2zh.init(mHandler);

            speechZh.init(mHandler);
            speechJp.init(mHandler);
            speechEn.init(context, mHandler);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
//    public final Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//
//            switch (msg.what) {
//                case 100:
//                        ToastUtil.show(SameSideActivity.this, "中文小模型初始成功", 500);
//                    Logger.e("中文小模型初始成功");
//                    break;
//                case 103:
////                        ToastUtil.show(SameSideActivity.this, "英文模型初始成功", 500);
//                    break;
//                case -100:
////                        ToastUtil.show(SameSideActivity.this, "中文小模型初始失败", 500);
//                    break;
//                case 101:
////                        ToastUtil.show(SameSideActivity.this, "中文大模型初始成功", 500);
//                    break;
//                case -101:
////                        ToastUtil.show(SameSideActivity.this, "中文大模型初始失败", 500);
//                    break;
////                case 3:
////                    String res = msg.obj.toString();
////                    tv_show.setText(res);
////
////                    break;
//
//            }
//        }
//    };



    public void sout(String sout) {
        Log.e(TAG, sout + ": " + Constant.OFFLINE_SPEECH_CREATE_NUMBER + " " + Constant.OFFLINE_SPEECH_RELEASE_NUMBER +
                " " + Constant.OFFLINE_TRAN_CREATE_NUMBER + " " + Constant.OFFLINE_TRAN_RELEASE_NUMBER);
    }

    public boolean isCreateComplete() {
        if (Constant.OFFLINE_SPEECH_CREATE_NUMBER == 2 && Constant.OFFLINE_TRAN_CREATE_NUMBER == 2) {
            return true;
        }
        return false;
    }

    public boolean isReleaseComplete() {
        if (Constant.OFFLINE_SPEECH_RELEASE_NUMBER == 2 && Constant.OFFLINE_TRAN_RELEASE_NUMBER == 2) {
            return true;
        }
        return false;
    }


    public final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 100://识别模型创建
                    if (msg.obj != null) {

                        switch (msg.obj.toString()) {
                            case "zhSmall":
                                sout("zhSmall chuangjian");
                                Constant.OFFLINE_SPEECH_CREATE_NUMBER++;
                                Constant.OFFLINE_SPEECH_RELEASE_NUMBER++;
                                Constant.IS_OFFLINE_CREATE_COMPLETE = isCreateComplete();
                                Constant.IS_OFFLINE_RELEASE_COMPLETE = isReleaseComplete();
                                break;
                            case "zhBig":
                                break;
                            case "jpSmall":
                                sout("jpSmall chuangjian");
                                Constant.OFFLINE_SPEECH_CREATE_NUMBER++;
                                Constant.OFFLINE_SPEECH_RELEASE_NUMBER++;
                                Constant.IS_OFFLINE_CREATE_COMPLETE = isCreateComplete();
                                Constant.IS_OFFLINE_RELEASE_COMPLETE = isReleaseComplete();
                                break;
                            case "jpBig":
                                break;
                            case "enBig":
                                sout("enBig chuangjian");
                                Constant.OFFLINE_SPEECH_CREATE_NUMBER++;
                                Constant.OFFLINE_SPEECH_RELEASE_NUMBER++;
                                Constant.IS_OFFLINE_CREATE_COMPLETE = isCreateComplete();
                                Constant.IS_OFFLINE_RELEASE_COMPLETE = isReleaseComplete();
                                break;
                        }
                    }

                    break;

                case -100:
                    if (msg.obj != null) {
                        switch (msg.obj.toString()) {
                            case "zhSmall":

                                break;
                            case "zhBig":
                                break;
                            case "jpSmall":

                                break;
                            case "jpBig":
                                break;
                            case "enBig":
                                break;
                        }
                    }

                    break;
                case 200://识别模型释放
                    if (msg.obj != null) {
                        switch (msg.obj.toString()) {
                            case "zhSmall":

                                break;
                            case "zhBig":
                                sout("zhBig shifang");
//                                ToastUtil.showShort(context, "识别模型释放" + Constant.OFFLINE_SPEECH_RELEASE_NUMBER);
                                ToastUtil.showShort(context.getResources().getString(R.string.shifan));
                                Constant.OFFLINE_SPEECH_RELEASE_NUMBER--;
                                Constant.OFFLINE_SPEECH_CREATE_NUMBER--;
                                Constant.IS_OFFLINE_CREATE_COMPLETE = isCreateComplete();
                                Constant.IS_OFFLINE_RELEASE_COMPLETE = isReleaseComplete();
                                break;
                            case "jpSmall":

                                break;
                            case "jpBig":
                                sout("jpBig shifang");
//                                ToastUtil.showShort(context, "识别模型释放" + Constant.OFFLINE_SPEECH_RELEASE_NUMBER);
                                ToastUtil.showShort(context.getResources().getString(R.string.shifan) );
                                Constant.OFFLINE_SPEECH_RELEASE_NUMBER--;
                                Constant.OFFLINE_SPEECH_CREATE_NUMBER--;
                                Constant.IS_OFFLINE_CREATE_COMPLETE = isCreateComplete();
                                Constant.IS_OFFLINE_RELEASE_COMPLETE = isReleaseComplete();
                                break;
                            case "enBig":
                                sout("enBig shifang");
//                                ToastUtil.showShort(context, "识别模型释放" + Constant.OFFLINE_SPEECH_RELEASE_NUMBER);
                                ToastUtil.showShort(context.getResources().getString(R.string.shifan) );
                                Constant.OFFLINE_SPEECH_RELEASE_NUMBER--;
                                Constant.OFFLINE_SPEECH_CREATE_NUMBER--;
                                Constant.IS_OFFLINE_CREATE_COMPLETE = isCreateComplete();
                                Constant.IS_OFFLINE_RELEASE_COMPLETE = isReleaseComplete();
                                break;
                        }
                    }


                    break;

                case 300://翻译模型创建
                    if (msg.obj != null) {
                        switch (msg.obj.toString()) {
                            case "en2zh":
                                sout("en2zh fanyi chuangjian");
                                Constant.OFFLINE_TRAN_CREATE_NUMBER++;
                                Constant.OFFLINE_TRAN_RELEASE_NUMBER++;
                                Constant.IS_OFFLINE_CREATE_COMPLETE = isCreateComplete();
                                Constant.IS_OFFLINE_RELEASE_COMPLETE = isReleaseComplete();
                                break;
                            case "jp2zh":
                                sout("jp2zh fanyi chuangjian");
                                Constant.OFFLINE_TRAN_CREATE_NUMBER++;
                                Constant.OFFLINE_TRAN_RELEASE_NUMBER++;
                                Constant.IS_OFFLINE_CREATE_COMPLETE = isCreateComplete();
                                Constant.IS_OFFLINE_RELEASE_COMPLETE = isReleaseComplete();
                                break;
                            case "ko2zh":

                                break;
                            case "zh2en":
                                sout("zh2en fanyi chuangjian");
                                Constant.OFFLINE_TRAN_CREATE_NUMBER++;
                                Constant.OFFLINE_TRAN_RELEASE_NUMBER++;
                                Constant.IS_OFFLINE_CREATE_COMPLETE = isCreateComplete();
                                Constant.IS_OFFLINE_RELEASE_COMPLETE = isReleaseComplete();
                                break;
                            case "zh2jp":
                                sout("zh2jp fanyi chuangjian");
                                Constant.OFFLINE_TRAN_CREATE_NUMBER++;
                                Constant.OFFLINE_TRAN_RELEASE_NUMBER++;
                                Constant.IS_OFFLINE_CREATE_COMPLETE = isCreateComplete();
                                Constant.IS_OFFLINE_RELEASE_COMPLETE = isReleaseComplete();
                                break;
                            case "zh2ko":

                                break;
                        }
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
                case 400://翻译模型释放
                    if (msg.obj != null) {
                        switch (msg.obj.toString()) {
                            case "en2zh":
                                sout("en2zh fanyi shifang");
                                Constant.OFFLINE_TRAN_RELEASE_NUMBER--;
                                Constant.OFFLINE_TRAN_CREATE_NUMBER--;
                                Constant.IS_OFFLINE_CREATE_COMPLETE = isCreateComplete();
                                Constant.IS_OFFLINE_RELEASE_COMPLETE = isReleaseComplete();
                                break;
                            case "jp2zh":
                                sout("jp2zh fanyi shifang");
                                Constant.OFFLINE_TRAN_RELEASE_NUMBER--;
                                Constant.OFFLINE_TRAN_CREATE_NUMBER--;
                                Constant.IS_OFFLINE_CREATE_COMPLETE = isCreateComplete();
                                Constant.IS_OFFLINE_RELEASE_COMPLETE = isReleaseComplete();
                                break;
                            case "ko2zh":

                                break;
                            case "zh2en":
                                sout("zh2en fanyi shifang");
                                Constant.OFFLINE_TRAN_RELEASE_NUMBER--;
                                Constant.OFFLINE_TRAN_CREATE_NUMBER--;
                                Constant.IS_OFFLINE_CREATE_COMPLETE = isCreateComplete();
                                Constant.IS_OFFLINE_RELEASE_COMPLETE = isReleaseComplete();
                                break;
                            case "zh2jp":
                                sout("zh2jp fanyi shifang");
                                Constant.OFFLINE_TRAN_RELEASE_NUMBER--;
                                Constant.OFFLINE_TRAN_CREATE_NUMBER--;
                                Constant.IS_OFFLINE_CREATE_COMPLETE = isCreateComplete();
                                Constant.IS_OFFLINE_RELEASE_COMPLETE = isReleaseComplete();
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
     * 设置识别引擎
     *
     * @param lan 语言
     */
    public void setSpeech(String lan) {
        Log.e(">>>lan", lan);
        if (lan.contains("en")) {
            lan = "en";
        }
        switch (lan) {
            case "ch_ch":
                speechZh = new SpeechZH();
                speechZh.init(mHandler);
                break;
            case "en":
                speechEn = new SpeechEN();
                speechEn.init(context, mHandler);
                break;
            case "jpa":
                speechJp = new SpeechJP();
                speechJp.init(mHandler);
                break;
            default:
//                if (Constant.OFFLINE_SPEECH_CREATE_NUMBER > 0) {
//                    Constant.OFFLINE_SPEECH_CREATE_NUMBER--;
//                }
                break;
        }
    }

    /**
     * 释放识别引擎
     *
     * @param lan_code
     */
    public void releaseSpeech(String lan_code) {
        Log.e(TAG, "releaseSpeech: " + lan_code);
        if (lan_code.contains("en"))
            lan_code = "en";

        try {
            switch (lan_code) {
                case "ch_ch":
                   // ToastUtil.showShort(context, "释放中文识别");
                    Log.e(TAG, "释放中文识别" + Constant.OFFLINE_SPEECH_RELEASE_NUMBER + "");
                    speechZh.close(mHandler);
                    break;
                case "en":
                  //  ToastUtil.showShort(context, "释放英文识别");
                    Log.e(TAG, "释放英文识别" + Constant.OFFLINE_SPEECH_RELEASE_NUMBER + "");
                    speechEn.close(mHandler);
                    break;
                case "jpa":
                    //ToastUtil.showShort(context, "释放日文识别");
                    Log.e(TAG, "释放日文识别" + Constant.OFFLINE_SPEECH_RELEASE_NUMBER + "");
                    speechJp.close(mHandler);
                    break;
                default:
                    Log.e(TAG, "releaseSpeech: " + lan_code);
//                    if (Constant.OFFLINE_SPEECH_RELEASE_NUMBER < 3) {
//                        Constant.OFFLINE_SPEECH_RELEASE_NUMBER++;
//                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置翻译引擎
     *
     * @param
     * @param
     * @return
     */
    public void setTran(String lan_or, String lan_oj) {
        Log.e("<<<<<<<", "lan_or---" + lan_or + "----lan_oj----" + lan_oj);
       /* if (TextUtils.isEmpty(lan_or)) {
            Toast.makeText(context, "源语言不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(lan_oj)) {
            Toast.makeText(context, "目标语言不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }*/
        if (lan_or.contains("en")) {
            lan_or = "en";
        }
        if (lan_oj.contains("en")) {
            lan_oj = "en";
        }
        String key = lan_or + "2" + lan_oj;
        try {
            switch (key) {
                case "ch_ch2jpa":
                    tranZh2jp = new TranZh2jp();
                    tranZh2jp.init(mHandler);
                    break;
                case "jpa2ch_ch":
                    tranJp2zh = new TranJp2zh();
                    tranJp2zh.init(mHandler);
                    break;

                case "en2ch_ch":
                    tranEn2zh = new TranEn2zh();
                    tranEn2zh.init(mHandler);
                    break;

                case "ch_ch2en":
                    Log.e("<<<<<<<", "+++++++++");
                    tranZh2en = new TranZh2en();
                    tranZh2en.init(mHandler);
                    break;
                default:
//                    if (Constant.OFFLINE_TRAN_CREATE_NUMBER > 0) {
//                        Constant.OFFLINE_TRAN_CREATE_NUMBER--;
//                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 释放翻译引擎
     *
     * @param lan_or
     * @param lan_oj
     */
    public void releaseTran(String lan_or, String lan_oj) {
        Log.e(TAG, "releaseTran: " + lan_or + "   " + lan_oj);
       /* if (TextUtils.isEmpty(lan_or)) {
            Toast.makeText(context, "源语言不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(lan_oj)) {
            Toast.makeText(context, "目标语言不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }*/
        if (lan_or.contains("en")) {
            lan_or = "en";
        }
        if (lan_oj.contains("en")) {
            lan_oj = "en";
        }
        String key = lan_or + "2" + lan_oj;

        try {
            switch (key) {
                case "ch_ch2jpa":
//                    ToastUtil.showShort(context, "释放中文到日文翻译");
                    tranZh2jp.close(mHandler);
                    break;
                case "ch_ch2en":
                  //  ToastUtil.showShort(context, "释放中文到英文翻译");
                    tranZh2en.close(mHandler);
                    break;
                case "en2ch_ch":
                   // ToastUtil.showShort(context, "释放英文到中文翻译");
                    tranEn2zh.close(mHandler);
                    break;
                case "jpa2ch_ch":
                    //ToastUtil.showShort(context, "释放日文到中文翻译");
                    tranJp2zh.close(mHandler);
                    break;
                default:
//                    Log.e(TAG, "releaseSpeech: " + key);
//                    if (Constant.OFFLINE_TRAN_RELEASE_NUMBER < 3) {
//                        Constant.OFFLINE_TRAN_RELEASE_NUMBER++;
//                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 获取当前的识别引擎
     *
     * @param lan_code
     * @return
     */
    public Object getCurrentSpeeh(String lan_code) {
        try {
            if (!CommonUtils.isAvailable()) {
                if (TextUtils.equals(lan_code, "ch_ch")) {
                    if (null != speechZh) {
                        return speechZh;
                    }
                } else if (TextUtils.equals(lan_code, "en_ch") || TextUtils.equals(lan_code, "en_en")
                        || TextUtils.equals(lan_code, "en_usa") || TextUtils.equals(lan_code, "en_aus")
                        || TextUtils.equals(lan_code, "en_new") || TextUtils.equals(lan_code, "en_can")
                        || TextUtils.equals(lan_code, "en_ind") || TextUtils.equals(lan_code, "en_ire")
                        || TextUtils.equals(lan_code, "en_new") || TextUtils.equals(lan_code, "en_sa")
                        || TextUtils.equals(lan_code, "en_new") || TextUtils.equals(lan_code, "en_can")
                        ) {
                    if (null != speechEn) {
                        return speechEn;
                    }
                } else if (TextUtils.equals(lan_code, "jpa")) {
                    if (null != speechJp) {
                        return speechJp;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * 获取当前翻译引擎
     *
     * @param lan_or
     * @param lan_oj
     * @return
     */
    public Object getCurrentTran(String lan_or, String lan_oj) {

     /*   if (TextUtils.isEmpty(lan_or)) {
            Toast.makeText(context, "源语言不能为空！", Toast.LENGTH_SHORT).show();
            return null;
        }
        if (TextUtils.isEmpty(lan_oj)) {
            Toast.makeText(context, "目标语言不能为空！", Toast.LENGTH_SHORT).show();
            return null;
        }*/
        try {
            String key = lan_or + "2" + lan_oj;
            if (TextUtils.equals(key, "ch_ch2jpa")) {
                return tranZh2jp;
            } else if (key.contains("ch_ch2en")) {
                return tranZh2en;
            } else if (key.contains("en") && key.contains("2ch_ch")) {
                return tranEn2zh;
            } else if (TextUtils.equals(key, "jpa2ch_ch")) {
                return tranJp2zh;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }


    /**
     * 重新设置识别引擎
     *
     * @param type
     * @param lan_code
     */
    public void resetSpeech(int type, final String lan_code) {
        Log.e(TAG, "handleMessage: false" + lan_code);
        String engine_op = SharePrefUtil.getString(context, Constant.CURRENT_OP_ENGINE, "en");
        String engine_my = SharePrefUtil.getString(context, Constant.CURRENT_MY_ENGINE, "ch_ch");
        try {
            if (!TextUtils.equals(engine_my, "ch_ch") && !TextUtils.equals(engine_op, "ch_ch") && TextUtils.equals(lan_code, "ch_ch")) {
                if ((TextUtils.equals(engine_my, "jpa") || engine_my.contains("en"))) {
                    Constant.IS_OFFLINE_CREATE_COMPLETE = false;
                    Constant.IS_OFFLINE_RELEASE_COMPLETE = false;
                    if (type == 1) {
                        SharePrefUtil.saveString(context, Constant.CURRENT_OP_ENGINE, lan_code);
                        releaseSpeech(engine_op);
                        setTran("ch_ch", engine_my);
                        setTran(engine_my, "ch_ch");
                    }
                    if (type == 2){
                        SharePrefUtil.saveString(context, Constant.CURRENT_MY_ENGINE, lan_code);
                        releaseSpeech(engine_my);
                        setTran("ch_ch", engine_op);
                        setTran(engine_op, "ch_ch");
                    }
                    return;
                } else if (engine_op.contains("en") || TextUtils.equals(engine_op, "jpa")) {
                    Constant.IS_OFFLINE_CREATE_COMPLETE = false;
                    Constant.IS_OFFLINE_RELEASE_COMPLETE = false;
                    if (type == 2) {
                        SharePrefUtil.saveString(context, Constant.CURRENT_MY_ENGINE, lan_code);
                        releaseSpeech(engine_my);
                        setTran("ch_ch", engine_op);
                        setTran(engine_op, "ch_ch");
                    }
                    if (type == 1) {
                        SharePrefUtil.saveString(context, Constant.CURRENT_OP_ENGINE, lan_code);
                        releaseSpeech(engine_op);
                        setTran("ch_ch", engine_my);
                        setTran(engine_my, "ch_ch");
                    }
                    return;
                } else {
                    if (type == 1) {
                        SharePrefUtil.saveString(context, Constant.CURRENT_OP_ENGINE, lan_code);
                    }
                    if (type == 2) {
                        SharePrefUtil.saveString(context, Constant.CURRENT_MY_ENGINE, lan_code);
                    }
                    return;
                }
            } else if (TextUtils.equals(engine_my, lan_code)) {
                if (type == 1 && !TextUtils.equals(engine_op, "ch_ch")) {
                    Constant.IS_OFFLINE_CREATE_COMPLETE = false;
                    Constant.IS_OFFLINE_RELEASE_COMPLETE = false;
                    releaseSpeech(engine_op);
                    releaseTran(engine_op, engine_my);
                    releaseTran(engine_my, engine_op);
                    SharePrefUtil.saveString(context, Constant.CURRENT_OP_ENGINE, lan_code);
                    setTran(lan_code, engine_my);
                    setTran(engine_my, lan_code);
                    return;
                }
                if (type == 2) {
                    SharePrefUtil.saveString(context, Constant.CURRENT_MY_ENGINE, lan_code);
                    return;
                }
            } else if (TextUtils.equals(engine_op, lan_code)) {
                if (type == 2 && !TextUtils.equals(engine_my, "ch_ch")) {
                    Constant.IS_OFFLINE_CREATE_COMPLETE = false;
                    Constant.IS_OFFLINE_RELEASE_COMPLETE = false;
                    releaseSpeech(engine_my);
                    releaseTran(engine_op, engine_my);
                    releaseTran(engine_my, engine_op);
                    SharePrefUtil.saveString(context, Constant.CURRENT_MY_ENGINE, lan_code);
                    setTran(lan_code, engine_op);
                    setTran(engine_op, lan_code);
                    return;
                }
                if (type == 1) {
                    SharePrefUtil.saveString(context, Constant.CURRENT_OP_ENGINE, lan_code);
                    return;
                }
            } else if (type == 1) {
                Constant.IS_OFFLINE_CREATE_COMPLETE = false;
                Constant.IS_OFFLINE_RELEASE_COMPLETE = false;
                if (!TextUtils.equals(engine_op, "ch_ch")) {
                    releaseSpeech(engine_op);
                }
                releaseTran(engine_op, engine_my);
                releaseTran(engine_my, engine_op);
                setTran(lan_code, engine_my);
                setTran(engine_my, lan_code);
                SharePrefUtil.saveString(context, Constant.CURRENT_OP_ENGINE, lan_code);
                setSpeech(lan_code);
                return;
            } else if (type == 2) {
                Constant.IS_OFFLINE_CREATE_COMPLETE = false;
                Constant.IS_OFFLINE_RELEASE_COMPLETE = false;
                if (!TextUtils.equals(engine_my, "ch_ch")) {
                    releaseSpeech(engine_my);
                }
                releaseTran(engine_op, engine_my);
                releaseTran(engine_my, engine_op);
                setTran(lan_code, engine_op);
                setTran(engine_op, lan_code);
                SharePrefUtil.saveString(context, Constant.CURRENT_MY_ENGINE, lan_code);
                setSpeech(lan_code);
                return;
            }
//            if (!TextUtils.equals(engine_my, lan_code) && !TextUtils.equals(engine_op, lan_code)) {
//                switch (type) {
//                    case 1://上面的
//
//                        releaseSpeech(engine_op);
//                        SharePrefUtil.saveString(context, Constant.CURRENT_OP_ENGINE, lan_code);
//                        break;
//                    case 2://下面的
//                        releaseSpeech(engine_my);
//                        SharePrefUtil.saveString(context, Constant.CURRENT_MY_ENGINE, lan_code);
//                        break;
//                    default:
//                        break;
//                }
//                //启动新的识别引擎和翻译引擎
//                setSpeech(lan_code);
//            } else {
////                Toast.makeText(context, "已存在初始化过的模型，无需初始化！", Toast.LENGTH_SHORT).show();
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 重新设置翻译引擎
     *
     * @param
     * @param
     */
    public void resetTran(final String lan_or, final String lan_oj) {

        String key = lan_or + "2" + lan_oj;
        String key2 = lan_oj + "2" + lan_or;
        String engine_1 = SharePrefUtil.getString(context, Constant.CURRENT_TRAN_ENGINE1, "");
        String engine_2 = SharePrefUtil.getString(context, Constant.CURRENT_TRAN_ENGINE2, "");

        if (TextUtils.equals(lan_or, lan_oj)) {//源语言和目标翻译语言相同时 不进行初始化
            return;
        }
        if (!TextUtils.equals(engine_1, key) && !TextUtils.equals(engine_2, key)) {


            if (!TextUtils.isEmpty(engine_1)) {//如果为空证明原来不存在翻译引擎
                String[] engine1Array = engine_1.split("2");
                try {
                    //释放当前引擎
                    releaseTran(engine1Array[0], engine1Array[1]);
//                    releaseTran(lan_or, lan_oj);
//                    ((TranBase) getCurrentTran(engine1Array[0], engine1Array[1])).close(mHandler);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            if (!TextUtils.isEmpty(engine_2)) {//如果为空证明原来不存在翻译引擎
                String[] engine2Array = engine_2.split("2");
                try {
                    //释放当前引擎
                    releaseTran(engine2Array[0], engine2Array[1]);
//                    releaseTran(lan_or, lan_oj);
//                    ((TranBase) getCurrentTran(engine2Array[0], engine2Array[1])).close(mHandler);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //释放当前引擎

            }
            //启动新的翻译引擎
            setTran(lan_or, lan_oj);
            setTran(lan_oj, lan_or);
            SharePrefUtil.saveString(context, Constant.CURRENT_TRAN_ENGINE1, key);
            SharePrefUtil.saveString(context, Constant.CURRENT_TRAN_ENGINE2, key2);
        } else {
//            Toast.makeText(context, "已存在初始化过的模型，无需初始化！", Toast.LENGTH_SHORT).show();
        }
    }

}
