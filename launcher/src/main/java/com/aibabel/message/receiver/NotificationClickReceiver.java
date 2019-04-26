package com.aibabel.message.receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.util.Log;

import com.aibabel.baselibrary.utils.FastJsonUtil;
import com.aibabel.menu.activity.MainActivity;
import com.aibabel.menu.bean.PushMessageBean;
import com.aibabel.message.broadcast.ResidentNotificationHelper;
import com.aibabel.message.utiles.MessageUtil;

import java.io.Serializable;
import java.util.HashMap;

public class NotificationClickReceiver extends BroadcastReceiver {
    public static final String TYPE = "type";

    @Override
    public void onReceive(Context context, Intent intent) {
        //todo 跳转之前要处理的逻辑
        Log.i("hjs", "userClick:触发！ ");

        String action = intent.getAction();
        int type = intent.getIntExtra(TYPE, -1);

        if (type != -1) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(type);
        }
        if ((action != null)) {
            if (action.equals("notification_clicked")) {
                //处理点击事件
                //String message = intent.getStringExtra("MESSAGE");
                //Toast.makeText(context, "clicked " + message, Toast.LENGTH_LONG).show();

                String extjson = intent.getStringExtra(ResidentNotificationHelper.intentjson);
                Log.i("hjs", "userClick:我被点击啦！！！ "+extjson);
                String title = intent.getStringExtra("title");
                MessageUtil.TITLE_JG = title;

                MessageUtil.openNotification(context, extjson);
                if(MainActivity.loopHandler!=null){

                    try {
                        PushMessageBean bean = FastJsonUtil.changeJsonToBean(extjson, PushMessageBean.class);
                        MainActivity.loopHandler.sendEmptyMessage(302);
                        /**####  start-hjs-addStatisticsEvent   ##**/
                        HashMap<String, Serializable> add_hp = new HashMap<>();
                        add_hp.put("push_notification1_def", bean.getTitle());
                        add_hp.put("push_notification1_id", bean.getNum());
                        Message msgtemp = new Message();
                        msgtemp.what = 320;
                        msgtemp.obj=add_hp;
                        MainActivity.loopHandler.sendMessage(msgtemp);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    /**####  end-hjs-addStatisticsEvent  ##**/

                }
            }

            if (action.equals("notification_cancelled")) {
                //处理滑动清除和点击删除事件
                //Toast.makeText(context, "cancelled", Toast.LENGTH_LONG).show();
                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    String extjson = intent.getStringExtra(ResidentNotificationHelper.intentjson);
                    PushMessageBean bean = FastJsonUtil.changeJsonToBean(extjson, PushMessageBean.class);
                    HashMap<String, Serializable> add_hp = new HashMap<>();
                    add_hp.put("push_notification5_def", bean.getTitle());
                    add_hp.put("push_notification5_id",bean.getNum() );
                    Message msgtemp = new Message();
                    msgtemp.what = 320;
                    msgtemp.obj=add_hp;
                    MainActivity.loopHandler.sendMessage(msgtemp);

                }catch (Exception e){
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/

                Log.i("hjs", "userClick:我取消啦！！！ ");
                if(MainActivity.loopHandler!=null)MainActivity.loopHandler.sendEmptyMessage(302);
            }
        }


    }

//
//    private  String TITLE_JG = "";
//    private  String MESSAGE_JG = "";
//    private  String CONTEXTS_JG = "";
//    /**
//     * 点击通知栏
//     *
//     * @param context
//     * @param jsonString
//     */
//    private void openNotification(Context context, String jsonString) {
////        String jsonString = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//        Log.e("MyReceiver_jsonString", jsonString + "------");
//        try {
//            PushMessageBean bean = FastJsonUtil.changeJsonToBean(jsonString, PushMessageBean.class);
//            MESSAGE_JG = bean.getContent();
//
//            if (TextUtils.equals(bean.getType(), "5")) {
//                // TODO: 2019/1/10 打开链接地址
//
//            } else if (TextUtils.equals(bean.getType(), "4")) {
//                // TODO: 2019/1/10 调用其他应用
//                switch (bean.getApk()) {
//                    case "travel":
//                        startScenic(bean, context);
//                        break;
//                    default:
//                        break;
//                }
//            } else if (TextUtils.equals(bean.getType(), "3")) {
//                // TODO: 2019/1/10 弹出带取消和确定提示框
////                startDialog(context, Constants.TITLE_JG, Constants.MESSAGE_JG, bean.getType(),bean.getPackageName(),bean.getPath());
//                startDialog(context, TITLE_JG, MESSAGE_JG, bean);
//
//            } else if (TextUtils.equals(bean.getType(), "2")) {
//                startDialog(context, TITLE_JG, MESSAGE_JG, bean);
//
//            } else if (TextUtils.equals(bean.getType(), "1")) {
//                startDialog(context, TITLE_JG, MESSAGE_JG, bean);
//            }
//
//        } catch (Exception e) {
//            Log.w("", "Unexpected: extras is not a valid json", e);
//            return;
//        }
//    }
//    /**
//     * 启动一个dialog
//     *
//     * @param context
//     * @param title
//     * @param msg
//     * @param bean
//     */
//    private void startDialog(Context context, String title, String msg, PushMessageBean bean) {
//        try {
//            Intent intent = new Intent();
//            intent.setClass(context, JiGuangActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            intent.putExtra("msg", bean.getContent());
//            intent.putExtra("title", title);
//            intent.putExtra("type", bean.getType());
//            intent.putExtra("couponId", bean.getResultData().get(0).getCouponId());
//            intent.putExtra("package", bean.getPackageName());
//            intent.putExtra("path", bean.getPath());
//            context.startActivity(intent);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 启动景区导览详情页
//     *
//     * @param bean
//     * @param context
//     */
//    private void startScenic(PushMessageBean bean, Context context) {
//        try {
//            List<DetailBean> list = new ArrayList<>();
//            for (PushMessageBean.ResultDataBean data : bean.getResultData()) {
//                DetailBean detailBean = new DetailBean();
//                detailBean.setAudioUrl(data.getAudiosurl());
//                detailBean.setImageUrl(data.getCover());
//                detailBean.setName(data.getName());
//                list.add(detailBean);
//            }
//
//            Intent mIntent = new Intent();
//            ComponentName componentName = new ComponentName(bean.getPackageName(), bean.getPath());
//            mIntent.setComponent(componentName);
//            mIntent.putExtra("position", 0);
//            mIntent.putExtra("list", FastJsonUtil.changListToString(list));
//            mIntent.putExtra("from", "notification");
//            mIntent.putExtra("id", bean.getResultData().get(0).getIdstring());
//            mIntent.putExtra("url", bean.getResultData().get(0).getCover());
//            mIntent.putExtra("name", bean.getResultData().get(0).getName());
//            context.startActivity(mIntent);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

}