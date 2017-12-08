package com.qunar.model;

import com.qunar.bean.applyChange.ApplyChangeResult;
import com.qunar.bean.changeSearch.PlaneChangeSerachBean;

import java.io.Serializable;

/**
 * 改签支付
 * @author Zophar
 */
public class PlaneChangeApplyResult implements Serializable{
  public static final long serialVersionUID = 46601L;
  private int status;
  private String message;
  private Object content;
  private String type;
  private ApplyChangeResult data;

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

  public ApplyChangeResult getData() {
    return data;
  }

  public void setData(ApplyChangeResult data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return "PlaneChangeApplyResult{" +
            "status=" + status +
            ", message='" + message + '\'' +
            ", content=" + content +
            ", type='" + type + '\'' +
            ", data=" + data +
            '}';
  }
}