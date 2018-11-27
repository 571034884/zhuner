package com.aibabel.ocr.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.aibabel.ocr.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends BaseActivity {
    @BindView(R.id.iv_detail)
    ImageView ivDetail;
    @BindView(R.id.bt_paly)
    TextView btPaly;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.seekBar)
    SeekBar seekBar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.v_line)
    View vLine;
    @BindView(R.id.tv_comment)
    TextView tvComment;

//    @BindView(R.id.iv_detail)
//    ImageView ivDetail;
//    @BindView(R.id.bt_paly)
//    TextView btPaly;
//    @BindView(R.id.tv_back)
//    TextView tvBack;
//    @BindView(R.id.tv_title)
//    TextView tvTitle;
//    @BindView(R.id.tv_content)
//    TextView tvContent;
//    @BindView(R.id.seekBar)
//    SeekBar seekBar;
//    @BindView(R.id.tv_comment)
//    TextView tvComment;
//    @BindView(R.id.v_line)
//    View vLine;


//    private String path = "http://www.170mv.com/kw/other.web.nf01.sycdn.kuwo.cn/resource/n1/18/43/1455693132.mp3";
//    private MediaPlayer player;
//    private boolean isPause = false;


    //    //消息机制，让seekbar可以和音乐播放同步显示进度
//    Handler handler = new Handler() {
//        public void handleMessage(Message msg) {
//            if (player != null) {
////使用mp当前播放的音乐总长度来设置seekbar的最大值
//                seekBar.setMax(player.getDuration());
////使用mp当前播放的音乐进度来设置seekbar当前显示进度值
//                seekBar.setProgress(player.getCurrentPosition());
////重复发送更新的消息
//                sendEmptyMessageDelayed(0, 1000);
//            }
//        }
//
//        ;
//    };
    private String title;
    private String detail;
    private String url;
    private String audioUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        title = getIntent().getStringExtra("title");
        detail = getIntent().getStringExtra("detail");
        url = getIntent().getStringExtra("url");
        audioUrl = getIntent().getStringExtra("audioUrl");


        if (!TextUtils.isEmpty(detail) && TextUtils.equals(title, "高台寺京族料理：花咲・萬治郎")) {
            String[] strs = detail.split("\\|\\|");
            tvContent.setText(strs[0]);
            tvComment.setText(strs[1]);
        } else {
            tvContent.setText("" + detail);
            tvComment.setVisibility(View.GONE);
            vLine.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(audioUrl)) {
            btPaly.setVisibility(View.GONE);
        }


        tvTitle.setText("" + title);
        RequestOptions mRequestOptions = RequestOptions
                .circleCropTransform()
                .placeholder(R.mipmap.ic_error)//图片加载出来前，显示的图片
                .error(R.mipmap.ic_error)//图片加载失败后，显示的图片
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true);
        Glide.with(this)
                .load(url)
                .apply(mRequestOptions)
                .into(ivDetail);

    }


    @OnClick({R.id.bt_paly, R.id.tv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                onBackPressed();
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
