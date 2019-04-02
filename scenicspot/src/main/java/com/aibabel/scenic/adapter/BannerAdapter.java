package com.aibabel.scenic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aibabel.scenic.R;
import com.aibabel.scenic.banner.BannerLayout;
import com.aibabel.scenic.bean.PoiDetailsBean;
import com.aibabel.scenic.utils.CommonUtils;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by fytworks on 2019/4/2.
 */

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.MzViewHolder> {

    private Context context;
    private List<PoiDetailsBean> mData;
    private BannerLayout.OnBannerItemClickListener onBannerItemClickListener;

    public BannerAdapter(Context context, List<PoiDetailsBean> mData) {
        this.context = context;
        this.mData = mData;
    }

    public void setOnBannerItemClickListener(BannerLayout.OnBannerItemClickListener onBannerItemClickListener) {
        this.onBannerItemClickListener = onBannerItemClickListener;
    }

    @Override
    public BannerAdapter.MzViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MzViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false));
    }

    @Override
    public void onBindViewHolder(BannerAdapter.MzViewHolder holder, final int position) {
        if (mData == null || mData.isEmpty())
            return;
        final int P = position % mData.size();
        PoiDetailsBean bean = mData.get(P);
        ImageView img = holder.imageView;
        TextView tvName = holder.tvName;
        tvName.setText(bean.cityname+" · "+bean.name);
        Glide.with(context).load(bean.cover).apply(CommonUtils.options).into(img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBannerItemClickListener != null) {
                    onBannerItemClickListener.onItemClick(P);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }


    class MzViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvName;

        MzViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_iv);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }

}
