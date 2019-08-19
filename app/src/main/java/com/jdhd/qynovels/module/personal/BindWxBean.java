package com.jdhd.qynovels.module.personal;

public class BindWxBean {

    /**
     * code : 200
     * msg : 绑定成功
     * time : 1562413484
     * data : {"wx_name":"微信昵称","nickname":"给我","bind_wx":1}
     */

    private int code;
    private String msg;
    private int time;
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

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
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
         * wx_name : 微信昵称
         * nickname : 给我
         * bind_wx : 1
         */

        private String wx_name;
        private String nickname;
        private int bind_wx;

        public String getWx_name() {
            return wx_name;
        }

        public void setWx_name(String wx_name) {
            this.wx_name = wx_name;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getBind_wx() {
            return bind_wx;
        }

        public void setBind_wx(int bind_wx) {
            this.bind_wx = bind_wx;
        }
    }
}
