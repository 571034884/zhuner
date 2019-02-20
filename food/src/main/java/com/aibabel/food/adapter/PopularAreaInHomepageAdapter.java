package com.aibabel.food.adapter;

import android.content.Context;
import android.widget.TextView;

import com.aibabel.food.R;
import com.aibabel.food.bean.HomePageAllBean;
import com.aibabel.food.bean.HomePageBean;
import com.aibabel.food.utils.CommonUtils;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewAdapter;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder;

import java.util.List;

/**
 * 作者：SunSH on 2018/12/4 17:36
 * 功能：
 * 版本：1.0
 */
public class PopularAreaInHomepageAdapter extends HelperRecyclerViewAdapter<HomePageAllBean.DataBean.BegionShoptypeJsonBean.ListBean> {

    public Context context;

    public PopularAreaInHomepageAdapter(List data, Context context, int... layoutId) {
        super(data, context, layoutId);
        this.context = context;
    }

    @Override
    protected void HelperBindData(HelperRecyclerViewHolder viewHolder, int position, HomePageAllBean.DataBean.BegionShoptypeJsonBean.ListBean item) {
        CommonUtils.setPictureCircle1x1(item.getIconUrl(), viewHolder.getView(R.id.ivAreaIcon));
        ((TextView) viewHolder.getView(R.id.tvAreaName)).setText(item.getName());
    }
}
