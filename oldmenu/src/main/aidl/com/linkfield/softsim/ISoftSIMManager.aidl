package com.linkfield.softsim;

import com.linkfield.softsim.ISoftSIMCallback;
import com.linkfield.softsim.model.SoftSIMInfo;

interface ISoftSIMManager {
    /**
     * 注册SoftSIM服务回调接口
     *
     * @param cb 回调函数
     *
     * @return true 注册成功, false 注册失败
     */
    boolean registerCallback(in ISoftSIMCallback cb);
    /**
     * 取消SoftSIM服务回调接口
     *
     * @param cb 回调函数
     *
     * @return true 取消成功, false 取消失败
     */
    boolean deregisterCallback(in ISoftSIMCallback cb);
    /**
     * 查询SoftSIM服务启用状态
     *
     * @return true SoftSIM服务已启用, false SoftSIM服务未启用
     */
    boolean isSoftSIMEnabled();
    /**
     * 启用/关闭SoftSIM服务
     *
     * @param enabled true 启用SoftSIM服务, false 关闭SoftSIM服务
     *
     * @return true 操作成功, false 操作失败
     */
    boolean setSoftSIMEnabled(boolean enabled);
    /**
     * 启动号码个人化，需在SoftSIM服务启动状态下调用
     *
     * @return true 如果当前SoftSIM为未写号码状态则启动成功， false 其他状态启动失败
     *
     */
    boolean startPersonalize();
    /**
     * 切换回Global号码状态，需在SoftSIM服务启动状态下调用
     *
     * @return true 切换成功， false 切换失败
     */
    boolean switchToGlobal();
    /**
     * 获得当前SoftSIM的号码信息
     *
     * @return SoftSIMInfo null SoftSIM未启用，否则为号码信息
     */
    SoftSIMInfo getSoftSIMInfo();
    /**
     * 启动SoftSIM OTA更新，需在SoftSIM服务启动状态下调用
     * 注意：此功能只针对SoftSIM服务支持自动OTA时有效，对于使用Profile管理方式时调用无意义
     *       对于SoftSIM服务支持自动OTA，调用会触发SoftSIM服务与后台进行数据同步。
     *
     * @return true 成功， false 失败
     */
    boolean refreshProfile();

    /**
     * 获取运行LOG
     *
     * @return 二进制LOG数据， 失败：NULL
     */
    byte[] getRunningLog();
}
