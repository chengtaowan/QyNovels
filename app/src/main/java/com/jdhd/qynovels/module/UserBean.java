package com.jdhd.qynovels.module;

public class UserBean {

    /**
     * code : 200
     * msg : success
     * time : 1561369689
     * data : {"avatar":"","sex":"未知","nickname":"","uid":524531,"red_code":"QP8GUZ2","create_time":"1970-01-01 08:00:00","total_gold":10000,"balance":200000,"today_gold":10000,"read_time":0,"message_count":0,"invite_id":null,"money":20,"bind_show":0}
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
         * avatar :
         * sex : 未知
         * nickname :
         * uid : 524531
         * red_code : QP8GUZ2
         * create_time : 1970-01-01 08:00:00
         * total_gold : 10000
         * balance : 200000
         * today_gold : 10000
         * read_time : 0
         * message_count : 0
         * invite_id : null
         * money : 20
         * bind_show : 0
         */

        private String avatar;
        private String sex;
        private String nickname;
        private int uid;
        private String red_code;
        private String create_time;
        private int total_gold;
        private int balance;
        private int today_gold;
        private int read_time;
        private int message_count;
        private Object invite_id;
        private int money;
        private int bind_show;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getRed_code() {
            return red_code;
        }

        public void setRed_code(String red_code) {
            this.red_code = red_code;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getTotal_gold() {
            return total_gold;
        }

        public void setTotal_gold(int total_gold) {
            this.total_gold = total_gold;
        }

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public int getToday_gold() {
            return today_gold;
        }

        public void setToday_gold(int today_gold) {
            this.today_gold = today_gold;
        }

        public int getRead_time() {
            return read_time;
        }

        public void setRead_time(int read_time) {
            this.read_time = read_time;
        }

        public int getMessage_count() {
            return message_count;
        }

        public void setMessage_count(int message_count) {
            this.message_count = message_count;
        }

        public Object getInvite_id() {
            return invite_id;
        }

        public void setInvite_id(Object invite_id) {
            this.invite_id = invite_id;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public int getBind_show() {
            return bind_show;
        }

        public void setBind_show(int bind_show) {
            this.bind_show = bind_show;
        }
    }
}
