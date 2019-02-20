package com.aibabel.food.adapter;

import android.content.Context;
import android.widget.TextView;

import com.aibabel.baselibrary.adapter.BaseRecyclercAdapter;
import com.aibabel.food.R;
import com.aibabel.food.bean.AreaBean;
import com.aibabel.food.bean.HomePageAllBean;
import com.aibabel.food.utils.CommonUtils;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewAdapter;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder;

import java.util.List;

/**
 * 作者：SunSH on 2018/12/4 17:36
 * 功能：
 * 版本：1.0
 */
public class PopularAreaAdapter extends BaseRecyclercAdapter<AreaBean.DataBean> {

    public Context context;

    public PopularAreaAdapter(Context context) {
        super(context, R.layout.item_area_homepage);
        this.context = context;
    }

    @Override
    protected void HelperBindData(HelperRecyclerViewHolder viewHolder, int position, AreaBean.DataBean item) {
        CommonUtils.setPictureCircle1x1(item.getPic_url(), viewHolder.getView(R.id.ivAreaIcon));
        ((TextView) viewHolder.getView(R.id.tvAreaName)).setText(item.getName_cn());
    }
}
