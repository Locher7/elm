package com.neusoft.elm.view;

import java.util.List;

import com.neusoft.elm.po.Food;

public interface FoodView {
	
	public List<Food> showFoodList(int businessId);
	public void saveFood(int businessId);
	public void updateFood(int businessId);
	public void removeFood(int businessId);
}
