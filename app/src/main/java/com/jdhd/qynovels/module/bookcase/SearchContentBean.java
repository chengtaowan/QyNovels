package com.jdhd.qynovels.module.bookcase;

import java.util.List;

public class SearchContentBean {

    /**
     * code : 200
     * msg : 请求成功
     * time : 1562402580
     * data : {"list":[{"id":943,"name":"完美宠婚：禁爱总裁请节制","image":"http://www.hxtk.com/cover/18/77b1cced-656b-447d-8608-c9266ec77a71.jpg","intro":"\u201c风先生，你不会是弯的吧？\u201d某女托着下巴，一脸正经的样子，琥珀色的眸子忽闪忽闪。 某男将她逼到墙角，一个壁咚，嘴角勾起一抹邪魅的笑，\u201c叶小姐，这得试过才知道！\u201d 一次偶然，叶清悠闯入了风祁傲的世界。从此，他便成为她生命的主旋律；从此，他便惜她如命，宠她入骨。 \u201c风先生，我们离婚吧！\u201d叶清悠扔下��纸离婚书，带着肚子里的小包子逃了。 风祁傲最后悔的事便是放了叶清悠离开，这一次，他绝不会放开她的手了！ \u201c叶小姐，你拐了我的小包子，还想往哪走？\u201d 叶清悠红了眼。 \u201c叶小姐，作为你抛弃我的补偿，你得再给我生个小包子！\u201d风祁傲将她压在身下，在她耳边吹气道。 \u201c无耻！\u201d叶清悠红着脸娇嗔道...","author":"倾沫璃","grade":6.7,"finishStatus":10,"number":216919,"search":1959,"attention":1270,"hot":12081},{"id":3,"name":"穿越到完美大陆","image":"http://www.hxtk.com/cover/0/675.jpg","intro":"当2位好朋友在网吧玩网游的时候网吧外一道闪电经过。两个人的灵魂就穿越到了完美大陆！最后两位好朋友都到达了顶峰。最后他们...............","author":"阿布罗狄","grade":6.4,"finishStatus":10,"number":154574,"search":2309,"attention":877,"hot":3663}]}
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
             * id : 943
             * name : 完美宠婚：禁爱总裁请节制
             * image : http://www.hxtk.com/cover/18/77b1cced-656b-447d-8608-c9266ec77a71.jpg
             * intro : “风先生，你不会是弯的吧？”某女托着下巴，一脸正经的样子，琥珀色的眸子忽闪忽闪。 某男将她逼到墙角，一个壁咚，嘴角勾起一抹邪魅的笑，“叶小姐，这得试过才知道！” 一次偶然，叶清悠闯入了风祁傲的世界。从此，他便成为她生命的主旋律；从此，他便惜她如命，宠她入骨。 “风先生，我们离婚吧！”叶清悠扔下��纸离婚书，带着肚子里的小包子逃了。 风祁傲最后悔的事便是放了叶清悠离开，这一次，他绝不会放开她的手了！ “叶小姐，你拐了我的小包子，还想往哪走？” 叶清悠红了眼。 “叶小姐，作为你抛弃我的补偿，你得再给我生个小包子！”风祁傲将她压在身下，在她耳边吹气道。 “无耻！”叶清悠红着脸娇嗔道...
             * author : 倾沫璃
             * grade : 6.7
             * finishStatus : 10
             * number : 216919
             * search : 1959
             * attention : 1270
             * hot : 12081
             */

            private int bookId;
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
