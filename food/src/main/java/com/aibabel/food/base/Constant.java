package com.aibabel.food.base;

import com.aibabel.food.R;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;

/**
 * 作者：SunSH on 2018/12/14 17:12
 * 功能：
 * 版本：1.0
 */
public class Constant {

    public static String CURRENT_CITY = "东京";

    public final static String METHOD_LANUCHER_GO = "GetReturnAddrByLatLng"; //启动页面跳转
    public final static String METHOD_HOMEPAGE_ALL = "GetFoodHomePage"; //首页全部接口
    public final static String METHOD_FILTER_LIST = "GetRegionFoodList"; //筛选后的没事列表
    public final static String METHOD_FILTER_WINDOW = "GetFoodCityTotal"; //筛选后的没事列表
    public final static String METHOD_KIND_LIST = "GetFoodType"; //分类列表
    public final static String METHOD_AREA_LIST = "GetFoodAllRegion"; //区域列表
    public final static String METHOD_SEARCH = "GetFoodSearchKeyWord"; //关键字搜索
    public final static String METHOD_HOT_SEARCH = "GetFoodHotSearch"; //热门搜索列表
    public final static String METHOD_AREA_CHANGE = "GetFoodCityList"; //城市列表
    public final static String METHOD_HTML_URL = "GetFoodH5PageUrl"; //获取h5页面

    public final static String KEY_HISTORY = "historyData"; //区域列表

    public final static int REQUEST_CODE_LAUNCHER = 111;//首页跳转请求码
    public final static int RESULT_CODE_AREA_SELECT = 666;//城市选择页面结果码

    public static final int LOADING_1X1 = R.mipmap.loading_1x1;
    public static final int LOADING_CIRCLE_1X1 = R.mipmap.loading_circle_1x1;
    public static final int LOADING_540X400 = R.mipmap.loading_540x400;
    public static final int LOADING_540X280 = R.mipmap.loading_540x280;
    public static final int LOAD_FAIL_1X1 = R.mipmap.load_fail_1x1;
    public static final int LOAD_FAIL_CIRCLE_1X1 = R.mipmap.load_fail_circle_1x1;
    public static final int LOAD_FAIL_540X400 = R.mipmap.load_fail_540x400;
    public static final int LOAD_FAIL_540X280 = R.mipmap.load_fail_540x280;

    public static final String HTML5_DETAIL = "http://destination.cdn.aibabel.com/food/H5/cateDetail1812190921.html?";
    public static final String HTML5_BANNER = "http://destination.cdn.aibabel.com/food/H5/foodSoft1812181807.html?";

    public static Map<String, Integer> kindMap = new HashMap<>();//初始化美食分类

    static {
        Map map = new HashMap<>();
        map.put("创意菜", R.mipmap.kind_chuangyi);
        map.put("地方料理", R.mipmap.kind_difang);
        map.put("海鲜", R.mipmap.kind_haixian);
        map.put("火锅", R.mipmap.kind_huoguo);
        map.put("居酒屋", R.mipmap.kind_jujiuwu);
        map.put("烤串", R.mipmap.kind_kaochuan);
        map.put("面食", R.mipmap.kind_mianshi);
        map.put("日式料理", R.mipmap.kind_rishi);
        map.put("肉料理", R.mipmap.kind_rou);
        map.put("寿司", R.mipmap.kind_shousi);
        map.put("西餐", R.mipmap.kind_xican);
        map.put("下午茶", R.mipmap.kind_xiawucha);
        map.put("亚洲料理", R.mipmap.kind_yazhou);
        map.put("中华料理", R.mipmap.kind_zhonghua);
        map.put("自助餐", R.mipmap.kind_zizhu);
        kindMap = Collections.unmodifiableMap(map);
    }
}
