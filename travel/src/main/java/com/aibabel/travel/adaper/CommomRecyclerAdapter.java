package com.aibabel.travel.adaper;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SunSH on 2018/2/5.
 */

public abstract class CommomRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected Context mContext;
    private LayoutInflater mInflater;
    private OnItemClickListener itemClickListener = null;
    private OnItemLongClickListener itemLongClickListener = null;



    private int selectedPos = -1;
    private int oldPos = -1;
    /**
     * 数据利用泛型
     */
    protected List<T> mDatas;
    /**
     * 布局直接从构造里面传递
     */
    private int mLayoutId;

    public CommomRecyclerAdapter(Context mContext, List<T> mDatas, int mLayoutId,
                                 OnItemClickListener itemClickListener, OnItemLongClickListener
                                         itemLongClickListener) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas == null ? new ArrayList<T>() : mDatas;
        this.mLayoutId = mLayoutId;
        this.itemClickListener = itemClickListener;
        this.itemLongClickListener = itemLongClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = CommonRecyclerViewHolder.getViewHolder(mContext, parent, mLayoutId, itemClickListener, itemLongClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            convert((CommonRecyclerViewHolder) holder, mDatas.get(position), position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
//        if (payloads.isEmpty()) {
//            //payloads 为 空，说明是更新整个 ViewHolder
//            onBindViewHolder(holder, position);
//
//        } else {
//            LinearLayout ll_recy = ((CommonRecyclerViewHolder)holder).getView(R.id.ll_recy);
//            ll_recy.setBackgroundResource(R.drawable.bg_circ_rec_red);
//        }
//
//    }
//    public void refreshItem(int position) {
//        if (selectedPos != -1) {
//            oldPos  = selectedPos;
//        }
//        selectedPos = position;
//        if (oldPos != -1) {
//            notifyItemChanged(oldPos);
//        }
//        notifyItemChanged(selectedPos);
//    }


    /**
     * 暴露给用户进行操作
     *
     * @param holder   布局
     * @param t        数据
     * @param position 位置
     */
    public abstract void convert(CommonRecyclerViewHolder holder, T t, int position);

    @Override
    public int getItemCount() {
        if (mDatas != null)
            return mDatas.size();
        else {
            return 0;
        }
    }

    public Object getItem(int position) {
        return mDatas.get(position);
    }

    /**
     * 定义子项点击事件
     */
    public interface OnItemClickListener {
        void onItemClick(CommonRecyclerViewHolder holder, int postion);
    }

    /**
     * 定义子项长按事件
     */
    public interface OnItemLongClickListener {
        void onItemLongClick(CommonRecyclerViewHolder holder, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.itemLongClickListener = listener;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     */
    public List<T> getData() {
        return mDatas;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateData(List<T> list) {
        this.mDatas = list;
        notifyDataSetChanged();
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void addList(List<T> list) {
        if (null != list){
            mDatas.addAll(list);
            notifyDataSetChanged();
        }


    }







}
