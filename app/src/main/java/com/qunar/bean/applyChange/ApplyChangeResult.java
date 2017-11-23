package com.qunar.bean.applyChange;

import java.util.List;

/**
 * 改签支付
 * Created by Zophar on 2017/11/23.
 */

public  class ApplyChangeResult {

    /**
     * code : 0
     * message : SUCCESS
     * createTime : 1505114963107
     * result : {"code":0,"results":[{"orderNo":"ppg170906161649119p2922812cash","orderDate":"20170911072922","pmCode":"DAIKOU","bankCode":"QUNARPAY","payId":"newttszzgq,DK201709111529228477;","payAmount":"690.00","payStatus":"SUCCESS","payTime":"20170911152922","errCode":"0","errMsg":""}]}
     */

    private int code;
    private String message;
    private long createTime;
    private ResultBean result;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * code : 0
         * results : [{"orderNo":"ppg170906161649119p2922812cash","orderDate":"20170911072922","pmCode":"DAIKOU","bankCode":"QUNARPAY","payId":"newttszzgq,DK201709111529228477;","payAmount":"690.00","payStatus":"SUCCESS","payTime":"20170911152922","errCode":"0","errMsg":""}]
         */

        private int code;
        private List<ResultsBean> results;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public List<ResultsBean> getResults() {
            return results;
        }

        public void setResults(List<ResultsBean> results) {
            this.results = results;
        }

        public static class ResultsBean {
            /**
             * orderNo : ppg170906161649119p2922812cash
             * orderDate : 20170911072922
             * pmCode : DAIKOU
             * bankCode : QUNARPAY
             * payId : newttszzgq,DK201709111529228477;
             * payAmount : 690.00
             * payStatus : SUCCESS
             * payTime : 20170911152922
             * errCode : 0
             * errMsg :
             */

            private String orderNo;
            private String orderDate;
            private String pmCode;
            private String bankCode;
            private String payId;
            private String payAmount;
            private String payStatus;
            private String payTime;
            private String errCode;
            private String errMsg;

            public String getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
            }

            public String getOrderDate() {
                return orderDate;
            }

            public void setOrderDate(String orderDate) {
                this.orderDate = orderDate;
            }

            public String getPmCode() {
                return pmCode;
            }

            public void setPmCode(String pmCode) {
                this.pmCode = pmCode;
            }

            public String getBankCode() {
                return bankCode;
            }

            public void setBankCode(String bankCode) {
                this.bankCode = bankCode;
            }

            public String getPayId() {
                return payId;
            }

            public void setPayId(String payId) {
                this.payId = payId;
            }

            public String getPayAmount() {
                return payAmount;
            }

            public void setPayAmount(String payAmount) {
                this.payAmount = payAmount;
            }

            public String getPayStatus() {
                return payStatus;
            }

            public void setPayStatus(String payStatus) {
                this.payStatus = payStatus;
            }

            public String getPayTime() {
                return payTime;
            }

            public void setPayTime(String payTime) {
                this.payTime = payTime;
            }

            public String getErrCode() {
                return errCode;
            }

            public void setErrCode(String errCode) {
                this.errCode = errCode;
            }

            public String getErrMsg() {
                return errMsg;
            }

            public void setErrMsg(String errMsg) {
                this.errMsg = errMsg;
            }
        }
    }
}
