package com.neusoft.elmboot.service;

import com.neusoft.elmboot.mapper.UserMapper;
import com.neusoft.elmboot.po.User;

public interface UserService {

	public User getUserByIdByPass(User user);
}
