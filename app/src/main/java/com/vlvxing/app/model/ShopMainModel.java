package com.vlvxing.app.model;

import java.util.List;

/**
 * Created by Administrator on 2017/6/9 0009.
 * 店铺主页
 */

public class ShopMainModel {


    /**
     * message : 操作成功!
     * data : {"businessid":null,"pathlng":null,"pathlat":null,"areaid":null,"cityid":null,"businessname":null,"descraption":null,"businesspic":null,"createtime":null,"tel":null,"isdelete":null,"businessproject":null,"projects":[{"businessprojectid":2,"businessid":2,"isdelete":0,"descraption":"说法<img src=\"http://localhost:80/lvyous_upload/jsp/image/20170612/20170612140435_866.png\" alt=\"\" /><img src=\"http://localhost:80/lvyous_upload/jsp/image/20170612/20170612140435_653.jpg\" alt=\"\" /><img src=\"http://localhost:80/lvyous_upload/jsp/image/20170612/20170612140435_154.jpg\" alt=\"\" /><img src=\"http://localhost:80/lvyous_upload/jsp/image/20170612/20170612140435_131.jpg\" alt=\"\" /><img src=\"http://localhost:80/lvyous_upload/jsp/image/20170612/20170612140436_645.jpg\" alt=\"\" /><img src=\"http://localhost:80/lvyous_upload/jsp/image/20170612/20170612140436_286.jpg\" alt=\"\" /><img src=\"http://localhost:80/lvyous_upload/jsp/image/20170612/20170612140436_557.jpg\" alt=\"\" />"}]}
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

    public static class DataBean {
        /**
         * businessid : null
         * pathlng : null
         * pathlat : null
         * areaid : null
         * cityid : null
         * businessname : null
         * descraption : null
         * businesspic : null
         * createtime : null
         * tel : null
         * isdelete : null
         * businessproject : null
         * projects : [{"businessprojectid":2,"businessid":2,"isdelete":0,"descraption":"说法<img src=\"http://localhost:80/lvyous_upload/jsp/image/20170612/20170612140435_866.png\" alt=\"\" /><img src=\"http://localhost:80/lvyous_upload/jsp/image/20170612/20170612140435_653.jpg\" alt=\"\" /><img src=\"http://localhost:80/lvyous_upload/jsp/image/20170612/20170612140435_154.jpg\" alt=\"\" /><img src=\"http://localhost:80/lvyous_upload/jsp/image/20170612/20170612140435_131.jpg\" alt=\"\" /><img src=\"http://localhost:80/lvyous_upload/jsp/image/20170612/20170612140436_645.jpg\" alt=\"\" /><img src=\"http://localhost:80/lvyous_upload/jsp/image/20170612/20170612140436_286.jpg\" alt=\"\" /><img src=\"http://localhost:80/lvyous_upload/jsp/image/20170612/20170612140436_557.jpg\" alt=\"\" />"}]
         */

        private String businessid;
        private String pathlng;
        private String pathlat;
        private String areaid;
        private String cityid;
        private String businessname;
        private String descraption;
        private String businesspic;
        private String createtime;
        private String tel;
        private String isdelete;
        private String businessproject;
        private List<ProjectsBean> projects;

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

        public String getCityid() {
            return cityid;
        }

        public void setCityid(String cityid) {
            this.cityid = cityid;
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

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
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

        public List<ProjectsBean> getProjects() {
            return projects;
        }

        public void setProjects(List<ProjectsBean> projects) {
            this.projects = projects;
        }

        public static class ProjectsBean {
            /**
             * businessprojectid : 2
             * businessid : 2
             * isdelete : 0
             * descraption : 说法<img src="http://localhost:80/lvyous_upload/jsp/image/20170612/20170612140435_866.png" alt="" /><img src="http://localhost:80/lvyous_upload/jsp/image/20170612/20170612140435_653.jpg" alt="" /><img src="http://localhost:80/lvyous_upload/jsp/image/20170612/20170612140435_154.jpg" alt="" /><img src="http://localhost:80/lvyous_upload/jsp/image/20170612/20170612140435_131.jpg" alt="" /><img src="http://localhost:80/lvyous_upload/jsp/image/20170612/20170612140436_645.jpg" alt="" /><img src="http://localhost:80/lvyous_upload/jsp/image/20170612/20170612140436_286.jpg" alt="" /><img src="http://localhost:80/lvyous_upload/jsp/image/20170612/20170612140436_557.jpg" alt="" />
             */

            private String businessprojectid;
            private String businessid;
            private String isdelete;
            private String descraption;

            public String getBusinessprojectid() {
                return businessprojectid;
            }

            public void setBusinessprojectid(String businessprojectid) {
                this.businessprojectid = businessprojectid;
            }

            public String getBusinessid() {
                return businessid;
            }

            public void setBusinessid(String businessid) {
                this.businessid = businessid;
            }

            public String getIsdelete() {
                return isdelete;
            }

            public void setIsdelete(String isdelete) {
                this.isdelete = isdelete;
            }

            public String getDescraption() {
                return descraption;
            }

            public void setDescraption(String descraption) {
                this.descraption = descraption;
            }
        }
    }
}
