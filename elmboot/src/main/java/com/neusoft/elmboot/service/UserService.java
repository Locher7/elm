package com.neusoft.elmboot.service;

import com.neusoft.elmboot.mapper.UserMapper;
import com.neusoft.elmboot.po.User;

public interface UserService {

	public User getUserByIdByPass(User user);
	
	public int getUserById(String userId);
	
	public int saveUser(User user);
	
	public int editPasswordByUserId(String userId, String password);
	
	public int editUserNamedByUserId(String userId, String userName);
	
	public int editUserImgByUserId(String userId, String userImg);
	
	public User getUserMessById(String userId);
}
