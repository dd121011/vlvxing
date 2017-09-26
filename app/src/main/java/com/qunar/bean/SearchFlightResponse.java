package com.qunar.bean;

import java.util.List;

/**
 * Created by wjjunjjun.wang on 2017/7/26.
 */
public class SearchFlightResponse {

    private int code;
    private String message;
    private long createTime;
    private Result result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

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

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result {
        private int total;
        private List<FlightInfo> flightInfos;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<FlightInfo> getFlightInfos() {
            return flightInfos;
        }

        public void setFlightInfos(List<FlightInfo> flightInfos) {
            this.flightInfos = flightInfos;
        }
    }

    public static class FlightInfo {
        private String dpt;
        private String arr;
        private String dptAriport;
        private String dptTerminal;
        private String arrAirport;
        private String arrTerminal;
        private String dptTime;
        private String arrTime;
        private String flightNum;
        private String barePrice;

        public String getDpt() {
            return dpt;
        }

        public void setDpt(String dpt) {
            this.dpt = dpt;
        }

        public String getArr() {
            return arr;
        }

        public void setArr(String arr) {
            this.arr = arr;
        }

        public String getDptAriport() {
            return dptAriport;
        }

        public void setDptAriport(String dptAriport) {
            this.dptAriport = dptAriport;
        }

        public String getDptTerminal() {
            return dptTerminal;
        }

        public void setDptTerminal(String dptTerminal) {
            this.dptTerminal = dptTerminal;
        }

        public String getArrAirport() {
            return arrAirport;
        }

        public void setArrAirport(String arrAirport) {
            this.arrAirport = arrAirport;
        }

        public String getArrTerminal() {
            return arrTerminal;
        }

        public void setArrTerminal(String arrTerminal) {
            this.arrTerminal = arrTerminal;
        }

        public String getDptTime() {
            return dptTime;
        }

        public void setDptTime(String dptTime) {
            this.dptTime = dptTime;
        }

        public String getArrTime() {
            return arrTime;
        }

        public void setArrTime(String arrTime) {
            this.arrTime = arrTime;
        }

        public String getFlightNum() {
            return flightNum;
        }

        public void setFlightNum(String flightNum) {
            this.flightNum = flightNum;
        }

        public String getBarePrice() {
            return barePrice;
        }

        public void setBarePrice(String barePrice) {
            this.barePrice = barePrice;
        }
    }

}
