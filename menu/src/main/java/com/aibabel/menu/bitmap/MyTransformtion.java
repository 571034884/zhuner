package com.aibabel.menu.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

public class MyTransformtion extends BitmapTransformation {
    private Context mContext;

    public MyTransformtion(Context context) {
        mContext=context;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        Log.e("TAG", "transform: bitmap-------->"+toTransform );
//        return FastBlur.doBlur(toTransform,30,true);
        return BlurBitmapUtil.instance().blurBitmap(mContext, toTransform, 25,outWidth,outHeight);

    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

    }
}
