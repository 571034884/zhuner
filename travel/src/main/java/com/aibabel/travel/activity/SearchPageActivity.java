package com.aibabel.travel.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.aibabel.travel.R;
import com.aibabel.travel.bean.BaseModel;
import com.aibabel.travel.bean.CityItemBean;
import com.aibabel.travel.bean.SearchResultBean;
import com.aibabel.travel.http.ResponseCallback;
import com.aibabel.travel.utils.Constant;
import com.aibabel.travel.utils.FastJsonUtil;
import com.aibabel.travel.utils.SharePrefUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ==========================================================================================
 *
 * @Author：CreateBy 张文颖
 * @Date：2018/6/19
 * @Desc：搜索页
 *
 * ==========================================================================================
 */
public class SearchPageActivity extends BaseActivity implements ResponseCallback {

    //    @BindView(R.id.sv_search)
//    SearchView svSearch;
//    @BindView(R.id.tv_cancel)
//    TextView tvCancel;
    @BindView(R.id.f_search)
    FrameLayout fSearch;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.sv_search)
    SearchView svSearch;
    private String searchInput;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    SearchFragment searchFragment;
    SearchResultFragment searchResultFragment;
    public List<CityItemBean> hot_city_list = new ArrayList();
    int currentFragment = 1;
    private TextView tv;

    @Override
    public int initLayout() {
        return R.layout.activity_search_page;
    }

    @Override
    public void init() {
        initView();
        initTitle();
        initData();
    }


    public void initView() {
        svSearch.setIconifiedByDefault(false);
        svSearch.setFocusable(false);
        initSearch();
        showFragment(1);
    }

    public void initTitle() {
        svSearch.setVisibility(View.VISIBLE);
        tvRight.setVisibility(View.VISIBLE);

        svSearch.setIconifiedByDefault(false);
        svSearch.setFocusable(false);
        initSearch();
    }

    public void initData() {
//        初始化热门目的地

    }

    public List<CityItemBean> initHot() {
        String str = getIntent().getStringExtra("hot");
        if (!TextUtils.isEmpty(str)) {
            hot_city_list = FastJsonUtil.changeJsonToList(str, CityItemBean.class);
            SharePrefUtil.saveString(this, Constant.HOT_CITY, str);
        } else {
            String json = SharePrefUtil.getString(this, Constant.HOT_CITY, "");
            if (!TextUtils.isEmpty(json)) {
                hot_city_list = FastJsonUtil.changeJsonToList(str, CityItemBean.class);
            }
        }
        return hot_city_list;
    }


    /**
     * 展示不同的Fragment
     *
     * @param type
     */
    public void showFragment(int type) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        hideAllFragment(fragmentTransaction);
        switch (type) {
            case 1://搜索过程
                if (searchFragment == null) {
                    searchFragment = new SearchFragment();
                    fragmentTransaction.add(R.id.f_search, searchFragment);
                } else {
                    fragmentTransaction.show(searchFragment);
                }
                currentFragment = 1;
                break;
            case 2://所搜结果
                if (searchResultFragment == null) {
                    searchResultFragment = new SearchResultFragment();
                    fragmentTransaction.add(R.id.f_search, searchResultFragment);
                } else {
                    fragmentTransaction.show(searchResultFragment);
                }
                currentFragment = 2;
                break;
        }
        fragmentTransaction.commit();
    }

    /**
     * 隐藏所有的Fragment
     *
     * @param transaction
     */
    private void hideAllFragment(FragmentTransaction transaction) {
        if (searchFragment != null) {
            transaction.hide(searchFragment);
        }
        if (searchResultFragment != null) {
            transaction.hide(searchResultFragment);
        }
    }


    /**
     * 获取当前的fragment
     *
     * @return
     */
    public int getCurrentFragment() {
        return currentFragment;
    }


    private void initSearch() {
        //search_close_btn
        ImageView searchButton = (ImageView) svSearch.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        //设置图片
        searchButton.setImageResource(R.mipmap.ic_cancle);
        //更改文字颜色，光标样色
        try {
            Class cls = Class.forName("android.support.v7.widget.SearchView");

            //--指定某个私有属性,mSearchPlate是搜索框父布局的名字
            Field ownField = cls.getDeclaredField("mSearchPlate");
            //--暴力反射,只有暴力反射才能拿到私有属性
            ownField.setAccessible(true);
            View mView = (View) ownField.get(svSearch);
            //--设置背景
            mView.setBackgroundColor(Color.TRANSPARENT);

            Field field = cls.getDeclaredField("mSearchSrcTextView");
            field.setAccessible(true);
            tv = (TextView) field.get(svSearch);
            tv.setHintTextColor(getResources().getColor(R.color.titleHint));
            tv.setTextColor(getResources().getColor(R.color.color_33));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, 24);

            Class[] clses = cls.getDeclaredClasses();
            for (Class cls_ : clses) {
                Log.e("TAG", cls_.toString());
                if (cls_.toString().endsWith("android.support.v7.widget.SearchView$SearchAutoComplete")) {
                    Class targetCls = cls_.getSuperclass().getSuperclass().getSuperclass().getSuperclass();
                    Field cuosorIconField = targetCls.getDeclaredField("mCursorDrawableRes");
                    cuosorIconField.setAccessible(true);
                    cuosorIconField.set(tv, R.drawable.v_line_00);
                }
            }

            Field field1 = cls.getDeclaredField("mCollapsedIcon");
            field1.setAccessible(true);
            ImageView iv = (ImageView) field1.get(svSearch);
            iv.setImageResource(R.mipmap.ic_search);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TAG", "ERROR setCursorIcon = " + e.toString());
        }


        //搜索框展开时后面叉叉按钮的点击事件
        svSearch.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchInput = "";
                Log.e("TAG", "ERROR setCursorIcon = ");
                if (getCurrentFragment() == 2)
                    showFragment(1);
                return false;
            }
        });

        //搜索框文字变化监听
        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchInput = s;
                if (!TextUtils.equals(searchInput, "")) {

                    searchFragment.refreshHistory(searchInput);
                    if (getCurrentFragment() == 1) showFragment(2);
                    getList(searchInput);
                }
//                svSearch.clearFocus();  //可以收起键盘
//                svSearch.onActionViewCollapsed();    //可以收起SearchView视图
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchInput = s;
                if (getCurrentFragment() == 2 && TextUtils.equals(s, ""))
                    showFragment(1);
                return false;
            }
        });
    }

    public void getList(String keyword) {
        searchInput = keyword;
        if (getCurrentFragment() == 1) showFragment(2);
        Map<String, String> map = new HashMap<>();
//        map.put("cmd","searchName");
        map.put("name", keyword);
        get(SearchPageActivity.this, this, SearchResultBean.class, map, "searchName");
//        OkGoUtil.get(UrlConfig.URL_HOST,SearchPageActivity.this, SearchResultBean.class,map,0);

    }

    //当点击edittext意外位置时候 ，关闭软键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            boolean hideInputResult = isShouldHideInput(v, ev);
            Log.v("hideInputResult", "zzz-->>" + hideInputResult);
            if (hideInputResult) {
                v.clearFocus();
                InputMethodManager imm = (InputMethodManager) SearchPageActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                if (v != null) {
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof SearchView.SearchAutoComplete)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            //之前一直不成功的原因是,getX获取的是相对父视图的坐标,getRawX获取的才是相对屏幕原点的坐标！！！
            Log.v("leftTop[]", "zz--left:" + left + "--top:" + top + "--bottom:" + bottom + "--right:" + right);
            Log.v("event", "zz--getX():" + event.getRawX() + "--getY():" + event.getRawY());
            if (event.getRawX() > left && event.getRawX() < right
                    && event.getRawY() > top && event.getRawY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


    @OnClick({R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                finish();
                break;
        }
    }


    @Override
    public void onSuccess(String method, BaseModel result) {
        SearchResultBean bean = (SearchResultBean) result;

        List<SearchResultBean.DataBean> dataBeans = bean.getData();
        if (null != dataBeans && dataBeans.size() > 0) {
            searchResultFragment.refreshSearchList(searchInput, dataBeans);
        } else {
            searchResultFragment.refreshSearchList(searchInput, dataBeans);
            toastShort(getResources().getString(R.string.no_data));
        }

    }

    @Override
    public void onError() {

    }
}
