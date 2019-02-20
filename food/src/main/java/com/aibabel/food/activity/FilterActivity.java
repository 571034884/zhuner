package com.aibabel.food.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.baselibrary.adapter.BaseRecyclercAdapter;
import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.baselibrary.http.BaseBean;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.baselibrary.utils.FastJsonUtil;
import com.aibabel.food.R;
import com.aibabel.food.adapter.FilterAdapter;
import com.aibabel.food.adapter.PopupWindowAdapter1;
import com.aibabel.food.adapter.PopupWindowAdapter2;
import com.aibabel.food.adapter.PopupWindowAdapter3;
import com.aibabel.food.base.Constant;
import com.aibabel.food.bean.FilterBean;
import com.aibabel.food.bean.FilterBeanOld;
import com.aibabel.food.bean.HomePageAllBean;
import com.aibabel.food.bean.KindBeanold;
import com.aibabel.food.bean.PopupWindowBean;
import com.aibabel.food.custom.BackgroundDarkPopupWindow;
import com.aibabel.food.custom.DrawableCenterTextView;
import com.aibabel.food.decoration.MyItemDecoration;
import com.zhouyou.recyclerview.XRecyclerView;
import com.zhouyou.recyclerview.adapter.BaseRecyclerViewAdapter;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewAdapter;
import com.zhouyou.recyclerview.adapter.HelperStateRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class FilterActivity extends BaseActivity implements BaseCallback<BaseBean>, BaseRecyclercAdapter.OnErrorClickListener {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvCity)
    TextView tvCity;
    @BindView(R.id.clTitle)
    ConstraintLayout clTitle;
    @BindView(R.id.dctvKind1)
    DrawableCenterTextView dctvKind1;
    @BindView(R.id.dctvKind2)
    DrawableCenterTextView dctvKind2;
    @BindView(R.id.dctvKind3)
    DrawableCenterTextView dctvKind3;
    @BindView(R.id.rvFilter)
    XRecyclerView rvFilter;
    @BindView(R.id.vLine1)
    View vLine1;
    @BindView(R.id.llKind)
    LinearLayout llKind;

    public final static String FILTER_TAG1 = "area";
    public final static String FILTER_TAG2 = "kind";
    public final static String FILTER_TAG3 = "order";

    public String area, kind, order;

    int lastType = 0;
    BackgroundDarkPopupWindow window;
    private PopupWindowAdapter1 popupWindowAdapter1;
    private PopupWindowAdapter2 popupWindowAdapter2;
    private PopupWindowAdapter3 popupWindowAdapter3;
    private KindBeanold kindBean;
    private FilterBeanOld filterBeanOld;

    FilterAdapter adapter = null;
    LinearLayoutManager layoutManager = null;
    private PopupWindowBean windowBean;
    private XRecyclerView rvKind;

    @Override
    public int getLayout(Bundle bundle) {
        return R.layout.activity_filter;
    }

    @Override
    public void init() {
        setPathParams(Constant.CURRENT_CITY + " ");
        tvCity.setText(Constant.CURRENT_CITY);
//        initKindData();
//        initFilterData();
        getIntentData();
        window = initPopupWindow(R.layout.item_kind);

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new FilterAdapter(this);
        rvFilter.setPullRefreshEnabled(false);
        rvFilter.setLoadingMoreEnabled(false);
        rvFilter.setLayoutManager(layoutManager);
        rvFilter.setNestedScrollingEnabled(false);
        rvFilter.addItemDecoration(new MyItemDecoration(10));
        rvFilter.setAdapter(adapter);

        adapter.setOnErrorClickListener(this::requestAgain);
        adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<FilterBean
                .DataBean>() {
            @Override
            public void onItemClick(View view, FilterBean.DataBean item, int position) {
                Map<String, String> map = new HashMap<>();
                map.put("商家id", item.getShopId());
                StatisticsManager.getInstance(mContext).addEventAidl("店铺跳转", map);
                startActivity(new Intent(FilterActivity.this, Html5Activity.class)
                        .putExtra("shopId", item.getShopId())
                        .putExtra("shopName", item.getShopName())
                        .putExtra("where", "filter"));
            }
        });
        getFilterData();
        getWindowData();
    }

    public void getIntentData() {
        area = getIntent().getStringExtra(FILTER_TAG1);
        kind = getIntent().getStringExtra(FILTER_TAG2);
        order = getIntent().getStringExtra(FILTER_TAG3);

        if (area != null && !TextUtils.equals("", area)) dctvKind1.setText(area);
        if (kind != null && !TextUtils.equals("", kind)) dctvKind2.setText(kind);
        if (order != null && !TextUtils.equals("", order)) dctvKind3.setText(order);
    }

    public void getFilterData() {
        adapter.setState(HelperStateRecyclerViewAdapter.STATE_LOADING);//模拟加载中
        Map<String, String> map = new HashMap<>();
        map.put("city", Constant.CURRENT_CITY);
        map.put("region", area);
        map.put("tag", kind);
        map.put("sort", order);
        OkGoUtil.<FilterBean>get(Constant.METHOD_FILTER_LIST, map, FilterBean.class, this);
    }

    public void getWindowData() {
        Map<String, String> map = new HashMap<>();
        map.put("city", Constant.CURRENT_CITY);
        map.put("region", area);
        map.put("tag", kind);
        OkGoUtil.<PopupWindowBean>get(Constant.METHOD_FILTER_WINDOW, map, PopupWindowBean.class, this);
    }


    @OnClick({R.id.ivBack, R.id.tvCity, R.id.dctvKind1, R.id.dctvKind2, R.id.dctvKind3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvCity:
                break;
            case R.id.dctvKind1:
                dctvKind1.setTextColor(Color.RED);
                dctvKind1.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.mipmap.fenlei_select), null);
                showWindow(1, llKind);
                break;
            case R.id.dctvKind2:
                dctvKind2.setTextColor(Color.RED);
                dctvKind2.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.mipmap.fenlei_select), null);
                showWindow(2, llKind);
                break;
            case R.id.dctvKind3:
                dctvKind3.setTextColor(Color.RED);
                dctvKind3.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.mipmap.fenlei_select), null);
                showWindow(3, llKind);
                break;
        }
    }

    public void showWindow(int type, View targetView) {
        if (type == 1) rvKind.setAdapter(popupWindowAdapter1);
        if (type == 2) rvKind.setAdapter(popupWindowAdapter2);
        if (type == 3) rvKind.setAdapter(popupWindowAdapter3);
        if (lastType == type && window != null && window.isShowing()) {
            window.dismiss();
            return;
        }
        window.setDarkStyle(-1);
        window.setDarkColor(Color.parseColor("#a0000000"));
        window.resetDarkPosition();
        window.darkBelow(targetView);
        window.showAsDropDown(targetView, targetView.getRight() / 2, 0);
    }

    public BackgroundDarkPopupWindow initPopupWindow(int layoutId) {
        rvKind = (XRecyclerView) LayoutInflater.from(this).inflate(layoutId, null, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        popupWindowAdapter1 = new PopupWindowAdapter1(this, area);
        popupWindowAdapter2 = new PopupWindowAdapter2(this, kind);
        popupWindowAdapter3 = new PopupWindowAdapter3(this, order);
        setWindowAdapterListener(popupWindowAdapter1, 1);
        setWindowAdapterListener(popupWindowAdapter2, 2);
        setWindowAdapterListener(popupWindowAdapter3, 3);
        rvKind.setPullRefreshEnabled(false);
        rvKind.setLoadingMoreEnabled(false);
        rvKind.setLayoutManager(layoutManager);
        rvKind.setNestedScrollingEnabled(false);
        BackgroundDarkPopupWindow popupWindow = new BackgroundDarkPopupWindow(rvKind,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams
                .WRAP_CONTENT);
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        popupWindow.setOutsideTouchable(true);//点击空白处关闭
        popupWindow.setFocusable(true);//能fou够响应外部控件事件
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dctvKind1.setTextColor(getResources().getColor(R.color.c_66));
                dctvKind2.setTextColor(getResources().getColor(R.color.c_66));
                dctvKind3.setTextColor(getResources().getColor(R.color.c_66));
                dctvKind1.setCompoundDrawablesWithIntrinsicBounds(null, null, getDrawable(R.mipmap.fenlei_normal), null);
                dctvKind2.setCompoundDrawablesWithIntrinsicBounds(null, null, getDrawable(R.mipmap.fenlei_normal), null);
                dctvKind3.setCompoundDrawablesWithIntrinsicBounds(null, null, getDrawable(R.mipmap.fenlei_normal), null);
                lastType = 0;
//                xialaType.setImageResource(R.mipmap.moreicon);
            }
        });
        return popupWindow;
    }

    public void initKindData() {
        List<KindBeanold.ItemKindBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            KindBeanold.ItemKindBean bean = new KindBeanold.ItemKindBean();
            bean.setName("银座");
            list.add(bean);
        }
        kindBean = new KindBeanold();
        kindBean.setCode("1");
        kindBean.setMsg("Success");
        kindBean.setItemKindList(list);
//        Log.e("initData: ", FastJsonUtil.changObjectToString(kindBean));

        List<String> list1 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list1.add("i+" + i);
        }
        Log.e("initData: ", FastJsonUtil.changListToString(list1));
    }

    public void initFilterData() {
        List<FilterBeanOld.FilterItemBean> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            FilterBeanOld.FilterItemBean bean = new FilterBeanOld.FilterItemBean();
            bean.setUrl("");
            bean.setName("依兰拉面");
            bean.setScore(4.0f);
            bean.setManPay("120$/人");
            bean.setTip("面食");
            list.add(bean);
        }
        filterBeanOld = new FilterBeanOld();
        filterBeanOld.setCode("1");
        filterBeanOld.setMsg("Success");
        filterBeanOld.setFilterItemBeanList(list);
        Log.e("initFilterData: ", FastJsonUtil.changObjectToString(filterBeanOld));
    }

    public void setWindowAdapterListener(HelperRecyclerViewAdapter windowAdapter, int windowType) {
        windowAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object item, int position) {
                if (windowType == 1) {
                    dctvKind1.setText(area = ((PopupWindowBean.DataBean.DistrictsBean) item).getName());
                    popupWindowAdapter1.refresh(area);
                } else if (windowType == 2) {
                    dctvKind2.setText(kind = ((PopupWindowBean.DataBean.TagBean) item).getNameTag());
                    popupWindowAdapter2.refresh(kind);
                } else if (windowType == 3) {
                    dctvKind3.setText(order = ((String) item));
                    popupWindowAdapter3.refresh(order);
                }
                window.dismiss();
                getWindowData();
                getFilterData();
            }
        });
    }

    @Override
    public void onSuccess(String s, BaseBean baseBean, String s1) {
        switch (s) {
            case Constant.METHOD_FILTER_LIST:
                adapter.setListAll(((FilterBean) baseBean).getData());
                break;
            case Constant.METHOD_FILTER_WINDOW:
                Log.e(TAG, "onSuccess: METHOD_FILTER_WINDOW");
                windowBean = (PopupWindowBean) baseBean;
                popupWindowAdapter1.setListAll(windowBean.getData().getDistricts());
                popupWindowAdapter2.setListAll(windowBean.getData().getTag());
                popupWindowAdapter3.setListAll(windowBean.getData().getSorts());
                break;
        }
    }

    @Override
    public void onError(String s, String s1, String s2) {
        adapter.setState(HelperStateRecyclerViewAdapter.STATE_ERROR);
    }

    @Override
    public void onFinsh(String s) {

    }

    @Override
    public void requestAgain() {
        getFilterData();
    }
}
