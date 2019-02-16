package com.aibabel.travel.bean;

public class Country_City extends BaseModel{


    /**
     * data : {"id":8,"name":"日本","cover":"https://music.gowithtommy.com/mjtt_backend_server/prod/data/e1d64bfa0c35f82223da4038a860cacadfecab33.jpg","type":1,"pid":0,"pname":"","cover_path":"","map_image":"","map_image_path":"","navi_image":"","navi_image_path":"","cityname":"","countryname":"","latitude":"35.719546","longitude":"139.733040","audios":{"url":"","size":0,"path":""},"images":{"url":"","size":0,"path":""},"subcount":21}
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
         * id : 8
         * name : 日本
         * cover : https://music.gowithtommy.com/mjtt_backend_server/prod/data/e1d64bfa0c35f82223da4038a860cacadfecab33.jpg
         * type : 1
         * pid : 0
         * pname :
         * cover_path :
         * map_image :
         * map_image_path :
         * navi_image :
         * navi_image_path :
         * cityname :
         * countryname :
         * latitude : 35.719546
         * longitude : 139.733040
         * audios : {"url":"","size":0,"path":""}
         * images : {"url":"","size":0,"path":""}
         * subcount : 21
         */

        private int id;
        private String name;
        private String cover;
        private int type;
        private int pid;
        private String pname;
        private String cover_path;
        private String map_image;
        private String map_image_path;
        private String navi_image;
        private String navi_image_path;
        private String cityname;
        private String countryname;
        private String latitude;
        private String longitude;
        private AudiosBean audios;
        private ImagesBean images;
        private int subcount;

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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
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

        public String getCityname() {
            return cityname;
        }

        public void setCityname(String cityname) {
            this.cityname = cityname;
        }

        public String getCountryname() {
            return countryname;
        }

        public void setCountryname(String countryname) {
            this.countryname = countryname;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public AudiosBean getAudios() {
            return audios;
        }

        public void setAudios(AudiosBean audios) {
            this.audios = audios;
        }

        public ImagesBean getImages() {
            return images;
        }

        public void setImages(ImagesBean images) {
            this.images = images;
        }

        public int getSubcount() {
            return subcount;
        }

        public void setSubcount(int subcount) {
            this.subcount = subcount;
        }

        public static class AudiosBean {
            /**
             * url :
             * size : 0
             * path :
             */

            private String url;
            private int size;
            private String path;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
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
             * url :
             * size : 0
             * path :
             */

            private String url;
            private int size;
            private String path;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
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
