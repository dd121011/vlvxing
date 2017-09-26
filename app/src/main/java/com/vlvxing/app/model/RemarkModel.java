package com.vlvxing.app.model;

import java.util.List;

/**
 * Created by Administrator on 2017/5/24 0024.
 */

public class RemarkModel {


    /**
     * type : 1
     * message : 操作成功!
     * data : {"goodCounts":4,"allCounts":5,"badCounts":0,"evaluates":[{"evaluateid":1,"productid":3,"evaluatelevel":1,"evaluatecontent":"好评1","createtime":1495519417000,"userid":1,"usernick":null,"userpic":null,"evaluatepic":"http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/1.jpg,http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/2.jpg,http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/3.jpg","isdelete":0},{"evaluateid":17,"productid":3,"evaluatelevel":1,"evaluatecontent":"好评1","createtime":1495519417000,"userid":1,"usernick":null,"userpic":null,"evaluatepic":"http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/1.jpg,http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/2.jpg,http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/3.jpg","isdelete":0},{"evaluateid":18,"productid":3,"evaluatelevel":1,"evaluatecontent":"好评3","createtime":1495519417000,"userid":1,"usernick":null,"userpic":null,"evaluatepic":"http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/1.jpg,http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/2.jpg,http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/3.jpg","isdelete":0},{"evaluateid":19,"productid":3,"evaluatelevel":1,"evaluatecontent":"好评4","createtime":1495519417000,"userid":1,"usernick":null,"userpic":null,"evaluatepic":"http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/1.jpg,http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/2.jpg,http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/3.jpg","isdelete":0}],"averageCounts":1}
     * status : 1
     */

    private String type;
    private String message;
    private DataBean data;
    private String status;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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
         * goodCounts : 4
         * allCounts : 5
         * badCounts : 0
         * evaluates : [{"evaluateid":1,"productid":3,"evaluatelevel":1,"evaluatecontent":"好评1","createtime":1495519417000,"userid":1,"usernick":null,"userpic":null,"evaluatepic":"http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/1.jpg,http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/2.jpg,http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/3.jpg","isdelete":0},{"evaluateid":17,"productid":3,"evaluatelevel":1,"evaluatecontent":"好评1","createtime":1495519417000,"userid":1,"usernick":null,"userpic":null,"evaluatepic":"http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/1.jpg,http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/2.jpg,http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/3.jpg","isdelete":0},{"evaluateid":18,"productid":3,"evaluatelevel":1,"evaluatecontent":"好评3","createtime":1495519417000,"userid":1,"usernick":null,"userpic":null,"evaluatepic":"http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/1.jpg,http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/2.jpg,http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/3.jpg","isdelete":0},{"evaluateid":19,"productid":3,"evaluatelevel":1,"evaluatecontent":"好评4","createtime":1495519417000,"userid":1,"usernick":null,"userpic":null,"evaluatepic":"http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/1.jpg,http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/2.jpg,http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/3.jpg","isdelete":0}]
         * averageCounts : 1
         */

        private String goodCounts;
        private String allCounts;
        private String badCounts;
        private String averageCounts;
        private List<EvaluatesBean> evaluates;

        public String getGoodCounts() {
            return goodCounts;
        }

        public void setGoodCounts(String goodCounts) {
            this.goodCounts = goodCounts;
        }

        public String getAllCounts() {
            return allCounts;
        }

        public void setAllCounts(String allCounts) {
            this.allCounts = allCounts;
        }

        public String getBadCounts() {
            return badCounts;
        }

        public void setBadCounts(String badCounts) {
            this.badCounts = badCounts;
        }

        public String getAverageCounts() {
            return averageCounts;
        }

        public void setAverageCounts(String averageCounts) {
            this.averageCounts = averageCounts;
        }

        public List<EvaluatesBean> getEvaluates() {
            return evaluates;
        }

        public void setEvaluates(List<EvaluatesBean> evaluates) {
            this.evaluates = evaluates;
        }

        public static class EvaluatesBean {
            /**
             * evaluateid : 1
             * productid : 3
             * evaluatelevel : 1
             * evaluatecontent : 好评1
             * createtime : 1495519417000
             * userid : 1
             * usernick : null
             * userpic : null
             * evaluatepic : http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/1.jpg,http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/2.jpg,http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/3.jpg
             * isdelete : 0
             */

            private String evaluateid;
            private String productid;
            private String evaluatelevel;
            private String evaluatecontent;
            private String createtime;
            private String userid;
            private String usernick;
            private String userpic;
            private String evaluatepic;
            private String isdelete;

            public String getEvaluateid() {
                return evaluateid;
            }

            public void setEvaluateid(String evaluateid) {
                this.evaluateid = evaluateid;
            }

            public String getProductid() {
                return productid;
            }

            public void setProductid(String productid) {
                this.productid = productid;
            }

            public String getEvaluatelevel() {
                return evaluatelevel;
            }

            public void setEvaluatelevel(String evaluatelevel) {
                this.evaluatelevel = evaluatelevel;
            }

            public String getEvaluatecontent() {
                return evaluatecontent;
            }

            public void setEvaluatecontent(String evaluatecontent) {
                this.evaluatecontent = evaluatecontent;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getUsernick() {
                return usernick;
            }

            public void setUsernick(String usernick) {
                this.usernick = usernick;
            }

            public String getUserpic() {
                return userpic;
            }

            public void setUserpic(String userpic) {
                this.userpic = userpic;
            }

            public String getEvaluatepic() {
                return evaluatepic;
            }

            public void setEvaluatepic(String evaluatepic) {
                this.evaluatepic = evaluatepic;
            }

            public String getIsdelete() {
                return isdelete;
            }

            public void setIsdelete(String isdelete) {
                this.isdelete = isdelete;
            }
        }
    }
}
