package com.aibabel.weather;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aibabel.baselibrary.base.StatisticsBaseActivity;
import com.aibabel.weather.adapter.CommomRecyclerAdapter;
import com.aibabel.weather.adapter.CommonRecyclerViewHolder;
import com.aibabel.weather.app.BaseActivity;
import com.aibabel.weather.bean.WeatherBean;
import com.aibabel.weather.utils.WeatherIconUtil;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：SunSH on 2018/5/25 11:11
 * 功能：
 * 版本：1.0
 */
public class WeatherFragment extends Fragment {

    String TAG = "WeatherFragment";

    @BindView(R.id.rv_weather_for_hour)
    RecyclerView rvWeatherForHour;
    Unbinder unbinder;

    List<WeatherBean> list = new ArrayList<>();
    WeatherBean weatherBean;
    @BindView(R.id.tv_day1)
    TextView tvDay1;
    @BindView(R.id.iv_weather1)
    ImageView ivWeather1;
    @BindView(R.id.tv_max_min1)
    TextView tvMaxMin1;
    @BindView(R.id.tv_day2)
    TextView tvDay2;
    @BindView(R.id.iv_weather2)
    ImageView ivWeather2;
    @BindView(R.id.tv_max_min2)
    TextView tvMaxMin2;
    @BindView(R.id.tv_day3)
    TextView tvDay3;
    @BindView(R.id.iv_weather3)
    ImageView ivWeather3;
    @BindView(R.id.tv_max_min3)
    TextView tvMaxMin3;
    @BindView(R.id.tv_day4)
    TextView tvDay4;
    @BindView(R.id.iv_weather4)
    ImageView ivWeather4;
    @BindView(R.id.tv_max_min4)
    TextView tvMaxMin4;
    @BindView(R.id.ll_weather)
    LinearLayout llWeather;
    @BindView(R.id.v_line2)
    View vLine2;
    @BindView(R.id.v_line1)
    View vLine1;
    @BindView(R.id.ll_kongqizhiling)
    LinearLayout llKongqizhiling;
    @BindView(R.id.tv_wendu)
    TextView tvWendu;
    @BindView(R.id.tv_shijian_yangli)
    TextView tvShijianYangli;
    @BindView(R.id.tv_shijian_xingqi)
    TextView tvShijianXingqi;
    @BindView(R.id.tv_shijian_yinli)
    TextView tvShijianYinli;
    @BindView(R.id.ll_riqi)
    LinearLayout llRiqi;
    @BindView(R.id.tv_shidu)
    TextView tvShidu;
    @BindView(R.id.tv_jiangshuiliang)
    TextView tvJiangshuiliang;
    @BindView(R.id.tv_fengsu)
    TextView tvFengsu;
    @BindView(R.id.cl_root)
    ConstraintLayout clRoot;


    @SuppressLint("ValidFragment")
    public WeatherFragment(WeatherBean weatherBean) {
        this.weatherBean = weatherBean;
    }

    public WeatherFragment() {
//        this.weatherBean = (WeatherBean) getArguments().gel("bean");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_for_weather_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        this.weatherBean = (WeatherBean) getArguments().getSerializable("bean");

//        for (int i = 0; i < 7; i++) {
//            list.add("i");
//        }
        initView();


        return view;
    }

    public void initView() {

        Log.e(TAG, "initView: " + weatherBean);

        WeatherBean.WeatherHoursDataBean bean = new WeatherBean.WeatherHoursDataBean();
        bean.setFCTTIME("0");
        bean.setIcon(weatherBean.getWeatherNowData().getIcon());
        bean.setTemp(weatherBean.getWeatherNowData().getTemperature_string());
        tvWendu.setText(weatherBean.getWeatherNowData().getTemperature_string());//截取操作都转移到bean对象中处理
        try {
            DateFormat df_from = new SimpleDateFormat("EEE,dd MMM yyyy HH:mm:ss Z", Locale.US);
            Date date = df_from.parse(weatherBean.getWeatherNowData().getLocal_time_rfc822());
            DateFormat df_to = new SimpleDateFormat("MM-dd HH:mm:ss");
            clRoot.setBackgroundResource(WeatherIconUtil.getIconBg(weatherBean.getWeatherNowData().getIcon(), df_to.format(date).split(" ")[1].split(":")[0]));
            df_to = DateFormat.getDateInstance(DateFormat.FULL);
            String dateTemp = df_to.format(date);
            String year = dateTemp.split("年")[0];
            String month = dateTemp.split("年")[1].split("月")[0];
            String day = dateTemp.split("年")[1].split("月")[1].split("日")[0];
            String[] strings = dateTemp.substring(5, dateTemp.length()).split("星");
//            tvShijianYangli.setText(strings[0]);
//            tvShijianXingqi.setText(strings[1].replace("期", "周"));
//            tvShijianYinli.setText(DateUtils.getLunarMonth(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(day)) + DateUtils.getLunarDay(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(day)));
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        tvShidu.setText(weatherBean.getWeatherNowData().getRelative_humidity());
        String jianshuilian = weatherBean.getWeatherNowData().getPrecip_today_in();
        if (TextUtils.equals(jianshuilian, "")) {
            tvJiangshuiliang.setText("--");

        } else {
            tvJiangshuiliang.setText(Double.valueOf(jianshuilian)+"mm");
        }

        tvFengsu.setText(weatherBean.getWeatherNowData().getWind_degrees() + "");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //设置布局管理器
        rvWeatherForHour.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        //设置Adapter
        rvWeatherForHour.setAdapter(new CommomRecyclerAdapter(getActivity(), bean, weatherBean.getWeatherHoursData(), R.layout.item_layout_for_weather_of_hour, null, null) {
            @Override
            public void convert(CommonRecyclerViewHolder holder, Object o, int position) {

                TextView tv_hour = holder.getView(R.id.tv_hour);
                ImageView iv_tianqi = holder.getView(R.id.iv_tianqi);
                TextView tv_wendu = holder.getView(R.id.tv_wendu);
//                tv_hour.setText(((WeatherBean.WeatherHoursDataBean) o).getFCTTIME() + "时");
                iv_tianqi.setImageResource(WeatherIconUtil.getIcon(((WeatherBean.WeatherHoursDataBean) o).getIcon(), ((WeatherBean.WeatherHoursDataBean) o).getFCTTIME()));
                tv_wendu.setText(((WeatherBean.WeatherHoursDataBean) o).getTemp() + "°");

                if (position == 0) {
                    tv_hour.setText(getResources().getString(R.string.xianzai));
                    tv_hour.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    tv_wendu.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                } else {
                    tv_hour.setText(((WeatherBean.WeatherHoursDataBean) o).getFCTTIME() + getResources().getString(R.string.shi));
                    tv_hour.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    tv_wendu.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                }
            }
        });
        rvWeatherForHour.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e("scrollState==",""+newState);
                if (newState == 0) {
                    StatisticsBaseActivity statisticsBaseActivity=   (StatisticsBaseActivity) getActivity();
                    if (statisticsBaseActivity!=null){
                        statisticsBaseActivity.addStatisticsEvent("weather_main6",null);
                    }


                }
            }
        });
//
//        rvWeatherForHour.setOnScrollChangeListener(new RecyclerView.OnScrollChangeListener(){
//
//
//            @Override
//            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//
//
//            }
//        });

        if (weatherBean.getWeatherobj() != null && weatherBean.getWeatherobj().size() > 3) {
            Log.e(TAG, "initView: " + weatherBean.getWeatherobj().get(2).getData());
            tvDay3.setText(weatherBean.getWeatherobj().get(2).getData());
            tvDay4.setText(weatherBean.getWeatherobj().get(3).getData());
            ivWeather1.setImageResource(WeatherIconUtil.getIcon(weatherBean.getWeatherobj().get(0).getIcon()));
            ivWeather2.setImageResource(WeatherIconUtil.getIcon(weatherBean.getWeatherobj().get(1).getIcon()));
            ivWeather3.setImageResource(WeatherIconUtil.getIcon(weatherBean.getWeatherobj().get(2).getIcon()));
            ivWeather4.setImageResource(WeatherIconUtil.getIcon(weatherBean.getWeatherobj().get(3).getIcon()));
            setMaxMinText(tvMaxMin1, weatherBean.getWeatherobj().get(0).getTemplow(), weatherBean.getWeatherobj().get(0).getTemphigh());
            setMaxMinText(tvMaxMin2, weatherBean.getWeatherobj().get(1).getTemplow(), weatherBean.getWeatherobj().get(1).getTemphigh());
            setMaxMinText(tvMaxMin3, weatherBean.getWeatherobj().get(2).getTemplow(), weatherBean.getWeatherobj().get(2).getTemphigh());
            setMaxMinText(tvMaxMin4, weatherBean.getWeatherobj().get(3).getTemplow(), weatherBean.getWeatherobj().get(3).getTemphigh());
        } else {
            tvDay3.setText("--");
            tvDay4.setText("--");
            ivWeather1.setImageResource(0);
            ivWeather2.setImageResource(0);
            ivWeather3.setImageResource(0);
            ivWeather4.setImageResource(0);
            setMaxMinText(tvMaxMin1, "", "");
            setMaxMinText(tvMaxMin2, "", "");
            setMaxMinText(tvMaxMin3, "", "");
            setMaxMinText(tvMaxMin4, "", "");
        }
    }


    public void setMaxMinText(TextView textView, String textMax, String textMin) {
        if (TextUtils.equals(textMax, "")) {
            textView.setText("--~");
        } else {
            textView.setText(textMax + "°~");
        }
        if (TextUtils.equals(textMin, "")) {
            textView.append("--");
        } else {
            textView.append(textMin + "°");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
