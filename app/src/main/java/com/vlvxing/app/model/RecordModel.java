package com.vlvxing.app.model;

import android.support.v4.media.RatingCompat;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/12 0012.
 */

public class RecordModel implements Serializable{
    //    途中点 1 lng：经度  2 lat：纬度 3 picUrl：图片url,4. videoUrl：视频url ，
//            5 time：标注点保存时间 6 pathName ：途中点名称 7. address ：地图获取的地点名称
    private double lng;
    private double lat;
    private String picUrl;
    private String coverUrl;
    private String videoUrl;
    private String time;
    private String pathName;
    private String address;

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public RecordModel(double lng, double lat, String picUrl,String coverUrl, String videoUrl, String time, String pathName, String address) {
        this.lng = lng;
        this.lat = lat;
        this.picUrl = picUrl;
        this.coverUrl=coverUrl;
        this.videoUrl = videoUrl;
        this.time = time;
        this.pathName = pathName;
        this.address = address;
    }
}
