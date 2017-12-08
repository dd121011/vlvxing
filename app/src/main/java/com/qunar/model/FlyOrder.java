package com.qunar.model;

import com.qunar.bean.FlyPassenger;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class FlyOrder implements Serializable{
    private String orderid;

    private String orderno;

    private String airlinename;

    private String airlinecode;

    private String flightnum;

    private String deptcity;

    private String arricity;

    private String deptdate;

    private String depttime;

    private String arritime;

    private String cabin;

    private int nopayamount;//金额

    private String deptairportcity;

    private String arriairportcity;

    private int status;

    private String statusdesc;

    private Long userid;

    private Long createtime;

    private String flightTimes;

    private String clientsite;

    private List<FlyPassenger> passengers;

    private String phone;

    private String allowchange;		//是否允许签转
    private String cancharge;		//是否允许改签
    private String canrefund;	//是否允许退票

    public String getAllowchange() {
        return allowchange;
    }

    public void setAllowchange(String allowchange) {
        this.allowchange = allowchange;
    }

    public String getCancharge() {
        return cancharge;
    }

    public void setCancharge(String cancharge) {
        this.cancharge = cancharge;
    }

    public String getCanrefund() {
        return canrefund;
    }

    public void setCanrefund(String canrefund) {
        this.canrefund = canrefund;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<FlyPassenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<FlyPassenger> passengers) {
        this.passengers = passengers;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getAirlinename() {
        return airlinename;
    }

    public void setAirlinename(String airlinename) {
        this.airlinename = airlinename;
    }

    public String getAirlinecode() {
        return airlinecode;
    }

    public void setAirlinecode(String airlinecode) {
        this.airlinecode = airlinecode;
    }

    public String getFlightnum() {
        return flightnum;
    }

    public void setFlightnum(String flightnum) {
        this.flightnum = flightnum;
    }

    public String getDeptcity() {
        return deptcity;
    }

    public void setDeptcity(String deptcity) {
        this.deptcity = deptcity;
    }

    public String getArricity() {
        return arricity;
    }

    public void setArricity(String arricity) {
        this.arricity = arricity;
    }

    public String getDeptdate() {
        return deptdate;
    }

    public void setDeptdate(String deptdate) {
        this.deptdate = deptdate;
    }

    public String getDepttime() {
        return depttime;
    }

    public void setDepttime(String depttime) {
        this.depttime = depttime;
    }

    public String getArritime() {
        return arritime;
    }

    public void setArritime(String arritime) {
        this.arritime = arritime;
    }

    public String getCabin() {
        return cabin;
    }

    public void setCabin(String cabin) {
        this.cabin = cabin;
    }

    public int getNopayamount() {
        return nopayamount;
    }

    public void setNopayamount(int nopayamount) {
        this.nopayamount = nopayamount;
    }

    public String getDeptairportcity() {
        return deptairportcity;
    }

    public void setDeptairportcity(String deptairportcity) {
        this.deptairportcity = deptairportcity;
    }

    public String getArriairportcity() {
        return arriairportcity;
    }

    public void setArriairportcity(String arriairportcity) {
        this.arriairportcity = arriairportcity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusdesc() {
        return statusdesc;
    }

    public void setStatusdesc(String statusdesc) {
        this.statusdesc = statusdesc;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Long createtime) {
        this.createtime = createtime;
    }

    public String getFlightTimes() {
        return flightTimes;
    }

    public void setFlightTimes(String flightTimes) {
        this.flightTimes = flightTimes;
    }

    public String getClientsite() {
        return clientsite;
    }

    public void setClientsite(String clientsite) {
        this.clientsite = clientsite;
    }

    @Override
    public String toString() {
        return "FlyOrder{" +
                "orderid='" + orderid + '\'' +
                ", orderno='" + orderno + '\'' +
                ", airlinename='" + airlinename + '\'' +
                ", airlinecode='" + airlinecode + '\'' +
                ", flightnum='" + flightnum + '\'' +
                ", deptcity='" + deptcity + '\'' +
                ", arricity='" + arricity + '\'' +
                ", deptdate='" + deptdate + '\'' +
                ", depttime='" + depttime + '\'' +
                ", arritime='" + arritime + '\'' +
                ", cabin='" + cabin + '\'' +
                ", nopayamount=" + nopayamount +
                ", deptairportcity='" + deptairportcity + '\'' +
                ", arriairportcity='" + arriairportcity + '\'' +
                ", status=" + status +
                ", statusdesc='" + statusdesc + '\'' +
                ", userid=" + userid +
                ", createtime=" + createtime +
                ", flightTimes='" + flightTimes + '\'' +
                ", clientsite='" + clientsite + '\'' +
                ", passengers=" + passengers +
                ", phone='" + phone + '\'' +
                ", allowchange='" + allowchange + '\'' +
                ", cancharge='" + cancharge + '\'' +
                ", canrefund='" + canrefund + '\'' +
                '}';
    }
}