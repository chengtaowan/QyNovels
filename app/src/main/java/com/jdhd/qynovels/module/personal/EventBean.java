package com.jdhd.qynovels.module.personal;

import java.util.List;

public class EventBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * eventType : 事件类型 10系统事件 20按钮点击事件 30页面统计事件
         * eventStartTime : 1452365482
         * eventEndTime : 1452563251
         * operationType : 操作类型 10 打开/关闭页面 20关闭app
         * event : 事件内容 10 打开关闭app 20 我的-点击登录-登录页 30 红包--登录立即提现-登录页 40 书架-搜索页 50 书城-搜索页 60 福利-邀请好友-分享 70 福利-点击观看小视频 80 书城页面 90 福利页面 100 书架页面
         */

        private int eventType;
        private int eventStartTime;
        private int eventEndTime;
        private int operationType;
        private int event;



        public int getEventStartTime() {
            return eventStartTime;
        }

        public void setEventStartTime(int eventStartTime) {
            this.eventStartTime = eventStartTime;
        }

        public int getEventEndTime() {
            return eventEndTime;
        }

        public void setEventEndTime(int eventEndTime) {
            this.eventEndTime = eventEndTime;
        }


        public int getEventType() {
            return eventType;
        }

        public void setEventType(int eventType) {
            this.eventType = eventType;
        }

        public int getOperationType() {
            return operationType;
        }

        public void setOperationType(int operationType) {
            this.operationType = operationType;
        }

        public int getEvent() {
            return event;
        }

        public void setEvent(int event) {
            this.event = event;
        }
    }
}
