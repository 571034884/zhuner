package com.aibabel.food.bean;

import com.aibabel.baselibrary.http.BaseBean;

import java.util.List;

/**
 * 作者：SunSH on 2018/12/17 18:00
 * 功能：
 * 版本：1.0
 */
public class PopupWindowBean extends BaseBean {
    /**
     * data : {"districts":[{"id":"123456","name":"券商区","count":"3"},{"id":"654321","name":"勾下去","count":"1"}],"sorts":["score","distance"],"tag":[{"id":"11110003","nameTag":"烤串","count":"0"},{"id":"1111004","nameTag":"面食","count":"0"},{"id":"111112322","nameTag":"狗屎","count":"0"},{"id":"1212312","nameTag":"火锅","count":"3"},{"id":"1arafr","nameTag":"海鲜","count":"1"},{"id":"2222001","nameTag":"寿司","count":"0"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<DistrictsBean> districts;
        private List<String> sorts;
        private List<TagBean> tag;

        public List<DistrictsBean> getDistricts() {
            return districts;
        }

        public void setDistricts(List<DistrictsBean> districts) {
            this.districts = districts;
        }

        public List<String> getSorts() {
            return sorts;
        }

        public void setSorts(List<String> sorts) {
            this.sorts = sorts;
        }

        public List<TagBean> getTag() {
            return tag;
        }

        public void setTag(List<TagBean> tag) {
            this.tag = tag;
        }

        public static class DistrictsBean {
            /**
             * id : 123456
             * name : 券商区
             * count : 3
             */

            private String id;
            private String name;
            private String count;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }
        }

        public static class TagBean {
            /**
             * id : 11110003
             * nameTag : 烤串
             * count : 0
             */

            private String id;
            private String nameTag;
            private String count;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getNameTag() {
                return nameTag;
            }

            public void setNameTag(String nameTag) {
                this.nameTag = nameTag;
            }

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }
        }
    }
}
