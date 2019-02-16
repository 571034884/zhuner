package com.aibabel.travel.bean;

import java.io.Serializable;
import java.util.List;

public class SpotChildrenBean extends BaseModel implements Serializable{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
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

        public static class ResultsBean implements Serializable{
            /**
             * id : 1843
             * name : 徒步登上埃菲尔铁塔
             * pid : 16
             * pname : 埃菲尔铁塔
             * cover : https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F5b42e78770c18fb1ab765ced4c79dd5cfe0ad649.jpg
             * cover_path : country_5/city_13/scene_16/subscene_1843/images/5b42e78770c18fb1ab765ced4c79dd5cfe0ad649.jpg
             * cityName : 巴黎
             * countryName : 法国
             * latitude : 0
             * longitude : 0
             * audios : [{"url":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/9a5c582e8fe624266d0ac240a79d3dbb310dd0b2.mp3","size":0,"path":"country_5/city_13/scene_16/subscene_1843/audios/9a5c582e8fe624266d0ac240a79d3dbb310dd0b2.mp3"}]
             * images : [{"url":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F0d994bf3c5df95a3e0a09805ff588c944c45b26c.jpg","size":0.09,"path":"country_5/city_13/scene_16/subscene_1843/images/0d994bf3c5df95a3e0a09805ff588c944c45b26c.jpg"}]
             * subCount :
             */

            private int id;
            private String name;
            private int pid;
            private String pname;
            private String cover;
            private String cover_path;
            private String cityName;
            private String countryName;
            private double latitude;
            private double longitude;
            private String subCount;
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

            public int getPid() {
                return pid;
            }

            public void setPid(int pid) {
                this.pid = pid;
            }

            public String getPname() {
                return pname;
            }

            public void setPname(String pname) {
                this.pname = pname;
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

            public String getSubCount() {
                return subCount;
            }

            public void setSubCount(String subCount) {
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
                 * url : https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/9a5c582e8fe624266d0ac240a79d3dbb310dd0b2.mp3
                 * size : 0
                 * path : country_5/city_13/scene_16/subscene_1843/audios/9a5c582e8fe624266d0ac240a79d3dbb310dd0b2.mp3
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
                 * url : https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F0d994bf3c5df95a3e0a09805ff588c944c45b26c.jpg
                 * size : 0.09
                 * path : country_5/city_13/scene_16/subscene_1843/images/0d994bf3c5df95a3e0a09805ff588c944c45b26c.jpg
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
