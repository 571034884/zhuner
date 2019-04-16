package com.aibabel.translate.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aibabel.translate.R;
import com.aibabel.translate.adapter.NewCountryLanguageAdapter;
import com.aibabel.translate.bean.LanguageBean;
import com.aibabel.translate.offline.ChangeOffline;
import com.aibabel.translate.utils.CommonUtils;
import com.aibabel.translate.utils.Constant;
import com.aibabel.translate.utils.LanguageUtils;
import com.aibabel.translate.utils.SharePrefUtil;
import com.aibabel.translate.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

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
    private boolean isNet = true;

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
        /**
         * 132为上，131为下
         */
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
        switch (v.getId()) {
            case R.id.iv_close:
                onBackPressed();
                break;
        }
    }


    public void onBackPressed() {
        //可能需要同异侧判断
        setResult(200);
        Constant.IS_NEED_SHOW = false;
        super.onBackPressed();
    }


    private void initData() {
        //读取语言选择的 json 文件
//        group = LanguageUtils.getLanList(this);
        if (lan_key == 132) {
            String code = SharePrefUtil.getString(this, Constant.CODE_DOWN, "");
            if (TextUtils.equals(code, "ch_yy") || TextUtils.equals(code, "ch_hk_j")) {
                group = LanguageUtils.getSpecailList(LanguageUtils.getLanList(this));
            } else if (LanguageUtils.getNotSupport().contains(code)) {
                group = LanguageUtils.getList(LanguageUtils.getLanList(this));
            } else {
                group = LanguageUtils.getLanList(this);
            }
        }

        if (lan_key == 131) {
            String code = SharePrefUtil.getString(this, Constant.CODE_UP, "");
            if (TextUtils.equals(code, "ch_yy") || TextUtils.equals(code, "ch_hk_j")) {
                group = LanguageUtils.getSpecailList(LanguageUtils.getLanList(this));
            } else if (LanguageUtils.getNotSupport().contains(code)) {
                group = LanguageUtils.getList(LanguageUtils.getLanList(this));
            } else {
                group = LanguageUtils.getLanList(this);
            }
        }
        isNet = CommonUtils.isAvailable();
        if (!isNet) {
            ChangeOffline.getInstance().getOfflineList();
            List<LanguageBean> list = new ArrayList<>();
            for (int i = 0; i < group.size(); i++) {
                if (ChangeOffline.getInstance().offlineListMap.containsKey(group.get(i).getLang_code())) {
                    group.get(i).setIsOffline(true);
                    list.add(group.get(i));
                } else {
                    list.add(group.get(i));
                }
            }
            group = list;
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
        if (group.get(groupPosition).getChild().get(childPosition).isNotSupport()) {
            ToastUtil.showShort(getString(R.string.not_support));
            return false;
        }
        LanguageBean.ChildBean newLanguageBean = group.get(groupPosition).getChild().get(childPosition);
        LanguageBean languageBean = group.get(groupPosition);
        newExlistAdapter.notifyDataSetChanged();

        SharePrefUtil.saveString(LanguageActivity.this, "var_name", newLanguageBean.getVar());
        SharePrefUtil.saveInt(LanguageActivity.this, "idi", languageBean.getId());
        lanText = newLanguageBean.getVar();
        name_str = newLanguageBean.getVar_code();

        String alert = group.get(groupPosition).getAlert();
        try {
            alert = group.get(groupPosition).getChild().get(childPosition).getAlert();
        } catch (Exception e) {

        }

        sound = group.get(groupPosition).getSound() + "";
        String offline = group.get(groupPosition).getOffline();
        saveLan(lan_key, lanText, name_str, alert, sound, offline);
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
            saveLan(lan_key, lanText, name_str, lan_alert, sound, offline);
//            L.e("==============================离线并支持的语言");
            return true;

        } else if (!isNet && !ChangeOffline.getInstance().offlineListMap.containsKey(group.get(groupPosition).getLang_code())) {
            ToastUtil.showShort(getString(R.string.buzhichi));
//            L.e("==============================离线不支持的语言");
            return true;
        } else if (isNet) {
//            L.e("==============================在线支持的语言");
            if (group.get(groupPosition).getChild().size() > 0) {//点击展开二级列表

                SharePrefUtil.saveString(LanguageActivity.this, "choice_name", name);
                if (choice == false) {
                    choice = true;
                    SharePrefUtil.saveBoolean(LanguageActivity.this, "choice", choice);
                } else {
                    choice = false;
                    SharePrefUtil.saveBoolean(LanguageActivity.this, "choice", choice);
                }
                newExlistAdapter.notifyDataSetChanged();

            } else {
                if (!group.get(groupPosition).isNotSupport()) {//是否支持粤语
                    SharePrefUtil.saveInt(LanguageActivity.this, "idi", group.get(groupPosition).getId());
                    LanguageBean newLanguageBean = group.get(groupPosition);
                    lanText = newLanguageBean.getName();
                    name_str = newLanguageBean.getLang_code();
                    sound = newLanguageBean.getSound() + "";
                    saveLan(lan_key, lanText, name_str, lan_alert, sound, offline);
                } else {
                    ToastUtil.showShort(getString(R.string.not_support));
                }

            }

            return false;
        }


        return false;
    }


    private void saveLan(int key, String name, String code, String lan_alert, String sound, String offline) {
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


    /**
     * @param list
     * @return
     */
    private List<LanguageBean> specialLan(List<LanguageBean> list) {

        for (int i = list.size() - 1; i >= 0; i--) {
            LanguageBean bean = list.get(i);
            if (TextUtils.equals(bean.getLang_code(), "kk-KZ")//哈萨克语
                    || TextUtils.equals(bean.getLang_code(), "ka-GE")//格鲁吉亚
                    || TextUtils.equals(bean.getLang_code(), "am-ET")//阿姆哈拉语
                    || TextUtils.equals(bean.getLang_code(), "az-AZ")//阿塞拜疆语
                    || TextUtils.equals(bean.getLang_code(), "ne-NP")//尼泊尔语
                    || TextUtils.equals(bean.getLang_code(), "lo-LA")//老挝语
                    || TextUtils.equals(bean.getLang_code(), "km-KH")//高棉语
                    || TextUtils.equals(bean.getLang_code(), "gl")//加利西亚语
                    || TextUtils.equals(bean.getLang_code(), "ja")//爪哇语
                    || TextUtils.equals(bean.getLang_code(), "zu")) {//祖鲁语

                list.remove(i);

            }
        }
        return list;
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
