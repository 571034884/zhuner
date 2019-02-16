package com.aibabel.map.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aibabel.baselibrary.imageloader.ImageLoader;
import com.aibabel.map.MainActivity;
import com.aibabel.map.R;
import com.aibabel.map.activity.DialogActivity;
import com.aibabel.map.activity.TestActivity;
import com.aibabel.map.bean.BusinessBean;
import com.aibabel.map.bean.CardItem;
import com.aibabel.map.utils.StringUtils;
import com.aibabel.map.views.CornerTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


import java.util.ArrayList;
import java.util.List;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    //    private List<CardItem> mData;
    private List<BusinessBean.DataBean> mData;
    private float mBaseElevation;
    private Context context;
    private onClickListener listener;

    public CardPagerAdapter(List<BusinessBean.DataBean> mData, Context context) {
        this.mData = mData;
        this.context = context;
        mViews = new ArrayList<>();
        if (mData != null) {
            for (int i = 0; i < mData.size(); i++) {
                mViews.add(null);
            }
        }
    }


    public void setOnItemClickListener(onClickListener listener) {
        this.listener = listener;
    }


    public void addCardItem(List<BusinessBean.DataBean> items) {
        mViews.removeAll(mViews);
        this.mData = items;
        if (mData != null) {
            for (int i = 0; i < mData.size(); i++) {
                mViews.add(null);
            }
        }
        notifyDataSetChanged();
    }

    public void clearCardItem() {
        mViews.clear();
        mData.clear();
        this.notifyDataSetChanged();
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.adapter_card, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(mData.get(position), view);
            }
        });

//        if (mBaseElevation == 0) {
//            mBaseElevation = cardView.getCar
// dElevation();
//        }
//
//        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
//        cardView.setCardElevation(20);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(final BusinessBean.DataBean item, View view) {
        TextView tv_ch = (TextView) view.findViewById(R.id.tv_ch);
        TextView tv_local = (TextView) view.findViewById(R.id.tv_local);
        TextView tv_go = (TextView) view.findViewById(R.id.tv_go);
        ImageView iv_desc = (ImageView) view.findViewById(R.id.iv_desc);
        ImageView iv_label = (ImageView) view.findViewById(R.id.iv_label);
        TextView tv_category = (TextView) view.findViewById(R.id.tv_category);
        TextView tv_distance = (TextView) view.findViewById(R.id.tv_distance);
        tv_ch.setText(item.getNameCh() + "");
        tv_category.setText(item.getTag() + "");
        tv_distance.setText(StringUtils.getKm(item.getDistance()) + "");

        if (TextUtils.equals(item.getRecommend(), "1")) {
            iv_label.setVisibility(View.VISIBLE);
        }
        if (TextUtils.equals(item.getTagType(), "metro")) {
            iv_desc.setVisibility(View.GONE);
            iv_label.setVisibility(View.GONE);
//            tv_go.setVisibility(View.VISIBLE);
            tv_distance.setVisibility(View.GONE);
            tv_local.setText(item.getAddress() + "");
        } else {
            tv_local.setText(item.getLocalName() + "");
        }

        if (TextUtils.equals(item.getTagType(), "scenic")){//景区
            ImageLoader.getInstance().load(item.getImage()).placeholder(R.mipmap.icon_scenic_default_img).error(R.mipmap.icon_scenic_img).into(iv_desc);
        }else if (TextUtils.equals(item.getTagType(), "cate")){//美食
            ImageLoader.getInstance().load(item.getImage()).placeholder(R.mipmap.icon_cate_default_img).error(R.mipmap.icon_cate_img).into(iv_desc);
        }else if (TextUtils.equals(item.getTagType(), "shop")){//购物
            ImageLoader.getInstance().load(item.getImage()).placeholder(R.mipmap.icon_shop_default_img).error(R.mipmap.icon_shop_img).into(iv_desc);
        }

    }


    public interface onClickListener {
        void onItemClick(BusinessBean.DataBean item, View view);
    }


}
