package com.aibabel.ocr.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import com.aibabel.ocr.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * =====================================================================
 * <p>
 * 作者 : 张文颖
 * <p>
 * 时间: 2018/3/29
 * <p>
 * 描述: 手势涂抹控件
 * <p>
 * =====================================================================
 */
public class Guaguaka extends View {
    private OnTumoListener onTumoListener;
    private Path mPath = new Path();
    private Paint mPaint = new Paint();
    private int mLastX;
    private int mLastY;
    private RectF dstRect;//设置可涂抹区域
    private int dstWidth;
    private int dstHeight;
    private Canvas canvas;

    private List<RectF> rects = new ArrayList<RectF>();//涂抹区域列表
    private RectF currRect;//当前涂抹区域
    private boolean isSelectAll = false;//是否选择全部


    public Guaguaka(Context context) {
        super(context);
        init();
    }

    public Guaguaka(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Guaguaka(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
//        mPaint.setColor(Color.WHITE);
        mPaint.setColor(0x55FFFFFF);
        //mPaint.setAntiAlias(true);
        //mPaint.setDither(true);
        mPaint.setStrokeWidth(DisplayUtil.dip2px(this.getContext(), 20));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND); // 圆角
        mPaint.setStrokeCap(Paint.Cap.ROUND);
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        rects = new ArrayList<RectF>();
        //关闭硬件加速(一定要，否则不起作用)
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (dstRect == null && dstWidth == 0 && dstHeight == 0)
            return;
        //涂抹
        initDstRect();
//        canvas.drawColor(0x55000000);
//        canvas.drawColor(0x00000000);

        this.canvas = canvas;
        if (isSelectAll) {
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawRect(rects.get(0), mPaint);
        } else {
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(mPath, mPaint);
        }
        //drawArea(canvas);
    }

    /**
     * 初始化dstrect
     *
     * @param
     * @return
     */
    private void initDstRect() {
        if (dstRect == null) {
            if (dstWidth != 0 && dstHeight != 0) {
                int left = (this.getWidth() - dstWidth) / 2;
                int top = 0;//(this.getHeight()-dstHeight)/2;
                dstRect = new RectF(left, top, left + dstWidth, top + dstHeight);
            } else {
                dstRect = new RectF(0, 0, this.getWidth(), this.getHeight());
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (dstRect == null)
            return true;
        int action = event.getAction();

        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mPath.reset();
                mLastX = x;
                mLastY = y;
                currRect = null;
                mPath.moveTo(mLastX, mLastY);
                if (this.onTumoListener != null)
                    this.onTumoListener.onDown(this);
                break;
            case MotionEvent.ACTION_MOVE:
                handleMove(event);
                break;
            case MotionEvent.ACTION_UP:
                handleUp(event);
                if (this.onTumoListener != null)
                    this.onTumoListener.onUp(this);
                break;
        }
        return true;

    }

    /**
     * 计算实时涂抹区域
     */
    private void handleMove(MotionEvent event) {
        //计算涂抹区域
        int x = (int) event.getX();
        int y = (int) event.getY();
        if (currRect == null) {
            currRect = new RectF(x, y, x, y);
            return;
        }
        //是否超出涂抹区域
        if (x < dstRect.left || x > dstRect.right || y < dstRect.top || y > dstRect.bottom) {
            invalidate();
            return;
        }
        currRect.left = Math.min(x, currRect.left);
        currRect.top = Math.min(y, currRect.top);
        currRect.right = Math.max(x, currRect.right);
        currRect.bottom = Math.max(y, currRect.bottom);
        //画path
        int dx = Math.abs(x - mLastX);
        int dy = Math.abs(y - mLastY);
        mLastX = x;
        mLastY = y;
        // 如果横向或者纵向移动的坐标值大于3像素说明路径有可连接性(接着绘制路径)
        if (dx > 3 || dy > 3) {
            mPath.lineTo(x, y);
        }


        invalidate();
    }

    /**
     * 计算涂抹区域
     *
     * @return
     */
    private void handleUp(MotionEvent event) {
        //System.out.println("zuoliangzhu-up:left="+currRect.left + " top="+currRect.top + "
        // right="+currRect.right+" bottom="+currRect.bottom);
        if (currRect == null)
            return;
        RectF rect = currRect;
        currRect.left = Math.max(currRect.left, dstRect.left);
        currRect.top = Math.max(currRect.top, dstRect.top);
        currRect.right = Math.min(currRect.right, dstRect.right);
        currRect.bottom = Math.min(currRect.bottom, dstRect.bottom);

        for (RectF item : rects) {
            if (isContain(item, rect)) {
                return;
            }
            if (isContain(rect, item)) {
                item.left = rect.left;
                item.top = rect.top;
                item.right = rect.right;
                item.bottom = rect.bottom;
                return;
            }
        }
        rects.add(rect);
    }

    /**
     * 判断rect1是否包含rect2
     *
     * @return
     */
    private boolean isContain(RectF rect1, RectF rect2) {

        if (rect1.left <= rect2.left && rect1.right >= rect2.right) {
            return rect1.top <= rect2.top && rect1.bottom >= rect2.bottom;
        }
//        if(rect2.left <= rect1.left && rect2.right >= rect1.right){
//            if(rect2.top <= rect1.top && rect2.bottom >= rect1.bottom){
//                return true;
//            }
//        }
        return false;
    }

    /**
     * 画涂抹后的区域(用于调试)
     *
     * @return
     */
    private void drawArea(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.RED);// 设置红色
        p.setStyle(Paint.Style.FILL);
        for (RectF item : rects) {
            canvas.drawRect(item, p);
        }
    }

    /**
     * 清理涂抹
     */
    public void clear() {
        rects.clear();
        mPath.reset();
        isSelectAll = false;
        this.invalidate();
    }

    /**
     * 涂抹全部
     */
    public void selectAll() {
        rects.clear();
        rects.add(dstRect);
        mPath.reset();
        isSelectAll = true;
        this.invalidate();
    }

    /**
     *
     */
    public void changeFront() {
        if(null!=canvas){
            canvas.drawColor(0x55000000);
            draw(canvas);
        }
        this.invalidate();
    }




    /**
     * 设置可涂抹区域的宽度、高度
     */
    public void setDstWidthAndHeight(int width, int height) {
        clear();
        this.dstWidth = width;
        this.dstHeight = height;
        dstRect = null;
//        int left = (this.getWidth()-width)/2;
//        int top = (this.getHeight()-height)/2;
//        dstRect = new RectF(left, top, left+width, top+height);
        //this.invalidate();
    }

    /**
     * 获得所有涂抹区域
     *
     * @return
     */
    public List<RectF> getRects() {
        if (dstRect == null)
            return null;
        //转换坐标，以dst的右上角为原点
        List<RectF> newRects = new ArrayList<RectF>();
        for (RectF rect : rects) {
            RectF newRect = new RectF();
            newRect.left = rect.left - dstRect.left;
            newRect.top = rect.top - dstRect.top;
            newRect.right = rect.right - dstRect.left;
            newRect.bottom = rect.bottom - dstRect.top;
            newRects.add(newRect);
        }
        return newRects;
    }

    public void setOnDownListener(OnTumoListener onTumoListener) {
        this.onTumoListener = onTumoListener;
    }

    /**
     * 手指按下事件
     */
    public interface OnTumoListener {
        void onDown(View view);

        void onUp(View view);
    }
}
