package com.aibabel.tucao.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.aibabel.tucao.BuildConfig;
import com.aibabel.tucao.R;
import com.aibabel.tucao.beans.Constans;
import com.aibabel.tucao.beans.Record;
import com.aibabel.tucao.manager.AudioRecordButton;
import com.aibabel.tucao.manager.MediaManager;
import com.aibabel.tucao.okgo.BaseBean;
import com.aibabel.tucao.okgo.BaseCallback;
import com.aibabel.tucao.okgo.OkGoUtil;
import com.aibabel.tucao.utils.CommonUtils;
import com.aibabel.tucao.utils.MyScrollview;
import com.aibabel.tucao.utils.NetUtil;
import com.aibabel.tucao.utils.ToastUtil;
import com.aibabel.tucao.utils.WeizhiUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.taobao.sophix.SophixManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main2Activity extends BaseActivity implements BaseCallback, AudioRecordButton.OnLongListener {

    @BindView(R.id.sl_root)
    MyScrollview slRoot;
    @BindView(R.id.iv_close)
    ImageView tvClose;
    @BindView(R.id.tv_tel_title)
    TextView tvTelTitle;
    @BindView(R.id.tv_weixin_title)
    TextView tvWeixinTitle;
    @BindView(R.id.ab_audio)
    AudioRecordButton abAudio;
    @BindView(R.id.rv_records)
    RecyclerView rvRecords;
    @BindView(R.id.tv_satisfied)
    TextView tvSatisfied;
    @BindView(R.id.rb_satisfied)
    RadioButton rbSatisfied;
    @BindView(R.id.rb_unSatisfied)
    RadioButton rbUnSatisfied;
    @BindView(R.id.rg_question)
    RadioGroup rgQuestion;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.tv_question)
    TextView tvQuestion;
    @BindView(R.id.cb_answer1)
    CheckBox cbAnswer1;
    @BindView(R.id.cb_answer2)
    CheckBox cbAnswer2;
    @BindView(R.id.cb_answer3)
    CheckBox cbAnswer3;
    @BindView(R.id.cb_answer4)
    CheckBox cbAnswer4;
    @BindView(R.id.ll_question)
    LinearLayout llQuestion;


    List<Record> mRecords = new ArrayList<>();
    private String iccid;
    private String radioText;
    private String checkText1;
    private String checkText2;
    private String checkText3;
    private String checkText4;
    private boolean isFind;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvClose.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        abAudio.setOnLongListener(this);
        initData();
    }

    private void initData() {


        rexiufu();

//        abAudio.setHasRecordPromission(true);
//        abAudio.setAudioFinishRecorderListener(new AudioRecordButton.AudioFinishRecorderListener() {
//            @Override
//            public void onFinished(float seconds, String filePath) {
//                Log.e("filePath=====", filePath);
//                Record recordModel = new Record();
//                recordModel.setSecond((int) seconds <= 0 ? 1 : (int) seconds);
//                recordModel.setPath(filePath);
//                recordModel.setPlayed(false);
//                if (mRecords.size() < 3) {
//                    mRecords.add(recordModel);
//                    mExampleAdapter.notifyDataSetChanged();
//                    mEmLvRecodeList.setSelection(mRecords.size() - 1);
//                    initAudioRecord(filePath);
//                } else {
//                    ToastUtil.showShort(this, "亲，最多支持3条吐槽录音");
//                }
//
//            }
//        });
    }

    public void rexiufu() {
        String latitude = WeizhiUtil.getInfo(this, WeizhiUtil.CONTENT_URI_WY, "latitude");
        String longitude = WeizhiUtil.getInfo(this, WeizhiUtil.CONTENT_URI_WY, "longitude");
        String url = Constans.HOST_XW + "/v1/jonersystem/GetAppNew?sn=" + CommonUtils.getSN() + "&no=" + CommonUtils.getRandom() + "&sl=" + CommonUtils.getLocalLanguage() + "&av=" + BuildConfig.VERSION_NAME + "&app=" + getPackageName() + "&sv=" + Build.DISPLAY + "&lat=" + latitude + "&lng=" + longitude;

        OkGo.<String>get(url)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("热修复", response.body().toString());
                        if (!TextUtils.isEmpty(response.body().toString())) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().toString());
                                boolean isNew = (Boolean) ((JSONObject) jsonObject.get("data")).get("isNew");
                                if (isNew) {
                                    SophixManager.getInstance().queryAndLoadNewPatch();
                                    Log.e("success:", "=================" + isNew + "=================");
                                } else {
                                    Log.e("failed:", "=================" + isNew + "=================");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("Exception:", "==========" + e.getMessage() + "===========");
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                    }
                });
    }


    private void initAudioRecord(String filePath) {

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void initMoreIccid() {
        initMtkDoubleSim();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void initMtkDoubleSim() {
        try {
            List<SubscriptionInfo> list = SubscriptionManager.from(this).getActiveSubscriptionInfoList();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getSimSlotIndex() == 1) {
                    Log.e("iccid123", list.get(i).getIccId());
                    iccid = list.get(i).getIccId();
                    isFind = true;
                    return;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.iv_close:
                finish();
                break;
            case R.id.rb_satisfied:
                radioText = rbSatisfied.getText().toString();
                break;
            case R.id.rb_unSatisfied:
                radioText = rbUnSatisfied.getText().toString();
                break;
            case R.id.tv_submit:
                if (NetUtil.isNetworkAvailable(this)) {
                    Map<String, String> map = new HashMap<>();
                    String display = Build.DISPLAY;
                    map.put("iccid", iccid);
                    map.put("DeviceVersion", display);
                    map.put("Answer1", radioText);
                    map.put("Answer2", checkText1);
                    map.put("Answer3", checkText2);
                    map.put("Answer4", checkText3);
                    map.put("Answer5", checkText4);
                    map.put("QuestionVer", "321");
                    OkGoUtil.<BaseBean>get(this, Constans.METHOD_SHANGCHUANGWULUYIN, map, BaseBean.class, this);
                } else {
                    ToastUtil.showShort(this, getResources().getString(R.string.isnonet));
                }
                break;
        }
    }

    @Override
    public void onScrolltrue() {

    }

    @Override
    public void onScrollfalse() {

    }


    @Override
    protected void onPause() {
        MediaManager.release();//保证在退出该页面时，终止语音播放
        super.onPause();
    }


    @Override
    public void stopMedia() {

    }

    @Override
    public void onSuccess(String method, BaseBean model) {

    }

    @Override
    public void onError(String method, Response<String> response) {

    }
}
