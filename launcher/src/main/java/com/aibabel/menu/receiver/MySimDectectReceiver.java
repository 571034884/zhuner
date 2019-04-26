package com.aibabel.menu.receiver;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import com.aibabel.menu.activity.MainActivity;
import com.aibabel.menu.utils.LogUtil;


public class MySimDectectReceiver extends BroadcastReceiver {

    private final static String ACTION_SIM_STATE_CHANGED = "android.intent.action.SIM_STATE_CHANGED";
    private final static int SIM_VALID = 0;
    private final static int SIM_INVALID = 1;
    private int simState = SIM_INVALID;

    public int getSimState() {
        return simState;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        final String action = intent.getAction();
        LogUtil.e("hjs","++++++Action:"+action);
//        if("android.intent.action.SIM_STATE_CHANGED".equals(action)){
//            LogUtil.e("hjs","++++++Action:"+action);
//            MainActivity.loopHandler.sendEmptyMessage(110);
//            //setSimInfoByChange(context,intent);
//        }


        if (intent.getAction().equals(ACTION_SIM_STATE_CHANGED)) {
            TelephonyManager tm = (TelephonyManager)context.getSystemService(Service.TELEPHONY_SERVICE);
            int state = tm.getSimState();
            LogUtil.e("hjs","++++++state:"+state);
            switch (state) {
                case TelephonyManager.SIM_STATE_READY :
                    simState = SIM_VALID;
                    break;
                case TelephonyManager.SIM_STATE_ABSENT :

                    if(MainActivity.loopHandler!=null)MainActivity.loopHandler.sendEmptyMessage(110);
                    break;
                case TelephonyManager.SIM_STATE_UNKNOWN :
                case TelephonyManager.SIM_STATE_PIN_REQUIRED :
                case TelephonyManager.SIM_STATE_PUK_REQUIRED :
                case TelephonyManager.SIM_STATE_NETWORK_LOCKED :
                default:
                    simState = SIM_INVALID;
                    break;
            }
        }
    }




//    private void setSimInfoByChange(Context context,Intent intent){
//        //subid 卡ID
//        int subId = intent.getIntExtra(PhoneConstants.SUBSCRIPTION_KEY, SubscriptionManager.INVALID_SUBSCRIPTION_ID);
//        //soltId 卡槽ID 0：卡槽一  1：卡槽二
//        int soltId = intent.getIntExtra("slot",SOLTID);
//        Log.d(TAG, " #######subId:"+subId);
//        Bundle mBundle = intent.getExtras();//从中可以看到intent的发送过来的数据
//
//        Log.d(TAG, " #######mBundle:"+mBundle.toString());
//        String stateExtra = intent.getStringExtra(IccCardConstants.INTENT_KEY_ICC_STATE);
//        if (stateExtra!=null) {
//            if (stateExtra.equals("ABSENT")) { //卡拔出状态
//                SIM_STATE = 1;
//                SimInfo = context.getString(R.string.cell_broadcast_widget_no_sim_card, "No SIM Cards");
//            } else if (stateExtra.equals("READY") ||  //卡正常状态  即可以读出卡信息
//                    stateExtra.equals("IMSI") ||
//                    stateExtra.equals("LOADED") ){
//                SIM_STATE = 0;
//            }else if(stateExtra.equals("LOCKED") || //卡被锁状态
//                    stateExtra.equals("NOT_READY") ||
//                    stateExtra.equals("PIN")||
//                    stateExtra.equals("PUK")){
//                SIM_STATE = 2;
//                SimInfo = context.getString(R.string.cell_broadcast_widget_no_service,"No Services");
//            }
//        }
//        Log.d(TAG, " #######stateExtra:"+stateExtra+"+++++SimInfo:"+SimInfo);
//        Log.d(TAG, " #######soltId:"+soltId);
//    }
}
