package com.aibabel.download.offline.bean;

import java.util.List;

public class OfflineList {


    /**
     * code : 1
     * msg : Success!
     * data : {"code":1,"msg":"Success!","data":[{"id":"testMp3","ver":"0001","name":"1212.mp3","size":"3.253MB","md5":"39C38560EF11AFA55EC47D1BA992A9BD","note":"测试使用"},{"id":"testText","ver":"0001","name":"test.txt","size":"4B","md5":"FF13F7F55F7154F6984D9A26D8A317F9","note":"测试使用"},{"id":"ru_RU","ver":"0001","name":"ru_RU.rar","size":"181.51MB","md5":"8C9D07FAECA4B68BDACE7B2ECDF22F35","note":"俄语"},{"id":"ko_KR","ver":"0001","name":"ko_KR.zip","size":"197.06MB","md5":"E4736E3C38661AFF318D056D28CBE386","note":"韩语"}]}
     */

    private String code;
    private String msg;
    private DataBeanX data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * code : 1
         * msg : Success!
         * data : [{"id":"testMp3","ver":"0001","name":"1212.mp3","size":"3.253MB","md5":"39C38560EF11AFA55EC47D1BA992A9BD","note":"测试使用"},{"id":"testText","ver":"0001","name":"test.txt","size":"4B","md5":"FF13F7F55F7154F6984D9A26D8A317F9","note":"测试使用"},{"id":"ru_RU","ver":"0001","name":"ru_RU.rar","size":"181.51MB","md5":"8C9D07FAECA4B68BDACE7B2ECDF22F35","note":"俄语"},{"id":"ko_KR","ver":"0001","name":"ko_KR.zip","size":"197.06MB","md5":"E4736E3C38661AFF318D056D28CBE386","note":"韩语"}]
         */

        private int code;
        private String msg;
        private List<DataBean> data;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            @Override
            public String toString() {
                return "DataBean{" +
                        "id='" + id + '\'' +
                        ", ver='" + ver + '\'' +
                        ", name='" + name + '\'' +
                        ", size='" + size + '\'' +
                        ", md5='" + md5 + '\'' +
                        ", note='" + note + '\'' +
                        ", status='" + status + '\'' +
                        ", lan_name='" + lan_name + '\'' +
                        ", copy_path='" + copy_path + '\'' +
                        ", lan_code='" + lan_code + '\'' +
                        ", down_size_prog='" + down_size_prog + '\'' +
                        '}';
            }

            /**
             * id : testMp3
             * ver : 0001
             * name : 1212.mp3
             * size : 3.253MB
             * md5 : 39C38560EF11AFA55EC47D1BA992A9BD
             * note : 测试使用
             */

            private String id;
            private String ver;
            private String name;
            private String size;
            private String md5;
            private String note;
            private String status;
            private String lan_name;

            public String getCopy_path() {
                return copy_path;
            }

            public void setCopy_path(String copy_path) {
                this.copy_path = copy_path;
            }

            private String copy_path;

            public String getLan_name() {
                return lan_name;
            }

            public void setLan_name(String lan_name) {
                this.lan_name = lan_name;
            }

            public String getLan_code() {
                return lan_code;
            }

            public void setLan_code(String lan_code) {
                this.lan_code = lan_code;
            }

            private String lan_code;


            public String getDown_size_prog() {
                if (down_size_prog==null) {
                    return "";
                }
                return down_size_prog;
            }

            public void setDown_size_prog(String down_size_prog) {
                this.down_size_prog = down_size_prog;
            }

            private String down_size_prog;

            public String getStatus() {
                if (status==null) {
                    return "";
                }
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getVer() {
                return ver;
            }

            public void setVer(String ver) {
                this.ver = ver;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSize() {
                return size;
            }

            public void setSize(String size) {
                this.size = size;
            }

            public String getMd5() {
                return md5;
            }

            public void setMd5(String md5) {
                this.md5 = md5;
            }

            public String getNote() {
                return note;
            }

            public void setNote(String note) {
                this.note = note;
            }
        }
    }
}
