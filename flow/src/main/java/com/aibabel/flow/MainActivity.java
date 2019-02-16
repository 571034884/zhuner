package com.aibabel.flow;

import android.content.Context;
import android.net.TrafficStats;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.tv_today_flow_number)
    TextView tvTodayFlowNumber;
    @BindView(R.id.tv_today_flow_Company)
    TextView tvTodayFlowCompany;
    @BindView(R.id.ll_today)
    LinearLayout llToday;
    @BindView(R.id.tv_today_flow)
    TextView tvTodayFlow;
    @BindView(R.id.tv_num_flow_number)
    TextView tvNumFlowNumber;
    @BindView(R.id.tv_num_flow_Company)
    TextView tvNumFlowCompany;
    @BindView(R.id.tv_num_flow)
    TextView tvNumFlow;
    private String str_time;
    private long now_flow;
    private long zero_now_flow;
    private long today_flow;
    private long last_zero_now_flow;
    private int TIME = 30000;
    private long now_flow_num;
    private long now_flow_last;
    private Timer timer;
    private String str_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        get_flow();
                    }
                });

            }
        };
        timer.schedule(task, 0, 1000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
//        SharePrefUtil.saveString(MainActivity.this,"date",str_date);
    }

    public long get_flow_num() {
        //获取关机前的流量
        now_flow_last = SharePrefUtil.getLong(MainActivity.this, "now_flow", 0);
        Log.e("guanjihou", now_flow_last + "");
        //开机到现在的流量（重启之后）
        now_flow = flow();
        now_flow_num = now_flow + now_flow_last;
        return now_flow_num;
    }

    public void get_flow() {
        //上次的 流量(关机之前)
        String date_old = SharePrefUtil.getString(MainActivity.this, "date", "");
        SimpleDateFormat sdf_date = new SimpleDateFormat("yy-MM-dd");
        Date date_new = new Date();
        str_date = sdf_date.format(date_new);

        now_flow_num = get_flow_num();
        Log.e("guanji1", now_flow_num + "");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date data = new Date();
        str_time = sdf.format(data);

        if (TextUtils.equals(date_old, str_date)) {
            /*if (str_time.equals("00:00:00")) {
                zero_now_flow = get_flow_num();
                SharePrefUtil.saveLong(Main2Activity.this, "zero_now_flow", zero_now_flow);
                today_flow = now_flow_num - zero_now_flow;
            } else {*/
            last_zero_now_flow = SharePrefUtil.getLong(MainActivity.this, "zero_now_flow", 0);
            today_flow = now_flow_num - last_zero_now_flow;
//            }
        }else {
            last_zero_now_flow = SharePrefUtil.getLong(MainActivity.this, "now_flow", 0);
            SharePrefUtil.saveLong(MainActivity.this, "zero_now_flow", last_zero_now_flow);
            today_flow = now_flow_num - last_zero_now_flow;
        }
//        textView.setText(str_time);
//        textView2.setText(now_flow_num+ "");
//        tvNumFlowNumber.setText(getAvailableInternalMemorySize(this, (1024*1024*1024)));

//        Spannable_str(tvNumFlowNumber,getAvailableInternalMemorySize(this, now_flow_num));

        String tv_now = getAvailableInternalMemorySize(this, now_flow_num);
        String tv_today = getAvailableInternalMemorySize(this, today_flow);
        String tv_now_company = tv_now.substring(tv_now.length() - 2, tv_now.length());
        String tv_now_number = tv_now.substring(0, tv_now.length() - 2);
        String tv_today_company = tv_today.substring(tv_today.length() - 2, tv_today.length());
        String tv_today_number = tv_today.substring(0, tv_today.length() - 2);

        tvTodayFlowNumber.setText(tv_today_number.trim());
        tvTodayFlowCompany.setText(tv_today_company);
        tvNumFlowNumber.setText(tv_now_number.trim());
        tvNumFlowCompany.setText(tv_now_company);
//        tvTodayFlowNumber, tv_today
//                Spannable_str(tvNumFlowNumber, getAvailableInternalMemorySize(this, (1024 * 1024 * 1024)));
//        Spannable_str(tvTodayFlowNumber,getAvailableInternalMemorySize(this, today_flow));
//        Spannable_str();
//        tvNumFlowNumber.setText(getAvailableInternalMemorySize(this, now_flow_num).);
//        textView3.setText(today_flow + "");
//        tvTodayFlowNumber.setText(getAvailableInternalMemorySize(this, 1024));
//        tvTodayFlowNumber.setText(getAvailableInternalMemorySize(this, today_flow));
    }

    public long flow() {
        long mobileRxBytes = TrafficStats.getMobileRxBytes();
        long mobileTxBytes = TrafficStats.getMobileTxBytes();
        //开机到现在一共的流量
        long now_flow = mobileRxBytes + mobileTxBytes;
        return now_flow;
    }

    public long flow_M(long flow) {
        return flow / 1024 / 1024;


    }

    /**
     * 获取手机内部可用存储空间
     *
     * @param context
     * @return 以M, G为单位的容量
     */
    public static String getAvailableInternalMemorySize(Context context, long flow) {

        return Formatter.formatFileSize(context, flow);
    }
}
