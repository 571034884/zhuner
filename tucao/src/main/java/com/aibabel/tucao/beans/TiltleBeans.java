package com.aibabel.tucao.beans;

import com.aibabel.tucao.okgo.BaseBean;

import java.util.List;

/**
 * 作者：wuqinghua_fyt on 2018/8/17 15:30
 * 功能：
 * 版本：1.0
 */
public class TiltleBeans extends BaseBean {


    /**
     * data : {"title":"吐槽开始","descriptor":"您觉得准儿还有哪些地方可以改进。","questionVersion":"0000","question1":[{"questionMsg":"本次使用是否满意","QuestionType":"1","answer":["满意","不满意"]}],"question2":[{"questionMsg":"您觉得准儿哪个功能超出了您的预期","QuestionType":"2","answer":["语音翻译，沟通更顺畅","拍照翻译，点菜没烦恼","景区导览，自由行无忧"]},{"questionMsg":"您觉得准儿哪些功能还可以改进","QuestionType":"2","answer":["翻译准确率 ","网络稳定性 ","实用小功能"]}],"note":""}
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
         * title : 吐槽开始
         * descriptor : 您觉得准儿还有哪些地方可以改进。
         * questionVersion : 0000
         * question1 : [{"questionMsg":"本次使用是否满意","QuestionType":"1","answer":["满意","不满意"]}]
         * question2 : [{"questionMsg":"您觉得准儿哪个功能超出了您的预期","QuestionType":"2","answer":["语音翻译，沟通更顺畅","拍照翻译，点菜没烦恼","景区导览，自由行无忧"]},{"questionMsg":"您觉得准儿哪些功能还可以改进","QuestionType":"2","answer":["翻译准确率 ","网络稳定性 ","实用小功能"]}]
         * note :
         */

        private String title;
        private String descriptor;
        private String questionVersion;
        private String note;
        private List<Question1Bean> question1;
        private List<Question2Bean> question2;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescriptor() {
            return descriptor;
        }

        public void setDescriptor(String descriptor) {
            this.descriptor = descriptor;
        }

        public String getQuestionVersion() {
            return questionVersion;
        }

        public void setQuestionVersion(String questionVersion) {
            this.questionVersion = questionVersion;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public List<Question1Bean> getQuestion1() {
            return question1;
        }

        public void setQuestion1(List<Question1Bean> question1) {
            this.question1 = question1;
        }

        public List<Question2Bean> getQuestion2() {
            return question2;
        }

        public void setQuestion2(List<Question2Bean> question2) {
            this.question2 = question2;
        }

        public static class Question1Bean {
            /**
             * questionMsg : 本次使用是否满意
             * QuestionType : 1
             * answer : ["满意","不满意"]
             */

            private String questionMsg;
            private String QuestionType;
            private List<String> answer;

            public String getQuestionMsg() {
                return questionMsg;
            }

            public void setQuestionMsg(String questionMsg) {
                this.questionMsg = questionMsg;
            }

            public String getQuestionType() {
                return QuestionType;
            }

            public void setQuestionType(String QuestionType) {
                this.QuestionType = QuestionType;
            }

            public List<String> getAnswer() {
                return answer;
            }

            public void setAnswer(List<String> answer) {
                this.answer = answer;
            }
        }

        public static class Question2Bean {
            /**
             * questionMsg : 您觉得准儿哪个功能超出了您的预期
             * QuestionType : 2
             * answer : ["语音翻译，沟通更顺畅","拍照翻译，点菜没烦恼","景区导览，自由行无忧"]
             */

            private String questionMsg;
            private String QuestionType;
            private List<String> answer;

            public String getQuestionMsg() {
                return questionMsg;
            }

            public void setQuestionMsg(String questionMsg) {
                this.questionMsg = questionMsg;
            }

            public String getQuestionType() {
                return QuestionType;
            }

            public void setQuestionType(String QuestionType) {
                this.QuestionType = QuestionType;
            }

            public List<String> getAnswer() {
                return answer;
            }

            public void setAnswer(List<String> answer) {
                this.answer = answer;
            }
        }
    }
}
