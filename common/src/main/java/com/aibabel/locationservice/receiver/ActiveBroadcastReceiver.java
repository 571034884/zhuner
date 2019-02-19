package com.aibabel.locationservice.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.aibabel.locationservice.app.BaseApplication;
import com.aibabel.locationservice.service.LocationService;
import com.aibabel.locationservice.utils.SharePrefUtil;
/**
 *==========================================================================================
 * @Author： 张文颖
 *
 * @Date：2018/11/9
 *
 * @Desc：租赁设备激活、到期广播
 *==========================================================================================
 */
public class ActiveBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (TextUtils.equals(action, "com.android.zhuner")) {//收到激活、到期广播
            String isActive = intent.getStringExtra("Zhuner_devices");
            if (TextUtils.equals(isActive, "time_start_L")) {//判定是激活
                SharePrefUtil.saveBoolean(BaseApplication.CONTEXT, "isActive", true);
            } else {//到期
                SharePrefUtil.saveBoolean(BaseApplication.CONTEXT, "isActive", false);
            }
        }


    }
}
