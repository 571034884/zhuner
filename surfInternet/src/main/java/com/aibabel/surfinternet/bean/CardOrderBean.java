package com.aibabel.surfinternet.bean;

import com.aibabel.surfinternet.okgo.BaseBean;

import java.util.List;

/**
 * Created by Wuqinghua on 2018/6/18 0018.
 */
public class CardOrderBean extends BaseBean {

    /**
     * tradeCode : 1000
     * tradeMsg : 成功
     * tradeData : [{"orderId":"2528719566466104","channelOrderId":"","subOrderList":[{"subOrderId":"1528719566496106","channelSubOrderId":"10000000001","skuId":null,"copies":null,"planStatus":null,"planStartTime":null,"planEndTime":null,"totalDays":null,"remainingDays":null}]},{"orderId":"2528719820256126","channelOrderId":"","subOrderList":[{"subOrderId":"1528719820285128","channelSubOrderId":"100000001","skuId":null,"copies":null,"planStatus":null,"planStartTime":null,"planEndTime":null,"totalDays":null,"remainingDays":null}]},{"orderId":"2528719839479135","channelOrderId":"","subOrderList":[{"subOrderId":"1528719839507137","channelSubOrderId":"100000002","skuId":null,"copies":null,"planStatus":null,"planStartTime":null,"planEndTime":null,"totalDays":null,"remainingDays":null}]},{"orderId":"2528713834998911","channelOrderId":"","subOrderList":[{"subOrderId":"1528713835038913","channelSubOrderId":"10000000001","skuId":null,"copies":null,"planStatus":null,"planStartTime":null,"planEndTime":null,"totalDays":null,"remainingDays":null}]},{"orderId":"2528718833052949","channelOrderId":"","subOrderList":[{"subOrderId":"1528718833081951","channelSubOrderId":"10000000001","skuId":null,"copies":null,"planStatus":null,"planStartTime":null,"planEndTime":null,"totalDays":null,"remainingDays":null}]},{"orderId":"2528719640824113","channelOrderId":"","subOrderList":[{"subOrderId":"1528719640853115","channelSubOrderId":"10000000001","skuId":null,"copies":null,"planStatus":null,"planStartTime":null,"planEndTime":null,"totalDays":null,"remainingDays":null}]},{"orderId":"2528855470341455","channelOrderId":"","subOrderList":[{"subOrderId":"1528855470371457","channelSubOrderId":"100000004","skuId":null,"copies":null,"planStatus":null,"planStartTime":null,"planEndTime":null,"totalDays":null,"remainingDays":null}]},{"orderId":"2528719284399980","channelOrderId":"","subOrderList":[{"subOrderId":"1528719284428982","channelSubOrderId":"10000000001","skuId":null,"copies":null,"planStatus":null,"planStartTime":null,"planEndTime":null,"totalDays":null,"remainingDays":null}]},{"orderId":"2508572480879821","channelOrderId":"","subOrderList":[{"subOrderId":"1508572480876820","channelSubOrderId":"150857246556472_1","skuId":null,"copies":null,"planStatus":null,"planStartTime":null,"planEndTime":null,"totalDays":null,"remainingDays":null}]},{"orderId":"2528719172724971","channelOrderId":"","subOrderList":[{"subOrderId":"1528719172755973","channelSubOrderId":"10000000001","skuId":null,"copies":null,"planStatus":null,"planStartTime":null,"planEndTime":null,"totalDays":null,"remainingDays":null}]},{"orderId":"2528855781926466","channelOrderId":"","subOrderList":[{"subOrderId":"1528855781954468","channelSubOrderId":"100000004","skuId":null,"copies":null,"planStatus":null,"planStartTime":null,"planEndTime":null,"totalDays":null,"remainingDays":null}]},{"orderId":"2528719337815989","channelOrderId":"","subOrderList":[{"subOrderId":"1528719337845991","channelSubOrderId":"10000000001","skuId":null,"copies":null,"planStatus":null,"planStartTime":null,"planEndTime":null,"totalDays":null,"remainingDays":null}]},{"orderId":"3528093768128492","channelOrderId":"","subOrderList":[{"subOrderId":"1528093768125491","channelSubOrderId":"","skuId":null,"copies":null,"planStatus":null,"planStartTime":null,"planEndTime":null,"totalDays":null,"remainingDays":null}]},{"orderId":"2528719019695958","channelOrderId":"","subOrderList":[{"subOrderId":"1528719019724960","channelSubOrderId":"10000000001","skuId":null,"copies":null,"planStatus":null,"planStartTime":null,"planEndTime":null,"totalDays":null,"remainingDays":null}]},{"orderId":"2528707437248492","channelOrderId":"","subOrderList":[{"subOrderId":"1528707437253494","channelSubOrderId":"10000000001","skuId":null,"copies":null,"planStatus":null,"planStartTime":null,"planEndTime":null,"totalDays":null,"remainingDays":null}]},{"orderId":"2528719969135144","channelOrderId":"","subOrderList":[{"subOrderId":"1528719969164146","channelSubOrderId":"100000003","skuId":null,"copies":null,"planStatus":null,"planStartTime":null,"planEndTime":null,"totalDays":null,"remainingDays":null}]}]
     */

    private String tradeCode;
    private String tradeMsg;
    private List<TradeDataBean> tradeData;

    public String getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
    }

    public String getTradeMsg() {
        return tradeMsg;
    }

    public void setTradeMsg(String tradeMsg) {
        this.tradeMsg = tradeMsg;
    }

    public List<TradeDataBean> getTradeData() {
        return tradeData;
    }

    public void setTradeData(List<TradeDataBean> tradeData) {
        this.tradeData = tradeData;
    }

    public static class TradeDataBean {
        /**
         * orderId : 2528719566466104
         * channelOrderId :
         * subOrderList : [{"subOrderId":"1528719566496106","channelSubOrderId":"10000000001","skuId":null,"copies":null,"planStatus":null,"planStartTime":null,"planEndTime":null,"totalDays":null,"remainingDays":null}]
         */

        private String orderId;
        private String channelOrderId;
        private List<SubOrderListBean> subOrderList;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getChannelOrderId() {
            return channelOrderId;
        }

        public void setChannelOrderId(String channelOrderId) {
            this.channelOrderId = channelOrderId;
        }

        public List<SubOrderListBean> getSubOrderList() {
            return subOrderList;
        }

        public void setSubOrderList(List<SubOrderListBean> subOrderList) {
            this.subOrderList = subOrderList;
        }

        public static class SubOrderListBean {
            /**
             * subOrderId : 1528719566496106
             * channelSubOrderId : 10000000001
             * skuId : null
             * copies : null
             * planStatus : null
             * planStartTime : null
             * planEndTime : null
             * totalDays : null
             * remainingDays : null
             */

            private String subOrderId;
            private String channelSubOrderId;
            private Object skuId;
            private Object copies;
            private Object planStatus;
            private Object planStartTime;
            private Object planEndTime;
            private Object totalDays;
            private Object remainingDays;

            public String getSubOrderId() {
                return subOrderId;
            }

            public void setSubOrderId(String subOrderId) {
                this.subOrderId = subOrderId;
            }

            public String getChannelSubOrderId() {
                return channelSubOrderId;
            }

            public void setChannelSubOrderId(String channelSubOrderId) {
                this.channelSubOrderId = channelSubOrderId;
            }

            public Object getSkuId() {
                return skuId;
            }

            public void setSkuId(Object skuId) {
                this.skuId = skuId;
            }

            public Object getCopies() {
                return copies;
            }

            public void setCopies(Object copies) {
                this.copies = copies;
            }

            public Object getPlanStatus() {
                return planStatus;
            }

            public void setPlanStatus(Object planStatus) {
                this.planStatus = planStatus;
            }

            public Object getPlanStartTime() {
                return planStartTime;
            }

            public void setPlanStartTime(Object planStartTime) {
                this.planStartTime = planStartTime;
            }

            public Object getPlanEndTime() {
                return planEndTime;
            }

            public void setPlanEndTime(Object planEndTime) {
                this.planEndTime = planEndTime;
            }

            public Object getTotalDays() {
                return totalDays;
            }

            public void setTotalDays(Object totalDays) {
                this.totalDays = totalDays;
            }

            public Object getRemainingDays() {
                return remainingDays;
            }

            public void setRemainingDays(Object remainingDays) {
                this.remainingDays = remainingDays;
            }
        }
    }
}
