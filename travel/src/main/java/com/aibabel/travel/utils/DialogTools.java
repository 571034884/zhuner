package com.aibabel.travel.utils;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aibabel.travel.R;

/**
 * 对话框工具类
 *
 * @version V1.0
 *
 * @date
 */
public class DialogTools {

    private static Dialog dialog;

    /**
     * 加载对话框
     *
     * @param context
     * @param msg
     */
    public static void createLoadDialog(Context context, String msg) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(msg);
        dialog.setIndeterminate(false);
        dialog.setCancelable(false);
        dialog.show();
    }

    public static void showProgressDialog(Context context) {
        showProgressDialog(context);
    }

    public static void showProgressDialog(Context context,View view) {

        // main.xml中的ImageView
        ImageView spaceshipImage = (ImageView) view.findViewById(R.id.imgDialog);
        // 加载动画

        AnimationDrawable animationDrawable = (AnimationDrawable) spaceshipImage.getBackground();
        animationDrawable.start();
        // 使用ImageView显示动画

        if(null!=dialog){
        	dialog.dismiss();
        	dialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
        }else{
        	dialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
        }
        dialog.setCancelable(false);// 不可以用“返回键”取消
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));// 设置布局
        dialog.show();

    }
    
    
//    public static void showProgress(Context context, String msg) {
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View v = inflater.inflate(R.layout.loading_dialog, null); // 得到加载view
////        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view); // 加载布局
//        // main.xml中的ImageView
//        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.imgDialog);
////        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView); // 提示文字
//        // 加载动画
//
//        AnimationDrawable animationDrawable = (AnimationDrawable) spaceshipImage.getBackground();
//        animationDrawable.start();
//        // 使用ImageView显示动画
////        tipTextView.setText(msg);// 设置加载信息
//
//        if(null!=dialog){
//        	dialog.dismiss();
//        	dialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
//        }else{
//        	dialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
//        }
//        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
//        dialog.setCancelable(false);// 不可以用“返回键”取消
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setContentView(v, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
//        dialog.show();
//
//    }
//
    


    public static void closeProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * 当判断当前手机没有网络时使用
     *
     * @param context
     */
    public static void showNoNetWork(final Context context) {
        Builder builder = new Builder(context);
        builder.setIcon(android.R.drawable.ic_dialog_info)//
                .setTitle("温馨提示")//
                .setMessage("当前无网络").setPositiveButton("设置", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // 跳转到系统的网络设置界面
                if (android.os.Build.VERSION.SDK_INT > 10) {
                    context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                } else {
                    context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                }

            }
        }).setNegativeButton("知道了 ", null).show();
    }






    public void closeDialog(Dialog dialog) {
        if (null != dialog) {
            dialog.dismiss();
        }
    }

}