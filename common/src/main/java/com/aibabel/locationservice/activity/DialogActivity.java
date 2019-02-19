package com.aibabel.locationservice.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.aibabel.locationservice.R;

import butterknife.ButterKnife;

public class DialogActivity extends Activity {

    private TextView tvTitle;
    private TextView tvContent;
    private TextView tvConfirm;

//    http://api.joner.aibabel.cn:7001/v1/jonerPush/PushMsgBySn?sn=999900000000084&noticeAlert=你有一张新的优惠券，请注意查收&msgContentType=text-ok&msgTitle=优惠券到账提醒&msgContent={"type":"1","msgContent":"你有一张新的优惠券，将于2018年12月31日到期，请尽快使用"}&no=1234
//{"type":"1","msgContent":"你有一张新的优惠券，将于2018年12月31日到期，请尽快使用"}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);

        String msg = getIntent().getStringExtra("msg");
        String title =getIntent().getStringExtra("title");

        String jiguang = getIntent().getStringExtra("jiguang");

        tvConfirm = findViewById(R.id.tv_confirm);
        tvContent = findViewById(R.id.tv_content);
        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(title+"");
        tvContent.setText(msg+"");


        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogActivity.this.finish();
            }
        });

    }
}
