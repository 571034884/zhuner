package com.aibabel.map.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.aibabel.map.R;
import com.aibabel.map.bean.TransitBusBean;
import com.aibabel.map.bean.TransitDetailsBean;
import com.aibabel.map.bean.transiten.TransitRoutesEnBean;
import com.aibabel.map.bean.transitzh.TransitRoutesZhBean;
import com.aibabel.map.views.AutoNextLineLinearlayout;
import com.aibabel.map.views.FlowLayout;
import com.aibabel.map.views.MyListView;

import java.util.List;

/**
 * Created by fytworks on 2018/12/12.
 */

public class TransitRouteAdapter extends PagerAdapter {

    private List<?> routes;
    private Context mContext;
    private int locationWhere;
    private String start, end;
    private List<TransitDetailsBean> transitHeader;
    TransitDetailsZhAdapter zhAdapter = null;
    TransitDetailsEnAdapter enAdapter = null;

    public TransitRouteAdapter(Context mContext, List<?> routes, int locationWhere, String start, String end,List<TransitDetailsBean> transitHeader) {
        this.routes = routes;
        this.mContext = mContext;
        this.locationWhere = locationWhere;
        this.start = start;
        this.end = end;
        this.transitHeader = transitHeader;
    }

    @Override
    public int getCount() {
        return routes.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.viewpager_item, container,false);
        FlowLayout anlName = view.findViewById(R.id.al_name);
        TextView tvDetails = view.findViewById(R.id.tv_details);
        LinearLayout llPoint = view.findViewById(R.id.ll_point);
        for (int i = 0 ; i <routes.size() ; i++){
            View viewPonit = new View(mContext);
            LinearLayout.LayoutParams layoutParams = null;
            if (i == position){
                viewPonit.setBackgroundResource(R.drawable.point_focused);
                //1,定义点的宽高
                layoutParams = new LinearLayout.LayoutParams(15, 10);
            }else{
                viewPonit.setBackgroundResource(R.drawable.point_unfocused);
                //1,定义点的宽高
                layoutParams = new LinearLayout.LayoutParams(10, 10);
            }
            //2,给点设置间距
            layoutParams.setMargins(0, 0, 10, 0);
            //3,作用当前规则给子控件
            llPoint.addView(viewPonit, layoutParams);
        }
        //公共
        TransitDetailsBean beanHeader = transitHeader.get(position);

        switch (locationWhere) {
            case 0://国外
                TransitRoutesEnBean trEn = (TransitRoutesEnBean) routes.get(position);
                break;
            case 1://国内
                TransitRoutesZhBean trZh = (TransitRoutesZhBean) routes.get(position);
                break;
        }
        tvDetails.setText(beanHeader.getTimer()+" · "+beanHeader.getSubWay()+" · "+beanHeader.getStartName());
        showName(beanHeader.getLineName(), anlName);
        container.addView(view);
        return view;
    }

    /**
     * 循环添加View
     * @param lineNameZh 当前需要添加的集合
     * @param mName 父View
     */
    private void showName(List<TransitBusBean> lineNameZh, FlowLayout mName) {
        for (int i = 0; i < lineNameZh.size(); i++) {
            if (i == lineNameZh.size()-1){
                if (lineNameZh.get(i).getType() == 0 || lineNameZh.get(i).getType() == 10){
                    addViewAuto(lineNameZh.get(i).getName(),R.layout.transit_name_view,mName);
                }else if (lineNameZh.get(i).getType() == 1){
                    addViewAuto(lineNameZh.get(i).getName(),R.layout.subway_name_view,mName);
                }
            }else{
                if (lineNameZh.get(i).getType() == 0  || lineNameZh.get(i).getType() == 10){
                    addViewAuto(lineNameZh.get(i).getName(),R.layout.transit_name_view,mName);
                }else if (lineNameZh.get(i).getType() == 1){
                    addViewAuto(lineNameZh.get(i).getName(),R.layout.subway_name_view,mName);
                }
                //加载图片
                View view = LayoutInflater.from(mContext).inflate(R.layout.transit_center_view, null);
                mName.addView(view);
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
