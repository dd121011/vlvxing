package com.qunar.bean.refundSearch;

import java.util.List;

public class RefundSearchResponseParam {

	private List<BaseOrderInfo> baseOrderInfo;
	private List<RefundPassengerPriceInfoList> infoList;
	public List<BaseOrderInfo> getBaseOrderInfo() {
		return baseOrderInfo;
	}
	public void setBaseOrderInfo(List<BaseOrderInfo> baseOrderInfo) {
		this.baseOrderInfo = baseOrderInfo;
	}
	public List<RefundPassengerPriceInfoList> getInfoList() {
		return infoList;
	}
	public void setInfoList(List<RefundPassengerPriceInfoList> infoList) {
		this.infoList = infoList;
	}
	
	
}
