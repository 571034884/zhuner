package com.aibabel.speech.tcp;

import android.content.Context;
import android.util.Log;

import com.aibabel.speech.app.BaseApplication;
import com.aibabel.speech.properites.Constants;
import com.aibabel.speech.util.HostUtil;
import com.aibabel.speech.util.L;
import com.xuhao.android.libsocket.sdk.ConnectionInfo;
import com.xuhao.android.libsocket.sdk.OkSocketOptions;
import com.xuhao.android.libsocket.sdk.bean.IPulseSendable;
import com.xuhao.android.libsocket.sdk.bean.ISendable;
import com.xuhao.android.libsocket.sdk.bean.OriginalData;
import com.xuhao.android.libsocket.sdk.connection.IConnectionManager;
import com.xuhao.android.libsocket.sdk.connection.NoneReconnect;
import com.xuhao.android.libsocket.sdk.connection.interfacies.ISocketActionListener;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.xuhao.android.libsocket.sdk.OkSocket.open;

/**
 * 作者：SunSH on 2018/5/28 15:04
 * 功能：
 * 版本：1.0
 */
public class SocketManger implements ISocketActionListener {

    private String TAG = "SocketManger";

    public void jsetIP(String IP) {
        this.IP = IP;
    }

    public void setPORT(int PORT) {
        this.PORT = PORT;
    }

    private String IP = "abroad.api.function.aibabel.cn";

    public String getIP() {
        return IP;
    }

    public int getPORT() {
        return PORT;
    }

    private  int PORT = 5005;
    private ConnectionInfo mInfo;//连接信息服务类
    private OkSocketOptions mOkOptions;//OkSocket参数配置
    private IConnectionManager mManager;//连接管理
    private OnReceiveListener listener;
    private OnReconnectListener onReconnectListener;
    private boolean isResponseTimeout = false;
    private List<ISendable> reSendDataList = new ArrayList<>();

    private static SocketManger socketManger;

    public void setIP(String IP) {
        this.IP = IP;
    }

    /**
     * 数据发送成功回调
     */
    public interface OnReceiveListener {
        void onSuccess(int flag, String json);

        void onError(int flag);//1异常断开，2响应超时断开
    }

    /**
     * 在没连接的时候发送的数据手动监听连接回调，再次发送当次数据
     */
    public interface OnReconnectListener {
        void reSend(ISendable data);
    }


    private SocketManger() {
    }

    public static SocketManger getInstance() {
        if (socketManger == null) {
            socketManger = new SocketManger();
        }
        return socketManger;
    }

    /**
     * 设置监听
     *
     * @param onReceiveListener
     */
    public void setOnReceiveListener(OnReceiveListener onReceiveListener) {
        listener = onReceiveListener;
    }

    /**
     * 设置监听
     *
     * @param onReconnectListener
     */
    public void setOnReconnectListener(OnReconnectListener onReconnectListener) {
        this.onReconnectListener = onReconnectListener;
    }

    /**
     * 初始化连接
     */
    public void initConfig() {
        mInfo = new ConnectionInfo(IP, PORT);
        mOkOptions = new OkSocketOptions.Builder()
                .setReconnectionManager(new NoneReconnect())
                .setWritePackageBytes(1024)
                .build();
        mManager = open(mInfo).option(mOkOptions);

    }
    public void reInitConfig(String ip,int port) {
        mInfo = new ConnectionInfo(ip, port);
        mOkOptions = new OkSocketOptions.Builder()
                .setReconnectionManager(new NoneReconnect())
                .setWritePackageBytes(1024)
                .build();
        mManager = open(mInfo).option(mOkOptions);

    }



    public IConnectionManager connect() {
//        if (mManager == null) {
            initConfig();
//        }
        mManager.registerReceiver(this);
        if (!mManager.isConnect()) {
            mManager.connect();
        }
        return mManager;
    }

    public void sendMessage(final ISendable data) {
        Log.e(TAG, "sendMessage进入" + mManager + "    " + mManager.isConnect()+"=======IP:"+mManager.getConnectionInfo().getIp());
        if (mManager != null && mManager.isConnect()) {
            mManager.send(data);
            if (data.parse()[4] == 51)
                L.e("-------------------------"+getIP());
            Log.i("sendCmd", "长连接发送数据:" + data.parse().length);
            if (data.parse()[4] == 51 || data.parse()[4] == 53 || data.parse()[4] == 52)
                Log.i("sendCmd", "" + data.parse()[4]);
        } else {
            reSendDataList.add(data);
            connect();
        }
    }

    /**
     * 关闭tcp
     */
    public void disconnect() {
        try {
            if (mManager != null) {
                mManager.unRegisterReceiver(this);
                if (   mManager.isConnect()) {
                    mManager.disconnect();
                }


            }
        } catch (Exception e) {

        }

    }

    /**
     * Socket通讯IO线程的启动<br>
     * 该方法调用后IO线程将会正常工作
     *
     * @param context
     * @param action
     */
    @Override
    public void onSocketIOThreadStart(Context context, String action) {

    }

    /**
     * Socket通讯IO线程的关闭<br>
     * 该方法调用后IO线程将彻底死亡
     *
     * @param context
     * @param action
     * @param e       关闭时将会产生的异常,IO线程一般情况下都会有异常产生
     */
    @Override
    public void onSocketIOThreadShutdown(Context context, String action, Exception e) {
        Log.e(TAG, e.getMessage());
    }

    /**
     * Socket通讯读取到消息后的响应   if(mManager != null){
     * //            //喂狗操作,持续发送心跳包
     * //            mManager.getPulseManager().feed();
     * //        }
     *
     * @param context
     * @param action
     * @param data    原始的读取到的数据{@link OriginalData}
     */
    @Override
    public void onSocketReadResponse(Context context, ConnectionInfo info, String action, OriginalData data) {
        if (mManager != null) {
            //喂狗操作,持续发送心跳包
            mManager.getPulseManager().feed();
        }
        int flag = data.getBodyBytes()[0];
        byte[] result = new byte[data.getBodyBytes().length - 1];
        System.arraycopy(data.getBodyBytes(), 1, result, 0, data.getBodyBytes().length - 1);
        String json = new String(result, Charset.forName("utf-8"));
        Log.e(TAG, "接收到的数据：flag = " + flag + " 数据：" + json);
        if (listener != null)
            listener.onSuccess(flag, json);
    }

    /**
     * Socket通讯写出后的响应回调
     *
     * @param context
     * @param action
     * @param data    写出的数据{@link ISendable}
     */
    @Override
    public void onSocketWriteResponse(Context context, ConnectionInfo info, String action, ISendable data) {

    }

    /**
     * Socket心跳发送后的回调<br>
     * 心跳发送是一个很特殊的写操作<br>
     * 该心跳发送后将不会回调{@link #onSocketWriteResponse(Context, ConnectionInfo, String, ISendable)}方法
     *
     * @param context
     * @param info    这次连接的连接信息
     * @param data    心跳发送数据{@link IPulseSendable}
     */
    @Override
    public void onPulseSend(Context context, ConnectionInfo info, IPulseSendable data) {

    }

    /**
     * Socket断开后进行的回调<br>
     * 当Socket彻底断开后,系统会回调该方法
     *
     * @param context
     * @param info    这次连接的连接信息
     * @param action
     * @param e       Socket断开时的异常信息,如果是正常断开(调用disconnect()),异常信息将为null.使用e变量时应该进行判空操作
     */
    @Override
    public void onSocketDisconnection(Context context, ConnectionInfo info, String action, Exception e) {
        Log.e(TAG, "连接已断开！");

    }

    /**
     * 当Socket连接建立成功后<br>
     * 系统会回调该方法,此时有可能读写线程还未启动完成,不过不会影响大碍<br>
     * 当回调此方法后,我们可以认为Socket连接已经建立完成,并且读写线程也初始化完
     *
     * @param context
     * @param info    这次连接的连接信息
     * @param action
     */
    @Override
    public void onSocketConnectionSuccess(Context context, ConnectionInfo info, String action) {
        Log.e(TAG, "send连接成功，开始发送心跳包");
        isResponseTimeout = false;
        while (reSendDataList != null && reSendDataList.size() > 0) {
            sendMessage(reSendDataList.get(0));
            reSendDataList.remove(0);
        }
    }

    /**
     * 当Socket连接失败时会进行回调<br>
     * 建立Socket连接,如果服务器出现故障,网络出现异常都将导致该方法被回调<br>
     * 系统回调此方法时,IO线程均未启动.如果IO线程启动将会回调{@link #onSocketDisconnection(Context, ConnectionInfo, String, Exception)}
     *
     * @param context
     * @param info    这次连接的连接信息
     * @param action
     * @param e       连接未成功建立的错误原因
     */
    @Override
    public void onSocketConnectionFailed(Context context, ConnectionInfo info, String action, Exception e) {
        Log.e(TAG, "位置异常:" + e);
        if (e != null) {
            Log.e(TAG, "异常断开:" + e.getMessage());
            if (listener != null)
                listener.onError(1);
        } else {
            Log.e(TAG, "正常断开");
            if (isResponseTimeout) {
                if (listener != null)
                    listener.onError(2);
            }
        }
    }

    /**
     * 设置响应超时时间
     *
     * @param timeout
     */
    public void setResponseTimeout(long timeout) {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                isResponseTimeout = true;
                disconnect();
            }
        };
        timer.schedule(timerTask, timeout);
    }


}
