package com.neusoft.elmboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neusoft.elmboot.mapper.UserMapper;
import com.neusoft.elmboot.po.User;
import com.neusoft.elmboot.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public User getUserByIdByPass(User user) {
		return userMapper.getUserByIdByPass(user);
	}
	
	@Override
	public int getUserById(String userId) {
		return userMapper.getUserById(userId);
	}
	
	@Override
	public int saveUser(User user) {
		return userMapper.saveUser(user);
	}
	
	@Override
	public int editPasswordByUserId(String userId, String password) {
		User user = userMapper.getUserMessById(userId);
		user.setPassword(password);
		return userMapper.updateUserPass(user);
	}
	
	@Override
	public int editUserNamedByUserId(String userId, String userName) {
		User user = userMapper.getUserMessById(userId);
		user.setUserName(userName);
		return userMapper.updateUserName(user);
	}
	
	@Override
	public int editUserImgByUserId(String userId, String userImg) {
		User user = userMapper.getUserMessById(userId);
		user.setUserImg(userImg);
		return userMapper.updateUserImg(user);
	}

}
