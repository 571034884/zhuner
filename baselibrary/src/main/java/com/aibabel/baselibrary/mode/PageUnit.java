package com.aibabel.baselibrary.mode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class PageUnit {
    public String notify;
    public String PageName;
    public String inTime;
    public String outTime;
    public HashMap<String , Serializable> paramters;
    public ArrayList<Event> events;

}
