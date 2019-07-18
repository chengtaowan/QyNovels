package com.jdhd.qynovels.module.personal;

import java.util.List;

public class ShareImgBean {

    /**
     * code : 200
     * msg : 请求成功
     * time : 1562146774
     * data : {"share_img":"http://192.168.1.199:19919/upload/shareImg/42/4239f0d4b8b1a6d0426a3fdef2f6832e.png","red_code":"I1GSO8I","rule":[{"sort":1,"content":"好友注册后您将获得10000金币奖励"},{"sort":2,"content":"邀请好友数量无上限，邀请越多奖励越多"}]}
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
         * share_img : http://192.168.1.199:19919/upload/shareImg/42/4239f0d4b8b1a6d0426a3fdef2f6832e.png
         * red_code : I1GSO8I
         * rule : [{"sort":1,"content":"好友注册后您将获得10000金币奖励"},{"sort":2,"content":"邀请好友数量无上限，邀请越多奖励越多"}]
         */

        private String share_img;
        private String red_code;
        private List<RuleBean> rule;

        public String getShare_img() {
            return share_img;
        }

        public void setShare_img(String share_img) {
            this.share_img = share_img;
        }

        public String getRed_code() {
            return red_code;
        }

        public void setRed_code(String red_code) {
            this.red_code = red_code;
        }

        public List<RuleBean> getRule() {
            return rule;
        }

        public void setRule(List<RuleBean> rule) {
            this.rule = rule;
        }

        public static class RuleBean {
            /**
             * sort : 1
             * content : 好友注册后您将获得10000金币奖励
             */

            private int sort;
            private String content;

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
