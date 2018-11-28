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
    private String settlementPrice1;
    private String operator;
    private PaymentBean paymentBean;
    private List<PriceBeans.DataBean.PriceBean> price1;
    private Double price11;
    private PopupWindow popupWindow;
    private View popu;
    private EditText mEditText;
    private Button mBut_cancle;
    private Button mbut_Submit;
    private String mEditText_str;
    private String tvNuber_num;
    private String days;
    //copys 的份数
    private ArrayList<String> copys_num = new ArrayList<>();
    private boolean is_onclick = true;
    private boolean isMtkDoubleSim;
    private String iccid;
    private boolean isFind = false;
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
        initMtkDoubleSim();
//        iccid = "89860012017300216438";
        params = getWindow().getAttributes();

        Map<String, String> map = new HashMap<>();

        if (NetUtil.isNetworkAvailable(DetailsActivity.this)) {
            initData();

//            Glide.with(DetailsActivity.this).load(R.mipmap.success2).into(ivBack);
//            initPopupwindow();
        } else {
            rl.setVisibility(View.VISIBLE);
            clDetails.setVisibility(View.GONE);
            rlNoNet.setVisibility(View.VISIBLE);
        }


       /* rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetUtil.isNetworkAvailable(DetailsActivity.this)) {
                    initData();
                }
            }
        });*/
        initShopping();
        initonClick();
        btSubmission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popup();

             /*   if (NetUtil.isNetworkAvailable(DetailsActivity.this)) {
                    String price_num = tvPriceNum3.getText().toString();
                    initPayment(price_num);
                } else {

                    rl.setVisibility(View.VISIBLE);
                    clDetails.setVisibility(View.GONE);

                    rlNoNet.setVisibility(View.VISIBLE);
//                    rl.setBackgroundResource(R.mipmap.net1);
                }*/

            }


        });


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(DetailsActivity.this, TrandActivity.class));
                finish();
            }
        });
        ivBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(DetailsActivity.this, TrandActivity.class));
                finish();
            }
        });

//        tvNumber.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onClick(View v) {
//                tvNuber_num = tvNumber.getText().toString();
//
////                popupWindowShow();
//
//            }
//        });
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

        if (TextUtils.equals(Constans.PHONE_MOBILE_NUMBER,"PH")&&TextUtils.equals(Constans.COUNTRY_VERSION_NUMBER,"5")&&TextUtils.equals(Constans.PRO_VERSION_NUMBER,"S")){
            LinPayPal.setVisibility(View.VISIBLE);
            LinWeChat.setVisibility(View.GONE);
            LinAlipay.setVisibility(View.GONE);
        }else {
            LinPayPal.setVisibility(View.GONE);
            LinWeChat.setVisibility(View.VISIBLE);
            LinAlipay.setVisibility(View.VISIBLE);
        }




     /*   if (TextUtils.equals(Constans.COUNTRY_VERSION_NUMBER, "5")) {
            LinPayPal.setVisibility(View.VISIBLE);
            LinWeChat.setVisibility(View.GONE);
            LinAlipay.setVisibility(View.GONE);
        } else {
            LinPayPal.setVisibility(View.GONE);
            LinWeChat.setVisibility(View.VISIBLE);
            LinAlipay.setVisibility(View.VISIBLE);
        }*/

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
        //获取当前手机的语言
        country = Locale.getDefault().getCountry();
        language = getResources().getConfiguration().locale.getLanguage();
        //读取语言选择的 json 文件
        Log.e("lan_country", country + "========lan_language----------" + language + "--------");

        if (language.equals("zh")) {
            if (country.equals("CN")) {
                Constans.SETCOUNTRYlANGUAGE = "Chj";
                tvPriceNum6.setVisibility(View.GONE);
                tvPriceNum4.setVisibility(View.VISIBLE);

                if (TextUtils.equals(Constans.PHONE_MOBILE_NUMBER,"PH")&&TextUtils.equals(Constans.COUNTRY_VERSION_NUMBER,"5")&&TextUtils.equals(Constans.PRO_VERSION_NUMBER,"S")){
                    tvPriceNum4.setText(getResources().getString(R.string.dollar));
                }else {
                    tvPriceNum4.setText(getResources().getString(R.string.rmb));
                }


                /*if (TextUtils.equals(Constans.COUNTRY_VERSION_NUMBER, "5")) {
                    tvPriceNum4.setText(getResources().getString(R.string.dollar));
                }else {
                    tvPriceNum4.setText(getResources().getString(R.string.rmb));

                }*/

            } else if (country.equals("TW")) {
                Constans.SETCOUNTRYlANGUAGE = "Chf";
                tvPriceNum6.setVisibility(View.GONE);
                tvPriceNum4.setVisibility(View.VISIBLE);
                if (TextUtils.equals(Constans.PHONE_MOBILE_NUMBER,"PH")&&TextUtils.equals(Constans.COUNTRY_VERSION_NUMBER,"5")&&TextUtils.equals(Constans.PRO_VERSION_NUMBER,"S")){
                    tvPriceNum4.setText(getResources().getString(R.string.dollar));
                }else {
                    tvPriceNum4.setText(getResources().getString(R.string.rmb));
                }
               /* if (TextUtils.equals(Constans.COUNTRY_VERSION_NUMBER, "5")) {
                    tvPriceNum4.setText(getResources().getString(R.string.dollar));
                }else {
                    tvPriceNum4.setText(getResources().getString(R.string.rmb));

                }*/
            } else {
                Constans.SETCOUNTRYlANGUAGE = "Chj";
                tvPriceNum6.setVisibility(View.GONE);
                tvPriceNum4.setVisibility(View.VISIBLE);

                if (TextUtils.equals(Constans.PHONE_MOBILE_NUMBER,"PH")&&TextUtils.equals(Constans.COUNTRY_VERSION_NUMBER,"5")&&TextUtils.equals(Constans.PRO_VERSION_NUMBER,"S")){
                    tvPriceNum4.setText(getResources().getString(R.string.dollar));
                }else {
                    tvPriceNum4.setText(getResources().getString(R.string.rmb));
                }
               /* if (TextUtils.equals(Constans.COUNTRY_VERSION_NUMBER, "5")) {
                    tvPriceNum4.setText(getResources().getString(R.string.dollar));
                }else {
                    tvPriceNum4.setText(getResources().getString(R.string.rmb));

                }*/
            }
        } else if (language.equals("en")) {
            Constans.SETCOUNTRYlANGUAGE = "En";
            tvPriceNum6.setVisibility(View.VISIBLE);
            tvPriceNum4.setVisibility(View.GONE);

            if (TextUtils.equals(Constans.PHONE_MOBILE_NUMBER,"PH")&&TextUtils.equals(Constans.COUNTRY_VERSION_NUMBER,"5")&&TextUtils.equals(Constans.PRO_VERSION_NUMBER,"S")){
                tvPriceNum6.setText(getResources().getString(R.string.dollar));
            }else {
                tvPriceNum6.setText(getResources().getString(R.string.rmb));
            }
           /* if (TextUtils.equals(Constans.COUNTRY_VERSION_NUMBER, "5")) {
                tvPriceNum6.setText(getResources().getString(R.string.dollar));
            }else {
                tvPriceNum6.setText(getResources().getString(R.string.rmb));

            }*/
        } else if (language.equals("ja")) {
            Constans.SETCOUNTRYlANGUAGE = "Jpa";
            tvPriceNum6.setVisibility(View.VISIBLE);
            tvPriceNum4.setVisibility(View.GONE);

            if (TextUtils.equals(Constans.PHONE_MOBILE_NUMBER,"PH")&&TextUtils.equals(Constans.COUNTRY_VERSION_NUMBER,"5")&&TextUtils.equals(Constans.PRO_VERSION_NUMBER,"S")){
                tvPriceNum6.setText(getResources().getString(R.string.dollar));
            }else {
                tvPriceNum6.setText(getResources().getString(R.string.rmb));
            }
           /* if (TextUtils.equals(Constans.COUNTRY_VERSION_NUMBER, "5")) {
                tvPriceNum6.setText(getResources().getString(R.string.dollar));
            }else {
                tvPriceNum6.setText(getResources().getString(R.string.rmb));

            }*/
        } else if (language.equals("ko")) {
            Constans.SETCOUNTRYlANGUAGE = "Kor";
            tvPriceNum6.setVisibility(View.VISIBLE);
            tvPriceNum4.setVisibility(View.GONE);

            if (TextUtils.equals(Constans.PHONE_MOBILE_NUMBER,"PH")&&TextUtils.equals(Constans.COUNTRY_VERSION_NUMBER,"5")&&TextUtils.equals(Constans.PRO_VERSION_NUMBER,"S")){
                tvPriceNum6.setText(getResources().getString(R.string.dollar));
            }else {
                tvPriceNum6.setText(getResources().getString(R.string.rmb));
            }
            /*if (TextUtils.equals(Constans.COUNTRY_VERSION_NUMBER, "5")) {
                tvPriceNum6.setText(getResources().getString(R.string.dollar));
            }else {
                tvPriceNum6.setText(getResources().getString(R.string.rmb));
            }*/
        } else {
            Constans.SETCOUNTRYlANGUAGE = "Chj";
            tvPriceNum6.setVisibility(View.GONE);
            tvPriceNum4.setVisibility(View.VISIBLE);
            if (TextUtils.equals(Constans.PHONE_MOBILE_NUMBER,"PH")&&TextUtils.equals(Constans.COUNTRY_VERSION_NUMBER,"5")&&TextUtils.equals(Constans.PRO_VERSION_NUMBER,"S")){
                tvPriceNum4.setText(getResources().getString(R.string.dollar));
            }else {
                tvPriceNum4.setText(getResources().getString(R.string.rmb));
            }
           /* if (TextUtils.equals(Constans.COUNTRY_VERSION_NUMBER, "5")) {
                tvPriceNum4.setText(getResources().getString(R.string.dollar));
            }else {
                tvPriceNum4.setText(getResources().getString(R.string.rmb));
            }*/
        }


    }


    /*  *//**
     * 初始化修改我方语言的popupwindow
     *//*
    private void initPopupwindow() {
        popu = View.inflate(DetailsActivity.this, R.layout.text_popu, null);
        mEditText = popu.findViewById(R.id.mEditText);
        mBut_cancle = popu.findViewById(R.id.mbut_Cancle);
        mbut_Submit = popu.findViewById(R.id.mbut_Submit);


    }*/


    /**
     * 底部弹出popupWindow
     *//*
    private void popupWindowShow() {
        popupWindow = new PopupWindow(popu, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(clDetails, Gravity.BOTTOM, 0, 0);

        mEditText.setText(tvNuber_num);
        mEditText.setSelection(tvNuber_num.length());
        mbut_Submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                mEditText_str = mEditText.getText().toString();

                Integer integer = Integer.valueOf(mEditText_str);
                if (integer > 30) {
                    ToastUtil.showShort(DetailsActivity.this, getResources().getString(R.string.day_num1));
                } else if (integer == 0) {
//                    ToastUtil.showShort(DetailsActivity.this, "该产品下单天数最小为1天，请修改");
                    ToastUtil.showShort(DetailsActivity.this, getResources().getString(R.string.day_num2));
                } else {

                    tvNumber.setText(integer + "");
                    tvPriceNum1.setText(integer + " ");
//                    int pricenum = Math.multiplyExact(integer, price11);
                    Double pricenum = integer * price11;
                    tvPriceNum3.setText(pricenum + "");
                    popupWindow.dismiss();
                }

            }
        });
        mBut_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvNumber.setText(tvNuber_num);
                mEditText.setText(tvNuber_num);
                popupWindow.dismiss();
            }
        });
    }*/
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void initMtkDoubleSim() {


        try {
            List<SubscriptionInfo> list = SubscriptionManager.from(this).getActiveSubscriptionInfoList();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getSimSlotIndex() == 1) {
                    Log.e("iccid123", list.get(i).getIccId());
                    iccid = list.get(i).getIccId();
                    isFind = true;
                    return;
                }
            }
//                if (!isFind) {
//                    ivClose.setVisibility(View.GONE);
//                    ll.setVisibility(View.VISIBLE);
//                    llIsnet.setVisibility(View.VISIBLE);
//                    ivError.setImageResource(R.mipmap.iccid);
//                    tvError.setText(getResources().getString(R.string.iccid));
//
//                }
//            if (list.get(i).getSimSlotIndex()==1){
//                Log.e("iccid123",list.get(i).getIccId());
//                iccid = list.get(i).getIccId();
//                if (null == iccid) {
//                    ivClose.setVisibility(View.GONE);
//                    ll.setVisibility(View.VISIBLE);
//                    llIsnet.setVisibility(View.VISIBLE);
//                    ivError.setImageResource(R.mipmap.iccid);
//                    tvError.setText(getResources().getString(R.string.iccid));
//                }
//            }else {
//                iccid = list.get(i).getIccId();
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initPayment(String payType) {
//        iccid = "89860012017300216438";
        Log.e("iccid_xiadan", iccid);

        if (iccid == null) {
            initMtkDoubleSim();
//            iccid = "89860012017300216438";
        }

        Map<String, String> map = new HashMap<>();
//        iccid="89860012017300216438";
//        map.put("iccId", "89860012017300216438");   //卡号
//        map.put("iccId", "123123");
        map.put("iccId", iccid);
        map.put("skuId", skuid);
        map.put("describe", desc);
        map.put("copies", tvNumber.getText().toString());
        String s1 = tvPriceNum3.getText().toString();
        String s = bigDecimalDo(s1, 100);
        String s2 = s.substring(0, s.length() - 3);

        map.put("spend", s2);
//        map.put("spend", "1");
        map.put("skuName", operator);
        map.put("days", days);

        map.put("payType", payType);

        map.put("language",Constans.SETCOUNTRYlANGUAGE);


        OkGoUtil.<PaymentBean>post(DetailsActivity.this, Constans.METHOD_CHUANGJIANDINGDAN, new JSONObject(map), PaymentBean.class, this);
        /*for (Map.Entry<String, String> entry : map.entrySet()) {
            Log.e("map", entry.getKey() + "====" + map.get(entry.getKey()));
        }*/

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

        if (TextUtils.equals(Constans.PHONE_MOBILE_NUMBER,"PH")&&TextUtils.equals(Constans.COUNTRY_VERSION_NUMBER,"5")&&TextUtils.equals(Constans.PRO_VERSION_NUMBER,"S")){
            map.put("currencyType", "Dollar");
            map.put("priceFor", "forSell");
        }else if (TextUtils.equals(Constans.PRO_VERSION_NUMBER,"S")){
            map.put("priceFor", "forSell");
        }else {
            map.put("priceFor", "forLease");
        }
        map.put("sysLanguage", Constans.SETCOUNTRYlANGUAGE);

        map.put("hasBaseDays", "true");

        OkGoUtil.<PriceBeans>get(DetailsActivity.this, Constans.METHOD_GUOJIALIEBIAOXIANQINGYEPIRCE, map, PriceBeans.class, this);

    }

    private void initonClick() {
        btReduce.setOnClickListener(this);
        btPlus.setOnClickListener(this);
//        tvNumber.setOnClickListener(this);
    }

    private void initData() {
        Intent intent = getIntent();
        trand_skuid = intent.getStringExtra("skuid");
        //单价
        price = intent.getStringExtra("price");

        price11 = Double.valueOf(price);
//        price11 = Integer.parseInt(price);
        name = intent.getStringExtra("name");
        days = intent.getStringExtra("days");
        Map<String, String> map = new HashMap<>();
        map.put("sysLanguage", Constans.SETCOUNTRYlANGUAGE);
        OkGoUtil.<DetailsBean>get(DetailsActivity.this, Constans.METHOD_GUOJIALIEBIAOXIANQINGYE, map, DetailsBean.class, this);

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

                        if (TextUtils.equals(Constans.PHONE_MOBILE_NUMBER,"PH")&&TextUtils.equals(Constans.COUNTRY_VERSION_NUMBER,"5")&&TextUtils.equals(Constans.PRO_VERSION_NUMBER,"S")){
                            tvPriceDay.setText("$ " + price + "/" + getResources().getString(R.string.day));
                        }else {
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
                switch (payType) {
                    case "1":
                        intent = new Intent(DetailsActivity.this, CustomWebViewActivity.class);
                        intent.putExtra("url", url);
                        intent.putExtra("subOrderNo", subOrderNo);
                        intent.putExtra("time", time);
                        intent.putExtra("payType", payType);
                        break;
                    case "2":
                        intent = new Intent(DetailsActivity.this, CustomWebViewActivity.class);
                        intent.putExtra("url", url);
                        intent.putExtra("subOrderNo", subOrderNo);
                        intent.putExtra("time", time);
                        intent.putExtra("payType", payType);
                        break;
                    case "3":
                        intent = new Intent(DetailsActivity.this, PayPalActivity.class);
                        intent.putExtra("url", url);
                        intent.putExtra("subOrderNo", subOrderNo);
                        intent.putExtra("time", time);
                        intent.putExtra("payType", payType);
                        break;
                }

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
