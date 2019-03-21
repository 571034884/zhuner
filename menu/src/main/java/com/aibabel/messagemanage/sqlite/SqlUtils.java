package com.aibabel.messagemanage.sqlite;

import android.content.ContentValues;

import com.aibabel.menu.bean.PushMessageBean;

import org.litepal.LitePal;

import java.util.List;


public class SqlUtils {

    //插入数据
    public static boolean insertData(String from, String to, String from_code, String to_code, String asr, String mt, String eng, String tts, long time) {
        PushMessageBean recordBean = new PushMessageBean();
        return recordBean.save();
    }

    //插入数据
    public static boolean insertData(PushMessageBean recordBean) {
        return recordBean.save();
    }


    //删除指定Id 的数据
    public static int deleteById(int id) {
        return LitePal.deleteAll(PushMessageBean.class, "id = ?", String.valueOf(id));
    }

    //删除当前时间的数据
    public static int deleteByTime(long time) {
        return LitePal.deleteAll(PushMessageBean.class, "time = ?", String.valueOf(time));
    }

//    //删除当前时间的数据
//    public static int update_bean(long id) {
//        ContentValues values = new ContentValues();
//        values.put("badge", false);
//        return LitePal.update(PushMessageBean.class,values,id);
//    }

    /**
     * 删除多条数据
     *
     * @param list 多条 数据 的 ID / 时间 的 集合
     * @param type 0：ID  1：时间
     */
    public static int deleteMore(List<Integer> list, int type) {
        int result = 0;
        if (null != list && list.size() > 0) {
            return result;
        }
        for (Integer integer : list) {
            switch (type) {
                case 0:
                    result = deleteById(integer);
                    break;
                case 1:
                    result = deleteByTime(integer);
                    break;
            }
        }
        return result;

    }

    // 删除所有的数据，
    public static void deleteDataAll() {
        try {
            LitePal.deleteAll(PushMessageBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //遍历查询所有数据，保存到List里面
    public static List<PushMessageBean> retrieve(int page, int pagesize) {
        return LitePal.order("time desc").limit(pagesize).offset(pagesize * (page - 1)).find(PushMessageBean.class);
    }

    public static List<PushMessageBean> queryMethed() {
        try {
            List<PushMessageBean> allSongs = LitePal.findAll(PushMessageBean.class);
            return allSongs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

}
