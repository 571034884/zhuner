package com.aibabel.food.fragment;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;

import com.aibabel.baselibrary.base.BaseFragment;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.baselibrary.utils.FastJsonUtil;
import com.aibabel.baselibrary.utils.SharePrefUtil;
import com.aibabel.food.R;
import com.aibabel.food.activity.SearchActivity;
import com.aibabel.food.adapter.MyTagAdapter;
import com.aibabel.food.base.Constant;
import com.aibabel.food.bean.HotSearchBean;
import com.aibabel.food.bean.Html5UrlBean;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 作者：SunSH on 2018/12/11 10:56
 * 功能：
 * 版本：1.0
 */
public class SearchFragment extends BaseFragment implements BaseCallback<HotSearchBean> {

    @BindView(R.id.flLishi)
    TagFlowLayout flLishi;
    @BindView(R.id.flRemen)
    TagFlowLayout flRemen;
    @BindView(R.id.ivClear)
    ImageView ivClear;
    @BindView(R.id.clLishi)
    ConstraintLayout clLishi;

    MyTagAdapter histoeyAdapter;
    MyTagAdapter remenAdapter;
    List<String> historyList;
    List<String> hotSearchList;

    private int length;
    private String searchInput;

    @Override
    public int getLayout() {
        return R.layout.fragment_for_search;
    }

    @Override
    public void init(View view, Bundle bundle) {
        getLishiDate();
        flLishi.setAdapter(histoeyAdapter = new MyTagAdapter(getContext(), historyList, R.layout.item_lishi));

        flLishi.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                ((SearchActivity) mContext).setSvSearchText(historyList.get(position));
                ((SearchActivity) mContext).turnToSearchResult(historyList.get(position), 1);
                return true;
            }
        });
        flRemen.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                ((SearchActivity) mContext).setSvSearchText(hotSearchList.get(position));
                ((SearchActivity) mContext).turnToSearchResult(hotSearchList.get(position), 1);
                return true;
            }
        });
    }

    /**
     * 展示历史记录布局
     */
    public void showHistoryLayout() {
        if (historyList.size() > 0) {
            clLishi.setVisibility(View.VISIBLE);
        } else {
            clLishi.setVisibility(View.GONE);
        }
    }

    public void getLishiDate() {
        historyList = FastJsonUtil.changeJsonToList(SharePrefUtil.getString(getActivity(), Constant.KEY_HISTORY, "[]"), String.class);
        showHistoryLayout();
        hotSearchList = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        OkGoUtil.<HotSearchBean>get(Constant.METHOD_HOT_SEARCH, map, HotSearchBean.class, this);
    }

    public void refreshHistory(String searchInput) {
        if (historyList.contains(searchInput)) historyList.remove(searchInput);
        historyList.add(0, searchInput);
        if (historyList.size() > 20) historyList.remove(20);
        showHistoryLayout();
        histoeyAdapter.refereshData(historyList);
        SharePrefUtil.saveString(getActivity(), Constant.KEY_HISTORY, FastJsonUtil.changListToString(historyList));
    }


    @OnClick({R.id.ivClear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivClear:
                historyList.clear();
                histoeyAdapter.refereshData(historyList);
                showHistoryLayout();
                SharePrefUtil.saveString(getActivity(), Constant.KEY_HISTORY, "[]");
                break;
        }
    }

    @Override
    public void onSuccess(String s, HotSearchBean hotSearchBean, String s1) {
        switch (s) {
            case Constant.METHOD_HOT_SEARCH:
                hotSearchList = hotSearchBean.getData();
                flRemen.setAdapter(remenAdapter = new MyTagAdapter(getContext(), hotSearchList, R.layout.item_remen));
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
