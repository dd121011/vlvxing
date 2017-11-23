/**
  * Copyright 2017 bejson.com 
  */
package com.qunar.bean.refundSearch;
import java.util.Date;

/**
 * Auto-generated: 2017-11-19 21:55:2
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Result {

    private RefundSearchResult refundSearchResult;
    private String refundApplyResult;
    private String changeSearchResult;
    private String changeApplyResult;
    private int id;
    private String name;
    private String cardType;
    private String cardNum;
    private String ticketNum;
    private Date birthday;
    private int gender;
    public void setRefundSearchResult(RefundSearchResult refundSearchResult) {
         this.refundSearchResult = refundSearchResult;
     }
     public RefundSearchResult getRefundSearchResult() {
         return refundSearchResult;
     }

    public void setRefundApplyResult(String refundApplyResult) {
         this.refundApplyResult = refundApplyResult;
     }
     public String getRefundApplyResult() {
         return refundApplyResult;
     }

    public void setChangeSearchResult(String changeSearchResult) {
         this.changeSearchResult = changeSearchResult;
     }
     public String getChangeSearchResult() {
         return changeSearchResult;
     }

    public void setChangeApplyResult(String changeApplyResult) {
         this.changeApplyResult = changeApplyResult;
     }
     public String getChangeApplyResult() {
         return changeApplyResult;
     }

    public void setId(int id) {
         this.id = id;
     }
     public int getId() {
         return id;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setCardType(String cardType) {
         this.cardType = cardType;
     }
     public String getCardType() {
         return cardType;
     }

    public void setCardNum(String cardNum) {
         this.cardNum = cardNum;
     }
     public String getCardNum() {
         return cardNum;
     }

    public void setTicketNum(String ticketNum) {
         this.ticketNum = ticketNum;
     }
     public String getTicketNum() {
         return ticketNum;
     }

    public void setBirthday(Date birthday) {
         this.birthday = birthday;
     }
     public Date getBirthday() {
         return birthday;
     }

    public void setGender(int gender) {
         this.gender = gender;
     }
     public int getGender() {
         return gender;
     }

}