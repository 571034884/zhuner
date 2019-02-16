package com.aibabel.map.base;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ZoomControls;

import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.map.FYTApplication;
import com.aibabel.map.utils.BaiDuConstant;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;

/**
 * Created by fytworks on 2019/1/8.
 *
 *  2.0版本重写BaseActivity
 *  定位放在BaseActivity内部执行 连续定位
 *
 */

public abstract class MapBaseActivity extends BaseActivity{



    @Override
    public int getLayout(Bundle bundle) {
        return getLayoutMap(bundle);
    }

    @Override
    public void init() {
        initMap();

    }

    //布局
    public abstract int getLayoutMap(Bundle var1);
    /**
     * 业务实现
     * mBaiduMap = mMapView.getMap();使用添加，不使用 不添加
     */
    public abstract void initMap();
    //定位回调处理
    public abstract void receiveLocation(BDLocation location);

    @Override
    protected void onStart() {
        super.onStart();
        timer.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //页面被销毁关闭timer
        timer.cancel();
    }
    /**
     * 每1秒获取一次定位信息
     */
    CountDownTimer timer = new CountDownTimer(BaiDuConstant.baseMapMinMuch, BaiDuConstant.baseMapMinLess) {
        @Override
        public void onTick(long millisUntilFinished) {
        }

        @Override
        public void onFinish() {
            BDLocation bdLocation = FYTApplication.getLocation();
            if (bdLocation != null){
                receiveLocation(bdLocation);
            }
            timer.start();
        }
    };

    /**
     * 某些页面不需要 可以关闭循环获取
     */
    public void stopTimer(){
        if (timer != null){
            timer.cancel();
        }
    }
}
