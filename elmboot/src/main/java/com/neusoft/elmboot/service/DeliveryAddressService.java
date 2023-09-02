package com.neusoft.elmboot.service;

import java.util.List;

import com.neusoft.elmboot.po.DeliveryAddress;

public interface DeliveryAddressService {
	
	public List<DeliveryAddress> listDeliveryAddressByUserId(String userId);

}
