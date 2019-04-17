package com.aibabel.messagemanage.fragment;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aibabel.baselibrary.base.BaseFragment;
import com.aibabel.menu.R;
import com.aibabel.messagemanage.adapter.ChatAdapter;
import com.aibabel.messagemanage.utiles.ChatUiHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMCursorResult;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupInfo;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * ==========================================================================================
 *
 * @Author： 张文颖
 * @Date：2019/4/16
 * @Desc：聊天界面
 * @==========================================================================================
 */
public class Fragment_Chat extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    private static final int ITEM_VIDEO = 11;
    private static final int ITEM_FILE = 12;
    private static final int ITEM_VOICE_CALL = 13;
    private static final int ITEM_VIDEO_CALL = 14;
    private static final int ITEM_CONFERENCE_CALL = 15;
    private static final int ITEM_LIVE = 16;

    private static final int REQUEST_CODE_SELECT_VIDEO = 11;
    private static final int REQUEST_CODE_SELECT_FILE = 12;
    private static final int REQUEST_CODE_GROUP_DETAIL = 13;
    private static final int REQUEST_CODE_CONTEXT_MENU = 14;
    private static final int REQUEST_CODE_SELECT_AT_USER = 15;


    private static final int MESSAGE_TYPE_SENT_VOICE_CALL = 1;
    private static final int MESSAGE_TYPE_RECV_VOICE_CALL = 2;
    private static final int MESSAGE_TYPE_SENT_VIDEO_CALL = 3;
    private static final int MESSAGE_TYPE_RECV_VIDEO_CALL = 4;
    private static final int MESSAGE_TYPE_CONFERENCE_INVITE = 5;
    private static final int MESSAGE_TYPE_LIVE_INVITE = 6;
    private static final int MESSAGE_TYPE_RECALL = 9;
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
    protected EMConversation conversation;

    private ChatAdapter mAdapter;

    @Override
    public int getLayout() {
        return R.layout.fragment_chat;
    }

    @Override
    public void init(View view, Bundle savedInstanceState) {
        initContent();
    }


    protected void initContent() {
        mAdapter = new ChatAdapter(mContext, new ArrayList<EMMessage>());
        LinearLayoutManager mLinearLayout = new LinearLayoutManager(mContext);
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


    }


    @Override
    public void onRefresh() {
        //下拉刷新模拟获取历史消息
        List<EMMessage> mReceiveMsgList = new ArrayList<EMMessage>();
//        //构建文本消息
//        Message mMessgaeText = getBaseReceiveMessage(MsgType.TEXT);
//        TextMsgBody mTextMsgBody = new TextMsgBody();
//        mTextMsgBody.setMessage("收到的消息");
//        mMessgaeText.setBody(mTextMsgBody);
//        mReceiveMsgList.add(mMessgaeText);
//        //构建图片消息
//        Message mMessgaeImage = getBaseReceiveMessage(MsgType.IMAGE);
//        ImageMsgBody mImageMsgBody = new ImageMsgBody();
//        mImageMsgBody.setThumbUrl("http://pic19.nipic.com/20120323/9248108_173720311160_2.jpg");
//        mMessgaeImage.setBody(mImageMsgBody);
//        mReceiveMsgList.add(mMessgaeImage);
//        //构建文件消息
//        Message mMessgaeFile = getBaseReceiveMessage(MsgType.FILE);
//        FileMsgBody mFileMsgBody = new FileMsgBody();
//        mFileMsgBody.setDisplayName("收到的文件");
//        mFileMsgBody.setSize(12);
//        mMessgaeFile.setBody(mFileMsgBody);
//        mReceiveMsgList.add(mMessgaeFile);
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
        EMMessage message = EMMessage.createTxtSendMessage(content, "toChatUsername");
        //如果是群聊，设置chattype，默认是群聊
//        if (chatType == CHATTYPE_GROUP)
        message.setChatType(EMMessage.ChatType.GroupChat);
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message);

        //开始发送
        mAdapter.addData(message);
        //模拟两秒后发送成功
//        updateMsg(mMessgae);
    }


    private void getGroup() throws HyphenateException {

        //从服务器获取自己加入的和创建的群组列表，此api获取的群组sdk会自动保存到内存和db。
        List<EMGroup> grouplist = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();//需异步处理

        //从本地加载群组列表
//    List<EMGroup> grouplist = EMClient.getInstance().groupManager().getAllGroups();


    }


}
