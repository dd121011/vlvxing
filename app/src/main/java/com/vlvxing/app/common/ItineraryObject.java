package com.vlvxing.app.common;

import java.io.Serializable;

public class ItineraryObject implements Serializable {

	/**
	 * 生成序列
	 */
	public  static final long serialVersionUID = -8259065211116638563L;

	public int id;
	public String adress;
	public String city;
	public float distance;
	public double myLat;
	public double myLng;

	public String getAdress() {
		return adress;
	}

	public ItineraryObject(int id, String adress, double myLat, double myLng,
						   String city, float distance) {
		super();
		this.id = id;
		this.adress = adress;
		this.myLat = myLat;
		this.myLng = myLng;
		this.city = city;
		this.distance = distance;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public ItineraryObject(String adress, double myLat, double myLng,
						   String city, float distance) {
		super();
		this.adress = adress;
		this.myLat = myLat;
		this.myLng = myLng;
		this.city = city;
		this.distance = distance;
	}
	public float getDistance() {
		return distance;
	}
	public void setDistance(float distance) {
		this.distance = distance;
	}
	public String dynamictype() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public double getMyLat() {
		return myLat;
	}
	public void setMyLat(double myLat) {
		this.myLat = myLat;
	}
	public double getMyLng() {
		return myLng;
	}
	public void setMyLng(double myLng) {
		this.myLng = myLng;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public ItineraryObject() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ItineraryObject(String adress, double myLat, double myLng,
						   String city) {
		super();
		this.adress = adress;
		this.myLat = myLat;
		this.myLng = myLng;
		this.city = city;
	}
	@Override
	public String toString() {
		return "ItineraryObject [id=" + id + ", adress=" + adress + ", myLat="
				+ myLat + ", myLng=" + myLng + ", city=" + city + ", distance="
				+ distance + "]";
	}

}
