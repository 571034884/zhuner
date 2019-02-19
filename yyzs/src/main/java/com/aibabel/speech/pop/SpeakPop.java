package com.aibabel.speech.pop;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.aibabel.speech.R;

public class SpeakPop {

    private PopupWindow pop;
    private ImageView imageView;
    private AnimationDrawable animationDrawable;

    public SpeakPop(Context context) {

        View contentView = LayoutInflater.from(context).inflate(R.layout.pop_speak, null);
        imageView=contentView.findViewById(R.id.pop_speak_img);

         pop = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        // TODO: 2016/5/17 设置背景颜色
        pop.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        // TODO: 2016/5/17 设置可以获取焦点
        pop.setFocusable(false);
        // TODO: 2016/5/17 设置可以触摸弹出框以外的区域
        pop.setOutsideTouchable(true);

        setXml2FrameAnim1();

    }

    /**
     * 通过XML添加帧动画方法一
     */
    private void setXml2FrameAnim1() {

        // 把动画资源设置为imageView的背景,也可直接在XML里面设置
        imageView.setBackgroundResource(R.drawable.amin_speak);
        animationDrawable=new AnimationDrawable();
        animationDrawable = (AnimationDrawable) imageView.getBackground();
    }


    public void show(View view) {
        try {
            if (pop != null && !pop.isShowing()) {
                animationDrawable.start();
                pop.showAtLocation(view, Gravity.CENTER, 0, 0);

            }
        } catch (Exception e) {

        }
    }


    public void close() {
        try {

            if (pop != null &&pop.isShowing()) {
                animationDrawable.stop();
                pop.dismiss();

            }




        } catch (Exception e) {

        }
    }

    public void destory() {
        try {
            animationDrawable.stop();
            pop.dismiss();
            animationDrawable=null;
            pop=null;
        } catch (Exception e) {

        }

    }
}
