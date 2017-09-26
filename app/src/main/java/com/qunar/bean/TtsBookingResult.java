package com.qunar.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by stanley on 15-11-19 At 18:51
 */
public class TtsBookingResult implements Serializable{
    private static final long serialVersionUID = -4961986690062601215L;
    /**
     * booking 状态
     */
    private String bookingStatus = "";
    /**
     * booking失败信息
     */
    private String errMsg = "";

    /**
     * booking　Tag
     */
    private String bookingTag = "";
    /**
     * 航班信息
     */
    private List<BookingFlightInfo> flightInfo;
    /**
     * 报价信息
     */
    private BookingPriceInfo priceInfo;
    /**
     * Booking 扩展信息
     */
    private ExtInfo extInfo;
    /**
     * 退改签说明
     */
    private TgqShowData tgqShowData;
    /**
     * 出票时间说明
     */
    private String ticketTime = "";
    /**
     * 保险信息
     */
    private Ins bookingIns;

    /**
     * 报销信息与快递费用信息
     */
    private ExpressInfo expressInfo;

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getBookingTag() {
        return bookingTag;
    }

    public void setBookingTag(String bookingTag) {
        this.bookingTag = bookingTag;
    }

    public List<BookingFlightInfo> getFlightInfo() {
        return flightInfo;
    }

    public void setFlightInfo(List<BookingFlightInfo> flightInfo) {
        this.flightInfo = flightInfo;
    }

    public BookingPriceInfo getPriceInfo() {
        return priceInfo;
    }

    public void setPriceInfo(BookingPriceInfo priceInfo) {
        this.priceInfo = priceInfo;
    }

    public ExtInfo getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(ExtInfo extInfo) {
        this.extInfo = extInfo;
    }

    public TgqShowData getTgqShowData() {
        return tgqShowData;
    }

    public void setTgqShowData(TgqShowData tgqShowData) {
        this.tgqShowData = tgqShowData;
    }

    public String getTicketTime() {
        return ticketTime;
    }

    public void setTicketTime(String ticketTime) {
        this.ticketTime = ticketTime;
    }

    public Ins getBookingIns() {
        return bookingIns;
    }

    public void setBookingIns(Ins bookingIns) {
        this.bookingIns = bookingIns;
    }

    public ExpressInfo getExpressInfo() {
        return expressInfo;
    }

    public void setExpressInfo(ExpressInfo expressInfo) {
        this.expressInfo = expressInfo;
    }

    @Override
    public String toString() {
        return "TtsBookingResult{" +
                "bookingStatus='" + bookingStatus + '\'' +
                ", errMsg='" + errMsg + '\'' +
                ", bookingTag='" + bookingTag + '\'' +
                ", flightInfo=" + flightInfo +
                ", priceInfo=" + priceInfo +
                ", extInfo=" + extInfo +
                ", tgqShowData=" + tgqShowData +
                ", ticketTime='" + ticketTime + '\'' +
                ", bookingIns=" + bookingIns +
                ", expressInfo=" + expressInfo +
                '}';
    }
}
