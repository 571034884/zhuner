package com.aibabel.coupon.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aibabel.baselibrary.base.BaseFragment;
import com.aibabel.baselibrary.http.BaseBean;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.coupon.R;
import com.aibabel.coupon.activity.DetailsActivity;
import com.aibabel.coupon.activity.ReceiveActivity;
import com.aibabel.coupon.adapter.FilterAdapter;
import com.aibabel.coupon.bean.Constans;
import com.aibabel.coupon.bean.FilterBean;
import com.aibabel.coupon.bean.FilterBeanOld;
import com.aibabel.coupon.utils.CommonUtils;
import com.aibabel.coupon.utils.FastJsonUtil;
import com.zhouyou.recyclerview.XRecyclerView;
import com.zhouyou.recyclerview.adapter.BaseRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchResultFragment extends BaseFragment implements BaseCallback<FilterBean> {

    @BindView(R.id.rvFilter)
    XRecyclerView rvFilter;
    private FilterBeanOld filterBeanOld;

    FilterAdapter adapter = null;
    LinearLayoutManager layoutManager = null;
    private TextView tv_receive;
    private int couponId;
    private List<FilterBean.DataBean> beanList = new ArrayList<>();
    private String country_name;

    @Override
    public int getLayout() {
        return R.layout.fragment_search_result;
    }

    @Override
    public void init(View view, Bundle bundle) {
        initFilterData();
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new FilterAdapter(getContext());
        rvFilter.setPullRefreshEnabled(false);
        rvFilter.setLoadingMoreEnabled(false);
        rvFilter.setLayoutManager(layoutManager);
        rvFilter.setNestedScrollingEnabled(false);
        rvFilter.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<FilterBean.DataBean>() {
            @Override
            public void onItemClick(View view, FilterBean.DataBean item, int position) {
                tv_receive = view.findViewById(R.id.tv_receive);
                country_name = item.getCouponData().getCountryname();
                RelativeLayout rlReceive = view.findViewById(R.id.rl_receive);

                CommonUtils.setPicture1x1(item.getCouponData().getImage(), view.findViewById(R.id.iv_shop_img));

                        couponId = item.getCouponData().getCouponId();
                        Log.e("couponId", couponId + "");
                        if (TextUtils.equals(item.getUserHasThisCoupon(), "false")) {
                            userGetOneCoupon(couponId);
                            item.setUserHasThisCoupon("true");
                            adapter.setListAll(beanList);
                        } else {
                            Intent intent = new Intent(getActivity(), DetailsActivity.class);
                            intent.putExtra("couponId", couponId);
                            startActivity(intent);
                        }
//                        Intent intent = new Intent(getActivity(), DetailsActivity.class);
//                        intent.putExtra("couponId", couponId);
//                        startActivity(intent);



            }

        });
    }

    public void getSearchResult(String searchInput, String city, int page, int pageSize) {
        Log.e("searchInput", searchInput);
        Map<String, String> map = new HashMap<>();
        map.put("keyWord", searchInput);
        if (TextUtils.equals(Constans.PRO_VERSION,"L")){
            map.put("leaseId",Constans.PRO_DEV_OID);
        }
        OkGoUtil.<FilterBean>get(false, Constans.METHOD_GETCOUPONSEARCH, map, FilterBean.class, this);
    }

    private void userGetOneCoupon(int couponId) {
        Map<String, String> map = new HashMap<>();
        map.put("CouponId", couponId + "");
        map.put("countryName", country_name);
        OkGoUtil.<BaseBean>get(getActivity(), Constans.METHOD_USERGETONECOUPON, map, BaseBean.class, this);

    }

    public void initFilterData() {
        List<FilterBeanOld.FilterItemBean> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            FilterBeanOld.FilterItemBean bean = new FilterBeanOld.FilterItemBean();
            bean.setUrl("");
            bean.setName("依兰拉面");
            bean.setScore(4.0f);
            bean.setManPay("120$/人");
            bean.setTip("面食");
            list.add(bean);
        }
        filterBeanOld = new FilterBeanOld();
        filterBeanOld.setCode("1");
        filterBeanOld.setMsg("Success");
        filterBeanOld.setFilterItemBeanList(list);
        Log.e("initFilterData: ", FastJsonUtil.changObjectToString(filterBeanOld));
    }

    @Override
    public void onSuccess(String s, FilterBean filterBean, String s1) {
        rvFilter.setFocusable(true);
        rvFilter.setFocusableInTouchMode(true);
        switch (s) {
            case Constans.METHOD_GETCOUPONSEARCH:
                beanList.clear();
                beanList.addAll(filterBean.getData());
                adapter.setListAll(beanList);
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