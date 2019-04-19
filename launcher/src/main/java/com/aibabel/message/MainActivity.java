package com.aibabel.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.baselibrary.sphelper.SPHelper;
import com.aibabel.baselibrary.utils.CommonUtils;
import com.aibabel.launcher.R;
import com.aibabel.message.fragment.Fragment_Chat;
import com.aibabel.message.fragment.Fragment_Convertions;
import com.aibabel.message.fragment.Fragment_Message;
import com.aibabel.message.fragment.Fragment_Task;
import com.aibabel.message.receiver.MessageListener;
import com.aibabel.message.service.MessageService;
import com.hyphenate.chat.EMClient;


import butterknife.BindView;

public class MainActivity extends BaseActivity implements MessageListener {


    @BindView(R.id.fl_content)
    FrameLayout flContent;

    Fragment_Chat fragmentChat;
    Fragment_Task fragmentTask;
    Fragment_Convertions fragmentConvertions;
    Fragment_Message fragmentMessage;
    @BindView(R.id.btn_msg)
    Button btnMsg;
    @BindView(R.id.tv_unread_msg_number)
    TextView tvUnreadMsgNumber;
    @BindView(R.id.btn_container_msg)
    RelativeLayout btnContainerMsg;
    @BindView(R.id.btn_chat)
    Button btnChat;
    @BindView(R.id.tv_unread_number)
    TextView tvUnreadNumber;
    @BindView(R.id.btn_container_chat)
    RelativeLayout btnContainerChat;
    @BindView(R.id.btn_task)
    Button btnTask;
    @BindView(R.id.tv_new)
    TextView tvNew;
    @BindView(R.id.btn_container_task)
    RelativeLayout btnContainerTask;
    @BindView(R.id.main_top)
    LinearLayout mainTop;
    private View[] mViews;
    private Button[] mTabs;
    private Fragment[] fragments;
    private int index;
    private int currentTabIndex;
    private String toChatUsername;
    private int fragment;

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_mm);
//    }

    @Override
    public int getLayout(Bundle savedInstanceState) {
        return R.layout.activity_main_mm;
    }

    @Override
    public void init() {
        //get user id or group id
//        toChatUsername = getIntent().getExtras().getString("userId");
        tvUnreadNumber = findViewById(R.id.tv_unread_number);
        fragment = getIntent().getExtras().getInt("fragment", 0);
        mTabs = new Button[3];
        mTabs[0] = findViewById(R.id.btn_msg);
        mTabs[1] = findViewById(R.id.btn_chat);
        mTabs[2] = findViewById(R.id.btn_task);
        mViews = new View[3];
        mViews[0] = findViewById(R.id.v_msg);
        mViews[1] = findViewById(R.id.v_chat);
        mViews[2] = findViewById(R.id.v_task);
        // select tab
        mTabs[fragment].setSelected(true);
        mViews[fragment].setVisibility(View.VISIBLE);

        fragmentChat = new Fragment_Chat();
        fragmentConvertions = new Fragment_Convertions();
        fragmentMessage = new Fragment_Message();
        fragmentTask = new Fragment_Task();
        fragments = new Fragment[]{fragmentMessage, fragmentConvertions, fragmentTask};

        //判定是否支持，以便于显示不同的布局
        selectUI();

        if (fragment == 0) {
            getSupportFragmentManager().beginTransaction()
                    .show(fragmentMessage)
                    .hide(fragmentConvertions)
                    .hide(fragmentTask)
                    .commit();
        } else if (fragment == 1) {
            getSupportFragmentManager().beginTransaction()
                    .show(fragmentConvertions)
                    .hide(fragmentMessage)
                    .hide(fragmentTask)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .hide(fragmentMessage)
                    .hide(fragmentConvertions)
                    .show(fragmentTask)
                    .commit();
        }
    }


    private void selectUI() {
        if (isSupported()) {
            btnContainerChat.setVisibility(View.VISIBLE);
        } else {
            btnContainerChat.setVisibility(View.GONE);
        }
    }


    private boolean isSupported() {
        boolean isSupported = false;
        String country = SPHelper.getString("country", "cn");
        if (TextUtils.equals(country, "jpa") || TextUtils.equals(country, "th")) {
            isSupported = true;
        }
        return isSupported;
    }


    /**
     * tab点击切换
     *
     * @param view
     */
    public void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_msg:
                index = 0;
                break;
            case R.id.btn_chat:
                index = 1;
                makeReaded();
                break;
            case R.id.btn_task:
                index = 2;
                break;
        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fl_content, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        mTabs[currentTabIndex].setSelected(false);
        // set current tab selected
        mTabs[index].setSelected(true);
        mViews[currentTabIndex].setVisibility(View.INVISIBLE);
        mViews[index].setVisibility(View.VISIBLE);
        currentTabIndex = index;

    }

    /**
     * 获取未读消息数
     */
    private void setUnreadNum() {

        if (isSupported()) {
            if (!CommonUtils.isNetworkAvailable(this)) {
                tvUnreadNumber.setVisibility(View.GONE);
                return;
            }
            if (EMClient.getInstance().isConnected() && currentTabIndex != 1) {
                int count = EMClient.getInstance().chatManager().getUnreadMessageCount();
                tvUnreadNumber.setVisibility(View.VISIBLE);
                if (count > 0) {
                    tvUnreadNumber.setText(count);
                }
            } else {
                tvUnreadNumber.setVisibility(View.GONE);
            }
        }
    }

    private void makeReaded() {
        tvUnreadNumber.setText("");
        tvUnreadNumber.setVisibility(View.GONE);
    }


    @Override
    public void getMessage(Intent intent) {
        if (EMClient.getInstance().isLoggedInBefore() && currentTabIndex != 1) {
            int count = EMClient.getInstance().chatManager().getUnreadMessageCount();
            tvUnreadNumber.setVisibility(View.VISIBLE);
            if (count > 0) {
                tvUnreadNumber.setText(count);
            }
        } else {
            tvUnreadNumber.setVisibility(View.GONE);
        }


    }
}
