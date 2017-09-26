package com.qunar.bean;

/**
 * Created by wjjunjjun.wang on 2017/7/26.
 */
public class BookingRequest {

    private String vendorStr;
    private String depCode;
    private String arrCode;
    private String code;
    private String date;
    private String carrier;
    private String btime;

    public String getVendorStr() {
        return vendorStr;
    }

    public void setVendorStr(String vendorStr) {
        this.vendorStr = vendorStr;
    }

    public String getDepCode() {
        return depCode;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode;
    }

    public String getArrCode() {
        return arrCode;
    }

    public void setArrCode(String arrCode) {
        this.arrCode = arrCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getBtime() {
        return btime;
    }

    public void setBtime(String btime) {
        this.btime = btime;
    }
}
