package com.vlvxing.app.model.plane;

/**
 * 机票订单
 */
public class PlaneOrderBean {
    private String id;//订单id
    private int state;//订单状态
    private String arrCity;//到达城市
    private String dptCity;//出发城市
    private int price;//价钱
    private String time;//时间
    private String date;//日期

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getArrCity() {
        return arrCity;
    }

    public void setArrCity(String arrCity) {
        this.arrCity = arrCity;
    }

    public String getDptCity() {
        return dptCity;
    }

    public void setDptCity(String dptCity) {
        this.dptCity = dptCity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
