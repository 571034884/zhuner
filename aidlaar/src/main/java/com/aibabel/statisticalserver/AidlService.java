package com.aibabel.statisticalserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import com.aibabel.aidlaar.StatisticsData;
import com.aibabel.aidlaar.StatisticsDataController;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.base.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AidlService extends Service {

    private static String TAG = "AidlService";

    //以下是要传到服务器的统计数据
    private List<StatisticsData> sendingList = new ArrayList<>();
    private List<StatisticsData> failureList = new ArrayList<>();
//    private StatisticsData statisticsData;
    //是否正在上传标志
    private boolean isSending = false;

    public AidlService() {
        initDataList();
    }

    public void initDataList() {
        sendingList.add(new StatisticsData());
    }

    public void clearDataList(boolean success) {
        sendingList.clear();
        if (!success) {
            sendingList.addAll(failureList);
            failureList.clear();
        }
    }

    StatisticsDataController.Stub controller = new StatisticsDataController.Stub() {


        @Override
        public void addPath(String appName, String appVersion, String pageName, long entryTime, long exitTime, int interactions, String param) throws RemoteException {
            try {
                if (sendingList.get(sendingList.size()-1).getPath() == null) {
                    List<StatisticsData.PathBean> pathBeanList = new ArrayList<>();
                    sendingList.get(sendingList.size()-1).setPath(pathBeanList);
                }
                int pathSize = sendingList.get(sendingList.size()-1).getPath().size();
                //没有路径 或 与上一次路径不同则添加新路径
                if (pathSize < 1 || !sendingList.get(sendingList.size()-1).getPath().get(pathSize - 1).getAn().equals(appName)) {
                    sendingList.get(sendingList.size()-1).getPath().add(buildOnePath(appName, appVersion));
                    pathSize++;
                }
                //将页面填入最后一次的路径中
                sendingList.get(sendingList.size()-1).getPath().get(pathSize - 1).getC().add(buildOnePage(pageName, entryTime, exitTime, interactions, param));
            }catch (Exception e){
                e.printStackTrace();
            }


        }

        @Override
        public void addEvent(int eventId, long time, String param) throws RemoteException {
            try {
                if (sendingList.get(sendingList.size()-1).getEvent() == null) {
                    List<StatisticsData.EventBean> eventBeanList = new ArrayList<>();
                    sendingList.get(sendingList.size()-1).setEvent(eventBeanList);
                }
                StatisticsData.EventBean eventBean = new StatisticsData.EventBean();
                eventBean.setEid(eventId);
                eventBean.setEt(time);
                eventBean.setP(param);
                sendingList.get(sendingList.size()-1).getEvent().add(eventBean);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        @Override
        public void addNotify(int notifyId, long time, int type, String param) throws RemoteException {
            if (sendingList.get(sendingList.size()-1).getNotify() == null) {
                List<StatisticsData.NotifyBean> notifyBeanList = new ArrayList<>();
                sendingList.get(sendingList.size()-1).setNotify(notifyBeanList);
            }
            //没有通知 或 与上一次路径不同则添加新通知
            int notifySize = sendingList.get(sendingList.size()-1).getNotify().size();
            if (notifySize < 1 || sendingList.get(sendingList.size()-1).getNotify().get(notifySize - 1).getNid() != notifyId) {
                sendingList.get(sendingList.size()-1).getNotify().add(buildOneNotify(notifyId, time));
                notifySize++;
            }
            //将页面填入最后一次的通知中
            sendingList.get(sendingList.size()-1).getNotify().get(notifySize - 1).getC().add(buildOneContent(type, param));
        }

        @Override
        public void sendDataToHost(String url, Map urlparams) throws RemoteException {
            if (isSending) {
                failureList.addAll(sendingList);
                clearDataList(false);
            } else if (!isSending && sendingList.size() < 1) {
                clearDataList(true);
                initDataList();
            } else {
                post(url, urlparams);
            }
        }

        @Override
        public void getAllData() {
            try {
                Log.e(TAG, "getAllData: " + FastJsonUtil.changObjectToString(sendingList));
            }catch (Exception e){
            }

        }

        @Override
        public void saveSharePreference(String key, String value) throws RemoteException {
            SharePrefUtil.put(getApplicationContext(), key, value);
        }

        @Override
        public String getStringSP(String key, String defaultValue) throws RemoteException {
            return (String) SharePrefUtil.get(getApplicationContext(), key, defaultValue);
        }

        @Override
        public boolean getBooleanSP(String key, boolean defaultValue) throws RemoteException {
            return (boolean) SharePrefUtil.get(getApplicationContext(), key, defaultValue);
        }

        @Override
        public int getIntSP(String key, int defaultValue) throws RemoteException {
            return (int) SharePrefUtil.get(getApplicationContext(), key, defaultValue);
        }

        @Override
        public float getFloatSP(String key, float defaultValue) throws RemoteException {
            return (float) SharePrefUtil.get(getApplicationContext(), key, defaultValue);
        }

        @Override
        public long getLongSP(String key, long defaultValue) throws RemoteException {
            return (long) SharePrefUtil.get(getApplicationContext(), key, defaultValue);
        }


    };

    @Override
    public IBinder onBind(Intent intent) {
        return controller;
    }

    public void post(String url, Map<String, String> postMap) {
        PostRequest<String> postRequest = OkGo.<String>post(url).tag("JonerLogPush");
//        PostRequest<String> postRequest = OkGo.<String>post("http://39.107.238.111:7001/v1/ddot/JonerLogPush").tag("JonerLogPush");
        postRequest.params("data", FastJsonUtil.changObjectToString(sendingList));
        //公共参数
        for (Map.Entry<String, String> entry : postMap.entrySet()) {
            postRequest.params(entry.getKey(), entry.getValue());
        }
//        postRequest.params("sv", "PL01-L01");
//        postRequest.params("sn", "987654321000011");
//        postRequest.params("sl", "zh_CN");
//        postRequest.params("av", "1.1.1");
//        postRequest.params("no", "1234");
//        postRequest.params("lat", "1111");
//        postRequest.params("lng", "11");

        postRequest.execute(new StringCallback() {
            @Override
            public void onStart(Request<String, ? extends Request> request) {
                super.onStart(request);
                isSending = true;
            }

            @Override
            public void onSuccess(Response<String> response) {
                clearDataList(true);
                initDataList();
                Log.e(TAG, "onSuccess: " + response.body());
            }

            @Override
            public void onError(Response<String> response) {
                Log.e(TAG, "onError: " + response.getException().getMessage());
                failureList.addAll(sendingList);
                clearDataList(false);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                isSending = false;
            }
        });
    }

    public StatisticsData.PathBean buildOnePath(String appName, String appVersion) {
        StatisticsData.PathBean pathBean = new StatisticsData.PathBean();
        pathBean.setAn(appName);
        pathBean.setAv(appVersion);
        List<StatisticsData.PathBean.CBean> cBeanList = new ArrayList<>();
        pathBean.setC(cBeanList);
        return pathBean;
    }

    public StatisticsData.PathBean.CBean buildOnePage(String pageName, long entryTime, long exitTime, int interactions, String param) {
        StatisticsData.PathBean.CBean bean = new StatisticsData.PathBean.CBean();
        bean.setPn(pageName);
        bean.setIt(entryTime);
        bean.setOt(exitTime);
        bean.setI(interactions);
        bean.setP(param);
        return bean;
    }
    public StatisticsData.NotifyBean buildOneNotify(int notifyId, long time) {
        StatisticsData.NotifyBean notifyBean = new StatisticsData.NotifyBean();
        notifyBean.setNid(notifyId);
        notifyBean.setTi(time);
        List<StatisticsData.NotifyBean.CBeanX> cBeanXList = new ArrayList<>();
        notifyBean.setC(cBeanXList);
        return notifyBean;
    }

    public StatisticsData.NotifyBean.CBeanX buildOneContent(int type, String param) {
        StatisticsData.NotifyBean.CBeanX cBeanX = new StatisticsData.NotifyBean.CBeanX();
        cBeanX.setT(type);
        cBeanX.setP(param);
        return cBeanX;
    }

}
