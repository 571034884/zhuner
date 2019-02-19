package com.aibabel.locationservice.provider;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;


public class LocationModel {

    protected static final String CONTENT_AUTHORITY = "com.aibabel.locationservice.provider.AibabelProvider";
    protected static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    protected static final String PATH_AIBABEL = "aibabel_location";
    public static final class LocationEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_AIBABEL).build();

        protected static Uri buildUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        protected static final String TABLE_NAME = "_location";


        public static final String COLUMN_ADDR = "addr";//详细地址信息
        public static final String COLUMN_WHERE = "locationWhere";//国内外
        public static final String COLUMN_COOR = "coor";//coorType，定位类型
        public static final String COLUMN_COUNTRY = "country";//国家
        public static final String COLUMN_PROVINCE = "province";//省份
        public static final String COLUMN_CITY = "city";//城市
        public static final String COLUMN_DISTRICT = "district"; //区县
        public static final String COLUMN_STREET = "street"; //街道信息
        public static final String COLUMN_DESCRIBE = "describe";//位置描述信息
        public static final String COLUMN_LATITUDE = "latitude";//纬度
        public static final String COLUMN_LONGITUDE = "longitude"; //经度
        public static final String COLUMN_IP = "ips"; //服务器地址列表
        public static final String COLUMN_TRAVEL = "travel"; //景区导览
        public static final String COLUMN_WEATHER = "weather"; //天气
        public static final String COLUMN_CLOCK = "clock"; //全球时钟
        public static final String COLUMN_CURRENCY = "currency"; //汇率
        public static final String COLUMN_ADVISORY = "advisory"; //旅游资讯
        public static final String COLUMN_INTERNET = "internet"; //全球上网
        public static final String COLUMN_DICTIONARY = "dictionary"; //词典
        public static final String COLUMN_SPEECH = "speech"; //小秘书
        public static final String COLUMN_TRANSLATE= "translate"; //语音翻译
        public static final String COLUMN_LOCATION= "location"; //locationservise
        public static final String COLUMN_OCR= "ocr"; //拍照翻译
        public static final String COLUMN_OBJECT= "object"; //物体识别
        public static final String COLUMN_CHAT = "chat"; //多机对话
        public static final String COLUMN_SOS= "sos"; //sos
        public static final String COLUMN_CONVERT= "convert"; //单位换算
        public static final String COLUMN_TRANSLATE_2= "translation2"; //真人翻译
        public static final String COLUMN_RENT= "rent"; //租车
        public static final String COLUMN_LOCAL= "local"; //当地
        public static final String COLUMN_FOOD= "food"; //美食
        /*以下为保留字段*/
        public static final String COLUMN_RESERVE_1 = "reserve1"; //
        public static final String COLUMN_RESERVE_2 = "reserve2"; //
        public static final String COLUMN_RESERVE_3 = "reserve3"; //
        public static final String COLUMN_RESERVE_4 = "reserve4"; //
        public static final String COLUMN_RESERVE_5 = "reserve5"; //
        public static final String COLUMN_RESERVE_6 = "reserve6"; //
        public static final String COLUMN_RESERVE_7 = "reserve7"; //
        public static final String COLUMN_RESERVE_8 = "reserve8"; //
        public static final String COLUMN_RESERVE_9 = "reserve9"; //
        public static final String COLUMN_RESERVE_10 = "reserve10"; //
        public static final String COLUMN_RESERVE_11 = "reserve11"; //
        public static final String COLUMN_RESERVE_12 = "reserve12"; //
        public static final String COLUMN_RESERVE_13 = "reserve13"; //
        public static final String COLUMN_RESERVE_14 = "reserve14"; //
        public static final String COLUMN_RESERVE_15 = "reserve15"; //
        public static final String COLUMN_RESERVE_16 = "reserve16"; //
        public static final String COLUMN_RESERVE_17 = "reserve17"; //
        public static final String COLUMN_RESERVE_18 = "reserve18"; //
        public static final String COLUMN_RESERVE_19 = "reserve19"; //
        public static final String COLUMN_RESERVE_20 = "reserve20"; //


    }
}
  