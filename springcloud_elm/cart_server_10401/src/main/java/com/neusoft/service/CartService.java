package com.neusoft.service;

import java.io.IOException;
import java.util.List;

import com.neusoft.mapper.CartMapper;
import com.neusoft.po.Cart;

public interface CartService {
	
	public List<Cart> listCart(Cart cart);
	
	public int saveCart(Cart cart);
	
	public int updateCart(Cart cart);
	
	public int removeCart(Cart cart);
	
	public String aiSuggestion (Cart cart) throws IOException;

}
