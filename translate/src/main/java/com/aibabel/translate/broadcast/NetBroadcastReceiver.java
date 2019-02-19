package com.aibabel.translate.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.aibabel.translate.app.BaseApplication;
import com.aibabel.translate.offline.ChangeOffline;
import com.aibabel.translate.utils.CommonUtils;
import com.aibabel.translate.utils.Constant;
import com.aibabel.translate.utils.L;
import com.aibabel.translate.utils.OffLineUtils;
import com.aibabel.translate.utils.SystemUtil;
import com.aibabel.translate.utils.ThreadPoolManager;

/**
 * Created by Wuqinghua on 2018/6/23 0023.
 */
public class NetBroadcastReceiver extends BroadcastReceiver {

    private NetListener listener;

    private boolean isRelease=true;
    private Thread loadOrReleaseThread;

    @Override
    public void onReceive(final Context context, Intent intent) {
        if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
           listener.netChanged();

           L.e("网络变化===================="+CommonUtils.isAvailable());
            try {
                if (CommonUtils.isAvailable()) {
                    Log.e("NetBroadcastReceiver","NetBroadcastReceiver");
//                    loadOrReleaseThread = new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            isRelease = true;
//                            try {
//                                Thread.sleep(60 * 1000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//
//                            if (isRelease) {
//
//                                    if (CommonUtils.isAvailable()) {
//                                        L.e("有网络");
//                                        if (!BaseApplication.isTran) {
//                                            //没有正在使用模型
//                                            if (ThreadPoolManager.getInstance().getCarryNum()==0) {
//                                                ChangeOffline.getInstance().releaseOnline();
//                                            }
//
//                                        }
//                                    }
//
//
//                            }
//
//                        }
//
//                    });
//                    loadOrReleaseThread.start();
                } else {
                    isRelease = false;
                    ChangeOffline.getInstance().getOfflineList();
                    ChangeOffline.getInstance().createOrChange();
                }
            } catch (Exception e) {
               Log.e("NetBroadcastReceiver",e.getMessage().toString());
            }

        }

    }


    public void setListener(NetListener listener) {
        this.listener = listener;
    }

    public interface NetListener {
       void netChanged();
    }


}
