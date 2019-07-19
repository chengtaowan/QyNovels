package com.jdhd.qynovels.module.personal;

import java.util.List;

public class ShareListBean {

    /**
     * code : 200
     * msg : 请求成功
     * time : 1563343576
     * data : {"list":[{"avatar":"http://api.damobi.cn/static/image/avatar.png","nickname":"趣阅用户：524521"},{"avatar":"http://api.damobi.cn/upload/avatar/68/68933f6e742aa94d8d384de0e7c9090d.png","nickname":"趣阅"},{"avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIMd1TXqbrtus0p2VJutJdOySeks8RkGsFgGllwS1qghYibGxvMttxicUIH2mQWLmDwlwMVTsARonicw/132","nickname":"灯泡先生"},{"avatar":"http://api.damobi.cn/static/image/avatar.png","nickname":"趣阅用户：524530"},{"avatar":"https://thirdwx.qlogo.cn/mmopen/vi_32/Q3auHgzwzM5pnicS49CLKko4fvicRhlfkcvs5sTxGGYyoCgHS0MO9icE2oLibTPJGUd15XQh4SsShlh9lYBMyQmVfA/132","nickname":"如雪"},{"avatar":"http://api.damobi.cn/static/image/avatar.png","nickname":"趣阅用户：524528"},{"avatar":"http://api.damobi.cn/static/image/avatar.png","nickname":"趣阅用户：524529"}]}
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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * avatar : http://api.damobi.cn/static/image/avatar.png
             * nickname : 趣阅用户：524521
             */

            private String avatar;
            private String nickname;

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }
        }
    }
}
