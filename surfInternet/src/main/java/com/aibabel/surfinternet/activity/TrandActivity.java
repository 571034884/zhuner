package com.aibabel.surfinternet.activity;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aibabel.surfinternet.R;
import com.aibabel.surfinternet.adapter.CommomRecyclerAdapter;
import com.aibabel.surfinternet.adapter.CommonRecyclerViewHolder;
import com.aibabel.surfinternet.bean.Constans;
import com.aibabel.surfinternet.bean.TrandBean;
import com.aibabel.surfinternet.okgo.BaseBean;
import com.aibabel.surfinternet.okgo.BaseCallback;
import com.aibabel.surfinternet.okgo.OkGoUtil;
import com.aibabel.surfinternet.utils.NetUtil;
import com.aibabel.surfinternet.utils.ToastUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.okgo.model.Response;
import com.umeng.analytics.MobclickAgent;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;


public class TrandActivity extends BaseActivity implements BaseCallback {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_quanqiu)
    TextView tvQuanqiu;
    @BindView(R.id.ll)
    RelativeLayout ll;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.cl_trand)
    ConstraintLayout clTrand;
    @BindView(R.id.rl2)
    RelativeLayout rl2;
    @BindView(R.id.ll_isnet)
    LinearLayout llIsnet;
    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.tv_net_help)
    TextView tvNetHelp;
    private List<TrandBean.DataBean> trandList = new ArrayList<>();
    private TrandBean trandBean;
    private CommomRecyclerAdapter adapter;
    private TextView tv_qian;
    private int onclick = 0;
    private boolean is_onclick = true;
    private String version = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setNavigationBarVisibility(false);
        setContentView(R.layout.activity_trand);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        int first = intent.getIntExtra("first", 0);
        if (first == 1) {
            ivBack.setVisibility(View.GONE);
        }
        if (NetUtil.isNetworkAvailable(TrandActivity.this)) {
            initAdapter();
            initData();
        } else {
            rl2.setVisibility(View.GONE);
            llIsnet.setVisibility(View.VISIBLE);
        }

        tvNetHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_onclick) {
                    onClickable(tvNetHelp, is_onclick);
                    startActivity(new Intent(TrandActivity.this, ViewPagerActivity.class));
                    is_onclick = false;
                }


            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * @param view
     * @param b    是否可点击
     */
    private void onClickable(View view, boolean b) {
        if (b) {
            if (null != view) {
                view.setClickable(true);
            }
        } else {

            if (null != view) {
                view.setClickable(false);
            }

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        is_onclick = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    /**
     * 设置导航栏显示状态
     *
     * @param visible
     */
    private void setNavigationBarVisibility(boolean visible) {
        int flag = 0;
        if (!visible) {
            flag = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }
        getWindow().getDecorView().setSystemUiVisibility(flag);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    private static final int MIN_DELAY_TIME = 1000;  // 两次点击间隔不能少于1000ms
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }


    private void initAdapter() {


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        rv.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        adapter = new CommomRecyclerAdapter(this, trandList, R.layout.item_layout_for_each_currency, new CommomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommonRecyclerViewHolder holder, int postion) {

                if (isFastClick()) {
                    return;
                }
                String skuid = trandList.get(postion).getSkuid();

                String price = trandList.get(postion).getPrice();
//                String price = s.substring(0, s.indexOf("."));
                float s1 = Float.valueOf(price);
                DecimalFormat fnum = new DecimalFormat("##0.00");
                String dd = fnum.format(s1);
                String name = trandList.get(postion).getCountry().toString();
                String days = trandList.get(postion).getDays().toString();
//                String price = trandList.get(postion).getPrice();
                Intent intent = new Intent(TrandActivity.this, DetailsActivity.class);
                intent.putExtra("skuid", skuid);
                intent.putExtra("price", dd + "");
                intent.putExtra("name", name);
                intent.putExtra("days", days);

                if (NetUtil.isNetworkAvailable(TrandActivity.this)) {

                    if (is_onclick) {
                        onClickable(holder.getView(postion), is_onclick);
                        startActivity(intent);
                        is_onclick = false;
                    }

//                    finish();
                } else {
//                    rl2.setBackground(getResources().getDrawable(R.mipmap.net1));
                    rl2.setVisibility(View.GONE);
                    llIsnet.setVisibility(View.VISIBLE);
                    ToastUtil.showShort(TrandActivity.this, getResources().getString(R.string.wuwangluo));
//                    ToastUtil.showShort(TrandActivity.this,"当前无网络，请联网操作");
                }
            }
        }, null) {


            @Override
            public void convert(CommonRecyclerViewHolder holder, Object o, int position) {

                ImageView iv_country = holder.getView(R.id.iv_country);
                TextView tv_zhongwen = holder.getView(R.id.tv_zhongwen);
                tv_qian = holder.getView(R.id.tv_qian);


                RequestOptions requestOptions = new RequestOptions()
                        .placeholder(R.mipmap.morentu)
                        .bitmapTransform(new CropCircleTransformation(TrandActivity.this));

                Glide.with(TrandActivity.this)
                        .load(((TrandBean.DataBean) o).getImpage())
                        .apply(requestOptions)
                        .into(iv_country);
                tv_zhongwen.setText(((TrandBean.DataBean) o).getCountry());
                String days = ((TrandBean.DataBean) o).getDays().toString();
                String s = ((TrandBean.DataBean) o).getPrice().toString();
                float s1 = Float.valueOf(s);

                DecimalFormat fnum = new DecimalFormat("##0.00");
                String dd = fnum.format(s1);
//                String substring = s.substring(0, s.indexOf("."));
                if (TextUtils.equals(Constans.PHONE_MOBILE_NUMBER,"PH")&&TextUtils.equals(Constans.COUNTRY_VERSION_NUMBER,"5")&&TextUtils.equals(Constans.PRO_VERSION_NUMBER,"S")){
                        tv_qian.setText("$ " + dd + "/" + getResources().getString(R.string.day));
                }else {
                    tv_qian.setText("¥ " + dd + "/" + getResources().getString(R.string.day));
                }

//                tv_qian.setText("¥ " + "**" +"/"+ days + getResources().getString(R.string.day));
//                tv_qian.setText("¥ " + "**" + "/天");
            }
        };
        rv.setAdapter(adapter);
    }

    private void initData() {
//
        Map<String, String> map = new HashMap<>();
        try {
            String display = Build.DISPLAY;
//            Constans.PRO_VERSION_NUMBER = display.substring(9, 10);
            Constans.PRO_VERSION_NUMBER = "S";

//            Constans.COUNTRY_VERSION_NUMBER = display.substring(7, 8);
            Constans.COUNTRY_VERSION_NUMBER = "5";

//            Constans.PHONE_MOBILE_NUMBER = display.substring(0, 2);
            Constans.PHONE_MOBILE_NUMBER = "PH";
        } catch (Exception e) {
            e.printStackTrace();
        }

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
        OkGoUtil.<TrandBean>get(TrandActivity.this, Constans.METHOD_GUOJIALIEBIAO, map, TrandBean.class, this);
    }


    @Override
    public void onSuccess(String method, BaseBean model) {

        trandBean = (TrandBean) model;
        trandList = trandBean.getData();
        adapter.updateData(trandList);
        clTrand.setVisibility(View.VISIBLE);
    }


    @Override
    public void onError(String method, Response<String> response) {
        rl2.setVisibility(View.GONE);
        llIsnet.setVisibility(View.VISIBLE);
        tvError.setText(getResources().getString(R.string.zoudiule));
    }
}
