package com.jdhd.qynovels.entry;

public class ReadAwardBean {

    /**
     * code : 200
     * msg : success
     * time : 1561026707
     * data : {"award":1}
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
         * award : 1
         */

        private int award;

        public int getAward() {
            return award;
        }

        public void setAward(int award) {
            this.award = award;
        }
    }
}
