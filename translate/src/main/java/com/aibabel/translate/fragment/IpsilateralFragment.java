package com.aibabel.translate.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.translate.R;
import com.aibabel.translate.activity.EditActivity;
import com.aibabel.translate.activity.LanguageActivity;
import com.aibabel.translate.activity.RecordActivity;
import com.aibabel.translate.app.BaseApplication;
import com.aibabel.translate.broadcast.ScreenBroadcastReceiver;
import com.aibabel.translate.offline.ChangeOffline;
import com.aibabel.translate.socket.OnResponseListener;
import com.aibabel.translate.socket.SocketManger;
import com.aibabel.translate.socket.TTSUtil;
import com.aibabel.translate.socket.TranslateUtil;
import com.aibabel.translate.utils.AnimationUtils;
import com.aibabel.translate.utils.CommonUtils;
import com.aibabel.translate.utils.Constant;
import com.aibabel.translate.utils.FileUtils;
import com.aibabel.translate.utils.L;
import com.aibabel.translate.utils.LanguageUtils;
import com.aibabel.translate.utils.MediaPlayerUtil;
import com.aibabel.translate.utils.SharePrefUtil;
import com.aibabel.translate.utils.ThreadPoolManager;
import com.aibabel.translate.utils.ToastUtil;
import com.aibabel.translate.view.AdaptionSizeTextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class IpsilateralFragment extends BaseFragment implements OnResponseListener, ScreenBroadcastReceiver.ScreenListener {

    @BindView(R.id.tv_up_lan)
    TextView tvUpLan;
    @BindView(R.id.iv_up_sound)
    ImageView ivUpSound;
    @BindView(R.id.tv_up_off)
    TextView tvUpOff;
    @BindView(R.id.ll_up_sound)
    LinearLayout llUpSound;
    @BindView(R.id.tv_up_standard)
    TextView tvUpStandard;
    @BindView(R.id.iv_up_count)
    ImageView ivUpCount;
    @BindView(R.id.iv_up_speak)
    ImageView ivUpSpeak;
    @BindView(R.id.tv_up_speak)
    TextView tvUpSpeak;
    @BindView(R.id.ll_up_speak)
    LinearLayout llUpSpeak;
    @BindView(R.id.tv_up_text)
    AdaptionSizeTextView tvUpText;
    @BindView(R.id.tv_up_English)
    TextView tvUpEnglish;
    @BindView(R.id.rl_up_text)
    RelativeLayout rlUpText;
    @BindView(R.id.iv_down_speak)
    ImageView ivDownSpeak;
    @BindView(R.id.tv_down_speak)
    TextView tvDownSpeak;
    @BindView(R.id.ll_down_speak)
    LinearLayout llDownSpeak;
    @BindView(R.id.rl_up)
    RelativeLayout rlUp;
    @BindView(R.id.iv_down_sound)
    ImageView ivDownSound;
    @BindView(R.id.tv_down_off)
    TextView tvDownOff;
    @BindView(R.id.ll_down_sound)
    LinearLayout llDownSound;
    @BindView(R.id.tv_down_standard)
    TextView tvDownStandard;
    @BindView(R.id.iv_down_count)
    ImageView ivDownCount;
    @BindView(R.id.tv_down_text)
    AdaptionSizeTextView tvDownText;
    @BindView(R.id.tv_down_English)
    TextView tvDownEnglish;
    @BindView(R.id.rl_down_text)
    RelativeLayout rlDownText;
    @BindView(R.id.rl_down)
    RelativeLayout rlDown;
    @BindView(R.id.iv_switch)
    ImageView ivSwitch;
    @BindView(R.id.tv_down_lan)
    TextView tvDownLan;
    @BindView(R.id.rl_down_lan)
    RelativeLayout rlDownLan;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    Unbinder unbinder;
    //    @BindView(R.id.tv_up_count)
//    TextView tvUpCount;
    @BindView(R.id.sv_up_text)
    ScrollView svUpText;
    @BindView(R.id.rl_up_content)
    RelativeLayout rlUpContent;
    @BindView(R.id.mView)
    View mView;
    @BindView(R.id.sv_down_text)
    ScrollView svDownText;
    @BindView(R.id.iv_record)
    ImageView ivRecord;

    /*引导页布局*/
    private Handler mHandler = new Handler();
    private View popu1;

    private TextView tv_I_kown1;
    private TextView tv_I_kown2;
    private TextView tv_I_kown3;
    private TextView tv_I_kown4;
    private TextView tv_I_kown5;
    private PopupWindow popupWindow;
    private RelativeLayout rl1;
    private RelativeLayout rl2;
    private RelativeLayout rl3;
    private RelativeLayout rl4;
    private RelativeLayout rl5;


    private String code_from;//语言code
    private String code_to;//语言code
    private String lan_up;//上面语言的值
    private String lan_do;//下面语言的值
    private Context context;
    private int DEFAULT;
    private CountDownTimer timer;
    private AnimationDrawable animationCountUp;
    private AnimationDrawable animationCountDown;
    private AnimationDrawable animationDrawableUp;
    private AnimationDrawable animationDrawableDown;
    private boolean isTimeOut = false;
    private boolean isRecording = false;
    private CountDownTimer countDownTimer;
    public TranslateUtil translate;
    private int curr_press;
    private ScreenBroadcastReceiver mScreenReceiver;
    private long oldTime;
    private String mtText;
    private String code_do;
    private String code_up;
    private boolean isOnline;//判定是否为在线（用户播放语音时判断）
    private String asrtext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ipsilateral, container, false);
        unbinder = ButterKnife.bind(this, view);
//        L.e("IpsilateralFragment onCreateView==============");
        return view;
    }


    @Override
    public void initView() {
        //录音帧动画
        animationDrawableUp = (AnimationDrawable) ivUpSpeak.getDrawable();
        animationDrawableDown = (AnimationDrawable) ivDownSpeak.getDrawable();
        //倒计时
        animationCountUp = (AnimationDrawable) ivUpCount.getDrawable();
        animationCountDown = (AnimationDrawable) ivDownCount.getDrawable();

    }

    @Override
    public void initData() {
//        L.e("isl   indata=================================");
        context = getActivity();
        translate = new TranslateUtil(context, getActivity());
        translate.setResponseListener(this);
        ivDownSound.setOnClickListener(this);
        ivUpSound.setOnClickListener(this);
        tvDownLan.setOnClickListener(this);
        tvUpLan.setOnClickListener(this);
        ivSwitch.setOnClickListener(this);
        tvUpText.setOnClickListener(this);
        tvDownText.setOnClickListener(this);
        ivRecord.setOnClickListener(this);

        //////////////////////绑一下界面数据////////////////////
//        lan_do = LanguageUtils.getCurrentDown(context).get(Constant.LAN_DOWN);
//        lan_up = LanguageUtils.getCurrentUp(context).get(Constant.LAN_UP);
        code_do = LanguageUtils.getCurrentDown(context).get(Constant.CODE_DOWN);
        code_up = LanguageUtils.getCurrentUp(context).get(Constant.CODE_UP);

        lan_do = LanguageUtils.getNameByCode(code_do, context);
        lan_up = LanguageUtils.getNameByCode(code_up, context);


        SharePrefUtil.saveString(context, Constant.LAN_DOWN, lan_do);
        SharePrefUtil.saveString(context, Constant.LAN_UP, lan_up);

        String alert_up = LanguageUtils.getCurrentUp(context).get(Constant.ALERT_UP);
        String alert_do = LanguageUtils.getCurrentDown(context).get(Constant.ALERT_DOWN);
        tvUpStandard.setText(alert_up);
        tvDownStandard.setText(alert_do);
        tvUpLan.setText(lan_up);
        tvDownLan.setText(lan_do);
        tvUpSpeak.setText(alert_up);
        tvDownSpeak.setText(alert_do);

        //注册监听屏幕变化广播
        mScreenReceiver = new ScreenBroadcastReceiver();
        mScreenReceiver.registerScreenBroadcastReceiver(context);
        mScreenReceiver.setScreenListener(this);
        /*引导页*/
//        mHandler.postDelayed(mRunnable, 500);
//        initPopupwindow(CommonUtils.getVersion(context));
    }


    /**
     * 初始化修改我方语言的popupwindow
     */
    private void initPopupwindow(View view) {
        popu1 = view;
        tv_I_kown1 = popu1.findViewById(R.id.tv_I_kown1);
        tv_I_kown2 = popu1.findViewById(R.id.tv_I_kown2);
        tv_I_kown3 = popu1.findViewById(R.id.tv_I_kown3);
        tv_I_kown4 = popu1.findViewById(R.id.tv_I_kown4);
        tv_I_kown5 = popu1.findViewById(R.id.tv_I_kown5);
        rl1 = popu1.findViewById(R.id.rl1);
        rl2 = popu1.findViewById(R.id.rl2);
        rl3 = popu1.findViewById(R.id.rl3);
        rl4 = popu1.findViewById(R.id.rl4);
        rl5 = popu1.findViewById(R.id.rl5);
        rl1.setVisibility(View.VISIBLE);


    }

    private Runnable mRunnable = new Runnable() {
        public void run() {
            // 弹出PopupWindow的具体代码

//            Log.e("samebool", "=====" + SharePrefUtil.getBoolean(context, "isfirstTime", true));
            if (SharePrefUtil.getBoolean(context, "isfirstTime", true)) {
                popupWindowShow(popu1);
                initpopu_onclick();
                SharePrefUtil.saveBoolean(context, "isfirstTime", false);
            }
        }
    };

    /**
     * 底部弹出popupWindow
     */
    private void popupWindowShow(View popu) {
        popupWindow = new PopupWindow(popu, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(llRoot, Gravity.CENTER, 0, 0);
    }

    private void initpopu_onclick() {
        tv_I_kown1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl1.setVisibility(View.GONE);
                rl2.setVisibility(View.VISIBLE);
            }
        });
        tv_I_kown2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl2.setVisibility(View.GONE);
                rl3.setVisibility(View.VISIBLE);
            }
        });
        tv_I_kown3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl3.setVisibility(View.GONE);
                rl4.setVisibility(View.VISIBLE);
            }
        });
        tv_I_kown4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl4.setVisibility(View.GONE);
                rl5.setVisibility(View.VISIBLE);
            }
        });
        tv_I_kown5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }


    private void init() {
        tvDownEnglish.setVisibility(View.GONE);
        tvDownStandard.setVisibility(View.VISIBLE);
        tvDownText.setVisibility(View.GONE);
        ivDownCount.setVisibility(View.GONE);
        llDownSpeak.setVisibility(View.GONE);
        llDownSound.setVisibility(View.GONE);
        ivDownSound.setVisibility(View.GONE);


        tvUpEnglish.setVisibility(View.GONE);
        tvUpStandard.setVisibility(View.VISIBLE);
        tvUpText.setVisibility(View.GONE);
        ivUpCount.setVisibility(View.GONE);
        llUpSpeak.setVisibility(View.GONE);
        llUpSound.setVisibility(View.GONE);
        ivUpSound.setVisibility(View.GONE);
//        lan_do = LanguageUtils.getCurrentDown(context).get(Constant.LAN_DOWN);
//        lan_up = LanguageUtils.getCurrentUp(context).get(Constant.LAN_UP);
        code_do = LanguageUtils.getCurrentDown(context).get(Constant.CODE_DOWN);
        code_up = LanguageUtils.getCurrentUp(context).get(Constant.CODE_UP);
        lan_do = LanguageUtils.getNameByCode(code_do, context);
        lan_up = LanguageUtils.getNameByCode(code_up, context);
        String alert_up = LanguageUtils.getCurrentUp(context).get(Constant.ALERT_UP);
        String alert_do = LanguageUtils.getCurrentDown(context).get(Constant.ALERT_DOWN);
        tvUpStandard.setText(alert_up);
        tvDownStandard.setText(alert_do);
        tvUpSpeak.setText(alert_up);
        tvDownSpeak.setText(alert_do);
        tvUpLan.setText(lan_up);
        tvDownLan.setText(lan_do);

    }


    @Override
    public void onResume() {
        super.onResume();
        BaseApplication.isIpsil = "Ipsil";
        netChanged();
    }


    public void onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case DOWN_KEY:
//                L.e("oldReadnum==============" + translate.oldReadnum + "===========readnum:" + translate.readnum);
                int a = translate.oldReadnum;
                translate.readnum++;
                if (!CommonUtils.isAvailable()) {
                    if (!BaseApplication.isTran) {
                        if (ThreadPoolManager.getInstance().getCarryNum() == 0) {
                            BaseApplication.isTran = true;
                        } else {
//                            L.e("正在执行的线程===============" + ThreadPoolManager.getInstance().getCarryNum());
                            ToastUtil.showShort(getString(R.string.zhengzaishibie));
                            return;
                        }
                    }
                    if (isJumpLanguage()) {
                        ChangeOffline.getInstance().test();
                        return;
                    }
                    if (!ChangeOffline.getInstance().isContansAsr()) {
                        return;
                    }
//                    L.e("CODE_DOWN==========" + LanguageUtils.getCurrentDown(getContext()).get(Constant.CODE_DOWN) + "==========CODE_UP=====" + LanguageUtils.getCurrentUp(getContext()).get(Constant.CODE_UP));
                    if (!ChangeOffline.getInstance().lanMap.containsKey(LanguageUtils.getCurrentDown(getContext()).get(Constant.CODE_DOWN) + ">" + LanguageUtils.getCurrentUp(getContext()).get(Constant.CODE_UP))) {
//                        String up_name = LanguageUtils.getCurrentUp(getContext()).get(Constant.LAN_UP);
//                        String down_name = LanguageUtils.getCurrentDown(getContext()).get(Constant.LAN_DOWN);
                        ToastUtil.showShort(getString(R.string.buzhichi));
                        return;
                    } else {

                        if (isJumpLanguage()) {
                            ChangeOffline.getInstance().test();
                            return;
                        }

                    }
                }
                sendAudio(DOWN_KEY);
//                L.e("=======================down_down=========================");
                break;
            case UP_KEY:
                translate.readnum++;
                if (!CommonUtils.isAvailable()) {
                    if (!BaseApplication.isTran) {
                        if (ThreadPoolManager.getInstance().getCarryNum() == 0) {
                            BaseApplication.isTran = true;
                        } else {
//                            L.e("正在执行的线程===============" + ThreadPoolManager.getInstance().getCarryNum());
                            ToastUtil.showShort(getString(R.string.zhengzaishibie));
                            return;
                        }
                    }
                    if (isJumpLanguage()) {
                        ChangeOffline.getInstance().test();
                        return;
                    }
                    if (!ChangeOffline.getInstance().isContansAsr()) {
                        return;
                    }
//                    L.e("==========CODE_UP=====" + LanguageUtils.getCurrentUp(getContext()).get(Constant.CODE_UP) + "====CODE_DOWN==========" + LanguageUtils.getCurrentDown(getContext()).get(Constant.CODE_DOWN));
//                    L.e("====================" + LanguageUtils.getCurrentUp(getContext()).get(Constant.CODE_UP) + ">" + LanguageUtils.getCurrentDown(getContext()).get(Constant.CODE_DOWN));
                    if (!ChangeOffline.getInstance().lanMap.containsKey(LanguageUtils.getCurrentUp(getContext()).get(Constant.CODE_UP) + ">" + LanguageUtils.getCurrentDown(getContext()).get(Constant.CODE_DOWN))) {
//                        String up_name = LanguageUtils.getCurrentUp(getContext()).get(Constant.LAN_UP);
//                        String down_name = LanguageUtils.getCurrentDown(getContext()).get(Constant.LAN_DOWN);
                        ToastUtil.showShort(getString(R.string.buzhichi));
                        return;
                    } else {

                        if (isJumpLanguage()) {
                            ChangeOffline.getInstance().test();
                            return;
                        }

                    }
                }
                sendAudio(UP_KEY);
//                L.e("=======================down_up=========================");
                break;
            default:
                break;
        }

    }

    /**
     * 按键抬起事件
     *
     * @param keyCode
     * @param event
     */
    public void onKeyUp(int keyCode, KeyEvent event) {
        if (isTimeOut) {
            isRecording = false;
            return;
        }

        switch (keyCode) {
            case DOWN_KEY:
                isRecording = false;
                animationDrawableDown.stop();
                stopCountDownTimer();
                llUpSpeak.setVisibility(View.GONE);
                llDownSpeak.setVisibility(View.GONE);
                ivDownCount.setVisibility(View.GONE);
                if (CommonUtils.isWake(context)) {
                    translate.stop(LanguageUtils.getCurrentDown(getContext()).get(Constant.CODE_DOWN), LanguageUtils.getCurrentUp(getContext()).get(Constant.CODE_UP), DOWN_KEY);
                }
                break;
            case UP_KEY:
                isRecording = false;
                animationDrawableUp.stop();
                stopCountDownTimer();
                llUpSpeak.setVisibility(View.GONE);
                llDownSpeak.setVisibility(View.GONE);
                ivUpCount.setVisibility(View.GONE);
                if (CommonUtils.isWake(context)) {
                    translate.stop(LanguageUtils.getCurrentUp(getContext()).get(Constant.CODE_UP), LanguageUtils.getCurrentDown(getContext()).get(Constant.CODE_DOWN), UP_KEY);
                }

                break;
            default:
                break;
        }

    }


    /**
     * 发送语音
     *
     * @param key
     */
    private void sendAudio(int key) {
        asrtext = "";
        isTimeOut = false;
        isRecording = true;
        Constant.isSound = true;
//        L.e("tvUpText========================"+tvUpText);
        tvUpText.setVisibility(View.GONE);
        tvDownText.setVisibility(View.GONE);
        llDownSound.setVisibility(View.GONE);
        llUpSound.setVisibility(View.GONE);
        tvUpEnglish.setVisibility(View.GONE);
        tvDownEnglish.setVisibility(View.GONE);
        ivUpSound.setVisibility(View.GONE);
        ivDownSound.setVisibility(View.GONE);

        stopCountDownTimer();
        //判定当前点击的是上键还是下键
        curr_press = key;
        if (curr_press == DOWN_KEY) {
            code_from = LanguageUtils.getCurrentDown(getActivity()).get(Constant.CODE_DOWN);
            code_to = LanguageUtils.getCurrentUp(getActivity()).get(Constant.CODE_UP);
        } else {
            code_to = LanguageUtils.getCurrentDown(getActivity()).get(Constant.CODE_DOWN);
            code_from = LanguageUtils.getCurrentUp(getActivity()).get(Constant.CODE_UP);
        }


        if (CommonUtils.isAvailable()) {
            DEFAULT = Constant.DEFAULT;
        } else {
            DEFAULT = Constant.OFFLINE_DEFAULT;
        }
        //启动录音动画和屏幕比例变化动画和倒计时
        startAnimation(key);


        translate.sendAudio(code_from, code_to, key, "Ipsil");
    }

    /**
     * 执行动画
     *
     * @param key
     */
    private void startAnimation(int key) {
        //上面的提示语隐藏
        tvUpStandard.setVisibility(View.GONE);
        //下面的提示语隐藏
        tvDownStandard.setVisibility(View.GONE);
        if (key == DOWN_KEY) {
            llUpSound.setVisibility(View.GONE);
            ivUpSound.setVisibility(View.GONE);
            //录音动画显示
            llDownSpeak.setVisibility(View.VISIBLE);
            ivDownSpeak.setVisibility(View.VISIBLE);
            //动画
            AnimationUtils.start(rlUp, rlUp.getHeight(), llRoot.getHeight() * 0.25f, 100, null, null);
            //对方人物动画消失
            llUpSpeak.setVisibility(View.GONE);
            //启动录音动画
            animationDrawableDown.start();
            //启动倒计时
            countDown(key, animationCountDown);
        } else {
            llDownSound.setVisibility(View.GONE);
            ivDownSound.setVisibility(View.GONE);
            //录音动画显示
            llUpSpeak.setVisibility(View.VISIBLE);
            ivUpSpeak.setVisibility(View.VISIBLE);
            tvUpSpeak.setVisibility(View.VISIBLE);
            //动画
            AnimationUtils.start(rlUp, rlUp.getHeight(), llRoot.getHeight() * 0.75f, 100, null, null);
            //对方人物动画消失
            llDownSpeak.setVisibility(View.GONE);
            //启动录音动画
            animationDrawableUp.start();
            //启动倒计时
            countDown(key, animationCountUp);
        }
    }

    /**
     * 倒计时动画
     *
     * @param key
     */
    private void countDown(final int key, final AnimationDrawable animationDrawable) {
        long currTime = System.currentTimeMillis();
        if (currTime - oldTime < 500) {
            oldTime = currTime;
            return;
        }
        oldTime = currTime;

        countDownTimer = new CountDownTimer(DEFAULT, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (5 == millisUntilFinished / 1000) {
                    switch (key) {
                        case DOWN_KEY:
                            animationDrawableDown.stop();
                            ivDownSpeak.setVisibility(View.GONE);
                            ivDownCount.setVisibility(View.VISIBLE);
                            break;
                        case UP_KEY:
                            animationDrawableUp.stop();
                            ivUpSpeak.setVisibility(View.GONE);
                            ivUpCount.setVisibility(View.VISIBLE);
                            break;
                        default:
                            break;
                    }
                    animationDrawable.start();
                }

            }

            @Override
            public void onFinish() {
                stopCountDownTimer();
                translate.stop("", "", 0);
                isTimeOut = true;
                isRecording = false;
                llUpSpeak.setVisibility(View.GONE);
                ivUpCount.setVisibility(View.GONE);
                llDownSpeak.setVisibility(View.GONE);
                ivDownCount.setVisibility(View.GONE);
            }
        };
        countDownTimer.start();
    }


    /**
     * 取消并停止倒计时动画
     */
    private void stopCountDownTimer() {
        if (null != countDownTimer) {
            countDownTimer.cancel();
            countDownTimer = null;
        }

        animationCountUp.stop();
        animationCountDown.stop();
        animationDrawableDown.stop();
        animationDrawableUp.stop();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_down_lan:
                if (!CommonUtils.isAvailable()) {
                    if (isJumpLanguage()) {
                        ChangeOffline.getInstance().test();
                        return;
                    }
                }
                selectLan(DOWN_KEY, lan_do);
                break;
            case R.id.tv_up_lan:
                if (!CommonUtils.isAvailable()) {
                    if (isJumpLanguage()) {
                        ChangeOffline.getInstance().test();
                        return;
                    }
                }
                selectLan(UP_KEY, lan_up);
                break;
            case R.id.iv_switch:
                StatisticsManager.getInstance(context).addEventAidl(1305);
                toOppos();
                break;
            case R.id.iv_down_sound:
            case R.id.iv_up_sound:
                playAudio();
                break;
            case R.id.tv_down_text:
                if (curr_press == DOWN_KEY) {
//                    toEdit(code_from, code_to, asrtext);
                    return;
                } else {
                    playAudio();
                }
                break;
            case R.id.tv_up_text:
                if (curr_press == UP_KEY) {
//                    toEdit(code_from, code_to, asrtext);
                    return;
                } else {
                    playAudio();
                }

                break;
            case R.id.iv_record:
                StatisticsManager.getInstance(context).addEventAidl(1306);
                toRecord();
                break;

        }
    }


    /**
     * 播放翻译内容
     */
    private void playAudio() {
        Map<String, String> map = new HashMap<>();
        map.put("p1", code_to);
        StatisticsManager.getInstance(context).addEventAidl(1304);
        if (isOnline) {
            MediaPlayerUtil.playMp3(SharePrefUtil.getString(context, "mp3_1", ""), context);
        } else {
            TTSUtil.playText(code_to, mtText);
        }
    }

    /**
     * 跳转到异侧
     */
    private void toOppos() {
        if (isRecording)
            return;
        if (!BaseApplication.isTran)
            return;
        BaseApplication.isIpsil = "Oppos";
        Constant.isSound = false;
        activity.showFragment(1);
    }


    /**
     * 跳转历史记录
     */
    private void toRecord() {
        if (isRecording)
            return;
        if (!BaseApplication.isTran)
            return;
        Intent intent = new Intent();
        intent.setClass(context, RecordActivity.class);
        startActivity(intent);
    }


    /**
     * 跳转编辑
     */
    private void toEdit(String from, String to, String text) {
        if (!CommonUtils.isAvailable()) {
            //判定模型是否加载成功
            if (isJumpLanguage()) {
                ChangeOffline.getInstance().test();
                return;
            }
        }
        if (isRecording)
            return;
        if (!BaseApplication.isTran)
            return;
//        if (TextUtils.isEmpty(text))
//            Toast.makeText(context, "不能为空！", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra("text", text);
        intent.putExtra("from", from);
        intent.putExtra("to", to);
        intent.setClass(context, EditActivity.class);
        startActivityForResult(intent, 101);
    }


    /**
     * 跳转到语言选择类 选择语言
     *
     * @param key
     * @param name
     */
    private void selectLan(int key, String name) {
        if (isRecording)
            return;
        if (!BaseApplication.isTran)
            return;
        BaseApplication.isIpsil = "Lan";
        MediaPlayerUtil.release();
        Constant.isSound = false;
        Intent intent = new Intent(context, LanguageActivity.class);
        intent.putExtra("lan_key", key);
        intent.putExtra("lan_name", name);
        //少个参数 同异侧问清华
        //同侧 req100  res200   或可能需加同异侧标识
        startActivityForResult(intent, 100);

    }


    @Override
    public void setEnglish(String text) {
        if (code_to.contains("en") || code_from.contains("en")) {//判定当前翻译结果如果为英文就不需要显示下面的小的英文
            tvUpEnglish.setVisibility(View.GONE);
            tvDownEnglish.setVisibility(View.GONE);
            return;
        }
        switch (curr_press) {
            case DOWN_KEY:
                rlUpText.setVisibility(View.VISIBLE);
                tvUpEnglish.setVisibility(View.VISIBLE);
                tvUpEnglish.setText(text);
                break;
            case UP_KEY:
                rlDownText.setVisibility(View.VISIBLE);
                tvDownEnglish.setVisibility(View.VISIBLE);
                tvDownEnglish.setText(text);
                break;
            default:
                break;
        }
    }

    @Override
    public void setAsr(String text, int flag) {
        asrtext = text;
        try {
            stopCountDownTimer();
            isOnline = flag == Constant.FLAG_ONLINE ? true : false;
            switch (curr_press) {
                case DOWN_KEY:
                    llDownSpeak.setVisibility(View.GONE);
                    tvDownStandard.setVisibility(View.GONE);
                    tvDownEnglish.setVisibility(View.GONE);
                    rlDownText.setVisibility(View.VISIBLE);
                    tvDownText.setVisibility(View.VISIBLE);
                    tvDownText.setTextSize(TypedValue.COMPLEX_UNIT_PX, 46);
                    tvDownText.setText(text);
                    tvDownText.setTextColor(context.getColor(R.color.asr));
                    break;
                case UP_KEY:
                    llUpSpeak.setVisibility(View.GONE);
                    tvUpStandard.setVisibility(View.GONE);
                    tvUpEnglish.setVisibility(View.GONE);
                    rlUpText.setVisibility(View.VISIBLE);
                    tvUpText.setVisibility(View.VISIBLE);
                    tvUpText.setTextSize(TypedValue.COMPLEX_UNIT_PX, 46);
                    tvUpText.setText(text);
                    tvUpText.setTextColor(context.getColor(R.color.asr));

                    break;
                default:
                    break;
            }

        } catch (Exception e) {

        }

    }

    @Override
    public void setMt(String text, int flag) {

        try {
            mtText = "";
            mtText = text;
            stopCountDownTimer();
            switch (curr_press) {
                case DOWN_KEY:
                    llUpSpeak.setVisibility(View.GONE);
                    tvUpStandard.setVisibility(View.GONE);
                    llUpSound.setVisibility(View.VISIBLE);
                    ivUpSound.setVisibility(View.VISIBLE);
                    rlUpText.setVisibility(View.VISIBLE);
                    tvUpText.setVisibility(View.VISIBLE);
                    tvUpText.setTextSize(TypedValue.COMPLEX_UNIT_PX, 46);
                    tvUpText.setText(text);
                    tvUpText.setTextColor(context.getColor(R.color.mt));
                    if (TextUtils.equals("1", SharePrefUtil.getString(context, Constant.SOUND_UP, "1"))) {
                        ivUpSound.setImageDrawable(context.getDrawable(R.mipmap.horn));
                    } else {
                        ivUpSound.setImageDrawable(context.getDrawable(R.mipmap.horn_no));
                    }

                    //动画
                    AnimationUtils.start(rlUp, rlUp.getHeight(), llRoot.getHeight() * 0.7f, 100, null, null);
                    break;
                case UP_KEY:
                    L.e("setMT===========" + text);
                    llDownSpeak.setVisibility(View.GONE);
                    tvDownStandard.setVisibility(View.GONE);
                    llDownSound.setVisibility(View.VISIBLE);
                    ivDownSound.setVisibility(View.VISIBLE);
                    rlDownText.setVisibility(View.VISIBLE);
                    tvDownText.setVisibility(View.VISIBLE);
                    tvDownText.setTextSize(TypedValue.COMPLEX_UNIT_PX, 46);
                    tvDownText.setText(text);
                    tvDownText.setTextColor(context.getColor(R.color.mt));
                    if (TextUtils.equals("1", SharePrefUtil.getString(context, Constant.SOUND_DOWN, "1"))) {
                        ivDownSound.setImageDrawable(context.getDrawable(R.mipmap.horn));
                    } else {
                        ivDownSound.setImageDrawable(context.getDrawable(R.mipmap.horn_no));
                    }
                    //动画
                    AnimationUtils.start(rlUp, rlUp.getHeight(), llRoot.getHeight() * 0.3f, 100, null, null);
                    break;
                default:
                    break;
            }

        } catch (Exception e) {

        }


    }


    @Override
    public void reset() {
        //翻译或者识别失败了重置界面
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    tvDownEnglish.setVisibility(View.GONE);
                    tvDownStandard.setVisibility(View.VISIBLE);
                    tvDownText.setVisibility(View.GONE);
                    ivDownCount.setVisibility(View.GONE);
                    llDownSpeak.setVisibility(View.GONE);
                    llDownSound.setVisibility(View.GONE);
                    ivDownSound.setVisibility(View.GONE);


                    tvUpEnglish.setVisibility(View.GONE);
                    tvUpStandard.setVisibility(View.VISIBLE);
                    tvUpText.setVisibility(View.GONE);
                    ivUpCount.setVisibility(View.GONE);
                    llUpSpeak.setVisibility(View.GONE);
                    llUpSound.setVisibility(View.GONE);
                    ivUpSound.setVisibility(View.GONE);
                    AnimationUtils.start(rlUp, rlUp.getHeight(), llRoot.getHeight() * 0.5f, 100, null, null);
                    stopCountDownTimer();
                    animationDrawableDown.stop();
                    animationDrawableUp.stop();

                } catch (Exception e) {

                }

            }
        });
    }

    /**
     * 在这里去处sp里的东西   所以得使用startActivityForResult();  在onResume每次执行一遍效率低下
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 200) {//选择语言
            AnimationUtils.start(rlUp, rlUp.getHeight(), llRoot.getHeight() * 0.5f, 100, null, null);
            init();
            //无网络实现离线模型切换
            if (!CommonUtils.isAvailable()) {
                netChanged();
                ChangeOffline.getInstance().createOrChange();
            }
            //添加统计
            Map<String, String> map = new HashMap<>();
            map.put("p1", lan_do);
            map.put("p2", lan_up);
            StatisticsManager.getInstance(context).addEventAidl(1311, map);

        } else if (requestCode == 101 && resultCode == 201) {//编辑修改页面返回设置参数
            String mt = data.getStringExtra("mt");
            String en = data.getStringExtra("en");
            String asr = data.getStringExtra("asr");
            int flag = data.getIntExtra("flag", 0);
            translate.resetListener();
            setMt(mt, flag);
            setAsr(asr, flag);
            setEnglish(en);
            ivUpSound.setVisibility(View.GONE);
            ivDownSound.setVisibility(View.GONE);
            if (flag == Constant.FLAG_ONLINE) {
                FileUtils.deleteCacheFile();
            }
        }

    }


    @Override
    public void wake() {
        reset();
    }

    @Override
    public void sleep() {
        reset();
        translate.stopPlay();
    }


    @Override
    public void onPause() {
        super.onPause();
        translate.stopPlay();
        SocketManger.getInstance().disconnect();
        translate.stop("", "", curr_press);
        stopCountDownTimer();
        translate.readnum++;
        L.e("Ipsil==========================onPause============================");
    }


    @Override
    public void onStop() {
        super.onStop();
        L.e("Ipsil==========================onStop============================");
        translate.readnum++;
        translate.stopPlay();
        stopCountDownTimer();
        translate.stop("", "", curr_press);
        SocketManger.getInstance().disconnect();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        L.e("==========================IpsilateralFragment onDestroyView============================");
        unbinder.unbind();
        //注销屏幕监听广播
        mScreenReceiver.unRegisterScreenBroadcastReceiver(context);
    }


    public boolean isJumpLanguage() {
        if (ChangeOffline.getInstance().getMapNum() > 0) {
            ToastUtil.showShort(getString(R.string.chuangjian));
            return true;
        }

        return false;
    }


    /**
     * 网络变化监听在Mainactivity中调用
     */
    public void netChanged() {
        if (CommonUtils.isAvailable()) {
            tvUpOff.setVisibility(View.GONE);
            tvDownOff.setVisibility(View.GONE);
        } else {
            tvUpOff.setVisibility(View.VISIBLE);
            tvDownOff.setVisibility(View.VISIBLE);
            tvUpOff.setText(LanguageUtils.getCurrentUp(context).get(Constant.OFFLINE_UP));
            tvDownOff.setText(LanguageUtils.getCurrentDown(context).get(Constant.OFFLINE_DOWN));
        }
    }

    /**
     * 获取当前是否正在录音
     */
    public boolean getIsRecording() {

        return isRecording;
    }


}
