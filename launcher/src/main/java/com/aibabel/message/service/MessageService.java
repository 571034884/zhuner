package com.aibabel.message.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.aibabel.baselibrary.sphelper.SPHelper;
import com.aibabel.message.helper.DemoHelper;
import com.aibabel.message.utiles.Constant;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.tencent.mmkv.MMKV;

import java.util.List;


public class MessageService extends Service {

    private String TAG = MessageService.class.getSimpleName().toString();

    private String username;
    private String password;
    private Messenger mMessenger;
    private MessageMusicBroadReceiver receiver;

    public MessageService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        regFilter();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //添加接受消息监听
        EMClient.getInstance().chatManager().addMessageListener(messageListener);

        if (intent != null) {
            mMessenger = (Messenger) intent.getExtras().get("messenger");
        }

        return super.onStartCommand(intent, flags, startId);
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


    /**
     * 注册广播
     */
    private void regFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_LOGIN);
        intentFilter.setPriority(1000);
        if (receiver == null) {
            receiver = new MessageMusicBroadReceiver();
        }
        getApplicationContext().registerReceiver(receiver, intentFilter);
    }


    /**
     * 广播接收者
     */
    public class MessageMusicBroadReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Constant.ACTION_LOGIN:
                    Log.i(TAG, "onReceive: ACTION_Login");
                    String userId = intent.getStringExtra("username");
                    String pswd = intent.getStringExtra("pswd");
                    signIn(userId, pswd);
                    break;


            }
        }
    }


    /**
     * 环信登录方法
     */
    private void signIn(String username, String password) {

        if (DemoHelper.getInstance().isLoggedIn()) {
            Log.i(TAG, "已经登录了");
            return;
        }

        username = "user1";
        password = "123";
        /**
         * 保存账号密码到全局
         */
        MMKV.defaultMMKV().encode(Constant.EM_USERNAME,username);
        MMKV.defaultMMKV().encode(Constant.EM_PASSWORD,password);
        EMClient.getInstance().login(username, password, new EMCallBack() {
            /**
             * 登陆成功的回调
             */
            @Override
            public void onSuccess() {
                Log.e(TAG, "登录成功");
                // 加载所有会话到内存
                EMClient.getInstance().chatManager().loadAllConversations();
                // 加载所有群组到内存，如果使用了群组的话
                EMClient.getInstance().groupManager().loadAllGroups();

                sentLoginStatus(Constant.STATE_LOGIN_SUCCESS);



            }

            /**
             * 登陆错误的回调
             * @param i
             * @param s
             */
            @Override
            public void onError(final int i, final String s) {

                sentLoginStatus(Constant.STATE_LOGIN_FAILED);

                Log.d("lzan13", "登录失败 Error code:" + i + ", message:" + s);
                /**
                 * 关于错误码可以参考官方api详细说明
                 * http://www.easemob.com/apidoc/android/chat3.0/classcom_1_1hyphenate_1_1_e_m_error.html
                 */
                switch (i) {
                    // 网络异常 2
                    case EMError.NETWORK_ERROR:
                        Log.e(TAG, "网络错误 code: " + i + ", message:" + s);
//                        Toast.makeText(MessageService.this, "网络错误 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                        break;
                    // 无效的用户名 101
                    case EMError.INVALID_USER_NAME:
                        Log.e(TAG, "无效的用户名 code: " + i + ", message:" + s);
                        Toast.makeText(MessageService.this, "无效的用户名 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
//                        Toast.makeText(MessageService.this, "无效的用户名 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                        break;
                    // 无效的密码 102
                    case EMError.INVALID_PASSWORD:
                        Log.e(TAG, "无效的密码 code: " + i + ", message:" + s);
//                        Toast.makeText(MessageService.this, "无效的密码 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                        break;
                    // 用户认证失败，用户名或密码错误 202
                    case EMError.USER_AUTHENTICATION_FAILED:
                        Log.e(TAG, "用户认证失败，用户名或密码错误 code: " + i + ", message:" + s);
//                        Toast.makeText(MessageService.this, "用户认证失败，用户名或密码错误 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                        break;
                    // 用户不存在 204
                    case EMError.USER_NOT_FOUND:
                        Log.e(TAG, "用户不存在 code: " + i + ", message:" + s);
//                        Toast.makeText(MessageService.this, "用户不存在 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                        break;
                    // 无法访问到服务器 300
                    case EMError.SERVER_NOT_REACHABLE:
                        Log.e(TAG, "无法访问到服务器 code: " + i + ", message:" + s);
//                        Toast.makeText(MessageService.this, "无法访问到服务器 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                        break;
                    // 等待服务器响应超时 301
                    case EMError.SERVER_TIMEOUT:
                        Log.e(TAG, "等待服务器响应超时 code: " + i + ", message:" + s);
//                        Toast.makeText(MessageService.this, "等待服务器响应超时 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                        break;
                    // 服务器繁忙 302
                    case EMError.SERVER_BUSY:
                        Log.e(TAG, "服务器繁忙 code: " + i + ", message:" + s);
//                        Toast.makeText(MessageService.this, "服务器繁忙 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                        break;
                    // 未知 Server 异常 303 一般断网会出现这个错误
                    case EMError.SERVER_UNKNOWN_ERROR:
                        Log.e(TAG, "未知的服务器异常 code: " + i + ", message:" + s);
//                        Toast.makeText(MessageService.this, "未知的服务器异常 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Log.e(TAG, "ml_sign_in_failed code: " + i + ", message:" + s);
//                        Toast.makeText(MessageService.this, "ml_sign_in_failed code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                        break;
                }


            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    /**
     * 发送登录状态
     *
     * @param status
     */
    private void sentLoginStatus(int status) {
        Message mMessage = Message.obtain();
        mMessage.what = Constant.STATE_LOGIN_SUCCESS;
        mMessage.obj = status;
        try {
            //发送登录状态
            mMessenger.send(mMessage);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }


    /**
     * 更新未读数量
     */
    private void refreshUIWithMessage() {
        // refresh unread count
        Message mMessage = Message.obtain();
        mMessage.what = Constant.MSG_RECEIVER;
        int count = getUnreadMsgCountTotal();
        mMessage.arg1 = count;
        try {
            //发送未读数量状态
            mMessenger.send(mMessage);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    /**
     * update unread message count
     */
    public void updateUnreadLabel() {
        int count = getUnreadMsgCountTotal();

    }

    /**
     * get unread message count
     *
     * @return
     */
    public int getUnreadMsgCountTotal() {
        return EMClient.getInstance().chatManager().getUnreadMessageCount();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            getApplicationContext().unregisterReceiver(receiver);
        }

    }
}
