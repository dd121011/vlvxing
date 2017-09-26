package com.vlvxing.app.model;

import java.util.List;

/**
 * Created by Administrator on 2017/5/27 0027.
 */

public class HotScienceModel {


    /**
     * message : 操作成功!
     * data : [{"hotspotsid":2,"areaid":110111,"areaname":"房山区","isforegin":1,"pic":"http://handongkeji.com:8090/lvyous_upload/user/2017-05-22/YK1495430452860_mid.jpg"},{"hotspotsid":1,"areaid":110101,"areaname":"东城区","isforegin":1,"pic":"http://handongkeji.com:8090/lvyous_upload/user/2017-05-22/YK1495430452860_mid.jpg"}]
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
         * hotspotsid : 2
         * areaid : 110111
         * areaname : 房山区
         * isforegin : 1
         * pic : http://handongkeji.com:8090/lvyous_upload/user/2017-05-22/YK1495430452860_mid.jpg
         */

        private String hotspotsid;
        private String areaid;
        private String areaname;
        private String isforegin;
        private String pic;

        public DataBean(String hotspotsid, String areaid, String areaname, String isforegin, String pic) {
            this.hotspotsid = hotspotsid;
            this.areaid = areaid;
            this.areaname = areaname;
            this.isforegin = isforegin;
            this.pic = pic;
        }

        public String getHotspotsid() {
            return hotspotsid;
        }

        public void setHotspotsid(String hotspotsid) {
            this.hotspotsid = hotspotsid;
        }

        public String getAreaid() {
            return areaid;
        }

        public void setAreaid(String areaid) {
            this.areaid = areaid;
        }

        public String getAreaname() {
            return areaname;
        }

        public void setAreaname(String areaname) {
            this.areaname = areaname;
        }

        public String getIsforegin() {
            return isforegin;
        }

        public void setIsforegin(String isforegin) {
            this.isforegin = isforegin;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }
}
