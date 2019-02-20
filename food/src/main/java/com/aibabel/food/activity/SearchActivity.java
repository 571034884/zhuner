package com.aibabel.food.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.baselibrary.utils.SharePrefUtil;
import com.aibabel.food.R;
import com.aibabel.food.base.Constant;
import com.aibabel.food.fragment.SearchFragment;
import com.aibabel.food.fragment.SearchResultFragment;

import org.litepal.util.SharedUtil;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity implements BaseActivity.FragmentListener {

    SearchFragment searchFragment;
    SearchResultFragment resultFragment;
    @BindView(R.id.tv_right1)
    TextView tvRight1;
    @BindView(R.id.sv_search)
    SearchView svSearch;
    private EditText tv;//searchview中的

    private String searchInput;
    int currentFragment = 1;
    private int page = 1;
    public final static int pageSize = 30;

    @Override
    public int getLayout(Bundle bundle) {
        return R.layout.activity_search;
    }

    @Override
    public void init() {
        initTitle();

        setFragmentListener(this::getFragmentViewId);
        addFragment(searchFragment = new SearchFragment());
        showFragment(searchFragment);
    }

    @Override
    public int getFragmentViewId() {
        return R.id.fSearch;
    }

    public void initTitle() {
        svSearch.setVisibility(View.VISIBLE);
        tvRight1.setVisibility(View.VISIBLE);

        svSearch.setIconifiedByDefault(false);
        svSearch.setFocusable(false);
        initSearch();
    }

    private void initSearch() {
        //search_close_btn
        ImageView searchButton = (ImageView) svSearch.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        //设置图片
        searchButton.setImageResource(R.mipmap.quxiao);
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
            tv = (EditText) field.get(svSearch);
            tv.setHintTextColor(getResources().getColor(R.color.titleHint));
            tv.setTextColor(getResources().getColor(R.color.titleColor));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, 24);

            Class[] clses = cls.getDeclaredClasses();
            for (Class cls_ : clses) {
                Log.e("TAG", cls_.toString());
                if (cls_.toString().endsWith("android.support.v7.widget.SearchView$SearchAutoComplete")) {
                    Class targetCls = cls_.getSuperclass().getSuperclass().getSuperclass().getSuperclass();
                    Field cuosorIconField = targetCls.getDeclaredField("mCursorDrawableRes");
                    cuosorIconField.setAccessible(true);
                    cuosorIconField.set(tv, R.drawable.cursor00);
                }
            }

            Field field1 = cls.getDeclaredField("mCollapsedIcon");
            field1.setAccessible(true);
            ImageView iv = (ImageView) field1.get(svSearch);
            iv.setImageResource(R.mipmap.sousuo);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TAG", "ERROR setCursorIcon = " + e.toString());
        }

        //搜索框展开时后面叉叉按钮的点击事件
        svSearch.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchInput = "";
                if (currentFragment == 2)
                    showFragment(searchFragment);
                return false;
            }
        });

        //搜索框文字变化监听
        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (!TextUtils.equals(searchInput, "")) {
                    searchInput = s;
                    turnToSearchResult(searchInput, page);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchInput = s;
                if (currentFragment == 2 && TextUtils.equals(s, "")) {
                    currentFragment = 1;
                    hideFragment(resultFragment);
                    showFragment(searchFragment);
                }
                return false;
            }
        });
    }

    @OnClick({R.id.tv_right1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right1:
                finish();
                break;
        }
    }

    public void setSvSearchText(String text) {
        tv.setText(text);
        tv.setSelection(text.length());
    }

    public void turnToSearchResult(String searchInput, int page) {
        if (currentFragment == 1) {
            hideFragment(searchFragment);
            if (resultFragment == null) {
                addFragment(resultFragment = new SearchResultFragment());
            }
            showFragment(resultFragment);
            Log.e(TAG, "turnToSearchResult: " + System.currentTimeMillis());
            currentFragment = 2;
        }
        Log.e(TAG, "turnToSearchResult: " + System.currentTimeMillis());
        resultFragment.getSearchResult(searchInput, Constant.CURRENT_CITY, page, pageSize);
        searchFragment.refreshHistory(searchInput);
    }
}
