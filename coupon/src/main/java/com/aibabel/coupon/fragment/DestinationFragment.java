package com.aibabel.coupon.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.baselibrary.base.BaseFragment;
import com.aibabel.baselibrary.base.StatisticsBaseActivity;
import com.aibabel.baselibrary.http.BaseBean;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.coupon.R;
import com.aibabel.coupon.activity.CouponActivity;
import com.aibabel.coupon.activity.ReceiveActivity;
import com.aibabel.coupon.activity.SearchActivity;
import com.aibabel.coupon.adapter.CommomRecyclerAdapter;
import com.aibabel.coupon.adapter.CommonRecyclerViewHolder;
import com.aibabel.coupon.bean.Constans;
import com.aibabel.coupon.bean.CountryBean;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.okgo.model.Response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * <p>
 * 目的地 首页
 */
public class DestinationFragment extends BaseFragment implements BaseCallback<BaseBean> {
    RecyclerView rvCountry;
    TextView tvMudidi;
    TextView tvCoupon;
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
    @BindView(R.id.ll_root)
    CoordinatorLayout llRoot;
    private final int requestCode = 100;
    private CouponActivity activity;


    private ViewStub vsTest;
    private int[] country_img = {R.mipmap.japan, R.mipmap.thailand, R.mipmap.korea, R.mipmap.others};
    private CommomRecyclerAdapter adapter;
    private CountryBean countryBean;
    private List<CountryBean.DataBean> countryBeanData = new ArrayList<>();


    @Override
    public int getLayout() {
        return R.layout.activity_base_mudidi;
    }

    @Override
    public void init(View view, Bundle bundle) {
        vsTest = view.findViewById(R.id.vs_test);
        vsTest.setLayoutResource(R.layout.stub_mudidi);
        View iv_vsContent = vsTest.inflate();
        rvCountry = iv_vsContent.findViewById(R.id.rv_country);
        tvMudidi = iv_vsContent.findViewById(R.id.tv_mudidi);
        tvCoupon = iv_vsContent.findViewById(R.id.tv_coupon);
        activity = (CouponActivity)getActivity();
        initTitle();

        // 国家 recyclerview
        initAdapter();
        // 添加国家 数据
        initData();

        //搜索框的 动画渐变效果
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float percent = Float.valueOf(Math.abs(verticalOffset)) / Float.valueOf(appBarLayout.getTotalScrollRange());
                //第一种
                int toolbarHeight = appBarLayout.getTotalScrollRange();
                int dy = Math.abs(verticalOffset);
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

        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });


    }

    public void initTitle() {
        ivLeft.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        ivRight.setVisibility(View.GONE);
        Intent intent = getActivity().getIntent();
        String from = intent.getStringExtra("from");

        if (TextUtils.equals("map", from)) {
            ivLeft.setVisibility(getActivity().getIntent().getIntExtra("isback", View.VISIBLE));
        }else {
            ivLeft.setVisibility(getActivity().getIntent().getIntExtra("isback", View.GONE));
        }
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });


        ivLeft.setImageResource(R.mipmap.ic_back);
        tvTitle.setHint(getResources().getString(R.string.search_hint_shop));
        tvTitle.setHintTextColor(getResources().getColor(R.color.color_99));
        tvTitle.setBackgroundResource(R.drawable.bg_search_80while);
        ivRight.setImageResource(R.mipmap.ic_home_w);
        clRoot.setAlpha(1);


    }


    private void initData() {
     /*   countryList.clear();
        countryList.add(new CountryBean(getResources().getString(R.string.country_japan), getResources().getString(R.string.country_japan_eng), country_img[0]));
        countryList.add(new CountryBean(getResources().getString(R.string.country_Thailand), getResources().getString(R.string.country_Thailand_eng), country_img[1]));
        countryList.add(new CountryBean(getResources().getString(R.string.country_korea), getResources().getString(R.string.country_korea_eng), country_img[2]));
        countryList.add(new CountryBean(getResources().getString(R.string.country_other), getResources().getString(R.string.country_other_eng), country_img[3]));
        adapter.updateData(countryList);*/
        Map<String, String> map = new HashMap<>();
        OkGoUtil.<CountryBean>get(getActivity(), Constans.METHOD_GETCOUPONCOUNTRY, map, CountryBean.class, this);
    }

    private void initAdapter() {


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //设置布局管理器
        rvCountry.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        adapter = new CommomRecyclerAdapter(getActivity(), countryBeanData, R.layout.recy_country, new CommomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommonRecyclerViewHolder holder, int postion) {

                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    HashMap<String, Serializable> add_hp = new HashMap<>();
                    add_hp.put("coupon_name_def", countryBeanData.get(postion).getCountryname());
                    ((StatisticsBaseActivity)getActivity()).addStatisticsEvent("coupon_main1", add_hp);
                }catch (Exception e){
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/


                String country_name = countryBeanData.get(postion).getCountryname();
                String country_name_english = countryBeanData.get(postion).getCountryengname();
                String country_img = countryBeanData.get(postion).getCountryimage();
                String country_banner_img = countryBeanData.get(postion).getBannerimage();
                Intent intent = new Intent(getActivity(), ReceiveActivity.class);
                intent.putExtra("country_name", country_name);
                intent.putExtra("country_name_english", country_name_english);
                intent.putExtra("country_img", country_img);
                intent.putExtra("country_banner_img", country_banner_img);
                startActivityForResult(intent,requestCode);
            }
        }, null) {


            @Override
            public void convert(CommonRecyclerViewHolder holder, Object o, int position) {
                TextView tv_country_name = holder.getView(R.id.tv_country_name);
                TextView tv_country_english = holder.getView(R.id.tv_country_english);
                ImageView iv_country_img = holder.getView(R.id.iv_country_img);
                tv_country_name.setText(((CountryBean.DataBean) o).getCountryname());
                tv_country_english.setText(((CountryBean.DataBean) o).getCountryengname());
                Glide.with(getActivity())
                        .load(((CountryBean.DataBean) o).getCountryimage())
                        .into(iv_country_img);

            }
        };
        rvCountry.setAdapter(adapter);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

//        if(requestCode==100&&resultCode==200){
//
//        }
        int type = data.getIntExtra("type",0);
        activity.showFragment(type);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onSuccess(String method, BaseBean baseBean, String s1) {
        switch (method) {
            case Constans.METHOD_GETCOUPONCOUNTRY:
                countryBean = (CountryBean) baseBean;
                countryBeanData = countryBean.getData();
                Log.e("size", countryBeanData.size() + "");
                adapter.updateData(countryBeanData);
                break;
        }
    }

    @Override
    public void onError(String s, String s1, String s2) {

    }

    @Override
    public void onFinsh(String s) {

    }
}
