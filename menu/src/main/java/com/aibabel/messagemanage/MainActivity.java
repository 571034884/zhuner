package com.aibabel.messagemanage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.menu.R;
import com.aibabel.messagemanage.fragment.Fragment_Chat;
import com.aibabel.messagemanage.fragment.Fragment_Message;
import com.aibabel.messagemanage.fragment.Fragment_Task;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements EMMessageListener {


    @BindView(R.id.fl_content)
    FrameLayout flContent;

    Fragment_Chat fragmentChat;
    Fragment_Task fragmentTask;
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

    protected EMConversation conversation;

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
        mTabs = new Button[3];
        mTabs[0] = findViewById(R.id.btn_msg);
        mTabs[1] = findViewById(R.id.btn_chat);
        mTabs[2] = findViewById(R.id.btn_task);
        mViews = new View[3];
        mViews[0] = findViewById(R.id.v_msg);
        mViews[1] = findViewById(R.id.v_chat);
        mViews[2] = findViewById(R.id.v_task);

        // select first tab
        mTabs[0].setSelected(true);
        mViews[0].setVisibility(View.VISIBLE);

        fragmentChat = new Fragment_Chat();
        fragmentTask = new Fragment_Task();
        fragmentMessage = new Fragment_Message();
        fragments = new Fragment[]{fragmentMessage, fragmentChat, fragmentTask};
        getSupportFragmentManager().beginTransaction()
                .show(fragmentMessage)
                .hide(fragmentChat)
                .hide(fragmentTask)
                .commit();


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


//=============================消息发送状态变化，接收=====================================


    @Override
    public void onMessageReceived(List<EMMessage> messages) {
        //收到消息
//        for (EMMessage message : messages) {
//            String username = null;
//            // group message
//            if (message.getChatType() == EMMessage.ChatType.GroupChat || message.getChatType() == EMMessage.ChatType.ChatRoom) {
//                username = message.getTo();
//            }
//
//            // if the message is for current conversation
//            if (username.equals(toChatUsername) || message.getTo().equals(toChatUsername) || message.conversationId().equals(toChatUsername)) {
//                messageList.refreshSelectLast();
//                conversation.markMessageAsRead(message.getMsgId());
//            }
//        }
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> messages) {
        //收到透传消息
    }

    @Override
    public void onMessageRead(List<EMMessage> messages) {
        //收到已读回执
    }

    @Override
    public void onMessageDelivered(List<EMMessage> message) {
        //收到已送达回执
    }
    @Override
    public void onMessageRecalled(List<EMMessage> messages) {
        //消息被撤回
    }

    @Override
    public void onMessageChanged(EMMessage message, Object change) {
        //消息状态变动
    }

    //=============================消息发送状态变化，接收，结束=====================================

    @Override
    public void onDestroy() {
        super.onDestroy();
//
//        if (groupListener != null) {
//            EMClient.getInstance().groupManager().removeGroupChangeListener(groupListener);
//        }

    }
}
