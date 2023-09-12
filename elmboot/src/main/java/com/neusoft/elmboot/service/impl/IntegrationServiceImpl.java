package com.neusoft.elmboot.service.impl;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.elmboot.mapper.IntegrationMapper;
import com.neusoft.elmboot.mapper.OrdersMapper;
import com.neusoft.elmboot.po.Integration;
import com.neusoft.elmboot.po.Orders;
import com.neusoft.elmboot.service.IntegrationService;
import com.neusoft.elmboot.util.CommonUtil;

@Service
public class IntegrationServiceImpl implements IntegrationService {

	@Autowired
	private IntegrationMapper integrationMapper;
	
	@Autowired
	private OrdersMapper ordersMapper;

	@Override
	public List<Integration> getDetailsByUserId(String userId) {
		// TODO Auto-generated method stub
		return integrationMapper.getDetailByUserId(userId);
	}

	@Override
	@Transactional
	public Integer getCreditByUserId(String userId) {
		// System.out.println("IntGet");
		//Integer result = 0;
		Integration integration = new Integration();
		
		List<Integration> list = integrationMapper.getDetailByUserId(userId);
		Collections.reverse(list);
		Iterator<Integration> it = list.iterator();
		
		String nowDate = CommonUtil.getCurrentDate();
		while (it.hasNext()) {
			integration = it.next();
			long dateDiff = CommonUtil.dateToStamp(nowDate) - CommonUtil.dateToStamp(integration.getIntegrationDate());
			if (integration.getIntegrationState() == 0 && dateDiff > CommonUtil.validDate) {
				System.out.println(dateDiff);
				integration.setIntegrationState(1);
				integrationMapper.updateIntegration(integration);
				// 更新历史记录为过期
				
				integration.setPoints(integration.getUsedPoints()-integration.getPoints());
				integration.setIntegrationState(1);
				integration.setIntegrationDate(nowDate);
				integrationMapper.insertIntegration(integration);
				// 加入积分减少的记录
				
			}
		}
		return integrationMapper.getCreditByUserId(userId);
	}
	
	@Override
	@Transactional
	public Integer payCredit(Integration integration) {
		String nowDate = CommonUtil.getCurrentDate();
		
		Integer orderId = integration.getUsedPoints();
		
		Integer flag = integration.getIntegrationState();
		integration.setUsedPoints(0);
		integration.setIntegrationDate(nowDate);
		integration.setIntegrationState(0);
		
		Orders orders = ordersMapper.getOrdersById(orderId);
		orders.setOrderState(1);
		Integer realPoints = orders.getOrderTotal(); // 订单价格
		Integer costPoints = orders.getOrderTotal()/100; // 如果使用积分应该减少的积分
		Integer finalPoints =  (realPoints-costPoints*10)/100;  // 如果使用积分应该增加的积分
		
		// System.out.println(realPoints);
		if(flag == 1) {
			orders.setOrderTotal(realPoints-costPoints*10); // 折扣价格
		} else {
			orders.setOrderTotal(realPoints);
		}
		ordersMapper.updateOrders(orders);
		
		integration.setIntegrationState(2);
		integration.setPoints(costPoints);
		
		if(flag == 1) { // 表示使用积分
			
			//System.out.println("进入");
			List<Integration> list = integrationMapper.listIntegration(integration);
			// 得到历史账单 并从前往后遍历
			//Collections.reverse(list);
			Iterator<Integration> it = list.iterator();
			
			while(it.hasNext() && costPoints != 0) {
				Integration record = it.next();
				if(record.getIntegrationState() == 0) {
					Integer remain = record.getPoints() - record.getUsedPoints(); //剩余积分
					if(remain > costPoints) {  // 够了
						record.setUsedPoints(record.getUsedPoints()+costPoints);
						integrationMapper.updateIntegration(record);
						costPoints = 0;
					} else { // 不够或者刚好
						record.setUsedPoints(record.getPoints());
						record.setIntegrationState(2);
						integrationMapper.updateIntegration(record);
						costPoints -= remain;
					}
				}
			}
			realPoints = finalPoints;
			integrationMapper.insertIntegration(integration);
		}
		
		integration.setIntegrationState(0);
		integration.setPoints(realPoints);
		return (Integer)integrationMapper.insertIntegration(integration);
	}
}
