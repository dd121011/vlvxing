package com.vlvxing.app.model;

import java.util.List;

/**
 * Created by Administrator on 2017/6/8 0008.
 */

public class MessageModel {


    /**
     * message : 操作成功!
     * data : [{"msgid":116,"msgcontent":"您已成功购买用车一簇23","msgtime":1497945329000,"msgstatus":0,"msgtype":2,"userid":3,"targetid":null,"titile":null,"postid":null,"redirection":null,"ispush":0,"orderid":149}]
     * status : 1
     */

    private String message;
    private String status;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * msgid : 116
         * msgcontent : 您已成功购买用车一簇23
         * msgtime : 1497945329000
         * msgstatus : 0
         * msgtype : 2
         * userid : 3
         * targetid : null
         * titile : null
         * postid : null
         * redirection : null
         * ispush : 0
         * orderid : 149
         * androidclassname :跳转内部Activity
         * productid :线路ID
         *
         */

        private String msgid;
        private String msgcontent;
        private String msgtime;
        private String msgstatus;
        private String msgtype;
        private String userid;
        private String targetid;
        private String titile;
        private String postid;
        private String redirection;
        private String ispush;
        private String orderid;
        private String msgurl;
        private int type;
        private String androidclassname;
        private Integer productid;

        public String getAndroidclassname() {
            return androidclassname;
        }

        public void setAndroidclassname(String androidclassname) {
            this.androidclassname = androidclassname;
        }

        public Integer getProductid() {
            return productid;
        }

        public void setProductid(Integer productid) {
            this.productid = productid;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getMsgurl() {
            return msgurl;
        }

        public void setMsgurl(String msgurl) {
            this.msgurl = msgurl;
        }

        public String getMsgid() {
            return msgid;
        }

        public void setMsgid(String msgid) {
            this.msgid = msgid;
        }

        public String getMsgcontent() {
            return msgcontent;
        }

        public void setMsgcontent(String msgcontent) {
            this.msgcontent = msgcontent;
        }

        public String getMsgtime() {
            return msgtime;
        }

        public void setMsgtime(String msgtime) {
            this.msgtime = msgtime;
        }

        public String getMsgstatus() {
            return msgstatus;
        }

        public void setMsgstatus(String msgstatus) {
            this.msgstatus = msgstatus;
        }

        public String getMsgtype() {
            return msgtype;
        }

        public void setMsgtype(String msgtype) {
            this.msgtype = msgtype;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getTargetid() {
            return targetid;
        }

        public void setTargetid(String targetid) {
            this.targetid = targetid;
        }

        public String getTitile() {
            return titile;
        }

        public void setTitile(String titile) {
            this.titile = titile;
        }

        public String getPostid() {
            return postid;
        }

        public void setPostid(String postid) {
            this.postid = postid;
        }

        public String getRedirection() {
            return redirection;
        }

        public void setRedirection(String redirection) {
            this.redirection = redirection;
        }

        public String getIspush() {
            return ispush;
        }

        public void setIspush(String ispush) {
            this.ispush = ispush;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }
    }
}
