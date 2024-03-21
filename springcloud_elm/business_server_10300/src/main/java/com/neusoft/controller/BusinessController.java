package com.neusoft.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.neusoft.po.Business;
import com.neusoft.po.CommonResult;
import com.neusoft.service.BusinessService;

@RestController
@CrossOrigin("*") // 允许来自此域的请求
@RequestMapping("/BusinessController")
public class BusinessController {

	@Autowired
	private BusinessService businessService;
	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/listBusinessByOrderTypeId/{orderTypeId}")
	public CommonResult<List> listBusinessByOrderTypeId(@PathVariable("orderTypeId") Integer orderTypeId)
			throws Exception {
		List<Business> list = businessService.listBusinessByOrderTypeId(orderTypeId);
		return new CommonResult(200, "success", list);
	}

	@GetMapping("/getBusinessById/{businessId}")
	public CommonResult<Business> getBusinessById(@PathVariable("businessId") Integer businessId) throws Exception {
		Business business = businessService.getBusinessById(businessId);
		// 在商家微服务中调用食品微服务
		CommonResult<List> result = restTemplate.getForObject(
				"http://localhost:10200/FoodController/listFoodByBusinessId/" + businessId, CommonResult.class);
		if (result.getCode() == 200) {
			business.setFoodList(result.getResult());
		}
		return new CommonResult(200, "success", business);
	}
}