package com.aibabel.food.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.baselibrary.adapter.BaseRecyclercAdapter;
import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.food.R;
import com.aibabel.food.adapter.HomepageAdapter;
import com.aibabel.food.base.Constant;
import com.aibabel.food.bean.HomePageAllBean;
import com.aibabel.food.custom.DrawableCenterTextView;
import com.aibabel.food.utils.CommonUtils;
import com.bumptech.glide.request.RequestOptions;
import com.stx.xhb.xbanner.XBanner;
import com.zhouyou.recyclerview.XRecyclerView;
import com.zhouyou.recyclerview.adapter.HelperStateRecyclerViewAdapter;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class HomePageActivityWant extends BaseActivity implements AppBarLayout
        .OnOffsetChangedListener, BaseCallback<HomePageAllBean>, BaseRecyclercAdapter
        .OnErrorClickListener {

    @BindView(R.id.dctvCityOpen)
    DrawableCenterTextView dctvCityOpen;
    @BindView(R.id.dctvSearchOpen)
    DrawableCenterTextView dctvSearchOpen;
    @BindView(R.id.dctvCityClose)
    DrawableCenterTextView dctvCityClose;
    @BindView(R.id.dctvSearchClose)
    DrawableCenterTextView dctvSearchClose;
    @BindView(R.id.appBar)
    AppBarLayout appBar;
    @BindView(R.id.rvHomepage)
    XRecyclerView rvHomepage;
    @BindView(R.id.xbanner)
    XBanner xbanner;
    /**
     * 展开状态下toolbar显示的内容
     */
    private View toolbarOpen;
    /**
     * 收缩状态下toolbar显示的内容
     */
    private View toolbarClose;

    private int openWidth;
    private int openHeigth;
    private int closeWidth;
    private int closeHeight;

    BaseRecyclercAdapter adapter = null;
    LinearLayoutManager layoutManager = null;

    @Override
    public int getLayout(Bundle bundle) {
        return R.layout.activity_home_page;
    }

    @Override
    public void init() {
        setHotRepairEnable(true);
        toolbarOpen = findViewById(R.id.include_toolbar_open);
        toolbarClose = findViewById(R.id.include_toolbar_close);

        dctvCityOpen.setText(Constant.CURRENT_CITY);
        dctvCityClose.setText(Constant.CURRENT_CITY);

        xbanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                HomePageAllBean.DataBean.BannerJsonBean item = (HomePageAllBean.DataBean
                        .BannerJsonBean) model;
                RequestOptions requestOptions = new RequestOptions()
                        .placeholder(new ColorDrawable(Color.BLACK))
                        .error(new ColorDrawable(Color.BLUE))
                        .fallback(new ColorDrawable(Color.RED));
                ImageView imageView = ((ImageView) view);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                CommonUtils.setPicture540x280(item.getUrl(), imageView);
            }
        });

        xbanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                startActivity(new Intent(HomePageActivityWant.this, Html5Activity.class).putExtra("bannerId", ((HomePageAllBean.DataBean.BannerJsonBean) model).getId())
                        .putExtra("bannerName", ((HomePageAllBean.DataBean.BannerJsonBean) model).getNameCn())
                        .putExtra("where", "banner"));
            }
        });

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new HomepageAdapter(this);
        rvHomepage.setLayoutManager(layoutManager);
        rvHomepage.setPullRefreshEnabled(false);
        rvHomepage.setLoadingMoreEnabled(false);
        rvHomepage.setNestedScrollingEnabled(false);
        //设置分割线
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration
                .VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.homepage_divider));
        rvHomepage.addItemDecoration(divider);
        rvHomepage.setAdapter(adapter);
        adapter.setOnErrorClickListener(this::requestAgain);

        initDate();
    }

    public void initDate() {
        adapter.setState(HelperStateRecyclerViewAdapter.STATE_LOADING);//模拟加载中
        Map<String, String> map = new HashMap<>();
        map.put("city", Constant.CURRENT_CITY);
        OkGoUtil.<HomePageAllBean>get(false, Constant.METHOD_HOMEPAGE_ALL, map, HomePageAllBean
                .class, this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        openWidth = dctvSearchOpen.getWidth();
        openHeigth = dctvSearchOpen.getHeight();
        closeWidth = dctvSearchClose.getWidth();
        closeHeight = dctvSearchClose.getHeight();
        Log.e("aa", "onWindowFocusChanged: width = " + openWidth + openHeigth + "   height = " +
                closeWidth + closeHeight);
    }

    @Override
    protected void onResume() {
        super.onResume();
        appBar.addOnOffsetChangedListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        appBar.removeOnOffsetChangedListener(this);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        //垂直方向偏移量
        int offset = Math.abs(verticalOffset);
        //最大偏移距离
        int scrollRange = appBarLayout.getTotalScrollRange();
//        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) dctvSearchOpen
// .getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
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

    @OnClick({R.id.dctvCityOpen, R.id.dctvSearchOpen, R.id.dctvCityClose, R.id.dctvSearchClose})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dctvCityOpen:
                startActivityForResult(AreaSelectActivity.class, 666);
                break;
            case R.id.dctvSearchOpen:
                startActivity(SearchActivity.class);
                break;
            case R.id.dctvCityClose:
                startActivityForResult(AreaSelectActivity.class, 666);
                break;
            case R.id.dctvSearchClose:
                startActivity(SearchActivity.class);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constant.RESULT_CODE_AREA_SELECT) {
            dctvCityClose.setText(data.getExtras().getString("cityName"));
            dctvCityOpen.setText(data.getExtras().getString("cityName"));
            initDate();
        }
    }

    @Override
    public void onSuccess(String method, HomePageAllBean homePageAllBean, String s1) {
        switch (method) {
            case Constant.METHOD_HOMEPAGE_ALL:
                xbanner.setData(homePageAllBean.getData().getBanner_json(), null);
                adapter.setListAll(homePageAllBean.getData().getBegion_shoptype_json());
                break;
        }
    }

    @Override
    public void onError(String s, String s1, String s2) {
        Log.e("onError: ", s + ":" + s1);
        adapter.setState(HelperStateRecyclerViewAdapter.STATE_ERROR);
    }

    @Override
    public void onFinsh(String s) {
    }

    @Override
    public void requestAgain() {
        initDate();
    }
}
