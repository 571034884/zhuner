package com.aibabel.currencyconversion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.aibabel.baselibrary.base.BaseActivity;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;


import butterknife.BindView;

public class PhotoViewActivity extends BaseActivity {


    @BindView(R.id.photoView)
    PhotoView photoView;


    @Override
    public void init() {
        Intent intent = getIntent();
        String photo_img = intent.getStringExtra("photo_img");
        Glide.with(this).load(photo_img).into(photoView);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public int getLayout(Bundle bundle) {
        return R.layout.activity_photo_view;
    }
}
