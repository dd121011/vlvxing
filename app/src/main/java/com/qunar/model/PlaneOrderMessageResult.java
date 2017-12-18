package com.qunar.model;

import com.qunar.bean.SearchQuoteResponse;

import java.io.Serializable;

/**
 * 机票订单推送 详情返回数据
 * @author hjgang
 */
public class PlaneOrderMessageResult implements Serializable{
  public static final long serialVersionUID = 46601L;
  private int status;
  private String message;
  private Object content;
  private String type;
  private FlyOrder data;

  public FlyOrder getData() {
    return data;
  }

  public void setData(FlyOrder data) {
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