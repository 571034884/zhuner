package com.aibabel.ocr.widgets;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.widget.FrameLayout;
import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

//import com.nineoldandroids.view.ViewHelper;


public class PowerfulLayout extends FrameLayout {
    // 屏幕宽高
    private int screenHeight;
    private int screenWidth;
    private ViewDragHelper mDragHelper;
    private long lastMultiTouchTime;// 记录多点触控缩放后的时间
    private int originalWidth;// view宽度
    private int originalHeight;// view高度
    private ScaleGestureDetector mScaleGestureDetector = null;
    // private View view;
//    private int downX;// 手指按下的x坐标值
//    private int downY;// 手指按下的y坐标值
    private int left;// view的左坐标值
    private int top;// view的上坐标值
    private int right;// view的右坐标值
    private int bottom;// view的下坐标值
    private int newHeight;
    private int newWidth;

    // 多点触屏时的中心点
    private PointF midPoint = new PointF();
    // 手指按下屏幕的X坐标
    private float downX;
    // 手指按下屏幕的Y坐标
    private float downY;
    // 手指之间的初始距离
    private float downDistance;


    // 触控模式
    private int mode;
    private static final int NONE = 0; // 无模式
    private static final int TRANS = 1; // 拖拽模式
    private static final int ZOOM = 2; // 缩放模式

    private float minScale = 0.5f;//缩放最小比例
    private float maxScale = 2f;//缩放最大比例
    private float scale;

//    private float scale;
    private float preScale = 1;// 默认前一次缩放比例为1

    public PowerfulLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public PowerfulLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PowerfulLayout(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
//        mDragHelper = ViewDragHelper.create(this, callback);
//        mScaleGestureDetector = new ScaleGestureDetector(context,
//                new ScaleGestureListener());

        // view.post(new Runnable() {
        //
        // @Override
        // public void run() {
        // left = view.getLeft();
        // top = view.getTop();
        // right = view.getRight();
        // bottom = view.getBottom();
        // originalWidth = view.getWidth();
        // originalHeight = view.getHeight();
        // }
        // });
    }

//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//        screenWidth = getMeasuredWidth();
//        screenHeight = getMeasuredHeight();
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        super.onInterceptTouchEvent(ev);
//        boolean b = mDragHelper.shouldInterceptTouchEvent(ev);// 由mDragHelper决定是否拦截事件，并传递给onTouchEvent
//        return b;
//    }
//
//    private boolean needToHandle=true;
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//        int pointerCount = event.getPointerCount(); // 获得多少点
//        if (pointerCount > 1) {// 多点触控，
//            switch (event.getAction()) {
//                case MotionEvent.ACTION_DOWN:
//                    needToHandle=false;
//                    break;
//                case MotionEvent.ACTION_MOVE:
//
//                    break;
//                case MotionEvent.ACTION_POINTER_2_UP://第二个手指抬起的时候
//                    needToHandle=true;
//                    break;
//
//                default:
//                    break;
//            }
//            return mScaleGestureDetector.onTouchEvent(event);//让mScaleGestureDetector处理触摸事件
//        } else {
//            long currentTimeMillis = System.currentTimeMillis();
//            if (currentTimeMillis - lastMultiTouchTime > 200&&needToHandle) {
////                  多点触控全部手指抬起后要等待200毫秒才能执行单指触控的操作，避免多点触控后出现颤抖的情况
//                try {
//                    mDragHelper.processTouchEvent(event);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return true;
//            }
////            }
//        }
//        return false;
//    }
//
//    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
//        /**
//         * 用于判断是否捕获当前child的触摸事件
//         *
//         * @param child
//         *            当前触摸的子view
//         * @param pointerId
//         * @return true就捕获并解析；false不捕获
//         */
//        @Override
//        public boolean tryCaptureView(View child, int pointerId) {
//            if (preScale > 1)
//                return true;
//            return false;
//        }
//
//        /**
//         * 控制水平方向上的位置
//         */
//        @Override
//        public int clampViewPositionHorizontal(View child, int left, int dx) {
//
//            if (left < (screenWidth - screenWidth * preScale) / 2)
//                left = (int) (screenWidth - screenWidth * preScale) / 2;// 限制mainView可向左移动到的位置
//            if (left > (screenWidth * preScale - screenWidth) / 2)
//                left = (int) (screenWidth * preScale - screenWidth) / 2;// 限制mainView可向右移动到的位置
//            return left;
//        }
//
//        public int clampViewPositionVertical(View child, int top, int dy) {
//
//            if (top < (screenHeight - screenHeight * preScale) / 2) {
//                top = (int) (screenHeight - screenHeight * preScale) / 2;// 限制mainView可向上移动到的位置
//            }
//            if (top > (screenHeight * preScale - screenHeight) / 2) {
//                top = (int) (screenHeight * preScale - screenHeight) / 2;// 限制mainView可向上移动到的位置
//            }
//            return top;
//        }
//
//    };
//
//    public class ScaleGestureListener implements
//            ScaleGestureDetector.OnScaleGestureListener {
//
//        @Override
//        public boolean onScale(ScaleGestureDetector detector) {
//
//            float previousSpan = detector.getPreviousSpan();// 前一次双指间距
//            float currentSpan = detector.getCurrentSpan();// 本次双指间距
//            if (currentSpan < previousSpan) {
//                // 缩小
//                // scale = preScale-detector.getScaleFactor()/3;
//                scale = preScale - (previousSpan - currentSpan) / 1000;
//            } else {
//                // 放大
//                // scale = preScale+detector.getScaleFactor()/3;
//                scale = preScale + (currentSpan - previousSpan) / 1000;
//            }
//            // 缩放view
//            if (scale > 0.5) {
//                ViewHelper.setScaleX(PowerfulLayout.this, scale);// x方向上缩放
//                ViewHelper.setScaleY(PowerfulLayout.this, scale);// y方向上缩放
//            }
//            return false;
//        }
//
//        @Override
//        public boolean onScaleBegin(ScaleGestureDetector detector) {
//            // 一定要返回true才会进入onScale()这个函数
//            return true;
//        }
//
//        @Override
//        public void onScaleEnd(ScaleGestureDetector detector) {
//            preScale = scale;// 记录本次缩放比例
//            lastMultiTouchTime = System.currentTimeMillis();// 记录双指缩放后的时间
//        }
//    }


//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        int action = MotionEventCompat.getActionMasked(event);
//        int x = (int) event.getX();
//        int y = (int) event.getY();
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                mode = TRANS;
//                downX = event.getX();
//                downY = event.getY();
//                break;
//            case MotionEvent.ACTION_POINTER_DOWN://多点触碰
//                mode = ZOOM;
//                downDistance = getSpaceDistance(event);
//
//                midPoint = getMidPoint(event);
//                Log.d("downDistance:",downDistance+"");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                if (mode == ZOOM) {// 缩放
//                    if (event.getPointerCount() < 2)
//                        break;
//                    scale = getSpaceDistance(event) / downDistance;
//                    invalidate();
//                } else if (mode == TRANS) {// 平移
//                    setTransitionGroup(true);
//                    invalidate();
//                }
//                break;
//
//            case MotionEvent.ACTION_UP:
//                if (mode == ZOOM) {// 缩放
//                    if (scale > 1) {
//                        if (scale > maxScale) {
//                            setScaleX(scale);
//                            setScaleY(scale);
//                            maxScale = 1f;
//                            minScale = 0.25f;
//                        } else {
//                            maxScale = maxScale / scale;
//                            minScale = minScale / scale;
//                        }
//                    } else {
//                        if (scale < minScale) {
//                            setScaleX(scale);
//                            setScaleY(scale);
//                            maxScale = 4f;
//                            minScale = 1f;
//                        } else {
//                            maxScale = maxScale / scale;
//                            minScale = minScale / scale;
//                        }
//                    }
//
//
//                    invalidate();
//                }
//                break;
//        }
//        return true;
//
//    }


    /**
     * 获取手指间的距离
     *
     * @param event
     * @return
     */
    private float getSpaceDistance(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * 获取手势中心点
     *
     * @param event
     */
    private PointF getMidPoint(MotionEvent event) {
        PointF point = new PointF();
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
        return point;
    }


}
