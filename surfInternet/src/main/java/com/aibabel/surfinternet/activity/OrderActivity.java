package com.aibabel.surfinternet.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aibabel.surfinternet.R;
import com.aibabel.surfinternet.adapter.CommomRecyclerAdapter;
import com.aibabel.surfinternet.adapter.CommonRecyclerViewHolder;
import com.aibabel.surfinternet.bean.Constans;
import com.aibabel.surfinternet.bean.OrderitemBean;
import com.aibabel.surfinternet.js.CustomWebViewActivity;
import com.aibabel.surfinternet.okgo.BaseBean;
import com.aibabel.surfinternet.okgo.BaseCallback;
import com.aibabel.surfinternet.utils.NetUtil;
import com.aibabel.surfinternet.utils.SharePrefUtil;
import com.lzy.okgo.model.Response;
import com.umeng.analytics.MobclickAgent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderActivity extends BaseActivity implements BaseCallback {


    @BindView(R.id.iv_back1)
    ImageView ivBack1;
    @BindView(R.id.tv_tcxq)
    TextView tvTcxq;
    @BindView(R.id.rv_dingdan)
    RecyclerView rvDingdan;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.item_view2)
    View itemView2;
    @BindView(R.id.tv_purchase)
    TextView tvPurchase;
    @BindView(R.id.cl_order)
    ConstraintLayout clOrder;
    @BindView(R.id.iv_back2)
    ImageView ivBack2;
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.rl_NoNet)
    RelativeLayout rlNoNet;
    @BindView(R.id.tv_error)

    TextView tvError;
    @BindView(R.id.tv_net_help)
    TextView tvNetHelp;
    //
    private String orderId;

    private CommomRecyclerAdapter adapter;
    private SimpleDateFormat dateFormat;
    private SharedPreferences sp;
    private List<OrderitemBean.DataBean> datalist = new ArrayList<>();
    private OrderitemBean orderitemBean;
    private boolean is_onclick = true;
    private TextView tv_help;
    private TextView tv_xuzu;
    private String version = "";
    private int daynum = 0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        int first = intent.getIntExtra("first", 0);
        if (first == 1) {
            ivBack1.setVisibility(View.GONE);
            ivBack2.setVisibility(View.GONE);
        }
        if (NetUtil.isNetworkAvailable(OrderActivity.this)) {
            initAdapter();

            try {
                initData();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            clOrder.setVisibility(View.GONE);
            rlNoNet.setVisibility(View.VISIBLE);
        }

        ivBack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
        ivBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });


    }

    private void initCountry() {
        String country = Locale.getDefault().getCountry();
        String language = getResources().getConfiguration().locale.getLanguage();

        if (language.equals("zh")||language.equals("en")) {
            tv_help.setVisibility(View.VISIBLE);
        } else {
            tv_help.setVisibility(View.GONE);
        }
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

    private void initAdapter() {
//        tvPurchase.setVisibility(View.GONE);

        try {
            String display = Build.DISPLAY;
            Constans.PRO_VERSION_NUMBER = display.substring(9, 10);

        } catch (Exception e) {
            e.printStackTrace();
        }


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        rvDingdan.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        adapter = new CommomRecyclerAdapter(this, datalist, R.layout.rv_order_item, new CommomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommonRecyclerViewHolder holder, int postion) {

            }

        }, null) {


            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void convert(CommonRecyclerViewHolder holder, Object o, int position) {

                TextView dingdan = holder.getView(R.id.dingdan);
                TextView tv_jihuozhuantai = holder.getView(R.id.tv_jihuozhuantai);
                tv_xuzu = holder.getView(R.id.tv_xuzu);
                tv_help = holder.getView(R.id.tv_help);
                TextView tv_jihuo_help = holder.getView(R.id.tv_jihuo_help);
                TextView tv_dingdan_number = holder.getView(R.id.tv_dingdan_number);
                TextView tv_data = holder.getView(R.id.tv_data);
                TextView dingdan_data = holder.getView(R.id.dingdan_data);
                TextView tv_xianqing = holder.getView(R.id.tv_xianqing);
                TextView tv_goumaixianqing = holder.getView(R.id.tv_goumaixianqing);
                LinearLayout ll_xq = holder.getView(R.id.ll_xq);
                LinearLayout ll_jihuo = holder.getView(R.id.lin_jihuo);
                TextView dingdan_zhuyi = holder.getView(R.id.dingdan_zhuyi);
                int type = ((OrderitemBean.DataBean) o).getState();
                dingdan.setText(((OrderitemBean.DataBean) o).getChannelSubOrderId());
                String describe = ((OrderitemBean.DataBean) o).getDescribe();
                Log.e("describe", describe);
                String chaka = describe.replaceAll("插卡", "");
                dingdan_zhuyi.setText(chaka);
                initCountry();

                tv_help.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (is_onclick) {
                            onClickable(tvNetHelp, is_onclick);
                            startActivity(new Intent(OrderActivity.this, ViewPagerActivity.class));
                            is_onclick = false;
                        }
                    }
                });

                tv_xuzu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (NetUtil.isNetworkAvailable(OrderActivity.this)) {
                            if (is_onclick) {
                                onClickable(tvPurchase, is_onclick);


                                Intent intent = new Intent(OrderActivity.this, TrandActivity.class);


                                startActivity(intent);
                                is_onclick = false;
                            }


                        } else {
                            clOrder.setVisibility(View.GONE);
                            rlNoNet.setVisibility(View.VISIBLE);

//                    ToastUtil.showShort(OrderActivity.this, "当前无网络请联网后操作");
                        }

//                startActivity(new Intent(OrderActivity.this, TrandActivity.class));
                    }
                });

                switch (Constans.PRO_VERSION_NUMBER) {
                    case "L":
                        tv_xuzu.setText(getResources().getString(R.string.xuzu));
                        break;
                    case "S":
                        tv_xuzu.setText(getResources().getString(R.string.jixu_goumai));
                        break;
                }


                if (type == 9) {
//                    tv_data.setText("下单时间:");
                    tv_data.setText(getResources().getString(R.string.xiadan_data) + " :");
                    dingdan_data.setText(((OrderitemBean.DataBean) o).getCreatedAt());
                    //两次加大字体，设置字体为红色（big会加大字号，font可以定义颜色）
//                    tv_dingdan_number.setText("订单 ：" + ((OrderitemBean.DataBean) o).getSkuName() + " " + "(待退款)");
                    tv_dingdan_number.setText(getResources().getString(R.string.dingdan) + ((OrderitemBean.DataBean) o).getSkuName());
                    tv_jihuozhuantai.setText(getResources().getString(R.string.daituikuan));
                    ll_jihuo.setVisibility(View.GONE);
                    tv_jihuozhuantai.setTextColor(getColor(R.color.red));
                    tv_jihuo_help.setVisibility(View.GONE);
                    ll_xq.setVisibility(View.VISIBLE);
                    String days = ((OrderitemBean.DataBean) o).getDays();
                    String copies = ((OrderitemBean.DataBean) o).getCopies();
                    Integer days1 = Integer.valueOf(days);
                    Integer copies1 = Integer.valueOf(copies);
                    int days2 = Math.multiplyExact(days1, copies1);
//                    tv_goumaixianqing.setText(days1 + getResources().getString(R.string.day) + " * " + copies1 + "份" + "共" + days2 + getResources().getString(R.string.day));
                    tv_goumaixianqing.setText(getResources().getString(R.string.gong) + " " + days2 + " " + getResources().getString(R.string.day));
                } else if (type == 1) {


//                    tv_data.setText("下单时间:");
                    tv_data.setText(getResources().getString(R.string.xiadan_data) + " :");
                    dingdan_data.setText(((OrderitemBean.DataBean) o).getCreatedAt());
                    tv_dingdan_number.setText(getResources().getString(R.string.dingdan) + ((OrderitemBean.DataBean) o).getSkuName());
                    tv_jihuozhuantai.setText(getResources().getString(R.string.weijihuo));
                    tv_jihuozhuantai.setTextColor(getColor(R.color.red));
                    ll_jihuo.setVisibility(View.VISIBLE);
                    tv_jihuo_help.setVisibility(View.VISIBLE);
                    tv_jihuo_help.setText(getResources().getString(R.string.jihuo_help));
                    tv_jihuo_help.setTextColor(getColor(R.color.yellow));


                    ll_xq.setVisibility(View.VISIBLE);
                    String days = ((OrderitemBean.DataBean) o).getDays();
                    String copies = ((OrderitemBean.DataBean) o).getCopies();
                    Integer days1 = Integer.valueOf(days);
                    Integer copies1 = Integer.valueOf(copies);
                    int days2 = Math.multiplyExact(days1, copies1);
                    tv_goumaixianqing.setText(getResources().getString(R.string.gong) + " " + days2 + " " + getResources().getString(R.string.day));


                } else if (type == 8) {
                    tv_data.setText(getResources().getString(R.string.daoqi_data) + " :");
//                    tv_data.setText("到期时间:");
                    dingdan_data.setText(((OrderitemBean.DataBean) o).getStopTime());
                    tv_dingdan_number.setText(getResources().getString(R.string.dingdan) + ((OrderitemBean.DataBean) o).getSkuName());
                    ll_jihuo.setVisibility(View.GONE);
                    tv_jihuozhuantai.setText(getResources().getString(R.string.yijihuo));
                    tv_jihuozhuantai.setTextColor(getColor(R.color.green));
                    tv_jihuo_help.setVisibility(View.GONE);
                    ll_xq.setVisibility(View.GONE);

                }
            }
        };
        rvDingdan.setAdapter(adapter);
    }

    public static String getDateDelay(long data, int delay) {
        long temp = data + 86400000 * delay;
        Date d = new Date(temp);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = sdf.format(d);
        Log.e("wzf", "format=" + format);
        return format;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initData() throws ParseException {
        Intent intent = getIntent();
        orderitemBean = (OrderitemBean) intent.getSerializableExtra("orderitemBean");
        datalist = orderitemBean.getData();
        adapter.updateData(datalist);
//        rl1.setVisibility(View.VISIBLE);
//        clOrder.setVisibility(View.VISIBLE);

        //续租代码
        /*if (TextUtils.equals(version, "L")) {
            for (int i = 0; i < datalist.size(); i++) {
                int state = datalist.get(i).getState();
                if (state == 1 || state == 8) {
                    String channelSubOrderId = datalist.get(i).getChannelSubOrderId();
                    String orderFrom = datalist.get(i).getOrderFrom();
                    if (TextUtils.equals(orderFrom, "1")) {
                        String days = datalist.get(i).getDays();
                        String copies = datalist.get(i).getCopies();
                        Integer days1 = Integer.valueOf(days);
                        Integer copies1 = Integer.valueOf(copies);
                        int days2 = Math.multiplyExact(days1, copies1);
                        Log.e("天数", days2 + "");
                        daynum = daynum + days2;
                        Log.e("daynum", "====" + daynum);
                        String time_end_first = SharePrefUtil.getString(OrderActivity.this, "time_end_first", "");
                        Log.e("time_end_first", time_end_first);
                        long time_end1_first = stringToLong(time_end_first, "yyyyMMddHHmmss");
                        String dateDelay = getDateDelay(time_end1_first, daynum);
                        Log.e("dateDelay123", "=====" + dateDelay);
                        Intent intent1 = new Intent();
                        intent1.setAction("com.android.zhuner.time");
                        intent1.putExtra("Zhuner_Time", dateDelay);
                        Log.e("time123", "====" + dateDelay);
                        OrderActivity.this.sendBroadcast(intent1);
                        SharePrefUtil.saveString(OrderActivity.this, "time_end", dateDelay);
                    }
                }
            }
        }*/
    }

    @Override
    public void onSuccess(String method, BaseBean model) {


    }


    @Override
    public void onError(String method, Response<String> response) {
        clOrder.setVisibility(View.GONE);
        rlNoNet.setVisibility(View.VISIBLE);
        tvError.setText(getResources().getString(R.string.zoudiule));

    }

    public static long stringToLong(String strTime, String formatType)
            throws ParseException {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    public static long dateToLong(Date date) {
        return date.getTime();
    }

}
