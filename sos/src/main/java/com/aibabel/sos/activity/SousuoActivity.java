package com.aibabel.sos.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aibabel.sos.R;
import com.aibabel.sos.adapter.CommomRecyclerAdapter;
import com.aibabel.sos.adapter.CommonRecyclerViewHolder;
import com.aibabel.sos.adapter.SpaceItemDecoration;
import com.aibabel.sos.app.BaseActivity;
import com.aibabel.sos.app.Constans;
import com.aibabel.sos.bean.ContactSortModel;
import com.aibabel.sos.bean.SosBean;
import com.aibabel.sos.bean.SousuoBean;
import com.aibabel.sos.custom.MyRecyclerView;
import com.aibabel.sos.utils.CommonUtils;
import com.aibabel.sos.utils.PinyinComparator;
import com.aibabel.sos.utils.PinyinUtils;
import com.aibabel.sos.utils.SosDbUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SousuoActivity extends BaseActivity {

    //    @BindView(R.id.et_sousuo)
//    EditText etSousuo;
//    @BindView(R.id.iv_fanhui)
//    ImageView ivFanhui;
//    @BindView(R.id.tv_quxiao)
//    TextView tvQuxiao;
    @BindView(R.id.rv_sousuo)
    RecyclerView rvSousuo;
    @BindView(R.id.rv_edit)
    MyRecyclerView rvEdit;
    @BindView(R.id.iv_no)
    ImageView ivNo;
    @BindView(R.id.sv_search)
    SearchView svSearch;
    @BindView(R.id.tv_right1)
    TextView tvRight1;

    private String searchInput;

    private CommonAdapter<ContactSortModel> mAdapter;
    private List<ContactSortModel> list;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;

    TextView tvCity;
    TextView tvDingwei;
    TextView tvCountry;
    RecyclerView rvHeaderItem;
    private CommonAdapter<SosBean> mHeaderAdapter;
    private List<SosBean> headList;

    private CommomRecyclerAdapter<SousuoBean> editAdapter;
    private List<SousuoBean> editList = new ArrayList<>();

    SosBean dingweiBean;

    TextView tv = null;

    @Override
    public int initLayout() {
        return R.layout.activity_sousuo;
    }

    @Override
    public void init() {
        initTitle();
        initRecycleView();
    }

    public void initTitle() {
        tvRight1.setVisibility(View.VISIBLE);
        svSearch.setVisibility(View.VISIBLE);

        tvRight1.setText(getResources().getString(R.string.quxiao));
        tvRight1.setTextColor(getResources().getColor(R.color.cfe5000));
        svSearch.setQueryHint(getResources().getString(R.string.morenxianshi));
        svSearch.setIconifiedByDefault(false);
        svSearch.setFocusable(false);
        configSearchView();
    }

    private void configSearchView() {
        //search_close_btn
        ImageView searchButton = (ImageView) svSearch.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        //设置图片
        searchButton.setImageResource(R.mipmap.sousuo_quxiao);

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
            tv.setHintTextColor(getResources().getColor(R.color.gray66));
            tv.setTextColor(getResources().getColor(R.color.white));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, 24);

            Class[] clses = cls.getDeclaredClasses();
            for (Class cls_ : clses) {
                Log.e("TAG", cls_.toString());
                if (cls_.toString().endsWith("android.support.v7.widget.SearchView$SearchAutoComplete")) {
                    Class targetCls = cls_.getSuperclass().getSuperclass().getSuperclass().getSuperclass();
                    Field cuosorIconField = targetCls.getDeclaredField("mCursorDrawableRes");
                    cuosorIconField.setAccessible(true);
                    cuosorIconField.set(tv, R.drawable.serach_guangbiao);
                }
            }

            Field field1 = cls.getDeclaredField("mCollapsedIcon");
            field1.setAccessible(true);
            ImageView iv = (ImageView) field1.get(svSearch);
            iv.setImageResource(R.mipmap.sousuo_sousuo);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TAG", "ERROR setCursorIcon = " + e.toString());
        }

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("");
                searchInput = "";
                ivNo.setVisibility(View.GONE);
                editList.removeAll(editList);
                rvEdit.setVisibility(View.GONE);
                rvSousuo.setVisibility(View.VISIBLE);
            }
        });

//        //搜索框展开时后面叉叉按钮的点击事件
//        svSearch.setOnCloseListener(new SearchView.OnCloseListener() {
//            @Override
//            public boolean onClose() {
//                Toast.makeText(getApplicationContext(), "Close", Toast.LENGTH_SHORT).show();
//                searchInput = "";
//                ivNo.setVisibility(View.GONE);
//                editList.removeAll(editList);
//                rvEdit.setVisibility(View.GONE);
//                rvSousuo.setVisibility(View.VISIBLE);
//                tvRight1.setVisibility(View.GONE);
//                return false;
//            }
//        });

        //搜索框文字变化监听
        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (!TextUtils.equals(searchInput, "")) {
                    editList = SosDbUtil.mohuSousuo(searchInput);
                    editAdapter.updateData(editList);
                    rvEdit.setVisibility(View.VISIBLE);
                    rvSousuo.setVisibility(View.GONE);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.equals("")) s = " ";
                searchInput = s;
//                if (currentFragment == 2 && TextUtils.equals(s, ""))
//                    showFragment(1);
                return false;
            }
        });
    }

    public void initRecycleView() {
        headList = new ArrayList<>();
        headList = SosDbUtil.getChengshi(Constans.GUOJIA);

        if (CommonUtils.getLocalLanguage().contains("zh")||CommonUtils.getLocalLanguage().contains("en")) {
            list = filledData(SosDbUtil.getChengshi(false));
            Collections.sort(list, new PinyinComparator());
        } else list = filledData(SosDbUtil.getChengshi(true));
//        for (int i = 0; i < 6; i++) {
//            headList.add(i + "head");
//        }
        rvSousuo.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CommonAdapter<ContactSortModel>(this, R.layout.item_for_sousuo_rv, list) {
            @Override
            protected void convert(ViewHolder holder, ContactSortModel contactSortModel, int position) {
                TextView tv_place = holder.getView(R.id.tv_place);
                TextView tv_flag = holder.getView(R.id.tv_flag);
                tv_place.setText(contactSortModel.getName());
                if (contactSortModel.getSortLetters() != null && position > 1 && contactSortModel.getSortLetters().equals(list.get(position - 2).getSortLetters())) {
                    tv_flag.setVisibility(View.GONE);
                } else {
                    tv_flag.setVisibility(View.VISIBLE);
                }

                tv_flag.setText(contactSortModel.getSortLetters());
            }

        };
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(SousuoActivity.this, InformationItemActivity.class);
                intent.putExtra("cs", list.get(position - 1).getName());
                intent.putExtra("gj", list.get(position - 1).getGuojia());
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        View headView = LayoutInflater.from(this).inflate(R.layout.layout_for_sousuo_header, rvSousuo, false);
        tvCity = headView.findViewById(R.id.tv_city);
        tvDingwei = headView.findViewById(R.id.tv_dingwei);
        tvDingwei.setText(Constans.CHENGSHI);
        if (SosDbUtil.getLingshiguan(Constans.CHENGSHI).size() > 0) {
            dingweiBean = SosDbUtil.getLingshiguan(Constans.CHENGSHI).get(0);
        }
        tvDingwei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dingweiBean != null) {
                    Intent intent = new Intent(SousuoActivity.this, InformationItemActivity.class);
                    intent.putExtra("cs", dingweiBean.getCs());
                    intent.putExtra("gj", dingweiBean.getGj());
                    startActivity(intent);
                } else {
                    Toast.makeText(SousuoActivity.this, getResources().getString(R.string.toast_zanwuxinxi), Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvCountry = headView.findViewById(R.id.tv_country);
        if (headList.size() == 0) {
            tvCountry.setVisibility(View.GONE);
        }
        rvHeaderItem = headView.findViewById(R.id.rv_header_item);
        rvHeaderItem.setLayoutManager(new GridLayoutManager(this, 3));

        mHeaderAdapter = new CommonAdapter<SosBean>(this, R.layout.item_for_header_rv, headList) {
            @Override
            protected void convert(ViewHolder holder, SosBean sosBean, int position) {
                TextView tv_dingwei = holder.getView(R.id.tv_dingwei);
                tv_dingwei.setText(sosBean.getCs());
            }
        };
        mHeaderAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(SousuoActivity.this, InformationItemActivity.class);
                intent.putExtra("cs", dingweiBean.getCs());
                intent.putExtra("gj", dingweiBean.getGj());
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvHeaderItem.addItemDecoration(new SpaceItemDecoration(20));
        rvHeaderItem.setAdapter(mHeaderAdapter);

        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);
        mHeaderAndFooterWrapper.addHeaderView(headView);
//        rvSousuo.addItemDecoration(new CommonItemDecoration(getDrawable(R.drawable.divider_list)));
        rvSousuo.setAdapter(mHeaderAndFooterWrapper);

//        etSousuo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                boolean handled = false;
//                if (actionId == EditorInfo.IME_ACTION_SEARCH && !TextUtils.equals(etSousuo.getText().toString(), "")) {
//                    editList = SosDbUtil.mohuSousuo(etSousuo.getText().toString());
//                    editAdapter.updateData(editList);
//                    rvEdit.setVisibility(View.VISIBLE);
//                    rvSousuo.setVisibility(View.GONE);
//                    tvQuxiao.setVisibility(View.VISIBLE);
////                    Toast.makeText(SousuoActivity.this, "搜索", Toast.LENGTH_SHORT).show();
//                    handled = true;
//
//                    /*隐藏软键盘*/
//                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    if (inputMethodManager.isActive()) {
//                        inputMethodManager.hideSoftInputFromWindow(SousuoActivity.this.getCurrentFocus().getWindowToken(), 0);
//                    }
//                }
//                return handled;
//            }
//        });

        rvEdit.setLayoutManager(new LinearLayoutManager(this));
        editAdapter = new CommomRecyclerAdapter<SousuoBean>(SousuoActivity.this, editList, R.layout.item_for_sousuo_rv, new CommomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommonRecyclerViewHolder holder, int position) {
                Intent intent = new Intent(SousuoActivity.this, InformationItemActivity.class);
                intent.putExtra("cs", editList.get(position).getCs());
                intent.putExtra("gj", editList.get(position).getGj());
                startActivity(intent);
            }
        }, null) {
            @Override
            public void convert(CommonRecyclerViewHolder holder, SousuoBean bean, int position) {
                TextView tv_place = holder.getView(R.id.tv_place);
                TextView tv_flag = holder.getView(R.id.tv_flag);
                tv_place.setText(bean.getCs() + "(" + bean.getGj() + ")");
                initTextColor(tv_place, searchInput);
//                initTextColor(tv_place, etSousuo.getText().toString());
                tv_flag.setVisibility(View.GONE);
            }
        };
//        View view = LayoutInflater.from(this).inflate(R.layout.default_loading, rvEdit, false);
        rvEdit.setEmptyView(ivNo);
        rvEdit.setAdapter(editAdapter);
        ivNo.setVisibility(View.GONE);
    }

    private void initTextColor(TextView tv_name, String searchInput) {
        SpannableStringBuilder spannable = new SpannableStringBuilder(tv_name.getText().toString());

        int i = tv_name.getText().toString().indexOf(searchInput);//*第一个出现的索引位置
        while (i != -1) {
            spannable.setSpan(new ForegroundColorSpan(Color.RED), i, i + searchInput.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            i = tv_name.getText().toString().indexOf(searchInput, i + searchInput.length() + 1);//*从这个索引往后开始第一个出现的位置
        }
        tv_name.setText(spannable);
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
                InputMethodManager imm = (InputMethodManager) SousuoActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
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
        if (v != null && (v instanceof EditText)) {
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

    @OnClick({R.id.iv_left1, R.id.tv_right1})
//    @OnClick({R.id.iv_fanhui, R.id.et_sousuo, R.id.tv_quxiao,R.id.tv_right1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.iv_fanhui:
//                finish();
//                break;
//            case R.id.et_sousuo:
//                break;
//            case R.id.tv_quxiao:
//                ivNo.setVisibility(View.GONE);
//                editList.removeAll(editList);
//                rvEdit.setVisibility(View.GONE);
//                rvSousuo.setVisibility(View.VISIBLE);
//                tvQuxiao.setVisibility(View.GONE);
//                break;
            case R.id.tv_right1:
                finish();
                break;
        }
    }

    private List<ContactSortModel> filledData(List<SosBean> list) {
        List<ContactSortModel> mSortList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            ContactSortModel sortModel = new ContactSortModel();
            sortModel.setName(list.get(i).getCs());
            sortModel.setGuojia(list.get(i).getGj());
            if (CommonUtils.getLocalLanguage().contains("ko")) {
                sortModel.setSortLetters(list.get(i).getL());
            } else if (CommonUtils.getLocalLanguage().contains("ja")) {
                sortModel.setSortLetters(list.get(i).getL());
            } else {
                String pinyin = PinyinUtils.getPingYin(list.get(i).getCs());
                String sortString = pinyin.substring(0, 1).toUpperCase();
                if (sortString.matches("[A-Z]")) {
                    sortModel.setSortLetters(sortString.toUpperCase());
                }else {
                    Log.e( "filledData: " ,"" );
                }
            }
            mSortList.add(sortModel);
        }
        return mSortList;
    }
}
