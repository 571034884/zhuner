package com.aibabel.travel.bean;

import java.util.List;

public class SpotBean extends BaseModel {

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
             * id : 2803
             * name : 红场
             * cover : https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F682961907141e6cfb7c1cd96a668ae7492cdad53.jpg
             * cover_path : country_31/city_31/scene_2803/images/682961907141e6cfb7c1cd96a668ae7492cdad53.jpg
             * map_image :
             * map_image_path :
             * navi_image : https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F9953e63935f18fa19ede281352ae4cc07f99b7ad.png
             * navi_image_path : country_31/city_31/scene_2803/images/9953e63935f18fa19ede281352ae4cc07f99b7ad.png
             * cityName : 莫斯科
             * countryName : 俄罗斯
             * latitude : 55.753929
             * longitude : 37.620805
             * audios : [{"url":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/3eb366506b8da95a6f02f0562b8b83cc46124692.mp3","size":0.45,"path":"country_31/city_31/scene_2803/audios/3eb366506b8da95a6f02f0562b8b83cc46124692.mp3"}]
             * images : [{"url":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2Fc5a1ed37f34377134e9ec519dccab5b8ad3529c1.jpg","size":0.09,"path":"country_31/city_31/scene_2803/images/c5a1ed37f34377134e9ec519dccab5b8ad3529c1.jpg"}]
             * subCount : 10
             */

            private int id;
            private String name;
            private String cover;
            private String cover_path;
            private String map_image;
            private String map_image_path;
            private String navi_image;
            private String navi_image_path;
            private String cityName;
            private String countryName;
            private double latitude;
            private double longitude;
            private int subCount;
            private List<AudiosBean> audios;
            private List<ImagesBean> images;

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

            public String getMap_image() {
                return map_image;
            }

            public void setMap_image(String map_image) {
                this.map_image = map_image;
            }

            public String getMap_image_path() {
                return map_image_path;
            }

            public void setMap_image_path(String map_image_path) {
                this.map_image_path = map_image_path;
            }

            public String getNavi_image() {
                return navi_image;
            }

            public void setNavi_image(String navi_image) {
                this.navi_image = navi_image;
            }

            public String getNavi_image_path() {
                return navi_image_path;
            }

            public void setNavi_image_path(String navi_image_path) {
                this.navi_image_path = navi_image_path;
            }

            public String getCityName() {
                return cityName;
            }

            public void setCityName(String cityName) {
                this.cityName = cityName;
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

            public List<AudiosBean> getAudios() {
                return audios;
            }

            public void setAudios(List<AudiosBean> audios) {
                this.audios = audios;
            }

            public List<ImagesBean> getImages() {
                return images;
            }

            public void setImages(List<ImagesBean> images) {
                this.images = images;
            }

            public static class AudiosBean {
                /**
                 * url : https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/3eb366506b8da95a6f02f0562b8b83cc46124692.mp3
                 * size : 0.45
                 * path : country_31/city_31/scene_2803/audios/3eb366506b8da95a6f02f0562b8b83cc46124692.mp3
                 */

                private String url;
                private double size;
                private String path;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public double getSize() {
                    return size;
                }

                public void setSize(double size) {
                    this.size = size;
                }

                public String getPath() {
                    return path;
                }

                public void setPath(String path) {
                    this.path = path;
                }
            }

            public static class ImagesBean {
                /**
                 * url : https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2Fc5a1ed37f34377134e9ec519dccab5b8ad3529c1.jpg
                 * size : 0.09
                 * path : country_31/city_31/scene_2803/images/c5a1ed37f34377134e9ec519dccab5b8ad3529c1.jpg
                 */

                private String url;
                private double size;
                private String path;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public double getSize() {
                    return size;
                }

                public void setSize(double size) {
                    this.size = size;
                }

                public String getPath() {
                    return path;
                }

                public void setPath(String path) {
                    this.path = path;
                }
            }
        }
    }
}
