package com.aibabel.surfinternet.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.aibabel.surfinternet.bean.Constans;


/**
 * 作者：wuqinghua_fyt on 2018/9/14 15:26
 * 功能：
 * 版本：1.0
 */
public class XuZuBroadcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {


        if (TextUtils.equals(Constans.PRO_VERSION_NUMBER,"L")){
            String time_start = intent.getStringExtra("Time_Start");
            String time_end =intent.getStringExtra("Time_End");
            String time_end_first =intent.getStringExtra("Time_End_First");
            Log.e("Time_End","===="+time_end);
            Log.e("Time_Start","===="+time_start);
            Log.e("Time_End_First","===="+time_end_first);
            if ((!TextUtils.equals(time_start, "") && null != time_start) && (!TextUtils.equals(time_end, "") && null != time_end)) {
                SharePrefUtil.saveString(context, "time_start", time_start);
                //时刻变化的结束时间
                SharePrefUtil.saveString(context, "time_end", time_end);
                //第一次的 结束时间
                SharePrefUtil.saveString(context, "time_end_first", time_end_first);
            }
        }

    }
}
