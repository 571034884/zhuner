package com.aibabel.map.views;

import android.animation.ArgbEvaluator;
import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;

import com.aibabel.map.R;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.GroundOverlay;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.mapapi.model.LatLng;


public class MapRipple {
    private BaiduMap mBaiduMap;
    private LatLng mLatLng, mPrevLatLng;
    private BitmapDescriptor mBackgroundImageDescriptor;  //ripple image.
    private float mTransparency = 0.5f;                   //transparency of image.
    private volatile double mDistance = 2000;             //distance to which ripple should be shown in metres
    private int mNumberOfRipples = 1;                     //number of ripples to show, max = 4
    private int mFillColor = Color.TRANSPARENT;           //fill color of circle
    private int mStrokeColor = Color.BLACK;               //border color of circle
    private int mStrokeWidth = 10;                        //border width of circle
    private long mDurationBetweenTwoRipples = 2000;       //in microseconds.
    private long mRippleDuration = 12000;                 //in microseconds
    private ValueAnimator mAnimators[];
    private Handler mHandlers[];
    private GroundOverlay mGroundOverlays[];
    private GradientDrawable mBackground;
    private boolean isAnimationRunning = false;

    public MapRipple(BaiduMap baiduMap, LatLng latLng, Context context) {
        mBaiduMap = baiduMap;
        mLatLng = latLng;
        mPrevLatLng = latLng;
        mBackground = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.background);
        mAnimators = new ValueAnimator[4];
        mHandlers = new Handler[4];
        mGroundOverlays = new GroundOverlay[4];
    }

    /**
     * @param transparency sets transparency for background of circle
     */
    public MapRipple withTransparency(float transparency) {
        mTransparency = transparency;
        return this;
    }

    /**
     * @param distance sets radius distance for circle
     */
    public MapRipple withDistance(double distance) {
        if (distance < 200) {
            distance = 200;
        }
        mDistance = distance;
        return this;
    }

    /**
     * @param latLng sets position for center of circle
     */
    public MapRipple withLatLng(LatLng latLng) {
        mPrevLatLng = mLatLng;
        mLatLng = latLng;
        return this;
    }

    /**
     * @param numberOfRipples sets count of ripples
     */
    public MapRipple withNumberOfRipples(int numberOfRipples) {
        if (numberOfRipples > 4 || numberOfRipples < 1) {
            numberOfRipples = 4;
        }
        mNumberOfRipples = numberOfRipples;
        return this;
    }

    /**
     * @param fillColor sets fill color
     */
    public MapRipple withFillColor(int fillColor) {
        mFillColor = fillColor;
        return this;
    }

    /**
     * @param strokeColor sets stroke color
     */
    public MapRipple withStrokeColor(int strokeColor) {
        mStrokeColor = strokeColor;
        return this;
    }

//    /**
//     * @deprecated use {@link #withStrokeWidth(int)} instead
//     */
//    @Deprecated
//    public void withStrokewidth(int strokeWidth) {
//        mStrokeWidth = strokeWidth;
//    }

    /**
     * @param strokeWidth sets stroke width
     */
    public MapRipple withStrokeWidth(int strokeWidth) {
        mStrokeWidth = strokeWidth;
        return this;
    }

    /**
     * @param durationBetweenTwoRipples sets duration before pulse tick animation
     */
    public MapRipple withDurationBetweenTwoRipples(long durationBetweenTwoRipples) {
        mDurationBetweenTwoRipples = durationBetweenTwoRipples;
        return this;
    }

    /**
     * @return current state of animation
     */
    public boolean isAnimationRunning() {
        return isAnimationRunning;
    }

    /**
     * @param rippleDuration sets duration of ripple animation
     */
    public MapRipple withRippleDuration(long rippleDuration) {
        mRippleDuration = rippleDuration;
        return this;
    }

    private final Runnable mCircleOneRunnable = new Runnable() {
        @Override
        public void run() {
            mGroundOverlays[0] = (GroundOverlay) mBaiduMap.addOverlay(new GroundOverlayOptions()
                    .dimensions((int) mDistance)
                    .position(mLatLng)
                    .transparency(mTransparency)
                    .image(mBackgroundImageDescriptor));

            startAnimation(0);
        }
    };

    private final Runnable mCircleTwoRunnable = new Runnable() {
        @Override
        public void run() {
            mGroundOverlays[1] = (GroundOverlay) mBaiduMap.addOverlay(new GroundOverlayOptions()
                    .dimensions((int) mDistance)
                    .position(mLatLng)
                    .transparency(mTransparency)
                    .image(mBackgroundImageDescriptor));
            startAnimation(1);
        }
    };

    private final Runnable mCircleThreeRunnable = new Runnable() {
        @Override
        public void run() {
            mGroundOverlays[2] = (GroundOverlay) mBaiduMap.addOverlay(new GroundOverlayOptions()
                    .dimensions((int) mDistance)
                    .position(mLatLng)
                    .transparency(mTransparency)
                    .image(mBackgroundImageDescriptor));
            startAnimation(2);
        }
    };

    private final Runnable mCircleFourRunnable = new Runnable() {
        @Override
        public void run() {
            mGroundOverlays[3] = (GroundOverlay) mBaiduMap.addOverlay(new GroundOverlayOptions()
                    .dimensions((int) mDistance)
                    .position(mLatLng)
                    .transparency(mTransparency)
                    .image(mBackgroundImageDescriptor));
            startAnimation(3);
        }
    };

    private void startAnimation(final int numberOfRipple) {
        ValueAnimator animator = ValueAnimator.ofInt(0, (int) mDistance);
        //设置重复次数为一直
        animator.setRepeatCount(ValueAnimator.INFINITE);
        //设置模式为重新开始
        animator.setRepeatMode(ValueAnimator.RESTART);
        //设置持续时间
        animator.setDuration(mRippleDuration);
        //设置插值器
        animator.setEvaluator(new IntEvaluator());
//        animator.setEvaluator(new ArgbEvaluator());
        //设置速度变化
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int animated = (int) valueAnimator.getAnimatedValue();
                if (animated == 0) {
                    animated++;
                }
                mGroundOverlays[numberOfRipple].setDimensions(animated);
                if (mDistance - animated <= 10) {
                    if (mLatLng != mPrevLatLng) {
                        mGroundOverlays[numberOfRipple].setPosition(mLatLng);
                    }
                }
            }
        });
        animator.start();
        mAnimators[numberOfRipple] = animator;
    }

    private void setDrawableAndBitmap() {
        mBackground.setColor(mFillColor);
        mBackground.setStroke(UiUtil.dpToPx(mStrokeWidth), mStrokeColor);
        mBackgroundImageDescriptor = UiUtil.drawableToBitmapDescriptor(mBackground);
    }

    /**
     * Stops current animation if it running
     */
    public void stopRippleMapAnimation() {
        if (isAnimationRunning) {
            try {
                for (int i = 0; i < mNumberOfRipples; i++) {
                    if (i == 0) {
                        mHandlers[i].removeCallbacks(mCircleOneRunnable);
                        mAnimators[i].cancel();
                        mGroundOverlays[i].remove();
                    }
                    if (i == 1) {
                        mHandlers[i].removeCallbacks(mCircleTwoRunnable);
                        mAnimators[i].cancel();
                        mGroundOverlays[i].remove();
                    }
                    if (i == 2) {
                        mHandlers[i].removeCallbacks(mCircleThreeRunnable);
                        mAnimators[i].cancel();
                        mGroundOverlays[i].remove();
                    }
                    if (i == 3) {
                        mHandlers[i].removeCallbacks(mCircleFourRunnable);
                        mAnimators[i].cancel();
                        mGroundOverlays[i].remove();
                    }
                }
            } catch (Exception e) {
                //no need to handle it
            }
        }
        isAnimationRunning = false;
    }

    /**
     * Starts animations
     */
    public void startRippleMapAnimation() {
        if (!isAnimationRunning) {
            setDrawableAndBitmap();
            for (int i = 0; i < mNumberOfRipples; i++) {
                if (i == 0) {
                    mHandlers[i] = new Handler();
                    mHandlers[i].postDelayed(mCircleOneRunnable, mDurationBetweenTwoRipples * i);
                }
                if (i == 1) {
                    mHandlers[i] = new Handler();
                    mHandlers[i].postDelayed(mCircleTwoRunnable, mDurationBetweenTwoRipples * i);
                }
                if (i == 2) {
                    mHandlers[i] = new Handler();
                    mHandlers[i].postDelayed(mCircleThreeRunnable, mDurationBetweenTwoRipples * i);
                }
                if (i == 3) {
                    mHandlers[i] = new Handler();
                    mHandlers[i].postDelayed(mCircleFourRunnable, mDurationBetweenTwoRipples * i);
                }
            }
        }
        isAnimationRunning = true;
    }
}
