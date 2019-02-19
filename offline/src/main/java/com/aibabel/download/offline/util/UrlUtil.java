package com.aibabel.download.offline.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class UrlUtil {

    public static  String decode(String url) {

        String res="";
        try {
           res= URLDecoder.decode(url,"utf-8");
           L.e("-------------------url decode:"+res);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return res;
    }
}
