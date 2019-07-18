package com.jdhd.qynovels.module.personal;

import java.util.List;

public class SignSetingBean {

    /**
     * code : 200
     * msg : 请求成功
     * time : 1562066227
     * data : {"signNum":11,"is_sign":0,"rule":[{"rule":"规则1","sort":1},{"rule":"规则2","sort":2},{"rule":"规则3","sort":3},{"rule":"规则4","sort":4}],"signData":[{"date":"06.29","is_sign":1,"award":70},{"date":"06.30","is_sign":1,"award":70},{"date":"07.01","is_sign":1,"award":70},{"date":"今天","is_sign":0,"award":70},{"date":"07.03","is_sign":0,"award":70},{"date":"07.04","is_sign":0,"award":70},{"date":"07.05","is_sign":0,"award":70}]}
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
         * signNum : 11
         * is_sign : 0
         * rule : [{"rule":"规则1","sort":1},{"rule":"规则2","sort":2},{"rule":"规则3","sort":3},{"rule":"规则4","sort":4}]
         * signData : [{"date":"06.29","is_sign":1,"award":70},{"date":"06.30","is_sign":1,"award":70},{"date":"07.01","is_sign":1,"award":70},{"date":"今天","is_sign":0,"award":70},{"date":"07.03","is_sign":0,"award":70},{"date":"07.04","is_sign":0,"award":70},{"date":"07.05","is_sign":0,"award":70}]
         */

        private int signNum;
        private int is_sign;
        private List<RuleBean> rule;
        private List<SignDataBean> signData;

        public int getSignNum() {
            return signNum;
        }

        public void setSignNum(int signNum) {
            this.signNum = signNum;
        }

        public int getIs_sign() {
            return is_sign;
        }

        public void setIs_sign(int is_sign) {
            this.is_sign = is_sign;
        }

        public List<RuleBean> getRule() {
            return rule;
        }

        public void setRule(List<RuleBean> rule) {
            this.rule = rule;
        }

        public List<SignDataBean> getSignData() {
            return signData;
        }

        public void setSignData(List<SignDataBean> signData) {
            this.signData = signData;
        }

        public static class RuleBean {
            /**
             * rule : 规则1
             * sort : 1
             */

            private String rule;
            private int sort;

            public String getRule() {
                return rule;
            }

            public void setRule(String rule) {
                this.rule = rule;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }
        }

        public static class SignDataBean {
            /**
             * date : 06.29
             * is_sign : 1
             * award : 70
             */

            private String date;
            private int is_sign;
            private int award;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public int getIs_sign() {
                return is_sign;
            }

            public void setIs_sign(int is_sign) {
                this.is_sign = is_sign;
            }

            public int getAward() {
                return award;
            }

            public void setAward(int award) {
                this.award = award;
            }
        }
    }
}
