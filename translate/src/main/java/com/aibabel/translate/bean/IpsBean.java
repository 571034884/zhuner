package com.aibabel.translate.bean;

import java.util.List;

/**
 * 作者：SunSH on 2018/7/6 17:40
 * 功能：
 * 版本：1.0
 */
public class IpsBean {
    /**
     * name : 中国
     * packagelist : [{"name":"com.aibabel.weather","server":[{"domain":"http://api.joner.aibabel.cn:7001","name":"joner","region":"ch"}]},{"name":"com.aibabel.alliedclock","server":[{"domain":"http://api.joner.aibabel.cn:7001","name":"joner","region":"ch"}]},{"name":"com.aibabel.currencyconversion","server":[{"domain":"http://api.joner.aibabel.cn:7001","name":"joner","region":"ch"}]},{"name":"com.aibabel.traveladvisory","server":[{"domain":"http://api.joner.aibabel.cn:7001","name":"joner","region":"ch"}]},{"name":"com.aibabel.surfinternet","server":[{"domain":"http://api.joner.aibabel.cn:7001","name":"joner","region":"ch"},{"domain":"http://api.web.aibabel.cn:7002","name":"pay","region":"ch"}]},{"name":"com.aibabel.dictionary","server":[{"domain":"http://api.joner.aibabel.cn:7001","name":"joner","region":"ch"}]},{"name":"com.aibabel.speech","server":[{"domain":"http://api.joner.aibabel.cn:7001","name":"joner","region":"ch"}]},{"name":"com.aibabel.translate","server":[{"domain":"http://api.joner.aibabel.cn:7001","name":"joner","region":"ch"},{"domain":"api.function.aibabel.cn:5050","name":"function","region":"ch"}]},{"name":"com.aibabel.locationservice","server":[{"domain":"http://api.joner.aibabel.cn:7001","name":"joner","region":"ch"}]},{"name":"com.aibabel.ocr","server":[{"domain":"http://api.joner.aibabel.cn:7001","name":"joner","region":"ch"}]},{"name":"com.aibabel.ocrobject","server":[{"domain":"http://api.joner.aibabel.cn:7001","name":"joner","region":"ch"}]},{"name":"com.aibabel.travel","server":[{"domain":"http://api.joner.aibabel.cn:7001","name":"joner","region":"ch"}]},{"name":"com.aibabel.chat","server":[{"domain":"http://api.joner.aibabel.cn:7001","name":"joner","region":"ch"}]}]
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
         * server : [{"domain":"http://api.joner.aibabel.cn:7001","name":"joner","region":"ch"}]
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
             * domain : http://api.joner.aibabel.cn:7001
             * name : joner
             * region : ch
             */

            private String domain;
            private String name;
            private String region;

            public String getDomain() {
                return domain;
            }

            public void setDomain(String domain) {
                this.domain = domain;
            }

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
        }
    }
}
