package com.aibabel.launcher.rent;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.aibabel.menu.R;
import com.aibabel.launcher.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SimDetectActivity extends Activity implements View.OnClickListener {
    private Unbinder mUnbinder;

    @BindView(R.id.sim_relat)
    RelativeLayout simrelat;

    @BindView(R.id.sim_detect_close)
    LinearLayout sim_detect_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sim_detect);
        mUnbinder = ButterKnife.bind(this);

        LogUtil.e("hjs", "simrelat" + (simrelat == null));

        if (simrelat != null) simrelat.setOnClickListener(this);
        if (sim_detect_close != null) sim_detect_close.setOnClickListener(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if(mUnbinder!=null)mUnbinder.unbind();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sim_relat:
                this.finish();
                break;
            case R.id.sim_detect_close:
                this.finish();
                break;

        }
    }
}
