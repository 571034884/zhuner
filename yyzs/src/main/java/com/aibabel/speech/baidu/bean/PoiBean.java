package com.aibabel.speech.baidu.bean;

import java.util.List;

public class PoiBean {


    /**
     * code : 003_0
     * bot_session : <DROPPED>
     * data : {"answer":"为您查找当前位置附近的美食...","type":"poi_list","body":[{"name":"北京醉爱时尚餐厅","area":"海淀区","distance":111,"rating":"3.2","price":"142.5","tag":"美食;中餐厅","url":"http://api.map.baidu.com/place/detail?uid=cc0849a00fd5174994031909&output=html&source=placeapi_v2"},{"name":"东方宫(五道口店)","area":"海淀区","distance":66,"rating":"4.6","price":"30.0","tag":"美食;中餐厅","url":"http://api.map.baidu.com/place/detail?uid=de5f16ce5f141f8b223bf2e3&output=html&source=placeapi_v2"},{"name":"全聚德(清华园店)","area":"海淀区","distance":359,"rating":"3.9","price":"140.0","tag":"美食;中餐厅","url":"http://api.map.baidu.com/place/detail?uid=be54cde942b9064178f7967e&output=html&source=placeapi_v2"},{"name":"天厨妙香素食馆(五道口店)","area":"海淀区","distance":485,"rating":"4.8","price":"69.5","tag":"美食;中餐厅","url":"http://api.map.baidu.com/place/detail?uid=75d0881a951532543a04b1ca&output=html&source=placeapi_v2"},{"name":"warawara(五道口店)","area":"海淀区","distance":199,"rating":"4.5","price":"106.0","tag":"美食;中餐厅","url":"http://api.map.baidu.com/place/detail?uid=e8aa3806a2ff7d0e6f3e1bcd&output=html&source=placeapi_v2"},{"name":"宴铭园(清华店)","area":"海淀区","distance":316,"rating":"4.2","price":"136.0","tag":"美食;中餐厅","url":"http://api.map.baidu.com/place/detail?uid=4ce10ce66000f0b8451a328f&output=html&source=placeapi_v2"},{"name":"呷哺呷哺(五道口华联店)","area":"海淀区","distance":471,"rating":"4.2","price":"46.0","tag":"美食;中餐厅","url":"http://api.map.baidu.com/place/detail?uid=a3e8341c724b8d8c4afd3f67&output=html&source=placeapi_v2"},{"name":"东来顺(五道口店)","area":"海淀区","distance":438,"rating":"4.1","price":"111.0","tag":"美食;中餐厅","url":"http://api.map.baidu.com/place/detail?uid=593850fbb8bb0944de1e52b7&output=html&source=placeapi_v2"},{"name":"江边城外烤全鱼(五道口店)","area":"海淀区","distance":501,"rating":"4.5","price":"84.0","tag":"美食;中餐厅","url":"http://api.map.baidu.com/place/detail?uid=ba509d95c549c2192cb17e53&output=html&source=placeapi_v2"},{"name":"麻辣诱惑(五道口店)","area":"海淀区","distance":428,"rating":"4.5","price":"92.5","tag":"美食;中餐厅","url":"http://api.map.baidu.com/place/detail?uid=4eb3b5333bf50f6c43024538&output=html&source=placeapi_v2"}]}
     */

    private String code;
    private String bot_session;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBot_session() {
        return bot_session;
    }

    public void setBot_session(String bot_session) {
        this.bot_session = bot_session;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * answer : 为您查找当前位置附近的美食...
         * type : poi_list
         * body : [{"name":"北京醉爱时尚餐厅","area":"海淀区","distance":111,"rating":"3.2","price":"142.5","tag":"美食;中餐厅","url":"http://api.map.baidu.com/place/detail?uid=cc0849a00fd5174994031909&output=html&source=placeapi_v2"},{"name":"东方宫(五道口店)","area":"海淀区","distance":66,"rating":"4.6","price":"30.0","tag":"美食;中餐厅","url":"http://api.map.baidu.com/place/detail?uid=de5f16ce5f141f8b223bf2e3&output=html&source=placeapi_v2"},{"name":"全聚德(清华园店)","area":"海淀区","distance":359,"rating":"3.9","price":"140.0","tag":"美食;中餐厅","url":"http://api.map.baidu.com/place/detail?uid=be54cde942b9064178f7967e&output=html&source=placeapi_v2"},{"name":"天厨妙香素食馆(五道口店)","area":"海淀区","distance":485,"rating":"4.8","price":"69.5","tag":"美食;中餐厅","url":"http://api.map.baidu.com/place/detail?uid=75d0881a951532543a04b1ca&output=html&source=placeapi_v2"},{"name":"warawara(五道口店)","area":"海淀区","distance":199,"rating":"4.5","price":"106.0","tag":"美食;中餐厅","url":"http://api.map.baidu.com/place/detail?uid=e8aa3806a2ff7d0e6f3e1bcd&output=html&source=placeapi_v2"},{"name":"宴铭园(清华店)","area":"海淀区","distance":316,"rating":"4.2","price":"136.0","tag":"美食;中餐厅","url":"http://api.map.baidu.com/place/detail?uid=4ce10ce66000f0b8451a328f&output=html&source=placeapi_v2"},{"name":"呷哺呷哺(五道口华联店)","area":"海淀区","distance":471,"rating":"4.2","price":"46.0","tag":"美食;中餐厅","url":"http://api.map.baidu.com/place/detail?uid=a3e8341c724b8d8c4afd3f67&output=html&source=placeapi_v2"},{"name":"东来顺(五道口店)","area":"海淀区","distance":438,"rating":"4.1","price":"111.0","tag":"美食;中餐厅","url":"http://api.map.baidu.com/place/detail?uid=593850fbb8bb0944de1e52b7&output=html&source=placeapi_v2"},{"name":"江边城外烤全鱼(五道口店)","area":"海淀区","distance":501,"rating":"4.5","price":"84.0","tag":"美食;中餐厅","url":"http://api.map.baidu.com/place/detail?uid=ba509d95c549c2192cb17e53&output=html&source=placeapi_v2"},{"name":"麻辣诱惑(五道口店)","area":"海淀区","distance":428,"rating":"4.5","price":"92.5","tag":"美食;中餐厅","url":"http://api.map.baidu.com/place/detail?uid=4eb3b5333bf50f6c43024538&output=html&source=placeapi_v2"}]
         */

        private String answer;
        private String type;
        private List<BodyBean> body;

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<BodyBean> getBody() {
            return body;
        }

        public void setBody(List<BodyBean> body) {
            this.body = body;
        }

        public static class BodyBean {
            /**
             * name : 北京醉爱时尚餐厅
             * area : 海淀区
             * distance : 111
             * rating : 3.2
             * price : 142.5
             * tag : 美食;中餐厅
             * url : http://api.map.baidu.com/place/detail?uid=cc0849a00fd5174994031909&output=html&source=placeapi_v2
             */

            private String name;
            private String area;
            private int distance;
            private String rating;
            private String price;
            private String tag;
            private String url;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public int getDistance() {
                return distance;
            }

            public void setDistance(int distance) {
                this.distance = distance;
            }

            public String getRating() {
                return rating;
            }

            public void setRating(String rating) {
                this.rating = rating;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
