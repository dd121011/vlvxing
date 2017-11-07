package com.qunar.model;

/**
 * 查询机票的条件
 * @author Administrator
 *
 */
public class CreateTicket {

	private String stop;//直飞经停
	
	private int flyTime;//时间段（1:0--6;2:6--12）
	
	private String carrier;//航空公司
	
	private String dptAirpot;//起飞机场
	
	private String arriAirport;//到达机场
	
	private String sort;//排序

	public String getStop() {
		return stop;
	}

	public void setStop(String stop) {
		this.stop = stop;
	}

	public int getFlyTime() {
		return flyTime;
	}

	public void setFlyTime(int flyTime) {
		this.flyTime = flyTime;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getDptAirpot() {
		return dptAirpot;
	}

	public void setDptAirpot(String dptAirpot) {
		this.dptAirpot = dptAirpot;
	}

	public String getArriAirport() {
		return arriAirport;
	}

	public void setArriAirport(String arriAirport) {
		this.arriAirport = arriAirport;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}


	
	
	
	
}
