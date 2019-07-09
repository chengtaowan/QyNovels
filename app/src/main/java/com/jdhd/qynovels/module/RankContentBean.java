package com.jdhd.qynovels.module;

import java.util.List;

public class RankContentBean {

    /**
     * code : 200
     * msg : 请求成功
     * time : 1562396232
     * data : {"list":[{"id":1,"name":"阴阳合神","image":"http://www.hxtk.com/cover/0/376.jpg","intro":"天下是天下人的天下，是老百姓的天下。 分久必合，合久必分。 朝代的更替，新皇的出现，很正常的现象！但对于风之殇来说，自己要当皇帝，不是为了权力，而是为了亲人，爱人，他要保护他们，不让他们受到伤害\u2026\u2026 身份地位的提升，同时实力也在提升，他又到达了另一个领域\u2014\u2014力量的领域！ 一切都是拜师之后才开始\u2026\u2026 喜欢的朋友帮忙推荐推荐！！！谢谢���家的 支持！","author":"昊天之恋","grade":8.7,"finishStatus":20,"number":147691,"search":2428,"attention":1335,"hot":8169}]}
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
             * id : 1
             * name : 阴阳合神
             * image : http://www.hxtk.com/cover/0/376.jpg
             * intro : 天下是天下人的天下，是老百姓的天下。 分久必合，合久必分。 朝代的更替，新皇的出现，很正常的现象！但对于风之殇来说，自己要当皇帝，不是为了权力，而是为了亲人，爱人，他要保护他们，不让他们受到伤害…… 身份地位的提升，同时实力也在提升，他又到达了另一个领域——力量的领域！ 一切都是拜师之后才开始…… 喜欢的朋友帮忙推荐推荐！！！谢谢���家的 支持！
             * author : 昊天之恋
             * grade : 8.7
             * finishStatus : 20
             * number : 147691
             * search : 2428
             * attention : 1335
             * hot : 8169
             */

            private int id;
            private String name;
            private String image;
            private String intro;
            private String author;
            private double grade;
            private int finishStatus;
            private int number;
            private int search;
            private int attention;
            private int hot;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public double getGrade() {
                return grade;
            }

            public void setGrade(double grade) {
                this.grade = grade;
            }

            public int getFinishStatus() {
                return finishStatus;
            }

            public void setFinishStatus(int finishStatus) {
                this.finishStatus = finishStatus;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public int getSearch() {
                return search;
            }

            public void setSearch(int search) {
                this.search = search;
            }

            public int getAttention() {
                return attention;
            }

            public void setAttention(int attention) {
                this.attention = attention;
            }

            public int getHot() {
                return hot;
            }

            public void setHot(int hot) {
                this.hot = hot;
            }
        }
    }
}
