package com.aibabel.surfinternet.bean;


import com.aibabel.baselibrary.http.BaseBean;

import java.util.List;

/**
 * Created by Wuqinghua on 2018/6/18 0018.
 */
public class DetailsBean extends BaseBean {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * skuId : 1528279273341548
         * name : 泰国happy卡
         * type : 212
         * days :
         * capacity :
         * highFlowSize :
         * limitFlowSpeed :
         * hotspotSupport :
         * country : [{"mcc":"KR","name":"韩国"}]
         * apn :
         * operatorInfo :
         * desc : 11
         */

        private String skuId;
        private String name;
        private String type;
        private String days;
        private String capacity;
        private String highFlowSize;
        private String limitFlowSpeed;
        private String hotspotSupport;
        private String apn;
        private String operatorInfo;
        private String desc;
        private List<CountryBean> country;

        public String getSkuId() {
            return skuId;
        }

        public void setSkuId(String skuId) {
            this.skuId = skuId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }

        public String getCapacity() {
            return capacity;
        }

        public void setCapacity(String capacity) {
            this.capacity = capacity;
        }

        public String getHighFlowSize() {
            return highFlowSize;
        }

        public void setHighFlowSize(String highFlowSize) {
            this.highFlowSize = highFlowSize;
        }

        public String getLimitFlowSpeed() {
            return limitFlowSpeed;
        }

        public void setLimitFlowSpeed(String limitFlowSpeed) {
            this.limitFlowSpeed = limitFlowSpeed;
        }

        public String getHotspotSupport() {
            return hotspotSupport;
        }

        public void setHotspotSupport(String hotspotSupport) {
            this.hotspotSupport = hotspotSupport;
        }

        public String getApn() {
            return apn;
        }

        public void setApn(String apn) {
            this.apn = apn;
        }

        public String getOperatorInfo() {
            return operatorInfo;
        }

        public void setOperatorInfo(String operatorInfo) {
            this.operatorInfo = operatorInfo;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public List<CountryBean> getCountry() {
            return country;
        }

        public void setCountry(List<CountryBean> country) {
            this.country = country;
        }

        public static class CountryBean {
            /**
             * mcc : KR
             * name : 韩国
             */

            private String mcc;
            private String name;

            public String getMcc() {
                return mcc;
            }

            public void setMcc(String mcc) {
                this.mcc = mcc;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
