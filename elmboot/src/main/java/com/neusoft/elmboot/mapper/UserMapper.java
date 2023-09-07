package com.neusoft.elmboot.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.neusoft.elmboot.po.Integration;
import com.neusoft.elmboot.po.User;

@Mapper
public interface UserMapper {

	@Select("select * from user where userId = #{userId}")
	public User getUserMessById(String userId);
	
	@Select("select * from user where userId = #{userId} and password = #{password}")
	public User getUserByIdByPass(User user);
	
	
	@Select("select count(*) from user where userId = #{userId}")
	public int getUserById(String userId);
	
	@Insert("insert into user values(#{userId}, #{password}, #{userName}, #{userSex}, null, 1)")
	public int saveUser(User user);
	
	@Update("update user set password = #{password} where userId = #{userId}")
	public int updateUserPass(User user);
	
	@Update("update user set userName = #{userName} where userId = #{userId}")
	public int updateUserName(User user);
	
	@Update("update user set userImg = #{userImg} where userId = #{userId}")
	public int updateUserImg(User user);
}
