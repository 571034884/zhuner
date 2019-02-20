package com.aibabel.food.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 作者：SunSH on 2018/12/29 11:41
 * 功能：
 * 版本：1.0
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private int columnCount;

    public SpaceItemDecoration(int space,int columnCount) {
        this.space = space;
        this.columnCount = columnCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //不是第一个的格子都设一个左边和底部的间距
        outRect.left = space;
        outRect.bottom = space;
        //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
        if (parent.getChildLayoutPosition(view) % columnCount == 0) {
            outRect.left = 0;
        }
    }
}
