package com.aibabel.translate.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * 作者 : 张文颖
 * 时间: 2018/3/22
 * 描述: 自定义RecyclerView，实现要setEmptyView
 */


public class MyRecyclerView extends RecyclerView {


//    float y1 = 0;
//    float y2 = 0;
//    CallBack callBack;

    private View emptyView;
    private static final String TAG = "MyRecyclerView";

    final private AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkIfEmpty();
        }
    };

    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }



    private void checkIfEmpty() {
        if (emptyView != null && getAdapter() != null) {
            final boolean emptyViewVisible = getAdapter().getItemCount() == 0;
            emptyView.setVisibility(emptyViewVisible ? VISIBLE : GONE);
            setVisibility(emptyViewVisible ? GONE : VISIBLE);
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(observer);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }

        checkIfEmpty();
    }

    //设置没有内容时，提示用户的空布局
    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
        checkIfEmpty();
    }

//    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
//        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
//        super.onMeasure(widthMeasureSpec, expandSpec);
//    }


//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                y1 = event.getY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                y2 = event.getY();
//                if (y1 - y2 > 50) {
//                    //向上滑
////                    MyScrollView.STATE = 2;
////                    if (!MyScrollView.isFlowHead){
////                        callBack.top();
////                    }
////                    Log.e("isTop", "向上滑"+MyScrollView.isFlowHead);
//                } else if (y2 - y1 > 50) {
//                    //向下滑
////                    MyScrollView.STATE = 1;
////                    Log.e("isTop", "向下滑");
//                    if (computeVerticalScrollOffset() == 0) {
//                        callBack.top();
//                    }
//                }
//                break;
//        }
//        return super.onTouchEvent(event);
//    }
//
//    public void setCallBack(CallBack callBack) {
//        this.callBack = callBack;
//    }
//
//    public interface CallBack {
//        void top();
//    }


}

