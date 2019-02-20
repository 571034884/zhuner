package com.aibabel.food.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 作者：SunSH on 2019/1/17 13:47
 * 功能：
 * 版本：1.0
 */
public class MyItemDecoration extends RecyclerView.ItemDecoration {
    int mSpace;

    /**
     * @param outRect 边界
     * @param view    recyclerView ItemView
     * @param parent  recyclerView
     * @param state   recycler 内部数据管理
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, 0, mSpace);
    }

    public MyItemDecoration(int space) {
        this.mSpace = space;
    }
}
