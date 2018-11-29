package com.aibabel.baselibrary.observer;

/**
 * Created by liuwei on 2013/1/26.
 *
 * 观察者接口
 */
public interface ObserverListener {
    void onReceiveMessage(String name, Object object);
}
