package com.aibabel.translate.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.aibabel.translate.app.BaseApplication;
import com.aibabel.translate.offline.ChangeOffline;
import com.aibabel.translate.utils.CommonUtils;
import com.aibabel.translate.utils.L;
import com.aibabel.translate.utils.SystemUtil;
import com.aibabel.translate.utils.ThreadPoolManager;

public class ReleaseModelReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //有网  黑屏 模型没有正在使用
        L.e("收到切换广播=========================");
        if (!SystemUtil.isScreenLocked(context)) {
            L.e("是黑屏"+SystemUtil.isScreenLocked(context));
            if (CommonUtils.isAvailable()) {
                L.e("有网络============");
                if (BaseApplication.isTran) {
                    //没有正在使用模型
                    if (ThreadPoolManager.getInstance().getCarryNum()==0) {
                        L.e("释放=======================");
                        ChangeOffline.getInstance().releaseOnline();
                    }

                }
            }
        }
    }
}
