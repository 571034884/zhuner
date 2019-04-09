package com.aibabel.surfinternet.bean;


import com.aibabel.baselibrary.http.BaseBean;

import java.util.List;

/**
 * Created by Wuqinghua on 2018/6/18 0018.
 */
public class PriceBeans extends BaseBean {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * price : [{"retailPrice":"43.0000","copies":"1","settlementPrice":"48.0000"},{"retailPrice":"54.0000","copies":"2","settlementPrice":"48.0000"},{"retailPrice":"65.0000","copies":"3","settlementPrice":"48.0000"},{"retailPrice":"75.0000","copies":"4","settlementPrice":"48.0000"},{"retailPrice":"85.0000","copies":"5","settlementPrice":"55.0000"},{"retailPrice":"99.0000","copies":"6","settlementPrice":"64.0000"},{"retailPrice":"112.0000","copies":"7","settlementPrice":"73.0000"},{"retailPrice":"126.0000","copies":"8","settlementPrice":"81.0000"},{"retailPrice":"139.0000","copies":"9","settlementPrice":"90.0000"},{"retailPrice":"153.0000","copies":"10","settlementPrice":"99.0000"},{"retailPrice":"167.0000","copies":"11","settlementPrice":"108.0000"},{"retailPrice":"180.0000","copies":"12","settlementPrice":"117.0000"},{"retailPrice":"192.0000","copies":"13","settlementPrice":"124.0000"},{"retailPrice":"204.0000","copies":"14","settlementPrice":"132.0000"},{"retailPrice":"216.0000","copies":"15","settlementPrice":"140.0000"},{"retailPrice":"228.0000","copies":"16","settlementPrice":"147.0000"},{"retailPrice":"240.0000","copies":"17","settlementPrice":"155.0000"},{"retailPrice":"252.0000","copies":"18","settlementPrice":"163.0000"},{"retailPrice":"264.0000","copies":"19","settlementPrice":"171.0000"},{"retailPrice":"275.0000","copies":"20","settlementPrice":"178.0000"},{"retailPrice":"287.0000","copies":"21","settlementPrice":"186.0000"},{"retailPrice":"299.0000","copies":"22","settlementPrice":"194.0000"},{"retailPrice":"311.0000","copies":"23","settlementPrice":"201.0000"},{"retailPrice":"323.0000","copies":"24","settlementPrice":"209.0000"},{"retailPrice":"335.0000","copies":"25","settlementPrice":"217.0000"},{"retailPrice":"347.0000","copies":"26","settlementPrice":"224.0000"},{"retailPrice":"359.0000","copies":"27","settlementPrice":"232.0000"},{"retailPrice":"371.0000","copies":"28","settlementPrice":"240.0000"},{"retailPrice":"383.0000","copies":"29","settlementPrice":"248.0000"},{"retailPrice":"394.0000","copies":"30","settlementPrice":"255.0000"},{"retailPrice":"524.0000","copies":"40","settlementPrice":"339.0000"},{"retailPrice":"646.0000","copies":"50","settlementPrice":"418.0000"},{"retailPrice":"774.0000","copies":"60","settlementPrice":"501.0000"},{"retailPrice":"901.0000","copies":"70","settlementPrice":"583.0000"},{"retailPrice":"1029.0000","copies":"80","settlementPrice":"666.0000"},{"retailPrice":"1156.0000","copies":"90","settlementPrice":"748.0000"}]
         * skuId : 1530870394923662
         */

        private String skuId;
        private List<PriceBean> price;

        public String getSkuId() {
            return skuId;
        }

        public void setSkuId(String skuId) {
            this.skuId = skuId;
        }

        public List<PriceBean> getPrice() {
            return price;
        }

        public void setPrice(List<PriceBean> price) {
            this.price = price;
        }

        public static class PriceBean {
            /**
             * retailPrice : 43.0000
             * copies : 1
             * settlementPrice : 48.0000
             */

            private String retailPrice;
            private String copies;
            private String settlementPrice;

            public String getRetailPrice() {
                return retailPrice;
            }

            public void setRetailPrice(String retailPrice) {
                this.retailPrice = retailPrice;
            }

            public String getCopies() {
                return copies;
            }

            public void setCopies(String copies) {
                this.copies = copies;
            }

            public String getSettlementPrice() {
                return settlementPrice;
            }

            public void setSettlementPrice(String settlementPrice) {
                this.settlementPrice = settlementPrice;
            }
        }
    }
}
