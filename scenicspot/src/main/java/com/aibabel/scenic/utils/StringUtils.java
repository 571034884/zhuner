package com.aibabel.scenic.utils;

import android.os.Environment;
import android.text.TextUtils;

import com.aibabel.scenic.base.ScenicBaseApplication;
import com.aibabel.scenic.bean.HistoryBean;
import com.aibabel.scenic.bean.MusicBean;
import com.aibabel.scenic.bean.SpotsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * ==========================================================================================
 *
 * @Author： 张文颖
 * @Date：2019/3/26
 * @Desc：String工具类
 * @==========================================================================================
 */

public class StringUtils {


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
     * SpotsBean 转 MusicBean
     *
     * @param bean
     * @return
     */
    public static List<MusicBean> convertList(SpotsBean bean) {
        List<MusicBean> list = new ArrayList<>();
        if (null == bean) {
            return list;
        }
        SpotsBean.DataBean.PoiMsgBean poiMsgBean = bean.getData().getPoiMsg();
        List<SpotsBean.DataBean.SubpoiMsgBean> subpoiMsgBeanList = bean.getData().getSubpoiMsg();
        if (null == poiMsgBean) {
            return list;
        }
        MusicBean music = new MusicBean(poiMsgBean.getAudiosUrl(), poiMsgBean.getCover(), poiMsgBean.getName());
        list.add(music);

        if (null != subpoiMsgBeanList && subpoiMsgBeanList.size() > 0) {
            for (SpotsBean.DataBean.SubpoiMsgBean subpoiMsgBean : subpoiMsgBeanList) {
                MusicBean musicBean = new MusicBean(subpoiMsgBean.getAudiosUrl(), subpoiMsgBean.getCover(), subpoiMsgBean.getName());
                list.add(musicBean);
            }

        }

        return list;
    }


    /**
     * SpotsBean 转 MusicBean
     *
     * @param data
     * @return
     */
    public static List<MusicBean> convertList(List<SpotsBean.DataBean.SubpoiMsgBean> data) {
        List<MusicBean> list = new ArrayList<>();

        for (SpotsBean.DataBean.SubpoiMsgBean subpoiMsgBean : data) {
            MusicBean musicBean = new MusicBean(subpoiMsgBean.getAudiosUrl(), subpoiMsgBean.getCover(), subpoiMsgBean.getName());
            list.add(musicBean);
        }


        return list;
    }

    /**
     * 历史风俗bean 转 MusicBean
     *
     * @param data
     * @return
     */
    public static List<MusicBean> convertMusicList(List<HistoryBean> data) {
        List<MusicBean> list = new ArrayList<>();
        if (null != data && data.size() > 0) {
            for (HistoryBean tourguideBean : data) {
                MusicBean musicBean = new MusicBean(tourguideBean.getAudiosUrl(), tourguideBean.getCover(), tourguideBean.getName());
                list.add(musicBean);
            }
        }

        return list;
    }

    /**
     * 拼接url
     * @param
     * @return
     */
    public static String getCountryUrl(String url, String countryName, String countryId) {
        StringBuffer stringBuffer = new StringBuffer(url);
        stringBuffer.append("?sn=" + CommonUtils.getSN())
                .append("&sv=" + CommonUtils.getDeviceInfo())
                .append("&countryId=" + countryId)
                .append("&countryName=" + countryName);
        Logs.e(stringBuffer.toString());
        return stringBuffer.toString();
    }


}
