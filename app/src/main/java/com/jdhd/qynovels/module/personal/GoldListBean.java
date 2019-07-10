package com.jdhd.qynovels.module.personal;

import java.util.List;

public class GoldListBean {

    /**
     * code : 200
     * msg : 请求成功
     * time : 1562066108
     * data : {"list":[{"title":"金币奖励","award":"+66金币","time":"2019-07-02 15:07:37"},{"title":"每日阅读180分钟奖励","award":"+1800金币","time":"2019-06-28 16:56:56"},{"title":"注册奖励","award":"+10000金币","time":"2019-06-28 11:07:52"}]}
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
             * title : 金币奖励
             * award : +66金币
             * time : 2019-07-02 15:07:37
             */

            private String title;
            private String award;
            private String time;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getAward() {
                return award;
            }

            public void setAward(String award) {
                this.award = award;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }
    }
}
