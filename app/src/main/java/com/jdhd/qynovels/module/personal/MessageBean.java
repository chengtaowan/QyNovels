package com.jdhd.qynovels.module.personal;

import java.util.List;

public class MessageBean {

    /**
     * code : 200
     * msg : 请求成功
     * time : 1562066067
     * data : {"list":[{"content":"嗨嗨嗨，醒醒，大清亡了你知道吗？","title":"【大清亡了】","is_read":10,"time":1561443643},{"content":"谁说我大清亡了","title":"【大清亡了】","is_read":10,"time":1561443031},{"content":"大清亡了？不可能","title":"【大清亡了】","is_read":10,"time":1561442899},{"content":"什么？李自成攻破北京了？","title":"【大清亡了】","is_read":10,"time":1561442843},{"content":"什么？大清亡了？","title":"【大清亡了】","is_read":10,"time":1561442643}]}
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
             * content : 嗨嗨嗨，醒醒，大清亡了你知道吗？
             * title : 【大清亡了】
             * is_read : 10
             * time : 1561443643
             */

            private String content;
            private String title;
            private int is_read;
            private int time;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getIs_read() {
                return is_read;
            }

            public void setIs_read(int is_read) {
                this.is_read = is_read;
            }

            public int getTime() {
                return time;
            }

            public void setTime(int time) {
                this.time = time;
            }
        }
    }
}
