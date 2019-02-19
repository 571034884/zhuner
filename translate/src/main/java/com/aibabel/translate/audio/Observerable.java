package com.aibabel.translate.audio;

/***

 * @author jstao
 *
 */

/**
 * @==========================================================================================
 * @Author： 张文颖
 * @Date：2019/2/13
 * @Desc：抽象被观察者接口,声明了添加、删除、通知观察者方法
 * @==========================================================================================
 */
public interface Observerable {

    void registerObserver(Observer o);

    void removeObserver(Observer o);

    void notifyObserver();

}