package com.aibabel.scenic.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aibabel.scenic.R;
import com.aibabel.scenic.bean.SearchBean;
import com.aibabel.scenic.bean.SearchWordBean;

import java.util.List;

/**
 * Created by fytworks on 2019/3/25.
 */

public class SearchWordAdapter extends BaseAdapter{

    private Context mContext;
    private List<SearchWordBean.DataBean.CityListBean> mData;
    private onClickListener listener;
    public SearchWordAdapter(Context mContext, List<SearchWordBean.DataBean.CityListBean> mData){
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
            convertView = View.inflate(mContext, R.layout.item_search,null);
            holder = new ViewHolder();
            holder.tvName = convertView.findViewById(R.id.tv_name);
            holder.tvRead = convertView.findViewById(R.id.tv_read);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        SearchWordBean.DataBean.CityListBean bean = mData.get(position);
        holder.tvName.setText(bean.getName());
        holder.tvRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(bean);
            }
        });
        return convertView;
    }

    public class ViewHolder{
        TextView tvName;
        TextView tvRead;
    }

    public void setOnItemClickListener(onClickListener listener) {
        this.listener = listener;
    }


    public interface onClickListener {
        void onItemClick(SearchWordBean.DataBean.CityListBean bean);
    }

}
