package com.neusoft.elm.controller;

import javax.servlet.http.HttpServletRequest;

import com.neusoft.elm.po.User;

public class UserController {
	
	public Object login(HttpServletRequest request) {
		String username = request.getParameter("username");
		System.out.println(username);
		System.out.println("do login");
		User user = new User();
		user.setUserId("2233");
		user.setUserName("zhang");
		return user;
	}
}
