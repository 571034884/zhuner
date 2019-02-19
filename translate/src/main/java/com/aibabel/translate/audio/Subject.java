package com.aibabel.translate.audio;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private List<Observer> observers = new ArrayList<>();

    public void setMsg(byte[] bytes) {
        notifyAll(bytes);
    }

    //订阅
    public void addAttach(Observer observer) {
        observers.add(observer);
    }

    //通知所有订阅的观察者
    private void notifyAll(byte[] bytes) {
        for (Observer observer : observers) {
            observer.update(bytes);
        }
    }
}
