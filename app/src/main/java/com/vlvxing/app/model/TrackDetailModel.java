package com.vlvxing.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public class TrackDetailModel implements Parcelable {


    /**
     * message : 操作成功!
     * data : {"travelroadid":34,"travelroadtitle":"菜花花录的","description":"啦啦","userid":3,"createtime":1497587749000,"startarea":"中国北京市朝阳区建国路88号院7","stratlng":"116.483207","stratlat":"39.912968","destinationlng":"116.483207","destinationlat":"39.912968","destination":"中国北京市朝阳区建国路88号院7","startareaanddestination":"中国北京市朝阳区建国路88号院7-中国北京市朝阳区建国路88号院7","distance":null,"isdelete":0,"isrecommend":null,"authority":null,"coordinate":"116.483207#39.912968","pathinfos":[{"pathinfoid":84,"userid":3,"pathname":"啦啦","travelroadid":34,"address":"中国北京市朝阳区建国路88号院7","pathlng":"116.483207","pathlat":"39.912968","createtime":1497587749000,"picurl":"http://handongkeji.com:8090/lvyous_upload/user/2017-06-16/VH1497587611065_mid.jpg","videourl":null,"coverurl":null,"type":0,"isdelete":0}]}
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
         * travelroadid : 34
         * travelroadtitle : 菜花花录的
         * description : 啦啦
         * userid : 3
         * createtime : 1497587749000
         * startarea : 中国北京市朝阳区建国路88号院7
         * stratlng : 116.483207
         * stratlat : 39.912968
         * destinationlng : 116.483207
         * destinationlat : 39.912968
         * destination : 中国北京市朝阳区建国路88号院7
         * startareaanddestination : 中国北京市朝阳区建国路88号院7-中国北京市朝阳区建国路88号院7
         * distance : null
         * isdelete : 0
         * isrecommend : null
         * authority : null
         * coordinate : 116.483207#39.912968
         * pathinfos : [{"pathinfoid":84,"userid":3,"pathname":"啦啦","travelroadid":34,"address":"中国北京市朝阳区建国路88号院7","pathlng":"116.483207","pathlat":"39.912968","createtime":1497587749000,"picurl":"http://handongkeji.com:8090/lvyous_upload/user/2017-06-16/VH1497587611065_mid.jpg","videourl":null,"coverurl":null,"type":0,"isdelete":0}]
         */

        private String travelroadid;
        private String travelroadtitle;
        private String description;
        private String userid;
        private String createtime;
        private String startarea;
        private String stratlng;
        private String stratlat;
        private String destinationlng;
        private String destinationlat;
        private String destination;
        private String startareaanddestination;
        private String distance;
        private String isdelete;
        private String isrecommend;
        private String authority;
        private String coordinate;
        private List<PathinfosBean> pathinfos;

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

        public String getStratlng() {
            return stratlng;
        }

        public void setStratlng(String stratlng) {
            this.stratlng = stratlng;
        }

        public String getStratlat() {
            return stratlat;
        }

        public void setStratlat(String stratlat) {
            this.stratlat = stratlat;
        }

        public String getDestinationlng() {
            return destinationlng;
        }

        public void setDestinationlng(String destinationlng) {
            this.destinationlng = destinationlng;
        }

        public String getDestinationlat() {
            return destinationlat;
        }

        public void setDestinationlat(String destinationlat) {
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

        public List<PathinfosBean> getPathinfos() {
            return pathinfos;
        }

        public void setPathinfos(List<PathinfosBean> pathinfos) {
            this.pathinfos = pathinfos;
        }

        public static class PathinfosBean implements Parcelable {
            /**
             * pathinfoid : 84
             * userid : 3
             * pathname : 啦啦
             * travelroadid : 34
             * address : 中国北京市朝阳区建国路88号院7
             * pathlng : 116.483207
             * pathlat : 39.912968
             * createtime : 1497587749000
             * picurl : http://handongkeji.com:8090/lvyous_upload/user/2017-06-16/VH1497587611065_mid.jpg
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

            public PathinfosBean() {
            }

            protected PathinfosBean(Parcel in) {
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

            public static final Creator<PathinfosBean> CREATOR = new Creator<PathinfosBean>() {
                @Override
                public PathinfosBean createFromParcel(Parcel source) {
                    return new PathinfosBean(source);
                }

                @Override
                public PathinfosBean[] newArray(int size) {
                    return new PathinfosBean[size];
                }
            };
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
            dest.writeString(this.stratlng);
            dest.writeString(this.stratlat);
            dest.writeString(this.destinationlng);
            dest.writeString(this.destinationlat);
            dest.writeString(this.destination);
            dest.writeString(this.startareaanddestination);
            dest.writeString(this.distance);
            dest.writeString(this.isdelete);
            dest.writeString(this.isrecommend);
            dest.writeString(this.authority);
            dest.writeString(this.coordinate);
            dest.writeList(this.pathinfos);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.travelroadid = in.readString();
            this.travelroadtitle = in.readString();
            this.description = in.readString();
            this.userid = in.readString();
            this.createtime = in.readString();
            this.startarea = in.readString();
            this.stratlng = in.readString();
            this.stratlat = in.readString();
            this.destinationlng = in.readString();
            this.destinationlat = in.readString();
            this.destination = in.readString();
            this.startareaanddestination = in.readString();
            this.distance = in.readString();
            this.isdelete = in.readString();
            this.isrecommend = in.readString();
            this.authority = in.readString();
            this.coordinate = in.readString();
            this.pathinfos = new ArrayList<PathinfosBean>();
            in.readList(this.pathinfos, PathinfosBean.class.getClassLoader());
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

    public TrackDetailModel() {
    }

    protected TrackDetailModel(Parcel in) {
        this.message = in.readString();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        this.status = in.readString();
    }

    public static final Parcelable.Creator<TrackDetailModel> CREATOR = new Parcelable.Creator<TrackDetailModel>() {
        @Override
        public TrackDetailModel createFromParcel(Parcel source) {
            return new TrackDetailModel(source);
        }

        @Override
        public TrackDetailModel[] newArray(int size) {
            return new TrackDetailModel[size];
        }
    };
}
