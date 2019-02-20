package com.aibabel.traveladvisory.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.aibabel.traveladvisory.R;
import com.aibabel.traveladvisory.app.BaseActivity;
import com.aibabel.traveladvisory.utils.OffLineUtil;
import com.bumptech.glide.Glide;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.senab.photoview.PhotoView;

public class RujingkaActivity extends BaseActivity {

    String imgUrl = "";
    String title = "";
    @BindView(R.id.img_plan)
    PhotoView imgPlan;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    private boolean isOfflineSupport;
    private String countryName;

    @Override
    public int initLayout() {
        return R.layout.activity_rujingka;
    }

    @Override
    public void init() {
        title = getIntent().getStringExtra("title");
        isOfflineSupport = getIntent().getBooleanExtra("isOfflineSupport",false);
        imgUrl = getIntent().getStringExtra("imgUrl");
        countryName = getIntent().getStringExtra("countryName");
        String s1 = imgUrl.substring(imgUrl.lastIndexOf("入境卡填写") + 5);
        String s2 = s1.substring(s1.lastIndexOf("src") + 5);
        String s3 = s2.substring(0, s2.indexOf(".jpg") + 4);

        if (isOfflineSupport) {
            String s4 = s3.substring(s3.lastIndexOf("/") + 1, s3.length());
            String filePath = OffLineUtil.offlinePath + OffLineUtil.offlineSupportMap.get(countryName) + "/" + countryName + "/";
            Glide.with(RujingkaActivity.this).load(new File(filePath + s4)).into(imgPlan);
        }else {
            Glide.with(RujingkaActivity.this).load(s3).into(imgPlan);
        }
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
