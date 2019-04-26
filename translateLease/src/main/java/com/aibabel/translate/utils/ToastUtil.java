package com.aibabel.translate.utils;

import android.content.Context;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aibabel.translate.app.BaseApplication;

/**
 * Created by SunSH on 2018/3/14.
 * <p>
 * Toast统一管理类
 * 解决问题：
 * 1)需要弹出一个新的Toast时，上一个Toast还没有显示完
 * 2)可能重复弹出相同的信息
 * 3)Toast具体有哪些用法不是很熟悉，用到时导出去找
 * 4)app退出去了，Toast还在弹
 */

public class ToastUtil {

    /**
     * 默认显示
     */
    private static boolean isShow = true;
    /**
     * 全局唯一的Toast
     */
    private static Toast mToast = null;

    /**
     * private控制不应该被实例化
     */
    private ToastUtil() {
        throw new UnsupportedOperationException("不能被实例化");
    }

    /**
     * 全局控制是否显示Toast
     *
     * @param isShowToast
     */
    public static void controlShow(boolean isShowToast) {
        isShow = isShowToast;
    }

    /**
     * 取消Toast显示
     */
    public void cancelToast() {
        if (isShow && mToast != null) {
            mToast.cancel();
        }
    }

    /**
     * 短时间显示Toast
     *
     * @param
     * @param message
     */
    public static void showShort(CharSequence message) {

            if (isShow) {
                if (mToast == null) {
                    mToast = Toast.makeText(BaseApplication.getContext(), message, Toast.LENGTH_SHORT);
                } else {
                    mToast.setText(message);
                }
                mToast.show();
            }

    }

    /**
     * 短时间显示Toast
     *
     * @param
     * @param resId 资源ID:getResources().getString(R.string.xxxxxx);
     */
    public static void showShort(int resId) {
        if (isShow) {
            if (mToast == null) {
                mToast = Toast.makeText(BaseApplication.getContext(), resId, Toast.LENGTH_SHORT);
            } else {
                mToast.setText(resId);
            }
            mToast.show();
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(CharSequence message) {
        if (isShow) {
            if (mToast == null) {
                mToast = Toast.makeText(BaseApplication.getContext(), message, Toast.LENGTH_LONG);
            } else {
                mToast.setText(message);
            }
            mToast.show();
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param
     * @param resId 资源ID:getResources().getString(R.string.xxxxxx);
     */
    public static void showLong(int resId) {
        if (isShow) {
            if (mToast == null) {
                mToast = Toast.makeText(BaseApplication.getContext(), resId, Toast.LENGTH_LONG);
            } else {
                mToast.setText(resId);
            }
            mToast.show();
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param message
     * @param duration 单位:毫秒
     */
    public static void show(CharSequence message, int duration) {
        if (isShow) {
            if (mToast == null) {
                mToast = Toast.makeText(BaseApplication.getContext(), message, duration);
            } else {
                mToast.setText(message);
            }
            mToast.show();
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param resId    资源ID:getResources().getString(R.string.xxxxxx);
     * @param duration 单位:毫秒
     */
    public static void show(int resId, int duration) {
        if (isShow) {
            if (mToast == null) {
                mToast = Toast.makeText(BaseApplication.getContext(), resId, duration);
            } else {
                mToast.setText(resId);
            }
            mToast.show();
        }
    }

    /**
     * 自定义Toast的View
     *
     * @param message
     * @param duration 单位:毫秒
     * @param view     显示自己的View
     */
    public static void customToastView(CharSequence message, int duration, View
            view) {
        if (isShow) {
            if (mToast == null) {
                mToast = Toast.makeText(BaseApplication.getContext(), message, duration);
            } else {
                mToast.setText(message);
            }
            if (view != null) {
                mToast.setView(view);
            }
            mToast.show();
        }
    }

    /**
     * 自定义Toast的位置
     *
     * @param message
     * @param duration 单位:毫秒
     * @param gravity
     * @param xOffset
     * @param yOffset
     */
    public static void customToastGravity(CharSequence message, int duration,
                                          int gravity, int xOffset, int yOffset) {
        if (isShow) {
            if (mToast == null) {
                mToast = Toast.makeText(BaseApplication.getContext(), message, duration);
            } else {
                mToast.setText(message);
            }
            mToast.setGravity(gravity, xOffset, yOffset);
            mToast.show();
        }
    }


}
