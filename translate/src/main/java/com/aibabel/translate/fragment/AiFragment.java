package com.aibabel.translate.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
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
import com.aibabel.translate.bean.ErrorResultBean;
import com.aibabel.translate.bean.MessageBean;
import com.aibabel.translate.bean.Returnjson;
import com.aibabel.translate.bean.AsrAndMtBean;
import com.aibabel.translate.sqlite.AiSqlUtils;
import com.aibabel.translate.utils.CheckTimerTask;
import com.aibabel.translate.utils.CommonUtils;
import com.aibabel.translate.utils.Constant;
import com.aibabel.translate.utils.DensityHelper;
import com.aibabel.translate.utils.FastJsonUtil;
import com.aibabel.translate.utils.L;
import com.aibabel.translate.utils.MediaPlayerUtil;
import com.aibabel.translate.utils.SharePrefUtil;
import com.aibabel.translate.utils.StringUtils;
import com.aibabel.translate.utils.ToastUtil;
import com.aibabel.translate.view.CommonDialog;
import com.aibabel.translate.view.MyAlertDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import proto.Dls;
import proto.ResultOuterClass;

import static com.aibabel.translate.utils.Constant.DEFAULT;
import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;

/**
 * @==========================================================================================
 * @Author： 张文颖
 * @Date：2019/3/5
 * @Desc：智能识别
 * @==========================================================================================
 */
public class AiFragment extends BaseFragment implements BaseQuickAdapter.OnItemLongClickListener, ChatAdapter.ChatOnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.rv_chat)
    RecyclerView rvChat;
    Unbinder unbinder;
    @BindView(R.id.tv_left_lan)
    TextView tvLeftLan;
    @BindView(R.id.tv_right_lan)
    TextView tvRightLan;
    @BindView(R.id.common_toolbar)
    RelativeLayout commonToolbar;
    @BindView(R.id.cl_delete)
    ConstraintLayout clDelete;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;


    private ImageView ivAudioAnim;
    private ImageView ivCountAnim;
    private ImageView ivProgressAnim;
    private TextView tvAsr;
    private TextView tvMt;
    private View line;
    private ConstraintLayout clMt;
    private ConstraintLayout clAsr;
    private LinearLayout llAudio;
    private LinearLayout llFailed;

    private Context context;
    private AnimationDrawable animationCountDown;
    private AnimationDrawable animationAsr;
    private AnimationDrawable animationMt;
    private CountDownTimer countDownTimer;
    private boolean isTimeOut = false;
    private boolean isRecording = false;
    private long oldTime;
    private ChatAdapter mAdapter;
    private List<MessageBean> list = new ArrayList<MessageBean>();
    private boolean isShowCheck;
    private boolean isSelectAll;
    private List<Integer> checkList = new ArrayList<>();
    private int page = 1;
    private int pagesize = 5;
    //======================以下部分以后再替换========================
    List<byte[]> array_record_data_blank = new ArrayList<byte[]>();
    private AudioRecord mRecorder;
    private int mMinBufferSize;
    private int id;
    //数据存储类变量
    private Dls.SpeechInfo speechInfo;
    private Date time;
    private View headerView;
    private View footerView;
    private String from;
    private String to;
    private long returnTime;
    private Timer timer;
    private CheckTimerTask task;
    private WebSocketClient webSocket;
    //    private WebSocketClient webSocket;
    private String TAG = AiFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ai, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void initView() {
        context = getActivity();

        //TODO 测试弹出 上线请注释
        SharePrefUtil.saveBoolean(context,"guideView",false);

        LinearLayoutManager mLinearLayout = new LinearLayoutManager(context);
        rvChat.setLayoutManager(mLinearLayout);
        page = 1;
        list = AiSqlUtils.retrieve(page, pagesize);
        mAdapter = new ChatAdapter(context, list);
        rvChat.setAdapter(mAdapter);
        footerView = getFooterView();
        headerView = getHeaderView();
        mAdapter.addFooterView(footerView, 0);
        if (list.size() == 0)
            mAdapter.addHeaderView(headerView);

        rvChat.scrollToPosition(mAdapter.getItemCount() - 1);
        animationAsr = (AnimationDrawable) ivAudioAnim.getDrawable();
        animationMt = (AnimationDrawable) ivProgressAnim.getDrawable();
        animationCountDown = (AnimationDrawable) ivCountAnim.getDrawable();

        /**
         * 1.获取引导标识
         * 2.判断引导标识进行是否显示Dialog
         */
        isShowDialogOne();


    }

    private void isShowDialogOne() {
        boolean guideView = SharePrefUtil.getBoolean(context,"guideView",false);
        if (guideView){
            return;
        }
        View view = getLayoutInflater().inflate(R.layout.dialog_guide_one, null);
        final MyAlertDialog builders = new MyAlertDialog(context, 0, 0, view, R.style.dialog);
        builders.setCancelable(false);
        TextView mDialogOk = view.findViewById(R.id.dialog_ok);
        mDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builders.dismiss();
                isShowDialogTwo();
            }
        });

        builders.show();
    }

    private void isShowDialogTwo() {
        View view = getLayoutInflater().inflate(R.layout.dialog_guide_two, null);
        final MyAlertDialog builders = new MyAlertDialog(context, 0, 0, view, R.style.dialog);
        builders.setCancelable(false);
        TextView mDialogOk = view.findViewById(R.id.dialog_ok);
        LinearLayout mLayoutIs = view.findViewById(R.id.dialog_isshow);
        final ImageView mImageIs = view.findViewById(R.id.dialog_is);
        final TextView mTextDes = view.findViewById(R.id.dialog_des);

        mLayoutIs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isShow = SharePrefUtil.getBoolean(context,"guideView",false);
                if (isShow){
                    mImageIs.setImageResource(R.mipmap.ic_checket_default);
                    mTextDes.setTextColor(context.getResources().getColor(R.color.gray));
                    SharePrefUtil.saveBoolean(context,"guideView",false);
                }else{
                    mImageIs.setImageResource(R.mipmap.ic_checket_select);
                    mTextDes.setTextColor(context.getResources().getColor(R.color.c_33));
                    SharePrefUtil.saveBoolean(context,"guideView",true);
                }
            }
        });
        mDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builders.dismiss();
            }
        });

        builders.show();
    }

    @Override
    public void initData() {
        ivMenu.setOnClickListener(this);
        tvAll.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        tvDelete.setOnClickListener(this);
        mAdapter.setOnItemLongClickListener(this);
        mAdapter.setChatOnItemClickListener(this);
        swipeLayout.setOnRefreshListener(this);
        //初始化录音的硬件
        initRecord();
    }

    private void initRecord() {
        mMinBufferSize = AudioRecord.getMinBufferSize(16000, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
        mRecorder = new AudioRecord(MediaRecorder.AudioSource.MIC, 16000, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, mMinBufferSize * 2);
    }

    /**
     * 获取footer布局
     *
     * @return
     */
    private View getFooterView() {
        View view = getLayoutInflater().inflate(R.layout.audio_layout, (ViewGroup) rvChat.getParent(), false);
        SwipeRefreshLayout.LayoutParams params = view.getLayoutParams();
        //获取当前控件的布局对象
        params.height = DensityHelper.getSystemWH(activity).get("height") * 2 / 3;//设置当前控件布局的高度
        view.setLayoutParams(params);//将设置好的布局参数应用到控件中
        Log.e("height", "=" + params.height);
        ivAudioAnim = view.findViewById(R.id.iv_audio_anim);
        ivCountAnim = view.findViewById(R.id.iv_count_anim);
        ivProgressAnim = view.findViewById(R.id.iv_progress_anim);
        tvAsr = view.findViewById(R.id.tv_ai_asr);
        tvMt = view.findViewById(R.id.tv_ai_mt);
        line = view.findViewById(R.id.v_tran_line);
        llAudio = view.findViewById(R.id.ll_audio);
        clAsr = view.findViewById(R.id.cl_ai_asr);
        clMt = view.findViewById(R.id.cl_ai_mt);
        llFailed = view.findViewById(R.id.ll_failed);
        return view;
    }

    /**
     * 获取空布局
     *
     * @return
     */
    private View getHeaderView() {
        View view = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) rvChat.getParent(), false);
        SwipeRefreshLayout.LayoutParams params = view.getLayoutParams();

        //获取当前控件的布局对象
        params.height = (DensityHelper.getSystemWH(activity).get("height") * 1 / 3) - 80;//设置当前控件布局的高度

        Log.e("EmptyViewHeight", "=" + params.height);
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
                sendAudio();
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
     */
    private void sendAudio() {
        showErrorUI(false);
        if (!CommonUtils.isAvailable()) {
            ToastUtil.showShort("当前网络状况不佳,请切换到语音翻译!");
            return;
        }
        // TODO: 2019/3/10 更新UI 并请求新的识别翻译
        updateMsg();
        isTimeOut = false;
        //启动实时录音功能
        if (!isRecording) {
            Log.e("123", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            connect();
        }
        time = new Date();
        startAnimation();

    }


    /**
     * 执行动画
     */
    private void startAnimation() {
        /**
         * 录音等待动画
         */
        ivAudioAnim.setVisibility(View.VISIBLE);
        tvAsr.setVisibility(View.GONE);
        clMt.setVisibility(View.GONE);
        line.setVisibility(View.GONE);
        tvAsr.setText(" ");
        tvMt.setText(" ");
        animationAsr.start();
        countDown();
    }

    /**
     * 倒计时动画
     */
    private void countDown() {
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
                    stopAnimAsr();
                    ivCountAnim.setVisibility(View.VISIBLE);
                    animationCountDown.start();
                }

            }

            @Override
            public void onFinish() {
                stopCountDownTimer();
                isTimeOut = true;
                isRecording = false;
            }
        };
        countDownTimer.start();
    }


    /**
     * 停止录音动画
     */
    private void stopAnimAsr() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                animationAsr.stop();
                ivAudioAnim.setVisibility(View.GONE);
            }
        });

    }

    /**
     * 开始翻译等待动画
     */
    private void startAnimMt() {
        line.setVisibility(View.VISIBLE);
        clMt.setVisibility(View.VISIBLE);
        ivProgressAnim.setVisibility(View.VISIBLE);
        animationMt.start();
    }


    /**
     * 停止翻译等待动画
     */
    private void stopAnimMt() {
        animationMt.stop();
        ivProgressAnim.setVisibility(View.GONE);
    }


    /**
     * 取消并停止倒计时动画
     */
    private void stopCountDownTimer() {
        if (null != countDownTimer) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        ivCountAnim.setVisibility(View.GONE);
        animationCountDown.stop();
        animationAsr.stop();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_menu:
                activity.showDrawerLayout(0);
                break;
            case R.id.tv_cancel:
                cancel();
                break;
            case R.id.tv_all:
                selectAll();
                break;
            case R.id.tv_delete:
                delete();
                break;

        }
    }


    /**
     * 播放翻译内容
     */
    private void playAudio() {
        MediaPlayerUtil.playMp3(SharePrefUtil.getString(context, "mp3_1", ""), context);
    }


    public void reset() {
        //重置界面

    }


    /**
     * 全选
     */
    private void selectAll() {
        mAdapter.setNewData(list);
        checkList.clear();
        for (int i = 0; i < list.size(); i++) {
            checkList.add(i);
        }
        Log.e("list的size,ai", list.size() + "");
        mAdapter.checkAll(true);
    }

    /**
     * 取消
     */
    public void cancel() {
        clDelete.setVisibility(View.GONE);
        tvCancel.setVisibility(View.GONE);
        checkList.clear();
        isShowCheck = false;
        mAdapter.setNewData(list);
        mAdapter.setCheckBoxVisibility(false);

    }

    /**
     * 删除
     */
    private void delete() {
        final CommonDialog dialog = new CommonDialog(activity);
        dialog.setMessage(activity.getString(R.string.delete_record));
        dialog.setOnBtnClickListener(new CommonDialog.OnBtnClickListener() {
            @Override
            public void onCancelClick() {
                if (dialog.isShowing())
                    dialog.dismiss();
            }

            @Override
            public void onConfirmClick() {
                try {
                    if (checkList.size() > 0) {
                        for (int i = 0, j = 0; i < checkList.size(); i++, j++) {
                            int index = checkList.get(i);
                            AiSqlUtils.deleteById(list.remove(index - j).getId());
                        }
                        mAdapter.setNewData(list);
                        if (list.size() == 0)
                            mAdapter.addHeaderView(headerView);
                        mAdapter.setCheckBoxVisibility(false);
                        checkList.clear();
                        isShowCheck = false;
                        cancel();
                        if (dialog.isShowing())
                            dialog.dismiss();
                    } else {
                        ToastUtil.showShort("您还没有选中任何记录！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        dialog.show();

    }

    @Override
    public void onItemClick(View view, int position) {
        if (isShowCheck) {
            if (checkList.contains(Integer.valueOf(position))) {
                checkList.remove(Integer.valueOf(position));
            } else {
                checkList.add(position);
            }
        }
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

        if (!isShowCheck) {
            tvCancel.setVisibility(View.VISIBLE);
            mAdapter.setCheckBoxVisibility(true);
            clDelete.setVisibility(View.VISIBLE);
            isShowCheck = true;
        }
        Toast.makeText(context, "长按了item", Toast.LENGTH_SHORT).show();
        return false;
    }


    @Override
    public void onPause() {
        super.onPause();
        L.e("ai==========================onPause============================");
        cancelTimer();

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


    /**
     * 开始录音，并且实时检测当前是否有人在讲话（实时静音检测）。
     *
     * @param webSocket
     */
    public void startRecord(final WebSocketClient webSocket) {
        isRecording = true;
        new Thread(new Runnable() {
            @Override
            public void run() {

                Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO);
                int readSize;
                if (mRecorder.getState() != AudioRecord.STATE_INITIALIZED) {
                    return;
                }
                mRecorder.startRecording();

                while (isRecording) {
                    byte[] audioData = new byte[mMinBufferSize];
                    if (null != mRecorder) {
                        readSize = mRecorder.read(audioData, 0, mMinBufferSize);
                        if (readSize != 0 && readSize != -1) {
                            //发送数据
                            Dls.Speech speech = Dls.Speech.newBuilder().setFinished(false).setVoice(ByteString.copyFrom(audioData)).build();
                            webSocket.send(speech.toByteArray());

                            if (array_record_data_blank.size() > 10) {
                                array_record_data_blank.remove(0);
                            }
                            array_record_data_blank.add(audioData);


                            if (new Date().getTime() - time.getTime() > 50000) {
                                isRecording = false;
                            }
                        }
                    }
                }
                Dls.Speech speech_end = Dls.Speech.newBuilder().setFinished(true).setVoice(ByteString.copyFrom(new byte[1])).build();
                webSocket.send(speech_end.toByteArray());
                if (mRecorder != null) {
                    mRecorder.stop();
                    mRecorder = null;
                    initRecord();
                }
                if (isRecording) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            connect();
                        }
                    });
                }
            }
        }).start();
    }


    private void connect() {
        webSocket = null;
        webSocket = new WebSocketClient(URI.create("ws://52.192.220.183:8082/cnSpeechV1test/audio/dls")) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.e(">>>>>>>>>>>>>>", "connect success");
                id++;
                speechInfo = StringUtils.getSpeechInfo(id, "", "", context);
                this.send(speechInfo.toByteArray());
                for (int i = 0; i < array_record_data_blank.size(); i++) {
                    Dls.Speech speech = Dls.Speech.newBuilder().setFinished(false).setVoice(ByteString.copyFrom(array_record_data_blank.get(i))).build();
                    this.send(speech.toByteArray());
                }
                array_record_data_blank.clear();
                time = new Date();
                startRecord(this);
            }

            @Override
            public void onMessage(String s) {
                Log.e(">>>>>>>>>>>>>>", "connect msg" + s);
            }

            @Override
            public void onMessage(ByteBuffer s) {
                try {
                    //解析数据
                    ResultOuterClass.Result result = ResultOuterClass.Result.parseFrom(s.array());
                    Log.e(">>>>>>>>>>>>>>>>>>>", "================" + result.getMsg());
                    Log.e(">>>>>>>>>>>>>>>>>>>", "================" + result.getCode());
                    Message msg = new Message();
                    switch (result.getCode()) {
                        case Constant.RESPONSE_ASR://识别结果
                            AsrAndMtBean bean = FastJsonUtil.changeJsonToBean(result.getMsg(), AsrAndMtBean.class);
                            Returnjson returnjson = FastJsonUtil.changeJsonToBean(bean.getInfo(), Returnjson.class);
                            msg.what = Constant.RESPONSE_ASR;
                            //判断数据是否为最后一句
                            if (returnjson.getResults().get(0).isIs_final()) {
                                msg.arg2 = 2;
                                checkTimeOut();
                            } else {
                                msg.arg2 = 1;
                            }
                            //判断数据是否从中文翻译到英文
                            if (StringUtils.isChinese(returnjson.getResults().get(0).getAlternatives().get(0).getTranscript())) {
                                msg.arg1 = 1;
                            } else {
                                msg.arg1 = 2;
                            }
                            //String类型
                            msg.obj = returnjson.getResults().get(0).getAlternatives().get(0).getTranscript();
                            break;
                        case Constant.RESPONSE_MT://翻译
                            cancelTimer();
                            msg.what = Constant.RESPONSE_MT;
                            AsrAndMtBean mtBean = FastJsonUtil.changeJsonToBean(result.getMsg(), AsrAndMtBean.class);
                            //String类型
                            msg.obj = mtBean.getInfo();
                            break;
                        case Constant.RESPONSE_TTS://合成
                            msg.what = Constant.RESPONSE_TTS;
                            AsrAndMtBean ttsBean = FastJsonUtil.changeJsonToBean(result.getMsg(), AsrAndMtBean.class);
                            //String类型
                            msg.obj = ttsBean.getInfo();
                            Log.e(TAG,ttsBean.getInfo()+"----");
                            result.getBuffer();
                            Log.e(TAG,  String.valueOf(result.getBuffer()) +"----");
                            break;
                        case Constant.RESPONSE_NULL://没有识别到任何音频
                            msg.what = Constant.RESPONSE_NULL;
                            break;
                        case Constant.RESPONSE_ERROR://后台返回错误
                            msg.what = Constant.RESPONSE_ERROR;
                            ErrorResultBean errorResultBean = FastJsonUtil.changeJsonToBean(result.getMsg(), ErrorResultBean.class);
                            //int 类型
                            msg.obj = errorResultBean.getCode();
                            break;
                        default:
                            break;
                    }
                    handler.sendMessage(msg);
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.e(">>>>>>>>>>>>>>", "connect close" + s);
                stopAnimAsr();
            }

            @Override
            public void onError(Exception e) {
                Log.e(">>>>>>>>>>>>>>", "connect error:" + e.getMessage());
                stopAnimAsr();
            }
        };
        webSocket.connect();
    }


    private String temp = "";
    //用于线程接收数据，处理识别返回的结果
    Handler handler = new Handler() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void handleMessage(final Message msg) {

            switch (msg.what) {
                case Constant.RESPONSE_ASR:
                    if (!msg.obj.equals("")) {
                        temp = (String) msg.obj;
                    }
                    //判断是否是已经识别结束了，识别未结束直接显示，识别结束调用翻译
                    if (msg.arg2 == 1) {
                        //控件显示结果
                        setAsr(temp);

                    } else if (msg.arg2 == 2) {
                        //控件显示结果
                        setAsr(temp);
                        //设置翻译的源语言和目标语言
                        from = "zh";
                        to = "en";
                        if (msg.arg1 == 2) {
                            from = "en";
                            to = "zh";
                        }
                        startAnimMt();
                        //调用翻译
//                        translation(from, to, temp);

                    }
                    break;
                case Constant.RESPONSE_NULL:
                    ToastUtil.showShort("没有检测到音频输入，请您再说一次！");
                    break;
                case Constant.RESPONSE_MT:
                    String mt = (String) msg.obj;
                    if (!TextUtils.isEmpty(mt)) {
                        setMt(mt);
                    } else {
                        ServerError(102);
                    }
                    break;
                case Constant.RESPONSE_ERROR:
                    int code = (Integer) msg.obj;
                    ServerError(code);
                    break;
                case Constant.TIMEOUT_READ:
                    ToastUtil.showShort("网络请求超时了，请重新尝试！");
                    showErrorUI(true);
                    stopAnimMt();
                    break;
                default:
                    break;


            }
        }
    };


    private void translation(String from, String to, String text) {
        if (!CommonUtils.isAvailable()) {
            ToastUtil.showShort("当前网络状况不佳,请切换到语音翻译!");
            return;
        }
        OkHttpClient client = new OkHttpClient();
        FormBody formBody = new FormBody
                .Builder()
                .add("from", from)
                .add("to", to)
                .add("text", text)
                .build();
        final Request request = new Request.Builder()
                .url("http://47.94.21.248:5050/trans")
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        stopAnimMt();
                        cancelTimer();
                        Log.e("123", "");
                    }
                });

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String responseStr = response.body().string();
                final MessageBean transBean = FastJsonUtil.changeJsonToBean(responseStr, MessageBean.class);
                Log.e("123", transBean.getTrans_result());
                runOnUiThread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void run() {

                        setMt(transBean.getTrans_result());
                    }
                });
            }
        });
    }

    public void setAsr(String text) {
        // TODO: 2019/3/7 在回来数据的地方调用这个这些方法
        ivAudioAnim.setVisibility(View.GONE);
        animationAsr.stop();
        tvAsr.setVisibility(View.VISIBLE);
        tvAsr.setText(text);
    }


    public void setMt(String text) {
        stopAnimMt();
        returnTime = System.currentTimeMillis();
        tvMt.setVisibility(View.VISIBLE);
        tvMt.setText(text);
    }


    private void updateMsg() {
        rvChat.setFocusable(true);
        rvChat.requestFocus();

        MessageBean bean = new MessageBean();
        String mt = tvMt.getText().toString();
        String asr = tvAsr.getText().toString();
        bean.setTrans_text(asr);
        bean.setTrans_result(mt);
        bean.setFrom(from);
        bean.setTo(to);
        bean.setTime(returnTime);
        L.e("=======================asr：" + asr + "=========================");
        L.e("=======================Mt：" + mt + "=========================");
        if (TextUtils.isEmpty(asr) || TextUtils.isEmpty(mt) || TextUtils.equals(asr, " ") || TextUtils.equals(mt, " ")) {
            rvChat.scrollToPosition(mAdapter.getItemCount() - 1);
            return;
        }

        list.add(bean);
        mAdapter.setNewData(list);
        rvChat.scrollToPosition(mAdapter.getItemCount() - 1);
        // TODO: 2019/3/10存入数据库
        AiSqlUtils.insertData(bean);
        if (list.size() > 0)
            mAdapter.removeHeaderView(headerView);

    }


    @Override
    public void onRefresh() {
        //下拉刷新获取历史消息
        page++;
        List<MessageBean> mReceiveMsgList = AiSqlUtils.retrieve(page, pagesize);
        swipeLayout.setRefreshing(false);
        if (mReceiveMsgList.size() <= 0) {
            ToastUtil.showShort("没有更多数据了");
            return;
        }
        mAdapter.addData(0, mReceiveMsgList);
        mAdapter.notifyDataSetChanged();
    }


    /**
     * 检查翻译是否超时，时间为5秒钟
     */
    private void checkTimeOut() {
        try {
            timer = new Timer();
            task = new CheckTimerTask(handler);
            timer.schedule(task, Constant.TIME_OUT);
        } catch (Exception e) {
            Log.e("timer", e.getMessage());
        }
    }

    private void cancelTimer() {
        if (null != timer)
            timer.cancel();
//        if (null != webSocket)
//            webSocket.close();

    }


    private void showErrorUI(boolean isShow) {
        if (isShow) {
            llAudio.setVisibility(View.GONE);
            llFailed.setVisibility(View.VISIBLE);
        } else {
            llAudio.setVisibility(View.VISIBLE);
            llFailed.setVisibility(View.GONE);
        }

    }


    /**
     * 服务器返回错误54中 包含的101/102/103/104
     *
     * @param code
     */
    private void ServerError(int code) {
        cancelTimer();
        L.e("cmd:", String.valueOf(code));
        switch (code) {
            case 101://识别失败
                showErrorUI(true);
                break;
            case 102://翻译失败
                stopAnimMt();
                showErrorUI(true);
                break;
            case 103://合成失败

                break;
            case 104://参数不对

                break;
            default:
                break;
        }
    }
}


