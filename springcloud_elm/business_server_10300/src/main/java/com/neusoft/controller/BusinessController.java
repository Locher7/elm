package com.neusoft.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
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
	@Autowired
	private DiscoveryClient discoveryClient;

	@GetMapping("/listBusinessByOrderTypeId/{orderTypeId}")
	public CommonResult<List> listBusinessByOrderTypeId(@PathVariable("orderTypeId") Integer orderTypeId)
			throws Exception {
		List<Business> list = businessService.listBusinessByOrderTypeId(orderTypeId);
		return new CommonResult(200, "success", list);
	}

	@GetMapping("/getBusinessById/{businessId}")
	public CommonResult<Business> getBusinessById(@PathVariable("businessId") Integer businessId) throws Exception {
		 // 通过食品微服务名（food-server），获取Eureka server上的元数据
		List<ServiceInstance> instanceList = discoveryClient.getInstances("food-server");
		// 集合中现在只有一个微服务实例，所以直接获取即可
		ServiceInstance instance = instanceList.get(0);
		
		Business business = businessService.getBusinessById(businessId);
		// 在商家微服务中调用食品微服务
		CommonResult<List> result = restTemplate.getForObject(
				"http://"+instance.getHost()+":"+instance.getPort()+"/FoodController/listFoodByBusinessId/" + businessId, CommonResult.class);
		if (result.getCode() == 200) {
			business.setFoodList(result.getResult());
		}
		return new CommonResult(200, "success", business);
	}
}
