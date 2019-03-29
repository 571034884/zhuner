package com.aibabel.scenic.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.aibabel.scenic.R;
import com.aibabel.scenic.bean.HistoryBean;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

//public class Adapter_V extends BaseQuickAdapter<HistoryBean.DataBean.DataTourguideBean, BaseViewHolder> {
//
//
//
//    public Adapter_V(int layoutResId, @Nullable List<HistoryBean.DataBean.DataTourguideBean> data) {
//        super(layoutResId, data);
//    }
//
//    @Override
//    protected void convert(BaseViewHolder helper, HistoryBean.DataBean.DataTourguideBean item) {
//        helper.setText(R.id.tv_item_name, item.getName());
//        // 加载网络图片
//        Glide.with(mContext).load(item.getCover()).into((ImageView) helper.getView(R.id.iv_item_img));
//
//    }
//}
public class Adapter_V extends BaseQuickAdapter<HistoryBean, BaseViewHolder> {



    public Adapter_V(int layoutResId, @Nullable List<HistoryBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryBean item) {
        helper.setText(R.id.tv_item_name, item.getName());
        // 加载网络图片
        Glide.with(mContext).load(item.getCover()).into((ImageView) helper.getView(R.id.iv_item_img));

    }
}