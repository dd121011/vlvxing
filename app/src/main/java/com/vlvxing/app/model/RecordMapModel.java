package com.vlvxing.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2017/6/13 0013.
 */

public class RecordMapModel implements Parcelable {


    /**
     * content : [{"travelroadid":145,"travelroadtitle":null,"description":null,"userid":36,"createtime":1500015402000,"startarea":"中国北京市朝阳区建国路88号院10号","stratlng":"116.483263","stratlat":"39.913044","destinationlng":"116.483263","destinationlat":"39.913044","destination":"中国北京市朝阳区建国路88号院10号","startareaanddestination":"中国北京市朝阳区建国路88号院10号-中国北京市朝阳区建国路88号院10号","distance":null,"isdelete":0,"isrecommend":null,"authority":null,"coordinate":null},{"travelroadid":146,"travelroadtitle":null,"description":null,"userid":36,"createtime":1500015536000,"startarea":"中国北京市朝阳区建国路88号院10号","stratlng":"116.483345","stratlat":"39.913125","destinationlng":"116.483301","destinationlat":"39.913059","destination":"中国北京市朝阳区建国路88号院10号","startareaanddestination":"中国北京市朝阳区建国路88号院10号-中国北京市朝阳区建国路88号院10号","distance":null,"isdelete":0,"isrecommend":null,"authority":null,"coordinate":null}]
     * message : 操作成功!
     * data : [{"pathinfoid":377,"userid":36,"pathname":null,"travelroadid":145,"address":"中国北京市朝阳区建国路88号院10号","pathlng":"116.483255","pathlat":"39.913057","createtime":1500015402000,"picurl":"http://app.mtvlx.cn/lvyous_upload/user/2017-07-14/HZ1500015382937_mid.jpg","videourl":null,"coverurl":null,"type":0,"isdelete":0},{"pathinfoid":378,"userid":36,"pathname":null,"travelroadid":145,"address":"中国北京市朝阳区建国路88号院10号","pathlng":"116.48329","pathlat":"39.913055","createtime":1500015402000,"picurl":"http://app.mtvlx.cn/lvyous_upload/user/2017-07-14/FF1500015394749_mid.jpg","videourl":null,"coverurl":null,"type":0,"isdelete":0},{"pathinfoid":380,"userid":36,"pathname":null,"travelroadid":146,"address":"中国北京市朝阳区建国路88号院10号","pathlng":"116.483345","pathlat":"39.913125","createtime":1500015536000,"picurl":"http://app.mtvlx.cn/lvyous_upload/user/2017-07-14/SX1500015466468_mid.jpg","videourl":null,"coverurl":null,"type":0,"isdelete":0},{"pathinfoid":381,"userid":36,"pathname":null,"travelroadid":146,"address":"中国北京市朝阳区建国路88号院10号","pathlng":"116.483301","pathlat":"39.913059","createtime":1500015536000,"picurl":"http://app.mtvlx.cn/lvyous_upload/user/2017-07-14/IR1500015492593_mid.jpg","videourl":null,"coverurl":null,"type":0,"isdelete":0},{"pathinfoid":382,"userid":36,"pathname":null,"travelroadid":146,"address":"中国北京市朝阳区建国路88号院10号","pathlng":"116.483301","pathlat":"39.913059","createtime":1500015536000,"picurl":"http://app.mtvlx.cn/lvyous_upload/user/2017-07-14/RY1500015529343_mid.jpg","videourl":null,"coverurl":null,"type":0,"isdelete":0}]
     * status : 1
     */

    private String message;
    private String status;
    private List<ContentBean> content;
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

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class ContentBean implements Parcelable {
        /**
         * travelroadid : 145
         * travelroadtitle : null
         * description : null
         * userid : 36
         * createtime : 1500015402000
         * startarea : 中国北京市朝阳区建国路88号院10号
         * stratlng : 116.483263
         * stratlat : 39.913044
         * destinationlng : 116.483263
         * destinationlat : 39.913044
         * destination : 中国北京市朝阳区建国路88号院10号
         * startareaanddestination : 中国北京市朝阳区建国路88号院10号-中国北京市朝阳区建国路88号院10号
         * distance : null
         * isdelete : 0
         * isrecommend : null
         * authority : null
         * coordinate : null
         */

        private String travelroadid;
        private String travelroadtitle;
        private String description;
        private String userid;
        private String createtime;
        private String startarea;
        private double stratlng;
        private double stratlat;
        private double destinationlng;
        private double destinationlat;
        private String destination;
        private String startareaanddestination;
        private String distance;
        private String isdelete;
        private String isrecommend;
        private String authority;
        private String coordinate;

        public String getTravelroadid() {
            return travelroadid;
        }

        public void setTravelroadid(String travelroadid) {
            this.travelroadid = travelroadid;
        }

        public String getTravelroadtitle() {
            return travelroadtitle;
        }

        public void setTravelroadtitle(String travelroadtitle) {
            this.travelroadtitle = travelroadtitle;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getStartarea() {
            return startarea;
        }

        public void setStartarea(String startarea) {
            this.startarea = startarea;
        }

        public double getStratlng() {
            return stratlng;
        }

        public void setStratlng(double stratlng) {
            this.stratlng = stratlng;
        }

        public double getStratlat() {
            return stratlat;
        }

        public void setStratlat(double stratlat) {
            this.stratlat = stratlat;
        }

        public double getDestinationlng() {
            return destinationlng;
        }

        public void setDestinationlng(double destinationlng) {
            this.destinationlng = destinationlng;
        }

        public double getDestinationlat() {
            return destinationlat;
        }

        public void setDestinationlat(double destinationlat) {
            this.destinationlat = destinationlat;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public String getStartareaanddestination() {
            return startareaanddestination;
        }

        public void setStartareaanddestination(String startareaanddestination) {
            this.startareaanddestination = startareaanddestination;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getIsdelete() {
            return isdelete;
        }

        public void setIsdelete(String isdelete) {
            this.isdelete = isdelete;
        }

        public String getIsrecommend() {
            return isrecommend;
        }

        public void setIsrecommend(String isrecommend) {
            this.isrecommend = isrecommend;
        }

        public String getAuthority() {
            return authority;
        }

        public void setAuthority(String authority) {
            this.authority = authority;
        }

        public String getCoordinate() {
            return coordinate;
        }

        public void setCoordinate(String coordinate) {
            this.coordinate = coordinate;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.travelroadid);
            dest.writeString(this.travelroadtitle);
            dest.writeString(this.description);
            dest.writeString(this.userid);
            dest.writeString(this.createtime);
            dest.writeString(this.startarea);
            dest.writeDouble(this.stratlng);
            dest.writeDouble(this.stratlat);
            dest.writeDouble(this.destinationlng);
            dest.writeDouble(this.destinationlat);
            dest.writeString(this.destination);
            dest.writeString(this.startareaanddestination);
            dest.writeString(this.distance);
            dest.writeString(this.isdelete);
            dest.writeString(this.isrecommend);
            dest.writeString(this.authority);
            dest.writeString(this.coordinate);
        }

        public ContentBean() {
        }

        protected ContentBean(Parcel in) {
            this.travelroadid = in.readString();
            this.travelroadtitle = in.readString();
            this.description = in.readString();
            this.userid = in.readString();
            this.createtime = in.readString();
            this.startarea = in.readString();
            this.stratlng = in.readDouble();
            this.stratlat = in.readDouble();
            this.destinationlng = in.readDouble();
            this.destinationlat = in.readDouble();
            this.destination = in.readString();
            this.startareaanddestination = in.readString();
            this.distance = in.readString();
            this.isdelete = in.readString();
            this.isrecommend = in.readString();
            this.authority = in.readString();
            this.coordinate = in.readString();
        }

        public static final Parcelable.Creator<ContentBean> CREATOR = new Parcelable.Creator<ContentBean>() {
            @Override
            public ContentBean createFromParcel(Parcel source) {
                return new ContentBean(source);
            }

            @Override
            public ContentBean[] newArray(int size) {
                return new ContentBean[size];
            }
        };
    }

    public static class DataBean implements Parcelable {
        /**
         * pathinfoid : 377
         * userid : 36
         * pathname : null
         * travelroadid : 145
         * address : 中国北京市朝阳区建国路88号院10号
         * pathlng : 116.483255
         * pathlat : 39.913057
         * createtime : 1500015402000
         * picurl : http://app.mtvlx.cn/lvyous_upload/user/2017-07-14/HZ1500015382937_mid.jpg
         * videourl : null
         * coverurl : null
         * type : 0
         * isdelete : 0
         */

        private String pathinfoid;
        private String userid;
        private String pathname;
        private String travelroadid;
        private String address;
        private double pathlng;
        private double pathlat;
        private String createtime;
        private String picurl;
        private String videourl;
        private String coverurl;
        private String type;
        private String isdelete;

        public String getPathinfoid() {
            return pathinfoid;
        }

        public void setPathinfoid(String pathinfoid) {
            this.pathinfoid = pathinfoid;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getPathname() {
            return pathname;
        }

        public void setPathname(String pathname) {
            this.pathname = pathname;
        }

        public String getTravelroadid() {
            return travelroadid;
        }

        public void setTravelroadid(String travelroadid) {
            this.travelroadid = travelroadid;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getPicurl() {
            return picurl;
        }

        public void setPicurl(String picurl) {
            this.picurl = picurl;
        }

        public String getVideourl() {
            return videourl;
        }

        public void setVideourl(String videourl) {
            this.videourl = videourl;
        }

        public String getCoverurl() {
            return coverurl;
        }

        public void setCoverurl(String coverurl) {
            this.coverurl = coverurl;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getIsdelete() {
            return isdelete;
        }

        public void setIsdelete(String isdelete) {
            this.isdelete = isdelete;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.pathinfoid);
            dest.writeString(this.userid);
            dest.writeString(this.pathname);
            dest.writeString(this.travelroadid);
            dest.writeString(this.address);
            dest.writeDouble(this.pathlng);
            dest.writeDouble(this.pathlat);
            dest.writeString(this.createtime);
            dest.writeString(this.picurl);
            dest.writeString(this.videourl);
            dest.writeString(this.coverurl);
            dest.writeString(this.type);
            dest.writeString(this.isdelete);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.pathinfoid = in.readString();
            this.userid = in.readString();
            this.pathname = in.readString();
            this.travelroadid = in.readString();
            this.address = in.readString();
            this.pathlng = in.readDouble();
            this.pathlat = in.readDouble();
            this.createtime = in.readString();
            this.picurl = in.readString();
            this.videourl = in.readString();
            this.coverurl = in.readString();
            this.type = in.readString();
            this.isdelete = in.readString();
        }

        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
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
        dest.writeTypedList(this.content);
        dest.writeTypedList(this.data);
    }

    public RecordMapModel() {
    }

    protected RecordMapModel(Parcel in) {
        this.message = in.readString();
        this.status = in.readString();
        this.content = in.createTypedArrayList(ContentBean.CREATOR);
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<RecordMapModel> CREATOR = new Parcelable.Creator<RecordMapModel>() {
        @Override
        public RecordMapModel createFromParcel(Parcel source) {
            return new RecordMapModel(source);
        }

        @Override
        public RecordMapModel[] newArray(int size) {
            return new RecordMapModel[size];
        }
    };
}


