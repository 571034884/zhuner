package com.aibabel.locationservice.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.aibabel.locationservice.utils.FlowUtil;

/**
 * 作者：wuqinghua_fyt on 2019/2/19 9:26
 * 功能：
 * 版本：1.0
 * 接受 系统给发送的 卡切换的广播
 */

public class CardBroadcastReceiver extends BroadcastReceiver {


    private static Switch_Card listener;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("LocationService", "========切卡广播监听到了=======");
        try {
            //切换卡后不能立即去判定网络是否可用，因为不一定能有网
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (FlowUtil.isMobileEnabled(context)) {
            BootBroadcastReceiver.CARD_TYPE = FlowUtil.getDefaultDataSubId(context);
            if (BootBroadcastReceiver.LAST_CARD_TYPE != BootBroadcastReceiver.CARD_TYPE) {
                Log.e("LocationService", "================和上次不一样================");
                BootBroadcastReceiver.LAST_CARD_TYPE = BootBroadcastReceiver.CARD_TYPE;
                if (null != listener) {
                    Log.e("LocationService", "========开始切卡=======");
                    listener.switch_Card();
                }
            }

        }
    }

    public static void setSwitch_card(Switch_Card switch_card) {
        listener = switch_card;
    }

    public interface Switch_Card {
        void switch_Card();
    }
}
