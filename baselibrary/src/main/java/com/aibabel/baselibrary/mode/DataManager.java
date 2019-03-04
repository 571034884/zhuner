/*
 * Copyright (C) 2018 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aibabel.baselibrary.mode;
import com.aibabel.baselibrary.impl.IDataManager;
import com.aibabel.baselibrary.utils.SharePrefUtil;
import com.xuexiang.xipc.annotation.ClassName;
import com.xuexiang.xipc.annotation.MethodName;
import com.xuexiang.xipc.annotation.Singleton;

import static com.xuexiang.xipc.XIPC.getContext;

/**
 * 单例类。进行存储 获取
 *
 *
 */
@ClassName("DataManager")
public class DataManager implements IDataManager {

    private static volatile DataManager sInstance = null;

    private String s;
    private boolean b;
    private int i;
    private long l;
    private float f;


    private DataManager() {
        s = "";
        b = false;
        i = 0;
        l = 0;
        f = 0;
    }

    @Singleton
    public static DataManager getInstance() {
        if (sInstance == null) {
            synchronized (DataManager.class) {
                if (sInstance == null) {
                    sInstance = new DataManager();
                }
            }
        }
        return sInstance;
    }

    @MethodName("getString")
    @Override
    public String getString(String key) {
        return SharePrefUtil.getString(getContext(),key,"");
    }

    @MethodName("setSaveString")
    @Override
    public void setSaveString(String key, String value) {
        SharePrefUtil.saveString(getContext(),key,value);
    }

    @MethodName("getBoolean")
    @Override
    public boolean getBoolean(String key) {
        return SharePrefUtil.getBoolean(getContext(),key,false);
    }

    @MethodName("setSaveBoolean")
    @Override
    public void setSaveBoolean(String key, boolean value) {
        SharePrefUtil.saveBoolean(getContext(),key,value);
    }

    @MethodName("getInt")
    @Override
    public int getInt(String key) {
        return SharePrefUtil.getInt(getContext(),key,0);
    }

    @MethodName("setSaveInt")
    @Override
    public void setSaveInt(String key, int value) {
        SharePrefUtil.saveInt(getContext(),key,value);
    }

    @MethodName("getLong")
    @Override
    public long getLong(String key) {
        return SharePrefUtil.getInt(getContext(),key,0);
    }

    @MethodName("setSaveLong")
    @Override
    public void setSaveLong(String key, long value) {
        SharePrefUtil.saveLong(getContext(),key,value);
    }

    @MethodName("getFloat")
    @Override
    public float getFloat(String key) {
        return SharePrefUtil.getFloat(getContext(),key,0);
    }

    @MethodName("setSaveFloat")
    @Override
    public void setSaveFloat(String key, float value) {
        SharePrefUtil.saveFloat(getContext(),key,value);
    }
}
