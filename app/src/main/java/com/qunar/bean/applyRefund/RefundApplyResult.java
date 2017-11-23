/**
  * Copyright 2017 bejson.com 
  */
package com.qunar.bean.applyRefund;

/**
 * Auto-generated: 2017-11-19 22:27:47
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class RefundApplyResult {

    private boolean success;
    private String reason;
    private boolean volunteer;
    private boolean noTicket;
    public void setSuccess(boolean success) {
         this.success = success;
     }
     public boolean getSuccess() {
         return success;
     }

    public void setReason(String reason) {
         this.reason = reason;
     }
     public String getReason() {
         return reason;
     }

    public void setVolunteer(boolean volunteer) {
         this.volunteer = volunteer;
     }
     public boolean getVolunteer() {
         return volunteer;
     }

    public void setNoTicket(boolean noTicket) {
         this.noTicket = noTicket;
     }
     public boolean getNoTicket() {
         return noTicket;
     }

}