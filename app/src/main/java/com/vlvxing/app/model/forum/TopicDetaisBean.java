package com.vlvxing.app.model.forum;

import java.util.List;

/**
 * 话题详情  层主评论(内含详情)
 */
public class TopicDetaisBean {

    private String name;//昵称
    private String date;//日期
    private String time;//时间
    private String imgUrl;//头像URL
    private String body;//帖子详情
    private List<Body> messageList;//回复列表

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<Body> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Body> messageList) {
        this.messageList = messageList;
    }


    public static class Body{

        public String mName;
        public String mBody;
        public String mDate;
        public String mTime;


        public String getmName() {
            return mName;
        }

        public void setmName(String mName) {
            this.mName = mName;
        }

        public String getmBody() {
            return mBody;
        }

        public void setmBody(String mBody) {
            this.mBody = mBody;
        }

        public String getmDate() {
            return mDate;
        }

        public void setmDate(String mDate) {
            this.mDate = mDate;
        }

        public String getmTime() {
            return mTime;
        }

        public void setmTime(String mTime) {
            this.mTime = mTime;
        }
    }



}

