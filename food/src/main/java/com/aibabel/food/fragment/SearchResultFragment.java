package com.aibabel.food.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.aibabel.baselibrary.adapter.BaseRecyclercAdapter;
import com.aibabel.baselibrary.base.BaseFragment;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.baselibrary.utils.FastJsonUtil;
import com.aibabel.food.R;
import com.aibabel.food.activity.Html5Activity;
import com.aibabel.food.activity.SearchActivity;
import com.aibabel.food.adapter.FilterAdapter;
import com.aibabel.food.base.Constant;
import com.aibabel.food.bean.FilterBean;
import com.aibabel.food.bean.FilterBeanOld;
import com.zhouyou.recyclerview.XRecyclerView;
import com.zhouyou.recyclerview.adapter.BaseRecyclerViewAdapter;
import com.zhouyou.recyclerview.adapter.HelperStateRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 作者：SunSH on 2018/12/11 10:56
 * 功能：
 * 版本：1.0
 */
public class SearchResultFragment extends BaseFragment implements BaseCallback<FilterBean>, BaseRecyclercAdapter.OnErrorClickListener {

    public String TAG = this.getClass().getSimpleName();
    @BindView(R.id.rvFilter)
    XRecyclerView rvFilter;
    private FilterBeanOld filterBeanOld;

    FilterAdapter adapter = null;
    LinearLayoutManager layoutManager = null;

    String currentInput = "";

    @Override
    public int getLayout() {
        return R.layout.fragment_for_search_result;
    }

    @Override
    public void init(View view, Bundle bundle) {
//        initFilterData();
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        Log.e(TAG, "init: " + System.currentTimeMillis());
        adapter = new FilterAdapter(getContext());
        rvFilter.setPullRefreshEnabled(false);
        rvFilter.setLoadingMoreEnabled(false);
        rvFilter.setLayoutManager(layoutManager);
        rvFilter.setNestedScrollingEnabled(false);
        rvFilter.setAdapter(adapter);

        adapter.setOnErrorClickListener(this::requestAgain);
        adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<FilterBean.DataBean>() {
            @Override
            public void onItemClick(View view, FilterBean.DataBean item, int position) {
//                startActivity(DetailActivity.class);
                startActivity(new Intent(getContext(), Html5Activity.class)
                        .putExtra("shopId", item.getShopId())
                        .putExtra("shopName", item.getShopName())
                        .putExtra("where", "search"));
            }
        });
    }

    public void getSearchResult(String searchInput, String city, int page, int pageSize) {
        if (adapter != null)
            adapter.setState(HelperStateRecyclerViewAdapter.STATE_LOADING);
        currentInput = searchInput;
        Map<String, String> map = new HashMap<>();
        map.put("keyWord", searchInput);
        map.put("city", city);
        map.put("page", page + "");
        map.put("pageSize", pageSize + "");
        OkGoUtil.<FilterBean>get(false, Constant.METHOD_SEARCH, map, FilterBean.class, this);
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
            case Constant.METHOD_SEARCH:
                adapter.setListAll(filterBean.getData());
                break;
        }
    }

    @Override
    public void onError(String s, String s1, String s2) {
        if (adapter != null)
            adapter.setState(HelperStateRecyclerViewAdapter.STATE_ERROR);
    }

    @Override
    public void onFinsh(String s) {

    }

    @Override
    public void requestAgain() {
        getSearchResult(currentInput, Constant.CURRENT_CITY, 1, SearchActivity.pageSize);
    }
}
