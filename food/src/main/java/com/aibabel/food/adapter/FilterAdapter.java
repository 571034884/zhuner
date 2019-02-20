package com.aibabel.food.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.aibabel.baselibrary.adapter.BaseRecyclercAdapter;
import com.aibabel.food.R;
import com.aibabel.food.bean.FilterBean;
import com.aibabel.food.custom.ratingbar.XRatingBar;
import com.aibabel.food.utils.CommonUtils;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder;

/**
 * 作者：SunSH on 2018/12/4 17:36
 * 功能：
 * 版本：1.0
 */
public class FilterAdapter extends BaseRecyclercAdapter<FilterBean.DataBean> {

    public Context context;

    public FilterAdapter(Context context) {
        super(context, R.layout.item_kind_filter);
        this.context = context;
    }

    @Override
    protected void HelperBindData(HelperRecyclerViewHolder viewHolder, int position, FilterBean.DataBean item) {
        ((TextView) viewHolder.getView(R.id.tvName)).setText(item.getShopName());
        CommonUtils.setPicture1x1(item.getPicUrl(), viewHolder.getView(R.id.ivIcon));
        ((TextView) viewHolder.getView(R.id.tvManPay)).setText(item.getPerperson());
        ((TextView) viewHolder.getView(R.id.tvTips)).setText(item.getTagJson());
        ((XRatingBar) viewHolder.getView(R.id.rbStar)).setRating(Float.valueOf(item.getScore()));
    }
}
