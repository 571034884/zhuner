package com.aibabel.map.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.baselibrary.utils.FastJsonUtil;
import com.aibabel.baselibrary.utils.ToastUtil;
import com.aibabel.map.R;
import com.aibabel.map.adapter.TrafficAdapter;
import com.aibabel.map.bean.TrafficBean;
import com.aibabel.map.bean.trafficen.EnBean;
import com.aibabel.map.bean.trafficen.RoutesEnBean;
import com.aibabel.map.bean.trafficen.StepsEnBean;
import com.aibabel.map.bean.trafficzh.RoutesZhBean;
import com.aibabel.map.bean.trafficzh.StepsZhBean;
import com.aibabel.map.bean.trafficzh.ZhBean;
import com.aibabel.map.utils.BaiDuUtil;
import com.aibabel.map.utils.CommonUtils;
import com.aibabel.map.utils.MathUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 *
 * 交通详情统一处理
 * Created by fytworks on 2018/12/5.
 */

public class TrafficDetailActivity extends BaseActivity{

    @BindView(R.id.tv_close)
    TextView tvClose;
    @BindView(R.id.rl_scheme_one)
    RelativeLayout rlSchemeOne;
    @BindView(R.id.rl_scheme_two)
    RelativeLayout rlSchemeTwo;
    @BindView(R.id.rl_scheme_three)
    RelativeLayout rlSchemeThree;
    @BindView(R.id.tv_route_name_one)
    TextView tvRouteNameOne;
    @BindView(R.id.tv_route_name_two)
    TextView tvRouteNameTwo;
    @BindView(R.id.tv_route_name_three)
    TextView tvRouteNameThree;
    @BindView(R.id.tv_route_min_one)
    TextView tvRouteMinOne;
    @BindView(R.id.tv_route_min_two)
    TextView tvRouteMinTwo;
    @BindView(R.id.tv_route_min_three)
    TextView tvRouteMinThree;
    @BindView(R.id.tv_route_km_one)
    TextView tvRouteKmOne;
    @BindView(R.id.tv_route_km_two)
    TextView tvRouteKmTwo;
    @BindView(R.id.tv_route_km_three)
    TextView tvRouteKmThree;
    @BindView(R.id.tv_route_light_one)
    ImageView tvRouteLightOne;
    @BindView(R.id.tv_route_light_two)
    ImageView tvRouteLightTwo;
    @BindView(R.id.tv_route_light_three)
    ImageView tvRouteLightThree;
    @BindView(R.id.tv_route_lightnum_one)
    TextView tvRouteLightNumOne;
    @BindView(R.id.tv_route_lightnum_two)
    TextView tvRouteLightNumTwo;
    @BindView(R.id.tv_route_lightnum_three)
    TextView tvRouteLightNumThree;
    @BindView(R.id.v_line_one)
    View vLineOne;
    @BindView(R.id.v_line_two)
    View vLineTwo;
    @BindView(R.id.lv_details)
    ListView lvDetails;
    @BindView(R.id.rl_less)
    RelativeLayout rlLess;
    @BindView(R.id.ll_much)
    LinearLayout llMuch;
    @BindView(R.id.tv_less_min)
    TextView tvLessMin;
    @BindView(R.id.tv_less_km)
    TextView tvLessKm;
    @BindView(R.id.tv_route_light)
    ImageView tvRouteLight;
    @BindView(R.id.tv_route_lightnum)
    TextView tvRouteLightNum;


    List<TrafficBean> trafficList = null;
    ZhBean driveZhBean = null;
    EnBean driveEnBean = null;
    int locationWhere = 0;
    int index = 0;
    String startName = "";
    String endName = "";
    @Override
    public int getLayout(Bundle bundle) {
        return R.layout.activity_traffic;
    }

    @Override
    public void init() {
        locationWhere = getIntent().getIntExtra("locationWhere",0);
        index = getIntent().getIntExtra("index",0);
        startName = getIntent().getStringExtra("startName");
        endName = getIntent().getStringExtra("endName");
//        String json = getIntent().getStringExtra("json");
        if (locationWhere == 1){
            driveZhBean = (ZhBean) getIntent().getSerializableExtra("json");
//            driveZhBean = FastJsonUtil.changeJsonToBean(json,ZhBean.class);
            selectLineZh(driveZhBean);
            defaultView(index);
        }else if (locationWhere == 0){
            driveEnBean = (EnBean) getIntent().getSerializableExtra("json");
//            driveEnBean = FastJsonUtil.changeJsonToBean(json,EnBean.class);
            selectLineEn(driveEnBean);
            defaultView(index);
        }

//        lvDetails.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                if (firstVisibleItem == 0) {
//                    View firstVisibleItemView = lvDetails.getChildAt(0);
//                    if (firstVisibleItemView != null && firstVisibleItemView.getTop() == 0) {
//                        if (flagTrue){
//                            TrafficDetailActivity.this.finish();
//                            return;
//                        }
//                        flagTrue = true;
//                    }
//                }
//            }
//        });


    }
    private boolean flagTrue = false;

    private void selectLineEn(EnBean driveEnBean) {
        //判定有几条路线
        int size = driveEnBean.getData().getResult().getRoutes().size();
        if (size == 1){//只有一条
            rlLess.setVisibility(View.VISIBLE);//显示一条展示
            llMuch.setVisibility(View.GONE);//隐藏多条展示
            //数据展示
            showLessViewEn(driveEnBean.getData().getResult().getRoutes());
        }else{//有多条
            rlLess.setVisibility(View.GONE);//显示一条展示
            llMuch.setVisibility(View.VISIBLE);//隐藏多条展示
            showMuchViewEn(driveEnBean.getData().getResult().getRoutes());
        }
    }
    /**
     * 只有一条的显示方案    en
     * @param routes
     */
    private void showLessViewEn(List<RoutesEnBean> routes) {
        RoutesEnBean routesEnBean = routes.get(0);
        tvLessMin.setText(BaiDuUtil.getHoursMin(routesEnBean.getDuration()));
        tvLessKm.setText(MathUtil.mathDivision(routesEnBean.getDistance(), 1000, 2)+"公里");
        tvRouteLightNum.setText(routesEnBean.getLight_number()+"");
    }

    /**
     * 多条的显示方案 en
     * @param routes
     */
    private void showMuchViewEn(List<RoutesEnBean> routes) {
        //默认显示第一条方案
        defaultView(0);
        //判定方案是>=2or3
        showViewTraffic(routes.size());
        for (int i = 0 ;i <routes.size();i++){
            RoutesEnBean driveRoutesEnBean = routes.get(i);
            if (i == 0){
                tvRouteNameOne.setText("最优路线");
                tvRouteMinOne.setText(BaiDuUtil.getHoursMin(driveRoutesEnBean.getDuration()));
                tvRouteKmOne.setText(MathUtil.mathDivision(driveRoutesEnBean.getDistance(), 1000, 2)+"公里");
                tvRouteLightNumOne.setText(driveRoutesEnBean.getLight_number()+"");
            }else if (i == 1){
                tvRouteNameTwo.setText("不走高速");
                tvRouteMinTwo.setText(BaiDuUtil.getHoursMin(driveRoutesEnBean.getDuration()));
                tvRouteKmTwo.setText(MathUtil.mathDivision(driveRoutesEnBean.getDistance(), 1000, 2)+"公里");
                tvRouteLightNumTwo.setText(driveRoutesEnBean.getLight_number()+"");
            }
        }
    }

    private void selectLineZh(ZhBean driveZhBean) {
        //判定有几条路线
        int size = driveZhBean.getData().getResult().getRoutes().size();
        if (size == 1){//只有一条
            rlLess.setVisibility(View.VISIBLE);//显示一条展示
            llMuch.setVisibility(View.GONE);//隐藏多条展示
            //数据展示
            showLessViewZh(driveZhBean.getData().getResult().getRoutes());
        }else{//有多条
            rlLess.setVisibility(View.GONE);//显示一条展示
            llMuch.setVisibility(View.VISIBLE);//隐藏多条展示
            showMuchViewZh(driveZhBean.getData().getResult().getRoutes());
        }
    }

    /**
     * 只有一条的显示方案    zh
     * @param routes
     */
    private void showLessViewZh(List<RoutesZhBean> routes){
        RoutesZhBean driveRoutesZhBean = routes.get(0);
        tvLessMin.setText(BaiDuUtil.getHoursMin(driveRoutesZhBean.getDuration()));
        tvLessKm.setText(MathUtil.mathDivision(driveRoutesZhBean.getDistance(), 1000, 2)+"公里");
    }

    /**
     * 多条的显示方案 zh
     * @param routes
     */
    private void showMuchViewZh(List<RoutesZhBean> routes){
        //默认显示第一条方案
        defaultView(0);
        //判定方案是>=2or3
        showViewTraffic(routes.size());
        for (int i = 0 ;i <routes.size();i++){
            RoutesZhBean driveRoutesZhBean = routes.get(i);
            if (i == 0){
                tvRouteNameOne.setText("最优路线");
                tvRouteMinOne.setText(BaiDuUtil.getHoursMin(driveRoutesZhBean.getDuration()));
                tvRouteKmOne.setText(MathUtil.mathDivision(driveRoutesZhBean.getDistance(), 1000, 2)+"公里");
            }else if (i == 1){
                tvRouteNameTwo.setText("不走高速");
                tvRouteMinTwo.setText(BaiDuUtil.getHoursMin(driveRoutesZhBean.getDuration()));
                tvRouteKmTwo.setText(MathUtil.mathDivision(driveRoutesZhBean.getDistance(), 1000, 2)+"公里");
            }
        }
    }
    /**
     * 多条公共显示方案
     * @param size
     */
    private void showViewTraffic(int size) {
        switch (size){
            case 1:
                rlSchemeOne.setVisibility(View.VISIBLE);
                rlSchemeTwo.setVisibility(View.GONE);
                rlSchemeThree.setVisibility(View.GONE);
                vLineOne.setVisibility(View.GONE);
                vLineTwo.setVisibility(View.GONE);
                break;
            case 2:
                rlSchemeOne.setVisibility(View.VISIBLE);
                rlSchemeTwo.setVisibility(View.VISIBLE);
                rlSchemeThree.setVisibility(View.GONE);
                vLineOne.setVisibility(View.VISIBLE);
                vLineTwo.setVisibility(View.GONE);
                break;
            case 3:
                rlSchemeOne.setVisibility(View.VISIBLE);
                rlSchemeTwo.setVisibility(View.VISIBLE);
                rlSchemeThree.setVisibility(View.VISIBLE);
                vLineOne.setVisibility(View.VISIBLE);
                vLineTwo.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * 展示类型
     * @param index
     */
    private void defaultView(int index) {
        Resources res = getResources();
        tvRouteNameOne.setTextColor(res.getColor(R.color.c_99));
        tvRouteNameTwo.setTextColor(res.getColor(R.color.c_99));
        tvRouteNameThree.setTextColor(res.getColor(R.color.c_99));
        tvRouteMinOne.setTextColor(res.getColor(R.color.c_33));
        tvRouteMinTwo.setTextColor(res.getColor(R.color.c_33));
        tvRouteMinThree.setTextColor(res.getColor(R.color.c_33));
        tvRouteKmOne.setTextColor(res.getColor(R.color.c_33));
        tvRouteKmTwo.setTextColor(res.getColor(R.color.c_33));
        tvRouteKmThree.setTextColor(res.getColor(R.color.c_33));
        tvRouteLightOne.setImageResource(R.mipmap.ic_light_default);
        tvRouteLightTwo.setImageResource(R.mipmap.ic_light_default);
        tvRouteLightThree.setImageResource(R.mipmap.ic_light_default);
        tvRouteLightNumOne.setTextColor(res.getColor(R.color.c_33));
        tvRouteLightNumTwo.setTextColor(res.getColor(R.color.c_33));
        tvRouteLightNumThree.setTextColor(res.getColor(R.color.c_33));
        switch (locationWhere){
            case 1:
                tvRouteLightOne.setVisibility(View.GONE);
                tvRouteLightNumOne.setVisibility(View.GONE);
                tvRouteLightTwo.setVisibility(View.GONE);
                tvRouteLightNumTwo.setVisibility(View.GONE);
                tvRouteLightThree.setVisibility(View.GONE);
                tvRouteLightNumThree.setVisibility(View.GONE);
                tvRouteLight.setVisibility(View.GONE);
                tvRouteLightNum.setVisibility(View.GONE);
                initDrawDataZh(index);
                break;
            case 0:
                tvRouteLightOne.setVisibility(View.VISIBLE);
                tvRouteLightNumOne.setVisibility(View.VISIBLE);
                tvRouteLightTwo.setVisibility(View.VISIBLE);
                tvRouteLightNumTwo.setVisibility(View.VISIBLE);
                tvRouteLightThree.setVisibility(View.VISIBLE);
                tvRouteLightNumThree.setVisibility(View.VISIBLE);
                tvRouteLight.setVisibility(View.VISIBLE);
                tvRouteLightNum.setVisibility(View.VISIBLE);
                initDrawDataEn(index);
                break;
        }
        switch (index){
            case 0:
                tvRouteNameOne.setTextColor(res.getColor(R.color.c_red));
                tvRouteMinOne.setTextColor(res.getColor(R.color.c_red));
                tvRouteKmOne.setTextColor(res.getColor(R.color.c_red));
                tvRouteLightOne.setImageResource(R.mipmap.ic_light_select);
                tvRouteLightNumOne.setTextColor(res.getColor(R.color.c_red));
                break;
            case 1:
                tvRouteNameTwo.setTextColor(res.getColor(R.color.c_red));
                tvRouteMinTwo.setTextColor(res.getColor(R.color.c_red));
                tvRouteKmTwo.setTextColor(res.getColor(R.color.c_red));
                tvRouteLightTwo.setImageResource(R.mipmap.ic_light_select);
                tvRouteLightNumTwo.setTextColor(res.getColor(R.color.c_red));
                break;
            case 2:
                tvRouteNameThree.setTextColor(res.getColor(R.color.c_red));
                tvRouteMinThree.setTextColor(res.getColor(R.color.c_red));
                tvRouteKmThree.setTextColor(res.getColor(R.color.c_red));
                tvRouteLightThree.setImageResource(R.mipmap.ic_light_select);
                tvRouteLightNumThree.setTextColor(res.getColor(R.color.c_red));
                break;
        }

    }

    private void initDrawDataEn(int index) {
        List<StepsEnBean> steps = driveEnBean.getData().getResult().getRoutes().get(index).getSteps();
        trafficList = new ArrayList<TrafficBean>();
        trafficList.add(new TrafficBean(17,"起点 ("+startName+")"));
        for (StepsEnBean sb : steps){
            trafficList.add(new TrafficBean(sb.getTurn(),sb.getInstructions()));
        }
        trafficList.add(new TrafficBean(18,"终点 ("+endName+")"));
        adapter = new TrafficAdapter(mContext,trafficList,locationWhere);
        lvDetails.setAdapter(adapter);
    }

    TrafficAdapter adapter = null;
    private void initDrawDataZh(int index) {
        List<StepsZhBean> steps = driveZhBean.getData().getResult().getRoutes().get(index).getSteps();
        trafficList = new ArrayList<TrafficBean>();
        trafficList.add(new TrafficBean(17,"起点 ("+startName+")"));
        for (StepsZhBean sb : steps){
            trafficList.add(new TrafficBean(sb.getTurn(),sb.getInstruction()));
        }
        trafficList.add(new TrafficBean(18,"终点 ("+endName+")"));

        adapter = new TrafficAdapter(mContext,trafficList,locationWhere);
        lvDetails.setAdapter(adapter);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_close:
                addStatisticsEvent("map_route_detail_close",null);

                finish();
                break;
            case R.id.rl_scheme_one:
                if (!CommonUtils.isNetworkAvailable(mContext)){
                    ToastUtil.showShort(mContext,"请检查网络连接");
                    return;
                }

                addStatisticsEvent("map_route_detail_one",null);
                index = 0;
                defaultView(index);
                break;
            case R.id.rl_scheme_two:
                if (!CommonUtils.isNetworkAvailable(mContext)){
                    ToastUtil.showShort(mContext,"请检查网络连接");
                    return;
                }

                addStatisticsEvent("map_route_detail_two",null);
                index = 1;
                defaultView(index);
                break;
            case R.id.rl_scheme_three:
                if (!CommonUtils.isNetworkAvailable(mContext)){
                    ToastUtil.showShort(mContext,"请检查网络连接");
                    return;
                }

                addStatisticsEvent("map_route_detail_three",null);
                index = 2;
                defaultView(index);
                break;
        }
    }

}
