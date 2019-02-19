package com.aibabel.speech.tcp;

public interface OnResponseListener {
    void onSuccess(int flag, String json);
//    void onSuccess(int flag,byte[] result);
    void onFailed(int flag, String msg);

}
