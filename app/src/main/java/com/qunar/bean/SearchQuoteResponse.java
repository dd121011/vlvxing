package com.qunar.bean;

import java.util.List;

/**
 * Created by wjjunjjun.wang on 2017/7/26.
 */
public class SearchQuoteResponse {

    private int code;
    private String message;//返回信息
    private long createTime;//时间戳
    private Result result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result {
        private String date;
        private List<Vendor> vendors;
        private String depCode;
        private String arrCode;
        private String code;//航班号
        private String carrier;//航司
        private String btime;//出发时间
        private String etime;//到达时间
        private String com;//航班公司
        private String depAirport;//出发机场
        private String arrAirport;//到达机场
        private String correct;//准点率
        private String meal;//true表示有餐食
        private String distance;//航程
        private String flightType;//机型

        public String getEtime() {
            return etime;
        }

        public void setEtime(String etime) {
            this.etime = etime;
        }

        public String getDepAirport() {
            return depAirport;
        }

        public void setDepAirport(String depAirport) {
            this.depAirport = depAirport;
        }

        public String getArrAirport() {
            return arrAirport;
        }

        public void setArrAirport(String arrAirport) {
            this.arrAirport = arrAirport;
        }

        public String getCorrect() {
            return correct;
        }

        public void setCorrect(String correct) {
            this.correct = correct;
        }

        public String getMeal() {
            return meal;
        }

        public void setMeal(String meal) {
            this.meal = meal;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getFlightType() {
            return flightType;
        }

        public void setFlightType(String flightType) {
            this.flightType = flightType;
        }

        public String getCom() {
            return com;
        }

        public void setCom(String com) {
            this.com = com;
        }

        public List<Vendor> getVendors() {
            return vendors;
        }

        public void setVendors(List<Vendor> vendors) {
            this.vendors = vendors;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDepCode() {
            return depCode;
        }

        public void setDepCode(String depCode) {
            this.depCode = depCode;
        }

        public String getArrCode() {
            return arrCode;
        }

        public void setArrCode(String arrCode) {
            this.arrCode = arrCode;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCarrier() {
            return carrier;
        }

        public void setCarrier(String carrier) {
            this.carrier = carrier;
        }

        public String getBtime() {
            return btime;
        }

        public void setBtime(String btime) {
            this.btime = btime;
        }
    }

}
