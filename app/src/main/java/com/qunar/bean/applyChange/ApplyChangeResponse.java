package com.qunar.bean.applyChange;

import java.util.List;

public class ApplyChangeResponse {


	/**
	 * code : 0
	 * message : SUCCESS
	 * createTime : 1511252140618
	 * result : [{"id":29320613,"name":"李海光","cardType":"NI","cardNum":"4102kPbps9Mo7I=539","ticketNum":"999-5908314020","birthday":"1988-02-04","gender":1,"changeApplyResult":{"success":true,"orderNo":"xep171117211816765","orderId":19009218,"createProduct":false,"gqId":5375220}}]
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
		 * id : 29320613
		 * name : 李海光
		 * cardType : NI
		 * cardNum : 4102kPbps9Mo7I=539
		 * ticketNum : 999-5908314020
		 * birthday : 1988-02-04
		 * gender : 1
		 * changeApplyResult : {"success":true,"orderNo":"xep171117211816765","orderId":19009218,"createProduct":false,"gqId":5375220}
		 */

		private int id;
		private String name;
		private String cardType;
		private String cardNum;
		private String ticketNum;
		private String birthday;
		private int gender;
		private ChangeApplyResultBean changeApplyResult;

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

		public ChangeApplyResultBean getChangeApplyResult() {
			return changeApplyResult;
		}

		public void setChangeApplyResult(ChangeApplyResultBean changeApplyResult) {
			this.changeApplyResult = changeApplyResult;
		}

		public static class ChangeApplyResultBean {
			/**
			 * success : true
			 * orderNo : xep171117211816765
			 * orderId : 19009218
			 * createProduct : false
			 * gqId : 5375220
			 */

			private boolean success;
			private String orderNo;
			private int orderId;
			private boolean createProduct;
			private int gqId;

			public boolean isSuccess() {
				return success;
			}

			public void setSuccess(boolean success) {
				this.success = success;
			}

			public String getOrderNo() {
				return orderNo;
			}

			public void setOrderNo(String orderNo) {
				this.orderNo = orderNo;
			}

			public int getOrderId() {
				return orderId;
			}

			public void setOrderId(int orderId) {
				this.orderId = orderId;
			}

			public boolean isCreateProduct() {
				return createProduct;
			}

			public void setCreateProduct(boolean createProduct) {
				this.createProduct = createProduct;
			}

			public int getGqId() {
				return gqId;
			}

			public void setGqId(int gqId) {
				this.gqId = gqId;
			}
		}
	}
}