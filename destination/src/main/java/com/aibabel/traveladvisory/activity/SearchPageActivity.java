package com.aibabel.traveladvisory.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.aibabel.traveladvisory.R;
import com.aibabel.traveladvisory.app.BaseActivity;
import com.aibabel.traveladvisory.fragment.SearchFragment;
import com.aibabel.traveladvisory.fragment.SearchResultFragment;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchPageActivity extends BaseActivity {

    @BindView(R.id.sv_search)
    SearchView svSearch;
    //    @BindView(R.id.tv_cancel)
//    TextView tvCancel;
    @BindView(R.id.tv_right1)
    TextView tvRight1;
    private String searchInput;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    SearchFragment searchFragment;
    SearchResultFragment searchResultFragment;

    int currentFragment = 1;
    private int page = 1;
    private int pageSize = 30;
    private TextView tv;

    @Override
    public int getLayout(Bundle savedInstanceState){
        return R.layout.activity_search_page;
    }

    @Override
    public void init() {
//        svSearch.setIconifiedByDefault(false);
//        svSearch.setFocusable(false);
//        initSearch();

        initTitle();
        showFragment(1);
    }

    public void initTitle() {
        svSearch.setVisibility(View.VISIBLE);
        tvRight1.setVisibility(View.VISIBLE);

        svSearch.setIconifiedByDefault(false);
        svSearch.setFocusable(false);
        initSearch();
    }

    public void showFragment(int type) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        hideAllFragment(fragmentTransaction);
        switch (type) {
            case 1://搜索过程
                if (searchFragment == null) {
                    searchFragment = new SearchFragment();
                    fragmentTransaction.add(R.id.f_search, searchFragment);
//                    fragmentTransaction.replace(R.id.f_search, searchResultFragment);
                } else {
                    fragmentTransaction.show(searchFragment);
                }
                currentFragment = 1;
                break;
            case 2://所搜结果
                if (searchResultFragment == null) {
                    searchResultFragment = new SearchResultFragment();
                    fragmentTransaction.add(R.id.f_search, searchResultFragment);
//                    fragmentTransaction.replace(R.id.f_search, searchResultFragment);
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

//    public int getCurrentFragment() {
//        return currentFragment;
//    }

    public Fragment getCurrentFragment() {
        if (currentFragment == 1) return searchFragment;
        return searchResultFragment;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setSvSearchText(String text) {
        tv.setText(text);
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
            tv = (TextView) field.get(svSearch);
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
                    cuosorIconField.set(tv, R.drawable.v_line_00);
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
                Log.e("TAG", "ERROR setCursorIcon = ");
                if (currentFragment == 2)
                    showFragment(1);
                return false;
            }
        });

        //搜索框文字变化监听
        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (!TextUtils.equals(searchInput, "")) {
                    searchInput = s;
                    if (currentFragment == 1) showFragment(2);
                    searchResultFragment.refreshSearchList(SearchPageActivity.this, searchInput, getIntent().getStringExtra("cnCityName"), page, pageSize);
                    searchFragment.refreshHistory(searchInput);

//                    Map<String, String> map = new HashMap<>();
//                    map.put("keyword", "日本");
//                    map.put("page", page + "");
//                    map.put("pageSize", pageSize + "");
//                    OkGoUtil.<FuzzyQueryBean>get(SearchPageActivity.this, Constans.METHOD_GET_MSG, map, FuzzyQueryBean.class, 1, SearchPageActivity.this);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchInput = s;
                if (currentFragment == 2 && TextUtils.equals(s, ""))
                    showFragment(1);
                return false;
            }
        });
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


    @OnClick({R.id.tv_right1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right1:
                finish();
                break;
        }
    }

}
