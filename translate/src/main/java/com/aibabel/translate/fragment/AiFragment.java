package com.aibabel.translate.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aibabel.translate.R;
import com.aibabel.translate.adapter.ChatAdapter;
import com.aibabel.translate.app.BaseApplication;
import com.aibabel.translate.bean.MessageBean;
import com.aibabel.translate.socket.TranslateUtil;
import com.aibabel.translate.utils.Constant;
import com.aibabel.translate.utils.DensityHelper;
import com.aibabel.translate.utils.L;
import com.aibabel.translate.utils.MediaPlayerUtil;
import com.aibabel.translate.utils.SharePrefUtil;
import com.aibabel.translate.utils.ToastUtil;
import com.aibabel.translate.view.MyRecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.aibabel.translate.utils.Constant.DEFAULT;

/**
 * @==========================================================================================
 * @Author： 张文颖
 * @Date：2019/3/5
 * @Desc：智能识别
 * @==========================================================================================
 */
public class AiFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.llContent)
    LinearLayout llContent;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_chat)
    RecyclerView rvChat;
    //    @BindView(R.id.sl_chat)
//    SwipeRefreshLayout slChat;
    Unbinder unbinder;
    private Context context;
    private AnimationDrawable animationCountDown;
    private AnimationDrawable animationAsr;
    private AnimationDrawable animationMt;
    private boolean isTimeOut = false;
    private boolean isRecording = false;
    private CountDownTimer countDownTimer;
    public TranslateUtil translate;
    private int curr_press;
    private long oldTime;
    private ChatAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ai, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void initView() {
        context = getActivity();
        ivMenu.setOnClickListener(this);
        llContent.setOnClickListener(this);
        mAdapter = new ChatAdapter(context, new ArrayList<MessageBean>());
        LinearLayoutManager mLinearLayout = new LinearLayoutManager(context);
        rvChat.setLayoutManager(mLinearLayout);
        View footerView = getFooterView();
        View emptyView = getEmptyView();
        mAdapter.addFooterView(footerView, 0);
        rvChat.setAdapter(mAdapter);
        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(context, "长按了item", Toast.LENGTH_SHORT).show();
                return false;
            }

        });
        mAdapter.setEmptyView(emptyView);
//        slChat.setOnRefreshListener(this);
    }

    @Override
    public void initData() {
//        L.e("isl   indata=================================");


    }

    private View getFooterView() {
        View view = getLayoutInflater().inflate(R.layout.audio_layout, (ViewGroup) rvChat.getParent(), false);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        //获取当前控件的布局对象
        params.height = DensityHelper.getSystemWH(activity).get("height") * 2 / 3;//设置当前控件布局的高度
        view.setLayoutParams(params);//将设置好的布局参数应用到控件中

        ImageView audioAnim = view.findViewById(R.id.iv_audio_anim);
        ImageView countAnim = view.findViewById(R.id.iv_count_anim);
        ImageView progressAnim = view.findViewById(R.id.iv_progress_anim);
        TextView tv_asr = view.findViewById(R.id.tv_ai_asr);
        TextView tv_mt = view.findViewById(R.id.tv_ai_mt);
        LinearLayout ll_audio = view.findViewById(R.id.ll_audio);
        LinearLayout ll_failed = view.findViewById(R.id.ll_failed);

        return view;
    }

    private View getEmptyView() {
        View view = getLayoutInflater().inflate(R.layout.audio_layout, (ViewGroup) rvChat.getParent(), false);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        //获取当前控件的布局对象
        params.height = DensityHelper.getSystemWH(activity).get("height") * 1 / 3;//设置当前控件布局的高度
        view.setLayoutParams(params);//将设置好的布局参数应用到控件中
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    public void onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case DOWN_KEY:
            case UP_KEY:
                sendAudio(DOWN_KEY);
                L.e("=======================down=========================");
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
                break;
            case UP_KEY:
                isRecording = false;
                stopCountDownTimer();
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


        stopCountDownTimer();
        //判定当前点击的是上键还是下键
        curr_press = key;
        //启动录音动画和屏幕比例变化动画和倒计时
        startAnimation(key);


//        translate.sendAudio(code_from, code_to, key, "Ipsil");
    }

    /**
     * 执行动画
     *
     * @param key
     */
    private void startAnimation(int key) {

//        if (key == DOWN_KEY) {
//            //对方人物动画消失
//            llUpSpeak.setVisibility(View.GONE);
//            //启动录音动画
//            animationDrawableDown.start();
//            //启动倒计时
//            countDown(key, animationCountDown);
//        } else {
//            //对方人物动画消失
//            llDownSpeak.setVisibility(View.GONE);
//            //启动录音动画
//            animationDrawableUp.start();
//            //启动倒计时
//            countDown(key, animationCountUp);
//        }
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
                    animationDrawable.start();
                }

            }

            @Override
            public void onFinish() {
                stopCountDownTimer();
                translate.stop("", "", 0);
                isTimeOut = true;
                isRecording = false;
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
        animationCountDown.stop();
        animationAsr.stop();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_menu:
                activity.drag();
                break;
            case R.id.llContent:
                ToastUtil.showShort("11111111111111111111111");
                break;

        }
    }

    /**
     * 切换图片
     *
     * @param isOpen
     */
    public void switchMenuIcon(boolean isOpen) {
        System.out.println(isOpen);
        if (isOpen) {
            ivMenu.setImageDrawable(context.getDrawable(R.mipmap.ic_translate_back));
        } else {
            ivMenu.setImageDrawable(context.getDrawable(R.mipmap.ic_translate_menu));
        }
    }


    /**
     * 播放翻译内容
     */
    private void playAudio() {
        MediaPlayerUtil.playMp3(SharePrefUtil.getString(context, "mp3_1", ""), context);

    }


    public void setAsr(String text, int flag) {

    }


    public void setMt(String text, int flag) {


    }


    public void reset() {
        //翻译或者识别失败了重置界面
    }


    @Override
    public void onRefresh() {

    }


    @Override
    public void onPause() {
        super.onPause();
        L.e("Ipsil==========================onPause============================");
    }


    @Override
    public void onStop() {
        super.onStop();
        L.e("Ipsil==========================onStop============================");

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        L.e("==========================IpsilateralFragment onDestroyView============================");
        unbinder.unbind();

    }
}


