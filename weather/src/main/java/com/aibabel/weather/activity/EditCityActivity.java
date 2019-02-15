package com.aibabel.weather.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.weather.MainActivity;
import com.aibabel.weather.R;
import com.aibabel.weather.adapter.CommonItemDecoration;
import com.aibabel.weather.app.BaseActivity;
import com.aibabel.weather.app.Constant;
import com.aibabel.weather.app.MyApplication;
import com.aibabel.weather.bean.WeatherBean;
import com.aibabel.weather.bean.WeatherUrlBean;
import com.aibabel.weather.custom.removeitemrecyclerview.ItemRemoveRecyclerView;
import com.aibabel.weather.custom.removeitemrecyclerview.MyAdapter;
import com.aibabel.weather.custom.removeitemrecyclerview.OnItemClickListener;
import com.aibabel.weather.utils.FastJsonUtil;
import com.aibabel.weather.utils.SharePrefUtil;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditCityActivity extends BaseActivity {

    @BindView(R.id.tv_time_dingwei)
    TextView tvTimeDingwei;
    @BindView(R.id.tv_city_dingwei)
    TextView tvCityDingwei;
    @BindView(R.id.tv_wendu_dingwei)
    TextView tvWenduDingwei;
    @BindView(R.id.rv_city)
    ItemRemoveRecyclerView rvCity;

    WeatherBean dataBean;
    WeatherUrlBean weatherUrlBean;
    //二级列表数据集合
    List<WeatherBean> weatherBeanList;
    List<WeatherUrlBean> weatherUrlBeanList;
    MyAdapter adapter;

    Intent resultIntent = new Intent();
    List<Integer> delPosition = new ArrayList<>();
    @BindView(R.id.cl_dingwei)
    ConstraintLayout clDingwei;

    @Override
    public int initLayout() {
        return R.layout.activity_edit_city;
    }

    @Override
    public void init() {
        getIntentData();
        if (weatherBeanList != null && weatherBeanList != null) {
            initData();
        }
    }

    public void getIntentData() {
        Intent intentGet = getIntent();
        weatherBeanList = (ArrayList<WeatherBean>) intentGet.getSerializableExtra("weatherBeanList");
        weatherUrlBeanList = (ArrayList<WeatherUrlBean>) intentGet.getSerializableExtra("weatherUrlBeanList");
    }

    public void initData() {
        dataBean = weatherBeanList.get(0);
        weatherUrlBean = weatherUrlBeanList.get(0);

        if (dataBean.getWeatherobj() != null) {
            setMaxMinText(tvTimeDingwei, dataBean.getWeatherobj().get(0).getTemplow(), dataBean.getWeatherobj().get(0).getTemphigh());
        } else {
            setMaxMinText(tvTimeDingwei, "", "");
        }
        tvCityDingwei.setText(weatherUrlBean.getCityCN() + "," + weatherUrlBean.getCountryCN());
        tvWenduDingwei.setText(dataBean.getWeatherNowData().getTemperature_string() + "°");

        weatherBeanList.remove(dataBean);
        weatherUrlBeanList.remove(weatherUrlBean);

        adapter = new MyAdapter(this, weatherBeanList, weatherUrlBeanList);
        adapter.setHasStableIds(true);
        rvCity.setLayoutManager(new LinearLayoutManager(this));
        rvCity.addItemDecoration(new CommonItemDecoration(getDrawable(R.drawable.gray333333)));
        rvCity.setAdapter(adapter);
        rvCity.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Toast.makeText(EditCityActivity.this, "** " + position + " **", Toast.LENGTH_SHORT).show();
                resultIntent.putExtra("delPosition", (Serializable) delPosition);
                resultIntent.putExtra("clickPosition", position);
                setResult(555, resultIntent);
                finish();
            }

            @Override
            public void onDeleteClick(int position) {
                Map<String, String> map = new HashMap<>();
                map.put("删除", adapter.getmUrlList().get(position).getCityCN() + adapter.getmUrlList().get(position).getCountryCN());
                StatisticsManager.getInstance(EditCityActivity.this).addEventAidl("点击事件", map);
                adapter.removeItem(position);
                delPosition.add(position);
            }
        });

        rvCity.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private TimerTask timerTask;
            private Timer timer;
            private boolean isScrolling = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        if (isScrolling) timer.cancel();
                        isScrolling = false;
                        Timer timer1 = new Timer();
                        TimerTask timerTask1 = new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        };
                        timer1.schedule(timerTask1, 300);
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        if (!isScrolling) {
                            timer = new Timer();
                            timerTask = new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            adapter.notifyDataSetChanged();
                                        }
                                    });

                                }
                            };
                            timer.schedule(timerTask, 0, 300);
                            isScrolling = true;
                        }

                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        Timer timer2 = new Timer();
        TimerTask timerTask2 = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });

            }
        };
        timer2.schedule(timerTask2, 500);

    }


    @OnClick(R.id.cl_dingwei)
    public void onViewClicked() {
        resultIntent.putExtra("delPosition", (Serializable) delPosition);
        resultIntent.putExtra("clickPosition", 0);
        setResult(444, resultIntent);
        finish();
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case 133:
                weatherBeanList.add(0, dataBean);
                weatherUrlBeanList.add(0, weatherUrlBean);
                saveLishichengshi(weatherUrlBeanList);
                saveLishitianqi(weatherBeanList);
                MyApplication.exit();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void saveLishichengshi(List<WeatherUrlBean> list) {
        String json = FastJsonUtil.changListToString(list);
        SharePrefUtil.saveString(EditCityActivity.this, Constant.LISHI_CHENGSHI_KEY, json);
    }

    public void saveLishitianqi(List<WeatherBean> list) {
        String json = FastJsonUtil.changListToString(list);
        SharePrefUtil.saveString(EditCityActivity.this, Constant.LISHI_TIANQI_KEY, json);
    }
}
