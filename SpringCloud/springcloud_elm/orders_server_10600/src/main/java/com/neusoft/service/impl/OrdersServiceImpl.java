package com.neusoft.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.mapper.CartMapper;
import com.neusoft.mapper.OrderDetailetMapper;
import com.neusoft.mapper.OrdersMapper;
import com.neusoft.po.Cart;
import com.neusoft.po.OrderDetailet;
import com.neusoft.po.Orders;
import com.neusoft.service.OrdersService;
import com.neusoft.util.CommonUtil;


@Service
public class OrdersServiceImpl implements OrdersService{
	
	@Autowired
	private CartMapper cartMapper;
	@Autowired
	private OrdersMapper ordersMapper;
	@Autowired
	private OrderDetailetMapper orderDetailetMapper;

	@Override
	@Transactional
	public int createOrders(Orders orders) {
		
		// 查询当前用户购物车中当前商家的所有食品
		Cart cart = new Cart();
		cart.setUserId(orders.getUserId());
		cart.setBusinessId(orders.getBusinessId());
		List<Cart> cartList = cartMapper.listCart(cart);
		
		// 创建订单 (返回生成的订单编号)
		orders.setOrderDate(CommonUtil.getCurrentDate());
		ordersMapper.saveOrders(orders);
		int orderId = orders.getOrderId();
		
		// 批量添加订单明细
		List<OrderDetailet> list = new ArrayList<>();
		for(Cart c: cartList) {
			OrderDetailet od = new OrderDetailet();
			od.setOrderId(orderId);
			od.setFoodId(c.getFoodId());
			od.setQuantity(c.getQuantity());
			list.add(od);
		}
		System.out.println("/**\n\n\n开始orderDetailetMapper.saveOrderDetailetBatch\n\n**/");
		orderDetailetMapper.saveOrderDetailetBatch(list);
		System.out.println("/**\n\n\n开始remove\n\n**/");
		// 从购物车表中删除相关食品信息
		cartMapper.removeCart(cart);
		
		return orderId;
	}
	
	@Override
	public Orders getOrdersById (Integer orderId) {
		return ordersMapper.getOrdersById(orderId);
	}
	
	@Override
	public List<Orders> listOrdersByUserId (String userId){
		return ordersMapper.listOrdersByUserId(userId);
	}
}
