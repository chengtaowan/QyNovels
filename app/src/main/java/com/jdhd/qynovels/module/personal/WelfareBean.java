package com.jdhd.qynovels.module.personal;

import java.util.List;

public class WelfareBean {

    /**
     * code : 200
     * msg : 请求成功
     * time : 1561983114
     * data : {"is_sign":1,"readingBonus":[{"duration":1800,"award":30,"status":0},{"duration":3600,"award":60,"status":0},{"duration":5400,"award":90,"status":0},{"duration":10800,"award":180,"status":0}],"video_award":0}
     */

    private int code;
    private String msg;
    private String time;
    private DataBean data;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * is_sign : 1
         * readingBonus : [{"duration":1800,"award":30,"status":0},{"duration":3600,"award":60,"status":0},{"duration":5400,"award":90,"status":0},{"duration":10800,"award":180,"status":0}]
         * video_award : 0
         */

        private int is_sign;
        private int video_award;
        private List<ReadingBonusBean> readingBonus;

        public int getIs_sign() {
            return is_sign;
        }

        public void setIs_sign(int is_sign) {
            this.is_sign = is_sign;
        }

        public int getVideo_award() {
            return video_award;
        }

        public void setVideo_award(int video_award) {
            this.video_award = video_award;
        }

        public List<ReadingBonusBean> getReadingBonus() {
            return readingBonus;
        }

        public void setReadingBonus(List<ReadingBonusBean> readingBonus) {
            this.readingBonus = readingBonus;
        }

        public static class ReadingBonusBean {
            /**
             * duration : 1800
             * award : 30
             * status : 0
             */

            private int duration;
            private int award;
            private int status;

            public int getDuration() {
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
            }

            public int getAward() {
                return award;
            }

            public void setAward(int award) {
                this.award = award;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
