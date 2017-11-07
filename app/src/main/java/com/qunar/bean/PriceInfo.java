package com.qunar.bean;

import java.util.List;
import java.util.Map;

	public class PriceInfo{
		
		 private String ticketPrice; 
		  private String price; 
		   private String barePrice; 
		  private String basePrice; 
		  private String discount; 
		  private String childPrice; 
		  private String childTicketPrice; 
		  private String babyPrice; 
		  private String babyTicketPrice; 
		  private String returnMoney; 
		  private String cutMoney; 
		  private String babyServiceFee; 
		  private Inventory inventory; 
		  private String tof; 
		  private String arf; 
		  private String childtof; 
		  private String prdTag; 
		  private String dtTag; 
		  private Map<String, List<PackageInfo>> priceTag; 
		  
	public String getTicketPrice() {
			return ticketPrice;
		}
		public void setTicketPrice(String ticketPrice) {
			this.ticketPrice = ticketPrice;
		}
		public String getPrice() {
			return price;
		}
		public void setPrice(String price) {
			this.price = price;
		}
		
		public String getBarePrice() {
			return barePrice;
		}
		public void setBarePrice(String barePrice) {
			this.barePrice = barePrice;
		}
	
		public String getBasePrice() {
			return basePrice;
		}
		public void setBasePrice(String basePrice) {
			this.basePrice = basePrice;
		}
		public String getDiscount() {
			return discount;
		}
		public void setDiscount(String discount) {
			this.discount = discount;
		}
		public String getChildPrice() {
			return childPrice;
		}
		public void setChildPrice(String childPrice) {
			this.childPrice = childPrice;
		}
		public String getChildTicketPrice() {
			return childTicketPrice;
		}
		public void setChildTicketPrice(String childTicketPrice) {
			this.childTicketPrice = childTicketPrice;
		}
		
		public String getBabyPrice() {
			return babyPrice;
		}
		public void setBabyPrice(String babyPrice) {
			this.babyPrice = babyPrice;
		}
		public String getBabyTicketPrice() {
			return babyTicketPrice;
		}
		public void setBabyTicketPrice(String babyTicketPrice) {
			this.babyTicketPrice = babyTicketPrice;
		}
	
		public String getReturnMoney() {
			return returnMoney;
		}
		public void setReturnMoney(String returnMoney) {
			this.returnMoney = returnMoney;
		}
		public String getCutMoney() {
			return cutMoney;
		}
		public void setCutMoney(String cutMoney) {
			this.cutMoney = cutMoney;
		}
		public String getBabyServiceFee() {
			return babyServiceFee;
		}
		public void setBabyServiceFee(String babyServiceFee) {
			this.babyServiceFee = babyServiceFee;
		}
		public Inventory getInventory() {
			return inventory;
		}
		public void setInventory(Inventory inventory) {
			this.inventory = inventory;
		}
		public String getTof() {
			return tof;
		}
		public void setTof(String tof) {
			this.tof = tof;
		}
		public String getArf() {
			return arf;
		}
		public void setArf(String arf) {
			this.arf = arf;
		}
		public String getChildtof() {
			return childtof;
		}
		public void setChildtof(String childtof) {
			this.childtof = childtof;
		}
		public String getPrdTag() {
			return prdTag;
		}
		public void setPrdTag(String prdTag) {
			this.prdTag = prdTag;
		}
		public String getDtTag() {
			return dtTag;
		}
		public void setDtTag(String dtTag) {
			this.dtTag = dtTag;
		}
		public Map<String, List<PackageInfo>> getPriceTag() {
			return priceTag;
		}
		public void setPriceTag(Map<String, List<PackageInfo>> priceTag) {
			this.priceTag = priceTag;
		}
		
		  
	
}
