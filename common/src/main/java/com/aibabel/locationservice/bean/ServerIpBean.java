package com.aibabel.locationservice.bean;

import java.util.List;

public class ServerIpBean {


    /**
     * code : 1
     * msg : success
     * data : [{"name":"中国","packagelist":[{"name":"com.aibabel.weather","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]},{"name":"com.aibabel.alliedclock","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]},{"name":"com.aibabel.currencyconversion","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]},{"name":"com.aibabel.traveladvisory","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]},{"name":"com.aibabel.surfinternet","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"},{"name":"pay","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]},{"name":"com.aibabel.dictionary","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]},{"name":"com.aibabel.speech","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]},{"name":"com.aibabel.translate","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]},{"name":"com.aibabel.locationservice","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]},{"name":"com.aibabel.ocr","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]},{"name":"com.aibabel.ocrobject","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]},{"name":"com.aibabel.travel","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]},{"name":"com.aibabel.chat","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]}]},{"name":"default","packagelist":[{"name":"com.aibabel.weather","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]},{"name":"com.aibabel.alliedclock","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]},{"name":"com.aibabel.currencyconversion","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]},{"name":"com.aibabel.traveladvisory","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]},{"name":"com.aibabel.surfinternet","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"},{"name":"pay","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]},{"name":"com.aibabel.dictionary","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]},{"name":"com.aibabel.speech","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]},{"name":"com.aibabel.translate","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]},{"name":"com.aibabel.locationservice","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]},{"name":"com.aibabel.ocr","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]},{"name":"com.aibabel.ocrobject","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]},{"name":"com.aibabel.travel","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]},{"name":"com.aibabel.chat","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]}]}]
     */

    private String code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * name : 中国
         * packagelist : [{"name":"com.aibabel.weather","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]},{"name":"com.aibabel.alliedclock","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]},{"name":"com.aibabel.currencyconversion","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]},{"name":"com.aibabel.traveladvisory","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]},{"name":"com.aibabel.surfinternet","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"},{"name":"pay","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]},{"name":"com.aibabel.dictionary","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]},{"name":"com.aibabel.speech","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]},{"name":"com.aibabel.translate","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]},{"name":"com.aibabel.locationservice","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]},{"name":"com.aibabel.ocr","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]},{"name":"com.aibabel.ocrobject","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]},{"name":"com.aibabel.travel","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]},{"name":"com.aibabel.chat","server":[{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]}]
         */

        private String name;
        private List<PackagelistBean> packagelist;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<PackagelistBean> getPackagelist() {
            return packagelist;
        }

        public void setPackagelist(List<PackagelistBean> packagelist) {
            this.packagelist = packagelist;
        }

        public static class PackagelistBean {
            /**
             * name : com.aibabel.weather
             * server : [{"name":"api","region":"ch","domain":"http://api.joner.aibabel.cn:7001"}]
             */

            private String name;
            private List<ServerBean> server;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<ServerBean> getServer() {
                return server;
            }

            public void setServer(List<ServerBean> server) {
                this.server = server;
            }

            public static class ServerBean {
                /**
                 * name : api
                 * region : ch
                 * domain : http://api.joner.aibabel.cn:7001
                 */

                private String name;
                private String region;
                private String domain;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getRegion() {
                    return region;
                }

                public void setRegion(String region) {
                    this.region = region;
                }

                public String getDomain() {
                    return domain;
                }

                public void setDomain(String domain) {
                    this.domain = domain;
                }
            }
        }
    }
}
