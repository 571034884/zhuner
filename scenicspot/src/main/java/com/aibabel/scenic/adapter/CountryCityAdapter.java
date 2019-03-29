package com.aibabel.scenic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.aibabel.scenic.R;
import com.aibabel.scenic.bean.AddressBean;
import com.aibabel.scenic.view.FlowLayout;
import com.aibabel.scenic.view.MyBaseAdapter;
import com.aibabel.scenic.view.ViewHolder;

import java.util.List;

/**
 * Created by fytworks on 2019/3/26.
 */

public class CountryCityAdapter extends MyBaseAdapter<AddressBean.DataBean,ListView> {

    private Context mContext;
    private List<AddressBean.DataBean> listBean;

    private onClickListener listener;
    public CountryCityAdapter(Context mContext, List<AddressBean.DataBean>  listBean){
        super(mContext,listBean);
        this.mContext = mContext;
        this.listBean = listBean;
    }

    @Override
    public int getCount() {
        return listBean.size();
    }

    @Override
    public Object getItem(int position) {
        return listBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        AddressBean.DataBean addressBean = listBean.get(position);
        convertView = View.inflate(mContext, R.layout.item_citycountry,null);
        TextView tvName = ViewHolder.get(convertView,R.id.tv_country);
        FlowLayout addFlow = ViewHolder.get(convertView,R.id.auto);
        tvName.setText(addressBean.getCountryName());
        addView(addressBean.getCityMsg(),addFlow);
        return convertView;
    }

    private class AgeOnClickListener implements View.OnClickListener {

        private Context mContext;
        private String str;

        public AgeOnClickListener(Context mContext, String str) {
            this.mContext = mContext;
            this.str = str;
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(str);
        }
    }

    private void addView(List<AddressBean.DataBean.CityMsgBean> cityMsg, FlowLayout addAuto) {
        if (cityMsg != null && cityMsg.size() != 0){
            for (int i = 0;i<cityMsg.size();i++){
                View view = LayoutInflater.from(mContext).inflate(R.layout.labels_item, null);
                view.setOnClickListener(new AgeOnClickListener(mContext, cityMsg.get(i).getCityName()));
                TextView tv = view.findViewById(R.id.tv_tag_content);
                tv.setText(cityMsg.get(i).getCityName());
                addAuto.addView(view);
            }
        }
    }

    public void setOnItemClickListener(onClickListener listener) {
        this.listener = listener;
    }


    public interface onClickListener {
        void onItemClick(String cityName);
    }

}
