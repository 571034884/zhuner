package com.dommy.qrcode;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.dommy.qrcode.util.Constant;
import com.dommy.qrcode.util.DateUtils;
import com.dommy.qrcode.util.DevUtils;
import com.dommy.qrcode.util.Getsystem_info;
import com.dommy.qrcode.util.IsInternet;
import com.dommy.qrcode.util.SystemPropertiesProxy;
import com.dommy.qrcode.util.ZXingUtils;
import com.google.zxing.activity.CaptureActivity;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;
import static com.dommy.qrcode.util.DateUtils.strToDateFormat;

public class MainActivity extends Activity implements View.OnClickListener{
    Button btnQrCode,btnShengji,btnchongzhi,btnchongzhi1,daoqi; // 扫码
    TextView tvResult; // 结果
    private Button Bt8;
    private SQLiteDatabase db;
    private TextView tv_sn,tv_ww;
    public String sn="987654321000009";
    //private EditText tv_qrCode_content;//用来生成二维码图片内包含的内容
    final static int COUNTS = 5;// 点击次数
    final static long DURATION = 1000;// 规定有效时间
    long[] mHits = new long[COUNTS];
    private TextView tv_click;//按钮

    private ImageView iv_qr_code;//显示二维码的ImageView
    public String ttime,ttime1,date,ddtime,fftime,dev_sn;
    boolean isFirst=true;
    private String s;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置去除ActionBar
        Log.e("QRCODEdEMO ACTIVITY onCreate","====================");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        tv_sn = findViewById(R.id.snhao);
        /*try {
            Intent intent = getIntent();
            sn= intent.getStringExtra("sn_name");
            tv_sn.setText(sn);
        }catch (ActivityNotFoundException e){

        }catch (SecurityException e){}*/
        //tv_sn.setText(DevUtils.getSN(this));

        //Log.e("===============SN",Getsystem_info.getSN_SN());

        tv_sn.setText(Getsystem_info.getSN_SN());
        initView();
        btnchongzhi.setVisibility(View.VISIBLE);
        try{
            Intent intent=getIntent();
            s=intent.getStringExtra("erweima");
            Log.e("wff","s="+s);
            if (s!=null && s.equals("daoqi")){
                btnShengji.setVisibility(View.GONE);
                btnQrCode.setVisibility(View.GONE);
                btnchongzhi.setVisibility(View.GONE);
                btnchongzhi1.setVisibility(View.VISIBLE);
            }else {
            }
        }catch (Exception e){}


    }

    public static String getlanguage(Context context)
    {

        String language = "";
        Locale locale = context.getResources().getConfiguration().locale;
        language=Locale.getDefault().getLanguage();
        Log.e("wzf","language="+language);

        return language;
    }


    /* Handler mHandler = new Handler();
    Runnable r = new Runnable() {

        @Override
        public void run() {
            //do something
            //每隔1s循环执行run方法
            weiResult();
            mHandler.postDelayed(this, 10000);
        }
    };*/
    private void initView() {
        btnQrCode = (Button) findViewById(R.id.btn_qrcode);
        btnShengji= findViewById(R.id.btn_shengji);
        btnchongzhi= findViewById(R.id.btn_chongzhi);
        btnchongzhi1= findViewById(R.id.btn_chongzhi1);
        tv_ww=findViewById(R.id.t);
        tv_ww.setOnClickListener(this);
        btnQrCode.setOnClickListener(this);
        btnchongzhi1.setOnClickListener(this);
        btnShengji.setOnClickListener(this);
        btnchongzhi.setOnClickListener(this);
        iv_qr_code= (ImageView) findViewById(R.id.iv_qr_code);

    }

    // 开始扫码
    private void startQrCode() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // 申请权限
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, Constant.REQ_PERM_CAMERA);
            return;
        }
        // 二维码扫码
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
        startActivityForResult(intent, Constant.REQ_QR_CODE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_qrcode:
                startQrCode();
                break;
           /* case R.id.ooo:
                //IsToday();
                break;*/
            case R.id.btn_chongzhi:
                Intent intent = new Intent();
                intent.setAction("com.xiazdong");
                intent.putExtra("Zhuner_Reset", "Device_Reset");
                MainActivity.this.sendBroadcast(intent);
                //Toast.makeText(getApplicationContext(), "发送广播成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_shengji:
                if (IsInternet.isNetworkAvalible(this)) {
                    try {
                        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.redstone.ota.ui");
                        startActivity(LaunchIntent);
                        //Toast.makeText(MainActivity.this, "全新功能，敬请期待...", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        }
                }else {
                    try {
                        ComponentName component = new ComponentName("com.zhuner.administrator.settings", "com.zhuner.administrator.settings.wtwd.WifiSettingNokeyActivity");
                        Intent intent1 = new Intent();
                        intent1.setComponent(component);
                        //intent1.putExtra("wifi_zhuner", "wifi_settings");
                        startActivity(intent1);
                        //Toast.makeText(MainActivity.this, "没有网络,请连接网络", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        //TODO 可以在这里提示用户没有安装应用或找不到指定Activity，或者是做其他的操作
                    }
                }
                break;
            case R.id.btn_chongzhi1:
                Intent intent1 = new Intent();
                intent1.setAction("com.xiazdong");
                intent1.putExtra("Zhuner_Reset", "Device_Reset");
                MainActivity.this.sendBroadcast(intent1);
                //Toast.makeText(getApplicationContext(), "发送广播成功", Toast.LENGTH_SHORT).show();
                break;

        }

    }



    public  void IsToday() {
        //String date ="20180620";
        Log.e("wzf>>>>>>>>>>>>", "s=" + s);
        SharedPreferences shared = getSharedPreferences("is", MODE_PRIVATE);
        boolean isfer = shared.getBoolean("isfer", true);
        SharedPreferences.Editor editor = shared.edit();
            if (s != null && s.equals("daoqi")) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
                date = sdf.format(new java.util.Date().getTime());
                Log.e("wzf>>>>>>>>>>>>", "date=" + date);
                try {
                    if (DateUtils.isDateOneBigger(date, ttime1, ttime)) {
                        setResult(101);
                        finish();
                        android.os.Process.killProcess(android.os.Process.myPid());

                        //Toast.makeText(this, "可以用", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, R.string.my_guihai, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {

                    Log.e(">>>>>>>>>>>>>>>>>", e.getMessage());
                }

            } else if (dev_sn.equals("zhuner")){
                Intent intent2 = new Intent();
                String nian = ddtime.substring(0, 4);
                String yue = ddtime.substring(4, 6);
                String ri = ddtime.substring(6, 8);
                String shi = ddtime.substring(8, 10);
                String fen = ddtime.substring(10, 12);
                intent2.putExtra("time_nian", nian);
                intent2.putExtra("time_yue", yue);
                intent2.putExtra("time_ri", ri);
                intent2.putExtra("time_shi", shi);
                intent2.putExtra("time_fen", fen);
                intent2.putExtra("result", 101);
                        /*Log.e("wzf","nian="+nian);
                        Log.e("wzf","yue="+yue);
                        Log.e("wzf","ri="+ri);
                        Log.e("wzf","shi="+shi);
                        Log.e("wzf","fen="+fen);*/
                setResult(101, intent2);
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());

            }
            else{
                try {
                    if (Getsystem_info.getSN_SN().equals(dev_sn)) {
                        if (DateUtils.isDateOneBigger(ddtime, fftime, ttime) && DateUtils.isDate2Bigger(fftime, ddtime)) {

                            Intent intent2 = new Intent();
                            String nian = ddtime.substring(0, 4);
                            String yue = ddtime.substring(4, 6);
                            String ri = ddtime.substring(6, 8);
                            String shi = ddtime.substring(8, 10);
                            String fen = ddtime.substring(10, 12);
                            intent2.putExtra("time_nian", nian);
                            intent2.putExtra("time_yue", yue);
                            intent2.putExtra("time_ri", ri);
                            intent2.putExtra("time_shi", shi);
                            intent2.putExtra("time_fen", fen);
                            intent2.putExtra("result", 101);
                        /*Log.e("wzf","nian="+nian);
                        Log.e("wzf","yue="+yue);
                        Log.e("wzf","ri="+ri);
                        Log.e("wzf","shi="+shi);
                        Log.e("wzf","fen="+fen);*/
                            setResult(101, intent2);
                            finish();
                            android.os.Process.killProcess(android.os.Process.myPid());

                            //Toast.makeText(this, "可以用", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, R.string.my_weizaizulinqi, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, R.string.my_snbuzhengque, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {

                    Log.e(">>>>>>>>>>>>>>>>>", e.getMessage());
                }

            }
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //扫描结果回调
        if (requestCode == Constant.REQ_QR_CODE && resultCode == 1000) {
            try{
                Bundle bundle = data.getExtras();
                ttime= bundle.getString("data");
                ttime1= bundle.getString("data1");
                ddtime= bundle.getString("dd_time");
                fftime= bundle.getString("fft");
                dev_sn= bundle.getString("sn");
                Log.e(">>>>>>>>>>>>","dev_sn="+dev_sn);
                IsToday();
                //Toast.makeText(MainActivity.this, "扫描成功,请点击跳过继续", Toast.LENGTH_LONG).show();
                //Log.e(">>>>>>>>>>>>","ttime="+ttime);
                //Log.e(">>>>>>>>>>>>","ttime1="+ttime1);
                //Log.e(">>>>>>>>>>>>","ddtime="+ddtime);
                //Log.e(">>>>>>>>>>>>","fftime="+fftime);
                /*Intent intent1 = new Intent();
                intent1.putExtra("f", ttime1);
                intent1.putExtra("t", ttime);
                startActivity(intent1);*/
            }catch (Exception e){
                Toast.makeText(MainActivity.this, R.string.my_chongxinsaoma, Toast.LENGTH_LONG).show();
            }

        }
    }

@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constant.REQ_PERM_CAMERA:
                // 摄像头权限申请
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获得授权
                    startQrCode();
                } else {
                    // 被禁止授权
                    Toast.makeText(MainActivity.this, "请至权限中心打开本应用的相机访问权限", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    protected void weiResult() {

        String content = Getsystem_info.getSN_SN();
        if (content != null && !content.equals("")) {
            //Log.e("wzf","content="+content);
            //Toast.makeText(MainActivity.this, "到这里", Toast.LENGTH_LONG).show();
            Bitmap bitmap = ZXingUtils.createQRImage(content, iv_qr_code.getWidth(), iv_qr_code.getHeight());
            //Toast.makeText(MainActivity.this, "bitmap＝"+bitmap, Toast.LENGTH_LONG).show();
            iv_qr_code.setImageBitmap(bitmap);
        }else {
            Toast.makeText(MainActivity.this, R.string.my_erweishibei, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("onDestroy","============================");
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (isFirst) {
            isFirst=false;
           weiResult();
        }

        super.onWindowFocusChanged(hasFocus);
    }
}
