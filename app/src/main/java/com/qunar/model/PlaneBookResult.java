package com.qunar.model;

import com.qunar.bean.BookingResponseParam;
import com.qunar.bean.SearchQuoteResponse;

import java.io.Serializable;

/**
 * 机票booking详情返回数据
 * @author hjgang
 */
public class PlaneBookResult implements Serializable{
  public static final long serialVersionUID = 46601L;
  private Integer status;
  private String message;
  private Object content;
  private String type;
  private Object data;

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
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