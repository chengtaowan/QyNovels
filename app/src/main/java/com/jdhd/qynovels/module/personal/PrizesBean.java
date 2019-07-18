package com.jdhd.qynovels.module.personal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PrizesBean {

    /**
     * code : 200
     * msg : 请求成功
     * time : 1562065532
     * data : {"0":{"list":{"prize":[{"name":"iPhoneXs max","img":"","level":1,"award_num":1,"is_gold":0,"color":"#FFF4D6"},{"name":"华为P30 Pro","img":"","level":2,"award_num":1,"is_gold":0,"color":"#FFFFFF"},{"name":"888 金币","img":"","level":3,"award_num":888,"is_gold":1,"color":"#FFF4D6"},{"name":"666 金币","img":"","level":4,"award_num":666,"is_gold":1,"color":"#FFFFFF"},{"name":"66 金币","img":"","level":5,"award_num":66,"is_gold":1,"color":"#FFF4D6"},{"name":"6 金币","img":"","level":6,"award_num":6,"is_gold":1,"color":"#FFFFFF"}]}},"balance":9817543,"game_num":2}
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
         * 0 : {"list":{"prize":[{"name":"iPhoneXs max","img":"","level":1,"award_num":1,"is_gold":0,"color":"#FFF4D6"},{"name":"华为P30 Pro","img":"","level":2,"award_num":1,"is_gold":0,"color":"#FFFFFF"},{"name":"888 金币","img":"","level":3,"award_num":888,"is_gold":1,"color":"#FFF4D6"},{"name":"666 金币","img":"","level":4,"award_num":666,"is_gold":1,"color":"#FFFFFF"},{"name":"66 金币","img":"","level":5,"award_num":66,"is_gold":1,"color":"#FFF4D6"},{"name":"6 金币","img":"","level":6,"award_num":6,"is_gold":1,"color":"#FFFFFF"}]}}
         * balance : 9817543
         * game_num : 2
         */

        @SerializedName("0")
        private _$0Bean _$0;
        private int balance;
        private int game_num;

        public _$0Bean get_$0() {
            return _$0;
        }

        public void set_$0(_$0Bean _$0) {
            this._$0 = _$0;
        }

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public int getGame_num() {
            return game_num;
        }

        public void setGame_num(int game_num) {
            this.game_num = game_num;
        }

        public static class _$0Bean {
            /**
             * list : {"prize":[{"name":"iPhoneXs max","img":"","level":1,"award_num":1,"is_gold":0,"color":"#FFF4D6"},{"name":"华为P30 Pro","img":"","level":2,"award_num":1,"is_gold":0,"color":"#FFFFFF"},{"name":"888 金币","img":"","level":3,"award_num":888,"is_gold":1,"color":"#FFF4D6"},{"name":"666 金币","img":"","level":4,"award_num":666,"is_gold":1,"color":"#FFFFFF"},{"name":"66 金币","img":"","level":5,"award_num":66,"is_gold":1,"color":"#FFF4D6"},{"name":"6 金币","img":"","level":6,"award_num":6,"is_gold":1,"color":"#FFFFFF"}]}
             */

            private ListBean list;

            public ListBean getList() {
                return list;
            }

            public void setList(ListBean list) {
                this.list = list;
            }

            public static class ListBean {
                private List<PrizeBean> prize;

                public List<PrizeBean> getPrize() {
                    return prize;
                }

                public void setPrize(List<PrizeBean> prize) {
                    this.prize = prize;
                }

                public static class PrizeBean {
                    /**
                     * name : iPhoneXs max
                     * img :
                     * level : 1
                     * award_num : 1
                     * is_gold : 0
                     * color : #FFF4D6
                     */

                    private String name;
                    private String img;
                    private int level;
                    private int award_num;
                    private int is_gold;
                    private String color;

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getImg() {
                        return img;
                    }

                    public void setImg(String img) {
                        this.img = img;
                    }

                    public int getLevel() {
                        return level;
                    }

                    public void setLevel(int level) {
                        this.level = level;
                    }

                    public int getAward_num() {
                        return award_num;
                    }

                    public void setAward_num(int award_num) {
                        this.award_num = award_num;
                    }

                    public int getIs_gold() {
                        return is_gold;
                    }

                    public void setIs_gold(int is_gold) {
                        this.is_gold = is_gold;
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
    }
}
