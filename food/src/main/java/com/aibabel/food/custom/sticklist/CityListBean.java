package com.aibabel.food.custom.sticklist;

import com.aibabel.baselibrary.http.BaseBean;

import java.util.List;

/**
 * 作者：SunSH on 2018/7/26 16:50
 * 功能：
 * 版本：1.0
 */
public class CityListBean extends BaseBean {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * name_cn : 东京
         * city_id : 0d94706df92c482e967aa22e5853d4b7
         * groupBy : D
         */

        private String name_cn;
        private String city_id;
        private String groupBy;

        public String getName_cn() {
            return name_cn;
        }

        public void setName_cn(String name_cn) {
            this.name_cn = name_cn;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getGroupBy() {
            return groupBy;
        }

        public void setGroupBy(String groupBy) {
            this.groupBy = groupBy;
        }
    }
}
