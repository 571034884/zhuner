package com.aibabel.message.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.aibabel.message.utiles.Constant;

/**
 *==========================================================================================
 * @Author： 张文颖
 *
 * @Date：2019/4/18
 *
 * @Desc：处理消息
 *==========================================================================================
 */
public class MessageReceiver extends BroadcastReceiver {



    private static MessageListener listener;


    @Override
    public void onReceive(Context context, Intent intent) {

//        if(intent.getAction()== Constant.ACTION_MESSAGE){
//            listener.getMessage(intent);
//        }

    }


    public static void setLisenter(MessageListener listener) {
        MessageReceiver.listener = listener;
    }





}
