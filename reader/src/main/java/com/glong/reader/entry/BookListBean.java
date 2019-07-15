package com.glong.reader.entry;

import java.util.List;

public class BookListBean {

    /**
     * code : 200
     * msg : success
     * data : {"list":[{"id":3261,"name":"让她继续等着呗","readType":10,"wordNum":2526},{"id":3262,"name":"让她继续等着呗","readType":20,"wordNum":2562}]}
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
             * id : 3261
             * name : 让她继续等着呗
             * readType : 10
             * wordNum : 2526
             */

            private int id;
            private String name;
            private int readType;
            private int wordNum;

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

            public int getReadType() {
                return readType;
            }

            public void setReadType(int readType) {
                this.readType = readType;
            }

            public int getWordNum() {
                return wordNum;
            }

            public void setWordNum(int wordNum) {
                this.wordNum = wordNum;
            }
        }
    }
}
