package com.vlvxing.app.model;

import java.io.Serializable;

/**
 * 去哪网购票乘客信息
 */

public class PlaneUserInfo implements Serializable {
    private Integer number;
    private String name;
    private String card;


    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
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



    public PlaneUserInfo(Integer number,String name, String card) {
        this.number = number;
        this.name = name;
        this.card = card;
    }
}
