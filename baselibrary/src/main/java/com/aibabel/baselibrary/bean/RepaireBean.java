package com.aibabel.baselibrary.bean;

import com.aibabel.baselibrary.http.BaseBean;

/**
 * 作者：SunSH on 2018/12/21 9:26
 * 功能：
 * 版本：1.0
 */
public class RepaireBean extends BaseBean {
    /**
     * data : {"isNew":true}
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
         * isNew : true
         */

        private boolean isNew;

        public boolean isIsNew() {
            return isNew;
        }

        public void setIsNew(boolean isNew) {
            this.isNew = isNew;
        }
    }
}
