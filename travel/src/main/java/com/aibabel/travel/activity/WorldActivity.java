package com.aibabel.travel.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.travel.MainActivity;
import com.aibabel.travel.R;
import com.aibabel.travel.adaper.CommomRecyclerAdapter;
import com.aibabel.travel.adaper.CommonRecyclerViewHolder;
import com.aibabel.travel.bean.BaseModel;
import com.aibabel.travel.bean.CityItemBean;
import com.aibabel.travel.bean.CountryData;
import com.aibabel.travel.bean.OfflineBean;
import com.aibabel.travel.broadcastreceiver.NetBroadcastReceiver;
import com.aibabel.travel.http.ResponseCallback;
import com.aibabel.travel.utils.CommonUtils;
import com.aibabel.travel.utils.Constant;
import com.aibabel.travel.utils.ContentProviderUtil;
import com.aibabel.travel.utils.FastJsonUtil;
import com.aibabel.travel.utils.JsonUtils;
import com.aibabel.travel.utils.MyDialog;
import com.aibabel.travel.utils.NetworkUtils;
import com.aibabel.travel.utils.SharePrefUtil;
import com.aibabel.travel.utils.StringUtil;
import com.aibabel.travel.widgets.MyGridLayoutManager;
import com.aibabel.travel.widgets.MyRecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ==========================================================================================
 *
 * @Author：CreateBy 张文颖
 * @Date：2018/6/19
 * @Desc：世界页
 * @==========================================================================================
 */
public class WorldActivity extends BaseActivity implements ResponseCallback, NetBroadcastReceiver.NetListener {

    MyRecyclerView rvHotCitys;
    TextView tvHot;
    MyRecyclerView rvAsiaCitys;
    TextView tvAsia;
    MyRecyclerView rvEuropeCitys;
    TextView tvEurope;
    MyRecyclerView rvAfricaCitys;
    TextView tvAfrica;
    MyRecyclerView rvNorthAmericaCitys;
    TextView tvNorthAmerica;
    MyRecyclerView rvSouthAmericaCitys;
    TextView tvSouthAmerica;
    MyRecyclerView rvOceaniaCitys;
    TextView tvOceania;
    TextView tvHotEmpty;
    TextView tvAsiaEmpty;
    TextView tvEuropeEmpty;
    TextView tvAfricaEmpty;
    TextView tvNorthAmericaEmpty;
    TextView tvSouthAmericaEmpty;
    TextView tvOceaniaEmpty;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    ViewStub vsTest;
    @BindView(R.id.cl_root)
    ConstraintLayout clRoot;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    LinearLayout llHotCitys;
    LinearLayout llAsiaCitys;
    LinearLayout llEuropeCitys;
    LinearLayout llAfricaCitys;
    LinearLayout llNorthAmericaCitys;
    LinearLayout llSouthAmericaCitys;
    LinearLayout llOceaniaCitys;


    private AlertDialog dialog;
    private Window window;

    private List<CityItemBean> hot_city_list = new ArrayList();
    private List<CityItemBean> asia_city_list = new ArrayList();
    private List<CityItemBean> europe_city_list = new ArrayList();
    private List<CityItemBean> africa_city_list = new ArrayList();
    private List<CityItemBean> northAmerica_city_list = new ArrayList();
    private List<CityItemBean> southAmerica_city_list = new ArrayList();
    private List<CityItemBean> oceania_city_list = new ArrayList();
    private CountryData bean;
    private List<CountryData.DataBean.ResultsBean> list = new ArrayList<>();
    private List<OfflineBean> offlineList;
    private CommomRecyclerAdapter<CityItemBean> objectCommomRecyclerAdapter;
    public NetBroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        //网络变化广播监听
        broadcastReceiver = new NetBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(broadcastReceiver, intentFilter);
        broadcastReceiver.setListener(this);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_base;
    }

    @Override
    public void init() {
        vsTest = findViewById(R.id.vs_test);
        vsTest.setLayoutResource(R.layout.stub_world);
        View iv_vsContent = vsTest.inflate();
        llAfricaCitys = iv_vsContent.findViewById(R.id.ll_africa_citys);
        llAsiaCitys = iv_vsContent.findViewById(R.id.ll_asia_citys);
        llEuropeCitys = iv_vsContent.findViewById(R.id.ll_europe_citys);
        llHotCitys = iv_vsContent.findViewById(R.id.ll_hot_citys);
        llNorthAmericaCitys = iv_vsContent.findViewById(R.id.ll_NorthAmerica_citys);
        llOceaniaCitys = iv_vsContent.findViewById(R.id.ll_Oceania_citys);
        llSouthAmericaCitys = iv_vsContent.findViewById(R.id.ll_SouthAmerica_citys);
        tvHot = iv_vsContent.findViewById(R.id.tv_hot);
        tvAsia = iv_vsContent.findViewById(R.id.tv_asia);
        tvAfrica = iv_vsContent.findViewById(R.id.tv_africa);
        tvEurope = iv_vsContent.findViewById(R.id.tv_europe);
        tvNorthAmerica = iv_vsContent.findViewById(R.id.tv_NorthAmerica);
        tvOceania = iv_vsContent.findViewById(R.id.tv_Oceania);
        tvSouthAmerica = iv_vsContent.findViewById(R.id.tv_SouthAmerica);
        rvHotCitys = iv_vsContent.findViewById(R.id.rv_hot_citys);
        rvAsiaCitys = iv_vsContent.findViewById(R.id.rv_asia_citys);
        rvAfricaCitys = iv_vsContent.findViewById(R.id.rv_africa_citys);
        rvEuropeCitys = iv_vsContent.findViewById(R.id.rv_europe_citys);
        rvNorthAmericaCitys = iv_vsContent.findViewById(R.id.rv_NorthAmerica_citys);
        rvOceaniaCitys = iv_vsContent.findViewById(R.id.rv_Oceania_citys);
        rvSouthAmericaCitys = iv_vsContent.findViewById(R.id.rv_SouthAmerica_citys);
        tvHotEmpty = iv_vsContent.findViewById(R.id.tv_hot_empty);
        tvAsiaEmpty = iv_vsContent.findViewById(R.id.tv_asia_empty);
        tvAfricaEmpty = iv_vsContent.findViewById(R.id.tv_africa_empty);
        tvEuropeEmpty = iv_vsContent.findViewById(R.id.tv_europe_empty);
        tvNorthAmericaEmpty = iv_vsContent.findViewById(R.id.tv_NorthAmerica_empty);
        tvOceaniaEmpty = iv_vsContent.findViewById(R.id.tv_Oceania_empty);
        tvSouthAmericaEmpty = iv_vsContent.findViewById(R.id.tv_SouthAmerica_empty);

        offlineList = ContentProviderUtil.getOffline();
        initTitle();
        initView();

        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float percent = Float.valueOf(Math.abs(verticalOffset)) / Float.valueOf(appBarLayout.getTotalScrollRange());
                //第一种
                int toolbarHeight = appBarLayout.getTotalScrollRange();
                int dy = Math.abs(verticalOffset);
                if (dy <= toolbarHeight) {
                    float scale = (float) dy / toolbarHeight;
                    float alpha = scale * 255;
                    clRoot.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                }
//                第二种
//                clRoot.setAlpha(percent);
            }
        });


    }

    public void initView() {
        Intent intent = getIntent();
        int first = intent.getIntExtra("first", 0);
        if (first == 1) {
            ivLeft.setVisibility(View.GONE);
        }
        CommonUtils.setMargins(tvTitle, ivLeft.getVisibility() == View.GONE ? 20 : 0, 0, 20, 0);
        tvHot.setOnClickListener(this);
        tvSouthAmerica.setOnClickListener(this);
        tvEurope.setOnClickListener(this);
        tvOceania.setOnClickListener(this);
        tvNorthAmerica.setOnClickListener(this);
        tvAsia.setOnClickListener(this);
        tvAfrica.setOnClickListener(this);
        tvTitle.setOnClickListener(this);
        ivLeft.setOnClickListener(this);
        initRecyclerView(rvHotCitys, R.layout.hot_city, 2, hot_city_list, tvHotEmpty);
        initRecyclerView(rvAsiaCitys, R.layout.continent_city, 3, asia_city_list, tvAsiaEmpty);
        initRecyclerView(rvAfricaCitys, R.layout.continent_city, 3, africa_city_list, tvAfricaEmpty);
        initRecyclerView(rvEuropeCitys, R.layout.continent_city, 3, europe_city_list, tvEuropeEmpty);
        initRecyclerView(rvNorthAmericaCitys, R.layout.continent_city, 3, northAmerica_city_list, tvNorthAmericaEmpty);
        initRecyclerView(rvOceaniaCitys, R.layout.continent_city, 3, oceania_city_list, tvOceaniaEmpty);
        initRecyclerView(rvSouthAmericaCitys, R.layout.continent_city, 3, southAmerica_city_list, tvSouthAmericaEmpty);
    }

    public void initTitle() {
        ivLeft.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
//        ivRight.setVisibility(View.GONE);

        ivLeft.setVisibility(getIntent().getIntExtra("isback", View.VISIBLE));

        ivLeft.setImageResource(R.mipmap.ic_backb);
        tvTitle.setHint(getResources().getString(R.string.search_hint));
        tvTitle.setHintTextColor(getResources().getColor(R.color.color_66));
        tvTitle.setBackgroundResource(R.drawable.bg_search_80while);
        //        ivRight.setImageResource(R.mipmap.ic_home_w);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != list && list.size() == 0) {
            initData();
        }

    }

    public void initData() {
//        if (CommonUtils.isAvailable(this)) {
//            Map<String, String> map = new HashMap<>();
//            get(this, this, CountryData.class, map, "getcountry");
//            llAfricaCitys.setVisibility(View.VISIBLE);
//            llSouthAmericaCitys.setVisibility(View.VISIBLE);
//        } else {
        list = JsonUtils.getCountry(this).getData().getResults();
//            list = DataDAO.queryCountrys();
        sendDataToView(list);
//        }

    }


    private void initRecyclerView(final MyRecyclerView lvList, int mLayId, int spanCount, final List<CityItemBean> city_list, TextView tv) {

//        hsvScroll.setCurrentScrollableContainer(this);

        MyGridLayoutManager gridLayoutManager = new MyGridLayoutManager(this, spanCount);
        //设置布局管理器
        lvList.setLayoutManager(gridLayoutManager);

        //用户判断是否显示离线图标
        objectCommomRecyclerAdapter = new CommomRecyclerAdapter<CityItemBean>(WorldActivity.this, city_list, mLayId, new CommomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommonRecyclerViewHolder holder, int postion) {
                if (CommonUtils.isFastClick()) {
                    toCountry(city_list.get(postion).getId(), city_list.get(postion).getName());
                }
            }
        }, null) {
            @Override
            public void convert(CommonRecyclerViewHolder holder, CityItemBean bean, int position) {

                ImageView iv_item_icon = holder.getView(R.id.iv_item_icon);
                ImageView iv_item_label = holder.getView(R.id.iv_item_label);
                TextView tv_item = holder.getView(R.id.tv_item);
                tv_item.setText(bean.getName());
//                if (CommonUtils.isAvailable()) {//用户判断是否显示离线图标
//                    iv_item_label.setVisibility(View.GONE);
//                } else {
                if (StringUtil.isOffline(offlineList, city_list.get(position).getId()) == 0) {
                    iv_item_label.setVisibility(View.GONE);
                } else if (StringUtil.isOffline(offlineList, city_list.get(position).getId()) == 1) {
                    iv_item_label.setVisibility(View.VISIBLE);
                    iv_item_label.setImageResource(R.mipmap.download);
                } else if (StringUtil.isOffline(offlineList, city_list.get(position).getId()) == 2) {
                    iv_item_label.setVisibility(View.VISIBLE);
                    iv_item_label.setImageResource(R.mipmap.not_download);
                }
//                }


                RequestOptions options = new RequestOptions().error(R.mipmap.error_h).placeholder(R.mipmap.placeholder_h);
                Glide.with(WorldActivity.this)
                        .load(bean.getCover_path())
                        .apply(options)
                        .into(iv_item_icon);
                Log.e("mmmmm", bean.getCover());
            }
        };
        //设置Adapter
        lvList.setAdapter(objectCommomRecyclerAdapter);
        lvList.setEmptyView(tv);
        objectCommomRecyclerAdapter.notifyDataSetChanged();


    }


    /**
     * 跳转到国家页面
     *
     * @param id
     * @param name
     */
    private void toCountry(int id, String name) {
        Map<String, Integer> map = StringUtil.getOffline();
        //遍历map判定id的值是否在map中
        if (null == offlineList || !map.containsValue(id) || TextUtils.equals(CommonUtils.getDeviceInfo(), Constant.PM)) {
            if (!CommonUtils.isAvailable()) {
                Toast.makeText(this, getString(R.string.net_unavailable), Toast.LENGTH_SHORT).show();
                return;
            }

        }



        switch (StringUtil.isSupportOffline(id, offlineList)) {
            case 0://不支持离线
                online(id, name);
                break;
            case 1://支持离线并下载安装成功了


                Constant.PREFIX = StringUtil.getKeyByValue(map,id);
                Intent intent = new Intent(this, CountryActivity.class);
                intent.putExtra("id", String.valueOf(id));
                intent.putExtra("offline", true);
                intent.putExtra("name", name);
                startActivity(intent);
                break;
            case 2://支持离线下载完成，但是没有安装
                if (!SharePrefUtil.getBoolean(WorldActivity.this, StringUtil.getKeyByValue(map,id), false)) {
                    MyDialog.showDialog(WorldActivity.this, "离线包已下载，快去安装吧！");
                    SharePrefUtil.saveBoolean(WorldActivity.this, StringUtil.getKeyByValue(map,id), true);
                } else {

                    if (CommonUtils.isAvailable()) {
                        online(id, name);
                    } else {
                        Toast.makeText(this, getString(R.string.net_unavailable), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case 3://有离线包,但是没有下载
                if (!SharePrefUtil.getBoolean(WorldActivity.this, StringUtil.getKeyByValue(map,id), false)) {
                    MyDialog.showDialog(WorldActivity.this, "有离线包,快去联网下载吧！");
                    SharePrefUtil.saveBoolean(WorldActivity.this, StringUtil.getKeyByValue(map,id), true);
                } else {

                    if (CommonUtils.isAvailable()) {
                        online(id, name);
                    } else {
                        Toast.makeText(this, getString(R.string.net_unavailable), Toast.LENGTH_SHORT).show();
                    }
                }
                break;

        }


//        Map<String, Integer> map = StringUtil.getOffline();
//        //遍历map判定id的值是否在map中
//        if (null == offlineList || !map.containsValue(id) || TextUtils.equals(CommonUtils.getDeviceInfo(), Constant.PM)) {
//            if (!CommonUtils.isAvailable()) {
//                Toast.makeText(this, getString(R.string.net_unavailable), Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//        }


//        if (map.containsKey(id)) {
//            for (OfflineBean bean : offlineList) {
//                if (TextUtils.equals("1", bean.getStatus())) {
//                    Constant.PREFIX = bean.getId();
//                    Intent intent = new Intent(this, CountryActivity.class);
//                    intent.putExtra("id", String.valueOf(id));
//                    intent.putExtra("offline", true);
//                    intent.putExtra("name", name);
//                    startActivity(intent);
//                    return;
//                } else if (TextUtils.equals("10", bean.getStatus())
//                        || TextUtils.equals("12", bean.getStatus())) {
//                    if (!SharePrefUtil.getBoolean(WorldActivity.this, bean.getId(), false)) {
//                        MyDialog.showDialog(WorldActivity.this, "离线包已下载，快去安装吧！");
//                        SharePrefUtil.saveBoolean(WorldActivity.this, bean.getId(), true);
//                    } else {
//
//                        if (CommonUtils.isAvailable()) {
//                            online(id, name);
//                        } else {
//                            Toast.makeText(this, getString(R.string.net_unavailable), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//
//                } else if (TextUtils.equals("99", bean.getStatus())
//                        || TextUtils.equals("-1", bean.getStatus())
//                        || TextUtils.equals("5", bean.getStatus())) {
//
//                    if (!SharePrefUtil.getBoolean(WorldActivity.this, bean.getId(), false)) {
//                        MyDialog.showDialog(WorldActivity.this, "有离线包,快去联网下载吧！");
//                        SharePrefUtil.saveBoolean(WorldActivity.this, bean.getId(), true);
//                    } else {
//
//                        if (CommonUtils.isAvailable()) {
//                            online(id, name);
//                        } else {
//                            Toast.makeText(this, getString(R.string.net_unavailable), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                }
//            }
//
//        } else {
//            if (CommonUtils.isAvailable()) {
//                online(id, name);
//                return;
//            } else {
//                Toast.makeText(this, getString(R.string.net_unavailable), Toast.LENGTH_SHORT).show();
//                return;
//            }
//        }


//        if (!CommonUtils.isAvailable()) {
//            Map<String, Integer> map = StringUtil.getOffline();
//            //遍历map判定id的值是否在map中
//            if (null == offlineList || !map.containsValue(id) || TextUtils.equals(CommonUtils.getDeviceInfo(), Constant.PM)) {
//                Toast.makeText(this, getString(R.string.net_unavailable), Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//
//            for (OfflineBean bean : offlineList) {
//                String mmid = bean.getId();
//                Log.e("mmid", bean.getId());
//                if (map.containsKey(mmid) && map.get(mmid) == id) {
//                    if (TextUtils.equals("1", bean.getStatus())) {
//                        Constant.PREFIX = bean.getId();
//                        Intent intent = new Intent(this, CountryActivity.class);
//                        intent.putExtra("id", String.valueOf(id));
//                        intent.putExtra("offline", true);
//                        intent.putExtra("name", name);
//                        startActivity(intent);
//                        return;
//                    } else if (TextUtils.equals("10", bean.getStatus())
//                            || TextUtils.equals("12", bean.getStatus())) {
//                        MyDialog.showDialog(WorldActivity.this, "离线包已下载，快去安装吧！");
//                        return;
//                    } else if (TextUtils.equals("99", bean.getStatus())
//                            || TextUtils.equals("-1", bean.getStatus())
//                            || TextUtils.equals("5", bean.getStatus())) {
//                        MyDialog.showDialog(WorldActivity.this, "有离线包,快去联网下载吧！");
//                        return;
//                    }
//                }
//            }
//        } else {
//            Intent intent = new Intent(this, CountryActivity.class);
//            intent.putExtra("id", String.valueOf(id));
//            intent.putExtra("name", name);
//            if (StringUtil.isOffline(offlineList, id) == 1)
//                intent.putExtra("offline", true);
//            startActivity(intent);
//        }


    }

    private void online(int id, String name) {
        Intent intent = new Intent(this, CountryActivity.class);
        intent.putExtra("id", String.valueOf(id));
        intent.putExtra("name", name);
//        if (StringUtil.isOffline(offlineList, id) == 1)
//            intent.putExtra("offline", true);
        startActivity(intent);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_hot:
                initRecyclerView(rvHotCitys, R.layout.hot_city, 2, hot_city_list, tvHotEmpty);
                tvHot.setVisibility(View.GONE);
                break;
            case R.id.tv_asia:
                initRecyclerView(rvAsiaCitys, R.layout.continent_city, 3, asia_city_list, tvAsiaEmpty);
                tvAsia.setVisibility(View.GONE);
                break;
            case R.id.tv_africa:
                initRecyclerView(rvAfricaCitys, R.layout.continent_city, 3, africa_city_list, tvAfricaEmpty);
                tvAfrica.setVisibility(View.GONE);
                break;
            case R.id.tv_europe:
                initRecyclerView(rvEuropeCitys, R.layout.continent_city, 3, europe_city_list, tvEuropeEmpty);
                tvEurope.setVisibility(View.GONE);
                break;
            case R.id.tv_NorthAmerica:
                initRecyclerView(rvNorthAmericaCitys, R.layout.continent_city, 3, northAmerica_city_list, tvNorthAmericaEmpty);
                tvNorthAmerica.setVisibility(View.GONE);
                break;
            case R.id.tv_Oceania:
                initRecyclerView(rvOceaniaCitys, R.layout.continent_city, 3, oceania_city_list, tvOceaniaEmpty);
                tvOceania.setVisibility(View.GONE);
                break;
            case R.id.tv_SouthAmerica:
                initRecyclerView(rvSouthAmericaCitys, R.layout.continent_city, 3, southAmerica_city_list, tvSouthAmericaEmpty);
                tvSouthAmerica.setVisibility(View.GONE);
                break;
            case R.id.tv_title:
                if (!NetworkUtils.isAvailable(this)) {
                    toastShort(getString(R.string.net_unavailable));
                    return;
                }
                Intent intent = new Intent(WorldActivity.this, SearchPageActivity.class);
                intent.putExtra("hot", FastJsonUtil.changListToString(hot_city_list));
                startActivity(intent);
                break;
            case R.id.iv_left:
                finish();
                break;
            default:
                break;
        }
    }


    @Override
    public void onSuccess(String method, BaseModel result) {
//        bean = (CountryData) result;
//        list = bean.getData().getResults();
//        sendDataToView(list);
    }

    @Override
    public void onError() {

    }

    private void sendDataToView(List<CountryData.DataBean.ResultsBean> list) {
        hot_city_list = StringUtil.getCountrys(this.list, "1");
        asia_city_list = StringUtil.getCountrys(list, "亚洲");
        europe_city_list = StringUtil.getCountrys(list, "欧洲");
        africa_city_list = StringUtil.getCountrys(list, "非洲");
        northAmerica_city_list = StringUtil.getCountrys(list, "北美洲");
        southAmerica_city_list = StringUtil.getCountrys(list, "南美洲");
        oceania_city_list = StringUtil.getCountrys(list, "大洋洲");

        if (hot_city_list.size() > 4) {
            showMore(1);
            initRecyclerView(rvHotCitys, R.layout.hot_city, 2, hot_city_list.subList(0, 4), tvHotEmpty);
        } else {
            initRecyclerView(rvHotCitys, R.layout.hot_city, 2, hot_city_list, tvHotEmpty);
        }
        if (asia_city_list.size() > 6) {
            showMore(2);
            initRecyclerView(rvAsiaCitys, R.layout.continent_city, 3, asia_city_list.subList(0, 6), tvAsiaEmpty);
        } else {
            initRecyclerView(rvAsiaCitys, R.layout.continent_city, 3, asia_city_list, tvAsiaEmpty);
        }

        if (europe_city_list.size() > 6) {
            showMore(3);
            initRecyclerView(rvEuropeCitys, R.layout.continent_city, 3, europe_city_list.subList(0, 6), tvEuropeEmpty);
        } else {
            initRecyclerView(rvEuropeCitys, R.layout.continent_city, 3, europe_city_list, tvEuropeEmpty);
        }

        if (africa_city_list.size() > 6) {
            showMore(4);
            initRecyclerView(rvAfricaCitys, R.layout.continent_city, 3, africa_city_list.subList(0, 6), tvAfricaEmpty);
        } else {
            initRecyclerView(rvAfricaCitys, R.layout.continent_city, 3, africa_city_list, tvAfricaEmpty);
        }
        if (northAmerica_city_list.size() > 6) {
            showMore(5);
            initRecyclerView(rvNorthAmericaCitys, R.layout.continent_city, 3, northAmerica_city_list.subList(0, 6), tvNorthAmericaEmpty);
        } else {
            initRecyclerView(rvNorthAmericaCitys, R.layout.continent_city, 3, northAmerica_city_list, tvNorthAmericaEmpty);
        }
        if (southAmerica_city_list.size() > 6) {
            showMore(6);
            initRecyclerView(rvSouthAmericaCitys, R.layout.continent_city, 3, southAmerica_city_list.subList(0, 6), tvSouthAmericaEmpty);
        } else {
            initRecyclerView(rvSouthAmericaCitys, R.layout.continent_city, 3, southAmerica_city_list, tvSouthAmericaEmpty);
        }

        if (oceania_city_list.size() > 6) {
            showMore(7);
            initRecyclerView(rvOceaniaCitys, R.layout.continent_city, 3, oceania_city_list.subList(0, 6), tvOceaniaEmpty);
        } else {
            initRecyclerView(rvOceaniaCitys, R.layout.continent_city, 3, oceania_city_list, tvOceaniaEmpty);
        }
    }


    private void showMore(int id) {
        switch (id) {
            case 1:
                tvHot.setVisibility(View.VISIBLE);
                break;
            case 2:
                tvAsia.setVisibility(View.VISIBLE);
                break;
            case 3:
                tvEurope.setVisibility(View.VISIBLE);
                break;
            case 4:
                tvAfrica.setVisibility(View.VISIBLE);
                break;
            case 5:
                tvNorthAmerica.setVisibility(View.VISIBLE);
                break;
            case 6:
                tvSouthAmerica.setVisibility(View.VISIBLE);
                break;
            case 7:
                tvOceania.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }


    @Override
    public void netChanged() {
        //网络连接
        if (null != objectCommomRecyclerAdapter)
            objectCommomRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}
