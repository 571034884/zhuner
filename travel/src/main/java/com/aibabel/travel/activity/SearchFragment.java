package com.aibabel.travel.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aibabel.travel.R;
import com.aibabel.travel.adaper.CommomRecyclerAdapter;
import com.aibabel.travel.adaper.CommonRecyclerViewHolder;
import com.aibabel.travel.bean.CityItemBean;
import com.aibabel.travel.bean.SearchBean;
import com.aibabel.travel.utils.FastJsonUtil;
import com.aibabel.travel.utils.SharePrefUtil;
import com.aibabel.travel.widgets.MyGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 作者：SunSH on 2018/6/13 20:44
 * 功能：
 * 版本：1.0
 */
public class SearchFragment extends Fragment {

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
    private List<CityItemBean> hotSearchBeanList = new ArrayList<>();
    private CommomRecyclerAdapter recordAdapter;
    private CommomRecyclerAdapter hotAdapter;
    private Context mContext;

    private String searchInput;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout_for_search, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {

        //从本地取出最近搜索的历史记录json ，最多5项
        String recordSearchList = SharePrefUtil.getString(getActivity(), "recordSearchList", "[]");

        recordSearchBeanList = FastJsonUtil.changeJsonToList(recordSearchList, SearchBean.class);

        recordAdapter = initRecyclerView(rvRecord, recordAdapter, R.layout.lv_item, 1, recordSearchBeanList);


        initHotRecycler();
        refreshHot();
    }

    private CommomRecyclerAdapter initRecyclerView(RecyclerView rv, CommomRecyclerAdapter adapter, int mLayId, int spanCount, List<SearchBean> list) {
        MyGridLayoutManager gridLayoutManager = new MyGridLayoutManager(getActivity(), spanCount);
        //设置布局管理器
        rv.setLayoutManager(gridLayoutManager);
        adapter = new CommomRecyclerAdapter(getActivity(), list, mLayId, new CommomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommonRecyclerViewHolder holder, int postion) {
//                //搜索城市 景点 国家
                ((SearchPageActivity)getActivity()).getList(recordSearchBeanList.get(postion).getPlace());
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



    private void initHotRecycler() {
        MyGridLayoutManager gridLayoutManager = new MyGridLayoutManager(getActivity(), 3);
        //设置布局管理器
        rvHot.setLayoutManager(gridLayoutManager);
        hotAdapter = new CommomRecyclerAdapter<CityItemBean>(getActivity(), hotSearchBeanList, R.layout.recy_item, new CommomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommonRecyclerViewHolder holder, int postion) {
                String id  = hotSearchBeanList.get(postion).getId()+"";
                String name  = hotSearchBeanList.get(postion).getName();
                String path  = hotSearchBeanList.get(postion).getCover();
//                String audioUrl  = hotSearchBeanList.get(postion).getAudios().getUrl();
//                int type  = hotSearchBeanList.get(postion).getType();

                toCountry(id,name);

            }
        }, null) {
            @Override
            public void convert(CommonRecyclerViewHolder holder, CityItemBean bean, int position) {

                TextView tv_item = holder.getView(R.id.tv_city_address);
                tv_item.setText(bean.getName());
            }
        };
        //设置Adapter
        rvHot.setAdapter(hotAdapter);
    }

    private void toCountry(String id, String name) {
        Intent intent = new Intent(getActivity(), CountryActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("name", name);

        startActivity(intent);
    }


    @OnClick({R.id.tv_clear_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_clear_history:
                SharePrefUtil.saveString(mContext, "recordSearchList", "[]");
                recordSearchBeanList.removeAll(recordSearchBeanList);
                recordAdapter.updateData(recordSearchBeanList);
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
        recordAdapter.updateData(recordSearchBeanList);
        SharePrefUtil.saveString(mContext, "recordSearchList", FastJsonUtil.changListToString(recordSearchBeanList));
    }





    public void refreshHot() {
        hotSearchBeanList = ((SearchPageActivity)getActivity()).initHot();
        hotAdapter.updateData(hotSearchBeanList);
    }


}
