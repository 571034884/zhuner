package com.aibabel.scenic.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aibabel.scenic.R;
import com.aibabel.scenic.bean.PoiDetailsBean;
import com.aibabel.scenic.utils.CommonUtils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fytworks on 2019/3/23.
 */

public class CardHomeAdapter extends PagerAdapter implements CardAdapter{
    private List<CardView> mViews;
    private Context mContext;
    private List<PoiDetailsBean> mData;

    private onClickListener listener;
    private float mBaseElevation;

    public CardHomeAdapter(Context mContext, List<PoiDetailsBean> mData){
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
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(mData.get(position), view);
            }
        });

        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bindViewData(PoiDetailsBean beans, View view) {
        TextView tvName = view.findViewById(R.id.tv_name);
        tvName.setText(beans.cityname+" · "+beans.name);
        ImageView iv = view.findViewById(R.id.item_iv);
        Glide.with(mContext).load(beans.cover).apply(CommonUtils.options).into(iv);
    }


    public void setOnItemClickListener(onClickListener listener) {
        this.listener = listener;
    }


    public interface onClickListener {
        void onItemClick(PoiDetailsBean bean, View view);
    }

}
