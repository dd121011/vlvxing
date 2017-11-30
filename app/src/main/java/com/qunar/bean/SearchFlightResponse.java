package com.qunar.bean;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

        public Map<String,String> airlineMap;//航空公司
        private Set<String> dptAirportSet;//起飞机场
        private Set<String> arrAirportSet;//落地机场


        public Set<String> getDptAirportSet() {
            return dptAirportSet;
        }

        public void setDptAirportSet(Set<String> dptAirportSet) {
            this.dptAirportSet = dptAirportSet;
        }

        public Set<String> getArrAirportSet() {
            return arrAirportSet;
        }

        public void setArrAirportSet(Set<String> arrAirportSet) {
            this.arrAirportSet = arrAirportSet;
        }

        public Map<String, String> getAirlineMap() {
            return airlineMap;
        }

        public void setAirlineMap(Map<String, String> airlineMap) {
            this.airlineMap = airlineMap;
        }
    }

    public static class FlightInfo {
        private String dpt;//出发机场三字码
        private String arr;//到达机场三字码
        private String dptAirport;//出发机场
        private String arrAirport;//到达机场
        private String dptTerminal;//到达机场
        private String arrTerminal;//到达航站楼
        private String dptTime;//出发时间
        private String arrTime;//到达时间
        private String flightNum;//航班号
        private String barePrice;//销售价
        private String flightTimes;//飞行时间
        private String carrier;//航司
        private String flightTypeFullName;//机型全名
        private String arf;//机建
        private String tof;//燃油
        private String airlineName;//航空公司

        public String getAirlineName() {
            return airlineName;
        }

        public void setAirlineName(String airlineName) {
            this.airlineName = airlineName;
        }

        public String getArrAirport() {
            return arrAirport;
        }

        public void setArrAirport(String arrAirport) {
            this.arrAirport = arrAirport;
        }

        public String getArf() {
            return arf;
        }

        public void setArf(String arf) {
            this.arf = arf;
        }

        public String getTof() {
            return tof;
        }

        public void setTof(String tof) {
            this.tof = tof;
        }

        public String getDptAirport() {
            return dptAirport;
        }

        public void setDptAirport(String dptAirport) {
            this.dptAirport = dptAirport;
        }

        public String getFlightTypeFullName() {
            return flightTypeFullName;
        }

        public void setFlightTypeFullName(String flightTypeFullName) {
            this.flightTypeFullName = flightTypeFullName;
        }

        public String getCarrier() {
            return carrier;
        }

        public void setCarrier(String carrier) {
            this.carrier = carrier;
        }


        public String getFlightTimes() {
            return flightTimes;
        }

        public void setFlightTimes(String flightTimes) {
            this.flightTimes = flightTimes;
        }

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


        public String getDptTerminal() {
            return dptTerminal;
        }

        public void setDptTerminal(String dptTerminal) {
            this.dptTerminal = dptTerminal;
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
