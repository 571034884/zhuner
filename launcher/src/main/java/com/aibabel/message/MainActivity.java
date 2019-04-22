package com.aibabel.message;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.baselibrary.sphelper.SPHelper;
import com.aibabel.baselibrary.utils.CommonUtils;
import com.aibabel.launcher.R;
import com.aibabel.launcher.base.LaunBaseActivity;
import com.aibabel.launcher.view.MyDialog;
import com.aibabel.message.fragment.Fragment_Chat;
import com.aibabel.message.fragment.Fragment_Conversation;
import com.aibabel.message.fragment.Fragment_Message;
import com.aibabel.message.fragment.Fragment_Task;
import com.aibabel.message.helper.DemoHelper;
import com.aibabel.message.receiver.MessageListener;
import com.aibabel.message.service.MessageService;
import com.aibabel.message.utiles.Constant;
import com.aibabel.message.utiles.StringUtils;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.utils.EaseUserUtils;


import java.util.List;
import java.util.UUID;

import butterknife.BindView;

public class MainActivity extends LaunBaseActivity  {


    @BindView(R.id.fl_content)
    FrameLayout flContent;

    Fragment_Chat fragmentChat;
    Fragment_Task fragmentTask;
    Fragment_Conversation fragmentConversation;
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
    private int fragment_index;
    private boolean isSetNick;
    private MyDialog builder;
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
    protected void initView() {
        isSetNick = mmkv.decodeBool("isSetNick", true);
        //get user id or group id
//        toChatUsername = getIntent().getExtras().getString("userId");
        tvUnreadNumber = findViewById(R.id.tv_unread_number);
        fragment_index = getIntent().getExtras().getInt("fragment", 0);
        mTabs = new Button[3];
        mTabs[0] = findViewById(R.id.btn_msg);
        mTabs[1] = findViewById(R.id.btn_chat);
        mTabs[2] = findViewById(R.id.btn_task);
        mViews = new View[3];
        mViews[0] = findViewById(R.id.v_msg);
        mViews[1] = findViewById(R.id.v_chat);
        mViews[2] = findViewById(R.id.v_task);
        // select tab
        mTabs[fragment_index].setSelected(true);
        mViews[fragment_index].setVisibility(View.VISIBLE);

        fragmentChat = new Fragment_Chat();
        fragmentConversation = new Fragment_Conversation();
        fragmentMessage = new Fragment_Message();
        fragmentTask = new Fragment_Task();
        fragments = new Fragment[]{fragmentMessage, fragmentConversation, fragmentTask};

        //判定是否支持，以便于显示不同的布局
//        selectUI();
        currentTabIndex = fragment_index;
        if (fragment_index == 0) {
            refreshUIWithMessage();
            getSupportFragmentManager().beginTransaction()
                    .show(fragmentMessage)
                    .hide(fragmentConversation)
                    .hide(fragmentTask)
                    .commit();
        } else if (fragment_index == 1) {
            isShowDialog();
            getSupportFragmentManager().beginTransaction()
                    .show(fragmentConversation)
                    .hide(fragmentMessage)
                    .hide(fragmentTask)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .hide(fragmentMessage)
                    .hide(fragmentConversation)
                    .show(fragmentTask)
                    .commit();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    private void selectUI() {
        if (StringUtils.isSupported()) {
            btnContainerChat.setVisibility(View.VISIBLE);
        } else {
            btnContainerChat.setVisibility(View.GONE);
        }
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
                isShowDialog();
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


    private void makeReaded() {
        tvUnreadNumber.setText("");
        tvUnreadNumber.setVisibility(View.GONE);
    }



    public void getMessage() {
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


    EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            refreshUIWithMessage();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {

        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
        }

        @Override
        public void onMessageRecalled(List<EMMessage> messages) {

        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {

        }
    };

    private void refreshUIWithMessage() {
        if (StringUtils.isSupported()) {
            if (EMClient.getInstance().isConnected() && currentTabIndex != 1) {
                int count = EMClient.getInstance().chatManager().getUnreadMessageCount();
                tvUnreadNumber.setVisibility(View.VISIBLE);
                if (count > 0) {
                    tvUnreadNumber.setText(String.valueOf(count));
                }
                if (count > 99) {
                    tvUnreadNumber.setText("99+");
                }
            } else {
                tvUnreadNumber.setVisibility(View.GONE);
            }
        }
    }



    private void showDialogView() {
        View view = getLayoutInflater().inflate(R.layout.dialog_layout_nick, null);
        builder = new MyDialog(mContext, 0, 0, view, R.style.dialog);
        builder.setCancelable(false);

        //初始化控件
        EditText editText = view.findViewById(R.id.et_dialog_nick);
        TextView btnCommit = view.findViewById(R.id.tv_dialog_commit);

        editText.setHint("准儿帮" + getUUID());
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // TODO: 2019/4/20  此处要设置环信昵称

                builder.dismiss();
            }
        });
        builder.show();
    }

    /**
     * 获得一个UUID
     *
     * @return String UUID
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        //去掉“-”符号
        return uuid.replaceAll("-", "");
    }

    private void isShowDialog() {
        if (isSetNick) {
            mmkv.encode("isSetNick", false);
            showDialogView();
        }
    }

}
