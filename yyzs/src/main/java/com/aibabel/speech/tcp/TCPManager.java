package com.aibabel.speech.tcp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.aibabel.speech.util.L;
import com.aibabel.speech.util.NetUtil;
import com.alibaba.fastjson.JSON;

import com.xuhao.android.libsocket.impl.PulseManager;
import com.xuhao.android.libsocket.impl.exceptions.ReadException;
import com.xuhao.android.libsocket.sdk.ConnectionInfo;
import com.xuhao.android.libsocket.sdk.OkSocket;
import com.xuhao.android.libsocket.sdk.OkSocketOptions;
import com.xuhao.android.libsocket.sdk.bean.IPulseSendable;
import com.xuhao.android.libsocket.sdk.bean.ISendable;
import com.xuhao.android.libsocket.sdk.bean.OriginalData;
import com.xuhao.android.libsocket.sdk.connection.DefaultReconnectManager;
import com.xuhao.android.libsocket.sdk.connection.IConnectionManager;
import com.xuhao.android.libsocket.sdk.connection.NoneReconnect;
import com.xuhao.android.libsocket.sdk.connection.interfacies.ISocketActionListener;

import java.nio.charset.Charset;
import java.util.Timer;
import java.util.TimerTask;

public class  TCPManager implements ISocketActionListener {
    private final String IP = "api.function.aibabel.cn";
    private final int PORT = 8888;
    private ConnectionInfo mInfo;//连接信息服务类
    private OkSocketOptions mOkOptions;//OkSocket参数配置
    private IConnectionManager mManager;//连接管理
    private PulseData mPulseData = new PulseData();//心跳
    private OnResponseListener listener;
    private OnConnectionSuccess connectionSuccess;
    private Context context;
    private String TAG = TCPManager.class.getSimpleName().toString();



    private volatile static TCPManager tcpManager;

    private TCPManager() {
    }

    public static TCPManager getInstance() {
        if (tcpManager == null) {
            synchronized (TCPManager.class) {
                if (tcpManager == null) {
                    tcpManager = new TCPManager();
                }
            }
        }
        return tcpManager;
    }

    public static boolean isConnection = false;


    /**
     * 设置监听
     *
     * @param onResponseListener
     */
    public void setOnResponseListener(OnResponseListener onResponseListener) {
        this.listener = onResponseListener;
    }

    /**
     * 设置监听
     *
     * @param onConnectionSuccess
     */
    public void setOnConnectionSuccess(OnConnectionSuccess onConnectionSuccess) {
        connectionSuccess = onConnectionSuccess;
    }


    /**
     * 初始化连接
     */
    public IConnectionManager init() {
        if (mManager != null && mManager.isConnect()) {
            return mManager;
        }
        OkSocket.setBackgroundSurvivalTime(-1);
        mInfo = new ConnectionInfo(IP, PORT);
        mOkOptions = new OkSocketOptions.Builder()
                .setConnectTimeoutSecond(60000)
                .setIOThreadMode(OkSocketOptions.IOThreadMode.DUPLEX)
                .setConnectionHolden(false)
                .setReconnectionManager(new DefaultReconnectManager())
                .build();
        try {
            mManager = OkSocket.open(mInfo).option(mOkOptions);
        }catch (Exception e){
            e.printStackTrace();
        }



        mManager = OkSocket.open(mInfo).option(mOkOptions);
        if (mManager == null) {
            return null;
        }
        mManager.registerReceiver(this);
        if (!mManager.isConnect()) {
            mManager.connect();
        }

        return mManager;
    }

    public void sendMessage(final ISendable data) {
//        L.e(TAG, "sendMessage进入" + mManager + "    " + mManager.isConnect());
        if (mManager != null && mManager.isConnect()) {
            mManager.send(data);


            L.e(TAG, "send发送数据开始");
        } else {
            setOnConnectionSuccess(new OnConnectionSuccess() {
                @Override
                public void onSuccess() {//连接成功后再开始发送数据
                    mManager.send(data);


                    L.e("send", "连接后发送数据开始");
                }
            });
        }
    }


    public void close() {
        if (mManager != null) {
            mManager.unRegisterReceiver(this);
            mManager.disconnect();
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
//        Toast.makeText(context, action, Toast.LENGTH_SHORT).show();
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
        this.context = context;
    }

    /**
     * Socket通讯读取到消息后的响应
     *
     * @param context
     * @param action
     * @param data    原始的读取到的数据{@link OriginalData}
     */
    @Override
    public void onSocketReadResponse(Context context, ConnectionInfo info, String action, OriginalData data) {
        int flag = data.getBodyBytes()[0];
        byte[] result = new byte[data.getBodyBytes().length - 1];
        System.arraycopy(data.getBodyBytes(), 1, result, 0, data.getBodyBytes().length - 1);
        String json = new String(result, Charset.forName("utf-8"));
        L.e("接收到的数据：flag = " + flag + " 数据：" + json);


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
        Log.d(TAG, "连接已断开！");
        isConnection = false;
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
        Log.d("send", "连接成功，开始发送心跳包");

        isConnection = true;
        connectionSuccess.onSuccess();
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
        isConnection = false;


        Log.d("error", e.getMessage());
        if (NetUtil.isNetworkAvailable(context)) {
//            Toast.makeText(context, "服务器连接失败", Toast.LENGTH_SHORT).show();
            L.e("Socket服务器连接失败");
//            listener.onSuccess(-1, "");
        } else {
            Toast.makeText(context, "当前无网络，请稍后重试！", Toast.LENGTH_SHORT).show();
        }
        e.printStackTrace();
    }
}
