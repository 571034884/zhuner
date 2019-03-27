package com.aibabel.translate.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.baselibrary.base.StatisticsBaseActivity;
import com.aibabel.translate.R;
import com.aibabel.translate.activity.BaseActivity;
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
import com.aibabel.translate.utils.L;
import com.aibabel.translate.utils.LanguageUtils;
import com.aibabel.translate.utils.MediaPlayerUtil;
import com.aibabel.translate.utils.SharePrefUtil;
import com.aibabel.translate.utils.ThreadPoolManager;
import com.aibabel.translate.utils.ToastUtil;
import com.aibabel.translate.view.AdaptionSizeTextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * ==========================================================================================
 *
 * @Author：CreateBy 张文颖
 * @Date：2018/8/3
 * @Desc：异侧模式 ==========================================================================================
 */
public class OppositeFragment extends BaseFragment implements OnResponseListener, ScreenBroadcastReceiver.ScreenListener, SensorEventListener {


    //按键keycode
    private final static int UP_KEY = 132;
    private final static int DOWN_KEY = 131;
    @BindView(R.id.tv_up_lan)
    TextView tvUpLan;
    @BindView(R.id.iv_up_sound)
    ImageView ivUpSound;
    @BindView(R.id.tv_up_off)
    TextView tvUpOff;
    @BindView(R.id.ll_up_sound)
    LinearLayout llUpSound;
    //    @BindView(R.id.tv_up_standard)
    TextView tvUpStandard;
    @BindView(R.id.iv_up_speak)
    ImageView ivUpSpeak;
    @BindView(R.id.iv_up_count)
    ImageView ivUpCount;
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
    @BindView(R.id.sv_up_text)
    ScrollView svUpText;
    @BindView(R.id.rl_up_content)
    RelativeLayout rlUpContent;
    @BindView(R.id.rl_up)
    RelativeLayout rlUp;
    @BindView(R.id.mView)
    View mView;
    @BindView(R.id.iv_down_sound)
    ImageView ivDownSound;
    @BindView(R.id.tv_down_off)
    TextView tvDownOff;
    @BindView(R.id.ll_down_sound)
    LinearLayout llDownSound;
    @BindView(R.id.tv_down_standard)
    TextView tvDownStandard;
    @BindView(R.id.iv_down_speak)
    ImageView ivDownSpeak;
    @BindView(R.id.iv_down_count)
    ImageView ivDownCount;
    @BindView(R.id.tv_down_speak)
    TextView tvDownSpeak;
    @BindView(R.id.ll_down_speak)
    LinearLayout llDownSpeak;
    @BindView(R.id.tv_down_text)
    AdaptionSizeTextView tvDownText;
    @BindView(R.id.tv_down_English)
    TextView tvDownEnglish;
    @BindView(R.id.rl_down_text)
    RelativeLayout rlDownText;
    @BindView(R.id.iv_switch)
    ImageView ivSwitch;
    @BindView(R.id.tv_down_lan)
    TextView tvDownLan;
    @BindView(R.id.rl_down_lan)
    RelativeLayout rlDownLan;
    @BindView(R.id.rl_down)
    RelativeLayout rlDown;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    Unbinder unbinder;
    @BindView(R.id.iv_record)
    ImageView ivRecord;

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
    private SensorManager manager;
    private Sensor sensor;
    private float y;
    private int direction_up = 0;
    private int direction_down = 0;
    private int color_white;
    private int color_gray;
    private Timer updateTimer;
    private String code_do;
    private String code_up;
    private boolean isOnline;
    private String mtText;
      private String asrtext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_opposite, container, false);
        tvUpStandard = view.findViewById(R.id.tv_up_standard);
        unbinder = ButterKnife.bind(this, view);
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

        /**
         * 初始化数据
         */
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
        tvUpLan.setText(lan_up);
        tvDownLan.setText(lan_do);
        tvUpSpeak.setText(alert_up);
        tvDownSpeak.setText(alert_do);
        initSensor();
        color_white = context.getColor(R.color.white);
        color_gray = context.getColor(R.color.gray);
        //注册监听屏幕变化广播
        mScreenReceiver = new ScreenBroadcastReceiver();
        mScreenReceiver.registerScreenBroadcastReceiver(context);
        mScreenReceiver.setScreenListener(this);
        updateTimer = new Timer("gForceUpdate");
        updateTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                updateGUI();
            }
        }, 0, 1000);

    }

    private void updateGUI() {

        try {
            if (isRecording)
                return;
            if (tvUpStandard.getVisibility() == View.VISIBLE && tvDownStandard.getVisibility() == View.VISIBLE) {

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (y > 0) {
                                tvUpStandard.setTextColor(color_gray);
                                tvDownStandard.setTextColor(color_white);
                            } else {
                                tvUpStandard.setTextColor(color_white);
                                tvDownStandard.setTextColor(color_gray);
                            }
                        } catch (Exception e) {

                        }
                    }
                });

                return;
            }
//            L.e("=======================" + isRecording + "=========================");
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        switch (curr_press) {
                            case DOWN_KEY:
                                if (y > 0) {//朝自己
                                    if (direction_down == 1) {
                                        direction_down = 1;
                                        return;
                                    }
                                    direction_down = 1;
                                    L.e("=======================" + 1 + "=========================");
                                    changeColor(1);
                                    AnimationUtils.start(rlUp, rlUp.getHeight(), llRoot.getHeight() * 0.5f, 100, null, null);
                                } else {
                                    if (direction_down == 2) {
                                        direction_down = 2;
                                        return;
                                    }
                                    direction_down = 2;
                                    L.e("=======================" + 2 + "=========================");
                                    changeColor(2);
                                    AnimationUtils.start(rlUp, rlUp.getHeight(), llRoot.getHeight() * 0.7f, 100, null, null);
                                }
                                break;

                            case UP_KEY:
                                if (y > 0 && curr_press == UP_KEY) {//朝自己
                                    if (direction_up == 1) {
                                        direction_up = 1;
                                        return;
                                    }
                                    L.e("=======================" + 3 + "=========================");
                                    direction_up = 1;
                                    changeColor(1);
                                    AnimationUtils.start(rlUp, rlUp.getHeight(), llRoot.getHeight() * 0.3f, 100, null, null);
                                } else {
                                    if (direction_up == 2) {
                                        direction_up = 2;
                                        return;
                                    }
                                    L.e("=======================" + 4 + "=========================");
                                    direction_up = 2;
                                    changeColor(2);
                                    AnimationUtils.start(rlUp, rlUp.getHeight(), llRoot.getHeight() * 0.5f, 100, null, null);
                                }

                                break;
                        }
                    } catch (Exception e) {

                    }

                }
            });

        } catch (Exception e) {

        }


    }

    private void initSensor() {
        // 实例化传感器管理者
        manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        // 初始化加速度传感器
        sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


    }

    private void init() {
        tvDownEnglish.setVisibility(View.GONE);
//        tvDownOff.setVisibility(View.GONE);
        tvDownStandard.setVisibility(View.VISIBLE);
        tvDownText.setVisibility(View.GONE);
        ivDownCount.setVisibility(View.GONE);
        llDownSpeak.setVisibility(View.GONE);
        llDownSound.setVisibility(View.GONE);
        ivDownSound.setVisibility(View.GONE);
//        tvDownOff.setVisibility(View.GONE);


        tvUpEnglish.setVisibility(View.GONE);
//        tvUpOff.setVisibility(View.GONE);
        tvUpStandard.setVisibility(View.VISIBLE);
        tvUpText.setVisibility(View.GONE);
        ivUpCount.setVisibility(View.GONE);
        llUpSpeak.setVisibility(View.GONE);
        llUpSound.setVisibility(View.GONE);
        ivUpSound.setVisibility(View.GONE);
//        tvUpOff.setVisibility(View.GONE);
        code_do = LanguageUtils.getCurrentDown(context).get(Constant.CODE_DOWN);
        code_up = LanguageUtils.getCurrentUp(context).get(Constant.CODE_UP);
        lan_do = LanguageUtils.getNameByCode(code_do, context);
        lan_up = LanguageUtils.getNameByCode(code_up, context);
        SharePrefUtil.saveString(context, Constant.LAN_DOWN, lan_do);
        SharePrefUtil.saveString(context, Constant.LAN_UP, lan_up);
//        lan_do = LanguageUtils.getCurrentDown(context).get(Constant.LAN_DOWN);
//        lan_up = LanguageUtils.getCurrentUp(context).get(Constant.LAN_UP);
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
        BaseApplication.isIpsil = "Oppos";
        manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
        netChanged();
    }

    public void onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case DOWN_KEY:
                translate.readnum++;
                if (!CommonUtils.isAvailable()) {
                    if (!BaseApplication.isTran) {
                        if (ThreadPoolManager.getInstance().getCarryNum() == 0) {
                            BaseApplication.isTran = true;
                        } else {
                            L.e("正在执行的线程===============" + ThreadPoolManager.getInstance().getCarryNum());
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
                    L.e("CODE_DOWN==========" + LanguageUtils.getCurrentDown(getContext()).get(Constant.CODE_DOWN) + "==========CODE_UP=====" + LanguageUtils.getCurrentUp(getContext()).get(Constant.CODE_UP));
                    if (!ChangeOffline.getInstance().lanMap.containsKey(LanguageUtils.getCurrentDown(getContext()).get(Constant.CODE_DOWN) + ">" + LanguageUtils.getCurrentUp(getContext()).get(Constant.CODE_UP))) {
//                        String up_name = LanguageUtils.getCurrentUp(getContext()).get(Constant.LAN_UP);
//                        String down_name = LanguageUtils.getCurrentDown(getContext()).get(Constant.LAN_DOWN);
                        ToastUtil.showShort(getString(R.string.buzhichi));
                        return;
                    } else {
                        //实现模型切换
//                        createOrChange();
//                        ToastUtil.showShort("还在执行的数：" + ChangeOffline.getInstance().getMapNum());

                        if (isJumpLanguage()) {
                            ChangeOffline.getInstance().test();
                            return;
                        }

                    }
                }
                sendAudio(DOWN_KEY);
                break;
            case UP_KEY:
                translate.readnum++;
                if (!CommonUtils.isAvailable()) {
                    if (!BaseApplication.isTran) {
                        if (ThreadPoolManager.getInstance().getCarryNum() == 0) {
                            BaseApplication.isTran = true;
                        } else {
                            L.e("正在执行的线程===============" + ThreadPoolManager.getInstance().getCarryNum());
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
                    L.e("==========CODE_UP=====" + LanguageUtils.getCurrentUp(getContext()).get(Constant.CODE_UP) + "====CODE_DOWN==========" + LanguageUtils.getCurrentDown(getContext()).get(Constant.CODE_DOWN));
                    L.e("====================" + LanguageUtils.getCurrentUp(getContext()).get(Constant.CODE_UP) + ">" + LanguageUtils.getCurrentDown(getContext()).get(Constant.CODE_DOWN));
                    if (!ChangeOffline.getInstance().lanMap.containsKey(LanguageUtils.getCurrentUp(getContext()).get(Constant.CODE_UP) + ">" + LanguageUtils.getCurrentDown(getContext()).get(Constant.CODE_DOWN))) {
//                        String up_name = LanguageUtils.getCurrentUp(getContext()).get(Constant.LAN_UP);
//                        String down_name = LanguageUtils.getCurrentDown(getContext()).get(Constant.LAN_DOWN);
                        ToastUtil.showShort(getString(R.string.buzhichi));
                        return;
                    } else {
                        //实现模型切换
//                        createOrChange();
//                        ToastUtil.showShort("还在执行的数：" + ChangeOffline.getInstance().getMapNum());
                        if (isJumpLanguage()) {
                            ChangeOffline.getInstance().test();
                            return;
                        }

                    }
                }

                sendAudio(UP_KEY);
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
        if (isTimeOut){
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
        isTimeOut = false;
        isRecording = true;
        Constant.isSound = true;
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

        translate.sendAudio(code_from, code_to, key, "Oppos");
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
        countDownTimer = null;
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
                selectLan(DOWN_KEY, lan_do, false);
                break;
            case R.id.tv_up_lan:
                if (!CommonUtils.isAvailable()) {
                    if (isJumpLanguage()) {
                        ChangeOffline.getInstance().test();
                        return;
                    }
                }
                selectLan(UP_KEY, lan_up, true);
                break;
            case R.id.iv_switch:
                StatisticsManager.getInstance(context).addEventAidl(1305);
                toIpsil();
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

            case R.id.iv_record:
                StatisticsManager.getInstance(context).addEventAidl(1306);
                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    ((StatisticsBaseActivity)getActivity()).addStatisticsEvent("translation_main12", null);
                }catch (Exception e){
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/

                toRecord();
                break;
        }
    }

    /**
     * 播放翻译内容
     */
    private void playAudio() {
        Map<String, String> map = new HashMap<>();
        map.put("p1",code_to);
        StatisticsManager.getInstance(context).addEventAidl(1304);
        /**####  start-hjs-addStatisticsEvent   ##**/
        try {
            ((StatisticsBaseActivity)getActivity()).addStatisticsEvent("translation_main11", null);
        }catch (Exception e){
            e.printStackTrace();
        }
        /**####  end-hjs-addStatisticsEvent  ##**/
        if (isOnline) {
            MediaPlayerUtil.playMp3(SharePrefUtil.getString(context, "mp3_1", ""), context);
        } else {
            TTSUtil.playText(code_to, mtText);
        }
    }


    /**
     * 跳转到异侧
     */
    private void toIpsil() {
        if (isRecording)
            return;
        if (!BaseApplication.isTran)
            return;
        BaseApplication.isIpsil = "Ipsil";
        Constant.isSound = false;
        activity.showFragment(0);
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
        intent.setClass(context, com.aibabel.translate.activity.EditActivity.class);
        startActivityForResult(intent, 101);
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
     * 跳转到语言选择类 选择语言
     *
     * @param key
     * @param name
     */
    private void selectLan(int key, String name, boolean isUp) {
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
        if (isUp)
            intent.putExtra("flipp", true);
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
            if (flag == Constant.FLAG_ONLINE) {
                isOnline = true;
            } else {
                isOnline = false;
            }
            switch (curr_press) {
                case DOWN_KEY:
                    llUpSpeak.setVisibility(View.GONE);
                    tvUpStandard.setVisibility(View.GONE);
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
                    if (y > 0) {
                        AnimationUtils.start(rlUp, rlUp.getHeight(), llRoot.getHeight() * 0.5f, 100, null, null);
                        changeColor(1);
                    } else {
                        changeColor(2);
                        AnimationUtils.start(rlUp, rlUp.getHeight(), llRoot.getHeight() * 0.7f, 100, null, null);
                    }
                    break;
                case UP_KEY:
                    llDownSpeak.setVisibility(View.GONE);
                    tvDownStandard.setVisibility(View.GONE);
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
                    if (y > 0) {
                        AnimationUtils.start(rlUp, rlUp.getHeight(), llRoot.getHeight() * 0.3f, 100, null, null);
                    } else {
                        AnimationUtils.start(rlUp, rlUp.getHeight(), llRoot.getHeight() * 0.5f, 100, null, null);
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {

        }


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
        if (requestCode == 100 && resultCode == 200) {
            AnimationUtils.start(rlUp, rlUp.getHeight(), llRoot.getHeight() * 0.5f, 100, null, null);
            init();
            //无网络实现离线模型切换
            if (!CommonUtils.isAvailable()) {
                ChangeOffline.getInstance().createOrChange();
            }

            //添加统计
            Map<String, String> map = new HashMap<>();
            map.put("p1",lan_do);
            map.put("p2",lan_up);
            StatisticsManager.getInstance(context).addEventAidl(1311,map);

            /**####  start-hjs-addStatisticsEvent   ##**/
            try {
                ((StatisticsBaseActivity)getActivity()).addStatisticsEvent("translation_language3", null);
            }catch (Exception e){
                e.printStackTrace();
            }
            /**####  end-hjs-addStatisticsEvent  ##**/

        } else if (requestCode == 101 && resultCode == 201) {
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
                com.aibabel.translate.utils.FileUtils.deleteCacheFile();
            }
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
//                    tvDownOff.setVisibility(View.GONE);
                    tvDownStandard.setVisibility(View.VISIBLE);
                    tvDownText.setVisibility(View.GONE);
                    ivDownCount.setVisibility(View.GONE);
                    llDownSpeak.setVisibility(View.GONE);
                    llDownSound.setVisibility(View.GONE);
                    ivDownSound.setVisibility(View.GONE);
//                    tvDownOff.setVisibility(View.GONE);


                    tvUpEnglish.setVisibility(View.GONE);
//                    tvUpOff.setVisibility(View.GONE);
                    tvUpStandard.setVisibility(View.VISIBLE);
                    tvUpText.setVisibility(View.GONE);
                    ivUpCount.setVisibility(View.GONE);
                    llUpSpeak.setVisibility(View.GONE);
                    llUpSound.setVisibility(View.GONE);
                    ivUpSound.setVisibility(View.GONE);
//                    tvUpOff.setVisibility(View.GONE);
                    stopCountDownTimer();
                    AnimationUtils.start(rlUp, rlUp.getHeight(), llRoot.getHeight() * 0.5f, 100, null, null);
                } catch (Exception e) {


                }


            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        // 解除注册
        translate.readnum++;
        manager.unregisterListener(this);
        stopCountDownTimer();
        translate.stopPlay();
        translate.stop("", "", curr_press);
        SocketManger.getInstance().disconnect();
        L.e("OPPo=======================onPause==========================");
    }

    @Override
    public void onStop() {
        super.onStop();
        L.e("OPPo=======================onstop==========================");
        translate.readnum++;
        manager.unregisterListener(this);
        translate.stopPlay();
        translate.stop("", "", curr_press);
        stopCountDownTimer();
        SocketManger.getInstance().disconnect();
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
    public void onDestroyView() {
        super.onDestroyView();
        L.e("========================== OppositeFragment onDestroyView============================");
        updateTimer.cancel();
        //注销屏幕监听广播
        mScreenReceiver.unRegisterScreenBroadcastReceiver(context);
        manager.unregisterListener(this);
        unbinder.unbind();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // x,y,z分别存储坐标轴x,y,z上的加速度
//            float x = event.values[0];
            y = event.values[1];
//            float z = event.values[2];

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * 根据陀螺仪判定文字颜色
     *
     * @param flag
     */
    private void changeColor(int flag) {

        if (flag == 1) {
            tvDownText.setTextColor(color_white);
            tvDownEnglish.setTextColor(color_white);
            tvUpText.setTextColor(color_gray);
            tvUpEnglish.setTextColor(color_gray);
        } else {
            tvDownText.setTextColor(color_gray);
            tvDownEnglish.setTextColor(color_gray);
            tvUpText.setTextColor(color_white);
            tvUpEnglish.setTextColor(color_white);
        }
        tvDownText.invalidate();
        tvDownEnglish.invalidate();
        tvUpText.invalidate();
        tvUpEnglish.invalidate();
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
     * 是否可以跳转语言列表
     *
     * @return
     */
    public boolean isJumpLanguage() {
        if (ChangeOffline.getInstance().getMapNum() > 0) {
            ToastUtil.showShort(getString(R.string.chuangjian));
            return true;
        }

        return false;
    }


    /**
     * 获取当前是否正在录音
     */
    public boolean getIsRecording(){

        return isRecording;
    }

}
