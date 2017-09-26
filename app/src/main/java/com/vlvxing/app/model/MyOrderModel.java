package com.vlvxing.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/6 0006.
 */

public class MyOrderModel implements Parcelable {


    /**
     * message : 操作成功!
     * data : [{"orderid":7,"userid":1,"travelproductid":8,"ordername":"wodedingdan5","orderallprice":900,"ordercount":2,"orderprice":450,"orderstatus":1,"orderusername":"xiaowang","orderuserphone":"13683649882","orderuseraddress":"abc","orderuserid":"1","orderusermessage":"一定成功5","ordercreatetime":1496466090000,"outtradeno":null,"systemtradeno":"149646609000857","orderisdel":0,"orderpaytype":102,"ordertype":1,"orderpic":"http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/1.jpg","orderaddresslng":null,"orderaddresslat":null},{"orderid":3,"userid":1,"travelproductid":3,"ordername":"wodedingdan1","orderallprice":900,"ordercount":2,"orderprice":450,"orderstatus":1,"orderusername":"xiaowang","orderuserphone":"13683649882","orderuseraddress":"abc","orderuserid":"1","orderusermessage":"一定成功1","ordercreatetime":1496462183000,"outtradeno":null,"systemtradeno":"149646218344699","orderisdel":0,"orderpaytype":101,"ordertype":1,"orderpic":"http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/1.jpg","orderaddresslng":null,"orderaddresslat":null}]
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

    public static class DataBean implements Parcelable {
        /**
         * orderid : 7
         * userid : 1
         * travelproductid : 8
         * ordername : wodedingdan5
         * orderallprice : 900.0
         * ordercount : 2
         * orderprice : 450.0
         * orderstatus : 1
         * orderusername : xiaowang
         * orderuserphone : 13683649882
         * orderuseraddress : abc
         * orderuserid : 1
         * orderusermessage : 一定成功5
         * ordercreatetime : 1496466090000
         * outtradeno : null
         * systemtradeno : 149646609000857
         * orderisdel : 0
         * orderpaytype : 102
         * ordertype : 1
         * orderpic : http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/1.jpg
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.orderid);
            dest.writeString(this.userid);
            dest.writeString(this.travelproductid);
            dest.writeString(this.ordername);
            dest.writeDouble(this.orderallprice);
            dest.writeString(this.ordercount);
            dest.writeDouble(this.orderprice);
            dest.writeString(this.orderstatus);
            dest.writeString(this.orderusername);
            dest.writeString(this.orderuserphone);
            dest.writeString(this.orderuseraddress);
            dest.writeString(this.orderuserid);
            dest.writeString(this.orderusermessage);
            dest.writeString(this.ordercreatetime);
            dest.writeString(this.outtradeno);
            dest.writeString(this.systemtradeno);
            dest.writeString(this.orderisdel);
            dest.writeString(this.orderpaytype);
            dest.writeString(this.ordertype);
            dest.writeString(this.orderpic);
            dest.writeDouble(this.orderaddresslng);
            dest.writeDouble(this.orderaddresslat);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.orderid = in.readString();
            this.userid = in.readString();
            this.travelproductid = in.readString();
            this.ordername = in.readString();
            this.orderallprice = in.readDouble();
            this.ordercount = in.readString();
            this.orderprice = in.readDouble();
            this.orderstatus = in.readString();
            this.orderusername = in.readString();
            this.orderuserphone = in.readString();
            this.orderuseraddress = in.readString();
            this.orderuserid = in.readString();
            this.orderusermessage = in.readString();
            this.ordercreatetime = in.readString();
            this.outtradeno = in.readString();
            this.systemtradeno = in.readString();
            this.orderisdel = in.readString();
            this.orderpaytype = in.readString();
            this.ordertype = in.readString();
            this.orderpic = in.readString();
            this.orderaddresslng = in.readDouble();
            this.orderaddresslat = in.readDouble();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };


    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.message);
        dest.writeString(this.status);
        dest.writeList(this.data);
    }

    public MyOrderModel() {
    }

    protected MyOrderModel(Parcel in) {
        this.message = in.readString();
        this.status = in.readString();
        this.data = new ArrayList<DataBean>();
        in.readList(this.data, DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<MyOrderModel> CREATOR = new Parcelable.Creator<MyOrderModel>() {
        @Override
        public MyOrderModel createFromParcel(Parcel source) {
            return new MyOrderModel(source);
        }

        @Override
        public MyOrderModel[] newArray(int size) {
            return new MyOrderModel[size];
        }
    };
}
