package com.aibabel.food.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.baselibrary.utils.FastJsonUtil;
import com.aibabel.food.R;
import com.aibabel.food.adapter.CommentInDetailAdapter;
import com.aibabel.food.adapter.RecommendInDetailAdapter;
import com.aibabel.food.bean.CommentBean;
import com.aibabel.food.bean.RecommentBean;
import com.aibabel.food.custom.ratingbar.XRatingBar;
import com.zhouyou.recyclerview.XRecyclerView;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DetailActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener {

    @BindView(R.id.ivTitleOpenBack)
    ImageView ivTitleOpenBack;
    @BindView(R.id.ivTitleCloseBack)
    ImageView ivTitleCloseBack;
    @BindView(R.id.rbTab1)
    RadioButton rbTab1;
    @BindView(R.id.rbTab2)
    RadioButton rbTab2;
    @BindView(R.id.rbTab3)
    RadioButton rbTab3;
    @BindView(R.id.rgTab)
    RadioGroup rgTab;
    @BindView(R.id.appBar)
    AppBarLayout appBar;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.rbStar)
    XRatingBar rbStar;
    @BindView(R.id.tvScore)
    TextView tvScore;
    @BindView(R.id.tvIntroduce)
    TextView tvIntroduce;
    @BindView(R.id.tvOpentime)
    TextView tvOpentime;
    @BindView(R.id.tvManPay)
    TextView tvManPay;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.llTip)
    LinearLayout llTip;
    @BindView(R.id.tvLocation)
    TextView tvLocation;
    @BindView(R.id.rvRecommend)
    XRecyclerView rvRecommend;
    @BindView(R.id.rvComment)
    XRecyclerView rvComment;
    /**
     * 展开状态下toolbar显示的内容
     */
    private View toolbarOpen;
    /**
     * 收缩状态下toolbar显示的内容
     */
    private View toolbarClose;


    HelperRecyclerViewAdapter recommendAdapter = null;
    HelperRecyclerViewAdapter commentAdapter = null;
    LinearLayoutManager recommendLayoutManager = null;
    LinearLayoutManager commentLayoutManager = null;
    private RecommentBean recommentBean;
    private CommentBean commentBean;

    @Override
    public int getLayout(Bundle bundle) {
        return R.layout.activity_detail;
    }

    @Override
    public void init() {
        toolbarOpen = findViewById(R.id.include_toolbar_open);
        toolbarClose = findViewById(R.id.include_toolbar_close);
        appBar.addOnOffsetChangedListener(this);

        initData();
        recommendLayoutManager = new LinearLayoutManager(this);
        recommendLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recommendAdapter = new RecommendInDetailAdapter(recommentBean.getItemRecommentBeanList(), this, R.layout.item_recommend_detail);
        rvRecommend.setPullRefreshEnabled(false);
        rvRecommend.setLoadingMoreEnabled(false);
        rvRecommend.setLayoutManager(recommendLayoutManager);
        rvRecommend.setNestedScrollingEnabled(false);
        rvRecommend.setAdapter(recommendAdapter);

        commentLayoutManager = new LinearLayoutManager(this);
        commentLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        commentAdapter = new CommentInDetailAdapter(commentBean.getItemCommentBeanList(), this, R.layout.item_comment_detail);
        rvComment.setPullRefreshEnabled(false);
        rvComment.setLoadingMoreEnabled(false);
        rvComment.setLayoutManager(commentLayoutManager);
        rvComment.setNestedScrollingEnabled(false);
        rvComment.setAdapter(commentAdapter);
    }


    @OnClick({R.id.ivTitleOpenBack, R.id.ivTitleCloseBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivTitleOpenBack:
                finish();
                break;
            case R.id.ivTitleCloseBack:
                finish();
                break;
        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        //垂直方向偏移量
        int offset = Math.abs(verticalOffset);
        //最大偏移距离
        int scrollRange = appBarLayout.getTotalScrollRange();
//        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) dctvSearchOpen.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
//        float a = (float) offset / (float) scrollRange;
//        linearParams.width = offset / scrollRange * (openWidth - closeWidth);// 控件的宽强制设成30
//        Log.e("aaa", (1 - a) * (openWidth - closeWidth) + "");
//        dctvSearchOpen.setLayoutParams(linearParams);
        if (offset <= scrollRange / 2) {//当滑动没超过一半，展开状态下toolbar显示内容，根据收缩位置，改变透明值
            toolbarOpen.setVisibility(View.VISIBLE);
            toolbarClose.setVisibility(View.GONE);
            //根据偏移百分比 计算透明值
            float scale2 = (float) offset / (scrollRange / 2);
            int alpha2 = (int) (255 * scale2);
            toolbarOpen.setBackgroundColor(Color.argb(alpha2, 255, 255, 255));
        } else {//当滑动超过一半，收缩状态下toolbar显示内容，根据收缩位置，改变透明值
            toolbarClose.setVisibility(View.VISIBLE);
            toolbarOpen.setVisibility(View.GONE);
            float scale3 = (float) (scrollRange - offset) / (scrollRange / 2);
            int alpha3 = (int) (255 * scale3);
            toolbarClose.setBackgroundColor(Color.rgb(255, 255, 255));
        }
    }

    public void initData() {
        recommentBean = new RecommentBean();
        List<RecommentBean.RecommentItemBean> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            RecommentBean.RecommentItemBean bean = new RecommentBean.RecommentItemBean();
            bean.setUrl("aaaa");
            bean.setIntroduce("introduce");
            bean.setManPay("20meiren");
            bean.setName("酸辣面");
            list.add(bean);
        }
        recommentBean.setItemRecommentBeanList(list);

        commentBean = new CommentBean();
        List<CommentBean.CommentItemBean> list1 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            CommentBean.CommentItemBean bean = new CommentBean.CommentItemBean();
            bean.setDate(" aaa");
            bean.setManName("nihao");
            bean.setManSay("agsdfghjkl");
            bean.setManUrl("fioawefbkjal");
            bean.setScore(4.0f);
            List<String> list2 = new ArrayList<>();
            list2.add("urla");
            list2.add("urla");
            list2.add("urla");
            bean.setUrlList(list2);
            list1.add(bean);
        }
        commentBean.setItemRecommentBeanList(list1);

        Log.e("initData: ", FastJsonUtil.changObjectToString(commentBean));
        Log.e("initData: ", FastJsonUtil.changObjectToString(recommentBean));
    }


}
