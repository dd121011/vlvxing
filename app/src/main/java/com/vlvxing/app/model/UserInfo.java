package com.vlvxing.app.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/19 0019.
 */

public class UserInfo implements Serializable {
    private String name;
    private String phone;
    private String address;
    private String card;

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserInfo(String name, String phone, String address, String card) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.card = card;
    }
}
