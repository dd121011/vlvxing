package com.qunar.model;

import com.qunar.bean.applyChange.ApplyChangeResponse;
import com.qunar.bean.changeSearch.PlaneChangeSerachBean;

import java.io.Serializable;

/**
 * 改签查询
 * @author Zophar
 */
public class PlaneChangeSuccessResult implements Serializable{
  public static final long serialVersionUID = 46601L;
  private int status;
  private String message;
  private Object content;
  private String type;
  private ApplyChangeResponse data;

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

  public ApplyChangeResponse getData() {
    return data;
  }

  public void setData(ApplyChangeResponse data) {
    this.data = data;
  }
}