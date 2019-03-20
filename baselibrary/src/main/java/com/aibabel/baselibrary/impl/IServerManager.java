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
 * 数据类型
 *
 */
@ClassName("ServerManager")
public interface IServerManager {
    /**
     * 1.key存储形式以 域名key+_error
     *      如：服务器域名key ：default_com_aibabel_alliedclock_joner
     *                          ServerKeyUtils.serverKeyAlliedClockJoner
     *          当前APP Error ：default_com_aibabel_alliedclock_joner_error
     *                          ServerKeyUtils.serverKeyAlliedClockJonerError
     * 2.传递时间值
     *
     * @param key   键
     * @param timer 时间戳
     */
    @MethodName("setSaveServerError")
    void setSaveServerError(String key, long timer);


    /**
     *
     * @param key 服务器域名key
     */
    @MethodName("setPingServerError")
    void setPingServerError(String key);
}
