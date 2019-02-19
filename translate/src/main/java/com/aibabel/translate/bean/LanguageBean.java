package com.aibabel.translate.bean;

import java.util.List;

/**
 * Created by Wuqinghua on 2018/3/29 0029.
 */

public class LanguageBean {


    /**
     * id : 0
     * name : Chinese
     * name_local : 汉语
     * lang_code : ch_ch
     * alert : 请说标准普通话。
     * sound : 1
     * child : [{"var_id":0,"var":"Chinese(China)","var_code":"ch_ch","var_local":"中国大陆","offline":"离线","sound":1,"offline_code":"ch_ch","alert":"请说标准普通话。"},{"var_id":1,"var":"Chinese(Taiwan,China)","var_code":"ch_tw","var_local":"中国台湾","offline":"离线","sound":1,"offline_code":"ch_tw","alert":"請說標準國語。"}]
     * offline : 离线
     * offline_code : ch_ch
     */

    private int id;
    private String name;
    private String name_local;
    private String lang_code;
    private String alert;
    private int sound;
    private String offline;
    private String offline_code;
    private List<ChildBean> child;

    public boolean getIsOffline() {
        return isOffline;
    }

    public void setIsOffline(boolean offline) {
        isOffline = offline;
    }

    private boolean isOffline;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_local() {
        return name_local;
    }

    public void setName_local(String name_local) {
        this.name_local = name_local;
    }

    public String getLang_code() {
        return lang_code;
    }

    public void setLang_code(String lang_code) {
        this.lang_code = lang_code;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public int getSound() {
        return sound;
    }

    public void setSound(int sound) {
        this.sound = sound;
    }

    public String getOffline() {
        return offline;
    }

    public void setOffline(String offline) {
        this.offline = offline;
    }

    public String getOffline_code() {
        return offline_code;
    }

    public void setOffline_code(String offline_code) {
        this.offline_code = offline_code;
    }

    public List<ChildBean> getChild() {
        return child;
    }

    public void setChild(List<ChildBean> child) {
        this.child = child;
    }

    public static class ChildBean {
        /**
         * var_id : 0
         * var : Chinese(China)
         * var_code : ch_ch
         * var_local : 中国大陆
         * offline : 离线
         * sound : 1
         * offline_code : ch_ch
         * alert : 请说标准普通话。
         */

        private int var_id;
        private String var;
        private String var_code;
        private String var_local;
        private String offline;
        private int sound;
        private String offline_code;
        private String alert;

        public int getVar_id() {
            return var_id;
        }

        public void setVar_id(int var_id) {
            this.var_id = var_id;
        }

        public String getVar() {
            return var;
        }

        public void setVar(String var) {
            this.var = var;
        }

        public String getVar_code() {
            return var_code;
        }

        public void setVar_code(String var_code) {
            this.var_code = var_code;
        }

        public String getVar_local() {
            return var_local;
        }

        public void setVar_local(String var_local) {
            this.var_local = var_local;
        }

        public String getOffline() {
            return offline;
        }

        public void setOffline(String offline) {
            this.offline = offline;
        }

        public int getSound() {
            return sound;
        }

        public void setSound(int sound) {
            this.sound = sound;
        }

        public String getOffline_code() {
            return offline_code;
        }

        public void setOffline_code(String offline_code) {
            this.offline_code = offline_code;
        }

        public String getAlert() {
            return alert;
        }

        public void setAlert(String alert) {
            this.alert = alert;
        }
    }
}
