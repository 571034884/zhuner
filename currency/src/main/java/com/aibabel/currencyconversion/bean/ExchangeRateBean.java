package com.aibabel.currencyconversion.bean;

import java.util.List;

/**
 * 作者：SunSH on 2018/5/17 21:05
 * 功能：税率JsonBean
 * 版本：1.0
 */
public class ExchangeRateBean {
    /**
     * from : JPY
     * to : [{"symbol":"USD","mid":0.009078271759642457},{"symbol":"CNY","mid":0.05799199999996971}]
     * ts : 1526563049 时间戳
     */

    private String from;
    private String ts;
    private List<ToBean> to;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public List<ToBean> getTo() {
        return to;
    }

    public void setTo(List<ToBean> to) {
        this.to = to;
    }

    public static class ToBean {
        /**
         * symbol : USD
         * mid : 0.009078271759642457
         */

        private String symbol;
        private double mid;

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public double getMid() {
            return mid;
        }

        public void setMid(double mid) {
            this.mid = mid;
        }
    }
}
