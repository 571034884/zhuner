package com.aibabel.travel.activity;

import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aibabel.travel.R;
import com.aibabel.travel.adaper.HorizontalListViewAdapter;
import com.aibabel.travel.bean.DetailBean;
import com.aibabel.travel.utils.FastJsonUtil;
import com.aibabel.travel.utils.StringUtil;
import com.aibabel.travel.widgets.HorizontalListView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends BaseActivity implements View.OnClickListener ,AdapterView.OnItemClickListener{
    @BindView(R.id.iv_scenery)
    ImageView iv_scenery;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.seekber)
    SeekBar seekBar;
    @BindView(R.id.tv_progress)
    TextView tvProgress;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_scenery_name)
    TextView tv_scenery_name;
    @BindView(R.id.iv_last_one)
    ImageView iv_last_one;
    @BindView(R.id.iv_start)
    ImageView iv_start;
    @BindView(R.id.iv_next_one)
    ImageView iv_next_one;
//    @BindView(R.id.horizontal_lv)
//    HorizontalListView horizontalLv;
    private HorizontalListView mHorizontalListView;
    private HorizontalListViewAdapter mHorizontalListViewAdapter;

    private List<DetailBean> children = new ArrayList<>();
    private boolean iv_scenery_onclick = false;

//    private String path = "http://www.170mv.com/kw/other.web.nf01.sycdn.kuwo.cn/resource/n1/18/43/1455693132.mp3";
    private MediaPlayer player;
    private boolean isPause = false;
    private int index = 0;
    private int position;

    //消息机制，让seekbar可以和音乐播放同步显示进度
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (player != null) {
                int mCurrentPosition = player.getCurrentPosition() / 1000;//获取player当前进度，毫秒表示
                int total = player.getDuration() / 1000;//获取当前歌曲总时长
                seekBar.setProgress(mCurrentPosition);//seekbar同步歌曲进度
                seekBar.setMax(total);//seekbar设置总时长
                tvTotal.setText(StringUtil.calculateTime(total));
                tvProgress.setText(StringUtil.calculateTime(mCurrentPosition));
                sendEmptyMessageDelayed(0, 1000);
            }
        }

        ;
    };

    @Override
    public int initLayout() {
        return R.layout.activity_spot_detail;
    }

    @Override
    public void init() {
        initView();
        initData();
        initOncliker();
    }


    private void initOncliker() {
        iv_last_one.setOnClickListener(this);
        iv_start.setOnClickListener(this);
        iv_next_one.setOnClickListener(this);
        iv_scenery.setOnClickListener(this);
        tv_title.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        mHorizontalListView.setOnItemClickListener(this);

    }

    /**
     * 初始化
     */
    private void initAudio(String path) {

        try {
            if (null == player) player = new MediaPlayer();
            player.setDataSource(path);
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void initView() {
        tv_title.setMovementMethod(ScrollingMovementMethod.getInstance());
//        mHorizontalListView = (HorizontalListView) findViewById(R.id.horizontal_lv);
        mHorizontalListViewAdapter = new HorizontalListViewAdapter(this,children);
        mHorizontalListView.setAdapter(mHorizontalListViewAdapter);
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });//设置seekbar 不能拖动
    }

    public void initData() {
        position = getIntent().getIntExtra("position",-1);
        String str = getIntent().getStringExtra("list");
        children = FastJsonUtil.changeJsonToList(str,DetailBean.class);
        if(-1 != position){
            mHorizontalListView.setSelection(position);
            mHorizontalListView.scrollTo(position);
        }
        mHorizontalListViewAdapter.updata(children);
        play(children.get(position).getAudioUrl());
        setSpotImage(children.get(position).getImageUrl());
        setSceneryName(children.get(position).getName());
        index = position;
    }

    private void setSceneryName(String name){
        if(!TextUtils.isEmpty(name)){
        tv_scenery_name.setText(name+"");
        }
    }


    private void setSpotImage(String url) {
        SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                iv_scenery.setBackground(resource);
            }
        };
        RequestOptions options = new RequestOptions().error(R.mipmap.error_h);
        Glide.with(this)
                .load(url)
                .apply(options)
                .into(simpleTarget);
    }
    /**
     * 播放暂停
     */
    private void play(String path) {

        try {
            initAudio(path);
            if (player.isPlaying() && !isPause) {
                pause();
            } else if (!player.isPlaying() && isPause) {
                start();
            } else {
                player.prepareAsync();
                //当mp开始播放音乐的时候   启动消息完成seekbar的同步显示进度
                handler.sendEmptyMessage(0);
                player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        // 装载完毕回调
                        start();
                    }
                });
            }
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
               iv_start.setImageResource(R.mipmap.start);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 播放
     */
    private void start() {
        isPause = false;
        player.start();
        iv_start.setImageResource(R.mipmap.pause);
    }

    /**
     * 暂停
     */
    private void pause() {
        isPause = true;
        try {
            player.pause();
            iv_start.setImageResource(R.mipmap.start);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 停止
     */
    private void stop() {
        if (player != null && player.isPlaying()) {
            //停止当前音乐的时候  要停止消息的发送
            handler.removeMessages(0);
            player.stop();
            player.release();
            player = null;
        }

    }


    @Override
    protected void onStop() {
        super.onStop();
        pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stop();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_last_one:
                index--;
                if (index < 0) {
                    Toast.makeText(this, "已经是第一个了", Toast.LENGTH_SHORT).show();
                    index = 0;
                } else {
                    stop();
                    play(children.get(index).getAudioUrl());
                    setSpotImage(children.get(index).getImageUrl());
                    setSceneryName(children.get(index).getName());
                }
                break;
            case R.id.iv_start:
                play(children.get(index).getAudioUrl());
                break;
            case R.id.iv_next_one:
                index++;
                if (index == children.size() + 1) {
                    Toast.makeText(this, "已经是最后一个了", Toast.LENGTH_SHORT).show();
                    index = children.size();
                } else {
                    stop();
                    play(children.get(index).getAudioUrl());
                    setSpotImage(children.get(index).getImageUrl());
                    setSceneryName(children.get(index).getName());
                }
                break;
            case R.id.iv_scenery:
//                if (iv_scenery_onclick == false) {
//                    Glide.with(this)
//                            .load(R.mipmap.background)
////                            .bitmapTransform(new BlurTransformation(this, 20))
//                            .into(iv_scenery);
//                    iv_scenery.setScaleType(ImageView.ScaleType.FIT_XY);
//                    tv_title.setVisibility(View.VISIBLE);
//                    iv_scenery_onclick = true;
//                } else {
//                    Glide.with(this)
//                            .load(R.mipmap.background)
//                            .into(iv_scenery);
//                    tv_title.setVisibility(View.GONE);
//                    iv_scenery.setScaleType(ImageView.ScaleType.FIT_XY);
//                    iv_scenery_onclick = false;
//                }
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
                default:
                    break;
//            case R.id.tv_title:
//                if (iv_scenery_onclick == false) {
//                    Glide.with(this)
//                            .load(R.mipmap.background)
//                            .into(iv_scenery);
//                    iv_scenery.setScaleType(ImageView.ScaleType.FIT_XY);
//                    tv_title.setVisibility(View.VISIBLE);
//                    iv_scenery_onclick = true;
//                } else {
//                    Glide.with(this)
//                            .load(R.mipmap.background)
//                            .into(iv_scenery);
//                    tv_title.setVisibility(View.GONE);
//                    iv_scenery.setScaleType(ImageView.ScaleType.FIT_XY);
//                    iv_scenery_onclick = false;
//                }
//                break;
        }


    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        index = position;
        setSpotImage(children.get(position).getImageUrl());
        setSceneryName(children.get(position).getName());
        stop();
        play(children.get(position).getAudioUrl());
    }
}
