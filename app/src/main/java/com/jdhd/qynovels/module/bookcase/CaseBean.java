package com.jdhd.qynovels.module.bookcase;

import java.util.List;

public class CaseBean {


    /**
     * code : 200
     * msg : success
     * data : {"list":[{"id":365,"name":"阴阳合神","image":"http://www.hxtk.com/cover/0/376.jpg","author":"昊天之恋","readContent":"天下是天下人的天下，是老百姓的天下","readStatus":10,"bookStatus":20,"bookId":256,"backlistPercent":3,"lastTime":14425154521,"backlistId":123}],"hot":{"bookId":617,"name":"风云少年","image":"http://cdn.taxiaoshuo.com/FoAeDznDs-PtrYPU9rqJ7p3pA_8I.jpg","intro":"龙跃飞是一个灵力大陆上的少年，在过去的时候曾经也是一个风云的人物，但是因为个人的放荡不羁，得罪了一些更加有实力的人，被人差不多打成了废物\u2026\u2026 自那以后，他开始研究了医药学，却也从此开始了一个新的人生\u2026\u2026","author":"星辰"}}
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
        /**
         * list : [{"id":365,"name":"阴阳合神","image":"http://www.hxtk.com/cover/0/376.jpg","author":"昊天之恋","readContent":"天下是天下人的天下，是老百姓的天下","readStatus":10,"bookStatus":20,"bookId":256,"backlistPercent":3,"lastTime":14425154521,"backlistId":123}]
         * hot : {"bookId":617,"name":"风云少年","image":"http://cdn.taxiaoshuo.com/FoAeDznDs-PtrYPU9rqJ7p3pA_8I.jpg","intro":"龙跃飞是一个灵力大陆上的少年，在过去的时候曾经也是一个风云的人物，但是因为个人的放荡不羁，得罪了一些更加有实力的人，被人差不多打成了废物\u2026\u2026 自那以后，他开始研究了医药学，却也从此开始了一个新的人生\u2026\u2026","author":"星辰"}
         */

        private HotBean hot;
        private List<ListBean> list;

        public HotBean getHot() {
            return hot;
        }

        public void setHot(HotBean hot) {
            this.hot = hot;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class HotBean {
            /**
             * bookId : 617
             * name : 风云少年
             * image : http://cdn.taxiaoshuo.com/FoAeDznDs-PtrYPU9rqJ7p3pA_8I.jpg
             * intro : 龙跃飞是一个灵力大陆上的少年，在过去的时候曾经也是一个风云的人物，但是因为个人的放荡不羁，得罪了一些更加有实力的人，被人差不多打成了废物…… 自那以后，他开始研究了医药学，却也从此开始了一个新的人生……
             * author : 星辰
             */

            private int bookId;
            private String name;
            private String image;
            private String intro;
            private String author;

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

            @Override
            public String toString() {
                return "HotBean{" +
                        "bookId=" + bookId +
                        ", name='" + name + '\'' +
                        ", image='" + image + '\'' +
                        ", intro='" + intro + '\'' +
                        ", author='" + author + '\'' +
                        '}';
            }
        }

        public static class ListBean {
            /**
             * id : 365
             * name : 阴阳合神
             * image : http://www.hxtk.com/cover/0/376.jpg
             * author : 昊天之恋
             * readContent : 天下是天下人的天下，是老百姓的天下
             * readStatus : 10
             * bookStatus : 20
             * bookId : 256
             * backlistPercent : 3
             * lastTime : 14425154521
             * backlistId : 123
             */

            private int id;
            private String name;
            private String image;
            private String author;
            private String readContent;
            private int readStatus;
            private int bookStatus;
            private int bookId;
            private int backlistPercent;
            private long lastTime;
            private int backlistId;

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

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public String getReadContent() {
                return readContent;
            }

            public void setReadContent(String readContent) {
                this.readContent = readContent;
            }

            public int getReadStatus() {
                return readStatus;
            }

            public void setReadStatus(int readStatus) {
                this.readStatus = readStatus;
            }

            public int getBookStatus() {
                return bookStatus;
            }

            public void setBookStatus(int bookStatus) {
                this.bookStatus = bookStatus;
            }

            public int getBookId() {
                return bookId;
            }

            public void setBookId(int bookId) {
                this.bookId = bookId;
            }

            public int getBacklistPercent() {
                return backlistPercent;
            }

            public void setBacklistPercent(int backlistPercent) {
                this.backlistPercent = backlistPercent;
            }

            public long getLastTime() {
                return lastTime;
            }

            public void setLastTime(long lastTime) {
                this.lastTime = lastTime;
            }

            public int getBacklistId() {
                return backlistId;
            }

            public void setBacklistId(int backlistId) {
                this.backlistId = backlistId;
            }
        }
    }
}
