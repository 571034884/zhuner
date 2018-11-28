package com.aibabel.baselibrary.imageloader.object;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.aibabel.baselibrary.base.BaseApplication;
import com.aibabel.baselibrary.imageloader.LoaderOptions;
import com.aibabel.baselibrary.imageloader.interface_.ILoaderStrategy;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.security.MessageDigest;

/**
 * 作者：SunSH on 2018/11/13 14:40
 * 功能：imageloader实际代理的对象
 * 版本：1.0
 */
public class GlideLoader implements ILoaderStrategy {

    private volatile static RequestBuilder<Drawable> mGlideBuilder;

    private static RequestBuilder getlideBuilder() {
        if (mGlideBuilder == null) {
            synchronized (GlideLoader.class) {
                if (mGlideBuilder == null) {
                    mGlideBuilder = Glide.with(BaseApplication.mApplication).asDrawable();
                }
            }
        }
        return mGlideBuilder;
    }

    @Override
    public void loadImage(LoaderOptions options) {
        RequestOptions glideOption = new RequestOptions();

        //尺寸
        if (options.targetHeight > 0 && options.targetWidth > 0) {
            glideOption.override(options.targetWidth, options.targetHeight);
        }
        //填充方式
        if (options.isCenterInside) {
            glideOption.centerInside();
        } else if (options.isCenterCrop) {
            glideOption.centerCrop();
        }
        //解码格式
        if (options.config == 565) {
            glideOption.format(DecodeFormat.PREFER_RGB_565);
        } else {
            glideOption.format(DecodeFormat.PREFER_ARGB_8888);
        }
        //
        if (options.errorResId != 0) {
            glideOption.error(options.errorResId);
        }
        if (options.placeholderResId != 0) {
            glideOption.placeholder(options.placeholderResId);
        }
        if (options.bitmapAngle != 0) {
            glideOption.transform(new RoundedCorners((int) options.bitmapAngle));
        }
        if (options.skipLocalCache) {
            glideOption.diskCacheStrategy(DiskCacheStrategy.NONE);
        }
        if (options.skipNetCache) {
            glideOption.skipMemoryCache(options.skipNetCache);
        }
        if (options.degrees != 0) {
            final float d = options.degrees;
            glideOption.transform(new BitmapTransformation() {
                @Override
                protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(d);
                    return Bitmap.createBitmap(toTransform, 0, 0, toTransform.getWidth(), toTransform.getHeight(), matrix, true);
                }

                @Override
                public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

                }
            });
        }
        if (options.url != null) {
            mGlideBuilder = getlideBuilder().load(options.url);
        } else if (options.file != null) {
            mGlideBuilder = getlideBuilder().load(options.file);
        } else if (options.drawableResId != 0) {
            mGlideBuilder = getlideBuilder().load(options.drawableResId);
        } else if (options.uri != null) {
            mGlideBuilder = getlideBuilder().load(options.uri);
        }
        mGlideBuilder.apply(glideOption);
        if (options.targetView instanceof ImageView) {
            mGlideBuilder.into(((ImageView) options.targetView));
        }
//        else if (options.callBack != null) {
//            requestCreator.into(new PicassoTarget(options.callBack));
//        }
    }

    @Override
    public void clearMemoryCache() {

    }

    @Override
    public void clearDiskCache() {

    }
}
