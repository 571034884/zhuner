package com.aibabel.traveladvisory.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aibabel.traveladvisory.R;
import com.aibabel.traveladvisory.activity.CountryActivity;
import com.aibabel.traveladvisory.activity.SearchPageActivity;
import com.aibabel.traveladvisory.adapter.CommomRecyclerAdapter;
import com.aibabel.traveladvisory.adapter.CommonRecyclerViewHolder;
import com.aibabel.traveladvisory.app.Constans;
import com.aibabel.traveladvisory.bean.HotBean;
import com.aibabel.traveladvisory.bean.SearchBean;
import com.aibabel.traveladvisory.okgo.BaseBean;
import com.aibabel.traveladvisory.okgo.BaseCallback;
import com.aibabel.traveladvisory.okgo.OkGoUtil;
import com.aibabel.traveladvisory.utils.FastJsonUtil;
import com.aibabel.traveladvisory.utils.MyGridLayoutManager;
import com.aibabel.traveladvisory.utils.SharePrefUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 作者：SunSH on 2018/6/13 20:44
 * 功能：
 * 版本：1.0
 */
public class SearchFragment extends Fragment implements BaseCallback {

    //    @BindView(R.id.sv_search)
//    SearchView svSearch;
//    @BindView(R.id.tv_cancel)
//    TextView tvCancel;
    @BindView(R.id.tv_recent_search)
    TextView tvRecentSearch;
    @BindView(R.id.tv_clear_history)
    TextView tvClearHistory;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.rv_record)
    RecyclerView rvRecord;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.tv_hot_destination)
    TextView tvHotDestination;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.rv_hot)
    RecyclerView rvHot;
    private List<SearchBean> recordSearchBeanList = new ArrayList<>();
    private List<HotBean.DataBean> hotSearchBeanList = new ArrayList<>();
    private CommomRecyclerAdapter recordAdapter;
    private CommomRecyclerAdapter hotAdapter;

    private String searchInput;
    Unbinder unbinder;
    private HotBean hotBean;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout_for_search, container, false);
        unbinder = ButterKnife.bind(this, view);

        initData();

        return view;
    }

    private CommomRecyclerAdapter initRecyclerView(RecyclerView rv, int mLayId, int spanCount, final List<SearchBean> list) {
        MyGridLayoutManager gridLayoutManager = new MyGridLayoutManager(getActivity(), spanCount);
        //设置布局管理器
        rv.setLayoutManager(gridLayoutManager);
        CommomRecyclerAdapter adapter = new CommomRecyclerAdapter(getActivity(), list, mLayId, new CommomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommonRecyclerViewHolder holder, int postion) {
                SearchPageActivity activity = (SearchPageActivity) getActivity();
                activity.setSvSearchText(list.get(postion).getPlace());
                activity.showFragment(2);
                ((SearchResultFragment) (activity.getCurrentFragment())).refreshSearchList(activity, list.get(postion).getPlace(), getActivity().getIntent().getStringExtra("cnCityName"), activity.getPage(), activity.getPageSize());
                refreshHistory(list.get(postion).getPlace());
            }
        }, null) {
            @Override
            public void convert(CommonRecyclerViewHolder holder, Object o, int position) {

                TextView tv_item = holder.getView(R.id.tv_city_address);
                tv_item.setText(((SearchBean) o).getPlace());
            }
        };
        //设置Adapter
        rv.setAdapter(adapter);
        return adapter;
    }

    private CommomRecyclerAdapter initHotRecyclerView(RecyclerView rv, int mLayId, int spanCount, final List<HotBean.DataBean> list) {
        MyGridLayoutManager gridLayoutManager = new MyGridLayoutManager(getActivity(), spanCount);
        //设置布局管理器
        rv.setLayoutManager(gridLayoutManager);
        CommomRecyclerAdapter adapter = new CommomRecyclerAdapter(getActivity(), list, mLayId, new CommomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommonRecyclerViewHolder holder, int position) {
//                SearchPageActivity activity = (SearchPageActivity) getActivity();
////                activity.setSvSearchText(list.get(postion).getCountryName());
////                activity.showFragment(2);
////                ((SearchResultFragment) (activity.getCurrentFragment())).refreshSearchList(activity, list.get(postion).getCountryName(), "", activity.getPage(), activity.getPageSize());
////                refreshHistory(list.get(postion).getCountryName());
                Intent intent = new Intent(getActivity(), CountryActivity.class);
                intent.putExtra("countryId", list.get(position).getCountryId());
                intent.putExtra("countryName", list.get(position).getCountryName());
                startActivity(intent);
            }
        }, null) {
            @Override
            public void convert(CommonRecyclerViewHolder holder, Object o, int position) {

                TextView tv_item = holder.getView(R.id.tv_city_address);
                tv_item.setText(((HotBean.DataBean) o).getCountryName());
            }
        };
        //设置Adapter
        rv.setAdapter(adapter);
        return adapter;
    }

    private void initData() {

        //从本地取出最近搜索的历史记录json ，最多5项
        String recordSearchList = SharePrefUtil.getString(getActivity(), "recordSearchList", "[]");

        recordSearchBeanList = FastJsonUtil.changeJsonToList(recordSearchList, SearchBean.class);
        if (recordSearchBeanList.size() > 0) {
            tvClearHistory.setVisibility(View.VISIBLE);
        } else {
            tvClearHistory.setVisibility(View.GONE);
        }
        recordAdapter = initRecyclerView(rvRecord, R.layout.lv_item, 1, recordSearchBeanList);
        hotAdapter = initHotRecyclerView(rvHot, R.layout.recy_item, 3, hotSearchBeanList);

        Map<String, String> map = new HashMap<>();
        OkGoUtil.<HotBean>get(getActivity(), Constans.METHOD_GET_HOT_COUNTRY, map, HotBean.class, this);
    }

    @OnClick({R.id.tv_clear_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.tv_cancel:
//                getActivity().finish();
//                break;
            case R.id.tv_clear_history:
                SharePrefUtil.saveString(getActivity(), "recordSearchList", "[]");
                recordSearchBeanList.removeAll(recordSearchBeanList);
                recordAdapter.updateData(recordSearchBeanList);
                tvClearHistory.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void refreshHistory(String searchInput) {
        SearchBean bean = new SearchBean();
        bean.setPlace(searchInput);
        recordSearchBeanList.add(0, bean);
        for (int i = 0; i < (recordSearchBeanList.size() > 2 ? (recordSearchBeanList.size() - 1) : 0); i++) {
            if (TextUtils.equals(searchInput, recordSearchBeanList.get(i + 1).getPlace())) {
                recordSearchBeanList.remove(i + 1);
                break;
            }
        }
        if (recordSearchBeanList.size() > 5) recordSearchBeanList.remove(5);
//        SearchBean bean = new SearchBean();
//        bean.setPlace(searchInput);
//        recordSearchBeanList.add(0, bean);
        if (recordSearchBeanList.size() > 0) {
            tvClearHistory.setVisibility(View.VISIBLE);
        } else {
            tvClearHistory.setVisibility(View.GONE);
        }
        recordAdapter.updateData(recordSearchBeanList);
        SharePrefUtil.saveString(getActivity(), "recordSearchList", FastJsonUtil.changListToString(recordSearchBeanList));
    }

    @Override
    public void onSuccess(String method, BaseBean model) {
        switch (method) {
            case Constans.METHOD_GET_HOT_COUNTRY:
                hotBean = (HotBean) model;
                for (int j = 0; j < (hotBean.getData().size() > 9 ? 9 : hotBean.getData().size()); j++) {
                    hotSearchBeanList.add(hotBean.getData().get(j));
                }
                hotAdapter.updateData(hotSearchBeanList);
                break;
        }
    }

    @Override
    public void onError(String method, String message) {

    }
}
