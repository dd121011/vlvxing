package com.qunar.bean;


import java.util.List;


public class BookingResponseParam {

	
	private int code;
	private String message;
	private long createTime;
	private Result result;

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

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	
		public class Result {

			private String bookingStatus; 
			  private String errMsg; 
			  private String bookingTag; 
			  private List<FlightInfo1> flightInfo; 
			  private PriceInfo priceInfo;
			  private ExtInfo extInfo; 
			  private TgqShowData tgqShowData; 
			  private String ticketTime; 
			  private BookingIns bookingIns;
			  private ExpressInfo expressInfo; 

			  public String getBookingStatus(){
			  	return bookingStatus; 
			  }
			  public void setBookingStatus(String input){
			  	 this.bookingStatus = input;
			  }
			  public String getErrMsg(){
			  	return errMsg; 
			  }
			  public void setErrMsg(String input){
			  	 this.errMsg = input;
			  }
			  public String getBookingTag(){
			  	return bookingTag; 
			  }
			  public void setBookingTag(String input){
			  	 this.bookingTag = input;
			  }
			  public List<FlightInfo1> getFlightInfo(){
			  	return flightInfo; 
			  }
			  public void setFlightInfo(List<FlightInfo1> input){
			  	 this.flightInfo = input;
			  }
			
			  public PriceInfo getPriceInfo() {
				return priceInfo;
			}
			public void setPriceInfo(PriceInfo priceInfo) {
				this.priceInfo = priceInfo;
			}
			public ExtInfo getExtInfo(){
			  	return extInfo; 
			  }
			  public void setExtInfo(ExtInfo input){
			  	 this.extInfo = input;
			  }
			  public TgqShowData getTgqShowData(){
			  	return tgqShowData; 
			  }
			  public void setTgqShowData(TgqShowData input){
			  	 this.tgqShowData = input;
			  }
			  public String getTicketTime(){
			  	return ticketTime; 
			  }
			  public void setTicketTime(String input){
			  	 this.ticketTime = input;
			  }
		     public BookingIns getBookingIns(){
			  	return bookingIns; 
			  }
			  public void setBookingIns(BookingIns input){
			  	 this.bookingIns = input;
			  }
			  public ExpressInfo getExpressInfo(){
			  	return expressInfo; 
			  }
			  public void setExpressInfo(ExpressInfo input){
			  	 this.expressInfo = input;
			  }
		}
        
		class BookingIns{
			
			private int adultMax;
			private int childMax;
			private int babyMax;
			private int adultMin;
			private int childMin;
			private int babyMin;
			private String note;
			private String name;
			private String price;
			private String url;
			private String phone;
			public int getAdultMax() {
				return adultMax;
			}
			public void setAdultMax(int adultMax) {
				this.adultMax = adultMax;
			}
			public int getChildMax() {
				return childMax;
			}
			public void setChildMax(int childMax) {
				this.childMax = childMax;
			}
			public int getBabyMax() {
				return babyMax;
			}
			public void setBabyMax(int babyMax) {
				this.babyMax = babyMax;
			}
			public int getAdultMin() {
				return adultMin;
			}
			public void setAdultMin(int adultMin) {
				this.adultMin = adultMin;
			}
			public int getChildMin() {
				return childMin;
			}
			public void setChildMin(int childMin) {
				this.childMin = childMin;
			}
			public int getBabyMin() {
				return babyMin;
			}
			public void setBabyMin(int babyMin) {
				this.babyMin = babyMin;
			}
			public String getNote() {
				return note;
			}
			public void setNote(String note) {
				this.note = note;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public String getPrice() {
				return price;
			}
			public void setPrice(String price) {
				this.price = price;
			}
			public String getUrl() {
				return url;
			}
			public void setUrl(String url) {
				this.url = url;
			}
			public String getPhone() {
				return phone;
			}
			public void setPhone(String phone) {
				this.phone = phone;
			}
			
		}
	}

	


