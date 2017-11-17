package com.qunar.model;

import android.content.ServiceConnection;

import com.qunar.bean.BookingResponseParam;
import com.qunar.bean.CreateOrderResult;

import java.io.Serializable;

/**
 * Created by mingchao on 2017/11/8.
 */

public class CreateOrderData implements Serializable {
    public static final long serialVersionUID = 46601L;
    private Integer status;
    private String message;
    private Object content;
    private String type;
    private CreateOrderResult data;

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

    public CreateOrderResult getData() {
        return data;
    }

    public void setData(CreateOrderResult data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CreateOrderData{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", content=" + content +
                ", type='" + type + '\'' +
                ", data=" + data +
                '}';
    }
}
