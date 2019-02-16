package com.aibabel.travel.bean;

import java.util.List;

public class CountryData extends BaseModel{



    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {


        private int count;
        private String next;
        private String previous;
        private List<ResultsBean> results;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }

        public String getPrevious() {
            return previous;
        }

        public void setPrevious(String previous) {
            this.previous = previous;
        }

        public List<ResultsBean> getResults() {
            return results;
        }

        public void setResults(List<ResultsBean> results) {
            this.results = results;
        }

        public static class ResultsBean {
            /**
             * id : 5
             * name : 法国
             * cover : https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fupload%2Fe773e4769fa3998e6e33dbe8705427bb.jpg
             * latitude : 48.848088
             * longitude : 2.328697
             * subCount : 11
             * state : 欧洲
             * hotCountry : 1
             */

            private int id;
            private String name;
            private String cover;
            private int cover_path;
            private double latitude;
            private double longitude;
            private int subCount;
            private String state;
            private String hotCountry;
            private String offline;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }
            public int getCover_path() {
                return cover_path;
            }

            public void setCover_path(int cover_path) {
                this.cover_path = cover_path;
            }
            public double getLatitude() {
                return latitude;
            }

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }

            public double getLongitude() {
                return longitude;
            }

            public void setLongitude(double longitude) {
                this.longitude = longitude;
            }

            public int getSubCount() {
                return subCount;
            }

            public void setSubCount(int subCount) {
                this.subCount = subCount;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getHotCountry() {
                return hotCountry;
            }

            public void setHotCountry(String hotCountry) {
                this.hotCountry = hotCountry;
            }

            public String getOffline() {
                return offline;
            }

            public void setOffline(String offline) {
                this.offline = offline;
            }
        }
    }
}
