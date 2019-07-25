package com.jdhd.qynovels.module.personal;

public class FunctionBean {
    String functionName;
    String path;
    String title;
    String dataPath;
    String reqName;
    String code;
    String type;
    String scene;
    String share_img;
    reqParameter reqParameter;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getShare_img() {
        return share_img;
    }

    public void setShare_img(String share_img) {
        this.share_img = share_img;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReqName() {
        return reqName;
    }

    public void setReqName(String reqName) {
        this.reqName = reqName;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public FunctionBean.reqParameter getReqParameter() {
        return reqParameter;
    }

    public void setReqParameter(FunctionBean.reqParameter reqParameter) {
        this.reqParameter = reqParameter;
    }

    public class reqParameter{
        String game_name;
        String game_num;
        String page;
        String limit;

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public String getLimit() {
            return limit;
        }

        public void setLimit(String limit) {
            this.limit = limit;
        }

        public String getGame_num() {
            return game_num;
        }

        public void setGame_num(String game_num) {
            this.game_num = game_num;
        }

        public String getGame_name() {
            return game_name;
        }

        public void setGame_name(String game_name) {
            this.game_name = game_name;
        }
    }




    public FunctionBean(String functionName) {
        this.functionName = functionName;
    }
}
