package com.aibabel.speech.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class MyAdapter1<T> extends BaseAdapter {


    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    private int layoutId; //把布局单独提取出来

    public MyAdapter1(Context context, List<T> datas, int layoutId) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = datas;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //初始化ViewHolder
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent, layoutId, position);//layoutId就是单个item的布局
        convert(holder, getItem(position));
        return holder.getConvertView();
    }

    //将convert方法公布出去
    public abstract void convert(ViewHolder holder, T t);


    //刷新数据
    public void fresh(List<T> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    //加载数据
    public void load(List<T> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }
    //加载数据
    public void loadoOne(T t) {
        mDatas.add(t);
        notifyDataSetChanged();
    }
}
