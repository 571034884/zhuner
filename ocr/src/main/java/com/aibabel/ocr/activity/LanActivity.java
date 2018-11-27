package com.aibabel.ocr.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.aibabel.ocr.R;
import com.aibabel.ocr.adapter.LanAdapter;
import com.aibabel.ocr.bean.LanBean;
import com.aibabel.ocr.utils.Constant;
import com.aibabel.ocr.utils.LanguageUtils;
import com.aibabel.ocr.utils.SharePrefUtil;
import com.aibabel.ocr.utils.StringUtils;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LanActivity extends AppCompatActivity implements ExpandableListView.OnChildClickListener, ExpandableListView.OnGroupClickListener {

    @BindView(R.id.elv_lv)
    ExpandableListView elvLv;
    @BindView(R.id.iv_close)
    ImageView iv_close;
    private ArrayList<LanBean> lists;
    private String current_lan;
    private String current_code;
    private int lan_type = 0; //1、对方的语言，2、我的语言
    private boolean choice = false;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_new_language);
        ButterKnife.bind(this);
        init();

    }

    private void init() {
        elvLv = findViewById(R.id.elv_lv);
        iv_close = findViewById(R.id.iv_close);
        elvLv.setOnChildClickListener(this);
        elvLv.setOnGroupClickListener(this);
        initDate();
        initAdapter();
    }

    /**
     * @param lan_type 类型： 1、源语言， 2、 目标语言
     * @param lanText  语言
     * @param name_str 对应的code
     */
    private void saveLan(int lan_type, String lanText, String name_str) {
        switch (lan_type) {
            case 1:
                SharePrefUtil.saveString(this, Constant.LAN_OR, lanText);
                SharePrefUtil.saveString(this, Constant.LAN_OR_CODE, name_str);
                break;
            case 2:
                SharePrefUtil.saveString(this, Constant.LAN_TR, lanText);
                SharePrefUtil.saveString(this, Constant.LAN_TR_CODE, name_str);
                break;
            default:
                break;
        }
        this.finish();
    }

    /**
     * 初始化adapter
     */
    private void initAdapter() {
        LanAdapter lanAdapter = new LanAdapter(lists, LanActivity.this);
        elvLv.setAdapter(lanAdapter);
    }

    /**
     * 初始化数据
     */
    private void initDate() {
        lan_type = getIntent().getIntExtra("type", 0);
        //判定那个语言被选中
        if (lan_type == 1) {
            current_code = SharePrefUtil.getString(this, Constant.LAN_OR_CODE, "en");
        } else if (lan_type == 2) {
            current_code = SharePrefUtil.getString(this, Constant.LAN_TR_CODE, "en");
        }
        lists = (ArrayList) LanguageUtils.getLanList(this);
        for (LanBean bean : lists) {//选中处理
            code = bean.getLang_code();
            if (TextUtils.equals(code, current_code)) {//父布局选中
                bean.setChoice(1);
            }
//            if (StringUtils.hasChild(bean)) {
//                for (LanBean.ChildBean child : bean.getChild()) {//子布局中选中
//                    code = child.getVar_code();
//                    if (TextUtils.equals(code, current_code)) {
//                        child.setChoice(1);
//                    }
//                }
//            }
        }

    }


    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//        LanBean lanBean = lists.get(groupPosition);
//        String name = lanBean.getChild().get(childPosition).getVar();
//        String code = lanBean.getChild().get(childPosition).getVar_code();
//        saveLan(lan_type, name, code);

        return false;
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View view, int groupPosition, long id) {
        LanBean bean = lists.get(groupPosition);
        if (!StringUtils.hasChild(bean)) {//判断是否有二级语言列表
            String lanText = bean.getName();
            String name_str = bean.getName_local();
            saveLan(lan_type, lanText, name_str);
        }
        return false;
    }


    @OnClick(R.id.iv_close)
    public void onViewClicked() {
        onBackPressed();
    }
}
