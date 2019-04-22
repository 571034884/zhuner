package com.aibabel.launcher.rent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.aibabel.baselibrary.utils.SharePrefUtil;
import com.aibabel.menu.R;
import com.aibabel.launcher.activity.MainActivity;

/**
 * 你所租赁的即将到期，hjs
 */

public class RentDialogActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_rent_dialog);
        RelativeLayout rent_relative = (RelativeLayout)findViewById(R.id.rent_relative);
        LinearLayout usercomfim_bt = (LinearLayout)findViewById(R.id.rent_dialog_usercomfim);
        LinearLayout goto_rent_bt = (LinearLayout)findViewById(R.id.rent_goto_rent);
        //if(rent_relative!=null)rent_relative.setOnClickListener(this);
        if(usercomfim_bt!=null)usercomfim_bt.setOnClickListener(this);
        if(goto_rent_bt!=null) goto_rent_bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rent_relative:
                //this.finish();
                break;
            case R.id.rent_dialog_usercomfim:
                this.finish();
                break;
            case R.id.rent_goto_rent:
                lockloopmsg();
                //startActivity(new Intent(this, MenuActivity.class));
                this.finish();
                break;

        }
    }

    /**
     * 跳转
     */
    private void lockloopmsg(){
        String order_end =  SharePrefUtil.getString(this, MainActivity.order_endttime,"");
        String channelName =  SharePrefUtil.getString(this, MainActivity.order_channelName,"");
        int isZhuner =  SharePrefUtil.getInt(this, MainActivity.order_isZhuner,-1);


        try {
                Message message = new Message();
                Bundle bun = new Bundle();

                if((isZhuner==1)) {
                    bun.putString(MainActivity.bunder_iszhuner, "zhuner");
                }else if(isZhuner==0) {
                    bun.putString(MainActivity.bunder_qudao,channelName);
                }else{
                    if(TextUtils.isEmpty(channelName)){
                        bun.putString(MainActivity.bunder_iszhuner, "zhuner");
                        bun.putString(MainActivity.bunder_qudao,"");
                    }else{
                        bun.putString(MainActivity.bunder_iszhuner, "zz");
                        bun.putString(MainActivity.bunder_qudao,channelName);
                    }
                }
                message.setData(bun);
            Intent keepuse =   new Intent(this, RentKeepUseActivity.class);
            keepuse.putExtras(bun);
            startActivity(keepuse);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
