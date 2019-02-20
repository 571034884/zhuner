package com.aibabel.traveladvisory.bean;

import com.aibabel.traveladvisory.okgo.BaseBean;

/**
 * 作者：SunSH on 2018/8/17 11:02
 * 功能：
 * 版本：1.0
 */
public class ShouyeBean extends BaseBean {

    /**
     * data : {"name":"","type":"World","id":""}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * name :
         * type : World
         * id :
         */

        private String name;
        private String type;
        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
