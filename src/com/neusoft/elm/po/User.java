package com.neusoft.elm.po;

import sun.net.www.content.image.gif;

public class User {

	private String userId;
	private String password;
	private String userName;
	private Integer userSex;
	private String userImg;
	private Integer selTag;
	
	public String getPassword() {
		return password;
	}
	
	public Integer getSelTag() {
		return selTag;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public String getUserImg() {
		return userImg;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public Integer getUserSex() {
		return userSex;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setSelTag(Integer selTag) {
		this.selTag = selTag;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setUserSex(Integer userSex) {
		this.userSex = userSex;
	}
}
