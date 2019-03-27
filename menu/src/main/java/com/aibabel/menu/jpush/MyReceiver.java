package com.aibabel.menu.jpush;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;

import com.aibabel.baselibrary.utils.FastJsonUtil;
import com.aibabel.menu.MainActivity;
import com.aibabel.menu.R;
import com.aibabel.menu.base.BaseActivity;
import com.aibabel.menu.bean.DetailBean;
import com.aibabel.menu.bean.PushBean;
import com.aibabel.menu.bean.PushMessageBean;
import com.aibabel.menu.broadcast.ResidentNotificationHelper;
import com.aibabel.menu.util.DetectUtil;
import com.aibabel.menu.util.LogUtil;
import com.aibabel.messagemanage.JiGuangActivity;
import com.aibabel.messagemanage.sqlite.SqlUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
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
        checksyncOrder(context,bundle);

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver]MESSAGE接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));

            receivingNotification(context, bundle);

            /*** 处理推送流程hjs*/
            MenuonReceive(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            /**
             * 如果通知的内容为空，则在通知栏上不会展示通知。
             * 但是，这个广播 Intent 还是会有。开发者可以取到通知内容外的其他信息。
             */
            Log.d(TAG, "[MyReceiver] 通知消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 通知的ID: " + notifactionId);
//            setNotification(context, Constants.TITLE_JG, Constants.MESSAGE_JG);
            MenuonReceive(context, bundle);


        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {

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
     * 检查同步更新
     *
     * @param bundle
     */
    private void checksyncOrder(Context context, Bundle bundle) {
        try {
            /**解锁推送**/
            String extra_str = bundle.getString(JPushInterface.EXTRA_EXTRA);
            LogUtil.e(" =========" + extra_str);
            if (!TextUtils.isEmpty(extra_str)) {
                int numid = -1;
                int code = -1;
//                extra_str = extra_str.replace(" ","");
                if (extra_str.contains("relet")) {
//                    LogUtil.e(" ====extra_str.contains(\"code\"=====" + extra_str.contains("code"));
//                    if (extra_str.contains("code")) {
//                    LogUtil.e("----------------code  = 1");
//                        if(extra_str.contains(":1,")) {
//                            Intent stopIntent = new Intent("com.android.qrcode.unlock.ok");
//                            context.sendBroadcast(stopIntent);
//                        }
//                    } else
//                    String aaa = "{\"relet\":\"{\\\"code\\\":1,\\\"msg\\\":\\\"请同步订单\\\"}\"}";
                        {
                        try {
                            JSONObject json = new JSONObject(extra_str);
                            String relet = (String) json.get("relet");
                            JSONObject jsonRelet = new JSONObject(relet);
                            code = (Integer) jsonRelet.get("code");
                            if (code == 1) {
                                Intent stopIntent = new Intent("com.android.qrcode.unlock.ok");
                                context.sendBroadcast(stopIntent);
                                LogUtil.e("code  = 1");
                            }
                            numid = (int) json.get("no");
                        } catch (JSONException e) {
                            Log.e(TAG, "Get message extra JSON error!");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    /**####  start-hjs-addStatisticsEvent   ##**/
                    try {
                        if (MainActivity.loopHandler != null) {
                            HashMap<String, Serializable> add_hp = new HashMap<>();
                            add_hp.put("push_notification2_def", code);
                            add_hp.put("push_notification2_def", numid);
                            //MainActivity.loopHandler.obtainMessage(330, add_hp);
                            Message msgtemp = new Message();
                            msgtemp.what = 330;
                            msgtemp.obj=add_hp;
                            MainActivity.loopHandler.sendMessage(msgtemp);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    /**####  end-hjs-addStatisticsEvent  ##**/
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    private String TITLE_JG = "";
    private String MESSAGE_JG = "";
    private String CONTEXTS_JG = "";

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
                startDialog(context, TITLE_JG, MESSAGE_JG, bean);

            } else if (TextUtils.equals(bean.getType(), "2")) {
                startDialog(context, TITLE_JG, MESSAGE_JG, bean);

            } else if (TextUtils.equals(bean.getType(), "1")) {
                startDialog(context, TITLE_JG, MESSAGE_JG, bean);
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
            List<DetailBean> list = new ArrayList<>();
            for (PushMessageBean.ResultDataBean data : bean.getResultData()) {
                DetailBean detailBean = new DetailBean();
                detailBean.setAudioUrl(data.getAudiosurl());
                detailBean.setImageUrl(data.getCover());
                detailBean.setName(data.getName());
                list.add(detailBean);
            }

            Intent mIntent = new Intent();
            ComponentName componentName = new ComponentName(bean.getPackageName(), bean.getPath());
            mIntent.setComponent(componentName);
            mIntent.putExtra("position", 0);
            mIntent.putExtra("list", FastJsonUtil.changListToString(list));
            mIntent.putExtra("from", "notification");
            mIntent.putExtra("id", bean.getResultData().get(0).getIdstring());
            mIntent.putExtra("url", bean.getResultData().get(0).getCover());
            mIntent.putExtra("name", bean.getResultData().get(0).getName());
            context.startActivity(mIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 启动一个dialog
     *
     * @param context
     * @param title
     * @param msg
     * @param bean
     */
    private void startDialog(Context context, String title, String msg, PushMessageBean bean) {
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
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                try {
                    sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
                } catch (Exception e) {
                    e.printStackTrace();
                }
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


    /**
     * 调起应用  没有启动直接启动   启动过直接调起
     *
     * @param context
     * @param packageName
     * @return
     */
    public static Intent getAppOpenIntentByPackageName(Context context, String packageName) {
        String mainAct = null;
        PackageManager pkgMag = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_NEW_TASK);

        @SuppressLint("WrongConstant")
        List<ResolveInfo> list = pkgMag.queryIntentActivities(intent, PackageManager.GET_ACTIVITIES);
        for (int i = 0; i < list.size(); i++) {
            ResolveInfo info = list.get(i);
            if (info.activityInfo.packageName.equals(packageName)) {
//                mainAct = info.activityInfo.name;
                mainAct = "com.aibabel.travel.activity.SpotDetailActivity";
                break;
            }
        }
        if (TextUtils.isEmpty(mainAct)) {
            return null;
        }
        intent.setComponent(new ComponentName(packageName, mainAct));
        return intent;
    }


    public void MenuonReceive(Context context, Bundle intent) {
        try {
            String json = intent.getString(JPushInterface.EXTRA_MESSAGE);
            String title = intent.getString(JPushInterface.EXTRA_TITLE);
            if (TextUtils.isEmpty(title)) title = "";

            PushMessageBean bean = FastJsonUtil.changeJsonToBean(json, PushMessageBean.class);
            if (bean != null) {
                bean.setJson(json);
                SqlUtils.insertData(bean);
            } else return;


            if (MainActivity.loopHandler != null) {
//                MainActivity.loopHandler.obtainMessage(301,bean);
                Message msgtempadd = new Message();
                msgtempadd.what = 301;
                msgtempadd.obj = bean;
                MainActivity.loopHandler.sendMessage(msgtempadd);


                HashMap<String, Serializable> add_hp = new HashMap<>();
                add_hp.put("push_notification_def", bean.getPackageName());
                add_hp.put("push_notification_id", bean.getNum());
                Message msgtemp = new Message();
                msgtemp.what = 310;
                msgtemp.obj=add_hp;
                MainActivity.loopHandler.sendMessage(msgtemp);
            }

            String pushcontent = bean.getContent();
            if (TextUtils.isEmpty(pushcontent)) pushcontent = "";

            if (TextUtils.equals(bean.getLevel(), "1")) {
                Intent noticeIntent = new Intent();
                noticeIntent.putExtra("json", json);
//                noticeIntent.setClass(context, Class.forName(noticeIntent.getPackage().getClass()));

                ResidentNotificationHelper.sendResidentNotice(context, "" + title, "" + pushcontent, noticeIntent);

            } else if (TextUtils.equals(bean.getLevel(), "2")) {
                //判定语音翻译，小秘书，拍照翻译是否在前台处理显示通知
                if (DetectUtil.isAppInForeground(context, "com.aibabel.translate")
                        || (DetectUtil.isAppInForeground(context, "com.aibabel.speech"))
                        || (DetectUtil.isAppInForeground(context, "com.aibabel.ocr"))) {
                    LogUtil.e("语音翻译正在运行----");
                    boolean delayshow = true;
                    while (delayshow) {
                        try {
                            Thread.sleep(1000 * 60 * 3);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (DetectUtil.isAppInForeground(context, "com.aibabel.translate")
                                || (DetectUtil.isAppInForeground(context, "com.aibabel.speech"))
                                || (DetectUtil.isAppInForeground(context, "com.aibabel.ocr"))) {
                            delayshow = true;
                        } else {
                            delayshow = false;
                            Intent noticeIntent = new Intent();
                            noticeIntent.putExtra("json", json);
                            ResidentNotificationHelper.sendResidentNotice(context, "" + title, "" + pushcontent, noticeIntent);
                        }
                    }
                } else {
                    Intent noticeIntent = new Intent();
                    noticeIntent.putExtra("json", json);
                    ResidentNotificationHelper.sendResidentNotice(context, "" + title, "" + pushcontent, noticeIntent);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    public static void main(String args[]){
////        String jsonstr="{\"type\":\"4\",\"dialogType\":\"\",\"content\":\"向您推荐景点清华大学\",\"apk\":\"travel\",\"num\":\"10\",\"packageName\":\"com.aibabel.travel\",\"path\":\"com.aibabel.travel.activity.SpotDetailActivity\",\"timeCode\":\"1553155357\",\"resultData\":[{\"idstring\":\"3836\",\"name\":\"清华大学\",\"cover\":\"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2Fd255e9e79b76c5cfb486829ddc50451ba013bf59.jpg\",\"Audiosurl\":\"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/705ab5abbf4277dfa698d8ac41ac7170ca7793a3.mp3\"}],\"level\":\"2\",\"title\":\"景点\"}";
////        PushMessageBean bean = FastJsonUtil.changeJsonToBean(jsonstr, PushMessageBean.class);
////       System.out.println(bean.getLevel());
////        System.out.println(bean.getResultData().get(0).getIdstring());
//
//        String extra_ = "[relet - {\"code\":1,\"msg\":\"请同步订单\"}]";
//        try {
//            JSONObject json = new JSONObject(extra_);
//            String relet = (String) json.get("relet");
//
//            JSONObject jsonRelet = new JSONObject(relet);
//            int code = (Integer) jsonRelet.get("code");
//            if (code == 1) {
//                Intent stopIntent = new Intent("com.android.qrcode.unlock.ok");
//
//                System.out.println("code  = 1");
//            }
//        } catch (JSONException e) {
//            Log.e(TAG, "Get message extra JSON error!");
//        }
//
//    }


}



