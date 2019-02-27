package com.aibabel.travel.utils;

import com.aibabel.travel.R;

import java.util.HashMap;
import java.util.Map;

public class Constant {

    public static final String HOT_CITY = "hot_city";
    public static final String CURRENT_NET = "net";
    public static final int PAGE_COUNT = 50;//
    public static final CharSequence PH = "PH";//go
    public static final CharSequence PM = "PM";//fly
    public static final CharSequence PL = "PL";//pro
    public static String PREFIX = "";//
    public static final String RADIUS = "3";//默认半径
//    public static final int LOCATION_MILLIS = 60 * 1000 * 3;//默认请求间隔，单位毫秒
    public static  final   int LOCATION_MILLIS = 10 * 1000 ;//默认请求间隔，单位毫秒、
    public static  final   int REMINDER_DISTANCE = 30 ;//位置提醒的范围距离
    public static final long POI_MILLIS = 60 * 1000 * 5;//延时请求服务器列表数据，单位毫秒
//    public static final long POI_MILLIS = 2* 1000 ;//延时请求服务器列表数据，单位毫秒
    public static String DIALOG_NAME ="";
    public static String RXF_URL ="http://abroad.api.joner.aibabel.cn:7001";

    public static final String  DB_VERSION_CODE_KEY = "version_code";
    public static final String  DB_COUNTRY = "version_code";

    public static final int MSG_PROGRESS = 001;
    public static final int MSG_PREPARED = 002;
    public static final int MSG_PLAY_STATE = 003;

    public static long EXIT_TIME = 0;
    public static long GET_TIME = 0;

    public static final Map<String ,Integer> map = new HashMap<>();
    public static final Map<String ,Integer> map240 = new HashMap<>();
    static {
        map.put("1ecc311a7f91dfb5af55e59edfe5fdc15fede50f", R.mipmap.pic1ecc311a7f91dfb5af55e59edfe5fdc15fede50f);
        map.put("2dab74db9653370f43ba8ac22cc1e975b11731d6", R.mipmap.pic2dab74db9653370f43ba8ac22cc1e975b11731d6);
        map.put("03b5e47529ca4a347961bcebf18e0afec88570a8", R.mipmap.pic03b5e47529ca4a347961bcebf18e0afec88570a8);
        map.put("6c2eeb292512ec008bc332533a5ed40baa82ad70", R.mipmap.pic6c2eeb292512ec008bc332533a5ed40baa82ad70);
        map.put("7c8f416d6ca7d2a5c9e29c11302f4f6318f76bfc", R.mipmap.pic7c8f416d6ca7d2a5c9e29c11302f4f6318f76bfc);
        map.put("8e89ed575383c1843b1599cc0dc86bd33d3c2686", R.mipmap.pic8e89ed575383c1843b1599cc0dc86bd33d3c2686);
        map.put("11d737dd5384282b416c0204287d512be62d22c6", R.mipmap.pic11d737dd5384282b416c0204287d512be62d22c6);
        map.put("62cd27a8e40405011570667803695a3760410dd5", R.mipmap.pic62cd27a8e40405011570667803695a3760410dd5);
        map.put("722b6037154b0817cc1caa211fd183466bb86669", R.mipmap.pic722b6037154b0817cc1caa211fd183466bb86669);
        map.put("2582a5471756aa6f95d2e4b5b8288371c88cd433", R.mipmap.pic2582a5471756aa6f95d2e4b5b8288371c88cd433);
        map.put("5853db5294189420d721c668a6a2bf1de1e56a97", R.mipmap.pic5853db5294189420d721c668a6a2bf1de1e56a97);
        map.put("7292f5173138ec814d8047221ebf6f9a71ff1b2a", R.mipmap.pic7292f5173138ec814d8047221ebf6f9a71ff1b2a);
        map.put("37917488270f769a70464d2b79c5260108595d7e", R.mipmap.pic37917488270f769a70464d2b79c5260108595d7e);
        map.put("020838266b986dbc309efa1449be215562127089", R.mipmap.pic020838266b986dbc309efa1449be215562127089);
        map.put("ac61aceb2a982055eaf5101b40c0ec5fc18b4ae8", R.mipmap.picac61aceb2a982055eaf5101b40c0ec5fc18b4ae8);
        map.put("da09bb6725491ab94273b98f48fe191e42f609a2", R.mipmap.picda09bb6725491ab94273b98f48fe191e42f609a2);
        map.put("e1d64bfa0c35f82223da4038a860cacadfecab33", R.mipmap.pice1d64bfa0c35f82223da4038a860cacadfecab33);
        map.put("ec2cc64dc70838ce52e4b3df9f367991364cf56a", R.mipmap.picec2cc64dc70838ce52e4b3df9f367991364cf56a);
        map.put("f059d4417efd785372c855025734a240209f7e73", R.mipmap.picf059d4417efd785372c855025734a240209f7e73);
        map.put("f62f81ca366b1214185fafbd426f80df31aa92fd", R.mipmap.picf62f81ca366b1214185fafbd426f80df31aa92fd);
        map.put("f2437ca019d3229a47a5c158312d3e806c3c4389", R.mipmap.picf2437ca019d3229a47a5c158312d3e806c3c4389);
        map.put("1a2deecae4c360e9ff8f73c1367564ce", R.mipmap.pic1a2deecae4c360e9ff8f73c1367564ce);
        map.put("1d777a9ae1721cd9f284cea8169c207d", R.mipmap.pic1d777a9ae1721cd9f284cea8169c207d);
        map.put("2a0d79bc33e245115af783fa92f8685c", R.mipmap.pic2a0d79bc33e245115af783fa92f8685c);
        map.put("2b99fe0f2ec3931a2384e30190a2daad", R.mipmap.pic2b99fe0f2ec3931a2384e30190a2daad);
        map.put("3bb7033c26000ea72c97769221971aff", R.mipmap.pic3bb7033c26000ea72c97769221971aff);
        map.put("5cb6574c61b0d667f7dd4bb56e0e7e1a", R.mipmap.pic5cb6574c61b0d667f7dd4bb56e0e7e1a);
        map.put("5ced1aaf2c411d3cd32135d49152cdcd", R.mipmap.pic5ced1aaf2c411d3cd32135d49152cdcd);
        map.put("5db463d6f4339f3bbab8da92b81ab9bd", R.mipmap.pic5db463d6f4339f3bbab8da92b81ab9bd);
        map.put("21b63aeeb43af98d50b305f8f2639123", R.mipmap.pic21b63aeeb43af98d50b305f8f2639123);
        map.put("37e0f3f79fd173a1d5a607c4ab15c574", R.mipmap.pic37e0f3f79fd173a1d5a607c4ab15c574);
        map.put("3068f994d3e8a54a231f184284729f36", R.mipmap.pic3068f994d3e8a54a231f184284729f36);
        map.put("5631e8ed693f75de7c93369d95f1a76a", R.mipmap.pic5631e8ed693f75de7c93369d95f1a76a);
        map.put("9201bd017f76d7c93f03891560b76fbd", R.mipmap.pic9201bd017f76d7c93f03891560b76fbd);
        map.put("80062f2858e6318ccf3272dac4a85640", R.mipmap.pic80062f2858e6318ccf3272dac4a85640);
        map.put("507458adf85fed07371eb8bba06648cf", R.mipmap.pic507458adf85fed07371eb8bba06648cf);
        map.put("a1ac7b14afae0e3cd0389d62d967e7bc", R.mipmap.pica1ac7b14afae0e3cd0389d62d967e7bc);
        map.put("a7ce7c752badc9151c2f6c25dceca614", R.mipmap.pica7ce7c752badc9151c2f6c25dceca614);
        map.put("a999d45552578d747a476a3b8ea428f6", R.mipmap.pica999d45552578d747a476a3b8ea428f6);
        map.put("b45552f1697ca3c3cddb6e4582897ee1", R.mipmap.picb45552f1697ca3c3cddb6e4582897ee1);
        map.put("c3bbe13fc00af77176886c0f7d47877f", R.mipmap.picc3bbe13fc00af77176886c0f7d47877f);
        map.put("cd14df7a100748da7182fd5fe0797d51", R.mipmap.piccd14df7a100748da7182fd5fe0797d51);
        map.put("d99fe3c13ccf4739a5e0459fcb2b2be6", R.mipmap.picd99fe3c13ccf4739a5e0459fcb2b2be6);
        map.put("e773e4769fa3998e6e33dbe8705427bb", R.mipmap.pice773e4769fa3998e6e33dbe8705427bb);
        map.put("eadb3175206c220a9e0c842b48e699ff", R.mipmap.piceadb3175206c220a9e0c842b48e699ff);
        map.put("eddd47f04ff803a2a856f9d3fdcaa532", R.mipmap.piceddd47f04ff803a2a856f9d3fdcaa532);
        map.put("f4a48119256b4c1c272688023d7bcbc2", R.mipmap.picf4a48119256b4c1c272688023d7bcbc2);
        map.put("fecdbad5d2518af641096cfbc8c7b0df", R.mipmap.picfecdbad5d2518af641096cfbc8c7b0df);
        map.put("ff258de84c58aac405489785a249c208", R.mipmap.picff258de84c58aac405489785a249c208);
        map.put("5cecf0a77beb5820293b7054f227acb2cebb88f2", R.mipmap.pic5cecf0a77beb5820293b7054f227acb2cebb88f2);
    }
    static {
        map240.put("5db463d6f4339f3bbab8da92b81ab9bd", R.mipmap.pic5db463d6f4339f3bbab8da92b81ab9bd240x150);
        map240.put("507458adf85fed07371eb8bba06648cf", R.mipmap.pic507458adf85fed07371eb8bba06648cf240x150);
        map240.put("a999d45552578d747a476a3b8ea428f6", R.mipmap.pica999d45552578d747a476a3b8ea428f6240x150);
        map240.put("cd14df7a100748da7182fd5fe0797d51", R.mipmap.piccd14df7a100748da7182fd5fe0797d51240x150);
        map240.put("e1d64bfa0c35f82223da4038a860cacadfecab33", R.mipmap.pice1d64bfa0c35f82223da4038a860cacadfecab33240x150);
        map240.put("e773e4769fa3998e6e33dbe8705427bb", R.mipmap.pice773e4769fa3998e6e33dbe8705427bb240x150);
        map240.put("eddd47f04ff803a2a856f9d3fdcaa532", R.mipmap.piceddd47f04ff803a2a856f9d3fdcaa532240x150);
    }



}
