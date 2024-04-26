package com.neusoft.service;

import java.util.List;

import com.neusoft.po.Integration;

public interface IntegrationService {

	public List<Integration> getDetailsByUserId(String userId);
	
	public Integer getCreditByUserId(String userId);
	
	public Integer payCredit(Integration integration);
}
