package com.qunar.bean;

import java.util.List;

/**
 * Created by wjjunjjun.wang on 2017/7/26.
 */
public class SearchQuoteResponse {

    private int code;
    private Result result;

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
        private String code;
        private String carrier;
        private String btime;

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
