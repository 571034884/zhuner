package com.aibabel.food.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.baselibrary.adapter.BaseRecyclercAdapter;
import com.aibabel.food.R;
import com.aibabel.food.activity.AllOffThingsActivity;
import com.aibabel.food.activity.FilterActivity;
import com.aibabel.food.bean.HomePageAllBean;
import com.zhouyou.recyclerview.XRecyclerView;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewAdapter;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：SunSH on 2018/12/4 17:36
 * 功能：
 * 版本：1.0
 */
public class HomepageAdapter extends BaseRecyclercAdapter<HomePageAllBean.DataBean
        .BegionShoptypeJsonBean> {

    private Context context;

    public HomepageAdapter(Context context) {
        super(context, R.layout.item_homepage);
        this.context = context;
    }

    @Override
    protected void HelperBindData(HelperRecyclerViewHolder viewHolder, int position,
                                  HomePageAllBean.DataBean.BegionShoptypeJsonBean item) {
        XRecyclerView recyclerView = viewHolder.getView(R.id.rvItemHomepage);
        HelperRecyclerViewAdapter adapter = null;
        View headerView = null;
        GridLayoutManager layoutManager = null;
        if (position == 0) {
            layoutManager = new GridLayoutManager(context, 4);
            headerView = LayoutInflater.from(context).inflate(R.layout.item_area_header, (
                    (Activity) context).findViewById(R.id.rvHomepage), false);
            adapter = new PopularAreaInHomepageAdapter(item.getList(), context, R.layout
                    .item_area_homepage);
            headerView.findViewById(R.id.tvAreaHeaderAll).setOnClickListener(new View
                    .OnClickListener() {
                @Override
                public void onClick(View v) {
                    turnToAll(AllOffThingsActivity.class, AllOffThingsActivity.TAG_AREA);
                    Map<String, String> map = new HashMap<>();
                    StatisticsManager.getInstance(mContext).addEventAidl("全部区域", map);
                }
            });
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, Object item, int position) {
                    turnToList(FilterActivity.FILTER_TAG1, ((HomePageAllBean.DataBean
                            .BegionShoptypeJsonBean.ListBean) item).getName());
                    Map<String, String> map = new HashMap<>();
                    map.put("区域名称", ((HomePageAllBean.DataBean.BegionShoptypeJsonBean.ListBean) item).getName());
                    StatisticsManager.getInstance(mContext).addEventAidl("人气区域", map);
                }
            });
        } else if (position == 1) {
            layoutManager = new GridLayoutManager(context, 2);
            headerView = LayoutInflater.from(context).inflate(R.layout.item_kind_header, (
                    (Activity) context).findViewById(R.id.rvHomepage), false);
            adapter = new CateKindInHomapageAdapter(item.getList(), context, R.layout
                    .item_kind_homepage);
            headerView.findViewById(R.id.tvKindHeaderAll).setOnClickListener(new View
                    .OnClickListener() {
                @Override
                public void onClick(View v) {
                    turnToAll(AllOffThingsActivity.class, AllOffThingsActivity.TAG_KIND);
                    Map<String, String> map = new HashMap<>();
                    StatisticsManager.getInstance(mContext).addEventAidl("全部分类", map);
                }
            });
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, Object item, int position) {
                    turnToList(FilterActivity.FILTER_TAG2, ((HomePageAllBean.DataBean.BegionShoptypeJsonBean.ListBean) item).getName());
                    Map<String, String> map = new HashMap<>();
                    map.put("分类名称", ((HomePageAllBean.DataBean.BegionShoptypeJsonBean.ListBean) item).getName());
                    StatisticsManager.getInstance(mContext).addEventAidl("美食分类", map);
                }
            });
        } else if (position == 2) {
//            layoutManager = new GridLayoutManager(context, 1);
//            headerView = LayoutInflater.from(context).inflate(R.layout.item_recommend_header, (
// (Activity) context).findViewById(R.id.rvHomepage), false);
//            adapter = new RecommendAdapter(item.getList(), context, R.layout
// .item_recommend_homepage);
        }
        if (adapter != null) {
            recyclerView.setPullRefreshEnabled(false);
            recyclerView.setLoadingMoreEnabled(false);
            recyclerView.setLayoutManager(layoutManager);
            if (recyclerView.getHeadersCount() < 1) recyclerView.addHeaderView(headerView);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setAdapter(adapter);
        }
    }

    public void turnToAll(Class clazz, int param) {
        Intent intent = new Intent(context, clazz);
        intent.putExtra(AllOffThingsActivity.ACTIVITY_TAG, param);
        context.startActivity(intent);
    }

    public void turnToList(String tag, String param) {
        Intent intent = new Intent(context, FilterActivity.class);
        intent.putExtra(tag, param);
        context.startActivity(intent);
    }

}
