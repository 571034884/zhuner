package com.aibabel.translate.sqlite;

import com.aibabel.translate.bean.RecordBean;


import org.litepal.LitePal;

import java.util.List;

/**
 * 作者：wuqinghua_fyt on 2018/10/16 9:15
 * 功能：
 * 版本：1.0
 */
public class SqlUtils {

    //插入数据
    public static boolean insertData(String from, String to, String from_code, String to_code, String asr, String mt, String eng, String tts, long time) {
        RecordBean recordBean = new RecordBean();
        recordBean.setFrom(from);
        recordBean.setTo(to);
        recordBean.setFrom_code(from_code);
        recordBean.setTo_code(to_code);
        recordBean.setAsr(asr);
        recordBean.setMt(mt);
        recordBean.setEng(eng);
        recordBean.setFile(tts);
        recordBean.setTime(time);
        return recordBean.save();
    }

    //插入数据
    public static boolean insertData(RecordBean recordBean) {
        return recordBean.save();
    }


    //删除指定Id 的数据
    public static int deleteById(int id) {
        return LitePal.deleteAll(RecordBean.class, "id = ?", String.valueOf(id));
    }

    //删除当前时间的数据
    public static int deleteByTime(long time) {
        return LitePal.deleteAll(RecordBean.class, "time = ?", String.valueOf(time));
    }

    /**
     * 删除多条数据
     *
     * @param list 多条 数据 的 ID / 时间 的 集合
     * @param type 0：ID  1：时间
     */
    public static int deleteMore(List<Integer> list, int type) {
        int result=0;
        if(null!=list&&list.size()>0){
            return result;
        }
        for (Integer integer : list) {
            switch (type) {
                case 0:
                    result = deleteById(integer);
                    break;
                case 1:
                    result =deleteByTime(integer);
                    break;
            }
        }
        return result;

    }

    // 删除所有的数据，
    public static void deleteDataAll() {
        LitePal.deleteAll(RecordBean.class);
    }

    //遍历查询所有数据，保存到List里面
    public static List<RecordBean> retrieve(int page, int pagesize) {

        return LitePal.order("time desc").limit(pagesize).offset(pagesize * (page - 1)).find(RecordBean.class);

    }
}
