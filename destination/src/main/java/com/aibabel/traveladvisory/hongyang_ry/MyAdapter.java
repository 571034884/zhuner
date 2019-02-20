package com.aibabel.traveladvisory.hongyang_ry;

import android.content.Context;
import android.view.LayoutInflater;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * 作者：SunSH on 2018/6/28 15:10
 * 功能：
 * 版本：1.0
 */
public abstract class MyAdapter<T> extends MultiItemTypeAdapter<T> {
    protected Context mContext;
    protected int mLayoutId;
    protected LayoutInflater mInflater;

    public MyAdapter(final Context context, final int layoutId, List<T> datas) {
        super(context, datas);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position) {
                MyAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(ViewHolder holder, T t, int position);

    /**
     * 当RecycleView数据发生变化时,调用此方法来更新RecycleView
     *
     * @param list
     */
    public void updateData(List<T> list) {
        this.mDatas = list;
        notifyDataSetChanged();
    }
}
