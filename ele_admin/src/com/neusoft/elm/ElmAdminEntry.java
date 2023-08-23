package com.neusoft.elm;

import java.util.Scanner;

public class ElmAdminEntry {
	public void work() {
		Scanner input = new Scanner(System.in);
		
		System.out.println("----------------------------------------------------------");
		System.out.println("| \t\t\t 饿了么后台管理系统 \t\t\t |");
		System.out.println("----------------------------------------------------------");
		
		// log in
		if (true) {
			int menu = 0;
			while(menu != 5) {
				// print main menu
				System.out.println("\n============= 1.所有商家列表  2.搜索商家  3.新建商家  4.删除商家  5.退出系统 =============\n");
				System.out.println("请输入你的选择：");
				try {
					menu = input.nextInt();
				} catch (Exception e) {
					menu = 6;
					input.nextLine(); // 清除输入缓冲区中的无效输入
				} 
				
				switch (menu) {
					case 1:
						System.out.println("所有商家列表");
						break;
					case 2:
						System.out.println("搜索商家");
						break;
					case 3:
						System.out.println("新建商家");
						break;
					case 4:
						System.out.println("删除商家");
						break;
					case 5:
						System.out.println("---------------------欢迎下次访问饿了么后台管理系统---------------------");
						break;
					default:
						System.out.println("没有该选项");
						break;
				}
			}
		}
		else {
			System.out.println("\n管理员名称或密码输入错误\n");
		}
		
		input.close();
	}
	
	public static void main(String[] args) {
		new ElmAdminEntry().work();
	}
}
