package com.aibabel.travel.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.travel.R;
import com.aibabel.travel.adaper.CommomRecyclerAdapter;
import com.aibabel.travel.adaper.CommonRecyclerViewHolder;
import com.aibabel.travel.app.UrlConfig;
import com.aibabel.travel.bean.BaseModel;
import com.aibabel.travel.bean.CityBean;
import com.aibabel.travel.db.DataDAO;
import com.aibabel.travel.http.ResponseCallback;
import com.aibabel.travel.utils.CommonUtils;
import com.aibabel.travel.utils.GlideCacheUtil;
import com.aibabel.travel.utils.NetworkUtils;
import com.aibabel.travel.utils.StringUtil;
import com.aibabel.travel.widgets.GridSpacingItemDecoration;
import com.aibabel.travel.widgets.MyGridLayoutManager;
import com.aibabel.travel.widgets.MyRecyclerView;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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
 * @Desc：国家页 ==========================================================================================
 */
public class CountryActivity extends BaseActivity implements ResponseCallback {
    @BindView(R.id.tv_country)
    TextView tvCountry;
    MyRecyclerView rvCity;
    TextView tvCityEmpty;
    @BindView(R.id.iv_title)
    ImageView ivTitle;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.cl_root)
    ConstraintLayout clRoot;
    @BindView(R.id.appbar)
    AppBarLayout appbar;

    ViewStub vsTest;


    private CommomRecyclerAdapter<CityBean.DataBean.ResultsBean> adapter;
    private List<CityBean.DataBean.ResultsBean> list = new ArrayList<>();
    private CityBean bean = new CityBean();
    private String urlCity;
    private String name;
    private int first;
    private boolean isStartActivity = true;
    private boolean offline;

    @Override
    public int initLayout() {
        return R.layout.activity_base_country;
    }

    @Override
    public void init() {
        vsTest = findViewById(R.id.vs_test);
        vsTest.setLayoutResource(R.layout.stub_country);
        View iv_vsContent = vsTest.inflate();
        tvCityEmpty = iv_vsContent.findViewById(R.id.tv_city_empty);
        rvCity = iv_vsContent.findViewById(R.id.rv_city);
        initView();
        initTitle();
        initData();

//        GlideCacheUtil.getInstance().clearImageAllCache(this);
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float percent = Float.valueOf(Math.abs(verticalOffset)) / Float.valueOf(appBarLayout.getTotalScrollRange());
                //第一种
                int toolbarHeight = appBarLayout.getTotalScrollRange();
                int dy = Math.abs(verticalOffset);
//                Log.e(TAG, "onOffsetChanged: " + dy + "   " + clRoot.getHeight());
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

        ivTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(CountryActivity.this,ServiceActivity.class));
            }
        });
    }


    public void initView() {
        tvTitle.setOnClickListener(this);
        ivLeft.setOnClickListener(this);
        ivRight.setOnClickListener(this);
        initRecycleView();
    }

    public void initTitle() {
        ivLeft.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        ivRight.setVisibility(View.VISIBLE);

        ivLeft.setVisibility(getIntent().getIntExtra("first", View.VISIBLE));

        ivLeft.setImageResource(R.mipmap.ic_back);
        tvTitle.setHint(getResources().getString(R.string.search_hint));
        tvTitle.setHintTextColor(getResources().getColor(R.color.color_66));
        tvTitle.setBackgroundResource(R.drawable.bg_search_80while);
        ivRight.setImageResource(R.mipmap.ic_home_w);

        clRoot.setAlpha(1);
    }


    public void initData() {
        String id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        first = getIntent().getIntExtra("first", 0);
        offline = getIntent().getBooleanExtra("offline", false);

        Map map1 = new HashMap();
        map1.put("p1",name);
        setPathParams(JSONObject.toJSON(map1).toString());

        if (first == 1) {
            ivLeft.setVisibility(View.GONE);
        }
        CommonUtils.setMargins(tvTitle, ivLeft.getVisibility()==View.GONE?20:0, 0, 0, 0);
        tvCountry.setText(name);

        if(offline){
            if (!TextUtils.isEmpty(name)) {
                list = DataDAO.queryCitys(name);
            }
            adapter.updateData(list);
            if (list.size() > 2) {
                urlCity = list.get(1).getCover();
            } else if (list.size() == 1) {
                urlCity = list.get(0).getCover();
            }
            setTitleBackGround(urlCity);
        }else{

                Map<String, String> map = new HashMap<>();
                map.put("GetCityCountryIdIs", id);
                get(this, this, CityBean.class, map, UrlConfig.CMD_CITY);

        }

//        if (CommonUtils.isAvailable()) {
//            Map<String, String> map = new HashMap<>();
//            map.put("GetCityCountryIdIs", id);
//            get(this, this, CityBean.class, map, UrlConfig.CMD_CITY);
//        } else {
//            if (!TextUtils.isEmpty(name)) {
//                list = DataDAO.queryCitys(name);
//            }
//            adapter.updateData(list);
//            if (list.size() > 2) {
//                urlCity = list.get(1).getCover();
//            } else if (list.size() == 1) {
//                urlCity = list.get(0).getCover();
//            }
//            setTitleBackGround(urlCity);
//        }

    }


    /*初始化列表*/
    public void initRecycleView() {
        MyGridLayoutManager gridLayoutManager = new MyGridLayoutManager(this, 2);
//        gridLayoutManager.setScrollEnabled(false);
        rvCity.setLayoutManager(gridLayoutManager);
        rvCity.setEmptyView(tvCityEmpty);
        rvCity.addItemDecoration(new GridSpacingItemDecoration(14));
//        rvCity.setEmptyView(tv_main_empty); //设置空布局
        adapter = new CommomRecyclerAdapter<CityBean.DataBean.ResultsBean>(this, list, R.layout.item_city, new CommomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommonRecyclerViewHolder holder, int postion) {

                toSpots(list.get(postion).getId() + "", list.get(postion).getCover(), list.get(postion).getName());

            }

        }, null) {
            @Override
            public void convert(CommonRecyclerViewHolder holder, CityBean.DataBean.ResultsBean bean, int position) {
                TextView tv_city = ((CommonRecyclerViewHolder) holder).getView(R.id.tv_item_city);
                ImageView iv_city = ((CommonRecyclerViewHolder) holder).getView(R.id.iv_item_city);
                tv_city.setText(bean.getName());

//                RequestOptions options = new RequestOptions().placeholder(R.mipmap.placeholder_h).error(R.mipmap.error_h).bitmapTransform(new ColorFilterTransformation(R.color.color_tr));
                RequestOptions options = new RequestOptions().placeholder(R.mipmap.placeholder_h).error(R.mipmap.error_h);
                Glide.with(CountryActivity.this)
                        .load(bean.getCover())
                        .apply(options)
                        .into(iv_city);

                Log.e("mmm", ">>>>>>>" + bean.getCover());
            }

        };
//        添加分割线
        rvCity.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        isStartActivity = true;
    }

    private void initOnClickable(View holder, boolean isStartActivity) {
        if (isStartActivity) {
            holder.setClickable(true);
        } else {
            holder.setClickable(false);
        }
    }


    private void toSpots(String id, String url, String name) {

        Intent intent = new Intent(CountryActivity.this, CityActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("url", url);
        intent.putExtra("name", name);
        intent.putExtra("offline", offline);
        startActivity(intent);
    }

    private void toWorld() {
        Intent intent = new Intent(CountryActivity.this, WorldActivity.class);
        startActivity(intent);
    }


    private void toSearch() {
        Intent intent = new Intent(CountryActivity.this, SearchPageActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_title:
                if (!NetworkUtils.isAvailable(this)) {
                    toastShort(getString(R.string.net_unavailable));
                    return;
                }
                if (isStartActivity) {
                    toSearch();
                    initOnClickable(tvTitle, isStartActivity);
                    isStartActivity = false;
                }

                break;
            case R.id.iv_right:
                if (isStartActivity) {
                    toWorld();
                    initOnClickable(ivRight, isStartActivity);
                    isStartActivity = false;
                }
                break;
            case R.id.iv_left:
                finish();
                break;
        }
    }


    @Override
    public void onSuccess(String method, BaseModel model) {
        bean = (CityBean) model;
        list = bean.getData().getResults();
        adapter.updateData(list);
        if (null != list) {
            if (list.size() > 2) {
                urlCity = list.get(1).getCover();
            } else {
                urlCity = list.get(0).getCover();
            }
        }
        setTitleBackGround(urlCity);
    }


    @Override
    public void onError() {

    }

    private void setTitleBackGround(String url) {
        RequestOptions options = new RequestOptions().placeholder(R.mipmap.placeholder_h).error(R.mipmap.error_h);
        Glide.with(CountryActivity.this)
                .load(url)
                .apply(options)
                .into(ivTitle);
    }

}