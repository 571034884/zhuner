package com.aibabel.menu.bean;

public class ReletSn {
    String sn;
    int no;
    String relet;

    public String getRelet() {
        return relet;
    }

    public void setRelet(String relet) {
        this.relet = relet;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public int getNo() {
        return no;
    }


    public void setNo(int no) {
        this.no = no;
    }

    public relet getObjrelet() {
        return objrelet;
    }

    public void setObjrelet(relet objrelet) {
        this.objrelet = objrelet;
    }

    relet objrelet;


    public class relet {
        int code;
        String msg;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
