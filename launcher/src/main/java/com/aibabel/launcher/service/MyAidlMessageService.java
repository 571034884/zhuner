package com.aibabel.launcher.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.aibabel.baselibrary.utils.SharePrefUtil;
import com.aibabel.launcher.utils.LogUtil;
import com.aibabel.menu.IMyAidl_Order_Interface;

/**
 *  hjs 2019/02/08
 */
public class MyAidlMessageService extends Service {
    private static final String order_oid  = "order_oid";

    IMyAidl_Order_Interface.Stub mBinder = new IMyAidl_Order_Interface.Stub(){

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public String AIDL_getOid() throws RemoteException {
            LogUtil.e("order_id==============");
            String order_id =  SharePrefUtil.getString(getBaseContext(),order_oid,"");
            LogUtil.e("order_id="+order_id);
            if(TextUtils.isEmpty(order_id)){
                return null;
            }
            return order_id;
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.e("执行了onBind()");
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.e("执行了onCreat()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.e("执行了onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e("执行了onDestory()");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtil.e("执行了onUnbind()");
        return super.onUnbind(intent);
    }
}
