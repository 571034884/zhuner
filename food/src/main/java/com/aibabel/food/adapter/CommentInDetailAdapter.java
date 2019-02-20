package com.aibabel.food.adapter;

import android.content.Context;

import com.aibabel.food.bean.CommentBean;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewAdapter;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder;

import java.util.List;

/**
 * 作者：SunSH on 2018/12/4 17:36
 * 功能：详情页面的评论列表适配器
 * 版本：1.0
 */
public class CommentInDetailAdapter extends HelperRecyclerViewAdapter<CommentBean.CommentItemBean> {

    public Context context;

    public CommentInDetailAdapter(List data, Context context, int... layoutId) {
        super(data, context, layoutId);
        this.context = context;
    }

    @Override
    protected void HelperBindData(HelperRecyclerViewHolder viewHolder, int position, CommentBean.CommentItemBean item) {
//        ((TextView) viewHolder.getView(R.id.tvName)).setText(item.getName());
//        ((TextView) viewHolder.getView(R.id.tvManPay)).setText(item.getManPay());
//        ((TextView) viewHolder.getView(R.id.tvTips)).setText(item.getTip());
//        ((XRatingBar) viewHolder.getView(R.id.rbStar)).setRating(item.getScore());
    }
}
