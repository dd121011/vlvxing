package com.vlvxing.app.model;

import java.util.List;

/**
 * Created by Administrator on 2017/5/23 0023.
 */

public class HotAreaModel {


    /**
     * message : 操作成功!
     * data : [{"hotareaid":1,"areaid":110108,"areaname":"海淀区","isforeign":1,"isstop":0,"pic":"http://handongkeji.com:8090/lvyous_upload/user/2017-05-22/YK1495430452860_mid.jpg"},{"hotareaid":2,"areaid":120100,"areaname":"天津市","isforeign":1,"isstop":0,"pic":"http://handongkeji.com:8090/lvyous_upload/user/2017-05-22/YK1495430452860_mid.jpg"},{"hotareaid":3,"areaid":120109,"areaname":"加利福利亚","isforeign":2,"isstop":0,"pic":"http://handongkeji.com:8090/lvyous_upload/user/2017-05-22/YK1495430452860_mid.jpg"}]
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
         * hotareaid : 1
         * areaid : 110108
         * areaname : 海淀区
         * isforeign : 1
         * isstop : 0
         * pic : http://handongkeji.com:8090/lvyous_upload/user/2017-05-22/YK1495430452860_mid.jpg
         */

        private String hotareaid;
        private String areaid;
        private String areaname;
        private String isforeign;
        private String isstop;
        private String pic;

        public String getHotareaid() {
            return hotareaid;
        }

        public void setHotareaid(String hotareaid) {
            this.hotareaid = hotareaid;
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

        public String getIsforeign() {
            return isforeign;
        }

        public void setIsforeign(String isforeign) {
            this.isforeign = isforeign;
        }

        public String getIsstop() {
            return isstop;
        }

        public void setIsstop(String isstop) {
            this.isstop = isstop;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }
}
