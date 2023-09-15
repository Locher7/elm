package com.neusoft.elm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.neusoft.elm.dao.AdminDao;
import com.neusoft.elm.po.Admin;
import com.neusoft.elm.util.DBUtil;

public class AdminDaoImpl implements AdminDao{
	
	private Connection con = null;
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	
	// 将数据转化为对象实体
	@Override
	// 当返回值为一个对象时 命名为get
	public Admin getAdminByNameByPass(String adminName, String password) {
		// 当返回值为一个对象时 初始化为空指针 是否成功查询到目标只需判断是否为空指针
		Admin admin = null;
		// ？ 为参数的占位符
		String sql = "select * from admin where adminName = ? and password = ?";
		
		try {
			con = DBUtil.getConnection();  // 创建与数据库的连接
			pst = con.prepareStatement(sql); // 预编译sql语句
			pst.setString(1, adminName); // 将第一个参数替换为adminName变量（String类型）
			pst.setString(2, password); // 将第二个参数替换为password变量（String类型）
			rs = pst.executeQuery(); // 执行查询 保存结果到rs
			// 查到多个数据（一行） 进行遍历   封装到一个对象中
			while (rs.next()) {
				admin = new Admin();
				admin.setAdminId(rs.getInt("adminId")); //获取columnindex为“adminId”的int变量 
				admin.setAdminName(rs.getString("adminName"));
				admin.setPassword(rs.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 释放资源
			DBUtil.close(rs, pst, con);
		}
		
		return admin;
	}
	
	

}
