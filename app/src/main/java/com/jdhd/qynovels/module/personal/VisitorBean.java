package com.jdhd.qynovels.module.personal;

public class VisitorBean {


    /**
     * code : 200
     * msg : 请求成功
     * time : 1567404308
     * data : {"token":"7e204fbc7671e3a7de8e2f290cf5c13d","past_due":1568009109,"is_login":1}
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
         * token : 7e204fbc7671e3a7de8e2f290cf5c13d
         * past_due : 1568009109
         * is_login : 1
         */

        private String token;
        private int past_due;
        private int is_login;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getPast_due() {
            return past_due;
        }

        public void setPast_due(int past_due) {
            this.past_due = past_due;
        }

        public int getIs_login() {
            return is_login;
        }

        public void setIs_login(int is_login) {
            this.is_login = is_login;
        }
    }
}
