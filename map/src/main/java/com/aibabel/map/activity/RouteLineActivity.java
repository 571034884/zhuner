package com.aibabel.map.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.baselibrary.utils.ToastUtil;
import com.aibabel.map.R;
import com.aibabel.map.base.MapBaseActivity;
import com.aibabel.map.bean.LocationBean;
import com.aibabel.map.bean.RouteBean;
import com.aibabel.map.bean.search.AddressResult;
import com.aibabel.map.fragment.DriveFragment;
import com.aibabel.map.fragment.TransitFragment;
import com.aibabel.map.fragment.WalkFragment;
import com.aibabel.map.utils.ActivityConstant;
import com.aibabel.map.utils.BaiDuUtil;
import com.aibabel.map.utils.CommonUtils;
import com.baidu.location.BDLocation;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 *  路线规划
 * 重构 替换RouteActivity 2019年1月3日13:48:04
 *
 * Created by fytworks on 2018/12/26.
 */

public class RouteLineActivity extends MapBaseActivity {

    @BindView(R.id.tv_start)
    TextView mTextStart;
    @BindView(R.id.tv_end)
    TextView mTextEnd;
    @BindView(R.id.tab_bar)
    TabLayout mTabBar;
    @BindView(R.id.fl_scheme)
    FrameLayout mFrameScheme;
    BDLocation mLocation;
    //具体参数集合体
    private RouteBean routeBean = null;

    private FragmentManager fragmentManager;
    private Fragment fragment;
    @Override
    public int getLayoutMap(Bundle bundle) {
        return R.layout.activity_routeline;
    }

    @Override
    public void initMap() {
        fragmentManager = getSupportFragmentManager();
        //接收参数
        initDataBean();
        //注意坑：监听必须首先写，不然在add之后写 首次进入无效
        initTabListener();
        //初始化tabBar
        initTabBarData();
        //首次加载某个Fragment
        mTabBar.getTabAt(1).select();
    }

    @Override
    public void receiveLocation(BDLocation location) {
        mLocation = location;
    }

    private void initTabListener() {
        mTabBar.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
               int pos = tab.getPosition();
               routeBean.setIndex(pos);
               routeBean.setMode(BaiDuUtil.getModeType(pos));
               switchFragment(pos);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initTabBarData() {
        for(int i = 0; i<ActivityConstant.titles.length; i++){
            mTabBar.addTab(mTabBar.newTab());
        }
        for(int i=0;i<ActivityConstant.titles.length;i++){
            mTabBar.getTabAt(i).setCustomView(makeTabView(i));
        }
    }

    /**
     * 引入布局设置图标和标题
     * @param position 下标
     * @return view
     */
    private View makeTabView(int position){
        View tabView = LayoutInflater.from(this).inflate(R.layout.tab_text_icon,null);
        TextView textView = tabView.findViewById(R.id.textview);
        ImageView imageView = tabView.findViewById(R.id.imageview);
        textView.setText(ActivityConstant.titles[position]);
        imageView.setImageResource(ActivityConstant.pics[position]);
        return tabView;
    }


    private void initDataBean() {
        //TODO 跳转过来的路线规传递实体类。上划，必须一页请判空
        String from = getIntent().getExtras().getString("from");
        if (!TextUtils.isEmpty(from)){
            if (from.equals("food")){
                //美食
                try{
                    String endName = getIntent().getExtras().getString("endName");
                    String[] endLoc = getIntent().getExtras().getString("endLoc").split(",");

                    routeBean = new RouteBean();
                    routeBean.setStartName("我的位置");
                    routeBean.setEndName(endName);
                    routeBean.setEndLoc(new LocationBean(Double.parseDouble(endLoc[0]),Double.parseDouble(endLoc[1])));
                    routeBean.setIndex(1);
                    routeBean.setMode("transit");

                    String latlng = CommonUtils.getLatLng(mContext);
                    if (!TextUtils.isEmpty(latlng)){
                        String[] ll = latlng.split(",");
                        routeBean.setStartLoc(new LocationBean(Double.parseDouble(ll[0]),Double.parseDouble(ll[1])));
                    }else{
                        routeBean.setStartLoc(new LocationBean(0.0,0.0));
                        ToastUtil.showShort(mContext,"准儿出错了");
                        return;
                    }

                    String city = CommonUtils.getCity(mContext);
                    if (!TextUtils.isEmpty(city)){
                        routeBean.setCity(city);
                    }else{
                        routeBean.setCity("日本");
                        ToastUtil.showShort(mContext,"准儿出错了");
                        return;
                    }

                    String coor = CommonUtils.getCoorType(mContext);
                    if (!TextUtils.isEmpty(coor)){
                        routeBean.setCoord_type(coor);
                    }else{
                        routeBean.setCoord_type("wgs84");
                        ToastUtil.showShort(mContext,"准儿出错了");
                        return;
                    }

                    String where = CommonUtils.getLocationWhere(mContext);
                    if (!TextUtils.isEmpty(where)){
                        routeBean.setLocationWhere(Integer.parseInt(where));
                    }else{
                        routeBean.setLocationWhere(0);
                        ToastUtil.showShort(mContext,"准儿出错了");
                        return;
                    }
                }catch (Exception es){
                    ToastUtil.showShort(mContext,es.toString());
                }
                mTextStart.setText(routeBean.getStartName());
                mTextEnd.setText(routeBean.getEndName());
            }else{
                //TODO 其他应用
            }
        }else{
            routeBean = getIntent().getExtras().getParcelable("routes");
            if (routeBean != null){
                mTextStart.setText(routeBean.getStartName());
                mTextEnd.setText(routeBean.getEndName());
            }else{
                ToastUtil.showShort(mContext,"准儿出错了");
                return;
            }
        }

        //TODO 测试
//        initTestJunit(0);

    }

    private void initTestJunit(int pos) {

        switch (pos){
            case 0://日本
                routeBean.setStartName("过桥米线");
                routeBean.setStartLoc(new LocationBean(35.703239,139.770075));
                routeBean.setEndName("芝公园");
                routeBean.setEndLoc(new LocationBean(35.654951,139.747995));
                break;
            case 1://泰国
                routeBean.setStartName("综合医院");
                routeBean.setStartLoc(new LocationBean(13.719442,100.525142));
                routeBean.setEndName("泰国博乐大学");
                routeBean.setEndLoc(new LocationBean(13.739334,100.626408));
                break;
            case 2://美国
                routeBean.setStartName("美国风味餐馆");
                routeBean.setStartLoc(new LocationBean(38.897972,-77.03337));
                routeBean.setEndName("美式牛扒屋");
                routeBean.setEndLoc(new LocationBean(38.882231,-76.995354));
                break;
            case 3://英国
                routeBean.setStartName("伦敦眼");
                routeBean.setStartLoc(new LocationBean(51.50329,-0.119536));
                routeBean.setEndName("卡姆登镇跳蚤市场");
                routeBean.setEndLoc(new LocationBean(51.541515,-0.145726));
                break;
        }


        routeBean.setCoord_type("wgs84");
        routeBean.setLocationWhere(0);

        mTextStart.setText(routeBean.getStartName());
        mTextEnd.setText(routeBean.getEndName());

    }

    private void switchFragment(int pos){
        //传递值
        Bundle bundle = new Bundle();
        bundle.putParcelable("routeBean",routeBean);

        switch (pos){
            case 0://驾车
                fragment = new DriveFragment(bundle);
                switchChange(fragment);
                break;
            case 1://公交
                fragment = new TransitFragment(bundle);
                switchChange(fragment);
                break;
            case 2://步行
                fragment = new DriveFragment(bundle);
                switchChange(fragment);
                break;
        }
    }

    private void switchChange(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_scheme, fragment);
        fragmentTransaction.commit();
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return://退出页面
                this.finish();
                break;
            case R.id.iv_change://切换
                if (!CommonUtils.isNetworkAvailable(this)){
                    ToastUtil.showShort(mContext,"请检查网络连接");
                    return;
                }
                changeStartEnd();
                break;
            case R.id.tv_start://搜索起点

                if (!CommonUtils.isNetworkAvailable(this)){
                    ToastUtil.showShort(mContext,"请检查网络连接");
                    return;
                }
                skipSearchAty(mTextStart.getText().toString(), "0");
                break;
            case R.id.tv_end://搜索终点

                if (!CommonUtils.isNetworkAvailable(this)){
                    ToastUtil.showShort(mContext,"请检查网络连接");
                    return;
                }
                skipSearchAty(mTextEnd.getText().toString(), "1");
                break;
        }
    }

    private void skipSearchAty(String name, String searchType) {
        Intent intent = new Intent(RouteLineActivity.this, SearchActivity.class);
        intent.putExtra("value", name);
        intent.putExtra("type", searchType);
        intent.putExtra("city",routeBean.getCity());
        intent.putExtra("coorType", routeBean.getCoord_type());
        intent.putExtra("locationWhere", routeBean.getLocationWhere());
        startActivityForResult(intent, ActivityConstant.REQUSET_OK);
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);

    }


    private void changeStartEnd() {
        String start = routeBean.getStartName();
        String end = routeBean.getEndName();
        routeBean.setStartName(end);
        routeBean.setEndName(start);
        LocationBean startLB = routeBean.getStartLoc();
        LocationBean endLB = routeBean.getEndLoc();
        routeBean.setStartLoc(endLB);
        routeBean.setEndLoc(startLB);
        mTextStart.setText(routeBean.getStartName());
        mTextEnd.setText(routeBean.getEndName());
        changeDataFragment();
    }

    private void changeDataFragment(){
        if (routeBean.getMode().equals("driving")){//驾车
            ((DriveFragment)fragment).changeFramgnet(routeBean);
        }else if (routeBean.getMode().equals("transit")){//公交
            ((TransitFragment)fragment).changeFramgnet(routeBean);
        } else if (routeBean.getMode().equals("walking")){//步行
            ((DriveFragment)fragment).changeFramgnet(routeBean);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActivityConstant.REQUSET_OK && resultCode == ActivityConstant.RESULT_OK) {
            String type = data.getExtras().getString("type");
            AddressResult address = data.getExtras().getParcelable("address");
            if (type.equals("0")) {
                routeBean.setStartName(address.getName());
                routeBean.setStartLoc(new LocationBean(address.getLocation().getLat(), address.getLocation().getLng()));
                mTextStart.setText(routeBean.getStartName());
            } else if (type.equals("1")) {
                routeBean.setEndName(address.getName());
                routeBean.setEndLoc(new LocationBean(address.getLocation().getLat(), address.getLocation().getLng()));
                mTextEnd.setText(routeBean.getEndName());
            }

            /**####  start-hjs-addStatisticsEvent   ##**/
            try {
                HashMap<String, Serializable> add_hp = new HashMap<>();
                add_hp.put("map_search_letter11",routeBean.getStartName()+routeBean.getEndName() );
                addStatisticsEvent("path_plan_car1", add_hp);
            }catch (Exception e){
                e.printStackTrace();
            }
            /**####  end-hjs-addStatisticsEvent  ##**/

            changeDataFragment();
        }
    }
}
