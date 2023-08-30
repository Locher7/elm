package com.neusoft.elm.po;

public class Business {
	
	private Integer businessId;
	private String businessName;
	private String businessAddress;
	private String businessExplain;
	private String businessImg;
	private Integer orderTypeId;
	private double starPrice; //起送费
	private double deliveryPrice; //配送费
	private String remarks;
	
	public String getBusinessAddress() {
		return businessAddress;
	}
	
	public String getBusinessExplain() {
		return businessExplain;
	}
	
	public Integer getBusinessId() {
		return businessId;
	}
	
	public String getBusinessImg() {
		return businessImg;
	}
	
	public String getBusinessName() {
		return businessName;
	}
	
	public double getDeliveryPrice() {
		return deliveryPrice;
	}
	
	public Integer getOrderTypeId() {
		return orderTypeId;
	}
	
	public String getRemarks() {
		return remarks;
	}
	
	public double getStarPrice() {
		return starPrice;
	}
	
	public void setBusinessAddress(String businessAddress) {
		this.businessAddress = businessAddress;
	}
	
	public void setBusinessExplain(String businessExplain) {
		this.businessExplain = businessExplain;
	}
	
	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	public void setBusinessImg(String businessImg) {
		this.businessImg = businessImg;
	}
	
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	
	public void setDeliveryPrice(double deliveryPrice) {
		this.deliveryPrice = deliveryPrice;
	}
	
	public void setOrderTypeId(Integer orderTypeId) {
		this.orderTypeId = orderTypeId;
	}
	
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public void setStarPrice(double starPrice) {
		this.starPrice = starPrice;
	}
	
}
