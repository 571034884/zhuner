package com.aibabel.weather;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.weather.activity.AddCityActivity;
import com.aibabel.weather.activity.EditCityActivity;
import com.aibabel.weather.adapter.WeatherAdapter;
import com.aibabel.weather.app.BaseActivity;
import com.aibabel.weather.app.Constant;
import com.aibabel.weather.bean.LocationInternationalBean;
import com.aibabel.weather.bean.WeatherBean;
import com.aibabel.weather.bean.WeatherUrlBean;
import com.aibabel.weather.okgo.BaseBean;
import com.aibabel.weather.okgo.BaseCallback;
import com.aibabel.weather.okgo.DialogCallBack;
import com.aibabel.weather.okgo.OkGoUtil;
import com.aibabel.weather.utils.CommonUtils;
import com.aibabel.weather.utils.FastJsonUtil;
import com.aibabel.weather.utils.SharePrefUtil;
import com.aibabel.weather.utils.ToastUtil;
import com.aibabel.weather.utils.WeizhiUtil;
import com.github.promeg.pinyinhelper.Pinyin;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.taobao.sophix.SophixManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements BaseCallback {

    String TAG = "MainActivity";

    @BindView(R.id.vp_weather)
    ViewPager vpWeather;
    @BindView(R.id.ll_dot)
    LinearLayout llDot;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.iv_tianjia)
    ImageView ivTianjia;
    @BindView(R.id.iv_dingwei)
    ImageView ivDingwei;
    @BindView(R.id.tv_cityName)
    TextView tvCityName;

    private WeatherAdapter adapter;
    private List<Fragment> fragmentList;
    private List<WeatherBean> weatherBeanList;
    private List<WeatherUrlBean> weatherUrlBeanList;
    private List<TextView> dots;

    private LinearLayout.LayoutParams lp_dots_normal, lp_dots_focused;
    private int oldPosition = 0;
    private int currentPosition = 0;

    private String countryName = "";
    private String cityNameCN = "";
    private String countryNameCN = "";
    private String cityName = "";

    private String ips = "";
    private String key = "";
    private Context context;

    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        rexiufu();
        context = this;
        initViewPager();
        weatherUrlBeanList.addAll(getLishichengshi());
        weatherBeanList.addAll(getLishitianqi());
        Log.e(TAG, "init: " + weatherUrlBeanList.size() + "       " + weatherBeanList.size());
        if (CommonUtils.isNetAvailable(context)) {
            getDingWeiInfo();
        } else {
            for (int i = 0; i < weatherBeanList.size(); i++) {
                WeatherFragment weatherFragment = new WeatherFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", weatherBeanList.get(i));
                weatherFragment.setArguments(bundle);
                fragmentList.add(weatherFragment);
            }
            if (weatherUrlBeanList.size() > 0) {
                resetAdapter(0);
                if (Constant.CURRENT_LOCATION_VALUE.equalsIgnoreCase(weatherUrlBeanList.get(0).getCity())) {
                    ivDingwei.setImageResource(R.mipmap.tq_dizhi);
                } else {
                    ivDingwei.setImageResource(0);
                }
                tvCityName.setText(weatherUrlBeanList.get(0).getCityCN() + "," + weatherUrlBeanList.get(0).getCountryCN());
            }
        }
        initClickEvent();
    }

    public void initViewPager() {
        fragmentList = new ArrayList<>();
        weatherBeanList = new ArrayList<>();
        weatherUrlBeanList = new ArrayList<>();
        dots = new ArrayList<>();
        adapter = new WeatherAdapter(getSupportFragmentManager(), fragmentList);
        vpWeather.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //将对应的位置背景变为focus形式
                currentPosition = position;
                lp_dots_normal = (LinearLayout.LayoutParams) (dots.get(oldPosition)).getLayoutParams();
                lp_dots_focused = (LinearLayout.LayoutParams) (dots.get(currentPosition)).getLayoutParams();
                dots.get(oldPosition).setLayoutParams(lp_dots_normal);
                dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
                dots.get(currentPosition).setLayoutParams(lp_dots_focused);
                dots.get(currentPosition).setBackgroundResource(R.drawable.dot_focused);
                oldPosition = currentPosition;

                if (position == 0 && Constant.CURRENT_LOCATION_VALUE.equalsIgnoreCase(weatherUrlBeanList.get(0).getCity())) {
                    ivDingwei.setImageResource(R.mipmap.tq_dizhi);
                } else {
                    ivDingwei.setImageResource(0);
                }
                tvCityName.setText(weatherUrlBeanList.get(oldPosition).getCityCN() + "," + weatherUrlBeanList.get(oldPosition).getCountryCN());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vpWeather.setAdapter(adapter);
    }

    public void initClickEvent() {
        ivTianjia.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                if (dots.size() > 9) {
                    ToastUtil.show(MainActivity.this, getResources().getString(R.string.toast_chengshiguoduo), Toast.LENGTH_SHORT);
                } else {
                    Intent intent = new Intent(MainActivity.this, AddCityActivity.class);
                    startActivityForResult(intent, 888);
                }
                Map<String, String> map = new HashMap<>();
                StatisticsManager.getInstance(MainActivity.this).addEventAidl(2300, map);
            }
        });
        tvEdit.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                if (weatherBeanList.size() > 0) {
                    Intent intent = new Intent(MainActivity.this, EditCityActivity.class);
                    intent.putExtra("weatherBeanList", (Serializable) weatherBeanList);
                    intent.putExtra("weatherUrlBeanList", (Serializable) weatherUrlBeanList);
                    startActivityForResult(intent, 888);
                    Map<String, String> map = new HashMap<>();
                    StatisticsManager.getInstance(MainActivity.this).addEventAidl(2301, map);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void initData(int position, int size) {
        llDot.removeAllViews();
        dots.removeAll(dots);
        // 将要分页显示的View装入数组中
        for (int i = 0; i < size; i++) {
            TextView v_dot = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_for_viewpager_point, null);
            LinearLayout.LayoutParams docParams;
            if (i == position) {
                docParams = new LinearLayout.LayoutParams(10, 10);
                v_dot.setBackgroundResource(R.drawable.dot_focused);
            } else {
                docParams = new LinearLayout.LayoutParams(10, 10);
                v_dot.setBackgroundResource(R.drawable.dot_normal);
            }
            docParams.setMargins(4, 0, 4, 0);
            v_dot.setLayoutParams(docParams);
            llDot.addView(v_dot);
            dots.add(v_dot);
        }

    }

    /**
     * 防止用户瞎点击
     */
    public abstract static class OnMultiClickListener implements View.OnClickListener {
        // 两次点击按钮之间的点击间隔不能少于1500毫秒
        private static final int MIN_CLICK_DELAY_TIME = 1000;
        private long lastClickTime;

        public abstract void onMultiClick(View v);

        @Override
        public void onClick(View v) {
            long curClickTime = System.currentTimeMillis();
            if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
                // 超过点击间隔后再将lastClickTime重置为当前点击时间
                lastClickTime = curClickTime;
                onMultiClick(v);
            } else {
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 666) {
            //获取最后新添加的城市的天气，并更新
            WeatherUrlBean urlBean = new WeatherUrlBean();
            urlBean.setCountry(data.getStringExtra("countryEn"));
            urlBean.setCity(data.getStringExtra("cityEn"));
            urlBean.setCountryCN(data.getStringExtra("countryCn"));
            urlBean.setCityCN(data.getStringExtra("cityCn"));
            //是否已经存在于 sharePreference中
            int cunzai = -1;
            for (int i = 0; i < weatherUrlBeanList.size(); i++) {
                if (weatherUrlBeanList.get(i).getCity().equalsIgnoreCase(urlBean.getCity()))
                    cunzai = i;
            }
            if (cunzai != -1) {
                vpWeather.setCurrentItem(cunzai);
            } else {
                weatherUrlBeanList.add(urlBean);
                getWeather(Constant.IP_PORT + Constant.URL + urlBean.toString(), adapter.getCount(), false);
            }
        }
        //点击常规子项返回来的事件
        if (resultCode == 555) {
            List<Integer> list = (ArrayList<Integer>) data.getSerializableExtra("delPosition");
            int position = data.getIntExtra("clickPosition", 0) + 1;
            for (int i = 0; i < list.size(); i++) {
                synchronized (this) {
                    weatherUrlBeanList.remove(list.get(i) + 1);
                    weatherBeanList.remove(list.get(i) + 1);
                    fragmentList.remove(list.get(i) + 1);
                }
            }
            resetAdapter(position);
            saveLishichengshi(weatherUrlBeanList);
            saveLishitianqi(weatherBeanList);
        }
        //点击定位子项返回来的事件
        if (resultCode == 444) {
            Intent intentGet = data;
            List<Integer> list = (ArrayList<Integer>) data.getSerializableExtra("delPosition");
            int position = data.getIntExtra("clickPosition", 0);
            for (int i = 0; i < list.size(); i++) {
                weatherUrlBeanList.remove(list.get(i) + 1);
                weatherBeanList.remove(list.get(i) + 1);
                fragmentList.remove(list.get(i) + 1);
            }
            resetAdapter(position);
            saveLishichengshi(weatherUrlBeanList);
            saveLishitianqi(weatherBeanList);
        }
//        String json = FastJsonUtil.changListToString(weatherUrlBeanList);
//        SharePrefUtil.saveString(MainActivity.this, "weatherUrlBeanList", json);
    }

    public void getWeather() {
        StringBuffer urlBuffer = new StringBuffer();
        for (int i = 0; i < weatherUrlBeanList.size(); i++) {
            urlBuffer.append(weatherUrlBeanList.get(i).toString());
        }
        if (!TextUtils.equals(urlBuffer.toString(), ""))
            getWeather(Constant.IP_PORT + Constant.URL + urlBuffer.toString(), 0, true);
        Log.e(TAG, "initViewPager: " + Constant.IP_PORT + Constant.URL + urlBuffer.toString());
    }

    /**
     * 获取天气
     *
     * @param url      参数
     * @param position 刷新viewpager后显示那个位置
     */
    public void getWeather(String url, final int position, final boolean needClear) {

        GetRequest<String> getRequest = OkGo.<String>get(url).tag(this);
        getRequest.params("sn", CommonUtils.getSN());
        getRequest.params("sl", CommonUtils.getLocalLanguage());
        getRequest.params("no", CommonUtils.getRandom());
        getRequest.execute(new DialogCallBack(MainActivity.this, "") {
            @Override
            public void onSuccess(Response<String> response) {
                if (needClear)
                    weatherBeanList.removeAll(weatherBeanList);
                Log.e(TAG, response.body());
                List<WeatherBean> list = FastJsonUtil.changeJsonToList(response.body(), WeatherBean.class);
                for (int i = 0; i < list.size(); i++) {
                    weatherBeanList.add(list.get(i));
                    WeatherFragment weatherFragment = new WeatherFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("bean", list.get(i));
                    weatherFragment.setArguments(bundle);
                    fragmentList.add(weatherFragment);
                }
                resetAdapter(position);
                if (Constant.CURRENT_LOCATION_VALUE.equalsIgnoreCase(weatherUrlBeanList.get(position).getCity())) {
                    ivDingwei.setImageResource(R.mipmap.tq_dizhi);
                } else {
                    ivDingwei.setImageResource(0);
                }
                tvCityName.setText(weatherUrlBeanList.get(position).getCityCN() + "," + weatherUrlBeanList.get(position).getCountryCN());

                saveLishichengshi(weatherUrlBeanList);
                saveLishitianqi(weatherBeanList);
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Log.e(TAG, "onError");
            }
        });
    }

    public void getDingWeiInfo() {
        countryNameCN = WeizhiUtil.getInfo(MainActivity.this, WeizhiUtil.CONTENT_URI_WY, "country");
        cityNameCN = WeizhiUtil.getInfo(MainActivity.this, WeizhiUtil.CONTENT_URI_WY, "city");
//        countryNameCN = "Japan";
//        cityNameCN = "Tokyo";
        ips = WeizhiUtil.getInfo(MainActivity.this, WeizhiUtil.CONTENT_URI_WY, "ips");

        if (TextUtils.equals(countryNameCN, "中国")) {
            key = "中国_" + getPackageName() + "_joner";
            countryName = "China";
            if (cityNameCN.contains("香港")) cityNameCN = "香港";
            else if (cityNameCN.contains("澳门")) cityNameCN = "澳门";
            if (cityNameCN.length() > 2 && cityNameCN.contains("市"))
                cityNameCN = cityNameCN.substring(0, cityNameCN.lastIndexOf("市"));
            for (int i = 0; i < cityNameCN.length(); i++) {
                cityName = cityName + Pinyin.toPinyin(cityNameCN.charAt(i));
            }
            aaa();
        } else {
            key = "default_" + getPackageName() + "_joner";
            countryName = countryNameCN;
            cityName = cityNameCN;
            if (!TextUtils.equals(countryNameCN, "") && TextUtils.equals(cityNameCN, ""))
                getChineseNameOfLocation(countryNameCN, cityNameCN);
            else aaa();
        }
//        Constant.IP_PORT = "http://39.107.238.111:7001/";
//        Constant.IP_PORT = "http://192.168.50.10:7001/";
    }

    /**
     * 原本在getDingWeiInfo方法末端  因为要做定位显示国际化提出来
     */
    public void aaa() {
        try {
            JSONObject jsonObject = new JSONObject(ips);
            JSONArray jsonArray = new JSONArray(jsonObject.getString(key));
            Constant.IP_PORT = jsonArray.getJSONObject(0).get("domain").toString();
            Constant.CURRENT_LOCATION_VALUE = cityName;
            //是否已经存在于 sharePreference中
            if (!cityName.equals("")) {
                int cunzai = -1;
                for (int i = 0; i < weatherUrlBeanList.size(); i++) {
                    if (weatherUrlBeanList.get(i).getCity().equalsIgnoreCase(cityName))
                        cunzai = i;
                }
                if (cunzai == -1) {
                    WeatherUrlBean bean = new WeatherUrlBean();
                    bean.setCity(cityName);
                    bean.setCountry(countryName);
                    bean.setCityCN(cityNameCN);
                    bean.setCountryCN(countryNameCN);
                    weatherUrlBeanList.add(0, bean);
                } else {
                    weatherUrlBeanList.add(0, weatherUrlBeanList.remove(cunzai));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getWeather();
    }

    public void getChineseNameOfLocation(String countryNameCN, String cityNameCN) {
        Map<String, String> map = new HashMap<>();
        map.put("enCountryName", countryNameCN);
        map.put("enCityName", cityNameCN);
        OkGoUtil.get(mContext, Constant.METHOD_GROUP_MDD, Constant.METHOD_GET_LOCATION_INTERNATIONAL, map, LocationInternationalBean.class, this);
    }

    public void saveLishichengshi(List<WeatherUrlBean> list) {
        String json = FastJsonUtil.changListToString(list);
        SharePrefUtil.saveString(MainActivity.this, Constant.LISHI_CHENGSHI_KEY, json);
    }

    public void saveLishitianqi(List<WeatherBean> list) {
        String json = FastJsonUtil.changListToString(list);
        SharePrefUtil.saveString(MainActivity.this, Constant.LISHI_TIANQI_KEY, json);
    }

    public List<WeatherUrlBean> getLishichengshi() {
        String json = SharePrefUtil.getString(MainActivity.this, Constant.LISHI_CHENGSHI_KEY, "[]");
        return FastJsonUtil.changeJsonToList(json, WeatherUrlBean.class);
    }

    public List<WeatherBean> getLishitianqi() {
        String json = SharePrefUtil.getString(MainActivity.this, Constant.LISHI_TIANQI_KEY, "[]");
        return FastJsonUtil.changeJsonToList(json, WeatherBean.class);
    }

    /**
     * 刷新adapter，并显示指定位置
     *
     * @param index
     */
    public void resetAdapter(int index) {
        adapter.notifyDataSetChanged();
        initData(index, fragmentList.size());
        vpWeather.setCurrentItem(index);
    }


    @Override
    public void onSuccess(String method, BaseBean model) {
        switch (method) {
            case Constant.METHOD_GET_LOCATION_INTERNATIONAL:
                LocationInternationalBean bean = (LocationInternationalBean) model;
                countryNameCN = bean.getData().getCountryCnName();
                cityNameCN = bean.getData().getCityCnName();
                aaa();
        }
    }

    @Override
    public void onError(String method, String message) {
        Log.e(TAG, "onError: " + message);
        aaa();
    }

    public void rexiufu() {
        String latitude = "1111";
        String longitude = "1111";
//        String latitude = WeizhiUtil.getInfo(this, WeizhiUtil.CONTENT_URI_WY, "latitude");
//        String longitude = WeizhiUtil.getInfo(this, WeizhiUtil.CONTENT_URI_WY, "longitude");
        String url = Constant.IP_PORT + "/v1/jonersystem/GetAppNew?sn=" + CommonUtils.getSN() + "&no=" + CommonUtils.getRandom() + "&sl=" + CommonUtils.getLocalLanguage() + "&av=" + BuildConfig.VERSION_NAME + "&app=" + getPackageName() + "&sv=" + Build.DISPLAY + "&lat=" + latitude + "&lng=" + longitude;

        OkGo.<String>get(url)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(TAG, response.body().toString());
                        if (!TextUtils.isEmpty(response.body().toString())) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().toString());
                                boolean isNew = (Boolean) ((JSONObject) jsonObject.get("data")).get("isNew");
                                if (isNew) {
                                    SophixManager.getInstance().queryAndLoadNewPatch();
                                    Log.e("success:", "=================" + isNew + "=================");
                                } else {
                                    Log.e("failed:", "=================" + isNew + "=================");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("Exception:", "==========" + e.getMessage() + "===========");
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                    }
                });
    }

}
