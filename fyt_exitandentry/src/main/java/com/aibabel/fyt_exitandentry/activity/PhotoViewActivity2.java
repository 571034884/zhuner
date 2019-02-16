package com.aibabel.fyt_exitandentry.activity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.fyt_exitandentry.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoViewActivity2 extends BaseActivity {


    @BindView(R.id.photoView)
    PhotoView photoView;

    @Override
    public int getLayout(Bundle bundle) {
        return R.layout.activity_photo_view2;
    }

    @Override
    public void init() {

        AnimatorSet set_guan = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.anim.wenluka_guanbi);
        set_guan.setTarget(photoView);

        Intent intent = getIntent();
        String img = intent.getStringExtra("img");
        RequestOptions options = new RequestOptions().placeholder(R.mipmap.jichangjiazaizhong).error(R.mipmap.jichangjiazaishibai);
//        Glide.with(PhotoViewActivity2.this).load(img).apply(options).into(photoView);

        Picasso.with(PhotoViewActivity2.this).load(img).config(Bitmap.Config.RGB_565).placeholder(R.mipmap.jichangjiazaizhong).error(R.mipmap.jichangjiazaishibai).into(photoView);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}
