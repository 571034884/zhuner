package com.aibabel.travel.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.travel.R;
import com.aibabel.travel.adaper.Adapter_Spots;
import com.aibabel.travel.adaper.HeaderAndFooterWrapper;
import com.aibabel.travel.adaper.MyCommonAdapter;
import com.aibabel.travel.app.UrlConfig;
import com.aibabel.travel.bean.BaseModel;
import com.aibabel.travel.bean.DetailBean;
import com.aibabel.travel.bean.SpotChildrenBean;
import com.aibabel.travel.db.DataDAO;
import com.aibabel.travel.http.ResponseCallback;
import com.aibabel.travel.utils.CommonUtils;
import com.aibabel.travel.utils.Constant;
import com.aibabel.travel.utils.FastJsonUtil;
import com.aibabel.travel.utils.NetworkUtils;
import com.aibabel.travel.utils.StringUtil;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;

/**
 * ==========================================================================================
 *
 * @Author：CreateBy 张文颖
 * @Date：2018/6/19
 * @Desc：景区页面 
 ==========================================================================================
 */
public class SpotActivity extends BaseActivity implements ResponseCallback {


    //    @BindView(R.id.rv_scenic_spot)
    RecyclerView mRecyclerView;
    //    @BindView(R.id.tv_back)
//    TextView tvBack;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.cl_root)
    ConstraintLayout clRoot;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    ViewStub vsTest;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_englishName)
    TextView tvEnglishName;
    @BindView(R.id.iv_play)
    ImageView ivPlay;
    @BindView(R.id.rl_place)
    RelativeLayout rlPlace;
    TextView tvCount;

    private String id;
    private String urlSpot;
    private int count;
    private List<SpotChildrenBean.DataBean.ResultsBean> list = new ArrayList<>();
    private SpotChildrenBean bean = new SpotChildrenBean();
    private String name;
    private MyCommonAdapter<SpotChildrenBean.DataBean.ResultsBean> mAdapter;
    private String audioUrl;
    private int pageNum = 0;
    private int oldPosition = 0;
    private LinearLayout headerView;
    private View footerView;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private EmptyWrapper mEmptyWrapper;
    private LoadMoreWrapper mLoadMoreWrapper;

    LinearLayout ll_loading;

    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    @Override
    public int initLayout() {
        return R.layout.activity_base_spot;
    }

    @Override
    public void init() {
        vsTest = findViewById(R.id.vs_test);
        vsTest.setLayoutResource(R.layout.stub_spot);
        View iv_vsContent = vsTest.inflate();
        mRecyclerView = iv_vsContent.findViewById(R.id.rv_scenic_spot);
        initView();
        initTitle();
        initData();
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float percent = Float.valueOf(Math.abs(verticalOffset)) / Float.valueOf(appBarLayout.getTotalScrollRange());
                //第一种
                int toolbarHeight = appBarLayout.getTotalScrollRange();
                int dy = Math.abs(verticalOffset);
                Log.e(TAG, "onOffsetChanged: " + dy + "   " + clRoot.getHeight());
                if (dy < toolbarHeight - clRoot.getHeight()) {
                    float scale = (float) dy / toolbarHeight;
                    float alpha = scale * 255;
                    clRoot.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                    ivLeft.setImageResource(R.mipmap.ic_back);
                    ivRight.setImageResource(R.mipmap.ic_home_w);
                } else {
                    clRoot.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));
                    ivLeft.setImageResource(R.mipmap.ic_backb);
                    ivRight.setImageResource(R.mipmap.ic_home_o);
                }
            }
        });
    }


    public void initView() {
        ivLeft.setOnClickListener(this);
        tvTitle.setOnClickListener(this);
        ivRight.setOnClickListener(this);
        initRecycleView();
    }

    public void initTitle() {
        ivLeft.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        ivRight.setVisibility(View.VISIBLE);

        ivLeft.setImageResource(R.mipmap.ic_backb);
        tvTitle.setHint(getResources().getString(R.string.search_hint));
        tvTitle.setHintTextColor(getResources().getColor(R.color.color_66));
        tvTitle.setBackgroundResource(R.drawable.bg_search_80while);
        ivRight.setImageResource(R.mipmap.ic_home_w);
    }


    public void initData() {
        id = getIntent().getStringExtra("id");
        urlSpot = getIntent().getStringExtra("url");
        audioUrl = getIntent().getStringExtra("audioUrl");
        name = getIntent().getStringExtra("name");
        tvName.setText(name);
        setSpotImage(urlSpot);

        Map map1 = new HashMap();
        map1.put("p1",name);
        setPathParams(JSONObject.toJSON(map1).toString());
        if (!CommonUtils.isAvailable()) {
            list = DataDAO.querySpotsChildren(name, pageNum,id);
            initRecycleView();
            mLoadMoreWrapper.notifyDataSetChanged();
            mAdapter.updateData(list);
            tvCount.setText("内含" + (list.size() + 1) + "处景区讲解");
        }
    }

    public void initRecycleView() {

        mRecyclerView = findViewById(R.id.rv_scenic_spot);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        mAdapter = new MyCommonAdapter<SpotChildrenBean.DataBean.ResultsBean>(this, R.layout.item_main, list) {
            @Override
            protected void convert(ViewHolder holder, SpotChildrenBean.DataBean.ResultsBean bean, int position) {

                ImageView iv_item_child = holder.getView(R.id.iv_item_child);
                TextView tv_item_name = holder.getView(R.id.tv_item_name);
                tv_item_name.setText(bean.getName());
                RequestOptions options = new RequestOptions().placeholder(R.mipmap.placeholder_h).error(R.mipmap.error_h).bitmapTransform(new ColorFilterTransformation(R.color.color_tr));
                Glide.with(SpotActivity.this)
                        .load(bean.getCover())
                        .apply(options)
                        .into(iv_item_child);
            }
        };

        initHeaderAndFooter();

        /*footer*/
        footerView = View.inflate(this, R.layout.default_loading, null);
        ll_loading = footerView.findViewById(R.id.ll_loading);
        mLoadMoreWrapper = new LoadMoreWrapper(mHeaderAndFooterWrapper);
        mLoadMoreWrapper.setLoadMoreView(footerView);
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                int max = StringUtil.getInt(count, Constant.PAGE_COUNT);
                if (pageNum == 0) {
                    ll_loading.setVisibility(View.GONE);
                    loadMore();
                    return;
                } else if (0 < pageNum && max == 0) {
                    return;
                } else if (0 < pageNum && pageNum < max) {
                    ll_loading.setVisibility(View.VISIBLE);
                    loadMore();
                } else {
                    ll_loading.setVisibility(View.GONE);
                    Toast.makeText(SpotActivity.this, getString(R.string.no_more), Toast.LENGTH_SHORT).show();
                }

            }
        });

        mRecyclerView.setAdapter(mLoadMoreWrapper);
        mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                long currentTime = Calendar.getInstance().getTimeInMillis();
                if (currentTime - Constant.EXIT_TIME < MIN_CLICK_DELAY_TIME) {
//                    lastClickTime = currentTime;
                    return;
                }else {
                    Constant.EXIT_TIME = currentTime;
                    toDetail(position, list);
                }
//                lastClickTime = currentTime;
                //因为有头布局 这里你不用加1了


            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }


    private void initHeaderAndFooter() {
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);
        headerView = (LinearLayout) View.inflate(this, R.layout.spot_header, null);
        tvCount = (TextView) headerView.findViewById(R.id.tv_count);
        mHeaderAndFooterWrapper.addHeaderView(headerView);
        ivPlay.setOnClickListener(this);


    }


    private void loadMore() {
        pageNum++;
        if (CommonUtils.isAvailable()) {

            Map<String, String> map = new HashMap<>();
            map.put("GetSubscenicScenicIdIs", id);
            map.put("pageNum", String.valueOf(pageNum));
            get(this, this, SpotChildrenBean.class, map, UrlConfig.CMD_SUBSCENIC);

        }
//        else {
//            if (!TextUtils.isEmpty(name)) {
//                list = DataDAO.querySpotsChildren(name, pageNum);
//            }
//        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setSpotImage(String url) {
        SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                rlPlace.setBackground(resource);
            }
        };
        RequestOptions options = new RequestOptions().placeholder(R.mipmap.placeholder_h).error(R.mipmap.error_h).bitmapTransform(new ColorFilterTransformation(R.color.color_tr));
        Glide.with(this)
                .load(url)
                .apply(options)
                .into(simpleTarget);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.iv_play:
                toDetail(0, list);
                break;
            case R.id.iv_right:
                toWorld();
                break;
            case R.id.tv_title:
                toSearch();
                break;
            default:
                break;
        }
    }

    private void toWorld() {
        Intent intent = new Intent(this, WorldActivity.class);
        startActivity(intent);
    }


    private void toSearch() {
        if (!NetworkUtils.isAvailable(this)) {
            toastShort(getString(R.string.net_unavailable));
            return;
        }
        Intent intent = new Intent(this, SearchPageActivity.class);
        startActivity(intent);
    }


    @Override
    public void onSuccess(String method, BaseModel result) {
        bean = (SpotChildrenBean) result;
        if (null == bean.getData()) {
            Toast.makeText(SpotActivity.this, "没有更多数据了", Toast.LENGTH_SHORT).show();
            return;
        }
        count = bean.getData().getCount();
        tvCount.setText("内含" + (count + 1) + "处景区讲解");

        List<SpotChildrenBean.DataBean.ResultsBean> resultsBeanList = bean.getData().getResults();
        if (null == resultsBeanList) {
//            Toast.makeText(SpotActivity.this, "没有更多数据了", Toast.LENGTH_SHORT).show();
            mLoadMoreWrapper.notifyDataSetChanged();
            return;
        }

        if (null != resultsBeanList) {
            list.addAll(resultsBeanList);
        }
        mLoadMoreWrapper.notifyDataSetChanged();

        mAdapter.updateData(list);
    }

    @Override
    public void onError() {

    }

    /**
     * 跳转到详情页面
     */
    private void toDetail(int position, List<SpotChildrenBean.DataBean.ResultsBean> list) {
//        if (!NetworkUtils.isAvailable(this)) {
//            Toast.makeText(this, "当前网络状况不佳，请稍后重试！", Toast.LENGTH_SHORT).show();
//            return;
//        }



        String json = addList(list);
        Map map1 = new HashMap();
        map1.put("p1",json);
        StatisticsManager.getInstance(SpotActivity.this).addEventAidl( 1644, map1);
        Intent intent = new Intent(SpotActivity.this, SpotDetailActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("list", json);
        startActivity(intent);
    }


    /**
     * 将返回的结果转化成String
     *
     * @param list
     * @return
     */
    private String addList(List<SpotChildrenBean.DataBean.ResultsBean> list) {
        String str = "";
        List<DetailBean> details = new ArrayList<>();
        DetailBean detail = new DetailBean();
        detail.setAudioUrl(audioUrl);
        detail.setImageUrl(urlSpot);
        detail.setName(name);
        details.add(detail);
        if (null != list && list.size() > 0) {
            for (SpotChildrenBean.DataBean.ResultsBean result : list) {
                DetailBean bean = new DetailBean();
                bean.setAudioUrl(result.getAudios().get(0).getUrl());
                bean.setImageUrl(result.getCover());
                bean.setName(result.getName());
                details.add(bean);
            }
        }
        str = FastJsonUtil.changListToString(details);
        return str;
    }
}
