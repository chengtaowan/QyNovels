package com.jdhd.qynovels.module.personal;

import java.util.List;

public class DrawListBean {

    /**
     * code : 200
     * msg : success
     * time : 1561020113
     * data : {"list":[{"title":"金币提现 1元","status":"成功","time":"2019-05-05 15:59:59","remark":"","color":"#F1F3F5"},{"title":"金币提现 1000元","status":"打款失败","time":"2020-05-05 15:59:59","remark":"微信账户未实名认证，无法付款","color":"#FF6262"},{"title":"金币提现 1000元","status":"审核中","time":"2021-05-05 15:59:59","remark":"","color":"#FFC450"}]}
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
             * title : 金币提现 1元
             * status : 成功
             * time : 2019-05-05 15:59:59
             * remark :
             * color : #F1F3F5
             */

            private String title;
            private String status;
            private String time;
            private String remark;
            private String color;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }
        }
    }
}
