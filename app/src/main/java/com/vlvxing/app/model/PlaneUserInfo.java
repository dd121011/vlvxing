package com.vlvxing.app.model;

import java.io.Serializable;

/**
 * 去哪网购票乘客信息
 */

public class PlaneUserInfo implements Serializable {
    private int number;
    private String name;
    private String card;


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

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



    public PlaneUserInfo(int number,String name, String card) {
        this.number = number;
        this.name = name;
        this.card = card;
    }
}
