package com.aibabel.surfinternet.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.aibabel.baselibrary.http.BaseBean;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.baselibrary.sphelper.SPHelper;
import com.aibabel.baselibrary.utils.CommonUtils;
import com.aibabel.baselibrary.utils.ToastUtil;
import com.aibabel.surfinternet.R;
import com.aibabel.surfinternet.adapter.CardHomeAdapter;
import com.aibabel.surfinternet.base.BaseNetActivity;
import com.aibabel.surfinternet.bean.DetailPayBean;
import com.aibabel.surfinternet.bean.PaymentBean;
import com.aibabel.surfinternet.js.CustomWebViewActivity;
import com.aibabel.surfinternet.js.PayPalActivity;
import com.aibabel.surfinternet.net.Api;
import com.aibabel.surfinternet.net.OkGoUtilWeb;
import com.aibabel.surfinternet.utils.Logs;
import com.aibabel.surfinternet.utils.NetUtil;
import com.aibabel.surfinternet.view.EmptyLayout;
import com.aibabel.surfinternet.view.ShadowTransformer;
import com.tencent.mmkv.MMKV;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by fytworks on 2019/4/8.
 */

public class DetailsPayActivity extends BaseNetActivity{

    private String countryName;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.vp_info)
    ViewPager vpInfo;
    @BindView(R.id.tv_page_name)
    TextView tvPageName;
    @BindView(R.id.tv_page_count)
    TextView tvPageCount;
    @BindView(R.id.tv_readme)
    TextView tvReadme;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_price_drot)
    TextView tvPriceDrot;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.add_count)
    ImageView mAddCount;
    @BindView(R.id.revice_count)
    ImageView mMinusCount;
    @BindView(R.id.empty)
    EmptyLayout mEmpty;


    private ShadowTransformer mCardTransformer;
    private CardHomeAdapter mCardAdapter;
    List<DetailPayBean.DataBean> dataBeans;
    private int maxCount = 0;
    private int minCount = 0;
    private int stepCount = 0;
    private double price = 0;

    @Override
    public int getLayouts(Bundle var1) {
        return R.layout.activity_detailspay;
    }

    @Override
    public void initView() {
        countryName = getIntent().getStringExtra("countryName");
        tvName.setText(countryName);
        isNetWork();
    }

    private void isNetWork() {
        if (CommonUtils.isNetworkAvailable(mContext)){
            getDataValue(countryName);
        }else{
            mEmpty.setErrorType(EmptyLayout.NETWORK_EMPTY);
            ToastUtil.showShort(mContext,"当前没有网络");
        }
    }
    String iccid;
    private void getDataValue(String countryName) {

        Map<String, String> map = new HashMap<>();
        MMKV mmkv = MMKV.defaultMMKV();
        iccid = mmkv.decodeString("iccid_intent");
        String deviceType = mmkv.decodeString("deviceType");
        if (TextUtils.isEmpty(iccid) || TextUtils.isEmpty(deviceType)){
            ToastUtil.showShort(mContext,"获取标识出错，请重启应用");
            finish();
            return;
        }
        if (TextUtils.equals(Api.PHONE_MOBILE_NUMBER, "PH") && TextUtils.equals(Api.COUNTRY_VERSION_NUMBER, "5") && TextUtils.equals(Api.PRO_VERSION_NUMBER, "S")) {
            map.put("currencyType", "USD");
        } else {
            map.put("currencyType", "CNY");
        }
        map.put("iccid",iccid);
        map.put("deviceType",deviceType);
        map.put("countryName",countryName);
        OkGoUtil.get(mContext, Api.GET_COUNTRY_DETAIL, map, DetailPayBean.class, new BaseCallback<DetailPayBean>() {
            @Override
            public void onSuccess(String method, DetailPayBean model, String resoureJson) {
                mEmpty.setErrorType(EmptyLayout.SUCCESS_EMPTY);
                dataBeans = model.getData();
                showResult();
            }

            @Override
            public void onError(String method, String message, String resoureJson) {
                Logs.e(Api.GET_COUNTRY_DETAIL+":"+message.toString());
                mEmpty.setErrorType(EmptyLayout.ERROR_EMPTY);
            }

            @Override
            public void onFinsh(String method) {

            }
        });

    }

    private void showResult() {
        mCardAdapter = new CardHomeAdapter(mContext,dataBeans);
        mCardTransformer = new ShadowTransformer(vpInfo, mCardAdapter);
        vpInfo.setAdapter(mCardAdapter);
        mCardTransformer.enableScaling(true);
        switchShow(0);


        vpInfo.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switchShow(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }
    DetailPayBean.DataBean dataBean;
    public void switchShow(int index){
        dataBean =  dataBeans.get(index);
        maxCount = Integer.parseInt(dataBean.getQuantity_max());
        minCount = Integer.parseInt(dataBean.getQuantity_min());
        stepCount = Integer.parseInt(dataBean.getQuantity_step());
        price = Double.parseDouble(dataBean.getPriceCny());

        tvPageName.setText(dataBean.getPackageName());
        tvPageCount.setText(dataBean.getHighFlowSize()+"M/");
        tvReadme.setText(dataBean.getIntroduct());
        String[] s = dataBean.getPriceCny().split("\\.");
        tvPrice.setText(s[0]);
        tvPriceDrot.setText("."+s[1]);
        tvCount.setText(dataBean.getQuantity_min());
        mAddCount.setImageResource(R.mipmap.ic_add_select);
        mMinusCount.setImageResource(R.mipmap.ic_revice_default);


    }


    @Override
    public void initData() {
        mEmpty.setOnBtnClickListener(new EmptyLayout.onClickListener() {
            @Override
            public void onBtnClick() {
                isNetWork();
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.add_count:
                countShare(true);
                break;
            case R.id.revice_count:
                countShare(false);
                break;
            case R.id.tv_pay:
                showPopuWindows();
                break;
        }
    }
    private void countShare(boolean flag) {
        //当前数字
        int cot = Integer.parseInt(tvCount.getText().toString().trim());
        if (flag){
            //进行加法运算
            if (cot == maxCount){
                /**
                 * 当前已到购买最大值
                 * 1.把加法图标改变成灰色
                 */
                mAddCount.setImageResource(R.mipmap.ic_add_default);
                mMinusCount.setImageResource(R.mipmap.ic_revice_select);
            }else{
                /**
                 * 当前没有到购买最大值
                 * 1.把加法图标改变为橙色
                 * 2.把值赋值到控件中
                 * 3.计算当前价格
                 * 4.把减法图标改为橙色
                 */
                mAddCount.setImageResource(R.mipmap.ic_add_select);
                tvCount.setText((cot+stepCount)+"");
                String str = String.format("%.2f",price*(cot+stepCount));
                String[] s = str.split("\\.");
                tvPrice.setText(s[0]);
                tvPriceDrot.setText("."+s[1]);
                mMinusCount.setImageResource(R.mipmap.ic_revice_select);

                if ((cot+stepCount) == maxCount){
                    mAddCount.setImageResource(R.mipmap.ic_add_default);
                }
            }
        }else {
            //进行减法运算
            if (cot == minCount){
                /**
                 * 当前已减到购买最小值
                 * 1.把减法图标改变成灰色
                 * 2.把加法图标改为橙色
                 */
                mAddCount.setImageResource(R.mipmap.ic_add_select);
                mMinusCount.setImageResource(R.mipmap.ic_revice_default);
            }else{
                /**
                 * 当前没有到购买最小值
                 * 1.把减法图标改变为橙色
                 * 2.把值赋值到控件中
                 * 3.计算当前价格
                 * 4.把加法图标改为橙色
                 */
                mMinusCount.setImageResource(R.mipmap.ic_revice_select);
                tvCount.setText((cot-stepCount)+"");
                String str = String.format("%.2f",price*(cot-stepCount));
                String[] s = str.split("\\.");
                tvPrice.setText(s[0]);
                tvPriceDrot.setText("."+s[1]);
                mAddCount.setImageResource(R.mipmap.ic_add_select);
                if ((cot-stepCount) == minCount){
                    mMinusCount.setImageResource(R.mipmap.ic_revice_default);
                }
            }
        }
    }

    private Dialog mDialog;
    private LinearLayout LinWeChat;
    private LinearLayout LinAlipay;
    private LinearLayout LinPayPal;
    private String payType = "";
    private void showPopuWindows() {
        mDialog = new Dialog(this, R.style.my_dialog);
        View root = View.inflate(this, R.layout.popu, null);
        root.findViewById(R.id.btn_cancel).setOnClickListener(btnlistener);
        LinWeChat = root.findViewById(R.id.Lin_Wechat);
        LinAlipay = root.findViewById(R.id.Lin_Alipay);
        LinPayPal = root.findViewById(R.id.Lin_PayPal);
        //判断是否显示
        if (TextUtils.equals(Api.PHONE_MOBILE_NUMBER, "PH") && TextUtils.equals(Api.COUNTRY_VERSION_NUMBER, "5") && TextUtils.equals(Api.PRO_VERSION_NUMBER, "S")) {
            LinPayPal.setVisibility(View.VISIBLE);
            LinWeChat.setVisibility(View.GONE);
            LinAlipay.setVisibility(View.GONE);
        } else {
            LinPayPal.setVisibility(View.GONE);
            LinWeChat.setVisibility(View.VISIBLE);
            LinAlipay.setVisibility(View.VISIBLE);
        }
        LinWeChat.setOnClickListener(btnlistener);
        LinAlipay.setOnClickListener(btnlistener);
        LinPayPal.setOnClickListener(btnlistener);

        mDialog.setContentView(root);
        Window dialogWindow = mDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.Popupwindow); // 添加动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = -20; // 新位置Y坐标
        lp.width = getResources().getDisplayMetrics().widthPixels; // 宽度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();
        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.show();
    }
    private View.OnClickListener btnlistener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mDialog != null) {
                mDialog.dismiss();
            }
            switch (v.getId()) {
                case R.id.Lin_Wechat:
                    payType = "1";
                    initPayment(payType);
                    break;
                case R.id.Lin_Alipay:
                    payType = "2";
                    initPayment(payType);
                    break;
                case R.id.Lin_PayPal:
                    payType = "3";
                    initPayment(payType);
                    break;
            }
        }
    };



    private PaymentBean paymentBean;
    private void initPayment(String payType) {
        Map<String, String> map = new HashMap<>();
        map.put("iccId",iccid);
        map.put("skuId", dataBean.getSku_id());
        map.put("describe", tvReadme.getText().toString().trim());
        map.put("copies", tvCount.getText().toString().trim());
        //TODO 订单价格
        double dPrice = Double.parseDouble(tvPrice.getText().toString().trim())*100;
//        map.put("spend", (int)dPrice+"");
        map.put("spend", "1");
        map.put("skuName", tvName.getText().toString().trim()+"-"+dataBean.getHighFlowSize()+"MB");
        map.put("days", "1");
        map.put("payType", payType);
        map.put("language", Api.SETCOUNTRYlANGUAGE);
        if (Api.Lk_CARDTYPE){
            map.put("cardType","lksc");
        }

        OkGoUtilWeb.post(mContext,Api.METHOD_CHUANGJIANDINGDAN, new JSONObject(map),PaymentBean.class, new BaseCallback<PaymentBean>(){

            @Override
            public void onSuccess(String method, PaymentBean model, String resoureJson) {
                Logs.e(Api.METHOD_CHUANGJIANDINGDAN+":"+resoureJson);
                paymentBean = model;
                String url = paymentBean.getUrl();
                Log.e("url_xs", url);
                String subOrderNo = paymentBean.getSubOrderNo();
                String time = tvCount.getText().toString().trim();
                Intent intent;
                //1 微信  2 支付宝 3 paypal
                if (!TextUtils.equals(payType, "3")) {
                    intent = new Intent(DetailsPayActivity.this, CustomWebViewActivity.class);
                } else {
                    intent = new Intent(DetailsPayActivity.this, PayPalActivity.class);
                }
                intent.putExtra("url", url);
                intent.putExtra("subOrderNo", subOrderNo);
                intent.putExtra("time", time);
                intent.putExtra("payType", payType);
                intent.putExtra("SKUID", dataBean.getSku_id());
                startActivity(intent);
            }

            @Override
            public void onError(String method, String message, String resoureJson) {
                Logs.e(Api.METHOD_CHUANGJIANDINGDAN+":"+message.toString());
                ToastUtil.showShort(mContext,"准儿出错了");
            }

            @Override
            public void onFinsh(String method) {

            }
        });
    }

}
