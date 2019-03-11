package com.aibabel.translate.bean;

import java.util.List;

public class Returnjson {

    private List<ResultsBean> results;

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * alternatives : [{"transcript":"喂喂喂喂现在说话看一下中间有没有结果回来喂喂喂喂喂什么","confidence":0.94319963}]
         * is_final : true
         * result_end_time : {"seconds":10,"nanos":680000000}
         * language_code : cmn-hans-cn
         */

        private boolean is_final;
        private ResultEndTimeBean result_end_time;
        private String language_code;
        private List<AlternativesBean> alternatives;

        public boolean isIs_final() {
            return is_final;
        }

        public void setIs_final(boolean is_final) {
            this.is_final = is_final;
        }

        public ResultEndTimeBean getResult_end_time() {
            return result_end_time;
        }

        public void setResult_end_time(ResultEndTimeBean result_end_time) {
            this.result_end_time = result_end_time;
        }

        public String getLanguage_code() {
            return language_code;
        }

        public void setLanguage_code(String language_code) {
            this.language_code = language_code;
        }

        public List<AlternativesBean> getAlternatives() {
            return alternatives;
        }

        public void setAlternatives(List<AlternativesBean> alternatives) {
            this.alternatives = alternatives;
        }

        public static class ResultEndTimeBean {
            /**
             * seconds : 10
             * nanos : 680000000
             */

            private int seconds;
            private int nanos;

            public int getSeconds() {
                return seconds;
            }

            public void setSeconds(int seconds) {
                this.seconds = seconds;
            }

            public int getNanos() {
                return nanos;
            }

            public void setNanos(int nanos) {
                this.nanos = nanos;
            }
        }

        public static class AlternativesBean {
            /**
             * transcript : 喂喂喂喂现在说话看一下中间有没有结果回来喂喂喂喂喂什么
             * confidence : 0.94319963
             */

            private String transcript;
            private double confidence;

            public String getTranscript() {
                return transcript;
            }

            public void setTranscript(String transcript) {
                this.transcript = transcript;
            }

            public double getConfidence() {
                return confidence;
            }

            public void setConfidence(double confidence) {
                this.confidence = confidence;
            }
        }
    }
}
