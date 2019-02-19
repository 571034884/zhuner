package com.aibabel.locationservice.receiver;


import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.aibabel.locationservice.activity.DialogActivity;
import com.aibabel.locationservice.R;
import com.aibabel.locationservice.activity.JiGuangActivity;
import com.aibabel.locationservice.bean.DetailBean;
import com.aibabel.locationservice.bean.PushMessageBean;
import com.aibabel.locationservice.utils.CommonUtils;
import com.aibabel.locationservice.utils.Constants;
import com.aibabel.locationservice.utils.FastJsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

/**
 * 自定义广播接收器
 * 极光会通过广播的形式转发给App
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
        Log.e("sn", CommonUtils.getSN() + "==");
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //140fe1da9eabf4cefe2
            //send the Registration Id to your server...
            //SDK 向 JPush Server 注册所得到的注册 ID 。可以通过此 ID 向对应的客户端发送消息和通知。

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            Constants.CONTEXTS_JG = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            Constants.TITLE_JG = bundle.getString(JPushInterface.EXTRA_TITLE);
            Constants.MESSAGE_JG = bundle.getString(JPushInterface.EXTRA_ALERT);
            Log.d(TAG, "[TITLE_JG]" + Constants.TITLE_JG + " /n[MESSAGE_JG]" + Constants.MESSAGE_JG);
            receivingNotification(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            /**
             * 如果通知的内容为空，则在通知栏上不会展示通知。
             * 但是，这个广播 Intent 还是会有。开发者可以取到通知内容外的其他信息。
             */
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
//            string = bundle.getString(JPushInterface.EXTRA_ALERT);
//            Log.e("消息", string);
            setNotification(context, Constants.TITLE_JG, Constants.MESSAGE_JG);


        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");

//            String jsonString = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//            String title = bundle.getString(JPushInterface.EXTRA_TITLE);
//            String msg = bundle.getString(JPushInterface.EXTRA_ALERT);
            openNotification(context, Constants.CONTEXTS_JG);

            /**
             * 如果开发者在 AndroidManifest.xml 里未配置此 receiver action，那么，
             * SDK 会默认打开应用程序的主 Activity，相当于用户点击桌面图标的效果。
             如果开发者在 AndroidManifest.xml 里配置了此 receiver action，
             那么，当用户点击通知时，SDK 不会做动作。开发者应该在自己写的 BroadcastReceiver 类里处理，比如打开某 Activity 。
             */
            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知" + extra);

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    /**
     * 接收到通知
     *
     * @param context
     * @param bundle
     */
    private void receivingNotification(Context context, Bundle bundle) {
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        Log.e(TAG, " title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        Log.e(TAG, "message : " + message);
    }


    /**
     * 点击通知栏
     *
     * @param context
     * @param jsonString
     */
    private void openNotification(Context context, String jsonString) {
//        String jsonString = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        Log.e("MyReceiver_jsonString", jsonString + "------");
        try {
            PushMessageBean bean = FastJsonUtil.changeJsonToBean(jsonString, PushMessageBean.class);

            if (TextUtils.equals(bean.getType(), "5")) {
                // TODO: 2019/1/10 打开链接地址

            } else if (TextUtils.equals(bean.getType(), "4")) {
                // TODO: 2019/1/10 调用其他应用
                switch (bean.getApk()) {
                    case "travel":
                        startScenic(bean, context);
                        break;
                    default:
                        break;
                }
            } else if (TextUtils.equals(bean.getType(), "3")) {
                // TODO: 2019/1/10 弹出带取消和确定提示框
//                startDialog(context, Constants.TITLE_JG, Constants.MESSAGE_JG, bean.getType(),bean.getPackageName(),bean.getPath());
                startDialog(context, Constants.TITLE_JG, Constants.MESSAGE_JG, bean);

            } else if (TextUtils.equals(bean.getType(), "2")) {
                // TODO: 2019/1/10 弹出带确定提示框
                startDialog(context, Constants.TITLE_JG, Constants.MESSAGE_JG, bean);

            }else if (TextUtils.equals(bean.getType(), "1")) {
                // TODO: 2019/1/10 弹出普通提示框
                startDialog(context, Constants.TITLE_JG, Constants.MESSAGE_JG, bean);
            }

        } catch (Exception e) {
            Log.w(TAG, "Unexpected: extras is not a valid json", e);
            return;
        }
    }

    /**
     * 启动景区导览详情页
     *
     * @param bean
     * @param context
     */
    private void startScenic(PushMessageBean bean, Context context) {
        try {
            Intent mIntent = new Intent();
            ComponentName componentName = new ComponentName("com.aibabel.travel", "com.aibabel.travel.activity.SpotDetailActivity");
            mIntent.setComponent(componentName);
            mIntent.putExtra("position", 0);
            List<DetailBean> list = new ArrayList<>();
            for (PushMessageBean.ResultDataBean data : bean.getResultData()) {
                DetailBean detailBean = new DetailBean();
                detailBean.setAudioUrl(data.getAudiosurl());
                detailBean.setImageUrl(data.getCover());
                detailBean.setName(data.getName());
                list.add(detailBean);
            }
            mIntent.putExtra("list", FastJsonUtil.changListToString(list));
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(mIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 启动一个dialog
     * @param context
     * @param title
     * @param msg
     * @param bean
     */
    private void startDialog(Context context, String title, String msg,PushMessageBean bean) {
        try {
            Intent intent = new Intent();
            intent.setClass(context, JiGuangActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("msg", bean.getContent());
            intent.putExtra("title", title);
            intent.putExtra("type", bean.getType());
            intent.putExtra("couponId", bean.getResultData().get(0).getCouponId());
            intent.putExtra("package", bean.getPackageName());
            intent.putExtra("path", bean.getPath());
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }


    /**
     * 设置极光推送通知栏样式
     *
     * @param context
     * @param title
     * @param content
     */
    private void setNotification(Context context, String title, String content) {
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(context);
        builder.statusBarDrawable = R.drawable.jpush_notification_icon; //通知图标
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL
                | Notification.FLAG_SHOW_LIGHTS; //设置为自动消失和呼吸灯闪烁
        builder.notificationDefaults = Notification.DEFAULT_SOUND
                | Notification.DEFAULT_VIBRATE
                | Notification.DEFAULT_LIGHTS; // 设置为铃声、震动、呼吸灯闪烁都要
        JPushInterface.setPushNotificationBuilder(1, builder);
    }


//    //send msg to MainActivity
//    private void processCustomMessage(Context context, Bundle bundle) {
//        if (MainActivity.isForeground) {
//            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//            Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
//            msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
//            if (!ExampleUtil.isEmpty(extras)) {
//                try {
//                    JSONObject extraJson = new JSONObject(extras);
//                    if (null != extraJson && extraJson.length() > 0) {
//                        msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
//                    }
//                } catch (JSONException e) {
//
//                }
//
//            }
//            context.sendBroadcast(msgIntent);
//        }
//    }
//}
}
