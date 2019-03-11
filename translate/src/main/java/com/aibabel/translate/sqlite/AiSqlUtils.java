package com.aibabel.translate.sqlite;

import com.aibabel.translate.bean.MessageBean;
import com.aibabel.translate.bean.RecordBean;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * 作者：wuqinghua_fyt on 2018/10/16 9:15
 * 功能：
 * 版本：1.0
 */
public class AiSqlUtils {

    //插入数据
    public static boolean insertData(MessageBean bean) {

        return bean.save();
    }



    //删除指定Id 的数据
    public static int deleteById(int id) {
        return DataSupport.deleteAll(MessageBean.class, "id = ?", String.valueOf(id));
    }

    //删除当前时间的数据
    public static int deleteByTime(long time) {
        return DataSupport.deleteAll(MessageBean.class, "time = ?", String.valueOf(time));
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
        DataSupport.deleteAll(MessageBean.class);
    }

    //遍历查询所有数据，保存到List里面
    public static List<MessageBean> retrieve(int page, int pagesize) {

        return DataSupport.order("time asc").limit(pagesize).offset(pagesize * (page - 1)).find(MessageBean.class);

    }
}
