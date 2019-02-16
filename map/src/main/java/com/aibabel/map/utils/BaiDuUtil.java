package com.aibabel.map.utils;

import com.aibabel.map.bean.TransitBusBean;
import com.aibabel.map.bean.TransitDetailsBean;
import com.aibabel.map.bean.transiten.TransitDetailEnBean;
import com.aibabel.map.bean.transiten.TransitRoutesEnBean;
import com.aibabel.map.bean.transiten.TransitSchemesEnBean;
import com.aibabel.map.bean.transiten.TransitStepsEnBean;
import com.aibabel.map.bean.transitzh.TransitRoutesZhBean;
import com.aibabel.map.bean.transitzh.TransitStepsZhBean;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fytworks on 2018/11/29.
 */

public class BaiDuUtil {

    /**
     * 判断是国内or国外
     *
     * @param type
     * @return
     */
    public static String getCountryType(String type) {
        if (type.equals("bd09ll")) {
            return "0";
        } else {
            return "1";
        }
    }

    /**
     * 返回URL
     *
     * @param locationWhere 国内外
     * @param type 类型
     * @return url
     */
    public static String getUrl(int locationWhere, int type) {
        String url = "";
        switch (type) {
            case BaiDuConstant.DRIVING_MODE:
                if (locationWhere ==1) {
                    url = ApiConstant.API_DRIVE_ZH;
                } else if (locationWhere == 0){
                    url = ApiConstant.API_DRIVE_EN;
                }
                break;
            case BaiDuConstant.TRANSIT_MODE:
                if (locationWhere == 1) {
                    url = ApiConstant.API_TRANSIT_ZH;
                } else if (locationWhere == 0){
                    url = ApiConstant.API_TRANSIT_EN;
                }
                break;
            case BaiDuConstant.WALKING_MODE:
                if (locationWhere == 1) {
                    url = ApiConstant.API_WALK_ZH;
                } else if (locationWhere == 0){
                    url = ApiConstant.API_WALK_EN;
                }
                break;
        }
        return url;
    }

    public static String getModeType(int type) {
        String mode = "";
        switch (type) {
            case BaiDuConstant.DRIVING_MODE:
                mode = "driving";
                break;
            case BaiDuConstant.TRANSIT_MODE:
                mode = "transit";
                break;
            case BaiDuConstant.WALKING_MODE:
                mode = "walking";
                break;
        }
        return mode;
    }


    public static List<LatLng> currentLatLon(String step) {
        List<LatLng> points11 = new ArrayList<>();

        if (step.equals("")){
            return points11;
        }

        String[] latlon = step.split(";");
        for (int k = 0; k < latlon.length; k++) {
            String s = latlon[k];
            points11.add(latLon(s));
        }
        return points11;
    }

    private static LatLng latLon(String latlon) {
        String[] lalo = latlon.split(",");
        return new LatLng(Double.parseDouble(lalo[1]), Double.parseDouble(lalo[0]));
    }

    /**
     * type 类型
     * 1：火车     2：飞机    3：公交    4：驾车    5：步行    6：大巴
     * <p>
     * 获取地铁站数
     *
     * @param steps 当前线路
     * @return 处理过的地铁站数
     */
    public static String getLineCountZh(List<List<TransitStepsZhBean>> steps) {
        int subway = 0;
        for (int i = 0; i < steps.size(); i++) {
            List<TransitStepsZhBean> transitStepsZhBeans = steps.get(i);
            for (TransitStepsZhBean step : transitStepsZhBeans) {
                int type = step.getType();
                if (type == 3) {
                    subway += step.getVehicle().getStop_num();
                }
                break;
            }
        }
        return subway + "站";
    }
    /**
     * type 类型
     * 1：火车     2：飞机    3：公交    4：驾车    5：步行    6：大巴
     * <p>
     * 获取地铁站数
     *
     * @param steps 当前线路
     * @return 处理过的地铁站数
     */
    public static String getLineCountEn(List<TransitStepsEnBean> steps) {
        int subway = 0;
        for (TransitStepsEnBean stepsEnBean : steps){
            List<TransitSchemesEnBean> schemes = stepsEnBean.getSchemes();
            for (TransitSchemesEnBean schemesEnBean:schemes){
                int type = schemesEnBean.getVehicle_info().getType();
                if (type == 3) {
                    subway += schemesEnBean.getVehicle_info().getDetail().getStop_num();
                }
                break;
            }
        }
        return subway + "站";
    }


    /**
     * type 类型
     * 1：火车     2：飞机    3：公交    4：驾车    5：步行    6：大巴
     * <p>
     * 获取步行公里
     *
     * @param steps 当前线路
     * @return 处理过的地铁站数
     */
    public static String getLineWalkEn(List<TransitStepsEnBean> steps) {
        double km = 0;
        for (TransitStepsEnBean stepsEnBean : steps){
            List<TransitSchemesEnBean> schemes = stepsEnBean.getSchemes();
            for (TransitSchemesEnBean schemesEnBean:schemes){
                int type = schemesEnBean.getVehicle_info().getType();
                if (type == 5) {
                    km += schemesEnBean.getDistance();
                }
                break;
            }
        }
        return MathUtil.mathDivision(km, 1000, 2) + "公里";
    }
    /**
     * 获取上车地点
     *
     * @param steps
     * @return 返回上车点
     */
    public static String getLineStartEn(List<TransitStepsEnBean> steps) {
        String start = "";
        for (TransitStepsEnBean stepsEnBean : steps){
            List<TransitSchemesEnBean> schemes = stepsEnBean.getSchemes();
            for (TransitSchemesEnBean schemesEnBean:schemes){
                int type = schemesEnBean.getVehicle_info().getType();
                if (type == 3 && start.equals("")) {
                    start = schemesEnBean.getVehicle_info().getDetail().getOn_station();
                    break;
                }
                break;
            }
        }
        return start + "上车";
    }

    /**
     * type 类型
     * 1：火车     2：飞机    3：公交    4：驾车    5：步行    6：大巴
     * <p>
     * 获取步行公里
     *
     * @param steps 当前线路
     * @return 处理过的地铁站数
     */
    public static String getLineWalkZh(List<List<TransitStepsZhBean>> steps) {
        double km = 0;
        for (int i = 0; i < steps.size(); i++) {
            List<TransitStepsZhBean> transitStepsZhBeans = steps.get(i);
            for (TransitStepsZhBean step : transitStepsZhBeans) {
                int type = step.getType();
                if (type == 5) {
                    km += step.getDistance();
                }
                break;
            }
        }
        return MathUtil.mathDivision(km, 1000, 2) + "公里";
    }


    /**
     * 获取上车地点
     *
     * @param steps
     * @return 返回上车点
     */
    public static String getLineStartZh(List<List<TransitStepsZhBean>> steps) {
        String start = "";
        for (int i = 0; i < steps.size(); i++) {
            List<TransitStepsZhBean> transitStepsZhBeans = steps.get(i);
            for (TransitStepsZhBean step : transitStepsZhBeans) {
                int type = step.getType();
                if (type == 3 && start.equals("")) {
                    start = step.getVehicle().getStart_name();
                    break;
                }
            }
        }
        return start + "上车";
    }

    /**
     * 获取耗时
     *
     * @param zhBean
     * @return
     */
    public static String getLineTimer(int zhBean) {
        int duration = zhBean / 60;
        return duration + "分钟";
    }

    /**
     * type 类型
     * 1：火车     2：飞机    3：公交    4：驾车    5：步行    6：大巴
     *
     * busType 类型
     * 公交0  地铁1
     * <p>
     * 获取地铁名称集合
     *
     * @param steps 当前线路
     * @return 地铁集合
     */
    public static List<TransitBusBean> getLineNameZh(List<List<TransitStepsZhBean>> steps) {
        List<TransitBusBean> name = new ArrayList<TransitBusBean>();
        for (int i = 0; i < steps.size(); i++) {
            List<TransitStepsZhBean> transitStepsZhBeans = steps.get(i);
            for (TransitStepsZhBean step : transitStepsZhBeans) {
                int type = step.getType();
                if (type == 3) {
                    //公交or地铁名称   地铁名称 去掉地铁，例:地铁13号线  ==》 13号线
                    String busName = step.getVehicle().getName().replaceAll("地铁", "");
                    //方式  公交0  地铁1
                    int busType = step.getVehicle().getType();
                    TransitBusBean bean = new TransitBusBean(busName,busType);
                    name.add(bean);
                }
                break;
            }
        }
        return name;
    }

    /**
     * type 类型
     * 1：火车     2：飞机    3：公交    4：驾车    5：步行    6：大巴
     *
     * busType 类型
     * 公交0  地铁1
     * <p>
     * 获取地铁名称集合
     *
     * @param steps 当前线路
     * @return 地铁集合
     */
    public static List<TransitBusBean> getLineNameEn(List<TransitStepsEnBean> steps) {
        List<TransitBusBean> name = new ArrayList<TransitBusBean>();
        for (TransitStepsEnBean stepsEnBean : steps){
            List<TransitSchemesEnBean> schemes = stepsEnBean.getSchemes();
            for (TransitSchemesEnBean schemesEnBean:schemes){
                int type = schemesEnBean.getVehicle_info().getType();
                if (type == 3) {
                    TransitDetailEnBean detail = schemesEnBean.getVehicle_info().getDetail();
                    String busName = detail.getName();//公交OR地铁名称
                    int busType = detail.getType();
                    TransitBusBean bean = new TransitBusBean(busName,busType);
                    name.add(bean);
                }
                break;
            }
        }
        return name;
    }

    /**
     * 国内公交header
     * @param routes
     * @return
     */
    public static List<TransitDetailsBean> getTransitHeaderZh(List<TransitRoutesZhBean> routes){
        List<TransitDetailsBean> listHeader = new ArrayList<TransitDetailsBean>();
        for (TransitRoutesZhBean bean : routes){
            TransitDetailsBean tdb = new TransitDetailsBean();
            tdb.setLineName(getLineNameZh(bean.getSteps()));
            tdb.setSubWay(getLineCountZh(bean.getSteps()));
            tdb.setStartName(getLineStartZh(bean.getSteps()));
            tdb.setTimer(getHoursMin(bean.getDuration()));
            listHeader.add(tdb);
        }
        return listHeader;
    }

    /**
     * 国外公交header
     * @param routes
     * @return
     */
    public static List<TransitDetailsBean> getTransitHeaderEn(List<TransitRoutesEnBean> routes){

        List<TransitDetailsBean> listHeader = new ArrayList<TransitDetailsBean>();
        for (TransitRoutesEnBean bean : routes){
            TransitDetailsBean tdb = new TransitDetailsBean();
            tdb.setLineName(getLineNameEn(bean.getSteps()));
            tdb.setSubWay(getLineCountEn(bean.getSteps()));
            tdb.setStartName(getLineStartEn(bean.getSteps()));
            tdb.setTimer(getHoursMin(bean.getDuration()));
            listHeader.add(tdb);
        }
        return listHeader;
    }


    public static String getHoursMin(int seconds) {
        String hoursMin = "";
        int min = seconds/60;

        if (min < 60){
            //1小时以内
            hoursMin=min+"分钟";
        }else{
            int hours = min/60;
            int mins = min%60;

            hoursMin = hours+"小时"+mins+"分";

        }
        return hoursMin;
    }
}
