package com.neusoft.elm.view.Impl;

import java.util.List;
import java.util.Scanner;


import com.neusoft.elm.dao.FoodDao;
import com.neusoft.elm.dao.impl.FoodDaoImpl;
import com.neusoft.elm.po.Food;
import com.neusoft.elm.view.FoodView;

public class FoodViewImpl implements FoodView{
	
	private Scanner input = new Scanner(System.in);

	@Override
	public List<Food> showFoodList(int businessId) {
		FoodDao dao = new FoodDaoImpl();
		List<Food> list= dao.listFoodByBusinessId(businessId);
		System.out.println("食品编号\t食品名称\t食品介绍\t食品价格");
		for(Food f : list) {
			System.out.println(f.getFoodId() + "\t" + f.getFoodName()  + "\t" + f.getFoodExplain()  + "\t" + f.getFoodPrice());
		}
		return list;
		
	}

	@Override
	public void saveFood(int businessId) {
		FoodDao dao = new FoodDaoImpl();
		Food food = new Food();
		
		System.out.println("请输入食品名称");
		food.setFoodName(input.next());
		System.out.println("请输入食品介绍");
		food.setFoodExplain(input.next());
		System.out.println("请输入食品价格");
		food.setFoodPrice(input.nextDouble());
		food.setBusinessId(businessId);
		
		int result = dao.saveFood(food);
		if(result > 0) {
			System.out.println("新增食品成功");
		}
		else {
			System.out.println("新增食品失败");
		}
		
	}
	
	@Override
	public void updateFood(int businessId) {
		FoodDao dao = new FoodDaoImpl();
		List<Food> list = showFoodList(businessId);
		
		if(list.size() == 0) {
			System.out.println("当前食品列表为空");
		}
		else {
			System.out.println("\n请输入需要修改的食品编号：");
			int foodId = input.nextInt();
			Food food = dao.getFoodByFoodId(foodId);
			System.out.println(food);
			
			String inputStr = "";
			System.out.println("\n是否修改食品名称 (y/n)：");
			inputStr = input.next();
			if(inputStr.equals("y")) {
				System.out.println("请输入新的食品名称：");
				food.setFoodName(input.next());
			}
			
			System.out.println("是否修改食品介绍 (y/n)：");
			inputStr = input.next();
			if(inputStr.equals("y")) {
				System.out.println("请输入新的食品介绍：");
				food.setFoodExplain(input.next());
			}
			
			System.out.println("是否修改食品名价格(y/n)：");
			inputStr = input.next();
			if(inputStr.equals("y")) {
				System.out.println("请输入新的食品价格：");
				food.setFoodPrice(input.nextDouble());
			}
			
			int result = dao.updateFood(food);
			if(result > 0) {
				System.out.println("修改食品信息成功");
			}
			else {
				System.out.println("修改食品信息失败");
			}
		}
	}

	@Override
	public void removeFood(int businessId) {
		int result = 0;
		FoodDao dao = new FoodDaoImpl();
		List<Food> list = showFoodList(businessId);
		
		if(list.size() == 0) {
			System.out.println("当前食品列表为空");
		}
		else {
			System.out.println("\n请输入需要修改的食品编号：");
			int foodId = input.nextInt();
			
			String inputStr = "";
			System.out.println("\n确认是否删除 (y/n)：");
			inputStr = input.next();
			if(inputStr.equals("y")) {
				result = dao.removeFood(foodId);
			}
			
			if(result > 0) {
				System.out.println("删除食品信息成功");
			}
			else {
				System.out.println("删除食品信息失败");
			}
		}
		
	}
	
	

}
