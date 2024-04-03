package com.neusoft.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neusoft.po.CommonResult;
import com.neusoft.po.Integration;
import com.neusoft.po.Orders;
import com.neusoft.service.IntegrationService;

@RestController
@RequestMapping("/IntegrationController")
public class IntegrationController {
	
	@Autowired
	private IntegrationService integrationService;
	
	@GetMapping("/getDetailsByUserId/{userId}")
	public CommonResult<List> getDetailsByUserId(@PathVariable("userId") String userId) throws Exception {
		List<Integration> list = integrationService.getDetailsByUserId(userId);
		return new CommonResult(200,"success",list);
	}

	@GetMapping("/getCreditByUserId/{userId}")
	public CommonResult<Integer> getCreditByUserId(@PathVariable("userId") String userId) throws Exception {
		//System.out.println("here");
		int result = integrationService.getCreditByUserId(userId);
		return new CommonResult(200,"success",result);
	}
	
	@PutMapping("/payCredit/{userId}/{usedPoints}/{integrationState}")
	public CommonResult<Integer> payCredit(
			@PathVariable("userId") String userId,
			@PathVariable("usedPoints") Integer usedPoints,
			@PathVariable("integrationState") Integer integrationState) throws Exception {
//		System.out.println(integration.getUserId());
//		System.out.println(integration.getPoints());
//		System.out.println(integration.getUsedPoints());
//		System.out.println(integration.getIntegrationState());
		Integration param = new Integration();
		param.setUserId(userId);
		param.setUsedPoints(usedPoints);
		param.setIntegrationState(integrationState);
		int result = integrationService.payCredit(param);
		return new CommonResult(200,"success",result);
	}
}
