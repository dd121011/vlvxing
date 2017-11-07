package com.vlvxing.app.model.forum;

import java.util.ArrayList;
import java.util.List;

/**
 * 更多回复
 */
public class TopicLayerDetaisBean {

    private String name;//昵称
    private String date;//日期
    private String time;//时间
    private String imgUrl;//头像URL
    private String body;//帖子详情
    private int number;//层数
    private ArrayList<String> listUrl;//层主图片详情

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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ArrayList<String> getListUrl() {
        return listUrl;
    }

    public void setListUrl(ArrayList<String> listUrl) {
        this.listUrl = listUrl;
    }
}

