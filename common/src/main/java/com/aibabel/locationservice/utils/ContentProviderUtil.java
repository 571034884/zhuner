package com.aibabel.locationservice.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONObject;

public class ContentProviderUtil {
    /*获取当前定位城市和该地区的host地址*/
    private static final Uri CITY_URI = Uri.parse("content://com.aibabel.locationservice.provider.AibabelProvider/aibabel_location");

//    public static String ips = "{\n" +
//            "\t\t\"default_com.aibabel.alliedclock_joner\": [{\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:7001\"\n" +
//            "\t\t}, {\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:7001\"\n" +
//            "\t\t}],\n" +
//            "\t\t\"default_com.aibabel.chat_joner\": [{\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:5500\"\n" +
//            "\t\t}, {\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:5500\"\n" +
//            "\t\t}, {\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:5500\"\n" +
//            "\t\t}],\n" +
//            "\t\t\"default_com.aibabel.currencyconversion_joner\": [{\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:7001\"\n" +
//            "\t\t}, {\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:7001\"\n" +
//            "\t\t}],\n" +
//            "\t\t\"default_com.aibabel.dictionary_joner\": [{\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:7001\"\n" +
//            "\t\t}, {\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:7001\"\n" +
//            "\t\t}],\n" +
//            "\t\t\"default_com.aibabel.locationservice_joner\": [{\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:7001\"\n" +
//            "\t\t}, {\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:7001\"\n" +
//            "\t\t}],\n" +
//            "\t\t\"default_com.aibabel.ocr_ocr\": [{\n" +
//            "\t\t\t\"domain\": \"http://abroad.api.ocr.aibabel.cn:6001\"\n" +
//            "\t\t}, {\n" +
//            "\t\t\t\"domain\": \"http://api.ocr.aibabel.cn:6001\"\n" +
//            "\t\t}],\n" +
//            "\t\t\"default_com.aibabel.ocrobject_object\": [{\n" +
//            "\t\t\t\"domain\": \"http://abroad.api.object.aibabel.cn:6011\"\n" +
//            "\t\t}, {\n" +
//            "\t\t\t\"domain\": \"http://api.object.aibabel.cn:6011\"\n" +
//            "\t\t}],\n" +
//            "\t\t\"default_com.aibabel.speech_function\": [{\n" +
//            "\t\t\t\"domain\": \"abroad.api.function.aibabel.cn:5005\"\n" +
//            "\t\t}, {\n" +
//            "\t\t\t\"domain\": \"api.function.aibabel.cn:5005\"\n" +
//            "\t\t}, {\n" +
//            "\t\t\t\"domain\": \"spare.api.function.aibabel.cn:5005\"\n" +
//            "\t\t}],\n" +
//            "\t\t\"default_com.aibabel.speech_pa\": [{\n" +
//            "\t\t\t\"domain\": \"http://abroad.api.pa.aibabel.cn:6021\"\n" +
//            "\t\t}, {\n" +
//            "\t\t\t\"domain\": \"http://api.pa.aibabel.cn:6021\"\n" +
//            "\t\t}],\n" +
//            "\t\t\"default_com.aibabel.surfinternet_joner\": [{\n" +
//            "\t\t\t\"domain\": \"http://abroad.api.joner.aibabel.cn:7001\"\n" +
//            "\t\t}, {\n" +
//            "\t\t\t\"domain\": \"http://api.joner.aibabel.cn:7001\"\n" +
//            "\t\t}],\n" +
//            "\t\t\"default_com.aibabel.surfinternet_pay\": [{\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:7002\"\n" +
//            "\t\t}, {\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:7002\"\n" +
//            "\t\t}],\n" +
//            "\t\t\"default_com.aibabel.translate_function\": [{\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:5005\"\n" +
//            "\t\t}, {\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:5005\"\n" +
//            "\t\t}, {\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:5005\"\n" +
//            "\t\t}],\n" +
//            "\t\t\"default_com.aibabel.travel_joner\": [{\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:7001\"\n" +
//            "\t\t}, {\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:7001\"\n" +
//            "\t\t}],\n" +
//            "\t\t\"default_com.aibabel.traveladvisory_joner\": [{\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:7001\"\n" +
//            "\t\t}, {\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:7001\"\n" +
//            "\t\t}],\n" +
//            "\t\t\"default_com.aibabel.weather_joner\": [{\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:7001\"\n" +
//            "\t\t}, {\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:7001\"\n" +
//            "\t\t}],\n" +
//            "\t\t\"中国_com.aibabel.alliedclock_joner\": [{\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:7001\"\n" +
//            "\t\t}, {\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:7001\"\n" +
//            "\t\t}],\n" +
//            "\t\t\"中国_com.aibabel.chat_joner\": [{\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:5500\"\n" +
//            "\t\t}, {\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:5500\"\n" +
//            "\t\t}, {\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:5500\"\n" +
//            "\t\t}],\n" +
//            "\t\t\"中国_com.aibabel.currencyconversion_joner\": [{\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:7001\"\n" +
//            "\t\t}, {\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:7001\"\n" +
//            "\t\t}],\n" +
//            "\t\t\"中国_com.aibabel.dictionary_joner\": [{\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:7001\"\n" +
//            "\t\t}, {\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:7001\"\n" +
//            "\t\t}],\n" +
//            "\t\t\"中国_com.aibabel.locationservice_joner\": [{\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:7001\"\n" +
//            "\t\t}, {\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:7001\"\n" +
//            "\t\t}],\n" +
//            "\t\t\"中国_com.aibabel.ocr_ocr\": [{\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:6001\"\n" +
//            "\t\t}, {\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:6001\"\n" +
//            "\t\t}],\n" +
//            "\t\t\"中国_com.aibabel.ocrobject_object\": [{\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:6011\"\n" +
//            "\t\t}, {\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:6011\"\n" +
//            "\t\t}],\n" +
//            "\t\t\"中国_com.aibabel.speech_function\": [{\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:5005\"\n" +
//            "\t\t}, {\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:5005\"\n" +
//            "\t\t}, {\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:5005\"\n" +
//            "\t\t}],\n" +
//            "\t\t\"中国_com.aibabel.speech_pa\": [{\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:6021\"\n" +
//            "\t\t}, {\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:6021\"\n" +
//            "\t\t}],\n" +
//            "\t\t\"中国_com.aibabel.surfinternet_joner\": [{\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:7001\"\n" +
//            "\t\t}, {\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:7001\"\n" +
//            "\t\t}],\n" +
//            "\t\t\"中国_com.aibabel.surfinternet_pay\": [{\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:7002\"\n" +
//            "\t\t}, {\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:7002\"\n" +
//            "\t\t}],\n" +
//            "\t\t\"中国_com.aibabel.translate_function\": [{\n" +
//            "\t\t\t\"domain\": \"api.function.aibabel.cn:5005\"\n" +
//            "\t\t}, {\n" +
//            "\t\t\t\"domain\": \"abroad.api.function.aibabel.cn:5005\"\n" +
//            "\t\t}, {\n" +
//            "\t\t\t\"domain\": \"spare.api.function.aibabel.cn:5005\"\n" +
//            "\t\t}],\n" +
//            "\t\t\"中国_com.aibabel.travel_joner\": [{\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:7001\"\n" +
//            "\t\t}, {\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:7001\"\n" +
//            "\t\t}],\n" +
//            "\t\t\"中国_com.aibabel.traveladvisory_joner\": [{\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:7001\"\n" +
//            "\t\t}, {\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:7001\"\n" +
//            "\t\t}],\n" +
//            "\t\t\"中国_com.aibabel.weather_joner\": [{\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:7001\"\n" +
//            "\t\t}, {\n" +
//            "\t\t\t\"domain\": \"http://39.107.238.111:7001\"\n" +
//            "\t\t}]\n" +
//            "\t}";


    public static String ips = "{\n" +
            "    \"default_com.aibabel.alliedclock_joner\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.joner.aibabel.cn:7001\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://api.joner.aibabel.cn:7001\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"default_com.aibabel.chat_joner\":[\n" +
            "        {\n" +
            "            \"domain\":\"abroad.api.chat.aibabel.cn:5500\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"api.chat.aibabel.cn:5500\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"spare.api.chat.aibabel.cn:5500\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"default_com.aibabel.coupon\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.joner.aibabel.cn:7001\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://api.joner.aibabel.cn:7001\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"default_com.aibabel.currencyconversion_joner\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.joner.aibabel.cn:7001\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://api.joner.aibabel.cn:7001\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"default_com.aibabel.ddot\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.joner.aibabel.cn:7001\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://api.joner.aibabel.cn:7001\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"default_com.aibabel.desktop\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.joner.aibabel.cn:7001\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://api.joner.aibabel.cn:7001\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"default_com.aibabel.dictionary_joner\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.joner.aibabel.cn:7001\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://api.joner.aibabel.cn:7001\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"default_com.aibabel.enterandexit\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.joner.aibabel.cn:7001\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://api.joner.aibabel.cn:7001\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"default_com.aibabel.food\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.joner.aibabel.cn:7001\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://api.joner.aibabel.cn:7001\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"default_com.aibabel.locationservice_joner\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.joner.aibabel.cn:7001\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://api.joner.aibabel.cn:7001\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"default_com.aibabel.map\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.joner.aibabel.cn:7001\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://api.joner.aibabel.cn:7001\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"default_com.aibabel.ocr_camera\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.ocr.aibabel.cn:9999\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://api.ocr.aibabel.cn:9999\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"default_com.aibabel.ocr_ocr\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.ocr.aibabel.cn:6001\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://api.ocr.aibabel.cn:6001\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"default_com.aibabel.ocrobject_object\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.object.aibabel.cn:6011\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://api.object.aibabel.cn:6011\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"default_com.aibabel.play\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.joner.aibabel.cn:7001\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://api.joner.aibabel.cn:7001\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"default_com.aibabel.speech_function\":[\n" +
            "        {\n" +
            "            \"domain\":\"abroad.api.function.aibabel.cn:5005\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"api.function.aibabel.cn:5005\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"spare.api.function.aibabel.cn:5005\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"default_com.aibabel.speech_pa\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.pa.aibabel.cn:6021\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://api.pa.aibabel.cn:6021\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"default_com.aibabel.surfinternet_joner\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.joner.aibabel.cn:7001\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://api.joner.aibabel.cn:7001\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"default_com.aibabel.surfinternet_pay\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.web.aibabel.cn:7002\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://api.web.aibabel.cn:7002\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"default_com.aibabel.translate_function\":[\n" +
            "        {\n" +
            "            \"domain\":\"abroad.api.function.aibabel.cn:5005\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"api.function.aibabel.cn:5005\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"spare.api.function.aibabel.cn:5005\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"default_com.aibabel.travel_joner\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.joner.aibabel.cn:7001\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://api.joner.aibabel.cn:7001\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"default_com.aibabel.traveladvisory_joner\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.joner.aibabel.cn:7001\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://api.joner.aibabel.cn:7001\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"default_com.aibabel.weather_joner\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.joner.aibabel.cn:7001\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://api.joner.aibabel.cn:7001\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"中国_com.aibabel.alliedclock_joner\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://api.joner.aibabel.cn:7001\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.joner.aibabel.cn:7001\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"中国_com.aibabel.chat_joner\":[\n" +
            "        {\n" +
            "            \"domain\":\"api.chat.aibabel.cn:5500\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"abroad.api.chat.aibabel.cn:5500\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"spare.api.chat.aibabel.cn:5500\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"中国_com.aibabel.coupon\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://api.joner.aibabel.cn:7001\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.joner.aibabel.cn:7001\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"中国_com.aibabel.currencyconversion_joner\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://api.joner.aibabel.cn:7001\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.joner.aibabel.cn:7001\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"中国_com.aibabel.ddot\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://api.joner.aibabel.cn:7001\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.joner.aibabel.cn:7001\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"中国_com.aibabel.desktop\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://api.joner.aibabel.cn:7001\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.joner.aibabel.cn:7001\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"中国_com.aibabel.dictionary_joner\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://api.joner.aibabel.cn:7001\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.joner.aibabel.cn:7001\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"中国_com.aibabel.enterandexit\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://api.joner.aibabel.cn:7001\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.joner.aibabel.cn:7001\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"中国_com.aibabel.food\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://api.joner.aibabel.cn:7001\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.joner.aibabel.cn:7001\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"中国_com.aibabel.locationservice_joner\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://api.joner.aibabel.cn:7001\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.joner.aibabel.cn:7001\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"中国_com.aibabel.map\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://api.joner.aibabel.cn:7001\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.joner.aibabel.cn:7001\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"中国_com.aibabel.ocr_camera\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://api.ocr.aibabel.cn:9999\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.ocr.aibabel.cn:9999\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"中国_com.aibabel.ocr_ocr\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://api.ocr.aibabel.cn:6001\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.ocr.aibabel.cn:6001\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"中国_com.aibabel.ocrobject_object\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://api.object.aibabel.cn:6011\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.object.aibabel.cn:6011\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"中国_com.aibabel.play\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://api.joner.aibabel.cn:7001\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.joner.aibabel.cn:7001\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"中国_com.aibabel.speech_function\":[\n" +
            "        {\n" +
            "            \"domain\":\"api.function.aibabel.cn:5005\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"abroad.api.function.aibabel.cn:5005\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"spare.api.function.aibabel.cn:5005\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"中国_com.aibabel.speech_pa\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://api.pa.aibabel.cn:6021\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.pa.aibabel.cn:6021\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"中国_com.aibabel.surfinternet_joner\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://api.joner.aibabel.cn:7001\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.joner.aibabel.cn:7001\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"中国_com.aibabel.surfinternet_pay\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://api.web.aibabel.cn:7002\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.web.aibabel.cn:7002\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"中国_com.aibabel.translate_function\":[\n" +
            "        {\n" +
            "            \"domain\":\"api.function.aibabel.cn:5005\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"abroad.api.function.aibabel.cn:5005\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"spare.api.function.aibabel.cn:5005\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"中国_com.aibabel.travel_joner\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://api.joner.aibabel.cn:7001\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.joner.aibabel.cn:7001\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"中国_com.aibabel.traveladvisory_joner\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://api.joner.aibabel.cn:7001\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.joner.aibabel.cn:7001\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"中国_com.aibabel.weather_joner\":[\n" +
            "        {\n" +
            "            \"domain\":\"http://api.joner.aibabel.cn:7001\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"domain\":\"http://abroad.api.joner.aibabel.cn:7001\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";


    /**
     * 获取当前地区的host地址
     *
     * @param context
     * @return
     */
    public static String getHost(Context context) {
        Cursor cursor = context.getContentResolver().query(CITY_URI, null, null, null, null);
        String ip_host = "http://abroad.api.joner.aibabel.cn:7001";
        String key = "";
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                String ips = cursor.getString(cursor.getColumnIndex("ips"));
                String countryNameCN = cursor.getString(cursor.getColumnIndex("country"));

                if (TextUtils.equals("中国", countryNameCN)) {
                    key = "中国_" + context.getPackageName() + "_joner";
                } else {
                    key = "default_" + context.getPackageName() + "_joner";
                }
                JSONObject jsonObject = new JSONObject(ips);
                JSONArray jsonArray = new JSONArray(jsonObject.getString(key));
                ip_host = jsonArray.getJSONObject(0).get("domain").toString();
//                Log.d("ip_host", ip_host);
            } else {
                Log.d("TAG", "：没有获取到定位信息");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != cursor)
                cursor.close();
        }
        Log.d("ContentProviderUtil", ip_host);
        return ip_host;

    }

    /**
     * 获取当前定位城市（省）
     *
     * @param context
     * @return
     */
    public static String getCountry(Context context) {
        Cursor cursor = context.getContentResolver().query(CITY_URI, null, null, null, null);
        String cityName = "";
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                int countryIndex = cursor.getColumnIndex("country");
                cityName = cursor.getString(countryIndex);
            } else {
                Log.d("TAG", "：没有获取到定位信息");
            }
        } finally {
            if (null != cursor)
                cursor.close();
        }
        return cityName;
    }


}
