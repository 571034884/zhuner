package com.aibabel.currencyconversion;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aibabel.baselibrary.utils.ProviderUtils;
import com.aibabel.currencyconversion.adapter.Adapter_Coupon;
import com.aibabel.currencyconversion.app.BaseActivity;
import com.aibabel.currencyconversion.app.Constant;
import com.aibabel.currencyconversion.bean.CouponBean;
import com.aibabel.currencyconversion.bean.ExchangeRateBean;
import com.aibabel.currencyconversion.bean.NewCurrencyBean;
import com.aibabel.currencyconversion.custom.EmptyLayout;
import com.aibabel.currencyconversion.custom.PullUpDragLayout;
import com.aibabel.currencyconversion.utils.Calculate;
import com.aibabel.currencyconversion.utils.CheckFlag;
import com.aibabel.currencyconversion.utils.CommonUtils;
import com.aibabel.currencyconversion.utils.FastJsonUtil;
import com.aibabel.currencyconversion.utils.InternationalizationUtil;
import com.aibabel.currencyconversion.utils.NetUtil;
import com.aibabel.currencyconversion.utils.SharePrefUtil;
import com.aibabel.currencyconversion.utils.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.taobao.sophix.SophixManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_provider)
    TextView tvProvider;
    @BindView(R.id.iv_country_flag1)
    CircleImageView ivCountryFlag1;
    @BindView(R.id.tv_currency_name1)
    TextView tvCurrencyName1;
    @BindView(R.id.iv_xiala1)
    ImageView ivXiala1;
    @BindView(R.id.et_currency_count1)
    TextView etCurrencyCount1;
    @BindView(R.id.tv_currency_abbreviations1)
    TextView tvCurrencyAbbreviations1;
    @BindView(R.id.iv_country_flag2)
    CircleImageView ivCountryFlag2;
    @BindView(R.id.tv_currency_name2)
    TextView tvCurrencyName2;
    @BindView(R.id.iv_xiala2)
    ImageView ivXiala2;
    @BindView(R.id.et_currency_count2)
    TextView etCurrencyCount2;
    @BindView(R.id.tv_currency_abbreviations2)
    TextView tvCurrencyAbbreviations2;
    @BindView(R.id.iv_country_flag3)
    CircleImageView ivCountryFlag3;
    @BindView(R.id.tv_currency_name3)
    TextView tvCurrencyName3;
    @BindView(R.id.iv_xiala3)
    ImageView ivXiala3;
    @BindView(R.id.et_currency_count3)
    TextView etCurrencyCount3;
    @BindView(R.id.tv_currency_abbreviations3)
    TextView tvCurrencyAbbreviations3;
    @BindView(R.id.ll_zuo1)
    LinearLayout llZuo1;
    @BindView(R.id.ll_you1)
    LinearLayout llYou1;
    @BindView(R.id.ll_zuo2)
    LinearLayout llZuo2;
    @BindView(R.id.ll_you2)
    LinearLayout llYou2;
    @BindView(R.id.ll_zuo3)
    LinearLayout llZuo3;
    @BindView(R.id.ll_you3)
    LinearLayout llYou3;
    @BindView(R.id.v_cursor1)
    View vCursor1;
    @BindView(R.id.v_cursor2)
    View vCursor2;
    @BindView(R.id.v_cursor3)
    View vCursor3;
    @BindView(R.id.rl_bottom_view)
    RelativeLayout rlBottomView;
    @BindView(R.id.iv_guanbi)
    ImageView ivGuanbi;
    @BindView(R.id.clGuanbi)
    ConstraintLayout clGuanbi;
    @BindView(R.id.pull_up_drag_layout)
    PullUpDragLayout pullUpDragLayout;
    @BindView(R.id.ll_mask)
    LinearLayout llMask;
    RecyclerView rvCoupon;
    ConstraintLayout cl_footer;
    EmptyLayout el_error;
    ImageView iv_up;

    List<CouponBean.DataBean> list = new ArrayList<>();


    private int screenWidth;
    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener;
    private Adapter_Coupon adapterCoupon;


    private int[] idNum = {R.id.tv_0, R.id.tv_1, R.id.tv_2, R.id.tv_3, R.id.tv_4, R.id.tv_5, R.id.tv_6, R.id.tv_7, R.id.tv_8, R.id.tv_9};  //数字Number输入
    private int[] idCal = {R.id.tv_add, R.id.tv_subtraction, R.id.tv_multiplication, R.id.tv_division, R.id.tv_dot};  //运算符
    private TextView[] buttonsCal = new TextView[idCal.length];
    private TextView[] buttonsNum = new TextView[idNum.length];
    private TextView buttonClear;  // AC
    /**
     * 当前以哪个币种为基准
     */
    private int which;
    private boolean isLastNumber = false;
    private boolean isLastYunsuanfu = false;

    private static final Uri CONTENT_URI = Uri.parse("content://com.aibabel.locationservice.provider.AibabelProvider/aibabel_location");
    private String countryName = "";
    private String ips = "";
    private String key = "";

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }

    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
//        initView();
//        rexiufu();
//        String guangbi = getIntent().getStringExtra("from");
//        if (guangbi != null && guangbi.equals("food")) {
        clGuanbi.setVisibility(View.VISIBLE);
        el_error = findViewById(R.id.el_error);
        iv_up = findViewById(R.id.iv_up);
        //初始化RecyclerVie
        initRecyclerView();
//        } else {
//            clGuanbi.setVisibility(View.GONE);
//        }


        try {
            Cursor cursor = getContentResolver().query(CONTENT_URI, null, null, null, null);
            cursor.moveToFirst();
            countryName = cursor.getString(2);
            ips = cursor.getString(cursor.getColumnIndex("ips"));
            if (countryName.equals("中国")) {
                key = "中国_" + getPackageName() + "_joner";
            } else {
                key = "default_" + getPackageName() + "_joner";
            }
            JSONObject jsonObject = new JSONObject(ips);
            JSONArray jsonArray = new JSONArray(jsonObject.getString(key));
            Constant.IP_PORT = jsonArray.getJSONObject(0).get("domain").toString();
            cursor.close();
        } catch (Exception e) {

        }

        initEvent();
        getCurrentExchangeRate();
        getCouponFromServer(countryName);
        //获取屏幕宽度
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        screenWidth = outMetrics.widthPixels;
        //监听控件宽度变化
        etCurrencyCount1.getViewTreeObserver().addOnGlobalLayoutListener(new MyGlobalLayoutListener(llZuo1, llYou1, tvCurrencyAbbreviations1, ivXiala1));
        etCurrencyCount2.getViewTreeObserver().addOnGlobalLayoutListener(new MyGlobalLayoutListener(llZuo2, llYou2, tvCurrencyAbbreviations2, ivXiala2));
        etCurrencyCount3.getViewTreeObserver().addOnGlobalLayoutListener(new MyGlobalLayoutListener(llZuo3, llYou3, tvCurrencyAbbreviations3, ivXiala3));

        String city = ProviderUtils.getInfo(ProviderUtils.COLUMN_CITY);
        if (city.equals("日本") || city.equals("泰国")){
            rlBottomView.setVisibility(View.VISIBLE);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
            Date date = new Date(System.currentTimeMillis());
            String currDate = simpleDateFormat.format(date);
            String currencyMask = SharePrefUtil.getString(mContext,"currencyMask","");
            String currencyMaskIndex = SharePrefUtil.getString(mContext,"currencyMaskIndex","");
            if (TextUtils.isEmpty(currencyMask) && TextUtils.isEmpty(currencyMaskIndex)){
                //1.第一天第一次打开
                llMask.setVisibility(View.VISIBLE);
                SharePrefUtil.saveString(mContext,"currencyMask",currDate);
                SharePrefUtil.saveString(mContext,"currencyMaskIndex","1");
            }else if (currencyMaskIndex.equals("1")){
                if (currencyMask.equals(currDate)){
                    //2.第一天第N次打开
                    llMask.setVisibility(View.GONE);
                }else{
                    //3.第二天第一次打开
                    llMask.setVisibility(View.VISIBLE);
                    SharePrefUtil.saveString(mContext,"currencyMask",currDate);
                    SharePrefUtil.saveString(mContext,"currencyMaskIndex","2");
                }
            }else if (currencyMaskIndex.equals("2")){
                //4.第二天第N次打开
                llMask.setVisibility(View.GONE);
            }
        }else {
            rlBottomView.setVisibility(View.GONE);
            llMask.setVisibility(View.GONE);
        }
    }

    private void initView() {
        tvCurrencyAbbreviations1 = findViewById(R.id.tv_currency_abbreviations1);
        tvCurrencyAbbreviations2 = findViewById(R.id.tv_currency_abbreviations2);
        tvCurrencyAbbreviations3 = findViewById(R.id.tv_currency_abbreviations3);
        tvCurrencyName1 = findViewById(R.id.tv_currency_name1);
        tvCurrencyName2 = findViewById(R.id.tv_currency_name2);
        tvCurrencyName3 = findViewById(R.id.tv_currency_name3);
        tvProvider = findViewById(R.id.tv_provider);
        ivCountryFlag1 = findViewById(R.id.iv_country_flag1);
        ivCountryFlag2 = findViewById(R.id.iv_country_flag2);
        ivCountryFlag3 = findViewById(R.id.iv_country_flag3);
        ivGuanbi = findViewById(R.id.iv_guanbi);
        ivXiala1 = findViewById(R.id.iv_xiala1);
        ivXiala2 = findViewById(R.id.iv_xiala2);
        ivXiala3 = findViewById(R.id.iv_xiala3);
        clGuanbi = findViewById(R.id.clGuanbi);
        llYou1 = findViewById(R.id.ll_you1);
        llYou2 = findViewById(R.id.ll_you2);
        llYou3 = findViewById(R.id.ll_you3);
        llZuo1 = findViewById(R.id.ll_zuo1);
        llYou2 = findViewById(R.id.ll_zuo2);
        llYou3 = findViewById(R.id.ll_zuo3);
        vCursor1 = findViewById(R.id.v_cursor1);
        vCursor2 = findViewById(R.id.v_cursor2);
        vCursor3 = findViewById(R.id.v_cursor3);
    }


    @OnClick(R.id.iv_guanbi)
    public void onViewClicked() {
        finish();
    }


    public class MyGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {

        View view1, view2, view3, view4;

        public MyGlobalLayoutListener(View view1, View view2, View view3, View view4) {
            this.view1 = view1;
            this.view2 = view2;
            this.view3 = view3;
            this.view4 = view4;
        }

        @Override
        public void onGlobalLayout() {
            if (view1.getWidth() + view2.getWidth() + 20 > screenWidth) {
                view3.setVisibility(View.GONE);
                view4.setVisibility(View.GONE);
            }
        }
    }

    public void initEvent() {
        which = 1;
        buttonClear = findViewById(R.id.tv_ac);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s;
                String next;
                String next_last;
                switch (which) {
                    case 1:
                        s = etCurrencyCount1.getText().toString();
                        if (s.length() > 1) {
                            next = s.substring(0, s.length() - 1);
                            next_last = next.substring(next.length() - 1, next.length());
                            etCurrencyCount1.setText(next);
                            if (is0_9(next_last)) {
                                etCurrencyCount2.setText(bigDecimalDo(new Calculate(etCurrencyCount1.getText().toString()).str.toString(), Constant.CURRENCY_VALUE_1_TO_2));
                                etCurrencyCount3.setText(bigDecimalDo(new Calculate(etCurrencyCount1.getText().toString()).str.toString(), Constant.CURRENCY_VALUE_1_TO_3));
                            }
                        } else if (s.length() > 0) {
                            resetInit("0");
                        }

                        break;
                    case 2:
                        s = etCurrencyCount2.getText().toString();
                        if (s.length() > 1) {
                            next = s.substring(0, s.length() - 1);
                            next_last = next.substring(next.length() - 1, next.length());
                            etCurrencyCount2.setText(next);
                            if (is0_9(next_last)) {
                                etCurrencyCount1.setText(bigDecimalDo(new Calculate(etCurrencyCount2.getText().toString()).str.toString(), Constant.CURRENCY_VALUE_2_TO_1));
                                etCurrencyCount3.setText(bigDecimalDo(new Calculate(etCurrencyCount2.getText().toString()).str.toString(), Constant.CURRENCY_VALUE_2_TO_3));
                            }
                        } else if (s.length() > 0) {
                            resetInit("0");
                        }

                        break;
                    case 3:
                        s = etCurrencyCount3.getText().toString();
                        if (s.length() > 1) {
                            next = s.substring(0, s.length() - 1);
                            next_last = next.substring(next.length() - 1, next.length());
                            etCurrencyCount3.setText(next);
                            if (is0_9(next_last)) {
                                etCurrencyCount1.setText(bigDecimalDo(new Calculate(etCurrencyCount3.getText().toString()).str.toString(), Constant.CURRENCY_VALUE_3_TO_1));
                                etCurrencyCount2.setText(bigDecimalDo(new Calculate(etCurrencyCount3.getText().toString()).str.toString(), Constant.CURRENCY_VALUE_3_TO_2));
                            }
                        } else if (s.length() > 0) {
                            resetInit("0");
                        }

                        break;
                }
            }
        });
        buttonClear.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                resetInit("0");
                return true;
            }
        });
        /**
         * 注册单击事件
         */
        for (int idcal = 0; idcal < idCal.length; idcal++) {
            buttonsCal[idcal] = findViewById(idCal[idcal]);
            buttonsCal[idcal].setOnClickListener(new CalOnClick(buttonsCal[idcal].getText().toString()));
        }
        for (int i = 0; i < idNum.length; i++) {
            buttonsNum[i] = findViewById(idNum[i]);
            buttonsNum[i].setOnClickListener(new NumberOnClick(buttonsNum[i].getText().toString())
            );
        }
    }

    /**
     * 获取当前税率
     */
    public void getCurrentExchangeRate() {
        //当前系统语言
        if (!TextUtils.equals(InternationalizationUtil.getNativeLanguage(), "")) {
            Constant.NATIVE_LANGUAGE = InternationalizationUtil.getNativeLanguage();
        } else {
            Constant.NATIVE_LANGUAGE = "CN";
        }

        //币种缩写
        Constant.CURRENCY_ABBREVIATION_VALUE_1 = SharePrefUtil.getString(this, Constant.CURRENCY_ABBREVIATION_KEY_1, "CNY");
        Constant.CURRENCY_ABBREVIATION_VALUE_2 = SharePrefUtil.getString(this, Constant.CURRENCY_ABBREVIATION_KEY_2, "USD");
        Constant.CURRENCY_ABBREVIATION_VALUE_3 = SharePrefUtil.getString(this, Constant.CURRENCY_ABBREVIATION_KEY_3, "JPY");
        //币种名称
        NewCurrencyBean childBean1 = FastJsonUtil.changeJsonToBean(SharePrefUtil.getString(this, Constant.CURRENCY_NAME_KEY_1, Constant.CURRENCY_NAME_MOREN_1), NewCurrencyBean
                .class);
        NewCurrencyBean childBean2 = FastJsonUtil.changeJsonToBean(SharePrefUtil.getString(this, Constant.CURRENCY_NAME_KEY_2, Constant.CURRENCY_NAME_MOREN_2), NewCurrencyBean
                .class);
        NewCurrencyBean childBean3 = FastJsonUtil.changeJsonToBean(SharePrefUtil.getString(this, Constant.CURRENCY_NAME_KEY_3, Constant.CURRENCY_NAME_MOREN_3), NewCurrencyBean
                .class);
        Constant.CURRENCY_NAME_VALUE_1 = InternationalizationUtil.getCurrentText(childBean1);
        Constant.CURRENCY_NAME_VALUE_2 = InternationalizationUtil.getCurrentText(childBean2);
        Constant.CURRENCY_NAME_VALUE_3 = InternationalizationUtil.getCurrentText(childBean3);
        //币种缩写赋值
        tvCurrencyAbbreviations1.setText(Constant.CURRENCY_ABBREVIATION_VALUE_1);
        tvCurrencyAbbreviations2.setText(Constant.CURRENCY_ABBREVIATION_VALUE_2);
        tvCurrencyAbbreviations3.setText(Constant.CURRENCY_ABBREVIATION_VALUE_3);
        //国旗赋值
        setCountryFlag(ivCountryFlag1, Constant.CURRENCY_ABBREVIATION_VALUE_1);
        setCountryFlag(ivCountryFlag2, Constant.CURRENCY_ABBREVIATION_VALUE_2);
        setCountryFlag(ivCountryFlag3, Constant.CURRENCY_ABBREVIATION_VALUE_3);
        //币种名称赋值
        tvCurrencyName1.setText(Constant.CURRENCY_NAME_VALUE_1);
        tvCurrencyName2.setText(Constant.CURRENCY_NAME_VALUE_2);
        tvCurrencyName3.setText(Constant.CURRENCY_NAME_VALUE_3);
        if (NetUtil.isNetworkAvailable(MainActivity.this)) {
            GetRequest<String> getRequest = OkGo.<String>get(Constant.IP_PORT + Constant.URL + Constant.CURRENCY_ABBREVIATION_VALUE_1 + "," + Constant
                    .CURRENCY_ABBREVIATION_VALUE_2 + "," + Constant.CURRENCY_ABBREVIATION_VALUE_3).tag(this);
            getRequest.params("sn", CommonUtils.getSN());
            getRequest.params("sysLanguage", CommonUtils.getSN());
            getRequest.params("sl", CommonUtils.getLocalLanguage());
            getRequest.params("no", CommonUtils.getRandom());
            getRequest.execute(new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    try {
                        Constant.CURRENCY_JSON_VALUE = response.body();
                        SharePrefUtil.saveString(MainActivity.this, Constant.CURRENCY_JSON_KEY, response.body());
                        setResult(Constant.CURRENCY_JSON_VALUE);
                    } catch (Exception e) {

                    }
                }

                @Override
                public void onError(Response<String> response) {
                    super.onError(response);
                    Log.e("currency_main", "onError");
                    Constant.CURRENCY_JSON_VALUE = SharePrefUtil.getString(MainActivity.this, Constant.CURRENCY_JSON_KEY, "");
                    if (!TextUtils.equals(Constant.CURRENCY_JSON_VALUE, "")) {
                        setResult(Constant.CURRENCY_JSON_VALUE);
                    } else {
                        Toast.makeText(MainActivity.this, getResources().getString(R.string.toast_zanwuxinxi), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Constant.CURRENCY_JSON_VALUE = SharePrefUtil.getString(MainActivity.this, Constant.CURRENCY_JSON_KEY, "");
            if (!TextUtils.equals(Constant.CURRENCY_JSON_VALUE, "")) {
                setResult(Constant.CURRENCY_JSON_VALUE);
            } else {
                Toast.makeText(MainActivity.this, getResources().getString(R.string.toast_zanwuxinxi), Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void setResult(String json) {
        List<ExchangeRateBean> exchangeRateBeanList = FastJsonUtil.changeJsonToList(json, ExchangeRateBean.class);
        List<ExchangeRateBean.ToBean> toBeans0 = exchangeRateBeanList.get(0).getTo();
        if (toBeans0 != null) {
            Constant.CURRENCY_VALUE_1_TO_2 = toBeans0.get(0).getMid();
            Constant.CURRENCY_VALUE_1_TO_3 = toBeans0.get(1).getMid();
        }
        List<ExchangeRateBean.ToBean> toBeans1 = exchangeRateBeanList.get(1).getTo();
        if (toBeans1 != null) {
            Constant.CURRENCY_VALUE_2_TO_1 = toBeans1.get(0).getMid();
            Constant.CURRENCY_VALUE_2_TO_3 = toBeans1.get(1).getMid();
        }
        List<ExchangeRateBean.ToBean> toBeans2 = exchangeRateBeanList.get(2).getTo();
        if (toBeans2 != null) {
            Constant.CURRENCY_VALUE_3_TO_1 = toBeans2.get(0).getMid();
            Constant.CURRENCY_VALUE_3_TO_2 = toBeans2.get(1).getMid();
        }
        Constant.CURRENCY_TIME = exchangeRateBeanList.get(0).getTs();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //屏幕模式显示
                resetInit("0");
                try {
                    tvProvider.setText(getResources().getString(R.string.provided_update) + " " + new SimpleDateFormat("yyyy-MM-dd").format(new Date(new Long(Constant.CURRENCY_TIME) * 1000)));
                } catch (Exception e) {

                }
            }
        });
    }

    @OnClick({R.id.ll_zuo1, R.id.ll_you1, R.id.ll_zuo2, R.id.ll_you2, R.id.ll_zuo3, R.id.ll_you3})
    public void onViewClicked(View view) {
        Intent intent = new Intent(this, ChooseCurrencyActivity.class);
        switch (view.getId()) {
            case R.id.ll_zuo1:
                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    addStatisticsEvent("currency_main1", null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/
                if (CommonUtils.isAvailable(MainActivity.this))
                    startActivityForResult(intent, 1);
                else
                    ToastUtil.showShort(MainActivity.this, R.string.toast_wuwangluo);
                break;
            case R.id.ll_zuo2:
                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    addStatisticsEvent("currency_main2", null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/
                if (CommonUtils.isAvailable(MainActivity.this))
                    startActivityForResult(intent, 2);
                else
                    ToastUtil.showShort(MainActivity.this, R.string.toast_wuwangluo);
                break;
            case R.id.ll_zuo3:
                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    addStatisticsEvent("currency_main3", null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/
                if (CommonUtils.isAvailable(MainActivity.this))
                    startActivityForResult(intent, 3);
                else
                    ToastUtil.showShort(MainActivity.this, R.string.toast_wuwangluo);
                break;
            case R.id.ll_you1:
                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    addStatisticsEvent("currency_main4", null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/
                llYou1.setEnabled(false);
                llYou2.setEnabled(true);
                llYou3.setEnabled(true);
                //当前点击变色，其余为白色
                which = 1;
                etCurrencyCount1.setTextColor(Color.parseColor("#fe5000"));
                etCurrencyCount2.setTextColor(Color.parseColor("#ffffff"));
                etCurrencyCount3.setTextColor(Color.parseColor("#ffffff"));
                etCurrencyCount1.setHintTextColor(Color.parseColor("#fe5000"));
                etCurrencyCount2.setHintTextColor(Color.parseColor("#ffffff"));
                etCurrencyCount3.setHintTextColor(Color.parseColor("#ffffff"));
                resetInit("0");
                break;
            case R.id.ll_you2:
                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    addStatisticsEvent("currency_main5", null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/
                llYou1.setEnabled(true);
                llYou2.setEnabled(false);
                llYou3.setEnabled(true);
                //当前点击变色，其余为白色
                which = 2;
                etCurrencyCount1.setTextColor(Color.parseColor("#ffffff"));
                etCurrencyCount2.setTextColor(Color.parseColor("#fe5000"));
                etCurrencyCount3.setTextColor(Color.parseColor("#ffffff"));
                etCurrencyCount1.setHintTextColor(Color.parseColor("#ffffff"));
                etCurrencyCount2.setHintTextColor(Color.parseColor("#fe5000"));
                etCurrencyCount3.setHintTextColor(Color.parseColor("#ffffff"));
                resetInit("0");
                break;
            case R.id.ll_you3:
                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    addStatisticsEvent("currency_main6", null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/
                llYou1.setEnabled(true);
                llYou2.setEnabled(true);
                llYou3.setEnabled(false);
                //当前点击变色，其余为白色
                which = 3;
                etCurrencyCount1.setTextColor(Color.parseColor("#ffffff"));
                etCurrencyCount2.setTextColor(Color.parseColor("#ffffff"));
                etCurrencyCount3.setTextColor(Color.parseColor("#fe5000"));
                etCurrencyCount1.setHintTextColor(Color.parseColor("#ffffff"));
                etCurrencyCount2.setHintTextColor(Color.parseColor("#ffffff"));
                etCurrencyCount3.setHintTextColor(Color.parseColor("#fe5000"));
                resetInit("0");
                break;
        }
    }

    //继承OnClick接口
    class NumberOnClick implements View.OnClickListener {
        String Msg;

        /**
         * @param msg 点击按钮传入字符
         */
        public NumberOnClick(String msg) {
            Msg = msg;
        }

        @Override
        public void onClick(View v) {
            switch (which) {
                case 1:

                    etCurrencyCount1.setText(canInput(etCurrencyCount1.getText().toString(), Msg));
                    etCurrencyCount2.setText(bigDecimalDo(new Calculate(etCurrencyCount1.getText().toString()).str.toString(), Constant.CURRENCY_VALUE_1_TO_2));
                    etCurrencyCount3.setText(bigDecimalDo(new Calculate(etCurrencyCount1.getText().toString()).str.toString(), Constant.CURRENCY_VALUE_1_TO_3));
                    break;
                case 2:
                    etCurrencyCount2.setText(canInput(etCurrencyCount2.getText().toString(), Msg));
                    etCurrencyCount1.setText(bigDecimalDo(new Calculate(etCurrencyCount2.getText().toString()).str.toString(), Constant.CURRENCY_VALUE_2_TO_1));
                    etCurrencyCount3.setText(bigDecimalDo(new Calculate(etCurrencyCount2.getText().toString()).str.toString(), Constant.CURRENCY_VALUE_2_TO_3));
                    break;
                case 3:
                    etCurrencyCount3.setText(canInput(etCurrencyCount3.getText().toString(), Msg));
                    etCurrencyCount1.setText(bigDecimalDo(new Calculate(etCurrencyCount3.getText().toString()).str.toString(), Constant.CURRENCY_VALUE_3_TO_1));
                    etCurrencyCount2.setText(bigDecimalDo(new Calculate(etCurrencyCount3.getText().toString()).str.toString(), Constant.CURRENCY_VALUE_3_TO_2));
                    break;
            }
        }
    }

    class CalOnClick implements View.OnClickListener {
        String Msg;
        String[] calSymbol = {"+", "-", "×", "÷", "."};

        public CalOnClick(String msg) {
            Msg = msg;
        }

        @Override
        public void onClick(View v) {
            addStatisticsEvent("currency_main7", null);

            switch (which) {
                case 1:
                    etCurrencyCount1.setText(canInput(etCurrencyCount1.getText().toString(), Msg));
                    break;
                case 2:
                    etCurrencyCount2.setText(canInput(etCurrencyCount2.getText().toString(), Msg));
                    break;
                case 3:
                    etCurrencyCount3.setText(canInput(etCurrencyCount3.getText().toString(), Msg));
                    break;
            }
        }
    }

    public void cal(String Msg, String[] calSymbol, TextView editText) {
        if (!editText.getText().toString().contains(".")) {
            if (!isLastNumber && editText.getText().toString().length() > 1) {
                //上一位运算符被替换替换
                editText.setText(editText.getText().toString().substring(0, editText.getText().toString().length() - 1) + Msg);
            } else {
                // 检查是否运算符重复输入
                for (int i = 0; i < calSymbol.length; i++) {
                    if (Msg.equals(calSymbol[i])) {
                        if (editText.getText().toString().split("")
                                [editText.getText().toString().split("").length - 1].equals(calSymbol[i])) {
                            Msg = "";
                        }
                    }
                }
                if (editText.getText().length() != 0) {
                    editText.append(Msg);
                }
            }
        } else if (editText.getText().toString().lastIndexOf(".") != editText.getText().toString().length() - 1) {
            if (!isLastNumber && editText.getText().toString().length() > 1) {
                //上一位运算符被替换替换
                editText.setText(editText.getText().toString().substring(0, editText.getText().toString().length() - 1) + Msg);
            } else {
                // 检查是否运算符重复输入
                for (int i = 0; i < calSymbol.length; i++) {
                    if (Msg.equals(calSymbol[i])) {
                        if (editText.getText().toString().split("")
                                [editText.getText().toString().split("").length - 1].equals(calSymbol[i])) {
                            Msg = "";
                        }
                    }
                }
                if (editText.getText().length() != 0) {
                    editText.append(Msg);
                }
            }
        }


    }

    /**
     * 金融相关的东西(例:银行钱的小数,商品价格小数,实验小数)
     *
     * @param str1
     * @param val
     * @return
     */
    public String bigDecimalDo(String str1, double val) {
        String result = "";
        String str2 = new BigDecimal(val).toString();
        try {
            BigDecimal bd1 = new BigDecimal(str1);
            BigDecimal bd2 = new BigDecimal(str2);
            result = bd1.multiply(bd2).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        } catch (Exception e) {
            resetInit("0");
        }
        return result;
    }

    public void resetInit(String morenzhi) {
        switch (which) {
            case 1:
                etCurrencyCount1.setHint(morenzhi);
                etCurrencyCount2.setHint(bigDecimalDo(new Calculate(etCurrencyCount1.getHint().toString()).str.toString(), Constant.CURRENCY_VALUE_1_TO_2));
                etCurrencyCount3.setHint(bigDecimalDo(new Calculate(etCurrencyCount1.getHint().toString()).str.toString(), Constant.CURRENCY_VALUE_1_TO_3));
                startFlick(vCursor1);
                stopFlick(vCursor2);
                stopFlick(vCursor3);
                break;
            case 2:
                etCurrencyCount2.setHint(morenzhi);
                etCurrencyCount1.setHint(bigDecimalDo(new Calculate(etCurrencyCount2.getHint().toString()).str.toString(), Constant.CURRENCY_VALUE_2_TO_1));
                etCurrencyCount3.setHint(bigDecimalDo(new Calculate(etCurrencyCount2.getHint().toString()).str.toString(), Constant.CURRENCY_VALUE_2_TO_3));
                stopFlick(vCursor1);
                startFlick(vCursor2);
                stopFlick(vCursor3);
                break;
            case 3:
                etCurrencyCount3.setHint(morenzhi);
                etCurrencyCount1.setHint(bigDecimalDo(new Calculate(etCurrencyCount3.getHint().toString()).str.toString(), Constant.CURRENCY_VALUE_3_TO_1));
                etCurrencyCount2.setHint(bigDecimalDo(new Calculate(etCurrencyCount3.getHint().toString()).str.toString(), Constant.CURRENCY_VALUE_3_TO_2));
                stopFlick(vCursor1);
                stopFlick(vCursor2);
                startFlick(vCursor3);
                break;
        }
        etCurrencyCount1.setText("");
        etCurrencyCount2.setText("");
        etCurrencyCount3.setText("");
        tvCurrencyAbbreviations1.setVisibility(View.VISIBLE);
        tvCurrencyAbbreviations2.setVisibility(View.VISIBLE);
        tvCurrencyAbbreviations3.setVisibility(View.VISIBLE);
        ivXiala1.setVisibility(View.VISIBLE);
        ivXiala2.setVisibility(View.VISIBLE);
        ivXiala3.setVisibility(View.VISIBLE);
    }

    /**
     * 设置国旗
     *
     * @param view
     * @param currencyAbbreviation 币种缩写
     */
    public void setCountryFlag(ImageView view, String currencyAbbreviation) {
        view.setImageResource(CheckFlag.getFlag(currencyAbbreviation));
    }

    /**
     * 开启View闪烁效果
     */
    private void startFlick(View view) {
        if (null == view) {
            return;
        }
        view.setVisibility(View.VISIBLE);
        Animation alphaAnimation = new AlphaAnimation(1, 0f);
        alphaAnimation.setDuration(500);
        alphaAnimation.setInterpolator(new LinearInterpolator());
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        view.startAnimation(alphaAnimation);
    }

    /**
     * 关闭View闪烁效果
     *
     * @param view
     */
    private void stopFlick(View view) {
        view.setVisibility(View.GONE);
        view.clearAnimation();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 666) {
//            String xuanzhongName = data.getStringExtra("xuanzhongName");
            NewCurrencyBean childBean = (NewCurrencyBean) data.getSerializableExtra("xuanzhongName");
            String xuanzhongName = InternationalizationUtil.getCurrentText(childBean);
            String xuanzhongAbbreviation = data.getStringExtra("xuanzhongAbbreviation");
            if (requestCode == 1) {
                SharePrefUtil.saveString(this, Constant.CURRENCY_ABBREVIATION_KEY_1, xuanzhongAbbreviation);
                SharePrefUtil.saveString(this, Constant.CURRENCY_NAME_KEY_1, FastJsonUtil.changObjectToString(childBean));
            } else if (requestCode == 2) {
                SharePrefUtil.saveString(this, Constant.CURRENCY_ABBREVIATION_KEY_2, xuanzhongAbbreviation);
                SharePrefUtil.saveString(this, Constant.CURRENCY_NAME_KEY_2, FastJsonUtil.changObjectToString(childBean));
            } else if (requestCode == 3) {
                SharePrefUtil.saveString(this, Constant.CURRENCY_ABBREVIATION_KEY_3, xuanzhongAbbreviation);
                SharePrefUtil.saveString(this, Constant.CURRENCY_NAME_KEY_3, FastJsonUtil.changObjectToString(childBean));
            }
            getCurrentExchangeRate();
        }
    }

    public String canInput(String string, String next) {
        if (string.equals("")) {
            if (isOperator(next) || is0(next))
                return string;
            else if (isDian(next))
                return 0 + next;
            else
                return string + next;
        } else {
            String regular = "[\\+\\-×\\÷]";
            String[] strings = string.split(regular);
            String lastNumber = strings[strings.length - 1];
            String last = string.charAt(string.length() - 1) + "";
            String last2 = "";
            if (string.length() > 2)
                last2 = string.charAt(string.length() - 2) + "";
            if (string.length() > 2 && last2.equals("÷") && is0(last)) {
                if (isDian(next))
                    return string + next;
                else if (is0_9(next))
                    return string.substring(0, string.length() - 1) + next;
                else
                    return string;
            } else if (isOperator(last)) {
                if (isOperator(next))
                    return string;
                else if (isDian(next))
                    return string + "0" + next;
                else
                    return string + next;
            } else if (isDian(last)) {
                if (is0_9(next))
                    return string + next;
                else
                    return string;
            } else {
                String[] lastArray = lastNumber.split("\\.");
                if (lastArray.length > 1) {
                    if (lastArray[1].length() > 3) {
                        if (isOperator(next))
                            return string + next;
                        else
                            return string;
                    } else if (isDian(next))
                        return string;
                    else
                        return string + next;
                } else {
                    if (isDian(last) && !isOperator(next))
                        return string + next;
                    else if (lastNumber.length() > 12) {
                        if (isOperator(next) || isDian(next))
                            return string + next;
                        else
                            return string;
                    } else {
                        return string + next;
                    }
                }
            }
        }
    }

    public boolean isOperator(String s) {
        return Pattern.matches(".*(\\+|\\-|×|\\÷).*", s);
    }

    public boolean isDianOr0(String s) {
        return Pattern.matches(".*(\\.|0).*", s);
    }

    public boolean isDian(String s) {
        return Pattern.matches(".*(\\.).*", s);
    }

    public boolean is0(String s) {
        return Pattern.matches(".*(0).*", s);
    }

    public boolean is0_9(String s) {
        return Pattern.matches(".*(0|1|2|3|4|5|6|7|8|9).*", s);
    }


    /**
     * @================================================================================
     * @修改人：张文颖
     * @修改时间：2019年4月19日 15:46:28
     * @修改内容：添加优惠券信息
     * @================================================================================
     */


    private void initMasking() {
        boolean isFirst = SharePrefUtil.getBoolean(this, "isFirst", true);
        if (isFirst) {
            llMask.setVisibility(View.VISIBLE);
            SharePrefUtil.saveBoolean(this, "isFirst", false);
        }
    }


    private void initRecyclerView() {

        rvCoupon = findViewById(R.id.rv_coupon);
        //横向RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        rvCoupon.setLayoutManager(layoutManager);
        adapterCoupon = new Adapter_Coupon(R.layout.item_coupon, new ArrayList<CouponBean.DataBean>());
        rvCoupon.setAdapter(adapterCoupon);
        View view = getFooterView();
        adapterCoupon.addFooterView(view);
        adapterCoupon.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, PhotoViewActivity.class);
                intent.putExtra("photo_img", list.get(position).getCouponData().getBasicimage());
                startActivity(intent);

            }
        });
        cl_footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toShop();
            }
        });

        iv_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pullUpDragLayout.toggleBottomView();
            }
        });

        pullUpDragLayout.setOnStateListener(new PullUpDragLayout.OnStateListener() {
            @Override
            public void open() {
                iv_up.setImageResource(R.mipmap.ic_down);
            }

            @Override
            public void close() {
                iv_up.setImageResource(R.mipmap.ic_up);
            }
        });


        /**
         * 重新加载
         */
        el_error.setOnBtnClickListener(new EmptyLayout.onClickListener() {
            @Override
            public void onBtnClick(int type) {
                switch (type) {
                    case EmptyLayout.NETWORK_EMPTY:
                        launcherApp("com.zhuner.administrator.settings");
                        break;
                    case EmptyLayout.ERROR_EMPTY:
                        getCouponFromServer(countryName);
                        break;
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_mask:
                llMask.setVisibility(View.GONE);
                break;
        }
    }

    public void launcherApp(String packageStr) {
        try {
            Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(packageStr);
            startActivity(LaunchIntent);
        } catch (Exception e) {
            Log.e("launcherApp", packageStr + ":" + e.toString());
        }
    }

    private void getCouponFromServer(String countryName) {
        if (!CommonUtils.isAvailable(this)) {
            el_error.setErrorType(EmptyLayout.NETWORK_EMPTY);
            return;
        }

//        Map<String, String> map = new HashMap<>();
//        map.put("countryName", "中国");
//        OkGoUtil.get(Constant.URL_COUPON, map, CouponBean.class, new BaseCallback<CouponBean>() {
//            @Override
//            public void onSuccess(String method, CouponBean model, String resoureJson) {
//                if (null != model && null != model.getData()&&model.getData().size()>0) {
//                    adapterCoupon.setNewData(model.getData());
//                    el_error.setErrorType(EmptyLayout.SUCCESS_EMPTY);
//                }else{
//                      el_error.setErrorType(EmptyLayout.NORMAL_EMPTY);
//                }
//            }
//
//            @Override
//            public void onError(String method, String message, String resoureJson) {
//                  el_error.setErrorType(EmptyLayout.ERROR_EMPTY);
//            }
//
//            @Override
//            public void onFinsh(String method) {
//
//            }
//        });
//        countryName = "日本";

        GetRequest<String> getRequest = OkGo.<String>get(Constant.IP_PORT + Constant.URL_COUPON).tag(this);
        getRequest.params("sn", CommonUtils.getSN());
        getRequest.params("sl", CommonUtils.getLocalLanguage());
        getRequest.params("no", CommonUtils.getRandom());
        getRequest.params("countryName", countryName);
        getRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                try {
                    String json = response.body();
                    CouponBean bean = FastJsonUtil.changeJsonToBean(json, CouponBean.class);
                    if (null != bean && null != bean.getData() && bean.getData().size() > 0) {
                        list = bean.getData();
                        adapterCoupon.setNewData(list);
                        el_error.setErrorType(EmptyLayout.SUCCESS_EMPTY);
                    } else {
                        el_error.setErrorType(EmptyLayout.NORMAL_EMPTY);
                    }

                } catch (Exception e) {
                    Log.e("currency_main", e.getMessage()+"=========================");
                    el_error.setErrorType(EmptyLayout.ERROR_EMPTY);
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Log.e("currency_main", "onError=====================================");
                el_error.setErrorType(EmptyLayout.ERROR_EMPTY);
            }
        });
    }

    /**
     * 获取 footer view
     *
     * @return
     */
    private View getFooterView() {
        View view = getLayoutInflater().inflate(R.layout.layout_footer, (ViewGroup) rvCoupon.getParent(), false);
        cl_footer = view.findViewById(R.id.cl_footer);
        return view;
    }


    /**
     * 跳转到优惠券
     */
    private void toShop() {
        try {
            Intent intent = getPackageManager().getLaunchIntentForPackage("com.aibabel.coupon");
            intent.putExtra("from", "map");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
