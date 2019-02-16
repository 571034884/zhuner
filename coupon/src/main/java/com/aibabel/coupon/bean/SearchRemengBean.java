package com.aibabel.coupon.bean;

import com.aibabel.baselibrary.http.BaseBean;

import java.util.List;

/**
 * 作者：wuqinghua_fyt on 2019/1/9 12:26
 * 功能：
 * 版本：1.0
 */
public class SearchRemengBean extends BaseBean{

    /**
     * code : 1
     * msg : Success!
     * data : ["杩＋灏�","鐜悆褰卞煄"]
     */

    private String code;
    private String msg;
    private List<String> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
