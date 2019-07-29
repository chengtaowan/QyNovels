package com.jdhd.qynovels.module.personal;

import java.util.List;

public class SignSetingBean {

    /**
     * code : 200
     * msg : 请求成功
     * time : 1564042035
     * data : {"signNum":1,"is_sign":2,"double_award":10,"rule":[{"rule":"第一天签到给10金币,第二天签到给20金币,第三天签到给30金币,第四天签到给40金币,第五天签到给50金币,第六天签到给60金币,第七天签到给70金币,第八天签到给100金币,...........,第n天给100金币","sort":1},{"rule":"采用连续签到，如果其中一天没签，则从第一天重新开始","sort":2},{"rule":"看小视频可获得双倍奖励","sort":3}],"signData":[{"date":"今天","is_sign":2,"award":10},{"date":"07.26","is_sign":0,"award":20},{"date":"07.27","is_sign":0,"award":30},{"date":"07.28","is_sign":0,"award":40},{"date":"07.29","is_sign":0,"award":50},{"date":"07.30","is_sign":0,"award":60},{"date":"07.31","is_sign":0,"award":70}]}
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
         * signNum : 1
         * is_sign : 2
         * double_award : 10
         * rule : [{"rule":"第一天签到给10金币,第二天签到给20金币,第三天签到给30金币,第四天签到给40金币,第五天签到给50金币,第六天签到给60金币,第七天签到给70金币,第八天签到给100金币,...........,第n天给100金币","sort":1},{"rule":"采用连续签到，如果其中一天没签，则从第一天重新开始","sort":2},{"rule":"看小视频可获得双倍奖励","sort":3}]
         * signData : [{"date":"今天","is_sign":2,"award":10},{"date":"07.26","is_sign":0,"award":20},{"date":"07.27","is_sign":0,"award":30},{"date":"07.28","is_sign":0,"award":40},{"date":"07.29","is_sign":0,"award":50},{"date":"07.30","is_sign":0,"award":60},{"date":"07.31","is_sign":0,"award":70}]
         */

        private int signNum;
        private int is_sign;
        private int double_award;
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

        public int getDouble_award() {
            return double_award;
        }

        public void setDouble_award(int double_award) {
            this.double_award = double_award;
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
             * rule : 第一天签到给10金币,第二天签到给20金币,第三天签到给30金币,第四天签到给40金币,第五天签到给50金币,第六天签到给60金币,第七天签到给70金币,第八天签到给100金币,...........,第n天给100金币
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
             * date : 今天
             * is_sign : 2
             * award : 10
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
