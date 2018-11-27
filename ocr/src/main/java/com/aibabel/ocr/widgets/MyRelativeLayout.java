package com.aibabel.ocr.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class MyRelativeLayout extends RelativeLayout {


    //    声明Paint对象
    private Paint mPaint = null;
    private int StrokeWidth = 2;
    Rect rect = new Rect(0, 0, 0, 0);


    public MyRelativeLayout(Context context) {
        super(context);

    }

    public MyRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(StrokeWidth);
        mPaint.setColor(Color.parseColor("#FE5000"));
        canvas.drawRect(rect, mPaint);

    }


    public void drawReat(int left, int top, int width, int height) {

//        invalidate(rect);
        int l = left < 0 ? 0 : left;
        int t = top < 0 ? 0 : top;
         rect = new Rect(l, t, l + width + StrokeWidth, top + height + StrokeWidth);
        // 更新界面
        postInvalidate();
    }


}
