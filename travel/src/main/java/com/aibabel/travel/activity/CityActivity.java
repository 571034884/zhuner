package com.aibabel.travel.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.travel.R;
import com.aibabel.travel.adaper.Adapter_Spots;
import com.aibabel.travel.bean.BaseModel;
import com.aibabel.travel.bean.SpotBean;
import com.aibabel.travel.db.DataDAO;
import com.aibabel.travel.http.ResponseCallback;
import com.aibabel.travel.utils.CommonUtils;
import com.aibabel.travel.utils.NetworkUtils;
import com.aibabel.travel.utils.StringUtil;
import com.aibabel.travel.widgets.MyListView;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;

/**
 * ==========================================================================================
 *
 * @Author：CreateBy 张文颖
 * @Date：2018/6/19
 * @Desc：城市页
 *
 * ==========================================================================================
 */
public class CityActivity extends BaseActivity implements ResponseCallback, AdapterView.OnItemClickListener {

    TextView tvCount;
    ImageView ivCity;
    MyListView lvSpots;
    @BindView(R.id.ll_root)
    CoordinatorLayout llRoot;
    TextView tvCityName;
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

    private String id;
    private String urlCity;
    private List<SpotBean.DataBean.ResultsBean> list = new ArrayList<>();
    private SpotBean bean = new SpotBean();
    Adapter_Spots adapter;
    private String name;
    private int first;
    private boolean offline;
    private boolean isStartActivity = true;

    @Override
    public int initLayout() {
        return R.layout.activity_base;
    }

    @Override
    public void init() {
//        GlideCacheUtil.getInstance().clearImageAllCache(this);
        vsTest = findViewById(R.id.vs_test);
        vsTest.setLayoutResource(R.layout.stub_city);
        View iv_vsContent = vsTest.inflate();
        tvCount = iv_vsContent.findViewById(R.id.tv_count);
        ivCity = iv_vsContent.findViewById(R.id.iv_city);
        tvCityName = iv_vsContent.findViewById(R.id.tv_city_name);
        lvSpots = iv_vsContent.findViewById(R.id.lv_spots);
        initView();
        initTitle();
        initData();


//        ResidentNotificationHelper.sendResidentNotice(this,"","",new Intent());

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
        lvSpots.setOnItemClickListener(this);
        ivRight.setOnClickListener(this);
        tvTitle.setOnClickListener(this);
        ivLeft.setOnClickListener(this);
    }

    public void initData() {
        id = getIntent().getStringExtra("id");
        urlCity = getIntent().getStringExtra("url");
        name = getIntent().getStringExtra("name");
        offline = getIntent().getBooleanExtra("offline", false);
        first = getIntent().getIntExtra("first", 0);
        Map map1 = new HashMap();
        map1.put("p1",name);
        setPathParams(JSONObject.toJSON(map1).toString());
        if (first == 1) {
            ivLeft.setVisibility(View.GONE);
        }
        CommonUtils.setMargins(tvTitle, ivLeft.getVisibility()==View.GONE?20:0, 0, 0, 0);

//        id="135";
//        urlCity="https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2Ff5fc4ef77c20509ccbc44cab8da7543c584a717c.jpg";
//        name = "北京";
        tvCityName.setText(name + "");
        setCityImage(urlCity);


        if(offline){
            if (!TextUtils.isEmpty(name)) {
                try {
                    list = DataDAO.querySpots(name);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            if(TextUtils.equals("121",id)){
                list = StringUtil.reList(list);
            }
            adapter = new Adapter_Spots(this, list);
            lvSpots.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            tvCount.setText(list.size() + "");
        }else{
            Map<String, String> map = new HashMap<>();
            map.put("GetScenicCityIdIs", id);
            get(this, this, SpotBean.class, map, "getscenic");
        }

    }

    public void initTitle() {
        ivLeft.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        ivRight.setVisibility(View.VISIBLE);

        ivLeft.setVisibility(getIntent().getIntExtra("isback", View.VISIBLE));
        ivLeft.setImageResource(R.mipmap.ic_backb);
        tvTitle.setHint(getResources().getString(R.string.search_hint));
        tvTitle.setHintTextColor(getResources().getColor(R.color.color_66));
        tvTitle.setBackgroundResource(R.drawable.bg_search_80while);
        ivRight.setImageResource(R.mipmap.ic_home_o);

    }

//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        initView();
//        initTitle();
//        initData();
//    }

    @Override
    protected void onResume() {
        super.onResume();
        llRoot.setFocusable(true);
        llRoot.setFocusableInTouchMode(true);
        llRoot.requestFocus();
        isStartActivity = true;

    }


    private void toWorld() {
        Map map = new HashMap();
        map.put("p1",name);
        StatisticsManager.getInstance(CityActivity.this).addEventAidl(1621, map);



        Intent intent = new Intent(this, WorldActivity.class);
        startActivity(intent);
    }

    private void toSearch() {
        Map map = new HashMap();
        map.put("p1",name);
        StatisticsManager.getInstance(CityActivity.this).addEventAidl( 1622, map);

        Intent intent = new Intent(this, SearchPageActivity.class);
        startActivity(intent);
    }


    @Override
    public void onSuccess(String method, BaseModel result) {
        bean = (SpotBean) result;
        list = bean.getData().getResults();
        adapter = new Adapter_Spots(this, list);
        lvSpots.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        tvCount.setText(bean.getData().getCount() + "");

    }

    @Override
    public void onError() {

    }

    private void setCityImage(String url) {
        int color_tr = getResources().getColor(R.color.color_tr);
        RequestOptions mRequestOptions = RequestOptions.bitmapTransform(new ColorFilterTransformation(color_tr)).circleCropTransform().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).error(R.mipmap.error_c).placeholder(R.mipmap.placeholder_c);
        Glide.with(this)
                .load(url)
                .apply(mRequestOptions)
                .into(ivCity);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.iv_right:
                if (isStartActivity) {
                    toWorld();
                    initOnClickable(tvTitle, isStartActivity);
                    isStartActivity = false;
                }

                break;
            case R.id.tv_title:
                if (!NetworkUtils.isAvailable(this)) {
                    toastShort(getString(R.string.net_unavailable));
                    return;
                }
                if (isStartActivity) {
                    toSearch();
                    initOnClickable(tvTitle, isStartActivity);
                    isStartActivity = false;
                }

                break;
            default:
                break;
        }
    }

    private void initOnClickable(View holder, boolean isStartActivity) {
        if (isStartActivity) {
            holder.setClickable(true);
        } else {
            holder.setClickable(false);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        toScenic(list.get(position).getId() + "", list.get(position).getCover(), list.get(position).getSubCount() + "", list.get(position).getName(), list.get(position).getAudios().get(0).getUrl());
    }

    /**
     * @param id
     * @param url
     * @param count
     */
    private void toScenic(String id, String url, String count, String name, String audioUrl) {
        Map map = new HashMap();
        map.put("p1",name);
        StatisticsManager.getInstance(CityActivity.this).addEventAidl( 1623, map);


        Intent intent = new Intent(CityActivity.this, SpotActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("url", url);
        intent.putExtra("count", count);
        intent.putExtra("name", name);
        intent.putExtra("offline", offline);
        intent.putExtra("audioUrl", audioUrl);
        startActivity(intent);
    }
}
