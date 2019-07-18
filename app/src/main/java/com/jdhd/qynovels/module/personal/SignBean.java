package com.jdhd.qynovels.module.personal;

public class SignBean {

    /**
     * code : 200
     * msg : 请求成功
     * time : 1561976372
     * data : {"award":70,"sign_num":11}
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
         * award : 70
         * sign_num : 11
         */

        private int award;
        private int sign_num;

        public int getAward() {
            return award;
        }

        public void setAward(int award) {
            this.award = award;
        }

        public int getSign_num() {
            return sign_num;
        }

        public void setSign_num(int sign_num) {
            this.sign_num = sign_num;
        }
    }
}
