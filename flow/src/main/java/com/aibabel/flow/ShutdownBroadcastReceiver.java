package com.aibabel.flow;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.TrafficStats;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Intent.ACTION_SHUTDOWN;

/**
 * 作者：wuqinghua_fyt on 2018/11/2 9:56
 * 功能：
 * 版本：1.0
 */
public class ShutdownBroadcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_SHUTDOWN)) {
            Log.e("guanji3", "关机监听");

            long now_flow_last = SharePrefUtil.getLong(context, "now_flow", 0);
            //开机到现在的流量（重启之后）
            long mobileRxBytes = TrafficStats.getMobileRxBytes();
            long mobileTxBytes = TrafficStats.getMobileTxBytes();
            //开机到现在一共的流量
            long now_flow = mobileRxBytes + mobileTxBytes;
            long now_flow_num = now_flow + now_flow_last;


            SharePrefUtil.saveLong(context, "now_flow", now_flow_num);

            SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
            Date data = new Date();
            String str_date = sdf.format(data);


            SharePrefUtil.saveString(context,"date",str_date);
            Log.e("guanjiqiang", now_flow_num + "");
        }

    }


}
