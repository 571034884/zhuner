package com.aibabel.traveladvisory.bean;

import com.aibabel.traveladvisory.app.Constans;
import com.aibabel.traveladvisory.okgo.BaseBean;

import java.util.List;

/**
 * 作者：SunSH on 2018/6/16 16:02
 * 功能：
 * 版本：1.0
 */
public class CountryCitysBean extends BaseBean {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 251
         * pid :
         * country_id : 148
         * country_name : 法国
         * cnName : 巴黎
         * enName : Paris
         * imageUrl :
         * travelTime : {"busy":{"month":"","desc":""},"common":{"month":"","desc":""},"slack":{"month":"","desc":""}}
         */
        private String id;
        private String pid;
        private String country_id;
        private String country_name;
        private String cnName;
        private String enName;
        private String imageUrl;
        private TravelTimeBean travelTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getCountry_id() {
            return country_id;
        }

        public void setCountry_id(String country_id) {
            this.country_id = country_id;
        }

        public String getCountry_name() {
            return country_name;
        }

        public void setCountry_name(String country_name) {
            this.country_name = country_name;
        }

        public String getCnName() {
            return cnName;
        }

        public void setCnName(String cnName) {
            this.cnName = cnName;
        }

        public String getEnName() {
            return enName;
        }

        public void setEnName(String enName) {
            this.enName = enName;
        }

        public String getImageUrl(boolean offline, int width, int height) {
            if (imageUrl==null||imageUrl.equals("")||imageUrl.contains("default.png"))
                return "";
            if (!offline) {
                int begin = imageUrl.indexOf("/", 8);
                int end = imageUrl.indexOf("@");
                begin = begin == -1 ? 0 : begin;
                end = end == -1 ? imageUrl.length() : end;
                String string = imageUrl.substring(begin, end);
                return Constans.PIC_HOST +
                        string + "?imageMogr2/thumbnail/!" + width + "x" + height + "r/gravity/Center/crop/" + width + "x" + height + "/quality/50";
            } else {
                int begin = imageUrl.lastIndexOf("/");
                if (imageUrl.contains("@")) {
                    return imageUrl.substring(begin + 1, imageUrl.indexOf("@"));
                } else {
                    return imageUrl.substring(begin + 1, imageUrl.length());
                }
            }
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public TravelTimeBean getTravelTime() {
            return travelTime;
        }

        public void setTravelTime(TravelTimeBean travelTime) {
            this.travelTime = travelTime;
        }

        public static class TravelTimeBean {
            /**
             * busy : {"month":"","desc":""}
             * common : {"month":"","desc":""}
             * slack : {"month":"","desc":""}
             */

            private BusyBean busy;
            private CommonBean common;
            private SlackBean slack;

            public BusyBean getBusy() {
                return busy;
            }

            public void setBusy(BusyBean busy) {
                this.busy = busy;
            }

            public CommonBean getCommon() {
                return common;
            }

            public void setCommon(CommonBean common) {
                this.common = common;
            }

            public SlackBean getSlack() {
                return slack;
            }

            public void setSlack(SlackBean slack) {
                this.slack = slack;
            }

            public static class BusyBean {
                /**
                 * month :
                 * desc :
                 */

                private String month;
                private String desc;

                public String getMonth() {
                    return month;
                }

                public void setMonth(String month) {
                    this.month = month;
                }

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }
            }

            public static class CommonBean {
                /**
                 * month :
                 * desc :
                 */

                private String month;
                private String desc;

                public String getMonth() {
                    return month;
                }

                public void setMonth(String month) {
                    this.month = month;
                }

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }
            }

            public static class SlackBean {
                /**
                 * month :
                 * desc :
                 */

                private String month;
                private String desc;

                public String getMonth() {
                    return month;
                }

                public void setMonth(String month) {
                    this.month = month;
                }

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }
            }
        }
    }
//    /**
//     * id : 261
//     * pid :
//     * country_id : 135
//     * country_name : 日本
//     * cnName : 东京
//     * enName : Tokyo
//     */
//
//    private String id;
//    private String pid;
//    private String country_id;
//    private String country_name;
//    private String cnName;
//    private String enName;
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getPid() {
//        return pid;
//    }
//
//    public void setPid(String pid) {
//        this.pid = pid;
//    }
//
//    public String getCountry_id() {
//        return country_id;
//    }
//
//    public void setCountry_id(String country_id) {
//        this.country_id = country_id;
//    }
//
//    public String getCountry_name() {
//        return country_name;
//    }
//
//    public void setCountry_name(String country_name) {
//        this.country_name = country_name;
//    }
//
//    public String getCnName() {
//        return cnName;
//    }
//
//    public void setCnName(String cnName) {
//        this.cnName = cnName;
//    }
//
//    public String getEnName() {
//        return enName;
//    }
//
//    public void setEnName(String enName) {
//        this.enName = enName;
//    }
}
