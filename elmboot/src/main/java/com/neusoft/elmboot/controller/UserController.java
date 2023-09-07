package com.neusoft.elmboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neusoft.elmboot.po.User;
import com.neusoft.elmboot.service.UserService;

@RestController
@RequestMapping("/UserController")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/getUserByIdByPass")
	public User getUserByIdByPass(User user) throws Exception{
		return userService.getUserByIdByPass(user);
	}
	
	@RequestMapping("/getUserById")
	public int getUserById(User user) throws Exception{
		return userService.getUserById(user.getUserId());
	}
	
	@RequestMapping("/saveUser")
	public int saveUser(User user) throws Exception{
		return userService.saveUser(user);
	}
	
	@RequestMapping("/editPasswordByUserId")
	public int editPasswordByUserId(User user) throws Exception {
		return userService.editPasswordByUserId(user.getUserId(), user.getPassword());
	}
	
	@RequestMapping("/editUserNameByUserId")
	public int editUserNameByUserId(User user) throws Exception {
		return userService.editUserNamedByUserId(user.getUserId(), user.getUserName());
	}
	
	@RequestMapping("/editUserImgByUserId")
	public int editUserImgByUserId(User user) throws Exception {
		return userService.editUserImgByUserId(user.getUserId(), user.getUserImg());
	}
}
