package com.aibabel.launcher.bean;

import com.aibabel.baselibrary.http.BaseBean;

public class SyncOrder extends BaseBean {
    Body body;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;
    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }


    public class Body {
        public String oid;
        public String uid;
        public String channelName;
        public String uname;
        public String sn;
        public String f;
        public String t;
        public String d;
        public int isLock;
        public int at;
        public int isZhuner;//1是zhuner ,0是非准儿


        public String getOid() {
            return oid;
        }

        public void setOid(String oid) {
            this.oid = oid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getChannelName() {
            return channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getF() {
            return f;
        }

        public void setF(String f) {
            this.f = f;
        }

        public String getT() {
            return t;
        }

        public void setT(String t) {
            this.t = t;
        }

        public String getD() {
            return d;
        }

        public void setD(String d) {
            this.d = d;
        }

        public int getIsLock() {
            return isLock;
        }

        public void setIsLock(int isLock) {
            this.isLock = isLock;
        }

        public int getAt() {
            return at;
        }

        public void setAt(int at) {
            this.at = at;
        }

        public int getIsZhuner() {
            return isZhuner;
        }

        public void setIsZhuner(int isZhuner) {
            this.isZhuner = isZhuner;
        }

    }


//    public static void main(String args[]){
//        String res  =  "{\"code\":1,\"msg\":\"\",\"body\":{\"oid\":\"123\",\"uid\":\"345\",\"channelName\":\"aaa\",\"uname\":\"\",\"sn\":\"\",\"f\":\"\",\"t\":\"\",\"d\":\"\",\"isLock\":1,\"at\":24}";
//
//        String jsonStr = "{\"name\":\"ZhangSan\",\"age\":22,\"sex\":\"male\",\"isGraduate\":\"true\",\"isBad\":false}";
//        System.out.println("json string: " + jsonStr);
//
////        Student stu = JSON.parseObject(jsonStr, Student.class);
////        System.out.println("student toString: " + stu.name);
//
////        String stuStr = JSON.toJSONString(stu);
////        System.out.println("JSON 串: " + stuStr);
//
//        SyncOrder synorder = JSON.parseObject(res,SyncOrder.class);
//        System.out.print(synorder.toString());
//
//    }

//    public static void main(String args[]){
//        String reposStr = "{\"code\":1,\"msg\":\"\",\"body\":{\"oid\":\"123\",\"uid\":\"345\",\"channelName\":\"去哪玩\",\"uname\":\"\",\"sn\":\"\",\"f\":\"20190222000000\",\"t\":\"20190224161000\",\"d\":\"\",\"isLock\":0,\"at\":24,\"isZhuner\":0}}";
//        String reposStr2 = "{\n" +
//                "      \"code\": 1,\n" +
//                "      \"statu22s\": \"ok\",\n" +
//                "      \"msg\": \"国外，无订单\",\n" +
//                "      \"body\": {\n" +
//                "        \"isLock\": 1,\n" +
//                "        \"oid\": \"\",\n" +
//                "        \"uid\": \"\",\n" +
//                "        \"channelName\": \"\",\n" +
//                "        \"uname\": \"\",\n" +
//                "        \"sn\": \"\",\n" +
//                "        \"f\": \"\",\n" +
//                "        \"t\": \"\",\n" +
//                "        \"d\": \"\",\n" +
//                "        \"at\": 24,\n" +
//                "        \"isZhuner\": 0\n" +
//                "      }\n" +
//                "    }";
//        //System.out.println(reposStr);
//        //System.out.println(reposStr2);
//
//        SyncOrder synorder = JSON.parseObject(reposStr2,SyncOrder.class);
//        System.out.println(synorder.getBody().getAt());
//        System.out.println(synorder.getBody().getIsLock());
//        SyncOrder fst = FastJsonUtil.changeJsonToBean(reposStr2,SyncOrder.class);
//        System.out.println(fst.getBody().getAt());
//        System.out.println(fst.getBody().getIsLock());
//
//    }


}
