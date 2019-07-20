package com.jdhd.qynovels.module.bookshop;

import java.util.List;

public class ClassContentBean {

    /**
     * code : 200
     * msg : success
     * data : {"list":[{"bookId":365,"name":"阴阳合神","image":"http://www.hxtk.com/cover/0/376.jpg","author":"昊天之恋","intro":"天下是天下人的天下，是老百姓的天下。\r\n分久必合，合久必分。\r\n朝代的更替，新皇的出现，很正常的现象！但对于风之殇来说，自己要当皇帝，不是为了权力，而是为了亲人，爱人，他要保护他们，不让他们受到伤害\u2026\u2026\r\n身份地位的提升，同时实力也在提升，他又到达了另一个领域\u2014\u2014力量的领域！\r\n一切都是拜师之后才开始\u2026\u2026\r\n喜欢的朋友帮忙推荐推荐！！！谢谢大家的\r\n支持！","grade":6.65,"finishStatus":10,"number":1253658,"search":698,"attention":600,"hot":6584,"className":"玄幻","pageTime":1489998670000,"label":"契约"}]}
     */

    private int code;
    private String msg;
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
             * bookId : 365
             * name : 阴阳合神
             * image : http://www.hxtk.com/cover/0/376.jpg
             * author : 昊天之恋
             * intro : 天下是天下人的天下，是老百姓的天下。
             分久必合，合久必分。
             朝代的更替，新皇的出现，很正常的现象！但对于风之殇来说，自己要当皇帝，不是为了权力，而是为了亲人，爱人，他要保护他们，不让他们受到伤害……
             身份地位的提升，同时实力也在提升，他又到达了另一个领域——力量的领域！
             一切都是拜师之后才开始……
             喜欢的朋友帮忙推荐推荐！！！谢谢大家的
             支持！
             * grade : 6.65
             * finishStatus : 10
             * number : 1253658
             * search : 698
             * attention : 600
             * hot : 6584
             * className : 玄幻
             * pageTime : 1489998670000
             * label : 契约
             */

            private int bookId;
            private String name;
            private String image;
            private String author;
            private String intro;
            private double grade;
            private int finishStatus;
            private int number;
            private int search;
            private int attention;
            private int hot;
            private String className;
            private long pageTime;
            private String label;

            public int getBookId() {
                return bookId;
            }

            public void setBookId(int bookId) {
                this.bookId = bookId;
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

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
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

            public String getClassName() {
                return className;
            }

            public void setClassName(String className) {
                this.className = className;
            }

            public long getPageTime() {
                return pageTime;
            }

            public void setPageTime(long pageTime) {
                this.pageTime = pageTime;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }
        }
    }
}
