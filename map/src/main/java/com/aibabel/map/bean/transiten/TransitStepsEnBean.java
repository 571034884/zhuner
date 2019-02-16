package com.aibabel.map.bean.transiten;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fytworks on 2018/12/1.
 */

public class TransitStepsEnBean implements Serializable{
    private List<TransitSchemesEnBean> schemes;

    public List<TransitSchemesEnBean> getSchemes() {
        return schemes;
    }

    public void setSchemes(List<TransitSchemesEnBean> schemes) {
        this.schemes = schemes;
    }
}
