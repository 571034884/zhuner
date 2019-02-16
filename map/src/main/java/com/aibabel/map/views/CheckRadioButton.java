package com.aibabel.map.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CompoundButton;

/**
 * Created by fytworks on 2019/1/24.
 */

public class CheckRadioButton extends CompoundButton {
    public CheckRadioButton(Context context) {
        this(context,null);
    }

    public CheckRadioButton(Context context, AttributeSet attrs) {
        this(context, attrs,android.R.attr.radioButtonStyle);
    }

    public CheckRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void toggle() {
        // we override to prevent toggle when the radio is checked
        super.toggle();
    }
}