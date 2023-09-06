package com.neusoft.elmboot.service;

import java.io.IOException;
import java.util.List;

import com.neusoft.elmboot.mapper.CartMapper;
import com.neusoft.elmboot.po.Cart;

public interface CartService {
	
	public List<Cart> listCart(Cart cart);
	
	public int saveCart(Cart cart);
	
	public int updateCart(Cart cart);
	
	public int removeCart(Cart cart);
	
	public String aiSuggestion (List<Cart> list) throws IOException;

}
