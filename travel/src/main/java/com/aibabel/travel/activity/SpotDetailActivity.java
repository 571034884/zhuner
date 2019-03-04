package com.aibabel.travel.activity;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.travel.R;
import com.aibabel.travel.adaper.CommomRecyclerAdapter;
import com.aibabel.travel.adaper.CommonRecyclerViewHolder;
import com.aibabel.travel.app.BaseApplication;
import com.aibabel.travel.bean.DetailBean;
import com.aibabel.travel.broadcastreceiver.ExpireBroadcast;
import com.aibabel.travel.media.MusicData;
import com.aibabel.travel.media.MusicPlayState;
import com.aibabel.travel.media.MusicPlayer;
import com.aibabel.travel.utils.CommonUtils;
import com.aibabel.travel.utils.Constant;
import com.aibabel.travel.utils.FastJsonUtil;
import com.aibabel.travel.utils.MyDialog;
import com.aibabel.travel.utils.NetworkUtils;
import com.aibabel.travel.utils.StringUtil;
import com.aibabel.travel.utils.ThreadPoolManager;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * ==========================================================================================
 *
 * @Author：CreateBy 张文颖
 * @Date：2018/6/19
 * @Desc：景点详细页面
 * @==========================================================================================
 */
public class SpotDetailActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, ExpireBroadcast.stopMp3, BaseApplication.BackGroundListener, MusicPlayer.NextPlay, MyDialog.Notice {
    @BindView(R.id.iv_scenery)
    ImageView iv_scenery;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    //    @BindView(R.id.seekber)
    static SeekBar seekBar;
    //    @BindView(R.id.tv_progress)
    static TextView tvProgress;
    @BindView(R.id.tv_scenery_name)
    TextView tv_scenery_name;
    @BindView(R.id.iv_last_one)
    ImageView iv_last_one;
    @BindView(R.id.iv_start)
    ImageView iv_start;
    @BindView(R.id.iv_next_one)
    ImageView iv_next_one;
    //    @BindView(R.id.tv_total)
    static TextView tvTotal;
    @BindView(R.id.imgDialog)
    ImageView imgDialog;
    @BindView(R.id.dialog_view)
    LinearLayout dialogView;
    @BindView(R.id.rv)
    RecyclerView rv;


    private List<DetailBean> children = new ArrayList<>();
    private boolean isPlay = false;
    private int position;
    private int mPlayState; // 播放器状态

    HeadsetPlugReceiver headsetPlugReceiver;
    private static MyDialog myDialog = new MyDialog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }


    Handler handler = new MyHandler(SpotDetailActivity.this);

    /**
     * 声明静态内部类不会持有外部类的隐式引用
     */
    private static class MyHandler extends Handler {
        private final WeakReference<SpotDetailActivity> mActivity;

        public MyHandler(SpotDetailActivity activity) {
            mActivity = new WeakReference<SpotDetailActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            SpotDetailActivity activity = mActivity.get();
            if (activity != null) {

                if (msg.what == Constant.MSG_PROGRESS) {
                    int currentPosition = msg.arg1;
                    int totalDuration = msg.arg2;
                    seekBar.setProgress(currentPosition / 1000);
                    tvProgress.setText(StringUtil.formatTime(currentPosition));
//                    tvTotal.setText(StringUtil.formatTime(totalDuration));

                }
                if (msg.what == Constant.MSG_PREPARED) {
                    int totalDuration = msg.arg2;
                    tvTotal.setText(StringUtil.formatTime(totalDuration));
                    seekBar.setMax(totalDuration / 1000);
                }

//                int mCurrentPosition = musicPlayer.getCurPosition() / 1000;//获取player当前进度，毫秒表示
//
//                seekBar.setProgress(mCurrentPosition);//seekbar同步歌曲进度

//                tvProgress.setText(StringUtil.calculateTime(mCurrentPosition));
//                sendEmptyMessageDelayed(0, 1000);
            }
        }
    }


    private static MusicPlayer musicPlayer; //声频
    private AudioManager audioManager = null; //音频
    private MusicPlayStateBrocast receiver;
    private List<MusicData> mMusicFileList;
    private int total;
    private ExpireBroadcast expireBroadcast = new ExpireBroadcast();

    private LinearLayoutManager layoutManager;
    private CommomRecyclerAdapter adapter;
    private int lastOnclick = 0;
    private int lastItemPosition;
    private int last = -1;

    @Override
    public int initLayout() {
        return R.layout.activity_spot_detail;
    }

    @Override
    public void init() {
        seekBar = findViewById(R.id.seekber);
        tvProgress = findViewById(R.id.tv_progress);
        tvTotal = findViewById(R.id.tv_total);

        audioManager = (AudioManager) getSystemService(Service.AUDIO_SERVICE);

        initView();
        initData();
        initOncliker();

        musicPlayer.setNextPlay(this);
        myDialog.setNotice(this);
        expireBroadcast.setStopMp3(this);

        rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                        RecyclerView.LayoutManager layoutManager1 = recyclerView.getLayoutManager();
                //判断是当前layoutManager是否为LinearLayoutManager
                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
//                Log.e("===", "==" + postion);
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    //获取最后一个可见view的位置
                    lastItemPosition = linearManager.findLastVisibleItemPosition();
                    Log.e("===", lastItemPosition + "==");
//                    if (lastItemPosition == postion) {
//                        MoveToPosition(layoutManager, postion);
//                    }
                }

            }
        });
    }

    private void initGallery(final List<DetailBean> children) {

        layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        rv.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.HORIZONTAL);

        adapter = new CommomRecyclerAdapter(this, children, R.layout.horizontallistview_item, new CommomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommonRecyclerViewHolder holder, final int postion) {

                Map map3 = new HashMap();

                map3.put("p1", children.get(lastOnclick).getName());
                map3.put("p2", children.get(postion).getName());

                StatisticsManager.getInstance(SpotDetailActivity.this).addEventAidl(1655, map3);


                if (postion != lastOnclick) {
                    if (postion == lastItemPosition) {
                        children.get(postion).setSelector(true);
                        children.get(lastOnclick).setSelector(false);

                        children.get(lastOnclick).setIslast(true);
                        children.get(postion).setIslast(false);
                        MoveToPosition(layoutManager, postion);
                        adapter.updateData(children);
                        musicPlayer.playIndex(postion);
                        last = lastOnclick;
                        lastOnclick = postion;
                    } else {
                        children.get(postion).setSelector(true);
                        children.get(lastOnclick).setSelector(false);
                        children.get(lastOnclick).setIslast(true);
                        children.get(postion).setIslast(false);
                        adapter.updateData(children);
//                    MoveToPosition(layoutManager, postion);
                        musicPlayer.playIndex(postion);
                        last = lastOnclick;
                        lastOnclick = postion;
                    }

                }
                if (postion == 0) {
                    children.get(postion).setSelector(true);
                    children.get(postion).setIslast(true);
                    adapter.updateData(children);
//                    MoveToPosition(layoutManager, postion);
                    musicPlayer.playIndex(postion);
                    lastOnclick = postion;
                }
            }
        }, null) {


            @Override
            public void convert(CommonRecyclerViewHolder holder, Object o, int position) {
                ImageView image = holder.getView(R.id.iv_item_child);
                CardView card = holder.getView(R.id.cv_cardview);
                CardView.LayoutParams linearParams = (CardView.LayoutParams) image.getLayoutParams();
                //设置图片圆角角度

                //通过RequestOptions扩展功能
//                RequestOptions options = RequestOptions.bitmapTransform(new RoundedCornersTransformation(30, 2)).override(300, 300);
                RequestOptions options = RequestOptions.bitmapTransform(new RoundedCornersTransformation(30, 2)).override(300, 300).error(R.mipmap.error_h).placeholder(R.mipmap.placeholder_h);
                Glide.with(SpotDetailActivity.this)
//                .load(image.get(position))
                        .load(children.get(position).getImageUrl())
                        .apply(options)
                        .into(image);

                if (children.get(position).isSelector()) {
                    linearParams.height = 90;
                    linearParams.width = 120;
//                    card.setElevation(20);
                    pingYiAmin_top(card);
//                    rl.setBackgroundResource(R.drawable.main_photo_frame);
                } else {
                    linearParams.height = 74;
                    linearParams.width = 100;
//                    image.setElevation(0);
                }
                if (position == last) {
//                    rl.setBackgroundResource(R.drawable.main_photo_frame_white);
//                    linearParams.height=74;
//                    linearParams.width=100;
                    pingYiAmin_bottom(card);
                }

//                if (position==lastOnclick){
//                    pingYiAmin_bottom(image);
//                }
                image.setLayoutParams(linearParams);
            }
        };
        rv.setAdapter(adapter);


    }

    private void pingYiAmin_top(View iv) {
        if (iv != null) {
            TranslateAnimation animation;
            animation = new TranslateAnimation(0, 0, 0,
                    -30);
            animation.setDuration(500);
            animation.setFillAfter(true);
            iv.startAnimation(animation);
        }
    }

    private void pingYiAmin_bottom(View iv) {
        if (iv != null) {
            TranslateAnimation animation;
            animation = new TranslateAnimation(0, 0, -30,
                    0);
            animation.setDuration(500);
            animation.setFillAfter(true);
            iv.startAnimation(animation);
        }

    }

    private void initOncliker() {
        iv_last_one.setOnClickListener(this);
        iv_start.setOnClickListener(this);
        iv_next_one.setOnClickListener(this);
        iv_scenery.setOnClickListener(this);
        tv_title.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        BaseApplication baseApplication = new BaseApplication();
        baseApplication.setListener(this);
//        mHorizontalListView.setOnItemClickListener(this);
    }


    public void initView() {
        tv_title.setMovementMethod(ScrollingMovementMethod.getInstance());
        //设置seekbar 不能拖动
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        registerHeadsetPlugReceiver();
        initReseiver();
//
    }


    public void initData() {
        position = getIntent().getIntExtra("position", -1);
        String str = getIntent().getStringExtra("list");
        Map map1 = new HashMap();
        map1.put("p1", str);
        setPathParams(JSONObject.toJSON(map1).toString());
        children = FastJsonUtil.changeJsonToList(str, DetailBean.class);
        initGallery(children);
        for (int i = 0; i < children.size(); i++) {
            if (i == 0) {
                children.get(0).setSelector(true);
            } else {
                children.get(position).setSelector(false);
            }
        }
        if (position != lastOnclick) {
            children.get(position).setSelector(true);
            children.get(lastOnclick).setSelector(false);
            lastOnclick = position;
            initGallery(children);
            adapter.updateData(children);
//            MoveToPosition(layoutManager,position);
        }
        if (position == 0) {
            children.get(position).setSelector(true);
            lastOnclick = position;
            initGallery(children);
            adapter.updateData(children);
        }
        MoveToPosition(layoutManager, position);
        setSpotImage(children.get(position).getImageUrl());
        setSceneryName(children.get(position).getName());
//        index = position;
        mMusicFileList = new ArrayList<>();
        for (DetailBean bean : children) {
            MusicData musicData = new MusicData();
            musicData.mMusicPath = bean.getAudioUrl();
            mMusicFileList.add(musicData);
        }

//        if (null != musicPlayer) {
////            musicPlayer.exit();
//            musicPlayer = null;
//
//        }
        musicPlayer = MusicPlayer.getInstance();
        musicPlayer.init(SpotDetailActivity.this, dialogView, new Messenger(handler));
        //
        ThreadPoolManager.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                musicPlayer.refreshMusicList(mMusicFileList);
                musicPlayer.playIndex(position);
            }
        });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("spotDetailActivity","onNewIntent");
        setIntent(intent);
        position = 0;
        lastOnclick = 0;
        lastItemPosition = 0;
        last = -1;
        if (null == musicPlayer) {
            init();
        } else {
            position = intent.getIntExtra("position", -1);
            String list = intent.getStringExtra("list");
            noticePlay(list);
        }


    }

    private void registerHeadsetPlugReceiver() {
        headsetPlugReceiver = new HeadsetPlugReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.HEADSET_PLUG");
        registerReceiver(headsetPlugReceiver, filter);
    }


    private void initReseiver() {
        receiver = new MusicPlayStateBrocast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(MusicPlayState.MUSIC_BROCAST);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (!CommonUtils.isRun(this)) {
            stopMp3();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        musicPlayer.exit();
        handler.removeCallbacksAndMessages(null);
        if (null != receiver)
            unregisterReceiver(receiver);//注销播放器监听
        unregisterReceiver(headsetPlugReceiver);  //注销耳机插拔监听
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_last_one:

                Map map1 = new HashMap();
                try {
                    map1.put("p1", children.get(musicPlayer.getCurIndex()).getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                StatisticsManager.getInstance(SpotDetailActivity.this).addEventAidl(1653, map1);


                musicPlayer.playPre();
                position = lastOnclick - 1;
                if (position < 0) {
                    position = children.size() - 1;
                }
                if (position != lastOnclick) {
                    children.get(position).setSelector(true);
                    children.get(lastOnclick).setSelector(false);
                    last = lastOnclick;
                    lastOnclick = position;
                    adapter.updateData(children);
                    MoveToPosition(layoutManager, position);
                }
                if (position == 0) {
                    children.get(position).setSelector(true);
                    last = lastOnclick;
                    lastOnclick = position;
                    adapter.updateData(children);
                    MoveToPosition(layoutManager, position);
                }
                break;
            case R.id.iv_start:
                if (musicPlayer.getPlayState() == MusicPlayState.S_PLAYING) {
                    musicPlayer.pause();

                    Map map2 = new HashMap();
                    try {
                        map2.put("p1", children.get(musicPlayer.getCurIndex()).getName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    StatisticsManager.getInstance(SpotDetailActivity.this).addEventAidl(1652, map2);

                } else if (musicPlayer.getPlayState() == MusicPlayState.S_PAUSE) {
                    musicPlayer.play();

                    Map map3 = new HashMap();
                    try {
                        map3.put("p1", children.get(musicPlayer.getCurIndex()).getName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    StatisticsManager.getInstance(SpotDetailActivity.this).addEventAidl(1651, map3);

                }

                break;
            case R.id.iv_next_one:
                Map map4 = new HashMap();
                try {
                    map4.put("p1", children.get(musicPlayer.getCurIndex()).getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                StatisticsManager.getInstance(SpotDetailActivity.this).addEventAidl(1654, map4);


                musicPlayer.playNext();


                break;
            case R.id.iv_back:
                Constant.EXIT_TIME = System.currentTimeMillis();
                onBackPressed();
                break;
            default:
                break;

        }


    }

    @Override
    public void nextplay() {

        position = lastOnclick + 1;
        if (position > children.size() - 1) {
            position = 0;
        }
        if (!NetworkUtils.isAvailable(SpotDetailActivity.this)) {
            if (!fileIsExists(children.get(position).getAudioUrl())) {
                return;
            }
        }

        if (position != lastOnclick) {

            if (position == lastItemPosition) {
                children.get(position).setSelector(true);
                children.get(lastOnclick).setSelector(false);

                children.get(lastOnclick).setIslast(true);
                children.get(position).setIslast(false);
                MoveToPosition(layoutManager, position);
                adapter.updateData(children);
//                musicPlayer.playIndex(position);
                last = lastOnclick;
                lastOnclick = position;
            } else {
                children.get(position).setSelector(true);
                children.get(lastOnclick).setSelector(false);
                last = lastOnclick;
                lastOnclick = position;
                adapter.updateData(children);
            }

//            MoveToPosition(layoutManager, position);

        }
        if (position == 0) {
            children.get(position).setSelector(true);
            last = lastOnclick;
            lastOnclick = position;
            adapter.updateData(children);
            MoveToPosition(layoutManager, position);

        }

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        index = position;
        musicPlayer.playIndex(position);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case 24:
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);  //调高声音
                break;
            case 25:
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);//调低声音
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 设置景点名称
     *
     * @param name
     */
    private void setSceneryName(String name) {
        if (!TextUtils.isEmpty(name)) {
            tv_scenery_name.setText(name + "");
        }
    }

    /**
     * 设置景点图片
     *
     * @param url
     */
    private void setSpotImage(String url) {
        SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                iv_scenery.setBackground(resource);
            }
        };
//        RequestOptions options = new RequestOptions().error(R.mipmap.error_v).placeholder(R.mipmap.placeholder_v).transform(new BlurTransformation());
        RequestOptions options = new RequestOptions().error(R.mipmap.error_v).placeholder(R.mipmap.placeholder_v);
        Glide.with(this)
                .load(url)
                .apply(options)
                .into(simpleTarget);

//        iv_scenery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SpotDetailActivity.this,UpdateActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public void stopMp3() {
        if (null != musicPlayer && musicPlayer.getPlayState() == MusicPlayState.S_PLAYING)
            musicPlayer.pause();

    }

    @Override
    public void isBackGround(boolean isBack) {
        stopMp3();
    }


    /**
     * 用于接收musicPlayer发过来的广播，更新UI控件
     */
    class MusicPlayStateBrocast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(MusicPlayState.MUSIC_BROCAST)) {
                updatePlayState(intent);
            }
        }

        /**
         * 更新音乐状态,当前播放音乐名称，根据接收到的广播
         *
         * @param intent
         */
        private void updatePlayState(Intent intent) {
            MusicData data = new MusicData();
            int state = intent.getIntExtra(MusicPlayState.STATE_NAME, -1);
            Bundle bundle = intent.getBundleExtra(MusicData.KEY_MUSIC_DATA);
            if (bundle != null) {
                data = bundle.getParcelable(MusicData.KEY_MUSIC_DATA);
            }
            int playIndex = intent.getIntExtra(MusicPlayState.MUSIC_INDEX, -1);
            boolean isAvailable = intent.getBooleanExtra(Constant.CURRENT_NET, false);

            if (data != null) {
                iv_start.setBackgroundResource(R.mipmap.start);
            }

            switch (state) {
                case MusicPlayState.S_INVALID: {// 无效音乐
                    toastShort(getString(R.string.fail_path));
                    iv_start.setBackgroundResource(R.mipmap.start);
                }
                break;
                case MusicPlayState.S_PAUSE: {// 暂停
//                    if (!isAvailable)
//                        toastShort(getString(R.string.net_unavailable));
                    iv_start.setBackgroundResource(R.mipmap.start);
                }
                break;
                case MusicPlayState.S_PLAYING: {// 播放
                    isPlay = true;
//                    total = musicPlayer.getDuration() / 1000;
//                    seekBar.setMax(total);
//                    tvTotal.setText(StringUtil.calculateTime(total));
                    iv_start.setBackgroundResource(R.mipmap.pause);

                }
                break;
                case MusicPlayState.S_PREPARE: {// 正在准备
                    iv_start.setBackgroundResource(R.mipmap.pause);
                    setSpotImage(children.get(playIndex).getImageUrl());
                    setSceneryName(children.get(playIndex).getName());
                    tvTotal.setText("0:00");
                    tvProgress.setText("0:00");
                }
                break;
                default:
                    break;
            }
        }
    }


    /**
     * 监听耳机插拔
     */

    class HeadsetPlugReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub

            if (intent.hasExtra("state")) {
                if (intent.getIntExtra("state", 0) == 0) {
                    if (musicPlayer.getPlayState() != MusicPlayState.S_PAUSE && isPlay)
                        musicPlayer.pause();
//                    Toast.makeText(context, "headset not connected", Toast.LENGTH_LONG).show();
                } else if (intent.getIntExtra("state", 0) == 1) {
//                    Toast.makeText(context, "headset  connected", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void MoveToPosition(LinearLayoutManager manager, int n) {
        if (children.size() >= 5) {
//            rv.scrollToPosition(n);
            manager.scrollToPositionWithOffset(n, 0);
            manager.setStackFromEnd(true);
        }

    }


    //判断文件是否存在
    public boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public void noticePlay(String json) {
        musicPlayer.stop();
        children = FastJsonUtil.changeJsonToList(json, DetailBean.class);
        children.get(0).setSelector(true);
        initGallery(children);
        adapter.updateData(children);
        setSpotImage(children.get(position).getImageUrl());
        setSceneryName(children.get(position).getName());


        mMusicFileList = new ArrayList<>();
        for (DetailBean bean : children) {
            MusicData musicData = new MusicData();
            musicData.mMusicPath = bean.getAudioUrl();
            mMusicFileList.add(musicData);
        }
        musicPlayer.refreshMusicList(mMusicFileList);
        position = 0;
//        initData(true,json);
        musicPlayer.playIndex(position);
//        init();
    }

}
