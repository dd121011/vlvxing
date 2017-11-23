/**
  * Copyright 2017 bejson.com 
  */
 package com.qunar.bean.refundSearch;
import java.util.List;

/**
 * Auto-generated: 2017-11-19 21:55:2
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class RefundSearchResponse {

    private int code;
    private String message;
    private long createTime;
    private List<Result> result;
    public void setCode(int code) {
         this.code = code;
     }
     public int getCode() {
         return code;
     }

    public void setMessage(String message) {
         this.message = message;
     }
     public String getMessage() {
         return message;
     }

    public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public void setResult(List<Result> result) {
         this.result = result;
     }
     public List<Result> getResult() {
         return result;
     }

}