package com.aibabel.scenic.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.aibabel.scenic.R;
import com.aibabel.scenic.bean.HistoryBean;
import com.aibabel.scenic.bean.SpotsBean;
import com.aibabel.scenic.view.CornerTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class Adapter_Spots extends BaseQuickAdapter<SpotsBean.DataBean.SubpoiMsgBean, BaseViewHolder> {



    public Adapter_Spots(int layoutResId, @Nullable List<SpotsBean.DataBean.SubpoiMsgBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SpotsBean.DataBean.SubpoiMsgBean item) {
        helper.setText(R.id.tv_item_name, TextUtils.isEmpty(item.getName())?" ":item.getName());
        // 加载网络图片
        RequestOptions options = new RequestOptions().placeholder(R.mipmap.placeholder_h).error(R.mipmap.error_h);
        Glide.with(mContext).load(item.getCover()).apply(options).into((ImageView) helper.getView(R.id.iv_item_img));
    }
}
