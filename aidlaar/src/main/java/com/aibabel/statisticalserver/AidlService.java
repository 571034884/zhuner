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
    private List<List<StatisticsData>> sendingList = new ArrayList<>();
    private List<List<StatisticsData>> failureList = new ArrayList<>();
    private List<StatisticsData> statisticsDataList;
    //是否正在上传标志
    private boolean isSending = false;

    public AidlService() {
        initDataList();
    }

    public void initDataList() {
        statisticsDataList = new ArrayList<>();
        sendingList.add(statisticsDataList);
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
        public void addPath(String appName, String appVersion, String pageName, long entryTime, long exitTime, int interactions, String keyWord) throws RemoteException {
            int indexApp = -1;
            for (int i = 0; i < statisticsDataList.size(); i++) {
                if (statisticsDataList.get(i).getAppName().equals(appName) && statisticsDataList.get(i).getAppVersion().equals(appVersion)) {
                    indexApp = i;
                    break;
                }
            }
            if (indexApp == -1) {
                StatisticsData data = new StatisticsData();
                List<StatisticsData.PathBean> pathBeanList = new ArrayList<>();
                StatisticsData.PathBean pathBean = new StatisticsData.PathBean();
                List<StatisticsData.PathBean.PathContentBean> pathContentBeanList = new ArrayList<>();
                StatisticsData.PathBean.PathContentBean contentBean = new StatisticsData.PathBean.PathContentBean();
                contentBean.setEntry(entryTime);
                contentBean.setExit(exitTime);
                contentBean.setInteractionTimes(interactions);
                if (TextUtils.equals(keyWord, "")) contentBean.setKeyWord(keyWord);
                pathContentBeanList.add(contentBean);
                pathBean.setName(pageName);
                pathBean.setContent(pathContentBeanList);
                pathBeanList.add(pathBean);
                data.setAppName(appName);
                data.setAppVersion(appVersion);
                data.setPath(pathBeanList);
                statisticsDataList.add(data);
                Log.e(TAG, "addPath: " + FastJsonUtil.changListToString(statisticsDataList));
                return;
            }
            int indexActivity = -1;
            for (int j = 0; statisticsDataList.get(indexApp).getPath() != null && j < statisticsDataList.get(indexApp).getPath().size(); j++) {
                if (statisticsDataList.get(indexApp).getPath().get(j).getName().equals(pageName)) {
                    indexActivity = j;
                    break;
                }
            }
            if (indexActivity == -1) {
                StatisticsData.PathBean pathBean = new StatisticsData.PathBean();
                List<StatisticsData.PathBean.PathContentBean> pathContentBeanList = new ArrayList<>();
                StatisticsData.PathBean.PathContentBean contentBean = new StatisticsData.PathBean.PathContentBean();
                contentBean.setEntry(entryTime);
                contentBean.setExit(exitTime);
                contentBean.setInteractionTimes(interactions);
                if (TextUtils.equals(keyWord, "")) contentBean.setKeyWord(keyWord);
                pathContentBeanList.add(contentBean);
                pathBean.setName(pageName);
                pathBean.setContent(pathContentBeanList);
                if (statisticsDataList.get(indexApp).getPath() != null) {
                    statisticsDataList.get(indexApp).getPath().add(pathBean);
                } else {
                    List<StatisticsData.PathBean> pathBeanList = new ArrayList<>();
                    pathBeanList.add(pathBean);
                    statisticsDataList.get(indexApp).setPath(pathBeanList);
                }
                Log.e(TAG, "addPath: " + FastJsonUtil.changListToString(statisticsDataList));
                return;
            }
            StatisticsData.PathBean.PathContentBean contentBean = new StatisticsData.PathBean.PathContentBean();
            contentBean.setEntry(entryTime);
            contentBean.setExit(exitTime);
            contentBean.setInteractionTimes(interactions);
            if (TextUtils.equals(keyWord, "")) contentBean.setKeyWord(keyWord);
            statisticsDataList.get(indexApp).getPath().get(indexActivity).getContent().add(contentBean);

            Log.e(TAG, "addPath: " + FastJsonUtil.changListToString(statisticsDataList));
        }

        @Override
        public void addEvent(String appName, String appVersion, String eventName, long time, String descirbe, String keyWord) throws RemoteException {
            int indexApp = -1;
            for (int i = 0; i < statisticsDataList.size(); i++) {
                if (statisticsDataList.get(i).getAppName().equals(appName) && statisticsDataList.get(i).getAppVersion().equals(appVersion)) {
                    indexApp = i;
                    break;
                }
            }
            if (indexApp == -1) {
                StatisticsData data = new StatisticsData();
                List<StatisticsData.EventBean> eventBeanList = new ArrayList<>();
                StatisticsData.EventBean eventBean = new StatisticsData.EventBean();
                List<StatisticsData.EventBean.EventContentBean> eventContentBeanList = new ArrayList<>();
                StatisticsData.EventBean.EventContentBean contentBean = new StatisticsData.EventBean.EventContentBean();
                contentBean.setTime(time);
                if (!TextUtils.equals(descirbe, "")) contentBean.setDescirbe(descirbe);
                if (!TextUtils.equals(keyWord, "")) contentBean.setKeyWord(keyWord);
                eventContentBeanList.add(contentBean);
                eventBean.setName(eventName);
                eventBean.setContent(eventContentBeanList);
                eventBeanList.add(eventBean);
                data.setAppName(appName);
                data.setAppVersion(appVersion);
                data.setEvent(eventBeanList);
                statisticsDataList.add(data);
                Log.e(TAG, "addEvent:" + FastJsonUtil.changListToString(statisticsDataList));
                return;
            }
            int indexEvent = -1;
            for (int j = 0; statisticsDataList.get(indexApp).getEvent() != null && j < statisticsDataList.get(indexApp).getEvent().size(); j++) {
                if (statisticsDataList.get(indexApp).getEvent().get(j).getName().equals(eventName)) {
                    indexEvent = j;
                    break;
                }
            }
            if (indexEvent == -1) {
                StatisticsData.EventBean eventBean = new StatisticsData.EventBean();
                List<StatisticsData.EventBean.EventContentBean> eventContentBeanList = new ArrayList<>();
                StatisticsData.EventBean.EventContentBean contentBean = new StatisticsData.EventBean.EventContentBean();
                contentBean.setTime(time);
                if (!TextUtils.equals(descirbe, "")) contentBean.setDescirbe(descirbe);
                if (!TextUtils.equals(keyWord, "")) contentBean.setKeyWord(keyWord);
                eventContentBeanList.add(contentBean);
                eventBean.setName(eventName);
                eventBean.setContent(eventContentBeanList);
                if (statisticsDataList.get(indexApp).getEvent() != null) {
                    statisticsDataList.get(indexApp).getEvent().add(eventBean);
                } else {
                    List<StatisticsData.EventBean> eventBeanList = new ArrayList<>();
                    eventBeanList.add(eventBean);
                    statisticsDataList.get(indexApp).setEvent(eventBeanList);
                }
                Log.e(TAG, "addEvent:" + FastJsonUtil.changListToString(statisticsDataList));
                return;
            }
            StatisticsData.EventBean.EventContentBean contentBean = new StatisticsData.EventBean.EventContentBean();
            contentBean.setTime(time);
            if (!TextUtils.equals(descirbe, "")) contentBean.setDescirbe(descirbe);
            if (!TextUtils.equals(keyWord, "")) contentBean.setKeyWord(keyWord);
            statisticsDataList.get(indexApp).getEvent().get(indexEvent).getContent().add(contentBean);

            Log.e(TAG, "addEvent:" + FastJsonUtil.changListToString(statisticsDataList));
        }

        @Override
        public void addNotify(String appName, String appVersion, int type, long time, String scope, String descirbe) throws RemoteException {
            int indexApp = -1;
            for (int i = 0; i < statisticsDataList.size(); i++) {
                if (statisticsDataList.get(i).getAppName().equals(appName) && statisticsDataList.get(i).getAppVersion().equals(appVersion)) {
                    indexApp = i;
                    break;
                }
            }
            if (indexApp == -1) {
                StatisticsData data = new StatisticsData();
                List<StatisticsData.NotifyBean> notifyBeanList = new ArrayList<>();
                StatisticsData.NotifyBean notifyBean = new StatisticsData.NotifyBean();
                List<StatisticsData.NotifyBean.NotifyContentBean> notifyContentBeanList = new ArrayList<>();
                StatisticsData.NotifyBean.NotifyContentBean contentBean = new StatisticsData.NotifyBean.NotifyContentBean();
                contentBean.setTime(time);
                contentBean.setDescirbe(descirbe);
                contentBean.setScope(scope);
                contentBean.setConsulted(false);
                notifyContentBeanList.add(contentBean);
                notifyBean.setType(type);
                notifyBean.setContent(notifyContentBeanList);
                notifyBeanList.add(notifyBean);
                data.setAppName(appName);
                data.setAppVersion(appVersion);
                data.setNotify(notifyBeanList);
                statisticsDataList.add(data);
                return;
            }
            int indexType = -1;
            for (int j = 0; statisticsDataList.get(indexApp).getNotify() != null && j < statisticsDataList.get(indexApp).getNotify().size(); j++) {
                if (statisticsDataList.get(indexApp).getNotify().get(j).getType() == type) {
                    indexType = j;
                    break;
                }
            }
            if (indexType == -1) {
                StatisticsData.NotifyBean notifyBean = new StatisticsData.NotifyBean();
                List<StatisticsData.NotifyBean.NotifyContentBean> notifyContentBeanList = new ArrayList<>();
                StatisticsData.NotifyBean.NotifyContentBean contentBean = new StatisticsData.NotifyBean.NotifyContentBean();
                contentBean.setTime(time);
                contentBean.setDescirbe(descirbe);
                contentBean.setScope(scope);
                contentBean.setConsulted(false);
                notifyContentBeanList.add(contentBean);
                notifyBean.setType(type);
                notifyBean.setContent(notifyContentBeanList);
                if (statisticsDataList.get(indexApp).getNotify() != null) {
                    statisticsDataList.get(indexApp).getNotify().add(notifyBean);
                } else {
                    List<StatisticsData.NotifyBean> notifyBeanList = new ArrayList<>();
                    notifyBeanList.add(notifyBean);
                    statisticsDataList.get(indexApp).setNotify(notifyBeanList);
                }
                return;
            }
            StatisticsData.NotifyBean.NotifyContentBean contentBean = new StatisticsData.NotifyBean.NotifyContentBean();
            contentBean.setTime(time);
            contentBean.setDescirbe(descirbe);
            contentBean.setScope(scope);
            contentBean.setConsulted(false);
            statisticsDataList.get(indexApp).getNotify().get(indexType).getContent().add(contentBean);
        }

        @Override
        public void setConsultedStatus(String appName, int type, long time) throws RemoteException {
            int indexApp = -1;
            for (int i = 0; i < statisticsDataList.size(); i++) {
                if (statisticsDataList.get(i).getAppName().equals(appName)) {
                    indexApp = i;
                    break;
                }
            }
            int indexType = -1;
            for (int i = 0; indexApp > -1 && i < statisticsDataList.get(indexApp).getNotify().size(); i++) {
                if (statisticsDataList.get(indexApp).getNotify().get(i).getType() == type) {
                    indexType = i;
                    break;
                }
            }
            for (int i = 0; indexType > -1 && i < statisticsDataList.get(indexApp).getNotify().get(indexType).getContent().size(); i++) {
                if (statisticsDataList.get(indexApp).getNotify().get(indexType).getContent().get(i).getTime() == time) {
                    statisticsDataList.get(indexApp).getNotify().get(indexType).getContent().get(i).setConsulted(true);
                    break;
                }
            }
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
        public void getAllData() throws RemoteException {
            Log.e(TAG, "getAllData: " + FastJsonUtil.changListToString(statisticsDataList));
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
        postRequest.params("data", FastJsonUtil.changObjectToString(statisticsDataList));
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
}
