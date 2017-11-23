package com.qunar.model;

import com.qunar.bean.BookingResponseParam;
import com.qunar.bean.changeSearch.PlaneChangeSerachBean;

import java.io.Serializable;
import java.util.List;

/**
 * 改签查询
 * @author Zophar
 */
public class PlaneChangeSerachResult implements Serializable{
  public static final long serialVersionUID = 46601L;
  private Integer status;
  private String message;
  private Object content;
  private String type;
  private PlaneChangeSerachBean data;

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

  public PlaneChangeSerachBean getData() {
    return data;
  }

  public void setData(PlaneChangeSerachBean data) {
    this.data = data;
  }
}