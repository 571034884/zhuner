package com.aibabel.menu.service;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.aibabel.baselibrary.utils.SharePrefUtil;
import com.aibabel.menu.MainActivity;
import com.aibabel.menu.util.LogUtil;

/**
 * Created by hjs on 2019/2/28.
 */

public class MenuMessengerService extends Service {

    private static final int case_msg_from_wy = 1;
    private static final String msg_from_clinet_01 = "order_oid";
    private static final String msg_key = "msg_key";
    private static final int msg_to_client = 2;

    private static final int msg_from_wy_gotoReny = 10;

    private static final int msg_gotoRent = 20;

    myMessageHander msg_object = new myMessageHander();

    class myMessageHander extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg != null) {
                switch (msg.what) {
                    case case_msg_from_wy:   //
                        try {
                            String order_id = "";
                            try {
                                LogUtil.e("hjs", "" + msg.getData().getString(msg_key));
                                order_id = SharePrefUtil.getString(context, msg_from_clinet_01, "");
                                LogUtil.e("order_id=" + order_id);
                                if (TextUtils.isEmpty(order_id)) {
                                    order_id = "";
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Messenger messenger = msg.replyTo;
                            Message message = Message.obtain(null, msg_to_client);
                            Bundle bundle = new Bundle();
                            bundle.putString(msg_from_clinet_01, "" + order_id);
                            message.setData(bundle);
                            messenger.send(message);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case msg_from_wy_gotoReny:   //推送清除订单
                        try{
                            if(MainActivity.loopHandler!=null) MainActivity.loopHandler.sendEmptyMessage(130);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    case msg_gotoRent:
                        try{
                            String spchannel = SharePrefUtil.getString(context, MainActivity.order_channelName, "");
                            int iszhuner =  SharePrefUtil.getInt(context, MainActivity.order_isZhuner, -1);
                            LogUtil.e("hjs", "spchannel = " + spchannel+" iszhuner: "+iszhuner);

                            Messenger messenger = msg.replyTo;
                            Message message = Message.obtain(null, 21);
                            Bundle bundle = new Bundle();
                            bundle.putString("channel", "" + spchannel);
                            bundle.putInt("iszhuner",iszhuner);
                            message.setData(bundle);
                            messenger.send(message);

                          // if(MainActivity.loopHandler!=null) MainActivity.loopHandler.sendEmptyMessage(300);



                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;

                }
            }
            super.handleMessage(msg);
        }
    }


    private Messenger mMessenger = new Messenger(msg_object);

    Context context;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.e("hjs", "onBind" + msg_object);
        context = getApplicationContext();
        return mMessenger.getBinder();
    }
}
