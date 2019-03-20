package com.aibabel.surfinternet.bean;

import com.aibabel.surfinternet.okgo.BaseBean;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Wuqinghua on 2018/6/18 0018.
 */
public class OrderitemBean extends BaseBean implements Serializable{


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * id : 0
         * Iccid : 89860012017310000832
         * SkuId : 1530870394941664
         * SkuName : 港澳新西兰-4G-自选-200MB
         * ChannelOrderId : 130712088866
         * ChannelSubOrderId : 13071208886601
         * YiDianOrderId :
         * YiDianSubOrderId :
         * StartTime : 2018/07/12  10:21:27
         * StopTime : 2018/07/13  10:21:27
         * Copies : 1
         * Spend : 2400
         * Describe : *本产品不限流量，超出200M/天，限速128kbps
         *当地4G网络，运营商：香港：3 HK、澳门：CTM、新西兰：Vodafone，APN设置为：emov
         *支持热点分享
         *流量有效期说明：插卡激活后24小时为一天，连续使用，直至购买天数用完为止
         *覆盖国家：香港、澳门、新西兰
         * OrderIdPay :
         * PayState : 0
         * CreatedAt : 2018/07/12  18:14:21
         * State : 8
         * days : 1
         */

        private int id;
        private String Iccid;
        private String SkuId;
        private String SkuName;
        private String ChannelOrderId;
        private String ChannelSubOrderId;
        private String YiDianOrderId;
        private String YiDianSubOrderId;
        private String StartTime;
        private String StopTime;
        private String Copies;
        private String Spend;
        private String Describe;
        private String OrderIdPay;
        private int PayState;
        private String CreatedAt;        //下单时间
        private int State;
        private String days;
        private String orderFrom;
        private String channel_name;   //渠道名称




        public String getChannel_name() {
            return channel_name;
        }

        public void setChannel_name(String channel_name) {
            this.channel_name = channel_name;
        }
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIccid() {
            return Iccid;
        }

        public void setIccid(String Iccid) {
            this.Iccid = Iccid;
        }

        public String getSkuId() {
            return SkuId;
        }

        public void setSkuId(String SkuId) {
            this.SkuId = SkuId;
        }

        public String getSkuName() {
            return SkuName;
        }

        public void setSkuName(String SkuName) {
            this.SkuName = SkuName;
        }

        public String getChannelOrderId() {
            return ChannelOrderId;
        }

        public void setChannelOrderId(String ChannelOrderId) {
            this.ChannelOrderId = ChannelOrderId;
        }

        public String getChannelSubOrderId() {
            return ChannelSubOrderId;
        }

        public void setChannelSubOrderId(String ChannelSubOrderId) {
            this.ChannelSubOrderId = ChannelSubOrderId;
        }

        public String getYiDianOrderId() {
            return YiDianOrderId;
        }

        public void setYiDianOrderId(String YiDianOrderId) {
            this.YiDianOrderId = YiDianOrderId;
        }

        public String getYiDianSubOrderId() {
            return YiDianSubOrderId;
        }

        public void setYiDianSubOrderId(String YiDianSubOrderId) {
            this.YiDianSubOrderId = YiDianSubOrderId;
        }

        public String getStartTime() {
            return StartTime;
        }

        public void setStartTime(String StartTime) {
            this.StartTime = StartTime;
        }

        public String getStopTime() {
            return StopTime;
        }

        public void setStopTime(String StopTime) {
            this.StopTime = StopTime;
        }

        public String getCopies() {
            return Copies;
        }

        public void setCopies(String Copies) {
            this.Copies = Copies;
        }

        public String getSpend() {
            return Spend;
        }

        public void setSpend(String Spend) {
            this.Spend = Spend;
        }

        public String getDescribe() {
            return Describe;
        }

        public void setDescribe(String Describe) {
            this.Describe = Describe;
        }

        public String getOrderIdPay() {
            return OrderIdPay;
        }

        public void setOrderIdPay(String OrderIdPay) {
            this.OrderIdPay = OrderIdPay;
        }

        public int getPayState() {
            return PayState;
        }

        public void setPayState(int PayState) {
            this.PayState = PayState;
        }

        public String getCreatedAt() {
            return CreatedAt;
        }

        public void setCreatedAt(String CreatedAt) {
            this.CreatedAt = CreatedAt;
        }

        public int getState() {
            return State;
        }

        public void setState(int State) {
            this.State = State;
        }

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }

        public String getOrderFrom() {
            return orderFrom;
        }

        public void setOrderFrom(String orderFrom) {
            this.orderFrom = orderFrom;
        }
    }
}
