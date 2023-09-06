package com.neusoft.elmboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neusoft.elmboot.po.Business;
import com.neusoft.elmboot.service.BusinessService;

@RestController
@CrossOrigin(origins = "http://localhost:8080") // 允许来自此域的请求
@RequestMapping("/BusinessController")
public class BusinessController {
	
	@Autowired
	private BusinessService businessService;
	
	@RequestMapping("/listBusinessByOrderTypeId")
	public List<Business> listBusinessByOrderTypeId(Business business) throws Exception{
		return businessService.listBusinessByOrderTypeId(business.getOrderTypeId());
	}
	
	@RequestMapping("/getBusinessById")
	public Business getBusinessById(Business business) throws Exception{
		return businessService.getBusinessById(business.getBusinessId());
	}

}
