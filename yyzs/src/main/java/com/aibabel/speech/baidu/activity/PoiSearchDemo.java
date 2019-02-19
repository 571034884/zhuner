package com.aibabel.speech.baidu.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;

import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiBoundSearchOption;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.aibabel.speech.R;
import com.aibabel.speech.baidu.BdMap;
import com.aibabel.speech.baidu.util.PoiOverlay;
import com.aibabel.speech.util.L;
import com.baidu.mapapi.utils.DistanceUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 演示poi搜索功能
 */
public class PoiSearchDemo extends FragmentActivity implements
        OnGetPoiSearchResultListener {

    private PoiSearch mPoiSearch = null;

    private BaiduMap mBaiduMap = null;
    private List<String> suggest;
    /**
     * 搜索关键字输入窗口
     */
    private EditText editCity = null;
    private AutoCompleteTextView keyWorldsView = null;
    private ArrayAdapter<String> sugAdapter = null;
    private int loadIndex = 10;

    LatLng center = new LatLng(39.92235, 116.380338);
    int radius = 100;
    LatLng southwest = new LatLng( 39.92235, 116.380338 );
    LatLng northeast = new LatLng( 39.947246, 116.414977);
    LatLngBounds searchbound = new LatLngBounds.Builder().include(southwest).include(northeast).build();

    int searchType = 0;  // 搜索的类型，在显示时区分



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poisearch);
        BdMap.getInstance().init(PoiSearchDemo.this);
//        BdMap.getInstance().init(this);

        // 初始化搜索模块，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);





        editCity = (EditText) findViewById(R.id.city);
        keyWorldsView = (AutoCompleteTextView) findViewById(R.id.searchkey);
        sugAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line);
        keyWorldsView.setAdapter(sugAdapter);
        keyWorldsView.setThreshold(1);
        mBaiduMap = ((SupportMapFragment) (getSupportFragmentManager()
                .findFragmentById(R.id.map))).getBaiduMap();
        /**
         * 当输入关键字变化时，动态更新建议列表
         */
        keyWorldsView.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {

            }

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2,
                                      int arg3) {
                if (cs.length() <= 0) {
                    return;
                }

                /**
                 * 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
                 */

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mPoiSearch.destroy();

        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * 响应城市内搜索按钮点击事件
     *
     * @param v
     */
    public void searchButtonProcess(View v) {
        searchType = 1;
        String citystr = editCity.getText().toString();
        String keystr = keyWorldsView.getText().toString();
        mPoiSearch.searchInCity((new PoiCitySearchOption())
                .city(citystr).keyword(keystr).pageNum(loadIndex));
    }

    /**
     * 响应周边搜索按钮点击事件
     *
     * @param v
     */
    public void  searchNearbyProcess(View v) {
        searchType = 2;
        L.e("----------"+BdMap.getInstance().getLocation(PoiSearchDemo.this));
        center = new LatLng(BdMap.getInstance().getLocData().latitude, BdMap.getInstance().getLocData().longitude);
        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption().keyword("美食"
                .toString()).sortType(PoiSortType.distance_from_near_to_far).location(center)
                .radius(1000).pageNum(10);
        mPoiSearch.searchNearby(nearbySearchOption);
    }

    public void goToNextPage(View v) {
        loadIndex++;
        searchButtonProcess(null);
    }

    /**
     * 响应区域搜索按钮点击事件
     *
     * @param v
     */
    public void searchBoundProcess(View v) {
        searchType = 3;
        mPoiSearch.searchInBound(new PoiBoundSearchOption().bound(searchbound)
                .keyword(keyWorldsView.getText().toString()));

    }


    /**
     * 获取POI搜索结果，包括searchInCity，searchNearby，searchInBound返回的搜索结果
     * @param result
     */
    public void onGetPoiResult(PoiResult result) {

        if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            Toast.makeText(PoiSearchDemo.this, "未找到结果", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            mBaiduMap.clear();
            PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(result);
            overlay.addToMap();
            overlay.zoomToSpan();

          List<PoiInfo> list=  result.getAllPoi();
            for (PoiInfo p:list) {
//                L.e("---"+p.address);
//                L.e("---"+p.city);
//                L.e("---"+p.name);
//                L.e("---"+p.phoneNum);
//                L.e("---"+p.postCode);
//                L.e("---"+p.uid);
//                L.e("---"+p.hasCaterDetails);
//                L.e("---"+p.isPano);
//                L.e("---"+p.location);
//                L.e("---"+p.type);


                mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
                        .poiUid(p.uid));





            }

            switch( searchType ) {
                case 2:

                    showNearbyArea(center, radius);

                    break;
                case 3:
                    showBound(searchbound);
                    break;
                default:
                    break;
            }

            return;
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {

            // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
            String strInfo = "在";
            for (CityInfo cityInfo : result.getSuggestCityList()) {
                strInfo += cityInfo.city;
                strInfo += ",";
            }
            strInfo += "找到结果";
            Toast.makeText(PoiSearchDemo.this, strInfo, Toast.LENGTH_LONG)
                    .show();
        }
    }
    /**
   * 获取POI详情搜索结果，得到searchPoiDetail返回的搜索结果
    * @param result
    */
    @Override
    public void onGetPoiDetailResult(PoiDetailResult result) {

//       L.e(result.getDetailUrl());
//       L.e(result.address);
//       L.e(result.getDetailUrl());
        L.e(result.name);
//        L.e(result.getLocation()+"");
//        L.e(result.getPrice()+"");
//        L.e(result.getOverallRating()+"");
//        L.e(result.getTag()+"");
//        L.e(result.getType()+"");
        L.e(result.getShopHours()+"-----");





        if (result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(PoiSearchDemo.this, "抱歉，未找到结果", Toast.LENGTH_SHORT)
                    .show();
        } else {



        }
    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        L.e("---------PoiIndoorResult");

    }








    private class MyPoiOverlay extends PoiOverlay {

        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            PoiInfo poi = getPoiResult().getAllPoi().get(index);
            // if (poi.hasCaterDetails) {
            mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
                    .poiUid(poi.uid));
            // }
            return true;
        }
    }

    /**
     * 对周边检索的范围进行绘制
     * @param center
     * @param radius
     */
    public void showNearbyArea( LatLng center, int radius) {
        BitmapDescriptor centerBitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_geo);
        MarkerOptions ooMarker = new MarkerOptions().position(center).icon(centerBitmap);
        mBaiduMap.addOverlay(ooMarker);

        OverlayOptions ooCircle = new CircleOptions().fillColor( 0xCCCCCC00 )
                .center(center).stroke(new Stroke(5, 0xFFFF00FF ))
                .radius(radius);
        mBaiduMap.addOverlay(ooCircle);
    }

    /**
     * 对区域检索的范围进行绘制
     * @param bounds
     */
    public void showBound( LatLngBounds bounds) {
        BitmapDescriptor bdGround = BitmapDescriptorFactory
                .fromResource(R.mipmap.ground_overlay);

        OverlayOptions ooGround = new GroundOverlayOptions()
                .positionFromBounds(bounds).image(bdGround).transparency(0.8f);
        mBaiduMap.addOverlay(ooGround);

        MapStatusUpdate u = MapStatusUpdateFactory
                .newLatLng(bounds.getCenter());
        mBaiduMap.setMapStatus(u);

        bdGround.recycle();
    }
}
