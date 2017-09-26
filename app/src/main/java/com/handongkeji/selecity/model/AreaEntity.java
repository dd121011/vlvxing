package com.handongkeji.selecity.model;


public class AreaEntity{

	private String areaId; // 地区ID
	private String areaName; // 地区名
	private String pareantAreaId; // 父类ID
	private String areaorderby;

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getPareantAreaId() {
		return pareantAreaId;
	}

	public void setPareantAreaId(String pareantAreaId) {
		this.pareantAreaId = pareantAreaId;
	}

	public String getAreaorderby() {
		return areaorderby;
	}

	public void setAreaorderby(String areaorderby) {
		this.areaorderby = areaorderby;
	}

}
