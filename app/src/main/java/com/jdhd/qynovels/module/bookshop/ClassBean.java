package com.jdhd.qynovels.module.bookshop;

import java.util.List;

public class ClassBean {

    /**
     * code : 200
     * msg : 请求成功
     * time : 1562399521
     * data : {"list":[{"id":1,"name":"男生","sort":1,"child":[{"id":3,"name":"现代都市","des":"都市生活\u2022都市百味\u2022异术超能","icon":"http://www.hxtk.com/cover/20/20914.jpg","sort":1,"bookNum":0}]}]}
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
             * name : 男生
             * sort : 1
             * child : [{"id":3,"name":"现代都市","des":"都市生活\u2022都市百味\u2022异术超能","icon":"http://www.hxtk.com/cover/20/20914.jpg","sort":1,"bookNum":0}]
             */

            private int id;
            private String name;
            private int sort;
            private List<ChildBean> child;

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

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public List<ChildBean> getChild() {
                return child;
            }

            public void setChild(List<ChildBean> child) {
                this.child = child;
            }

            public static class ChildBean {
                /**
                 * id : 3
                 * name : 现代都市
                 * des : 都市生活•都市百味•异术超能
                 * icon : http://www.hxtk.com/cover/20/20914.jpg
                 * sort : 1
                 * bookNum : 0
                 */

                private int id;
                private String name;
                private String des;
                private String icon;
                private int sort;
                private int bookNum;

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

                public String getDes() {
                    return des;
                }

                public void setDes(String des) {
                    this.des = des;
                }

                public String getIcon() {
                    return icon;
                }

                public void setIcon(String icon) {
                    this.icon = icon;
                }

                public int getSort() {
                    return sort;
                }

                public void setSort(int sort) {
                    this.sort = sort;
                }

                public int getBookNum() {
                    return bookNum;
                }

                public void setBookNum(int bookNum) {
                    this.bookNum = bookNum;
                }
            }
        }
    }
}
