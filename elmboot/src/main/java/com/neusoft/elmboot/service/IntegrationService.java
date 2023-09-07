package com.neusoft.elmboot.service;

import java.util.List;

import com.neusoft.elmboot.po.Integration;

public interface IntegrationService {

	public List<Integration> getDetailsByUserId(String userId);
	
	public Integer getCreditByUserId(String userId);
	
	public Integer payCredit(Integration integration);
}
