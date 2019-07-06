package com.jdhd.qynovels.module;

import java.util.List;

public class CaseBean {

    /**
     * code : 200
     * msg : success
     * data : {"list":[{"id":365,"name":"阴阳合神","image":"http://www.hxtk.com/cover/0/376.jpg","author":"昊天之恋","readContent":"天下是天下人的天下，是老百姓的天下","readStatus":10,"bookStatus":20,"bookId":256,"pageNum":3}]}
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
             * id : 365
             * name : 阴阳合神
             * image : http://www.hxtk.com/cover/0/376.jpg
             * author : 昊天之恋
             * readContent : 天下是天下人的天下，是老百姓的天下
             * readStatus : 10
             * bookStatus : 20
             * bookId : 256
             * pageNum : 3
             */

            private int id;
            private String name;
            private String image;
            private String author;
            private String readContent;
            private int readStatus;
            private int bookStatus;
            private int bookId;
            private int pageNum;

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

            public int getPageNum() {
                return pageNum;
            }

            public void setPageNum(int pageNum) {
                this.pageNum = pageNum;
            }
        }
    }
}
