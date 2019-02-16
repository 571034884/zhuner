package com.aibabel.map.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aibabel.map.R;
import com.aibabel.map.bean.trafficen.RoutesEnBean;
import com.aibabel.map.bean.trafficzh.RoutesZhBean;
import com.aibabel.map.utils.BaiDuUtil;
import com.aibabel.map.utils.MathUtil;

import java.util.List;

/**
 * Created by fytworks on 2019/1/2.
 */

public class MyGridAdapter extends BaseAdapter{
    private Context mContext;
    private List<?> routes;
    private int locationWhere;
    private LayoutInflater mInflater;
    private int clickSelected = 0;

    public MyGridAdapter(Context mContext, List<?> routes,int locationWhere){
        this.mContext = mContext;
        this.routes = routes;
        this.locationWhere = locationWhere;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return routes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.grid_item_scheme, parent, false);
            holder.mTvScheme = convertView.findViewById(R.id.tv_route_name_one);
            holder.mTvMin = convertView.findViewById(R.id.tv_route_min_one);
            holder.mTvKm = convertView.findViewById(R.id.tv_route_km_one);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        switch (position){
            case 0:
                holder.mTvScheme.setText("最优路线");
                break;
            case 1:
                holder.mTvScheme.setText("不走高速");
                break;
        }

        if (clickSelected == position){
            holder.mTvScheme.setTextColor(mContext.getResources().getColor(R.color.c_red));
            holder.mTvMin.setTextColor(mContext.getResources().getColor(R.color.c_red));
            holder.mTvKm.setTextColor(mContext.getResources().getColor(R.color.c_red));
        }else{
            holder.mTvScheme.setTextColor(mContext.getResources().getColor(R.color.c_33));
            holder.mTvMin.setTextColor(mContext.getResources().getColor(R.color.c_33));
            holder.mTvKm.setTextColor(mContext.getResources().getColor(R.color.c_33));
        }
        switch (locationWhere){
            case 0:
                //国外
                RoutesEnBean driveRoutesEnBean = (RoutesEnBean) routes.get(position);
                holder.mTvMin.setText(BaiDuUtil.getHoursMin(driveRoutesEnBean.getDuration()));
                holder.mTvKm.setText(MathUtil.mathDivision(driveRoutesEnBean.getDistance(), 1000, 2) + "公里");
                break;
            case 1:
                //国内
                RoutesZhBean driveRoutesZhBean = (RoutesZhBean) routes.get(position);
                holder.mTvMin.setText(BaiDuUtil.getHoursMin(driveRoutesZhBean.getDuration()));
                holder.mTvKm.setText(MathUtil.mathDivision(driveRoutesZhBean.getDistance(), 1000, 2) + "公里");
                break;
        }



        return convertView;
    }

    public class ViewHolder{
        public TextView mTvScheme;
        public TextView mTvMin;
        public TextView mTvKm;
    }

    /**
     * 暴露方法
     * @param clickSelected
     */
    public void setSeclection(int clickSelected) {
        this.clickSelected = clickSelected;
    }

}
