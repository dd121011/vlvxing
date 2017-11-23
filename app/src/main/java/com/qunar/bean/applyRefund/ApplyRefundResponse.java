/**
  * Copyright 2017 bejson.com 
  */
 package com.qunar.bean.applyRefund;
import java.util.List;

/**
 * Auto-generated: 2017-11-19 22:27:47
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class ApplyRefundResponse {


    /**
     * code : 0
     * message : SUCCESS
     * createTime : 1511424863273
     * result : [{"refundSearchResult":null,"refundApplyResult":{"success":true,"reason":null,"volunteer":true,"noTicket":false},"changeSearchResult":null,"changeApplyResult":null,"id":29320613,"name":"李海光","cardType":"NI","cardNum":"4102kPbps9Mo7I=539","ticketNum":"999-5908314020","birthday":570931200000,"gender":1}]
     */

    private int code;
    private String message;
    private long createTime;
    private List<ResultBean> result;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * refundSearchResult : null
         * refundApplyResult : {"success":true,"reason":null,"volunteer":true,"noTicket":false}
         * changeSearchResult : null
         * changeApplyResult : null
         * id : 29320613
         * name : 李海光
         * cardType : NI
         * cardNum : 4102kPbps9Mo7I=539
         * ticketNum : 999-5908314020
         * birthday : 570931200000
         * gender : 1
         */

        private Object refundSearchResult;
        private RefundApplyResultBean refundApplyResult;
        private Object changeSearchResult;
        private Object changeApplyResult;
        private int id;
        private String name;
        private String cardType;
        private String cardNum;
        private String ticketNum;
        private String birthday;
        private int gender;

        public Object getRefundSearchResult() {
            return refundSearchResult;
        }

        public void setRefundSearchResult(Object refundSearchResult) {
            this.refundSearchResult = refundSearchResult;
        }

        public RefundApplyResultBean getRefundApplyResult() {
            return refundApplyResult;
        }

        public void setRefundApplyResult(RefundApplyResultBean refundApplyResult) {
            this.refundApplyResult = refundApplyResult;
        }

        public Object getChangeSearchResult() {
            return changeSearchResult;
        }

        public void setChangeSearchResult(Object changeSearchResult) {
            this.changeSearchResult = changeSearchResult;
        }

        public Object getChangeApplyResult() {
            return changeApplyResult;
        }

        public void setChangeApplyResult(Object changeApplyResult) {
            this.changeApplyResult = changeApplyResult;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCardType() {
            return cardType;
        }

        public void setCardType(String cardType) {
            this.cardType = cardType;
        }

        public String getCardNum() {
            return cardNum;
        }

        public void setCardNum(String cardNum) {
            this.cardNum = cardNum;
        }

        public String getTicketNum() {
            return ticketNum;
        }

        public void setTicketNum(String ticketNum) {
            this.ticketNum = ticketNum;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public static class RefundApplyResultBean {
            /**
             * success : true
             * reason : null
             * volunteer : true
             * noTicket : false
             */

            private boolean success;
            private Object reason;
            private boolean volunteer;
            private boolean noTicket;

            public boolean isSuccess() {
                return success;
            }

            public void setSuccess(boolean success) {
                this.success = success;
            }

            public Object getReason() {
                return reason;
            }

            public void setReason(Object reason) {
                this.reason = reason;
            }

            public boolean isVolunteer() {
                return volunteer;
            }

            public void setVolunteer(boolean volunteer) {
                this.volunteer = volunteer;
            }

            public boolean isNoTicket() {
                return noTicket;
            }

            public void setNoTicket(boolean noTicket) {
                this.noTicket = noTicket;
            }
        }
    }
}