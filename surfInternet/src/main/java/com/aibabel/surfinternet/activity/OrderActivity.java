package com.aibabel.surfinternet.activity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.provider.Settings;
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

import com.aibabel.surfinternet.MainActivity;
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

public class OrderActivity extends BaseActivity {


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



    private CommomRecyclerAdapter adapter;
    private List<OrderitemBean.DataBean> datalist = new ArrayList<>();
    private OrderitemBean orderitemBean;
    private boolean is_onclick = true;
    private TextView tv_help;
    private TextView tv_xuzu;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
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

        bindMessage();
    }

    private  void bindMessage(){
        Intent intent = new Intent();
        intent.setAction("action.menu.MenuMessengerService");
        intent.setPackage("com.aibabel.menu");
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private void initCountry() {
        if (Constans.PHONE_LANGUAGE.equals("zh") || Constans.PHONE_LANGUAGE.equals("en")) {
            tv_help.setVisibility(View.VISIBLE);
        } else {
            tv_help.setVisibility(View.GONE);
        }

        if(Constans.PRO_VERSION_NUMBER.equalsIgnoreCase("L")) {
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
                LinearLayout ll_channel = holder.getView(R.id.ll_channel);
                TextView  tv_channel_name = holder.getView(R.id.tv_channel_name);

                LinearLayout ll_starttime = holder.getView(R.id.ll_starttime);
                TextView  tv_start_time = holder.getView(R.id.tv_start_time);

                LinearLayout ll_stop_time = holder.getView(R.id.ll_stop_time);
                TextView  tv_stoptime = holder.getView(R.id.tv_stoptime);


                int type = ((OrderitemBean.DataBean) o).getState();
                dingdan.setText(((OrderitemBean.DataBean) o).getChannelSubOrderId());
                String describe = ((OrderitemBean.DataBean) o).getDescribe();
                String channel = ((OrderitemBean.DataBean) o).getChannel_name();


                Log.e("describe", describe);
                String chaka = describe.replaceAll("插卡", "");
                dingdan_zhuyi.setText(chaka);
                initCountry();

                tv_help.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("hjs", "startActivity_APN");
//                        if(Constans.PRO_VERSION_NUMBER.equalsIgnoreCase("L")) {
//                            Log.e("hjs", "APN_L");
//
//                            startActivity_APN();
//                        }else
                            {
                        if (is_onclick) {
                            onClickable(tvNetHelp, is_onclick);
                            startActivity(new Intent(OrderActivity.this, ViewPagerActivity.class));
                            is_onclick = false;
                        }
                        }
                    }
                });

                tv_xuzu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.e("hjs","pro:"+Constans.PRO_VERSION_NUMBER);
                       if(Constans.PRO_VERSION_NUMBER.equalsIgnoreCase("L")) {
                           startacitivy_rent(1,channel);
                       }else{


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

                        }
                       }

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


                if (type == 9 || type == 1) {
                    tv_data.setText(getResources().getString(R.string.xiadan_data) + " :");
                    dingdan_data.setText(((OrderitemBean.DataBean) o).getCreatedAt());
                    tv_dingdan_number.setText(getResources().getString(R.string.dingdan) + ((OrderitemBean.DataBean) o).getSkuName());
                    tv_jihuozhuantai.setTextColor(getColor(R.color.red));
                    ll_xq.setVisibility(View.VISIBLE);
                    String days = ((OrderitemBean.DataBean) o).getDays();
                    String copies = ((OrderitemBean.DataBean) o).getCopies();

                    Integer days1 = Integer.valueOf(days);
                    Integer copies1 = Integer.valueOf(copies);
                    int days2 = Math.multiplyExact(days1, copies1);
                    tv_goumaixianqing.setText(getResources().getString(R.string.gong) + " " + days2 + " " + getResources().getString(R.string.day));
                    if (type == 9) {
                        tv_jihuozhuantai.setText(getResources().getString(R.string.daituikuan));
                        ll_jihuo.setVisibility(View.GONE);
                        tv_jihuo_help.setVisibility(View.GONE);
                    } else if (type == 1) {
                        tv_jihuozhuantai.setText(getResources().getString(R.string.weijihuo));
                        ll_jihuo.setVisibility(View.VISIBLE);
                        tv_jihuo_help.setVisibility(View.VISIBLE);
                        if(ll_channel!=null)ll_channel.setVisibility(View.VISIBLE);
                        tv_channel_name.setText(channel);
                        tv_jihuo_help.setText(getResources().getString(R.string.jihuo_help));
                        tv_jihuo_help.setTextColor(getColor(R.color.yellow));
                    }
                } else if (type == 8) {
                    tv_data.setText(getResources().getString(R.string.daoqi_data) + " :");
//                    tv_data.setText("到期时间:");
                    dingdan_data.setText(((OrderitemBean.DataBean) o).getStopTime());
                    tv_dingdan_number.setText(getResources().getString(R.string.dingdan) + ((OrderitemBean.DataBean) o).getSkuName());
                    ll_jihuo.setVisibility(View.GONE);


                    if(ll_channel!=null)ll_channel.setVisibility(View.VISIBLE);
                    tv_channel_name.setText(channel);

                    if(ll_starttime!=null)ll_starttime.setVisibility(View.VISIBLE);
                    tv_start_time.setText(((OrderitemBean.DataBean) o).getStartTime());

                    if(ll_stop_time!=null)ll_stop_time.setVisibility(View.VISIBLE);
                    tv_stoptime.setText(((OrderitemBean.DataBean) o).getStopTime());


                    tv_jihuozhuantai.setText(getResources().getString(R.string.yijihuo));
                    tv_jihuozhuantai.setTextColor(getColor(R.color.green));
                    tv_jihuo_help.setVisibility(View.GONE);
                    ll_xq.setVisibility(View.GONE);

                }
            }
        }

        ;
        rvDingdan.setAdapter(adapter);
    }


    public void startacitivy_rent(int isZhuner,String channelName){
        try {
            sendtoServer(channelName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startActivity_APN(){
//        Intent intent =  new Intent(android.provider.Settings.ACTION_APN_SETTINGS);
////        //intent.setAction("android.intent.action.INSERT");
////        startActivity(intent);
//
//        ComponentName componentName = new ComponentName("com.android.settings", "com.android.settings.Settings");
//        intent.setComponent(componentName);
//        //intent.putExtra("", "");//这里Intent传值
//        startActivity(intent);

//        Intent intent = new Intent();
//        intent.setClassName("com.android.settings", "com.android.settings.ApnSettings");
//        startActivity(intent);
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


    private static final int MSG_TO_SERVER = 20;
    private static final int MSG_FROM_SERVER = 21;

    private Message mMessage;
    private Messenger mMessenger;
    @SuppressLint("HandlerLeak")
    private Messenger getMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg == null) return;
            if (msg.what == MSG_FROM_SERVER) {
                Log.e("hjs2", "MSG_FROM_SERVER");
                try {
                    Bundle bundle = msg.getData();

                    int iszhuner = bundle.getInt("iszhuner",-1);
                   String  spchannel = bundle.getString("channel");
                    Log.e("hjs2", ""+spchannel+":"+iszhuner);

                    rentdialog(iszhuner,spchannel);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            super.handleMessage(msg);
        }
    });
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMessenger = new Messenger(service);
            mMessage = Message.obtain(null, 0);
            mMessage.replyTo = getMessenger;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    private  void sendtoServer(String channel){
        if (mMessenger != null) {
            Log.e("hjs", "order开始发送：");
            mMessage.what = MSG_TO_SERVER;
            Bundle bundle = new Bundle();
            bundle.putString("channel", ""+channel);
            mMessage.setData(bundle);
            try {
                mMessenger.send(mMessage);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private   final String bunder_iszhuner = "zhuner";
    private  final String bunder_qudao = "kefu";
    private  final String order_channelName = "order_channelName";

    /**
     * 跳转
     */
    private void rentdialog(int isZhuner,String channelName){

        try {
            Message message = new Message();
            Bundle bun = new Bundle();

            if((isZhuner==1)) {
                bun.putString(bunder_iszhuner, "zhuner");
            }else if(isZhuner==0) {
                bun.putString(bunder_qudao,channelName);
            }else{
                if(TextUtils.isEmpty(channelName)){
                    bun.putString(bunder_iszhuner, "zhuner");
                    bun.putString(bunder_qudao,"");
                }else{
                    bun.putString(bunder_iszhuner, "zz");
                    bun.putString(bunder_qudao,channelName);
                }
            }
            message.setData(bun);
            Intent keepuse =   new Intent(this, RentKeepUseActivity.class);
            keepuse.putExtras(bun);
            startActivity(keepuse);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            unbindService(mServiceConnection);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
