/**
  * Copyright 2017 bejson.com 
  */
package com.qunar.bean.refundSearch;

/**
 * Auto-generated: 2017-11-19 21:55:2
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class BasePassengerPriceInfo {

    private boolean disabled;
    private String disableReason;
    private int passengerId;
    private String passengerName;
    private String cardNum;
    private String passengerTypeStr;
    private int ticketPrice;
    private int constructionFee;
    private int fuelTax;
    public void setDisabled(boolean disabled) {
         this.disabled = disabled;
     }
     public boolean getDisabled() {
         return disabled;
     }

    public void setDisableReason(String disableReason) {
         this.disableReason = disableReason;
     }
     public String getDisableReason() {
         return disableReason;
     }

    public void setPassengerId(int passengerId) {
         this.passengerId = passengerId;
     }
     public int getPassengerId() {
         return passengerId;
     }

    public void setPassengerName(String passengerName) {
         this.passengerName = passengerName;
     }
     public String getPassengerName() {
         return passengerName;
     }

    public void setCardNum(String cardNum) {
         this.cardNum = cardNum;
     }
     public String getCardNum() {
         return cardNum;
     }

    public void setPassengerTypeStr(String passengerTypeStr) {
         this.passengerTypeStr = passengerTypeStr;
     }
     public String getPassengerTypeStr() {
         return passengerTypeStr;
     }

    public void setTicketPrice(int ticketPrice) {
         this.ticketPrice = ticketPrice;
     }
     public int getTicketPrice() {
         return ticketPrice;
     }

    public void setConstructionFee(int constructionFee) {
         this.constructionFee = constructionFee;
     }
     public int getConstructionFee() {
         return constructionFee;
     }

    public void setFuelTax(int fuelTax) {
         this.fuelTax = fuelTax;
     }
     public int getFuelTax() {
         return fuelTax;
     }

    @Override
    public String toString() {
        return "BasePassengerPriceInfo{" +
                "disabled=" + disabled +
                ", disableReason='" + disableReason + '\'' +
                ", passengerId=" + passengerId +
                ", passengerName='" + passengerName + '\'' +
                ", cardNum='" + cardNum + '\'' +
                ", passengerTypeStr='" + passengerTypeStr + '\'' +
                ", ticketPrice=" + ticketPrice +
                ", constructionFee=" + constructionFee +
                ", fuelTax=" + fuelTax +
                '}';
    }
}