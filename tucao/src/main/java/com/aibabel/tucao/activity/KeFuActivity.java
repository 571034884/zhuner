package com.aibabel.tucao.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.aibabel.tucao.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KeFuActivity extends BaseActivity {

    @BindView(R.id.iv_close)
    ImageView ivClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ke_fu);
        ButterKnife.bind(this);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
