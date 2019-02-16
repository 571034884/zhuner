package com.aibabel.map.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.aibabel.map.R;
import com.aibabel.map.bean.TransitBusBean;
import com.aibabel.map.bean.TransitDetailsBean;
import com.aibabel.map.views.AutoNextLineLinearlayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fytworks on 2018/12/3.
 */

public class TransitHeaderAdapter extends PagerAdapter implements CardAdapter{

    private Context mContext;
    private List<TransitDetailsBean> mData;
    private List<CardView> mViews;
    private float mBaseElevation;
    private onClickListener listener;

    public TransitHeaderAdapter(Context mContext, List<TransitDetailsBean> mData){
        this.mContext = mContext;
        this.mData = mData;
        mViews = new ArrayList<>();
        if (mData != null) {
            for (int i = 0; i < mData.size(); i++) {
                mViews.add(null);
            }
        }
    }

    public void setOnItemClickListener(onClickListener listener) {
        this.listener = listener;
    }

    public interface onClickListener {
        void onItemClick(TransitDetailsBean item);
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
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.hor_header_item, container, false);
        container.addView(view);
        bindData(mData.get(position),view,mData.size(),position);
        CardView cardView = view.findViewById(R.id.cardView);
        mViews.set(position, cardView);
        return view;
    }

    private void bindData(TransitDetailsBean beans, View view, int size, int index) {
        TextView tvDeatils = view.findViewById(R.id.tv_details);
        AutoNextLineLinearlayout alLine = view.findViewById(R.id.al_name);
        LinearLayout llPoint = view.findViewById(R.id.ll_point);

        for (int i = 0 ; i <size ; i++){
            View viewPonit = new View(mContext);
            LayoutParams layoutParams = null;
            if (i == index){
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


        tvDeatils.setText(beans.getTimer()+" · "+beans.getSubWay()+" · "+beans.getStartName());
        showName(beans.getLineName(), alLine);
    }

    /**
     * 循环添加View
     * @param lineNameZh 当前需要添加的集合
     * @param mName 父View
     */
    private void showName(List<TransitBusBean> lineNameZh, AutoNextLineLinearlayout mName) {
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
    private void addViewAuto(String name, int layout, AutoNextLineLinearlayout mName) {
        View view = LayoutInflater.from(mContext).inflate(layout, null);
        TextView tvName = view.findViewById(R.id.tv_name);
        tvName.setText(name);
        mName.addView(view);
    }
}
