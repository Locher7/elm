package com.neusoft.elmboot.service;

import java.util.List;

import com.neusoft.elmboot.po.Cart;

public interface CartService {
	
	public List<Cart> listCart(Cart cart);
	
	public int saveCart(Cart cart);

}
