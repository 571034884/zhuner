package com.aibabel.download.offline.bean;

import java.util.List;

public class NeizhiList {

    private List<ListFileBean> listFile;

    public List<ListFileBean> getListFile() {
        return listFile;
    }

    public void setListFile(List<ListFileBean> listFile) {
        this.listFile = listFile;
    }

    public static class ListFileBean {
        /**
         * id : ko_KR
         * fileName : ko_KR.zip
         * fileSize : 186.MB
         * asName : 韩语（Korean）
         * copyPath : {"zipPath":"/sdcard/download_offline/ko_KR.zip","unZipPath":"/sdcard/download_offline/ko_KR","childFiles":[{"fileName":"zh2ko","fromPath":"/sdcard/download_offline/ko_KR/zh2ko","toPath":"/sdcard/NiuTransTransformer/"},{"fileName":"ko2zh","fromPath":"/sdcard/download_offline/ko_KR/ko2zh","toPath":"/sdcard/NiuTransTransformer/"},{"fileName":"ko-KR","fromPath":"/sdcard/download_offline/ko-KR","toPath":"/sdcard/NiuTransTransformer/"}]}
         */

        private String id;
        private String fileName;
        private String fileSize;
        private String asName;
        private String lan_code;
        private String versionCode;

        public String getInstall_time() {
            return install_time;
        }

        public void setInstall_time(String install_time) {
            this.install_time = install_time;
        }

        private String install_time;

        public String getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(String versionCode) {
            this.versionCode = versionCode;
        }

        public String getLan_code() {
            return lan_code;
        }

        public void setLan_code(String lan_code) {
            this.lan_code = lan_code;
        }


        private CopyPathBean copyPath;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFileSize() {
            return fileSize;
        }

        public void setFileSize(String fileSize) {
            this.fileSize = fileSize;
        }

        public String getAsName() {
            return asName;
        }

        public void setAsName(String asName) {
            this.asName = asName;
        }

        public CopyPathBean getCopyPath() {
            return copyPath;
        }

        public void setCopyPath(CopyPathBean copyPath) {
            this.copyPath = copyPath;
        }

        public static class CopyPathBean {
            /**
             * zipPath : /sdcard/download_offline/ko_KR.zip
             * unZipPath : /sdcard/download_offline/ko_KR
             * childFiles : [{"fileName":"zh2ko","fromPath":"/sdcard/download_offline/ko_KR/zh2ko","toPath":"/sdcard/NiuTransTransformer/"},{"fileName":"ko2zh","fromPath":"/sdcard/download_offline/ko_KR/ko2zh","toPath":"/sdcard/NiuTransTransformer/"},{"fileName":"ko-KR","fromPath":"/sdcard/download_offline/ko-KR","toPath":"/sdcard/NiuTransTransformer/"}]
             */

            private String zipPath;
            private String unZipPath;
            private List<ChildFilesBean> childFiles;

            public String getZipPath() {
                return zipPath;
            }

            public void setZipPath(String zipPath) {
                this.zipPath = zipPath;
            }

            public String getUnZipPath() {
                return unZipPath;
            }

            public void setUnZipPath(String unZipPath) {
                this.unZipPath = unZipPath;
            }

            public List<ChildFilesBean> getChildFiles() {
                return childFiles;
            }

            public void setChildFiles(List<ChildFilesBean> childFiles) {
                this.childFiles = childFiles;
            }

            public static class ChildFilesBean {
                /**
                 * fileName : zh2ko
                 * fromPath : /sdcard/download_offline/ko_KR/zh2ko
                 * toPath : /sdcard/NiuTransTransformer/
                 */

                private String fileName;
                private String fromPath;
                private String toPath;

                public String getFileName() {
                    return fileName;
                }

                public void setFileName(String fileName) {
                    this.fileName = fileName;
                }

                public String getFromPath() {
                    return fromPath;
                }

                public void setFromPath(String fromPath) {
                    this.fromPath = fromPath;
                }

                public String getToPath() {
                    return toPath;
                }

                public void setToPath(String toPath) {
                    this.toPath = toPath;
                }
            }
        }
    }
}
