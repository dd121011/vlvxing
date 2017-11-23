package com.qunar.model;

import com.qunar.bean.refundSearch.RefundSearchResponseParam;

import java.io.Serializable;

/**
 * 退票查询
 * @author Zophar
 */
public class PlaneRefundSerachResult implements Serializable{
  public static final long serialVersionUID = 46601L;
  private Integer status;
  private String message;
  private RefundSearchResponseParam data;

  public RefundSearchResponseParam getData() {
    return data;
  }

  public void setData(RefundSearchResponseParam data) {
    this.data = data;
  }

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }



}