package com.aibabel.menu.broadcast;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.aibabel.baselibrary.utils.FastJsonUtil;
import com.aibabel.menu.R;
import com.aibabel.menu.bean.DetailBean;
import com.aibabel.menu.bean.PushBean;
import com.aibabel.menu.bean.PushMessageBean;
import com.aibabel.menu.util.CommonUtils;
import com.aibabel.menu.util.Constans;
import com.aibabel.menu.util.DetectUtil;
import com.aibabel.messagemanage.JiGuangActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NoticeBroadcastReceiver extends BroadcastReceiver {
    private static String TAG = "mesman";

    public static String TITLE_JG = "";
    public static String MESSAGE_JG = "";
    public static String CONTEXTS_JG = "";

//    Intent noticeIntent= new Intent();
//noticeIntent.setAction("com.aibabel.menu.msg");
//noticeIntent.putExtra("title",Constants.TITLE_JG);
//noticeIntent.putExtra("json",Constants.CONTEXTS_JG);
//noticeIntent.putExtra("alert",Constants.MESSAGE_JG);
//context.sendBroadcast(noticeIntent);

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (TextUtils.equals(action, "com.aibabel.menu.msg")) {
            String json = intent.getStringExtra("json");
            String title = intent.getStringExtra("title");
            PushMessageBean bean = FastJsonUtil.changeJsonToBean(json, PushMessageBean.class);
            if(TextUtils.equals(bean.getLevel(),"1")){
                Intent noticeIntent = new Intent();
                noticeIntent.putExtra("",json);
                ResidentNotificationHelper.sendResidentNotice(context, "", "", noticeIntent);
            }else  if(TextUtils.equals(bean.getLevel(),"1")){
                //判定语音翻译，小秘书，拍照翻译是否在前台处理显示通知

                if(DetectUtil.isAppInForeground(context,"com.aibabel.translate")||(DetectUtil.isAppInForeground(context,"com.aibabel.speech"))){

                }

            }

        }

        String json = intent.getStringExtra("json");
        Log.e("NoticeBroadcastReceiver", json.toString());
        List<PushBean> list = FastJsonUtil.changeJsonToList(json, PushBean.class);
        if (null != list && list.size() > 0) {

            /*1.0.1版本不上*/
            if (TextUtils.equals(CommonUtils.getLocal(context), "zh_CN")) {

            }



        }


    }




}
