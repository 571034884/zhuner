package com.aibabel.food.adapter;

import android.content.Context;
import android.widget.TextView;

import com.aibabel.food.R;
import com.aibabel.food.base.Constant;
import com.aibabel.food.bean.HomePageAllBean;
import com.aibabel.food.bean.HomePageBean;
import com.aibabel.food.custom.circleimage.CustomRoundAngleImageView;
import com.aibabel.food.utils.CommonUtils;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewAdapter;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder;

import java.util.List;

/**
 * 作者：SunSH on 2018/12/4 17:36
 * 功能：
 * 版本：1.0
 */
public class CateKindInHomapageAdapter extends HelperRecyclerViewAdapter<HomePageAllBean.DataBean.BegionShoptypeJsonBean.ListBean> {

    public Context context;

    public CateKindInHomapageAdapter(List data, Context context, int... layoutId) {
        super(data, context, layoutId);
        this.context = context;
    }

    @Override
    protected void HelperBindData(HelperRecyclerViewHolder viewHolder, int position, HomePageAllBean.DataBean.BegionShoptypeJsonBean.ListBean item) {
        if (Constant.kindMap.containsKey(item.getName())) {
            ((CustomRoundAngleImageView) viewHolder.getView(R.id.ivKindIcon)).setImageResource(Constant.kindMap.get(item.getName()));
        } else {
            CommonUtils.setPicture540x280(item.getIconUrl(), viewHolder.getView(R.id.ivKindIcon));
        }
        ((TextView) viewHolder.getView(R.id.tvKindName)).setText(item.getName());
    }
}
