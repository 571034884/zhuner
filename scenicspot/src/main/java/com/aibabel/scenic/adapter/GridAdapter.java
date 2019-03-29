package com.aibabel.scenic.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aibabel.scenic.R;
import com.aibabel.scenic.bean.PoiDetailsBean;
import com.aibabel.scenic.utils.CommonUtils;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by fytworks on 2019/3/25.
 */

public class GridAdapter extends BaseAdapter{

    private Context mContext;
    private List<PoiDetailsBean> mData;

    public GridAdapter(Context mContext,List<PoiDetailsBean> mData){
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = View.inflate(mContext, R.layout.item_grid,null);
            holder = new ViewHolder();
            holder.mImg = convertView.findViewById(R.id.item_iv);
            holder.tvName = convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        PoiDetailsBean bean = mData.get(position);
        holder.tvName.setText(bean.name);
        Glide.with(mContext).load(bean.cover).apply(CommonUtils.options).into(holder.mImg);

        return convertView;
    }

    public class ViewHolder{
        ImageView mImg;
        TextView tvName;
    }

}
