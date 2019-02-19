package com.aibabel.download.offline;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import com.aibabel.download.offline.app.MyApplication;
import com.aibabel.download.offline.base.BaseActivity;
import com.aibabel.download.offline.fragment.InstalledFragment;
import com.aibabel.download.offline.fragment.ListdFragment;
import com.aibabel.download.offline.fragment.PreloadFragment;
import com.aibabel.download.offline.fragment.PreloadInstalledFragment;
import com.aibabel.download.offline.util.L;
import com.aibabel.download.offline.util.LanUtil;
import com.aibabel.download.offline.util.ThreadPoolManager;

public class LocalResourceActivity extends BaseActivity {
    private ImageButton ib_return;
    private Fragment  preloadFragment,preloadInstalledFragment;
    private RadioGroup rg;


    private FragmentManager manager;
    private FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyApplication.dbHelper.shutdownUPdateDB();
        MyApplication.isFile=false;
        L.e("====================="+MyApplication.isFile);

    }

    @Override
    protected void assignView() {
        ib_return=findViewById(R.id.rl_top_return);
        rg=findViewById(R.id.rl_gp);
//        preloadFragment=new PreloadFragment();
//        preloadInstalledFragment=new PreloadInstalledFragment();
//        manager = getSupportFragmentManager();
//        transaction = manager.beginTransaction();
//        transaction.add(R.id.rl_framelayout, preloadFragment);
//        transaction.add(R.id.rl_framelayout, preloadInstalledFragment);
//        transaction.hide(preloadInstalledFragment).show(preloadFragment);
//        transaction.commit();



        manager = getSupportFragmentManager();

        if (manager.findFragmentByTag("preload") == null) {
            L.e("1111111111111111111");

            preloadFragment = new PreloadFragment();
            preloadInstalledFragment = new PreloadInstalledFragment();

            transaction = manager.beginTransaction();
            transaction.add(R.id.rl_framelayout, preloadInstalledFragment, "preloadInstalled");
            transaction.add(R.id.rl_framelayout, preloadFragment, "preload");
            transaction.hide(preloadInstalledFragment).show(preloadFragment);

            transaction.commit();

        } else {

            preloadFragment=manager.findFragmentByTag("preload");
            preloadInstalledFragment = manager.findFragmentByTag("preloadInstalled");

            if (preloadFragment.isAdded()) {
                manager.beginTransaction().show(preloadFragment).commit();
            } else {
                manager.beginTransaction().remove(preloadFragment).commit();
                preloadFragment=new ListdFragment();
                manager.beginTransaction().add(R.id.rl_framelayout,preloadFragment).commit();

            }

            if (preloadInstalledFragment.isAdded()) {
                manager.beginTransaction().hide(preloadInstalledFragment).commit();
            } else {
                manager.beginTransaction().remove(preloadInstalledFragment).commit();
                preloadInstalledFragment=new ListdFragment();
                manager.beginTransaction().add(R.id.rl_framelayout,preloadInstalledFragment).commit();

            }


        }
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onResume() {
        LanUtil.setLan(this);
        super.onResume();
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

                L.e("=========================" + i);
                if (i == R.id.rl_gp_zy) {
                    transaction.hide(preloadInstalledFragment).show(preloadFragment);
                } else if (i == R.id.rl_gp_install) {
                    transaction.hide(preloadFragment).show(preloadInstalledFragment);
                }

                transaction.commit();
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_local_resource;
    }

    @Override
    protected void initData() {

    }


    @Override
    protected void onDestroy() {

        L.e("LocalResourceActivity   onDestroy=================================");
        ThreadPoolManager.getInstance().shut();
        super.onDestroy();

        MyApplication.isFile=false;
    }
}
