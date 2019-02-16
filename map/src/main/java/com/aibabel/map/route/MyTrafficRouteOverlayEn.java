package com.aibabel.map.route;

import android.graphics.Color;
import android.util.Log;

import com.aibabel.map.bean.trafficen.RoutesEnBean;
import com.aibabel.map.bean.trafficen.StepsEnBean;
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
 * 用于显示一条驾车路线的overlay，自3.4.0版本起可实例化多个添加在地图中显示，当数据中包含路况数据时，则默认使用路况纹理分段绘制
 */
public class MyTrafficRouteOverlayEn extends OverlayManager {

    private RoutesEnBean routes = null;
    boolean focus = false;
    private LatLng origin;
    private LatLng destination;

    int typeRoute;

    private BitmapDescriptor mRedTexture = BitmapDescriptorFactory.fromAsset("icon_road_blue_arrow.png");
    List<BitmapDescriptor> textureList = new ArrayList<>();
    List<Integer> textureIndexs = new ArrayList<>();


    /**
     * 构造函数
     *
     * @param baiduMap
     *            该DrivingRouteOvelray引用的 BaiduMap
     */
    public MyTrafficRouteOverlayEn(BaiduMap baiduMap) {
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
        List<StepsEnBean> steps = routes.getSteps();
//        if (steps != null && steps.size() >0 ){
//            for (StepsDriveZH step : steps){
//                //路段转折点
//                overlayOptionses.add((new MarkerOptions())
//                        .position(new LatLng(step.getStart_location().getLat(),step.getStart_location().getLng()))
//                        .anchor(0.5f, 0.5f)
//                        .zIndex(10)
//                        .rotate((360 - step.getDirection()))
//                        .icon(BitmapDescriptorFactory
//                                .fromAssetWithDpi("Icon_line_node.png")));
//
//            }
//        }
        //起点
        overlayOptionses.add((new MarkerOptions())
                .position(origin)
                .icon(getStartMarker() != null ? getStartMarker() :
                        BitmapDescriptorFactory
                                .fromAssetWithDpi("Icon_start.png")).zIndex(10));
        //终点
        overlayOptionses
                .add((new MarkerOptions())
                        .position(destination)
                        .icon(getTerminalMarker() != null ? getTerminalMarker() :
                                BitmapDescriptorFactory
                                        .fromAssetWithDpi("Icon_end.png"))
                        .zIndex(10));

        List<LatLng> latLngs = new ArrayList<LatLng>();
        //具体的路线点
        if (steps != null && steps.size() >0 ){
            for (StepsEnBean step : steps){
                List<LatLng> latLng = BaiDuUtil.currentLatLon(step.getPath());
                for (LatLng ll : latLng){
                    latLngs.add(ll);
                }
            }
        }
        if (typeRoute == 0){
            //驾车
            overlayOptionses.add(new PolylineOptions()
                    .points(latLngs).width(20).dottedLine(true)
                    .customTextureList(textureList)
                    .textureIndex(textureIndexs)
                    .zIndex(0));
        }else if (typeRoute == 2){
            //步行
            overlayOptionses.add(new PolylineOptions()
                    .points(latLngs).width(20).dottedLine(true)
                    .customTextureList(textureList)
                    .textureIndex(textureIndexs)
                    .zIndex(0));
        }

        return overlayOptionses;
    }

    /**
     * 设置路线数据
     * 
     * @param routes
     *            路线数据
     */
    public void setData(RoutesEnBean routes, String origin, String destination,int typeRoute) {
        String[] splitOrigin = origin.split(",");
        String[] splitDestination = destination.split(",");
        this.routes = routes;
        this.origin = new LatLng(Double.parseDouble(splitOrigin[0]), Double.parseDouble(splitOrigin[1]));
        this.destination = new LatLng(Double.parseDouble(splitDestination[0]), Double.parseDouble(splitDestination[1]));
        this.typeRoute = typeRoute;
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
     * 覆写此方法以改变默认绘制颜色
     * @return 线颜色
     */
    public int getLineColor() {
        return 0;
    }
    public List<BitmapDescriptor> getCustomTextureList() {
        ArrayList<BitmapDescriptor> list = new ArrayList<BitmapDescriptor>();
        list.add(BitmapDescriptorFactory.fromAsset("Icon_road_blue_arrow.png"));
        list.add(BitmapDescriptorFactory.fromAsset("Icon_road_green_arrow.png"));
        list.add(BitmapDescriptorFactory.fromAsset("Icon_road_yellow_arrow.png"));
        list.add(BitmapDescriptorFactory.fromAsset("Icon_road_red_arrow.png"));
        list.add(BitmapDescriptorFactory.fromAsset("Icon_road_nofocus.png"));
        return list;
    }
    /**
     * 覆写此方法以改变默认终点图标
     * 
     * @return 终点图标
     */
    public BitmapDescriptor getTerminalMarker() {
        return null;
    }


    @Override
    public final boolean onMarkerClick(Marker marker) {
        for (Overlay mMarker : mOverlayList) {
            if (mMarker instanceof Marker && mMarker.equals(marker)) {
                if (marker.getExtraInfo() != null) {
                    Log.e("MarkerClick","111");
                }
            }
        }
        return true;
    }

    @Override
    public boolean onPolylineClick(Polyline polyline) {
        boolean flag = false;
        for (Overlay mPolyline : mOverlayList) {
            if (mPolyline instanceof Polyline && mPolyline.equals(polyline)) {
                // 选中
                flag = true;
                break;
            }
        }
        setFocus(flag);
        return true;
    }

    public void setFocus(boolean flag) {
        focus = flag;
        for (Overlay mPolyline : mOverlayList) {
            if (mPolyline instanceof Polyline) {
                // 选中
                ((Polyline) mPolyline).setFocus(flag);

                break;
            }
        }

    }
}
