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
	public List<Business> listBusiness() {
		// 当返回值为一个集合时 用new初始化  是否成功查询到目标用list的length来检查
		List<Business> list = new ArrayList<>();
		String sql = "select * from business order by businessId";
		
		try {
			con = DBUtil.getConnection();  // 创建与数据库的连接
			pst = con.prepareStatement(sql); // 预编译sql语句
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

}
