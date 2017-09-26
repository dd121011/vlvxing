package com.vlvxing.app.model;

import java.util.List;

/**
 * Created by Administrator on 2017/5/22 0022.
 */

public class SysadModel {


    /**
     * message : 操作成功!
     * data : [{"adid":2,"areaid":110000,"adtitle":null,"adpostion":null,"adtype":1,"adcontents":null,"adpicture":"http://handongkeji.com:8090/mingshiwangupload/common/2017-04-10/LC1491822847854.jpg","adredirection":"https://www.baidu.com/","categoryid":0},{"adid":3,"areaid":110000,"adtitle":null,"adpostion":null,"adtype":1,"adcontents":null,"adpicture":"http://handongkeji.com:8090/mingshiwangupload/common/2017-04-10/WZ1491822859917.jpg","adredirection":"https://www.baidu.com/","categoryid":0},{"adid":12,"areaid":120000,"adtitle":null,"adpostion":null,"adtype":2,"adcontents":null,"adpicture":"http://handongkeji.com:8090/mingshiwangupload/common/2017-04-10/KR1491823589935.png","adredirection":null,"categoryid":0},{"adid":13,"areaid":120000,"adtitle":null,"adpostion":null,"adtype":2,"adcontents":null,"adpicture":"http://handongkeji.com:8090/mingshiwangupload/common/2017-04-10/KR1491823589935.png","adredirection":null,"categoryid":0}]
     * status : 1
     */

    private String message;
    private int status;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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
         * adid : 2
         * areaid : 110000
         * adtitle : null
         * adpostion : null
         * adtype : 1
         * adcontents : null
         * adpicture : http://handongkeji.com:8090/mingshiwangupload/common/2017-04-10/LC1491822847854.jpg
         * adredirection : https://www.baidu.com/
         * categoryid : 0
         */

        private String adid;
        private int areaid;
        private String adtitle;
        private String adpostion;
        private String adtype;
        private String adcontents;
        private String adpicture;
        private String adredirection;
        private String categoryid;

        public String getAdid() {
            return adid;
        }

        public void setAdid(String adid) {
            this.adid = adid;
        }

        public int getAreaid() {
            return areaid;
        }

        public void setAreaid(int areaid) {
            this.areaid = areaid;
        }

        public Object getAdtitle() {
            return adtitle;
        }

        public void setAdtitle(String adtitle) {
            this.adtitle = adtitle;
        }

        public String getAdpostion() {
            return adpostion;
        }

        public void setAdpostion(String adpostion) {
            this.adpostion = adpostion;
        }

        public String getAdtype() {
            return adtype;
        }

        public void setAdtype(String adtype) {
            this.adtype = adtype;
        }

        public String getAdcontents() {
            return adcontents;
        }

        public void setAdcontents(String adcontents) {
            this.adcontents = adcontents;
        }

        public String getAdpicture() {
            return adpicture;
        }

        public void setAdpicture(String adpicture) {
            this.adpicture = adpicture;
        }

        public String getAdredirection() {
            return adredirection;
        }

        public void setAdredirection(String adredirection) {
            this.adredirection = adredirection;
        }

        public String getCategoryid() {
            return categoryid;
        }

        public void setCategoryid(String categoryid) {
            this.categoryid = categoryid;
        }
    }
}
