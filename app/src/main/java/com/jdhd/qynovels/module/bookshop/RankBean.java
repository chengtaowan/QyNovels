package com.jdhd.qynovels.module.bookshop;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class RankBean implements Parcelable {

    /**
     * code : 200
     * msg : 请求成功
     * time : 1562382037
     * data : {"list":[{"id":1,"name":"男生排行榜","child":[{"id":3,"name":"大热搜","des":"","updateTime":1561518652,"sort":1},{"id":4,"name":"完结榜","des":"","updateTime":1561518652,"sort":2},{"id":5,"name":"黑马榜","des":"","updateTime":1561518652,"sort":3},{"id":6,"name":"热搜榜","des":"","updateTime":1561518652,"sort":4}]},{"id":2,"name":"女生排行榜","child":[{"id":7,"name":"大热榜","des":"","updateTime":1561518652,"sort":1},{"id":8,"name":"完结榜","des":"","updateTime":1561518652,"sort":2},{"id":9,"name":"黑马榜","des":"","updateTime":1561518652,"sort":3},{"id":10,"name":"热搜榜","des":"","updateTime":1561518652,"sort":4}]}]}
     */

    private int code;
    private String msg;
    private String time;
    private DataBean data;

    protected RankBean(Parcel in) {
        code = in.readInt();
        msg = in.readString();
        time = in.readString();
    }

    public static final Creator<RankBean> CREATOR = new Creator<RankBean>() {
        @Override
        public RankBean createFromParcel(Parcel in) {
            return new RankBean(in);
        }

        @Override
        public RankBean[] newArray(int size) {
            return new RankBean[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(code);
        parcel.writeString(msg);
        parcel.writeString(time);
    }

    public static class DataBean implements Parcelable{
        private List<ListBean> list;

        protected DataBean(Parcel in) {
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
        }

        public static class ListBean implements Parcelable{
            /**
             * id : 1
             * name : 男生排行榜
             * child : [{"id":3,"name":"大热搜","des":"","updateTime":1561518652,"sort":1},{"id":4,"name":"完结榜","des":"","updateTime":1561518652,"sort":2},{"id":5,"name":"黑马榜","des":"","updateTime":1561518652,"sort":3},{"id":6,"name":"热搜榜","des":"","updateTime":1561518652,"sort":4}]
             */

            private int id;
            private String name;
            private List<ChildBean> child;

            protected ListBean(Parcel in) {
                id = in.readInt();
                name = in.readString();
            }

            public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
                @Override
                public ListBean createFromParcel(Parcel in) {
                    return new ListBean(in);
                }

                @Override
                public ListBean[] newArray(int size) {
                    return new ListBean[size];
                }
            };

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

            public List<ChildBean> getChild() {
                return child;
            }

            public void setChild(List<ChildBean> child) {
                this.child = child;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeInt(id);
                parcel.writeString(name);
            }

            public static class ChildBean implements Parcelable{
                /**
                 * id : 3
                 * name : 大热搜
                 * des :
                 * updateTime : 1561518652
                 * sort : 1
                 */

                private int id;
                private String name;
                private String des;
                private int updateTime;
                private int sort;

                protected ChildBean(Parcel in) {
                    id = in.readInt();
                    name = in.readString();
                    des = in.readString();
                    updateTime = in.readInt();
                    sort = in.readInt();
                }

                public static final Creator<ChildBean> CREATOR = new Creator<ChildBean>() {
                    @Override
                    public ChildBean createFromParcel(Parcel in) {
                        return new ChildBean(in);
                    }

                    @Override
                    public ChildBean[] newArray(int size) {
                        return new ChildBean[size];
                    }
                };

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

                public int getUpdateTime() {
                    return updateTime;
                }

                public void setUpdateTime(int updateTime) {
                    this.updateTime = updateTime;
                }

                public int getSort() {
                    return sort;
                }

                public void setSort(int sort) {
                    this.sort = sort;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel parcel, int i) {
                    parcel.writeInt(id);
                    parcel.writeString(name);
                    parcel.writeString(des);
                    parcel.writeInt(updateTime);
                    parcel.writeInt(sort);
                }
            }
        }
    }
}
