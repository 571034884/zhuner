package com.aibabel.message;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.utils.FastJsonUtil;
import com.aibabel.baselibrary.utils.ToastUtil;
import com.aibabel.launcher.base.LaunBaseActivity;
import com.aibabel.launcher.net.Api;
import com.aibabel.launcher.view.MaterialBadgeTextView;
import com.aibabel.launcher.view.MyDialog;
import com.aibabel.menu.R;
import com.aibabel.message.hx.bean.CustomMessage;
import com.aibabel.message.hx.bean.IMUser;
import com.aibabel.message.fragment.Fragment_Chat;
import com.aibabel.message.fragment.Fragment_Conversation;
import com.aibabel.message.fragment.Fragment_Message;
import com.aibabel.message.fragment.Fragment_Task;
import com.aibabel.message.hx.cache.UserCacheManager;
import com.aibabel.message.utiles.Constant;
import com.aibabel.message.utiles.OkGoUtilWeb;
import com.aibabel.message.utiles.StringUtils;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;

import static com.aibabel.launcher.activity.MainActivity.set_BadgeCount;

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
    MaterialBadgeTextView tvUnreadMsgNumber;
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
    private int unread;


    @Override
    public int getLayout(Bundle savedInstanceState) {
        return R.layout.activity_main_mm;
    }

    @Override
    protected void initView() {
        isSetNick = mmkv.decodeBool("isSetNick", true);
        unread = mmkv.decodeInt("count", 0);
        //get user id or group id
//        toChatUsername = getIntent().getExtras().getString("userId");
        tvUnreadNumber = findViewById(R.id.tv_unread_number);
        fragment_index = getIntent().getExtras().getInt("fragment_index", 0);
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


        if(tvUnreadMsgNumber!=null)tvUnreadMsgNumber.setBadgeCount(set_BadgeCount);
        //判定是否支持，以便于显示不同的布局
//        selectUI();
        currentTabIndex = fragment_index;
        if (fragment_index == 0) {
            // TODO: 2019/4/22 除非为强推消息，否则优先准儿帮
//            refreshUIWithMessage(unread);
            getSupportFragmentManager().beginTransaction()
                    .show(fragmentMessage)
                    .hide(fragmentConversation)
                    .hide(fragmentTask)
                    .commit();
        } else if (fragment_index == 1) {

            getSupportFragmentManager().beginTransaction()
                    .show(fragmentConversation)
                    .hide(fragmentMessage)
                    .hide(fragmentTask)
                    .commit();
            isShowDialog();
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



    /**
     * tab点击切换
     *
     * @param view
     */
    public void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_msg:
                index = 0;
                if(tvUnreadMsgNumber!=null)tvUnreadMsgNumber.setBadgeCount(0);
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
        unread=0;
        tvUnreadNumber.setText("");
        tvUnreadNumber.setVisibility(View.GONE);
    }



    EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            for (EMMessage message : messages) {
                try {
                    JSONArray atJson = message.getJSONArrayAttribute("em_at_list"); // 被@用户列表,如果当前用户被@，需要ui特殊显示

                    List<CustomMessage> customMessages = FastJsonUtil.changeJsonToList(atJson.toString(), CustomMessage.class);
                    if (customMessages != null && customMessages.size() > 0) {
                        for (CustomMessage custom : customMessages) {
                            if (TextUtils.equals(custom.getExt().getAt(), UserCacheManager.getMyInfo().getUserId())) {
                                unread++;
                                refreshUIWithMessage(unread);

                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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

    private void refreshUIWithMessage(int count) {
        if (StringUtils.isSupported()) {
            if (EMClient.getInstance().isConnected() && currentTabIndex != 1) {
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
        String default_nick = mmkv.getString("nick", "");
        String edit_nick = editText.getText().toString().trim();
        editText.setHint(default_nick);

        final String nick = TextUtils.isEmpty(edit_nick) ? default_nick : edit_nick;
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNick(nick);
                builder.dismiss();
            }
        });
        builder.show();
    }



    private void isShowDialog() {
        if (isSetNick) {
            mmkv.encode("isSetNick", false);
            showDialogView();
        }
    }

    /**
     * 设置昵称，如果用户没有设置则用默认
     *
     * @param nick
     */
    private void setNick(String nick) {
        if (!TextUtils.isEmpty(nick) && TextUtils.equals(nick, mmkv.getString("nick", ""))) {
            return;
        }

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user_id", UserCacheManager.getMyInfo().getUserId());
            jsonObject.put("nickname", nick);

            OkGoUtilWeb.<String>post(this, Api.METHOD_IM_EDIT, jsonObject, IMUser.class, new BaseCallback<IMUser>() {
                @Override
                public void onSuccess(String method, IMUser model, String resoureJson) {
                    if (null != model) {
                        // TODO: 2019/4/22  昵称缓存到本地
                        ToastUtil.showShort(MainActivity.this, "昵称设置成功！");
                    }

                }

                @Override
                public void onError(String method, String message, String resoureJson) {
                    ToastUtil.showShort(MainActivity.this, "修改失败了，您暂时使用默认昵称");
                }

                @Override
                public void onFinsh(String method) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
