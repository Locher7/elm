package com.neusoft.po;

import java.util.List;

public class Business {
	
	private Integer businessId;
	private String businessName;
	private String businessAddress;
	private String businessExplain;
	private String businessImg;
	private Integer orderTypeId;
	private Integer starPrice; //起送费
	private Integer deliveryPrice; //配送费
	private String remarks;
	
	private List<Food> foodList;
	
	public List<Food> getFoodList() {
		return foodList;
	}

	public void setFoodList(List<Food> foodList) {
		this.foodList = foodList;
	}

	
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
	
	public Integer getOrderTypeId() {
		return orderTypeId;
	}
	
	public String getRemarks() {
		return remarks;
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
	
	
	public void setOrderTypeId(Integer orderTypeId) {
		this.orderTypeId = orderTypeId;
	}
	
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getStarPrice() {
		return starPrice;
	}

	public void setStarPrice(Integer starPrice) {
		this.starPrice = starPrice;
	}

	public Integer getDeliveryPrice() {
		return deliveryPrice;
	}

	public void setDeliveryPrice(Integer deliveryPrice) {
		this.deliveryPrice = deliveryPrice;
	}
	

}
