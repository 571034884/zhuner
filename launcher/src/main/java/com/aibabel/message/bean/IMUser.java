package com.aibabel.message.bean;

import com.aibabel.baselibrary.http.BaseBean;

public class IMUser extends BaseBean{


    /**
     * status : ok
     * code : 1
     * body : {"user_id":"user1","pwd":"123"}
     */

    private String status;

    private BodyBean body;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public static class BodyBean {
        /**
         * user_id : user1
         * pwd : 123
         */

        private String user_id;
        private String pwd;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }
    }
}
