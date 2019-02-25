package com.example.root.testhuaping;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by root on 18-6-16.
 */

public class BootCompletedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
        {
            //Intent newIntent = new Intent(context, MainActivity.class);
            Log.v("bbbbbbbbbbbbbbbbb","This is Error.");
           // newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  //注意，必须添加这个标记，否则启动会失败
            //context.startActivity(newIntent);
        }
    }
}
