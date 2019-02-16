package com.aibabel.map.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.baselibrary.utils.ToastUtil;
import com.aibabel.map.FYTApplication;
import com.aibabel.map.R;
import com.aibabel.map.base.MapBaseActivity;
import com.aibabel.map.empty.EmptyLayout;
import com.baidu.location.BDLocation;

import butterknife.BindView;

/**
 * Created by fytworks on 2019/1/8.
 * 测试类
 */

public class SearchMapActivity extends MapBaseActivity {

    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.empty)
    EmptyLayout empty;
    @BindView(R.id.rb1)
    CheckBox rb1;
    @BindView(R.id.rb2)
    CheckBox rb2;

    private int FLAG = 1;

    @Override
    public int getLayoutMap(Bundle var1) {
        return R.layout.activity_searchmap;
    }

    @Override
    public void initMap() {

        empty.setOnButtonClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //2019年1月9日 重新请求API
                //TODO 测试
                switch (FLAG) {
                    case 1:
                        FLAG = 2;
                        empty.setErrorType(EmptyLayout.EMPTY_NORMAL_DATAS, "后台没有返回数据");
                        break;
                    case 2:
                        FLAG = 3;
                        empty.setErrorType(EmptyLayout.EMPTY_ERROR_DATAS, "后台or数据报错");
                        break;
                    case 3:
                        FLAG = 1;
                        empty.setErrorType(EmptyLayout.EMPTY_NETWORK_DATAS, "没有网络的情况下");
                        break;
                }
            }
        });

        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rb1.isChecked()){
                    rb1.setChecked(true);
                }else{
                    rb1.setChecked(false);
                }
                rb2.setChecked(false);
            }
        });

        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rb2.isChecked()){
                    rb2.setChecked(true);
                }else{
                    rb2.setChecked(false);
                }
                rb1.setChecked(false);
            }
        });

    }

    @Override
    public void receiveLocation(BDLocation location) {
        String des = tv.getText().toString();
        tv.setText(des + "\n" + location.getAddrStr());
    }
}
