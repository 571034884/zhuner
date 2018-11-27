package com.aibabel.alliedclock.adapter;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 作者：SunSH on 2018/6/5 20:08
 * 功能：
 * 版本：1.0
 */
public class CommonItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDivider;

    public CommonItemDecoration(Drawable divider) {
        // 利用Drawable绘制分割线
        mDivider = divider;
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        // 计算需要绘制的区域
        Rect rect = new Rect();
        rect.left = parent.getPaddingLeft() + 20;//数字为左右边距
        rect.right = parent.getWidth() - parent.getPaddingRight() - 20;
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            rect.top = childView.getBottom();
            rect.bottom = rect.top + mDivider.getIntrinsicHeight();
            // 直接利用Canvas去绘制
            mDivider.setBounds(rect);
            mDivider.draw(canvas);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State
            state) {
        // 在每个子View的下面留出来画分割线
        outRect.bottom += mDivider.getIntrinsicHeight();
    }
}
