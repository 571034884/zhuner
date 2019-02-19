package com.aibabel.speech.bean;

public class TxtResultBean {

    /**
     * code : 016
     * data : {"answer":"已为您找到梁静茹的暖暖","info":{"album":"亲亲","category":"","musicUrl":"http://cdn.stormorai.com/resource/music_bk/763/30169763.mp3","artist":"梁静茹","albumIcon":"https://y.gtimg.cn/music/photo_new/T002R300x300M000004R8LFN08EGAD.jpg?max_age=2592000","songName":"暖暖"}}
     */

    private String code;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * answer : 已为您找到梁静茹的暖暖
         * info : {"album":"亲亲","category":"","musicUrl":"http://cdn.stormorai.com/resource/music_bk/763/30169763.mp3","artist":"梁静茹","albumIcon":"https://y.gtimg.cn/music/photo_new/T002R300x300M000004R8LFN08EGAD.jpg?max_age=2592000","songName":"暖暖"}
         */

        private String answer;
        private InfoBean info;

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public static class InfoBean {
            /**
             * album : 亲亲
             * category :
             * musicUrl : http://cdn.stormorai.com/resource/music_bk/763/30169763.mp3
             * artist : 梁静茹
             * albumIcon : https://y.gtimg.cn/music/photo_new/T002R300x300M000004R8LFN08EGAD.jpg?max_age=2592000
             * songName : 暖暖
             */

            private String album;
            private String category;
            private String musicUrl;
            private String artist;
            private String albumIcon;
            private String songName;

            public String getAlbum() {
                return album;
            }

            public void setAlbum(String album) {
                this.album = album;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getMusicUrl() {
                return musicUrl;
            }

            public void setMusicUrl(String musicUrl) {
                this.musicUrl = musicUrl;
            }

            public String getArtist() {
                return artist;
            }

            public void setArtist(String artist) {
                this.artist = artist;
            }

            public String getAlbumIcon() {
                return albumIcon;
            }

            public void setAlbumIcon(String albumIcon) {
                this.albumIcon = albumIcon;
            }

            public String getSongName() {
                return songName;
            }

            public void setSongName(String songName) {
                this.songName = songName;
            }
        }
    }
}
