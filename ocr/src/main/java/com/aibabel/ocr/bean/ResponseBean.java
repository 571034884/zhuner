package com.aibabel.ocr.bean;

import java.util.List;

/**
 * =====================================================================
 * <p>
 * 作者 : 张文颖
 * <p>
 * 时间: 2018/4/2
 * <p>
 * 描述:
 * <p>
 * =====================================================================
 */

public class ResponseBean {

    /**
     * result : [{"words":"识别文本内容1","location":{"x":1,"y":1,"width":123,"height":321}},{"words":"识别文本内容2","location":{"x":1,"y":1,"width":123,"height":321}},{"words":"识别文本内容3","location":{"x":1,"y":1,"width":123,"height":321}}]
     * error_code : 0
     */
    private int error_code;
    private String 	mode;
    private List<ResultBean> result;
    private String error_message;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * words : 识别文本内容1
         * location : {"x":1,"y":1,"width":123,"height":321}
         */

        private String words;
        private String trans_words;
        private LocationBean location;

        public String getWords() {
            return words;
        }

        public void setWords(String words) {
            this.words = words;
        }

        public String getTrans_words() {
            return trans_words;
        }

        public void setTrans_words(String trans_words) {
            this.trans_words = trans_words;
        }

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public static class LocationBean {
            /**
             * x : 1
             * y : 1
             * width : 123
             * height : 321
             */

            private int x;
            private int y;
            private int width;
            private int height;

            public int getX() {
                return x;
            }

            public void setX(int x) {
                this.x = x;
            }

            public int getY() {
                return y;
            }

            public void setY(int y) {
                this.y = y;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }


            @Override
            public String toString() {
                return "LocationBean{" +
                        "x=" + x +
                        ", y=" + y +
                        ", width=" + width +
                        ", height=" + height +
                        '}';
            }
        }
    }


    @Override
    public String toString() {
        return "ResponseBean{" +
                "error_code=" + error_code +
                ", result=" + result +
                '}';
    }
}
