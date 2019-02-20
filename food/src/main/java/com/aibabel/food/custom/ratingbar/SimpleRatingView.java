package com.aibabel.food.custom.ratingbar;

import android.content.Context;
import android.widget.ImageView;

import com.aibabel.food.R;
import com.aibabel.food.custom.ratingbar.IRatingView;

/**
 * 作者：SunSH on 2018/12/5 17:38
 * 功能：
 * 版本：1.0
 */
public class SimpleRatingView implements IRatingView {

    @Override
    public int getStateRes(int posi, int state) {
        int id = R.mipmap.evaluation_normal;
        switch (state) {
            case STATE_NONE:
                id = R.mipmap.evaluation_normal;
                break;
            case STATE_HALF:
                id = R.mipmap.evaluation_normal;
                break;
            case STATE_FULL:
                id = R.mipmap.evaluation_select;
                break;
            default:
                break;
        }
        return id;
    }

    @Override
    public int getCurrentState(float rating, int numStars, int position) {
        position++;
        float dis = position - rating;
        if (dis <= 0) {
            return STATE_FULL;
        }
        if (dis == 0.5) {
            return STATE_HALF;
        }
        if (dis > 0.5) {
            return STATE_NONE;
        }
        return 0;
    }


    @Override
    public ImageView getRatingView(Context context, int numStars, int posi) {
        ImageView imageView = new ImageView(context);
        return imageView;
    }
}
