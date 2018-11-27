package com.aibabel.ocr.widgets;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.aibabel.ocr.bean.WordsResult;
import com.aibabel.ocr.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zuoliangzhu on 17/3/23.
 * ocr识别结果显示view
 */

public class OcrDisplayView extends View {
    private OnDownListener onDownListener;
    private List<WordsResultWrapper> wordsResult = new ArrayList<WordsResultWrapper>();
    private Paint paint = new Paint();
    //文字显示区域
//    private int width;
//    private int height;

    //翻译原图
    private Bitmap bitmap;
    //bitmap是否初始化了
    private boolean bitmapInit = false;
    // 手指按下时图片的矩阵
    private Matrix downMatrix;
    // 手指移动时图片的矩阵
    private Matrix moveMatrix;
    // 多点触屏时的中心点
    private PointF midPoint = new PointF();
    // 绘制图片的矩阵
    private Matrix matrix;

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
    private boolean canSmall;
    private boolean canBig;
    private float lastSmall;
    private float lastBig;

    public OcrDisplayView(Context context) {
        super(context);
    }

    public OcrDisplayView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OcrDisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawColor(0xFF000000);
        if (wordsResult == null || wordsResult.size() == 0 || bitmap == null)
            return;
        initBitmap();
        drawText();
        canvas.drawBitmap(bitmap, matrix, null);

    }

    //
//    /**
//     * 初始化
//     * @param
//     * @return
//     */
    private void initBitmap() {
        if (bitmapInit)
            return;
        bitmapInit = true;
        matrix = new Matrix();
        downMatrix = new Matrix();
        moveMatrix = new Matrix();
        Canvas canvas = new Canvas(bitmap);
        //canvas.drawColor(0xFFEE799F);
        int left = this.getWidth() / 2 - bitmap.getWidth() / 2;
        int top = this.getHeight()/2 - bitmap.getHeight()/2;
//        int top = 0;
        matrix.postTranslate(left, top);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mode = TRANS;
                downX = event.getX();
                downY = event.getY();
                if(null!=  downMatrix)
                downMatrix.set(matrix);
                if (this.onDownListener != null)
                    this.onDownListener.onDown(this);
                break;
            case MotionEvent.ACTION_POINTER_DOWN://多点触碰
                mode = ZOOM;
                downDistance = getSpaceDistance(event);
                downMatrix.set(matrix);
                midPoint = getMidPoint(event);
                Log.d("downDistance:",downDistance+"");
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == ZOOM) {// 缩放
                    if (event.getPointerCount() < 2)
                        break;
                    moveMatrix.set(downMatrix);
                    scale = getSpaceDistance(event) / downDistance;
                    moveMatrix.postScale(scale, scale, midPoint.x, midPoint.y);
                    matrix.set(moveMatrix);
                    invalidate();
                } else if (mode == TRANS) {// 平移
                    moveMatrix.set(downMatrix);
                    moveMatrix.postTranslate(event.getX() - downX, event.getY() - downY);
                    matrix.set(moveMatrix);
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_UP:
                if (mode == ZOOM) {// 缩放
                    if (scale > 1) {
                        if (scale > maxScale) {
                            moveMatrix.postScale(maxScale / scale, maxScale / scale, midPoint.x, midPoint.y);
                            maxScale = 1f;
                            minScale = 0.25f;
                        } else {
                            maxScale = maxScale / scale;
                            minScale = minScale / scale;
                        }
                    } else {
                        if (scale < minScale) {
                            moveMatrix.postScale(minScale / scale, minScale / scale, midPoint.x, midPoint.y);
                            maxScale = 4f;
                            minScale = 1f;
                        } else {
                            maxScale = maxScale / scale;
                            minScale = minScale / scale;
                        }
                    }

                    matrix.set(moveMatrix);
                    invalidate();
                }
                break;
        }
        return true;

    }

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





    /**
     * 画文字
     *
     * @param
     */
    private void drawText() {
        if (DisplayUtil.getScreenInch((Activity) this.getContext()) >= 4.4) {
            paint.setTextSize(DisplayUtil.sp2px(this.getContext(), 16));
        } else {
            paint.setTextSize(DisplayUtil.sp2px(this.getContext(), 12));
        }
        paint.setColor(Color.WHITE);
        Canvas canvas = new Canvas(bitmap);
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int txtHeight = fontMetrics.bottom - fontMetrics.ascent;
        for (WordsResultWrapper wrapper : wordsResult) {
            if (wrapper.drawed)
                continue;
            canvas.drawText(wrapper.wordsResult.getWords(), wrapper.wordsResult.getLeft(), wrapper.wordsResult.getTop() - fontMetrics.ascent, paint);
            wrapper.drawed = true;
        }

    }


    public void addWord(WordsResult word) {
        this.wordsResult.add(new WordsResultWrapper(word));
        System.out.println("ocr-------添加=" + word.getWords());
        this.invalidate();
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        this.bitmapInit = false;
        this.invalidate();
    }

    /**
     * 清理word
     */
    public void clear() {
        wordsResult.clear();
        this.bitmap = null;
        this.invalidate();
    }

    public void setOnDownListener(OnDownListener onDownListener) {
        this.onDownListener = onDownListener;
    }

    /**
     * 手指按下事件
     */
    public interface OnDownListener {
        void onDown(View view);
    }

    /**
     *
     */
    private class WordsResultWrapper {
        WordsResult wordsResult;
        boolean drawed;//是否画到bitmap上了

        WordsResultWrapper(WordsResult wordsResult) {
            drawed = false;
            this.wordsResult = wordsResult;
        }
    }
}
