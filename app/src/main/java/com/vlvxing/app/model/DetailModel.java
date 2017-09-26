package com.vlvxing.app.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/5/24 0024.
 */

public class DetailModel implements Parcelable {


    /**
     * message : 操作成功!
     * data : {"travelproductid":13,"areaid":110100,"parentid":null,"categoryid":null,"price":333,"address":"北京大望路1","isforeign":1,"pathlng":"116.485951","pathlat":"39.913365","productsmallpic":"http://handongkeji.com:8090/lvyous_upload/common/2017-05-26/MB1495796933116_mid.jpg","advertisebigpic":null,"productbigpic":"http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/1.jpg,http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/2.jpg,http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/3.jpg","productname":"用车一簇9","tel":"13568523356","carinfotype":2,"cartimetype":null,"starttime":null,"endtime":null,"salecounts":222,"traveltype":2,"isfarmyard":1,"isfeature":0,"isspecialprice":1,"specialprice":0,"ishot":0,"issessionplay":0,"isvisa":0,"isgroup":0,"isself":0,"istheme":0,"isdelete":0,"context":"conte我是内容","Stringroduction":"120迈","feesdescription":"最心动的价格","notice":"安全是心","distance":244}
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

    public static class DataBean implements Parcelable {
        /**
         * travelproductid : 13
         * areaid : 110100
         * parentid : null
         * categoryid : null
         * price : 333.0
         * address : 北京大望路1
         * isforeign : 1
         * pathlng : 116.485951
         * pathlat : 39.913365
         * productsmallpic : http://handongkeji.com:8090/lvyous_upload/common/2017-05-26/MB1495796933116_mid.jpg
         * advertisebigpic : null
         * productbigpic : http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/1.jpg,http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/2.jpg,http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/3.jpg
         * productname : 用车一簇9
         * tel : 13568523356
         * carinfotype : 2
         * cartimetype : null
         * starttime : null
         * endtime : null
         * salecounts : 222
         * traveltype : 2
         * isfarmyard : 1
         * isfeature : 0
         * isspecialprice : 1
         * specialprice : 0.0
         * ishot : 0
         * issessionplay : 0
         * isvisa : 0
         * isgroup : 0
         * isself : 0
         * istheme : 0
         * isdelete : 0
         * context : conte我是内容
         * Stringroduction : 120迈
         * feesdescription : 最心动的价格
         * notice : 安全是心
         * distance : 244
         */

        private String travelproductid;
        private String areaid;
        private String parentid;
        private String categoryid;
        private double price;
        private String address;
        private String isforeign;
        private double pathlng;
        private double pathlat;
        private String productsmallpic;
        private String advertisebigpic;
        private String productbigpic;
        private String productname;
        private String tel;
        private String carinfotype;
        private String cartimetype;
        private String starttime;
        private String endtime;
        private String salecounts;
        private String traveltype;
        private String isfarmyard;
        private String isfeature;
        private String isspecialprice;
        private double specialprice;
        private String ishot;
        private String issessionplay;
        private String isvisa;
        private String isgroup;
        private String isself;
        private String istheme;
        private String isdelete;
        private String context;
        private String introduction;
        private String feesdescription;
        private String notice;
        private String distance;

        public String getTravelproductid() {
            return travelproductid;
        }

        public void setTravelproductid(String travelproductid) {
            this.travelproductid = travelproductid;
        }

        public String getAreaid() {
            return areaid;
        }

        public void setAreaid(String areaid) {
            this.areaid = areaid;
        }

        public String getParentid() {
            return parentid;
        }

        public void setParentid(String parentid) {
            this.parentid = parentid;
        }

        public String getCategoryid() {
            return categoryid;
        }

        public void setCategoryid(String categoryid) {
            this.categoryid = categoryid;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getIsforeign() {
            return isforeign;
        }

        public void setIsforeign(String isforeign) {
            this.isforeign = isforeign;
        }

        public double getPathlng() {
            return pathlng;
        }

        public void setPathlng(double pathlng) {
            this.pathlng = pathlng;
        }

        public double getPathlat() {
            return pathlat;
        }

        public void setPathlat(double pathlat) {
            this.pathlat = pathlat;
        }

        public String getProductsmallpic() {
            return productsmallpic;
        }

        public void setProductsmallpic(String productsmallpic) {
            this.productsmallpic = productsmallpic;
        }

        public String getAdvertisebigpic() {
            return advertisebigpic;
        }

        public void setAdvertisebigpic(String advertisebigpic) {
            this.advertisebigpic = advertisebigpic;
        }

        public String getProductbigpic() {
            return productbigpic;
        }

        public void setProductbigpic(String productbigpic) {
            this.productbigpic = productbigpic;
        }

        public String getProductname() {
            return productname;
        }

        public void setProductname(String productname) {
            this.productname = productname;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getCarinfotype() {
            return carinfotype;
        }

        public void setCarinfotype(String carinfotype) {
            this.carinfotype = carinfotype;
        }

        public String getCartimetype() {
            return cartimetype;
        }

        public void setCartimetype(String cartimetype) {
            this.cartimetype = cartimetype;
        }

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getSalecounts() {
            return salecounts;
        }

        public void setSalecounts(String salecounts) {
            this.salecounts = salecounts;
        }

        public String getTraveltype() {
            return traveltype;
        }

        public void setTraveltype(String traveltype) {
            this.traveltype = traveltype;
        }

        public String getIsfarmyard() {
            return isfarmyard;
        }

        public void setIsfarmyard(String isfarmyard) {
            this.isfarmyard = isfarmyard;
        }

        public String getIsfeature() {
            return isfeature;
        }

        public void setIsfeature(String isfeature) {
            this.isfeature = isfeature;
        }

        public String getIsspecialprice() {
            return isspecialprice;
        }

        public void setIsspecialprice(String isspecialprice) {
            this.isspecialprice = isspecialprice;
        }

        public double getSpecialprice() {
            return specialprice;
        }

        public void setSpecialprice(double specialprice) {
            this.specialprice = specialprice;
        }

        public String getIshot() {
            return ishot;
        }

        public void setIshot(String ishot) {
            this.ishot = ishot;
        }

        public String getIssessionplay() {
            return issessionplay;
        }

        public void setIssessionplay(String issessionplay) {
            this.issessionplay = issessionplay;
        }

        public String getIsvisa() {
            return isvisa;
        }

        public void setIsvisa(String isvisa) {
            this.isvisa = isvisa;
        }

        public String getIsgroup() {
            return isgroup;
        }

        public void setIsgroup(String isgroup) {
            this.isgroup = isgroup;
        }

        public String getIsself() {
            return isself;
        }

        public void setIsself(String isself) {
            this.isself = isself;
        }

        public String getIstheme() {
            return istheme;
        }

        public void setIstheme(String istheme) {
            this.istheme = istheme;
        }

        public String getIsdelete() {
            return isdelete;
        }

        public void setIsdelete(String isdelete) {
            this.isdelete = isdelete;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getFeesdescription() {
            return feesdescription;
        }

        public void setFeesdescription(String feesdescription) {
            this.feesdescription = feesdescription;
        }

        public String getNotice() {
            return notice;
        }

        public void setNotice(String notice) {
            this.notice = notice;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.travelproductid);
            dest.writeString(this.areaid);
            dest.writeString(this.parentid);
            dest.writeString(this.categoryid);
            dest.writeDouble(this.price);
            dest.writeString(this.address);
            dest.writeString(this.isforeign);
            dest.writeDouble(this.pathlng);
            dest.writeDouble(this.pathlat);
            dest.writeString(this.productsmallpic);
            dest.writeString(this.advertisebigpic);
            dest.writeString(this.productbigpic);
            dest.writeString(this.productname);
            dest.writeString(this.tel);
            dest.writeString(this.carinfotype);
            dest.writeString(this.cartimetype);
            dest.writeString(this.starttime);
            dest.writeString(this.endtime);
            dest.writeString(this.salecounts);
            dest.writeString(this.traveltype);
            dest.writeString(this.isfarmyard);
            dest.writeString(this.isfeature);
            dest.writeString(this.isspecialprice);
            dest.writeDouble(this.specialprice);
            dest.writeString(this.ishot);
            dest.writeString(this.issessionplay);
            dest.writeString(this.isvisa);
            dest.writeString(this.isgroup);
            dest.writeString(this.isself);
            dest.writeString(this.istheme);
            dest.writeString(this.isdelete);
            dest.writeString(this.context);
            dest.writeString(this.introduction);
            dest.writeString(this.feesdescription);
            dest.writeString(this.notice);
            dest.writeString(this.distance);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.travelproductid = in.readString();
            this.areaid = in.readString();
            this.parentid = in.readString();
            this.categoryid = in.readString();
            this.price = in.readDouble();
            this.address = in.readString();
            this.isforeign = in.readString();
            this.pathlng = in.readDouble();
            this.pathlat = in.readDouble();
            this.productsmallpic = in.readString();
            this.advertisebigpic = in.readString();
            this.productbigpic = in.readString();
            this.productname = in.readString();
            this.tel = in.readString();
            this.carinfotype = in.readString();
            this.cartimetype = in.readString();
            this.starttime = in.readString();
            this.endtime = in.readString();
            this.salecounts = in.readString();
            this.traveltype = in.readString();
            this.isfarmyard = in.readString();
            this.isfeature = in.readString();
            this.isspecialprice = in.readString();
            this.specialprice = in.readDouble();
            this.ishot = in.readString();
            this.issessionplay = in.readString();
            this.isvisa = in.readString();
            this.isgroup = in.readString();
            this.isself = in.readString();
            this.istheme = in.readString();
            this.isdelete = in.readString();
            this.context = in.readString();
            this.introduction = in.readString();
            this.feesdescription = in.readString();
            this.notice = in.readString();
            this.distance = in.readString();
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
        dest.writeParcelable(this.data, flags);
        dest.writeString(this.status);
    }

    public DetailModel() {
    }

    protected DetailModel(Parcel in) {
        this.message = in.readString();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        this.status = in.readString();
    }

    public static final Parcelable.Creator<DetailModel> CREATOR = new Parcelable.Creator<DetailModel>() {
        @Override
        public DetailModel createFromParcel(Parcel source) {
            return new DetailModel(source);
        }

        @Override
        public DetailModel[] newArray(int size) {
            return new DetailModel[size];
        }
    };
}
