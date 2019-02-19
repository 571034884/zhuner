package com.aibabel.translate.audio;

/**
 * @==========================================================================================
 * @Author：CreateBy 张文颖
 * @Date：2018/8/28
 * @Desc：抽象观察者,定义了一个update()方法，当被观察者调用notifyObservers()方法时，观察者的update()方法会被回调。
 * @==========================================================================================
 */
public abstract class Observer {
    public abstract void update(byte[] bytes);
}
