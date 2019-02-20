package com.aibabel.traveladvisory.utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.aibabel.traveladvisory.BuildConfig;
import com.aibabel.traveladvisory.R;
import com.alibaba.fastjson.JSON;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 作者：SunSH on 2018/8/23 14:29
 * 功能：
 * 版本：1.0
 */
public class OffLineUtil {

    public static String TAG = "OffLineUtil";

    public static final Map offlineCountryMap;
    public static Map<String, String> offlineAllMap;//支持离线的文件路径
    public static Map<String, String> offlineSupportMap;//支持离线的文件路径
    public static Map<String, Integer> offlineStatusMap;//离线的文件状态
    public static Map<String, Integer> offlineTipMap;//离线的提示状态
    public static final int STATE_1 = 1; //下载并解压
    public static final int STATE_2 = 10; //下载未解压
    public static final int STATE_3 = 99; //未下载
    public static final int STATE_4 = -4; //在未下载或未解压情况下给过提示, 使用负值
    private volatile static OffLineUtil offLineUtil;//
    public static final String offlinePath = Environment.getExternalStorageDirectory() + "/offline/mdd/";
    //    public static final Uri CONTENT_URI_LQ = Uri.parse("content://com.aibabel.download.offline.provider.OfflineProvidovider");
    public static final Uri CONTENT_URI_LQ = Uri.parse("content://com.aibabel.download.offline.provider.OfflineProvider");

    public interface OnOfflineLister {
        void complete(String json);

        void error();
    }

    public static OffLineUtil getInstance() {
        if (offLineUtil == null) {
            synchronized (OffLineUtil.class) {
                if (offLineUtil == null) {
                    offLineUtil = new OffLineUtil();
                }
            }
        }
        return offLineUtil;
    }

    public boolean isSupport() {
        return true;
    }


    /**
     * 获取国家图片
     */
    public int getCountryImage(String key) {
        return (int) offlineCountryMap.get(key);
    }

    /**
     * 初始后静态offlineTipsMap
     */
    static {
        Map map = new HashMap();
        map.put("53d8bb019fcaf240x150", R.mipmap.pic53d8bb019fcaf240x150);
        map.put("53d8c2a0958d8240x150", R.mipmap.pic53d8c2a0958d8240x150);
        map.put("53d8c2f169c00240x150", R.mipmap.pic53d8c2f169c00240x150);
        map.put("53d8c5cbcc61c240x150", R.mipmap.pic53d8c5cbcc61c240x150);
        map.put("53d8c9cc0b95e240x150", R.mipmap.pic53d8c9cc0b95e240x150);
        map.put("ddb9276238dd9a301bc995087e94efcf240x150", R.mipmap.picddb9276238dd9a301bc995087e94efcf240x150);
        map.put("8919b6fe043e55e15d13c7b4b9b9f99e164x150", R.mipmap.pic8919b6fe043e55e15d13c7b4b9b9f99e164x150);
        map.put("ea7cba9efa8e7489e116cc3d35eab639164x150", R.mipmap.picea7cba9efa8e7489e116cc3d35eab639164x150);
        map.put("9598f5fc9f0da803a1935fe409471224164x150", R.mipmap.pic9598f5fc9f0da803a1935fe409471224164x150);
        map.put("59428b7d3758fbd4694ab18246d59f5a164x150", R.mipmap.pic59428b7d3758fbd4694ab18246d59f5a164x150);
        map.put("371b4d42df4430e3b031d1485849d3bf164x150", R.mipmap.pic371b4d42df4430e3b031d1485849d3bf164x150);
        map.put("ee4948e986f73256c32847f7a23d91c5164x150", R.mipmap.picee4948e986f73256c32847f7a23d91c5164x150);
        map.put("53f707cb1d48c164x150", R.mipmap.pic53f707cb1d48c164x150);
        map.put("53f70be8565fc164x150", R.mipmap.pic53f70be8565fc164x150);
        map.put("29b2a4eaa9fb09aff40363cd3fc2ffcd164x150", R.mipmap.pic29b2a4eaa9fb09aff40363cd3fc2ffcd164x150);
        map.put("78511bed893bcc199ffe8f5f194fdd91164x150", R.mipmap.pic78511bed893bcc199ffe8f5f194fdd91164x150);
        map.put("27376a6f6386ab8b761edac2cb9541ca164x150", R.mipmap.pic27376a6f6386ab8b761edac2cb9541ca164x150);
        map.put("c0d078d4587a0f922bb0e49e46d5b129164x150", R.mipmap.picc0d078d4587a0f922bb0e49e46d5b129164x150);
        map.put("b6bddc63e3725c1a8472408990870aa3164x150", R.mipmap.picb6bddc63e3725c1a8472408990870aa3164x150);
        map.put("53d8bd09a3f24164x150", R.mipmap.pic53d8bd09a3f24164x150);
        map.put("0520888d8c165012a3c486840abe6c8d164x150", R.mipmap.pic0520888d8c165012a3c486840abe6c8d164x150);
        map.put("53d8bdc0d63c9164x150", R.mipmap.pic53d8bdc0d63c9164x150);
        map.put("c07e72c2820b9d162872faba10297ef2164x150", R.mipmap.picc07e72c2820b9d162872faba10297ef2164x150);
        map.put("13bf7ea2d381ff5cf709fd894c221110164x150", R.mipmap.pic13bf7ea2d381ff5cf709fd894c221110164x150);
        map.put("269412a0702c142fdc72a9207b60cc36164x150", R.mipmap.pic269412a0702c142fdc72a9207b60cc36164x150);
        map.put("3cd7987f2150c4ad00a35856766ae60e164x150", R.mipmap.pic3cd7987f2150c4ad00a35856766ae60e164x150);
        map.put("9d13dab59e8986689d07f95033fa0bfe164x150", R.mipmap.pic9d13dab59e8986689d07f95033fa0bfe164x150);
        map.put("d565bf1efb987247f8b6621ed000317c164x150", R.mipmap.picd565bf1efb987247f8b6621ed000317c164x150);
        map.put("7099698a491fdee1f7de6cc6f7cbfb19164x150", R.mipmap.pic7099698a491fdee1f7de6cc6f7cbfb19164x150);
        map.put("44c33d497f1c8dbe979c24499c2e3ffe164x150", R.mipmap.pic44c33d497f1c8dbe979c24499c2e3ffe164x150);
        map.put("3f160ab06ba84f8564ecb8c468687206164x150", R.mipmap.pic3f160ab06ba84f8564ecb8c468687206164x150);
        map.put("67fcf6c5f2487fd4afb165d82f929eff164x150", R.mipmap.pic67fcf6c5f2487fd4afb165d82f929eff164x150);
        map.put("d8a71d680ec3606bf381d78d0f5655d5164x150", R.mipmap.picd8a71d680ec3606bf381d78d0f5655d5164x150);
        map.put("53d8acb56691b164x150", R.mipmap.pic53d8acb56691b164x150);
        map.put("639d629709e870d5fc60f4461251d133164x150", R.mipmap.pic639d629709e870d5fc60f4461251d133164x150);
        map.put("53d8af7f0426e164x150", R.mipmap.pic53d8af7f0426e164x150);
        map.put("53cf71c242a57164x150", R.mipmap.pic53cf71c242a57164x150);
        map.put("8919b6fe043e55e15d13c7b4b9b9f99e164x150", R.mipmap.pic8919b6fe043e55e15d13c7b4b9b9f99e164x150);
        map.put("ea7cba9efa8e7489e116cc3d35eab639164x150", R.mipmap.picea7cba9efa8e7489e116cc3d35eab639164x150);
        map.put("9598f5fc9f0da803a1935fe409471224164x150", R.mipmap.pic9598f5fc9f0da803a1935fe409471224164x150);
        map.put("59428b7d3758fbd4694ab18246d59f5a164x150", R.mipmap.pic59428b7d3758fbd4694ab18246d59f5a164x150);
        map.put("371b4d42df4430e3b031d1485849d3bf164x150", R.mipmap.pic371b4d42df4430e3b031d1485849d3bf164x150);
        map.put("ee4948e986f73256c32847f7a23d91c5164x150", R.mipmap.picee4948e986f73256c32847f7a23d91c5164x150);
        map.put("53d8b994a7830164x150", R.mipmap.pic53d8b994a7830164x150);
        map.put("b6930273f7c732f381b0da742100613c164x150", R.mipmap.picb6930273f7c732f381b0da742100613c164x150);
        map.put("652e7b7c25d30c611665d0a3033e4c93164x150", R.mipmap.pic652e7b7c25d30c611665d0a3033e4c93164x150);
        map.put("53d8c9cc0b95e164x150", R.mipmap.pic53d8c9cc0b95e164x150);
        map.put("d835f4e0abd18d17d34e2d9a00780900164x150", R.mipmap.picd835f4e0abd18d17d34e2d9a00780900164x150);
        map.put("53d8bb019fcaf164x150", R.mipmap.pic53d8bb019fcaf164x150);
        map.put("53ede16959fd0164x150", R.mipmap.pic53ede16959fd0164x150);
        map.put("53ede699289f6164x150", R.mipmap.pic53ede699289f6164x150);
        map.put("269fc09df9904318c10f9cebe98be01a164x150", R.mipmap.pic269fc09df9904318c10f9cebe98be01a164x150);
        map.put("53ede06776538164x150", R.mipmap.pic53ede06776538164x150);
        map.put("1b1eb1d54ff1b89e13d38b8120e61b2c164x150", R.mipmap.pic1b1eb1d54ff1b89e13d38b8120e61b2c164x150);
        map.put("53d8c812de553164x150", R.mipmap.pic53d8c812de553164x150);
        map.put("53d8b8d1cc0af164x150", R.mipmap.pic53d8b8d1cc0af164x150);
        map.put("54129e5051f6c164x150", R.mipmap.pic54129e5051f6c164x150);
        map.put("53f707cb1d48c164x150", R.mipmap.pic53f707cb1d48c164x150);
        map.put("53d8c2a0958d8164x150", R.mipmap.pic53d8c2a0958d8164x150);
        map.put("53f70be8565fc164x150", R.mipmap.pic53f70be8565fc164x150);
        map.put("29b2a4eaa9fb09aff40363cd3fc2ffcd164x150", R.mipmap.pic29b2a4eaa9fb09aff40363cd3fc2ffcd164x150);
        map.put("78511bed893bcc199ffe8f5f194fdd91164x150", R.mipmap.pic78511bed893bcc199ffe8f5f194fdd91164x150);
        map.put("27376a6f6386ab8b761edac2cb9541ca164x150", R.mipmap.pic27376a6f6386ab8b761edac2cb9541ca164x150);
        map.put("53d8c47652a84164x150", R.mipmap.pic53d8c47652a84164x150);
        map.put("53d8c138b1756164x150", R.mipmap.pic53d8c138b1756164x150);
        map.put("53d8c535188e4164x150", R.mipmap.pic53d8c535188e4164x150);
        map.put("53d8c2549cc1d164x150", R.mipmap.pic53d8c2549cc1d164x150);
        map.put("53d8c5cbcc61c164x150", R.mipmap.pic53d8c5cbcc61c164x150);
        map.put("53f7048c205c3164x150", R.mipmap.pic53f7048c205c3164x150);
        map.put("8efb883346af06f0af97a776b77f1f24164x150", R.mipmap.pic8efb883346af06f0af97a776b77f1f24164x150);
        map.put("a87422f6968ed02b2ce150ae35e11d05164x150", R.mipmap.pica87422f6968ed02b2ce150ae35e11d05164x150);
        map.put("6046c71fcd42669025b3e1d159337b8e164x150", R.mipmap.pic6046c71fcd42669025b3e1d159337b8e164x150);
        map.put("53f7005d2f4ba164x150", R.mipmap.pic53f7005d2f4ba164x150);
        map.put("53d8c2f169c00164x150", R.mipmap.pic53d8c2f169c00164x150);
        map.put("6654abe32190899b5b47068f8114a4f3164x150", R.mipmap.pic6654abe32190899b5b47068f8114a4f3164x150);
        map.put("53f707552049a164x150", R.mipmap.pic53f707552049a164x150);
        map.put("690378d687beb23ea3eb05e603890f9e164x150", R.mipmap.pic690378d687beb23ea3eb05e603890f9e164x150);
        map.put("53f7054b3e0b0164x150", R.mipmap.pic53f7054b3e0b0164x150);
        map.put("53f6ffe9ceecc164x150", R.mipmap.pic53f6ffe9ceecc164x150);
        map.put("c1fd7d81d76adb94669e1abee20f7ac2164x150", R.mipmap.picc1fd7d81d76adb94669e1abee20f7ac2164x150);
        map.put("53f7084d4e9ab164x150", R.mipmap.pic53f7084d4e9ab164x150);
        map.put("53f6fe7c54d5c164x150", R.mipmap.pic53f6fe7c54d5c164x150);
        map.put("53f7016145a36164x150", R.mipmap.pic53f7016145a36164x150);
        map.put("53f7037d1ee3d164x150", R.mipmap.pic53f7037d1ee3d164x150);
        map.put("53d8c4fc786a6164x150", R.mipmap.pic53d8c4fc786a6164x150);
        map.put("13bf7ea2d381ff5cf709fd894c221110164x150", R.mipmap.pic13bf7ea2d381ff5cf709fd894c221110164x150);
        map.put("269412a0702c142fdc72a9207b60cc36164x150", R.mipmap.pic269412a0702c142fdc72a9207b60cc36164x150);
        map.put("3cd7987f2150c4ad00a35856766ae60e164x150", R.mipmap.pic3cd7987f2150c4ad00a35856766ae60e164x150);
        map.put("9d13dab59e8986689d07f95033fa0bfe164x150", R.mipmap.pic9d13dab59e8986689d07f95033fa0bfe164x150);
        map.put("d565bf1efb987247f8b6621ed000317c164x150", R.mipmap.picd565bf1efb987247f8b6621ed000317c164x150);
        map.put("7099698a491fdee1f7de6cc6f7cbfb19164x150", R.mipmap.pic7099698a491fdee1f7de6cc6f7cbfb19164x150);
        map.put("e1b756b1554bbabe2d54c16440bf61f2164x150", R.mipmap.pice1b756b1554bbabe2d54c16440bf61f2164x150);
        map.put("4049f5134ad44a24460e391c204bf538164x150", R.mipmap.pic4049f5134ad44a24460e391c204bf538164x150);
        map.put("ddb9276238dd9a301bc995087e94efcf164x150", R.mipmap.picddb9276238dd9a301bc995087e94efcf164x150);
        offlineCountryMap = Collections.unmodifiableMap(map);
    }


    static {
        Map map = new HashMap();
        map.put("mdd_jp", "日本'99");
        map.put("mdd_ko", "韩国'99");
        map.put("mdd_sea", "泰国'99^新加坡'99^马来西亚'99^印度尼西亚'99^柬埔寨'99^越南'99^菲律宾'99");
        map.put("mdd_europe_hot", "英国'99^法国'99^西班牙'99^葡萄牙'99^意大利'99^希腊'99^德国'99^瑞士'99^捷克'99^土耳其'99^俄罗斯'99");
        map.put("mdd_north_europe", "瑞典'99^挪威'99^芬兰'99^丹麦'99^冰岛'99");
        map.put("mdd_usa_ca", "美国'99^加拿大'99");
        map.put("mdd_aus_new", "澳大利亚'99^新西兰'99");
        offlineAllMap = Collections.unmodifiableMap(map);
    }


    public static void init(Context context) {
        offlineSupportMap = new HashMap<>();
        offlineStatusMap = new HashMap<>();
        offlineTipMap = transStringToMap(SharePrefUtil.getString(context, "offlineTip", "{}"));
//        if (BuildConfig.AUTO_TYPE)
//        offlineSupportMap.put("日本", "mdd_jp");
//        offlineSupportMap.putAll(FileUtils.getFilesAllName("mdd_aus_new"));
//        offlineSupportMap.putAll(FileUtils.getFilesAllName("mdd_europe_hot"));
//        offlineSupportMap.putAll(FileUtils.getFilesAllName("mdd_jp"));
//        offlineSupportMap.putAll(FileUtils.getFilesAllName("mdd_ko"));
//        offlineSupportMap.putAll(FileUtils.getFilesAllName("mdd_north_europe"));
//        offlineSupportMap.putAll(FileUtils.getFilesAllName("mdd_sea"));
//        offlineSupportMap.putAll(FileUtils.getFilesAllName("mdd_usa_ca"));
//        offlineStatusMap.put("日本", 1);
//        offlineStatusMap.put("泰国", 3);
//        offlineStatusMap.put("美国", 3);
//        offlineStatusMap.putAll(FileUtils.getFilesAllName("mdd_aus_new", 1));
//        offlineStatusMap.putAll(FileUtils.getFilesAllName("mdd_europe_hot", 1));
//        offlineStatusMap.putAll(FileUtils.getFilesAllName("mdd_jp", 1));
//        offlineStatusMap.putAll(FileUtils.getFilesAllName("mdd_ko", 1));
//        offlineStatusMap.putAll(FileUtils.getFilesAllName("mdd_north_europe", 1));
//        offlineStatusMap.putAll(FileUtils.getFilesAllName("mdd_sea", 1));
//        offlineStatusMap.putAll(FileUtils.getFilesAllName("mdd_usa_ca", 1));

        if (!BuildConfig.AUTO_TYPE.equals("go")) {
            Cursor cursor = context.getContentResolver().query(CONTENT_URI_LQ, null, null, new String[]{"mdd"}, null);
            try {
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String path = cursor.getString(cursor.getColumnIndex("Id"));
                        int state = Integer.valueOf(cursor.getString(cursor.getColumnIndex("Status")));
                        if (STATE_1 == state) {
                            offlineSupportMap.putAll(FileUtils.getFilesAllName(path));
                            offlineStatusMap.putAll(FileUtils.getFilesAllName(path, state));
                        } else if (STATE_2 == state) {
                            offlineSupportMap.putAll(transStringToMap(offlineAllMap.get(path)));
                            Map<String, String> map = transStringToMap(offlineAllMap.get(path));
                            for (Map.Entry<String, String> entry : map.entrySet()) {
                                offlineStatusMap.put(entry.getKey(), state);
                            }
                        } else if (STATE_3 == state) {
                            offlineSupportMap.putAll(transStringToMap(offlineAllMap.get(path)));
                            offlineStatusMap.putAll(transStringToMapInteger(offlineAllMap.get(path)));
                        }

                    }
                } else {
                    Log.e(TAG, "cuusor null");
                }
            } catch (Exception e) {
                Log.e(TAG, "没有获取到定位信息");
                e.printStackTrace();
            } finally {
                if (cursor != null) cursor.close();
            }
        }

    }

    public static String getTimeFromLong(long diff) {
        final String HOURS = "h";
        final String MINUTES = "min";
        final String SECONDS = "sec";

        final long MS_IN_A_DAY = 1000 * 60 * 60 * 24;
        final long MS_IN_AN_HOUR = 1000 * 60 * 60;
        final long MS_IN_A_MINUTE = 1000 * 60;
        final long MS_IN_A_SECOND = 1000;
        //Date currentTime = new Date();
        //long numDays = diff / MS_IN_A_DAY;
        //diff = diff % MS_IN_A_DAY;
        long numHours = diff / MS_IN_AN_HOUR;
        diff = diff % MS_IN_AN_HOUR;
        long numMinutes = diff / MS_IN_A_MINUTE;
        diff = diff % MS_IN_A_MINUTE;
        long numSeconds = diff / MS_IN_A_SECOND;
        diff = diff % MS_IN_A_SECOND;
        long numMilliseconds = diff;

        StringBuffer buf = new StringBuffer();
        if (numHours > 0) {
            buf.append(numHours + " " + HOURS + ", ");
        }

        if (numMinutes > 0) {
            buf.append(numMinutes + " " + MINUTES);
        }

        buf.append(numSeconds + " " + SECONDS + " " + numMilliseconds);

        String result = buf.toString();


        return result;
    }

    public void getJsonData(final Context context, final String filePath, final OnOfflineLister listener) {
        //离线数据
        ThreadPoolManager.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                if (FileUtils.isFileExists(filePath)) {
                    long time = System.currentTimeMillis();
                    final String json = FileIOUtils.readFile2String(filePath);
                    Log.e(TAG, "run: " + OffLineUtil.getTimeFromLong(System.currentTimeMillis() - time));
//                    toursimDataBeanList.addAll(FastJsonUtil.changeJsonToList(json, InterestPointBean.DataBean.class));
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                                listener.complete(json);
                        }
                    });
//                    rvInterestPoint.scrollToPosition(0);
                }else {
                    listener.error();
                }
            }
        });
    }

    /**
     * 方法名称:transMapToString
     * 传入参数:map
     * 返回值:String 形如 username'chenziwen^password'1234
     */
    public static String transMapToString(Map map) {
        java.util.Map.Entry entry;
        StringBuffer sb = new StringBuffer();
        for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); ) {
            entry = (java.util.Map.Entry) iterator.next();
            sb.append(entry.getKey().toString()).append("'").append(null == entry.getValue() ? "" :
                    entry.getValue().toString()).append(iterator.hasNext() ? "^" : "");
        }
        return sb.toString();
    }

    /**
     * 方法名称:transStringToMap
     * 传入参数:mapString 形如 username'chenziwen^password'1234
     * 返回值:Map
     */
    public static Map transStringToMap(String mapString) {
        Map map = new HashMap();
        java.util.StringTokenizer items;
        for (StringTokenizer entrys = new StringTokenizer(mapString, "^"); entrys.hasMoreTokens();
             map.put(items.nextToken(), items.hasMoreTokens() ? ((Object) (items.nextToken())) : null))
            items = new StringTokenizer(entrys.nextToken(), "'");
        return map;
    }

    public static Map transStringToMapInteger(String mapString) {
        Map map = new HashMap();
        java.util.StringTokenizer items;
        for (StringTokenizer entrys = new StringTokenizer(mapString, "^"); entrys.hasMoreTokens();
             map.put(items.nextToken(), items.hasMoreTokens() ? (Integer.valueOf(items.nextToken())) : null))
            items = new StringTokenizer(entrys.nextToken(), "'");
        return map;
    }

}
