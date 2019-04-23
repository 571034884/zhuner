package com.aibabel.message.hx.bean;

public class CustomMessage {


    /**
     * ext : {"nickname":"123","head_img":"hea","at":"z223"}
     */

    private ExtBean ext;

    public ExtBean getExt() {
        return ext;
    }

    public void setExt(ExtBean ext) {
        this.ext = ext;
    }

    public static class ExtBean {
        /**
         * nickname : 123
         * head_img : hea
         * at : z223
         */

        private String nickname;
        private String head_img;
        private String at;

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

        public String getAt() {
            return at;
        }

        public void setAt(String at) {
            this.at = at;
        }
    }
}
