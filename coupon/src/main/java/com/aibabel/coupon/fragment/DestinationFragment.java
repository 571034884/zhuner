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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.aibabel.coupon.R;
import com.aibabel.coupon.activity.ReceiveActivity;
import com.aibabel.coupon.adapter.CommomRecyclerAdapter;
import com.aibabel.coupon.adapter.CommonRecyclerViewHolder;
import com.aibabel.coupon.bean.CountryBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 *
 * 目的地 首页
 */
public class DestinationFragment extends Fragment {
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
    Unbinder unbinder;

    private ViewStub vsTest;
    private int[] country_img = {R.mipmap.japan, R.mipmap.thailand, R.mipmap.korea, R.mipmap.others};
    private CommomRecyclerAdapter adapter;
    private List<CountryBean> countryList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.activity_base_mudidi, container, false);
        unbinder = ButterKnife.bind(this, inflate);
        // 搜索框 的 悬浮 效果
        vsTest = inflate.findViewById(R.id.vs_test);
        vsTest.setLayoutResource(R.layout.stub_mudidi);
        View iv_vsContent = vsTest.inflate();
        rvCountry = iv_vsContent.findViewById(R.id.rv_country);
        tvMudidi = iv_vsContent.findViewById(R.id.tv_mudidi);
        tvCoupon = iv_vsContent.findViewById(R.id.tv_coupon);

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


        return inflate;
    }

    public void initTitle() {
        ivLeft.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        ivRight.setVisibility(View.GONE);

        ivLeft.setVisibility(getActivity().getIntent().getIntExtra("isback", View.GONE));

        ivLeft.setImageResource(R.mipmap.ic_back);
        tvTitle.setHint(getResources().getString(R.string.search_hint_country));
        tvTitle.setHintTextColor(getResources().getColor(R.color.color_99));
        tvTitle.setBackgroundResource(R.drawable.bg_search_80while);
        ivRight.setImageResource(R.mipmap.ic_home_w);
        clRoot.setAlpha(1);
    }


    private void initData() {
        countryList.clear();
        countryList.add(new CountryBean(getResources().getString(R.string.country_japan), getResources().getString(R.string.country_japan_eng), country_img[0]));
        countryList.add(new CountryBean(getResources().getString(R.string.country_Thailand), getResources().getString(R.string.country_Thailand_eng), country_img[1]));
        countryList.add(new CountryBean(getResources().getString(R.string.country_korea), getResources().getString(R.string.country_korea_eng), country_img[2]));
        countryList.add(new CountryBean(getResources().getString(R.string.country_other), getResources().getString(R.string.country_other_eng), country_img[3]));
        adapter.updateData(countryList);
    }

    private void initAdapter() {


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //设置布局管理器
        rvCountry.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        adapter = new CommomRecyclerAdapter(getActivity(), countryList, R.layout.recy_country, new CommomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommonRecyclerViewHolder holder, int postion) {
                String country_name = countryList.get(postion).getCountry_name();
                String country_name_english = countryList.get(postion).getCountry_name_english();
                int country_img = countryList.get(postion).getCountry_img();
                Intent intent = new Intent(getActivity(), ReceiveActivity.class);
                intent.putExtra("country_name", country_name);
                intent.putExtra("country_name_english", country_name_english);
                intent.putExtra("country_img", country_img);
                startActivity(intent);
            }
        }, null) {


            @Override
            public void convert(CommonRecyclerViewHolder holder, Object o, int position) {
                TextView tv_country_name = holder.getView(R.id.tv_country_name);
                TextView tv_country_english = holder.getView(R.id.tv_country_english);
                ImageView iv_country_img = holder.getView(R.id.iv_country_img);
                tv_country_name.setText(((CountryBean) o).getCountry_name());
                tv_country_english.setText(((CountryBean) o).getCountry_name_english());
                iv_country_img.setImageResource(((CountryBean) o).getCountry_img());

            }
        };
        rvCountry.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
