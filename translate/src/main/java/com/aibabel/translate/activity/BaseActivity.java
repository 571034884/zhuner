package com.aibabel.translate.activity;

import android.content.Context;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.aibabel.baselibrary.base.StatisticsBaseActivity;
import com.aibabel.statisticalserver.SimpleStatisticsActivity;
import com.aibabel.translate.bean.JsonDataBean;
import com.aibabel.translate.socket.SocketManger;
import com.aibabel.translate.utils.CommonUtils;
import com.aibabel.translate.utils.Constant;
import com.aibabel.translate.utils.L;
import com.aibabel.translate.utils.StringUtils;
import com.umeng.analytics.MobclickAgent;

import butterknife.OnClick;

public class BaseActivity extends StatisticsBaseActivity implements View.OnClickListener {

    protected Context mContext;
    private long clickTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext=this;
        super.onCreate(savedInstanceState);
    }



    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case 133:
                Constant.IS_NEED_SHOW = true;
                L.e("BaseActivty 133================"+Constant.IS_NEED_SHOW);
                getIntent().putExtra("isTranslateKeyCode","null");
                break;
            case 134:
                Constant.IS_NEED_SHOW = true;
                L.e("BaseActivty 134================"+Constant.IS_NEED_SHOW);
                getIntent().putExtra("isTranslateKeyCode","null");
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     *
     * @param from
     * @param to
     * @param asr
     */
    public void sendAsr(String from,String to, String asr){
        if(CommonUtils.isAvailable()){
            SocketManger.getInstance().connect();
            SocketManger.getInstance().sendMessage(new JsonDataBean(Constant.TRANSLATE, StringUtils.getAsrJson(from, to,asr,this)));
        }
    }

}
