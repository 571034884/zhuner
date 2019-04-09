package com.aibabel.scenic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.aibabel.scenic.adapter.Adapter_Spots;
import com.aibabel.scenic.base.BaseScenicActivity;
import com.aibabel.scenic.bean.MusicBean;
import com.aibabel.scenic.bean.SpotsBean;
import com.aibabel.scenic.music.MusicService;
import com.aibabel.scenic.okgo.ApiConstant;
import com.aibabel.scenic.receiver.ExpireBroadcast;
import com.aibabel.scenic.utils.CommonUtils;
import com.aibabel.scenic.utils.Constants;
import com.aibabel.scenic.utils.Logs;
import com.aibabel.scenic.utils.StringUtils;
import com.aibabel.scenic.view.EmptyLayout;
import com.aibabel.scenic.view.RecyclerItemDecoration;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;

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
 * @Date：2019/3/25
 * @Desc：景点详情
 * @==========================================================================================
 */
public class SpotsActivity extends BaseScenicActivity implements ExpireBroadcast.StopMp3, BaseQuickAdapter.OnItemClickListener {


    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.rl_img)
    RelativeLayout rlImg;
    @BindView(R.id.iv_scenic)
    ImageView ivScenic;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.tv_music_name)
    TextView tvMusicName;
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
    @BindView(R.id.pb_progress)
    ProgressBar pbProgress;
    @BindView(R.id.rv_spots)
    RecyclerView rvSpots;
    @BindView(R.id.rl_music)
    RelativeLayout rlMusic;
    @BindView(R.id.el_error)
    EmptyLayout elError;
    /**
     * 头布局
     */
    TextView tvSpot;
    TextView tvSpots;
    TextView tvContent;
    LinearLayout llIntroduce;


    private List<SpotsBean.DataBean.SubpoiMsgBean> list = new ArrayList<>();
    private Adapter_Spots mAdapter;
    private int page;
    private final int PAGE_SIZE = 50;
    private int mPosition = 0;
    //播放状态
    private boolean mIsPlaying;
    private String poiId;
    Handler handler = new MyHandler(SpotsActivity.this);
    private List<MusicBean> musicList = new ArrayList<>();

    private boolean isFirst = true;
    private boolean isScenic = false;
    private long startTimer = 0;
    private long endTimer = 0;

    @Override
    public int getLayouts(Bundle var1) {
        return R.layout.activity_spots;
    }

    @Override
    public void initView() {
        tvPre.setOnClickListener(this);
        tvNext.setOnClickListener(this);
        tvLeft.setOnClickListener(this);
        tvStart.setOnClickListener(this);
        ivScenic.setOnClickListener(this);
        rlMusic.setOnClickListener(this);
        ExpireBroadcast.setStopMp3(this, Constants.key_spot);
    }

    @Override
    public void initData() {
        page = 1;
        mPosition = 0;
        poiId = getIntent().getStringExtra("poiId");
        try {
            HashMap<String, Serializable> map = new HashMap<>();
            map.put("scenic_spots_open_name", poiId);
            addStatisticsEvent("scenic_spots_open", map);
        } catch (Exception e) {
        }
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        //设置布局管理器
        rvSpots.setLayoutManager(layoutManager);
        mAdapter = new Adapter_Spots(R.layout.item_spots, list);
        View headerView = getHeaderView();
        mAdapter.addHeaderView(headerView);
        rvSpots.addItemDecoration(new RecyclerItemDecoration(12, 2));
        rvSpots.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getDataValue();
            }


        }, rvSpots);
        //错误处理
        elError.setOnBtnClickListener(new EmptyLayout.onClickListener() {
            @Override
            public void onBtnClick() {
                getDataValue();
            }
        });

        //请求数据
        getDataValue();
    }

    private View getHeaderView() {
        View view = getLayoutInflater().inflate(R.layout.layout_spot, (ViewGroup) rvSpots.getParent(), false);
        tvSpot = view.findViewById(R.id.tv_spot);
        tvSpots = view.findViewById(R.id.tv_spots);
        llIntroduce = view.findViewById(R.id.ll_introduce);
        tvContent = view.findViewById(R.id.tv_content);
        return view;
    }

    /**
     * 网络请求
     */
    private void getDataValue() {
        if (!CommonUtils.isNetworkAvailable(this)) {
            elError.setErrorType(EmptyLayout.NETWORK_EMPTY);
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("poiId", poiId);
        map.put("page", String.valueOf(page));
        map.put("pageSize", String.valueOf(PAGE_SIZE));
        OkGoUtil.get(mContext, ApiConstant.GET_SPOT, map, BaseBean.class, new BaseCallback() {
            @Override
            public void onSuccess(String method, BaseBean model, String json) {
                SpotsBean spotsBean = FastJsonUtil.changeJsonToBean(json, SpotsBean.class);
                Logs.e(json);
                boolean isRefresh = false;
                if (page == 1) {
                    isRefresh = true;
                } else {
                    isRefresh = false;
                }

                setData(isRefresh, spotsBean);

            }

            @Override
            public void onError(String method, String message, String json) {
//                ToastUtil.showShort(mContext, "准儿出错了");
                Logs.e("景区--" + ApiConstant.GET_HOME_SCENIC + "：" + message);
                mAdapter.loadMoreFail();
                if (page == 1) {
                    elError.setErrorType(EmptyLayout.ERROR_EMPTY);
                }
            }

            @Override
            public void onFinsh(String method) {

            }
        });
    }


    /**
     * 向页面赋值
     *
     * @param isRefresh
     */
    private void setData(boolean isRefresh, SpotsBean spotsBean) {
        page++;
        list = spotsBean.getData().getSubpoiMsg();
        SpotsBean.DataBean.PoiMsgBean bean = spotsBean.getData().getPoiMsg();
        final int size = list == null ? 0 : list.size();
        if (isRefresh) {



            mAdapter.setNewData(list);
            setText(bean);
            rlMusic.setVisibility(View.VISIBLE);
            musicList = StringUtils.convertList(spotsBean);
            startMusicService();
            if (size == 0) {
                elError.setErrorType(EmptyLayout.NORMAL_EMPTY);
            } else {
                elError.setErrorType(EmptyLayout.SUCCESS_EMPTY);
            }
            Logs.e("第一次执行");
        } else {
            if (size > 0) {
                Logs.e("第二次执行");
                mAdapter.addData(list);
                updateList(list);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mAdapter.loadMoreEnd(isRefresh);
//            Toast.makeText(this, "no more data", Toast.LENGTH_SHORT).show();
        } else {
            mAdapter.loadMoreComplete();
        }
    }

    /**
     * 赋值文本信息
     *
     * @param bean
     */
    private void setText(SpotsBean.DataBean.PoiMsgBean bean) {
        if (null == bean) {
            return;
        }
        String name = bean.getName();
        String desc = bean.getDesc();
        String title = bean.getName();
        if (TextUtils.isEmpty(desc)) {
            llIntroduce.setVisibility(View.GONE);
        } else {
            llIntroduce.setVisibility(View.VISIBLE);
            tvContent.setText(desc);
            tvSpot.setText(name);

        }
        tvName.setText(name);
        tvMusicName.setText(name);
        Glide.with(this).load(bean.getCover()).apply(CommonUtils.options).into(ivScenic);
        Glide.with(this).load(bean.getCover()).apply(CommonUtils.options).into(ivImg);
        tvTitle.setText(title);
    }

    public void addEventLong(){
        //TODO 播放时长

        if (startTimer != 0.0){
            endTimer = System.currentTimeMillis();
            HashMap<String, Serializable> map = new HashMap<>();
            map.put("scenic_spots_over_name",musicList.get(mPosition).getName());
            map.put("scenic_spots_over_long",(endTimer-startTimer)+"");
            addStatisticsEvent("scenic_spots_over",map);
        }
        startTimer = 0;
        endTimer = 0;
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_start:
                isScenic = true;
                if (mIsPlaying) {
                    addEventLong();
                    //TODO 暂停
                    HashMap<String, Serializable> maps = new HashMap<>();
                    maps.put("scenic_spots_auto_off_name",musicList.get(mPosition).getName());
                    addStatisticsEvent("scenic_spots_auto_off",maps);
                    sendBroadcast(Constants.ACTION_PAUSE);
                } else {
                    if(!isFirst){
                        HashMap<String, Serializable> maps = new HashMap<>();
                        maps.put("scenic_spots_auto_on_name",musicList.get(mPosition).getName());
                        addStatisticsEvent("scenic_spots_auto_on",maps);
                        sendBroadcast(Constants.ACTION_PLAY);
                    }else{
                        HashMap<String, Serializable> maps = new HashMap<>();
                        maps.put("scenic_spots_auto_on_name",musicList.get(0).getName());
                        addStatisticsEvent("scenic_spots_auto_on",maps);
                        sendBroadcast(Constants.ACTION_LIST_ITEM,0);
                        isFirst = false;
                    }

                }
                break;
            case R.id.tv_next:
                if (!CommonUtils.isNetworkAvailable(this)) {
                    ToastUtil.showShort(this, "当前网络不可用！");
                    return;
                }
                if (CommonUtils.isFastClick()) {
                    addEventLong();
                    //TODO 下一首
                    isScenic = true;
                    addStatisticsEvent("scenic_spots_down",null);
                    sendBroadcast(Constants.ACTION_NEXT);
                }
                break;
            case R.id.tv_pre:
                if (!CommonUtils.isNetworkAvailable(this)) {
                    ToastUtil.showShort(this, "当前网络不可用！");
                    return;
                }
                if (CommonUtils.isFastClick()) {
                    addEventLong();
                    //TODO 上一首
                    isScenic = true;
                    addStatisticsEvent("scenic_spots_up",null);
                    sendBroadcast(Constants.ACTION_PRV);
                }
                break;
            case R.id.tv_left:
                if (mIsPlaying){
                    addEventLong();
                }
                addStatisticsEvent("scenic_spots_close", null);
                onBackPressed();
                sendBroadcast(Constants.ACTION_CLOSE);
                Intent intent = new Intent(getApplicationContext(), MusicService.class);
                stopService(intent);// 关闭服务

                break;
            case R.id.iv_scenic:
                if (!CommonUtils.isNetworkAvailable(this)) {
                    ToastUtil.showShort(this, "当前网络不可用！");
                    return;
                }
                if (isScenic){
                    addEventLong();
                }else{
                    isScenic = true;
                }

                HashMap<String, Serializable> maps = new HashMap<>();
                maps.put("scenic_spots_list_name",musicList.get(0).getName());
                addStatisticsEvent("scenic_spots_list",maps);

                sendBroadcast(Constants.ACTION_LIST_ITEM, 0);
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
        if (isScenic){
            addEventLong();
        }else {
            isScenic = true;
        }

        HashMap<String, Serializable> maps = new HashMap<>();
        maps.put("scenic_spots_list_name",musicList.get(position+1).getName());
        addStatisticsEvent("scenic_spots_list",maps);
        isFirst = false;
        sendBroadcast(Constants.ACTION_LIST_ITEM, position + 1);
    }
    //======================================音乐处理=================================================

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
//        ToastUtil.showShort(this, "启动音乐服务");
    }


    /**
     * 声明静态内部类不会持有外部类的隐式引用
     */
    private class MyHandler extends Handler {
        private final WeakReference<SpotsActivity> mActivity;

        public MyHandler(SpotsActivity activity) {
            mActivity = new WeakReference<SpotsActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            SpotsActivity activity = mActivity.get();
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
        tvName.setText(TextUtils.isEmpty(name) ? " " : name);
        Glide.with(this)
                .load(url)
                .apply(CommonUtils.options)
                .into(ivImg);
    }

    @Override
    public void stopMp3() {
        sendBroadcast(Constants.ACTION_PAUSE);
    }

    @Override
    protected void onStop() {
        super.onStop();
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

    private void updateList(List<SpotsBean.DataBean.SubpoiMsgBean> list) {
        if (null != list || list.size() > 0) {
            musicList.addAll(StringUtils.convertList(list));
            MusicService.addData(StringUtils.convertList(list));
//            Intent intent = new Intent();
//            intent.setAction(action);
//            intent.putParcelableArrayListExtra("music_list", (ArrayList<? extends Parcelable>) StringUtils.convertList(list));
//            intent.setAction(action);
//            sendBroadcast(intent);
        }

    }

}


