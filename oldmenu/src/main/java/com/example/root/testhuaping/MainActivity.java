package com.example.root.testhuaping;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aibabel.baselibrary.mode.DataManager;
import com.aibabel.baselibrary.utils.FileUtil;
import com.aibabel.baselibrary.utils.FilesUtil;
import com.aibabel.baselibrary.utils.ToastUtil;
import com.example.root.testhuaping.service.Getsystem_info;
import com.example.root.testhuaping.util.CommonUtils;
import com.example.root.testhuaping.util.Constans;
import com.linkfield.softsim.ISoftSIMCallback;
import com.linkfield.softsim.ISoftSIMManager;
import com.linkfield.softsim.model.SoftSIMInfo;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

import static com.example.root.testhuaping.DateUtils.dateToLong;
import static com.example.root.testhuaping.DateUtils.stringToDate;


public class MainActivity extends FragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private final Uri CONTENT_URI = Uri.parse("content://com.dommy.qrcode/aibabel_information");
    private View tv1, tv2, tv3, tv4, tv5, tv6;
    private View tv11, tv12, tv13, tv14, tv15, tv16;
    private View tv21, tv22, tv23, tv24, tv25, tv26, tvwww, tv_lixian;
    private ViewPager vpager_one;
    private ArrayList<View> aList;
    private MyPagerAdapter mAdapter;
    private ImageView imageView;
    private LinearLayout ll_dots;
    private List<TextView> dots;
    private TextView tvDate;
    public String startTime, endTime, date, ffdTime;
    public SimpleDateFormat sdf;
    public boolean shucode = false;
    private NetworkChangeListener mNetworkListener;
    private IntentFilter intentFilter;
    public String endTime_tt;
    public String hao, hao_quan;
    public long endtttime;
    public String tt_wzf;
    public String delay_wzf;
    public String time_day = null;
    public String ttt;
    public ImageView imgview_tt;
    private LinearLayout ll;
    private final String TAG = "testhuaping";
    private boolean isnet = false;
    private final boolean DEG = true;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置去除ActionBar
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Intent intent1=new Intent();
        //intent1.setClass(MainActivity.this,MainActivity2.class);
        //startActivity(intent1);
        if (getCountryZipCode(this).equals("CN")) {
            setContentView(R.layout.activity_main);
            Log.e("====================", "wotewode");

            dots = new ArrayList<TextView>();

            ll_dots = findViewById(R.id.ll_dots);
            ll_dots.removeAllViews();
            dots.removeAll(dots);
            for (int i = 0; i < 3; i++) {
                TextView v_dot = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_for_ocr_viewpager_point, null);
                LinearLayout.LayoutParams docParams;
                docParams = new LinearLayout.LayoutParams(15, 15);
                v_dot.setBackgroundResource(R.drawable.dot_normal);

                docParams.setMargins(5, 0, 5, 0);
                v_dot.setLayoutParams(docParams);
                ll_dots.addView(v_dot);
                dots.add(v_dot);
            }
            dots.get(0).setBackground(getResources().getDrawable(R.drawable.dot_normal_current));
            vpager_one = (ViewPager) findViewById(R.id.vpager_main);
            aList = new ArrayList<View>();
            LayoutInflater li = getLayoutInflater();
            aList.add(li.inflate(R.layout.view_one, null, false));
            aList.add(li.inflate(R.layout.view_two, null, false));
            aList.add(li.inflate(R.layout.view_three, null, false));
            mAdapter = new MyPagerAdapter(aList);
            vpager_one.setAdapter(mAdapter);

            tv1 = aList.get(0).findViewById(R.id.tv_yuyin);
            tv2 = aList.get(0).findViewById(R.id.tv_paizhao);
            //tv3=(TextView) aList.get(0).findViewById(R.id.tv_quanqiu);
            //tv4=(TextView) aList.get(0).findViewById(R.id.tv_zhenren);
            tv5 = aList.get(0).findViewById(R.id.tv_duoji);
            tv6 = aList.get(0).findViewById(R.id.tv_wuti);
            tv11 = aList.get(0).findViewById(R.id.tv_shiyong);
            tv1.setOnClickListener(this);
            tv2.setOnClickListener(this);
            //tv3.setOnClickListener(this);
            //tv4.setOnClickListener(this);
            tv5.setOnClickListener(this);
            tv6.setOnClickListener(this);
            tv11.setOnClickListener(this);


            tv12 = aList.get(1).findViewById(R.id.tv_mishu);
            tv13 = aList.get(1).findViewById(R.id.tv_huilv);
            tv14 = aList.get(1).findViewById(R.id.tv_tianqi);
            tv15 = aList.get(1).findViewById(R.id.tv_shijiezhong);
            tv16 = aList.get(1).findViewById(R.id.tv_sos);
            tv4 = aList.get(1).findViewById(R.id.tv_kefu);
            tv12.setOnClickListener(this);
            tv13.setOnClickListener(this);
            tv14.setOnClickListener(this);
            tv15.setOnClickListener(this);
            tv16.setOnClickListener(this);
            tv4.setOnClickListener(this);

            //tv21 = (TextView) aList.get(2).findViewById(R.id.tv_zuche);
            tv22 = aList.get(2).findViewById(R.id.tv_jingqu);
            tv23 = aList.get(2).findViewById(R.id.tv_dangdi);
            tv24 = aList.get(2).findViewById(R.id.tv_daohang);
            tv25 = aList.get(2).findViewById(R.id.tv_meishi);
            tvwww = aList.get(2).findViewById(R.id.tv_quanqiu);
            tv_lixian = aList.get(2).findViewById(R.id.tv_xiazai);
            tv_lixian.setOnClickListener(this);
            tv22.setOnClickListener(this);
            tv23.setOnClickListener(this);
            tv24.setOnClickListener(this);
            tv25.setOnClickListener(this);
            tvwww.setOnClickListener(this);
        } else {
            setContentView(R.layout.activity_main);
            dots = new ArrayList<TextView>();

            ll_dots = findViewById(R.id.ll_dots);
            ll_dots.removeAllViews();
            dots.removeAll(dots);
            for (int i = 0; i < 2; i++) {
                TextView v_dot = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_for_ocr_viewpager_point, null);
                LinearLayout.LayoutParams docParams;
                docParams = new LinearLayout.LayoutParams(15, 15);
                v_dot.setBackgroundResource(R.drawable.dot_normal);

                docParams.setMargins(5, 0, 5, 0);
                v_dot.setLayoutParams(docParams);
                ll_dots.addView(v_dot);
                dots.add(v_dot);
            }
            dots.get(0).setBackground(getResources().getDrawable(R.drawable.dot_normal_current));
            vpager_one = (ViewPager) findViewById(R.id.vpager_main);
            aList = new ArrayList<View>();
            LayoutInflater li = getLayoutInflater();
            aList.add(li.inflate(R.layout.view_one_wai, null, false));
            aList.add(li.inflate(R.layout.view_two_wai, null, false));
            mAdapter = new MyPagerAdapter(aList);
            vpager_one.setAdapter(mAdapter);


            tv1 = aList.get(0).findViewById(R.id.tv_yuyin);
            tv2 = aList.get(0).findViewById(R.id.tv_paizhao);
            tv3 = aList.get(0).findViewById(R.id.tv_quanqiu);
            //tv4=(TextView) aList.get(0).findViewById(R.id.tv_zhenren);
            //tv5 =  aList.get(0).findViewById(R.id.tv_duoji);
            tv6 = aList.get(0).findViewById(R.id.tv_wuti);
            tv11 = aList.get(0).findViewById(R.id.tv_huilv);
            tv1.setOnClickListener(this);
            tv2.setOnClickListener(this);
            tv3.setOnClickListener(this);
            //tv4.setOnClickListener(this);
            //tv5.setOnClickListener(this);
            tv6.setOnClickListener(this);
            tv11.setOnClickListener(this);


            tv12 = aList.get(1).findViewById(R.id.tv_guditu);
            tv13 = aList.get(1).findViewById(R.id.tv_quan);
            //tv14 =  aList.get(1).findViewById(R.id.tv_tianqi);
            //tv15 =  aList.get(1).findViewById(R.id.tv_shijiezhong);
            tv16 = aList.get(1).findViewById(R.id.tv_sos);
            tv21 = aList.get(1).findViewById(R.id.tv_zuche);
            tv12.setOnClickListener(this);
            tv13.setOnClickListener(this);
            tv_lixian = aList.get(1).findViewById(R.id.tv_xiazai);
            tv_lixian.setOnClickListener(this);
            //tv14.setOnClickListener(this);
            //tv15.setOnClickListener(this);
            tv16.setOnClickListener(this);
            tv21.setOnClickListener(this);
        }

        imageView = (ImageView) findViewById(R.id.imgview);

        ll = (LinearLayout) findViewById(R.id.ll);
        hao_quan = getDeviceInfo();
        hao = hao_quan.substring(106, 124);
        initOkGo();
        if (TextUtils.equals("H", hao.substring(1, 2))) {

            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) imageView.getLayoutParams();
            lp.setMargins(0, 0, 0, 0);
            imageView.setLayoutParams(lp);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            TranslateAnimation translateAnimation = new TranslateAnimation(0, -200, 0, 0);
            translateAnimation.setDuration(15000);
            //translateAnimation.setRepeatCount(200000);
            translateAnimation.setRepeatCount(Animation.INFINITE);

            translateAnimation.setRepeatMode(Animation.REVERSE);
            imageView.setAnimation(translateAnimation);
            imageView.startAnimation(translateAnimation);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);


        }

        vpager_one.setOnPageChangeListener(this);

        final IntentFilter filter = new IntentFilter();
        // 屏幕灭屏广播
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        // 屏幕亮屏广播
        filter.addAction(Intent.ACTION_SCREEN_ON);
        // 屏幕解锁广播
        filter.addAction(Intent.ACTION_USER_PRESENT);
        // 当长按电源键弹出“关机”对话或者锁屏时系统会发出这个广播
        // example：有时候会用到系统对话框，权限可能很高，会覆盖在锁屏界面或者“关机”对话框之上，
        // 所以监听这个广播，当收到时就隐藏自己的对话，如点击pad右下角部分弹出的对话框
        filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                //Log.d(TAG, "onReceive");
                String action = intent.getAction();

                if (Intent.ACTION_SCREEN_ON.equals(action)) {
                    //Intent intent1=new Intent();
                    //intent1.setClass("com.example.root.screensavers","com.example.root.screensavers.MainActivity");
                    //startActivity(intent1);
                    //Toast.makeText(MainActivity.this, "亮屏", Toast.LENGTH_LONG).show();
                } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                    //Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.example.root.screensavers");
                    //startActivity(LaunchIntent);
                    //Log.d(TAG, "screen off");
                } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
                    //Log.d(TAG, "screen unlock");
                } else if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction())) {
                    //Log.i(TAG, " receive Intent.ACTION_CLOSE_SYSTEM_DIALOGS");
                }
            }
        };
        //Log.d(TAG, "registerReceiver");
        //registerReceiver(mBatInfoReceiver, filter);
        //mHandler.postDelayed(r, 5000);

        /*ServiceUtils serviceUtils = new ServiceUtils();
        try {
            if (serviceUtils.isServiceWork(this, "com.aibabel.locationservice.service.LocationService")) {
                //Toast.makeText(MainActivity.this, "已启动", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent();
                intent.setPackage("com.aibabel.locationservice");
                intent.setAction("android.intent.action.START_B_SERVICE");
                startService(intent);
                Log.d("weiqidong","yiqidong");
                //Toast.makeText(MainActivity.this, "未启动", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
        }
        try {
            Log.e("wzf","start");
            Cursor cursor=MainActivity.this.getContentResolver().query(CONTENT_URI,null,null,null,null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    //Log.e("wzf","11111111111111111111111");
                    ffdTime=cursor.getString(cursor.getColumnIndex("fft"));
                    startTime=cursor.getString(cursor.getColumnIndex("start_time"));
                    Log.e("wzf","ffdTime="+ffdTime);
                    endTime=cursor.getString(cursor.getColumnIndex("end_time"));
                    Log.e("wzf","endTime="+endTime);
                    //Toast.makeText(MainActivity.this, "startTime="+startTime, Toast.LENGTH_SHORT).show();
                    //Log.e("wzf","4444444444444444444444444444");
                    //Toast.makeText(MainActivity.this, "endTime="+endTime, Toast.LENGTH_SHORT).show();
                    //Log.e("wzf","end");

                } while (cursor.moveToNext());
            }

        }catch (Exception e){
            Log.e("wzf","tianxia="+e.getMessage());
        }
        //mHandler.postDelayed(r, 1000);

        try {

            Intent intent_softsim = new Intent();
            intent_softsim.setPackage("com.linkfield.softsim");
            intent_softsim.setAction("com.linkfield.softsim.service.SoftSIMService");
            //bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
            startService(intent_softsim);
            Log.e("softsim","softsim");
            Toast.makeText(MainActivity.this, "softsim", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }*/

        //启动服务
        /*startService(new Intent(MainActivity.this, MyService.class));
        //注册广播接收器
        receiver=new MyReceiver();
        IntentFilter filter1=new IntentFilter();
        filter1.addAction("com.example.root.testhuaping.service.MyService");
        MainActivity.this.registerReceiver(receiver,filter1);*/


        /*msgReceiver=new Receiver_yanqi();
        IntentFilter filter2=new IntentFilter();
        filter2.addAction("com.android.zhuner.time");
        MainActivity.this.registerReceiver(msgReceiver,filter2);*/

        hao_quan = getDeviceInfo();
        hao = hao_quan.substring(106, 124);
        //Log.e("wzf","hao_quan="+hao_quan);
        //Log.e("wzf","hao="+hao);

        if (TextUtils.equals("H", hao.substring(1, 2))) {
//        imgview.setImageResource((R.drawable.fly_launcher));
            imageView.setImageResource(R.drawable.fly_launcher);

        }

        String tt = Getsystem_info.getSN_SN();

        //获取iccid
        initMtkDoubleSim();
        //获取imei
        init_imei();

        get_okgo_net();
    }

    //1 硬卡   2软卡
    public void get_okgo_net() {
        boolean softSim = FilesUtil.readToFile();
        if (softSim) {
            saveFile(true);
            initService();
            Log.e("LK---001", "启动成功");
//            mSoftSIMManager.isSoftSIMEnabled();
        } else {
            isnet = true;
            initReceiver();
            Log.e("LK---001", "没有启动过软卡");
        }
    }

    /**
     * 启动软卡 领科
     */
    public void start_soft() {
        try {
            mSoftSIMManager.setSoftSIMEnabled(true);
            saveFile(true);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
    //--------------------------------------------------------------------------------

    /**
     * 单独存储
     * @param flag
     */
    public void saveFile(boolean flag) {
        FilesUtil.saveToFile(flag + "", new FilesUtil.SaveCompleteListener() {
            @Override
            public void Success(String body) {
                Log.e("LK---001", body);
            }

            @Override
            public void failure(String error) {
                Log.e("LK---001", error);
            }
        });
    }

    private ISoftSIMManager mSoftSIMManager;
    private SoftSIMInfo mSoftSIMInfo;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mSoftSIMManager = ISoftSIMManager.Stub.asInterface(iBinder);
            try {
                mSoftSIMManager.registerCallback(mCallback);
                mSoftSIMInfo = mSoftSIMManager.getSoftSIMInfo();
                if (mSoftSIMInfo != null && mSoftSIMInfo.getType() != null) {
                    DataManager.getInstance().setSaveString("softSimType", mSoftSIMInfo.getType().toString());
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mSoftSIMManager = null;
        }
    };
    private ISoftSIMCallback mCallback = new ISoftSIMCallback.Stub() {
        @Override
        public void onSoftSIMEvent(int type, int subtype) {

        }

        @Override
        public void onSoftSIMStateChange(SoftSIMInfo info) {
            try {
                mSoftSIMInfo = info;
                if (info != null && !TextUtils.isEmpty(mSoftSIMInfo.getType().toString())){
                    DataManager.getInstance().setSaveString("softSimType", mSoftSIMInfo.getType().toString());
                    Log.e("LK---001", "存储LK标识" + mSoftSIMInfo.getType().toString());
                    mHandler.sendMessage(mHandler.obtainMessage(10000, mSoftSIMInfo));
                }else{
                    saveFile(false);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 10000:
                    SoftSIMInfo info = (SoftSIMInfo) msg.obj;
                    try {
                        if (mSoftSIMManager.isSoftSIMEnabled()) {
                            Log.e("LK---001", "启动成功" + info.getIMSI());
                            //TODO 存储软卡信息
                            saveFile(true);
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
        Intent intent = new Intent("com.linkfield.softsim.service.SoftSIMService");
        intent.setPackage("com.linkfield.softsim");
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        //注册动态广播
        receiveBroadCast = new ReceiveBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.oldmenu");
        registerReceiver(receiveBroadCast, filter);

    }

    private ReceiveBroadCast receiveBroadCast;

    class ReceiveBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //得到广播中得到的数据，并显示出来
            Log.e("LK---001", "来自全球上网--进行refreshProfile刷新");
            String type = intent.getExtras().getString("type");
            if (type.equals("refresh")) {
                try {
                    if (mSoftSIMManager != null) {
                        Log.e("LK---001", "桌面收到来自全球上网的refreshProfile-lksc");
                        boolean flag = mSoftSIMManager.refreshProfile();
                        if (!flag) {
                            ToastUtil.showShort(context, "请重启设备，激活套餐");
                            Log.e("LK---001", "refreshProfile-lksc----失败");
                        }
                    } else {
                        ToastUtil.showShort(context, "请重启设备，激活套餐");
                        Log.e("LK---001", "refreshProfile-lksc----没有拿到引用mSoftSIMManager");
                    }

                } catch (RemoteException e) {
                    ToastUtil.showShort(context, "请重启设备，激活套餐");
                    Log.e("LK---001", "refreshProfile-lksc----异常");
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        if (mSoftSIMManager != null) {
            try {
                mSoftSIMManager.registerCallback(mCallback);
                mSoftSIMInfo = mSoftSIMManager.getSoftSIMInfo();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    //--------------------------------------------------------------------------------


    /**
     * 注册网络监听的广播
     */
    private void initReceiver() {
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mNetworkListener = new NetworkChangeListener();
        registerReceiver(mNetworkListener, intentFilter);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void initMtkDoubleSim() {

        try {
            List<SubscriptionInfo> list = SubscriptionManager.from(this).getActiveSubscriptionInfoList();
            if (null == list || list.size() == 0) {

            }


            for (int i = 0; i < list.size(); i++) {
                if (DEG) Log.e("Q_M", "ICCID-->" + list.get(i).getIccId());
                if (DEG) Log.e("Q_M", "sim_id-->" + list.get(i).getSimSlotIndex());
                if (list.get(i).getSimSlotIndex() == 1) {
                    if (DEG) Log.e("iccid123", list.get(i).getIccId());
                    String iccids = list.get(i).getIccId();

                    iccid =  iccids;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void init_imei() {


        TelephonyManager tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            imei =  "";
        }
        imei = telephonyManager.getDeviceId(0);
        //Toast.makeText(MainActivity.this, "slot=" + slot, Toast.LENGTH_LONG).show();

    }

    private String imei;
    private String iccid;

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public void get_okgo() {

        String sn = CommonUtils.getSN();

        Log.e("LK---001","url = "+Constans.MENU_LK+"-----imei = "+imei+"-----sn = "+sn+"------iccid = "+iccid);

        if (TextUtils.isEmpty(imei) || TextUtils.isEmpty(sn) || TextUtils.isEmpty(iccid)){
            ToastUtil.showShort(this,"无法获取到重要标识，请联系客服");
            return;
        }

        OkGo.<String>get(Constans.MENU_LK)
                .tag(this)
                .params("imei",imei)
                .params("sn",sn)
                .params("iccid",iccid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        //注意这里已经是在主线程了
                        String data_sim = response.body();//这个就是返回来的结果
                        try {
                            JSONObject mJsonObject = new JSONObject(data_sim);
                            String data1 = mJsonObject.getString("data");
                            //Log.e("TAG","data="+data1);
                            if (TextUtils.equals(data1, "2")) {
                                if (TextUtils.equals(getVersionType(), "PL") || TextUtils.equals(getVersionType(), "PH")) {
                                    if (TextUtils.equals(getVersionCode(), "S")) {
                                        saveFile(true);
                                        start_soft();
                                    }
                                }
                            } else {
                                saveFile(false);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }


                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
//                        Toast.makeText(MainActivity.this, "出错了！", Toast.LENGTH_SHORT).show();
                        //TODO 服务器出错
                        saveFile(false);
                    }
                });
    }

    /**
     * PH fly
     * PL pro
     * PM go
     *
     * @return
     */
    private String getVersionType() {
        try {
            String display = Build.DISPLAY;
            return display.substring(0, 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * S 销售   L租赁
     *
     * @return
     */
    private String getVersionCode() {
        try {
            String display = Build.DISPLAY;
            return display.substring(9, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 重写onKeyDown方法可以拦截系统默认的处理
     */
   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(this, "后退键", Toast.LENGTH_SHORT).show();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            Toast.makeText(this, "声音+", Toast.LENGTH_SHORT).show();
            return false;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            Toast.makeText(this, "声音-", Toast.LENGTH_SHORT).show();
            return false;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_MUTE) {
            Toast.makeText(this, "静音", Toast.LENGTH_SHORT).show();
            return false;
        } else if (keyCode == KeyEvent.KEYCODE_HOME) {
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
            return true;
        }else if (keyCode == 131) {
            Toast.makeText(this, "131", Toast.LENGTH_SHORT).show();
            return true;
        }else if (keyCode == 132) {
            Toast.makeText(this, "132", Toast.LENGTH_SHORT).show();
            return true;
        }else if (keyCode == 133) {
            Toast.makeText(this, "133", Toast.LENGTH_SHORT).show();
            return true;
        }else if (keyCode == 134) {
            Toast.makeText(this, "134", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/

    /**
     * 判断当前网络是否可用(6.0以上版本) * 实时 * @param context * @return
     */
    public static boolean isNetSystemUsable(Context context) {
        boolean isNetUsable = false;
        try {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                NetworkCapabilities networkCapabilities = manager.getNetworkCapabilities(manager.getActiveNetwork());
                isNetUsable = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return isNetUsable;
    }

    private void initOkGo() {
        //---------这里给出的是示例代码,告诉你可以这么传,实际使用的时候,根据需要传,不需要就不传-------------//
        HttpHeaders headers = new HttpHeaders();
        headers.put("commonHeaderKey1", "commonHeaderValue1");    //header不支持中文，不允许有特殊字符
        headers.put("commonHeaderKey2", "commonHeaderValue2");
        HttpParams params = new HttpParams();
        params.put("commonParamsKey1", "commonParamsValue1");     //param支持中文,直接传,不要自己编码
        params.put("commonParamsKey2", "这里支持中文参数");
        //----------------------------------------------------------------------------------------//

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setColorLevel(Level.INFO);                               //log颜色级别，决定了log在控制台显示的颜色
        builder.addInterceptor(loggingInterceptor);                                 //添加OkGo默认debug日志
        //第三方的开源库，使用通知显示当前请求的log，不过在做文件下载的时候，这个库好像有问题，对文件判断不准确
        //builder.addInterceptor(new ChuckInterceptor(this));

        //超时时间设置，默认60秒
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);   //全局的连接超时时间

        //自动管理cookie（或者叫session的保持），以下几种任选其一就行
        //builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));            //使用sp保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));              //使用数据库保持cookie，如果cookie不过期，则一直有效
        //builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));            //使用内存保持cookie，app退出后，cookie消失

        //https相关设置，以下几种方案根据需要自己设置
        //方法一：信任所有证书,不安全有风险
        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
        //方法二：自定义信任规则，校验服务端证书
        HttpsUtils.SSLParams sslParams2 = HttpsUtils.getSslSocketFactory(new SafeTrustManager());
        //方法三：使用预埋证书，校验服务端证书（自签名证书）
        //HttpsUtils.SSLParams sslParams3 = HttpsUtils.getSslSocketFactory(getAssets().open("srca.cer"));
        //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
        //HttpsUtils.SSLParams sslParams4 = HttpsUtils.getSslSocketFactory(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"));
        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);
        //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
        builder.hostnameVerifier(new SafeHostnameVerifier());

        // 其他统一的配置
        // 详细说明看GitHub文档：https://github.com/jeasonlzy/
        OkGo.getInstance().init(getApplication())                           //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置会使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3)                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
                .addCommonHeaders(headers)                      //全局公共头
                .addCommonParams(params);                       //全局公共参数
    }

    /**
     * 这里只是我谁便写的认证规则，具体每个业务是否需要验证，以及验证规则是什么，请与服务端或者leader确定
     * 这里只是我谁便写的认证规则，具体每个业务是否需要验证，以及验证规则是什么，请与服务端或者leader确定
     * 这里只是我谁便写的认证规则，具体每个业务是否需要验证，以及验证规则是什么，请与服务端或者leader确定
     * 重要的事情说三遍，以下代码不要直接使用
     */
    private class SafeTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            try {
                for (X509Certificate certificate : chain) {
                    certificate.checkValidity(); //检查证书是否过期，签名是否通过等
                }
            } catch (Exception e) {
                throw new CertificateException(e);
            }
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    /**
     * 这里只是我谁便写的认证规则，具体每个业务是否需要验证，以及验证规则是什么，请与服务端或者leader确定
     * 这里只是我谁便写的认证规则，具体每个业务是否需要验证，以及验证规则是什么，请与服务端或者leader确定
     * 这里只是我谁便写的认证规则，具体每个业务是否需要验证，以及验证规则是什么，请与服务端或者leader确定
     * 重要的事情说三遍，以下代码不要直接使用
     */
    private class SafeHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            //验证主机名是否匹配
            //return hostname.equals("server.jeasonlzy.com");
            return true;
        }
    }

    public static String getDateDelay(long data, int delay) {
        long temp = data + 86400000 * delay;
        Date d = new Date(temp);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = sdf.format(d);
        Log.e("wzf", "format=" + format);
        return format;
    }

    private String getDeviceInfo() {

        StringBuffer sb = new StringBuffer();
        sb.append("主板： " + Build.BOARD);
        sb.append("\n系统启动程序版本号： " + Build.BOOTLOADER);
        sb.append("\n系统定制商： " + Build.BRAND);
        sb.append("\ncpu指令集： " + Build.CPU_ABI);
        sb.append("\ncpu指令集2 " + Build.CPU_ABI2);
        sb.append("\n设置参数： " + Build.DEVICE);
        sb.append("\n显示屏参数：" + Build.DISPLAY);
        sb.append("\n无线电固件版本：" + Build.getRadioVersion());
        sb.append("\n硬件识别码： " + Build.FINGERPRINT);
        sb.append("\n硬件名称： " + Build.HARDWARE);
        sb.append("\nHOST: " + Build.HOST);
        sb.append("\nBuild.ID);" + Build.ID);
        sb.append("\n硬件制造商： " + Build.MANUFACTURER);
        sb.append("\n版本： " + Build.MODEL);
        sb.append("\n硬件序列号： " + Build.SERIAL);
        sb.append("\n手机制造商： " + Build.PRODUCT);
        sb.append("\n 描述Build的标签： " + Build.TAGS);
        sb.append("\nTIME: " + Build.TIME);
        sb.append("\nbuilder类型" + Build.TYPE);
        sb.append("\nUSER: " + Build.USER);
        return sb.toString();
    }

/*
    Handler mHandler = new Handler();
    Runnable r = new Runnable() {

        @Override
        public void run() {
            //do something
            //每隔1s循环执行run方法
            sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
            IsToday();
            // 注册receiver
            mHandler.postDelayed(this, 1000);
        }
    };*/

    public void IsToday() {
        //String date ="20180620";
        sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        date = sdf.format(new Date().getTime());
        Log.e("wzf>>>>>>>>>>>>", "date=" + date);
        try {
            delay_wzf = (SharePrefUtil.getString(MainActivity.this, "delaytime", endTime)).toString();
            if (DateUtils.isDateOneBigger(date, ffdTime, delay_wzf)) {

                //Toast.makeText(this, "可以用", Toast.LENGTH_SHORT).show();
            } else {
                Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.dommy.qrcode");
                LaunchIntent.putExtra("erweima", "daoqi");
                startActivity(LaunchIntent);

                Intent intent = new Intent();
                intent.setAction("com.android.zhuner");
                intent.putExtra("Zhuner_devices", "time_end");
                MainActivity.this.sendBroadcast(intent);
                Toast.makeText(this, R.string.my_weizaizulinqi, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {

            Log.e("wzfff", e.getMessage());
        }

    }

    public void Immersive(Window window, ActionBar actionBar) {

        if (Build.VERSION.SDK_INT >= 21) {

            View view = window.getDecorView();
            // TODO: 2017/4/13 两个FLAG一起使用表示会让应用的主体内容占用系统状态栏的时空间
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            view.setSystemUiVisibility(option);
            // 将状态栏设置成透明色
            window.setStatusBarColor(Color.TRANSPARENT);

        }
        // 将ActionBar隐藏
        actionBar.hide();

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        ComponentName cmpName = null;
        if (getCountryZipCode(this).equals("CN")) {
            switch (v.getId()) {
                case R.id.tv_yuyin: {
                    try {
                     /*Intent intent1=new Intent();
                intent1.setClass(MainActivity.this,oneActivity.class);
                startActivity(intent1);
                //cmpName=new ComponentName("com.fyt.fyttranslateprj","com.fyt.fyttranslateprj.MainActivity");*/
                        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.aibabel.translate");
                        startActivity(LaunchIntent);
                    } catch (Exception e) {
                    }
                    break;
                }

                case R.id.tv_paizhao: {
                    try {
                        /*Intent intent1=new Intent();
                intent1.setClass(MainActivity.this,two2Activity.class);
                startActivity(intent1);*/

                        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.aibabel.ocr");
                        startActivity(LaunchIntent);
                        //Toast.makeText(MainActivity.this, "全新功能，敬请期待...", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                    }
                    break;
                }
                case R.id.tv_quanqiu:
                    try {
                        if (hao.substring(9, 10).equals("L")) {
                            ttt = SharePrefUtil.getString(MainActivity.this, "delaybole", "true");
                            Log.e("wzf", "ttt=" + ttt);
                            if (delay_wzf.equals("") || TextUtils.equals("true", ttt)) {
                                Intent intentAction = new Intent("com.android.zhuner.wqhtime");
                                intentAction.putExtra("Time_Start", ffdTime);
                                intentAction.putExtra("Time_End", delay_wzf);
                                intentAction.putExtra("Time_End_First", endTime);
                                Log.e("wzfwqh", "endTime=" + endTime);
                                SharePrefUtil.saveString(MainActivity.this, "delaybole", "false");
                                MainActivity.this.sendBroadcast(intentAction);

                            } else {
                                ttt = SharePrefUtil.getString(MainActivity.this, "delaybole", "true");
                                Log.e("wzf", "ttt=" + ttt);
                                Intent intentAction = new Intent("com.android.zhuner.wqhtime");
                                intentAction.putExtra("Time_Start", ffdTime);
                                intentAction.putExtra("Time_End", delay_wzf);
                                intentAction.putExtra("Time_End_First", endTime);
                                Log.e("wzfwqh", "Time_End=" + delay_wzf);
                                MainActivity.this.sendBroadcast(intentAction);

                            }
                            Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.aibabel.surfinternet");
                            startActivity(LaunchIntent);
                        } else {
                            Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.aibabel.surfinternet");
                            startActivity(LaunchIntent);
                        }
                    } catch (Exception e) {
                    }

                    break;
                case R.id.tv_wuti: {
                    try {
                        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.aibabel.travel");
                        startActivity(LaunchIntent);
                        //cmpName=new ComponentName("com.fyt.ocrobject","com.fyt.ocrobject.activity.TakePhoteActivity");
                    } catch (Exception e) {
                    }
                    break;
                }
                case R.id.tv_huilv: {
                    try {
                        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.zhuner.administrator.settings");
                        startActivity(LaunchIntent);
                        // Toast.makeText(MainActivity.this, "全新功能，敬请期待...", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                    }
                    break;
                }
                case R.id.tv_mishu: {
                    try {
                        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.aibabel.ocrobject");
                        startActivity(LaunchIntent);
                        //Toast.makeText(MainActivity.this, "全新功能，敬请期待...", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                    }
                    break;
                }
                case R.id.tv_tianqi: {
                    tt_wzf = ContentProviderUtil.getCountry(MainActivity.this);
                    if (TextUtils.equals("中国", tt_wzf)) {
                        Log.e("wzf", "" + ContentProviderUtil.getCountry(MainActivity.this));
                        Intent intent1 = new Intent();
                        intent1.setClass(MainActivity.this, oneActivity.class);
                        startActivity(intent1);
                        //Toast.makeText(MainActivity.this, "中国哦", Toast.LENGTH_SHORT).show();

                    } else {
                        try {
                            Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.apps.maps");
                            startActivity(LaunchIntent);
                            //Toast.makeText(MainActivity.this, "全新功能，敬请期待...", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                        }
                    }
                }
                break;
                case R.id.tv_shijiezhong: {
                    try {
                        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.googlequicksearchbox");
                        startActivity(LaunchIntent);
                        //Toast.makeText(MainActivity.this, "全新功能，敬请期待...", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                    }
                    break;
                }
                case R.id.tv_sos: {
                    try {
                        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.aibabel.speech");
                        startActivity(LaunchIntent);
                        //Toast.makeText(MainActivity.this, "全新功能，敬请期待...", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                    }
                    break;
                }
                case R.id.tv_jingqu: {
                    try {
                        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.aibabel.currencyconversion");
                        startActivity(LaunchIntent);
                        //Toast.makeText(MainActivity.this, "全新功能，敬请期待...", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                    }
                    break;
                }
                case R.id.tv_dangdi: {
                    Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.aibabel.weather");
                    startActivity(LaunchIntent);
                    //Toast.makeText(MainActivity.this, "全新功能，敬请期待...", Toast.LENGTH_SHORT).show();
                    break;
                }
                case R.id.tv_daohang: {
                    try {
                        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.aibabel.alliedclock");
                        startActivity(LaunchIntent);
                        //Toast.makeText(MainActivity.this, "全新功能，敬请期待...", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                    }
                    break;
                }
                case R.id.tv_meishi: {
                    try {
                        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.aibabel.sos");
                        startActivity(LaunchIntent);
                        //Toast.makeText(MainActivity.this, "全新功能，敬请期待...", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                    }
                    break;

                }
                case R.id.tv_duoji: {
                    try {
                        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.aibabel.traveladvisory");
                        startActivity(LaunchIntent);
                        //Toast.makeText(MainActivity.this, "全新功能，敬请期待...", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                    }
                    break;

                }
                case R.id.tv_xiazai: {
                    try {
                        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.aibabel.download.offline");
                        //LaunchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(LaunchIntent);
                        //Toast.makeText(MainActivity.this, "全新功能，敬请期待...", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                    }
                    break;

                }
                case R.id.tv_shiyong: {
                    try {
                        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.aibabel.readme");//com.aibabel.readme
                        startActivity(LaunchIntent);
                        //Toast.makeText(MainActivity.this, "全新功能，敬请期待...", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                    }
                    break;
                }
                case R.id.tv_kefu: {
                    try {
                        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.example.root.kefu");//com.aibabel.readme
                        startActivity(LaunchIntent);
                        //Toast.makeText(MainActivity.this, "全新功能，敬请期待...", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                    }
                    break;

                }
                default:
                    break;
            }
        } else {
            switch (v.getId()) {
                case R.id.tv_yuyin: {
                    try {
                     /*Intent intent1=new Intent();
                intent1.setClass(MainActivity.this,oneActivity.class);
                startActivity(intent1);
                //cmpName=new ComponentName("com.fyt.fyttranslateprj","com.fyt.fyttranslateprj.MainActivity");*/
                        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.aibabel.translate");
                        startActivity(LaunchIntent);
                    } catch (Exception e) {
                    }
                    break;
                }
                case R.id.tv_paizhao: {
                    try {
                        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.aibabel.ocr");
                        startActivity(LaunchIntent);
                    } catch (Exception e) {
                    }
                    break;
                }
                case R.id.tv_quanqiu:
                    try {
                        if (hao.substring(9, 10).equals("L")) {
                            ttt = SharePrefUtil.getString(MainActivity.this, "delaybole", "true");
                            Log.e("wzf", "ttt=" + ttt);
                            if (delay_wzf.equals("") || TextUtils.equals("true", ttt)) {
                                Intent intentAction = new Intent("com.android.zhuner.wqhtime");
                                intentAction.putExtra("Time_Start", ffdTime);
                                intentAction.putExtra("Time_End", delay_wzf);
                                intentAction.putExtra("Time_End_First", endTime);
                                Log.e("wzfwqh", "endTime=" + endTime);
                                SharePrefUtil.saveString(MainActivity.this, "delaybole", "false");
                                MainActivity.this.sendBroadcast(intentAction);

                            } else {
                                ttt = SharePrefUtil.getString(MainActivity.this, "delaybole", "true");
                                Log.e("wzf", "ttt=" + ttt);
                                Intent intentAction = new Intent("com.android.zhuner.wqhtime");
                                intentAction.putExtra("Time_Start", ffdTime);
                                intentAction.putExtra("Time_End", delay_wzf);
                                intentAction.putExtra("Time_End_First", endTime);
                                Log.e("wzfwqh", "Time_End=" + delay_wzf);
                                MainActivity.this.sendBroadcast(intentAction);

                            }
                            Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.aibabel.surfinternet");
                            startActivity(LaunchIntent);
                        } else {
                            Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.aibabel.surfinternet");
                            startActivity(LaunchIntent);
                        }
                    } catch (Exception e) {
                    }
                    break;
                case R.id.tv_duoji: {
                    try {
                        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.aibabel.speech");
                        startActivity(LaunchIntent);
                    } catch (Exception e) {
                    }
                    break;
                }
                case R.id.tv_wuti: {
                    try {
                        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.aibabel.ocrobject");
                        startActivity(LaunchIntent);
                        //cmpName=new ComponentName("com.fyt.ocrobject","com.fyt.ocrobject.activity.TakePhoteActivity");
                    } catch (Exception e) {
                    }
                    break;
                }
                case R.id.tv_guditu: {
                   /* try {
                        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.apps.maps");
                        startActivity(LaunchIntent);
                        // Toast.makeText(MainActivity.this, "全新功能，敬请期待...", Toast.LENGTH_SHORT).show();
                        // Toast.makeText(MainActivity.this, "全新功能，敬请期待...", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                    }*/

                    try {
                        tt_wzf = ContentProviderUtil.getCountry(MainActivity.this);
                        if (TextUtils.equals("中国", tt_wzf)) {

                            Intent intent1 = new Intent();
                            intent1.setClass(MainActivity.this, oneActivity.class);
                            startActivity(intent1);
                            //Toast.makeText(MainActivity.this, "中国哦", Toast.LENGTH_SHORT).show();
                        } else {

                            Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.apps.maps");
                            startActivity(LaunchIntent);
                            // Toast.makeText(MainActivity.this, "全新功能，敬请期待...", Toast.LENGTH_SHORT).show();
                            // Toast.makeText(MainActivity.this, "全新功能，敬请期待...", Toast.LENGTH_SHORT).show();

                        }
                    } catch (Exception e) {
                    }
                    break;
                }
                case R.id.tv_huilv: {
                    try {
                        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.zhuner.administrator.settings");
                        startActivity(LaunchIntent);
                        //Toast.makeText(MainActivity.this, "全新功能，敬请期待...", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                    }
                    break;
                }
                case R.id.tv_tianqi: {
                    try {
                        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.aibabel.weather");
                        startActivity(LaunchIntent);
                        //Toast.makeText(MainActivity.this, "全新功能，敬请期待...", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                    }
                    break;
                }
                case R.id.tv_shijiezhong: {
                    try {
                        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.aibabel.alliedclock");
                        startActivity(LaunchIntent);
                        //Toast.makeText(MainActivity.this, "全新功能，敬请期待...", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                    }
                    break;
                }
                case R.id.tv_sos: {
                    try {
                        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.aibabel.sos");
                        startActivity(LaunchIntent);
                        //Toast.makeText(MainActivity.this, "全新功能，敬请期待...", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                    }
                    break;
                }
                case R.id.tv_zuche: {
                    try {
                        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.googlequicksearchbox");
                        startActivity(LaunchIntent);
                        //Toast.makeText(MainActivity.this, "全新功能，敬请期待...", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                    }
                    break;
                }
                case R.id.tv_quan: {
                    try {
                        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.aibabel.currencyconversion");
                        startActivity(LaunchIntent);
                        //Toast.makeText(MainActivity.this, "全新功能，敬请期待...", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                    }
                    break;
                }
                case R.id.tv_xiazai: {
                    try {
                        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.aibabel.download.offline");
                        startActivity(LaunchIntent);
                        //Toast.makeText(MainActivity.this, "全新功能，敬请期待...", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                    }
                }
                default:
                    break;
            }
        }
        if (cmpName != null) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmpName);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {

            }
        }

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setbackGround(position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    private void setbackGround(int postion) {
        if (getCountryZipCode(this).equals("CN")) {
            for (int i = 0; i < 3; i++) {
                if (i == postion) {
                    dots.get(i).setBackground(getResources().getDrawable(R.drawable.dot_normal_current));
                } else {
                    dots.get(i).setBackground(getResources().getDrawable(R.drawable.dot_normal));
                }
            }
        } else {
            for (int i = 0; i < 2; i++) {
                if (i == postion) {
                    dots.get(i).setBackground(getResources().getDrawable(R.drawable.dot_normal_current));
                } else {
                    dots.get(i).setBackground(getResources().getDrawable(R.drawable.dot_normal));
                }
            }
        }

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

    public boolean isCurrentInTimeScope1(int beginHour, int beginMin, int endHour, int endMin) {
        boolean result = false;
        final long aDayInMillis = 1000 * 60 * 60 * 24;
        final long currentTimeMillis = System.currentTimeMillis();
        //Log.e("wzf","currentTimeMillis="+currentTimeMillis);
        Time now = new Time();
        now.set(currentTimeMillis);

        Time startTime = new Time();
        startTime.set(currentTimeMillis);
        startTime.hour = beginHour;
        startTime.minute = beginMin;

        Time endTime = new Time();
        endTime.set(currentTimeMillis);
        endTime.hour = endHour;
        endTime.minute = endMin;

        if (!startTime.before(endTime)) {
            // 跨天的特殊情况（比如22:00-8:00）
            startTime.set(startTime.toMillis(true) - aDayInMillis);
            result = !now.before(startTime) && !now.after(endTime); // startTime <= now <= endTime
            Time startTimeInThisDay = new Time();
            startTimeInThisDay.set(startTime.toMillis(true) + aDayInMillis);
            if (!now.before(startTimeInThisDay)) {
                result = true;
            }
        } else {
            // 普通情况(比如 8:00 - 14:00)
            result = !now.before(startTime) && !now.after(endTime); // startTime <= now <= endTime
        }
        //Log.e("wzf","result="+result);
        return result;
    }

    public static long qianyitian(String specifiedDay) {
        long qianyitiantime = 5201314;
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMddHHmmss").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);

        String dayBefore = new SimpleDateFormat("yyyyMMddHHmmss").format(c.getTime());
        //Log.e("wzf", "dayBefore=" + dayBefore);
        try {
            qianyitiantime = stringToLong(dayBefore, "yyyyMMddHHmmss");
            //Log.e("wzf", "qianyitiantime=" + qianyitiantime);
            return qianyitiantime;
        } catch (Exception e) {
        }
        return qianyitiantime;
    }

    /**
     * 判断当前系统时间是否在指定时间的范围内
     *
     * @param beginHour 开始小时，例如22
     * @param beginMin  开始小时的分钟数，例如30
     * @param endHour   结束小时，例如 8
     * @param endMin    结束小时的分钟数，例如0
     * @return true表示在范围内，否则false
     */
    public static boolean isCurrentInTimeScope(String specifiedDay, int beginHour, int beginMin, int endHour, int endMin) {
        boolean result = false;
        final long aDayInMillis = 1000 * 60 * 60 * 24;
        //final long currentTimeMillis = System.currentTimeMillis();
        final long currentTimeMillis = qianyitian(specifiedDay);


        Time now = new Time();
        now.set(currentTimeMillis);

        Time startTime = new Time();
        startTime.set(currentTimeMillis);
        startTime.hour = beginHour;
        startTime.minute = beginMin;

        Time endTime = new Time();
        endTime.set(currentTimeMillis);
        endTime.hour = endHour;
        endTime.minute = endMin;

        if (!startTime.before(endTime)) {
            // 跨天的特殊情况（比如22:00-8:00）
            startTime.set(startTime.toMillis(true) - aDayInMillis);
            result = !now.before(startTime) && !now.after(endTime); // startTime <= now <= endTime
            Time startTimeInThisDay = new Time();
            startTimeInThisDay.set(startTime.toMillis(true) + aDayInMillis);
            if (!now.before(startTimeInThisDay)) {
                result = true;
            }
        } else {
            // 普通情况(比如 8:00 - 14:00)
            result = !now.before(startTime) && !now.after(endTime); // startTime <= now <= endTime
        }
        //Log.e("wzf","result="+result);
        return result;
    }
    /*public long dateDifft(String startTime, String endTime, String format) {
        // 按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat(format);
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        long day = 0;
        try {
            // 获得两个时间的毫秒时间差异
            diff = sd.parse(endTime).getTime()
                    - sd.parse(startTime).getTime();
            day = diff / nd;// 计算差多少天
            long hour = diff % nd / nh;// 计算差多少小时
            long min = diff % nd % nh / nm;// 计算差多少分钟
            long sec = diff % nd % nh % nm / ns;// 计算差多少秒
            // 输出结果
            System.out.println("时间相差：" + day + "天" + hour + "小时" + min
                    + "分钟" + sec + "秒。");
            if (day>=1) {
                return day;
            }else {
                if (day==0) {
                    return 1;
                }else {
                    return 0;
                }

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;

    }*/

    /**
     * TW    //繁体中文
     */
    public static String getCountryZipCode(Context context) {
        String CountryZipCode = "";
        Locale locale = context.getResources().getConfiguration().locale;
        CountryZipCode = locale.getCountry();
        //Log.e("wzf","CountryZipCode="+CountryZipCode);
        return CountryZipCode;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mNetworkListener != null) {
            unregisterReceiver(mNetworkListener);
        }
    }

    /**
     * 网络改变监控广播
     * <p>
     * 监听网络的改变状态,只有在用户操作网络连接开关(wifi,mobile)的时候接受广播,
     * 然后对相应的界面进行相应的操作，并将 状态 保存在我们的APP里面
     */
    class NetworkChangeListener extends BroadcastReceiver {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                Log.e("LK---001","当前网络可用请求接口get_okgo");
                //获取国内or国外 服务器
                initService();
                get_okgo();

            } else {
                Log.e("LK---001","当前网络不可用");
            }
        }
    }

    /**
     * 判断时区 进行服务器筛选
     */
    private void initService() {
        String timerID = TimeZone.getDefault().getID();
        if (timerID.equals("Asia/Shanghai")){
            Constans.HOST = Constans.HOST_ZH;
        }else{
            Constans.HOST = Constans.HOST_EN;
        }

        Log.e("LK---001","时区："+timerID+"----选择服务器:"+Constans.HOST);

    }

}


