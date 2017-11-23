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
public class TgqReasons {

    private int code;
    private String msg;
    private boolean will;
    private List<RefundPassengerPriceInfoList> refundPassengerPriceInfoList;
    private String changeFlightSegmentList;
    public void setCode(int code) {
         this.code = code;
     }
     public int getCode() {
         return code;
     }

    public void setMsg(String msg) {
         this.msg = msg;
     }
     public String getMsg() {
         return msg;
     }

    public void setWill(boolean will) {
         this.will = will;
     }
     public boolean getWill() {
         return will;
     }

    public void setRefundPassengerPriceInfoList(List<RefundPassengerPriceInfoList> refundPassengerPriceInfoList) {
         this.refundPassengerPriceInfoList = refundPassengerPriceInfoList;
     }
     public List<RefundPassengerPriceInfoList> getRefundPassengerPriceInfoList() {
         return refundPassengerPriceInfoList;
     }

    public void setChangeFlightSegmentList(String changeFlightSegmentList) {
         this.changeFlightSegmentList = changeFlightSegmentList;
     }
     public String getChangeFlightSegmentList() {
         return changeFlightSegmentList;
     }

}