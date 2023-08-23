package com.neusoft.elm.view.Impl;

import java.util.Scanner;

import javax.print.DocFlavor.INPUT_STREAM;

import com.neusoft.elm.dao.AdminDao;
import com.neusoft.elm.dao.impl.AdminDaoImpl;
import com.neusoft.elm.po.Admin;
import com.neusoft.elm.view.AdminView;

public class AdminViewImpl implements AdminView{

	private Scanner input = new Scanner(System.in);
	
	@Override
	public Admin login() {
		System.out.println("请输入管理员名称：");
		String adminName = input.next();
		System.out.println("请输入密码：");
		String password = input.next();
		
		AdminDao dao = new AdminDaoImpl();
		return dao.getAdminByNameByPass(adminName, password);
	}
	

}
