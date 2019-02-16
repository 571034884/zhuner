package com.aibabel.map.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.aibabel.baselibrary.imageloader.ImageLoader;
import com.aibabel.map.R;
import com.aibabel.map.bean.BusinessBean;
import com.aibabel.map.utils.StringUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class AdapterMain extends BaseQuickAdapter<BusinessBean.DataBean, BaseViewHolder> {

    public AdapterMain(int layoutResId, @Nullable List data) {
        super(layoutResId, data);
    }

    public AdapterMain(@Nullable List data) {
        super(data);
    }

    public AdapterMain(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, BusinessBean.DataBean item) {

        helper.setText(R.id.tv_ch, item.getNameCh());
        helper.setText(R.id.tv_en, item.getNameEn());
        helper.setText(R.id.tv_category, item.getTag());
        helper.setText(R.id.tv_distance, StringUtils.getKm(item.getDistance()));
        ImageView iv_desc = helper.getView(R.id.iv_desc);
        ImageLoader.getInstance().load(item.getImage()).angle(10).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(iv_desc);

    }


}
