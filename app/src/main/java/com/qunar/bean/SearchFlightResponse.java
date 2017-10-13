package com.qunar.bean;

import java.util.List;

/**
 * Created by wjjunjjun.wang on 2017/7/26.
 */
public class SearchFlightResponse {

    private int code;//返回码
    private String message;//返回信息
    private long createTime;//时间戳
    private Result result;//应用返回

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
        private int total;//航班数
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
        private String dpt;//出发机场三字码
        private String arr;//到达机场三字码
        private String dptAriport;//出发机场
        private String dptTerminal;//到达机场
        private String arrAirport;//到达机场
        private String arrTerminal;//到达航站楼
        private String dptTime;//出发时间
        private String arrTime;//到达时间
        private String flightNum;//航班号
        private String barePrice;//销售价

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
