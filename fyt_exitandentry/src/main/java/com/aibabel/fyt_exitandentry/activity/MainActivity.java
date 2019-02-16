package com.aibabel.fyt_exitandentry.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.baselibrary.http.BaseBean;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.fyt_exitandentry.R;
import com.aibabel.fyt_exitandentry.adapter.MyPagerAdapter;
import com.aibabel.fyt_exitandentry.bean.CityAirplaneBean;
import com.aibabel.fyt_exitandentry.bean.Constans;
import com.aibabel.fyt_exitandentry.fragment.AirportFragment;
import com.aibabel.fyt_exitandentry.utils.ContentProviderUtil;
import com.aibabel.fyt_exitandentry.utils.NetUtil;
import com.aibabel.fyt_exitandentry.utils.ToastUtil;
import com.aibabel.fyt_exitandentry.utils.WrapContentHeightViewPager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements TabLayout.OnTabSelectedListener, AppBarLayout.OnOffsetChangedListener, BaseCallback<BaseBean> {


    @BindView(R.id.appBar)
    AppBarLayout appBar;
    @BindView(R.id.country_img_cl_layout)
    RelativeLayout countryImgClLayout;
    @BindView(R.id.tv_country_name)
    TextView tvCountryName;
    @BindView(R.id.tv_city_name)
    TextView tvCityName;
    @BindView(R.id.choice_city_ll_layout)
    LinearLayout choiceCityLlLayout;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    WrapContentHeightViewPager viewPager;
    @BindView(R.id.airport_cl_layout)
    LinearLayout airportClLayout;
    @BindView(R.id.btn_top)
    Button btnTop;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.col)
    CoordinatorLayout col;
    @BindView(R.id.iv_country_img)
    ImageView ivCountryImg;
    //机场名字
    private List<String> airportList = null;
    //Fragment集合
    private List<Fragment> fragmentList =null;
    private MyPagerAdapter myPagerAdapter;
    /**
     * 展开状态下toolbar显示的内容
     */
    private View toolbarOpen;
    /**
     * 收缩状态下toolbar显示的内容
     */
    private View toolbarClose;
    private CityAirplaneBean cityAirplaneBean;

    private List<CityAirplaneBean.DataBean> airplaneBeanData;
    private String country = "";
    private String city = "";
    private TextView tvCityName1;
    private TextView tvCountryName1;
    private LinearLayout choiceCityLlLayout1;
    private int air;

    @Override
    public int getLayout(Bundle bundle) {
        return R.layout.activity_home_page;
    }

    @Override
    public void init() {
        Constans.HOST_XW = ContentProviderUtil.getHost(this);
//        Constans.CITY = ContentProviderUtil.getCity(this);
//        Constans.COUNTRY = ContentProviderUtil.getCountry(this);
        toolbarOpen = findViewById(R.id.include_toolbar_open);
        toolbarClose = findViewById(R.id.include_toolbar_close);
        choiceCityLlLayout1 = toolbarClose.findViewById(R.id.choice_city_ll_layout);
        tvCityName1 = toolbarClose.findViewById(R.id.tv_city_name);
        tvCountryName1 = toolbarClose.findViewById(R.id.tv_country_name);
        appBar.addOnOffsetChangedListener(this);
        if (NetUtil.isNetworkAvailable(this)) {
            initData();
        } else {
            ToastUtil.showShort(this, "当前无网络");
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( resultCode == RESULT_OK) {

            Constans.COUNTRY = data.getStringExtra("country");
            Constans.CITY = data.getStringExtra("city");
            Map<String, String> map = new HashMap<>();
            map.put("countryChj", Constans.COUNTRY );
//        map.put("countryChj","日本");
            map.put("cityChj",  Constans.CITY);
//        map.put("cityChj","东京");
            OkGoUtil.<CityAirplaneBean>get(MainActivity.this, Constans.METHOD_GETCITYAIRPLANE, map, CityAirplaneBean.class, this);

        }
    }

    private void initData() {
//        if (TextUtils.equals(country, "") && TextUtils.equals(city, "")) {
//            country = "日本";
//            city = "东京";
//        }

        Map<String, String> map = new HashMap<>();
        map.put("countryChj", Constans.COUNTRY);
//        map.put("countryChj","日本");
        map.put("cityChj", Constans.CITY);
//        map.put("cityChj","东京");
        OkGoUtil.<CityAirplaneBean>get(MainActivity.this, Constans.METHOD_GETCITYAIRPLANE, map, CityAirplaneBean.class, this);


//        if (TextUtils.equals(Constans.CITY,"")&&TextUtils.equals(Constans.COUNTRY,"")){
//            Map<String, String> map = new HashMap<>();
//            OkGoUtil.<CityAirplaneBean>get(this, Constans.METHOD_GETCITYLIST, map, CityAirplaneBean.class, this);
//        }
//
//        Map<String, String> map = new HashMap<>();
//        map.put("countryChj","日本");
//        map.put("cityChj","东京");
//        OkGoUtil.<CityAirplaneBean>get(this, Constans.METHOD_GETCITYAIRPLANE, map, CityAirplaneBean.class, this);


//        Intent intent = getIntent();
//        airplaneBeanData = (List<CityAirplaneBean.DataBean>) intent.getSerializableExtra("list");
//        tvCountryName.setText(airplaneBeanData.get(0).getCountryChj());
//        tvCityName.setText(airplaneBeanData.get(0).getCityChj());
//        creatTablayout(airplaneBeanData);
        btnTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });

        choiceCityLlLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map map = new HashMap();
                StatisticsManager.getInstance(mContext).addEventAidl("切换目的地",map);
                Intent intent1 = new Intent(MainActivity.this, ChoiceCityActivity.class);
                startActivityForResult(intent1, 10);
            }
        });
        choiceCityLlLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map map = new HashMap();
                StatisticsManager.getInstance(mContext).addEventAidl("切换目的地",map);
                Intent intent1 = new Intent(MainActivity.this, ChoiceCityActivity.class);
                startActivityForResult(intent1, 10);
            }
        });

        countryImgClLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent1 = new Intent(MainActivity.this, NoCityActivity.class);
//                startActivityForResult(intent1, 10);
            }
        });
    }

    private void creatTablayout(List<CityAirplaneBean.DataBean> airplaneBeanData) {

        for (int i = 0; i < airplaneBeanData.size(); i++) {
            Log.e("air", airplaneBeanData.size() + "=");
            air = airplaneBeanData.size() ;

            airportList.add(airplaneBeanData.get(i).getAirportName());
            TabLayout.Tab airportTab = tabLayout.newTab().setText(airportList.get(i).toString());
            tabLayout.addTab(airportTab);
            CityAirplaneBean.DataBean dataBean = airplaneBeanData.get(i);
            fragmentList.add(new AirportFragment(dataBean));

        }
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tabLayout, 30, 30);
            }
        });
        tabLayout.addOnTabSelectedListener(this);

        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), airportList, fragmentList);
        viewPager.setAdapter(myPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
//                updateFragment.updateFragmentDate(airplaneBeanData.get(i));
                viewPager.requestLayout();
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                    if (air>1){

                        scrollView.fullScroll(ScrollView.FOCUS_UP);
                    }


            }
        });
    }


    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("slidingTabIndicator");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }


    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

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


    @Override
    public void onSuccess(String method, BaseBean baseBean, String s1) {
        switch (method) {
            case Constans.METHOD_GETCITYAIRPLANE:
                fragmentList = new ArrayList<>();
                airportList= new ArrayList<>();
                cityAirplaneBean = (CityAirplaneBean) baseBean;
                airplaneBeanData = cityAirplaneBean.getData();
                tvCountryName.setText(airplaneBeanData.get(0).getCountryChj());
                tvCountryName1.setText(airplaneBeanData.get(0).getCountryChj());
                tvCityName.setText(airplaneBeanData.get(0).getCityChj());
                tvCityName1.setText(airplaneBeanData.get(0).getCityChj());
                creatTablayout(airplaneBeanData);


                SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        countryImgClLayout.setBackground(resource);
                    }
                };
                RequestOptions options = new RequestOptions().placeholder(R.mipmap.jiazaizhong1).error(R.mipmap.jiazaishibai1);

                Glide.with(MainActivity.this).load(airplaneBeanData.get(0).getCityimageurl()).apply(options).into(simpleTarget);
//                Glide.with(MainActivity.this).load(airplaneBeanData.get(0).get)
                scrollView.fullScroll(ScrollView.FOCUS_UP);
                break;

        }
    }

    @Override
    public void onError(String s, String s1, String s2) {

    }

    @Override
    public void onFinsh(String s) {

    }



    /* *//*
     * 更新fragment 的数据
     *
     * *//*
    public interface UpdateFragment {
        void updateFragmentDate(CityAirplaneBean.DataBean dataBean);
    }

    public void setData(UpdateFragment updateFragment) {
        this.updateFragment = updateFragment;
    }*/
}
