package com.vlvxing.app.model;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class OrderDetaisModel {


    /**
     * message : 操作成功!
     * data : {"orderid":146,"userid":3,"travelproductid":1,"ordername":"东城一日游","orderallprice":999,"ordercount":1,"orderprice":999,"orderstatus":0,"orderusername":"菜花花","orderuserphone":"15010233989","orderuseraddress":"北京","orderuserid":"511621199504122086","orderusermessage":"","ordercreatetime":1497940633000,"paytime":null,"outtradeno":null,"systemtradeno":"149794063303622","orderisdel":0,"orderpaytype":101,"ordertype":1,"orderpic":"http://handongkeji.com:8090/lvyous_upload/common/2017-05-26/MB1495796933116_mid.jpg","orderaddresslng":null,"orderaddresslat":null}
     * status : 1
     */

    private String message;
    private DataBean data;
    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class DataBean {
        /**
         * orderid : 146
         * userid : 3
         * travelproductid : 1
         * ordername : 东城一日游
         * orderallprice : 999.0
         * ordercount : 1
         * orderprice : 999.0
         * orderstatus : 0
         * orderusername : 菜花花
         * orderuserphone : 15010233989
         * orderuseraddress : 北京
         * orderuserid : 511621199504122086
         * orderusermessage : 
         * ordercreatetime : 1497940633000
         * paytime : null
         * outtradeno : null
         * systemtradeno : 149794063303622
         * orderisdel : 0
         * orderpaytype : 101
         * ordertype : 1
         * orderpic : http://handongkeji.com:8090/lvyous_upload/common/2017-05-26/MB1495796933116_mid.jpg
         * orderaddresslng : null
         * orderaddresslat : null
         */

        private String orderid;
        private String userid;
        private String travelproductid;
        private String ordername;
        private double orderallprice;
        private String ordercount;
        private double orderprice;
        private String orderstatus;
        private String orderusername;
        private String orderuserphone;
        private String orderuseraddress;
        private String orderuserid;
        private String orderusermessage;
        private String ordercreatetime;
        private String paytime;
        private String outtradeno;
        private String systemtradeno;
        private String orderisdel;
        private String orderpaytype;
        private String ordertype;
        private String orderpic;
        private double orderaddresslng;
        private double orderaddresslat;

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getTravelproductid() {
            return travelproductid;
        }

        public void setTravelproductid(String travelproductid) {
            this.travelproductid = travelproductid;
        }

        public String getOrdername() {
            return ordername;
        }

        public void setOrdername(String ordername) {
            this.ordername = ordername;
        }

        public double getOrderallprice() {
            return orderallprice;
        }

        public void setOrderallprice(double orderallprice) {
            this.orderallprice = orderallprice;
        }

        public String getOrdercount() {
            return ordercount;
        }

        public void setOrdercount(String ordercount) {
            this.ordercount = ordercount;
        }

        public double getOrderprice() {
            return orderprice;
        }

        public void setOrderprice(double orderprice) {
            this.orderprice = orderprice;
        }

        public String getOrderstatus() {
            return orderstatus;
        }

        public void setOrderstatus(String orderstatus) {
            this.orderstatus = orderstatus;
        }

        public String getOrderusername() {
            return orderusername;
        }

        public void setOrderusername(String orderusername) {
            this.orderusername = orderusername;
        }

        public String getOrderuserphone() {
            return orderuserphone;
        }

        public void setOrderuserphone(String orderuserphone) {
            this.orderuserphone = orderuserphone;
        }

        public String getOrderuseraddress() {
            return orderuseraddress;
        }

        public void setOrderuseraddress(String orderuseraddress) {
            this.orderuseraddress = orderuseraddress;
        }

        public String getOrderuserid() {
            return orderuserid;
        }

        public void setOrderuserid(String orderuserid) {
            this.orderuserid = orderuserid;
        }

        public String getOrderusermessage() {
            return orderusermessage;
        }

        public void setOrderusermessage(String orderusermessage) {
            this.orderusermessage = orderusermessage;
        }

        public String getOrdercreatetime() {
            return ordercreatetime;
        }

        public void setOrdercreatetime(String ordercreatetime) {
            this.ordercreatetime = ordercreatetime;
        }

        public String getPaytime() {
            return paytime;
        }

        public void setPaytime(String paytime) {
            this.paytime = paytime;
        }

        public String getOuttradeno() {
            return outtradeno;
        }

        public void setOuttradeno(String outtradeno) {
            this.outtradeno = outtradeno;
        }

        public String getSystemtradeno() {
            return systemtradeno;
        }

        public void setSystemtradeno(String systemtradeno) {
            this.systemtradeno = systemtradeno;
        }

        public String getOrderisdel() {
            return orderisdel;
        }

        public void setOrderisdel(String orderisdel) {
            this.orderisdel = orderisdel;
        }

        public String getOrderpaytype() {
            return orderpaytype;
        }

        public void setOrderpaytype(String orderpaytype) {
            this.orderpaytype = orderpaytype;
        }

        public String getOrdertype() {
            return ordertype;
        }

        public void setOrdertype(String ordertype) {
            this.ordertype = ordertype;
        }

        public String getOrderpic() {
            return orderpic;
        }

        public void setOrderpic(String orderpic) {
            this.orderpic = orderpic;
        }

        public double getOrderaddresslng() {
            return orderaddresslng;
        }

        public void setOrderaddresslng(double orderaddresslng) {
            this.orderaddresslng = orderaddresslng;
        }

        public double getOrderaddresslat() {
            return orderaddresslat;
        }

        public void setOrderaddresslat(double orderaddresslat) {
            this.orderaddresslat = orderaddresslat;
        }
    }
}
