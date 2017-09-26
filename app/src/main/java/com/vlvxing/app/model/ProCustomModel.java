package com.vlvxing.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/5 0005.
 */

public class ProCustomModel implements Parcelable {

    /**
     * message : 操作成功!
     * data : [{"customswimid":1,"userid":1,"departureid":110107,"departure":"石景山区","destinationid":120101,"destination":"和平区","time":1495004323000,"days":2,"peoplecounts":5,"name":"小甜甜","tel":"13655859886","mail":"8237232@qq.com","isstop":0,"requirement":"我的自由我做主"}]
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
         * customswimid : 1
         * userid : 1
         * departureid : 110107
         * departure : 石景山区
         * destinationid : 120101
         * destination : 和平区
         * time : 1495004323000
         * days : 2
         * peoplecounts : 5
         * name : 小甜甜
         * tel : 13655859886
         * mail : 8237232@qq.com
         * isstop : 0
         * requirement : 我的自由我做主
         */

        private String customswimid;
        private int userid;
        private int departureid;
        private String departure;
        private int destinationid;
        private String destination;
        private String time;
        private String days;
        private String peoplecounts;
        private String name;
        private String tel;
        private String mail;
        private int isstop;
        private String requirement;

        public String getCustomswimid() {
            return customswimid;
        }

        public void setCustomswimid(String customswimid) {
            this.customswimid = customswimid;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public int getDepartureid() {
            return departureid;
        }

        public void setDepartureid(int departureid) {
            this.departureid = departureid;
        }

        public String getDeparture() {
            return departure;
        }

        public void setDeparture(String departure) {
            this.departure = departure;
        }

        public int getDestinationid() {
            return destinationid;
        }

        public void setDestinationid(int destinationid) {
            this.destinationid = destinationid;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }

        public String getPeoplecounts() {
            return peoplecounts;
        }

        public void setPeoplecounts(String peoplecounts) {
            this.peoplecounts = peoplecounts;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getMail() {
            return mail;
        }

        public void setMail(String mail) {
            this.mail = mail;
        }

        public int getIsstop() {
            return isstop;
        }

        public void setIsstop(int isstop) {
            this.isstop = isstop;
        }

        public String getRequirement() {
            return requirement;
        }

        public void setRequirement(String requirement) {
            this.requirement = requirement;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.customswimid);
            dest.writeInt(this.userid);
            dest.writeInt(this.departureid);
            dest.writeString(this.departure);
            dest.writeInt(this.destinationid);
            dest.writeString(this.destination);
            dest.writeString(this.time);
            dest.writeString(this.days);
            dest.writeString(this.peoplecounts);
            dest.writeString(this.name);
            dest.writeString(this.tel);
            dest.writeString(this.mail);
            dest.writeInt(this.isstop);
            dest.writeString(this.requirement);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.customswimid = in.readString();
            this.userid = in.readInt();
            this.departureid = in.readInt();
            this.departure = in.readString();
            this.destinationid = in.readInt();
            this.destination = in.readString();
            this.time = in.readString();
            this.days = in.readString();
            this.peoplecounts = in.readString();
            this.name = in.readString();
            this.tel = in.readString();
            this.mail = in.readString();
            this.isstop = in.readInt();
            this.requirement = in.readString();
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

    public ProCustomModel() {
    }

    protected ProCustomModel(Parcel in) {
        this.message = in.readString();
        this.status = in.readString();
        this.data = new ArrayList<DataBean>();
        in.readList(this.data, DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<ProCustomModel> CREATOR = new Parcelable.Creator<ProCustomModel>() {
        @Override
        public ProCustomModel createFromParcel(Parcel source) {
            return new ProCustomModel(source);
        }

        @Override
        public ProCustomModel[] newArray(int size) {
            return new ProCustomModel[size];
        }
    };
}
