package com.aibabel.travel.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Wuqinghua on 2018/6/5 0005.
 */
public class CityBean extends BaseModel{

    /**
     * code : 0
     * data : {"count":9,"next":"","previous":"","results":[{"id":102,"name":"德国简介","cover":"https://music.gowithtommy.com/mjtt_backend_server/prod/upload/b02e162101b685b7141c1ea659f030e8.jpg","cover_path":"country_10/city_102/images/b02e162101b685b7141c1ea659f030e8.jpg","countryName":"德国","latitude":0,"longitude":0,"subCount":3},{"id":98,"name":"慕尼黑","cover":"https://music.gowithtommy.com/mjtt_backend_server/prod/data/3fd4c7e86235cafa11739a2ace1f4ba52889c7a8.jpg","cover_path":"country_10/city_98/images/3fd4c7e86235cafa11739a2ace1f4ba52889c7a8.jpg","countryName":"德国","latitude":48.129625,"longitude":11.577534,"subCount":17},{"id":100,"name":"法兰克福","cover":"https://music.gowithtommy.com/mjtt_backend_server/prod/data/707043cefbd5506c3870a806a1c1ad0fedf7335e.jpg","cover_path":"country_10/city_100/images/707043cefbd5506c3870a806a1c1ad0fedf7335e.jpg","countryName":"德国","latitude":50.11056,"longitude":8.683937,"subCount":19},{"id":95,"name":"柏林","cover":"https://music.gowithtommy.com/mjtt_backend_server/prod/data/aa88189524011ede3b96c4e6d8d31ee98530a9af.jpg","cover_path":"country_10/city_95/images/aa88189524011ede3b96c4e6d8d31ee98530a9af.jpg","countryName":"德国","latitude":52.519746,"longitude":13.401764,"subCount":19},{"id":97,"name":"科隆","cover":"https://music.gowithtommy.com/mjtt_backend_server/prod/data/10d6a1d7ec4c3cc42a9449d3161a265f664c8dc0.jpg","cover_path":"country_10/city_97/images/10d6a1d7ec4c3cc42a9449d3161a265f664c8dc0.jpg","countryName":"德国","latitude":50.935963,"longitude":6.955642,"subCount":13},{"id":96,"name":"福森","cover":"https://music.gowithtommy.com/mjtt_backend_server/prod/data/732bc5f20175cb55a87092083895f6fdbab33af5.jpg","cover_path":"country_10/city_96/images/732bc5f20175cb55a87092083895f6fdbab33af5.jpg","countryName":"德国","latitude":47.557559,"longitude":10.750423,"subCount":2},{"id":99,"name":"海德堡","cover":"https://music.gowithtommy.com/mjtt_backend_server/prod/data/c8d97f4964e3608d2b46d0a1e72d588d64bc71ab.jpg","cover_path":"country_10/city_99/images/c8d97f4964e3608d2b46d0a1e72d588d64bc71ab.jpg","countryName":"德国","latitude":49.419126,"longitude":8.670507,"subCount":5},{"id":101,"name":"汉堡","cover":"https://music.gowithtommy.com/mjtt_backend_server/prod/upload/70677cb152d7a6934b710ddb44c4a133.jpg","cover_path":"country_10/city_101/images/70677cb152d7a6934b710ddb44c4a133.jpg","countryName":"德国","latitude":53.5487,"longitude":9.997166,"subCount":15},{"id":172,"name":"杜塞尔多夫","cover":"https://music.gowithtommy.com/mjtt_backend_server/prod/upload/b97ea22d867ca7dcba7e28f8110aa425.jpg","cover_path":"country_10/city_172/images/b97ea22d867ca7dcba7e28f8110aa425.jpg","countryName":"德国","latitude":6.871167,"longitude":51.161475,"subCount":11}]}
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
         * count : 9
         * next :
         * previous :
         * results : [{"id":102,"name":"德国简介","cover":"https://music.gowithtommy.com/mjtt_backend_server/prod/upload/b02e162101b685b7141c1ea659f030e8.jpg","cover_path":"country_10/city_102/images/b02e162101b685b7141c1ea659f030e8.jpg","countryName":"德国","latitude":0,"longitude":0,"subCount":3},{"id":98,"name":"慕尼黑","cover":"https://music.gowithtommy.com/mjtt_backend_server/prod/data/3fd4c7e86235cafa11739a2ace1f4ba52889c7a8.jpg","cover_path":"country_10/city_98/images/3fd4c7e86235cafa11739a2ace1f4ba52889c7a8.jpg","countryName":"德国","latitude":48.129625,"longitude":11.577534,"subCount":17},{"id":100,"name":"法兰克福","cover":"https://music.gowithtommy.com/mjtt_backend_server/prod/data/707043cefbd5506c3870a806a1c1ad0fedf7335e.jpg","cover_path":"country_10/city_100/images/707043cefbd5506c3870a806a1c1ad0fedf7335e.jpg","countryName":"德国","latitude":50.11056,"longitude":8.683937,"subCount":19},{"id":95,"name":"柏林","cover":"https://music.gowithtommy.com/mjtt_backend_server/prod/data/aa88189524011ede3b96c4e6d8d31ee98530a9af.jpg","cover_path":"country_10/city_95/images/aa88189524011ede3b96c4e6d8d31ee98530a9af.jpg","countryName":"德国","latitude":52.519746,"longitude":13.401764,"subCount":19},{"id":97,"name":"科隆","cover":"https://music.gowithtommy.com/mjtt_backend_server/prod/data/10d6a1d7ec4c3cc42a9449d3161a265f664c8dc0.jpg","cover_path":"country_10/city_97/images/10d6a1d7ec4c3cc42a9449d3161a265f664c8dc0.jpg","countryName":"德国","latitude":50.935963,"longitude":6.955642,"subCount":13},{"id":96,"name":"福森","cover":"https://music.gowithtommy.com/mjtt_backend_server/prod/data/732bc5f20175cb55a87092083895f6fdbab33af5.jpg","cover_path":"country_10/city_96/images/732bc5f20175cb55a87092083895f6fdbab33af5.jpg","countryName":"德国","latitude":47.557559,"longitude":10.750423,"subCount":2},{"id":99,"name":"海德堡","cover":"https://music.gowithtommy.com/mjtt_backend_server/prod/data/c8d97f4964e3608d2b46d0a1e72d588d64bc71ab.jpg","cover_path":"country_10/city_99/images/c8d97f4964e3608d2b46d0a1e72d588d64bc71ab.jpg","countryName":"德国","latitude":49.419126,"longitude":8.670507,"subCount":5},{"id":101,"name":"汉堡","cover":"https://music.gowithtommy.com/mjtt_backend_server/prod/upload/70677cb152d7a6934b710ddb44c4a133.jpg","cover_path":"country_10/city_101/images/70677cb152d7a6934b710ddb44c4a133.jpg","countryName":"德国","latitude":53.5487,"longitude":9.997166,"subCount":15},{"id":172,"name":"杜塞尔多夫","cover":"https://music.gowithtommy.com/mjtt_backend_server/prod/upload/b97ea22d867ca7dcba7e28f8110aa425.jpg","cover_path":"country_10/city_172/images/b97ea22d867ca7dcba7e28f8110aa425.jpg","countryName":"德国","latitude":6.871167,"longitude":51.161475,"subCount":11}]
         */

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
             * id : 102
             * name : 德国简介
             * cover : https://music.gowithtommy.com/mjtt_backend_server/prod/upload/b02e162101b685b7141c1ea659f030e8.jpg
             * cover_path : country_10/city_102/images/b02e162101b685b7141c1ea659f030e8.jpg
             * countryName : 德国
             * latitude : 0
             * longitude : 0
             * subCount : 3
             */

            private int id;
            private String name;
            private String cover;
            private String cover_path;
            private String countryName;
            private double latitude;
            private double longitude;
            private int subCount;

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

            public String getCover_path() {
                return cover_path;
            }

            public void setCover_path(String cover_path) {
                this.cover_path = cover_path;
            }

            public String getCountryName() {
                return countryName;
            }

            public void setCountryName(String countryName) {
                this.countryName = countryName;
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

            @Override
            public String toString() {
                return "ResultsBean{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        ", cover='" + cover + '\'' +
                        ", cover_path='" + cover_path + '\'' +
                        ", countryName='" + countryName + '\'' +
                        ", latitude=" + latitude +
                        ", longitude=" + longitude +
                        ", subCount=" + subCount +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "count=" + count +
                    ", next='" + next + '\'' +
                    ", previous='" + previous + '\'' +
                    ", results=" + results +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CityBean{" +
                "data=" + data +
                '}';
    }
}
