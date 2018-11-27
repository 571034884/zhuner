package com.aibabel.ocr.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by zuoliangzhu on 17/3/24.
 */

public class DstView extends View{
    private Bitmap bitmap;
    public DstView(Context context) {
        super(context);
    }
    public DstView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public DstView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(bitmap == null)
            return ;
        int left = (this.getWidth()-bitmap.getWidth())/2;
        int top = ((this.getHeight()-bitmap.getHeight())/2);
//
////        int left = 0;
////        int top = 0;
        canvas.drawBitmap(bitmap, left, top, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 设置图片
     */
    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
        this.invalidate();
    }
}
