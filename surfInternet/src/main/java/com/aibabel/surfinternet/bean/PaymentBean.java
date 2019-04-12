package com.aibabel.surfinternet.bean;


import com.aibabel.baselibrary.http.BaseBean;

/**
 * Created by Wuqinghua on 2018/6/19 0019.
 */
public class PaymentBean extends BaseBean {

    /**
     * url : weixin://wxpay/bizpayurl?pr=os38Ob6
     */

    private String url;
    private String subOrderNo;

    public String getSubOrderNo() {
        return subOrderNo;
    }

    public void setSubOrderNo(String subOrderNo) {
        this.subOrderNo = subOrderNo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
