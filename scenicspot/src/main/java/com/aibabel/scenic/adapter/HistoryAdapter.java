package com.aibabel.scenic.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.aibabel.scenic.R;
import com.aibabel.scenic.bean.PoiDetailsBean;
import com.aibabel.scenic.utils.CommonUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by fytworks on 2019/3/25.
 */

public class HistoryAdapter extends BaseQuickAdapter<PoiDetailsBean, BaseViewHolder> {


    public HistoryAdapter(int layoutResId, @Nullable List<PoiDetailsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PoiDetailsBean item) {

        helper.setText(R.id.tv_name, item.name);
        // 加载网络图片
        Glide.with(mContext).load(item.cover).apply(CommonUtils.options).into((ImageView) helper.getView(R.id.item_iv));
    }
}
