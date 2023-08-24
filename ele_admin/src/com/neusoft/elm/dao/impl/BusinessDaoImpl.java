package com.neusoft.elm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.neusoft.elm.dao.BusinessDao;
import com.neusoft.elm.po.Business;
import com.neusoft.elm.util.DBUtil;

public class BusinessDaoImpl implements BusinessDao{

	private Connection con = null;
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	
	@Override
	// 当返回值为一个集合时 命名为list
	public List<Business> listBusiness(String businessName, String businessAddress) {
		// 当返回值为一个集合时 用new初始化  是否成功查询到目标用list的length来检查
		List<Business> list = new ArrayList<>();
		StringBuffer sql = new StringBuffer("select * from business where 1 = 1 ");
		if(businessName != null && !businessName.equals("")) {
			sql.append("and businessName like '%" + businessName + "%' ");
		}
		if(businessAddress != null && !businessAddress.equals("")) {
			sql.append("and businessAddress like '%" + businessAddress + "%' ");
		}
		
		try {
			con = DBUtil.getConnection();  // 创建与数据库的连接
			pst = con.prepareStatement(sql.toString()); // 预编译sql语句
			rs = pst.executeQuery(); // 执行查询 保存结果到rs
			// 查到多行数据  每次while对一行进行遍历   封装到一个对象中  将该对象加入list
			while (rs.next()) {
				Business business = new Business();
				business.setBusinessId(rs.getInt("businessId"));
				business.setPassword(rs.getString("password"));
				business.setBusinessName(rs.getString("businessName"));
				business.setBusinessAddress(rs.getString("businessAddress"));
				business.setBusinessExplain(rs.getString("businessExplain"));
				business.setStarPrice(rs.getDouble("starPrice"));
				business.setDeliveryPrice(rs.getDouble("deliveryPrice"));
				list.add(business);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 释放资源
			DBUtil.close(rs, pst, con);
		}
		
		return list;
	}
	
	@Override
	public int saveBusiness(String businessName) {
		int businessId = 0;
		String sql = "insert into business(businessName, password) values(?, '123')";
		
		try {
			con = DBUtil.getConnection();  
			// 设置返回自增长列的值
			pst = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS); 
			pst.setString(1, businessName);
			pst.executeUpdate(); // 执行更新 
			// 获取自增长列值 （一行一列）
			rs = pst.getGeneratedKeys();
			if(rs.next()) {
				businessId = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 释放资源
			DBUtil.close(rs, pst, con);
		}
		
		return businessId;
	}
	
	@Override
	public int removeBusiness(int businessId) {
		int result = 0;
		String delFoodSql = "delete from food where businessId = ?";
		String delBusinessSql = "delete from business where businessId = ?";
		
		try {
			con = DBUtil.getConnection();  
			
			// 默数据库连接处于自动提交模式，这意味着每个 SQL 语句都会立即提交到数据库
			// 关闭自动提交即开启一个事务
			con.setAutoCommit(false);
			
			// 先在子表中删除
			pst = con.prepareStatement(delFoodSql);
			pst.setInt(1, businessId);
			pst.executeUpdate();
			
			// 后在主表中删除
			pst = con.prepareStatement(delBusinessSql);
			pst.setInt(1, businessId);
			result = pst.executeUpdate();
			
			
			// 结束事务
			con.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
			result = 0;
			try {
				// 若失败 则回滚。 保持数据库一致性
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			// 释放资源
			DBUtil.close(null, pst, con);
		}
		
		return result;
	}
	
	@Override
	public Business getBusinessByIdByPass(Integer businessId, String password) {
		Business business = null;
		String sql = "select * from business where businessId = ? and password = ? ";
		
		try {
			con = DBUtil.getConnection();  // 创建与数据库的连接
			pst = con.prepareStatement(sql); // 预编译sql语句
			pst.setInt(1, businessId);
			pst.setString(2, password);
			rs = pst.executeQuery(); // 执行查询 保存结果到rs
			// 查到多行数据  每次while对一行进行遍历   封装到一个对象中  将该对象加入list
			while (rs.next()) {
				business = new Business();
				business.setBusinessId(rs.getInt("businessId"));
				business.setPassword(rs.getString("password"));
				business.setBusinessName(rs.getString("businessName"));
				business.setBusinessAddress(rs.getString("businessAddress"));
				business.setBusinessExplain(rs.getString("businessExplain"));
				business.setStarPrice(rs.getDouble("starPrice"));
				business.setDeliveryPrice(rs.getDouble("deliveryPrice"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 释放资源
			DBUtil.close(rs, pst, con);
		}
		
		return business;

	}
	
	@Override
	public Business getBusinessById(Integer businessId){
		Business business = null;
		String sql = "select * from business where businessId = ?";
		
		try {
			con = DBUtil.getConnection();  // 创建与数据库的连接
			pst = con.prepareStatement(sql); // 预编译sql语句
			pst.setInt(1, businessId);
			rs = pst.executeQuery(); // 执行查询 保存结果到rs
			// 查到多行数据  每次while对一行进行遍历   封装到一个对象中  将该对象加入list
			while (rs.next()) {
				business = new Business();
				business.setBusinessId(rs.getInt("businessId"));
				business.setPassword(rs.getString("password"));
				business.setBusinessName(rs.getString("businessName"));
				business.setBusinessAddress(rs.getString("businessAddress"));
				business.setBusinessExplain(rs.getString("businessExplain"));
				business.setStarPrice(rs.getDouble("starPrice"));
				business.setDeliveryPrice(rs.getDouble("deliveryPrice"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 释放资源
			DBUtil.close(rs, pst, con);
		}
		
		return business;
	}
	
	@Override
	public int updateBusiness(Business business) {
		int result = 0;
		String sql = "update business set businessName = ?, businessAddress = ?, businessExplain = ?, starPrice = ?, deliveryPrice = ? where businessId = ?";
		
		try {
			con = DBUtil.getConnection();  
			pst = con.prepareStatement(sql);
			pst.setString(1, business.getBusinessName());
			pst.setString(2, business.getBusinessAddress());
			pst.setString(3, business.getBusinessExplain());
			pst.setDouble(4, business.getStarPrice());
			pst.setDouble(5, business.getDeliveryPrice());
			pst.setInt(6, business.getBusinessId());
			result = pst.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			result = 0;
		} finally {
			// 释放资源
			DBUtil.close(null, pst, con);
		}
		
		return result;
	}

	@Override
	public int updateBusinessPassword(Integer businessId, String password) {
		int result = 0;
		String sql = "update business set password = ? where businessId = ?";
		
		try {
			con = DBUtil.getConnection();  
			pst = con.prepareStatement(sql);
			pst.setString(1, password);
			pst.setInt(2, businessId);
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result = 0;
		} finally {
			// 释放资源
			DBUtil.close(null, pst, con);
		}
		
		return result;
	}
}
