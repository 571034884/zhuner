package com.aibabel.map.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.baselibrary.base.BaseFragment;
import com.aibabel.baselibrary.base.StatisticsBaseActivity;
import com.aibabel.baselibrary.http.BaseBean;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.baselibrary.utils.FastJsonUtil;
import com.aibabel.baselibrary.utils.ToastUtil;
import com.aibabel.map.R;
import com.aibabel.map.activity.TransitRouteActivity;
import com.aibabel.map.adapter.TransitAdapter;
import com.aibabel.map.bean.RouteBean;
import com.aibabel.map.bean.transiten.TransitEnBean;
import com.aibabel.map.bean.transiten.TransitResultEnBean;
import com.aibabel.map.bean.transitzh.TransitResultZhBean;
import com.aibabel.map.bean.transitzh.TransitZhBean;
import com.aibabel.map.utils.BaiDuConstant;
import com.aibabel.map.utils.BaiDuStatusUtil;
import com.aibabel.map.utils.BaiDuUtil;
import com.aibabel.map.utils.CommonUtils;
import com.aibabel.map.utils.TimerDataUtil;
import com.aibabel.map.wheel.WheelView;
import com.aibabel.map.wheel.adapter.ArrayWheelAdapter;
import com.aibabel.map.wheel.listener.OnItemSelectedListener;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by fytworks on 2018/12/26.
 */

@SuppressLint("ValidFragment")
public class TransitFragment extends BaseFragment implements View.OnClickListener {

    private Context mContext;
    RouteBean routeBean;

    @BindView(R.id.tv_timer_new)
    TextView tvTimerNew;
    @BindView(R.id.iv_inout)
    ImageView ivInOut;
    @BindView(R.id.lv_transit)
    ListView lvTransit;
    @BindView(R.id.ll_error)
    LinearLayout llError;
    @BindView(R.id.route_view)
    View routeView;
    @BindView(R.id.rl_timer)
    LinearLayout mRlTimer;
    @BindView(R.id.tv_error)
    TextView mTvError;

    //时间选择器
    PopupWindow popWnd = null;
    WheelView month;
    WheelView hours;
    WheelView min;
    private List<String> monthListValues;
    private List<String> monthList;
    private List<String> hoursList;
    private List<String> minList;
    int indexMonth = 0;
    int indexHours = 0;
    int indexMin = 0;
    String startMonth = "";
    String startTimer = "";


    TransitZhBean tZhBean = null;
    TransitEnBean tEnBean = null;
    TransitAdapter transitAdapter = null;

    public TransitFragment(){}

    @SuppressLint("ValidFragment")
    public TransitFragment(Bundle bundle) {
        routeBean = bundle.getParcelable("routeBean");
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_transit;
    }

    @Override
    public void init(View view, Bundle bundle) {
        mContext = getContext();
        //默认GONE ERROR
        llError.setVisibility(View.GONE);

        //时间选择器获取当前之后15天的数据
        initDataTimer();
        //时间选择器
        isShowHideView();
        //获取数据
        obtainApi();
        //监听
        initListener();
    }

    private void initListener() {
        mRlTimer.setOnClickListener(this);
        lvTransit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!CommonUtils.isNetworkAvailable(mContext)){
                    ToastUtil.showShort(mContext,"请检查网络连接");
                    return;
                }

                Map map = new HashMap();
                map.put("type",routeBean.getMode());
                StatisticsManager.getInstance(mContext).addEventAidl(1211,map);

                Intent intent = new Intent(mContext, TransitRouteActivity.class);
                switch (routeBean.getLocationWhere()) {
                    case 1://国内
                        //JSON
                        TransitResultZhBean zhRoute = tZhBean.getData().getResult();
                        intent.putExtra("route", zhRoute);
                        break;
                    case 0://国外
                        TransitResultEnBean enRoute = tEnBean.getData().getResult();
                        intent.putExtra("route", enRoute);
                        break;
                }

                intent.putExtra("locationWhere", routeBean.getLocationWhere());
                intent.putExtra("index", position);
                intent.putExtra("startName", routeBean.getStartName());
                intent.putExtra("endName", routeBean.getEndName());
                startActivity(intent);
            }
        });
    }


    private void isShowHideView() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_popuwindow, null);
        popWnd = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popWnd.setContentView(contentView);
        popWnd.setAnimationStyle(R.style.TopPopAnim);
        popWnd.setOutsideTouchable(false);
        popWnd.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ivInOut.setImageResource(R.mipmap.ic_open_popu);
                Animation animation = new AlphaAnimation(1.0f, 0.0f);
                animation.setDuration(500);
                routeView.setAnimation(animation);
                routeView.setVisibility(View.GONE);
            }
        });

        month = contentView.findViewById(R.id.options1);
        hours = contentView.findViewById(R.id.options2);
        min = contentView.findViewById(R.id.options3);
        TextView tvOk = contentView.findViewById(R.id.tv_ok);
        //适配
        month.setAdapter(new ArrayWheelAdapter(monthList));
        hours.setAdapter(new ArrayWheelAdapter(hoursList));
        min.setAdapter(new ArrayWheelAdapter(minList));

        //当前选中
        month.setCurrentItem(indexMonth);
        hours.setCurrentItem(indexHours);
        min.setCurrentItem(indexMin);

        month.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                //当前日期和之后的日期 不会有之前的日期所以直接获取角标
                if (monthList.get(index).equals("今天")) {
                    indexMonth = TimerDataUtil.getIndexMonth(monthList, "今天");
                    indexHours = TimerDataUtil.getIndexHours(hoursList, TimerDataUtil.getCurrentHours());
                    indexMin = TimerDataUtil.getIndexMin(minList, TimerDataUtil.getCurrentMin());
                    month.setCurrentItem(indexMonth);
                    hours.setCurrentItem(indexHours);
                    min.setCurrentItem(indexMin);
                } else {
                    indexMonth = index;
                }
            }
        });
        hours.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                /**
                 * 用户滑动小时
                 *
                 * 首先判断日期
                 *  今天
                 *      以当前时间为轴
                 *          > 当前时间轴    正常(当前小时 之后的小时)
                 *              返回用户滑动小时 index
                 *          < 当前时间轴    异常(当前小时 之前的小时)
                 *              返回到当前时间轴 index
                 *  以后
                 *      无所谓
                 */
                int type = TimerDataUtil.getIndexHoursType(hoursList, monthList.get(indexMonth), hoursList.get(index));
                indexHours = type;
                hours.setCurrentItem(indexHours);

            }
        });
        min.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                //同小时一样
                int type = TimerDataUtil.getIndexMinType(minList, minList.get(index), monthList.get(indexMonth));
                indexMin = type;
                min.setCurrentItem(indexMin);
            }
        });

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWnd.dismiss();
                if (monthList.get(indexMonth).equals("今天")) {

                    /**
                     * 用户选择小时 >= 当前小时
                     *      return 用户选择小时
                     * 用户选择小时 <  当前小时
                     *      return 当前小时
                     */
                    indexHours = TimerDataUtil.getHoursConFirm(hoursList, hoursList.get(indexHours));

                    /**
                     * 用户选择分钟 >= 当前分钟
                     *      return 用户选择分钟
                     * 用户选择分钟 <  当前分钟
                     *      return 当前分钟
                     */
                    indexMin = TimerDataUtil.getMinConFirm(minList, minList.get(indexMin));

                    //设置到当前
                    month.setCurrentItem(indexMonth);
                    hours.setCurrentItem(indexHours);
                    min.setCurrentItem(indexMin);

                }
                tvTimerNew.setText(monthList.get(indexMonth) + " " + hoursList.get(indexHours) + ":" + minList.get(indexMin) + " 出发");

                //TODO 逻辑处理 请求API
                startMonth = monthListValues.get(indexMonth);
                startTimer = hoursList.get(indexHours) + ":" + minList.get(indexMin);
                //API请求数据
                obtainApi();
            }
        });
    }

    private void initDataTimer() {
        monthListValues = TimerDataUtil.getMonthValues(15);
        monthList = TimerDataUtil.getMonth(15);
        hoursList = TimerDataUtil.getHours();
        minList = TimerDataUtil.getMin();
        startMonth = TimerDataUtil.getStartMonth();
        startTimer = TimerDataUtil.getStartTimer();
        ////获取角标以及设置当前时间
        getDataTimer();
    }

    private void getDataTimer() {
        indexMonth = TimerDataUtil.getIndexMonth(monthList, "今天");
        indexHours = TimerDataUtil.getIndexHours(hoursList, TimerDataUtil.getCurrentHours());
        indexMin = TimerDataUtil.getIndexMin(minList, TimerDataUtil.getCurrentMin());
        tvTimerNew.setText(monthList.get(indexMonth) + " " + hoursList.get(indexHours) + ":" + minList.get(indexMin) + " 出发");
    }

    /**
     * 暴露方法 提供给Activity调用
     * @param routes
     */
    public void changeFramgnet(RouteBean routes){
        routeBean = routes;
        obtainApi();
    }

    private void obtainApi() {

        Map map = new HashMap();
        map.put("startLatLon",routeBean.getStartLoc().getLat()+","+routeBean.getStartLoc().getLng());
        map.put("startName",routeBean.getStartName());
        map.put("endLatLon",routeBean.getEndLoc().getLat()+","+routeBean.getEndLoc().getLng());
        map.put("endName",routeBean.getEndName());
        map.put("type",routeBean.getMode());
        StatisticsManager.getInstance(mContext).addEventAidl(1210,map);

        String url = BaiDuUtil.getUrl(routeBean.getLocationWhere(), routeBean.getIndex());
        Map param = new HashMap();
        param.put("origin", routeBean.getStartLoc().getLat() + "," + routeBean.getStartLoc().getLng());
        param.put("destination", routeBean.getEndLoc().getLat() + "," + routeBean.getEndLoc().getLng());
        param.put("mode", routeBean.getMode());
        param.put("coord_type", routeBean.getCoord_type());
        param.put("locationWhere", routeBean.getLocationWhere() + "");
        param.put("departure_date", startMonth + "");
        param.put("departure_time", startTimer + "");
        OkGoUtil.get(mContext, url, param, BaseBean.class, new BaseCallback() {
            @Override
            public void onSuccess(String s, BaseBean baseBean, String s1) {
                transitType(s1);
            }

            @Override
            public void onError(String s, String s1, String s2) {
                try{
                    llError.setVisibility(View.VISIBLE);
                    lvTransit.setVisibility(View.GONE);
                    iconShow(505050);
                }catch (Exception e){
                    Log.e("TransitFragment",e.toString());
                    ToastUtil.showShort(mContext,"准儿出错了");
                }

            }

            @Override
            public void onFinsh(String s) {
            }
        });
    }

    private void transitType(String json) {
        switch (routeBean.getLocationWhere()) {

            case 0://国外
                tEnBean = FastJsonUtil.changeJsonToBean(json, TransitEnBean.class);
                if (tEnBean.getData() != null) {
                    if (tEnBean.getData().getStatus() == 0) {
                        llError.setVisibility(View.GONE);
                        lvTransit.setVisibility(View.VISIBLE);
                        transitAdapter = new TransitAdapter(mContext, tEnBean.getData().getResult().getRoutes(), 1);
                        lvTransit.setAdapter(transitAdapter);
                    } else {
                        llError.setVisibility(View.VISIBLE);
                        lvTransit.setVisibility(View.GONE);
                        iconShow(tZhBean.getData().getStatus());
                    }
                } else {
                    llError.setVisibility(View.VISIBLE);
                    lvTransit.setVisibility(View.GONE);
                    iconShow(101010);
                }

                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    HashMap<String, Serializable> add_hp = new HashMap<>();
                    add_hp.put("map_search_letter12", routeBean.getLocationWhere());
                    ((StatisticsBaseActivity)getActivity()).addStatisticsEvent("path_plan_bus1", add_hp);
                }catch (Exception e){
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/
                break;
            case 1://国内
                tZhBean = FastJsonUtil.changeJsonToBean(json, TransitZhBean.class);
                if (tZhBean.getData() != null) {
                    if (tZhBean.getData().getStatus() == 0) {
                        llError.setVisibility(View.GONE);
                        lvTransit.setVisibility(View.VISIBLE);
                        transitAdapter = new TransitAdapter(mContext, tZhBean.getData().getResult().getRoutes(), 0);
                        lvTransit.setAdapter(transitAdapter);
                    } else {
                        llError.setVisibility(View.VISIBLE);
                        lvTransit.setVisibility(View.GONE);
                        iconShow(tZhBean.getData().getStatus());
                    }
                } else {
                    llError.setVisibility(View.VISIBLE);
                    lvTransit.setVisibility(View.GONE);
                    iconShow(101010);
                }

                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    HashMap<String, Serializable> add_hp = new HashMap<>();
                    add_hp.put("map_search_letter12", routeBean.getLocationWhere());
                    ((StatisticsBaseActivity)getActivity()).addStatisticsEvent("path_plan_bus1", add_hp);
                }catch (Exception e){
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_timer:
                if (!popWnd.isShowing()) {
                    ivInOut.setImageResource(R.mipmap.ic_close_popu);
                    popWnd.showAsDropDown(v);
                    Animation animation = new AlphaAnimation(0.0f, 1.0f);
                    animation.setDuration(500);
                    routeView.setVisibility(View.VISIBLE);
                    routeView.setAnimation(animation);
                }
                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    ((StatisticsBaseActivity)getActivity()).addStatisticsEvent("path_plan_bus2", null);
                }catch (Exception e){
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/
                break;
        }
    }


    /**
     * 具体的 体现
     * @param index
     */
    private void iconShow(int index){
        switch (index){
            case 0:
                //成功，不会出现，
                break;
            case 1:
            case 505050:
                mTvError.setText("准儿出错了");
                break;
            case 2:
                mTvError.setText("准儿出错了");
                break;
            case 7:
            case 1001://路程过短
            case 1002:
            case 2001:
            case 101010:
                mTvError.setText("准儿没找到信息");
                break;
            case 3001:
                mTvError.setText("暂不支持该出行方式");
                break;
        }
    }
}
