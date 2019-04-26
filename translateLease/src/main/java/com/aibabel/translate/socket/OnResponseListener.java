package com.aibabel.translate.socket;

public interface OnResponseListener {
     void setEnglish(String text);

     void setAsr(String text,int flag);

     void setMt(String text,int flag);

     void reset();
}
