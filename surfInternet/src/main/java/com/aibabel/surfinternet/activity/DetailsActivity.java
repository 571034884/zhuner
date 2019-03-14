package com.aibabel.surfinternet.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.surfinternet.R;
import com.aibabel.surfinternet.bean.Constans;
import com.aibabel.surfinternet.bean.DetailsBean;
import com.aibabel.surfinternet.bean.PaymentBean;
import com.aibabel.surfinternet.bean.PriceBeans;
import com.aibabel.surfinternet.js.CustomWebViewActivity;
import com.aibabel.surfinternet.js.PayPalActivity;
import com.aibabel.surfinternet.okgo.BaseBean;
import com.aibabel.surfinternet.okgo.BaseCallback;
import com.aibabel.surfinternet.okgo.OkGoUtil;
import com.aibabel.surfinternet.utils.NetUtil;
import com.aibabel.surfinternet.utils.ToastUtil;
import com.lzy.okgo.model.Response;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class DetailsActivity extends BaseActivity implements BaseCallback, View.OnClickListener {


    @BindView(R.id.tv_country)
    TextView tvCountry;
    @BindView(R.id.tv_operator)
    TextView tvOperator;
    @BindView(R.id.tv_price_day)
    TextView tvPriceDay;
    @BindView(R.id.tv_operator_name)
    TextView tvOperatorName;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.tv_internet)
    TextView tvInternet;
    @BindView(R.id.tv_roaming)
    TextView tvRoaming;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.tv_shopping)
    TextView tvShopping;
    @BindView(R.id.bt_reduce)
    TextView btReduce;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.bt_plus)
    TextView btPlus;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.tv_price_num1)
    TextView tvPriceNum1;
    @BindView(R.id.tv_price_num2)
    TextView tvPriceNum2;
    @BindView(R.id.tv_price_num3)
    TextView tvPriceNum3;
    @BindView(R.id.tv_price_num4)
    TextView tvPriceNum4;
    @BindView(R.id.ll2)
    LinearLayout ll2;
    @BindView(R.id.view4)
    View view4;
    @BindView(R.id.bt_submission)
    TextView btSubmission;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.cl_details)
    ConstraintLayout clDetails;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.iv_back2)
    ImageView ivBack2;
    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.rl_NoNet)
    RelativeLayout rlNoNet;
    @BindView(R.id.tv_price_num5)
    TextView tvPriceNum5;
    @BindView(R.id.tv_price_num6)
    TextView tvPriceNum6;
    @BindView(R.id.tv_MinDays)
    TextView tvMinDays;
    private List<DetailsBean.DataBean> detalist = new ArrayList<>();
    private List<PriceBeans.DataBean> pricelist = new ArrayList<>();
    private List<PriceBeans.DataBean.PriceBean> copyslist = new ArrayList<>();
    private DetailsBean detailsBean;
    private String price;
    private String trand_skuid;
    private int number = 1;
    private PriceBeans priceBeans;
    private String copies;
    private String name;
    private String skuid;
    private String desc;
    private String operator;
    private PaymentBean paymentBean;
    private List<PriceBeans.DataBean.PriceBean> price1;
    private Double price11;
    private PopupWindow popupWindow;
    private String days;
    //copys 的份数
    private ArrayList<String> copys_num = new ArrayList<>();
    private boolean is_onclick = true;
    private WindowManager.LayoutParams params;
    private TextView tvAlipay;
    private TextView tvPayPal;
    private TextView tvWeChat;
    private ImageView btnCancel;
    private String payType = "";
    private Intent intent;
    private LinearLayout LinWeChat;
    private LinearLayout LinAlipay;
    private LinearLayout LinPayPal;
    private String language;
    private String country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        params = getWindow().getAttributes();
        if (NetUtil.isNetworkAvailable(DetailsActivity.this)) {
            initData();
        } else {
            rl.setVisibility(View.VISIBLE);
            clDetails.setVisibility(View.GONE);
            rlNoNet.setVisibility(View.VISIBLE);
        }
        initShopping();
        initonClick();
        btSubmission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map map1 = new HashMap();
                map1.put("p1", name);
                StatisticsManager.getInstance(DetailsActivity.this).addEventAidl(1712, map1);
                popup();
            }
        });


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ivBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 这里popupWindow用的是showAtLocation而不是showAsDropDown
     * popupWindow.isShowing会一直返回false，所以要重新定义一个变量
     * 要注意setOutsideTouchable的干扰
     */
    private boolean mIsShowing = false;

    public void popup() {
        if (popupWindow == null) {
            initPopup();
        }
        if (!mIsShowing) {
            params.alpha = 0.3f;
            getWindow().setAttributes(params);
            popupWindow.showAtLocation(findViewById(R.id.rl), Gravity.BOTTOM, 0, 0);
            mIsShowing = true;
        }
    }

    public void popuDismiss() {
        if (popupWindow != null && mIsShowing) {
            popupWindow.dismiss();
            mIsShowing = false;
            params.alpha = 1f;
            getWindow().setAttributes(params);
        }
    }

    private void initPopup() {
        View pop = LayoutInflater.from(DetailsActivity.this).inflate(R.layout.popu, null);
        LinWeChat = pop.findViewById(R.id.Lin_Wechat);
        LinAlipay = pop.findViewById(R.id.Lin_Alipay);
        LinPayPal = pop.findViewById(R.id.Lin_PayPal);

        if (TextUtils.equals(Constans.PHONE_MOBILE_NUMBER, "PH") && TextUtils.equals(Constans.COUNTRY_VERSION_NUMBER, "5") && TextUtils.equals(Constans.PRO_VERSION_NUMBER, "S")) {
            LinPayPal.setVisibility(View.VISIBLE);
            LinWeChat.setVisibility(View.GONE);
            LinAlipay.setVisibility(View.GONE);
        } else {
            LinPayPal.setVisibility(View.GONE);
            LinWeChat.setVisibility(View.VISIBLE);
            LinAlipay.setVisibility(View.VISIBLE);
        }


        tvAlipay = pop.findViewById(R.id.tv_Alipay);
        tvPayPal = pop.findViewById(R.id.tv_PayPal);
        tvWeChat = pop.findViewById(R.id.tv_WeChat);
        btnCancel = pop.findViewById(R.id.btn_cancel);
        popupWindow = new PopupWindow(pop, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        popupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
        mIsShowing = false;

        Map map1 = new HashMap();
        map1.put("p1", skuid);
        StatisticsManager.getInstance(DetailsActivity.this).addEventAidl(1720, map1);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popuDismiss();
            }
        });
        tvWeChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetUtil.isNetworkAvailable(DetailsActivity.this)) {
                    payType = "1";
                    initPayment(payType);
                } else {
                    rl.setVisibility(View.VISIBLE);
                    clDetails.setVisibility(View.GONE);
                    rlNoNet.setVisibility(View.VISIBLE);
//                    rl.setBackgroundResource(R.mipmap.net1);
                }
            }
        });
        tvAlipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetUtil.isNetworkAvailable(DetailsActivity.this)) {
                    payType = "2";
                    initPayment(payType);
                } else {
                    rl.setVisibility(View.VISIBLE);
                    clDetails.setVisibility(View.GONE);
                    rlNoNet.setVisibility(View.VISIBLE);
//                    rl.setBackgroundResource(R.mipmap.net1);
                }
            }
        });
        tvPayPal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetUtil.isNetworkAvailable(DetailsActivity.this)) {
                    payType = "3";
                    initPayment(payType);
                } else {
                    rl.setVisibility(View.VISIBLE);
                    clDetails.setVisibility(View.GONE);
                    rlNoNet.setVisibility(View.VISIBLE);
//                    rl.setBackgroundResource(R.mipmap.net1);
                }
            }
        });
    }

    //为了解决弹出PopupWindow后外部的事件不会分发,既外部的界面不可以点击
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (popupWindow != null && popupWindow.isShowing()) {
            return false;
        }
        return super.dispatchTouchEvent(event);
    }

    private void initLanguage() {
        if (Constans.PHONE_LANGUAGE.equals("zh")) {
            tvPriceNum6.setVisibility(View.GONE);
            tvPriceNum4.setVisibility(View.VISIBLE);
            if (TextUtils.equals(Constans.PHONE_MOBILE_NUMBER, "PH") && TextUtils.equals(Constans.COUNTRY_VERSION_NUMBER, "5") && TextUtils.equals(Constans.PRO_VERSION_NUMBER, "S")) {
                tvPriceNum4.setText(getResources().getString(R.string.dollar));
            } else {
                tvPriceNum4.setText(getResources().getString(R.string.rmb));
            }
        } else if (Constans.PHONE_LANGUAGE.equals("en") || Constans.PHONE_LANGUAGE.equals("ja") || Constans.PHONE_LANGUAGE.equals("ko")) {
            tvPriceNum6.setVisibility(View.VISIBLE);
            tvPriceNum4.setVisibility(View.GONE);
            if (TextUtils.equals(Constans.PHONE_MOBILE_NUMBER, "PH") && TextUtils.equals(Constans.COUNTRY_VERSION_NUMBER, "5") && TextUtils.equals(Constans.PRO_VERSION_NUMBER, "S")) {
                tvPriceNum6.setText(getResources().getString(R.string.dollar));
            } else {
                tvPriceNum6.setText(getResources().getString(R.string.rmb));
            }
        } else {
            Constans.SETCOUNTRYlANGUAGE = "Chj";
            tvPriceNum6.setVisibility(View.GONE);
            tvPriceNum4.setVisibility(View.VISIBLE);
            if (TextUtils.equals(Constans.PHONE_MOBILE_NUMBER, "PH") && TextUtils.equals(Constans.COUNTRY_VERSION_NUMBER, "5") && TextUtils.equals(Constans.PRO_VERSION_NUMBER, "S")) {
                tvPriceNum4.setText(getResources().getString(R.string.dollar));
            } else {
                tvPriceNum4.setText(getResources().getString(R.string.rmb));
            }
        }
    }

    private void initPayment(String payType) {
        Map map1 = new HashMap();
        map1.put("p1", skuid);
        StatisticsManager.getInstance(DetailsActivity.this).addEventAidl(1722, map1);


        Map<String, String> map = new HashMap<>();
        map.put("iccId", Constans.PHONE_ICCID);
        map.put("skuId", skuid);
        map.put("describe", desc);
        map.put("copies", tvNumber.getText().toString());
        String s1 = tvPriceNum3.getText().toString();
        String s = bigDecimalDo(s1, 100);
        String s2 = s.substring(0, s.length() - 3);
        //TODO 订单价格
        map.put("spend", s2);
//        map.put("spend", "1");
        map.put("skuName", operator);
        map.put("days", days);
        map.put("payType", payType);
        map.put("language", Constans.SETCOUNTRYlANGUAGE);
        if (Constans.Lk_CARDTYPE){

            map.put("cardType","lksc");
        }
        OkGoUtil.<PaymentBean>post(DetailsActivity.this, Constans.METHOD_CHUANGJIANDINGDAN, new JSONObject(map), PaymentBean.class, this);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            Log.e("map_chuangjiandingdan", entry.getKey() + "====" + map.get(entry.getKey()));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        initLanguage();
        is_onclick = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void initShopping() {
        Map<String, String> map = new HashMap<>();

        if (TextUtils.equals(Constans.PHONE_MOBILE_NUMBER, "PH") && TextUtils.equals(Constans.COUNTRY_VERSION_NUMBER, "5") && TextUtils.equals(Constans.PRO_VERSION_NUMBER, "S")) {
            map.put("currencyType", "Dollar");
            map.put("priceFor", "forSell");
        } else if (TextUtils.equals(Constans.PRO_VERSION_NUMBER, "S")) {
            map.put("priceFor", "forSell");
        } else {
            map.put("priceFor", "forLease");
        }
        map.put("sysLanguage", Constans.SETCOUNTRYlANGUAGE);
        map.put("hasBaseDays", "true");
        map.put("iccid", Constans.PHONE_ICCID);
        if (Constans.Lk_CARDTYPE){

            map.put("cardType","lksc");
        }
        OkGoUtil.<PriceBeans>get(DetailsActivity.this, Constans.METHOD_GUOJIALIEBIAOXIANQINGYEPIRCE, map, PriceBeans.class, this);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            Log.e("map_gouwujiaqian", entry.getKey() + "====" + map.get(entry.getKey()));
        }
    }

    private void initonClick() {
        btReduce.setOnClickListener(this);
        btPlus.setOnClickListener(this);
    }

    private void initData() {
        Intent intent = getIntent();
        trand_skuid = intent.getStringExtra("skuid");
        //单价
        price = intent.getStringExtra("price");

        price11 = Double.valueOf(price);
        name = intent.getStringExtra("name");
        days = intent.getStringExtra("days");

        Map<String, String> map = new HashMap<>();
        map.put("sysLanguage", Constans.SETCOUNTRYlANGUAGE);
        map.put("iccid", Constans.PHONE_ICCID);
        if (Constans.Lk_CARDTYPE){

            map.put("cardType","lksc");
        }
        OkGoUtil.<DetailsBean>get(DetailsActivity.this, Constans.METHOD_GUOJIALIEBIAOXIANQINGYE, map, DetailsBean.class, this);

        for (Map.Entry<String, String> entry : map.entrySet()) {
            Log.e("map_kadingdanxiangqing", entry.getKey() + "====" + map.get(entry.getKey()));
        }

    }


    @Override
    public void onSuccess(String method, BaseBean model) {


        switch (method) {
            case Constans.METHOD_GUOJIALIEBIAOXIANQINGYE:
                detailsBean = (DetailsBean) model;
                detalist = detailsBean.getData();
                for (int i = 0; i < detalist.size(); i++) {
                    String skuId = detalist.get(i).getSkuId();

                    if (TextUtils.equals(trand_skuid, skuId)) {
                        operator = detalist.get(i).getName();
                        desc = detalist.get(i).getDesc();
                        tvCountry.setText(name);
                        try {
                            int index = desc.indexOf("运营商：");
                            String desc2 = desc.substring(index);
                            int index2 = desc2.indexOf("，");
                            String yys = desc2.substring(4, index2);
                            tvOperator.setText(yys);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (TextUtils.equals(Constans.PHONE_MOBILE_NUMBER, "PH") && TextUtils.equals(Constans.COUNTRY_VERSION_NUMBER, "5") && TextUtils.equals(Constans.PRO_VERSION_NUMBER, "S")) {
                            tvPriceDay.setText("$ " + price + "/" + getResources().getString(R.string.day));
                        } else {
                            tvPriceDay.setText("¥ " + price + "/" + getResources().getString(R.string.day));
                        }
                        String chaka = desc.replaceAll("插卡", "");
                        tvRoaming.setText(chaka);
                        tvOperatorName.setText(operator);
                        tvPriceNum3.setText(price);
                        skuid = detalist.get(i).getSkuId();
                        clDetails.setVisibility(View.VISIBLE);
                        rl.setVisibility(View.VISIBLE);
                    }

                }

                break;
            case Constans.METHOD_GUOJIALIEBIAOXIANQINGYEPIRCE:
                priceBeans = (PriceBeans) model;
                pricelist = priceBeans.getData();
                for (int i = 0; i < pricelist.size(); i++) {
                    String skuId = pricelist.get(i).getSkuId();
                    if (TextUtils.equals(trand_skuid, skuId)) {
                        copyslist = pricelist.get(i).getPrice();
                        for (int j = 0; j < copyslist.size(); j++) {
                            copys_num.add(copyslist.get(j).getCopies());
                        }
                        tvNumber.setText(copys_num.get(0));
                        tvPriceNum1.setText(copys_num.get(0));
                        tvPriceNum3.setText(bigDecimalDo(copys_num.get(0), price11));
                        if (TextUtils.equals("L", Constans.PRO_VERSION_NUMBER)) {
                            tvMinDays.setVisibility(View.GONE);
                        } else {
                            tvMinDays.setVisibility(View.VISIBLE);
                            tvMinDays.setText("(" + getResources().getString(R.string.min_days1) + copys_num.get(0) + getResources().getString(R.string.min_days2) + ")");
                        }
                    }
                }
                break;

            case Constans.METHOD_CHUANGJIANDINGDAN:
                paymentBean = (PaymentBean) model;
                Log.e("paymentBean", paymentBean.getMsg() + "==" + paymentBean.getCode() + "==" + paymentBean.getSubOrderNo() + "==" + paymentBean.getUrl());
                String url = paymentBean.getUrl();
                Log.e("url_xs", url);
                String subOrderNo = paymentBean.getSubOrderNo();
                String time = tvNumber.getText().toString();

                //1 微信  2 支付宝 3 paypal
                if (!TextUtils.equals(payType, "3")) {

                    Map map1 = new HashMap();
                    map1.put("p3", skuid);
                    map1.put("p1", "true");
                    if (TextUtils.equals(payType, "2")) {
                        map1.put("p2", "支付宝");
                    } else {
                        map1.put("p2", "微信");
                    }

                    StatisticsManager.getInstance(DetailsActivity.this).addEventAidl(1723, map1);
                    intent = new Intent(DetailsActivity.this, CustomWebViewActivity.class);
                } else {
                    Map map1 = new HashMap();
                    map1.put("p3", skuid);
                    map1.put("p1", "true");
                    map1.put("p2", "paypal");
                    StatisticsManager.getInstance(DetailsActivity.this).addEventAidl(1723, map1);

                    intent = new Intent(DetailsActivity.this, PayPalActivity.class);
                }
                intent.putExtra("url", url);
                intent.putExtra("subOrderNo", subOrderNo);
                intent.putExtra("time", time);
                intent.putExtra("payType", payType);
                intent.putExtra("SKUID", skuid);
                if (is_onclick) {
                    onClickable(btSubmission, is_onclick);
                    startActivity(intent);
                    popuDismiss();
                    is_onclick = false;
                }
                break;
        }


    }

    /**
     * @param view
     * @param b    是否可点击
     */
    private void onClickable(View view, boolean b) {
        if (b) {
            view.setClickable(true);
        } else {
            view.setClickable(false);
        }
    }

    @Override
    public void onError(String method, Response<String> response) {

        rl.setVisibility(View.VISIBLE);
        clDetails.setVisibility(View.GONE);
        rlNoNet.setVisibility(View.VISIBLE);
        tvError.setText(getResources().getString(R.string.zoudiule));


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        String numbers = tvNumber.getText().toString();
        Integer number1 = Integer.valueOf(numbers);
        number = number1;
        switch (v.getId()) {
            case R.id.bt_reduce:
                if (copys_num.size() == 1) {
                    ToastUtil.showShort(DetailsActivity.this, getResources().getString(R.string.bukexiugai));
                } else {
                    String tv_num = tvNumber.getText().toString();
                    for (int i = 0; i < copys_num.size(); i++) {
                        if (tv_num == copys_num.get(i)) {
                            if (i == 0) {
                                ToastUtil.showShort(DetailsActivity.this, getResources().getString(R.string.zuixiaofenshu));
                                tvNumber.setText(copys_num.get(i));
                                tvPriceNum1.setText(copys_num.get(i) + " ");
                                tvPriceNum3.setText(bigDecimalDo(copys_num.get(i), price11));

                            } else if (i > 0) {
                                tvNumber.setText(copys_num.get(i - 1));
                                tvPriceNum1.setText(copys_num.get(i - 1) + " ");
                                tvPriceNum3.setText(bigDecimalDo(copys_num.get(i - 1), price11));
                            }
                        }
                    }
                }
                break;
            case R.id.bt_plus:
                if (copys_num.size() == 1) {
                    ToastUtil.showShort(DetailsActivity.this, getResources().getString(R.string.bukexiugai));
                } else {
                    String tv_num = tvNumber.getText().toString();
                    for (int i = 0; i < copys_num.size(); i++) {
                        if (tv_num == copys_num.get(i)) {
                            if (i == copys_num.size() - 1) {
                                ToastUtil.showShort(DetailsActivity.this, getResources().getString(R.string.zuidafenshu));
                                tvNumber.setText(copys_num.get(i));
                                tvPriceNum1.setText(copys_num.get(i) + " ");
                                tvPriceNum3.setText(bigDecimalDo(copys_num.get(i), price11));
                            } else if (i < copys_num.size()) {
                                tvNumber.setText(copys_num.get(i + 1));
                                tvPriceNum1.setText(copys_num.get(i + 1) + " ");
//                                int integer = Integer.valueOf(copys_num.get(i+1));
//                                Double pricenum = integer*price11;
                                tvPriceNum3.setText(bigDecimalDo(copys_num.get(i + 1), price11));

                            }
                        }
                    }
                }
                break;
        }
    }

    public String bigDecimalDo(String str1, double val) {
        String result = "";
        String str2 = new BigDecimal(val).toString();
        try {
            BigDecimal bd1 = new BigDecimal(str1);
            BigDecimal bd2 = new BigDecimal(str2);
            result = bd1.multiply(bd2).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
