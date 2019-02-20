package com.aibabel.food;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.baselibrary.utils.ProviderUtils;
import com.aibabel.food.activity.AreaSelectActivity;
import com.aibabel.food.activity.HomePageActivity;
import com.aibabel.food.base.Constant;
import com.aibabel.food.bean.LauncherBean;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity implements BaseCallback<LauncherBean> {

    @Override
    public int getLayout(Bundle bundle) {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        setHotRepairEnable(true);
        turnToWhichActivity();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE_LAUNCHER && resultCode == Constant.RESULT_CODE_AREA_SELECT) {
            startActivity(HomePageActivity.class);
            finish();
        }
    }

    public void turnToWhichActivity() {
        if (com.aibabel.baselibrary.utils.CommonUtils.isNetworkAvailable(mContext)) {
            Map<String, String> map = new HashMap<>();
            map.put("city", Constant.CURRENT_CITY);
            OkGoUtil.<LauncherBean>get(false, Constant.METHOD_LANUCHER_GO, map, LauncherBean.class, this);
        } else {
            Constant.CURRENT_CITY = ProviderUtils.getInfo(ProviderUtils.COLUMN_CITY);
            if (!Constant.CURRENT_CITY.equals("")) {
                startActivity(HomePageActivity.class);
                finish();
            } else {
                startActivity(new Intent(mContext, AreaSelectActivity.class).putExtra("from", "main"));
                finish();
            }
        }
    }

    @Override
    public void onSuccess(String s, LauncherBean launcherBean, String s1) {
        switch (s) {
            case Constant.METHOD_LANUCHER_GO:
                Constant.CURRENT_CITY = launcherBean.getData();
                if (!Constant.CURRENT_CITY.equals("")) {
                    startActivity(HomePageActivity.class);
                    finish();
                } else {
                    startActivityForResult(new Intent(mContext, AreaSelectActivity.class).putExtra("from", "main"), Constant.REQUEST_CODE_LAUNCHER);
                }
                break;
        }
    }

    @Override
    public void onError(String s, String s1, String s2) {

    }

    @Override
    public void onFinsh(String s) {

    }
}
