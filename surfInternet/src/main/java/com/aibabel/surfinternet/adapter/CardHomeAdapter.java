package com.aibabel.surfinternet.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aibabel.baselibrary.utils.DeviceUtils;
import com.aibabel.surfinternet.R;
import com.aibabel.surfinternet.bean.DetailPayBean;
import com.aibabel.surfinternet.utils.CommonUtils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fytworks on 2019/3/23.
 */

public class CardHomeAdapter extends PagerAdapter implements CardAdapter{
    private List<CardView> mViews;
    private Context mContext;
    private List<DetailPayBean.DataBean> mData;

    private float mBaseElevation;

    public CardHomeAdapter(Context mContext, List<DetailPayBean.DataBean> mData){
        this.mContext = mContext;
        this.mData = mData;
        mViews = new ArrayList<>();
        if (mData != null) {
            for (int i = 0; i < mData.size(); i++) {
                mViews.add(null);
            }
        }
    }

    @Override
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
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_card, container, false);
        container.addView(view);
        bindViewData(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bindViewData(DetailPayBean.DataBean beans, View view) {
        ImageView iv = view.findViewById(R.id.imagerl);
        Glide.with(mContext).load(beans.getFlowPic()).apply(CommonUtils.options).into(iv);
        TextView tv = view.findViewById(R.id.tv_name);
        tv.setText(beans.getHighFlowSize()+"");
    }
}
