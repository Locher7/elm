package com.neusoft.elmboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neusoft.elmboot.po.Integration;
import com.neusoft.elmboot.service.IntegrationService;

@RestController
@CrossOrigin(origins = "http://localhost:8080") // 允许来自此域的请求
@RequestMapping("/IntegrationController")
public class IntegrationController {
	
	@Autowired
	private IntegrationService integrationService;
	
	@RequestMapping("/getDetailsByUserId")
	public List<Integration> getDetailsByUserId(Integration integration) throws Exception {
		return integrationService.getDetailsByUserId(integration.getUserId());
	}

	@RequestMapping("/getCreditByUserId")
	public Integer getCreditByUserId(Integration integration) throws Exception {
		//System.out.println("here");
		return integrationService.getCreditByUserId(integration.getUserId());
	}
	
	@RequestMapping("/payCredit")
	public Integer payCredit(Integration integration) throws Exception {
//		System.out.println(integration.getUserId());
//		System.out.println(integration.getPoints());
//		System.out.println(integration.getUsedPoints());
//		System.out.println(integration.getIntegrationState());
		return integrationService.payCredit(integration);
	}
}
