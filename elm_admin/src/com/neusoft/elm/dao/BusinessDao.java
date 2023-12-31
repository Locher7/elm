package com.neusoft.elm.dao;

import java.util.List;

import com.neusoft.elm.po.Business;

public interface BusinessDao {

	public List<Business> listBusiness(String businessName, String businessAdress);
	public int saveBusiness(String businessName); // 返回值为自动生成的businessId
	public int removeBusiness(int businessId); // 返回值为影响的行数
	
	public Business getBusinessByIdByPass(Integer businessId, String password); 
	public Business getBusinessById(Integer businessId); 
	public int updateBusiness(Business business);
	public int updateBusinessPassword(Integer businessId, String password);
}
