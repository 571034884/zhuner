package com.aibabel.locationservice.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.aibabel.locationservice.service.LocationService;
import com.aibabel.locationservice.utils.CommonUtils;
import com.aibabel.locationservice.utils.FlowUtil;

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
    public static Integer CARD_TYPE = -1;
    public static Integer LAST_CARD_TYPE = -1;
    private static final String TAG = "BootBroadcastReceiver";
    private static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_BOOT) && !CommonUtils.isServiceWork(context,"com.aibabel.locationservice.service.LocationService")) { //开机启动完成后，要做的事情
            Intent it=new Intent(context, LocationService.class);
            context.startService(it);
            Log.e("开机启动：","------------------------");
        }
        //开机获取那张卡在使用套餐
        if (intent.getAction().equals(ACTION_BOOT)){
            if (FlowUtil.isMobileEnabled(context)){
                CARD_TYPE = FlowUtil.getDefaultDataSubId(context);
                LAST_CARD_TYPE = CARD_TYPE ;
            }

        }
    }
}
