package com.aibabel.alliedclock.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;

/**
 * @作者： SunSH
 * @日期： 2018/4/3 10:17.
 * @描述：
 * @增改：
 */

public class AutoFitTextView extends android.support.v7.widget.AppCompatTextView {
    //unit px
    private static float DEFAULT_MIN_TEXT_SIZE = 24;
    private static float DEFAULT_MAX_TEXT_SIZE = 44;
    // Attributes
    private TextPaint testPaint;
    private float minTextSize;
    private float maxTextSize;
    private int flag = 1;

    public AutoFitTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialise();
    }

    private void initialise() {
        testPaint = new TextPaint();
        testPaint.set(this.getPaint());
        // max size defaults to the intially specified text size unless it is too small
        maxTextSize = this.getTextSize();
        if (maxTextSize <= DEFAULT_MIN_TEXT_SIZE) {
            maxTextSize = DEFAULT_MAX_TEXT_SIZE;
        }
        minTextSize = DEFAULT_MIN_TEXT_SIZE;
    }

    /**
     * Re size the font so the specified text fits in the text box * assuming the text box is the
     * specified width.
     */
    private void refitText(String text, int textWidth, int textHeight, float focusSize) {
        if (textWidth > 0 && textHeight > 0) {
            //allow diplay rect
            int availableWidth = textWidth - this.getPaddingLeft() - this.getPaddingRight();
            int availableHeight = textHeight - this.getPaddingBottom() - this.getPaddingTop();
            //by the line calculate allow displayWidth
            int autoWidth = availableWidth;
            float mult = 1f;
            float add = 0;
            if (Build.VERSION.SDK_INT > 16) {
                mult = getLineSpacingMultiplier();
                add = getLineSpacingExtra();
            } else {
                //the mult default is 1.0f,if you need change ,you can reflect invoke this field;
            }
            float trySize;
            if (flag == 1) {
                trySize = maxTextSize;
            } else {
                trySize = focusSize;
            }
            testPaint.setTextSize(trySize);
            int oldline = 1, newline = 1;
            while ((trySize > minTextSize)) {
                int displayW = (int) testPaint.measureText(text);
                //calculate text singleline height。
                int displaH = round(testPaint.getFontMetricsInt(null) * mult + add);
                if (displayW < autoWidth) {
                    flag = 2;
                    if ((autoWidth - displayW) > availableWidth / 2) {
                        break;
                    }
                }
                //calculate maxLines
                newline = availableHeight / displaH;
                //if line change ,calculate new autoWidth
                if (newline > oldline) {
                    oldline = newline;
                    autoWidth = availableWidth * newline;
                    continue;
                }
                //try more small TextSize
                trySize -= 1;
                if (trySize <= minTextSize) {
                    trySize = minTextSize;
                    break;
                }

                testPaint.setTextSize(trySize);
            }
            //setMultiLine
            if (newline >= 2) {
                this.setSingleLine(false);
                this.setMaxLines(newline);
            }
            this.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int before, int after) {
        super.onTextChanged(text, start, before, after);
        refitText(text.toString(), this.getWidth(), this.getHeight(), getTextSize());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.e("TagSizeChange", "new(" + w + "," + h + ") old(" + oldw + "" + oldh + ")");
        if (w != oldw || h != oldh) {
            refitText(this.getText().toString(), w, h, getTextSize());
        }
    }

    //FastMath.round()
    public static int round(float value) {
        long lx = (long) (value * (65536 * 256f));
        return (int) ((lx + 0x800000) >> 24);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getTextSize() > minTextSize && getLineCount() > getMaxLines() && flag == 2) {
            refitText(this.getText().toString(), this.getWidth(), this.getHeight(), getTextSize()
                    - 1);
        }
        super.onDraw(canvas);
        flag = 1;
    }
}
