package com.qunar.bean;

/**
 * Created by wjjunjjun.wang on 2017/8/1.
 */
public class CreateOrderRequest {

    private String contact;
    private String contactMob;
    private String cardNo;
    private String bookingResult;
    private boolean needXcd;
    private String address;


    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactMob() {
        return contactMob;
    }

    public void setContactMob(String contactMob) {
        this.contactMob = contactMob;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getBookingResult() {
        return bookingResult;
    }

    public void setBookingResult(String bookingResult) {
        this.bookingResult = bookingResult;
    }

    public boolean isNeedXcd() {
        return needXcd;
    }

    public void setNeedXcd(boolean needXcd) {
        this.needXcd = needXcd;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
