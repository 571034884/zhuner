package com.aibabel.download.offline;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.aibabel.baselibrary.utils.DeviceUtils;
import com.aibabel.download.offline.app.MyApplication;
import com.aibabel.download.offline.base.BaseActivity;
import com.aibabel.download.offline.fragment.InstalledFragment;
import com.aibabel.download.offline.fragment.ListdFragment;
import com.aibabel.download.offline.util.CommonUtils;
import com.aibabel.download.offline.util.L;
import com.aibabel.download.offline.util.LanUtil;
import com.aibabel.download.offline.util.ThreadPoolManager;
import com.liulishuo.filedownloader.FileDownloader;

public class DownLoadListActivity extends BaseActivity {
    private ImageButton ib_return;
    private FrameLayout frameLayout;
    private TextView tv_title;
    private RadioGroup rg;
    public String key, name;

    private Fragment listFrag, installFrag;

    private FragmentManager manager;
    private FragmentTransaction transaction;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.e("DownLoadListActivity onCreate=======================");
        MyApplication.isFile=false;

         MyApplication.dbHelper.shutdownUPdateDB();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LanUtil.setLan(this);
        String countyName=CommonUtils.getLocal(MyApplication.mContext);

        if (DeviceUtils.getSystem()==DeviceUtils.System.FLY_TAIWAN){
            if (!countyName.equals("zh_TW")&&!countyName.equals("zh_CN")) {

                if (!key.equals("yyfy")) {

                    finish();
                }

            }
        }else{
            if (!countyName.equals("zh_CN")) {

                if (!key.equals("yyfy")) {

                    finish();
                }

            }
        }


        L.e("list===================="+manager.findFragmentByTag("list")+"========install================="+manager.findFragmentByTag("install"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //停止所有下载
        MyApplication.isFile=false;
        FileDownloader.getImpl().pauseAll();
        ThreadPoolManager.getInstance().shut();

        L.e("DownLoadListActivity onDestroy=======================");

    }

    @Override
    protected void assignView() {
        ib_return = findViewById(R.id.dl_top_return);
        tv_title = findViewById(R.id.dl_top_title);
        frameLayout = findViewById(R.id.dl_framelayout);
        rg = findViewById(R.id.dl_gp);


        key = getIntent().getStringExtra("key");
        name = getIntent().getStringExtra("name");




        manager = getSupportFragmentManager();

        if (manager.findFragmentByTag("list") == null) {
            L.e("1111111111111111111");
            Bundle bundle = new Bundle();
            bundle.putString("key", key);
            bundle.putString("name", name);
            listFrag = new ListdFragment();
            installFrag = new InstalledFragment();
            listFrag.setArguments(bundle);
            installFrag.setArguments(bundle);
            transaction = manager.beginTransaction();
            transaction.add(R.id.dl_framelayout, installFrag, "install");
            transaction.add(R.id.dl_framelayout, listFrag, "list");
            transaction.hide(installFrag).show(listFrag);

            transaction.commit();

        } else {

            listFrag=manager.findFragmentByTag("list");
            installFrag = manager.findFragmentByTag("install");

            if (listFrag.isAdded()) {
                manager.beginTransaction().show(listFrag).commit();
            } else {
                manager.beginTransaction().remove(listFrag).commit();
                listFrag=new ListdFragment();
                manager.beginTransaction().add(R.id.dl_framelayout,listFrag).commit();

            }

            if (installFrag.isAdded()) {
                manager.beginTransaction().hide(installFrag).commit();
            } else {
                manager.beginTransaction().remove(installFrag).commit();
                installFrag=new ListdFragment();
                manager.beginTransaction().add(R.id.dl_framelayout,installFrag).commit();

            }

            Bundle bundle = new Bundle();
            bundle.putString("key", key);
            bundle.putString("name", name);


        }
    }

    @Override
    protected void initView() {


    }



    @Override
    protected void initListener() {
        ib_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                transaction = manager.beginTransaction();

                if (i == R.id.dl_gp_zy) {
                    transaction.hide(installFrag).show(listFrag);
                } else if (i == R.id.dl_gp_install) {
                    transaction.hide(listFrag).show(installFrag);
                }

                transaction.commit();
            }
        });


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_download_list;
    }

    @Override
    protected void initData() {

        switch (key) {
            case "yyfy":
                tv_title.setText(MyApplication.mContext.getString(R.string.yuyinfanyi));
                break;
            case "jqdl":
                tv_title.setText(MyApplication.mContext.getString(R.string.jingqudaolan));
                break;
            case "mdd":
                tv_title.setText(MyApplication.mContext.getString(R.string.mudidi));
                break;
        }



    }


}
