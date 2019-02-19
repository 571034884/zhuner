package com.aibabel.translate.utils;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class AnimationUtils {

//    private static  animator;

    /*****
     * 属性动画
     * @param a
     * @param b
     * @param view
     */
    public static void start(final View view, float a, float b, int time, final TextView textView, final String text) {

       final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ValueAnimator animator = ValueAnimator.ofFloat(a, b);
        animator.setDuration(time);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                params.height = (int) value;
                view.setLayoutParams(params);
            }
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (textView != null && text != null)
                    textView.setText(text);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animator.start();
    }







}
