package com.aibabel.food.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.aibabel.baselibrary.adapter.BaseRecyclercAdapter;
import com.aibabel.food.R;
import com.aibabel.food.base.Constant;
import com.aibabel.food.bean.KindBean;
import com.aibabel.food.custom.circleimage.CustomRoundAngleImageView;
import com.aibabel.food.utils.CommonUtils;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder;

/**
 * 作者：SunSH on 2018/12/4 17:36
 * 功能：
 * 版本：1.0
 */
public class CateKindAdapter extends BaseRecyclercAdapter<KindBean.DataBean> {

    public Context context;

    public CateKindAdapter(Context context) {
        super(context, R.layout.item_kind_homepage);
        this.context = context;
    }

    @Override
    protected void HelperBindData(HelperRecyclerViewHolder viewHolder, int position, KindBean.DataBean item) {
        if (Constant.kindMap.containsKey(item.getName_cn())) {
            ((CustomRoundAngleImageView) viewHolder.getView(R.id.ivKindIcon)).setImageResource(Constant.kindMap.get(item.getName_cn()));
        } else {
            CommonUtils.setPicture540x280(item.getImage_url(), viewHolder.getView(R.id.ivKindIcon));
        }
        ((TextView) viewHolder.getView(R.id.tvKindName)).setText(item.getName_cn());
    }
}
