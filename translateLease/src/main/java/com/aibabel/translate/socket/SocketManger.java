package com.aibabel.translate.socket;

import android.content.Context;
import android.util.Log;

import com.aibabel.translate.R;
import com.aibabel.translate.utils.CommonUtils;
import com.aibabel.translate.utils.Constant;
import com.aibabel.translate.utils.L;
import com.xuhao.android.libsocket.sdk.ConnectionInfo;
import com.xuhao.android.libsocket.sdk.OkSocketOptions;
import com.xuhao.android.libsocket.sdk.bean.IPulseSendable;
import com.xuhao.android.libsocket.sdk.bean.ISendable;
import com.xuhao.android.libsocket.sdk.bean.OriginalData;
import com.xuhao.android.libsocket.sdk.connection.IConnectionManager;
import com.xuhao.android.libsocket.sdk.connection.interfacies.ISocketActionListener;

import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.xuhao.android.libsocket.sdk.OkSocket.open;

//import com.xuhao.android.common.basic.bean.OriginalData;
//import com.xuhao.android.common.interfacies.client.msg.ISendable;
//import com.xuhao.android.libsocket.sdk.client.ConnectionInfo;
//import com.xuhao.android.libsocket.sdk.client.OkSocketOptions;
//import com.xuhao.android.libsocket.sdk.client.action.ISocketActionListener;
//import com.xuhao.android.libsocket.sdk.client.bean.IPulseSendable;
//import com.xuhao.android.libsocket.sdk.client.connection.IConnectionManager;
//import com.xuhao.android.libsocket.sdk.client.connection.NoneReconnect;

/**
 * 作者：SunSH on 2018/5/28 15:04
 * 功能：
 * 版本：1.0
 */
public class SocketManger implements ISocketActionListener {


    public static final String IP = "api.function.aibabel.cn";


    //    public static final int PORT = 5005;
    private ConnectionInfo mInfo;//连接信息服务类
    private OkSocketOptions mOkOptions;//OkSocket参数配置
    private IConnectionManager mManager;//连接管理
    private OnReceiveListener listener;
    private boolean isReadTimeout = false;
    private List<ISendable> reSendDataList = new ArrayList<>();
    private static SocketManger socketManger;
    private final long readTimeOutMills = 5 * 1000;
    private Timer timer;

    /**
     * 数据发送成功回调
     */
    public interface OnReceiveListener {
        void onSuccess(int flag, byte[] result);

        void onError(int flag, String result);//1异常断开，2响应超时断开

        void onFinish();
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
     * 注销监听
     *
     */
    public void unReceiveListener() {
        listener = null;
    }


    /**
     * 初始化连接
     */
    public void initConfig() {
        try {
//            L.e("Host", Constant.HOST + "======" + Constant.PORT);
            L.e("Host", CommonUtils.getTranslateHost() + "======" + Constant.PORT);
            mInfo = new ConnectionInfo(CommonUtils.getTranslateHost(), Constant.PORT);
            mOkOptions = new OkSocketOptions.Builder()
                    .setReconnectionManager(new NoneReconnect())
                    .setConnectTimeoutSecond(5)
                    .setWritePackageBytes(1024)
                    .build();
            mManager = open(mInfo).option(mOkOptions);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public IConnectionManager connect() {
        try {
//            if (mManager == null) {
                initConfig();
//            }
            mManager.registerReceiver(this);
            if (!mManager.isConnect()) {
                mManager.connect();
            }
        } catch (Exception e) {

        }

        return mManager;
    }

    public void sendMessage(final ISendable data) {
//        L.e(TAG, "sendMessage进入" + mManager + "    " + mManager.isConnect());
        if (null != timer)
            timer.cancel();
        if (mManager != null && mManager.isConnect()) {
            mManager.send(data);
            L.e("sendCmd", "发送:" + Arrays.toString(data.parse()));
            if (data.parse()[4] == Constant.RECOGNIZE_END || data.parse()[4] == Constant.TRANSLATE) {
                setReadTimeout(readTimeOutMills);
                L.e("倒计时：", "结束标志发送完毕，倒计时开始。");
            }
        } else {
            reSendDataList.add(data);
            connect();
        }
    }

    /**
     * 关闭tcp
     */
    public void disconnect() {
        if (mManager != null && mManager.isConnect()) {
            mManager.disconnect();
//            mManager.unRegisterReceiver(this);
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

    }

    /**
     * Socket通讯读取到消息后的响应   if(mManager != null){
     * //            //喂狗操作,持续发送心跳包
     * //            mManager.getPulseManager().feed();
     * <p>
     * //        }
     *
     * @param context
     * @param action
     * @param data    原始的读取到的数据{@link OriginalData}
     */
    @Override
    public void onSocketReadResponse(Context context, ConnectionInfo info, String action, OriginalData data) {
        if (null != timer)
            timer.cancel();
        L.e("倒计时", "数据返回，倒计时取消");
        int flag = data.getBodyBytes()[0];
        byte[] result = new byte[data.getBodyBytes().length - 1];
        System.arraycopy(data.getBodyBytes(), 1, result, 0, data.getBodyBytes().length - 1);
        String json = new String(result, Charset.forName("utf-8"));
        Log.e("接收到的数据:", "flag = " + flag + " 数据：" + Arrays.toString(result));
        if (flag == Constant.RESPONSE_ERROR) {
            listener.onError(flag, json);
        }

        listener.onSuccess(flag, result);
        listener.onFinish();
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
//        if(null!=timer)
//        timer.cancel();
        if (e != null) {
            L.e("异常断开:" + e.getMessage());
            listener.onError(1, "");
        } else {
            L.e("正常断开:", "断开成功了！");
//            listener.onError(Constant.TIMEOUT_READ,"读取服务器超时");
        }
        if (mManager != null) {
            mManager.unRegisterReceiver(this);
        }
        listener.onFinish();
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
        L.e("连接成功，开始发送");
        isReadTimeout = false;
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

        if (e instanceof SocketTimeoutException)
            listener.onError(Constant.TIMEOUT_CONNECTION, context.getString(R.string.timeout));
        else {
            listener.onError(Constant.CONNECTION_FAILED, e.getMessage());
        }
        listener.onFinish();

    }

    /**
     * 设置响应超时时间
     *
     * @param timeout
     */
    public void setReadTimeout(long timeout) {
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                isReadTimeout = true;
                L.e("倒计时", "执行倒计时后的任务");
                listener.onError(Constant.TIMEOUT_READ, "");
                listener.onFinish();
                disconnect();
            }
        };
        timer.schedule(timerTask, timeout);

    }
}
