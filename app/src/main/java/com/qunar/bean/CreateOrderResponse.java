package com.qunar.bean;

/**
 * Created by wjjunjjun.wang on 2017/8/4.
 */
public class CreateOrderResponse {

    private int code;
    private String message;
    private long createTime;
    private CreateOrderResult result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public CreateOrderResult getResult() {
        return result;
    }

    public void setResult(CreateOrderResult result) {
        this.result = result;
    }
}
