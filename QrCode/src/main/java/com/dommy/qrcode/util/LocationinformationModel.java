package com.dommy.qrcode.util;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;


public class LocationinformationModel {

    protected static final String CONTENT_AUTHORITY = "com.dommy.qrcode";
    protected static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    protected static final String PATH_AIBABEL = "aibabel_information";
    public static final class LocationEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_AIBABEL).build();

        protected static Uri buildUri(long id) {
            //Log.d("wzf","CONTENT_URI="+CONTENT_URI);
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        protected static final String TABLE_NAME = "_information";

        public static final String DEST_COUNTRY = "Country";//国家
        public static final String DEST_CITY = "City";//城市
        public static final String DEST_PROVINCE = "Province";//省份
        public static final String START_TIME = "start_time";//城市
        public static final String END_TIME = "end_time";//省份DD_TIME
        public static final String DD_TIME = "ddt";
        public static final String JH_TIME = "fft";

    }
}
  