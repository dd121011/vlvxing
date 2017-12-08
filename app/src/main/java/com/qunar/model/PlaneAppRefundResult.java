package com.qunar.model;

import com.qunar.bean.applyRefund.ApplyRefundResponse;

import java.io.Serializable;

/**
 * 退票申请
 * @author Zophar
 */
public class PlaneAppRefundResult implements Serializable{
  public static final long serialVersionUID = 46601L;
  private int status;
  private String message;
  private Object content;
  private String type;
  private ApplyRefundResponse data;

  public ApplyRefundResponse getData() {
    return data;
  }

  public void setData(ApplyRefundResponse data) {
    this.data = data;
  }

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Object getContent() {
    return content;
  }

  public void setContent(Object content) {
    this.content = content;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


}