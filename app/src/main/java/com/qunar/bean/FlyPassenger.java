package com.qunar.bean;

import java.io.Serializable;

/**
 * Created by mingchao on 2017/11/10.
 */

public class FlyPassenger implements Serializable{
    private Long passengerid;

    private String name;

    private int agetype;

    private String cardtype;

    private String cardno;

    private int sex;

    private String birthday;

    private String orderno;

    public Long getPassengerid() {
        return passengerid;
    }

    public void setPassengerid(Long passengerid) {
        this.passengerid = passengerid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAgetype() {
        return agetype;
    }

    public void setAgetype(int agetype) {
        this.agetype = agetype;
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }
}
