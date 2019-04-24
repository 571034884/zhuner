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
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.utils.CommonUtils;
import com.aibabel.menu.net.Api;
import com.aibabel.menu.utils.Logs;
import com.aibabel.message.helper.DemoHelper;
import com.aibabel.message.hx.bean.IMUser;
import com.aibabel.message.hx.cache.UserCacheManager;
import com.aibabel.message.receiver.NetBroadcastReceiver;
import com.aibabel.message.utiles.Constant;
import com.aibabel.message.utiles.OkGoUtilWeb;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.tencent.mmkv.MMKV;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;


public class MessageService extends Service implements NetBroadcastReceiver.NetListener {

    private String TAG = MessageService.class.getSimpleName().toString();
    private Messenger mMessenger;
    private MessageMusicBroadReceiver receiver;
    private int unread = 0;
    private MMKV mmkv = MMKV.defaultMMKV();

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

        if (intent != null) {
            mMessenger = (Messenger) intent.getExtras().get("messenger");
        }

        /**
         * 添加环信监听
         */
        EMClient.getInstance().chatManager().addMessageListener(messageListener);

        /**
         * 每次进来先去请求环信用户
         */
        getUserInfo();

        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * 环信监听
     */
    EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {


            for (EMMessage message : messages) {
                try {
                    Map<String, Object> map = message.ext();
                    String at = (String) map.get("at");

                    if (TextUtils.equals(at, mmkv.getString(Constant.EM_USERNAME,""))) {
                        unread++;
                        refreshUIWithMessage(unread);
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


    /**
     * 注册广播
     */
    private void regFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_LOGIN);
        intentFilter.addAction(Constant.ACTION_MAKE_READED);
        intentFilter.addAction(Constant.ACTION_MESSAGE);
        intentFilter.addAction(Constant.ACTION_HX_USERINFO);
        intentFilter.setPriority(1000);
        if (receiver == null) {
            receiver = new MessageMusicBroadReceiver();
        }
        getApplicationContext().registerReceiver(receiver, intentFilter);
    }

    @Override
    public void netState(boolean isAvailable) {
        //判定如果有网络检查环信是否登录，未登录去登录
        String isLogin = mmkv.decodeString("isLogin", "");
        if (isAvailable && TextUtils.isEmpty(isLogin)) {
            getUserInfo();
        } else {
            long intIP = Long.parseLong(isLogin);
            long outIP = System.currentTimeMillis();
            long results = outIP - intIP;
            Logs.e("环信登录计算:" + outIP + "-" + intIP + "=" + results);
            //超过5小时 请求一次 1800000
            if (results > 1800000) {
                getUserInfo();
            }
        }


    }

    @Override
    public void netState(String nameWifi) {

    }


    /**
     * 广播接收者
     */
    public class MessageMusicBroadReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Constant.ACTION_MAKE_READED:
                    unread = 0;
                    break;
                case Constant.ACTION_HX_USERINFO:
                    getUserInfo();
                    break;


            }
        }
    }


    /**
     * 请求后台获取环信登录账号密码
     */
    private void getUserInfo() {
        Log.i(TAG, "开始发送请求了");
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sn", CommonUtils.getSN());

            OkGoUtilWeb.<String>post(this, Api.METHOD_IM, jsonObject, IMUser.class, new BaseCallback<IMUser>() {
                @Override
                public void onSuccess(String method, IMUser model, String resoureJson) {
                    if (null != model) {
                        Log.i(TAG, "请求成功了");
                        mmkv.encode("isLogin", System.currentTimeMillis() + "");
                        if (model.getBody().getIs_im() == 1) {//支持准儿帮
                            mmkv.encode(Constant.EM_USERNAME, model.getBody().getUser_id());
                            mmkv.encode(Constant.EM_PASSWORD, model.getBody().getPwd());
                            mmkv.encode(Constant.EM_NICk, model.getBody().getNickname());
                            mmkv.encode(Constant.EM_AVATAR, model.getBody().getHead_img());
                            mmkv.encode(Constant.EM_GROUP, model.getBody().getGroup_id());
                            mmkv.encode(Constant.EM_GROUP_NAME, model.getBody().getGroup_name());
                            mmkv.encode(Constant.EM_SUPPORT, true);
                            signIn(model);
                        } else {
                            mmkv.encode(Constant.EM_SUPPORT, false);
                            //判定当前是否在登录着 如果登录着，则退出登录
                            if (DemoHelper.getInstance().isLoggedIn()) {
                                EMClient.getInstance().logout(true);
                            }
                        }

                    }

                }

                @Override
                public void onError(String method, String message, String resoureJson) {
                    mmkv.encode(Constant.EM_USERNAME, "");
                    mmkv.encode(Constant.EM_PASSWORD, "");
                    mmkv.encode(Constant.EM_NICk, "");
                    mmkv.encode(Constant.EM_AVATAR, "");
                    mmkv.encode(Constant.EM_GROUP, "");
                    mmkv.encode(Constant.EM_GROUP_NAME, "");
                    mmkv.encode(Constant.EM_SUPPORT, false);
                }

                @Override
                public void onFinsh(String method) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 环信登录方法
     */
    private void signIn(IMUser model) {
        final String username = model.getBody().getUser_id();
        final String password = model.getBody().getPwd();
        final String nickName = model.getBody().getNickname();
        final String avatar = model.getBody().getHead_img();

        Log.e(TAG, "group:" + model.getBody().getGroup_id() + " username:" + username + " password:" + password + " nickName:" + nickName + " avatar:" + avatar);


//        if (TextUtils.equals(username, UserCacheManager.getMyInfo().getUserId())) {
//            Log.i(TAG, "已经登录了");
//            return;
//        }
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Log.i(TAG, "账号密码空了");
            return;
        }


        EMClient.getInstance().login(username, password, new EMCallBack() {

            @Override
            public void onSuccess() {
                Log.e(TAG, "登录成功");
                // 加载所有会话到内存
                EMClient.getInstance().chatManager().loadAllConversations();
                // 加载所有群组到内存，如果使用了群组的话
                EMClient.getInstance().groupManager().loadAllGroups();
                sentLoginStatus(Constant.STATE_LOGIN_SUCCESS);
                /**
                 * 保存账号密码到全局
                 */
                MMKV.defaultMMKV().encode(Constant.EM_USERNAME, username);
                MMKV.defaultMMKV().encode(Constant.EM_PASSWORD, password);
                /**
                 * 登录成功后，保存用户信息
                 */
                UserCacheManager.save(username, nickName, avatar);

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
    private void refreshUIWithMessage(int count) {
        // refresh unread count
        Message mMessage = Message.obtain();
        mMessage.what = Constant.MSG_RECEIVER;
        mMessage.arg1 = count;
        try {
            //发送未读数量状态
            mMessenger.send(mMessage);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            getApplicationContext().unregisterReceiver(receiver);
        }

    }
}
