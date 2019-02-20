package com.aibabel.food.bean;

import com.aibabel.baselibrary.http.BaseBean;

import java.util.List;

/**
 * 作者：SunSH on 2018/12/14 17:19
 * 功能：
 * 版本：1.0
 */
public class HomePageAllBean extends BaseBean {
    /**
     * data : {"banner_json":[{"article":"http://mini.eastday.com/a/18105819.html?qid=02157 ","id":"123","url":"123"},{"article":"http://mini.eastday.com/a/18105819.html?qid=02157 ","id":"123","url":"123"}],"begion_shoptype_json":[{"objectlist":[{"iconUrl":"https://timgsa.b20b306yaa2.jpg","name":"新宿"},{"iconUrl":"https://timgsa.ba0b306yaa2.jpg","name":"新宿"},{"iconUrl":"https://timgsa.ba0b306yaa2.jpg","name":"新宿"},{"iconUrl":"https://timgsa.bec=06yaa2.jpg","name":"新宿"}],"partName":"人气区域","partType":1},{"objectlist":[{"iconUrl":"https://timgsa.baidu.6yaa2.jpg","name":"海鲜"},{"iconUrl":"https://timgsa.baid306yaa2.jpg","name":"海鲜"},{"iconUrl":"https://timgsa.baib306yaa2.jpg","name":"海鲜"},{"iconUrl":"https://timgsa.ba0b306yaa2.jpg","name":"海鲜"}],"partName":"美食分类","partType":2}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<BannerJsonBean> banner_json;
        private List<BegionShoptypeJsonBean> begion_shoptype_json;

        public List<BannerJsonBean> getBanner_json() {
            return banner_json;
        }

        public void setBanner_json(List<BannerJsonBean> banner_json) {
            this.banner_json = banner_json;
        }

        public List<BegionShoptypeJsonBean> getBegion_shoptype_json() {
            return begion_shoptype_json;
        }

        public void setBegion_shoptype_json(List<BegionShoptypeJsonBean> begion_shoptype_json) {
            this.begion_shoptype_json = begion_shoptype_json;
        }

        public static class BannerJsonBean {
            /**
             * article : http://mini.eastday.com/a/18105819.html?qid=02157
             * id : 123
             * url : 123
             * nameCn:""
             */

            private String article;
            private String id;
            private String url;
            private String nameCn;

            public String getArticle() {
                return article;
            }

            public void setArticle(String article) {
                this.article = article;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getNameCn() {
                return nameCn;
            }

            public void setNameCn(String nameCn) {
                this.nameCn = nameCn;
            }
        }

        public static class BegionShoptypeJsonBean {
            /**
             * objectlist : [{"iconUrl":"https://timgsa.b20b306yaa2.jpg","name":"新宿"},{"iconUrl":"https://timgsa.ba0b306yaa2.jpg","name":"新宿"},{"iconUrl":"https://timgsa.ba0b306yaa2.jpg","name":"新宿"},{"iconUrl":"https://timgsa.bec=06yaa2.jpg","name":"新宿"}]
             * partName : 人气区域
             * partType : 1
             */

            private String partName;
            private int partType;
            private List<ListBean> list;

            public String getPartName() {
                return partName;
            }

            public void setPartName(String partName) {
                this.partName = partName;
            }

            public int getPartType() {
                return partType;
            }

            public void setPartType(int partType) {
                this.partType = partType;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> objectlist) {
                this.list = objectlist;
            }

            public static class ListBean {
                /**
                 * iconUrl : https://timgsa.b20b306yaa2.jpg
                 * name : 新宿
                 */

                private String iconUrl;
                private String name;

                public String getIconUrl() {
                    return iconUrl;
                }

                public void setIconUrl(String iconUrl) {
                    this.iconUrl = iconUrl;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }
    }
}
