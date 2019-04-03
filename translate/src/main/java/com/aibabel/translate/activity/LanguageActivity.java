package com.aibabel.translate.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.translate.R;
import com.aibabel.translate.adapter.NewCountryLanguageAdapter;
import com.aibabel.translate.bean.LanguageBean;
import com.aibabel.translate.offline.ChangeOffline;
import com.aibabel.translate.utils.CommonUtils;
import com.aibabel.translate.utils.Constant;
import com.aibabel.translate.utils.L;
import com.aibabel.translate.utils.LanguageUtils;
import com.aibabel.translate.utils.SharePrefUtil;
import com.aibabel.translate.utils.ToastUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LanguageActivity extends BaseActivity implements ExpandableListView.OnChildClickListener, ExpandableListView.OnGroupClickListener {

    public static String TAG = LanguageActivity.class.getSimpleName();
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.el_lan)
    ExpandableListView elLan;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;


    private int lan_key = 0; //132、对方的语言，131、我的语言
    private String lan_name;
    private boolean choice = false;
    private List<LanguageBean> group;
    private NewCountryLanguageAdapter newExlistAdapter;
    private boolean flipp;
    private String sound;

    //进来的那一次网络状态  就是这次列表的最终状态
    private boolean isNet=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_language);
        ButterKnife.bind(this);
        initView();
        initIntent();
        initData();
    }

    private void initIntent() {
        Intent intent = getIntent();
        lan_key = intent.getIntExtra("lan_key", 0);
        lan_name = intent.getStringExtra("lan_name");
        flipp = intent.getBooleanExtra("flipp", false);

        if (flipp == true) {
            llRoot.setRotation(180);
            flipp = false;

        } else if (flipp == false) {
            llRoot.setRotation(0);
        }
    }






    private void initView() {
      ivClose.setOnClickListener(this);

      elLan.setOnChildClickListener(this);

      elLan.setOnGroupClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close:
                addStatisticsEvent("translation_language1",null);
                onBackPressed();
                break;
        }
    }



    public void onBackPressed() {
        /////////////////可能需要同异侧判断
        setResult(200);
        Constant.IS_NEED_SHOW = false;
        super.onBackPressed();
    }



    private void initData() {
        //获取当前手机的语言
        String country = Locale.getDefault().getCountry();
        String language = getResources().getConfiguration().locale.getLanguage();
        //读取语言选择的 json 文件
        group = LanguageUtils.getLanList(this);


          isNet=CommonUtils.isAvailable();
        if (!isNet) {

             ChangeOffline.getInstance().getOfflineList();

            List<LanguageBean> list=new ArrayList<>();
            for (int i = 0; i < group.size(); i++) {
                if (ChangeOffline.getInstance().offlineListMap.containsKey(group.get(i).getLang_code())) {
                    group.get(i).setIsOffline(true);
                    list.add(group.get(i));
                } else {
                    list.add(group.get(i));
                }
            }
            group=list;
        }
        newExlistAdapter = new NewCountryLanguageAdapter(group, LanguageActivity.this, isNet);
        elLan.setAdapter(newExlistAdapter);
        newExlistAdapter.notifyDataSetChanged();


//        int idi = SharePrefUtil.getInt(LanguageActivity.this, "idi", 0);
        for (int i = 0; i < group.size(); i++) {
            String name = group.get(i).getName();
            if (TextUtils.equals(name, lan_name)) {
                SharePrefUtil.saveInt(LanguageActivity.this, "idi", group.get(i).getId());
                SharePrefUtil.saveString(LanguageActivity.this, "var_name", "");
            }
            for (int j = 0; j < group.get(i).getChild().size(); j++) {
                if (TextUtils.equals(group.get(i).getChild().get(j).getVar(), lan_name)) {
                    SharePrefUtil.saveInt(LanguageActivity.this, "idi", group.get(i).getId());
                    SharePrefUtil.saveString(LanguageActivity.this, "var_name", group.get(i).getChild().get(j).getVar());
                }
            }
        }
        newExlistAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        String name_str;
        String lanText;
        LanguageBean.ChildBean newLanguageBean = group.get(groupPosition).getChild().get(childPosition);
        LanguageBean languageBean = group.get(groupPosition);
//                group.get(i).getChild().get(i1).setVar_choice(1);
        newExlistAdapter.notifyDataSetChanged();

        SharePrefUtil.saveString(LanguageActivity.this, "var_name", newLanguageBean.getVar());
        SharePrefUtil.saveInt(LanguageActivity.this, "idi", languageBean.getId());
        lanText = newLanguageBean.getVar();
        name_str = newLanguageBean.getVar_code();

        String alert = group.get(groupPosition).getAlert();
        try {
            alert=group.get(groupPosition).getChild().get(childPosition).getAlert();
        } catch (Exception e) {

        }

        sound = group.get(groupPosition).getSound() + "";
        String offline = group.get(groupPosition).getOffline();
        saveLan(lan_key, lanText, name_str, alert, sound,offline);

        return false;
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        String lanText;
        String name_str;
        String lan_alert = group.get(groupPosition).getAlert();

        String offline = group.get(groupPosition).getOffline();
//      SharePrefUtil.saveInt(LanguageActivity.this, "idi", group.get(groupPosition).getId());
        String name = group.get(groupPosition).getName();

        if (!isNet && ChangeOffline.getInstance().offlineListMap.containsKey(group.get(groupPosition).getLang_code())) {

                SharePrefUtil.saveInt(LanguageActivity.this, "idi", group.get(groupPosition).getId());
                LanguageBean newLanguageBean = group.get(groupPosition);
                lanText = newLanguageBean.getName();
                name_str = newLanguageBean.getLang_code();
                sound = newLanguageBean.getSound() + "";
                saveLan(lan_key, lanText, name_str, lan_alert, sound,offline);
//            L.e("==============================离线并支持的语言");
            return true;

        } else if (!isNet && !ChangeOffline.getInstance().offlineListMap.containsKey(group.get(groupPosition).getLang_code())) {
            ToastUtil.showShort(getString(R.string.buzhichi));
//            L.e("==============================离线不支持的语言");
            return true;
        } else if (isNet) {
//            L.e("==============================在线支持的语言");
            if (group.get(groupPosition).getChild().size() > 0) {
                SharePrefUtil.saveString(LanguageActivity.this, "choice_name", name);
                if (choice == false) {
                    choice = true;
                    SharePrefUtil.saveBoolean(LanguageActivity.this, "choice", choice);
                    //统计展开事件
                    HashMap<String,Serializable> map = new HashMap<>();
                    map.put("open_code",group.get(groupPosition).getLang_code());
                    addStatisticsEvent("translation_language3",map);
                } else {
                    choice = false;
                    SharePrefUtil.saveBoolean(LanguageActivity.this, "choice", choice);
                }
                newExlistAdapter.notifyDataSetChanged();
            } else {
                SharePrefUtil.saveInt(LanguageActivity.this, "idi", group.get(groupPosition).getId());
                LanguageBean newLanguageBean = group.get(groupPosition);
                lanText = newLanguageBean.getName();
                name_str = newLanguageBean.getLang_code();
                sound = newLanguageBean.getSound() + "";
                saveLan(lan_key, lanText, name_str, lan_alert, sound,offline);
            }

            return false;
        }


        return false;
    }


    private void saveLan(int key, String name, String code, String lan_alert, String sound,String offline) {
        //统计语种点击
        HashMap<String,Serializable> map = new HashMap<>();
        map.put("selected_code",code);
        addStatisticsEvent("translation_language2",null);
        switch (key) {
            case 132:
                SharePrefUtil.saveString(this, Constant.LAN_UP, name);
                SharePrefUtil.saveString(this, Constant.CODE_UP, code);
                SharePrefUtil.saveString(LanguageActivity.this, Constant.ALERT_UP, lan_alert);
                SharePrefUtil.saveString(LanguageActivity.this, Constant.SOUND_UP, sound);
                SharePrefUtil.saveString(LanguageActivity.this, Constant.OFFLINE_UP, offline);
                onBackPressed();
                break;
            case 131:
                SharePrefUtil.saveString(this, Constant.LAN_DOWN, name);
                SharePrefUtil.saveString(this, Constant.CODE_DOWN, code);
                SharePrefUtil.saveString(LanguageActivity.this, Constant.ALERT_DOWN, lan_alert);
                SharePrefUtil.saveString(LanguageActivity.this, Constant.SOUND_DOWN, sound);
                SharePrefUtil.saveString(LanguageActivity.this, Constant.OFFLINE_DOWN, offline);
                onBackPressed();
                break;
            default:
                break;

        }
    }





    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

}
