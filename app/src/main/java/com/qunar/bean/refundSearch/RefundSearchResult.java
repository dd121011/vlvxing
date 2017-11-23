/**
  * Copyright 2017 bejson.com 
  */
 package com.qunar.bean.refundSearch;
import java.util.List;

/**
 * Auto-generated: 2017-11-19 21:55:2
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class RefundSearchResult {

    private String reason;
    private BaseOrderInfo baseOrderInfo;
    private List<FlightSegmentList> flightSegmentList;
    private RefundRuleInfo refundRuleInfo;
    private ContactInfo contactInfo;
    private String tgqViewInfoJson;
    private List<TgqReasons> tgqReasons;
    private boolean canRefund;
    private boolean noTicket;
    private boolean airChangeAllRefund;
    private boolean needUploadProof;
    private String noTicketPassengerPriceInfo;
    public void setReason(String reason) {
         this.reason = reason;
     }
     public String getReason() {
         return reason;
     }

    public void setBaseOrderInfo(BaseOrderInfo baseOrderInfo) {
         this.baseOrderInfo = baseOrderInfo;
     }
     public BaseOrderInfo getBaseOrderInfo() {
         return baseOrderInfo;
     }

    public void setFlightSegmentList(List<FlightSegmentList> flightSegmentList) {
         this.flightSegmentList = flightSegmentList;
     }
     public List<FlightSegmentList> getFlightSegmentList() {
         return flightSegmentList;
     }

    public void setRefundRuleInfo(RefundRuleInfo refundRuleInfo) {
         this.refundRuleInfo = refundRuleInfo;
     }
     public RefundRuleInfo getRefundRuleInfo() {
         return refundRuleInfo;
     }

    public void setContactInfo(ContactInfo contactInfo) {
         this.contactInfo = contactInfo;
     }
     public ContactInfo getContactInfo() {
         return contactInfo;
     }

    public void setTgqViewInfoJson(String tgqViewInfoJson) {
         this.tgqViewInfoJson = tgqViewInfoJson;
     }
     public String getTgqViewInfoJson() {
         return tgqViewInfoJson;
     }

    public void setTgqReasons(List<TgqReasons> tgqReasons) {
         this.tgqReasons = tgqReasons;
     }
     public List<TgqReasons> getTgqReasons() {
         return tgqReasons;
     }

    public void setCanRefund(boolean canRefund) {
         this.canRefund = canRefund;
     }
     public boolean getCanRefund() {
         return canRefund;
     }

    public void setNoTicket(boolean noTicket) {
         this.noTicket = noTicket;
     }
     public boolean getNoTicket() {
         return noTicket;
     }

    public void setAirChangeAllRefund(boolean airChangeAllRefund) {
         this.airChangeAllRefund = airChangeAllRefund;
     }
     public boolean getAirChangeAllRefund() {
         return airChangeAllRefund;
     }

    public void setNeedUploadProof(boolean needUploadProof) {
         this.needUploadProof = needUploadProof;
     }
     public boolean getNeedUploadProof() {
         return needUploadProof;
     }

    public void setNoTicketPassengerPriceInfo(String noTicketPassengerPriceInfo) {
         this.noTicketPassengerPriceInfo = noTicketPassengerPriceInfo;
     }
     public String getNoTicketPassengerPriceInfo() {
         return noTicketPassengerPriceInfo;
     }

}