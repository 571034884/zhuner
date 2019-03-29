package com.aibabel.scenic.bean;

import com.aibabel.baselibrary.http.BaseBean;

public class CollectBean extends BaseBean {


    /**
     * data : {"status":true,"msg":"再次加入"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private boolean status;

        private String message;

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
