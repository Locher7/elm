package com.neusoft.elmboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neusoft.elmboot.po.Cart;
import com.neusoft.elmboot.service.CartService;

@RestController
@CrossOrigin(origins = "http://localhost:8080") // 允许来自此域的请求
@RequestMapping("/CartController")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@RequestMapping("/listCart")
	public List<Cart> listCart(Cart cart) throws Exception{
		return cartService.listCart(cart);
	}
	
	@RequestMapping("/saveCart")
	public int saveCart(Cart cart) throws Exception{
		return cartService.saveCart(cart);
	}
	
	@RequestMapping("/updateCart")
	public int updateCart(Cart cart) throws Exception{
		return cartService.updateCart(cart);
	}
	
	@RequestMapping("/removeCart")
	public int removeCart(Cart cart) throws Exception{
		return cartService.removeCart(cart);
	}
	
	@RequestMapping("/aiSuggestion")
	public String aiSuggestion (Cart cart) throws Exception{
		return cartService.aiSuggestion(cart);
	 
	}
}
