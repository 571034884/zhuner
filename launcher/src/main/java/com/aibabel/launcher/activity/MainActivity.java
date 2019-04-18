package com.aibabel.launcher.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aibabel.baselibrary.mode.DataManager;
import com.aibabel.baselibrary.sphelper.SPHelper;
import com.aibabel.baselibrary.utils.CommonUtils;
import com.aibabel.baselibrary.utils.FastJsonUtil;
import com.aibabel.baselibrary.utils.SharePrefUtil;
import com.aibabel.launcher.R;
import com.aibabel.launcher.base.LaunBaseActivity;
import com.aibabel.launcher.bean.PushMessageBean;
import com.aibabel.launcher.bean.SyncOrder;
import com.aibabel.launcher.rent.RentDialogActivity;
import com.aibabel.launcher.rent.RentKeepUseActivity;
import com.aibabel.launcher.rent.RentLockedActivity;
import com.aibabel.launcher.rent.SimDetectActivity;
import com.aibabel.launcher.utils.CalenderUtil;
import com.aibabel.launcher.utils.DetectUtil;
import com.aibabel.launcher.utils.LocationUtils;
import com.aibabel.launcher.utils.LogUtil;
import com.aibabel.launcher.view.MaterialBadgeTextView;
import com.aibabel.message.sqlite.SqlUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends LaunBaseActivity {

    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.home_badge_icon)
    ImageView homeBadgeIcon;
    @BindView(R.id.home_badge)
    MaterialBadgeTextView homeBadge;
    @BindView(R.id.fl_notice)
    FrameLayout flNotice;
    @BindView(R.id.tv_wifi)
    TextView tvWifi;
    @BindView(R.id.main_layout_one)
    LinearLayout mainLayoutOne;
    @BindView(R.id.tv_more)
    TextView tvMore;

    /**
     * 跳转到制定的消息fragment中
     */
    private int fragment_index;


    @Override
    public int getLayout(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public void init() {

    }

    @Override
    public void initView() {
        homeBadge = findViewById(R.id.home_badge);
        signIn();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cv_more:
                startAct(MoreActivity.class);
                break;
            case R.id.fl_notice:
                startActivity(fragment_index);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    private void startActivity(int fragment_type) {
        Intent intent = new Intent(this, com.aibabel.message.MainActivity.class);
        intent.putExtra("fragment", fragment_type);
        startActivity(intent);
        homeBadge.setVisibility(View.GONE);

    }


    /**
     * 环信登录方法
     */
    private void signIn() {

        String username = "user1";
        String password = "123";
        /**
         * 保存账号密码到全局
         */
        SPHelper.save("username", username);
        SPHelper.save("password", password);

        EMClient.getInstance().login(username, password, new EMCallBack() {
            /**
             * 登陆成功的回调
             */
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("MainActivity", "登录成功");
                        // 加载所有会话到内存
                        EMClient.getInstance().chatManager().loadAllConversations();
                        // 加载所有群组到内存，如果使用了群组的话
                        EMClient.getInstance().groupManager().loadAllGroups();
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                try {
//                                    //从服务器获取自己加入的和创建的群组列表，此api获取的群组sdk会自动保存到内存和db。
//                                    List<EMGroup> grouplist = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();//需异步处理
//                                    String groupId = grouplist.get(0).getGroupId();
//                                    SPHelper.save("groupId", groupId);
//                                    Log.d("groupId", groupId);
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }).start();

                    }
                });
            }

            /**
             * 登陆错误的回调
             * @param i
             * @param s
             */
            @Override
            public void onError(final int i, final String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Log.d("lzan13", "登录失败 Error code:" + i + ", message:" + s);
                        /**
                         * 关于错误码可以参考官方api详细说明
                         * http://www.easemob.com/apidoc/android/chat3.0/classcom_1_1hyphenate_1_1_e_m_error.html
                         */
                        switch (i) {
                            // 网络异常 2
                            case EMError.NETWORK_ERROR:
                                Toast.makeText(MainActivity.this, "网络错误 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 无效的用户名 101
                            case EMError.INVALID_USER_NAME:
                                Toast.makeText(MainActivity.this, "无效的用户名 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 无效的密码 102
                            case EMError.INVALID_PASSWORD:
                                Toast.makeText(MainActivity.this, "无效的密码 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 用户认证失败，用户名或密码错误 202
                            case EMError.USER_AUTHENTICATION_FAILED:
                                Toast.makeText(MainActivity.this, "用户认证失败，用户名或密码错误 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 用户不存在 204
                            case EMError.USER_NOT_FOUND:
                                Toast.makeText(MainActivity.this, "用户不存在 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 无法访问到服务器 300
                            case EMError.SERVER_NOT_REACHABLE:
                                Toast.makeText(MainActivity.this, "无法访问到服务器 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 等待服务器响应超时 301
                            case EMError.SERVER_TIMEOUT:
                                Toast.makeText(MainActivity.this, "等待服务器响应超时 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 服务器繁忙 302
                            case EMError.SERVER_BUSY:
                                Toast.makeText(MainActivity.this, "服务器繁忙 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            // 未知 Server 异常 303 一般断网会出现这个错误
                            case EMError.SERVER_UNKNOWN_ERROR:
                                Toast.makeText(MainActivity.this, "未知的服务器异常 code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                            default:
                                Toast.makeText(MainActivity.this, "ml_sign_in_failed code: " + i + ", message:" + s, Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                });
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            // notify new message
//            for (EMMessage message : messages) {
//                DemoHelper.getInstance().getNotifier().vibrateAndPlayTone(message);
//            }
            refreshUIWithMessage();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            refreshUIWithMessage();
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
        }

        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
//            refreshUIWithMessage();
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
        }
    };

    /**
     * 更新未读数量
     */
    private void refreshUIWithMessage() {
        runOnUiThread(new Runnable() {
            public void run() {
                // refresh unread count
                updateUnreadLabel();

            }
        });
    }

    /**
     * update unread message count
     */
    public void updateUnreadLabel() {
        int count = getUnreadMsgCountTotal();
        if (count > 0) {
            homeBadge.setBadgeCount(count);
            homeBadge.setVisibility(View.VISIBLE);
        } else {
            homeBadge.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * get unread message count
     *
     * @return
     */
    public int getUnreadMsgCountTotal() {
        return EMClient.getInstance().chatManager().getUnreadMessageCount();
    }

//===================================环信结束==============================================

    public static MaterialBadgeTextView home_badge;
    public static int set_BadgeCount = 0;
    /********锁机流程********/
    public static final int toast_rent_Time = 16;
    public static final String bunder_iszhuner = "zhuner";
    public static final String bunder_qudao = "kefu";
    public static final String order_channelName = "order_channelName";
    public static final String order_oid = "order_oid";
    public static final String order_uid = "order_uid";
    public static final String order_uname = "order_uname";
    public static final String order_sn = "order_sn";
    public static final String order_from = "order_from_time";
    public static final String order_endttime = "orderendttime";
    public static final String order_islock = "order_islock";
    public static final String order_lockattime = "order_at";
    public static final String order_isZhuner = "order_isZhuner";

    private static final String neverUseNet_start = "never_user_start_time";
    private static final String neverUseNet_end = "never_user_end_time";
    private static final String neverUseNetflag = "never_user_flag";
    public static LooptempHandler loopHandler;
    private static boolean locknetsync = true;
    /**
     * 如果锁机了，再次同步一次
     */
    private static boolean iflocksyncAgain = true;



    /***
     * 这是一个静态,loop轮询机制
     */
    public class LooptempHandler extends Handler {
        WeakReference<Activity> mWeakReference;

        public LooptempHandler(Activity activity) {
            mWeakReference = new WeakReference<Activity>(activity);
        }

        @TargetApi(Build.VERSION_CODES.O)
        @Override
        public void handleMessage(Message msg) {
            try {

                LogUtil.e("msg.what = " + msg.what);

                final Activity activity = mWeakReference.get();
                if (activity != null) {

                    switch (msg.what) {
                        case 100:
                            try {
                                boolean RentLocked_fore = DetectUtil.isForeground(activity, RentLockedActivity.class);
                                LogUtil.e("RentLocked_fore " + RentLocked_fore);
                                SqlUtils.deleteDataAll();
                                if (RentLocked_fore) return;
                                Bundle getbundle = msg.getData();
                                String zhuner_str = getbundle.getString(bunder_iszhuner);
                                String qudao_str = getbundle.getString(bunder_qudao);
                                LogUtil.e("RentLockedActivity start" + zhuner_str + qudao_str);
                                if (TextUtils.isEmpty(zhuner_str)) {
                                    int isZhuner = SharePrefUtil.getInt(mContext, order_isZhuner, -1);
                                    if ((isZhuner == 1)) {
                                        zhuner_str = "zhuner";
                                    }
                                }
                                if (TextUtils.isEmpty(qudao_str)) {
                                    qudao_str = SharePrefUtil.getString(mContext, order_channelName, "");
                                }
                                Intent keepuse = new Intent(activity, RentLockedActivity.class); //已经到期
                                keepuse.putExtra(bunder_iszhuner, zhuner_str);
                                keepuse.putExtra(bunder_qudao, qudao_str);
                                startActivity(keepuse);
                                if (iflocksyncAgain) {
                                    iflocksyncAgain = false;
                                    loopHandler.sendEmptyMessageDelayed(130, 1000 * 5);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;

                        case 110:

                            boolean isForeground = DetectUtil.isForeground(activity, SimDetectActivity.class);
                            LogUtil.e("isForeground:" + isForeground);
                            if (!isForeground) {
                                Intent simdetect = new Intent(activity, SimDetectActivity.class);
                                startActivity(simdetect);
                            }
                            break;
                        case 120:
                            boolean Foreground = DetectUtil.isForeground(activity, RentDialogActivity.class);
                            if (!Foreground) {
                                Intent RentLocked = new Intent(activity, RentDialogActivity.class); //即将到期
                                startActivity(RentLocked);
                            }
                            break;
                        case 130: ////同步订单
                            if (isNetworkConnected()) {
                                LogUtil.e("isNetworkConnected = true");
                                SharePrefUtil.saveString(mContext, neverUseNetflag, "net_ok");
                                syncOrder(activity);
                            } else {
                                ///没有网络情况下
                                lock90day();
                            }
                            break;
                        case 200:
                            unlock_ok_clear();  //清除flag等
                            if (loopHandler != null)
                                loopHandler.sendEmptyMessageDelayed(130, 1000 * 60 * 5);
                            break;
                        case 300:
                            //无网络的话更新时间
                            if (!isNetworkConnected()) {
                                updatetime(CalenderUtil.getyyyyMMddHHmmss());
                            }
                            break;
                        case 301:
                            set_BadgeCount += 1;
                            home_badge.setBadgeCount(set_BadgeCount);
                            try {
                                PushMessageBean bean = (PushMessageBean) msg.obj;
                                bean.setBadge(true);
                                SqlUtils.updateBadgeBean(bean);
                                LogUtil.e("++++++++++++new ");
                            } catch (Exception e) {
                            }
                            break;
                        case 302:
                            set_BadgeCount -= 1;
                            if (set_BadgeCount > 0) home_badge.setBadgeCount(set_BadgeCount);
                            else {
                                set_BadgeCount = 0;
                                home_badge.setBadgeCount(set_BadgeCount);
                            }
                            break;
                        case 310:
                            /**####  start-hjs-addStatisticsEvent   ##**/
                            try {
                                HashMap<String, Serializable> add_hp = (HashMap<String, Serializable>) msg.obj;
                                addStatisticsEvent("push_notification", add_hp);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            /**####  end-hjs-addStatisticsEvent  ##**/
                            break;
                        case 320:
                            try {
                                HashMap<String, Serializable> add_hp = (HashMap<String, Serializable>) msg.obj;
                                addStatisticsEvent("push_notification1", add_hp);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case 330:
                            try {
                                HashMap<String, Serializable> add_hp = (HashMap<String, Serializable>) msg.obj;
                                addStatisticsEvent("push_notification2", add_hp);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case 340:
                            try {
                                HashMap<String, Serializable> add_hp = (HashMap<String, Serializable>) msg.obj;
                                addStatisticsEvent("push_notification5", add_hp);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case 400:
                            HashMap<String, Serializable> add_hp = (HashMap<String, Serializable>) msg.obj;
                            LogUtil.e("hjs" + add_hp.get("key"));
                            break;
                        default:
                            break;

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 跳转 100 handler
     */
    private void lockloopmsg(String order_end) {
//        String order_end = SharePrefUtil.getString(mContext, order_endttime, "");
        String channelName = SharePrefUtil.getString(mContext, order_channelName, "");
        LogUtil.e("channelName=" + (channelName));
        int isZhuner = SharePrefUtil.getInt(mContext, order_isZhuner, -1);
        if (TextUtils.isEmpty(order_end)) {
            return;
        }

        try {
            LogUtil.e("lockloopmsg=" + (order_end));
            //负数的话为已经过期{
            if ((!TextUtils.isEmpty(order_end)) && (CalenderUtil.compaeTimeWithAfter24(order_end) <= 0)) {
                if (isNetworkConnected()) {
                    if (locknetsync) {
                        if (isNetworkConnected()) syncOrder(getApplication());
                        locknetsync = false;
                    }
                } else {
                    Message message = new Message();
                    message.what = 100;
                    Bundle bun = new Bundle();

                    if ((isZhuner == 1)) {
                        bun.putString(bunder_iszhuner, "zhuner");
                    } else if (isZhuner == 0) {
                        bun.putString(bunder_qudao, channelName);
                    } else {
                        if (TextUtils.isEmpty(channelName)) {
                            bun.putString(bunder_iszhuner, "zhuner");
                            bun.putString(bunder_qudao, "");
                        } else {
                            bun.putString(bunder_iszhuner, "zz");
                            bun.putString(bunder_qudao, channelName);
                        }
                    }
                    message.setData(bun);
                    if (loopHandler != null) loopHandler.sendMessage(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isnetok = true;

    private void requestNetwork() {
        final ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo network = connectivityManager.getActiveNetworkInfo();
        int type = ConnectivityManager.TYPE_DUMMY;
        if (network != null) {
            type = network.getType();
        }

        if (type == ConnectivityManager.TYPE_MOBILE) {
            LogUtil.v(TAG, " mobile data is connected: " + network.isConnected());

        } else if (type == ConnectivityManager.TYPE_WIFI) {
            LogUtil.v(TAG, "wifi is connected: " + network.isConnected());
        }

        connectivityManager.requestNetwork(new NetworkRequest.Builder().build(), new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                super.onAvailable(network);

                LogUtil.v("void onAvailable(Network network:");
                NetworkInfo networkinfo = connectivityManager.getActiveNetworkInfo();
                int type = ConnectivityManager.TYPE_DUMMY;
                if (networkinfo != null) {
                    type = networkinfo.getType();
                }

                if (type == ConnectivityManager.TYPE_MOBILE) {
                    LogUtil.v("onAvailable mobile data is connected: " + networkinfo.isConnected());

                } else if (type == ConnectivityManager.TYPE_WIFI) {
                    LogUtil.v("onAvailable wifi is connected: " + networkinfo.isConnected());
                }

                Log.e("SERVICE_FUWU", "监听到当前有网");
                // TODO: 2019/4/17 b博博业务逻辑
                // getInternetService();
                if (isnetok) {
                    if (loopHandler != null) loopHandler.sendEmptyMessageDelayed(130, 10000);
                    isnetok = false;
                }
            }

            @Override
            public void onLost(Network network) {
                super.onLost(network);
                LogUtil.v("onLost(Network network)  ");

                NetworkInfo networkinfo = connectivityManager.getActiveNetworkInfo();

                int type = ConnectivityManager.TYPE_DUMMY;
                if (networkinfo != null) {
                    type = networkinfo.getType();
                }

                if (type == ConnectivityManager.TYPE_MOBILE) {
                    LogUtil.v("onLost mobile data is lost: " + networkinfo.isConnected());

                } else if (type == ConnectivityManager.TYPE_WIFI) {
                    LogUtil.v("onLost wifi is lost: " + networkinfo.isConnected());
                }

            }
        });

    }

    /*
    * 是否连接
    * */
    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public int boot_start_lock() {
        int lockflag = SharePrefUtil.getInt(mContext, order_islock, 0);

        if (lockflag == 1) {
            Log.e("hjs", "上次锁机，开机继续锁机");
            if (loopHandler != null) loopHandler.sendEmptyMessageDelayed(100, 1000);
            return 1;
        }
        return -1;
    }

    /**
     * 测试IP
     */
    //private static final String url_sync_order = "https://wx.aibabel.com:3002/common/api/machine/syncOrder";
    private static final String url_sync_order = "https://api.web.aibabel.cn:7001/common/api/machine/syncOrder";

    /**
     * 同步订单， hjs
     *
     * @param context
     */
    private void syncOrder(final Context context) {
        try {
            LogUtil.e("sn = " + CommonUtils.getSN());
            OkGo.<String>post(url_sync_order)
                    .tag(this)
                    .params("sn", CommonUtils.getSN())
                    .params("isInChina", LocationUtils.locationWhere(context))
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            if ((response != null) && (response.body() != null)) {
                                String reposStr = response.body().toString();
                                LogUtil.e("sync = " + reposStr);
                                try {
                                    SyncOrder syncOrderObj = FastJsonUtil.changeJsonToBean(reposStr, SyncOrder.class);
                                    if ((syncOrderObj != null)) {
                                        LogUtil.e("synorder != null");
                                        Locklogic(context, syncOrderObj);
                                        //SharePrefUtil.saveString();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();

                                }
                            }
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            LogUtil.e("reposStr onError = ");
                            ////okgerror();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 90天锁机逻辑,或者
     */
    public void lock90day() {
        ///没有网络情况下
        try {
            if (!isNetworkConnected()) {
                int lock_type = boot_start_lock();
                LogUtil.e("lock_type =" + lock_type);
                if (lock_type == 1) return;

                String endtime = SharePrefUtil.getString(mContext, neverUseNet_end, "");//90天未使用
                if (!TextUtils.isEmpty(endtime)) {
                    int comparetime = CalenderUtil.compaeTimeWithNow(endtime);
                    LogUtil.e("lock90day " + comparetime);
                    if (((comparetime <= toast_rent_Time) && (comparetime >= 11))) {
                        if (loopHandler != null) loopHandler.sendEmptyMessage(120);
                        return;
                    }
                }


                String spnettemp = SharePrefUtil.getString(mContext, neverUseNetflag, "");
                LogUtil.e("neverUseNetflag =" + spnettemp);
                LogUtil.e(" neverUseNet_end =" + endtime);
                if (!TextUtils.isEmpty(endtime)) {
                    if (CalenderUtil.compaeTimeWithAfter24(endtime) <= 0) {
                        LogUtil.e(" compaeTimeWithAfter24  lockloopmsg()  <=0");
                        lockloopmsg(endtime);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * 更新时间
     *
     * @param partime
     */
    private void updatetime(String partime) {
        if (partime == null) return;
        try {
            String end90time = CalenderUtil.calculateTimeDifferenceadd90(partime);
            SharePrefUtil.put(mContext, neverUseNet_start, partime);
            SharePrefUtil.put(mContext, neverUseNet_end, end90time);
            Log.e("hjs", "updatetime ok" + end90time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static boolean synctimefore = true;

    /**
     * 锁机逻辑，hjs
     */
    public void Locklogic(Context context, SyncOrder synorder) {
        SyncOrder.Body OrderBody = synorder.getBody();
        if (OrderBody != null) {
            String chanelname = OrderBody.getChannelName();
            String oid = OrderBody.getOid();
            String uid = OrderBody.getUid();
            String uname = OrderBody.getUname();
            String sn = OrderBody.getSn();
            String from_time = OrderBody.getF();
            String end_time = OrderBody.getT();
            int islock = OrderBody.getIsLock();
            int attime = OrderBody.getAt();
            int isZhuner = OrderBody.getIsZhuner();

            SharePrefUtil.saveString(context, order_channelName, chanelname);
            SharePrefUtil.saveString(context, order_oid, oid);
            SPHelper.save(order_oid, oid);
            DataManager.getInstance().setSaveString(order_oid, oid);
            SharePrefUtil.saveString(context, order_uid, uid);
            SharePrefUtil.saveString(context, order_uname, uname);

            SharePrefUtil.saveString(context, order_endttime, end_time);
            SharePrefUtil.saveString(context, order_sn, sn);
            SharePrefUtil.saveString(context, order_from, from_time);

            if (!TextUtils.isEmpty(end_time)) {
                SharePrefUtil.saveString(context, end_time, end_time);
                updatetime(end_time);
            } else {
                try {
                    String[] startstr = CalenderUtil.calculateTimeDifferenceByDuration();
                    if (startstr != null) {
                        String starttime_str = startstr[0];
                        String end_time_str = startstr[1];
                        LogUtil.e(" = order starttime= " + starttime_str);
                        LogUtil.e(" =order  end_time= " + end_time_str);
                        SharePrefUtil.saveString(mContext, neverUseNet_start, starttime_str);
                        SharePrefUtil.saveString(mContext, neverUseNet_end, end_time_str);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            if (islock >= 0) {
                SharePrefUtil.saveInt(context, order_islock, islock);
                try {
                    boolean RentLocked_fore = DetectUtil.isForeground(this, RentLockedActivity.class);
                    boolean RentKeepUse = DetectUtil.isForeground(this, RentKeepUseActivity.class);
                    if ((RentKeepUse || RentLocked_fore) && islock == 0) {
                        LogUtil.e("RentLocked_fore = " + RentLocked_fore);
                        try {
                            RentLockedActivity.finsRentlock();
                            RentKeepUseActivity.finsRentlock();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //if (loopHandler != null) loopHandler.sendEmptyMessage(200);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            SharePrefUtil.saveInt(context, order_lockattime, attime);
            SharePrefUtil.saveInt(context, order_isZhuner, isZhuner);


            try {
                ///服务器锁机
                if (islock == 1) {
                    LogUtil.e("服务器锁机 ");
                    Message message = new Message();
                    message.what = 100;
                    Bundle bun = new Bundle();
                    if ((isZhuner == 1)) {
                        bun.putString(bunder_iszhuner, "zhuner");
                    } else if (isZhuner == 0) {
                        bun.putString(bunder_qudao, chanelname);
                    } else {
                        if (TextUtils.isEmpty(chanelname)) {
                            bun.putString(bunder_iszhuner, "zhuner");
                            bun.putString(bunder_qudao, "");
                        } else {
                            bun.putString(bunder_iszhuner, "zz");
                            bun.putString(bunder_qudao, chanelname);
                        }
                    }
                    message.setData(bun);
                    if (loopHandler != null) loopHandler.sendMessage(message);
                    return;
                }

                int comparetime = CalenderUtil.compaeTimeWithNow(end_time);
                LogUtil.e("提醒续租 ");
                //提醒续租
                if (((comparetime <= toast_rent_Time) && (comparetime >= 11))) {
                    if (loopHandler != null) loopHandler.sendEmptyMessage(120);
                    return;
                }
                LogUtil.e("到期后24小时内锁机 _fail ");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            String order_id = SharePrefUtil.getString(context, order_oid, "");
            ///国外 并且无订单
            if ((LocationUtils.locationWhere(context) == 0) && (TextUtils.isEmpty(order_id))) {
                LogUtil.e("国外 并且无订单  ");
            }
        }
    }

    /**
     * 扫码解锁后 清除标志位、清楚90天未联网日期、关闭lock按钮界面
     */
    private void unlock_ok_clear() {
        try {
            boolean RentLocked_fore = DetectUtil.isForeground(this, RentLockedActivity.class);
            LogUtil.e("RentLocked_fore = " + RentLocked_fore);
            try {
                if (RentLocked_fore) {
                    RentLockedActivity.finsRentlock();
                }
                RentKeepUseActivity.finsRentlock();
                RentLockedActivity.finsRentlock();
            } catch (Exception e) {

            }

            clearALlsharePreutil();

            init_neveruser();
            //TODO 清除服务器域名
            LogUtil.e("onRestart = RentLocked_fore");

            locknetsync = true;
            isnetok = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除所有sharep 订单信息
     */
    public void clearALlsharePreutil() {
        SqlUtils.deleteDataAll();
        SharePrefUtil.put(mContext, neverUseNetflag, "");
        SharePrefUtil.put(mContext, neverUseNet_start, "");
        SharePrefUtil.put(mContext, neverUseNet_end, "");
        SharePrefUtil.put(mContext, order_channelName, "");
        SharePrefUtil.put(mContext, order_oid, "");
        SharePrefUtil.put(mContext, order_uid, "");
        SharePrefUtil.put(mContext, order_uname, "");
        SharePrefUtil.put(mContext, order_sn, "");
        SharePrefUtil.put(mContext, order_from, "");
        SharePrefUtil.put(mContext, order_endttime, "");
        SharePrefUtil.put(mContext, order_islock, 0);
        SharePrefUtil.put(mContext, order_lockattime, 0);
        SharePrefUtil.put(mContext, order_isZhuner, 0);
        int lockflag = SharePrefUtil.getInt(mContext, order_islock, 0);
        String get_starttime = SharePrefUtil.getString(mContext, neverUseNet_start, "");
        String get_endtime = SharePrefUtil.getString(mContext, neverUseNet_end, "");

        LogUtil.e("get_starttime==" + get_starttime);
        LogUtil.e("get_endtime==" + get_endtime);

    }

    /**
     * 初始化从来未使用的函数
     */
    private void init_neveruser() {
        try {
            String get_starttime = "";
            String get_endtime = "";
            try {
                get_starttime = SharePrefUtil.getString(mContext, neverUseNet_start, "");
                get_endtime = SharePrefUtil.getString(mContext, neverUseNet_end, "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            LogUtil.e("start__= " + get_starttime + " = init_neveruser = " + get_endtime);
            if (TextUtils.isEmpty(get_starttime) || (TextUtils.isEmpty(get_endtime))) {
                String[] startstr = CalenderUtil.calculateTimeDifferenceByDuration();
                if (startstr != null) {
                    String starttime = startstr[0];
                    String end_time = startstr[1];

                    LogUtil.e(" = init_neveruser starttime= " + starttime);
                    LogUtil.e(" =  end_time= " + end_time);

                    SharePrefUtil.saveString(mContext, neverUseNet_start, starttime);
                    SharePrefUtil.saveString(mContext, neverUseNet_end, end_time);

                    get_starttime = SharePrefUtil.getString(mContext, neverUseNet_start, "");
                    LogUtil.e("  starttime= " + get_starttime);

                    get_endtime = SharePrefUtil.getString(mContext, neverUseNet_end, "");
                    LogUtil.e("  get_endtime= " + get_endtime);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
