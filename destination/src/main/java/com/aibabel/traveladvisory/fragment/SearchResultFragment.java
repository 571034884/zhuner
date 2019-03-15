package com.aibabel.traveladvisory.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aibabel.traveladvisory.R;
import com.aibabel.traveladvisory.activity.CityActivity;
import com.aibabel.traveladvisory.activity.CityDetailsActivity;
import com.aibabel.traveladvisory.activity.CountryActivity;
import com.aibabel.traveladvisory.app.Constans;
import com.aibabel.traveladvisory.bean.FuzzyQueryBean;
import com.aibabel.traveladvisory.custom.MyRecyclerView;
import com.aibabel.traveladvisory.hongyang_ry.MyAdapter;
import com.aibabel.traveladvisory.okgo.BaseBean;
import com.aibabel.traveladvisory.okgo.BaseCallback;
import com.aibabel.traveladvisory.okgo.OkGoUtil;
import com.aibabel.traveladvisory.utils.MyScrollview;
import com.aibabel.traveladvisory.utils.ToastUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：SunSH on 2018/6/13 20:44
 * 功能：
 * 版本：1.0
 */
public class SearchResultFragment extends Fragment implements BaseCallback {

    @BindView(R.id.tv_recent_search)
    TextView tvRecentSearch;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.rv_recycler)
    MyRecyclerView rvRecycler;
    @BindView(R.id.hsv_scroll)
    MyScrollview hsvScroll;
    @BindView(R.id.tv_no)
    TextView tvNo;
    private List<FuzzyQueryBean.DataBean> list = new ArrayList<>();

    private MyAdapter adapter;
    private EmptyWrapper mEmptyWrapper;
    private LoadMoreWrapper mLoadMoreWrapper;
//    private CommomRecyclerAdapter adapter;

    private TextView tv;
    private int length;
    private String searchInput;
    private int start_count = 0;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_destinations, container, false);
        unbinder = ButterKnife.bind(this, view);

        initRecycler();

        return view;
    }

    private void initTextColor(int position, TextView tv_name, String searchInput) {
        SpannableStringBuilder spannable = new SpannableStringBuilder(tv_name.getText().toString());

        int i = tv_name.getText().toString().indexOf(searchInput);//*第一个出现的索引位置
        while (i != -1) {
            spannable.setSpan(new ForegroundColorSpan(Color.RED), i, i + length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            i = tv_name.getText().toString().indexOf(searchInput, i + length + 1);//*从这个索引往后开始第一个出现的位置
        }
        tv_name.setText(spannable);
    }

    private void initRecycler() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        rvRecycler.setLayoutManager(gridLayoutManager);
        adapter = new MyAdapter<FuzzyQueryBean.DataBean>(getActivity(), R.layout.rv_destination_item, list) {
            @Override
            protected void convert(ViewHolder holder, FuzzyQueryBean.DataBean bean, int position) {
                ImageView iv_destination = holder.getView(R.id.iv_destination);
                TextView tv_name = holder.getView(R.id.tv_name);
                TextView tv_title = holder.getView(R.id.tv_title);
                TextView tv_branch = holder.getView(R.id.tv_branch);
                RequestOptions options_no_tongyong3 = new RequestOptions().placeholder(R.mipmap.no_tongyong3).error(R.mipmap.error_h);
                tv_name.setText(bean.getCnName());
                tv_title.setText(bean.getExplain());
                tv_branch.setText(bean.getCnCityName() + "/" + bean.getPoiTypeName());
                Glide.with(getActivity())
                        .load(bean.getPoi_image(136,136))
                        .apply(options_no_tongyong3)
//                        .placeholder(R.mipmap.no_tongyong3).error(R.mipmap.error_v)
                        .into(iv_destination);
//
                if (TextUtils.equals(bean.getExplain(), "")) {
                    tv_title.setVisibility(View.GONE);
                } else {
                    tv_title.setVisibility(View.VISIBLE);
                }
                if (!TextUtils.equals(tv_name.getText().toString(), "")) {
                    initTextColor(position, tv_name, searchInput);
                }
            }
        };
        adapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = null;
                switch (list.get(position).getType()) {
                    case "国家概览":
                        intent = new Intent(getActivity(), CountryActivity.class);
                        intent.putExtra("countryId", list.get(position).getPlaceId());
                        intent.putExtra("countryName", list.get(position).getCnName());
                        startActivity(intent);
                        break;
                    case "城市概览":
                        intent = new Intent(getActivity(), CityActivity.class);
                        intent.putExtra("cityName", list.get(position).getCnName());
                        intent.putExtra("cityId", list.get(position).getPlaceId()+"");
//                        intent.putExtra("cityNameEn", bean.getEnName());
                        startActivity(intent);
                        break;
                    case "poi":
                        intent = new Intent(getActivity(), CityDetailsActivity.class);
                        intent.putExtra("poiId", list.get(position).getPlaceId() + "");
                        startActivity(intent);
                        break;
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvRecycler.setEmptyView(tvNo);
        rvRecycler.setAdapter(adapter);
        rvRecycler.requestFocus();
    }


    public void refreshSearchList(Context context, String searchInput, String cnCityName, int page, int pageSize) {
        this.searchInput = searchInput;
        this.length = searchInput.length();
        list.removeAll(list);

        Map<String, String> map = new HashMap<>();
        map.put("keyword", searchInput);
        map.put("cnCityName", cnCityName);
        map.put("page", page + "");
        map.put("pageSize", pageSize + "");
        OkGoUtil.<FuzzyQueryBean>get(context, Constans.METHOD_GET_MSG, map, FuzzyQueryBean.class, this);

    }


    @Override
    public void onSuccess(String method, BaseBean model) {
        rvRecycler.setFocusable(true);
        rvRecycler.setFocusableInTouchMode(true);
        switch (method) {
            case Constans.METHOD_GET_MSG:
//                list = ((FuzzyQueryBean) model).getData();
////                mLoadMoreWrapper.notifyDataSetChanged();
//                adapter.updateData(list);

                if (((FuzzyQueryBean) model).getData() != null) {
                    list.addAll(((FuzzyQueryBean) model).getData());
                    adapter.updateData(list);
                } else {
                    ToastUtil.show(getActivity(), getResources().getString(R.string.toast_wushuju), 1500);
                    adapter.updateData(list);
                }
                break;
        }
    }


    @Override
    public void onError(String method, String message) {

    }

}
