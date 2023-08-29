package com.neusoft.elm.framework;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/") 
public class DispatcherServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, UnsupportedEncodingException {
		// 中文编码处理
		request.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("application/json;charset=utf-8");
		
		// 获取客户端请求路径
		String path = request.getServletPath();
		
		// 根据请求路径解析组件名和方法名
		String className = path.substring(1, path.lastIndexOf("/"));
		String methodName = path.substring(path.lastIndexOf("/")+1);

		PrintWriter out = null;
		
		try {
			// 通过Class.forName获取controller类的信息
			Class clazz = Class.forName("com.neusoft.elm.controller."+className);
			Object controller = clazz.newInstance();
			Method method = clazz.getMethod(methodName, new Class[] {HttpServletRequest.class});
			Object result = method.invoke(controller, new Object[] {request});
			
			out = resp.getWriter();
			ObjectMapper om = new ObjectMapper();
			out.print(om.writeValueAsString(result));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("DispatcherServlet信息：Servlet请求路径："+path);
			System.out.println("DispatcherServlet信息：类名："+className+"\t方法名："+methodName);
		} finally {
			if(out != null) {
				out.close();
			}
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
}
