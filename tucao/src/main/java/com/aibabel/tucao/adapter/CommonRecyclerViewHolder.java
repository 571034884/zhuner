package com.aibabel.tucao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by SunSH on 2018/2/5.
 */

public class CommonRecyclerViewHolder extends RecyclerView.ViewHolder implements View
        .OnClickListener, View.OnLongClickListener {

    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;
    private CommomRecyclerAdapter.OnItemClickListener itemClickListener = null;
    private CommomRecyclerAdapter.OnItemLongClickListener itemLongClickListener = null;

    public CommonRecyclerViewHolder(Context context, View itemView, ViewGroup parent,
                                    CommomRecyclerAdapter.OnItemClickListener itemClickListener,
                                    CommomRecyclerAdapter
                                            .OnItemLongClickListener
                                            itemLongClickListener) {
        super(itemView);
        this.mContext = context;
        this.mConvertView = itemView;
        this.mViews = new SparseArray<View>();
        this.itemClickListener = itemClickListener;
        this.itemLongClickListener = itemLongClickListener;
        mConvertView.setOnClickListener(this);
        mConvertView.setOnLongClickListener(this);
    }

    public static RecyclerView.ViewHolder getViewHolder(Context context, ViewGroup parent, int
            layoutId, CommomRecyclerAdapter.OnItemClickListener itemClickListener,
                                                        CommomRecyclerAdapter
                                                                .OnItemLongClickListener
                                                                itemLongClickListener) {

        View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        RecyclerView.ViewHolder holder = new CommonRecyclerViewHolder(context, itemView, parent,
                itemClickListener, itemLongClickListener);

        return holder;
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 通用方法：设置文本
     *
     * @param viewId
     * @param text
     * @return
     */
    public RecyclerView.ViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 通用方法：设置图片
     *
     * @param viewId
     * @param resId
     * @return
     */
    public RecyclerView.ViewHolder setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    /**
     * 通用方法：在设置子项中控件的点击事件
     *
     * @param viewId
     * @param listener
     * @return
     */
    public RecyclerView.ViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    /**
     * 整个子项点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        if (itemClickListener != null) {
            itemClickListener.onItemClick(this, getAdapterPosition());
        }
    }

    /**
     * 整个子项长按事件
     *
     * @param view
     */
    @Override
    public boolean onLongClick(View view) {
        if (itemLongClickListener != null) {
            itemLongClickListener.onItemLongClick(this, getAdapterPosition());
        }
        return true;
    }
}
