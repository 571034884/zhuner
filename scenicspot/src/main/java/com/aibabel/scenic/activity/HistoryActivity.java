package com.aibabel.scenic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aibabel.baselibrary.http.BaseBean;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.baselibrary.utils.FastJsonUtil;
import com.aibabel.baselibrary.utils.ToastUtil;
import com.aibabel.scenic.R;
import com.aibabel.scenic.adapter.Adapter_H;
import com.aibabel.scenic.adapter.Adapter_V;
import com.aibabel.scenic.base.BaseScenicActivity;
import com.aibabel.scenic.bean.HistoryBean;
import com.aibabel.scenic.bean.MusicBean;
import com.aibabel.scenic.bean.SpotsBean;
import com.aibabel.scenic.music.MusicService;
import com.aibabel.scenic.okgo.ApiConstant;
import com.aibabel.scenic.receiver.ExpireBroadcast;
import com.aibabel.scenic.utils.CommonUtils;
import com.aibabel.scenic.utils.Constants;
import com.aibabel.scenic.utils.Logs;
import com.aibabel.scenic.utils.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shehuan.niv.NiceImageView;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ==========================================================================================
 *
 * @Author： 张文颖
 * @Date：2019/3/23
 * @Desc：
 * @==========================================================================================
 */
public class HistoryActivity extends BaseScenicActivity implements ExpireBroadcast.StopMp3, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_scenic)
    NiceImageView ivScenic;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.rl_img)
    RelativeLayout rlImg;
    @BindView(R.id.rv_h)
    RecyclerView rvH;
    @BindView(R.id.wv_local)
    WebView wvLocal;
    @BindView(R.id.ll_h)
    LinearLayout llH;
    @BindView(R.id.rv_v)
    RecyclerView rvV;
    @BindView(R.id.ll_v)
    LinearLayout llV;
    @BindView(R.id.iv_img)
    NiceImageView ivImg;
    @BindView(R.id.tv_music_name)
    TextView tvMusicName;
    @BindView(R.id.pb_progress)
    ProgressBar pbProgress;
    @BindView(R.id.tv_pre)
    TextView tvPre;
    @BindView(R.id.ll_pre)
    LinearLayout llPre;
    @BindView(R.id.tv_start)
    TextView tvStart;
    @BindView(R.id.ll_start)
    LinearLayout llStart;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.ll_next)
    LinearLayout llNext;
    @BindView(R.id.ll_play)
    LinearLayout llPlay;
    @BindView(R.id.rl_music)
    RelativeLayout rlMusic;


    private Adapter_H adapterH;
    private Adapter_V adapterV;
    private List<HistoryBean> list = new ArrayList<>();
    private int page;
    private final int PAGE_SIZE = 50;
    private int mPosition = 0;
    //播放状态
    private boolean mIsPlaying;
    Handler handler = new MyHandler(HistoryActivity.this);
    private List<MusicBean> musicList = new ArrayList<>();
    private String json;
    private String menuCountryId;
    private String historyPage;
    private String imageCountry;
    private boolean ifFirst = true;


    @Override
    public int getLayouts(Bundle var1) {
        return R.layout.activity_history;
    }

    @Override
    public void initView() {
        tvPre.setOnClickListener(this);
        tvNext.setOnClickListener(this);
        tvLeft.setOnClickListener(this);
        tvStart.setOnClickListener(this);
        tvTitle.setText(R.string.title_history);
        rlMusic.setOnClickListener(this);
        ExpireBroadcast.setStopMp3(this, Constants.key_history);
    }

    @Override
    public void initData() {
        menuCountryId = getIntent().getStringExtra("menuCountryId");
        historyPage = getIntent().getStringExtra("historyPage");
        imageCountry = getIntent().getStringExtra("imageCountry");
        json = getIntent().getStringExtra("json");
        mPosition = getIntent().getIntExtra("position", 0);
        page = 1;

        try {
            HashMap<String, Serializable> map = new HashMap<>();
            map.put("scenic_history_open_name", list.get(mPosition).getName());
            addStatisticsEvent("scenic_history_open", map);
        } catch (Exception e) {
        }

        //横向RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //设置布局管理器
        rvH.setLayoutManager(layoutManager);
        adapterH = new Adapter_H(R.layout.item_h, list);
        rvH.setAdapter(adapterH);
        //纵向布局的rv
        LinearLayoutManager mLinearLayout = new LinearLayoutManager(this);
        rvV.setLayoutManager(mLinearLayout);
        adapterV = new Adapter_V(R.layout.item_v, list);
        rvV.setAdapter(adapterV);
        adapterH.setOnItemClickListener(this);
        adapterV.setOnItemClickListener(this);
        initWebView();
//        getDataValue();

        setData(json);
    }

    private void initWebView() {
        wvLocal.setVerticalScrollBarEnabled(true);
        WebSettings webSettings = wvLocal.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //设置缓存
        webSettings.setJavaScriptEnabled(true);//设置能够解析Javascript
        webSettings.setDomStorageEnabled(true);//设置适应Html5

    }


//    /**
//     * 网络请求
//     */
//    private void getDataValue() {
//
//        Map<String, String> map = new HashMap<>();
//        map.put("countryName", "美国");
//        map.put("page", String.valueOf(page));
//        map.put("pageSize", String.valueOf(PAGE_SIZE));
//        OkGoUtil.get(mContext, ApiConstant.GET_HISTORY_SCENIC, map, BaseBean.class, new BaseCallback() {
//            @Override
//            public void onSuccess(String method, BaseBean model, String resoureJson) {
//                ToastUtil.showShort(mContext, "成功");
//                HistoryBean spotsBean = FastJsonUtil.changeJsonToBean(resoureJson, HistoryBean.class);
//                setData(spotsBean.getData());
//            }
//
//            @Override
//            public void onError(String method, String message, String resoureJson) {
//                ToastUtil.showShort(mContext, "准儿出错了");
//                Logs.e("景区--" + ApiConstant.GET_HOME_SCENIC + "：" + message);
//            }
//
//            @Override
//            public void onFinsh(String method) {
//
//            }
//        });
//    }


    /**
     * 向页面赋值
     *
     * @param json
     */
    private void setData(String json) {
        list = FastJsonUtil.changeJsonToList(json, HistoryBean.class);

        if (TextUtils.isEmpty(historyPage)) {
            //如果没有历史风俗介绍显示纵向
            llV.setVisibility(View.VISIBLE);
            llH.setVisibility(View.GONE);
            adapterV.setNewData(list);
        } else {
            //如果有历史风俗介绍显示横向
            llH.setVisibility(View.VISIBLE);
            llV.setVisibility(View.GONE);
            adapterH.setNewData(list);
            wvLocal.loadUrl(StringUtils.getCountryUrl(historyPage, CommonUtils.getCountryType(), menuCountryId));
        }
        //启动音乐服务
        if (null != list && list.size() > 0) {
            rlMusic.setVisibility(View.VISIBLE);
            musicList = StringUtils.convertMusicList(list);
            tvMusicName.setText(list.get(mPosition).getName());
            Glide.with(this).load(imageCountry).apply(CommonUtils.options).into(ivScenic);
            Glide.with(this).load(list.get(mPosition).getCover()).apply(CommonUtils.options).into(ivImg);
            startMusicService();
        } else {
            rlMusic.setVisibility(View.GONE);
        }

    }

    public void addEventLong(){
        //TODO 播放时长

        if (startTimer != 0.0){
            endTimer = System.currentTimeMillis();
            HashMap<String, Serializable> map = new HashMap<>();
            map.put("scenic_history_over_name",musicList.get(mPosition).getName());
            map.put("scenic_history_over_long",(endTimer-startTimer)+"");
            addStatisticsEvent("scenic_history_over",map);
        }
        startTimer = 0;
        endTimer = 0;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_start:
                if (mIsPlaying) {
                    addEventLong();
                    //TODO 暂停
                    HashMap<String, Serializable> maps = new HashMap<>();
                    maps.put("scenic_history_auto_off_name",musicList.get(mPosition).getName());
                    addStatisticsEvent("scenic_history_auto_off",maps);
                    sendBroadcast(Constants.ACTION_PAUSE);
                } else {
                    if(!ifFirst){
                        HashMap<String, Serializable> maps = new HashMap<>();
                        maps.put("scenic_history_auto_on_name",musicList.get(mPosition).getName());
                        addStatisticsEvent("scenic_history_auto_on",maps);
                        sendBroadcast(Constants.ACTION_PLAY);
                    }else{
                        HashMap<String, Serializable> maps = new HashMap<>();
                        maps.put("scenic_history_auto_on_name",musicList.get(mPosition).getName());
                        addStatisticsEvent("scenic_history_auto_on",maps);
                        sendBroadcast(Constants.ACTION_LIST_ITEM,mPosition);
                        ifFirst = false;
                    }

                }
                break;
            case R.id.tv_next:
                if (!CommonUtils.isNetworkAvailable(this)) {
                    ToastUtil.showShort(this, "当前网络不可用！");
                    return;
                }
                addEventLong();
                addStatisticsEvent("scenic_history_down",null);
                sendBroadcast(Constants.ACTION_NEXT);
                break;
            case R.id.tv_pre:

                if (!CommonUtils.isNetworkAvailable(this)) {
                    ToastUtil.showShort(this, "当前网络不可用！");
                    return;
                }
                addEventLong();
                addStatisticsEvent("scenic_history_up",null);
                sendBroadcast(Constants.ACTION_PRV);
                break;
            case R.id.tv_left:
                if (mIsPlaying){
                    addEventLong();
                }
                addStatisticsEvent("scenic_history_close", null);
                onBackPressed();
                sendBroadcast(Constants.ACTION_CLOSE);
                Intent intent = new Intent(getApplicationContext(), MusicService.class);
                stopService(intent);// 关闭服务
                break;
            case R.id.rl_music:
                break;
        }

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (!CommonUtils.isNetworkAvailable(this)) {
            ToastUtil.showShort(this, "当前网络不可用！");
            return;
        }
        addEventLong();

        HashMap<String, Serializable> maps = new HashMap<>();
        maps.put("scenic_history_list_name",musicList.get(position).getName());
        addStatisticsEvent("scenic_history_list",maps);

        sendBroadcast(Constants.ACTION_LIST_ITEM, position);

    }


    //======================================音乐处理==================================================

    /**
     * 开始音乐服务并传输数据
     */
    private void startMusicService() {
        Intent musicService = new Intent();
        musicService.setClass(getApplicationContext(), MusicService.class);
        musicService.putParcelableArrayListExtra("music_list", (ArrayList<? extends Parcelable>) musicList);
        musicService.putExtra("messenger", new Messenger(handler));
        musicService.putExtra("position", mPosition);
        startService(musicService);
    }


    /**
     * 声明静态内部类不会持有外部类的隐式引用
     */
    private class MyHandler extends Handler {
        private final WeakReference<HistoryActivity> mActivity;


        public MyHandler(HistoryActivity activity) {
            mActivity = new WeakReference<HistoryActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            HistoryActivity activity = mActivity.get();
            if (activity != null) {
                if (msg.what == Constants.MSG_PROGRESS) {
                    int currentPosition = msg.arg1;
//                    int totalDuration = msg.arg2;
                    pbProgress.setProgress(currentPosition / 1000);
//                    tvProgress.setText(StringUtil.formatTime(currentPosition));

                }
                if (msg.what == Constants.MSG_PREPARED) {
                    mPosition = msg.arg1;
                    mIsPlaying = (boolean) msg.obj;
                    int totalDuration = msg.arg2;
//                    tvTotal.setText(StringUtil.formatTime(totalDuration));
                    pbProgress.setMax(totalDuration / 1000);
                    startTimer = System.currentTimeMillis();
                    switchUI(mPosition, mIsPlaying);
                }
                if (msg.what == Constants.MSG_PLAY_STATE) {
                    mIsPlaying = (boolean) msg.obj;
                    if(mIsPlaying && startTimer==0.0){
                        startTimer = System.currentTimeMillis();
                    }
                    switchUI(mPosition, mIsPlaying);
                }
                if (msg.what == Constants.MSG_CANCEL) {
                    mIsPlaying = false;
                    finish();
                }
            }
        }
    }

    private long startTimer;
    private long endTimer;

    /**
     * 切换图片
     *
     * @param position
     * @param mIsPlaying
     */
    private void switchUI(int position, boolean mIsPlaying) {
        if (mIsPlaying) {
            tvStart.setBackgroundResource(R.mipmap.ic_pause);
        } else {
            tvStart.setBackgroundResource(R.mipmap.ic_play_normal);
        }
        setScenery(musicList.get(position).getImageUrl(), musicList.get(position).getName());
    }


    /**
     * 设置景点名称和图片
     *
     * @param url
     */
    private void setScenery(String url, String name) {
        tvMusicName.setText(TextUtils.isEmpty(name) ? " " : name);
        RequestOptions options = new RequestOptions().error(R.mipmap.error_v);
        Glide.with(this)
                .load(url)
                .apply(options)
                .into(ivImg);
    }

    @Override
    public void stopMp3() {
        sendBroadcast(Constants.ACTION_PAUSE);
    }


    /**
     * 发送广播来对音乐进行操作
     *
     * @param action
     */
    private void sendBroadcast(String action) {


            Intent intent = new Intent();
            intent.setAction(action);
            sendBroadcast(intent);


    }

    private void sendBroadcast(String action, int position) {
            Intent intent = new Intent();
            intent.putExtra("position", position);
            intent.setAction(action);
            sendBroadcast(intent);

    }

}
