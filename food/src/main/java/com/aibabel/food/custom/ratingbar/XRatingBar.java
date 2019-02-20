package com.aibabel.food.custom.ratingbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aibabel.food.R;

/**
 * 作者：SunSH on 2018/12/5 18:03
 * 功能：
 * 版本：1.0
 */
public class XRatingBar extends LinearLayout {

    private int mNumStars;// 共有几个星星
    private float mRating;
    private float mDividerWidth;
    private IRatingView mIRatingView;

    public XRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.XRatingBar);
        mNumStars = typedArray.getInt(R.styleable.XRatingBar_numStars, 5);
        mRating = typedArray.getFloat(R.styleable.XRatingBar_rating, 0f);
        mDividerWidth = typedArray.getDimension(R.styleable.XRatingBar_dividerWidth, 0);
        String ratingViewClassName = typedArray.getString(R.styleable.XRatingBar_ratingViewClass);
        try {
            Class<?> netErrorClass = Class.forName(ratingViewClassName);
            mIRatingView = (IRatingView) netErrorClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initView();
    }

    public void setRatingView(IRatingView ratingView) {
        this.mIRatingView = ratingView;
        initView();
    }

    public int getNumStars() {
        return mNumStars;
    }

    public void setNumStars(int numStars) {
        this.mNumStars = numStars;
        initView();
    }

    public float getRating() {
        return mRating;
    }

    public void setRating(float rating) {
        if (rating > mNumStars) {
            return;
        }
        this.mRating = rating;
        initView();
    }


    private void initView() {
        removeAllViews();
        if (mIRatingView == null) {
            return;
        }
        for (int i = 0; i < mNumStars; i++) {
            ImageView ratingView = mIRatingView.getRatingView(getContext(), mNumStars, i);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins((int) mDividerWidth, 0, 0, 0);
            ratingView.setLayoutParams(params);
            int resid = mIRatingView.getStateRes(i, mIRatingView.getCurrentState(mRating, mNumStars, i));
            if (resid != -1) {
                ratingView.setImageResource(resid);
            }
            addView(ratingView);

            final int finalI = i + 1;
            ratingView.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (!isEnabled()) {
                        return false;
                    }
                    if (event.getAction() != MotionEvent.ACTION_UP) {
                        return true;
                    }
                    if (getOrientation() == LinearLayout.HORIZONTAL) {
                        if (event.getX() < v.getWidth() / 2f) {
                            mRating = finalI - 0.5f;
                        } else {
                            mRating = finalI;
                        }
                    } else {
                        if (event.getY() < v.getHeight() / 2f) {
                            mRating = finalI - 0.5f;
                        } else {
                            mRating = finalI;
                        }
                    }
                    resetRatingViewRes();
                    if (mOnRatingChangeListener != null) {
                        mOnRatingChangeListener.onChange(mRating, mNumStars);
                    }
                    return true;
                }
            });
        }

    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
    }

    private void resetRatingViewRes() {
        for (int i = 0; i < mNumStars; i++) {
            ImageView imageView = (ImageView) getChildAt(i);
            int resid = mIRatingView.getStateRes(i, mIRatingView.getCurrentState(mRating, mNumStars, i));
            if (resid != -1) {
                imageView.setImageResource(resid);
            }
        }
    }

    private OnRatingChangeListener mOnRatingChangeListener;

    public OnRatingChangeListener getOnRatingChangeListener() {
        return mOnRatingChangeListener;
    }

    public void setOnRatingChangeListener(OnRatingChangeListener onRatingChangeListener) {
        mOnRatingChangeListener = onRatingChangeListener;
    }

    public interface OnRatingChangeListener {

        void onChange(float rating, int numStars);
    }
}
