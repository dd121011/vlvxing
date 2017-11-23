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
public class BaseOrderInfo {

    private int status;
    private String statusDesc;
    private boolean showNotWork;
    private int distributeType;
    public void setStatus(int status) {
         this.status = status;
     }
     public int getStatus() {
         return status;
     }

    public void setStatusDesc(String statusDesc) {
         this.statusDesc = statusDesc;
     }
     public String getStatusDesc() {
         return statusDesc;
     }

    public void setShowNotWork(boolean showNotWork) {
         this.showNotWork = showNotWork;
     }
     public boolean getShowNotWork() {
         return showNotWork;
     }

    public void setDistributeType(int distributeType) {
         this.distributeType = distributeType;
     }
     public int getDistributeType() {
         return distributeType;
     }

}