package com.aibabel.message.hx.bean;

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
         * nickname:1231
         * head_img:1231
         * is_im:0  ，0表示不是IM用户，1表示是IM用户
         */

        private String user_id;
        private String pwd;
        private String nickname;
        private String head_img;
        private int is_im;


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

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public int getIs_im() {
            return is_im;
        }

        public void setIs_im(int is_im) {
            this.is_im = is_im;
        }
    }
}
