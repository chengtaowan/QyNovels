package com.jdhd.qynovels.module.personal;

public class SignInVideoBean {

    /**
     * code : 200
     * msg : 请求成功
     * time : 1561976372
     * data : {"award":100}
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
         * award : 100
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
