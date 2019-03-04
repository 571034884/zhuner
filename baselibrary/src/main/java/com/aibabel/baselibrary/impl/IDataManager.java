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

package com.aibabel.baselibrary.impl;

import com.xuexiang.xipc.annotation.ClassName;
import com.xuexiang.xipc.annotation.MethodName;

/**
 * 数据类型：
 *  String
 *  boolean
 *  int
 *  long
 *  float
 *  Object-》String//请把对象转换成json字符串以String形式存入读取
 *
 */
@ClassName("DataManager")
public interface IDataManager {

    /**
     * 获取key对应的value    String
     * @return value
     */
    @MethodName("getString")
    String getString(String key);

    /**
     * 存储键值对            String
     * @param key
     * @param value
     */
    @MethodName("setSaveString")
    void setSaveString(String key, String value);

    /**
     * 获取key对应的value    boolean
     * @param key
     * @return
     */
    @MethodName("getBoolean")
    boolean getBoolean(String key);

    /**
     * 存储键值对            boolean
     * @param key
     * @param value
     */
    @MethodName("setSaveBoolean")
    void setSaveBoolean(String key, boolean value);

    /**
     * 获取key对应的value    int
     * @param key
     * @return
     */
    @MethodName("getInt")
    int getInt(String key);

    /**
     * 存储键值对            int
     * @param key
     * @param value
     */
    @MethodName("setSaveInt")
    void setSaveInt(String key, int value);

    /**
     * 获取key对应的value    Long
     * @param key
     * @return
     */
    @MethodName("getLong")
    long getLong(String key);

    /**
     * 存储键值对            Long
     * @param key
     * @param value
     */
    @MethodName("setSaveLong")
    void setSaveLong(String key, long value);

    /**
     * 获取key对应的value    float
     * @param key
     * @return
     */
    @MethodName("getFloat")
    float getFloat(String key);

    /**
     * 存储键值对            float
     * @param key
     * @param value
     */
    @MethodName("setSaveFloat")
    void setSaveFloat(String key, float value);

}
