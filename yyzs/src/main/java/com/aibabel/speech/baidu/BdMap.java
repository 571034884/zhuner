package com.aibabel.speech.baidu;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.district.DistrictSearch;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.aibabel.speech.util.L;

import java.util.List;

/**
 * public static final String COLUMN_ADDR = "addr";//详细地址信息
 public static final String COLUMN_COUNTRY = "country";//国家
 public static final String COLUMN_PROVINCE = "province";//省份
 public static final String COLUMN_CITY = "city";//城市
 public static final String COLUMN_DISTRICT = "district"; //区县
 public static final String COLUMN_STREET = "street"; //街道信息
 public static final String COLUMN_DESCRIBE = "describe";//位置描述信息
 public static final String COLUMN_LATITUDE = "latitude";//纬度
 public static final String COLUMN_LONGITUDE = "longitude"; //经度
 */
public class BdMap {

    //BDAbstractLocationListener为7.2版本新增的Abstract类型的监听接口
    //原有BDLocationListener接口暂时同步保留。具体介绍请参考后文中的说明
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private static BdMap mInstance;
    private String mLocation="";
    private MyLocationData locData;
    //让外面使用定位的回调
    UpdateListern listern;
    //让外面使用坐标转换的回调
    SearchListern searchListern;
    //让外面使用Poi检索的回调
    PoiSearchListern poiSearchListern;

    //当前所在城市
    private String mCity="";
    private String mCountry="";
    private String mAddress="";

    public String getmAddress() {
        return mAddress;
    }


    //坐标转换
    private GeoCoder mSearch ;

    //POI检索
    private PoiSearch mPoiSearch = null;

    private static final Uri CONTENT_URI = Uri.parse("content://com.aibabel.locationservice.provider.AibabelProvider/aibabel_location");

    public void setUpdateListern(UpdateListern updateListern) {
        listern=updateListern;
    }

    public void setPoiSearchListern(PoiSearchListern p) {
        poiSearchListern=p;
    }

    //单一实例
    public synchronized static BdMap getInstance() {
        if (mInstance == null)
            mInstance = new BdMap();
        return mInstance;
    }

    /**
     * 获取坐标
     * @param context
     * @return
     */
    public String getLocation(Context context) {

        try {
            Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Double lat=cursor.getDouble( cursor.getColumnIndex("latitude"));
                    Double lon=cursor.getDouble( cursor.getColumnIndex("longitude"));
                   L.e(lat+"-----------");
                   L.e(lon+"----------");
                    if (lat == 4.9E-324) {
                        mLocation="";
                    } else {
                        mLocation=lat+","+lon;
                    }

                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            L.e("getLocation--------"+e.getMessage());

        }
        return mLocation;
    }

    /**
     * 获取定位国家
     * @param context
     * @return
     */
    public String getCountry(Context context) {

        try {
            Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    L.e(cursor.getString( cursor.getColumnIndex("country"))+"------------");
                    mCountry=cursor.getString( cursor.getColumnIndex("country"));

                } while (cursor.moveToNext());

            }
            cursor.close();

        } catch (Exception e) {
            L.e("getCountry--------"+e.getMessage());
        }

        return mCountry;
    }

    public Double getLat(Context context) {
        Double lat=0d;
        try {
            Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    L.e(cursor.getDouble( cursor.getColumnIndex("latitude"))+"-----------");

                    lat=cursor.getDouble( cursor.getColumnIndex("latitude"));

                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            L.e("getLat--------"+e.getMessage());
        }
        return lat;
    }
    public Double getLong(Context context) {
        Double lat=0d;
        try {
            Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    L.e(cursor.getDouble( cursor.getColumnIndex("longitude"))+"-----------");

                    lat=cursor.getDouble( cursor.getColumnIndex("longitude"));

                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            L.e("getLong--------"+e.getMessage());
        }
        return lat;
    }


    /**
     * 获取城市
     * @param context
     * @return
     */
    public String getCity(Context context) {

        try {
            Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    L.e(cursor.getString( cursor.getColumnIndex("city"))+"------------");
                    mCity=cursor.getString( cursor.getColumnIndex("city"));

                } while (cursor.moveToNext());

            }
            cursor.close();

        } catch (Exception e) {
            L.e("getCity--------"+e.getMessage());
        }

        return mCity;
    }

    /**
     * 定位地图  locdata配置信息
     * @return
     */
    public MyLocationData getLocData() {
       return locData;
    }

    /**
     * 返回坐标转换对象
     * @return
     */
    public GeoCoder getmSearch() {
        return mSearch;
    }

    public  PoiSearch getmPoiSearch() {
        return  mPoiSearch;
    }

    public  void setSearchListern(SearchListern searchListern) {
       this. searchListern=searchListern;
    }

    /**
     * 初始化百度定位
     */
    public void init(Context context) {
//        mLocationClient = new LocationClient(context);
//        //声明LocationClient类
//        mLocationClient.registerLocationListener(myListener);
//        //注册监听函数
//        LocationClientOption option = new LocationClientOption();
//
//        //可选，设置定位模式，默认高精度
//        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//        option.setCoorType("bd09ll");
//        option.setScanSpan(30 *1000);
//        //可选，设置是否使用gps，默认false
//        //使用高精度和仅用设备两种定位模式的，参数必须设置为true
//        option.setOpenGps(true);
//        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false
//        option.setLocationNotify(false);
//
//        //可选，定位SDK内部是一个service，并放到了独立进程。
//
//        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)
//        option.setIgnoreKillProcess(false);
//        //可选，设置是否收集Crash信息，默认收集，即参数为false
//        option.SetIgnoreCacheException(false);
//        //可选，7.2版本新增能力
//        //如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位
//        option.setWifiCacheTimeOut(5 * 60 * 1000);
//        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false
//
//        option.setEnableSimulateGps(false);
//        //可选，是否需要地址信息，默认为不需要，即参数为false
//        //如果开发者需要获得当前点的地址信息，此处必须为true
//        option.setIsNeedAddress(true);
//
//        mLocationClient.setLocOption(option);
//        mLocationClient.start();
//        mSearch=GeoCoder.newInstance();
//        //地理位置转换经纬度
//        mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
//            @Override
//            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
//
//                if (geoCodeResult == null || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
//                    //没有检索到结果
//
//                } else {
//                    L.e("转换结果："+  geoCodeResult.getAddress());
//                    if (searchListern!=null) {
//                        searchListern.returnSearch(geoCodeResult);
//                    }
//                }
//
//                //获取地理编码结果
//            }
//            @Override
//            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
//                if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
//                    //没有找到检索结果
//                }
//
//                //获取反向地理编码结果
//            }
//        });
//
//        //POI检索监听
//        mPoiSearch = PoiSearch.newInstance();
//        mPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
//            @Override
//            public void onGetPoiResult(PoiResult poiResult) {
//               int num= poiResult.getTotalPoiNum();
//                if (poiSearchListern!=null) {
//                    poiSearchListern.getTotal(num);
//                }
//                List<PoiInfo> list = poiResult.getAllPoi();
//                if (num > 10) {
//                    for (int i = 0; i < 10; i++) {
//                        mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
//                                .poiUid(list.get(i).uid));
//                    }
//
//                } else {
//                    for (int i = 0; i < num; i++) {
//                        mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
//                                .poiUid(list.get(i).uid));
//                    }
//                }
//
//            }
//
//            @Override
//            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
//                if (poiSearchListern!=null) {
//                    poiSearchListern.returnPoiSearch(poiDetailResult);
//                    L.e("POI----"+poiDetailResult.name);
//                }
//
//            }
//
//            @Override
//            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
//
//            }
//        });
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {

//            L.e(location.getAddrStr() + "");
//            L.e(location.getCity() + "");
//            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
//            //以下只列举与国内外判断相关的内容
//            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

//            //BDLocation.getLocationWhere()方法可获得当前定位点是否是国内，它的取值及含义如下：
//            //BDLocation.LOCATION_WHERE_IN_CN：当前定位点在国内；
//            //BDLocation.LOCATION_WHERE_OUT_CN：当前定位点在海外；
//            //其他：无法判定。
//
//            L.e(">>>>>>>>>>>>>>>>>>>"+location.getLocationID());
            mCity=location.getCity();
            mAddress=location.getStreet();
            double latitude = location.getLatitude();    //获取纬度信息
            double longitude = location.getLongitude();    //获取经度信息

            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(0).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();



            float radius = location.getRadius();    //获取定位精度，默认值为0.0f
//            L.e("精度："+radius);
//            L.e(latitude+","+longitude);
            mLocation=latitude+","+longitude;
            if (listern!=null) {
                listern.getlocD(locData);
            }

            String coorType = location.getCoorType();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

            int errorCode = location.getLocType();
        }
    }




    public interface UpdateListern{
        public void getlocD(MyLocationData myLocationData);
    }

    public interface SearchListern{
        public void returnSearch(GeoCodeResult result);
    }

    public interface PoiSearchListern{
        public  void returnPoiSearch(PoiDetailResult poiDetailResult);

        public void getTotal(int total);
    }

    /**
     * 销毁资源
     */
    public void destroy() {
        if (mLocationClient!=null) {
            mLocationClient.registerLocationListener(myListener);
            mLocationClient.stop();
            mLocationClient=null;
        }

        if (mSearch!=null) {
            mSearch.destroy();
        }
    }

}
