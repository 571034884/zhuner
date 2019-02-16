package com.aibabel.map.route;

import android.graphics.Color;
import android.util.Log;

import com.aibabel.map.bean.transiten.TransitDetailEnBean;
import com.aibabel.map.bean.transiten.TransitRoutesEnBean;
import com.aibabel.map.bean.transiten.TransitSchemesEnBean;
import com.aibabel.map.bean.transiten.TransitStepsEnBean;
import com.aibabel.map.bean.transiten.TransitVehicleEnBean;
import com.aibabel.map.utils.BaiDuUtil;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于显示换乘路线的Overlay，自3.4.0版本起可实例化多个添加在地图中显示
 */
public class MyTransitRouteOverlayFytEn extends OverlayManager {

    private BitmapDescriptor mRedTexture = BitmapDescriptorFactory.fromAsset("icon_road_blue_arrow.png");
    List<BitmapDescriptor> textureList = new ArrayList<>();
    List<Integer> textureIndexs = new ArrayList<>();
    private TransitRoutesEnBean routes = null;
    private LatLng origin;
    private LatLng destination;
    /**
     * 构造函数
     *
     * @param baiduMap
     *            该TransitRouteOverlay引用的 BaiduMap 对象
     */
    public MyTransitRouteOverlayFytEn(BaiduMap baiduMap) {
        super(baiduMap);
    }

    @Override
    public final List<OverlayOptions> getOverlayOptions() {

        if (routes == null) {
            return null;
        }


        textureList.add(mRedTexture);
        textureIndexs.add(0);

        List<OverlayOptions> overlayOptionses = new ArrayList<OverlayOptions>();

        List<TransitStepsEnBean> stepsList = routes.getSteps();
        if (stepsList != null && stepsList.size() >0 ){
            for (int i = 0; i <stepsList.size();i++){
                List<TransitSchemesEnBean> stepsEn = stepsList.get(i).getSchemes();
                for (TransitSchemesEnBean step : stepsEn){
                    TransitVehicleEnBean vehicle_info = step.getVehicle_info();
                    /**
                     * 3 公交或者地铁
                     *  ----detail---type---0公交
                     *  ----detail---type---1地铁
                     * 5 步行
                     */
                    int type = vehicle_info.getType();
                    if (type == 5){
                        overlayOptionses.add((new MarkerOptions())
                                .position(new LatLng(step.getStart_location().getLat(),step.getStart_location().getLng()))
                                .anchor(0.5f, 0.5f).zIndex(10)
                                .icon(getIconForStep(5)));
                    }else if (type == 3){
                        TransitDetailEnBean detail = vehicle_info.getDetail();
                        int bus = detail.getType();
                        if (bus == 0){
                            overlayOptionses.add((new MarkerOptions())
                                    .position(new LatLng(step.getStart_location().getLat(),step.getStart_location().getLng()))
                                    .anchor(0.5f, 0.5f).zIndex(10)
                                    .icon(getIconForStep(0)));
                            break;
                        }else if (bus == 1){
                            overlayOptionses.add((new MarkerOptions())
                                    .position(new LatLng(step.getStart_location().getLat(),step.getStart_location().getLng()))
                                    .anchor(0.5f, 0.5f).zIndex(10)
                                    .icon(getIconForStep(1)));
                            break;
                        }

                    }

                }
            }
        }

        overlayOptionses.add((new MarkerOptions())
                .position(origin)
                .icon(getStartMarker() != null ? getStartMarker() :
                        BitmapDescriptorFactory
                                .fromAssetWithDpi("Icon_start.png")).zIndex(10));
        overlayOptionses
                .add((new MarkerOptions())
                        .position(destination)
                        .icon(getTerminalMarker() != null ? getTerminalMarker() :
                                BitmapDescriptorFactory
                                        .fromAssetWithDpi("Icon_end.png"))
                        .zIndex(10));

        List<LatLng> latLngs = new ArrayList<LatLng>();
        if (stepsList != null && stepsList.size() >0 ) {
            for (int i = 0; i <stepsList.size();i++){
                List<TransitSchemesEnBean> stepsEn = stepsList.get(i).getSchemes();
                for (TransitSchemesEnBean step : stepsEn){
                    List<LatLng> latLng = BaiDuUtil.currentLatLon(step.getPath());
                    for (LatLng ll:latLng){
                        latLngs.add(ll);
                    }
                    break;
                }
            }
        }
        if (latLngs.size() != 0){
            overlayOptionses.add(new PolylineOptions()
                    .points(latLngs).width(20).dottedLine(true)
                    .customTextureList(textureList)
                    .textureIndex(textureIndexs)
                    .zIndex(0));
        }

        return overlayOptionses;
    }

    private BitmapDescriptor getIconForStep(int type) {
        switch (type) {
            case 5:
                //步行
                return BitmapDescriptorFactory.fromAssetWithDpi("Icon_walk_route.png");
            case 0:
                //公交
            return BitmapDescriptorFactory.fromAssetWithDpi("Icon_bus_station.png");
            case 1:
                //地铁
                return BitmapDescriptorFactory.fromAssetWithDpi("Icon_subway_station.png");
            default:
                return null;
        }
    }

    /**
     * 设置路线数据
     *
     *            路线数据
     */
    public void setData(TransitRoutesEnBean routes, String origin, String destination) {
        String[] splitOrigin = origin.split(",");
        String[] splitDestination = destination.split(",");
        this.routes = routes;
        this.origin = new LatLng(Double.parseDouble(splitOrigin[0]), Double.parseDouble(splitOrigin[1]));
        this.destination = new LatLng(Double.parseDouble(splitDestination[0]), Double.parseDouble(splitDestination[1]));
    }

    /**
     * 覆写此方法以改变默认起点图标
     *
     * @return 起点图标
     */
    public BitmapDescriptor getStartMarker() {
        return null;
    }

    /**
     * 覆写此方法以改变默认终点图标
     *
     * @return 终点图标
     */
    public BitmapDescriptor getTerminalMarker() {
        return null;
    }

    public int getLineColor() {
        return 0;
    }


    @Override
    public boolean onPolylineClick(Polyline polyline) {
        return false;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        for (Overlay mMarker : mOverlayList) {
            if (mMarker instanceof Marker && mMarker.equals(marker)) {
                if (marker.getExtraInfo() != null) {
                    Log.e("MarkerClick","111");
                }
            }
        }
        return true;
    }
}
