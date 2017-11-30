package com.vlvxing.app.model;

/**
 * Created by Administrator on 2017/9/21.
 */

public class PlaneBottonDialogThreeModel {
    private String title;
    private int position;
    public String code;
    private String price;
    private boolean bo;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isBo() {
        return bo;
    }

    public void setBo(boolean bo) {
        this.bo = bo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
