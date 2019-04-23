package com.aibabel.scenic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.scenic.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

public class PhotoViewActivity extends BaseActivity {


    @BindView(R.id.photoView)
    PhotoView photoView;


    @Override
    public void init() {
        Intent intent = getIntent();
        String photo_img = intent.getStringExtra("photo_img");
        Picasso.with(this).load(photo_img).into(photoView);
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
