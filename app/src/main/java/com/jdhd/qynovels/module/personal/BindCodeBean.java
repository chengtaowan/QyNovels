package com.jdhd.qynovels.module.personal;

public class BindCodeBean {

    /**
     * code : 200
     * msg : 请求成功
     * time : 1561976372
     * data : {"award":10000,"bind_show":0}
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
         * award : 10000
         * bind_show : 0
         */

        private int award;
        private int bind_show;

        public int getAward() {
            return award;
        }

        public void setAward(int award) {
            this.award = award;
        }

        public int getBind_show() {
            return bind_show;
        }

        public void setBind_show(int bind_show) {
            this.bind_show = bind_show;
        }
    }
}
