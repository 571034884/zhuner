package com.aibabel.ocr.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aibabel.ocr.R;
import com.aibabel.ocr.utils.GlideRoundTransform;
import com.aibabel.ocr.widgets.CardItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<CardItem> mData;
    private float mBaseElevation;
    private Context context;
    private onClickListener listener;

    public CardPagerAdapter(List<CardItem> mData, Context context) {
        this.mData =mData;
        this.context = context;
        mViews = new ArrayList<>();
        if (mData!=null){
            for (int i = 0; i < mData.size(); i++) {
                mViews.add(null);
            }
        }
    }


   public void setOnItemClickListener(onClickListener listener){
        this.listener = listener;
    }


    public void addCardItem(List<CardItem> items) {
        mViews.removeAll(mViews);
        this.mData = items;
        if (mData!=null){
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
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.adapter, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(final CardItem item, View view) {
        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        TextView contentTextView = (TextView) view.findViewById(R.id.contentTextView);
        ImageView imageDetail = (ImageView) view.findViewById(R.id.imageDetail);
        TextView tvMore = (TextView) view.findViewById(R.id.tv_more);
        titleTextView.setText(item.getText());
        contentTextView.setText(item.getTitle());

        if(!TextUtils.isEmpty(item.getmTitleResource())){
            tvMore.setVisibility(View.VISIBLE);
        }else{
            tvMore.setVisibility(View.GONE);
        }
        RequestOptions mRequestOptions = new RequestOptions()
                .placeholderOf(R.mipmap.place_image)//图片加载出来前，显示的图片
                .errorOf(R.mipmap.error_image)//图片加载失败后，显示的图片
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(new GlideRoundTransform(context,8))
                .skipMemoryCache(true);

        Glide.with(context)
                .load(item.getmUrlResource())
                .apply(mRequestOptions)
                .into(imageDetail);


        imageDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(item.getmTitleResource())){
                    listener.onItemClick(item);
                }
            }
        });

        titleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(item.getmTitleResource())){
                    listener.onItemClick(item);
                }
            }
        });

        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(item.getmTitleResource())){
                    listener.onItemClick(item);
                }

            }
        });
    }

    public interface onClickListener{
        void onItemClick(CardItem item);
    }


}
