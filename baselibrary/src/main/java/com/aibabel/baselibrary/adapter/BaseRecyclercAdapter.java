package com.aibabel.baselibrary.adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aibabel.baselibrary.R;
import com.aibabel.baselibrary.http.BaseBean;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder;
import com.zhouyou.recyclerview.adapter.HelperStateRecyclerViewAdapter;

import java.util.List;

/**
 * 作者：SunSH on 2018/11/30 18:08
 * 功能：
 * 版本：1.0
 */
public abstract class BaseRecyclercAdapter<T> extends HelperStateRecyclerViewAdapter<T> {
    TextView tvError;
    ImageView ivLoading;
    private AnimationDrawable animationDrawable;
    private boolean isOpen = false;
    OnErrorClickListener listener;

    public interface OnErrorClickListener {
        void requestAgain();
    }

    public BaseRecyclercAdapter(Context context, int... layoutId) {
        super(context, layoutId);
    }

    public BaseRecyclercAdapter(List data, Context context, int... layoutId) {
        super(data, context, layoutId);
    }

    @Override
    public View getEmptyView(ViewGroup parent) {
        return mLInflater.inflate(R.layout.adapter_empty_layout, parent, false);
    }

    @Override
    public View getErrorView(ViewGroup parent) {
        return mLInflater.inflate(R.layout.adapter_error_layout, parent, false);
    }

    @Override
    public View getLoadingView(ViewGroup parent) {
        return mLInflater.inflate(R.layout.adapter_loading_layouts, parent, false);
    }

    public void onBindErrorViewHolder(HelperRecyclerViewHolder holder) {
        tvError = holder.getView(R.id.tvError);
        tvError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) listener.requestAgain();
            }
        });
    }

    public void onBindLoadingViewHolder(HelperRecyclerViewHolder holder) {
        ivLoading = holder.getView(R.id.ivLoading);
        animationDrawable = (AnimationDrawable) ivLoading.getDrawable();
        if (isOpen) animationDrawable.start();
        else animationDrawable.stop();
    }

    protected void starAnimation() {
        isOpen = true;
    }

    protected void stopAnimation() {
        isOpen = false;
    }

    @Override
    public void setState(int state) {
        super.setState(state);
        if (state == HelperStateRecyclerViewAdapter.STATE_LOADING) starAnimation();
        else stopAnimation();
    }

    public void setOnErrorClickListener(OnErrorClickListener listener) {
        this.listener = listener;
    }
}
