package com.aibabel.alliedclock;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.alliedclock.activity.AddCityActivity;
import com.aibabel.alliedclock.adapter.CommonItemDecoration;
import com.aibabel.alliedclock.app.BaseActivity;
import com.aibabel.alliedclock.app.Constant;
import com.aibabel.alliedclock.bean.ClockBean;
import com.aibabel.alliedclock.custom.removeitemrecyclerview.ItemRemoveRecyclerView;
import com.aibabel.alliedclock.custom.removeitemrecyclerview.MyAdapter;
import com.aibabel.alliedclock.custom.removeitemrecyclerview.OnItemClickListener;
import com.aibabel.alliedclock.utils.CommonUtils;
import com.aibabel.alliedclock.utils.FastJsonUtil;
import com.aibabel.alliedclock.utils.NetUtil;
import com.aibabel.alliedclock.utils.SharePrefUtil;
import com.aibabel.alliedclock.utils.WeizhiUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.taobao.sophix.SophixManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    String TAG = this.getClass().getSimpleName();

    @BindView(R.id.rv_city)
    ItemRemoveRecyclerView rvCity;
    @BindView(R.id.iv_tianjia)
    ImageView ivTianjia;

    MyAdapter adapter;
    private List<ClockBean> clockBeanList;

    private static final Uri CONTENT_URI = Uri.parse("content://com.aibabel.locationservice" +
            ".provider.AibabelProvider/aibabel_location");
    private String countryNameCN;
    private String ips;
    private String key;

    private Context mContext;
    private String cityNameCN;

    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        mContext = this;
        rexiufu();
        //获取当前用户添加过得城市时区信息
        String json = SharePrefUtil.getString(MainActivity.this, "clockBeanList", "[]");
        clockBeanList = FastJsonUtil.changeJsonToList(json, ClockBean.class);

        if (SharePrefUtil.getBoolean(MainActivity.this, "isFirstUse", true)) {
            SharePrefUtil.saveBoolean(MainActivity.this, "isFirstUse", false);
            switch (CommonUtils.getLocalLanguage()) {
                case "zh_CN":
                case "zh_TW":
                    clockBeanList.add(firstUsedAdd("北京", "Beijing", "GMT+8"));
                    clockBeanList.add(firstUsedAdd("东京", "Tokyo", "GMT+9"));
                    clockBeanList.add(firstUsedAdd("曼谷", "Bangkok", "GMT+7"));
                    clockBeanList.add(firstUsedAdd("纽约", "New York", "GMT-5"));
                    break;
                case "en":
                    clockBeanList.add(firstUsedAdd("Beijing", "Beijing", "GMT+8"));
                    clockBeanList.add(firstUsedAdd("Tokyo", "Tokyo", "GMT+9"));
                    clockBeanList.add(firstUsedAdd("Bangkok", "Bangkok", "GMT+7"));
                    clockBeanList.add(firstUsedAdd("New York", "New York", "GMT-5"));
                    break;
                case "ja":
                    clockBeanList.add(firstUsedAdd("北京", "Beijing", "GMT+8"));
                    clockBeanList.add(firstUsedAdd("東京", "Tokyo", "GMT+9"));
                    clockBeanList.add(firstUsedAdd("バンコク", "Bangkok", "GMT+7"));
                    clockBeanList.add(firstUsedAdd("ニューヨーク", "New York", "GMT-5"));
                    break;
                case "ko":
                    clockBeanList.add(firstUsedAdd("베이징", "Beijing", "GMT+8"));
                    clockBeanList.add(firstUsedAdd("동경", "Tokyo", "GMT+9"));
                    clockBeanList.add(firstUsedAdd("방콕", "Bangkok", "GMT+7"));
                    clockBeanList.add(firstUsedAdd("뉴욕", "New York", "GMT-5"));
                    break;
                default:
                    clockBeanList.add(firstUsedAdd("北京", "Beijing", "GMT+8"));
                    clockBeanList.add(firstUsedAdd("东京", "Tokyo", "GMT+9"));
                    clockBeanList.add(firstUsedAdd("曼谷", "Bangkok", "GMT+7"));
                    clockBeanList.add(firstUsedAdd("纽约", "New York", "GMT-5"));
                    break;
            }
            String json1 = FastJsonUtil.changListToString(clockBeanList);
            SharePrefUtil.saveString(MainActivity.this, "clockBeanList", json1);
        }

        if (NetUtil.isNetworkAvailable(MainActivity.this)) {
            countryNameCN = WeizhiUtil.getInfo(MainActivity.this, WeizhiUtil.CONTENT_URI_WY,
                    "country");
            cityNameCN = WeizhiUtil.getInfo(MainActivity.this, WeizhiUtil.CONTENT_URI_WY, "city");
            ips = WeizhiUtil.getInfo(MainActivity.this, WeizhiUtil.CONTENT_URI_WY, "ips");

            if (cityNameCN.length() > 2 && cityNameCN.contains("市"))
                cityNameCN = cityNameCN.substring(0, cityNameCN.lastIndexOf("市"));
            try {
                if (countryNameCN.equals("中国")) {
                    key = "中国_" + getPackageName() + "_joner";
                } else {
                    key = "default_" + getPackageName() + "_joner";
                }
                JSONObject jsonObject = new JSONObject(ips);
                JSONArray jsonArray = new JSONArray(jsonObject.getString(key));
                Constant.IP_PORT = jsonArray.getJSONObject(0).get("domain").toString();
            } catch (Exception e) {
                Log.e("init: ", "ip信息错误");
            }
        }
        initData();

        ivTianjia.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                ivTianjia.setClickable(false);
                Intent intent = new Intent(MainActivity.this, AddCityActivity.class);
                startActivityForResult(intent, 888);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ivTianjia.setClickable(true);
    }


    public ClockBean firstUsedAdd(String cityCN, String city, String timeZone) {
        ClockBean clockBean = new ClockBean();
        clockBean.setTiemZone(timeZone);
        clockBean.setCity(city);
        clockBean.setCityCN(cityCN);
        return clockBean;
    }

    @Override
    protected void onPause() {
        super.onPause();
        String json = FastJsonUtil.changListToString(clockBeanList);
        SharePrefUtil.saveString(MainActivity.this, "clockBeanList", json);
    }

    public void initData() {
        adapter = new MyAdapter(this, clockBeanList);
        adapter.setHasStableIds(true);
        rvCity.setLayoutManager(new LinearLayoutManager(this));
        rvCity.addItemDecoration(new CommonItemDecoration(getDrawable(R.drawable.gray333333)));
        rvCity.setAdapter(adapter);
        rvCity.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Toast.makeText(MainActivity.this, "** " + position + " **", Toast.LENGTH_SHORT)
// .show();
            }

            @Override
            public void onDeleteClick(int position) {
                Map<String, String> map = new HashMap<>();
                map.put("城市名称", adapter.getmList().get(position).getCityCN());
                StatisticsManager.getInstance(MainActivity.this).addEventAidl("删除城市", map);
                adapter.removeItem(position);
                String json = FastJsonUtil.changListToString(clockBeanList);
                SharePrefUtil.saveString(MainActivity.this, "clockBeanList", json);
            }
        });
//        Timer timer = new Timer();
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        adapter.notifyDataSetChanged();
//                    }
//                });
//
//            }
//        };
//        timer.schedule(timerTask, 1000, 1000);
    }

    /**
     * 设置导航栏显示状态
     *
     * @param visible
     */
    private void setNavigationBarVisibility(boolean visible) {
        int flag = 0;
        if (!visible) {
            flag = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View
                    .SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }
        getWindow().getDecorView().setSystemUiVisibility(flag);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

//    @OnClick(R.id.iv_tianjia)
//    public void onViewClicked(View view) {
//        Intent intent = null;
//        switch (view.getId()) {
//            case R.id.iv_tianjia:
//                intent = new Intent(MainActivity.this, AddCityActivity.class);
//                break;
//        }
//        startActivityForResult(intent, 888);
//    }

    /**
     * 防止用户瞎点击
     */
    public abstract static class OnMultiClickListener implements View.OnClickListener {
        // 两次点击按钮之间的点击间隔不能少于1500毫秒
        private static final int MIN_CLICK_DELAY_TIME = 1500;
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
            ClockBean clockBean = new ClockBean();
            clockBean.setCity(data.getStringExtra("cityEn"));
            clockBean.setCityCN(data.getStringExtra("cityCn"));
            clockBean.setTiemZone(data.getStringExtra("timeZone"));
            //是否已经存在于 sharePreference中
            int cunzai = -1;
            for (int i = 0; i < clockBeanList.size(); i++) {
                if (clockBeanList.get(i).getCity().equalsIgnoreCase(clockBean.getCity()))
                    cunzai = i;
            }
            if (cunzai != -1) {
                rvCity.scrollToPosition(cunzai);
            } else {
                clockBeanList.add(clockBean);
                String json = FastJsonUtil.changListToString(clockBeanList);
                SharePrefUtil.saveString(MainActivity.this, "clockBeanList", json);
                adapter.notifyDataSetChanged();
                rvCity.scrollToPosition(clockBeanList.size() - 1);
            }
        }
    }

    public void rexiufu() {
//        String latitude = "111";
//        String longitude = "111";
        String latitude = WeizhiUtil.getInfo(this, WeizhiUtil.CONTENT_URI_WY, "latitude");
        String longitude = WeizhiUtil.getInfo(this, WeizhiUtil.CONTENT_URI_WY, "longitude");
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
