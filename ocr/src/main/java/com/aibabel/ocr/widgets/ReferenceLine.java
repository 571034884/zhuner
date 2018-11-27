package com.aibabel.ocr.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.aibabel.ocr.utils.DisplayUtil;

import static android.graphics.Color.*;

/**
 * =====================================================================
 * <p>
 * 作者 : 张文颖
 * <p>
 * 时间: 2018/3/29
 * <p>
 * 描述: 自定义辅助线
 * <p>
 * =====================================================================
 */

public class ReferenceLine extends View {

    private Paint mLinePaint;

    public ReferenceLine(Context context) {
        super(context);
        init();
    }

    public ReferenceLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ReferenceLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setColor(parseColor("#45e0e0e0"));
        mLinePaint.setStrokeWidth(1);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        int screenWidth = DisplayUtil.getScreenWidth(getContext());
        int screenHeight = DisplayUtil.getScreenHeight(getContext());

        int width = screenWidth/3;
        int height = screenHeight/3;

        for (int i = width, j = 0;i < screenWidth && j<2;i += width, j++) {
            canvas.drawLine(i, 0, i, screenHeight, mLinePaint);
        }
        for (int j = height,i = 0;j < screenHeight && i < 2;j += height,i++) {
            canvas.drawLine(0, j, screenWidth, j, mLinePaint);
        }
    }


}
