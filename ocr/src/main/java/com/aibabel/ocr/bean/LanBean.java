package com.aibabel.ocr.bean;

import java.util.List;

/**
 * 作者：SunSH on 2018/5/2 10:30
 * 功能：
 * 版本：1.0
 */
public class LanBean {

    /**
     * id : 12
     * name_zh : 葡萄牙语
     * name_en : Portuguese
     * name_local : Português
     * lang_code : pt
     * alert : Μιλήστε σε τυποποιημένη γλώσσα, παρακαλώ.
     * lang_ja : ポルトガル語
     * lang_ko : 포르투갈어
     * sound : 1
     * has_var : [{"var_id":0,"var_zh":"葡萄牙","var_en":"Portugal","var_ja":"ポルトガル","var_ko":"포르투갈","var_code":"pt_PT","var_local":"Portugal","sound":1},{"var_id":1,"var_zh":"巴西","var_en":"Brazil","var_ja":"ブラジル","var_ko":"브라질","var_code":"pt_BR","var_local":"Brazil","sound":1},{"var_id":0,"var_zh":"埃及","var_en":"Egypt","var_ja":"エジプト","var_ko":"이집트","var_code":"ar_EG","var_local":"المصرية","sound":1},{"var_id":1,"var_zh":"沙特阿拉伯","var_en":"Saudi Arabia","var_ja":"サウジアラビア","var_ko":"사우디아라비아","var_code":"ar_SA","var_local":"العربية السعودية","sound":1},{"var_id":2,"var_zh":"国际","var_en":"International","var_ja":"国際","var_ko":"국제","var_code":"ar_INTL","var_local":"الدولية","sound":1}]
     */

    private int id;
    private String name;
    private String name_local;
    private String lang_code;
    private String alert;
    private int sound;
//    private String choice;
    // 选择的对勾图标
//    private Drawable Choice;
    private List<ChildBean> child;
    private int choice;

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

    public List<ChildBean> getChild() {
        return child;
    }

    public void setChild(List<ChildBean> child) {
        this.child = child;
    }

    public int getChoice() {
        return choice;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }

    public static class ChildBean {
        /**
         * var_id : 0
         * var_zh : 葡萄牙
         * var_en : Portugal
         * var_ja : ポルトガル
         * var_ko : 포르투갈
         * var_code : pt_PT
         * var_local : Portugal
         * sound : 1
         */

        private int var_id;
        private String var;
        private String var_code;
        private String var_local;
        private int sound;
        // 选择的对勾图标
        private int choice;

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

        public int getSound() {
            return sound;
        }

        public void setSound(int sound) {
            this.sound = sound;
        }

        public int getChoice() {
            return choice;
        }

        public void setChoice(int choice) {
            this.choice = choice;
        }
    }
}
