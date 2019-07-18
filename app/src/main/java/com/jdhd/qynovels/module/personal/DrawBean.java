package com.jdhd.qynovels.module.personal;

public class DrawBean {

    /**
     * code : 200
     * msg : 请求成功
     * time : 1562051256
     * data : {"level":5,"award":66}
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
         * level : 5
         * award : 66
         */

        private int level;
        private int award;

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getAward() {
            return award;
        }

        public void setAward(int award) {
            this.award = award;
        }
    }
}
