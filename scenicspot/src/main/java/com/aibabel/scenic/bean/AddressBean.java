package com.aibabel.scenic.bean;

import com.aibabel.baselibrary.http.BaseBean;

import java.util.List;

/**
 * Created by fytworks on 2019/3/26.
 */

public class AddressBean extends BaseBean{


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * countryName : 埃及
         * countryId : CurrentCountryId47
         * cityMsg : [{"cityName":"","cityId":"CurrentCityId347"},{"cityName":"亚历山大","cityId":"CurrentScenicId3419"},{"cityName":"卢克索","cityId":"CurrentScenicId3379"},{"cityName":"埃及简介","cityId":"CurrentScenicId3687"},{"cityName":"开罗","cityId":"CurrentScenicId1475"}]
         */

        private String countryName;
        private String countryId;
        private List<CityMsgBean> cityMsg;

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public String getCountryId() {
            return countryId;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

        public List<CityMsgBean> getCityMsg() {
            return cityMsg;
        }

        public void setCityMsg(List<CityMsgBean> cityMsg) {
            this.cityMsg = cityMsg;
        }

        public static class CityMsgBean {
            /**
             * cityName :
             * cityId : CurrentCityId347
             */

            private String cityName;
            private String cityId;

            public String getCityName() {
                return cityName;
            }

            public void setCityName(String cityName) {
                this.cityName = cityName;
            }

            public String getCityId() {
                return cityId;
            }

            public void setCityId(String cityId) {
                this.cityId = cityId;
            }
        }
    }
}
