package com.aibabel.baselibrary.impl;

import org.json.JSONArray;
import org.json.JSONObject;

public interface IStatistics {
    /**
     * 添加统计路径：已页面（activity）为单位添加
     * @param appName   app的名字
     * @param appVersion  app版本号
     * @param info      统计数据详情  json格式
     */
       void addPath(String appName, String appVersion, JSONObject info);
}
