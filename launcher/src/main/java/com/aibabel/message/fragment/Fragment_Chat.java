package com.aibabel.message.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aibabel.baselibrary.base.BaseFragment;
import com.aibabel.baselibrary.sphelper.SPHelper;
import com.aibabel.menu.R;
import com.aibabel.message.adapter.ChatAdapter;
import com.aibabel.message.utiles.ChatUiHelper;
import com.aibabel.message.utiles.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ==========================================================================================
 *
 * @Author： 张文颖
 * @Date：2019/4/16
 * @Desc：聊天界面
 * @==========================================================================================
 */
public class Fragment_Chat extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, EMMessageListener {

    private String TAG = Fragment_Chat.class.getSimpleName().toString();

    @BindView(R.id.group_title)
    TextView groupTitle;
    @BindView(R.id.rv_chat_list)
    RecyclerView mRvChat;
    @BindView(R.id.swipe_chat)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.btn_send)
    TextView mBtnSend;
    @BindView(R.id.llContent)
    LinearLayout mLlContent;
    @BindView(R.id.v)
    View v;
    protected EMConversation conversation;
    private ExecutorService fetchQueue;
    private List<EMMessage> list = new ArrayList<>();

    private ChatAdapter mAdapter;
    private String toChatUsername;
    protected int chatType;
    protected boolean isRoaming = false;
    private int pagesize = 20;


    @Override
    public int getLayout() {
        return R.layout.fragment_chat;
    }

    @Override
    public void init(View view, Bundle savedInstanceState) {
        view.findViewById(R.id.rootView).setFitsSystemWindows(true);
        toChatUsername = SPHelper.getString("username", "");
        initContent();
    }


    protected void initContent() {
        mAdapter = new ChatAdapter(mContext, new ArrayList<EMMessage>());
        LinearLayoutManager mLinearLayout = new LinearLayoutManager(mContext);
        mLinearLayout.setStackFromEnd(true);
        mRvChat.setLayoutManager(mLinearLayout);
        mRvChat.setAdapter(mAdapter);
        mSwipeRefresh.setOnRefreshListener(this);
        initChatUi();
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

    }


    private void initChatUi() {
        mEtContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                //获取当前界面可视部分
                getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //获取屏幕的高度
                int screenHeight = getActivity().getWindow().getDecorView().getRootView().getHeight();
                //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                int heightDifference = screenHeight - r.bottom;

                lockContentHeight();
                unlockContentHeightDelayed(heightDifference);


            }
        });


        //mBtnAudio
        final ChatUiHelper mUiHelper = ChatUiHelper.with(getActivity());
        mUiHelper.bindContentLayout(mLlContent)
                .bindEditText(mEtContent);
        //底部布局弹出,聊天列表上滑
        mRvChat.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom) {
                    mRvChat.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mAdapter.getItemCount() > 0) {
                                mRvChat.smoothScrollToPosition(mAdapter.getItemCount() - 1);
                            }
                        }
                    });
                }
            }
        });
        //点击空白区域关闭键盘
        mRvChat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mUiHelper.hideBottomLayout(false);
                mUiHelper.hideSoftInput();
                mEtContent.clearFocus();
                return false;
            }
        });

        onConversationInit();
    }


    protected void onConversationInit() {
        conversation = EMClient.getInstance().chatManager().getConversation(toChatUsername, EaseCommonUtils.getConversationType(chatType), true);
        if (null != conversation)
            conversation.markAllMessagesAsRead();
        // the number of messages loaded into conversation is getChatOptions().getNumberOfMessagesLoaded
        // you can change this number
        if (!isRoaming) {
            final List<EMMessage> msgs = conversation.getAllMessages();
            int msgCount = msgs != null ? msgs.size() : 0;
            if (msgCount < conversation.getAllMsgCount() && msgCount < pagesize) {
                String msgId = null;
                if (msgs != null && msgs.size() > 0) {
                    msgId = msgs.get(0).getMsgId();
                }
                conversation.loadMoreMsgFromDB(msgId, pagesize - msgCount);
            }
        } else {
            fetchQueue.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        EMClient.getInstance().chatManager().fetchHistoryMessages(toChatUsername, EaseCommonUtils.getConversationType(chatType), pagesize, "");
                        final List<EMMessage> msgs = conversation.getAllMessages();
                        int msgCount = msgs != null ? msgs.size() : 0;
                        if (msgCount < conversation.getAllMsgCount() && msgCount < pagesize) {
                            String msgId = null;
                            if (msgs != null && msgs.size() > 0) {
                                msgId = msgs.get(0).getMsgId();
                            }
                            conversation.loadMoreMsgFromDB(msgId, pagesize - msgCount);
                        }

//                        messageList.refreshSelectLast();
                        mAdapter.setNewData(msgs);

                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


    @Override
    public void onRefresh() {
        //下拉刷新模拟获取历史消息
        List<EMMessage> mReceiveMsgList = new ArrayList<EMMessage>();
        mAdapter.addData(0, mReceiveMsgList);
        mSwipeRefresh.setRefreshing(false);
    }

    @OnClick({R.id.btn_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                sendTextMsg(mEtContent.getText().toString());
                mEtContent.setText("");
                break;

        }
    }

    //文本消息
    private void sendTextMsg(String content) {

        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        final EMMessage message = EMMessage.createTxtSendMessage(content, "79769202917377");
        //如果是群聊，设置chattype，默认是群聊
        message.setChatType(EMMessage.ChatType.GroupChat);
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message);

        mAdapter.addData(message);
        list.add(message);
        mRvChat.scrollToPosition(mAdapter.getItemCount() - 1);

        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                // 消息发送成功，打印下日志，正常操作应该去刷新ui
                Log.e("lzan13", "send message on success");
                message.setStatus(EMMessage.Status.SUCCESS);

            }

            @Override
            public void onError(int i, String s) {
                // 消息发送失败，打印下失败的信息，正常操作应该去刷新ui
                Log.e("lzan13", "send message on error " + i + " - " + s);
                message.setStatus(EMMessage.Status.FAIL);
//                mAdapter.notifyItemChanged();
            }

            @Override
            public void onProgress(int i, String s) {
                // 消息发送进度，一般只有在发送图片和文件等消息才会有回调，txt不回调
            }
        });

    }


    private void getGroup() throws HyphenateException {

        //从服务器获取自己加入的和创建的群组列表，此api获取的群组sdk会自动保存到内存和db。
        List<EMGroup> grouplist = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();//需异步处理

        //从本地加载群组列表
//    List<EMGroup> grouplist = EMClient.getInstance().groupManager().getAllGroups();


    }

    @Override
    public void onResume() {
        super.onResume();
//        if(isMessageListInited)
//            messageList.refresh();
        EaseUI.getInstance().pushActivity(getActivity());
        // register the event listener when enter the foreground
        EMClient.getInstance().chatManager().addMessageListener(this);

//        if (chatType == EaseConstant.CHATTYPE_GROUP) {
//            EaseAtMessageHelper.get().removeAtMeGroup(toChatUsername);
//        }
    }

    //=========================================================================

    @Override
    public void onMessageReceived(List<EMMessage> list) {
        //收到消息
        for (EMMessage message : list) {
            // 只有群组消息
            String username = message.getTo();
            mAdapter.addData(message);
            conversation.markMessageAsRead(message.getMsgId());
        }
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {
        //收到透传消息
    }

    @Override
    public void onMessageRead(List<EMMessage> list) {
        //收到已读回执
    }

    @Override
    public void onMessageDelivered(List<EMMessage> list) {
        //收到已送达回执
    }

    @Override
    public void onMessageRecalled(List<EMMessage> list) {
        //消息被撤回
    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {
        //消息状态变动
    }
    //=========================================================================


    @Override
    public void onDestroy() {
        super.onDestroy();
//        记得在不需要的时候移除listener，如在activity的onDestroy()时
        EMClient.getInstance().chatManager().removeMessageListener(this);
    }

    /**
     * 锁定内容高度，防止跳闪
     */
    private void lockContentHeight() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mLlContent.getLayoutParams();
        params.height = mLlContent.getHeight();
        params.weight = 0.0F;
    }


    /**
     * 释放被锁定的内容高度
     */
    public void unlockContentHeightDelayed(final int heightDifference) {
        mEtContent.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mLlContent == null) {
                    return;
                }
                ((LinearLayout.LayoutParams) mLlContent.getLayoutParams()).weight = 1.0F;
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.height = heightDifference - SizeUtils.dp2px(mContext, 36);
                v.setLayoutParams(layoutParams);
            }
        }, 10L);
    }


}
