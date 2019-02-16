package com.aibabel.coupon.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aibabel.baselibrary.adapter.BaseRecyclercAdapter;

import com.aibabel.baselibrary.http.BaseBean;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.coupon.R;
import com.aibabel.coupon.activity.DetailsActivity;
import com.aibabel.coupon.activity.ReceiveActivity;
import com.aibabel.coupon.bean.Constans;
import com.aibabel.coupon.bean.FilterBean;
import com.aibabel.coupon.ratingbar.XRatingBar;
import com.aibabel.coupon.utils.CommonUtils;
import com.bumptech.glide.Glide;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：SunSH on 2018/12/4 17:36
 * 功能：
 * 版本：1.0
 */
public class FilterAdapter extends BaseRecyclercAdapter<FilterBean.DataBean> {

    public Context context;
    private TextView tv_receive;
    private int couponId;
    public FilterAdapter(Context context) {
//        super(context, R.layout.item_kind_filter);
        super(context, R.layout.recy_coupon);
        this.context = context;
    }

    @Override
    protected void HelperBindData(HelperRecyclerViewHolder viewHolder, int position, FilterBean.DataBean item) {
//        ((TextView) viewHolder.getView(R.id.tvName)).setText(item.getTitle());
//        CommonUtils.setPicture1x1(item.getImage(), viewHolder.getView(R.id.ivIcon));
//        ((TextView) viewHolder.getView(R.id.tvManPay)).setText(item.getYouhui());
//        ((TextView) viewHolder.getView(R.id.tvTips)).setText(item.getTiaojianshort());
//        ((XRatingBar) viewHolder.getView(R.id.rbStar)).setRating(Float.valueOf(item.getScore()));
        TextView tv_shop_name = viewHolder.getView(R.id.tv_shop_name);
        TextView tv_shop_price = viewHolder.getView(R.id.tv_shop_price);
        TextView tv_shop_details = viewHolder.getView(R.id.tv_shop_details);
        tv_receive = viewHolder.getView(R.id.tv_receive);
        RelativeLayout rlPopu = viewHolder.getView(R.id.rl_popu);
        RelativeLayout rlReceive = viewHolder.getView(R.id.rl_receive);
        ImageView iv_shop_img = viewHolder.getView(R.id.iv_shop_img);
        CommonUtils.setPicture1x1(item.getCouponData().getImage(), iv_shop_img);
        tv_shop_name.setText(item.getCouponData().getTitle());
        tv_shop_price.setText(item.getCouponData().getYouhui());
        tv_shop_details.setText(item.getCouponData().getTiaojianshort());
        if (TextUtils.equals(item.getUserHasThisCoupon(), "true")) {
            tv_receive.setText("去使用");
        } else {
            tv_receive.setText("免费领");
        }
        if (TextUtils.equals(item.getUserHasThisCoupon(), "false")) {
//                    tv_receive.setBackgroundResource(R.drawable.shape_background);

            tv_receive.setTextColor(Color.parseColor("#ffffff"));
        } else {
//                    tv_receive.setBackgroundResource(R.drawable.shape_background2);

            tv_receive.setTextColor(Color.parseColor("#ffffff"));
        }

    }


}
