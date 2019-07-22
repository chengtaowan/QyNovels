package com.jdhd.qynovels.module.personal;

import java.util.List;

public class PrizeRecodeBean {

    /**
     * code : 200
     * msg : 请求成功
     * time : 1563776362
     * data : {"list":[{"prize_name":"6 金币","create_time":"2019-07-22 14:18:57"},{"prize_name":"6 金币","create_time":"2019-07-22 14:19:19"}]}
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
             * prize_name : 6 金币
             * create_time : 2019-07-22 14:18:57
             */

            private String prize_name;
            private String create_time;

            public String getPrize_name() {
                return prize_name;
            }

            public void setPrize_name(String prize_name) {
                this.prize_name = prize_name;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }
        }
    }
}
