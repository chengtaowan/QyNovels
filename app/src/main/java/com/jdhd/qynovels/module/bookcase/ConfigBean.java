package com.jdhd.qynovels.module.bookcase;

import java.util.List;

public class ConfigBean {

    /**
     * code : 200
     * msg : 请求成功
     * time : 1562036755
     * data : {"list":[{"location":10,"status":20,"showTime":5},{"location":20,"status":20,"intervalSpace":3},{"location":30,"status":20},{"location":40,"status":20},{"location":50,"status":20,"intervalSpace":10}]}
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
             * location : 10
             * status : 20
             * showTime : 5
             * intervalSpace : 3
             */

            private int location;
            private int status;
            private int showTime;
            private int intervalSpace;

            public int getLocation() {
                return location;
            }

            public void setLocation(int location) {
                this.location = location;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getShowTime() {
                return showTime;
            }

            public void setShowTime(int showTime) {
                this.showTime = showTime;
            }

            public int getIntervalSpace() {
                return intervalSpace;
            }

            public void setIntervalSpace(int intervalSpace) {
                this.intervalSpace = intervalSpace;
            }
        }
    }
}
