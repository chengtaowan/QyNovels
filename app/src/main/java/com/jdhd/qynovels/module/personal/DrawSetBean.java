package com.jdhd.qynovels.module.personal;

import java.util.List;

public class DrawSetBean {

    /**
     * code : 200
     * msg : 请求成功
     * time : 1561615640
     * data : {"setting":[{"money":10,"gold":100000},{"money":15,"gold":150000},{"money":20,"gold":200000},{"money":25,"gold":250000},{"money":30,"gold":300000},{"money":50,"gold":500000}],"rule":[{"sort":1,"content":"小说千万本"},{"sort":2,"content":"免费第一条"},{"sort":3,"content":"看书看盗版"},{"sort":4,"content":"越看越来劲"}]}
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
        private List<SettingBean> setting;
        private List<RuleBean> rule;

        public List<SettingBean> getSetting() {
            return setting;
        }

        public void setSetting(List<SettingBean> setting) {
            this.setting = setting;
        }

        public List<RuleBean> getRule() {
            return rule;
        }

        public void setRule(List<RuleBean> rule) {
            this.rule = rule;
        }

        public static class SettingBean {
            /**
             * money : 10
             * gold : 100000
             */

            private int money;
            private int gold;

            public int getMoney() {
                return money;
            }

            public void setMoney(int money) {
                this.money = money;
            }

            public int getGold() {
                return gold;
            }

            public void setGold(int gold) {
                this.gold = gold;
            }
        }

        public static class RuleBean {
            /**
             * sort : 1
             * content : 小说千万本
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
