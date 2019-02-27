package com.aibabel.travel.utils;

import android.os.Environment;
import android.text.TextUtils;

import com.aibabel.travel.activity.WorldActivity;
import com.aibabel.travel.bean.CityBean;
import com.aibabel.travel.bean.CityItemBean;
import com.aibabel.travel.bean.CountryData;
import com.aibabel.travel.bean.OfflineBean;
import com.aibabel.travel.bean.SpotBean;
import com.aibabel.travel.db.DataDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StringUtil {


    private static final String path_pre = Environment.getExternalStorageDirectory() + "/travel/";

    //计算歌曲时间
    public static String calculateTime(int time) {
        int minute;
        int second;
        if (time >= 60) {
            minute = time / 60;
            second = time % 60;
            if (second < 10) {
                return minute + ":" + "0" + second;
            } else {
                return minute + ":" + second;
            }

        } else if (time < 60) {
            second = time;
            if (second < 10) {
                return "0:" + "0" + second;
            } else {
                return "0:" + second;
            }

        }
        return "0:00";
    }


    /**
     * 格式化时间，将毫秒转换为分:秒格式
     *
     * @param time
     * @return
     */
    public static String formatTime(long time) {
        String min = time / (1000 * 60) + "";
        String sec = time % (1000 * 60) + "";
        if (min.length() < 2) {
            min = "0" + time / (1000 * 60) + "";
        } else {
            min = time / (1000 * 60) + "";
        }
        if (sec.length() == 4) {
            sec = "0" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 3) {
            sec = "00" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 2) {
            sec = "000" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 1) {
            sec = "0000" + (time % (1000 * 60)) + "";
        }
        return min + ":" + sec.trim().substring(0, 2);
    }

    /**
     * 获取各个大洲的国家和热门城市
     *
     * @param list
     * @param state
     * @return
     */

    public static List<CityItemBean> getCountrys(List<CountryData.DataBean.ResultsBean> list, String state) {
        List<CityItemBean> items = new ArrayList<>();

        if (null == list) {
            return items;
        }
        for (CountryData.DataBean.ResultsBean bean : list) {
            CityItemBean itemBean = new CityItemBean();

            try {
                String path = bean.getCover();
                int start = path.lastIndexOf("/") + 1;
                int end = path.indexOf(".jpg");
                String key = path.substring(start, end);

                String name = bean.getName();
                String mstate = bean.getState();

                if (!TextUtils.isEmpty(mstate) && TextUtils.equals(mstate, state)) {
                    int cover_path = Constant.map.get(key);
                    itemBean.setCover(bean.getCover());
                    itemBean.setCover_path(cover_path);
                    itemBean.setName(name);
                    itemBean.setId(bean.getId());
                    itemBean.setOffline(bean.getOffline());
                    items.add(itemBean);
                }

                if (!TextUtils.isEmpty(bean.getHotCountry()) && TextUtils.equals(bean.getHotCountry(), state)) {
                    int cover_path = Constant.map240.get(key);
                    itemBean.setCover(bean.getCover());
                    itemBean.setCover_path(cover_path);
                    itemBean.setName(bean.getName());
                    itemBean.setOffline(bean.getOffline());
                    itemBean.setId(bean.getId());
                    items.add(itemBean);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return items;
    }


    /**
     * 由于离线包中数据缺失，去除佛罗伦萨中的佛罗伦萨旧宫
     *
     * @param list
     * @return
     */
    public static List<SpotBean.DataBean.ResultsBean> reList(List<SpotBean.DataBean.ResultsBean> list) {
        if (null == list || list.size() == 0) {
            return list;
        }

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == 1420) {
                list.remove(i);
            }

        }
        return list;

    }


    /**
     * 从数据库中获取的id和实际的ID映射表
     *
     * @return
     */
    public static Map<String, Integer> getOffline() {
        Map<String, Integer> map = new HashMap<>();
        map.put("jqdl_aus", 13);
        map.put("jqdl_fr", 5);
        map.put("jqdl_it", 6);
        map.put("jqdl_en", 7);
        map.put("jqdl_ru", 31);
        map.put("jqdl_ch", 40);
        map.put("jqdl_jp", 8);
        map.put("jqdl_ko", 9);
        map.put("jqdl_th", 20);
        map.put("jqdl_usa", 12);
        return map;
    }

    /**
     * 通过value查找key值
     * @param map
     * @param id
     * @return
     */
    public static String getKeyByValue(Map<String,Integer> map,int id){
        String key = "";
        Set set = map.entrySet();
        Iterator it=set.iterator();
        while(it.hasNext()) {
            Map.Entry entry=(Map.Entry)it.next();
            if(entry.getValue().equals(id)) {
                key = (String)entry.getKey();
                return key;
            }

        }
        return key;
    }




    /**
     * 判定是否支持离线
     *
     * @param id
     * @return
     */
    public static int isSupportOffline(int id, List<OfflineBean> list) {
        int result = 0;

        if (null == list || list.size() == 0)
            return result;
        Map<String, Integer> map = StringUtil.getOffline();

        if (getOffline().containsValue(id)) {
            for (OfflineBean bean : list) {
                String mmid = bean.getId();
                if (getOffline().containsKey(mmid) && map.get(mmid) == id) {
                    if (TextUtils.equals(bean.getStatus(), "1")) {
                        result = 1;
                        return result;
                    } else if (TextUtils.equals("10", bean.getStatus())
                            || TextUtils.equals("12", bean.getStatus())) {
//                        MyDialog.showDialog(WorldActivity.this, "离线包已下载，快去安装吧！");
                        result = 2;
                        return result;
                    } else if (TextUtils.equals("99", bean.getStatus())
                            || TextUtils.equals("-1", bean.getStatus())
                            || TextUtils.equals("5", bean.getStatus())) {
//                        MyDialog.showDialog(WorldActivity.this, "有离线包,快去联网下载吧！");
                        result = 3;
                        return result;
                    }


                    return result;
                }
            }

        }


        return result;
    }


    /**
     * 除法取整
     *
     * @param a
     * @param b
     * @return
     */
    public static int getInt(int a, int b) {


        int c = 0;
        if (b == 0) {
            return c;
        }

        if (a % b != 0) {
            c = a / b + 1;
        } else {
            c = a / b;
        }
        return c;
    }


    /**
     * @return
     */
    public static int isOffline(List<OfflineBean> list, int id) {
        int result = 0;

        if (null == list) {
            return result;
        }
        for (OfflineBean bean : list) {
            if (getOffline().get(bean.getId()) == id) {

                if (TextUtils.equals(bean.getStatus(), "10")//下载成功，未安装
                        || TextUtils.equals(bean.getStatus(), "1")//安装成功
                        || TextUtils.equals(bean.getStatus(), "12")) {//解压失败
                    result = 1;
                    return result;
                } else if (TextUtils.equals(bean.getStatus(), "99")//服务器有离线包没有下载
                        || TextUtils.equals(bean.getStatus(), "-1")//已卸载
                        || TextUtils.equals(bean.getStatus(), "5")) {//下载失败
                    result = 2;
                    return result;
                }
            }
        }


        return result;
    }
}
