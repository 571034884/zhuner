package com.aibabel.tucao.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aibabel.tucao.BuildConfig;
import com.aibabel.tucao.R;
import com.aibabel.tucao.adapter.CommomRecyclerAdapter;
import com.aibabel.tucao.adapter.CommonRecyclerViewHolder;
import com.aibabel.tucao.adapter.ExampleAdapter;
import com.aibabel.tucao.beans.Constans;
import com.aibabel.tucao.beans.Record;
import com.aibabel.tucao.beans.TiltleBeans;
import com.aibabel.tucao.manager.AudioRecordButton;
import com.aibabel.tucao.manager.MediaManager;
import com.aibabel.tucao.okgo.BaseBean;
import com.aibabel.tucao.okgo.BaseCallback;
import com.aibabel.tucao.okgo.OkGoUtil;
import com.aibabel.tucao.utils.CommonUtils;
import com.aibabel.tucao.utils.Constant;
import com.aibabel.tucao.utils.FileUtils;
import com.aibabel.tucao.utils.MyScrollview;
import com.aibabel.tucao.utils.NetUtil;
import com.aibabel.tucao.utils.ToastUtil;
import com.aibabel.tucao.utils.WeizhiUtil;
import com.aibabel.tucao.views.CommonDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.taobao.sophix.SophixManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements BaseCallback, AudioRecordButton.OnLongListener {

    //    @BindView(R.id.tv_tucao)
//    TextView tvTucao;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_number1)
    TextView tvNumber1;
    @BindView(R.id.man_rb)
    RadioButton manRb;
    @BindView(R.id.woman_rb)
    RadioButton womanRb;
    @BindView(R.id.rg_number1)
    RadioGroup rgNumber1;
    @BindView(R.id.rv_number)
    RecyclerView rvNumber;
    //    @BindView(R.id.lin1)
//    LinearLayout lin1;
//    @BindView(R.id.tv_yuyin)
//    TextView tvYuyin;
//    @BindView(R.id.lin2)
//    LinearLayout lin2;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.em_lv_recodeList)
    ListView mEmLvRecodeList;
    @BindView(R.id.em_tv_btn)
    AudioRecordButton mEmTvBtn;
    @BindView(R.id.Scroll)
    MyScrollview Scroll;
    @BindView(R.id.cl)
    LinearLayout cl;
    //    @BindView(R.id.tv_tucao1)
//    TextView tvTucao1;
    @BindView(R.id.iv_close1)
    ImageView ivClose1;
    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.rl_isnet)
    RelativeLayout rlIsnet;
    @BindView(R.id.lin_isnet)
    LinearLayout linIsnet;
    private boolean isMtkDoubleSim;
    private CommomRecyclerAdapter adapter;
    private TiltleBeans titleBeans;
    private BaseBean baseBean;

    private List<TiltleBeans.DataBean.Question2Bean> question2 = new ArrayList<>();
    private TiltleBeans.DataBean titleBeansData;
    private CheckBox cb_item_daan1;
    private CheckBox cb_item_daan2;
    private CheckBox cb_item_daan3;
    private CheckBox cb_item_daan4;
    private CheckBox cb_item_daan5;


    private String RadioText = "";
    private String CheckText1 = "";
    private String CheckText5 = "";
    private String CheckText2 = "";
    private String CheckText3 = "";
    private String CheckText4 = "";
    private String iccid;
    private int RecordCount = 0;
    private boolean isHoldVoiceBtn;
    private int flag = 0;
    private String filePath;
    List<Record> mRecords;
    ExampleAdapter mExampleAdapter;
    int count = 0;
    private ViewTreeObserver vto;
    private static final Uri CONTENT_URI = Uri.parse("content://com.aibabel.locationservice.provider.AibabelProvider/aibabel_location");
    private String countryNameCN;
    private String ips;
    private String key_xw;
    private String key_xs;
    private boolean isFind = false;

    //    private RecorderUtil recorderUtil = new RecorderUtil();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

        try {
            Cursor cursor = getContentResolver().query(CONTENT_URI, null, null, null, null);
            if (null != cursor) {
                cursor.moveToFirst();
                countryNameCN = cursor.getString(2);
                ips = cursor.getString(cursor.getColumnIndex("ips"));
                Log.e("ips", ips);
                if (countryNameCN.equals("中国")) {
                    key_xw = "中国_" + getPackageName() + "_joner";
//                key_xs_payment = "中国_"+"com.aibabel.surfinternet"+"_pay";
                } else {
                    key_xw = "default_" + getPackageName() + "_joner";
//                key_xs_payment = "default_"+"com.aibabel.surfinternet"+"_pay";
                }
                JSONObject jsonObject = new JSONObject(ips);
                JSONArray jsonArray_xw = new JSONArray(jsonObject.getString(key_xw));
                Constans.HOST_XW = jsonArray_xw.getJSONObject(0).get("domain").toString();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        FileUtils.deleteFile(FileUtils.getAppRecordDir(MainActivity.this).getAbsolutePath());
        initAdapter();

        if (NetUtil.isNetworkAvailable(MainActivity.this)) {
            initData();
        } else {
            cl.setVisibility(View.GONE);
            linIsnet.setVisibility(View.VISIBLE);
        }

        initMoreIccid();


        initData1();
        initAdapter1();
        initListener();
//        initEvent();
        mEmTvBtn.setOnLongListener(this);
        rexiufu();


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

    private void initData1() {
        mRecords = new ArrayList<>();
    }

    private void initAdapter1() {
        mExampleAdapter = new ExampleAdapter(this, mRecords);
        mEmLvRecodeList.setAdapter(mExampleAdapter);

//        //开始获取数据库数据
//
//        List<Record> records = new ArrayList<>();
//        if (records == null || records.isEmpty())
//            return;
//        for (Record record : records) {
//            Log.e("wgy", "initAdapter: " + record.toString());
//        }
//        mRecords.addAll(records);
//        mExampleAdapter.notifyDataSetChanged();
//        mEmLvRecodeList.setSelection(mRecords.size() - 1);

    }

    private void initListener() {

        mEmTvBtn.setHasRecordPromission(true);
        mEmTvBtn.setAudioFinishRecorderListener(new AudioRecordButton.AudioFinishRecorderListener() {
            @Override
            public void onFinished(float seconds, String filePath) {
                Log.e("filePath=====", filePath);
                Record recordModel = new Record();
                recordModel.setSecond((int) seconds <= 0 ? 1 : (int) seconds);
                recordModel.setPath(filePath);
                recordModel.setPlayed(false);
                if (mRecords.size() < 3) {
                    mRecords.add(recordModel);
                    mExampleAdapter.notifyDataSetChanged();
                    mEmLvRecodeList.setSelection(mRecords.size() - 1);
                    initAudioRecord(filePath);
                } else {
                    ToastUtil.showShort(MainActivity.this, "亲，最多支持3条吐槽录音");
                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        int width = mEmTvBtn.getMeasuredWidth();
        int height = mEmTvBtn.getMeasuredHeight();
        float x = mEmTvBtn.getLeft();
        float y = mEmTvBtn.getTop();

    }

    private void initAudioRecord(String filePath) {
        if (NetUtil.isNetworkAvailable(MainActivity.this)) {
            File file = new File(filePath);
            Map<String, Object> map = new HashMap<>();
            String display = Build.DISPLAY;
            map.put("iccid", iccid);
            map.put("DeviceVersion", display);
            map.put("Answer1", RadioText);
            map.put("Answer2", CheckText1);
            map.put("Answer3", CheckText2);
            map.put("Answer4", CheckText3);
            map.put("Answer5", CheckText4);
            map.put("QuestionVer", "321");
            map.put("uploadfile", file);
            OkGoUtil.<BaseBean>post(MainActivity.this, Constans.METHOD_SHANGCHUANGLUYIN, map, BaseBean.class, this);
        } else {
            ToastUtil.showShort(MainActivity.this, getResources().getString(R.string.isnonet));
        }
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onPause() {
        MediaManager.release();//保证在退出该页面时，终止语音播放
        super.onPause();
    }

    private void initCheckListener(final CheckBox view, final int position) {
        view.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.e("bbbb", position + "");
                if (position == 0) {
                    switch (buttonView.getId()) {
                        case R.id.cb_item_daan1:
                            String s = question2.get(position).getAnswer().get(0).toString();

                            if (isChecked) {
                                if (CheckText1.equals("")) {
                                    CheckText1 = s + "&";
                                } else {
                                    CheckText1 = CheckText1 + s + "&";
                                }

                            } else {
                                CheckText1 = CheckText1.replaceAll(s + "&", "");
                            }

                            break;
                        case R.id.cb_item_daan2:
                            String s1 = question2.get(position).getAnswer().get(1).toString();
                            if (isChecked) {
                                if (CheckText1.equals("")) {
                                    CheckText1 = s1 + "&";
                                } else {
                                    CheckText1 = CheckText1 + s1 + "&";
                                }

                            } else {
                                CheckText1 = CheckText1.replaceAll(s1 + "&", "");
                            }

                            break;

                        case R.id.cb_item_daan3:
                            String s2 = question2.get(position).getAnswer().get(2).toString();
                            if (isChecked) {
                                if (CheckText1.equals("")) {
                                    CheckText1 = s2 + "&";
                                } else {
                                    CheckText1 = CheckText1 + s2 + "&";
                                }

                            } else {
                                CheckText1 = CheckText1.replaceAll(s2 + "&", "");
                            }

                            break;

                        case R.id.cb_item_daan4:
                            String s3 = question2.get(position).getAnswer().get(3).toString();
                            if (isChecked) {
                                if (CheckText1.equals("")) {
                                    CheckText1 = s3 + "&";
                                } else {
                                    CheckText1 = CheckText1 + s3 + "&";
                                }

                            } else {
                                CheckText1 = CheckText1.replaceAll(s3 + "&", "");
                            }

                            break;
                        case R.id.cb_item_daan5:
                            String s4 = question2.get(position).getAnswer().get(4).toString();
                            if (isChecked)

                            {
                                if (CheckText1.equals("")) {
                                    CheckText1 = s4 + "&";
                                } else {
                                    CheckText1 = CheckText1 + s4 + "&";
                                }

                            } else

                            {
                                CheckText1 = CheckText1.replaceAll(s4 + "&", "");
                            }

                            break;
                    }
                }
                if (position == 1) {
                    switch (buttonView.getId()) {
                        case R.id.cb_item_daan1:
                            String s = question2.get(position).getAnswer().get(0).toString();

                            if (isChecked) {
                                if (CheckText2.equals("")) {
                                    CheckText2 = s + "&";
                                } else {
                                    CheckText2 = CheckText2 + s + "&";
                                }

                            } else {
                                CheckText2 = CheckText2.replaceAll(s + "&", "");
                            }

                            break;
                        case R.id.cb_item_daan2:
                            String s1 = question2.get(position).getAnswer().get(1).toString();
                            if (isChecked) {
                                if (CheckText2.equals("")) {
                                    CheckText2 = s1 + "&";
                                } else {
                                    CheckText2 = CheckText2 + s1 + "&";
                                }

                            } else {
                                CheckText2 = CheckText2.replaceAll(s1 + "&", "");
                            }

                            break;

                        case R.id.cb_item_daan3:
                            String s2 = question2.get(position).getAnswer().get(2).toString();
                            if (isChecked) {
                                if (CheckText2.equals("")) {
                                    CheckText2 = s2 + "&";
                                } else {
                                    CheckText2 = CheckText2 + s2 + "&";
                                }

                            } else {
                                CheckText2 = CheckText2.replaceAll(s2 + "&", "");
                            }

                            break;

                        case R.id.cb_item_daan4:
                            String s3 = question2.get(position).getAnswer().get(3).toString();
                            if (isChecked) {
                                if (CheckText2.equals("")) {
                                    CheckText2 = s3 + "&";
                                } else {
                                    CheckText2 = CheckText2 + s3 + "&";
                                }

                            } else {
                                CheckText2 = CheckText2.replaceAll(s3 + "&", "");
                            }

                            break;
                        case R.id.cb_item_daan5:
                            String s4 = question2.get(position).getAnswer().get(4).toString();
                            if (isChecked)

                            {
                                if (CheckText2.equals("")) {
                                    CheckText2 = s4 + "&";
                                } else {
                                    CheckText2 = CheckText2 + s4 + "&";
                                }

                            } else

                            {
                                CheckText2 = CheckText2.replaceAll(s4 + "&", "");
                            }

                            break;
                    }
                }
                if (position == 3) {
                    switch (buttonView.getId()) {
                        case R.id.cb_item_daan1:
                            String s = question2.get(position).getAnswer().get(0).toString();

                            if (isChecked) {
                                if (CheckText3.equals("")) {
                                    CheckText3 = s + "&";
                                } else {
                                    CheckText3 = CheckText3 + s + "&";
                                }

                            } else {
                                CheckText3 = CheckText3.replaceAll(s + "&", "");
                            }

                            break;
                        case R.id.cb_item_daan2:
                            String s1 = question2.get(position).getAnswer().get(1).toString();
                            if (isChecked) {
                                if (CheckText3.equals("")) {
                                    CheckText3 = s1 + "&";
                                } else {
                                    CheckText3 = CheckText3 + s1 + "&";
                                }

                            } else {
                                CheckText3 = CheckText3.replaceAll(s1 + "&", "");
                            }

                            break;

                        case R.id.cb_item_daan3:
                            String s2 = question2.get(position).getAnswer().get(2).toString();
                            if (isChecked) {
                                if (CheckText3.equals("")) {
                                    CheckText3 = s2 + "&";
                                } else {
                                    CheckText3 = CheckText3 + s2 + "&";
                                }

                            } else {
                                CheckText3 = CheckText3.replaceAll(s2 + "&", "");
                            }

                            break;

                        case R.id.cb_item_daan4:
                            String s3 = question2.get(position).getAnswer().get(3).toString();
                            if (isChecked) {
                                if (CheckText3.equals("")) {
                                    CheckText3 = s3 + "&";
                                } else {
                                    CheckText3 = CheckText3 + s3 + "&";
                                }

                            } else {
                                CheckText3 = CheckText3.replaceAll(s3 + "&", "");
                            }

                            break;
                        case R.id.cb_item_daan5:
                            String s4 = question2.get(position).getAnswer().get(4).toString();
                            if (isChecked)

                            {
                                if (CheckText3.equals("")) {
                                    CheckText3 = s4 + "&";
                                } else {
                                    CheckText3 = CheckText3 + s4 + "&";
                                }

                            } else

                            {
                                CheckText3 = CheckText3.replaceAll(s4 + "&", "");
                            }

                            break;
                    }
                }
                if (position == 4) {
                    switch (buttonView.getId()) {
                        case R.id.cb_item_daan1:
                            String s = question2.get(position).getAnswer().get(0).toString();

                            if (isChecked) {
                                if (CheckText4.equals("")) {
                                    CheckText4 = s + "&";
                                } else {
                                    CheckText4 = CheckText4 + s + "&";
                                }

                            } else {
                                CheckText4 = CheckText4.replaceAll(s + "&", "");
                            }

                            break;
                        case R.id.cb_item_daan2:
                            String s1 = question2.get(position).getAnswer().get(1).toString();
                            if (isChecked) {
                                if (CheckText4.equals("")) {
                                    CheckText4 = s1 + "&";
                                } else {
                                    CheckText4 = CheckText4 + s1 + "&";
                                }

                            } else {
                                CheckText4 = CheckText4.replaceAll(s1 + "&", "");
                            }

                            break;

                        case R.id.cb_item_daan3:
                            String s2 = question2.get(position).getAnswer().get(2).toString();
                            if (isChecked) {
                                if (CheckText4.equals("")) {
                                    CheckText4 = s2 + "&";
                                } else {
                                    CheckText4 = CheckText4 + s2 + "&";
                                }

                            } else {
                                CheckText4 = CheckText4.replaceAll(s2 + "&", "");
                            }

                            break;

                        case R.id.cb_item_daan4:
                            String s3 = question2.get(position).getAnswer().get(3).toString();
                            if (isChecked) {
                                if (CheckText4.equals("")) {
                                    CheckText4 = s3 + "&";
                                } else {
                                    CheckText4 = CheckText4 + s3 + "&";
                                }

                            } else {
                                CheckText4 = CheckText4.replaceAll(s3 + "&", "");
                            }

                            break;
                        case R.id.cb_item_daan5:
                            String s4 = question2.get(position).getAnswer().get(4).toString();
                            if (isChecked)

                            {
                                if (CheckText4.equals("")) {
                                    CheckText4 = s4 + "&";
                                } else {
                                    CheckText4 = CheckText4 + s4 + "&";
                                }

                            } else

                            {
                                CheckText4 = CheckText4.replaceAll(s4 + "&", "");
                            }

                            break;
                    }
                }

            }
        });
    }

    private void initAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        rvNumber.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        adapter = new CommomRecyclerAdapter(this, question2, R.layout.rv_item, new CommomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommonRecyclerViewHolder holder, int postion) {
                Log.e("aaaa", postion + "");

            }

        }, null) {


            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void convert(CommonRecyclerViewHolder holder, Object o, int position) {

                LinearLayout lin_number1 = holder.getView(R.id.lin_number1);
                TextView tv_title1 = holder.getView(R.id.tv_title1);
                cb_item_daan1 = holder.getView(R.id.cb_item_daan1);
                cb_item_daan2 = holder.getView(R.id.cb_item_daan2);
                cb_item_daan3 = holder.getView(R.id.cb_item_daan3);
                cb_item_daan4 = holder.getView(R.id.cb_item_daan4);
                cb_item_daan5 = holder.getView(R.id.cb_item_daan5);
                tvTitle.setText(titleBeansData.getDescriptor());


                tv_title1.setText(((TiltleBeans.DataBean.Question2Bean) o).getQuestionMsg());
                int size = ((TiltleBeans.DataBean.Question2Bean) o).getAnswer().size();
                if (size > 0) {
                    if (size == 1) {
                        cb_item_daan1.setText(((TiltleBeans.DataBean.Question2Bean) o).getAnswer().get(0));
                        cb_item_daan2.setVisibility(View.GONE);
                        cb_item_daan3.setVisibility(View.GONE);
                        cb_item_daan4.setVisibility(View.GONE);
                        cb_item_daan5.setVisibility(View.GONE);

                    }
                    if (size == 2) {
                        cb_item_daan1.setText(((TiltleBeans.DataBean.Question2Bean) o).getAnswer().get(0));
                        cb_item_daan2.setText(((TiltleBeans.DataBean.Question2Bean) o).getAnswer().get(1));
                        cb_item_daan3.setVisibility(View.GONE);
                        cb_item_daan4.setVisibility(View.GONE);
                        cb_item_daan5.setVisibility(View.GONE);
                    }
                    if (size == 3) {
                        cb_item_daan1.setText(((TiltleBeans.DataBean.Question2Bean) o).getAnswer().get(0));
                        cb_item_daan2.setText(((TiltleBeans.DataBean.Question2Bean) o).getAnswer().get(1));
                        cb_item_daan3.setText(((TiltleBeans.DataBean.Question2Bean) o).getAnswer().get(2));
                        cb_item_daan4.setVisibility(View.GONE);
                        cb_item_daan5.setVisibility(View.GONE);
                    }
                    if (size == 4) {
                        cb_item_daan1.setText(((TiltleBeans.DataBean.Question2Bean) o).getAnswer().get(0));
                        cb_item_daan2.setText(((TiltleBeans.DataBean.Question2Bean) o).getAnswer().get(1));
                        cb_item_daan3.setText(((TiltleBeans.DataBean.Question2Bean) o).getAnswer().get(2));
                        cb_item_daan4.setText(((TiltleBeans.DataBean.Question2Bean) o).getAnswer().get(3));
                        cb_item_daan5.setVisibility(View.GONE);
                    }
                    if (size == 5) {
                        cb_item_daan1.setText(((TiltleBeans.DataBean.Question2Bean) o).getAnswer().get(0));
                        cb_item_daan2.setText(((TiltleBeans.DataBean.Question2Bean) o).getAnswer().get(1));
                        cb_item_daan3.setText(((TiltleBeans.DataBean.Question2Bean) o).getAnswer().get(2));
                        cb_item_daan4.setText(((TiltleBeans.DataBean.Question2Bean) o).getAnswer().get(3));
                        cb_item_daan5.setText(((TiltleBeans.DataBean.Question2Bean) o).getAnswer().get(4));
                    }
                }


                initCheckListener(cb_item_daan1, position);
                initCheckListener(cb_item_daan2, position);
                initCheckListener(cb_item_daan3, position);
                initCheckListener(cb_item_daan4, position);
                initCheckListener(cb_item_daan5, position);

            }
        };
        rvNumber.setAdapter(adapter);


    }

    private void initData() {
        Map<String, String> map = new HashMap<>();
        OkGoUtil.<TiltleBeans>get(MainActivity.this, Constans.METHOD_GUOJIALIEBIAO, map, TiltleBeans.class, this);
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


    @OnClick({R.id.iv_close, R.id.man_rb, R.id.woman_rb, R.id.tv_submit, R.id.iv_close1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                finish();
                break;
            case R.id.iv_close1:
                finish();
                break;
            case R.id.man_rb:
                RadioText = manRb.getText().toString();
                break;
            case R.id.woman_rb:
                RadioText = womanRb.getText().toString();
                break;
            case R.id.tv_submit:
                if (NetUtil.isNetworkAvailable(MainActivity.this)) {
                    Map<String, String> map = new HashMap<>();
                    String display = Build.DISPLAY;
                    map.put("iccid", iccid);
                    map.put("DeviceVersion", display);
                    map.put("Answer1", RadioText);
                    map.put("Answer2", CheckText1);
                    map.put("Answer3", CheckText2);
                    map.put("Answer4", CheckText3);
                    map.put("Answer5", CheckText4);
                    map.put("QuestionVer", "321");
                    OkGoUtil.<BaseBean>get(MainActivity.this, Constans.METHOD_SHANGCHUANGWULUYIN, map, BaseBean.class, this);
                } else {
                    ToastUtil.showShort(MainActivity.this, getResources().getString(R.string.isnonet));
                }
                break;
        }
    }


    @Override
    public void onSuccess(String method, BaseBean model) {
        switch (method) {
            case Constans.METHOD_GUOJIALIEBIAO:
                titleBeans = (TiltleBeans) model;
                titleBeansData = titleBeans.getData();
                List<TiltleBeans.DataBean.Question1Bean> question1 = titleBeansData.getQuestion1();
                question2 = titleBeansData.getQuestion2();
                adapter.updateData(question2);
                cl.setVisibility(View.VISIBLE);
                rlIsnet.setVisibility(View.GONE);
                if (null != question1) {
                    tvNumber1.setText(question1.get(0).getQuestionMsg());
                    manRb.setText(question1.get(0).getAnswer().get(0));
                    womanRb.setText(question1.get(0).getAnswer().get(1));
                }

                break;

            case Constans.METHOD_SHANGCHUANGWULUYIN:
                baseBean = (BaseBean) model;
                String success = baseBean.getMsg().toString();
                if (TextUtils.equals(success, "Success!")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showShort(MainActivity.this, "提交成功");
//                            startActivity(new Intent(MainActivity.this, KeFuActivity.class));
//                            finish();
                            showDialog();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showShort(MainActivity.this, "上传失败");
                        }
                    });
                }
                break;

            case Constans.METHOD_SHANGCHUANGLUYIN:
                baseBean = (BaseBean) model;
                String success1 = baseBean.getMsg().toString();
                if (!TextUtils.equals(success1, "Success!")) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showShort(MainActivity.this, "上传失败");
                        }
                    });
                }
                break;
        }
    }


    @Override
    public void onError(String method, Response<String> response) {
        cl.setVisibility(View.GONE);
        rlIsnet.setVisibility(View.VISIBLE);
        linIsnet.setVisibility(View.VISIBLE);
        tvError.setText(getResources().getString(R.string.zoudiule));
    }

    private void showDialog(){
        final CommonDialog dialog = new CommonDialog(this, R.style.dialog);
        dialog.setOnBtnClickListener(new CommonDialog.OnBtnClickListener() {
            @Override
            public void onCancelClick() {

            }

            @Override
            public void onConfirmClick() {
                try {
                    MainActivity.this.finish();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        dialog.show();
    }


    @Override
    public void onScrolltrue() {
        Scroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }

    @Override
    public void onScrollfalse() {
        Scroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    @Override
    public void stopMedia() {

    }
}
