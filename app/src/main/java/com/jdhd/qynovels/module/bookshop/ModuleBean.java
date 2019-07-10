package com.jdhd.qynovels.module.bookshop;

import java.util.List;

public class ModuleBean {

    /**
     * code : 200
     * msg : success
     * data : {"list":[{"id":1,"moduleName":"精选"},{"id":2,"moduleName":"男生"},{"id":3,"moduleName":"男生完结"},{"id":1,"moduleName":"女生完结"},{"id":3,"moduleName":"男生更新"},{"id":1,"moduleName":"女生更新"}]}
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
             * id : 1
             * moduleName : 精选
             */

            private int id;
            private String moduleName;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getModuleName() {
                return moduleName;
            }

            public void setModuleName(String moduleName) {
                this.moduleName = moduleName;
            }
        }
    }
}
