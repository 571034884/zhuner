package com.aibabel.ocr.utils;

import android.net.Uri;

import com.aibabel.ocr.app.BaseApplication;

/**
 * =====================================================================
 * <p>
 * 作者 : 张文颖
 * <p>
 * 时间: 2018/3/30
 * <p>
 * 描述:
 * <p>
 * =====================================================================
 */

public class Constant {
    public final static String LAN_OR = "LAN_OR";//源语言
    public final static String LAN_TR = "LAN_TR";//翻译语言
    public final static String LAN_OR_CODE = "LAN_OR_CODE";//源语言code
    public final static String LAN_TR_CODE = "LAN_TR_CODE";//翻译语言code
    public final static Uri CONTENT_URI = Uri.parse("content://com.aibabel.locationservice.provider.AibabelProvider/aibabel_location");
    public final static int HEIGHT_TITLE = 84;
    public final static int REQUIRE_W = 720;
    public final static int REQUIRE_H = 1280;
    public final static String TYPE_MENU = "menu";
    public final static String TYPE_ARTICAL = "article";
    public final static String TYPE_OBJECT = "object";


}
