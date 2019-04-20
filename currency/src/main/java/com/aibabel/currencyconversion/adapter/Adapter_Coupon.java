package com.aibabel.currencyconversion.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.aibabel.currencyconversion.R;
import com.aibabel.currencyconversion.bean.CouponBean;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


public class Adapter_Coupon extends BaseQuickAdapter<CouponBean.DataBean, BaseViewHolder> {


    public Adapter_Coupon(int layoutResId, @Nullable List<CouponBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponBean.DataBean item) {
        helper.setText(R.id.tv_item_shop, item.getCouponData().getTitle());
        helper.setText(R.id.tv_item_coupon, item.getCouponData().getTitle());
        helper.setText(R.id.tv_item_condition, item.getCouponData().getTitle());
        // 加载网络图片
        Glide.with(mContext).load(item.getCouponData().getImage()).into((ImageView) helper.getView(R.id.iv_item_img));

        helper.addOnClickListener(R.id.tv_item_use);

    }
}
