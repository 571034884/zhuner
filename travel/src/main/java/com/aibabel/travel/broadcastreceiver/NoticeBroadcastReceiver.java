package com.aibabel.travel.broadcastreceiver;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.aibabel.travel.R;
import com.aibabel.travel.activity.InformationActivity;
import com.aibabel.travel.activity.WorldActivity;
import com.aibabel.travel.bean.PushBean;
import com.aibabel.travel.utils.CommonUtils;
import com.aibabel.travel.utils.FastJsonUtil;
import com.aibabel.travel.widgets.NoticeDialog;

import java.util.List;
import java.util.Locale;

public class NoticeBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        String json = intent.getStringExtra("data");
        Log.e("NoticeBroadcastReceiver", json.toString());
        List<PushBean> list = FastJsonUtil.changeJsonToList(json, PushBean.class);
        if (null != list && list.size() > 0) {

            // 获得广播发送的数据
//            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
//            View view = View.inflate(context, R.layout.notice_dialog, null);
//            dialogBuilder.setView(view);
////            dialogBuilder.setTitle("提示");
////            dialogBuilder.setMessage("这是在BroadcastReceiver弹出的对话框。");
//            dialogBuilder.setCancelable(false);
////            dialogBuilder.setPositiveButton("确定", null);
//            AlertDialog alertDialog = dialogBuilder.create();
//            alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//            alertDialog.getWindow().getAttributes().gravity = Gravity.TOP;
//
//            alertDialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
//            WindowManager.LayoutParams lp = alertDialog.getWindow().getAttributes();
//            // 设置背景层透明度
//            lp.dimAmount = 0.0f;
//            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//            alertDialog.getWindow().setAttributes(lp);
//            alertDialog.show();


            /*1.0.1版本不上*/
            if(TextUtils.equals(CommonUtils.getLocal(context),"zh_CN")){
                Intent intent1 = new Intent(context, InformationActivity.class);
                intent1.putExtra("json", json);
                ResidentNotificationHelper.sendResidentNotice(context, "", "", intent1);
            }


//            NoticeDialog noticeDialog = new NoticeDialog(context);
//            noticeDialog.show();
        }


    }
}
