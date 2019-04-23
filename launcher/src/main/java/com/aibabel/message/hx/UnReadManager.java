package com.aibabel.message.hx;

import com.aibabel.message.receiver.MessageListener;

import java.util.List;

/**
 *==========================================================================================
 * @Author： 张文颖
 *
 * @Date：2019/4/23
 *
 * @Desc：自定义未读消息处理
 *==========================================================================================
 */
public class UnReadManager {
    public int unread = 0;

    private List<MessageListener> listeners;

    private static volatile UnReadManager singleton;

    private UnReadManager() {}

    public static UnReadManager getInstance() {
        if (singleton == null) {
            synchronized (UnReadManager.class) {
                if (singleton == null) {
                    singleton = new UnReadManager();
                }
            }
        }
        return singleton;
    }



    private void update() {
        unread++;
    }

    private int getCurrentCount() {

        return unread;
    }


    private void clear() {
        unread = 0;
    }


    private void makeMsgAsRead(){
        if(null!=listeners&&listeners.size()>0){
            for(MessageListener listener: listeners){
                listener.makeMessageAsRead();
            }
        }

    }


    public void addListener(MessageListener listener) {
        listeners.add(listener);
    }

    private void removeListener(MessageListener listener){
        listeners.remove(listener);
    }

    private void removeAllListener(){
        listeners.clear();
    }
}
