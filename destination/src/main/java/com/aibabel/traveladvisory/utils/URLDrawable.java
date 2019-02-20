package com.aibabel.traveladvisory.utils;

import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * 作者：SunSH on 2018/7/3 11:42
 * 功能：
 * 版本：1.0
 */
public class URLDrawable extends BitmapDrawable {
    protected Drawable drawable;
    @Override
    public void draw(Canvas canvas) {

        if (drawable != null) {
            drawable.draw(canvas);
        }
    }
}
