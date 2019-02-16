package com.aibabel.fyt_play.bean;


import android.graphics.drawable.Drawable;

import com.aibabel.baselibrary.http.BaseBean;

import java.util.List;

/**
 * 作者：wuqinghua_fyt on 2018/12/11 16:39
 * 功能：
 * 版本：1.0
 */
public class PlayBean extends BaseBean{


    /**
     * data : {"country_id":"","city_id":"","country":"鏃ユ湰","area":"涓滀含","citybanner":"http://destination.cdn.aibabel.com/play/image/tokyobanner.png","homePageMsg":[{"icon":"http://destination.cdn.aibabel.com/play/image/zhanwei.png","type":"浼橀\u20ac変綋楠�","detial":[{"image":"http://destination.cdn.aibabel.com/play/image/kardsuica.png","pdtitle":"鑻规灉鍗�111213","qr_code":"http://destination.cdn.aibabel.com/play/image/code.png","time":"鍙畾浠婃棩锛屽彲瀹氭槑鏃�","rule":"涓嶅彲閫\u20ac","price":"120-160","pdImage":"http://destination.cdn.aibabel.com/play/image/pdImage.png","pdbanner":"http://destination.cdn.aibabel.com/play/image/pdbanner.png","title":"瑗跨摐鍗�","context":"鏂囧瓧浠嬬粛11313"}]},{"icon":"http://destination.cdn.aibabel.com/play/image/zhanwei.png","type":"鍛ㄨ竟鎺ㄨ崘","detial":[{"image":"http://destination.cdn.aibabel.com/play/image/kardsuica.png","pdtitle":"鑻规灉鍗�111213","qr_code":"http://destination.cdn.aibabel.com/play/image/code.png","time":"鍙畾浠婃棩锛屽彲瀹氭槑鏃�","rule":"涓嶅彲閫\u20ac","price":"120-160","pdImage":"http://destination.cdn.aibabel.com/play/image/pdImage.png","pdbanner":"http://destination.cdn.aibabel.com/play/image/pdbanner.png","title":"瑗跨摐鍗�","context":"鏂囧瓧浠嬬粛11313"},{"image":"http://destination.cdn.aibabel.com/play/image/kardsuica.png","pdtitle":"鑿犺悵鍗�1211","qr_code":"http://destination.cdn.aibabel.com/play/image/code.png","time":"鍙畾浠婃棩锛屽彲瀹氭槑鏃�","rule":"涓嶅彲閫\u20ac","price":"120-160","pdImage":"http://destination.cdn.aibabel.com/play/image/pdImage.png","pdbanner":"http://destination.cdn.aibabel.com/play/image/pdbanner.png","title":"鑿犺悵鍗�","context":"鏂囧瓧浠嬬粛131"}]},{"icon":"http://destination.cdn.aibabel.com/play/image/zhanwei.png","type":"蹇呯帺蹇呭","detial":[{"image":"http://destination.cdn.aibabel.com/play/image/kardsuica.png","pdtitle":"鑻规灉鍗�111213","qr_code":"http://destination.cdn.aibabel.com/play/image/code.png","time":"鍙畾浠婃棩锛屽彲瀹氭槑鏃�","rule":"涓嶅彲閫\u20ac","price":"120-160","pdImage":"http://destination.cdn.aibabel.com/play/image/pdImage.png","pdbanner":"http://destination.cdn.aibabel.com/play/image/pdbanner.png","title":"瑗跨摐鍗�","context":"鏂囧瓧浠嬬粛11313"},{"image":"http://destination.cdn.aibabel.com/play/image/kardsuica.png","pdtitle":"鑿犺悵鍗�1211","qr_code":"http://destination.cdn.aibabel.com/play/image/code.png","time":"鍙畾浠婃棩锛屽彲瀹氭槑鏃�","rule":"涓嶅彲閫\u20ac","price":"120-160","pdImage":"http://destination.cdn.aibabel.com/play/image/pdImage.png","pdbanner":"http://destination.cdn.aibabel.com/play/image/pdbanner.png","title":"鑿犺悵鍗�","context":"鏂囧瓧浠嬬粛131"},{"image":"http://destination.cdn.aibabel.com/play/image/kardsuica.png","pdtitle":"瑗跨摐鍗�1","qr_code":"http://destination.cdn.aibabel.com/play/image/code.png","time":"鍙畾浠婃棩锛屽彲瀹氭槑鏃�","rule":"涓嶅彲閫\u20ac","price":"120-160","pdImage":"http://destination.cdn.aibabel.com/play/image/pdImage.png","pdbanner":"http://destination.cdn.aibabel.com/play/image/pdbanner.png","title":"瑗跨摐鍗�","context":"鏂囧瓧浠嬬粛1"}]}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * country_id :
         * city_id :
         * country : 鏃ユ湰
         * area : 涓滀含
         * citybanner : http://destination.cdn.aibabel.com/play/image/tokyobanner.png
         * homePageMsg : [{"icon":"http://destination.cdn.aibabel.com/play/image/zhanwei.png","type":"浼橀\u20ac変綋楠�","detial":[{"image":"http://destination.cdn.aibabel.com/play/image/kardsuica.png","pdtitle":"鑻规灉鍗�111213","qr_code":"http://destination.cdn.aibabel.com/play/image/code.png","time":"鍙畾浠婃棩锛屽彲瀹氭槑鏃�","rule":"涓嶅彲閫\u20ac","price":"120-160","pdImage":"http://destination.cdn.aibabel.com/play/image/pdImage.png","pdbanner":"http://destination.cdn.aibabel.com/play/image/pdbanner.png","title":"瑗跨摐鍗�","context":"鏂囧瓧浠嬬粛11313"}]},{"icon":"http://destination.cdn.aibabel.com/play/image/zhanwei.png","type":"鍛ㄨ竟鎺ㄨ崘","detial":[{"image":"http://destination.cdn.aibabel.com/play/image/kardsuica.png","pdtitle":"鑻规灉鍗�111213","qr_code":"http://destination.cdn.aibabel.com/play/image/code.png","time":"鍙畾浠婃棩锛屽彲瀹氭槑鏃�","rule":"涓嶅彲閫\u20ac","price":"120-160","pdImage":"http://destination.cdn.aibabel.com/play/image/pdImage.png","pdbanner":"http://destination.cdn.aibabel.com/play/image/pdbanner.png","title":"瑗跨摐鍗�","context":"鏂囧瓧浠嬬粛11313"},{"image":"http://destination.cdn.aibabel.com/play/image/kardsuica.png","pdtitle":"鑿犺悵鍗�1211","qr_code":"http://destination.cdn.aibabel.com/play/image/code.png","time":"鍙畾浠婃棩锛屽彲瀹氭槑鏃�","rule":"涓嶅彲閫\u20ac","price":"120-160","pdImage":"http://destination.cdn.aibabel.com/play/image/pdImage.png","pdbanner":"http://destination.cdn.aibabel.com/play/image/pdbanner.png","title":"鑿犺悵鍗�","context":"鏂囧瓧浠嬬粛131"}]},{"icon":"http://destination.cdn.aibabel.com/play/image/zhanwei.png","type":"蹇呯帺蹇呭","detial":[{"image":"http://destination.cdn.aibabel.com/play/image/kardsuica.png","pdtitle":"鑻规灉鍗�111213","qr_code":"http://destination.cdn.aibabel.com/play/image/code.png","time":"鍙畾浠婃棩锛屽彲瀹氭槑鏃�","rule":"涓嶅彲閫\u20ac","price":"120-160","pdImage":"http://destination.cdn.aibabel.com/play/image/pdImage.png","pdbanner":"http://destination.cdn.aibabel.com/play/image/pdbanner.png","title":"瑗跨摐鍗�","context":"鏂囧瓧浠嬬粛11313"},{"image":"http://destination.cdn.aibabel.com/play/image/kardsuica.png","pdtitle":"鑿犺悵鍗�1211","qr_code":"http://destination.cdn.aibabel.com/play/image/code.png","time":"鍙畾浠婃棩锛屽彲瀹氭槑鏃�","rule":"涓嶅彲閫\u20ac","price":"120-160","pdImage":"http://destination.cdn.aibabel.com/play/image/pdImage.png","pdbanner":"http://destination.cdn.aibabel.com/play/image/pdbanner.png","title":"鑿犺悵鍗�","context":"鏂囧瓧浠嬬粛131"},{"image":"http://destination.cdn.aibabel.com/play/image/kardsuica.png","pdtitle":"瑗跨摐鍗�1","qr_code":"http://destination.cdn.aibabel.com/play/image/code.png","time":"鍙畾浠婃棩锛屽彲瀹氭槑鏃�","rule":"涓嶅彲閫\u20ac","price":"120-160","pdImage":"http://destination.cdn.aibabel.com/play/image/pdImage.png","pdbanner":"http://destination.cdn.aibabel.com/play/image/pdbanner.png","title":"瑗跨摐鍗�","context":"鏂囧瓧浠嬬粛1"}]}]
         */

        private String country_id;
        private String city_id;
        private String country;
        private String area;
        private String citybanner;
        private List<HomePageMsgBean> homePageMsg;

        public String getCountry_id() {
            return country_id;
        }

        public void setCountry_id(String country_id) {
            this.country_id = country_id;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getCitybanner() {
            return citybanner;
        }

        public void setCitybanner(String citybanner) {
            this.citybanner = citybanner;
        }

        public List<HomePageMsgBean> getHomePageMsg() {
            return homePageMsg;
        }

        public void setHomePageMsg(List<HomePageMsgBean> homePageMsg) {
            this.homePageMsg = homePageMsg;
        }

        public static class HomePageMsgBean {
            /**
             * icon : http://destination.cdn.aibabel.com/play/image/zhanwei.png
             * type : 浼橀€変綋楠�
             * detial : [{"image":"http://destination.cdn.aibabel.com/play/image/kardsuica.png","pdtitle":"鑻规灉鍗�111213","qr_code":"http://destination.cdn.aibabel.com/play/image/code.png","time":"鍙畾浠婃棩锛屽彲瀹氭槑鏃�","rule":"涓嶅彲閫\u20ac","price":"120-160","pdImage":"http://destination.cdn.aibabel.com/play/image/pdImage.png","pdbanner":"http://destination.cdn.aibabel.com/play/image/pdbanner.png","title":"瑗跨摐鍗�","context":"鏂囧瓧浠嬬粛11313"}]
             */

            private String icon;
            private String type;
            private List<DetialBean> detial;

            public HomePageMsgBean() {
                super();
            }

            public HomePageMsgBean(String icon, String type, List<DetialBean> detial) {
                this.icon = icon;
                this.type = type;
                this.detial = detial;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<DetialBean> getDetial() {
                return detial;
            }

            public void setDetial(List<DetialBean> detial) {
                this.detial = detial;
            }

            public static class DetialBean {
                /**
                 * image : http://destination.cdn.aibabel.com/play/image/kardsuica.png
                 * pdtitle : 鑻规灉鍗�111213
                 * qr_code : http://destination.cdn.aibabel.com/play/image/code.png
                 * time : 鍙畾浠婃棩锛屽彲瀹氭槑鏃�
                 * rule : 涓嶅彲閫€
                 * price : 120-160
                 * pdImage : http://destination.cdn.aibabel.com/play/image/pdImage.png
                 * pdbanner : http://destination.cdn.aibabel.com/play/image/pdbanner.png
                 * title : 瑗跨摐鍗�
                 * context : 鏂囧瓧浠嬬粛11313
                 */

                private String image;
                private String pdtitle;
                private String qr_code;
                private String time;
                private String rule;
                private String price;
                private String pdImage;
                private String pdbanner;
                private String title;
                private String context;

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getPdtitle() {
                    return pdtitle;
                }

                public void setPdtitle(String pdtitle) {
                    this.pdtitle = pdtitle;
                }

                public String getQr_code() {
                    return qr_code;
                }

                public void setQr_code(String qr_code) {
                    this.qr_code = qr_code;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }

                public String getRule() {
                    return rule;
                }

                public void setRule(String rule) {
                    this.rule = rule;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getPdImage() {
                    return pdImage;
                }

                public void setPdImage(String pdImage) {
                    this.pdImage = pdImage;
                }

                public String getPdbanner() {
                    return pdbanner;
                }

                public void setPdbanner(String pdbanner) {
                    this.pdbanner = pdbanner;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getContext() {
                    return context;
                }

                public void setContext(String context) {
                    this.context = context;
                }
            }
        }
    }
}
