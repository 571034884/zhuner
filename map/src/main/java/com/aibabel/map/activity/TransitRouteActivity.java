package com.aibabel.map.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.baselibrary.base.StatisticsBaseActivity;
import com.aibabel.baselibrary.utils.FastJsonUtil;
import com.aibabel.map.R;
import com.aibabel.map.adapter.TransitDetailsEnAdapter;
import com.aibabel.map.adapter.TransitDetailsZhAdapter;
import com.aibabel.map.adapter.TransitRouteAdapter;
import com.aibabel.map.bean.TransitDetailsBean;
import com.aibabel.map.bean.transiten.TransitResultEnBean;
import com.aibabel.map.bean.transiten.TransitRoutesEnBean;
import com.aibabel.map.bean.transitzh.TransitResultZhBean;
import com.aibabel.map.bean.transitzh.TransitRoutesZhBean;
import com.aibabel.map.route.MyTransitRouteOverlayFytEn;
import com.aibabel.map.route.MyTransitRouteOverlayFytZh;
import com.aibabel.map.route.OverlayManager;
import com.aibabel.map.utils.BaiDuUtil;
import com.aibabel.map.utils.ScreenUtil;
import com.aibabel.map.views.ContentListView;
import com.aibabel.map.views.ScrollLayout;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;

import java.util.List;

import butterknife.BindView;

/**
 * 公交信息详情页
 * Created by fytworks on 2018/12/12.
 */

public class TransitRouteActivity extends BaseActivity{

    @BindView(R.id.rl_parent)
    RelativeLayout rlParent;
    @BindView(R.id.mv_map)
    MapView mapView;
    BaiduMap mBaiduMap;
    OverlayManager routeOverlay = null;

    @BindView(R.id.sl_parent)
    ScrollLayout slParent;
    @BindView(R.id.vp_details)
    ViewPager vpDetails;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.lv_transit)
    ContentListView lvTransit;


    @Override
    public int getLayout(Bundle bundle) {
        return R.layout.activity_transit_route;
    }
    int locationWhere = 0;
    int index = 0;
    String startName,endName;
    TransitResultZhBean zhRoute = null;
    TransitResultEnBean enRoute = null;
    List<TransitDetailsBean> transitHeader = null;
    TransitRouteAdapter adapter = null;

    TransitDetailsZhAdapter zhAdapter = null;
    TransitDetailsEnAdapter enAdapter = null;

    //handler消息机制
    private MyHandler myHandler;
    private class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            Bundle result = msg.getData();
            switch (msg.arg1){
                case 200:
                    int pos = result.getInt("index");
                    showTranistLine(pos);

                    break;
            }
        }
    }


    @Override
    public void init() {
        myHandler = new MyHandler();
        mBaiduMap = mapView.getMap();
        initView();

        locationWhere = getIntent().getIntExtra("locationWhere",0);//国内外类型
        index = getIntent().getIntExtra("index",0);//第几条数据类型
        startName = getIntent().getStringExtra("startName");//起点
        endName = getIntent().getStringExtra("endName");//终点
        switch (locationWhere){
            case 0://国外
                enRoute = (TransitResultEnBean) getIntent().getSerializableExtra("route");
                transitHeader = BaiDuUtil.getTransitHeaderEn(enRoute.getRoutes());
                adapter = new TransitRouteAdapter(mContext,enRoute.getRoutes(),locationWhere,startName,endName,transitHeader);
                enAdapter = new TransitDetailsEnAdapter(mContext,enRoute.getRoutes().get(0),startName,endName);
                lvTransit.setAdapter(enAdapter);
                break;
            case 1://国内
                zhRoute = (TransitResultZhBean) getIntent().getSerializableExtra("route");
                transitHeader = BaiDuUtil.getTransitHeaderZh(zhRoute.getRoutes());
                adapter = new TransitRouteAdapter(mContext,zhRoute.getRoutes(),locationWhere,startName,endName,transitHeader);
                zhAdapter =  new TransitDetailsZhAdapter(mContext,zhRoute.getRoutes().get(0),startName,endName);
                lvTransit.setAdapter(zhAdapter);
                break;
        }
        vpDetails.setAdapter(adapter);

        vpDetails.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.arg1 = 200;
                        Bundle bundle = new Bundle();
                        bundle.putInt("index", position);
                        message.setData(bundle);
                        myHandler.sendMessage(message);
                    }
                }, 500);//500秒后执行Runnable中的run方法

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        //默认
        vpDetails.setCurrentItem(index);
        showTranistLine(index);

    }

    private void showTranistLine(int position) {
        /**####  start-hjs-addStatisticsEvent   ##**/
        try {
           addStatisticsEvent("path_plan_bus5", null);
        }catch (Exception e){
            e.printStackTrace();
        }
        /**####  end-hjs-addStatisticsEvent  ##**/

        mBaiduMap.clear();
        switch (locationWhere){
            case 0:
                enAdapter = new TransitDetailsEnAdapter(mContext,enRoute.getRoutes().get(position),startName,endName);
                lvTransit.setAdapter(enAdapter);

                TransitRoutesEnBean transitEnBean = enRoute.getRoutes().get(position);
                MyTransitRouteOverlayFytEn myTransitRouteOverlayFytEn = new MyTransitRouteOverlayFytEn(mBaiduMap);
                routeOverlay = myTransitRouteOverlayFytEn;
                myTransitRouteOverlayFytEn.setData(transitEnBean,enRoute.getOrigin().getLocation().toString(),enRoute.getDestination().getLocation().toString());
                myTransitRouteOverlayFytEn.addToMap();
                myTransitRouteOverlayFytEn.zoomToSpan();
                break;
            case 1:
                //改变数据
                zhAdapter =  new TransitDetailsZhAdapter(mContext,zhRoute.getRoutes().get(position),startName,endName);
                lvTransit.setAdapter(zhAdapter);

                //画线
                TransitRoutesZhBean transitZhBean = zhRoute.getRoutes().get(position);
                MyTransitRouteOverlayFytZh myTransitRouteOverlayFytZh = new MyTransitRouteOverlayFytZh(mBaiduMap);
                routeOverlay = myTransitRouteOverlayFytZh;
                myTransitRouteOverlayFytZh.setData(transitZhBean,zhRoute.getOrigin().toString(),zhRoute.getDestination().toString());
                myTransitRouteOverlayFytZh.addToMap();
                myTransitRouteOverlayFytZh.zoomToSpan();
                break;
        }
    }

    /**设置 setting*/
    private void initView() {
        slParent.setMinOffset(0);
        slParent.setMaxOffset((int) (ScreenUtil.getScreenHeight(this) * 0.5));
        slParent.setExitOffset(170);
        slParent.setIsSupportExit(true);
        slParent.setAllowHorizontalScroll(true);
        slParent.setOnScrollChangedListener(mOnScrollChangedListener);
        slParent.setToExit();
        slParent.getBackground().setAlpha(0);
        slParent.setOpen();
        rlParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slParent.scrollToExit();
            }
        });


        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitRouteActivity.this.finish();
            }
        });
    }


    //View滑动监听
    private ScrollLayout.OnScrollChangedListener mOnScrollChangedListener = new ScrollLayout.OnScrollChangedListener() {
        @Override
        public void onScrollProgressChanged(float currentProgress) {
            if (currentProgress >= 0) {
                float precent = 255 * currentProgress;
                if (precent > 255) {
                    precent = 255;
                } else if (precent < 0) {
                    precent = 0;
                }
                int i = 255 - (int) precent;
                slParent.getBackground().setAlpha(i);
            }
        }

        @Override
        public void onScrollFinished(ScrollLayout.Status currentStatus) {
            if (currentStatus.equals(ScrollLayout.Status.EXIT)) {
                vpDetails.setVisibility(View.VISIBLE);
            }

            if (currentStatus.equals(ScrollLayout.Status.OPENED)) {
                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    addStatisticsEvent("path_plan_bus7", null);
                }catch (Exception e){
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/
            }else if(currentStatus.equals(ScrollLayout.Status.CLOSED)) {

                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    addStatisticsEvent("path_plan_bus8", null);
                }catch (Exception e){
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/

            }else if(currentStatus.equals(ScrollLayout.Status.EXIT)) {

                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    addStatisticsEvent("path_plan_bus6", null);
                }catch (Exception e){
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/
            }
        }

        @Override
        public void onChildScroll(int top) {
        }
    };


}
