package com.aibabel.menu.inf;

import com.aibabel.menu.bean.MenuDataBean;

public interface UpdateMenu {
    /**
     * 更新桌面
     * @param bean  实体
     * @param dataJson 原json
     * @param type  更改类型
     */
    void changeCity(MenuDataBean bean, String dataJson,String type);



    /**
     * 更新界面
     * @param bean
     */
    void updateUI(MenuDataBean bean);

    /**
     * 更新地址
     * @param addr
     */
    void updateAddr(String addr);

    /**
     * 请求shuj
     */
    void getDdata();

    /**
     * 停止h5中的音频播放
     */
    void closePlayer();

    /**
     * 错误提示
     * @param err
     */
     void onError(String err);

    /**
     * 未开放城市dialog
     */
    void noCity();

}
