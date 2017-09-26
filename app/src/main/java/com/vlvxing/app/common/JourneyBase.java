package com.vlvxing.app.common;

import java.io.Serializable;

public class JourneyBase implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
//	private String coordinateid;
//	private String pathid;
//	private String address;
	private String pathlng;
	private String pathlat;
//	private String createtime;
//	private String coordinateorder;
	
//	public String getCoordinateid() {
//		return coordinateid;
//	}
//	public void setCoordinateid(String coordinateid) {
//		this.coordinateid = coordinateid;
//	}
//	public String getPathid() {
//		return pathid;
//	}
//	public void setPathid(String pathid) {
//		this.pathid = pathid;
//	}
//	public String getAddress() {
//		return address;
//	}
//	public void setAddress(String address) {
//		this.address = address;
//	}
	public String getPathlng() {
		return pathlng;
	}
	public void setPathlng(String pathlng) {
		this.pathlng = pathlng;
	}
	public String getPathlat() {
		return pathlat;
	}
	public void setPathlat(String pathlat) {
		this.pathlat = pathlat;
	}
//	public String getCreatetime() {
//		return createtime;
//	}
//	public void setCreatetime(String createtime) {
//		this.createtime = createtime;
//	}
//	public String getCoordinateorder() {
//		return coordinateorder;
//	}
//	public void setCoordinateorder(String coordinateorder) {
//		this.coordinateorder = coordinateorder;
//	}
//	@Override
//	public String toString() {
//		return "JourneyBase [coordinateid=" + coordinateid + ", pathid="
//				+ pathid + ", address=" + address + ", pathlng=" + pathlng
//				+ ", pathlat=" + pathlat + ", createtime=" + createtime
//				+ ", coordinateorder=" + coordinateorder + "]";
//	}
//	public JourneyBase(String coordinateid, String pathid, String address,
//					   String pathlng, String pathlat, String createtime,
//					   String coordinateorder) {
//		super();
//		this.coordinateid = coordinateid;
//		this.pathid = pathid;
//		this.address = address;
//		this.pathlng = pathlng;
//		this.pathlat = pathlat;
//		this.createtime = createtime;
//		this.coordinateorder = coordinateorder;
//	}
	public JourneyBase() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JourneyBase(String pathlng, String pathlat) {
		this.pathlng = pathlng;
		this.pathlat = pathlat;
	}
}
