package com.aibabel.travel.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.aibabel.travel.R;
import com.aibabel.travel.activity.SpotDetailActivity;
import com.jiangyy.easydialog.OtherDialog;

import static android.content.Context.ACTIVITY_SERVICE;

public class MyDialog {


    private static Notice notice;

    public static void showDialog(final Context context, String content) {


        new OtherDialog.Builder(context)
                .setGravity(Gravity.CENTER)
                .setContentView(R.layout.commondialog)
                .setText(R.id.tv_wenzi, content)
                .setCancelable(false)
                .setOnClickListener(R.id.tv_lianwang, context.getString(R.string.conn_net), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try {
                            Intent LaunchIntent = context.getPackageManager().getLaunchIntentForPackage("com.aibabel.download.offline");
                            context.startActivity(LaunchIntent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                }).setOnClickListener(R.id.tv_jixu, context.getString(R.string.cont_use), null).show();
    }


//    public static void showDialog(final Context context,String commit){
//        new OtherDialog.Builder(context)
//                .setGravity(Gravity.CENTER)
//                .setContentView(R.layout.commondialog)
//                .setText(R.id.tv_wenzi, context.getString(R.string.download))
//                .setCancelable(false)
//                .setOnClickListener(R.id.tv_lianwang, commit, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        try{
//                            Intent LaunchIntent = context.getPackageManager().getLaunchIntentForPackage("com.aibabel.download.offline");
//                            context.startActivity(LaunchIntent);
//                            }catch(Exception e){
//                            e.printStackTrace();
//                            }
//
//
//
//                    }
//                }).setOnClickListener(R.id.tv_jixu, context.getString(R.string.cont_use), null).show();
//    }


    /*定位提醒跳转播放页*/

    public static void showDialogPlay(final Context context, final String content, final String json) {

        new OtherDialog.Builder(context)
                .setGravity(Gravity.CENTER)
                .setContentView(R.layout.commondialog)
                .setText(R.id.tv_wenzi, content)
                .setCancelable(false)
                .setOnClickListener(R.id.tv_lianwang, context.getString(R.string.conn_net), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try {
                            ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
                            ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
                            Log.e("cn", cn.getClassName());
                            if (cn.getClassName().contains("com.aibabel.travel.activity.SpotDetailActivity")) {
//                                spotDetailActivity.upDate(json);
                                notice.noticePlay(json);
                            } else {
                                Intent intent = new Intent(context, SpotDetailActivity.class);
                                intent.putExtra("position", 0);
                                intent.putExtra("list", json);
                                context.startActivity(intent);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                }).setOnClickListener(R.id.tv_jixu, context.getString(R.string.cont_use), null).show();




//        My_Dialog.Builder builderDialog = new My_Dialog.Builder(context);

//        new OtherDialog.Builder(context)
//                .setGravity(Gravity.CENTER)
//                .setContentView(R.layout.commondialog)
//                .setText(R.id.tv_wenzi, content)
//                .setCancelable(false)
//                .setOnClickListener(R.id.tv_lianwang, context.getString(R.string.conn_net), new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        try {
//
//                            ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
//                            ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
//                            Log.e("cn", cn.getClassName());
//                            if (cn.getClassName().contains("com.aibabel.travel.activity.SpotDetailActivity")) {
////                                spotDetailActivity.upDate(json);
//                                notice.noticePlay(json);
//                            } else {
//                                Intent intent = new Intent(context, SpotDetailActivity.class);
//                                intent.putExtra("position", 0);
//                                intent.putExtra("list", json);
//                                context.startActivity(intent);
//                            }
////                            builderDialog.dismiss();
//                            /*Intent intent = new Intent(context, SpotDetailActivity.class);
//                            intent.putExtra("position", 0);
//                            intent.putExtra("list", json);
//                            context.startActivity(intent);*/
//                        } catch (Exception e) {
//                            e.printStackTrace();
//
//                        }
//
//
//                    }
//                }).setOnClickListener(R.id.tv_jixu, context.getString(R.string.cont_use), null).show();


//        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
//        View view = View.inflate(context, R.layout.commondialog, null);
//
//        dialogBuilder.setView(view);
//        TextView tvJiXu = view.findViewById(R.id.tv_jixu);
//        TextView tvLianWang = view.findViewById(R.id.tv_lianwang);
//        TextView tvWenZi = view.findViewById(R.id.tv_wenzi);
//        tvWenZi.setText(content);
//
//
////            dialogBuilder.setTitle("提示");
////            dialogBuilder.setMessage("这是在BroadcastReceiver弹出的对话框。");
//        dialogBuilder.setCancelable(false);
////            dialogBuilder.setPositiveButton("确定", null);
//        final AlertDialog alertDialog = dialogBuilder.create();
//        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//        alertDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
//
//        alertDialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
//        WindowManager.LayoutParams lp = alertDialog.getWindow().getAttributes();
//        // 设置背景层透明度
//        lp.dimAmount = 0.0f;
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        alertDialog.getWindow().setAttributes(lp);
//        alertDialog.show();
//        tvJiXu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (alertDialog != null)
//                    alertDialog.dismiss();
//            }
//        });
//        tvLianWang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//
//                    ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
//                    ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
//                    Log.e("cn", cn.getClassName());
//                    if (cn.getClassName().contains("com.aibabel.travel.activity.SpotDetailActivity")) {
////                                spotDetailActivity.upDate(json);
//                        notice.noticePlay(json);
//
//                    } else {
//                        Intent intent = new Intent(context, SpotDetailActivity.class);
//                        intent.putExtra("position", 0);
//                        intent.putExtra("list", json);
//                        context.startActivity(intent);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//
//                }
//
//            }
//        });
    }

    public interface Notice {
        void noticePlay(String json);
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }

    /*public void setNotice(Notice notice) {
        this.snotice = notice;
    }*/





    /*public interface NextPlay{
        void nextplay();
    }
    public void setNextPlay(NextPlay nextPlay) {
        this.nextPlay = nextPlay;
    }*/
}
