package com.aibabel.food.adapter;

import android.content.Context;
import android.widget.TextView;

import com.aibabel.food.R;
import com.aibabel.food.bean.HomePageBean;
import com.aibabel.food.custom.ratingbar.SimpleRatingView;
import com.aibabel.food.custom.ratingbar.XRatingBar;
import com.aibabel.food.utils.CommonUtils;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewAdapter;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder;

import java.util.List;

/**
 * 作者：SunSH on 2018/12/4 17:36
 * 功能：
 * 版本：1.0
 */
public class RecommendAdapter extends HelperRecyclerViewAdapter<HomePageBean.PartBean.PartItemBean> {

    public Context context;

    public RecommendAdapter(List data, Context context, int... layoutId) {
        super(data, context, layoutId);
        this.context = context;
    }

    @Override
    protected void HelperBindData(HelperRecyclerViewHolder viewHolder, int position, HomePageBean.PartBean.PartItemBean item) {
//        CommonUtils.setPicture(context, item.getIconUrl(), viewHolder.getView(R.id.ivRecommendIcon));
        ((TextView) viewHolder.getView(R.id.tvRecommendName)).setText(item.getName());
        ((TextView) viewHolder.getView(R.id.tvRecommendTip)).setText(item.getTip());
//        CommonUtils.setPicture(context, item.getManUrl(), viewHolder.getView(R.id.ivManIcon));
        ((TextView) viewHolder.getView(R.id.tvManSay)).setText(item.getManSay());
        ((TextView) viewHolder.getView(R.id.tvManPay)).setText(item.getManPay());
        ((TextView) viewHolder.getView(R.id.tvScore)).setText(item.getScore() + "分");
        XRatingBar bar = viewHolder.getView(R.id.rbStar);
        bar.setRatingView(new SimpleRatingView());
        bar.setNumStars(5);
        bar.setRating(item.getScore());
    }
}
