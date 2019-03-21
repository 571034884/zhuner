package com.aibabel.messagemanage;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.aibabel.baselibrary.utils.FastJsonUtil;
import com.aibabel.menu.MainActivity;
import com.aibabel.menu.bean.DetailBean;
import com.aibabel.menu.bean.PushMessageBean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MessageUtil {

    public static   String TITLE_JG = "";
    public static   String MESSAGE_JG = "";
    public static   String CONTEXTS_JG = "";
    /**
     * 点击通知栏
     *
     * @param context
     * @param jsonString
     */
    public static void openNotification(Context context, String jsonString) {
//        String jsonString = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        Log.e("MyReceiver_jsonString", jsonString + "------");
        try {
            PushMessageBean bean = FastJsonUtil.changeJsonToBean(jsonString, PushMessageBean.class);

            if(bean==null)return;

            MESSAGE_JG = bean.getContent();

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
            Log.w("", "Unexpected: extras is not a valid json", e);
            return;
        }
    }

    /**
     * 点击通知栏
     *
     * @param context
     * @param
     */
    public static void openNotification_pushbean(Context context, PushMessageBean bean) {
        try {
            if(bean==null)return;
            MESSAGE_JG = bean.getContent();

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
            Log.w("", "Unexpected: extras is not a valid json", e);
            return;
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
    public static void startDialog(Context context, String title, String msg, PushMessageBean bean) {
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

    /**
     * 启动景区导览详情页
     *
     * @param bean
     * @param context
     */
    public static void startScenic(PushMessageBean bean, Context context) {
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


    public static String getRealtime() {
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            String format = formatter.format(calendar.getTime());
            return format;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getShowRealtime(String  systime) {
        if(TextUtils.isEmpty(systime))return "";
        try {
            DateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Long.valueOf(systime));
            String format = formatter.format(calendar.getTime());
            return format;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String args[]){
        System.out.println(getShowRealtime("1553136506886"));
        //System.out.println(System.currentTimeMillis());
    }

}
