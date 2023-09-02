package com.neusoft.elmboot.service;

import com.neusoft.elmboot.po.Orders;

public interface OrdersService {
	
	public int createOrders(Orders orders);
	
	public Orders getOrdersById (Integer orderId);

}
