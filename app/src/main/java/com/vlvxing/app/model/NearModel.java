package com.vlvxing.app.model;

import java.util.List;

/**
 * Created by Administrator on 2017/6/8 0008.
 */

public class NearModel {


    /**
     * message : 操作成功!
     * data : [{"businessid":2,"pathlng":"116.484951","pathlat":"39.913365","areaid":110100,"businessname":"是非得失","descraption":"地地道道的","businesspic":null,"tel":"13655289885","isdelete":0,"businessproject":null},{"businessid":1,"pathlng":"116.482551","pathlat":"39.914365","areaid":110100,"businessname":"商家无敌1","descraption":"啊啊啊","businesspic":null,"tel":"13686549886","isdelete":0,"businessproject":null}]
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
         * businessid : 2
         * pathlng : 116.484951
         * pathlat : 39.913365
         * areaid : 110100
         * businessname : 是非得失
         * descraption : 地地道道的
         * businesspic : null
         * tel : 13655289885
         * isdelete : 0
         * businessproject : null
         */

        private String businessid;
        private String pathlng;
        private String pathlat;
        private String areaid;
        private String businessname;
        private String descraption;
        private String businesspic;
        private String tel;
        private String isdelete;
        private String businessproject;

        public String getBusinessid() {
            return businessid;
        }

        public void setBusinessid(String businessid) {
            this.businessid = businessid;
        }

        public String getPathlng() {
            return pathlng;
        }

        public void setPathlng(String pathlng) {
            this.pathlng = pathlng;
        }

        public String getPathlat() {
            return pathlat;
        }

        public void setPathlat(String pathlat) {
            this.pathlat = pathlat;
        }

        public String getAreaid() {
            return areaid;
        }

        public void setAreaid(String areaid) {
            this.areaid = areaid;
        }

        public String getBusinessname() {
            return businessname;
        }

        public void setBusinessname(String businessname) {
            this.businessname = businessname;
        }

        public String getDescraption() {
            return descraption;
        }

        public void setDescraption(String descraption) {
            this.descraption = descraption;
        }

        public String getBusinesspic() {
            return businesspic;
        }

        public void setBusinesspic(String businesspic) {
            this.businesspic = businesspic;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getIsdelete() {
            return isdelete;
        }

        public void setIsdelete(String isdelete) {
            this.isdelete = isdelete;
        }

        public String getBusinessproject() {
            return businessproject;
        }

        public void setBusinessproject(String businessproject) {
            this.businessproject = businessproject;
        }
    }
}
