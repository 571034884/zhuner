package com.aibabel.locationservice.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.aibabel.locationservice.service.LocationService;
import com.aibabel.locationservice.utils.CommonUtils;
/**
 *==========================================================================================
 * @Author：CreateBy 张文颖
 *
 * @Date：2018/11/9
 *
 * @Desc：开机广播，监听开机广播启动定位服务
 *==========================================================================================
 */
public class BootBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "BootBroadcastReceiver";
    private static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_BOOT) && !CommonUtils.isServiceWork(context,"com.aibabel.locationservice.service.LocationService")) { //开机启动完成后，要做的事情
            Intent it=new Intent(context, LocationService.class);
            context.startService(it);
            Log.e("开机启动：","------------------------");
        }
    }
}
