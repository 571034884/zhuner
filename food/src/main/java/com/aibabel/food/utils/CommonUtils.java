package com.aibabel.food.utils;

import android.content.Context;
import android.widget.ImageView;

import com.aibabel.baselibrary.imageloader.ImageLoader;
import com.aibabel.food.R;
import com.aibabel.food.base.Constant;
import com.bumptech.glide.Glide;

/**
 * 作者：SunSH on 2018/12/5 11:41
 * 功能：
 * 版本：1.0
 */
public class CommonUtils {

    public static void setPicture1x1(String url, ImageView view) {
        ImageLoader.getInstance().load(url).placeholder(Constant.LOADING_1X1).error(Constant
                .LOAD_FAIL_1X1).into(view);
    }

    public static void setPictureCircle1x1(String url, ImageView view) {
        ImageLoader.getInstance().load(url).placeholder(Constant.LOADING_CIRCLE_1X1).error
                (Constant.LOAD_FAIL_CIRCLE_1X1).into(view);
    }

    public static void setPicture540x280(String url, ImageView view) {
        ImageLoader.getInstance().load(url).placeholder(Constant.LOADING_540X280).error(Constant
                .LOAD_FAIL_540X280).into(view);
    }

    public static void setPicture(String url, int placeholder, int error, ImageView view) {
        ImageLoader.getInstance().load(url).placeholder(placeholder).error(error).into(view);
    }
}
