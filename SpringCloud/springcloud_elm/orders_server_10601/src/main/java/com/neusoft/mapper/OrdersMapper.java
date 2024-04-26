package com.neusoft.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Update;

import com.neusoft.po.Integration;
import com.neusoft.po.Orders;

@Mapper
public interface OrdersMapper {

	@Insert("insert into orders(userId, businessId, orderDate, orderTotal, daId, orderState) values(#{userId}, #{businessId}, #{orderDate}, #{orderTotal}, #{daId}, 0)")
	@Options(useGeneratedKeys = true, keyProperty = "orderId", keyColumn = "orderId")
	public int saveOrders(Orders orders);
	
	
	public Orders getOrdersById (Integer orderId);
	
	@Update("update orders set orderState = #{orderState}, orderTotal = #{orderTotal} where orderId = #{orderId}")
	public int updateOrders(Orders orders);
	
	public List<Orders> listOrdersByUserId (String userId);

}
