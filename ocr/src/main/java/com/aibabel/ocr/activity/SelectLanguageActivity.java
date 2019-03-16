package com.aibabel.ocr.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.ocr.R;
import com.aibabel.ocr.adapter.Adapter_Language;
import com.aibabel.ocr.bean.LanBean;
import com.aibabel.ocr.utils.Constant;
import com.aibabel.ocr.utils.LanguageUtils;
import com.aibabel.ocr.utils.SharePrefUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectLanguageActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private ImageView iv_close;
    private ListView lv_language;
    private Adapter_Language adapter;
    private List<LanBean> list = null;
    private int lan_type = 0; //1、源语言，2、目标语言
    private String current_lan_code;//当前选中语言的code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language);
        init();
        initData();
    }

    private void init() {
        iv_close = findViewById(R.id.iv_close);
        lv_language = findViewById(R.id.lv_language);
        lv_language.setOnItemClickListener(this);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initData() {
        lan_type = getIntent().getIntExtra("type", 0);
        if (lan_type == 1) {
            current_lan_code = SharePrefUtil.getString(this, Constant.LAN_OR_CODE, "en");
        } else if (lan_type == 2) {
            current_lan_code = SharePrefUtil.getString(this, Constant.LAN_TR_CODE, "ch_ch");
        }
        list = LanguageUtils.getLanList(this);
        for (LanBean bean : list) {
            String code = bean.getLang_code();
            if (TextUtils.equals(code, current_lan_code)) {
                bean.setChoice(1);
            }
        }
        adapter = new Adapter_Language(this, list);
        lv_language.setAdapter(adapter);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LanBean bean = list.get(position);
        String name = bean.getName();
        String name_code = bean.getLang_code();
        Map<String, String> map = new HashMap<>();
        map.put("p2", Constant.LAN_TR);
        map.put("p1", bean.getName());
        StatisticsManager.getInstance(SelectLanguageActivity.this).addEventAidl(1411, map);
        saveLan(name, name_code);

    }


    /**
     * @param name 语言
     * @param code 对应的code
     */
    private void saveLan(String name, String code) {
        switch (lan_type) {// 类型： 1、源语言， 2、 目标语言
            case 1:
                SharePrefUtil.saveString(this, Constant.LAN_OR, name);
                SharePrefUtil.saveString(this, Constant.LAN_OR_CODE, code);
                setResult(200);
                break;
            case 2:
                SharePrefUtil.saveString(this, Constant.LAN_TR, name);
                SharePrefUtil.saveString(this, Constant.LAN_TR_CODE, code);
                break;
            default:
                break;
        }
        this.finish();

    }
}
