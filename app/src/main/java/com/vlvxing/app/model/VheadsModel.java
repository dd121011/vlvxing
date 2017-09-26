package com.vlvxing.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/8 0008.
 */

public class VheadsModel implements Parcelable {


    /**
     * message : 操作成功!
     * data : [{"vheadid":1,"headname":"北京一日玩","time":1494919586000,"headpic":null,"description":"故宫好玩啊！","isstop":0,"areaid":1},{"vheadid":2,"headname":"山海大图","time":1494833356000,"headpic":null,"description":"日期蓝色的","isstop":0,"areaid":1}]
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
         * vheadid : 1
         * headname : 北京一日玩
         * time : 1494919586000
         * headpic : null
         * description : 故宫好玩啊！
         * isstop : 0
         * areaid : 1
         */

        private String vheadid;
        private String headname;
        private String time;
        private String headpic;
        private String description;
        private String isstop;
        private String areaid;

        public String getVheadid() {
            return vheadid;
        }

        public void setVheadid(String vheadid) {
            this.vheadid = vheadid;
        }

        public String getHeadname() {
            return headname;
        }

        public void setHeadname(String headname) {
            this.headname = headname;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getHeadpic() {
            return headpic;
        }

        public void setHeadpic(String headpic) {
            this.headpic = headpic;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIsstop() {
            return isstop;
        }

        public void setIsstop(String isstop) {
            this.isstop = isstop;
        }

        public String getAreaid() {
            return areaid;
        }

        public void setAreaid(String areaid) {
            this.areaid = areaid;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.vheadid);
            dest.writeString(this.headname);
            dest.writeString(this.time);
            dest.writeString(this.headpic);
            dest.writeString(this.description);
            dest.writeString(this.isstop);
            dest.writeString(this.areaid);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.vheadid = in.readString();
            this.headname = in.readString();
            this.time = in.readString();
            this.headpic = in.readString();
            this.description = in.readString();
            this.isstop = in.readString();
            this.areaid = in.readString();
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

    public VheadsModel() {
    }

    protected VheadsModel(Parcel in) {
        this.message = in.readString();
        this.status = in.readString();
        this.data = new ArrayList<DataBean>();
        in.readList(this.data, DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<VheadsModel> CREATOR = new Parcelable.Creator<VheadsModel>() {
        @Override
        public VheadsModel createFromParcel(Parcel source) {
            return new VheadsModel(source);
        }

        @Override
        public VheadsModel[] newArray(int size) {
            return new VheadsModel[size];
        }
    };
}
