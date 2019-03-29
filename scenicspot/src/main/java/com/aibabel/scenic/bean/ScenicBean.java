package com.aibabel.scenic.bean;

import com.aibabel.baselibrary.http.BaseBean;

import java.util.List;

/**
 * Created by fytworks on 2019/3/22.
 */

public class ScenicBean extends BaseBean{
    public Datas data;

    public class Datas{

        public String searchWords;

        public List<PoiDetailsBean> poiTop;

        public List<PoiDetailsBean> poiMsgHot;

        public List<PoiDetailsBean> poiMsgNearby;

        public List<PoiDetailsBean> poiMsgMy;

        public List<PoiDetailsBean> countryHistoryCustom;

        public List<PoiDetailsBean> peripheryCity;

        public String menuCountryId;

        public String historyPage;

        public String imageCountry;
    }
}
