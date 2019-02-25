// StatisticsDataController.aidl
package com.aibabel.aidlaar;

interface StatisticsDataController {
    /**
     * 发送统计数据到后台
     */
//    void getAllData();//获取全部数据
//    void sendDataToHost(String url,inout Map parameters);//先后台发送一个统计周期的数据
//    void setAppVersionName(String appVersionName);//設置版本號
//    void addPathBean(String appName, String pageName, String time, int interactions,String param);//添加路径统计
//    void addEventsBean(String appName, String eventName,inout Map parameters);//添加事件统计
//    void addNotifyBean(String scope, int type, String msg, long time);//添加通知统计
//    void setConsultedStatus(String scope, int type, long time);//修改查阅状态

    void addPath(String appName, String appVersion, String pageName, long entryTime, long exitTime, int interactions, String keyWord);//添加路径统计
    void addEvent(String appName, String appVersion, String eventName, long time, String descirbe, String keyWord);//添加事件统计
    void addNotify(String appName, String appVersion, int type, long time, String scope, String descirbe);//添加通知统计
    void setConsultedStatus(String appName, int type, long time);//修改查阅状态
    void sendDataToHost(String url,inout Map urlparams);//先后台发送一个统计周期的数据
    void getAllData();//获取全部数据

    void saveSharePreference(String key, String value);//保存配置文件
    String getStringSP(String key, String defaultValue);//保存配置文件
    boolean getBooleanSP(String key, boolean defaultValue);//保存配置文件
    int getIntSP(String key, int defaultValue);//保存配置文件
    float getFloatSP(String key, float defaultValue);//保存配置文件
    long getLongSP(String key, long defaultValue);//保存配置文件
}
