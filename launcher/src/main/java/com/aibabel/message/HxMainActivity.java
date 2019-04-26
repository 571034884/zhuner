package com.aibabel.message;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.utils.CommonUtils;
import com.aibabel.baselibrary.utils.ToastUtil;
import com.aibabel.menu.activity.MainActivity;
import com.aibabel.menu.base.LaunBaseActivity;
import com.aibabel.menu.net.Api;
import com.aibabel.menu.view.MaterialBadgeTextView;
import com.aibabel.menu.view.MyDialog;
import com.aibabel.menu.R;
import com.aibabel.message.hx.bean.IMUser;
import com.aibabel.message.fragment.Fragment_Chat;
import com.aibabel.message.fragment.Fragment_Conversation;
import com.aibabel.message.fragment.Fragment_Message;
import com.aibabel.message.fragment.Fragment_Task;
import com.aibabel.message.hx.cache.UserCacheManager;
import com.aibabel.message.receiver.MessageListener;
import com.aibabel.message.utiles.Constant;
import com.aibabel.message.utiles.OkGoUtilWeb;
import com.aibabel.message.utiles.StringUtils;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

import static com.aibabel.menu.activity.MainActivity.set_BadgeCount;

public class HxMainActivity extends LaunBaseActivity {


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
    MaterialBadgeTextView tvUnreadNumber;
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
    private int fragment_index = 0;
    private boolean isSetNick;
    private MyDialog builder;
    private String groupName;


    @Override
    public int getLayout(Bundle savedInstanceState) {
        return R.layout.activity_main_mm;
    }

    @Override
    protected void initView() {
        try {
            fragment_index = getIntent().getExtras().getInt("fragment_index", 0);
            isSetNick = mmkv.decodeBool("isSetNick", true);
            toChatUsername = mmkv.decodeString(Constant.EM_GROUP);
            groupName = mmkv.decodeString(Constant.EM_GROUP_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (TextUtils.isEmpty(toChatUsername) || !mmkv.decodeBool(Constant.EM_SUPPORT, false)) {
            fragment_index = 0;
        }

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
        //传入Fragment中
        Bundle bundle = new Bundle();
        bundle.putString("toChatUsername", toChatUsername);
        bundle.putString("groupName", groupName);
        fragmentConversation.setArguments(bundle);
        if (tvUnreadMsgNumber != null) tvUnreadMsgNumber.setBadgeCount(set_BadgeCount);
        //判定是否支持，以便于显示不同的布局
        currentTabIndex = fragment_index;
        isSupport();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (tvUnreadMsgNumber != null) tvUnreadMsgNumber.setBadgeCount(set_BadgeCount);
    }

    /**
     * 判定是否支持准儿帮，这个参数有后台返回
     * <p>
     * 在MessageService保存的
     */
    private void isSupport() {
        if (mmkv.decodeBool(Constant.EM_SUPPORT, false)) {
            btnContainerChat.setVisibility(View.VISIBLE);
            if (fragment_index == 0) {
                // TODO: 2019/4/22 除非为强推消息，否则优先准儿帮
                showSelect(fragment_index);
                // refreshUIWithMessage(unread);
            } else if (fragment_index == 1) {
                showSelect(fragment_index);
                isShowDialog();
            } else {
                showSelect(fragment_index);
            }

        } else {
            btnContainerChat.setVisibility(View.GONE);
            showSelect(0);
        }


    }


    @Override
    protected void onResume() {
        super.onResume();
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
                addStatisticsEvent("menu_team_butt", null);
                if (tvUnreadMsgNumber != null) tvUnreadMsgNumber.setBadgeCount(0);
                break;
            case R.id.btn_chat:
                index = 1;
                addStatisticsEvent("menu_team_help", null);
//                makeAsRead();
                isShowDialog();
                break;
            case R.id.btn_task:
                addStatisticsEvent("menu_team_task", null);
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


    private void showSelect(int selectIndex) {
        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < fragments.length; i++) {
            if (i == selectIndex) {
                trx.add(R.id.fl_content, fragments[i]);
                Log.e("selectIndex", selectIndex + "");
                trx.show(fragments[i]);
                if (i == 0) {
                    addStatisticsEvent("menu_team_butt", null);
                } else if (i == 1) {
                    addStatisticsEvent("menu_team_help", null);
                } else if (i == 2) {
                    addStatisticsEvent("menu_team_task", null);
                }
            } else {
                trx.hide(fragments[i]);
            }
        }
        trx.commit();
    }


    private void isShowDialog() {
        if (isSetNick) {
            mmkv.encode("isSetNick", false);
            showDialogView();
        }
    }

    private void showDialogView() {
        View view = getLayoutInflater().inflate(R.layout.dialog_layout_nick, null);
        builder = new MyDialog(mContext, 0, 0, view, R.style.dialog);
        builder.setCancelable(false);

        //初始化控件
        EditText editText = view.findViewById(R.id.et_dialog_nick);
        TextView btnCommit = view.findViewById(R.id.tv_dialog_commit);
        String default_nick = mmkv.getString(Constant.EM_NICk, "");
        editText.setHint(default_nick);
        String edit_nick = editText.getText().toString().trim();
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

    /**
     * 设置昵称，如果用户没有设置则用默认
     *
     * @param nick
     */
    private void setNick(final String nick) {
        if (TextUtils.isEmpty(nick)) {
            ToastUtil.showShort(HxMainActivity.this, "昵称不能为空！");
            return;
        }
        if (!TextUtils.isEmpty(nick) && TextUtils.equals(nick, mmkv.getString(Constant.EM_NICk, ""))) {
            return;
        }

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user_id", mmkv.decodeString(Constant.EM_USERNAME));
            jsonObject.put("nickname", nick);
            jsonObject.put("version", CommonUtils.getDevice());
            OkGoUtilWeb.<String>post(this, Api.METHOD_IM_EDIT, jsonObject, IMUser.class, new BaseCallback<IMUser>() {
                @Override
                public void onSuccess(String method, IMUser model, String resoureJson) {
//                    if (null != model) {
                    ToastUtil.showShort(HxMainActivity.this, "昵称设置成功！");
                    // TODO: 2019/4/22  昵称缓存到本地
                    UserCacheManager.updateMyNick(nick);
                    mmkv.encode(Constant.EM_NICk, nick);
//                    }

                }

                @Override
                public void onError(String method, String message, String resoureJson) {
                    ToastUtil.showShort(HxMainActivity.this, "修改失败了，您暂时使用默认昵称");
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
