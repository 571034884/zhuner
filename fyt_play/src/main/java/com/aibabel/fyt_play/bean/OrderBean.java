package com.aibabel.fyt_play.bean;

import com.aibabel.baselibrary.http.BaseBean;

import java.util.List;

/**
 * 作者：wuqinghua_fyt on 2019/1/11 14:30
 * 功能：
 * 版本：1.0
 */
public class OrderBean extends BaseBean {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * order_id : 250111009275
         * lease_id : 14122004882901
         * order_pay_id :
         * order_status : 1
         * sn : 0
         * sku_id : 101003
         * price : 1
         * pay_type : 2
         * use_time : 1547395200
         * create_time : 1547184499
         * user_name : ghgggg
         * user_mail :
         * user_wx : aaaa
         * use_voucher :
         * use_code :
         * remark :
         * sku_name : 瑗跨摐鍗�1
         * des : 48灏忔椂鍎跨鍒�
         * img : http://destination.cdn.aibabel.com/play/image/kardsuica.png
         */

        private String order_id;
        private String lease_id;
        private String order_pay_id;
        private int order_status;
        private int sn;
        private int sku_id;
        private int price;
        private int pay_type;
        private String use_time;
        private int create_time;
        private String user_name;
        private String user_mail;
        private String user_wx;
        private String use_voucher;
        private String use_code;
        private String remark;
        private String sku_name;
        private String des;
        private String img;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getLease_id() {
            return lease_id;
        }

        public void setLease_id(String lease_id) {
            this.lease_id = lease_id;
        }

        public String getOrder_pay_id() {
            return order_pay_id;
        }

        public void setOrder_pay_id(String order_pay_id) {
            this.order_pay_id = order_pay_id;
        }

        public int getOrder_status() {
            return order_status;
        }

        public void setOrder_status(int order_status) {
            this.order_status = order_status;
        }

        public int getSn() {
            return sn;
        }

        public void setSn(int sn) {
            this.sn = sn;
        }

        public int getSku_id() {
            return sku_id;
        }

        public void setSku_id(int sku_id) {
            this.sku_id = sku_id;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getPay_type() {
            return pay_type;
        }

        public void setPay_type(int pay_type) {
            this.pay_type = pay_type;
        }

        public String getUse_time() {
            return use_time;
        }

        public void setUse_time(String use_time) {
            this.use_time = use_time;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_mail() {
            return user_mail;
        }

        public void setUser_mail(String user_mail) {
            this.user_mail = user_mail;
        }

        public String getUser_wx() {
            return user_wx;
        }

        public void setUser_wx(String user_wx) {
            this.user_wx = user_wx;
        }

        public String getUse_voucher() {
            return use_voucher;
        }

        public void setUse_voucher(String use_voucher) {
            this.use_voucher = use_voucher;
        }

        public String getUse_code() {
            return use_code;
        }

        public void setUse_code(String use_code) {
            this.use_code = use_code;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getSku_name() {
            return sku_name;
        }

        public void setSku_name(String sku_name) {
            this.sku_name = sku_name;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
