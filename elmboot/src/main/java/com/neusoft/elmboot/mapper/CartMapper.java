package com.neusoft.elmboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.neusoft.elmboot.po.Cart;

@Mapper
public interface CartMapper {
	
	public List<Cart> listCart(Cart cart);
	
	public int saveCart(Cart cart);
}
