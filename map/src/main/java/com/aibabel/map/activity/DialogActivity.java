package com.aibabel.map.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.baselibrary.base.BaseApplication;
import com.aibabel.baselibrary.base.StatisticsBaseActivity;
import com.aibabel.baselibrary.imageloader.ImageLoader;
import com.aibabel.baselibrary.utils.ToastUtil;
import com.aibabel.map.R;
import com.aibabel.map.bean.BusinessBean;
import com.aibabel.map.bean.LocationBean;
import com.aibabel.map.bean.RouteBean;
import com.aibabel.map.utils.BaiDuConstant;
import com.aibabel.map.utils.BaiDuUtil;
import com.aibabel.map.utils.CommonUtils;
import com.aibabel.map.utils.StringUtils;
import com.aibabel.map.views.AutoNextLineLinearlayout;
import com.aibabel.map.views.CornerTransform;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.okgo.OkGo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.umeng.commonsdk.stateless.UMSLEnvelopeBuild.mContext;

public class DialogActivity extends StatisticsBaseActivity implements View.OnClickListener {


    @BindView(R.id.anl_label)
    AutoNextLineLinearlayout anlLabel;
    @BindView(R.id.iv_label)
    ImageView ivLabel;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_ch)
    TextView tvCh;
    @BindView(R.id.tv_local)
    TextView tvLocal;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_open)
    TextView tvOpen;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_more)
    TextView tvMore;
    @BindView(R.id.iv_play)
    ImageView ivPlay;
    @BindView(R.id.iv_desc)
    ImageView ivDesc;
    @BindView(R.id.tv_go)
    TextView tvGo;

    private List<String> list = new ArrayList<>();
    private BusinessBean.DataBean data;
    private String url = "";
    private String nameCh = "";
    private String nameLocal = "";
    private String text = "";
    private String address = "";
    private String open = "";
    private int tourguideId;
    private int destinationId;
    private int cateId;
    private String recommend = "";
    private String audioUrl = "";
    private ComponentName componentName;
    private double lat;
    private double lng;
    private double mCurrentLat;
    private double mCurrentLon;
    private int locationWhere;
    private String coord_type;
    private String city;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        Window window = this.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        ButterKnife.bind(this);
        init();

    }


    public void init() {
        mCurrentLat = getIntent().getDoubleExtra("mCurrentLat", 0.00);
        mCurrentLon = getIntent().getDoubleExtra("mCurrentLon", 0.00);
        locationWhere = getIntent().getIntExtra("locationWhere", 0);
        coord_type = getIntent().getStringExtra("coord_type");
        city = getIntent().getStringExtra("city");
        data = (BusinessBean.DataBean) getIntent().getSerializableExtra("data");
        setContentToView();
    }


    /**
     * 赋值到页面上
     */
    private void setContentToView() {
        if (null != data) {
            list = StringUtils.spliteByRegex(data.getTag(), ";");
            nameLocal = data.getLocalName();
            text = data.getDesc();
            open = data.getOpenTime();
            nameCh = data.getNameCh();
            address = data.getAddress();
            recommend = data.getRecommend();
            tourguideId = data.getTourguideId();
            destinationId = data.getDetinationId();
            cateId = data.getCateId();
            audioUrl = data.getAudioUrl();
            url = data.getImage() + "";
            lat = data.getLat();
            lng = data.getLng();
        } else {
            ToastUtil.showShort(this, "资源加载失败请重新尝试！");
//            this.finish();
            return;
        }


        //判定是否显示推荐标签
        if (!TextUtils.isEmpty(recommend) && TextUtils.equals("1", recommend)) {
            ivLabel.setVisibility(View.VISIBLE);
        }

        //是否显示播放按钮
        if (tourguideId != 0) {
            ivPlay.setVisibility(View.VISIBLE);
        }

        //是否显示更多
//        if(!TextUtils.isEmpty(detinationId)){
//            tvMore.setVisibility(View.VISIBLE);
//        }

        for (int i = 0; i < list.size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.label_item, null);
            TextView tv = view.findViewById(R.id.tv_item_label);
            tv.setText(list.get(i));
            anlLabel.addView(view);
        }


//        tvAddress.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvAddress.getPaint().setAntiAlias(true);//抗锯齿

        CornerTransform transformation = new CornerTransform(this, 10);
        //只是绘制左上角和右上角圆角
        transformation.setExceptCorner(false, false, true, true);
        RequestOptions options = new RequestOptions().transform(transformation).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher);

        if (TextUtils.equals(data.getTagType(), "scenic")) {//景区
            ImageLoader.getInstance().load(data.getImage()).placeholder(R.mipmap.icon_scenic_default_img_small).error(R.mipmap.icon_scenic_img_small).into(ivDesc);
        } else if (TextUtils.equals(data.getTagType(), "cate")) {//美食
            ImageLoader.getInstance().load(data.getImage()).placeholder(R.mipmap.icon_cate_default_img_small).error(R.mipmap.icon_cate_img_small).into(ivDesc);
        } else if (TextUtils.equals(data.getTagType(), "shop")) {//购物
            ImageLoader.getInstance().load(data.getImage()).placeholder(R.mipmap.icon_shop_default_img_small).error(R.mipmap.icon_shop_img_small).into(ivDesc);
        }
        StringUtils.setText(tvLocal, nameLocal);
        StringUtils.setText(tvContent, text);
        StringUtils.setText(tvOpen, open);
        StringUtils.setText(tvAddress, address);


        tvCh.setText(nameCh + "");
        ivClose.setOnClickListener(this);
        ivPlay.setOnClickListener(this);
        tvMore.setOnClickListener(this);
        setGone();
    }


    @Override
    public void onClick(View v) {

        Map map = new HashMap();
        switch (v.getId()) {
            case R.id.iv_close:
                StatisticsManager.getInstance(this).addEventAidl(1215, map);

                finish();
                //加动画避免闪屏
                overridePendingTransition(R.anim.dialog_enter, R.anim.dialog_exit);
                break;
            case R.id.iv_play:

                if (!CommonUtils.isNetworkAvailable(this)){
                    ToastUtil.showShort(mContext,"请检查网络连接");
                    return;
                }
                play();
                break;
            case R.id.tv_more:

                if (!CommonUtils.isNetworkAvailable(this)){
                    ToastUtil.showShort(mContext,"请检查网络连接");
                    return;
                }
                map.put("name", data.getNameCh());
                map.put("addr", data.getAddress());
                map.put("latlon", lat + "," + lng);
                StatisticsManager.getInstance(this).addEventAidl(1214, map);
                toMore();
                break;
            case R.id.tv_address:
            case R.id.tv_go:
                try{
                    if (!CommonUtils.isNetworkAvailable(this)){
                        ToastUtil.showShort(this,"请检查网络连接");
                        return;
                    }
                }catch (Exception e){
                }

                toRoute();
                break;
        }
    }


    /**
     * 跳转到路线规划中
     */
    private void toRoute() {
        //TODO 跳转到路线规划中
        Intent intent = new Intent(this, RouteLineActivity.class);
        RouteBean bean = new RouteBean();
        /**
         * DRIVING_MODE 驾车
         * TRANSIT_MODE 公交
         * WALKING_MODE 步行
         */
        bean.setIndex(BaiDuConstant.TRANSIT_MODE);
        bean.setStartName("我的位置");//我的位置
        bean.setStartLoc(new LocationBean(mCurrentLat, mCurrentLon));
        bean.setEndName(TextUtils.isEmpty(nameCh) ? "目的地" : nameCh);
        bean.setCity(city);
        bean.setEndLoc(new LocationBean(lat, lng));
        bean.setMode(BaiDuUtil.getModeType(BaiDuConstant.TRANSIT_MODE));
        bean.setCoord_type(coord_type);
        bean.setLocationWhere(locationWhere);


        Map map = new HashMap();
        map.put("startLatLon", bean.getStartLoc().getLat() + "," + bean.getStartLoc().getLng());
        map.put("startName", bean.getStartName());
        map.put("endLatLon", bean.getEndLoc().getLat() + "," + bean.getEndLoc().getLng());
        map.put("endName", bean.getEndName());
        map.put("type", bean.getMode());
        StatisticsManager.getInstance(this).addEventAidl(1216, map);

        /**####  start-hjs-addStatisticsEvent   ##**/
        try {
            HashMap<String, Serializable> add_hp = new HashMap<>();
            add_hp.put("map_search_letter3", ""+bean.getStartName()+bean.getEndName());
            addStatisticsEvent("map_poi7", add_hp);
        }catch (Exception e){
            e.printStackTrace();
        }
        /**####  end-hjs-addStatisticsEvent  ##**/

        intent.putExtra("routes", bean);
        startActivity(intent);
        this.finish();
    }


    /**
     * 跳转到景区导览
     */
    private void play() {

        if (!CommonUtils.isFastClick()) return;
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName("com.aibabel.travel", "com.aibabel.travel.activity.SpotActivity");
        intent.setComponent(componentName);
        intent.putExtra("id", tourguideId + "");
        intent.putExtra("from", "map");
        intent.putExtra("url", url);
        intent.putExtra("name", nameCh);
        intent.putExtra("audioUrl", audioUrl);
        startActivity(intent);
        this.finish();
    }


    /**
     * 设置隐藏
     */
    private void setGone() {
        switch (data.getTagType()) {
            case "cate":
                if (cateId == 0) {
                    tvMore.setVisibility(View.GONE);
                }
                break;
            case "scenic":
                if (destinationId == 0) {
                    tvMore.setVisibility(View.GONE);
                }
                break;
            case "shop":

                break;
        }


    }


    /**
     * 跳转到对应应用的页面
     */
    private void toMore() {
        switch (data.getTagType()) {
            case "cate":

                if (!CommonUtils.isNetworkAvailable(this)){
                    ToastUtil.showShort(this,"请检查网络连接");
                    return;
                }
                toCate();
                break;
            case "scenic":

                if (!CommonUtils.isNetworkAvailable(this)){
                    ToastUtil.showShort(this,"请检查网络连接");
                    return;
                }
                toDestination();
                break;
            case "shop":

                if (!CommonUtils.isNetworkAvailable(this)){
                    ToastUtil.showShort(this,"请检查网络连接");
                    return;
                }
                toShop();
                break;
        }


    }


    /**
     * 跳转到目的地
     */
    private void toDestination() {

        if (!CommonUtils.isFastClick()) return;
        if (0 != destinationId) {
            Intent intent = new Intent(this, CityDetailsActivity.class);
            intent.putExtra("poiId", destinationId + "");
            RouteBean bean = new RouteBean();
            /**
             * DRIVING_MODE 驾车
             * TRANSIT_MODE 公交
             * WALKING_MODE 步行
             */
            bean.setIndex(BaiDuConstant.TRANSIT_MODE);
            bean.setStartName("我的位置");//我的位置
            bean.setStartLoc(new LocationBean(mCurrentLat, mCurrentLon));
            bean.setEndName(TextUtils.isEmpty(nameCh) ? "目的地" : nameCh);
            bean.setCity(city);
            bean.setEndLoc(new LocationBean(lat, lng));
            bean.setMode(BaiDuUtil.getModeType(BaiDuConstant.TRANSIT_MODE));
            bean.setCoord_type(coord_type);
            bean.setLocationWhere(locationWhere);
            intent.putExtra("routes", bean);
            startActivity(intent);

            /**####  start-hjs-addStatisticsEvent   ##**/
            try {
                HashMap<String, Serializable> add_hp = new HashMap<>();
                add_hp.put("map_search_letter5", locationWhere + "");
                addStatisticsEvent("map_poi9", add_hp);
            }catch (Exception e){
                e.printStackTrace();
            }
            /**####  end-hjs-addStatisticsEvent  ##**/
            this.finish();
        }

    }


    /**
     * 跳转到美食
     */
    private void toCate() {

        if (!CommonUtils.isFastClick()) return;
        if (0 != cateId) {
            Intent intent = new Intent();
            componentName = new ComponentName("com.aibabel.food", "com.aibabel.food.activity.DetailActivity");
            intent.setComponent(componentName);
            intent.putExtra("id", destinationId + "");
            intent.putExtra("from", "map");
            startActivity(intent);

            /**####  start-hjs-addStatisticsEvent   ##**/
            try {
                HashMap<String, Serializable> add_hp = new HashMap<>();
                add_hp.put("map_search_letter6", destinationId + "");
                addStatisticsEvent("map_poi10", add_hp);
            }catch (Exception e){
                e.printStackTrace();
            }
            /**####  end-hjs-addStatisticsEvent  ##**/

            this.finish();
        }

    }

    /**
     * 跳转到优惠券
     */
    private void toShop() {

//        if (0 != destinationId) {

        if (!CommonUtils.isFastClick()) return;

        Intent intent = getPackageManager().getLaunchIntentForPackage("com.aibabel.coupon");
        intent.putExtra("from", "map");
        startActivity(intent);

        /**####  start-hjs-addStatisticsEvent   ##**/
        try {
            HashMap<String, Serializable> add_hp = new HashMap<>();
            add_hp.put("map_search_letter4", "map");
            addStatisticsEvent("map_poi8", add_hp);
        }catch (Exception e){
            e.printStackTrace();
        }
        /**####  end-hjs-addStatisticsEvent  ##**/

        this.finish();
//        }

    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case 133:
            case 134:
                BaseApplication.exit();
        }
        return super.onKeyDown(keyCode, event);
    }


}
