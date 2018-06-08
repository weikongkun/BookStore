package com.wkk.product.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wkk.product.exception.UserException;
import com.wkk.product.service.UserService;

public class ActiveServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取激活码
		String activeCode = request.getParameter("activeCode");
		
		UserService us = new UserService();
		try {
			us.activeUser(activeCode);
		} catch (UserException e) {
			e.printStackTrace();
			//将失败信息显示到页面
			response.getWriter().write(e.getMessage());
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.doGet(request, response);
	}

}
