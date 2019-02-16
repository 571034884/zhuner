package com.aibabel.map.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aibabel.map.R;
import com.aibabel.map.bean.TransitBusBean;
import com.aibabel.map.bean.transiten.TransitRoutesEnBean;
import com.aibabel.map.bean.transitzh.TransitRoutesZhBean;
import com.aibabel.map.utils.BaiDuUtil;
import com.aibabel.map.views.DragFlowLayout;
import com.aibabel.map.views.FlowLayout;

import java.util.List;

/**
 * Created by fytworks on 2018/11/30.
 */

public class TransitAdapter extends BaseAdapter {

    private Context mContext;
    private List<?> routes;
    private LayoutInflater mInflater;
    private int index;//0是国内    1国外

    public TransitAdapter(Context context, List<?> routeLines, int index) {
        this.mContext = context;
        this.routes = routeLines;
        this.index = index;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return routes.size();
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
        //布局初始化
        convertView = mInflater.inflate(R.layout.route_transit_item, parent, false);
        //控件初始化
        FlowLayout mFlow = convertView.findViewById(R.id.flow_txt);
        TextView mBest = convertView.findViewById(R.id.tv_best);
        TextView mMin = convertView.findViewById(R.id.tv_min);
        TextView mKm = convertView.findViewById(R.id.tv_km);
        TextView mDetails = convertView.findViewById(R.id.tv_details);
        switch (index){
            case 0:
                //当前item数据
                TransitRoutesZhBean zhBean = (TransitRoutesZhBean) routes.get(position);
                //数据展示
                if (position == 0) {
                    mBest.setVisibility(View.VISIBLE);
                } else {
                    mBest.setVisibility(View.GONE);
                }
                mMin.setText(BaiDuUtil.getHoursMin(zhBean.getDuration()));//总耗时
                mKm.setText(BaiDuUtil.getLineWalkZh(zhBean.getSteps()));//步行公里
                String lineCountZh = BaiDuUtil.getLineCountZh(zhBean.getSteps());//多少站
                String lineStartZh = BaiDuUtil.getLineStartZh(zhBean.getSteps());//首次上车站点
                mDetails.setText(lineCountZh + " · " + lineStartZh);
                List<TransitBusBean> lineNameZh = BaiDuUtil.getLineNameZh(zhBean.getSteps());//获取当前路线公交or地铁集合
                showFlow(lineNameZh,mFlow);
                break;
            case 1:
                //当前item数据
                TransitRoutesEnBean eNBean = (TransitRoutesEnBean) routes.get(position);
                //数据展示
                if (position == 0) {
                    mBest.setVisibility(View.VISIBLE);
                } else {
                    mBest.setVisibility(View.GONE);
                }
                mMin.setText(BaiDuUtil.getHoursMin(eNBean.getDuration()));//总耗时
                mKm.setText(BaiDuUtil.getLineWalkEn(eNBean.getSteps()));//步行公里
                String lineCountEn = BaiDuUtil.getLineCountEn(eNBean.getSteps());//多少站
                String lineStartEn = BaiDuUtil.getLineStartEn(eNBean.getSteps());//首次上车站点
                mDetails.setText(lineCountEn + " · " + lineStartEn);
                List<TransitBusBean> lineNameEn = BaiDuUtil.getLineNameEn(eNBean.getSteps());//获取当前路线公交or地铁集合
                showFlow(lineNameEn,mFlow);
                break;
        }
        return convertView;
    }
    /**
     * 循环添加View
     * @param lineNameZh 当前需要添加的集合
     * @param mFlow 父View
     */
    private void showFlow(List<TransitBusBean> lineNameZh, FlowLayout mFlow) {
        mFlow.setAlignByCenter(FlowLayout.AlienState.LEFT);
        for (int i = 0; i < lineNameZh.size(); i++) {
            if (i == lineNameZh.size()-1){
                if (lineNameZh.get(i).getType() == 0  || lineNameZh.get(i).getType() == 10){
                    addViewAuto(lineNameZh.get(i).getName(),R.layout.transit_name_view,mFlow);
                }else if (lineNameZh.get(i).getType() == 1){
                    addViewAuto(lineNameZh.get(i).getName(),R.layout.subway_name_view,mFlow);
                }
            }else{
                if (lineNameZh.get(i).getType() == 0 || lineNameZh.get(i).getType() == 10){
                    addViewAuto(lineNameZh.get(i).getName(),R.layout.transit_name_view,mFlow);
                }else if (lineNameZh.get(i).getType() == 1){
                    addViewAuto(lineNameZh.get(i).getName(),R.layout.subway_name_view,mFlow);
                }
                //加载图片
                View view = LayoutInflater.from(mContext).inflate(R.layout.transit_center_view, null);
                mFlow.addView(view);
            }
        }
    }

    /**
     * 添加到父View
     * @param name  添加的文字
     * @param layout    布局
     * @param mName     父View
     */
    private void addViewAuto(String name, int layout, FlowLayout mName) {
        View view = LayoutInflater.from(mContext).inflate(layout, null);
        TextView tvName = view.findViewById(R.id.tv_name);
        tvName.setText(name);
        mName.addView(view);
    }
}
