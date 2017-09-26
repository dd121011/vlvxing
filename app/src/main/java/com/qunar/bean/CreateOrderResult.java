package com.qunar.bean;

/**
 * Created by wjjunjjun.wang on 2017/8/4.
 */
public class CreateOrderResult {

    private int id;
    private int noPayAmount;
    private String orderNo;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNoPayAmount() {
        return noPayAmount;
    }

    public void setNoPayAmount(int noPayAmount) {
        this.noPayAmount = noPayAmount;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
