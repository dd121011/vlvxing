package com.vlvxing.app.model;

/**
 * Created by Administrator on 2017/5/25 0025.
 */

public class CommonModel {


    /**
     * evaluateid : 2
     * productid : 3
     * evaluatelevel : 2
     * evaluatecontent : 中评1
     * createtime : 1495519483000
     * userid : 1
     * username : null
     * evaluatepic : http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/1.jpg,http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/2.jpg,http://handongkeji.com:8090/lvyous_upload/user/2017-05-21/3.jpg
     * isdelete : 0
     */

    private String evaluateid;
    private String productid;
    private String evaluatelevel;
    private String evaluatecontent;
    private String createtime;
    private String userid;
    private String username;
    private String evaluatepic;
    private String isdelete;

    public CommonModel(String evaluateid, String productid, String evaluatelevel, String evaluatecontent, String createtime, String userid, String username, String evaluatepic, String isdelete) {
        this.evaluateid = evaluateid;
        this.productid = productid;
        this.evaluatelevel = evaluatelevel;
        this.evaluatecontent = evaluatecontent;
        this.createtime = createtime;
        this.userid = userid;
        this.username = username;
        this.evaluatepic = evaluatepic;
        this.isdelete = isdelete;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
