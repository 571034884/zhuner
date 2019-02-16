package com.aibabel.map.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.aibabel.map.R;
import com.aibabel.map.bean.search.AddressResult;

import java.util.List;

/**
 * Created by fytworks on 2018/11/19.
 */

public class SearchAddressAdapter extends BaseAdapter {

    private Context mContext;
    private List<AddressResult> allPoi;
    private LayoutInflater mInflater;

    public SearchAddressAdapter(Context context, List<AddressResult> allPoi) {
        this.mContext = context;
        this.allPoi = allPoi;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return allPoi.size();
    }

    @Override
    public Object getItem(int position) {
        return allPoi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.adapter_address_item, viewGroup, false);
            holder.mName = convertView.findViewById(R.id.tv_name);
            holder.mCity = convertView.findViewById(R.id.tv_city);
            holder.mAddress = convertView.findViewById(R.id.tv_address);
            holder.mKyBord = convertView.findViewById(R.id.tv_kebord);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        AddressResult addressResult = allPoi.get(position);
        holder.mName.setText(addressResult.getName());
        holder.mCity.setText(addressResult.getCity());
        holder.mAddress.setText(addressResult.getAddr());
        if (allPoi.size()-1 == position){
            holder.mKyBord.setVisibility(View.VISIBLE);
        }else{
            holder.mKyBord.setVisibility(View.GONE);
        }


        return convertView;
    }

    public class ViewHolder {
        public TextView mName;
        public TextView mCity;
        public TextView mAddress;
        public View mKyBord;
    }

}
