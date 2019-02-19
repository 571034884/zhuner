package com.aibabel.locationservice.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.aibabel.locationservice.utils.CommonUtils;
/**
 *==========================================================================================
 * @Author： 张文颖
 *
 * @Date：2018/11/9
 *
 * @Desc：通知广播，接受到系统广播，弹出通知栏
 *==========================================================================================
 */
public class NoticeBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        String msg = intent.getStringExtra("msg");
        if (!TextUtils.isEmpty(msg) && TextUtils.equals(CommonUtils.getLocal(), "zh_CN")) {
            Intent intent1 = new Intent();
//                intent1.putExtra("msg", msg);
            ResidentNotificationHelper.sendResidentNotice(context, "", msg, intent1);
        }


    }
}
