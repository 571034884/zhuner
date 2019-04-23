package com.aibabel.message.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aibabel.menu.R;
import com.aibabel.message.helper.DemoHelper;
import com.aibabel.message.utiles.Constant;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.tencent.mmkv.MMKV;

import java.util.List;


/**
 *==========================================================================================
 * @Author： 张文颖
 *
 * @Date：2019/4/16
 *
 * @Desc：对话列表
 *==========================================================================================
 */
public class Fragment_Conversation extends EaseChatFragment implements EaseChatFragment.EaseChatFragmentHelper {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState, DemoHelper.getInstance().getModel().isMsgRoaming() && (chatType != EaseConstant.CHATTYPE_CHATROOM));
    }

    @Override
    protected boolean turnOnTyping() {
        return DemoHelper.getInstance().getModel().isShowMsgTyping();
    }

    @Override
    protected void setUpView() {
        setChatFragmentHelper(this);
        super.setUpView();
    }


    @Override
    public void onSetMessageAttributes(EMMessage message) {
        String nick = MMKV.defaultMMKV().getString(Constant.EM_NICk,"");
        String avatar = MMKV.defaultMMKV().getString(Constant.EM_AVATAR,"");
        message.setAttribute(EaseConstant.MESSAGE_ATTR_NICK_MSG, nick);
        message.setAttribute(EaseConstant.MESSAGE_ATTR_AVATAR_MSG, avatar);
    }

    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        return null;
    }


    @Override
    public void onEnterToChatDetails() {
        if (chatType == Constant.CHATTYPE_GROUP) {
            EMGroup group = EMClient.getInstance().groupManager().getGroup(toChatUsername);
            if (group == null) {
                Toast.makeText(getActivity(), R.string.gorup_not_found, Toast.LENGTH_SHORT).show();
                return;
            }
//            startActivityForResult((new Intent(getActivity(), GroupDetailsActivity.class).putExtra("groupId", toChatUsername)), REQUEST_CODE_GROUP_DETAIL);
        }
    }

    @Override
    public void onAvatarClick(String username) {
        //handling when user click avatar
//        Intent intent = new Intent(getActivity(), UserProfileActivity.class);
//        intent.putExtra("username", username);
//        startActivity(intent);
    }

    @Override
    public void onAvatarLongClick(String username) {
        inputAtUsername(username);
    }


    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        //消息框点击事件，demo这里不做覆盖，如需覆盖，return true
        return false;
    }
    @Override
    public void onCmdMessageReceived(List<EMMessage> messages) {
        super.onCmdMessageReceived(messages);
    }

    @Override
    public void onMessageBubbleLongClick(EMMessage message) {
    }

    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        //keep exist extend menu
        return false;
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
