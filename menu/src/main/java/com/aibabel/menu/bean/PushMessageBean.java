
package com.aibabel.menu.bean;

import org.litepal.crud.LitePalSupport;

import java.util.List;
public class PushMessageBean extends LitePalSupport {
    private boolean badge;
    private long id;
    private int num;


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "PushMessageBean{" +
                "badge=" + badge +
                ", id=" + id +
                ", json='" + json + '\'' +
                ", title='" + title + '\'' +
                ", level='" + level + '\'' +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", apk='" + apk + '\'' +
                ", timeCode='" + timeCode + '\'' +
                ", packageName='" + packageName + '\'' +
                ", path='" + path + '\'' +
                ", resultData=" + resultData +
                '}';
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    private String json;

    public long getId() {
        return id;
    }

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isBadge() {
        return badge;
    }

    public void setBadge(boolean badge) {
        this.badge = badge;
    }

    /**
     * 通知显示级别，1为立即显示，2为普通
     */
    private String level;


    /**
     * type 通知类型
     */
    private String type;
    /**
     * 通知内容详情
     */
    private String content;
    /**
     * apk名称
     */
    private String apk;
    /**
     * 时间戳
     */
    private String timeCode;
    /**
     * 待跳转应用包名
     */
    private String packageName;
    /**
     * 待跳转应用的全路径名
     */
    private String path;
    /**
     * 推送景点信息
     */
    private List<ResultDataBean> resultData;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getApk() {
        return apk;
    }

    public void setApk(String apk) {
        this.apk = apk;
    }

    public String getTimeCode() {
        return timeCode;
    }

    public void setTimeCode(String timeCode) {
        this.timeCode = timeCode;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<ResultDataBean> getResultData() {
        return resultData;
    }

    public void setResultData(List<ResultDataBean> resultData) {
        this.resultData = resultData;
    }

    public static class ResultDataBean {
        /**
         * idstring : CurrentCityId100
         * dataObj : {"id":100,"name":"法兰克福","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F707043cefbd5506c3870a806a1c1ad0fedf7335e.jpg","type":2,"pid":0,"pname":"","cover_path":"country_10/city_100/images/707043cefbd5506c3870a806a1c1ad0fedf7335e.jpg","map_image":"","map_image_path":"","navi_image":"","navi_image_path":"","cityname":"","countryname":"德国","latitude":"50.110560","longitude":"8.683937","audios":{"url":"","size":0,"path":""},"images":{"url":"","size":0,"path":""},"subcount":18}
         * md5 : 411ada6c1df5a1f27bca8e8521ec84cf
         */

        private String couponId;
        private String idstring;
        private String name;
        private String cover;
        private String audiosurl;

        public String getIdstring() {
            return idstring;
        }

        public void setIdstring(String idstring) {
            this.idstring = idstring;
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

        public String getAudiosurl() {
            return audiosurl;
        }

        public void setAudiosurl(String audiosurl) {
            this.audiosurl = audiosurl;
        }

        public String getCouponId() {
            return couponId;
        }

        public void setCouponId(String couponId) {
            this.couponId = couponId;
        }
    }


}
