package com.neusoft.elm;

import java.util.Scanner;

import com.neusoft.elm.po.Admin;
import com.neusoft.elm.po.Business;
import com.neusoft.elm.view.AdminView;
import com.neusoft.elm.view.BusinessView;
import com.neusoft.elm.view.Impl.AdminViewImpl;
import com.neusoft.elm.view.Impl.BusinessViewImpl;

public class ElmBusinessEntry {

	
	public void work() {
		Scanner input = new Scanner(System.in);
		
		System.out.println("------------------------------------------------------------------");
		System.out.println("| \t\t\t 饿了么后台商家管理系统 \t\t\t |");
		System.out.println("------------------------------------------------------------------");
		
		
		BusinessView businessView = new BusinessViewImpl();
		// log in
		Business business = businessView.login();
		if (business != null) {
			int menu = 0;
			while(menu != 5) {
				// print main menu
				System.out.println("\n============= 一级菜单（商家管理） 1.查看商家信息  2.修改商家信息  3.更新密码  4.所属商品管理  5.退出系统 =============\n");
				System.out.println("请输入你的选择：");
				try {
					menu = input.nextInt();
				} catch (Exception e) {
					menu = 6;
					input.nextLine(); // 清除输入缓冲区中的无效输入
				} 
				
				switch (menu) {
					case 1:
						businessView.showBusiness(business.getBusinessId());
						break;
					case 2:
						businessView.editBusiness(business.getBusinessId());
						break;
					case 3:
						businessView.editBusinessPassword(business.getBusinessId());
						break;
					case 4:
						foodmanager();
						break;
					case 5:
						System.out.println("---------------------欢迎下次访问饿了么后台商家管理系统---------------------");
						break;
					default:
						System.out.println("没有该选项");
						break;
				}
			}
		}
		else {
			System.out.println("\n商家编号或密码输入错误\n");
		}
		
		input.close();
	}
	
	private void foodmanager() {
		Scanner input = new Scanner(System.in);
		
		int menu = 0;
		while(menu != 5) {
			System.out.println("\n============= 二级菜单（食品管理） 1.查看食品列表  2.新增食品  3.修改食品  4.删除食品  5.返回一级菜单 =============\n");
			System.out.println("请输入你的选择：");
			try {
				menu = input.nextInt();
			} catch (Exception e) {
				menu = 6;
				input.nextLine(); // 清除输入缓冲区中的无效输入
			} 
			
			switch (menu) {
				case 1:
					System.out.println("1.查看食品列表");
					break;
				case 2:
					System.out.println("2.新增食品");
					break;
				case 3:
					System.out.println("3.修改食品");
					break;
				case 4:
					System.out.println("4.删除食品");
					break;
				case 5:
					break;
				default:
					System.out.println("没有该选项");
					break;
			}
		}
	}
	
	public static void main(String[] args) {
		new ElmBusinessEntry().work();
	}
}
