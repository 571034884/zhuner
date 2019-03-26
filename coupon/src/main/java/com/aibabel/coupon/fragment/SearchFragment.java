package com.aibabel.coupon.fragment;


import android.os.Bundle;
import android.support.design.internal.FlowLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aibabel.baselibrary.base.BaseFragment;
import com.aibabel.baselibrary.base.StatisticsBaseActivity;
import com.aibabel.baselibrary.http.BaseBean;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.coupon.R;
import com.aibabel.coupon.activity.ReceiveActivity;
import com.aibabel.coupon.activity.SearchActivity;
import com.aibabel.coupon.adapter.MyTagAdapter;
import com.aibabel.coupon.bean.Constans;
import com.aibabel.coupon.bean.CouponBean;
import com.aibabel.coupon.bean.SearchRemengBean;
import com.aibabel.coupon.utils.FastJsonUtil;
import com.aibabel.coupon.utils.SharePrefUtil;
import com.bumptech.glide.Glide;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends BaseFragment implements BaseCallback<BaseBean>{

    @BindView(R.id.flLishi)
    TagFlowLayout flLishi;
    @BindView(R.id.flRemen)
    TagFlowLayout flRemen;
    @BindView(R.id.ivClear)
    ImageView ivClear;

    MyTagAdapter histoeyAdapter;
    MyTagAdapter remenAdapter;
    List<String> historyList;

    Unbinder unbinder;

    private int length;
    private String searchInput;
    private List<String> searchRemengBeanList = new ArrayList<>();
    private SearchRemengBean searchRemengBean;

    @Override
    public int getLayout() {
        return R.layout.fragment_search;
    }

    @Override
    public void init(View view, Bundle bundle) {
        getLishiDate();
        flLishi.setAdapter(histoeyAdapter = new MyTagAdapter(getContext(), historyList, R.layout.item_lishi));


        flLishi.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, com.zhy.view.flowlayout.FlowLayout parent) {
                ((SearchActivity) mContext).setSvSearchText(historyList.get(position));
                ((SearchActivity) mContext).turnToSearchResult(historyList.get(position), 1);

                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    HashMap<String, Serializable> add_hp = new HashMap<>();
                    add_hp.put("coupon_search1_def",historyList.get(position) );
                    ((StatisticsBaseActivity)getActivity()).addStatisticsEvent("coupon_search1", add_hp);
                }catch (Exception e){
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/
                return true;
            }
        });
    }

    public void getLishiDate() {
//        historyList = new ArrayList<>();
//        historyList.add("haoifhewfoinaef");
//        historyList.add("hanaef");
//        historyList.add("haoifoinaef");
//        historyList.add("捏哈人");
//        historyList.add("破IE覅哈空间阿卡收到货");
//        historyList.add("破IE覅哈空间阿卡收到发发发货");
//        historyList.add("破IE覅哈空卡收到货");
//        historyList.add("破IE覅哈空间阿卡收到货");
//        historyList.add("破IE覅卡收到货");
        historyList = FastJsonUtil.changeJsonToList(SharePrefUtil.getString(getActivity(), "coupon", "[]"), String.class);
        if (historyList.size() > 0) ivClear.setVisibility(View.VISIBLE);
        else ivClear.setVisibility(View.GONE);
        Map<String, String> map = new HashMap<>();
        OkGoUtil.<SearchRemengBean>get(getActivity(), Constans.METHOD_GETCOUPONHOTSEARCH, map, SearchRemengBean.class, this);

    }

    public void refreshHistory(String searchInput) {
        if (historyList.contains(searchInput)) historyList.remove(searchInput);
        historyList.add(0, searchInput);
        if (historyList.size() > 5) historyList.remove(5);
        if (historyList.size() > 0) {
            ivClear.setVisibility(View.VISIBLE);
        } else {
            ivClear.setVisibility(View.GONE);
        }
        histoeyAdapter.refereshData(historyList);
        SharePrefUtil.saveString(getActivity(), "coupon", FastJsonUtil.changListToString(historyList));
    }


    @OnClick({R.id.ivClear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivClear:
                historyList.clear();
                histoeyAdapter.refereshData(historyList);
                ivClear.setVisibility(View.GONE);
                SharePrefUtil.saveString(getActivity(), "coupon", "[]");
                break;
        }
    }

    @Override
    public void onSuccess(String method, BaseBean baseBean, String s1) {
        switch (method) {
            case Constans.METHOD_GETCOUPONHOTSEARCH:
                searchRemengBean = (SearchRemengBean) baseBean;
                searchRemengBeanList = searchRemengBean.getData();
                flRemen.setAdapter(remenAdapter = new MyTagAdapter(getContext(), searchRemengBeanList, R.layout.item_remen));
//                remenAdapter.refereshData(searchRemengBeanList);
//                remenAdapter.notifyDataChanged();

                flRemen.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position, com.zhy.view.flowlayout.FlowLayout parent) {
                        ((SearchActivity) mContext).setSvSearchText(searchRemengBeanList.get(position));
                        ((SearchActivity) mContext).turnToSearchResult(searchRemengBeanList.get(position), 1);
                        /**####  start-hjs-addStatisticsEvent   ##**/
                        try {
                            HashMap<String, Serializable> add_hp = new HashMap<>();
                            add_hp.put("coupon_search2_def",historyList.get(position) );
                            ((StatisticsBaseActivity)getActivity()).addStatisticsEvent("coupon_search2", add_hp);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        /**####  end-hjs-addStatisticsEvent  ##**/
                        return true;
                    }
                });
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
