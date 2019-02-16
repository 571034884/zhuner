package com.aibabel.fyt_exitandentry.activity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.fyt_exitandentry.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoViewActivity extends BaseActivity {

    @BindView(R.id.photoView)
    PhotoView photoView;



    @Override
    public int getLayout(Bundle bundle) {
        return  R.layout.activity_photo_view;
    }


    @Override
    public void init() {

        AnimatorSet set_guan = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.anim.wenluka_guanbi);
        set_guan.setTarget(photoView);

        Intent intent = getIntent();
        String img = intent.getStringExtra("img");
        RequestOptions options = new RequestOptions().placeholder(R.mipmap.jichangjiazaizhong).error(R.mipmap.jichangjiazaishibai);
//        Glide.with(PhotoViewActivity.this).load(img).apply(options).into(photoView);

//        Picasso.with(PhotoViewActivity.this).load(img).into(photoView);
        Picasso.with(PhotoViewActivity.this).load(img).config(Bitmap.Config.RGB_565).placeholder(R.mipmap.jichangjiazaizhong).error(R.mipmap.jichangjiazaishibai).into(photoView);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
