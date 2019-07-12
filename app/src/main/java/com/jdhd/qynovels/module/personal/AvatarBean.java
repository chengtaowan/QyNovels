package com.jdhd.qynovels.module.personal;

public class AvatarBean {

    /**
     * code : 200
     * msg : success
     * time : 1561433701
     * data : {"avatar":"http://192.168.1.199:19919/upload/avatar/30/30ce7851032bc4e7331c4a7d2558517f.jpeg"}
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
         * avatar : http://192.168.1.199:19919/upload/avatar/30/30ce7851032bc4e7331c4a7d2558517f.jpeg
         */

        private String avatar;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}
