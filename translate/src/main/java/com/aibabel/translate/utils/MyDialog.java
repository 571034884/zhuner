package com.aibabel.translate.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;

import com.aibabel.translate.R;
import com.jiangyy.easydialog.OtherDialog;

public class MyDialog {

    static OtherDialog.Builder builder;

    public static void showDialog(final Context context) {
        if (null != builder) {
            builder.dismiss();
        }
        builder = new OtherDialog.Builder(context);
        builder.setGravity(Gravity.CENTER)
                .setContentView(R.layout.mydialog)
                .setText(R.id.tv_wenzi, context.getString(R.string.dialog_content))
                .setCancelable(false)
                .setOnClickListener(R.id.tv_lianwang, context.getString(R.string.conn_net), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ComponentName component = new ComponentName("com.gomtel.settings", "com.gomtel.settings.SettingsActivity");
                        Intent intent = new Intent();
                        intent.putExtra("key", "toWiFi");
                        intent.setComponent(component);
                        context.startActivity(intent);
                    }
                }).setOnClickListener(R.id.tv_jixu, context.getString(R.string.cont_use), null).show();
    }


    public static void dismiss() {
        if (null != builder) {
            builder.dismiss();
        }
    }

}
