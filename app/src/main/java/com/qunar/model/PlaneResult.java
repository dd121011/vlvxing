package com.qunar.model;

import com.qunar.bean.SearchFlightResponse;

import java.io.Serializable;

/**
 * 机票返回数据
 * @author hjgang
 */
public class PlaneResult implements Serializable{
  public static final long serialVersionUID = 46601L;
  private Integer status;
  private String message;
  private Object content;
  private String type;
  private SearchFlightResponse data;

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

  public SearchFlightResponse getData() {
    return data;
  }

  public void setData(SearchFlightResponse data) {
    this.data = data;
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