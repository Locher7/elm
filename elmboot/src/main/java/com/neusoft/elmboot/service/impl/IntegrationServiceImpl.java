package com.neusoft.elmboot.service.impl;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public Integer payCredit(Integration integration) {
		String nowDate = CommonUtil.getCurrentDate();
		//Integer realPoints = new Integer(integration.getPoints());
		Integer costPoints = new Integer(integration.getPoints());
		Integer orderId = integration.getUsedPoints();
		
		Integer flag = integration.getIntegrationState();
		integration.setUsedPoints(0);
		integration.setIntegrationDate(nowDate);
		integration.setIntegrationState(0);
		
		Orders orders = ordersMapper.getOrdersById(orderId);
		orders.setOrderState(1);
		Double realPoints = new Double(orders.getOrderTotal());
		System.out.println(realPoints);
		if(flag == 1) {
			orders.setOrderTotal(realPoints*0.9);
		} else {
			orders.setOrderTotal(realPoints);
		}
		ordersMapper.updateOrders(orders);
		
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
				
			integration.setIntegrationState(2);
			integration.setPoints(new Integer((int)-Math.round(realPoints)));
			integrationMapper.insertIntegration(integration);

			realPoints = realPoints*0.9;
		}
		
		integration.setIntegrationState(0);
		integration.setPoints(new Integer((int)Math.round(realPoints)));
		return (Integer)integrationMapper.insertIntegration(integration);
	}
}
