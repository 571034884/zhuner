package com.aibabel.travel.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.aibabel.travel.app.BaseApplication;
import com.aibabel.travel.bean.DetailBean;
import com.aibabel.travel.utils.Constant;
import com.aibabel.travel.utils.FastJsonUtil;
import com.aibabel.travel.utils.MyDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：wuqinghua_fyt on 2018/11/22 10:16
 * 功能：
 * 版本：1.0
 */
//接受定位发来的位置提醒
public class NotiftLocation extends BroadcastReceiver {

//    private String audioUrl = "https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/9a5c582e8fe624266d0ac240a79d3dbb310dd0b2.mp3";
//    private String name = "徒步登上埃菲尔铁塔";
//    private String urlSpot = "https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F0d994bf3c5df95a3e0a09805ff588c944c45b26c.jpg";
    private boolean isShow;

    @Override
    public void onReceive(Context context, Intent intent) {
        // 当定位发送广播后，在这里接受并处理收到的广播。
        String audioUrl = intent.getStringExtra("audioUrl");
        String imgUrl = intent.getStringExtra("imgUrl");
        String name = intent.getStringExtra("name");
//        Log.e("travelJson123", travelJson);
        List<DetailBean> list = new ArrayList<>();
        if (!TextUtils.equals(Constant.DIALOG_NAME,name)){
            list.add(new DetailBean(audioUrl, imgUrl, name , false, false));
            Constant.DIALOG_NAME=name;
            MyDialog.showDialogPlay(BaseApplication.getActivity(), "你来到了" + name + "，是否播放此景点", FastJsonUtil.changListToString(list));
            Log.e("travelJson1234","aa");
        }
        if (null!=list&&list.size()!=0){
            list.clear();
        }
    }
}
