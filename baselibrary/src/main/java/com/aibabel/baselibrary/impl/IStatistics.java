package com.aibabel.baselibrary.impl;

import com.aibabel.baselibrary.mode.PageUnit;
import com.xuexiang.xipc.annotation.ClassName;
import com.xuexiang.xipc.annotation.MethodName;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;

@ClassName("IStatistics")
public interface IStatistics {
    /**
     * 添加统计路径：已页面（activity）为单位添加
     * @param appName   app的名字
     * @param appVersion  app版本号
     * @param info      统计数据详情  json格式
     */
       @MethodName("addPath")
       void addPath(String appName, String appVersion, String info);
       @MethodName("createUploadData")
       String createUploadData(String order_id);
       @MethodName("addIndependentEvent")
       void addIndependentEvent(String id, HashMap<String , Serializable> map);
}
